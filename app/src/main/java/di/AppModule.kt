package di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import network.MatchApi
import org.jetbrains.annotations.ApiStatus
import repository.MatchRepository
import repository.MatchRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton

    fun provideRetrofit(): Retrofit{


        return Retrofit.Builder()
            .baseUrl("https://www.scorebat.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton

    fun provideMatchApi(retrofit: Retrofit): MatchApi {
        return retrofit.create(MatchApi::class.java)
    }


    @Provides
    @Singleton
    fun provideMatchRepository(api: MatchApi): MatchRepository {
        return MatchRepositoryImpl(api)
    }
}