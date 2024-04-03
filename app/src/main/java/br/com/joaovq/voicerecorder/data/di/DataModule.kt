package br.com.joaovq.voicerecorder.data.di

import android.content.Context
import br.com.joaovq.voicerecorder.data.storage.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun providesUserRepository(
        @ApplicationContext context: Context
    ): UserRepository = UserRepository(context)
}