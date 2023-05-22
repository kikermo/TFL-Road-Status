package org.kikermo.tflroadstatus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kikermo.tflroadstatus.BuildConfig
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    @Provides
    @Named("app_key")
    internal fun providesAppKey() = BuildConfig.APP_KEY
}
