package com.example.nangphagoproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nangphagoproject.Adapter.IngredientDataAdapter
import com.example.nangphagoproject.Room.AppDataBase
import com.example.nangphagoproject.Room.Ingredient
import com.example.nangphagoproject.Utils.Common
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainActivity : AppCompatActivity() {

    private var mDataList: MutableList<Ingredient> = arrayListOf()
    private var mAdapter: IngredientDataAdapter? = null
    lateinit var recyclerView : RecyclerView

    private var db: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var fab = findViewById<FloatingActionButton>(R.id.fab)

        db = Room.databaseBuilder(this, AppDataBase::class.java, "IngredientTable")
            .allowMainThreadQueries()
            .build()

        // 실온 데이터 가져오기
        getKindsList("01")

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val pos = tab.position
                changeView(pos)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        //dummyData(1, "1", "양파", "2", "K01", "2021-11-07", "2021-11-14")


        mAdapter = IngredientDataAdapter(applicationContext, mDataList)
        recyclerView.adapter = mAdapter

        fab.setOnClickListener{
            intent = Intent(this@MainActivity, IngredientWriteActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun getKindsList(keepKinds : String) {
        for (i in 0 until db?.IngredientDao()?.getKindsList(keepKinds)?.size!!) {

            val ingredient = Ingredient(
                db!!.IngredientDao().getKindsList(keepKinds)[i].id,
                db!!.IngredientDao().getKindsList(keepKinds)[i].keepKinds,
                db!!.IngredientDao().getKindsList(keepKinds)[i].ingredientName,
                db!!.IngredientDao().getKindsList(keepKinds)[i].ingredientCnt,
                db!!.IngredientDao().getKindsList(keepKinds)[i].kinds,
                db!!.IngredientDao().getKindsList(keepKinds)[i].purchaseDate,
                db!!.IngredientDao().getKindsList(keepKinds)[i].shelfLife,
                db!!.IngredientDao().getKindsList(keepKinds)[i].memoContent
            )
            //val idIngredient = Ingredient(db!!.IngredientDao().getKindsList(keepKinds)[i].id)

            mDataList.add(ingredient)
            mAdapter = IngredientDataAdapter(this@MainActivity, mDataList)
            recyclerView.adapter = mAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    // 보낸이 : MemoRecyclerAdapter
    @SuppressLint("RestrictedApi")
    @Subscribe
    fun onItemClick(event: IngredientDataAdapter.ItemClickEvent) {

        val ingredient : Ingredient? = db?.IngredientDao()?.getItem(event.id)
        val intent = Intent(this@MainActivity, IngredientDetailActivity::class.java)
        intent.putExtra("id", event.id)
        intent.putExtra("ingredient", ingredient)
        startActivity(intent)
        finish()

    }


    private fun changeView(pos: Int){
        mDataList.clear()
        when (pos) {
            0 -> {
                getKindsList("01")  // 실온
                //dummyData(1, "1", "양파", "2", "K01", "2021-11-07", "2021-11-14")
            }
            1 -> {
                getKindsList("02")  // 냉장
                //dummyData(2, "2", "대파", "1", "K01", "2021-11-07", "2021-11-14")
            }
            2 -> {
                getKindsList("03")  // 냉동
                //dummyData(3, "3", "돼지고기", "2", "K02", "2021-11-07", "2021-11-12")
            }
        }
        mAdapter = IngredientDataAdapter(applicationContext, mDataList)
        recyclerView.adapter = mAdapter
    }


    private fun dummyData(
        id: Long,
        keepKinds: String,
        ingredientName: String,
        ingredientCnt: String,
        kinds: String,
        purchaseDate: String,
        shelfLife: String,
        memoContent : String
    ){

        val item = Ingredient(
            id,
            keepKinds,
            ingredientName,
            ingredientCnt,
            kinds,
            purchaseDate,
            shelfLife,
            memoContent
        )
        mDataList.add(item)
    }

    // Migration 코드
    private val MIGRATION_3_4 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE 'Ingredient' ADD COLUMN 'memoContent' String")
        }
    }
    // Migration 코드
    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("Delete FROM 'Ingredient'")
        }
    }
}