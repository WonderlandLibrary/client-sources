/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPostUpdate;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.UTILS.world.MovementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow
extends Module {
    private static final C07PacketPlayerDigging PLAYER_DIGGING = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
    private static final C08PacketPlayerBlockPlacement BLOCK_PLACEMENT = new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f);
    private static final Mode<Enum> mode = new Mode("Modes", "Modes", (Enum[])Modes.values(), (Enum)Modes.Vanilla);

    public NoSlow() {
        super("NoSlow", new String[0], Type.MISC, "Allow's to run while blocking");
        this.addValues(mode);
    }

    @EventHandler
    public void a(EventPreUpdate e) {
        this.setSuffix(mode.getModeAsString());
        if (!MovementUtil.isMoving()) return;
        if (Minecraft.thePlayer.getHeldItem() == null) return;
        if (!(Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;
        if (!Minecraft.thePlayer.isBlocking()) return;
        switch (mode.getModeAsString()) {
            case "NCP": {
                mc.getNetHandler().getNetworkManager().sendPacket(PLAYER_DIGGING);
                return;
            }
        }
    }

    @EventHandler
    public void a(EventPostUpdate e) {
        if (!MovementUtil.isMoving()) return;
        if (Minecraft.thePlayer.getHeldItem() == null) return;
        if (!(Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;
        if (!Minecraft.thePlayer.isBlocking()) return;
        switch (mode.getModeAsString()) {
            case "NCP": {
                mc.getNetHandler().getNetworkManager().sendPacket(BLOCK_PLACEMENT);
                return;
            }
            case "AAC5": {
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, Minecraft.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                return;
            }
        }
    }

    static enum Modes {
        Vanilla,
        NCP,
        AAC5;

    }
}

