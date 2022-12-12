package org.wit.estate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.estate.databinding.CardEstateBinding
import org.wit.estate.models.EstateModel

interface EstateListener {
    fun onestateClick(estate: EstateModel, position : Int)
}

class EstateAdapter constructor(private var estates: List<EstateModel>,
                                   private val listener: EstateListener) :
        RecyclerView.Adapter<EstateAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardEstateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val estate = estates[holder.adapterPosition]
        holder.bind(estate, listener)
    }

    override fun getItemCount(): Int = estates.size

    class MainHolder(private val binding : CardEstateBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(estate: EstateModel, listener: EstateListener) {
            binding.estateType.text = estate.type
            binding.description.text = estate.estimated.toString()
            Picasso.get().load(estate.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onestateClick(estate,adapterPosition) }
        }
    }
}
