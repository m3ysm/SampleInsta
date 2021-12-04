package com.example.sample.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sample.databinding.ActivityMainBinding

abstract class BaseActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}