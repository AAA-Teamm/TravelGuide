package com.ateam.travelguide.presentation.ui.fragment.location_visit_history

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.FragmentLocationVisitHistoryBinding
import com.ateam.travelguide.presentation.adapter.VisitHistoryImageListAdapter
import com.ateam.travelguide.util.Constant.REQ_CODE_CAMERA
import com.ateam.travelguide.util.Constant.REQ_CODE_GALLERY
import com.ateam.travelguide.util.VisitHistoryImagesClickListener
import java.io.File
import java.io.IOException

class LocationVisitHistoryFragment : Fragment(), VisitHistoryImagesClickListener {

    private var _binding: FragmentLocationVisitHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VisitHistoryImageListAdapter
    private lateinit var viewModel: LocationVisitHistoryViewModel
    private lateinit var imageList: ArrayList<Uri>
    private lateinit var imagePath: String
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationVisitHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LocationVisitHistoryViewModel::class.java)
        // todo "we will be update this line"
        binding.apply {
            toolbarTitle.text = "Konum AdiX"
        }

        imageList = ArrayList()
        imageList.add(Uri.EMPTY)

        adapter = VisitHistoryImageListAdapter(this)
        updateImageListInRecycler(imageList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickedDeleteButton(position: Int) {
        imageList.removeAt(position)
        updateImageListInRecycler(imageList)
    }

    override fun onClickAddNewPhoto() {
        askAddNewPhotoLocation()
    }

    private fun askAddNewPhotoLocation() {
        val adb = AlertDialog.Builder(requireContext())
        adb.setTitle(getString(R.string.where_are_you_want_to_upload_image_from))
        adb.setPositiveButton(getString(R.string.camera)) { _, _ ->
            checkCameraPermission()
        }
        adb.setNegativeButton(getString(R.string.Gallery)) { _, _ ->
            checkGalleryPermission()
        }
        adb.show()
    }

    private fun checkCameraPermission() {
        val requestList = ArrayList<String>()
        var permissionState = ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

        if (!permissionState) {
            requestList.add(Manifest.permission.CAMERA)
        }

        permissionState = ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        if (!permissionState) {
            requestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        permissionState = ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        if (!permissionState) {
            requestList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (requestList.size == 0) {
            openCamera()
        } else {
            requestPermissions(requestList.toTypedArray(), REQ_CODE_CAMERA)
        }
    }

    private fun checkGalleryPermission() {
        val requestList = ArrayList<String>()
        val permissionState = ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        if (!permissionState) {
            requestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (requestList.size == 0) {
            openGallery()
        } else {
            requestPermissions(requestList.toTypedArray(), REQ_CODE_GALLERY)
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = createImageFile()
        imageUri = FileProvider.getUriForFile(requireContext(), requireContext().packageName, file)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraResultLauncher.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryResultLauncher.launch(intent)
    }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                imageList.add(it.data!!.data!!)
                updateImageListInRecycler(imageList)
            }
        }

    private var cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                imageList.add(imageUri)
                updateImageListInRecycler(imageList)
                Toast.makeText(requireContext(),
                    getString(R.string.image_taken),
                    Toast.LENGTH_SHORT).show()
            }
        }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var approvedAll = true
        for (gr in grantResults) {
            if (gr != PackageManager.PERMISSION_GRANTED) {
                approvedAll = false
                break
            }
        }

        if (!approvedAll) {
            var doNotShowAgain = false
            for (permission in permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                        permission)
                ) {
                    // reddedilmistir -> Sadece reddet denmistir tamamen reddet degil
                } else if (ContextCompat.checkSelfPermission(requireContext(),
                        permission) == PackageManager.PERMISSION_GRANTED
                ) {
                    // onaylandi
                } else {
                    // Tekrar gosterme demek
                    doNotShowAgain = true
                    break
                }
            }

            if (doNotShowAgain) {
                val adb = AlertDialog.Builder(requireContext())
                adb.setTitle(getString(R.string.permission_required))
                    .setMessage(getString(R.string.go_settings_and_approve_permissions))
                    .setPositiveButton(getString(R.string.settings)) { _, _ ->
                        openSettings()
                    }
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show()
            }

        } else {
            when (requestCode) {
                REQ_CODE_CAMERA -> {
                    openCamera()
                }

            }
        }
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val dir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("image", ".jpg", dir).apply {
            imagePath = absolutePath
        }
    }

    private fun updateImageListInRecycler(imageList: List<Uri>) {
        updateNewPhotoState()
        adapter.imageList = imageList
        binding.recyclerView.adapter = adapter
    }

    private fun updateNewPhotoState() {
        for (i in imageList.indices) {
            if (imageList[i] == Uri.EMPTY && imageList.size > i) {
                imageList.removeAt(i)
                imageList.add(Uri.EMPTY)
            }
        }
    }

}