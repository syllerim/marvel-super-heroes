package com.costular.marvelheroes.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.costular.marvelheroes.data.model.MarvelHeroEntity

@Database(entities = [MarvelHeroEntity::class], version = 1)
@TypeConverters(MarvelHeroesConverters::class)
abstract class MarvelHeroesDatabase : RoomDatabase() {

    abstract fun getMarvelHeroDao(): MarvelHeroesDao

}