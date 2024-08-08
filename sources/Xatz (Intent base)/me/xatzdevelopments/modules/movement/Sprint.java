package me.xatzdevelopments.modules.movement;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.util.ModulesUtils;
import me.xatzdevelopments.util.MoveUtils;

public class Sprint extends Module
{
    public ModeSetting Mode;
    int offGroundTicks;
    
    public Sprint() {
        super("Sprint", 0, Category.MOVEMENT, null);
        this.Mode = new ModeSetting("Mode", "Legit", new String[] { "Legit", "MultiDir", "MultiDir2" });
        this.offGroundTicks = 0;
        this.addSettings(this.Mode);
        this.toggled = true;
    }
    
    @Override
    public void onEnable() {
        this.offGroundTicks = 0;
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.setSprinting(this.mc.gameSettings.keyBindSprint.getIsKeyPressed());
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventMotion && e.isPre()) {
            final EventMotion event = (EventMotion)e;
            if (this.mc.thePlayer.onGround) {
                this.offGroundTicks = 0;
            }
            else {
                ++this.offGroundTicks;
            }
            this.mc.thePlayer.setSprinting(false);
            Label_0161: {
                if (this.Mode.getMode() == "Legit") {
                    if (this.mc.thePlayer.moveForward <= 0.0f) {
                        break Label_0161;
                    }
                }
                else if (!MoveUtils.isMoving()) {
                    break Label_0161;
                }
                if (!Xatz.getModuleByName("NoSlowDown").isEnabled()) {
                    if (this.mc.thePlayer.isUsingItem() || this.mc.thePlayer.isCollidedHorizontally || this.mc.thePlayer.isSneaking()) {
                        break Label_0161;
                    }
                }
                this.mc.thePlayer.setSprinting(true);
            }
            if (this.Mode.getMode() == "MultiDir2") {
                if (this.mc.gameSettings.keyBindBack.getIsKeyPressed()) {
                    event.setYaw(this.mc.thePlayer.rotationYaw - 180.0f);
                }
                else if (this.mc.gameSettings.keyBindLeft.getIsKeyPressed()) {
                    event.setYaw(this.mc.thePlayer.rotationYaw - 90.0f);
                }
                else if (this.mc.gameSettings.keyBindRight.getIsKeyPressed()) {
                    event.setYaw(this.mc.thePlayer.rotationYaw + 90.0f);
                }
            }
        }
    }
}
