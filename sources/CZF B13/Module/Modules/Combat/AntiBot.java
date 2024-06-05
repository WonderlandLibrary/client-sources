package gq.vapu.czfclient.Module.Modules.Combat;

import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.util.Objects;

public class AntiBot extends Module {
    public AntiBot() {
        super("AntiBot", new String[]{"nobot", "botkiller"}, ModuleType.Combat);
        this.setColor(new Color(217, 149, 251).getRGB());
        this.setSuffix("Hypixel");
    }

    public static boolean isServerBot(Entity entity) {
        if (Objects.requireNonNull(ModuleManager.getModuleByClass(AntiBot.class)).isEnabled()) {
            if (Helper.onServer("hypixel")) {
                return !entity.getDisplayName().getFormattedText().startsWith("\u00a7") || entity.isInvisible() || entity.getDisplayName().getFormattedText().toLowerCase().contains("npc");
            }
            if (Helper.onServer("mineplex")) {
                for (Object object : mc.theWorld.playerEntities) {
                    EntityPlayer entityPlayer = (EntityPlayer) object;
                    if (entityPlayer == null || entityPlayer == mc.thePlayer || !entityPlayer.getName().startsWith("Body #") && entityPlayer.getMaxHealth() != 20.0f)
                        continue;
                    return true;
                }
            }
        }
        return false;
    }
}

