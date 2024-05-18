/*     */ package net.minecraft.client.gui.stream;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.stream.IStream;
/*     */ import net.minecraft.client.stream.NullStream;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Session;
/*     */ import net.minecraft.util.Util;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import tv.twitch.ErrorCode;
/*     */ 
/*     */ public class GuiStreamUnavailable
/*     */   extends GuiScreen {
/*  27 */   private static final Logger field_152322_a = LogManager.getLogger();
/*     */   
/*     */   private final IChatComponent field_152324_f;
/*     */   private final GuiScreen parentScreen;
/*     */   private final Reason field_152326_h;
/*     */   private final List<ChatComponentTranslation> field_152327_i;
/*     */   private final List<String> field_152323_r;
/*     */   
/*     */   public GuiStreamUnavailable(GuiScreen p_i1070_1_, Reason p_i1070_2_) {
/*  36 */     this(p_i1070_1_, p_i1070_2_, (List<ChatComponentTranslation>)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiStreamUnavailable(GuiScreen parentScreenIn, Reason p_i46311_2_, List<ChatComponentTranslation> p_i46311_3_) {
/*  41 */     this.field_152324_f = (IChatComponent)new ChatComponentTranslation("stream.unavailable.title", new Object[0]);
/*  42 */     this.field_152323_r = Lists.newArrayList();
/*  43 */     this.parentScreen = parentScreenIn;
/*  44 */     this.field_152326_h = p_i46311_2_;
/*  45 */     this.field_152327_i = p_i46311_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  54 */     if (this.field_152323_r.isEmpty()) {
/*     */       
/*  56 */       this.field_152323_r.addAll(this.fontRendererObj.listFormattedStringToWidth(this.field_152326_h.func_152561_a().getFormattedText(), (int)(this.width * 0.75F)));
/*     */       
/*  58 */       if (this.field_152327_i != null) {
/*     */         
/*  60 */         this.field_152323_r.add("");
/*     */         
/*  62 */         for (ChatComponentTranslation chatcomponenttranslation : this.field_152327_i)
/*     */         {
/*  64 */           this.field_152323_r.add(chatcomponenttranslation.getUnformattedTextForChat());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     if (this.field_152326_h.func_152559_b() != null) {
/*     */       
/*  71 */       this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 50, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  72 */       this.buttonList.add(new GuiButton(1, this.width / 2 - 155 + 160, this.height - 50, 150, 20, I18n.format(this.field_152326_h.func_152559_b().getFormattedText(), new Object[0])));
/*     */     }
/*     */     else {
/*     */       
/*  76 */       this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height - 50, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  92 */     drawDefaultBackground();
/*  93 */     int i = Math.max((int)(this.height * 0.85D / 2.0D - ((this.field_152323_r.size() * this.fontRendererObj.FONT_HEIGHT) / 2.0F)), 50);
/*  94 */     drawCenteredString(this.fontRendererObj, this.field_152324_f.getFormattedText(), this.width / 2, i - this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
/*     */     
/*  96 */     for (String s : this.field_152323_r) {
/*     */       
/*  98 */       drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 10526880);
/*  99 */       i += this.fontRendererObj.FONT_HEIGHT;
/*     */     } 
/*     */     
/* 102 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 112 */     if (button.enabled) {
/*     */       
/* 114 */       if (button.id == 1)
/*     */       {
/* 116 */         switch (this.field_152326_h) {
/*     */           
/*     */           case null:
/*     */           case FAILED_TWITCH_AUTH:
/* 120 */             func_152320_a("https://account.mojang.com/me/settings");
/*     */             break;
/*     */           
/*     */           case ACCOUNT_NOT_MIGRATED:
/* 124 */             func_152320_a("https://account.mojang.com/migrate");
/*     */             break;
/*     */           
/*     */           case UNSUPPORTED_OS_MAC:
/* 128 */             func_152320_a("http://www.apple.com/osx/");
/*     */             break;
/*     */           
/*     */           case LIBRARY_FAILURE:
/*     */           case INITIALIZATION_FAILURE:
/*     */           case UNKNOWN:
/* 134 */             func_152320_a("http://bugs.mojang.com/browse/MC");
/*     */             break;
/*     */         } 
/*     */       }
/* 138 */       this.mc.displayGuiScreen(this.parentScreen);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_152320_a(String p_152320_1_) {
/*     */     try {
/* 146 */       Class<?> oclass = Class.forName("java.awt.Desktop");
/* 147 */       Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 148 */       oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI(p_152320_1_) });
/*     */     }
/* 150 */     catch (Throwable throwable) {
/*     */       
/* 152 */       field_152322_a.error("Couldn't open link", throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_152321_a(GuiScreen p_152321_0_) {
/* 158 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 159 */     IStream istream = minecraft.getTwitchStream();
/*     */     
/* 161 */     if (!OpenGlHelper.framebufferSupported) {
/*     */       
/* 163 */       List<ChatComponentTranslation> list = Lists.newArrayList();
/* 164 */       list.add(new ChatComponentTranslation("stream.unavailable.no_fbo.version", new Object[] { GL11.glGetString(7938) }));
/* 165 */       list.add(new ChatComponentTranslation("stream.unavailable.no_fbo.blend", new Object[] { Boolean.valueOf((GLContext.getCapabilities()).GL_EXT_blend_func_separate) }));
/* 166 */       list.add(new ChatComponentTranslation("stream.unavailable.no_fbo.arb", new Object[] { Boolean.valueOf((GLContext.getCapabilities()).GL_ARB_framebuffer_object) }));
/* 167 */       list.add(new ChatComponentTranslation("stream.unavailable.no_fbo.ext", new Object[] { Boolean.valueOf((GLContext.getCapabilities()).GL_EXT_framebuffer_object) }));
/* 168 */       minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.NO_FBO, list));
/*     */     }
/* 170 */     else if (istream instanceof NullStream) {
/*     */       
/* 172 */       if (((NullStream)istream).func_152937_a().getMessage().contains("Can't load AMD 64-bit .dll on a IA 32-bit platform"))
/*     */       {
/* 174 */         minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.LIBRARY_ARCH_MISMATCH));
/*     */       }
/*     */       else
/*     */       {
/* 178 */         minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.LIBRARY_FAILURE));
/*     */       }
/*     */     
/* 181 */     } else if (!istream.func_152928_D() && istream.func_152912_E() == ErrorCode.TTV_EC_OS_TOO_OLD) {
/*     */       
/* 183 */       switch (Util.getOSType()) {
/*     */         
/*     */         case WINDOWS:
/* 186 */           minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.UNSUPPORTED_OS_WINDOWS));
/*     */           return;
/*     */         
/*     */         case OSX:
/* 190 */           minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.UNSUPPORTED_OS_MAC));
/*     */           return;
/*     */       } 
/*     */       
/* 194 */       minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.UNSUPPORTED_OS_OTHER));
/*     */     
/*     */     }
/* 197 */     else if (!minecraft.getTwitchDetails().containsKey("twitch_access_token")) {
/*     */       
/* 199 */       if (minecraft.getSession().getSessionType() == Session.Type.LEGACY)
/*     */       {
/* 201 */         minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.ACCOUNT_NOT_MIGRATED));
/*     */       }
/*     */       else
/*     */       {
/* 205 */         minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.ACCOUNT_NOT_BOUND));
/*     */       }
/*     */     
/* 208 */     } else if (!istream.func_152913_F()) {
/*     */       
/* 210 */       switch (istream.func_152918_H()) {
/*     */         
/*     */         case INVALID_TOKEN:
/* 213 */           minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.FAILED_TWITCH_AUTH));
/*     */           return;
/*     */       } 
/*     */ 
/*     */       
/* 218 */       minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.FAILED_TWITCH_AUTH_ERROR));
/*     */     
/*     */     }
/* 221 */     else if (istream.func_152912_E() != null) {
/*     */       
/* 223 */       List<ChatComponentTranslation> list1 = Arrays.asList(new ChatComponentTranslation[] { new ChatComponentTranslation("stream.unavailable.initialization_failure.extra", new Object[] { ErrorCode.getString(istream.func_152912_E()) }) });
/* 224 */       minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.INITIALIZATION_FAILURE, list1));
/*     */     }
/*     */     else {
/*     */       
/* 228 */       minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.UNKNOWN));
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum Reason
/*     */   {
/* 234 */     NO_FBO((String)new ChatComponentTranslation("stream.unavailable.no_fbo", new Object[0])),
/* 235 */     LIBRARY_ARCH_MISMATCH((String)new ChatComponentTranslation("stream.unavailable.library_arch_mismatch", new Object[0])),
/* 236 */     LIBRARY_FAILURE((String)new ChatComponentTranslation("stream.unavailable.library_failure", new Object[0]), new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])),
/* 237 */     UNSUPPORTED_OS_WINDOWS((String)new ChatComponentTranslation("stream.unavailable.not_supported.windows", new Object[0])),
/* 238 */     UNSUPPORTED_OS_MAC((String)new ChatComponentTranslation("stream.unavailable.not_supported.mac", new Object[0]), new ChatComponentTranslation("stream.unavailable.not_supported.mac.okay", new Object[0])),
/* 239 */     UNSUPPORTED_OS_OTHER((String)new ChatComponentTranslation("stream.unavailable.not_supported.other", new Object[0])),
/* 240 */     ACCOUNT_NOT_MIGRATED((String)new ChatComponentTranslation("stream.unavailable.account_not_migrated", new Object[0]), new ChatComponentTranslation("stream.unavailable.account_not_migrated.okay", new Object[0])),
/* 241 */     ACCOUNT_NOT_BOUND((String)new ChatComponentTranslation("stream.unavailable.account_not_bound", new Object[0]), new ChatComponentTranslation("stream.unavailable.account_not_bound.okay", new Object[0])),
/* 242 */     FAILED_TWITCH_AUTH((String)new ChatComponentTranslation("stream.unavailable.failed_auth", new Object[0]), new ChatComponentTranslation("stream.unavailable.failed_auth.okay", new Object[0])),
/* 243 */     FAILED_TWITCH_AUTH_ERROR((String)new ChatComponentTranslation("stream.unavailable.failed_auth_error", new Object[0])),
/* 244 */     INITIALIZATION_FAILURE((String)new ChatComponentTranslation("stream.unavailable.initialization_failure", new Object[0]), new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])),
/* 245 */     UNKNOWN((String)new ChatComponentTranslation("stream.unavailable.unknown", new Object[0]), new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0]));
/*     */ 
/*     */ 
/*     */     
/*     */     private final IChatComponent field_152574_m;
/*     */ 
/*     */     
/*     */     private final IChatComponent field_152575_n;
/*     */ 
/*     */ 
/*     */     
/*     */     Reason(IChatComponent p_i1067_3_, IChatComponent p_i1067_4_) {
/* 257 */       this.field_152574_m = p_i1067_3_;
/* 258 */       this.field_152575_n = p_i1067_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public IChatComponent func_152561_a() {
/* 263 */       return this.field_152574_m;
/*     */     }
/*     */ 
/*     */     
/*     */     public IChatComponent func_152559_b() {
/* 268 */       return this.field_152575_n;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\stream\GuiStreamUnavailable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */