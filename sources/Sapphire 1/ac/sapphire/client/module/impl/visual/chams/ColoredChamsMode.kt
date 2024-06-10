package ac.sapphire.client.module.impl.visual.chams

import ac.sapphire.client.module.AbstractModuleMode
import ac.sapphire.client.property.impl.ColorProperty
import ac.sapphire.client.property.impl.primitive.BooleanProperty
import java.awt.Color

class ColoredChamsMode(parent: ChamsModule) : AbstractModuleMode<ChamsModule>(parent, "colored", "Colored") {
    val material by BooleanProperty("Material", false)
    val visibleColor by ColorProperty("Visible", Color.GREEN)
    val hiddenColor by ColorProperty("Hidden", Color.RED)
}