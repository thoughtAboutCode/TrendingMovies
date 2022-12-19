package bj.fasegiar.trendingmovies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bj.fasegiar.trendingmovies.db.dao.MovieDao
import bj.fasegiar.trendingmovies.db.entity.MovieEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

@Module
@InstallIn(SingletonComponent::class)
object AppDBModule {
    @Provides
    fun db(@ApplicationContext applicationContext: Context): AppDatabase = Room.databaseBuilder(
        applicationContext, AppDatabase::class.java, "app-db"
    ).build()

    @Provides
    fun movieDao(appDB: AppDatabase): MovieDao = appDB.movieDao()
}