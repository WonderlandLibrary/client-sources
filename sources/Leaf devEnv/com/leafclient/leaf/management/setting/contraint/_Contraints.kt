package com.leafclient.leaf.management.setting.contraint

import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.management.setting.contraint.generic.BoundContraint
import com.leafclient.leaf.management.setting.contraint.generic.ChoiceContraint
import com.leafclient.leaf.management.setting.contraint.generic.IncrementContraint

inline fun <reified C: Contraint<*>> Setting<*>.contraint() =
    contraints[C::class.java] as C?

/**
 * Bounds this [Setting] using the [BoundContraint]
 */
fun <T: Number> Setting<T>.bound(minimum: T, maximum: T)
        = BoundContraint(this, minimum, maximum).also { this.contraints[it.javaClass] = it }

/**
 * Bounds this [Setting] using the [BoundContraint]
 */
fun <T: Number> Setting<T>.increment(stepValue: T)
        = IncrementContraint(this, stepValue).also { this.contraints[it.javaClass] = it }

/**
 * Bounds this [Setting] using the [BoundContraint]
 */
fun <T: Any> Setting<T>.choice(vararg choicesValues: T)
        = ChoiceContraint(this, choicesValues).also { this.contraints[it.javaClass] = it }