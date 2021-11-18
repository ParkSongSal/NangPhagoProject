package com.example.nangphagoproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nangphagoproject.Adapter.IngredientDataAdapter
import com.example.nangphagoproject.Model.Ingredient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainActivity : AppCompatActivity() {

    private var mDataList: MutableList<Ingredient> = arrayListOf()
    private var mAdapter: IngredientDataAdapter? = null
    lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var fab = findViewById<FloatingActionButton>(R.id.fab)

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
        dummyData(1, "1", "양파", "2", "K01", "2021-11-07", "2021-11-14")


        mAdapter = IngredientDataAdapter(applicationContext, mDataList)
        recyclerView.adapter = mAdapter

        fab.setOnClickListener{
            intent = Intent(this@MainActivity, IngredientWriteActivity::class.java)
            startActivity(intent)
            finish()
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
        val ingredient : Ingredient = mDataList.get(event.position)

        val intent = Intent(this@MainActivity, IngredientDetailActivity::class.java)
        intent.putExtra("id", event.id)
        intent.putExtra("position", event.position)
        startActivity(intent)
        finish()

    }


    private fun changeView(pos: Int){
        mDataList.clear()
        when (pos) {
            0 -> {
                dummyData(1, "1", "양파", "2", "K01", "2021-11-07", "2021-11-14")
            }
            1 -> {
                dummyData(2, "2", "대파", "1", "K01", "2021-11-07", "2021-11-14")
            }
            2 -> {
                dummyData(3, "3", "돼지고기", "2", "K02", "2021-11-07", "2021-11-12")
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
        shelfLife: String
    ){

        val item = Ingredient(
            id,
            keepKinds,
            ingredientName,
            ingredientCnt,
            kinds,
            purchaseDate,
            shelfLife
        )
        mDataList.add(item)
    }
}