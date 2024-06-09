/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Misc;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import us.amerikan.events.EventPreMotion;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class TPBedDestroyer
extends Module {
    public TPBedDestroyer() {
        super("TPBedDestroyer", "TPBedDestroyer", 0, Category.MISC);
    }

    @EventTarget
    public void onPre(EventPreMotion event) {
        int id2 = Block.getIdFromBlock(TPBedDestroyer.mc.theWorld.getBlockState(TPBedDestroyer.mc.objectMouseOver.func_178782_a()).getBlock());
        if (id2 == 26 && TPBedDestroyer.mc.gameSettings.keyBindAttack.pressed) {
            Minecraft.thePlayer.setSprinting(false);
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(TPBedDestroyer.mc.objectMouseOver.func_178782_a().getX(), (double)TPBedDestroyer.mc.objectMouseOver.func_178782_a().getY() + 1.0, TPBedDestroyer.mc.objectMouseOver.func_178782_a().getZ(), Minecraft.thePlayer.onGround));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(TPBedDestroyer.mc.objectMouseOver.func_178782_a().getX(), (double)TPBedDestroyer.mc.objectMouseOver.func_178782_a().getY() + 1.0, TPBedDestroyer.mc.objectMouseOver.func_178782_a().getZ(), Minecraft.thePlayer.onGround));
            event.pitch = 89.6f;
            this.destroyBlock(TPBedDestroyer.mc.objectMouseOver.func_178782_a());
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
            Minecraft.thePlayer.setSprinting(false);
        }
    }

    private void destroyBlock(BlockPos pos) {
        Minecraft.thePlayer.swingItem();
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
}

