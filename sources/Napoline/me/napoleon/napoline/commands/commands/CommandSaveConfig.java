/*
 * 额
 */

package me.napoleon.napoline.commands.commands;

import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.junk.values.Value;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.utils.player.PlayerUtil;
import net.minecraft.client.gui.GuiScreen;

/**
 * @description: Dev用的保存云配置的
 * @author: QianXia
 * @create: 2020/9/26 21-00
 **/
public class CommandSaveConfig extends Command {
    public CommandSaveConfig() {
        super("SaveConfig", true);
    }

    @Override
    public void run(String[] args) {
        // Get Enabled Mods
        StringBuilder enabledMods = new StringBuilder();
        for (Mod m : ModuleManager.modList) {
            if (!m.getState()) {
                continue;
            }
            enabledMods.append(String.format("%s%s", m.getName(), ","));
        }

        // Mod Values
        StringBuilder modValues = new StringBuilder();
        boolean first = true;
        for(Mod m : ModuleManager.modList){
            for(Value v : m.getValues()){
                if(!first){
                    modValues.append(",");
                }
                if(first){
                    first = false;
                }
                modValues.append(String.format("%s:%s:%s", m.getName(), v.getName(), v.getValue()));
            }
        }

        String result = String.format("%s%s%s", enabledMods.toString(), "ILOVESUPERSKIDDERILOVESUPERSKIDDERILOVESUPERSKIDDER", modValues.toString());
        GuiScreen.setClipboardString(result);
        PlayerUtil.sendMessage("Copied To Clipboard");
    }
}
