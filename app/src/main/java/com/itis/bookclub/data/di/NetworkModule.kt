package com.itis.bookclub.data.di

import com.itis.bookclub.BuildConfig
import com.itis.bookclub.data.api.BookApi
import com.itis.bookclub.util.Keys
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()

        insertKey(clientBuilder)

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
                //level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return clientBuilder.build()
    }

    private fun insertKey(clientBuilder: OkHttpClient.Builder) {
        clientBuilder.addInterceptor { chain ->
            val originRequest =  chain.request()

            val newRequest = chain.request().newBuilder()
                .header(Keys.AUTHORIZATION, "Bearer trippi_troppa")
                .method(originRequest.method, originRequest.body)
                .build()

            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient, json: Json): BookApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(contentType = Keys.APPLICATION_JSON.toMediaType())
            )
            .build()
            .create(BookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideJson() =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
}