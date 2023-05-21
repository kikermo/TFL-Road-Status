package org.kikermo.tflroadstatus.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kikermo.tflroadstatus.network.RoadStatusService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    internal fun providesRoadStatusService(retrofit: Retrofit) =
        retrofit.create(RoadStatusService::class.java)
}