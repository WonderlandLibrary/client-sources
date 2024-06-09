package com.client.glowclient.commands;

import com.client.glowclient.utils.*;
import com.client.glowclient.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.entity.*;

public class Tp extends Command
{
    public Tp() {
        super("tp");
    }
    
    @Override
    public void M(String s, final String[] array) {
        if (array.length < 4) {
            qd.D("§cNot enough data given");
        }
        s = array[1];
        final String s2 = array[2];
        final String s3 = array[3];
        if (Wrapper.mc.player != null && sd.D(s) && sd.D(s2) && sd.D(s3)) {
            M((Entity)Wrapper.mc.player, Double.parseDouble(s), Double.parseDouble(s2), Double.parseDouble(s3), Wrapper.mc.player.rotationYaw, Wrapper.mc.player.rotationPitch);
            if (Wrapper.mc.player.getRidingEntity() != null) {
                M(Wrapper.mc.player.getRidingEntity(), Double.parseDouble(s), Double.parseDouble(s2), Double.parseDouble(s3), Wrapper.mc.player.rotationYaw, Wrapper.mc.player.rotationPitch);
            }
            Ob.M().sendPacket((Packet)new CPacketPlayer$Position((double)Integer.parseInt(s), (double)Integer.parseInt(s2), (double)Integer.parseInt(s3), true));
        }
        qd.D(new StringBuilder().insert(0, "§bTeleported to: ").append(s).append(", ").append(s2).append(", ").append(s3).toString());
    }
    
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("tp").toString();
    }
    
    private static void M(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (entity instanceof EntityPlayerMP) {
            final EnumSet<SPacketPlayerPosLook$EnumFlags> none = EnumSet.noneOf(SPacketPlayerPosLook$EnumFlags.class);
            final float wrapDegrees = MathHelper.wrapDegrees(n4);
            final float wrapDegrees2 = MathHelper.wrapDegrees(n5);
            entity.dismountRidingEntity();
            ((EntityPlayerMP)entity).connection.setPlayerLocation(n, n2, n3, wrapDegrees, wrapDegrees2, (Set)none);
            final Entity entity2 = entity;
            entity.setRotationYawHead(wrapDegrees);
        }
        else {
            final float wrapDegrees3 = MathHelper.wrapDegrees(n4);
            final float clamp = MathHelper.clamp(MathHelper.wrapDegrees(n5), -90.0f, 90.0f);
            final float rotationYawHead = wrapDegrees3;
            final Entity entity2 = entity;
            entity.setLocationAndAngles(n, n2, n3, wrapDegrees3, clamp);
            entity.setRotationYawHead(rotationYawHead);
        }
        Entity entity2;
        if (!(entity2 instanceof EntityLivingBase) || !((EntityLivingBase)entity).isElytraFlying()) {
            final boolean onGround = true;
            entity.motionY = 0.0;
            entity.onGround = onGround;
        }
    }
}
