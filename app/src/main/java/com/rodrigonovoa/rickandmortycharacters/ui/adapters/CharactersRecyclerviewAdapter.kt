package com.rodrigonovoa.rickandmortycharacters.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rodrigonovoa.rickandmortycharacters.R

class CharactersRecyclerviewAdapter(private val characters: List<com.rodrigonovoa.rickandmortycharacters.data.api.Character>) : RecyclerView.Adapter<CharactersRecyclerviewAdapter.CharactersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_row, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.tvName.text = characters[position].name
        Glide.with(holder.ivPic.context).load(characters[position].image).into(holder.ivPic);
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_character_name)
        val ivPic: ImageView = itemView.findViewById(R.id.iv_character_pic)
    }
}