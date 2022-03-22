package at.fhooe.mc.jetpack.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

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
}