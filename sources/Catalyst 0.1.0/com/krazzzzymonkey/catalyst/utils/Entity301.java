// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import net.minecraft.entity.MoverType;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.World;
import net.minecraft.util.MovementInput;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class Entity301 extends EntityOtherPlayerMP
{
    private static /* synthetic */ MovementInput movementInput;
    private static final /* synthetic */ int[] lIl;
    
    public boolean isSneaking() {
        return Entity301.lIl[1] != 0;
    }
    
    private static void lI() {
        (lIl = new int[2])[0] = " ".length();
        Entity301.lIl[1] = ((0x55 ^ 0x10) & ~(0xE0 ^ 0xA5));
    }
    
    static {
        lI();
        Entity301.movementInput = null;
    }
    
    public Entity301(final World llllllllllllIll, final GameProfile lllllllllllIlll) {
        super(llllllllllllIll, lllllllllllIlll);
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.noClip = (Entity301.lIl[0] != 0);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.noClip = (Entity301.lIl[1] != 0);
    }
    
    public void move(final MoverType llllllllllIlIlI, final double llllllllllIIlII, final double llllllllllIlIII, final double llllllllllIIlll) {
        this.onGround = (Entity301.lIl[0] != 0);
        super.move(llllllllllIlIlI, llllllllllIIlII, llllllllllIlIII, llllllllllIIlll);
        this.onGround = (Entity301.lIl[0] != 0);
    }
    
    private static boolean lII(final int lllllllllIlllII) {
        return lllllllllIlllII != 0;
    }
    
    public void setMovementInput(final MovementInput lllllllllllIIll) {
        Entity301.movementInput = lllllllllllIIll;
        if (lII(lllllllllllIIll.jump ? 1 : 0) && lII(this.onGround ? 1 : 0)) {
            super.jump();
        }
        super.moveRelative(lllllllllllIIll.moveStrafe, this.moveVertical, lllllllllllIIll.moveForward, this.movedDistance);
    }
}
