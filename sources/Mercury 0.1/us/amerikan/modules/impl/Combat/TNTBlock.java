/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Combat;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class TNTBlock
extends Module {
    public TNTBlock() {
        super("TNTBlock", "TNTBlock", 0, Category.COMBAT);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            for (Object j2 : TNTBlock.mc.theWorld.loadedEntityList) {
                Entity e2 = (Entity)j2;
                if (!(e2 instanceof EntityTNTPrimed)) continue;
                if (Minecraft.thePlayer.isDead) continue;
                if (!((double)Minecraft.thePlayer.getDistanceToEntity(e2) <= 6.0)) continue;
                try {
                    if (Minecraft.thePlayer.getHeldItem().getItem() == null) continue;
                    if (!(Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemSword)) continue;
                    Minecraft.thePlayer.setItemInUse(Minecraft.thePlayer.getHeldItem(), 100);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }
}

