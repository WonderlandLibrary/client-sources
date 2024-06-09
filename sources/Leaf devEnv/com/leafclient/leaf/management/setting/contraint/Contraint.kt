package com.leafclient.leaf.management.setting.contraint

import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.management.utils.Labelable

/**
 * [Contraint] are invoked when the value of a [Setting]
 */
abstract class Contraint<T: Any>(
    val setting: Setting<T>,
): Labelable
{

    override val label: String
        get() = setting.label

    /**
     * Method invoked by the [Setting] class when the value is changed
     */
    abstract fun constrict(futureValue: T): T

}