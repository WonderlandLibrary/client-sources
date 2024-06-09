/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump.LongJumpBypass2;

import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.longjump.LongJumpBypass2.LongJumpBypassMode;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class AAC2
extends LongJumpBypassMode {
    public AAC2(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (super.onUpdate(event)) {
            // empty if block
        }
        return true;
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event)) {
            List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, 0.4, 0.0));
            if (AAC2.mc.thePlayer.isAirBorne) {
                AAC2.mc.thePlayer.speedInAir = 0.09f;
            }
            collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
        }
        return true;
    }

    @Override
    public boolean disable() {
        if (super.disable()) {
            AAC2.mc.thePlayer.speedInAir = 0.02f;
        }
        return true;
    }
}

