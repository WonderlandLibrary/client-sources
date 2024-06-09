// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.player;

import net.minecraft.client.settings.GameSettings;
import xyz.niggfaclient.gui.click.ClickGui;
import net.minecraft.client.gui.inventory.GuiContainer;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "InvMove", description = "Allows you to move while in inventory", cat = Category.PLAYER)
public class InvMove extends Module
{
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public InvMove() {
        this.updateEventListener = (e -> {
            this.setDisplayName("Inventory Move");
            if (this.mc.currentScreen instanceof GuiContainer || this.mc.currentScreen instanceof ClickGui) {
                this.mc.gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindForward);
                this.mc.gameSettings.keyBindLeft.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindLeft);
                this.mc.gameSettings.keyBindBack.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindBack);
                this.mc.gameSettings.keyBindRight.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindRight);
                this.mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindJump);
            }
        });
    }
}
