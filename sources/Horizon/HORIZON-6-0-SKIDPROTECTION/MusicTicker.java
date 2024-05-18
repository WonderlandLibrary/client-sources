package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class MusicTicker implements IUpdatePlayerListBox
{
    private final Random HorizonCode_Horizon_È;
    private final Minecraft Â;
    private ISound Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001138";
    
    public MusicTicker(final Minecraft mcIn) {
        this.HorizonCode_Horizon_È = new Random();
        this.Ø­áŒŠá = 100;
        this.Â = mcIn;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        final HorizonCode_Horizon_È var1 = this.Â.Ø­Âµ();
        if (this.Ý != null) {
            if (!var1.HorizonCode_Horizon_È().equals(this.Ý.Â())) {
                this.Â.£ÂµÄ().Â(this.Ý);
                this.Ø­áŒŠá = MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 0, var1.Â() / 2);
            }
            if (!this.Â.£ÂµÄ().Ý(this.Ý)) {
                this.Ý = null;
                this.Ø­áŒŠá = Math.min(MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, var1.Â(), var1.Ý()), this.Ø­áŒŠá);
            }
        }
        if (this.Ý == null && this.Ø­áŒŠá-- <= 0) {
            this.Ý = PositionedSoundRecord.HorizonCode_Horizon_È(var1.HorizonCode_Horizon_È());
            this.Â.£ÂµÄ().HorizonCode_Horizon_È(this.Ý);
            this.Ø­áŒŠá = Integer.MAX_VALUE;
        }
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("MENU", 0, "MENU", 0, new ResourceLocation_1975012498("minecraft:music.menu"), 20, 600), 
        Â("GAME", 1, "GAME", 1, new ResourceLocation_1975012498("minecraft:music.game"), 12000, 24000), 
        Ý("CREATIVE", 2, "CREATIVE", 2, new ResourceLocation_1975012498("minecraft:music.game.creative"), 1200, 3600), 
        Ø­áŒŠá("CREDITS", 3, "CREDITS", 3, new ResourceLocation_1975012498("minecraft:music.game.end.credits"), Integer.MAX_VALUE, Integer.MAX_VALUE), 
        Âµá€("NETHER", 4, "NETHER", 4, new ResourceLocation_1975012498("minecraft:music.game.nether"), 1200, 3600), 
        Ó("END_BOSS", 5, "END_BOSS", 5, new ResourceLocation_1975012498("minecraft:music.game.end.dragon"), 0, 0), 
        à("END", 6, "END", 6, new ResourceLocation_1975012498("minecraft:music.game.end"), 6000, 24000);
        
        private final ResourceLocation_1975012498 Ø;
        private final int áŒŠÆ;
        private final int áˆºÑ¢Õ;
        private static final HorizonCode_Horizon_È[] ÂµÈ;
        private static final String á = "CL_00001139";
        
        static {
            ˆÏ­ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à };
            ÂµÈ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45111_1_, final int p_i45111_2_, final ResourceLocation_1975012498 location, final int p_i45111_4_, final int p_i45111_5_) {
            this.Ø = location;
            this.áŒŠÆ = p_i45111_4_;
            this.áˆºÑ¢Õ = p_i45111_5_;
        }
        
        public ResourceLocation_1975012498 HorizonCode_Horizon_È() {
            return this.Ø;
        }
        
        public int Â() {
            return this.áŒŠÆ;
        }
        
        public int Ý() {
            return this.áˆºÑ¢Õ;
        }
    }
}
