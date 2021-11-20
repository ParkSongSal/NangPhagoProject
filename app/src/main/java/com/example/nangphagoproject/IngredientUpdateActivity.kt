package com.example.nangphagoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.nangphagoproject.Room.Ingredient

class IngredientUpdateActivity : AppCompatActivity() {

    lateinit var dlg: AlertDialog.Builder

    var id : Long? = 0L
    private var ingredient :Ingredient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_update)


        ingredient = intent.getSerializableExtra("ingredient") as Ingredient
        Log.d("TAG","update Ingredient : $ingredient")
        // 재료 데이터가 정상적으로 넘어온 경우
        if("".equals(ingredient?.id) || ingredient?.id != null){
            id = ingredient?.id

        }else{ // 정상적으로 넘어오지 않은 경우 목록화면으로 이
            val intent = Intent(this@IngredientUpdateActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
        }
    }
}