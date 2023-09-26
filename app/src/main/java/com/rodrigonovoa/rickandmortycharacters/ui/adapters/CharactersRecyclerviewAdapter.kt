package com.rodrigonovoa.rickandmortycharacters.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rodrigonovoa.rickandmortycharacters.R
import com.rodrigonovoa.rickandmortycharacters.data.model.CharacterRow

class CharactersRecyclerviewAdapter(private var serieCharacters: List<CharacterRow>, private val listener: ItemClickListener) : RecyclerView.Adapter<CharactersRecyclerviewAdapter.CharactersViewHolder>() {

    interface ItemClickListener {
        fun onItemClicked(characterId: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_row, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.llCharacter.setOnClickListener {  listener.onItemClicked(13) }
        holder.tvName.text = serieCharacters[position].name
        Glide.with(holder.ivPic.context).load(serieCharacters[position].imageUrl).into(holder.ivPic);
    }

    override fun getItemCount(): Int {
        return serieCharacters.size
    }

    class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llCharacter: LinearLayout = itemView.findViewById(R.id.ll_character)
        val tvName: TextView = itemView.findViewById(R.id.tv_character_name)
        val ivPic: ImageView = itemView.findViewById(R.id.iv_character_pic)
    }

    fun setDataSet(newCharacters: List<CharacterRow>) {
        this.serieCharacters = newCharacters
        notifyDataSetChanged()
    }
}