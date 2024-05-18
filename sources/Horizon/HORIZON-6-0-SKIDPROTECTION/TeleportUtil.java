package HORIZON-6-0-SKIDPROTECTION;

import com.mojang.authlib.GameProfile;

public class TeleportUtil
{
    private static Minecraft HorizonCode_Horizon_È;
    
    static {
        TeleportUtil.HorizonCode_Horizon_È = Minecraft.áŒŠà();
    }
    
    public static void HorizonCode_Horizon_È(final double d, final double d1, final double d2) {
        for (int i = 0; i < 2; ++i) {
            TeleportUtil.HorizonCode_Horizon_È.á.Ý(d, d1, d2);
            TeleportUtil.HorizonCode_Horizon_È.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(d, d1, d2, true));
        }
        try {
            Thread.sleep(3L);
        }
        catch (Exception ex) {}
    }
    
    public static void Â(final double d, final double d1, final double d2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final EntityOtherPlayerMP entity = new EntityOtherPlayerMP(Minecraft.áŒŠà().áŒŠÆ, new GameProfile(Minecraft.áŒŠà().á.£áŒŠá(), Minecraft.áŒŠà().á.l_().v_()));
                Minecraft.áŒŠà().áŒŠÆ.HorizonCode_Horizon_È(-1337, entity);
                entity.HorizonCode_Horizon_È(Minecraft.áŒŠà().á.ŒÏ, Minecraft.áŒŠà().á.à¢.Â, Minecraft.áŒŠà().á.Ê, Minecraft.áŒŠà().á.É, Minecraft.áŒŠà().á.áƒ);
                entity.ˆÏ­();
                TeleportUtil.HorizonCode_Horizon_È(TeleportUtil.HorizonCode_Horizon_È.á.ŒÏ, 256.0, TeleportUtil.HorizonCode_Horizon_È.á.Ê);
                TeleportUtil.HorizonCode_Horizon_È(d, 256.0, d2);
                TeleportUtil.HorizonCode_Horizon_È(d, d1, d2);
                Minecraft.áŒŠà().áŒŠÆ.Â(-1337);
            }
        }).start();
    }
}
