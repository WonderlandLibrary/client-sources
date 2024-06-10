package ac.sapphire.client.module

import ac.sapphire.client.module.impl.combat.AntiBotModule
import ac.sapphire.client.module.impl.combat.ReachModule
import ac.sapphire.client.module.impl.combat.VelocityModule
import ac.sapphire.client.module.impl.combat.aim.AimAssistModule
import ac.sapphire.client.module.impl.combat.autoclicker.AutoClickerModule
import ac.sapphire.client.module.impl.movement.FastStopModule
import ac.sapphire.client.module.impl.movement.QuickAccelModule
import ac.sapphire.client.module.impl.player.FastPlaceModule
import ac.sapphire.client.module.impl.player.SafeWalkModule
import ac.sapphire.client.module.impl.player.TimerModule
import ac.sapphire.client.module.impl.visual.*
import ac.sapphire.client.module.impl.visual.chams.ChamsModule
import ac.sapphire.client.module.impl.visual.hud.HUDModule
import ac.sapphire.client.property.PropertyManager
import kotlin.contracts.ExperimentalContracts

class ModuleManager(private val propertyManager: PropertyManager) {
    val moduleMap: MutableMap<String, AbstractModule> = hashMapOf()

    private fun register(module: AbstractModule) {
        propertyManager.register(module)

        module.modes.forEach { propertyManager.register(it) }

        moduleMap[module.name] = module
    }

    @ExperimentalContracts
    fun registerModules() {
        register(HUDModule())
        register(TimerModule())
        register(AutoClickerModule())
        register(ReachModule())
        register(ChamsModule())
        register(AimAssistModule())
        register(VelocityModule())
        register(ClickUIModule())
        register(FastPlaceModule())
        register(ESPModule())
        register(TracersModule())
        register(SafeWalkModule())
        register(FastStopModule())
        register(ChestESPModule())
        register(AntiBotModule())
        register(QuickAccelModule())
        register(NametagsModule())

        // temporary until we have a fully fledged configuration system
        moduleMap.values.forEach { it.postLoad() }
    }

    operator fun <T : AbstractModule> get(moduleClass: Class<T>): T {
        for (value in moduleMap.values) {
            if (value.javaClass == moduleClass) {
                @Suppress("UNCHECKED_CAST") return value as T
            }
        }

        throw NullPointerException()
    }

    fun getModulesInCategory(category: ModuleCategory): List<AbstractModule> {
        return moduleMap.values.filter { it.category == category }
    }
}