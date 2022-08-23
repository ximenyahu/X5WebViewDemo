package com.simon.x5webviewdemo.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.simon.x5webviewdemo.R
import com.simon.x5webviewdemo.databinding.FragmentDashboardBinding
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "DashboardFragment"

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    //
    lateinit var binding: FragmentDashboardBinding
    val viewModel: WorkbenchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(p0: WebView?, p1: Int) {
                super.onProgressChanged(p0, p1)
                Log.i(TAG, "onProgressChanged: ")
            }
        }
        binding.webView.settings.allowFileAccess = true
        binding.webView.settings.supportZoom()
        binding.webView.settings.databaseEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.displayZoomControls = true

//        initWebViewClient()
//        initWebChromeClient()
//        binding.webView.loadUrl("https://www.baidu.com")
        if (viewModel.x5WebView.parent != null) {
            (viewModel.x5WebView.parent as ViewGroup).removeView(viewModel.x5WebView)
        }
        binding.frameLayout.addView(viewModel.x5WebView)
    }
}