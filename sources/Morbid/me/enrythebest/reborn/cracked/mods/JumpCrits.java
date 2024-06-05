package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

public final class JumpCrits extends ModBase
{
    public JumpCrits() {
        super("JumpCrits", "K", true, ".t jumpcrits");
        this.setDescription("Tp's you up a little when you are attacking to do critical damage.");
    }
    
    @Override
    public void postMotionUpdate() {
        if (this.shouldCrit() && this.checkBlock()) {
            this.getWrapper();
            MorbidWrapper.getPlayer().boundingBox.offset(0.0, 0.6, 0.0);
        }
    }
    
    private boolean shouldCrit() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().onGround) {
            this.getWrapper();
            if (!MorbidWrapper.getPlayer().isOnLadder()) {
                this.getWrapper();
                if (!MorbidWrapper.getPlayer().isInWater()) {
                    this.getWrapper();
                    if (!MorbidWrapper.getGameSettings().keyBindJump.pressed) {
                        this.getWrapper();
                        if (!MorbidWrapper.getPlayer().isCollidedHorizontally && KillAura.curTarget != null) {
                            final boolean var10000 = true;
                            return var10000;
                        }
                    }
                }
            }
        }
        final boolean var10000 = false;
        return var10000;
    }
    
    private boolean checkBlock() {
        this.getWrapper();
        MorbidWrapper.mcObj();
        final WorldClient var10000 = Minecraft.theWorld;
        this.getWrapper();
        final int var10001 = MathHelper.floor_double(MorbidWrapper.getPlayer().posX);
        this.getWrapper();
        final int var10002 = MathHelper.floor_double(MorbidWrapper.getPlayer().boundingBox.maxY + 1.0);
        this.getWrapper();
        final boolean var10003 = var10000.isAirBlock(var10001, var10002, MathHelper.floor_double(MorbidWrapper.getPlayer().posZ));
        return var10003;
    }
}
