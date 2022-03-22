package at.fhooe.mc.jetpack.room

import androidx.room.Database

@Database(entities = [BlogPost::class], version = 1)
abstract class AppDatabase {
    abstract fun blogPostDao(): BlogPostDao
}