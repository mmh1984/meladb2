package com.example.mealdb

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.mealdb.Adapters.MealAdapter
import com.example.mealdb.Class.Meals
import com.example.mealdb.Class.MySingleton
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class SearchMeal : AppCompatActivity() {
    //global
    lateinit var txtsearch:EditText
    lateinit var btnsearch:Button
    lateinit var pd: ProgressDialog
    //recyclerview
    lateinit var rvmeals:RecyclerView
    lateinit var meallist:ArrayList<Meals>
    lateinit var mealadp:MealAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_meal)

        //initialize
        txtsearch=findViewById(R.id.etmealname)
        btnsearch=findViewById(R.id.btnsearch)
        rvmeals=findViewById(R.id.rvmeals)

        pd= ProgressDialog(this)
        pd.setTitle("MEAL API")
        pd.setMessage("Loading random meal")
        pd.setCancelable(false)

        //trigger using the button
        btnsearch.setOnClickListener {
            //check if the textbox is empty or not
            if(txtsearch.text.length==0){
                txtsearch.error="Enter the name of the meal"
            }
            else{
                pd.show()
                apifetch()
            }
        }

    }
    //function
    fun apifetch(){
        var url="https://www.themealdb.com/api/json/v1/1/search.php?s=" + txtsearch.text //request URL
        var sr= JsonObjectRequest(Request.Method.GET,url,null, { response ->

            if(response.getString("meals").toString()=="null"){
                Toast.makeText(this,"No Results",Toast.LENGTH_LONG).show()
            }
            else{
                var result=response.getJSONArray("meals");
                var rows=result.length();
                Toast.makeText(this,rows.toString(),Toast.LENGTH_LONG).show()
                //initialize the arraylist
                meallist=ArrayList<Meals>()
                for(i in 0 until rows){
                    var mealobj=Meals()
                    mealobj.mealname=result.getJSONObject(i).getString("strMeal")
                    mealobj.mealcategory=result.getJSONObject(i).getString("strCategory")
                    mealobj.mealarea=result.getJSONObject(i).getString("strArea")
                    mealobj.mealinstructions=result.getJSONObject(i).getString("strInstructions")
                    mealobj.mealimg=result.getJSONObject(i).getString("strMealThumb")
                    meallist.add((mealobj))
                }
                mealadp= MealAdapter(this,meallist)
                rvmeals.layoutManager=LinearLayoutManager(this)
                rvmeals.adapter=mealadp

            }

            pd.dismiss()
        },{error->
            Toast.makeText(this,error.message.toString(), Toast.LENGTH_LONG).show()
            pd.dismiss();
        })
        MySingleton.getInstance(this).addToRequestQueue(sr);


    }
}