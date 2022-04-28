package com.ateam.travelguide.presentation.ui.fragment.location_visit_history

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.FragmentLocationVisitHistoryBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.model.VisitHistory
import com.ateam.travelguide.presentation.adapter.VisitHistoryImageListAdapter
import com.ateam.travelguide.util.*
import com.ateam.travelguide.util.Constant.NO_IMAGE_FEATURE
import com.ateam.travelguide.util.Constant.REQ_CODE_CAMERA
import com.ateam.travelguide.util.Constant.REQ_CODE_GALLERY
import com.ateam.travelguide.util.Permissions.Companion.cameraRequestList
import com.ateam.travelguide.util.Permissions.Companion.checkCameraPermission
import com.ateam.travelguide.util.Permissions.Companion.checkGalleryPermission
import com.ateam.travelguide.util.Permissions.Companion.galleryRequestList
import java.util.*

class LocationVisitHistoryFragment : Fragment(), VisitHistoryImagesClickListener {

    private var _binding: FragmentLocationVisitHistoryBinding? = null
    private val binding get() = _binding!!
    private val calendar: Calendar = Calendar.getInstance()
    private val args: LocationVisitHistoryFragmentArgs by navArgs()
    private var locationId: Int = 0
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var lastImageListSize = 0
    private lateinit var adapter: VisitHistoryImageListAdapter
    private lateinit var viewModel: LocationVisitHistoryViewModel
    private lateinit var imageList: ArrayList<Image?>
    private lateinit var imageUri: Uri
    private lateinit var defaultNoImageData: Image
    private lateinit var locationInfo: Location

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationVisitHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initClickListeners()
    }

    private fun initView() {
        locationId = args.locationId
        viewModel = ViewModelProvider(this).get(LocationVisitHistoryViewModel::class.java)
        adapter = VisitHistoryImageListAdapter(this)
        imageList = ArrayList()
        defaultNoImageData = Image(0, NO_IMAGE_FEATURE, null, null, null, locationId)
        locationInfo = viewModel.getLocationInfo(requireContext(), locationId)
        val today = String().getTheDay(calendar)

        day = String().getTheDayWithSeparate(calendar)[0]
        month = String().getTheDayWithSeparate(calendar)[1]
        year = String().getTheDayWithSeparate(calendar)[2]

        binding.apply {
            toolbarTitle.text = locationInfo.name
        }

        imageList = viewModel.getAllImage(requireContext(), locationId)
        lastImageListSize = imageList.size

        if (imageList.size < 10) {
            imageList.add(defaultNoImageData)
        }

        binding.textViewDate.text = today

        updateImageListInRecycler(imageList)
    }

    private fun initClickListeners() {
        binding.buttonSave.setOnClickListener {
            deleteAllLocationImages(locationId)
            val visitHistory = VisitHistory(
                id = 0,
                year = calendar[Calendar.YEAR],
                month = calendar[Calendar.MONTH] + 1,
                day = calendar[Calendar.DATE],
                longDescription = binding.editTextDescription.text.toString(),
                historyLocationId = locationId
            )
            viewModel.addVisitHistory(requireContext(), visitHistory)
            for (i in 0..imageList.size - 2) {
                imageList[i]?.let {
                    viewModel.addNewImagesToDatabase(requireContext(), it)
                }
            }
            viewModel.updateTravelState(requireContext(), locationId)
            findNavController().popBackStack()
        }

        binding.cardViewVisitDate.setOnClickListener {
            showCalender()
        }

        binding.imageViewBackNavigation.setOnClickListener {
            findNavController().popBackStack()
        }

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
            if (checkCameraPermission(requireContext())) {
                imageUri = requireContext().openCamera(cameraResultLauncher)
            } else {
                requestPermissions(cameraRequestList.toTypedArray(), REQ_CODE_CAMERA)
            }
        }
        adb.setNegativeButton(getString(R.string.Gallery)) { _, _ ->
            if (checkGalleryPermission(requireContext())) {
                requireContext().openGallery(galleryResultLauncher)
            } else {
                requestPermissions(galleryRequestList.toTypedArray(), REQ_CODE_GALLERY)
            }
        }
        adb.show()
    }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    val imageData = intentFromResult.data
                    if (imageData != null) {
                        val newImage =
                            Image(0, imageData.toString(), year, month, day, locationId)
                        imageList.add(newImage)
                        updateImageListInRecycler(imageList)
                    }
                }
            }
        }

    private var cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                val newImage = Image(0, imageUri.toString(), year, month, day, locationId)
                imageList.add(newImage)
                updateImageListInRecycler(imageList)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.image_taken),
                    Toast.LENGTH_SHORT
                ).show()
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
                        requireActivity().openSettings()
                    }
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show()
            }
        } else {
            when (requestCode) {
                REQ_CODE_CAMERA -> {
                    imageUri = requireContext().openCamera(cameraResultLauncher)
                }
                REQ_CODE_GALLERY -> {
                    requireContext().openGallery(galleryResultLauncher)
                }
            }
        }
    }

    private fun updateImageListInRecycler(imageList: List<Image?>) {
        updateAddNewPhotoState()
        adapter.imageList = imageList
        binding.recyclerView.adapter = adapter
    }

    private fun updateAddNewPhotoState() {
        for (i in imageList.indices) {
            if (imageList[i]!!.uri == NO_IMAGE_FEATURE && imageList.size > i) {
                imageList.removeAt(i)
                if (imageList.size < 10) {
                    imageList.add(defaultNoImageData)
                }
            }
        }
    }

    private fun showCalender() {
        val dateListener =
            DatePickerDialog.OnDateSetListener { _, yearX, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = "$dayOfMonth.${monthOfYear + 1}.$yearX"
                day = dayOfMonth
                month = monthOfYear
                year = yearX
                binding.textViewDate.text = date
            }

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun deleteAllLocationImages(locationId: Int) {
        viewModel.deleteAllLocationImages(requireContext(), locationId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}