package com.client.glowclient.modules.player;

import net.minecraft.client.*;
import net.minecraft.util.math.*;
import com.client.glowclient.*;
import net.minecraft.item.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoTool extends ModuleContainer
{
    private static int G;
    public int d;
    private static final Minecraft L;
    private static final boolean A = false;
    private static int B;
    private static BlockPos b;
    
    static {
        L = Minecraft.getMinecraft();
        AutoTool.B = -1;
    }
    
    @Override
    public void E() {
        if (AutoTool.B != -1) {
            AutoTool.L.player.inventory.currentItem = AutoTool.B;
            AutoTool.B = -1;
        }
    }
    
    public static void M(final BlockPos b) {
        if (AutoTool.L.player.capabilities.isCreativeMode) {
            return;
        }
        if (!pB.M(b)) {
            return;
        }
        float m;
        if (AutoTool.L.player.inventory.getCurrentItem() != null) {
            m = ZB.M(AutoTool.L.player.inventory.getCurrentItem(), b);
        }
        else {
            m = 1.0f;
        }
        int currentItem = -1;
        int n;
        int i = n = 0;
        while (i < 9) {
            final ItemStack stackInSlot;
            if (!ZB.D(stackInSlot = AutoTool.L.player.inventory.getStackInSlot(n))) {
                if (!(stackInSlot.getItem() instanceof ItemSword)) {
                    final float j;
                    if ((j = ZB.M(stackInSlot, b)) > m) {
                        m = j;
                        currentItem = n;
                    }
                }
            }
            i = ++n;
        }
        if (currentItem == -1) {
            return;
        }
        if (AutoTool.B == -1) {
            AutoTool.B = AutoTool.L.player.inventory.currentItem;
        }
        AutoTool.L.player.inventory.currentItem = currentItem;
        final int g = 4;
        AutoTool.b = b;
        AutoTool.G = g;
    }
    
    public AutoTool() {
        final int d = -1;
        super(Category.PLAYER, "AutoTool", false, -1, "Automatically selects best tool");
        this.d = d;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (AutoTool.L.gameSettings.keyBindAttack.isKeyDown() && AutoTool.L.objectMouseOver != null && AutoTool.L.objectMouseOver.getBlockPos() != null) {
            M(AutoTool.L.objectMouseOver.getBlockPos());
        }
        if (AutoTool.B == -1) {
            return;
        }
        if (AutoTool.G <= 0) {
            AutoTool.L.player.inventory.currentItem = AutoTool.B;
            AutoTool.B = -1;
            return;
        }
        if (!AutoTool.L.gameSettings.keyBindAttack.isKeyDown() || AutoTool.L.player.capabilities.isCreativeMode || !pB.M(AutoTool.b)) {
            --AutoTool.G;
        }
    }
}
