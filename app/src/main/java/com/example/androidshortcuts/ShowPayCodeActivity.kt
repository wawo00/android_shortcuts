package com.example.androidshortcuts

import android.graphics.Bitmap
import android.graphics.Color
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
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*

class ShowPayCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowPayCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPayCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 目前使用二维码生成，图片下载功能保留但暂时不调用
        generateQRCode()
        // loadPayCodeImage() // 保留但不调用
    }

    private fun generateQRCode() {
        // Show progress bar while generating
        binding.progressBar.visibility = View.VISIBLE

        try {
            val url = "https://tstyy.haier.net/index"
            val qrCodeBitmap = createQRCode(url)
            binding.ivPayCode.setImageBitmap(qrCodeBitmap)
            binding.progressBar.visibility = View.GONE
        } catch (e: Exception) {
            binding.progressBar.visibility = View.GONE
            // Handle error if needed
        }
    }

    /**
     * 根据文字内容创建二维码，这里使用ZXing库生成二维码
     */
    private fun createQRCode(content: String): Bitmap {
        val writer = QRCodeWriter()
        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hints[EncodeHintType.MARGIN] = 1

        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512, hints)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        return bitmap
    }

    // 保留原有的图片下载实现，目前不调用
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
