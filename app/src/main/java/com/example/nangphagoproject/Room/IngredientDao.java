package com.example.nangphagoproject.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {


    // 전체 재료 조회
    @Query("SELECT * FROM `Ingredient`")
    List<Ingredient> getAll();

    /**
     * 목록 화면
     * param : keepKinds  01 : 실온,
     *                    02 : 냉장,
     *                    03 : 냉동,
     */
    @Query("SELECT * FROM `Ingredient` WHERE keepKinds = :keepKinds")
    List<Ingredient> getKindsList(String keepKinds);

    /**
     * 재료 검색
     * param : ingredientName
     * */
    @Query("SELECT * FROM `Ingredient` WHERE ingredientName like '%' || :ingredientName || '%'")
    List<Ingredient> getSearchIngredientName(String ingredientName);

    /**
     * 재료 상세 조회
     * param : id
     */
    @Query("SELECT * FROM `Ingredient` WHERE id = :id")
    Ingredient getItem(Long id);

    /**
     * 재료 등록
     * param : object Ingredient
     */
    @Insert
    void insertIngredient(Ingredient ingredient);

    @Update
    void updateIngredient(Ingredient ingredient);

    /**
     * 재료 삭제
     * param : id
     */
    @Query("DELETE FROM `Ingredient` WHERE id = :id" )
    void deleteIngredient(Long id);

}
