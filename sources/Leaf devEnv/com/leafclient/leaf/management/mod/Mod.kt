package com.leafclient.leaf.management.mod

import com.leafclient.leaf.management.contributor
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.SettingContainer
import com.leafclient.leaf.management.utils.Describable
import com.leafclient.leaf.management.utils.Labelable
import com.leafclient.leaf.management.utils.Signable
import net.minecraft.client.Minecraft

/**
 * Represents a [Mod] to the client with all the information required for its organisation.
 */
abstract class Mod @JvmOverloads
constructor(override val label: String, val category: Category, override val description: String = "",
            contributors: Array<String> = emptyArray()
): Labelable, Describable, Signable, SettingContainer() {

    /**
     * Contains Minecraft's instance in a protected field so it can be accessed
     * easily.
     */
    protected val mc: Minecraft
            = Minecraft.getMinecraft()

    /**
     * Contains the various contributors of this mod
     */
    override val contributors = contributors
        .mapNotNull { contributor(it) }

    /**
     * Contains a suffix that can be displayed
     */
    open val suffix: String
        get() = ""

}