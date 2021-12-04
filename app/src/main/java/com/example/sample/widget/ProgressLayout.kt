package com.example.sample.widget

import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.sample.R
import com.example.sample.data.model.progressbar.ProgressBarStatus
import com.example.sample.databinding.LayoutProgressBinding

class ProgressLayout : RelativeLayout {

    private var _binding: LayoutProgressBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        _binding = LayoutProgressBinding.inflate(LayoutInflater.from(context), this, true)
        init()
    }

    private fun init() {
        setStatus(ProgressBarStatus.DONE)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val wrapDrawable =
                DrawableCompat.wrap(binding.progressLayoutProgressBar.indeterminateDrawable)
            DrawableCompat.setTint(
                wrapDrawable,
                ContextCompat.getColor(context, R.color.colorPrimaryAccent)
            )
            binding.progressLayoutProgressBar.indeterminateDrawable =
                DrawableCompat.unwrap(wrapDrawable)
        } else {
            binding.progressLayoutProgressBar.indeterminateDrawable.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryAccent
                ), PorterDuff.Mode.SRC_IN
            )
        }
    }

    fun setStatus(status: ProgressBarStatus, errorText: String = "") {
        when (status) {
            ProgressBarStatus.LOADING -> {
                binding.progressLayoutRoot.visibility = View.VISIBLE
                binding.progressLayoutTitle.text =
                    context.getString(R.string.progressLayout_pleaseWait)
                binding.progressLayoutTitle.visibility = View.VISIBLE
                binding.progressLayoutProgressBar.visibility = View.VISIBLE
                binding.progressLayoutRoot.visibility = View.VISIBLE
                requestLayout()
            }

            ProgressBarStatus.DONE -> {
                binding.progressLayoutRoot.visibility = View.GONE
                binding.progressLayoutTitle.text = ""
                binding.progressLayoutTitle.visibility = View.INVISIBLE
                binding.progressLayoutProgressBar.visibility = View.INVISIBLE
                binding.progressLayoutRoot.visibility = View.GONE
            }
        }
    }

    fun setErrorText(text: String) {
        binding.progressLayoutTitle.text = text
    }
}
