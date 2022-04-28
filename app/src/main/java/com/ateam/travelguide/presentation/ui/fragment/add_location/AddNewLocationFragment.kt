package com.ateam.travelguide.presentation.ui.fragment.add_location


import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
import com.ateam.travelguide.databinding.FragmentAddNewLocationBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.presentation.adapter.PriorityAdapter
import com.ateam.travelguide.presentation.adapter.VisitHistoryImageListAdapter
import com.ateam.travelguide.util.*
import com.ateam.travelguide.util.Constant.NO_IMAGE_FEATURE
import java.util.*
import kotlin.collections.ArrayList

class AddNewLocationFragment : Fragment(), VisitHistoryImagesClickListener {

    private var _binding: FragmentAddNewLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VisitHistoryImageListAdapter
    private var lastImageListSize = 0
    private lateinit var imageUri: Uri
    private lateinit var defaultNoImageData: Image
    private lateinit var imageList: ArrayList<Image?>
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var locationId : Int? = null
    private val args : AddNewLocationFragmentArgs by navArgs()
    private lateinit var viewModel: AddNewLocationViewModel
    private var prioritySelection : Int? = 2
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDate()
        initView()
        initClickListeners()
        setupSimpleSpinner()
    }

    private fun initDate(){
        day = String().getTheDayWithSeparate(calendar)[0]
        month = String().getTheDayWithSeparate(calendar)[1]
        year = String().getTheDayWithSeparate(calendar)[2]
    }

    private fun initView() {
        viewModel = ViewModelProvider(this).get(AddNewLocationViewModel::class.java)
        adapter = VisitHistoryImageListAdapter(this)
        imageList = ArrayList()

        locationId = viewModel.getNextId(requireContext())
        defaultNoImageData = Image(0, NO_IMAGE_FEATURE, null, null, null, locationId!!)

        imageList = viewModel.getAllImages(requireContext(), locationId!!)
        lastImageListSize = imageList.size

        if (imageList.size < 10) {
            imageList.add(defaultNoImageData)
        }

        updateImageListInRecycler(imageList)
    }

    private fun initClickListeners() {
        binding.apply {

            buttonAddLocationMap.setOnClickListener {
                findNavController().navigate(R.id.action_addNewLocationFragment_to_addLocationInMapFragment)
            }

            buttonAddLocation.setOnClickListener {
                if(args.lat != null && !args.lat.equals("")){
                    val location = Location(
                        id = 0,
                        name = binding.editTextLocationName.text.toString(),
                        date = "$day.$month.$year",
                        shortDescription = binding.editTextLocationShortDesc.text.toString(),
                        longDescription = binding.editTextLocationLongDesc.text.toString(),
                        priority = prioritySelection,
                        visitStatus = "false", //ziyaret edilmemiş
                        latitude = args.lat!!,
                        longitude = args.long!!
                    )

                    for (i in 0..imageList.size - 2) {
                        imageList[i]?.let {
                            viewModel.addNewImagesToDatabase(requireContext(), it)
                        }
                    }

                    viewModel.addNewLocation(requireContext(),location)
                    requireActivity().finish()
                }
                else{
                    haveToAddMap()
                }
            }

            imageViewBackNavigation.setOnClickListener {
                requireActivity().finish()
            }
        }
    }

    private fun haveToAddMap(){
        val adb : AlertDialog.Builder = AlertDialog.Builder(requireContext())
        adb.setTitle(R.string.map_required).setMessage(R.string.add_map_to_save_location)
            .setPositiveButton(R.string.okey,null).show()
    }

    private fun setupSimpleSpinner() {
        val adapter = PriorityAdapter(
            requireContext(),
            PriorityUtil().list!!
        )

        binding.customSpinner.adapter = adapter

        binding.customSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedPriObj = parent!!.getItemAtPosition(position).toString()

                if(selectedPriObj.contains("Öncelik 1")) prioritySelection = 0
                else if(selectedPriObj.contains("Öncelik 2")) prioritySelection = 1
                else prioritySelection = 2
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
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
            if (Permissions.checkCameraPermission(requireContext())) {
                imageUri = requireContext().openCamera(cameraResultLauncher)
            } else {
                requestPermissions(Permissions.cameraRequestList.toTypedArray(),
                    Constant.REQ_CODE_CAMERA)
            }
        }
        adb.setNegativeButton(getString(R.string.Gallery)) { _, _ ->
            if (Permissions.checkGalleryPermission(requireContext())) {
                requireContext().openGallery(galleryResultLauncher)
            } else {
                requestPermissions(Permissions.galleryRequestList.toTypedArray(),
                    Constant.REQ_CODE_GALLERY)
            }
        }
        adb.show()
    }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                val newImage = Image(0, it.data!!.data.toString(), year, month, day, locationId!!)
                if(imageList.size < 10){
                    imageList.add(newImage)
                    updateImageListInRecycler(imageList)
                }
            }
        }

    private var cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                val newImage = Image(0, imageUri.toString(), year, month, day, locationId!!)
                if(imageList.size < 10){
                    imageList.add(newImage)
                    updateImageListInRecycler(imageList)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.image_taken),
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
                Constant.REQ_CODE_CAMERA -> {
                    imageUri = requireContext().openCamera(cameraResultLauncher)
                }
                Constant.REQ_CODE_GALLERY -> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
