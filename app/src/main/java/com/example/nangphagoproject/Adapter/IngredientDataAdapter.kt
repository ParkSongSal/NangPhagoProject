package com.example.nangphagoproject.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nangphagoproject.R
import com.example.nangphagoproject.Room.Ingredient
import com.example.nangphagoproject.Utils.Common
import org.greenrobot.eventbus.EventBus

open class IngredientDataAdapter(
    var context: Context,
    var mData: MutableList<Ingredient>
) : RecyclerView.Adapter<IngredientDataAdapter.ViewHolder>(){


    open fun swap(newIngredientList: MutableList<Ingredient>) {
        mData = newIngredientList
        notifyDataSetChanged()
    }
    //Event Bus 클래스
    class ItemClickEvent     //this.id = id;
        (  //public long id;
        var position: Int,
        var id: Long?
    )

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val convertView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient_list, parent, false)
        return ViewHolder(convertView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.ingredientNameTxt.text = mData.get(position).ingredientName
        viewHolder.ingredientCntTxt.text = mData.get(position).ingredientCnt

        val common = Common()
        val mKinds = common.kindsFormat(mData[position].kinds.toString())
        viewHolder.kindsTxt.text = mKinds
        viewHolder.purchaseDateTxt.text = mData[position].purchaseDate

        val dDay = common.getShelfLife(mData[position].purchaseDate, mData[position].shelfLife)
        viewHolder.dDayTxt.text = "D -$dDay"

        viewHolder.itemView.setOnClickListener { // MainActivity에 onItemClick이 받음
            EventBus.getDefault().post(ItemClickEvent(position, mData[position].id))

        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var ingredientNameTxt: TextView
       var ingredientCntTxt: TextView
       var kindsTxt: TextView
       var purchaseDateTxt: TextView
       var dDayTxt : TextView
        init {
            // 레이아웃 들고 오기
            val ingredientName = itemView.findViewById<TextView>(R.id.ingredientNameTxt)
            val ingredientCnt = itemView.findViewById<TextView>(R.id.ingredientCntTxt)
            val kinds = itemView.findViewById<TextView>(R.id.kindsTxt)
            val purchaseDate = itemView.findViewById<TextView>(R.id.purchaseDateTxt)
            val dDay = itemView.findViewById<TextView>(R.id.d_dayTxt)


            this.ingredientNameTxt = ingredientName
            this.ingredientCntTxt = ingredientCnt
            this.kindsTxt = kinds
            this.purchaseDateTxt = purchaseDate
            this.dDayTxt = dDay
        }
    }
}