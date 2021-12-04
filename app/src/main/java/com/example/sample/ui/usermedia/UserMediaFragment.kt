package com.example.sample.ui.usermedia

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.sample.data.model.Status
import com.example.sample.data.model.progressbar.ProgressBarStatus
import com.example.sample.databinding.FragmentUserMediaBinding
import com.example.sample.ui.base.BaseFragment
import com.example.sample.ui.gettoken.AccessTokenPersistor
import com.example.sample.util.extensions.visible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.android.ext.android.inject

class UserMediaFragment : BaseFragment() {

    private val viewModel: UserMediaViewModel by inject()
    private var _binding: FragmentUserMediaBinding? = null
    private val binding get() = _binding!!
    private var userName = ""
    private var downloadUrl = ""
    private lateinit var sharedPreferences: AccessTokenPersistor
    private lateinit var moreBottomSheet: BottomSheetBehavior<ConstraintLayout>

    companion object {
        private const val PERMISSION_STORAGE_CODE = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initListeners() {
    }

    override fun initObservers() {
        observeGetUserNameLiveData()
        observeGetUserMediaIdAndCaptionLiveData()
        observeMediaDataLiveData()
        observeDownloadLiveData()
        observeWriteExternalStoragePermissionLiveData()
    }

    private fun observeWriteExternalStoragePermissionLiveData() {
        viewModel.writeExternalStoragePermissionLiveData.observe(
            viewLifecycleOwner,
            Observer { it ->
                when (it.status) {
                    Status.SUCCESS -> {
                        if (it.data!!) {
                            showBottomSheet(downloadUrl)
                        } else {
                            requestPermissions(
                                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                PERMISSION_STORAGE_CODE
                            )
                        }
                    }
                }
            })
    }

    private fun observeDownloadLiveData() {
        viewModel.downloadLiveData.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    startDownloading(it.data!!)
                }
            }
        })
    }

    private fun startDownloading(request: DownloadManager.Request) {
        val manager =
            requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

    private fun observeMediaDataLiveData() {
        viewModel.getMediaDataLiveData.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {
                Status.LOADING -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.LOADING)
                }
                Status.SUCCESS -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.DONE)
                    it.data?.let {
                        initRecyclerView()
                    }
                }
                Status.FAILED -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.DONE)
                }
            }
        })
    }

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerViewUserMediaFragment
        val adapter = UserMediaRecyclerViewAdapter(viewModel.medias, userName, onItemClicked = {
            downloadUrl = it
            checkWriteExternalStoragePermission()
        })
        recyclerView.adapter = adapter
    }

    private fun showBottomSheet(url: String) {
        moreBottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        moreBottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.layoutUserMediaFragmentMore.textViewMoreOptionBottomSheetDownload.setOnClickListener {
                            viewModel.startDownloading(url)
                            moreBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        setSoftInputAdjustResize()
                        moreBottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun observeGetUserMediaIdAndCaptionLiveData() {
        viewModel.getUserMediaIdAndCaptionLiveData.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {
                Status.LOADING -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.LOADING)
                }
                Status.SUCCESS -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.DONE)
                }
                Status.FAILED -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.DONE)
                }
            }
        })
    }

    private fun observeGetUserNameLiveData() {
        viewModel.getUserNameLiveData.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {
                Status.LOADING -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.LOADING)
                }
                Status.SUCCESS -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.DONE)
                    it.data?.let {
                        userName = it.userName
                        viewModel.getUserMediaIdAndCaption(
                            sharedPreferences.getUserId().toString(),
                            sharedPreferences.getToken()
                        )
                    }
                }
                Status.FAILED -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.DONE)
                }
            }
        })
    }

    override fun initUiComponents() {
        super.addTranslucentStatusFlag()
        initSharedPreferences()
        visibleProgressbar()
        getUserName()
        setAccessTokenAndUserIdValue()
        initBottomSheet()
    }

    private fun initBottomSheet() {
        moreBottomSheet =
            BottomSheetBehavior.from(binding.layoutUserMediaFragmentMore.constraintLayoutMoreOptionBottomSheetRootLayout)
    }

    private fun setAccessTokenAndUserIdValue() {
        viewModel.accessToken = sharedPreferences.getToken()
        viewModel.userId = sharedPreferences.getUserId()
    }

    private fun initSharedPreferences() {
        sharedPreferences = AccessTokenPersistor.getInstance(requireContext())
    }

    private fun getUserName() {
        viewModel.getUserName(
            sharedPreferences.getUserId().toString(),
            sharedPreferences.getToken()
        )
    }

    private fun visibleProgressbar() {
        binding.progressLayoutUserMediaFragment.visible()
    }

    private fun checkWriteExternalStoragePermission() {
        viewModel.checkWriteExternalStoragePermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}