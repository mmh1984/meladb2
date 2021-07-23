package com.example.mealdb


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.core.view.isVisible

class Loading : AppCompatActivity() {
    lateinit var lbltitle:TextView
    lateinit var pb:ProgressBar;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        lbltitle=findViewById(R.id.tvtitle)
        pb=findViewById(R.id.pb1)
        scale_title()

    }

    fun rotate_title(){
        val animator = ObjectAnimator.ofFloat(lbltitle, View.ROTATION, -360f, 0f)
        animator.duration = 1000
        animator.repeatCount=1
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {

            }
            override fun onAnimationEnd(animation: Animator?) {

            }
        })
        animator.start()
    }

    fun scale_title(){

            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
            val animator = ObjectAnimator.ofPropertyValuesHolder(
                lbltitle, scaleX, scaleY)
            animator.repeatCount = 2
            animator.repeatMode = ObjectAnimator.REVERSE
           animator.addListener (object: AnimatorListenerAdapter(){
               override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                   pb.isVisible=false
               }

               override fun onAnimationEnd(animation: Animator?) {
                   rotate_title()
                   pb.isVisible=true
                   fader()
               }
           })
            animator.start()
        }
    fun fader(){
        val animator = ObjectAnimator.ofFloat(pb, View.ALPHA, 0f)
        animator.repeatCount = 3
        animator.duration=1000
        animator.repeatMode = ObjectAnimator.REVERSE
        //animator.disableViewDuringAnimation(fadeButton)
        animator.addListener (object: AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                rotate_title()
            }

            override fun onAnimationEnd(animation: Animator?) {
              var i= Intent(application,MainActivity::class.java)
                startActivity(i)
            }
        })
        animator.start()
    }
}