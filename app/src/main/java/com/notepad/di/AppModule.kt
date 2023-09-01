package com.notepad.di


import android.content.Context
import com.notepad.data.adapter.LocalDateTimeTypeMoshiAdapter
import com.notepad.data.datasource.local.roomdb.NotesDAO
import com.notepad.data.datasource.local.roomdb.NotesDB
import com.notepad.data.repository.NoteRepoImpl
import com.notepad.data.repository.NoteRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.LocalDateTime
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNotesDB(
        @ApplicationContext appContext: Context
    ): NotesDB = NotesDB.getInstance(appContext)
}

@Provides
fun provideNoteDao(noteDB: NotesDB): NotesDAO {
    return noteDB.noteDAO()
}

@Provides
@Singleton
fun provideNoteRepo(
    noteDAO: NotesDAO,
): NoteRepository {
    return NoteRepoImpl(
        noteDAO
    )
}

@Provides
@Singleton
fun provideMoshi(moshiBuilder: Moshi.Builder): Moshi {
    return moshiBuilder.build()
}

@Provides
@Singleton
fun provideMoshBuilder(): Moshi.Builder {
    return Moshi.Builder()
        .add(LocalDateTime::class.java, LocalDateTimeTypeMoshiAdapter())
        .add(KotlinJsonAdapterFactory())
}