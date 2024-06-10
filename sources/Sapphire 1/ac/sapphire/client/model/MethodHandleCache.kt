package ac.sapphire.client.model

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method

object MethodHandleCache {
    private val HANDLE_MAP: MutableMap<Method, MethodHandle> = HashMap()

    @Throws(IllegalAccessException::class)
    operator fun get(method: Method): MethodHandle {
        method.isAccessible = true

        var handle = HANDLE_MAP[method]
        if (handle == null) {
            handle = MethodHandles.lookup().unreflect(method)
            HANDLE_MAP[method] = handle
        }

        return handle!!
    }
}