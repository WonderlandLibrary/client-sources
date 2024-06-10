package ac.sapphire.client.property

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import kotlin.reflect.KProperty

abstract class AbstractProperty<T>(override val displayName: String, override val isInternal: Boolean) : IProperty {
    constructor(name: String) : this(name, false)

    final override var shouldDisplay: () -> Boolean = { true }

    override fun displayIf(filter: () -> Boolean): AbstractProperty<T> {
        shouldDisplay = filter
        return this
    }

    abstract var value: T

    abstract fun writeJson(node: ObjectNode)

    abstract fun readJson(node: JsonNode)

    open operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}