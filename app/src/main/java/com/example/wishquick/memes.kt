package com.example.wishquick

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.memes_activity.*

class memes : AppCompatActivity() {

    //  variables declaration
    private lateinit var CurrentUrl:String
    private lateinit var imageUrl: String
    private val previousMeme =ArrayList<String>()
    private var counter = 0
    var i=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memes_activity)

        loadMeme()
        share.setOnClickListener {
            val send = Intent(Intent.ACTION_SEND)
            send.type ="text/plain"
            send.putExtra(Intent.EXTRA_TEXT,"Check this meme $CurrentUrl")
            val chooser = Intent.createChooser(send,"Share with")
            startActivity(chooser)
            }
        next.setOnClickListener {
            loadMeme()
            counter++
            i=1
        }

        previous.setOnClickListener {
            if (counter-i >= 0) {
                progressBar.visibility = View.VISIBLE
                Glide.with(this).load(previousMeme[counter-i])
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.VISIBLE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                    }).into(memeImage)
                CurrentUrl = previousMeme[counter-i]
                i++
            }
        }
    }

    private fun loadMeme() {
        val url = "https://meme-api.herokuapp.com/gimme"
        progressBar.visibility = View.VISIBLE
        val makeRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                //success request
                imageUrl = response.getString("url")
                CurrentUrl = imageUrl
                previousMeme.add(imageUrl)
                Glide.with(this).load(imageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(memeImage)

            }, {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(makeRequest)
    }
}

