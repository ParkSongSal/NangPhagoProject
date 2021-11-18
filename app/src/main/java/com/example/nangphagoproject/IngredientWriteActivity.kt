package com.example.nangphagoproject

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class IngredientWriteActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_write)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        var kindsSpinner = findViewById<Spinner>(R.id.kindsSpinner)
        var cancelBtn = findViewById<Button>(R.id.cancelBtn)
        
        
        
        
        toolbar.setTitleTextColor(getColor(R.color.colorPrimaryDark))
        toolbar.title = "재료등록"
        setSupportActionBar(toolbar)

        val items = resources.getStringArray(R.array.kinds)

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        kindsSpinner.adapter = myAdapter


        // 취소 버튼 클릭시 메인으로
        cancelBtn.setOnClickListener{
            intent = Intent(this@IngredientWriteActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



}