package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.Arrays;
import tv.twitch.ErrorCode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.GL11;
import java.net.URI;
import java.io.IOException;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class GuiStreamUnavailable extends GuiScreen
{
    private static final Logger HorizonCode_Horizon_È;
    private final IChatComponent Â;
    private final GuiScreen Ý;
    private final HorizonCode_Horizon_È Ø­áŒŠá;
    private final List Âµá€;
    private final List Ó;
    private static final String à = "CL_00001840";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public GuiStreamUnavailable(final GuiScreen p_i1070_1_, final HorizonCode_Horizon_È p_i1070_2_) {
        this(p_i1070_1_, p_i1070_2_, null);
    }
    
    public GuiStreamUnavailable(final GuiScreen p_i46311_1_, final HorizonCode_Horizon_È p_i46311_2_, final List p_i46311_3_) {
        this.Â = new ChatComponentTranslation("stream.unavailable.title", new Object[0]);
        this.Ó = Lists.newArrayList();
        this.Ý = p_i46311_1_;
        this.Ø­áŒŠá = p_i46311_2_;
        this.Âµá€ = p_i46311_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.Ó.isEmpty()) {
            this.Ó.addAll(this.É.Ý(this.Ø­áŒŠá.HorizonCode_Horizon_È().áŒŠÆ(), (int)(GuiStreamUnavailable.Çªà¢ * 0.75f)));
            if (this.Âµá€ != null) {
                this.Ó.add("");
                for (final ChatComponentTranslation var2 : this.Âµá€) {
                    this.Ó.add(var2.Ý());
                }
            }
        }
        if (this.Ø­áŒŠá.Â() != null) {
            this.ÇŽÉ.add(new GuiMenuButton(0, GuiStreamUnavailable.Çªà¢ / 2 - 155, GuiStreamUnavailable.Ê - 50, 150, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
            this.ÇŽÉ.add(new GuiMenuButton(1, GuiStreamUnavailable.Çªà¢ / 2 - 155 + 160, GuiStreamUnavailable.Ê - 50, 150, 20, I18n.HorizonCode_Horizon_È(this.Ø­áŒŠá.Â().áŒŠÆ(), new Object[0])));
        }
        else {
            this.ÇŽÉ.add(new GuiMenuButton(0, GuiStreamUnavailable.Çªà¢ / 2 - 75, GuiStreamUnavailable.Ê - 50, 150, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        }
    }
    
    @Override
    public void q_() {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(0.0f, 0.0f, GuiScreen.Çªà¢, (float)GuiScreen.Ê, -8418163);
        int var4 = Math.max((int)(GuiStreamUnavailable.Ê * 0.85 / 2.0 - this.Ó.size() * this.É.HorizonCode_Horizon_È / 2.0f), 50);
        this.HorizonCode_Horizon_È(this.É, this.Â.áŒŠÆ(), GuiStreamUnavailable.Çªà¢ / 2, var4 - this.É.HorizonCode_Horizon_È * 2, 16777215);
        for (final String var6 : this.Ó) {
            this.HorizonCode_Horizon_È(this.É, var6, GuiStreamUnavailable.Çªà¢ / 2, var4, 10526880);
            var4 += this.É.HorizonCode_Horizon_È;
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiStreamUnavailable.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/mainmenusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 1) {
                switch (GuiStreamUnavailable.Â.HorizonCode_Horizon_È[this.Ø­áŒŠá.ordinal()]) {
                    case 1:
                    case 2: {
                        this.HorizonCode_Horizon_È("https://account.mojang.com/me/settings");
                        break;
                    }
                    case 3: {
                        this.HorizonCode_Horizon_È("https://account.mojang.com/migrate");
                        break;
                    }
                    case 4: {
                        this.HorizonCode_Horizon_È("http://www.apple.com/osx/");
                        break;
                    }
                    case 5:
                    case 6:
                    case 7: {
                        this.HorizonCode_Horizon_È("http://bugs.mojang.com/browse/MC");
                        break;
                    }
                }
            }
            GuiStreamUnavailable.Ñ¢á.HorizonCode_Horizon_È(this.Ý);
        }
    }
    
    private void HorizonCode_Horizon_È(final String p_152320_1_) {
        try {
            final Class var2 = Class.forName("java.awt.Desktop");
            final Object var3 = var2.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
            var2.getMethod("browse", URI.class).invoke(var3, new URI(p_152320_1_));
        }
        catch (Throwable var4) {
            GuiStreamUnavailable.HorizonCode_Horizon_È.error("Couldn't open link", var4);
        }
    }
    
    public static void HorizonCode_Horizon_È(final GuiScreen p_152321_0_) {
        final Minecraft var1 = Minecraft.áŒŠà();
        final IStream var2 = var1.Ä();
        if (!OpenGlHelper.ÂµÈ) {
            final ArrayList var3 = Lists.newArrayList();
            var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.version", new Object[] { GL11.glGetString(7938) }));
            var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.blend", new Object[] { GLContext.getCapabilities().GL_EXT_blend_func_separate }));
            var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.arb", new Object[] { GLContext.getCapabilities().GL_ARB_framebuffer_object }));
            var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.ext", new Object[] { GLContext.getCapabilities().GL_EXT_framebuffer_object }));
            var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.HorizonCode_Horizon_È, var3));
        }
        else if (var2 instanceof NullStream) {
            if (((NullStream)var2).HorizonCode_Horizon_È().getMessage().contains("Can't load AMD 64-bit .dll on a IA 32-bit platform")) {
                var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.Â));
            }
            else {
                var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.Ý));
            }
        }
        else if (!var2.ŠÄ() && var2.Ñ¢á() == ErrorCode.TTV_EC_OS_TOO_OLD) {
            switch (Â.Â[Util_1252169911.HorizonCode_Horizon_È().ordinal()]) {
                case 1: {
                    var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.Ø­áŒŠá));
                    break;
                }
                case 2: {
                    var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.Âµá€));
                    break;
                }
                default: {
                    var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.Ó));
                    break;
                }
            }
        }
        else if (!var1.à¢().containsKey((Object)"twitch_access_token")) {
            if (var1.Õ().Ó() == Session.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.à));
            }
            else {
                var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.Ø));
            }
        }
        else if (!var2.ŒÏ()) {
            switch (Â.Ý[var2.Ê().ordinal()]) {
                case 1: {
                    var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.áŒŠÆ));
                    break;
                }
                default: {
                    var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.áˆºÑ¢Õ));
                    break;
                }
            }
        }
        else if (var2.Ñ¢á() != null) {
            final List var4 = Arrays.asList(new ChatComponentTranslation("stream.unavailable.initialization_failure.extra", new Object[] { ErrorCode.getString(var2.Ñ¢á()) }));
            var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.ÂµÈ, var4));
        }
        else {
            var1.HorizonCode_Horizon_È(new GuiStreamUnavailable(p_152321_0_, GuiStreamUnavailable.HorizonCode_Horizon_È.á));
        }
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("NO_FBO", 0, "NO_FBO", 0, (IChatComponent)new ChatComponentTranslation("stream.unavailable.no_fbo", new Object[0])), 
        Â("LIBRARY_ARCH_MISMATCH", 1, "LIBRARY_ARCH_MISMATCH", 1, (IChatComponent)new ChatComponentTranslation("stream.unavailable.library_arch_mismatch", new Object[0])), 
        Ý("LIBRARY_FAILURE", 2, "LIBRARY_FAILURE", 2, (IChatComponent)new ChatComponentTranslation("stream.unavailable.library_failure", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])), 
        Ø­áŒŠá("UNSUPPORTED_OS_WINDOWS", 3, "UNSUPPORTED_OS_WINDOWS", 3, (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.windows", new Object[0])), 
        Âµá€("UNSUPPORTED_OS_MAC", 4, "UNSUPPORTED_OS_MAC", 4, (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.mac", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.mac.okay", new Object[0])), 
        Ó("UNSUPPORTED_OS_OTHER", 5, "UNSUPPORTED_OS_OTHER", 5, (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.other", new Object[0])), 
        à("ACCOUNT_NOT_MIGRATED", 6, "ACCOUNT_NOT_MIGRATED", 6, (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_migrated", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_migrated.okay", new Object[0])), 
        Ø("ACCOUNT_NOT_BOUND", 7, "ACCOUNT_NOT_BOUND", 7, (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_bound", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_bound.okay", new Object[0])), 
        áŒŠÆ("FAILED_TWITCH_AUTH", 8, "FAILED_TWITCH_AUTH", 8, (IChatComponent)new ChatComponentTranslation("stream.unavailable.failed_auth", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.failed_auth.okay", new Object[0])), 
        áˆºÑ¢Õ("FAILED_TWITCH_AUTH_ERROR", 9, "FAILED_TWITCH_AUTH_ERROR", 9, (IChatComponent)new ChatComponentTranslation("stream.unavailable.failed_auth_error", new Object[0])), 
        ÂµÈ("INITIALIZATION_FAILURE", 10, "INITIALIZATION_FAILURE", 10, (IChatComponent)new ChatComponentTranslation("stream.unavailable.initialization_failure", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])), 
        á("UNKNOWN", 11, "UNKNOWN", 11, (IChatComponent)new ChatComponentTranslation("stream.unavailable.unknown", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0]));
        
        private final IChatComponent ˆÏ­;
        private final IChatComponent £á;
        private static final HorizonCode_Horizon_È[] Å;
        private static final String £à = "CL_00001838";
        
        static {
            µà = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á };
            Å = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i1066_1_, final int p_i1066_2_, final IChatComponent p_i1066_3_) {
            this(s, n, p_i1066_1_, p_i1066_2_, p_i1066_3_, null);
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i1067_1_, final int p_i1067_2_, final IChatComponent p_i1067_3_, final IChatComponent p_i1067_4_) {
            this.ˆÏ­ = p_i1067_3_;
            this.£á = p_i1067_4_;
        }
        
        public IChatComponent HorizonCode_Horizon_È() {
            return this.ˆÏ­;
        }
        
        public IChatComponent Â() {
            return this.£á;
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        static final int[] Ý;
        private static final String Ø­áŒŠá = "CL_00001839";
        
        static {
            Ý = new int[IStream.HorizonCode_Horizon_È.values().length];
            try {
                GuiStreamUnavailable.Â.Ý[IStream.HorizonCode_Horizon_È.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                GuiStreamUnavailable.Â.Ý[IStream.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            Â = new int[Util_1252169911.HorizonCode_Horizon_È.values().length];
            try {
                GuiStreamUnavailable.Â.Â[Util_1252169911.HorizonCode_Horizon_È.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                GuiStreamUnavailable.Â.Â[Util_1252169911.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            HorizonCode_Horizon_È = new int[GuiStreamUnavailable.HorizonCode_Horizon_È.values().length];
            try {
                GuiStreamUnavailable.Â.HorizonCode_Horizon_È[GuiStreamUnavailable.HorizonCode_Horizon_È.Ø.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                GuiStreamUnavailable.Â.HorizonCode_Horizon_È[GuiStreamUnavailable.HorizonCode_Horizon_È.áŒŠÆ.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                GuiStreamUnavailable.Â.HorizonCode_Horizon_È[GuiStreamUnavailable.HorizonCode_Horizon_È.à.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                GuiStreamUnavailable.Â.HorizonCode_Horizon_È[GuiStreamUnavailable.HorizonCode_Horizon_È.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                GuiStreamUnavailable.Â.HorizonCode_Horizon_È[GuiStreamUnavailable.HorizonCode_Horizon_È.á.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                GuiStreamUnavailable.Â.HorizonCode_Horizon_È[GuiStreamUnavailable.HorizonCode_Horizon_È.Ý.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                GuiStreamUnavailable.Â.HorizonCode_Horizon_È[GuiStreamUnavailable.HorizonCode_Horizon_È.ÂµÈ.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
        }
    }
}
