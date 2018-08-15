package com.costular.marvelheroes.repository

import android.arch.lifecycle.Observer
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.presentation.heroeslist.HeroesListViewModel
import com.costular.marvelheroes.util.SettingsManager
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class HeroesListViewModelTest {

    val observerState = mock<Observer<List<MarvelHeroEntity>>>()

    @Mock
    private var marvelHeroesRepository: MarvelHeroesRepository = mock()

    @Mock
    private var settingsManager: SettingsManager = mock()

    val viewModel by lazy { HeroesListViewModel(marvelHeroesRepository, settingsManager) }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        settingsManager.firstLoad = false
    }

//    @Test
//    fun `load marvel list of favorites should call repository`() {
//        whenever(marvelHeroesRepository.getFavoritesMarvelHeroesList()).thenReturn(Flowable.empty())
//
//        viewModel.loadFavoritesMarvelHeroesList()
//        verify(marvelHeroesRepository).getFavoritesMarvelHeroesList()
//    }
}