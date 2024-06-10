package ac.sapphire.client.event

import java.lang.invoke.MethodHandle

class EventSubscriber(private val parent: Any, val handle: MethodHandle) {
    operator fun invoke(event: Any?) {
        try {
            handle.invoke(parent, event)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}