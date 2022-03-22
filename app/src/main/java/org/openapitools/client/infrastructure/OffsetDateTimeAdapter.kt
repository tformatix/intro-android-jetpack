package org.openapitools.client.infrastructure

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * CHANGED AND NOT GENERATED!!!
 */
class OffsetDateTimeAdapter {

    @ToJson
    fun toJson(value: OffsetDateTime): String {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(
            value.atZoneSameInstant(ZoneOffset.UTC) // move back to UTC
        )
    }

    @FromJson
    fun fromJson(value: String): OffsetDateTime {
        return OffsetDateTime
            .parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .atZoneSameInstant(ZoneId.systemDefault()) // move to device's time zone
            .toOffsetDateTime()
    }

}
