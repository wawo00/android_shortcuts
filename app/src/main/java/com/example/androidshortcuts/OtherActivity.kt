package com.example.androidshortcuts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidshortcuts.databinding.ActivityOtherBinding

class OtherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
