package com.leafclient.leaf.management.setting.listener

import com.leafclient.leaf.management.setting.Setting

interface SettingListener {

    /**
     * Method invoked by a [Setting] after initializing
     */
    fun initialize(setting: Setting<*>)
    
}