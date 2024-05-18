package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

public final class AutoBlock extends ModBase
{
    public AutoBlock() {
        super("AutoBlock", "0", true, ".t block");
        this.setDescription("Makes you block while attacking.");
    }
    
    @Override
    public void preMotionUpdate() {
        if (KillAura.curTarget != null && this.shouldBlock()) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().isSprinting()) {
                this.getWrapper();
                MorbidWrapper.getPlayer().setSprinting(false);
            }
            this.getWrapper();
            this.getWrapper();
            this.getWrapper();
            this.getWrapper();
            final PlayerControllerMP controller = MorbidWrapper.getController();
            MorbidWrapper.mcObj();
            controller.sendUseItem(Minecraft.thePlayer, MorbidWrapper.getWorld(), MorbidWrapper.getPlayer().inventory.getCurrentItem());
        }
    }
    
    private boolean shouldBlock() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().getCurrentEquippedItem() != null) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().getCurrentEquippedItem().getItem() instanceof ItemSword) {
                this.getWrapper();
            }
            return !MorbidWrapper.mcObj().ingameGUI.persistantChatGUI.getChatOpen();
        }
        return false;
    }
}
