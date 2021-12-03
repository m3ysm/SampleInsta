package com.example.sample.ui.gettoken

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sample.databinding.FragmentGetTokenBinding
import com.example.sample.ui.base.BaseFragment
import com.example.sample.util.CustomWebView
import org.koin.android.ext.android.inject

class GetTokenFragment : BaseFragment() {

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
            requireActivity(),
            listener = {
                it?.let {
                }
            }
        )
    }

    private fun visibleProgressbar() {
        binding.progressLayoutGetTokenFragment.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}