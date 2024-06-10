package ac.sapphire.client.module

import ac.sapphire.client.Sapphire
import ac.sapphire.client.bind.IBinding
import ac.sapphire.client.model.IToggleable

abstract class AbstractModule(
    val name: String, val displayName: String, val description: String, val category: ModuleCategory
) : IToggleable {
    val modes: MutableList<AbstractModuleMode<*>> = arrayListOf()

    var isToggled = false
        private set

    private var currentMode: String? = null

    var bind: IBinding? = null
        set(value) {
            if (field != null) {
                Sapphire.bindingManager.bindings.remove(field)
            }

            if (value != null) {
                Sapphire.bindingManager.bindings.add(value)
            }

            field = value
        }

    fun verifyModeState() {
        if (currentMode == null) {
            // if the currentMode is null and the modes list is empty then the state is valid
            if (modes.isEmpty()) {
                return
            }

            // this would happen if you didn't have modes for a module and added them after a config had saved
            currentMode = modes[0].name
        } else {
            // would only happen if you had a mode selected and deleted all modes from the module
            if (modes.isEmpty()) {
                currentMode = null
                return
            }

            // if the currentMode doesn't exist any more just pick the first mode
            val anyMatch = modes.any { currentMode == it.name }
            if (!anyMatch) {
                currentMode = modes[0].name
            }
        }
    }

    fun toggle() {
        val bus = Sapphire.eventBus
        val mode = getCurrentMode()

        val toggled = isToggled
        isToggled = !toggled

        if (toggled) {
            // unsub the current mode and module from the event bus
            bus.unsubscribe(this)
            onDisable()

            if (mode != null) {
                bus.unsubscribe(mode)
                mode.onDisable()
            }
        } else {
            bus.subscribe(this)
            onEnable()

            if (mode != null) {
                bus.subscribe(mode)
                mode.onEnable()
            }
        }
    }

    fun toggle(state: Boolean) {
        if (isToggled && state || !isToggled && !state) {
            return
        }

        toggle()
    }

    /**
     * Called after this module is loaded from a config
     * Verifies the state of the module
     */
    fun postLoad() {
        verifyModeState()
    }

    fun getCurrentMode(): AbstractModuleMode<*>? {
        return if (currentMode == null) {
            null
        } else {
            modes.stream().filter { currentMode == it.name }.findFirst().orElse(null)
        }
    }

    fun setCurrentMode(mode: String) {
        if (modes.isEmpty()) {
            currentMode = null
            return
        }

        val foundMode = modes.firstOrNull { it.name == mode }
        if (foundMode == null) {
            currentMode = modes[0].name
            return
        }

        currentMode = mode
    }
}