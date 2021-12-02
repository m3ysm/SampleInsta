package com.example.sample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.sample.R
import com.example.sample.databinding.LayoutToolbarMainBinding
import com.example.sample.util.extensions.invisible
import com.example.sample.util.extensions.visible

class MainToolbar : RelativeLayout {

    private var _binding: LayoutToolbarMainBinding? = null
    private val binding get() = _binding!!
    private var title: String? = null
    private var isBackIconVisible = true

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    init {
        _binding = LayoutToolbarMainBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun init(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.mainToolbar_styleable, 0, 0).apply {
            try {
                title = getString(R.styleable.mainToolbar_styleable_mainToolbar_title)
                isBackIconVisible =
                    getBoolean(
                        R.styleable.mainToolbar_styleable_mainToolbar_isBackIconVisible,
                        true
                    )
            } catch (e: Exception) {

            } finally {
                recycle()
            }
        }
        setBackButtonVisibility()
        setTitle(title)
    }

    private fun setBackButtonVisibility() {
        if (isBackIconVisible) {
            binding.imageViewMainToolbarBack.visible()
        } else {
            binding.imageViewMainToolbarBack.invisible()
        }
    }

    fun setTitle(title: String?) {
        title?.let {
            binding.textViewMainToolbarTitle.text = it
        }
    }

    fun setOnBackPressedListener(listener: OnClickListener) {
        binding.imageViewMainToolbarBack.setOnClickListener(listener)
    }
}