package ac.sapphire.client.event

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.model.MethodHandleCache
import java.lang.reflect.Method
import java.util.*

class EventBus {
    private val subscriptions: MutableMap<Class<*>, MutableSet<EventSubscriber>> =
        Collections.synchronizedMap(hashMapOf())

    private fun isInvalid(method: Method): Boolean {
        return !method.isAnnotationPresent(Subscribe::class.java) || method.returnType != Void.TYPE || method.parameterCount != 1
    }

    fun subscribe(parent: Any) {
        for (method in parent.javaClass.declaredMethods) {
            if (isInvalid(method)) {
                continue
            }

            val eventClass = method.parameterTypes[0]
            subscriptions.compute(
                eventClass
            ) { _: Class<*>, _subscribers: MutableSet<EventSubscriber>? ->
                val subscribers = _subscribers ?: hashSetOf()

                try {
                    subscribers.add(EventSubscriber(parent, MethodHandleCache.get(method)))
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                subscribers
            }
        }
    }

    fun unsubscribe(parent: Any) {
        for (method in parent.javaClass.declaredMethods) {
            if (isInvalid(method)) {
                continue
            }

            val eventClass = method.parameterTypes[0]
            subscriptions.computeIfPresent(eventClass) { _: Class<*>, subscribers: MutableSet<EventSubscriber> ->
                try {
                    val handle = MethodHandleCache.get(method)
                    subscribers.removeIf { subscriber: EventSubscriber -> subscriber.handle.type() === handle.type() }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                subscribers
            }
        }
    }

    fun <T : Any> post(event: T): T {
        val subscribers = subscriptions[event::class.java] ?: return event
        for (subscriber in subscribers) {
            try {
                subscriber.invoke(event)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return event
    }
}