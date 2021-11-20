package com.example.nangphagoproject

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.example.nangphagoproject.Room.AppDataBase
import com.example.nangphagoproject.Room.Ingredient

class IngredientWriteActivity : AppCompatActivity() {

    // DatePickerDialog 선
    var dateCallbackMethod: DatePickerDialog.OnDateSetListener? = null
    var dateCallbackMethod2: DatePickerDialog.OnDateSetListener? = null


    private var db: AppDataBase? = null
    var keepKinds = ""
    var kinds = ""
    var purchaseDateEdit : EditText? = null
    var shelfLifeEdit : EditText? = null
    var ingredientCntEdit : EditText? = null
    var ingredientCnt = 0
    var ingredientCntTxt = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_write)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        var kindsSpinner = findViewById<Spinner>(R.id.kindsSpinner)
        var cancelBtn = findViewById<Button>(R.id.cancelBtn)
        var saveBtn = findViewById<Button>(R.id.saveBtn)
        var ingredientNameEdit = findViewById<EditText>(R.id.ingredientNameEdit)
        ingredientCntEdit = findViewById<EditText>(R.id.ingredientCntEdit)
        var memoEdit = findViewById<EditText>(R.id.memoEdit)
        purchaseDateEdit = findViewById<EditText>(R.id.purchaseDateEdit)
        shelfLifeEdit = findViewById<EditText>(R.id.shelfLifeEdit)
        db = Room.databaseBuilder(this, AppDataBase::class.java, "IngredientTable")
            .allowMainThreadQueries()
            .build()

        this.InitializeListener()


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

            kinds = kindsSpinner.selectedItem.toString()
            val ingredientName = ingredientNameEdit.text.toString()
            val mIngredientCnt = ingredientCntEdit?.text.toString()
            val purchaseDate = purchaseDateEdit?.text.toString()
            val shelfLife = shelfLifeEdit?.text.toString()
            val mMemoContent = memoEdit.text.toString()
            val item = Ingredient(keepKinds,
                                  ingredientName,
                                  mIngredientCnt,
                                  kinds,
                                  purchaseDate,
                                  shelfLife,
                                  mMemoContent)

            db!!.IngredientDao().insertTodo(item)

            val intent = Intent(applicationContext, MainActivity::class.java)
            Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }

        purchaseDateEdit?.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    //  .. 포커스시
                    val dialog = DatePickerDialog(this, dateCallbackMethod, 2021, 6, 10)

                    dialog.show()
                } else {
                    //  .. 포커스 뺏겼을 때
                }
            }
        shelfLifeEdit?.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    //  .. 포커스시
                    val dialog = DatePickerDialog(this, dateCallbackMethod2, 2021, 6, 10)

                    dialog.show()
                } else {
                    //  .. 포커스 뺏겼을 때
                }
            }
    }

    fun InitializeListener() {
        dateCallbackMethod =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val month = when (monthOfYear + 1) {
                    1 -> "01"
                    2 -> "02"
                    3 -> "03"
                    4 -> "04"
                    5 -> "05"
                    6 -> "06"
                    7 -> "07"
                    8 -> "08"
                    9 -> "09"
                    else -> monthOfYear.toString()
                }
                val day = when (dayOfMonth) {
                    1 -> "01"
                    2 -> "02"
                    3 -> "03"
                    4 -> "04"
                    5 -> "05"
                    6 -> "06"
                    7 -> "07"
                    8 -> "08"
                    9 -> "09"
                    else -> dayOfMonth.toString()
                }

                purchaseDateEdit?.setText(
                    "$year-$month-$day"
                )
                purchaseDateEdit?.clearFocus()
            }

        dateCallbackMethod2 =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val month = when (monthOfYear + 1) {
                    1 -> "01"
                    2 -> "02"
                    3 -> "03"
                    4 -> "04"
                    5 -> "05"
                    6 -> "06"
                    7 -> "07"
                    8 -> "08"
                    9 -> "09"
                    else -> monthOfYear.toString()
                }
                val day = when (dayOfMonth) {
                    1 -> "01"
                    2 -> "02"
                    3 -> "03"
                    4 -> "04"
                    5 -> "05"
                    6 -> "06"
                    7 -> "07"
                    8 -> "08"
                    9 -> "09"
                    else -> dayOfMonth.toString()
                }

                shelfLifeEdit?.setText(
                    "$year-$month-$day"
                )
                shelfLifeEdit?.clearFocus()

            }

    }
    fun onClick(view: View) {
        when(view.id){
            // 01 = 실온
            R.id.keepKindsBtn1->{
                keepKinds = "01"
            }
            // 02 = 냉장
            R.id.keepKindsBtn2->{
                keepKinds = "02"
            }
            // 03 = 냉동
            R.id.keepKindsBtn3->{
                keepKinds = "03"
            }
            R.id.minusBtn->{
                if(ingredientCnt == 0){
                    Toast.makeText(applicationContext, "0개보다 작게 입력할 수 없습니다.",Toast.LENGTH_SHORT).show()
                }else{
                    ingredientCnt--
                    ingredientCntTxt = ingredientCnt.toString()
                    ingredientCntEdit?.setText(ingredientCntTxt + "ea")
                }
            }
            R.id.plusBtn->{
                if(ingredientCnt > 100){
                    Toast.makeText(applicationContext, "100개보다 많게 입력할 수 없습니다.",Toast.LENGTH_SHORT).show()
                }else{
                    ingredientCnt++
                    ingredientCntTxt = ingredientCnt.toString()
                    ingredientCntEdit?.setText(ingredientCntTxt + "ea")
                }
            }
        }
    }


}