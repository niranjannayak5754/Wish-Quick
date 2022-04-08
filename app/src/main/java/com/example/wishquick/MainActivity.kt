package com.example.wishquick

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        title = ""

        setContentView(R.layout.activity_main)

        memeMaterial.setOnClickListener {
            Toast.makeText(this,"Loading Memes",Toast.LENGTH_SHORT).show()
            val meme= Intent(this,memes::class.java)
            startActivity(meme)
        }

        newsButton.setOnClickListener {
            Toast.makeText(this,"Loading News",Toast.LENGTH_SHORT).show()
            val news = Intent(this,News::class.java)
            startActivity(news)
        }
    }
}