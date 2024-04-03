package br.com.joaovq.voicerecorder.home.di

import br.com.joaovq.voicerecorder.data.service.AudioPlayer
import br.com.joaovq.voicerecorder.home.data.AndroidMediaPlayer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeDataModule {
    @Binds
    abstract fun bindsAndroidPlayer(
        androidMediaPlayer: AndroidMediaPlayer
    ): AudioPlayer
}