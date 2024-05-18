package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.Random;
import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public class TeleportToTeam implements ISpectatorMenuView, ISpectatorMenuObject
{
    private final List HorizonCode_Horizon_È;
    private static final String Â = "CL_00001920";
    
    public TeleportToTeam() {
        this.HorizonCode_Horizon_È = Lists.newArrayList();
        final Minecraft var1 = Minecraft.áŒŠà();
        for (final ScorePlayerTeam var3 : var1.áŒŠÆ.à¢().Âµá€()) {
            this.HorizonCode_Horizon_È.add(new HorizonCode_Horizon_È(var3));
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public IChatComponent Â() {
        return new ChatComponentText("Select a team to teleport to");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final SpectatorMenu p_178661_1_) {
        p_178661_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public IChatComponent Ý() {
        return new ChatComponentText("Teleport to team member");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_178663_1_, final int p_178663_2_) {
        Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(GuiSpectator.HorizonCode_Horizon_È);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 16.0f, 0.0f, 16, 16, 256.0f, 256.0f);
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        for (final ISpectatorMenuObject var2 : this.HorizonCode_Horizon_È) {
            if (var2.Ø­áŒŠá()) {
                return true;
            }
        }
        return false;
    }
    
    class HorizonCode_Horizon_È implements ISpectatorMenuObject
    {
        private final ScorePlayerTeam Â;
        private final ResourceLocation_1975012498 Ý;
        private final List Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001919";
        
        public HorizonCode_Horizon_È(final ScorePlayerTeam p_i45492_2_) {
            this.Â = p_i45492_2_;
            this.Ø­áŒŠá = Lists.newArrayList();
            for (final String var4 : p_i45492_2_.Ý()) {
                final NetworkPlayerInfo var5 = Minecraft.áŒŠà().µÕ().HorizonCode_Horizon_È(var4);
                if (var5 != null) {
                    this.Ø­áŒŠá.add(var5);
                }
            }
            if (!this.Ø­áŒŠá.isEmpty()) {
                final String var6 = this.Ø­áŒŠá.get(new Random().nextInt(this.Ø­áŒŠá.size())).HorizonCode_Horizon_È().getName();
                AbstractClientPlayer.HorizonCode_Horizon_È(this.Ý = AbstractClientPlayer.HorizonCode_Horizon_È(var6), var6);
            }
            else {
                this.Ý = DefaultPlayerSkin.HorizonCode_Horizon_È();
            }
        }
        
        @Override
        public void HorizonCode_Horizon_È(final SpectatorMenu p_178661_1_) {
            p_178661_1_.HorizonCode_Horizon_È(new TeleportToPlayer(this.Ø­áŒŠá));
        }
        
        @Override
        public IChatComponent Ý() {
            return new ChatComponentText(this.Â.Â());
        }
        
        @Override
        public void HorizonCode_Horizon_È(final float p_178663_1_, final int p_178663_2_) {
            int var3 = -1;
            final String var4 = FontRenderer.Ý(this.Â.Ø­áŒŠá());
            if (var4.length() >= 2) {
                var3 = Minecraft.áŒŠà().µà.Â(var4.charAt(1));
            }
            if (var3 >= 0) {
                final float var5 = (var3 >> 16 & 0xFF) / 255.0f;
                final float var6 = (var3 >> 8 & 0xFF) / 255.0f;
                final float var7 = (var3 & 0xFF) / 255.0f;
                Gui_1808253012.HorizonCode_Horizon_È(1, 1, 15, 15, MathHelper.Â(var5 * p_178663_1_, var6 * p_178663_1_, var7 * p_178663_1_) | p_178663_2_ << 24);
            }
            Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(this.Ý);
            GlStateManager.Ý(p_178663_1_, p_178663_1_, p_178663_1_, p_178663_2_ / 255.0f);
            Gui_1808253012.HorizonCode_Horizon_È(2, 2, 8.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
            Gui_1808253012.HorizonCode_Horizon_È(2, 2, 40.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
        }
        
        @Override
        public boolean Ø­áŒŠá() {
            return !this.Ø­áŒŠá.isEmpty();
        }
    }
}
