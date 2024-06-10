package ac.sapphire.client.property.impl

import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.property.AbstractProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import java.util.*

class ModuleModeProperty(val module: AbstractModule) : AbstractProperty<String>("mode") {
    override var value: String
        get() = Objects.requireNonNull(module.getCurrentMode())!!.name
        set(value) {
            module.setCurrentMode(value)
        }

    override fun writeJson(node: ObjectNode) {
        node.put("mode", value)
    }

    override fun readJson(node: JsonNode) {
        value = node["mode"].asText()
    }
}