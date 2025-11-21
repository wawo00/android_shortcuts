package com.example.androidshortcuts

import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.example.androidshortcuts.databinding.ActivityShowPayCodeActivitynewBinding

class ShowPayCodeActivitynew : AppCompatActivity() {
    private lateinit var binding: ActivityShowPayCodeActivitynewBinding

    companion object {
        private const val TAG = "ShowPayCodeActivitynew"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPayCodeActivitynewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWebView()
        loadUrl()
    }

    private fun setupWebView() {
        val webView = binding.webView
        val webSettings: WebSettings = webView.settings

        // 启用JavaScript支持
        webSettings.javaScriptEnabled = true

        // 启用DOM storage
        webSettings.domStorageEnabled = true

        // 启用数据库存储
        webSettings.databaseEnabled = true

        // 设置缓存模式
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT

        // 允许混合内容（如果需要）
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        // 设置自定义WebViewClient来拦截请求
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                request?.let {
                    Log.d(TAG, "=== URL Loading Override ===")
                    Log.d(TAG, "URL: ${it.url}")
                    Log.d(TAG, "Method: ${it.method}")
                    Log.d(TAG, "Is for main frame: ${it.isForMainFrame}")
                    Log.d(TAG, "Has gesture: ${it.hasGesture()}")
                    Log.d(TAG, "Is redirect: ${it.isRedirect}")

                    // 打印请求头
                    it.requestHeaders?.let { headers ->
                        Log.d(TAG, "--- Request Headers ---")
                        for ((key, value) in headers) {
                            Log.d(TAG, "$key: $value")
                        }
                    }
                    Log.d(TAG, "========================")
                }
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                Log.d(TAG, "=== Page Started ===")
                Log.d(TAG, "URL: $url")
                Log.d(TAG, "==================")
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d(TAG, "=== Page Finished ===")
                Log.d(TAG, "URL: $url")

                // 获取Cookie信息
                val cookies = CookieManager.getInstance().getCookie(url)
                Log.d(TAG, "Cookies: $cookies")
                Log.d(TAG, "====================")
                super.onPageFinished(view, url)
            }
        }

        // 启用WebView调试模式（可选）
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    private fun loadUrl() {
        val url = "https://tstyy.haier.net/index"
        Log.d(TAG, "=== Loading URL ===")
        Log.d(TAG, "Target URL: $url")
        Log.d(TAG, "==================")
        binding.webView.loadUrl(url)
    }

    override fun onResume() {
        super.onResume()
        // 每次Activity显示时都重新加载URL
        Log.d(TAG, "Activity resumed, reloading URL")
        loadUrl()
    }
}
