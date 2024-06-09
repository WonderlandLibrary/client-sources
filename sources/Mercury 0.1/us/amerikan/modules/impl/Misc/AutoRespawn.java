/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Misc;

import com.darkmagician6.eventapi.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class AutoRespawn
extends Module {
    public AutoRespawn() {
        super("AutoRespawn", "AutoRespawn", 0, Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        Minecraft.thePlayer.respawnPlayer();
    }

    @Override
    public void onDisabled() {
        EventManager.unregister(this);
    }
}

