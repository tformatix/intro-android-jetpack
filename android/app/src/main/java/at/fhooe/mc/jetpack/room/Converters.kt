package at.fhooe.mc.jetpack.room

import androidx.room.TypeConverter
import java.time.OffsetDateTime

/**
 * converts complex data types to savable data types or vice versa
 */
class Converters {

    /**
     * @param value DateTime string
     * @return OffsetDateTime of string
     * @see TypeConverter
     */
    @TypeConverter
    fun stringToOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            OffsetDateTime.parse(it)
        }
    }

    /**
     * @param date OffsetDateTime string
     * @return String of DateTime
     * @see TypeConverter
     */
    @TypeConverter
    fun offsetDateTimeToString(date: OffsetDateTime?): String? {
        return date?.toString()
    }

}