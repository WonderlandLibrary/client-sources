/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Mouse;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;

public class BowSpam
extends Module {
    public BowSpam() {
        super("BowSpam", 0, Module.Category.COMBAT);
        this.settings.add(new Settings("Charge", 4.0f, 20.0f, 2.0f, this));
        this.settings.add(new Settings("OnlyTightly", true, (Module)this));
    }

    @Override
    public void onUpdateMovement() {
        if (Minecraft.player.isBowing() && Mouse.isButtonDown(1) && (!this.currentBooleanValue("OnlyTightly") || BowSpam.mc.pointedEntity != null && Minecraft.player.getDistanceToEntity(BowSpam.mc.pointedEntity) <= 2.0f) && (float)Minecraft.player.getItemInUseMaxCount() > this.currentFloatValue("Charge")) {
            BowSpam.mc.playerController.onStoppedUsingItem(Minecraft.player);
            Minecraft.player.swingArm(Minecraft.player.getActiveHand());
            Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Minecraft.player.getHorizontalFacing()));
            Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItem(Minecraft.player.getActiveHand()));
        }
    }
}

