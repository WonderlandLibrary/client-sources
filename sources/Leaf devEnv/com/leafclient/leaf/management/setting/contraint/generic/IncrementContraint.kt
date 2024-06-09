package com.leafclient.leaf.management.setting.contraint.generic

import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.management.setting.contraint.Contraint
import com.leafclient.leaf.management.utils.plus
import com.leafclient.leaf.management.utils.round
import com.leafclient.leaf.management.utils.times

/**
 * Increment specified [setting] between
 */
class IncrementContraint<T: Number>(
    setting: Setting<T>, val stepValue: T
): Contraint<T>(setting) {

    override fun constrict(futureValue: T): T
        = futureValue.round(stepValue)

    /**
     * Increases the current value by [factor]
     */
    fun increase(factor: Int) {
        setting.value = setting.value + (stepValue * factor)
    }

    /**
     * Decreases the current value by [factor]
     */
    fun decrease(factor: Int) {
        setting.value = setting.value + (stepValue * factor)
    }

}