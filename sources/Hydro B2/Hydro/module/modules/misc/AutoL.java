package Hydro.module.modules.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.ChatUtils;
import Hydro.util.Timer;

public class AutoL extends Module {

    private final Timer timer = new Timer();

    public AutoL() {
        super("AutoL", 0, true, Category.MISC, "Automatically messages the user that your aura is attacking");
        Client.settingsManager.rSetting(new Setting("AutoLDelay", "Delay", this, 0.2, 0.1, 10, false));
        ArrayList<String> options = new ArrayList<>();
        options.add("/tell");
        options.add("/message");
        options.add("/msg");
        options.add("/pm");
        options.add("/dm");
        Client.settingsManager.rSetting(new Setting("AutoLMode", "Mode", this, "/message", options));
    }

    @Override
    public void onUpdate() {
        if(!Client.moduleManager.getModuleByName("Aura").isEnabled()){
            return;
        }
        List<Entity> targets = Minecraft.theWorld.loadedEntityList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < Client.settingsManager.getSettingByName("AuraRange").getValDouble() && entity != mc.thePlayer).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer)));
        targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());

        if(timer.hasTimeElapsed((long) Client.settingsManager.getSettingByName("AutoLDelay").getValDouble() * 1000, true)){
            if(Client.moduleManager.getModuleByName("Aura").isEnabled()){
                if(!targets.isEmpty()){
                    if(!(targets.get(0).getName().equalsIgnoreCase("") || targets.get(0).getName().equalsIgnoreCase(" ") || targets.get(0).getName().equalsIgnoreCase("NPC") || targets.get(0).getName().equalsIgnoreCase("[NPC]") || targets.get(0).getName().equalsIgnoreCase("BOT") || targets.get(0).getName().equalsIgnoreCase("[BOT]") || targets.get(0).isInvisibleToPlayer(mc.thePlayer))){
                        ChatUtils.sendMessage(Client.settingsManager.getSettingByName("AutoLMode").getValString() + " " + targets.get(0).getName() + " L");
                    }
                }
            }
        }
        if(!targets.isEmpty()) {
            if (!(targets.get(0).getName().equalsIgnoreCase("") || targets.get(0).getName().equalsIgnoreCase(" ") || targets.get(0).getName().equalsIgnoreCase("NPC") || targets.get(0).getName().equalsIgnoreCase("[NPC]") || targets.get(0).getName().equalsIgnoreCase("BOT") || targets.get(0).getName().equalsIgnoreCase("[BOT]") || targets.get(0).isInvisibleToPlayer(mc.thePlayer))) {
                ChatUtils.sendMessage("/r L");
            }
        }
    }
}
