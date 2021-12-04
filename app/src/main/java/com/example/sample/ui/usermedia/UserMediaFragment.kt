package com.example.sample.ui.usermedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sample.databinding.FragmentUserMediaBinding
import com.example.sample.ui.base.BaseFragment
import org.koin.android.ext.android.inject

class UserMediaFragment : BaseFragment() {

    private val viewModel: UserMediaViewModel by inject()
    private var _binding: FragmentUserMediaBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun initUiComponents() {
        super.addTranslucentStatusFlag()
        visibleProgressbar()
    }

    private fun visibleProgressbar() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}