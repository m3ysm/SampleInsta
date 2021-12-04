package com.example.sample.ui.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.sample.util.CommonUtils

abstract class BaseFragment : Fragment() {

    abstract fun initListeners()
    abstract fun initObservers()
    abstract fun initUiComponents()
    fun clearTranslucentStatusFlag() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    fun addTranslucentStatusFlag() {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    fun setSoftInputAdjustPan() {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    fun setSoftInputAdjustResize() {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    fun setSoftInputAdjustNothing() {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiComponents()
        initListeners()
        initObservers()
    }

    override fun onStop() {
        super.onStop()
        CommonUtils.hideKeyBoard(requireView())
    }
}