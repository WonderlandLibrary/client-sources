package com.cout970.fira.modules

import com.cout970.fira.Config
import net.minecraft.client.entity.EntityPlayerSP

object YawLock {

    fun hud(): String = "YawLock"

    fun onTurn(entity: Any?) {
        if (entity is EntityPlayerSP) {
            onTick(entity)
        }
    }

    fun onTick(p: EntityPlayerSP) {
        if (!Config.YawLock.enable) return
        if (Config.YawLock.onlyFlying && !p.isElytraFlying) return
        if (Config.YawLock.ignoreYawLock.isActive()) return

        val mod = p.rotationYaw % 360f
        val pos = if (mod < 0) mod + 360f else mod
        val region = (pos / 22.5).toInt()
        val section = ((region + 1) / 2) % 8

        val newYaw = section * 45f

        p.rotationYaw = newYaw
        p.prevRotationYaw = newYaw
        p.renderYawOffset = newYaw
    }
}