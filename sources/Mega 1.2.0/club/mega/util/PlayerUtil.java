package club.mega.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class PlayerUtil {
    static Minecraft mc = Minecraft.getMinecraft();
    public static double getEffectiveHealth(EntityLivingBase entity) {
        return entity.getHealth() * (entity.getMaxHealth() / entity.getTotalArmorValue());
    }

    public static Vec3 getVectorForRotation(float yaw, float pitch) {
        float f = MathHelper.cos((float) (-yaw * 0.017163291F - Math.PI));
        float f2 = MathHelper.sin((float) (-yaw * 0.017163291F - Math.PI));
        float f3 = -MathHelper.cos(-pitch * 0.017163291F);
        float f4 = MathHelper.sin(-pitch * 0.017163291F);
        return new Vec3(f2 * f3, f4, f * f3);
    }
    public static void verusdmg() {
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.001, mc.thePlayer.posZ, false));
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
    }

}
