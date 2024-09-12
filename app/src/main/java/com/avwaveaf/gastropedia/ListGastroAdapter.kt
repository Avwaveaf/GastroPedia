package com.avwaveaf.gastropedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avwaveaf.gastropedia.databinding.ItemRowGastroBinding
import com.bumptech.glide.Glide

class ListGastroAdapter(
    private val gastroList: ArrayList<Gastronomy>,
    private val onItemClick: (Gastronomy) -> Unit
) : RecyclerView.Adapter<ListGastroAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemRowGastroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gastronomy: Gastronomy) {
            with(binding) {
                tvItemName.text = gastronomy.name
                tvItemDescription.text = gastronomy.description
                tvItemCategory.text = gastronomy.category

                Glide.with(itemView.context)
                    .load(gastronomy.imgUrl)
                    .into(imgItemPhoto)

                root.setOnClickListener { onItemClick(gastronomy) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowGastroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = gastroList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val gastronomy = gastroList[position]
        holder.bind(gastronomy)
    }
}
