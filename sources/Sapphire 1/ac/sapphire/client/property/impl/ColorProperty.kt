package ac.sapphire.client.property.impl

import ac.sapphire.client.property.AbstractProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import java.awt.Color
import kotlin.reflect.KProperty

class ColorProperty @JvmOverloads constructor(
    name: String, override var value: Color, var rainbow: Boolean = false, internal: Boolean = false
) : AbstractProperty<Color>(name, internal) {

    override fun writeJson(node: ObjectNode) {
        node.put(displayName, value.rgb)
    }

    override fun readJson(node: JsonNode) {
        value = Color(node[displayName].asInt())
    }

    companion object {
        private var hue = 0F
    }

    fun getRainbow(): Color {
        hue += 0.7F
        if (hue > 255) {
            hue = 0F
        }

        return Color.getHSBColor(hue / 255F, 0.45F, 0.7F)
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): Color {
        if (rainbow) {
            return getRainbow()
        }

        return value
    }
}