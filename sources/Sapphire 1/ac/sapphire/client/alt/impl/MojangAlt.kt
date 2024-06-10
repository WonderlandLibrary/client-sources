package ac.sapphire.client.alt.impl

import ac.sapphire.client.alt.IAlt
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode

data class MojangAlt(override var email: String, override var password: String) : IAlt {
    val cracked = password.isBlank()

    override fun serialize(): JsonNode {
        val node = ObjectNode(JsonNodeFactory.instance)

        node.put("type", "mojang")
        node.put("email", email)
        node.put("password", password)

        return node
    }
}