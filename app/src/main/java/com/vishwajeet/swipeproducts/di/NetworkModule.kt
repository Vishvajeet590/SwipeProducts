package com.vishwajeet.swipeproducts.di

import com.vishwajeet.swipeproducts.api.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://app.getswipe.in/")
            .build()
    }

    @Singleton
    @Provides
    fun providesProductAPI(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

}