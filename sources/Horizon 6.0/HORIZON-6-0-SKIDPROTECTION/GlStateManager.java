package HORIZON-6-0-SKIDPROTECTION;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;

public class GlStateManager
{
    private static HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private static Ý Â;
    private static Ý[] Ý;
    private static Ø Ø­áŒŠá;
    private static Â Âµá€;
    private static áˆºÑ¢Õ Ó;
    private static ÂµÈ à;
    private static áŒŠÆ Ø;
    private static á áŒŠÆ;
    private static Ó áˆºÑ¢Õ;
    private static ˆà ÂµÈ;
    private static Ø­áŒŠá á;
    private static £á ˆÏ­;
    private static Ý £á;
    private static int Å;
    private static ¥Æ[] £à;
    private static int µà;
    private static Ý ˆà;
    private static à ¥Æ;
    private static Âµá€ Ø­à;
    private static Ø­à µÕ;
    private static final String Æ = "CL_00002558";
    
    static {
        GlStateManager.HorizonCode_Horizon_È = new HorizonCode_Horizon_È(null);
        GlStateManager.Â = new Ý(2896);
        GlStateManager.Ý = new Ý[8];
        GlStateManager.Ø­áŒŠá = new Ø(null);
        GlStateManager.Âµá€ = new Â(null);
        GlStateManager.Ó = new áˆºÑ¢Õ(null);
        GlStateManager.à = new ÂµÈ(null);
        GlStateManager.Ø = new áŒŠÆ(null);
        GlStateManager.áŒŠÆ = new á(null);
        GlStateManager.áˆºÑ¢Õ = new Ó(null);
        GlStateManager.ÂµÈ = new ˆà(null);
        GlStateManager.á = new Ø­áŒŠá(null);
        GlStateManager.ˆÏ­ = new £á(null);
        GlStateManager.£á = new Ý(2977);
        GlStateManager.Å = 0;
        GlStateManager.£à = new ¥Æ[8];
        GlStateManager.µà = 7425;
        GlStateManager.ˆà = new Ý(32826);
        GlStateManager.¥Æ = new à(null);
        GlStateManager.Ø­à = new Âµá€();
        GlStateManager.µÕ = new Ø­à(null);
        for (int var0 = 0; var0 < 8; ++var0) {
            GlStateManager.Ý[var0] = new Ý(16384 + var0);
        }
        for (int var0 = 0; var0 < 8; ++var0) {
            GlStateManager.£à[var0] = new ¥Æ(null);
        }
    }
    
    public static void HorizonCode_Horizon_È() {
        GL11.glPushAttrib(8256);
    }
    
    public static void Â() {
        GL11.glPopAttrib();
    }
    
    public static void Ý() {
        GlStateManager.HorizonCode_Horizon_È.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static void Ø­áŒŠá() {
        GlStateManager.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â();
    }
    
    public static void HorizonCode_Horizon_È(final int p_179092_0_, final float p_179092_1_) {
        if (p_179092_0_ != GlStateManager.HorizonCode_Horizon_È.Â || p_179092_1_ != GlStateManager.HorizonCode_Horizon_È.Ý) {
            GL11.glAlphaFunc(GlStateManager.HorizonCode_Horizon_È.Â = p_179092_0_, GlStateManager.HorizonCode_Horizon_È.Ý = p_179092_1_);
        }
    }
    
    public static void Âµá€() {
        GlStateManager.Â.Â();
    }
    
    public static void Ó() {
        GlStateManager.Â.HorizonCode_Horizon_È();
    }
    
    public static void HorizonCode_Horizon_È(final int p_179085_0_) {
        GlStateManager.Ý[p_179085_0_].Â();
    }
    
    public static void Â(final int p_179122_0_) {
        GlStateManager.Ý[p_179122_0_].HorizonCode_Horizon_È();
    }
    
    public static void à() {
        GlStateManager.Ø­áŒŠá.HorizonCode_Horizon_È.Â();
    }
    
    public static void Ø() {
        GlStateManager.Ø­áŒŠá.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static void HorizonCode_Horizon_È(final int p_179104_0_, final int p_179104_1_) {
        if (p_179104_0_ != GlStateManager.Ø­áŒŠá.Â || p_179104_1_ != GlStateManager.Ø­áŒŠá.Ý) {
            GL11.glColorMaterial(GlStateManager.Ø­áŒŠá.Â = p_179104_0_, GlStateManager.Ø­áŒŠá.Ý = p_179104_1_);
        }
    }
    
    public static void áŒŠÆ() {
        GlStateManager.Ó.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static void áˆºÑ¢Õ() {
        GlStateManager.Ó.HorizonCode_Horizon_È.Â();
    }
    
    public static void Ý(final int p_179143_0_) {
        if (p_179143_0_ != GlStateManager.Ó.Ý) {
            GL11.glDepthFunc(GlStateManager.Ó.Ý = p_179143_0_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final boolean p_179132_0_) {
        if (p_179132_0_ != GlStateManager.Ó.Â) {
            GL11.glDepthMask(GlStateManager.Ó.Â = p_179132_0_);
        }
    }
    
    public static void ÂµÈ() {
        GlStateManager.Âµá€.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static void á() {
        GlStateManager.Âµá€.HorizonCode_Horizon_È.Â();
    }
    
    public static void Â(final int p_179112_0_, final int p_179112_1_) {
        if (p_179112_0_ != GlStateManager.Âµá€.Â || p_179112_1_ != GlStateManager.Âµá€.Ý) {
            GL11.glBlendFunc(GlStateManager.Âµá€.Â = p_179112_0_, GlStateManager.Âµá€.Ý = p_179112_1_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final int p_179120_0_, final int p_179120_1_, final int p_179120_2_, final int p_179120_3_) {
        if (p_179120_0_ != GlStateManager.Âµá€.Â || p_179120_1_ != GlStateManager.Âµá€.Ý || p_179120_2_ != GlStateManager.Âµá€.Ø­áŒŠá || p_179120_3_ != GlStateManager.Âµá€.Âµá€) {
            OpenGlHelper.Ý(GlStateManager.Âµá€.Â = p_179120_0_, GlStateManager.Âµá€.Ý = p_179120_1_, GlStateManager.Âµá€.Ø­áŒŠá = p_179120_2_, GlStateManager.Âµá€.Âµá€ = p_179120_3_);
        }
    }
    
    public static void ˆÏ­() {
        GlStateManager.à.HorizonCode_Horizon_È.Â();
    }
    
    public static void £á() {
        GlStateManager.à.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static void Ø­áŒŠá(final int p_179093_0_) {
        if (p_179093_0_ != GlStateManager.à.Â) {
            GL11.glFogi(2917, GlStateManager.à.Â = p_179093_0_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final float p_179095_0_) {
        if (p_179095_0_ != GlStateManager.à.Ý) {
            GL11.glFogf(2914, GlStateManager.à.Ý = p_179095_0_);
        }
    }
    
    public static void Â(final float p_179102_0_) {
        if (p_179102_0_ != GlStateManager.à.Ø­áŒŠá) {
            GL11.glFogf(2915, GlStateManager.à.Ø­áŒŠá = p_179102_0_);
        }
    }
    
    public static void Ý(final float p_179153_0_) {
        if (p_179153_0_ != GlStateManager.à.Âµá€) {
            GL11.glFogf(2916, GlStateManager.à.Âµá€ = p_179153_0_);
        }
    }
    
    public static void Å() {
        GlStateManager.Ø.HorizonCode_Horizon_È.Â();
    }
    
    public static void £à() {
        GlStateManager.Ø.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static void Âµá€(final int p_179107_0_) {
        if (p_179107_0_ != GlStateManager.Ø.Â) {
            GL11.glCullFace(GlStateManager.Ø.Â = p_179107_0_);
        }
    }
    
    public static void µà() {
        GlStateManager.áŒŠÆ.HorizonCode_Horizon_È.Â();
    }
    
    public static void ˆà() {
        GlStateManager.áŒŠÆ.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static void HorizonCode_Horizon_È(final float p_179136_0_, final float p_179136_1_) {
        if (p_179136_0_ != GlStateManager.áŒŠÆ.Ý || p_179136_1_ != GlStateManager.áŒŠÆ.Ø­áŒŠá) {
            GL11.glPolygonOffset(GlStateManager.áŒŠÆ.Ý = p_179136_0_, GlStateManager.áŒŠÆ.Ø­áŒŠá = p_179136_1_);
        }
    }
    
    public static void ¥Æ() {
        GlStateManager.áˆºÑ¢Õ.HorizonCode_Horizon_È.Â();
    }
    
    public static void Ø­à() {
        GlStateManager.áˆºÑ¢Õ.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static void Ó(final int p_179116_0_) {
        if (p_179116_0_ != GlStateManager.áˆºÑ¢Õ.Â) {
            GL11.glLogicOp(GlStateManager.áˆºÑ¢Õ.Â = p_179116_0_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final £à p_179087_0_) {
        Ý(p_179087_0_).HorizonCode_Horizon_È.Â();
    }
    
    public static void Â(final £à p_179100_0_) {
        Ý(p_179100_0_).HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static void HorizonCode_Horizon_È(final £à p_179149_0_, final int p_179149_1_) {
        final µà var2 = Ý(p_179149_0_);
        if (p_179149_1_ != var2.Ý) {
            var2.Ý = p_179149_1_;
            GL11.glTexGeni(var2.Â, 9472, p_179149_1_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final £à p_179105_0_, final int p_179105_1_, final FloatBuffer p_179105_2_) {
        GL11.glTexGen(Ý(p_179105_0_).Â, p_179105_1_, p_179105_2_);
    }
    
    private static µà Ý(final £à p_179125_0_) {
        switch (GlStateManager.Å.HorizonCode_Horizon_È[p_179125_0_.ordinal()]) {
            case 1: {
                return GlStateManager.ÂµÈ.HorizonCode_Horizon_È;
            }
            case 2: {
                return GlStateManager.ÂµÈ.Â;
            }
            case 3: {
                return GlStateManager.ÂµÈ.Ý;
            }
            case 4: {
                return GlStateManager.ÂµÈ.Ø­áŒŠá;
            }
            default: {
                return GlStateManager.ÂµÈ.HorizonCode_Horizon_È;
            }
        }
    }
    
    public static void à(final int p_179138_0_) {
        if (GlStateManager.Å != p_179138_0_ - OpenGlHelper.£à) {
            GlStateManager.Å = p_179138_0_ - OpenGlHelper.£à;
            OpenGlHelper.ÂµÈ(p_179138_0_);
        }
    }
    
    public static void µÕ() {
        GlStateManager.£à[GlStateManager.Å].HorizonCode_Horizon_È.Â();
    }
    
    public static void Æ() {
        GlStateManager.£à[GlStateManager.Å].HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static int Šáƒ() {
        return GL11.glGenTextures();
    }
    
    public static void Ø(final int p_179150_0_) {
        GL11.glDeleteTextures(p_179150_0_);
        for (final ¥Æ var4 : GlStateManager.£à) {
            if (var4.Â == p_179150_0_) {
                var4.Â = -1;
            }
        }
    }
    
    public static void áŒŠÆ(final int p_179144_0_) {
        if (p_179144_0_ != GlStateManager.£à[GlStateManager.Å].Â) {
            GL11.glBindTexture(3553, GlStateManager.£à[GlStateManager.Å].Â = p_179144_0_);
        }
    }
    
    public static void Ï­Ðƒà() {
        GlStateManager.£á.Â();
    }
    
    public static void áŒŠà() {
        GlStateManager.£á.HorizonCode_Horizon_È();
    }
    
    public static void áˆºÑ¢Õ(final int p_179103_0_) {
        if (p_179103_0_ != GlStateManager.µà) {
            GL11.glShadeModel(GlStateManager.µà = p_179103_0_);
        }
    }
    
    public static void ŠÄ() {
        GlStateManager.ˆà.Â();
    }
    
    public static void Ñ¢á() {
        GlStateManager.ˆà.HorizonCode_Horizon_È();
    }
    
    public static void Â(final int p_179083_0_, final int p_179083_1_, final int p_179083_2_, final int p_179083_3_) {
        if (p_179083_0_ != GlStateManager.µÕ.HorizonCode_Horizon_È || p_179083_1_ != GlStateManager.µÕ.Â || p_179083_2_ != GlStateManager.µÕ.Ý || p_179083_3_ != GlStateManager.µÕ.Ø­áŒŠá) {
            GL11.glViewport(GlStateManager.µÕ.HorizonCode_Horizon_È = p_179083_0_, GlStateManager.µÕ.Â = p_179083_1_, GlStateManager.µÕ.Ý = p_179083_2_, GlStateManager.µÕ.Ø­áŒŠá = p_179083_3_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final boolean p_179135_0_, final boolean p_179135_1_, final boolean p_179135_2_, final boolean p_179135_3_) {
        if (p_179135_0_ != GlStateManager.¥Æ.HorizonCode_Horizon_È || p_179135_1_ != GlStateManager.¥Æ.Â || p_179135_2_ != GlStateManager.¥Æ.Ý || p_179135_3_ != GlStateManager.¥Æ.Ø­áŒŠá) {
            GL11.glColorMask(GlStateManager.¥Æ.HorizonCode_Horizon_È = p_179135_0_, GlStateManager.¥Æ.Â = p_179135_1_, GlStateManager.¥Æ.Ý = p_179135_2_, GlStateManager.¥Æ.Ø­áŒŠá = p_179135_3_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final double p_179151_0_) {
        if (p_179151_0_ != GlStateManager.á.HorizonCode_Horizon_È) {
            GL11.glClearDepth(GlStateManager.á.HorizonCode_Horizon_È = p_179151_0_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final float p_179082_0_, final float p_179082_1_, final float p_179082_2_, final float p_179082_3_) {
        if (p_179082_0_ != GlStateManager.á.Â.HorizonCode_Horizon_È || p_179082_1_ != GlStateManager.á.Â.Â || p_179082_2_ != GlStateManager.á.Â.Ý || p_179082_3_ != GlStateManager.á.Â.Ø­áŒŠá) {
            GL11.glClearColor(GlStateManager.á.Â.HorizonCode_Horizon_È = p_179082_0_, GlStateManager.á.Â.Â = p_179082_1_, GlStateManager.á.Â.Ý = p_179082_2_, GlStateManager.á.Â.Ø­áŒŠá = p_179082_3_);
        }
    }
    
    public static void ÂµÈ(final int p_179086_0_) {
        GL11.glClear(p_179086_0_);
    }
    
    public static void á(final int p_179128_0_) {
        GL11.glMatrixMode(p_179128_0_);
    }
    
    public static void ŒÏ() {
        GL11.glLoadIdentity();
    }
    
    public static void Çªà¢() {
        GL11.glPushMatrix();
    }
    
    public static void Ê() {
        GL11.glPopMatrix();
    }
    
    public static void HorizonCode_Horizon_È(final int p_179111_0_, final FloatBuffer p_179111_1_) {
        GL11.glGetFloat(p_179111_0_, p_179111_1_);
    }
    
    public static void HorizonCode_Horizon_È(final double p_179130_0_, final double p_179130_2_, final double p_179130_4_, final double p_179130_6_, final double p_179130_8_, final double p_179130_10_) {
        GL11.glOrtho(p_179130_0_, p_179130_2_, p_179130_4_, p_179130_6_, p_179130_8_, p_179130_10_);
    }
    
    public static void Â(final float p_179114_0_, final float p_179114_1_, final float p_179114_2_, final float p_179114_3_) {
        GL11.glRotatef(p_179114_0_, p_179114_1_, p_179114_2_, p_179114_3_);
    }
    
    public static void HorizonCode_Horizon_È(final float p_179152_0_, final float p_179152_1_, final float p_179152_2_) {
        GL11.glScalef(p_179152_0_, p_179152_1_, p_179152_2_);
    }
    
    public static void HorizonCode_Horizon_È(final double p_179139_0_, final double p_179139_2_, final double p_179139_4_) {
        GL11.glScaled(p_179139_0_, p_179139_2_, p_179139_4_);
    }
    
    public static void Â(final float p_179109_0_, final float p_179109_1_, final float p_179109_2_) {
        GL11.glTranslatef(p_179109_0_, p_179109_1_, p_179109_2_);
    }
    
    public static void Â(final double p_179137_0_, final double p_179137_2_, final double p_179137_4_) {
        GL11.glTranslated(p_179137_0_, p_179137_2_, p_179137_4_);
    }
    
    public static void HorizonCode_Horizon_È(final FloatBuffer p_179110_0_) {
        GL11.glMultMatrix(p_179110_0_);
    }
    
    public static void Ý(final float p_179131_0_, final float p_179131_1_, final float p_179131_2_, final float p_179131_3_) {
        if (p_179131_0_ != GlStateManager.Ø­à.HorizonCode_Horizon_È || p_179131_1_ != GlStateManager.Ø­à.Â || p_179131_2_ != GlStateManager.Ø­à.Ý || p_179131_3_ != GlStateManager.Ø­à.Ø­áŒŠá) {
            GL11.glColor4f(GlStateManager.Ø­à.HorizonCode_Horizon_È = p_179131_0_, GlStateManager.Ø­à.Â = p_179131_1_, GlStateManager.Ø­à.Ý = p_179131_2_, GlStateManager.Ø­à.Ø­áŒŠá = p_179131_3_);
        }
    }
    
    public static void Ý(final float p_179124_0_, final float p_179124_1_, final float p_179124_2_) {
        Ý(p_179124_0_, p_179124_1_, p_179124_2_, 1.0f);
    }
    
    public static void ÇŽÉ() {
        final Âµá€ ø­à = GlStateManager.Ø­à;
        final Âµá€ ø­à2 = GlStateManager.Ø­à;
        final Âµá€ ø­à3 = GlStateManager.Ø­à;
        final Âµá€ ø­à4 = GlStateManager.Ø­à;
        final float n = -1.0f;
        ø­à4.Ø­áŒŠá = n;
        ø­à3.Ý = n;
        ø­à2.Â = n;
        ø­à.HorizonCode_Horizon_È = n;
    }
    
    public static void ˆÏ­(final int p_179148_0_) {
        GL11.glCallList(p_179148_0_);
    }
    
    public enum £à
    {
        HorizonCode_Horizon_È("S", 0, "S", 0), 
        Â("T", 1, "T", 1), 
        Ý("R", 2, "R", 2), 
        Ø­áŒŠá("Q", 3, "Q", 3);
        
        private static final £à[] Âµá€;
        private static final String Ó = "CL_00002542";
        
        static {
            à = new £à[] { £à.HorizonCode_Horizon_È, £à.Â, £à.Ý, £à.Ø­áŒŠá };
            Âµá€ = new £à[] { £à.HorizonCode_Horizon_È, £à.Â, £à.Ý, £à.Ø­áŒŠá };
        }
        
        private £à(final String s, final int n, final String p_i46255_1_, final int p_i46255_2_) {
        }
    }
    
    static class HorizonCode_Horizon_È
    {
        public Ý HorizonCode_Horizon_È;
        public int Â;
        public float Ý;
        private static final String Ø­áŒŠá = "CL_00002556";
        
        private HorizonCode_Horizon_È() {
            this.HorizonCode_Horizon_È = new Ý(3008);
            this.Â = 519;
            this.Ý = -1.0f;
        }
        
        HorizonCode_Horizon_È(final Å p_i46269_1_) {
            this();
        }
    }
    
    static class Â
    {
        public Ý HorizonCode_Horizon_È;
        public int Â;
        public int Ý;
        public int Ø­áŒŠá;
        public int Âµá€;
        private static final String Ó = "CL_00002555";
        
        private Â() {
            this.HorizonCode_Horizon_È = new Ý(3042);
            this.Â = 1;
            this.Ý = 0;
            this.Ø­áŒŠá = 1;
            this.Âµá€ = 0;
        }
        
        Â(final Å p_i46268_1_) {
            this();
        }
    }
    
    static class Ý
    {
        private final int HorizonCode_Horizon_È;
        private boolean Â;
        private static final String Ý = "CL_00002554";
        
        public Ý(final int p_i46267_1_) {
            this.Â = false;
            this.HorizonCode_Horizon_È = p_i46267_1_;
        }
        
        public void HorizonCode_Horizon_È() {
            this.HorizonCode_Horizon_È(false);
        }
        
        public void Â() {
            this.HorizonCode_Horizon_È(true);
        }
        
        public void HorizonCode_Horizon_È(final boolean p_179199_1_) {
            if (p_179199_1_ != this.Â) {
                this.Â = p_179199_1_;
                if (p_179199_1_) {
                    GL11.glEnable(this.HorizonCode_Horizon_È);
                }
                else {
                    GL11.glDisable(this.HorizonCode_Horizon_È);
                }
            }
        }
    }
    
    static class Ø­áŒŠá
    {
        public double HorizonCode_Horizon_È;
        public Âµá€ Â;
        public int Ý;
        private static final String Ø­áŒŠá = "CL_00002553";
        
        private Ø­áŒŠá() {
            this.HorizonCode_Horizon_È = 1.0;
            this.Â = new Âµá€(0.0f, 0.0f, 0.0f, 0.0f);
            this.Ý = 0;
        }
        
        Ø­áŒŠá(final Å p_i46266_1_) {
            this();
        }
    }
    
    static class Âµá€
    {
        public float HorizonCode_Horizon_È;
        public float Â;
        public float Ý;
        public float Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002552";
        
        public Âµá€() {
            this.HorizonCode_Horizon_È = 1.0f;
            this.Â = 1.0f;
            this.Ý = 1.0f;
            this.Ø­áŒŠá = 1.0f;
        }
        
        public Âµá€(final float p_i46265_1_, final float p_i46265_2_, final float p_i46265_3_, final float p_i46265_4_) {
            this.HorizonCode_Horizon_È = 1.0f;
            this.Â = 1.0f;
            this.Ý = 1.0f;
            this.Ø­áŒŠá = 1.0f;
            this.HorizonCode_Horizon_È = p_i46265_1_;
            this.Â = p_i46265_2_;
            this.Ý = p_i46265_3_;
            this.Ø­áŒŠá = p_i46265_4_;
        }
    }
    
    static class Ó
    {
        public Ý HorizonCode_Horizon_È;
        public int Â;
        private static final String Ý = "CL_00002551";
        
        private Ó() {
            this.HorizonCode_Horizon_È = new Ý(3058);
            this.Â = 5379;
        }
        
        Ó(final Å p_i46264_1_) {
            this();
        }
    }
    
    static class à
    {
        public boolean HorizonCode_Horizon_È;
        public boolean Â;
        public boolean Ý;
        public boolean Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002550";
        
        private à() {
            this.HorizonCode_Horizon_È = true;
            this.Â = true;
            this.Ý = true;
            this.Ø­áŒŠá = true;
        }
        
        à(final Å p_i46263_1_) {
            this();
        }
    }
    
    static class Ø
    {
        public Ý HorizonCode_Horizon_È;
        public int Â;
        public int Ý;
        private static final String Ø­áŒŠá = "CL_00002549";
        
        private Ø() {
            this.HorizonCode_Horizon_È = new Ý(2903);
            this.Â = 1032;
            this.Ý = 5634;
        }
        
        Ø(final Å p_i46262_1_) {
            this();
        }
    }
    
    static class áŒŠÆ
    {
        public Ý HorizonCode_Horizon_È;
        public int Â;
        private static final String Ý = "CL_00002548";
        
        private áŒŠÆ() {
            this.HorizonCode_Horizon_È = new Ý(2884);
            this.Â = 1029;
        }
        
        áŒŠÆ(final Å p_i46261_1_) {
            this();
        }
    }
    
    static class áˆºÑ¢Õ
    {
        public Ý HorizonCode_Horizon_È;
        public boolean Â;
        public int Ý;
        private static final String Ø­áŒŠá = "CL_00002547";
        
        private áˆºÑ¢Õ() {
            this.HorizonCode_Horizon_È = new Ý(2929);
            this.Â = true;
            this.Ý = 513;
        }
        
        áˆºÑ¢Õ(final Å p_i46260_1_) {
            this();
        }
    }
    
    static class ÂµÈ
    {
        public Ý HorizonCode_Horizon_È;
        public int Â;
        public float Ý;
        public float Ø­áŒŠá;
        public float Âµá€;
        private static final String Ó = "CL_00002546";
        
        private ÂµÈ() {
            this.HorizonCode_Horizon_È = new Ý(2912);
            this.Â = 2048;
            this.Ý = 1.0f;
            this.Ø­áŒŠá = 0.0f;
            this.Âµá€ = 1.0f;
        }
        
        ÂµÈ(final Å p_i46259_1_) {
            this();
        }
    }
    
    static class á
    {
        public Ý HorizonCode_Horizon_È;
        public Ý Â;
        public float Ý;
        public float Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002545";
        
        private á() {
            this.HorizonCode_Horizon_È = new Ý(32823);
            this.Â = new Ý(10754);
            this.Ý = 0.0f;
            this.Ø­áŒŠá = 0.0f;
        }
        
        á(final Å p_i46258_1_) {
            this();
        }
    }
    
    static class ˆÏ­
    {
        public int HorizonCode_Horizon_È;
        public int Â;
        public int Ý;
        private static final String Ø­áŒŠá = "CL_00002544";
        
        private ˆÏ­() {
            this.HorizonCode_Horizon_È = 519;
            this.Â = 0;
            this.Ý = -1;
        }
        
        ˆÏ­(final Å p_i46257_1_) {
            this();
        }
    }
    
    static class £á
    {
        public ˆÏ­ HorizonCode_Horizon_È;
        public int Â;
        public int Ý;
        public int Ø­áŒŠá;
        public int Âµá€;
        private static final String Ó = "CL_00002543";
        
        private £á() {
            this.HorizonCode_Horizon_È = new ˆÏ­(null);
            this.Â = -1;
            this.Ý = 7680;
            this.Ø­áŒŠá = 7680;
            this.Âµá€ = 7680;
        }
        
        £á(final Å p_i46256_1_) {
            this();
        }
    }
    
    static final class Å
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002557";
        
        static {
            HorizonCode_Horizon_È = new int[GlStateManager.£à.values().length];
            try {
                Å.HorizonCode_Horizon_È[GlStateManager.£à.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Å.HorizonCode_Horizon_È[GlStateManager.£à.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Å.HorizonCode_Horizon_È[GlStateManager.£à.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                Å.HorizonCode_Horizon_È[GlStateManager.£à.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
    
    static class µà
    {
        public Ý HorizonCode_Horizon_È;
        public int Â;
        public int Ý;
        private static final String Ø­áŒŠá = "CL_00002541";
        
        public µà(final int p_i46254_1_, final int p_i46254_2_) {
            this.Ý = -1;
            this.Â = p_i46254_1_;
            this.HorizonCode_Horizon_È = new Ý(p_i46254_2_);
        }
    }
    
    static class ˆà
    {
        public µà HorizonCode_Horizon_È;
        public µà Â;
        public µà Ý;
        public µà Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002540";
        
        private ˆà() {
            this.HorizonCode_Horizon_È = new µà(8192, 3168);
            this.Â = new µà(8193, 3169);
            this.Ý = new µà(8194, 3170);
            this.Ø­áŒŠá = new µà(8195, 3171);
        }
        
        ˆà(final Å p_i46253_1_) {
            this();
        }
    }
    
    static class ¥Æ
    {
        public Ý HorizonCode_Horizon_È;
        public int Â;
        private static final String Ý = "CL_00002539";
        
        private ¥Æ() {
            this.HorizonCode_Horizon_È = new Ý(3553);
            this.Â = 0;
        }
        
        ¥Æ(final Å p_i46252_1_) {
            this();
        }
    }
    
    static class Ø­à
    {
        public int HorizonCode_Horizon_È;
        public int Â;
        public int Ý;
        public int Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002538";
        
        private Ø­à() {
            this.HorizonCode_Horizon_È = 0;
            this.Â = 0;
            this.Ý = 0;
            this.Ø­áŒŠá = 0;
        }
        
        Ø­à(final Å p_i46251_1_) {
            this();
        }
    }
}
