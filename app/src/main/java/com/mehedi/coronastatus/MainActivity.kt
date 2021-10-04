package com.mehedi.coronastatus

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.mehedi.coronastatus.util.ConnectionManager

class MainActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    private lateinit var myWebView: WebView
    private lateinit var btnIndia:Button
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnIndia = findViewById(R.id.btnAssam)
        btnIndia.setOnClickListener {
            val intent = Intent(this,AssamActivity::class.java)
            startActivity(intent)
        }
        if (ConnectionManager().checkConnectivity(this)) {
            progressBar = findViewById(R.id.progressbar)

           myWebView = findViewById(R.id.webview)


            myWebView.loadUrl("https://covid19.who.int/table")

            myWebView.settings.javaScriptEnabled = true
            myWebView.webViewClient = WebViewClient()
            myWebView.webChromeClient = WebChromeClient()


            myWebView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

                    progressBar.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)

                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    progressBar.visibility = View.GONE
                    super.onPageFinished(view, url)

                }
            }

        }
        else
        {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("NO CONNECTION")
            dialog.setCancelable(false)
            dialog.setMessage("Connection is not Established")
            dialog.setPositiveButton("Open settings") { _,_ ->
                val intentsettings = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intentsettings)
                finish()
            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(this)
            }
            dialog.create()
            dialog.show()
        }
    }

    override fun onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }


    }
