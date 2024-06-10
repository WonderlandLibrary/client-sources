package ac.sapphire.client.alt

import ac.sapphire.client.alt.impl.MojangAlt
import com.fasterxml.jackson.databind.JsonNode

object AltFactory {
    fun deserialize(node: JsonNode): IAlt {
        return when (node["type"].asText()) {
            "mojang" -> MojangAlt(node["email"].asText(), node["password"].asText())

            else -> throw NullPointerException()
        }
    }
}