package ac.sapphire.client.alt

import com.fasterxml.jackson.databind.JsonNode

interface IAlt {
    var email: String
    var password: String

    fun serialize(): JsonNode
}