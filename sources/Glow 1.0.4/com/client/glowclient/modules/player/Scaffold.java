package com.client.glowclient.modules.player;

import net.minecraft.util.math.*;
import com.client.glowclient.modules.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.client.glowclient.sponge.mixinutils.*;
import com.client.glowclient.events.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import com.client.glowclient.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;

public class Scaffold extends ModuleContainer
{
    private static boolean A;
    public static BooleanValue tower;
    public static nB mode;
    
    public Scaffold() {
        super(Category.PLAYER, "Scaffold", false, -1, "Placed blocks under player when walking");
    }
    
    private void M(final BlockPos blockPos) {
        try {
            final Vec3d vec3d = new Vec3d(Wrapper.mc.player.posX, Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight(), Wrapper.mc.player.posZ);
            EnumFacing[] values;
            for (int length = (values = EnumFacing.values()).length, i = 0; i < length; ++i) {
                final EnumFacing enumFacing = values[i];
                final BlockPos offset = blockPos.offset(enumFacing);
                final EnumFacing opposite = enumFacing.getOpposite();
                final Vec3d vec3d2 = vec3d;
                final Vec3d vec3d3 = new Vec3d((Vec3i)blockPos);
                final double n = 0.5;
                final double squareDistanceTo = vec3d2.squareDistanceTo(vec3d3.add(n, n, n));
                final Vec3d vec3d4 = vec3d;
                final Vec3d vec3d5 = new Vec3d((Vec3i)offset);
                final double n2 = 0.5;
                if (squareDistanceTo < vec3d4.squareDistanceTo(vec3d5.add(n2, n2, n2)) && pB.M(offset)) {
                    final Vec3d vec3d6 = vec3d;
                    final Vec3d vec3d7 = new Vec3d((Vec3i)offset);
                    final double n3 = 0.5;
                    final Vec3d add;
                    if (vec3d6.squareDistanceTo(add = vec3d7.add(n3, n3, n3).add(new Vec3d(opposite.getDirectionVec()).scale(0.5))) <= 18.0625) {
                        final BlockPos blockPos2 = offset;
                        final EnumFacing enumFacing2 = opposite;
                        y.M(add, this);
                        ControllerUtils.rightClickBlock(blockPos2, enumFacing2, add);
                        Ob.M().sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        Wrapper.mc.rightClickDelayTimer = 4;
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void E() {
        final boolean v19 = false;
        y.M(this);
        HookTranslator.v19 = v19;
        Ta.M();
        HookTranslator.v22 = false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        final BlockPos down = new BlockPos((Entity)Wrapper.mc.player).down();
        final BlockPos blockPos = new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY - 0.4, Wrapper.mc.player.posZ);
        if (Scaffold.tower.M() && Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
            if (Wrapper.mc.player.onGround) {
                Wrapper.mc.player.motionY = 0.41999998688697815;
            }
            if (Wrapper.mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR && !Wrapper.mc.player.onGround) {
                Wrapper.mc.player.motionY = -1.0;
            }
        }
        if (Scaffold.mode.e().equals("AAC")) {
            if (Wrapper.mc.gameSettings.keyBindSprint.isKeyDown()) {
                KeybindHelper.keySprint.M(false);
            }
            HookTranslator.v22 = true;
        }
        else {
            HookTranslator.v22 = false;
        }
        HookTranslator.v19 = true;
        if (!pB.M(down).isReplaceable()) {
            Ta.M();
            if (!Scaffold.A) {
                final boolean a = true;
                this.G.reset();
                Scaffold.A = a;
            }
            double n = 200.0;
            if (Scaffold.mode.e().equals("NCP")) {
                n = 200.0;
            }
            if (Scaffold.mode.e().equals("AAC")) {
                n = 500.0;
            }
            if (this.G.delay(n)) {
                y.M(this);
            }
            return;
        }
        Scaffold.A = false;
        if (Wrapper.mc.player.motionY == 0.0 && Wrapper.mc.player.motionX == 0.0 && Wrapper.mc.player.motionZ == 0.0) {
            y.M(this);
            return;
        }
        int currentItem = -1;
        int n2;
        int i = n2 = 0;
        while (true) {
            while (i < 9) {
                final ItemStack stackInSlot;
                if ((stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(n2)) != null && stackInSlot.getItem() instanceof ItemBlock && Block.getBlockFromItem(stackInSlot.getItem()).getDefaultState().isFullBlock()) {
                    final int n3 = currentItem = n2;
                    if (n3 == -1) {
                        return;
                    }
                    final int currentItem2 = Wrapper.mc.player.inventory.currentItem;
                    Wrapper.mc.player.inventory.currentItem = currentItem;
                    if (ModuleManager.M("Nuker").k()) {
                        if (!ModuleManager.M("Nuker").k) {
                            this.M(down);
                        }
                    }
                    else {
                        this.M(down);
                    }
                    Wrapper.mc.player.inventory.currentItem = currentItem2;
                    return;
                }
                else {
                    i = ++n2;
                }
            }
            final int n3 = currentItem;
            continue;
        }
    }
    
    static {
        Scaffold.tower = ValueFactory.M("Scaffold", "Tower", "Go up faster", false);
        Scaffold.mode = ValueFactory.M("Scaffold", "Mode", "Mode of scaffold", "NCP", "NCP", "AAC");
        Scaffold.A = false;
    }
}
