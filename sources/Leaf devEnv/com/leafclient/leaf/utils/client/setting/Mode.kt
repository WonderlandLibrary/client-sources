package com.leafclient.leaf.utils.client.setting

import com.leafclient.leaf.management.utils.Labelable
import net.minecraft.client.Minecraft

/**
 * A super class used by kill aura modes
 */
abstract class Mode(override val label: String): Labelable {

    protected val mc = Minecraft.getMinecraft()

}