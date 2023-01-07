package com.nads.kingshan.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainModule::class
    ]
)
interface AppComponent {

}