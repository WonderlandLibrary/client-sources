package com.krazzzzymonkey.catalyst.module.modules.movement;

import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import com.krazzzzymonkey.catalyst.utils.Utils;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.krazzzzymonkey.catalyst.module.Modules;

public class Speed extends Modules
{
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent tickEvent) {
        int n;
        if ((fcmpg(Math.abs(Wrapper.INSTANCE.player().rotationYawHead - Wrapper.INSTANCE.player().rotationYaw), 90.0f)) < 0) {
            n = 1;
        }
        else {
            n = 0;
        }
        final boolean bool1 = n != 0;
        if ((fcmpl(Wrapper.INSTANCE.player().moveForward, 0.0f) > 0) && (Wrapper.INSTANCE.player().hurtTime < 5)) {
            if (Wrapper.INSTANCE.player().onGround) {
                Wrapper.INSTANCE.player().motionY = 0.405;
                final float direction = Utils.getDirection();
                final EntityPlayerSP player = Wrapper.INSTANCE.player();
                player.motionX -= MathHelper.sin(direction) * 0.2f;
                final EntityPlayerSP player2 = Wrapper.INSTANCE.player();
                player2.motionZ += MathHelper.cos(direction) * 0.2f;

            }
            else {
                final double double1 = Math.sqrt(Wrapper.INSTANCE.player().motionX * Wrapper.INSTANCE.player().motionX + Wrapper.INSTANCE.player().motionZ * Wrapper.INSTANCE.player().motionZ);
                double n2;
                if (bool1) {
                    n2 = 1.0064;
                }
                else {
                    n2 = 1.001;
                }
                final double double2 = n2;
                final double direction = Utils.getDirection();
                Wrapper.INSTANCE.player().motionX = -Math.sin(direction) * double2 * double1;
                Wrapper.INSTANCE.player().motionZ = Math.cos(direction) * double2 * double1;
            }
        }
        super.onClientTick(tickEvent);
    }

   
    public Speed() {
        super("Bhop", ModuleCategory.MOVEMENT);
    }

    
}
