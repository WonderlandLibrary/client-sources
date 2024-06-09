package com.leafclient.leaf.utils.client.setting

import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.management.setting.contraint.Contraint
import com.leafclient.leaf.management.utils.Toggleable

class ToggleableContraint(setting: Setting<Boolean>): Contraint<Boolean>(setting), Toggleable {

    override var isRunning: Boolean
        get() = setting.value
        set(value) {
            setting.value = value
        }

    override fun constrict(futureValue: Boolean)
        = futureValue

}