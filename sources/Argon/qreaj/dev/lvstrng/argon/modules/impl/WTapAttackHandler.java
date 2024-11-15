// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.modules.impl;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

class WTapAttackHandler implements PlayerInteractEntityC2SPacket.Handler {
    final AutoWTap wTapModule;

    WTapAttackHandler(final AutoWTap wtap) {
        this.wTapModule = wtap;
    }

    @Override
    public void interact(Hand hand) {
    }

    @Override
    public void interactAt(Hand hand, Vec3d pos) {
    }

    @Override
    public void attack() {
        if (AutoWTap.getMinecraft(this.wTapModule).options.forwardKey.isPressed() && AutoWTap.getMinecraft(this.wTapModule).player.isOnGround()) {
            this.wTapModule.wTapTimer.reset();
            this.wTapModule.isWTapped = true;
        }
    }
}
