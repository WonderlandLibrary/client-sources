package ac.sapphire.client.property

import java.util.*

object PropertyFactory {
    operator fun get(parent: Any): Deque<IProperty> {
        val properties = ArrayDeque<IProperty>()

        for (field in parent.javaClass.declaredFields) {
            if (!IProperty::class.java.isAssignableFrom(field.type)) {
                continue
            }

            field.isAccessible = true

            try {
                val property = field[parent] as IProperty
                properties.addLast(property)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }

        return properties
    }
}