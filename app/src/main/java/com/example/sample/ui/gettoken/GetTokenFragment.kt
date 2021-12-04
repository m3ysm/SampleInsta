package com.example.sample.ui.gettoken

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.sample.R
import com.example.sample.data.model.Status
import com.example.sample.data.model.progressbar.ProgressBarStatus
import com.example.sample.databinding.FragmentGetTokenBinding
import com.example.sample.ui.base.BaseFragment
import com.example.sample.util.Constants
import com.example.sample.util.CustomWebView
import com.example.sample.util.extensions.navigateTo
import org.koin.android.ext.android.inject

class GetTokenFragment : BaseFragment() {

    private val viewModel: GetTokenViewModel by inject()
    private var _binding: FragmentGetTokenBinding? = null
    private val binding get() = _binding!!
    private val customWebView: CustomWebView by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetTokenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initListeners() {
    }

    override fun initObservers() {
        observeGetAccessTokenLiveData()
    }

    private fun observeGetAccessTokenLiveData() {
        viewModel.getAccessTokenLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressLayoutGetTokenFragment.setStatus(ProgressBarStatus.LOADING)
                }
                Status.SUCCESS -> {
                    binding.progressLayoutGetTokenFragment.setStatus(ProgressBarStatus.DONE)
                    it.data?.let { responseModel ->
                        AccessTokenPersistor.getInstance(requireContext())
                            .save(responseModel.accessToken, responseModel.userId)
                        navigateToUserMediaFragment()
                    }
                }
                Status.FAILED -> {
                    binding.progressLayoutGetTokenFragment.setStatus(ProgressBarStatus.DONE)
                }
            }
        })
    }

    override fun initUiComponents() {
        super.addTranslucentStatusFlag()
        visibleProgressbar()
        initWebView()
    }

    private fun initWebView() {
        customWebView.init(
            webView = binding.webViewGetTokenFragment,
            progressbar = binding.progressLayoutGetTokenFragment,
            activity = requireActivity(),
            listener = {
                it?.let {
                    viewModel.getAccessToken(
                        clientId = Constants.CLIENT_ID,
                        clientSecret = Constants.CLIENT_SECRET,
                        grantType = Constants.GRANT_TYPE,
                        redirectUri = Constants.REDIRECT_URL,
                        code = it
                    )
                }
            }
        )
    }

    private fun navigateToUserMediaFragment() {
        navigateTo(R.id.action_getTokenFragment_to_userMediaFragment)
    }

    private fun visibleProgressbar() {
        binding.progressLayoutGetTokenFragment.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}