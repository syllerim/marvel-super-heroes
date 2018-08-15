package com.costular.marvelheroes.repository

import com.costular.marvelheroes.FakeData
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by costular on 17/03/2018.
 */

class MarvelHeroesRepositoryTest {

    @Mock
    private var marvelHeroesRepository: MarvelHeroesRepository = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `repository should retrieve marvel heroes list`() {
        val heroes = listOf(FakeData.IRON_MAN)
        val observable = Flowable.just(heroes)
        whenever(marvelHeroesRepository.getMarvelHeroesList()).thenReturn(observable)

        marvelHeroesRepository
                .getMarvelHeroesList()
                .test()
                .assertValue { it.size == 1 }
                .assertValue { it.first().name == heroes.first().name }
                .assertComplete()

    }


    @Test
    fun `repository should retrieve favorites heroes list`() {
        val heroes = listOf(FakeData.ARROW, FakeData.IRON_MAN).filter { it.favorite }
        val observable = Flowable.just(heroes)
        whenever(marvelHeroesRepository.getFavoritesMarvelHeroesList()).thenReturn(observable)

        marvelHeroesRepository
                .getFavoritesMarvelHeroesList()!!
                .test()
                .assertValue { it.size == 1 }
                .assertValue { it.first().favorite == heroes.first().favorite }
                .assertComplete()
    }
}