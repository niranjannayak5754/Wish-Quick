package com.example.wishquick

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.news_item.view.*

class NewsListAdapter( private val listener : NewsItemClicked):RecyclerView.Adapter<NewsViewHolder>(){
    private val items = ArrayList<GetNews>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener { listener.onItemClicked(items[viewHolder.adapterPosition])}
        return  viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        currentItem.author.also { holder.author.text=it }
        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.image)
        currentItem.title.also { holder.titleView.text = it }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNews(updatedNews:ArrayList<GetNews>){
        items.clear()
        items.addAll(updatedNews)
//        to notify that the data need to be updated to reRun getItemCount, onCreate and onBindView functions
        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleView: TextView = itemView.title
        val image: ImageView = itemView.heading
        val author: TextView = itemView.authorName
}

interface NewsItemClicked{
    fun onItemClicked(item: GetNews)
}
