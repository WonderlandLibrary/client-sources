package ac.sapphire.client.property.impl.primitive.number

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode

class FloatProperty(
    override val displayName: String,
    override var value: Float,
    override val isInternal: Boolean,
    range: ClosedRange<Float>
) : AbstractNumberProperty<Float>(displayName, value, isInternal, range.start, range.endInclusive) {
    constructor(displayName: String, value: Float, range: ClosedRange<Float>) : this(displayName, value, false, range)

    override fun writeJson(node: ObjectNode) {
        node.put(displayName, value)
    }

    override fun readJson(node: JsonNode) {
        value = node[displayName].floatValue()
    }
}