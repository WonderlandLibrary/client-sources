package com.leafclient.leaf.mod

import com.leafclient.leaf.management.mod.Mod

inline fun <reified T: Mod> modInstance()
    = ModManager.get<T>()