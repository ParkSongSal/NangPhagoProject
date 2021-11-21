package com.example.nangphagoproject.Utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

open class Common {

    /*
    * keepKinds 재료 종류
    * 01 : 실온
    * 02 : 냉장
    * 03 : 냉동
    * */
    open fun keepKindsFormat(keepKinds: String?): String {
        var result = ""
        if (keepKinds != "") {
            when (keepKinds) {
                "01" -> {
                    result = "실온"
                }
                "02" -> {
                    result = "냉장"
                }
                "03" -> {
                    result = "냉동"
                }
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
    open fun getShelfLife(purchaseDate : String?, shelfLife : String): Long {

        val  format : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
        val FirstDate : Date = format.parse(purchaseDate)
        val SecondDate : Date  = format.parse(shelfLife)

        var calDate : Long = FirstDate.time - SecondDate.time;

        var calDateDays : Long = calDate / ( 24*60*60*1000);

        calDateDays = abs(calDateDays);

        return calDateDays
    }

    //현재 시간
    open fun nowDate(format: String?): String? {
        // 현재시간을 msec 으로 구한다.
        val now = System.currentTimeMillis()
        // 현재시간을 date 변수에 저장한다.
        val nowdate = Date(now)
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        val sdfNow = SimpleDateFormat(format)
        // nowDate 변수에 값을 저장한다.
        return sdfNow.format(nowdate)
    }



}