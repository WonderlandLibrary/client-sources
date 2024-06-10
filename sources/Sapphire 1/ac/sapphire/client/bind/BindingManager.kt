package ac.sapphire.client.bind

import ac.sapphire.client.bind.impl.keyboard.AbstractKeyboardBinding
import ac.sapphire.client.bind.impl.mouse.AbstractMouseBinding
import ac.sapphire.client.event.EventBus
import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.input.KeyboardInputEvent
import ac.sapphire.client.event.events.input.MouseInputEvent

class BindingManager(eventBus: EventBus) {
    val bindings = hashSetOf<IBinding>()

    init {
        eventBus.subscribe(this)
    }

    @Subscribe
    private fun onKeyboardInput(event: KeyboardInputEvent) {
        bindings.filterIsInstance<AbstractKeyboardBinding>().forEach {
            if (it.keyCode == event.key) {
                it.onPress()
            }
        }
    }

    @Subscribe
    private fun onMouseInput(event: MouseInputEvent) {
        bindings.filterIsInstance<AbstractMouseBinding>().forEach {
            if (it.button == event.button) {
                it.onPress()
            }
        }
    }
}