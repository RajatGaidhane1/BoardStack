package com.projemanag.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projemanag.R
import com.projemanag.databinding.ItemLabelColorBinding
import java.util.*

class LabelColorListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<String>,
    private val mSelectedColor: String
) : RecyclerView.Adapter<LabelColorListItemsAdapter.MyViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLabelColorBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        val binding = holder.binding

        binding.viewMain.setBackgroundColor(Color.parseColor(item))

        if (item == mSelectedColor) {
            binding.ivSelectedColor.visibility = View.VISIBLE
        } else {
            binding.ivSelectedColor.visibility = View.GONE
        }

        binding.root.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onClick(position, item)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(val binding: ItemLabelColorBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onClick(position: Int, color: String)
    }
}