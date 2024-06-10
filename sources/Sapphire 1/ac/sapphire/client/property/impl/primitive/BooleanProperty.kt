package ac.sapphire.client.property.impl.primitive

import ac.sapphire.client.property.AbstractProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode

class BooleanProperty @JvmOverloads constructor(name: String, override var value: Boolean, internal: Boolean = false) :
    AbstractProperty<Boolean>(name, internal) {

    override fun writeJson(node: ObjectNode) {
        node.put(displayName, value)
    }

    override fun readJson(node: JsonNode) {
        value = node[displayName].asBoolean()
    }
}