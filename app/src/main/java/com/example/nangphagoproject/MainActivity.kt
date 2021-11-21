package com.example.nangphagoproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.nangphagoproject.Adapter.IngredientDataAdapter
import com.example.nangphagoproject.Room.AppDataBase
import com.example.nangphagoproject.Room.Ingredient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.*
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

        val searchView = findViewById<SearchView>(R.id.Search_view)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

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

        mAdapter = IngredientDataAdapter(applicationContext, mDataList)
        recyclerView.adapter = mAdapter

        // 재료 등록 버튼 클릭 리스너
        fab.setOnClickListener{
            intent = Intent(this@MainActivity, IngredientWriteActivity::class.java)
            startActivity(intent)
            finish()
        }


        // 검색뷰
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { //완료누르면
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean { //변경될때마다
                mDataList.clear()
                if ("" == newText || newText.isEmpty()) {
                    // 검색할 내용이 없으면 실온 데이터 불러오기
                    tabLayout.visibility = VISIBLE
                    getKindsList("01")
                } else {
                    tabLayout.visibility = GONE
                    for (i in 0 until db?.IngredientDao()
                        ?.getSearchIngredientName(newText)?.size!!) {

                        val ingredient = Ingredient(
                            db!!.IngredientDao().getSearchIngredientName(newText)[i].id,
                            db!!.IngredientDao().getSearchIngredientName(newText)[i].keepKinds,
                            db!!.IngredientDao().getSearchIngredientName(newText)[i].ingredientName,
                            db!!.IngredientDao().getSearchIngredientName(newText)[i].ingredientCnt,
                            db!!.IngredientDao().getSearchIngredientName(newText)[i].kinds,
                            db!!.IngredientDao().getSearchIngredientName(newText)[i].purchaseDate,
                            db!!.IngredientDao().getSearchIngredientName(newText)[i].shelfLife,
                            db!!.IngredientDao().getSearchIngredientName(newText)[i].memoContent
                        )
                        mDataList.add(ingredient)
                        mAdapter!!.swap(mDataList)
                    }
                }
                return true
            }
        })

    }

    private fun getKindsList(keepKinds: String) {
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
            }
            1 -> {
                getKindsList("02")  // 냉장
            }
            2 -> {
                getKindsList("03")  // 냉동
            }
        }
        mAdapter = IngredientDataAdapter(applicationContext, mDataList)
        recyclerView.adapter = mAdapter
    }

    override fun onDestroy() {
        db = null
        super.onDestroy()
    }
}