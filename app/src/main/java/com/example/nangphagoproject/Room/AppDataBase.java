package com.example.nangphagoproject.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Ingredient.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract IngredientDao IngredientDao();
}
