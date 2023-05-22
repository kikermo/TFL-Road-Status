package org.kikermo.tflroadstatus.coroutines

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kikermo.tflroadstatus.domain.coroutines.CoroutinesContextProvider

@Module
@InstallIn(SingletonComponent::class)
internal interface CoroutinesModule {
    @Binds
    fun bindsContextProvider(contextProvider: AppCoroutinesContextProvider): CoroutinesContextProvider
}
