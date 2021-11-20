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
    void insertIngredient(Ingredient ingredient);

    @Query("DELETE FROM `Ingredient` WHERE id = :id" )
    void deleteIngredient(Long id);


    @Query("DELETE FROM `Ingredient`" )
    void deleteAll();

}
