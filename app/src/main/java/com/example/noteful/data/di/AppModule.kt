package com.example.noteful.data.di

import android.app.Application
import androidx.room.Room
import com.example.noteful.data.local.NotesDao
import com.example.noteful.data.local.NotesDatabase
import com.example.noteful.data.repository.RepositoryImpl
import com.example.noteful.domain.repository.NotesRepository
import com.example.noteful.domain.usecases.AddCategoryUseCase
import com.example.noteful.domain.usecases.DeleteCategoryUseCase
import com.example.noteful.domain.usecases.DeleteCategoryWithNotesUseCase
import com.example.noteful.domain.usecases.DeleteNoteUseCase
import com.example.noteful.domain.usecases.UpsertNoteUseCase
import com.example.noteful.domain.usecases.GetAllNotesUseCase
import com.example.noteful.domain.usecases.GetCategoriesUseCase
import com.example.noteful.domain.usecases.GetCategoryWithNotesUseCase
import com.example.noteful.domain.usecases.GetFavoriteNotesUseCase
import com.example.noteful.domain.usecases.GetNoteByIdUseCase
import com.example.noteful.domain.usecases.NotesUseCases
import com.example.noteful.domain.usecases.SearchNoteUseCase
import com.example.noteful.domain.usecases.UpdateCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            context = app, klass = NotesDatabase::class.java, name = "notes_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesNotesDao(notesDatabase: NotesDatabase): NotesDao = notesDatabase.notesDao


    @Provides
    @Singleton
    fun providesNotesRepository(notesDao: NotesDao): NotesRepository = RepositoryImpl(notesDao)


    @Provides
    @Singleton
    fun providesNotesUseCases(notesRepository: NotesRepository): NotesUseCases {
        return NotesUseCases(
            getAllNotesUseCase = GetAllNotesUseCase(notesRepository),
            upsertNoteUseCase = UpsertNoteUseCase(notesRepository),
            addCategoryUseCase = AddCategoryUseCase(notesRepository),
            getCategoryWithNotesUseCase = GetCategoryWithNotesUseCase(notesRepository),
            getCategoriesUseCase = GetCategoriesUseCase(notesRepository),
            getNoteByIdUseCase = GetNoteByIdUseCase(notesRepository),
            searchNoteUseCase = SearchNoteUseCase(notesRepository),
            deleteNoteUseCase = DeleteNoteUseCase(notesRepository),
            updateCategoryUseCase = UpdateCategoryUseCase(notesRepository),
            deleteCategoryUseCase = DeleteCategoryUseCase(notesRepository),
            deleteCategoryWithNotesUseCase = DeleteCategoryWithNotesUseCase(notesRepository),
            getFavoriteNotesUseCase = GetFavoriteNotesUseCase(notesRepository)
        )
    }

}