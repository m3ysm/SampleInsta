package com.example.sample.ui.usermedia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.sample.data.model.Status
import com.example.sample.data.model.progressbar.ProgressBarStatus
import com.example.sample.databinding.FragmentUserMediaBinding
import com.example.sample.ui.base.BaseFragment
import com.example.sample.ui.gettoken.AccessTokenPersistor
import com.example.sample.util.extensions.visible
import org.koin.android.ext.android.inject

class UserMediaFragment : BaseFragment() {

    private val viewModel: UserMediaViewModel by inject()
    private var _binding: FragmentUserMediaBinding? = null
    private val binding get() = _binding!!
    private var userName = ""
    private lateinit var sharedPreferences: AccessTokenPersistor

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
            Log.e("123123", "url = $it")
        })
        recyclerView.adapter = adapter
    }

    private fun observeGetUserMediaIdAndCaptionLiveData() {
        viewModel.getUserMediaIdAndCaptionLiveData.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {
                Status.LOADING -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.LOADING)
                }
                Status.SUCCESS -> {
                    binding.progressLayoutUserMediaFragment.setStatus(ProgressBarStatus.DONE)
                    it.data?.let {

                    }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}