package org.zibble.minestom.io

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.UUID

class UUIDTypeAdaptor : TypeAdapter<UUID>() {

    companion object {
        fun fromUUID(uuid: UUID): String {
            return uuid.toString().replace("-", "")
        }

        fun fromString(uuid: String): UUID {
            return UUID.fromString(uuid.replace("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})".toRegex(), "$1-$2-$3-$4-$5"))
        }
    }

    override fun write(out: JsonWriter, value: UUID) {
        out.value(fromUUID(value))
    }

    override fun read(`in`: JsonReader): UUID {
        return fromString(`in`.nextString())
    }
}