package com.example.nangphagoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.nangphagoproject.Room.Ingredient

class IngredientDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_detail)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        var ingredientNameTxt = findViewById<TextView>(R.id.ingredientNameTxt)
        var keepKindsTxt = findViewById<TextView>(R.id.keepKindsTxt)
        var kindsTxt = findViewById<TextView>(R.id.kindsTxt)
        var ingredientCntTxt = findViewById<TextView>(R.id.ingredientCntTxt)
        var purchaseDateTxt = findViewById<TextView>(R.id.purchaseDateTxt)
        var shelfLifeTxt = findViewById<TextView>(R.id.shelfLifeTxt)
        var memoTxt = findViewById<TextView>(R.id.memoTxt)

        toolbar.setTitleTextColor(getColor(R.color.colorPrimaryDark))
        toolbar.title = "재료상세"
        setSupportActionBar(toolbar)

        // 목록화면에서 넘겨준 재료 데이터
        val ingredient = intent.getSerializableExtra("ingredient") as Ingredient

        // 재료 데이터가 정상적으로 넘어온 경우
        if("".equals(ingredient.id) || ingredient.id != null){
            ingredientNameTxt.text = ingredient.ingredientName
            keepKindsTxt.text = ingredient.keepKinds
            kindsTxt.text = ingredient.kinds
            ingredientCntTxt.text = ingredient.ingredientCnt
            purchaseDateTxt.text = ingredient.purchaseDate
            shelfLifeTxt.text = ingredient.shelfLife
            memoTxt.text = ingredient.memoContent
        }else{ // 정상적으로 넘어오지 않은 경우 목록화면으로 이
            val intent = Intent(this@IngredientDetailActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClick(view: View) {
        when(view.id) {
            // 삭제 버튼
            R.id.delBtn -> {

            }
            // 수정 버튼
            R.id.updateBtn->{

            }
        }
    }
}