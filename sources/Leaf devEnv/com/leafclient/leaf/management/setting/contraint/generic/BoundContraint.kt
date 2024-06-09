package com.leafclient.leaf.management.setting.contraint.generic

import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.management.setting.contraint.Contraint
import com.leafclient.leaf.management.utils.compareTo

/**
 * Bounds specified [setting] between two numbers.
 */
class BoundContraint<T: Number>(
    setting: Setting<T>, val minimum: T, val maximum: T
): Contraint<T>(setting) {

    /**
     * Checks whether the [futureValue] is between [maximum] and [minimum]
     */
    override fun constrict(futureValue: T): T {
        return when {
            futureValue > maximum -> maximum
            futureValue < minimum -> minimum
            else -> futureValue
        }
    }

}