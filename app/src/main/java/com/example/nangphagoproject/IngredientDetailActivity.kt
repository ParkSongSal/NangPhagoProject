package com.example.nangphagoproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.example.nangphagoproject.Room.AppDataBase
import com.example.nangphagoproject.Room.Ingredient

class IngredientDetailActivity : AppCompatActivity() {

    lateinit var dlg: AlertDialog.Builder

    private var db: AppDataBase? = null
    private var ingredient :Ingredient? = null
    var id : Long? = 0L
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

        db = Room.databaseBuilder(this, AppDataBase::class.java, "IngredientTable")
            .allowMainThreadQueries()
            .build()
        // AlertDialog Init
        dlg = AlertDialog.Builder(this)
        // 목록화면에서 넘겨준 재료 데이터
        ingredient = intent.getSerializableExtra("ingredient") as Ingredient

        // 재료 데이터가 정상적으로 넘어온 경우
        if("".equals(ingredient?.id) || ingredient?.id != null){
            id = ingredient?.id
            ingredientNameTxt.text = ingredient?.ingredientName
            when(ingredient?.keepKinds){
                "01"->{
                    keepKindsTxt.text = "실온"
                }
                "02"->{
                    keepKindsTxt.text = "냉장"
                }
                "03"->{
                    keepKindsTxt.text = "냉동"
                }
            }
            kindsTxt.text = ingredient?.kinds
            ingredientCntTxt.text = ingredient?.ingredientCnt
            purchaseDateTxt.text = ingredient?.purchaseDate
            shelfLifeTxt.text = ingredient?.shelfLife
            memoTxt.text = ingredient?.memoContent
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
                dlg.setTitle("해당 재료를 삭제하시겠습니까?")
                .setPositiveButton("예") { dialog, which ->
                    deleteIngredient()
                }
                .setNegativeButton("아니오",null)
                .show()
            }
            // 수정 버튼
            R.id.updateBtn -> {
                val intent = Intent(this@IngredientDetailActivity, IngredientUpdateActivity::class.java)
                intent.putExtra("ingredient", ingredient)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun deleteIngredient(){
        if(id != 0L){
            db?.IngredientDao()?.deleteIngredient(id)
            val intent = Intent(this@IngredientDetailActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext, "잘못된 id입니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
