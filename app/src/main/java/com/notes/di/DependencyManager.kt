package com.notes.di

import android.app.Application

class DependencyManager private constructor(
    application: Application
) {

    companion object {
        private lateinit var instance: DependencyManager

        fun init(application: Application) {
            instance = DependencyManager(application)
        }

        fun getComponent() = instance.rootComponent
    }

    private val appComponent = DaggerAppComponent.factory().create(application)

    private val rootComponent = DaggerRootComponent.factory().create(appComponent)


}
