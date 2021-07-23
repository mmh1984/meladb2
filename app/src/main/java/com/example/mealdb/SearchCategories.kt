package com.example.mealdb

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.mealdb.Adapters.MealAdapter
import com.example.mealdb.Class.Meals
import com.example.mealdb.Class.MySingleton
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class SearchCategories : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var spcategory:Spinner
    lateinit var pd: ProgressDialog

    //recyclerview
    lateinit var rvmeals: RecyclerView
    lateinit var meallist:ArrayList<Meals>
    lateinit var mealadp: MealAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_categories)
        //initialize
        spcategory=findViewById(R.id.spcategories)
        rvmeals=findViewById(R.id.rvmeals)

        pd= ProgressDialog(this)
        pd.setTitle("MEAL API")
        pd.setMessage("Loading categories")
        pd.setCancelable(false)
        pd.show()
        load_categories();
        //add listener to the spinner
        spcategory.onItemSelectedListener=this


    }
    //functions
    fun load_categories(){
        var url="https://www.themealdb.com/api/json/v1/1/list.php?c=list" //request URL
        var sr= JsonObjectRequest(Request.Method.GET,url,null, { response ->
            var result=response.getJSONArray("meals");
            var rows=result.length();
            if(rows > 0){
                Toast.makeText(this,rows.toString(),Toast.LENGTH_LONG).show()
               //we need to store the categories to the spinner
                //create arraylist
                var categorylist=ArrayList<String>()
                for(i in 0 until rows){
                    categorylist.add(result.getJSONObject(i).getString("strCategory"))
                }
                val listadp=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,categorylist)
                spcategory.adapter=listadp

                pd.dismiss()
            }
        },{error->
            Toast.makeText(this,error.message.toString(), Toast.LENGTH_LONG).show()
            pd.dismiss();
        })
        MySingleton.getInstance(this).addToRequestQueue(sr);


    }//end of load_categories

    fun apifetch(selected:String){
        var url="https://www.themealdb.com/api/json/v1/1/filter.php?c=" + selected//request URL
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
                    mealobj.mealcategory=""
                    mealobj.mealarea=""
                    mealobj.mealinstructions=""
                    mealobj.mealimg=result.getJSONObject(i).getString("strMealThumb")
                    meallist.add((mealobj))
                }
                mealadp= MealAdapter(this,meallist)
                rvmeals.layoutManager= LinearLayoutManager(this)
                rvmeals.adapter=mealadp

            }

            pd.dismiss()
        },{error->
            Toast.makeText(this,error.message.toString(), Toast.LENGTH_LONG).show()
            pd.dismiss();
        })
        MySingleton.getInstance(this).addToRequestQueue(sr);


    }//end of apifetch

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       // Toast.makeText(this,spcategory.selectedItem.toString(),Toast.LENGTH_LONG).show()
        apifetch(spcategory.selectedItem.toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}