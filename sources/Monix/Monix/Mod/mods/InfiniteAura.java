/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Event.EventTarget;
import Monix.Event.events.EventUpdate;
import Monix.Mod.Mod;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class InfiniteAura
extends Mod {
    private int ticks = 0;

    public InfiniteAura() {
        super("InfiniteAura", "InfiniteAura", 34, Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        ++this.ticks;
        if (this.ticks >= 20 - this.speed()) {
            this.ticks = 0;
            for (Object theObject : InfiniteAura.mc.theWorld.loadedEntityList) {
                EntityLivingBase entity;
                if (!(theObject instanceof EntityLivingBase) || (entity = (EntityLivingBase)theObject) instanceof EntityPlayerSP || InfiniteAura.mc.thePlayer.getDistanceToEntity(entity) > 200.0f) continue;
                if (entity.isInvisible()) break;
                if (!entity.isEntityAlive()) continue;
                InfiniteAura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY, entity.posZ, true));
                if (InfiniteAura.mc.thePlayer.getHeldItem() != null) {
                    InfiniteAura.mc.thePlayer.setItemInUse(InfiniteAura.mc.thePlayer.getHeldItem(), InfiniteAura.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                }
                if (InfiniteAura.mc.thePlayer.isBlocking()) {
                    InfiniteAura.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
                }
                InfiniteAura.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.ATTACK));
                InfiniteAura.mc.thePlayer.swingItem();
                break;
            }
        }
    }

    private int speed() {
        return 18;
    }
}

