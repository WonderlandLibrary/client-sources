package com.leafclient.leaf.command

import com.leafclient.commando.context.CommandContext
import com.leafclient.commando.executor.CommandExecutor
import com.leafclient.leaf.management.mod.Mod
import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.management.setting.contraint.contraint
import com.leafclient.leaf.management.setting.contraint.generic.ChoiceContraint
import com.leafclient.leaf.management.utils.Labelable
import com.leafclient.leaf.utils.client.printError
import com.leafclient.leaf.utils.client.printInfo
import net.minecraft.client.Minecraft

/**
 * A bad command for a bad library (not supposed to work with kotlin yet
 * this is the easy solution when we don't have hud)
 */
class ModCommand(val mod: Mod): CommandExecutor<Minecraft> {

    override fun execute(mc: Minecraft, context: CommandContext) {
        val settingName = context.arg<String>("settingName")
        if(settingName == null) {
            sendHelp()
            return
        }

        val settingValue = context.arg<String>("settingValue")
        if(settingValue == null) {
            printError("§cWtfrick tell me what u want")
            return
        }

        val setting = mod.settings
            .firstOrNull { it.label.equals(settingName, true) } as Setting<Any>?

        if(setting == null) {
            sendHelp()
            return
        }

        val choiceContraint = setting.contraint<ChoiceContraint<*>>()
        if(choiceContraint == null || Enum::class.java.isAssignableFrom(setting.value.javaClass)) {
            val parsedValue = context.parse(setting.value.javaClass, settingValue).orElse(null)
            if(parsedValue == null) {
                printError("§cWtfrick is $settingValue ?????")
                return
            }

            setting.value = parsedValue
        } else {
            val value = context.parse(Integer::class.java, settingValue).orElse(null)
            if(value == null)
                setting.value = choiceContraint.nextChoice
            else
                setting.value = choiceContraint.choices[value.toInt().coerceIn(choiceContraint.choices.indices)]
        }
        val displayedValue = if(setting.value is Labelable) (setting.value as Labelable).label else setting.value
        printInfo("§b${setting.label} §7has been set to §b$displayedValue §7!")
    }

    private fun sendHelp() {
        printError("Uh oh, stinky... Poooop")
        printInfo(mod.settings.joinToString("§7, ") { "§b${it.label}" })
    }

     companion object {
         fun `for`(mod: Mod) = CommandManager
             .newCommand(mod.label)
             .description(mod.description)
             .optionalArgument("settingName", String::class.java)
             .optionalArgument("settingValue", String::class.java)
             .executor(ModCommand(mod))
             .done()
     }

}