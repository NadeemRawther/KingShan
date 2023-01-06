package com.nads.kingshan.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.nads.kingshan.data.repo.KingDefaultRepo
import com.nads.kingshan.data.remote.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor.invoke { chain: Interceptor.Chain ->
                val orginal: Request = chain.request()
                val request = orginal.newBuilder().method(
                    orginal.method,
                    orginal.body
                ).build()
                chain.proceed(request)
            }).build()
        return client
    }
    @Singleton
    @Provides
    fun providesRetrofitClient(): DataSource {
        val gson = GsonBuilder()
            .registerTypeAdapter(Int::class.javaPrimitiveType, IntTypeAdapter())
            .registerTypeAdapter(Int::class.java, IntTypeAdapter()).create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(providesOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(service: DataSource, caroutinedispatchers: CoroutineDispatcher): KingDefaultRepo {
        return KingDefaultRepo()
    }

    @Singleton
    @Provides
    fun provideCoroutine(): CoroutineDispatcher {
        return Dispatchers.IO
    }


//    @Singleton
//    @Provides
//    fun provideDataBase(@ApplicationContext context: Context): LandFindDataBase {
//        return Room.databaseBuilder(
//            context,
//            LandFindDataBase::class.java, "Lands.db"
//        ).build()
//    }



    class IntTypeAdapter : TypeAdapter<Number?>() {
        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: Number?) {
            out.value(value)
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): Number? {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }
            return try {
                val result = `in`.nextString()
                if ("" == result) {
                    null
                } else result.toInt()
            } catch (e: NumberFormatException) {
                throw JsonSyntaxException(e)
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://findfalcone.herokuapp.com/planets"
    }
}