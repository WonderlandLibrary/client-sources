package com.leafclient.leaf

import com.leafclient.leaf.management.Contributor
import com.leafclient.leaf.management.event.EventManager
import com.leafclient.leaf.extension.mutableSession
import com.leafclient.leaf.management.file.FileManager
import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.mod.ModManager
import com.leafclient.leaf.utils.AccountUtils
import com.leafclient.leaf.utils.client.setting.LeafSettingListener
import net.minecraft.client.Minecraft
import kotlin.system.measureTimeMillis

object Leaf {

    init {
        Setting.listener = LeafSettingListener()
        EventManager
        ModManager
        FileManager
        println("Loaded Leaf!")
    }

    /**
     * Contains the current client name
     */
    const val clientName =  "Leaf"

    /**
     * Contains the current client version
     */
    const val clientVersion = "devEnv"

    /**
     * Contains the person who contributed to Leaf, hall of fame dud
     */
    val contributors = arrayOf(
            Contributor("Shyrogan", "17 years old retard", arrayOf("https://twitter.com/Shyrogan")),
            Contributor("auto", "he made a lot of stuff", arrayOf("https://twitter.com/skidrr")),
            Contributor("waddle", "maths guy", arrayOf("")),
            Contributor("Tigreax", "maths gay", arrayOf("")),
            Contributor("Milse113", "Maths guy but actually also auth sorta", arrayOf("https://github.com/milse113"))
        )

}
