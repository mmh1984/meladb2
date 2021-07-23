package com.example.mealdb.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealdb.Class.Meals
import com.example.mealdb.R
import com.squareup.picasso.Picasso

class MealAdapter(var c: Context, var list:ArrayList<Meals>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view=LayoutInflater.from(c).inflate(R.layout.meal_layout,parent,false)
        return MealView(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MealView).bind(list[position].mealname,list[position].mealcategory,
            list[position].mealarea,list[position].mealinstructions,list[position].mealimg)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MealView(view: View):RecyclerView.ViewHolder(view){
        var lblname:TextView=view.findViewById(R.id.tvmealname)
        var lblcategoryarea:TextView=view.findViewById(R.id.tvcategoryarea)
        var lblinstructions:TextView=view.findViewById(R.id.tvinstructions)
        var imgmeal:ImageView=view.findViewById(R.id.ivmealimg)

        fun bind(name:String,category:String,area:String,ins:String,img:String){
            lblname.text=name
            lblcategoryarea.text=category + "(" + area + ")"
            lblinstructions.text=ins
            Picasso.get().load(img).into(imgmeal)
        }
    }


}