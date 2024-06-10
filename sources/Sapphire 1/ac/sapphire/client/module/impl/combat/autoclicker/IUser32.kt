@file:Suppress("FunctionName")

package ac.sapphire.client.module.impl.combat.autoclicker

import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef.*
import com.sun.jna.win32.StdCallLibrary
import kotlin.experimental.and

interface IUser32 : StdCallLibrary {
    companion object {
        val INSTANCE = Native.loadLibrary("User32", IUser32::class.java) as IUser32
    }

    fun FindWindowExA(hwndParent: HWND?, childAfter: HWND?, className: String?, windowName: String?): HWND?

    fun PostMessageA(hWnd: HWND, msg: Int, wParam: WPARAM, lParam: LPARAM): Boolean

    fun GetKeyState(keyCode: Int): Short

    fun GetForegroundWindow(): HWND
}

private val mcPtr: HWND?
    get() = IUser32.INSTANCE.FindWindowExA(null, null, "LWJGL", null)

fun isDown(key: Int) = IUser32.INSTANCE.GetKeyState(key) and 0x8000.toShort() == 0x8000.toShort()

fun isHoldingLeftClick() = IUser32.INSTANCE.GetForegroundWindow() == mcPtr && isDown(0x01)

fun postMessage(msg: Int) {
    if (mcPtr == null) {
        return
    }

    IUser32.INSTANCE.PostMessageA(mcPtr!!, msg, WPARAM(0), LPARAM(0))
}