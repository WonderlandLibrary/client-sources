/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.Client;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class Teams
extends Module {
    public Teams() {
        super("Teams", new String[0], Type.MISC, "Teams");
    }

    public static boolean isOnSameTeam(Entity entity) {
        if (!Client.instance.getModuleManager().getModuleByClass(Teams.class).isEnabled()) {
            return false;
        }
        Minecraft.getMinecraft();
        if (!Minecraft.thePlayer.getDisplayName().getUnformattedText().startsWith("\u00a7")) return false;
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer.getDisplayName().getUnformattedText().length() <= 2) return false;
        if (entity.getDisplayName().getUnformattedText().length() <= 2) {
            return false;
        }
        Minecraft.getMinecraft();
        if (!Minecraft.thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) return false;
        return true;
    }
}

