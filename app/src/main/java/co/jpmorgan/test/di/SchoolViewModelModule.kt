package co.jpmorgan.test.di

import androidx.lifecycle.SavedStateHandle
import co.jpmorgan.test.repositories.SchoolApi
import co.jpmorgan.test.repositories.SchoolRepository
import co.jpmorgan.test.repositories.SchoolRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object SchoolViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideRepo(schoolApi: SchoolApi) : SchoolRepository = SchoolRepositoryImp(schoolApi)
}