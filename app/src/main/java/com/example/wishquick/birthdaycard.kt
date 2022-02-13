package com.example.wishquick

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_birthdaycard.*


class birthdaycard : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthdaycard)

    supportActionBar?.hide()
        val personName = intent.getStringExtra("name")
        birthdayWish.text = "Happiest Birthday to the most craziest person in my life, it's you gedi...\n$personName"
    }
}