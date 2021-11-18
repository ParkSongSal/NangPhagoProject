package com.example.nangphagoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class IngredientDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_detail)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        
        toolbar.setTitleTextColor(getColor(R.color.colorPrimaryDark))
        toolbar.title = "재료상세"
        setSupportActionBar(toolbar)
    }
}