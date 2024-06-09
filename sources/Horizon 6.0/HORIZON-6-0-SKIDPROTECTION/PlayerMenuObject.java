package HORIZON-6-0-SKIDPROTECTION;

import com.mojang.authlib.GameProfile;

public class PlayerMenuObject implements ISpectatorMenuObject
{
    private final GameProfile HorizonCode_Horizon_È;
    private final ResourceLocation_1975012498 Â;
    private static final String Ý = "CL_00001929";
    
    public PlayerMenuObject(final GameProfile p_i45498_1_) {
        this.HorizonCode_Horizon_È = p_i45498_1_;
        AbstractClientPlayer.HorizonCode_Horizon_È(this.Â = AbstractClientPlayer.HorizonCode_Horizon_È(p_i45498_1_.getName()), p_i45498_1_.getName());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final SpectatorMenu p_178661_1_) {
        Minecraft.áŒŠà().µÕ().HorizonCode_Horizon_È(new C18PacketSpectate(this.HorizonCode_Horizon_È.getId()));
    }
    
    @Override
    public IChatComponent Ý() {
        return new ChatComponentText(this.HorizonCode_Horizon_È.getName());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_178663_1_, final int p_178663_2_) {
        Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(this.Â);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, p_178663_2_ / 255.0f);
        Gui_1808253012.HorizonCode_Horizon_È(2, 2, 8.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
        Gui_1808253012.HorizonCode_Horizon_È(2, 2, 40.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return true;
    }
}
