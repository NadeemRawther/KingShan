package com.nads.kingshan

import android.os.AsyncTask
import com.nads.kingshan.di.DefaultDispatcher
import com.nads.kingshan.di.IoDispatcher
import com.nads.kingshan.di.MainDispatcher
import com.nads.kingshan.di.MainModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher

//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [MainModule::class]
//)
//@Module
//abstract class TestCoroutinesDispatchersModule {
//
//    @DefaultDispatcher
//    @Provides
//    fun providesDefaultDispatcher(): CoroutineDispatcher =
//        AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()
//
//    @IoDispatcher
//    @Provides
//    fun providesIoDispatcher(): CoroutineDispatcher =
//        AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()
//
//    @MainDispatcher
//    @Provides
//    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
//}