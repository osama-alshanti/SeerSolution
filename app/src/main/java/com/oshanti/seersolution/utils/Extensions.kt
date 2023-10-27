package com.oshanti.seersolution.utils

import android.view.View

object Extensions {

    fun View.setVisibility(shouldVisible: Boolean){
        this.visibility = if (shouldVisible){
            View.VISIBLE
        }else{
            View.GONE
        }
    }
}