package com.leafclient.leaf.management.ui.utils

import kotlin.math.exp
import kotlin.math.pow

/**
 * Contains various [Transition] instances for animations, if you want to see
 * how it could look like, open GeoGebra and observe the function's curve on
 * [0; 1].
 */
object Transitions {

    /**
     * A linear [Transition]
     */
    val LINEAR: Transition = { x -> x }

    /**
     * Starts from slow at 0 and goes faster until it reaches 1
     */
    val PARABOLA: Transition = { x -> x * x }

    /**
     * Same the [PARABOLA] except the transition from slow to fast is even more marked
      */
    val CUBIC: Transition = { x -> x * x * x }

    /**
     * A cool looking animation that is pretty easy to calculate and goes slow at the beginning/end
     * and faster in the middle
     * https://www.reddit.com/r/gamedev/comments/4je7bz/nonbezier_sigmoid_easing_curves/
     */
    val SMOOTH_STEP: Transition = { x ->
        3 * x.pow(3) - 2 * x.pow(2)
    }

    // Utility for the next one
    private val S: (Double, Double) -> Double = { t, a ->
        (1.0 / (1.0 + exp(-a * t))) - 0.5
    }

    /**
     * The following transitions are from:
     * https://hackernoon.com/ease-in-out-the-sigmoid-factory-c5116d8abce9
     */

    /**
     * Sigmoid using k = 2.0
     */
    val LOW_SIGMOID: Transition = { x ->
        (0.5 / S.invoke(1.0, 2.0)) * S.invoke(2.0 * x - 1.0, 2.0) + 0.5
    }

    /**
     * Sigmoid using k = 4.5
     */
    val SIGMOID: Transition = { x ->
        (0.5 / S.invoke(1.0, 3.5)) * S.invoke(2.0 * x - 1.0, 3.5) + 0.5
    }

    /**
     * Sigmoid using k = 5.75
     */
    val SIGMOID_HIGH: Transition = { x ->
        (0.5 / S.invoke(1.0, 5.0)) * S.invoke(2.0 * x - 1.0, 5.0) + 0.5
    }

    /**
     * Sigmoid using k = 7.5
     */
    val SIGMOID_EXTREME: Transition = { x ->
        (0.5 / S.invoke(1.0, 7.5)) * S.invoke(2.0 * x - 1.0, 7.5) + 0.5
    }

}