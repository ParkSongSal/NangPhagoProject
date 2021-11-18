package com.example.nangphagoproject.Utils

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class Common {

    /*
    * kinds 재료 종류
    * K01 : 채소류
    * K02 : 육류
    * K03 : 계란류
    * K04 : 생선류
    * */
    open fun kindsFormat(kinds: String): String {
        var result = ""
        if (kinds != "") {
            if ("K01" == kinds) {
                result = "채소류"
            } else if ("K02" == kinds) {
                result = "육류"
            }
        } else {
            result = "미등록"
        }
        return result
    }


    /*
    * purchaseDate 구매일자
    * shelfLife 유통기한일자
    * return calDateDays 유통기한 D-Day
    * */
    open fun getShelfLife(purchaseDate : String, shelfLife : String): Long {

        val  format : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
        val FirstDate : Date = format.parse(purchaseDate)
        val SecondDate : Date  = format.parse(shelfLife)

        var calDate : Long = FirstDate.time - SecondDate.time;

        var calDateDays : Long = calDate / ( 24*60*60*1000);

        calDateDays = abs(calDateDays);

        return calDateDays
    }


}