package com.example.mealdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun button_event(view: View){
        val button=view as Button
        when(button.id){
            R.id.btnrequest1->{
                startActivity(Intent(this,RandomMeal::class.java))
            }
            R.id.btnrequest2->{
                startActivity(Intent(this,SearchMeal::class.java))
            }
            R.id.btnrequest3->{
                startActivity(Intent(this,SearchCategories::class.java))
            }
        }
    }
}