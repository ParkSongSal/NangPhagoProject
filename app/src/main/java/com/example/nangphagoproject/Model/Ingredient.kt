package com.example.nangphagoproject.Model

/*
*  식재료
* */
class Ingredient {

    var id: Long = 0

    var keepKinds : String? = ""            // 보관 ( 실온, 냉장, 냉동) 구분
    var ingredientName: String? = ""        // 재료명
    var ingredientCnt: String? = ""        // 재료갯수
    var kinds : String? = ""                // 재료 종류
    var purchaseDate : String = ""          // 구입일자
    var shelfLife : String = ""             // 유통기한일자




    constructor(
        id: Long,
        keepKinds: String?,
        ingredientName: String?,
        ingredientCnt: String?,
        kinds: String?,
        purchaseDate: String,
        shelfLife: String
    ) {
        this.id = id
        this.keepKinds = keepKinds
        this.ingredientName = ingredientName
        this.ingredientCnt = ingredientCnt
        this.kinds = kinds
        this.purchaseDate = purchaseDate
        this.shelfLife = shelfLife
    }






    override fun toString(): String {
        return "Ingredient(id=$id, keepKinds=$keepKinds, ingredientName=$ingredientName, ingredientCnt=$ingredientCnt, kinds=$kinds, purchaseDate='$purchaseDate', shelfLife='$shelfLife')"
    }
}