package com.costular.marvelheroes.data.db

import android.arch.persistence.room.*
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import io.reactivex.Maybe

@Dao
abstract class MarvelHeroesDao {

    // CRUD
    @Query("SELECT * FROM marvelHeroes")
    abstract fun getAllMarvelHeroes(): Maybe<List<MarvelHeroEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(marvelHeroes: List<MarvelHeroEntity>)

    @Query("DELETE FROM marvelHeroes")
    abstract fun deleteAllMarvelHeroes()

    @Transaction
    open fun removeInsertMarvelHeroes(marvelHeroes: List<MarvelHeroEntity>) {
        deleteAllMarvelHeroes()
        insertAll(marvelHeroes)
    }

    // FAVORITES
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(marvelHero: MarvelHeroEntity): Int

    @Query("SELECT * FROM marvelHeroes WHERE favorite = 1")
    abstract fun getAllFavoritesMarvelHeroes(): Maybe<List<MarvelHeroEntity>>

}