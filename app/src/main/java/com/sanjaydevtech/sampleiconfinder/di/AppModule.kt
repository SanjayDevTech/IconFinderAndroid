package com.sanjaydevtech.sampleiconfinder.di

import android.content.Context
import com.sanjaydevtech.sampleiconfinder.Secrets
import com.sanjaydevtech.sampleiconfinder.data.IconFinderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val cache = Cache(context.cacheDir, 10 * 1024 * 1024)
        return OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addInterceptor { chain ->
                chain.proceed(chain
                    .request()
                    .newBuilder()
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer ${Secrets.API_KEY}")
                    .build()
                )
            }
            .build()
    }

    @Provides
    @Singleton
    fun providesIconFinderService(
        client: OkHttpClient,
    ): IconFinderService {
        return Retrofit.Builder()
            .baseUrl("https://api.iconfinder.com/v4/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(IconFinderService::class.java)
    }
}