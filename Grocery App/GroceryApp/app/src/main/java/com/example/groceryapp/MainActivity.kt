package com.example.groceryapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), GroceryAdapter.GroceryItemsClickInterface {
    lateinit var itemsRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var list: List<GroceryItems>
    lateinit var groceryAdapter: GroceryAdapter
    lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemsRV = findViewById(R.id.idRVItems)
        addFAB = findViewById(R.id.idFABAdd)
        list = ArrayList<GroceryItems>()
        groceryAdapter = GroceryAdapter(list, this)
        itemsRV.layoutManager = LinearLayoutManager(this)
        itemsRV.adapter = groceryAdapter
        val groceryRepo = GroceryRepo(GroceryDatabase(this))
        val factory =GroceryViewModelFactory(groceryRepo)
        groceryViewModel = ViewModelProvider(this, factory).get(GroceryViewModel::class.java)
        groceryViewModel.getAllGroceryItems().observe(this, {
            groceryAdapter.list = it
            groceryAdapter.notifyDataSetChanged()
        })
        addFAB.setOnClickListener{
            openDialog()

        }
    }
    fun openDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialogbox)
        val cancelBtn = dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdt = dialog.findViewById<EditText>(R.id.idEdtItemName)
        val itemPrice = dialog.findViewById<EditText>(R.id.idEdtItemPrice)
        val itemQuntity = dialog.findViewById<EditText>(R.id.idEdtItemQuantity)

        cancelBtn.setOnClickListener{
            dialog.dismiss()
        }

        addBtn.setOnClickListener{
            val itemName : String = itemEdt.text.toString()
            val itemPric : String = itemPrice.text.toString()
            val itemQnt : String = itemQuntity.text.toString()

            val Qt : Int = itemQnt.toInt()
            val Pr : Int = itemPric.toInt()

            if(itemName.isNotEmpty() && itemPric.isNotEmpty() && itemQnt.isNotEmpty()){
                val items = GroceryItems(itemName,Qt,Pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext,"Item inserted..",Toast.LENGTH_SHORT).show()
                groceryAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            else{
                Toast.makeText(applicationContext,"Please Enter All Data",Toast.LENGTH_SHORT).show()

            }

        }
        dialog.show()

    }

    override fun OnItemClick(groceryItems: GroceryItems) {

        val delete = groceryViewModel.delete(groceryItems)
        groceryAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "Item Delete...", Toast.LENGTH_SHORT).show()
    }
}