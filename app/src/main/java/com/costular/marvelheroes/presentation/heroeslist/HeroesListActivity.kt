package com.costular.marvelheroes.presentation.heroeslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.costular.marvelheroes.R
import com.costular.marvelheroes.R.drawable.ic_favorite_empty
import com.costular.marvelheroes.R.id.action_show_favorites
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.MainApp
import com.costular.marvelheroes.util.Navigator
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.view.MenuInflater
import com.costular.marvelheroes.R.drawable.ic_favorite_filled


class HeroesListActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var heroesListViewModel: HeroesListViewModel

    lateinit var adapter: HeroesListAdapter

    var showFavorites = false

    val itemMenuIcon get() = if (showFavorites) ic_favorite_filled else ic_favorite_empty

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpRecycler()
        setUpViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) : Boolean = when (item?.itemId) {

        action_show_favorites -> {
            showFavorites = !showFavorites
            item.setIcon(itemMenuIcon)
            loadDataFor(showFavorites)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun inject() {
        (application as MainApp).component.inject(this)
    }

    private fun setUpRecycler() {
        adapter = HeroesListAdapter { hero, image -> goToHeroDetail(hero, image) }
        heroesListRecycler.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        heroesListRecycler.itemAnimator = DefaultItemAnimator()
        heroesListRecycler.adapter = adapter
    }

    private fun setUpViewModel() {
        heroesListViewModel = ViewModelProviders.of(this, viewModelFactory).get(HeroesListViewModel::class.java)
        bindEvents()
        heroesListViewModel.loadMarvelHeroesList()
    }

    private fun bindEvents() {
        heroesListViewModel.isLoadingState.observe(this, Observer { isLoading ->
            isLoading?.let {
                showLoading(it)
            }
        })

        heroesListViewModel.marvelHeroesListState.observe(this, Observer { marvelHeroesList ->
            marvelHeroesList?.let {
                onHeroesListLoaded(it)
            }
        })
    }

    private fun loadDataFor(favorites: Boolean) {
        if (favorites) {
            heroesListViewModel.loadFavoritesMarvelHeroesList()
        } else {

            heroesListViewModel.loadMarvelHeroesList()
        }
    }

    private fun onHeroesListLoaded(marvelHeroesList: List<MarvelHeroEntity>) {
        adapter.swapData(marvelHeroesList)
    }

    private fun goToHeroDetail(hero: MarvelHeroEntity, image: View) {
        navigator.goToHeroDetail(this, hero, image)
    }

    private fun showLoading(isLoading: Boolean) {
        heroesListLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

}
