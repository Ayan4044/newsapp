package com.example.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.Articles
import com.google.android.material.button.MaterialButton

class NewsAdapter(val context: Context, val dataset: ArrayList<Articles>):
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>()
{


    private var onItemClickListener:  OnItemClickListener? = null

    interface OnItemClickListener{


    }

    fun onItemClickListener(ontemClickListener:OnItemClickListener){
        onItemClickListener = ontemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = NewsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_news,parent,false),
        onItemClickListener
    )

    override fun getItemCount(): Int = dataset.size


    class NewsViewHolder(inflate: View, districtOnClickListenr: OnItemClickListener?): RecyclerView.ViewHolder(inflate){
        var textViewtitle: TextView = inflate.findViewById(R.id.textViewtitle)
        var textviewDescription: TextView = inflate.findViewById(R.id.textViewndescription)

        init {
//            districtButton.setOnClickListener(View.OnClickListener {
//                if (districtOnClickListenr != null) {
//                    val pos:Int = adapterPosition
//                    if (pos != RecyclerView.NO_POSITION) {
//                        districtOnClickListenr.districtItemClick(pos)
//                    }
//                }
//            })
        }


    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = dataset[position]
        holder.textViewtitle.text = article.title
        holder.textviewDescription.text = article.summary
    }







}