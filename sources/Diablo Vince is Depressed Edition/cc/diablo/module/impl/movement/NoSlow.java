/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.movement;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow
extends Module {
    public ModeSetting mode = new ModeSetting("NoSlow mode", "Vanilla", "Vanilla", "Watchdog", "NCP", "Verus");
    public NumberSetting speed = new NumberSetting("Ticks", 6.0, 0.0, 20.0, 1.0);

    public NoSlow() {
        super("NoSlow", "No Slowdown", 0, Category.Movement);
        this.addSettings(this.mode, this.speed);
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        String mode;
        this.setDisplayName("NoSlowdown\u00a77 " + this.mode.getMode() + " | " + this.speed.getVal());
        switch (mode = this.mode.getMode()) {
            case "Vanilla": {
                break;
            }
            case "NCP": {
                if (NoSlow.mc.thePlayer.getHeldItem() == null || !(NoSlow.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) || !NoSlow.mc.gameSettings.keyBindUseItem.isKeyDown()) break;
                if (e.isPre()) {
                    PacketHelper.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    break;
                }
                PacketHelper.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(NoSlow.mc.thePlayer.getHeldItem()));
                break;
            }
            case "Watchdog": {
                if (NoSlow.mc.thePlayer.getHeldItem() == null || !(NoSlow.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) || !NoSlow.mc.gameSettings.keyBindUseItem.isKeyDown() || (double)NoSlow.mc.thePlayer.ticksExisted % this.speed.getVal() != 0.0) break;
                if (e.isPost()) {
                    PacketHelper.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, NoSlow.mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
                    break;
                }
                if (!e.isPre()) break;
                PacketHelper.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
            }
        }
    }
}

