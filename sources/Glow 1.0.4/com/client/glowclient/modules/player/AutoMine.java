package com.client.glowclient.modules.player;

import com.client.glowclient.events.*;
import net.minecraft.block.material.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import com.client.glowclient.*;
import com.client.glowclient.utils.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class AutoMine extends ModuleContainer
{
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (Wrapper.mc.objectMouseOver == null || Wrapper.mc.objectMouseOver.getBlockPos() == null) {
            return;
        }
        final BlockPos blockPos = Wrapper.mc.objectMouseOver.getBlockPos();
        if (pB.M(Wrapper.mc.objectMouseOver.getBlockPos()) != Material.AIR && (Wrapper.mc.currentScreen instanceof GuiOptions || Wrapper.mc.currentScreen instanceof GuiVideoSettings || Wrapper.mc.currentScreen instanceof GuiScreenOptionsSounds || Wrapper.mc.currentScreen instanceof GuiContainer || Wrapper.mc.currentScreen instanceof GuiIngameMenu)) {
            HB.k(blockPos);
            kb.M();
        }
        KeybindHelper.keyAttack.M(pB.M(Wrapper.mc.objectMouseOver.getBlockPos()) != Material.AIR);
    }
    
    public AutoMine() {
        super(Category.PLAYER, "AutoMine", false, -1, "Automatically breaks blocks");
    }
    
    @Override
    public void E() {
        KeybindHelper.keyAttack.M(false);
    }
}
