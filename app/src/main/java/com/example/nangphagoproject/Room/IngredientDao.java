package com.example.nangphagoproject.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {


    @Query("SELECT * FROM `Ingredient`")
    List<Ingredient> getAll();

    @Query("SELECT * FROM `Ingredient` WHERE keepKinds = :keepKinds")
    List<Ingredient> getKindsList(String keepKinds);

    @Query("SELECT * FROM `Ingredient` WHERE id = :id")
    Ingredient getItem(Long id);

    @Insert
    void insertTodo(Ingredient ingredient);


    /*@Query("UPDATE `Ingredient` SET title =:check, checkGb=:checkGb WHERE id = :id")
    void updateTodo2(String check,Integer checkGb,long id);*/

    @Query("DELETE FROM `Ingredient` WHERE id = :id" )
    void deletePlan(int id);


    @Query("DELETE FROM `Ingredient`" )
    void deleteTodo();

    @Update
    void updateTodo(Ingredient plan);

    @Query("SELECT COUNT(id) FROM `Ingredient`")
    int getCount();

}
