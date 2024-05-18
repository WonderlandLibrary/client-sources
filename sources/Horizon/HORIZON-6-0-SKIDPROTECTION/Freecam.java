package HORIZON-6-0-SKIDPROTECTION;

import com.mojang.authlib.GameProfile;
import java.util.UUID;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -1, Â = "like NoClip", HorizonCode_Horizon_È = "Freecam")
public class Freecam extends Mod
{
    private EntityOtherPlayerMP Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private float à;
    private float Ø;
    
    public Freecam() {
        this.Ý = null;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        try {
            this.Â.á.ÇªÓ = true;
            this.Ø­áŒŠá = this.Â.á.ŒÏ;
            this.Âµá€ = this.Â.á.Çªà¢;
            this.Ó = this.Â.á.Ê;
            this.à = this.Â.á.áƒ;
            this.Ø = this.Â.á.É;
            (this.Ý = new EntityOtherPlayerMP(Minecraft.áŒŠà().á.k_(), new GameProfile(UUID.randomUUID(), this.Â.á.v_()))).HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢, this.Â.á.Ê, this.Â.á.É, this.Â.á.áƒ);
            this.Ý.Ø­Ñ¢Ï­Ø­áˆº = this.Â.á.Ø­Ñ¢Ï­Ø­áˆº;
            this.Ý.ŠÓ = this.Â.á.£áŒŠá();
            this.Â.áŒŠÆ.HorizonCode_Horizon_È(-1337, this.Ý);
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void á() {
        try {
            this.Â.áŒŠÆ.Â(-1337);
            this.Â.á.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.Âµá€, this.Ó, this.Ø, this.à);
            this.Â.á.ÇªÓ = false;
        }
        catch (Exception ex) {}
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È && this.Ý != null) {
            final float speed = 1.0f;
            this.Â.á.ÇŽÉ = 0.0;
            this.Â.á.ˆá = 0.0;
            this.Â.á.ÇŽÕ = 0.0;
            this.Â.á.ŠáˆºÂ = 1.5f;
            this.Â.á.Ø­Ñ¢á€ = 1.5f;
            if (Minecraft.áŒŠà().¥Æ == null) {
                if (GameSettings.HorizonCode_Horizon_È(Minecraft.áŒŠà().ŠÄ.Ø­Ñ¢Ï­Ø­áˆº)) {
                    Minecraft.áŒŠà().á.ˆá = speed * 5.0f / 6.0f;
                }
                if (GameSettings.HorizonCode_Horizon_È(Minecraft.áŒŠà().ŠÄ.ŒÂ)) {
                    Minecraft.áŒŠà().á.ˆá = -speed * 5.0f / 6.0f;
                }
            }
            final EntityPlayerSPOverwrite á = Minecraft.áŒŠà().á;
            á.Ø­Ñ¢á€ *= speed;
            this.Â.á.ŠÂµà = false;
            this.Â.á.Â(false);
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend packet) {
        if (packet.Ý() instanceof C03PacketPlayer) {
            final C03PacketPlayer p = (C03PacketPlayer)packet.Ý();
            packet.HorizonCode_Horizon_È(true);
        }
    }
}
