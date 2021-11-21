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
    /*
        Event Bus 클래스
        목록화면에서 재료 클릭시 사용
    */
    class ItemClickEvent
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
        viewHolder.ingredientNameTxt.text = mData[position].ingredientName
        viewHolder.ingredientCntTxt.text = mData[position].ingredientCnt + "ea"

        val common = Common()
        val keepKinds = common.keepKindsFormat(mData[position].keepKinds.toString())

        // 보관방법
        viewHolder.keepKindsTxt.text = keepKinds

        // 재료 종류
        viewHolder.kindsTxt.text = mData[position].kinds

        // 구입일자
        viewHolder.purchaseDateTxt.text = mData[position].purchaseDate

        // 남은 유통기한일자
        val dDay = common.getShelfLife(mData[position].purchaseDate, mData[position].shelfLife)
        viewHolder.dDayTxt.text = "D -$dDay"

        viewHolder.itemView.setOnClickListener {
            // MainActivity에 onItemClick이 받음
            EventBus.getDefault().post(ItemClickEvent(position, mData[position].id))

        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var ingredientNameTxt: TextView
       var ingredientCntTxt: TextView
       var keepKindsTxt : TextView
       var kindsTxt: TextView
       var purchaseDateTxt: TextView
       var dDayTxt : TextView
        init {
            // 레이아웃 들고 오기
            val ingredientName = itemView.findViewById<TextView>(R.id.ingredientNameTxt)
            val ingredientCnt = itemView.findViewById<TextView>(R.id.ingredientCntTxt)
            val keepKinds = itemView.findViewById<TextView>(R.id.keepKindsTxt)
            val kinds = itemView.findViewById<TextView>(R.id.kindsTxt)
            val purchaseDate = itemView.findViewById<TextView>(R.id.purchaseDateTxt)
            val dDay = itemView.findViewById<TextView>(R.id.d_dayTxt)


            this.ingredientNameTxt = ingredientName
            this.ingredientCntTxt = ingredientCnt
            this.keepKindsTxt = keepKinds
            this.kindsTxt = kinds
            this.purchaseDateTxt = purchaseDate
            this.dDayTxt = dDay
        }
    }
}