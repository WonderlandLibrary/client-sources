package com.leafclient.leaf.utils.client.setting

import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.management.setting.listener.SettingListener

class LeafSettingListener: SettingListener {

    @Suppress("unchecked_cast")
    override fun initialize(setting: Setting<*>) {
        if(setting.value is Boolean) {
            setting as Setting<Boolean>
            setting.contraints[ToggleableContraint::class.java] = ToggleableContraint(setting)
        }
    }

}