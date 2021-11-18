package com.example.nangphagoproject

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.example.nangphagoproject.Room.AppDataBase
import com.example.nangphagoproject.Room.Ingredient

class IngredientWriteActivity : AppCompatActivity() {


    private var db: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_write)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        var kindsSpinner = findViewById<Spinner>(R.id.kindsSpinner)
        var cancelBtn = findViewById<Button>(R.id.cancelBtn)
        var saveBtn = findViewById<Button>(R.id.saveBtn)

        db = Room.databaseBuilder(this, AppDataBase::class.java, "IngredientTable")
            .allowMainThreadQueries()
            .build()


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

        saveBtn.setOnClickListener{
            val item = Ingredient("03", "냉동만두", "1", "K07","2021-11-18","2022-11-25")

            db!!.IngredientDao().insertTodo(item)

            val intent = Intent(applicationContext, MainActivity::class.java)
            Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
    }



}