package com.example.sample.util.extensions

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.widget.MainToolbar
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnack(view: View, message: String) {
    context?.let { context ->
        val snack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val tv = snack.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        val tf = ResourcesCompat.getFont(context, R.font.iran_sans_dn_regular)
        tv.typeface = tf
        tv.gravity = Gravity.START
        tv.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        tv.maxLines = 4
        snack.isGestureInsetBottomIgnored = true
        val snackBarView = snack.view
        snackBarView.fitsSystemWindows = false
        ViewCompat.setOnApplyWindowInsetsListener(snackBarView, null)
        snack.show()
    }
}

fun Fragment.initToolbar(toolbar: MainToolbar) {
    toolbar.setOnBackPressedListener {
        findNavController().navigateUp()
    }
}

fun Fragment.navigateTo(actionId: Int, bundle: Bundle? = null) {
    findNavController().navigate(actionId, bundle)
}