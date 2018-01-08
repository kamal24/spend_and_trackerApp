package com.coderz.creative.music

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coderz.creative.music.Model.Songs;
import kotlinx.android.synthetic.main.item_android_version.view.*

/**
 * Created by kamal on 10/12/17.
 */
class MyCustomAdapter (val SongsList: ArrayList<Songs>, val listener: (Songs) -> Unit) : RecyclerView.Adapter<MyCustomAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_android_version, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyCustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(SongsList[position], listener)
    }

    override fun getItemCount(): Int {
        return SongsList.size
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(Songs: Songs, listener: (Songs) -> Unit) = with(itemView)  {
            itemView.tvName.text = Songs.name
            itemView.tvVersion.text = Songs.version
            itemView.ivIcon.setImageResource(Songs.imageIcon)
            itemView.setOnClickListener{listener(Songs)}
        }
    }
}
