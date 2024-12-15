package com.alan.clients.script.api.wrapper.impl;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.script.api.wrapper.ScriptWrapper;
import com.alan.clients.script.api.wrapper.impl.vector.ScriptVector3d;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import net.minecraft.util.BlockPos;

public class ScriptBlockPos extends ScriptWrapper<BlockPos> {

    public ScriptBlockPos(final BlockPos wrapped) {
        super(wrapped);
    }

    public ScriptVector3d getPosition() {
        return new ScriptVector3d(this.wrapped.getX(), this.wrapped.getY(), this.wrapped.getZ());
    }

    public float getHardness() {
        return SlotUtil.getPlayerRelativeBlockHardness(MC.thePlayer, MC.theWorld, this.wrapped, Client.INSTANCE.getComponentManager().get(Slot.class).getItemIndex());
    }

    public float getHardness(int hotBarSlot) {
        return SlotUtil.getPlayerRelativeBlockHardness(MC.thePlayer, MC.theWorld, this.wrapped, hotBarSlot);
    }

    public ScriptBlock getBlock() {
        return new ScriptBlock(PlayerUtil.block(this.wrapped));
    }
}
