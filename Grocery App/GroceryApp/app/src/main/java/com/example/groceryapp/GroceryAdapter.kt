package com.example.groceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroceryAdapter(var list: List<GroceryItems>, val groceryItemsClickInterface: GroceryItemsClickInterface
) : RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {


    inner class GroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameTv = itemView.findViewById<TextView>(R.id.idTVItemsName)
        val quantityTv = itemView.findViewById<TextView>(R.id.idTVQuantity)
        val rateTv = itemView.findViewById<TextView>(R.id.idTVRate)
        val amountTv = itemView.findViewById<TextView>(R.id.idTVTotalAMT)
        val deleteTv = itemView.findViewById<ImageView>(R.id.idIVDelete)

    }




    interface GroceryItemsClickInterface{
        fun OnItemClick(groceryItems: GroceryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grocery_rv_items,parent,false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.nameTv.text = list.get(position).itemName
        holder.quantityTv.text = list.get(position).itemQuantity.toString()
        holder.rateTv.text = "Rs. "+list.get(position).itemPrice.toString()
        val iteamtotal: Int = list.get(position).itemPrice * list.get(position).itemQuantity
        holder.amountTv.text = "Rs. "+iteamtotal.toString()
        holder.deleteTv.setOnClickListener{
            groceryItemsClickInterface.OnItemClick(list.get(position))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}