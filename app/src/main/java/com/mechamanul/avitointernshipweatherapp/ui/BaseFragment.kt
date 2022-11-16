package com.mechamanul.avitointernshipweatherapp.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

open class BaseFragment : Fragment() {
    protected fun displayImage(imageView: ImageView, url: String) {
        Glide.with(requireContext()).load(url).override(128,128)
            .into(imageView)
    }

    protected fun appendToTextView(textView: TextView, s: String) {
        textView.text = "${textView.text} $s"
    }
}