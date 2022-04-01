package at.fhooe.mc.jetpack.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

const val DB_NAME = "blog_db"

/**
 * database for whole application
 * uses the class Converters for complex types (e.g. OffsetDateTime)
 * @see Database
 * @see TypeConverters
 * @see RoomDatabase
 */
@Database(entities = [BlogPost::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    /**
     * @return data access object for blog post entries
     */
    abstract fun blogPostDao(): BlogPostDao

    companion object {
        /**
         * Singleton prevents multiple instances of database opening at the same time.
         * @see Volatile
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * @param context current context
         * @return database object
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}