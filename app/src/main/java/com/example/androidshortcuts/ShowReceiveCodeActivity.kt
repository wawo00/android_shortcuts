package com.example.androidshortcuts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidshortcuts.databinding.ActivityShowReceiveCodeBinding

class ShowReceiveCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowReceiveCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowReceiveCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
