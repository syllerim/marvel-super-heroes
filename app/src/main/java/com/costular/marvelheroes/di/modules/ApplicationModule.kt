package com.costular.marvelheroes.di.modules

import android.content.Context
import com.costular.marvelheroes.presentation.MainApp
import com.costular.marvelheroes.util.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by costular on 16/03/2018.
 */
@Module
class ApplicationModule(private val application: MainApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = Navigator()

}