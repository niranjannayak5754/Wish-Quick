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
        // removes status bar from the layout
        // imported extra view window and window manager library for this function


        // removes action bar from the activity
        supportActionBar?.hide()


        val input= stringName.editableText
        birthdayCard.setOnClickListener {
            Toast.makeText(this, "card is generating", Toast.LENGTH_SHORT).show()
            val wish = Intent(this, birthdaycard::class.java)
            wish.putExtra("name",input.toString())
            startActivity(wish)
        }

        memeMaterial.setOnClickListener {
            Toast.makeText(this,"Opening Meme App",Toast.LENGTH_SHORT).show()
            val meme= Intent(this,memes::class.java)
            startActivity(meme)
        }

        newsButton.setOnClickListener {
            Toast.makeText(this,"Opening Newz App",Toast.LENGTH_SHORT).show()
            val news = Intent(this,News::class.java)
            startActivity(news)
        }

    }

}