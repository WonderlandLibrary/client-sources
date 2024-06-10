package ac.sapphire.client.property

import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.AbstractModuleMode
import ac.sapphire.client.property.impl.ModuleModeProperty
import java.util.*

class PropertyManager {
    private val properties: MutableMap<String, Deque<IProperty>> = HashMap()

    fun register(module: AbstractModule) {
        val moduleSettings = PropertyFactory[module]
        if (module.modes.isNotEmpty()) {
            moduleSettings.addFirst(ModuleModeProperty(module))
        }

        properties[module.name] = moduleSettings
    }

    fun register(mode: AbstractModuleMode<*>) {
        properties[mode.parent.name + ":" + mode.name] = PropertyFactory[mode]
    }

    fun getProperties(module: AbstractModule): Deque<IProperty> {
        return properties.getOrDefault(module.name, LinkedList())
    }

    fun getProperties(mode: AbstractModuleMode<*>): Deque<IProperty> {
        return properties.getOrDefault(mode.parent.name + ":" + mode.name, LinkedList())
    }
}