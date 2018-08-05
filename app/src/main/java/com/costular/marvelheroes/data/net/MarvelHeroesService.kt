package com.costular.marvelheroes.data.net

import com.costular.marvelheroes.data.model.MarvelHeroesResponse
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * Created by costular on 17/03/2018.
 */
interface MarvelHeroesService {

    @GET(".")
    fun getMarvelHeroesList(): Flowable<MarvelHeroesResponse>

}