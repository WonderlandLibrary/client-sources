package com.leafclient.leaf.management.setting.contraint.generic

import com.leafclient.leaf.management.event.EventManager
import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.management.setting.contraint.Contraint

class ChoiceContraint<T: Any>(
    setting: Setting<T>, defaultChoices: Array<out T>
) : Contraint<T>(setting) {

    /**
     * Contains all the choices allowed by this [Contraint].
     */
    val choices = if(defaultChoices.contains(setting.value))
        defaultChoices.toList()
    else
        mutableListOf(setting.value).also { it.addAll(defaultChoices) }

    /**
     * Returns the next choice compared to the current value
     */
    val nextChoice: T
        get() {
            val index = choices.indexOf(setting.value) + 1
            return choices[index.coerceIn(choices.indices)]
        }

    /**
     * Returns the previous choice compared to the current value
     */
    val previousChoice: T
        get() {
            val index = choices.indexOf(setting.value) - 1
            return choices[index.coerceIn(choices.indices)]
        }

    /**
     * Constricts the values to the [choices].
     */
    override fun constrict(futureValue: T): T {
        if(choices.contains(futureValue)) {
            EventManager.unregister(setting.value)
            EventManager.register(futureValue)
            return futureValue
        }
        return setting.value
    }

}