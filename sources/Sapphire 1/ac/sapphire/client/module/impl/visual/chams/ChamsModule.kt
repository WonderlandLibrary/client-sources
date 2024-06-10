package ac.sapphire.client.module.impl.visual.chams

import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory

class ChamsModule : AbstractModule("chams", "Chams", "", ModuleCategory.VISUALS) {
    init {
        modes.add(NormalChamsMode(this))
        modes.add(ColoredChamsMode(this))
    }
}