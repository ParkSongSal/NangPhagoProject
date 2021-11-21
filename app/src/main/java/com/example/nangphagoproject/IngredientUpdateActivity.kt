package com.example.nangphagoproject

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.nangphagoproject.Room.AppDataBase
import com.example.nangphagoproject.Room.Ingredient

class IngredientUpdateActivity : AppCompatActivity() {

    // DatePickerDialog 선
    var dateCallbackMethod: DatePickerDialog.OnDateSetListener? = null
    var dateCallbackMethod2: DatePickerDialog.OnDateSetListener? = null

    private var db: AppDataBase? = null
    lateinit var dlg: AlertDialog.Builder

    var id : Long? = 0L
    var keepKinds : String? = ""
    var kinds = ""
    var keepKindsBtn1 : Button? = null
    var keepKindsBtn2 : Button? = null
    var keepKindsBtn3 : Button? = null
    var kindsSpinner : Spinner? = null
    var purchaseDateEdit : EditText? = null
    var shelfLifeEdit : EditText? = null
    var ingredientNameEdit : EditText? = null
    var ingredientCntEdit : EditText? = null
    var memoEdit : EditText? = null
    var ingredientCnt = 0
    var ingredientCntTxt = ""
    private var ingredient :Ingredient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_update)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        keepKindsBtn1 = findViewById<Button>(R.id.keepKindsBtn1)
        keepKindsBtn2 = findViewById<Button>(R.id.keepKindsBtn2)
        keepKindsBtn3 = findViewById<Button>(R.id.keepKindsBtn3)

        kindsSpinner = findViewById<Spinner>(R.id.kindsSpinner)
        ingredientNameEdit = findViewById<EditText>(R.id.ingredientNameEdit)
        ingredientCntEdit = findViewById<EditText>(R.id.ingredientCntEdit)
        memoEdit = findViewById<EditText>(R.id.memoEdit)
        purchaseDateEdit = findViewById<EditText>(R.id.purchaseDateEdit)
        shelfLifeEdit = findViewById<EditText>(R.id.shelfLifeEdit)

        toolbar.setTitleTextColor(getColor(R.color.colorPrimaryDark))
        toolbar.title = "재료수정"
        setSupportActionBar(toolbar)

        db = Room.databaseBuilder(this, AppDataBase::class.java, "IngredientTable")
            .allowMainThreadQueries()
            .build()

        // AlertDialog Init
        dlg = AlertDialog.Builder(this)

        // DatePicker Linsener Init
        this.InitializeListener()

        val items = resources.getStringArray(R.array.kinds)

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        kindsSpinner?.adapter = myAdapter

        ingredient = intent.getSerializableExtra("ingredient") as Ingredient
        Log.d("TAG","update Ingredient : $ingredient")
        // 재료 데이터가 정상적으로 넘어온 경우
        if("".equals(ingredient?.id) || ingredient?.id != null){

            id = ingredient?.id

            ingredientNameEdit?.setText(ingredient?.ingredientName)

            when(ingredient?.kinds){
                "채소류"->{
                    kindsSpinner?.setSelection(0)
                }
                "육"->{
                    kindsSpinner?.setSelection(1)
                }
                "기타"->{
                    kindsSpinner?.setSelection(3)
                }
            }
            when(ingredient?.keepKinds){
                "01"->{
                    keepKindsBtn1?.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                }
                "02"->{
                    keepKindsBtn2?.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                }
                "03"->{
                    keepKindsBtn3?.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                }
            }
            ingredientCnt = Integer.parseInt(ingredient?.ingredientCnt)
            ingredientCntEdit?.setText(ingredient?.ingredientCnt)
            purchaseDateEdit?.setText(ingredient?.purchaseDate)
            shelfLifeEdit?.setText(ingredient?.shelfLife)
            memoEdit?.setText(ingredient?.memoContent)

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
        }else{ // 정상적으로 넘어오지 않은 경우 목록화면으로 이
            val intent = Intent(this@IngredientUpdateActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
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

            // 취소버튼
            R.id.cancelBtn->{
                cancelEvent()
            }
            // 수정 처리 버튼
            R.id.saveBtn->{
                saveIngredient()
            }
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
                    ingredientCntEdit?.setText(ingredientCntTxt)
                }
            }
            R.id.plusBtn->{
                if(ingredientCnt > 100){
                    Toast.makeText(applicationContext, "100개보다 많게 입력할 수 없습니다.",Toast.LENGTH_SHORT).show()
                }else{
                    ingredientCnt++
                    ingredientCntTxt = ingredientCnt.toString()
                    ingredientCntEdit?.setText(ingredientCntTxt)
                }
            }
        }
    }

    // 저장 처리
    fun saveIngredient(){
        kinds = kindsSpinner?.selectedItem.toString()
        val ingredientName = ingredientNameEdit?.text.toString()
        val mIngredientCnt = ingredientCntEdit?.text.toString()
        val purchaseDate = purchaseDateEdit?.text.toString()
        val shelfLife = shelfLifeEdit?.text.toString()
        val mMemoContent = memoEdit?.text.toString()
        val item = Ingredient(id,
            keepKinds,
            ingredientName,
            mIngredientCnt,
            kinds,
            purchaseDate,
            shelfLife,
            mMemoContent)
        db!!.IngredientDao().updateIngredient(item)

        val intent = Intent(applicationContext, MainActivity::class.java)
        Toast.makeText(applicationContext, "수정 되었습니다.", Toast.LENGTH_SHORT).show()
        startActivity(intent)
        finish()
    }

    fun cancelEvent(){
        dlg.setTitle("취소 알림")
        dlg.setMessage("수정을 그만하고 메인으로 돌아가시겠습니까?")
        dlg.setPositiveButton("예") { dialog, which ->
                intent = Intent(this@IngredientUpdateActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        dlg.setNegativeButton("아니오",null)
        dlg.show()
    }
    // 뒤로가기 버튼 클릭시 메인으로
    override fun onBackPressed() {
        super.onBackPressed()
        cancelEvent()
    }

    override fun onStop() {
        super.onStop()
    }
}