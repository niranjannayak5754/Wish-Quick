package com.example.wishquick

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_news.*

class News : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        supportActionBar?.hide()

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchdata()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter= mAdapter
    }
    private fun fetchdata(){
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json"
//        val url ="https://gnews.io/api/v4/search?q=example&token=055a3571bf792aaec8227f3613da7ec1"
//        val url= "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=d1a33012d09d4666b650da05a60509aa"
//        val url = "https://newsapi.org/v2/top-headlines?q=cricket&category=sports&apiKey=5d8ada61da0746908e859d5e538840c7&country=in"
        val makeJsonRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<GetNews>()
                for(i in 0 until newsJsonArray.length()) {
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
            },{
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
        customTabsIntent.launchUrl(this,Uri.parse(item.url))
    }
}