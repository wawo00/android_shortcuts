package com.example.androidshortcuts

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import android.graphics.drawable.Drawable
import com.example.androidshortcuts.databinding.ActivityShowPayCodeBinding

class ShowPayCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowPayCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPayCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPayCodeImage()
    }

    private fun loadPayCodeImage() {
        // Show progress bar while loading
        binding.progressBar.visibility = View.VISIBLE

        // Load image from URL using Glide
        Glide.with(this)
            .load("https://picsum.photos/200/300")
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(binding.ivPayCode)
    }
}
