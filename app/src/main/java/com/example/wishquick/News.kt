package com.example.wishquick

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_news.*

class News : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        setUpSpinner()

    }

    private fun setUpSpinner() {
        val adapterNews =
            ArrayAdapter.createFromResource(this, R.array.news_categories, R.layout.dropdown_item)
        adapterNews.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerNews.adapter = adapterNews

        spinnerNews.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedNews = p0!!.getItemAtPosition(p2)
                recyclerView.layoutManager = LinearLayoutManager(this@News)
                fetchdata(selectedNews.toString())
                mAdapter = NewsListAdapter(this@News)
                recyclerView.adapter = mAdapter
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun fetchdata(news: String) {
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/$news/in.json"
        val makeJsonRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<GetNews>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = GetNews(
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            }, {
                Toast.makeText(this, "Poor Internet Connection", Toast.LENGTH_LONG).show()
            }

        )
        MySingleton.getInstance(this).addToRequestQueue(makeJsonRequest)
    }

    override fun onItemClicked(item: GetNews) {
        val builder = CustomTabsIntent.Builder()
//        set toolbar color
        val params = CustomTabColorSchemeParams.Builder()
        params.setToolbarColor(ContextCompat.getColor(this@News, R.color.cardview_dark_background))
        builder.setDefaultColorSchemeParams(params.build())

//        view title of webpage in toolbar
        builder.setShowTitle(true)
//        set menu to share the webpage
        builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)

        builder.setInstantAppsEnabled(true)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}