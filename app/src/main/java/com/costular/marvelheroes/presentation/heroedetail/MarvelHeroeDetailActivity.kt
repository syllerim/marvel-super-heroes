package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.costular.marvelheroes.R
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.MainApp
import com.costular.marvelheroes.presentation.heroeslist.MarvelHeroDetailViewModel
import kotlinx.android.synthetic.main.activity_hero_detail.*
import kotlinx.android.synthetic.main.activity_hero_detail.view.*
import javax.inject.Inject

/**
 * Created by costular on 18/03/2018.
 */
class MarvelHeroeDetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_HEROE = "heroe"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var marvelHeroDetailViewModel: MarvelHeroDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        inject()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hero_detail)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        supportPostponeEnterTransition() // Wait for image load and then draw the animation

        setupViewModel()

        val hero: MarvelHeroEntity? = intent?.extras?.getParcelable(PARAM_HEROE)
        hero?.let {
            fillHeroData(it)

            heroFavoriteButton.setOnClickListener {
                hero.favorite = !hero.favorite
                marvelHeroDetailViewModel.saveFavorite(hero)
            }

            heroRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
                hero.rating = rating.toInt()
                marvelHeroDetailViewModel.saveFavorite(hero)
            }
        }
    }

    private fun inject() {
        (application as MainApp).component.inject(this)
    }

    private fun setupViewModel() {
        marvelHeroDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MarvelHeroDetailViewModel::class.java)
        bindData()
    }

    private fun bindData() {
        marvelHeroDetailViewModel.marvelHeroState.observe(this, Observer { marvelHeroe ->
            marvelHeroe?.let {
                fillHeroData(it)
            }
        })
    }

    private fun fillHeroData(hero: MarvelHeroEntity) {
        Glide.with(this)
                .load(hero.photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(heroDetailImage)

        heroDetailName.text = hero.name
        heroDetailRealName.text = hero.realName
        heroDetailHeight.text = hero.height
        heroDetailPower.text = hero.power
        heroDetailAbilities.text = hero.abilities
        heroFavoriteButton.setImageResource(if (hero.favorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_empty)
        heroRatingBar.rating = hero.rating.toFloat()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}