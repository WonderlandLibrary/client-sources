package ac.sapphire.client.ext

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import kotlin.properties.ReadOnlyProperty

val mc: Minecraft = Minecraft.getMinecraft()

private fun playerGetter(): ReadOnlyProperty<Any?, EntityPlayerSP> = ReadOnlyProperty { _: Any?, _ -> mc.thePlayer }

val player: EntityPlayerSP by playerGetter()