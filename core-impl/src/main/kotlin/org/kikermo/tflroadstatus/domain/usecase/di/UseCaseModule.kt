package org.kikermo.tflroadstatus.domain.usecase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kikermo.tflroadstatus.domain.usecase.GetRoadStatusUseCase
import org.kikermo.tflroadstatus.domain.usecase.GetRoadStatusUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCaseModule {
    @Binds
    fun bindsGetRoadUseCase(contextProvider: GetRoadStatusUseCaseImpl): GetRoadStatusUseCase
}
