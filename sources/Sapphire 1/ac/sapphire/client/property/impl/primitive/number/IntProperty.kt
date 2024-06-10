package ac.sapphire.client.property.impl.primitive.number

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode

class IntProperty(
    override val displayName: String,
    override var value: Int,
    override val isInternal: Boolean,
    range: IntRange
) : AbstractNumberProperty<Int>(displayName, value, isInternal, range.first, range.last) {
    constructor(displayName: String, value: Int, range: IntRange) : this(displayName, value, false, range)

    override fun writeJson(node: ObjectNode) {
        node.put(displayName, value)
    }

    override fun readJson(node: JsonNode) {
        value = node[displayName].asInt()
    }
}