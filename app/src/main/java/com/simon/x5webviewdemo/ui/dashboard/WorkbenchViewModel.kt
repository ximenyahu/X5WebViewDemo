package com.simon.x5webviewdemo.ui.dashboard

import android.annotation.SuppressLint
import android.app.Application
import android.content.DialogInterface
import android.net.Uri
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback
import com.tencent.smtt.export.external.interfaces.JsPromptResult
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "WorkbenchViewModel"

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class WorkbenchViewModel @Inject constructor(val appContext: Application) : ViewModel() {

    val x5WebView: WebView = WebView(appContext)

    init {
        x5WebView.settings.javaScriptEnabled = true
        x5WebView.settings.allowFileAccess = true
        x5WebView.settings.setSupportZoom(true)
        x5WebView.settings.databaseEnabled = true
        x5WebView.settings.allowFileAccess = true
        x5WebView.settings.domStorageEnabled = true
        initWebViewClient()
        initWebChromeClient()
        x5WebView.loadUrl("https://www.baidu.com")
    }

    private fun initWebViewClient() {
        x5WebView.webViewClient = object : WebViewClient() {
//            override fun onPageStarted(webView: WebView, url: String, favicon: Bitmap) {
//                super.onPageStarted(webView, url, favicon)
//            }
//
//            override fun onPageFinished(webView: WebView, url: String) {
//                super.onPageFinished(webView, url)
//            }
//
//            override fun onReceivedError(
//                webView: WebView,
//                errorCode: Int,
//                description: String,
//                failingUrl: String
//            ) {
//                super.onReceivedError(webView, errorCode, description, failingUrl)
//            }

//            override fun shouldInterceptRequest(
//                webView: WebView,
//                webResourceRequest: WebResourceRequest
//            ): WebResourceResponse {
////                return super.shouldInterceptRequest(webView, webResourceRequest)
//                val requestValue = super.shouldInterceptRequest(webView, webResourceRequest)
//                Log.i(TAG, "shouldInterceptRequest: $requestValue")
//                return requestValue
//            }
        }
    }

    private fun initWebChromeClient() {
        x5WebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(webView: WebView, newProgress: Int) {
                super.onProgressChanged(webView, newProgress)
            }

            override fun onJsAlert(
                webView: WebView?,
                url: String?,
                message: String?,
                result: JsResult
            ): Boolean {
                AlertDialog.Builder(appContext).setTitle("JS弹窗Override")
                    .setMessage(message)
                    .setPositiveButton(
                        "OK"
                    ) { dialogInterface: DialogInterface?, i: Int -> result.confirm() }
                    .setCancelable(false)
                    .show()
                return true
            }

            override fun onJsConfirm(
                webView: WebView?,
                url: String?,
                message: String?,
                result: JsResult
            ): Boolean {
                AlertDialog.Builder(appContext).setTitle("JS弹窗Override")
                    .setMessage(message)
                    .setPositiveButton(
                        "OK"
                    ) { dialogInterface: DialogInterface?, i: Int -> result.confirm() }
                    .setNegativeButton(
                        "Cancel"
                    ) { dialogInterface: DialogInterface?, i: Int -> result.cancel() }
                    .setCancelable(false)
                    .show()
                return true
            }

            override fun onJsBeforeUnload(
                webView: WebView?,
                url: String?,
                message: String?,
                result: JsResult
            ): Boolean {
                AlertDialog.Builder(appContext).setTitle("页面即将跳转")
                    .setMessage(message)
                    .setPositiveButton(
                        "OK"
                    ) { dialogInterface: DialogInterface?, i: Int -> result.confirm() }
                    .setNegativeButton(
                        "Cancel"
                    ) { dialogInterface: DialogInterface?, i: Int -> result.cancel() }
                    .setCancelable(false)
                    .show()
                return true
            }

            override fun onJsPrompt(
                webView: WebView?,
                url: String?,
                message: String?,
                defaultValue: String?,
                result: JsPromptResult
            ): Boolean {
                val input = EditText(appContext)
                input.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                AlertDialog.Builder(appContext).setTitle("JS弹窗Override")
                    .setMessage(message)
                    .setView(input)
                    .setPositiveButton(
                        "OK"
                    ) { dialogInterface: DialogInterface?, i: Int ->
                        result.confirm(
                            input.text.toString()
                        )
                    }
                    .setCancelable(false)
                    .show()
                return true
            }

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri?>?>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                return true
            }

            override fun onGeolocationPermissionsShowPrompt(
                origin: String?,
                geolocationPermissionsCallback: GeolocationPermissionsCallback?
            ) {
            }
        }
    }

    private fun initJavaScriptInterface() {
    }
}
