package com.example.mealdb

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.mealdb.Class.MySingleton
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class RandomMeal : AppCompatActivity() {
    //global variable
    lateinit var txtmealname:TextView
    lateinit var txtcatarea:TextView
    lateinit var  txtinstructions:TextView
    lateinit var imgmeal:ImageView
    lateinit var btnrefresh:Button
    lateinit var pd:ProgressDialog
    //main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_meal)
      //initialize the global variables
        txtmealname=findViewById(R.id.tvmealname)
        txtcatarea=findViewById(R.id.tvcategoryarea)
        txtinstructions=findViewById(R.id.tvinstructions)
        imgmeal=findViewById(R.id.ivmealimg)
        btnrefresh=findViewById(R.id.btnrefresh)
        pd= ProgressDialog(this)
        pd.setTitle("MEAL API")
        pd.setMessage("Loading random meal")
        pd.setCancelable(false)
        pd.show()

        //call apifetch
        apifetch();

        //set the btnrefresh click
        btnrefresh.setOnClickListener {
            pd.show()
            apifetch()
        }
    }

    //function
    fun apifetch(){
        var url="https://www.themealdb.com/api/json/v1/1/random.php" //request URL
        var sr=JsonObjectRequest(Request.Method.GET,url,null, {response ->
            var result=response.getJSONArray("meals");
            var length=result.length();
            if(length > 0){
                //fetch API objects of array "meals
                txtmealname.text=result.getJSONObject(0).getString("strMeal")
                txtcatarea.text=result.getJSONObject(0).getString("strCategory").toString() +
                        " & " + result.getJSONObject(0).getString("strArea")
                txtinstructions.text=result.getJSONObject(0).getString("strInstructions")
                Picasso.get().load(result.getJSONObject(0).getString("strMealThumb")).into(imgmeal)
                pd.dismiss()
            }
        },{error->
            Toast.makeText(this,error.message.toString(),Toast.LENGTH_LONG).show()
            pd.dismiss();
        })
    MySingleton.getInstance(this).addToRequestQueue(sr);


    }
}