package com.bunty.notesappusingmvvmhilt.di

import com.bunty.notesappusingmvvmhilt.api.AuthInterceptor
import com.bunty.notesappusingmvvmhilt.api.NotesApiInterface
import com.bunty.notesappusingmvvmhilt.api.UserApiInterface
import com.bunty.notesappusingmvvmhilt.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        /*val logging = HttpLoggingInterceptor()
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder().addInterceptor(logging).build()*/
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit.Builder): UserApiInterface {
        return retrofit.build().create(UserApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideNotesApiInterface(
        retrofit: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): NotesApiInterface {
        return retrofit
            .client(okHttpClient)
            .build().create(NotesApiInterface::class.java)
    }
}