/*     */ package nightmare.gui.window.impl;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.fonts.impl.Fonts;
/*     */ import nightmare.gui.window.Window;
/*     */ import nightmare.utils.ColorUtils;
/*     */ import nightmare.utils.render.BlurUtils;
/*     */ import nightmare.utils.render.RenderUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TargetHUD
/*     */   extends Window
/*     */ {
/*  31 */   private EntityLivingBase target = null;
/*     */   
/*     */   private float health;
/*     */   private float armor;
/*  35 */   private Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*     */   public TargetHUD() {
/*  38 */     super("TargetHUD", 30, 30, 131, 34, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRender() {
/*  44 */     this.target = getClosest(Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Distance").getValDouble());
/*     */     
/*  46 */     if (this.mc.field_71462_r instanceof nightmare.clickgui.ClickGUI) {
/*  47 */       this.target = (EntityLivingBase)this.mc.field_71439_g;
/*     */     }
/*     */     
/*  50 */     if (this.target != null) {
/*     */       
/*  52 */       this.health = (this.target.func_110143_aJ() + this.target.func_110139_bj() < 20.0F) ? (this.target.func_110143_aJ() + this.target.func_110139_bj()) : 20.0F;
/*  53 */       this.armor = this.target.func_70658_aO();
/*     */       
/*  55 */       int i1 = (int)(this.target.field_70737_aN * 0.35F);
/*  56 */       double d1 = (this.target.field_70737_aN * 23);
/*     */       
/*  58 */       if (Nightmare.instance.moduleManager.getModuleByName("Blur").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Blur"), "TargetHUD").getValBoolean()) {
/*  59 */         BlurUtils.drawBlurRect(getX(), getY(), getWidth(), getHeight());
/*     */       }
/*     */       
/*  62 */       RenderUtils.drawBorderedRect(getX(), getY(), getWidth(), getHeight(), 2.0F, ColorUtils.getBackgroundColor(), (new Color(90, 90, 90, 180)).getRGB());
/*     */       
/*  64 */       RenderUtils.setColor(new Color(255, (int)(255.0D - d1), (int)(255.0D - d1)));
/*  65 */       renderPlayerFace(((getX() + 2) + i1 / 2.0F), ((getY() + 2) + i1 / 2.0F), 3.0F, 3.0F, 3, 3, 30 - i1, 30 - i1, 24.0F, 24.5F, (AbstractClientPlayer)this.target);
/*  66 */       RenderUtils.setColor(new Color(255, 255, 255));
/*     */       
/*  68 */       Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.target.func_70005_c_(), (getX() + 35), (getY() + 4), -1);
/*  69 */       Fonts.ICON.ICON_16.ICON_16.drawString("D", (getX() + 35), (getY() + 16), (new Color(255, 80, 80)).getRGB());
/*  70 */       Fonts.ICON.ICON_16.ICON_16.drawString("E", (getX() + 35), (getY() + 26), (new Color(150, 150, 150)).getRGB());
/*  71 */       RenderUtils.drawRect((getX() + 45), (getY() + 14), (getX() + 128), (getY() + 20), (new Color(100, 100, 100)).getRGB());
/*  72 */       RenderUtils.drawRect((getX() + 45), (getY() + 14), getX() + this.health * 4.0F + 48.0F, (getY() + 20), (new Color(10, 150, 75)).getRGB());
/*  73 */       RenderUtils.drawRect((getX() + 45), (getY() + 24), (getX() + 128), (getY() + 30), (new Color(100, 100, 100)).getRGB());
/*  74 */       RenderUtils.drawRect((getX() + 45), (getY() + 24), getX() + this.armor * 4.0F + 48.0F, (getY() + 30), (new Color(12, 180, 250)).getRGB());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canAttack(EntityLivingBase player) {
/*  80 */     if (player instanceof net.minecraft.entity.passive.EntityAnimal || player instanceof net.minecraft.entity.passive.EntitySquid || player instanceof net.minecraft.entity.monster.EntityMob || player instanceof net.minecraft.entity.passive.EntityVillager || player instanceof net.minecraft.entity.item.EntityArmorStand || player instanceof net.minecraft.entity.passive.EntityBat || player.func_145748_c_().func_150254_d().contains("[NPC]")) {
/*  81 */       return false;
/*     */     }
/*     */     
/*  84 */     return (player != this.mc.field_71439_g && player.func_70089_S() && this.mc.field_71439_g.func_70032_d((Entity)player) <= (float)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Distance").getValDouble());
/*     */   }
/*     */   
/*     */   private EntityLivingBase getClosest(double range) {
/*  88 */     double dist = range;
/*  89 */     EntityLivingBase target = null;
/*  90 */     for (Object object : this.mc.field_71441_e.field_72996_f) {
/*  91 */       Entity entity = (Entity)object;
/*  92 */       if (entity instanceof EntityLivingBase) {
/*  93 */         EntityLivingBase player = (EntityLivingBase)entity;
/*  94 */         if (canAttack(player)) {
/*  95 */           double currentDist = this.mc.field_71439_g.func_70032_d((Entity)player);
/*  96 */           if (currentDist <= dist) {
/*  97 */             dist = currentDist;
/*  98 */             target = player;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 103 */     return target;
/*     */   }
/*     */   
/*     */   public void renderPlayerFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
/* 107 */     ResourceLocation resourceLocation = target.func_110306_p();
/* 108 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation);
/* 109 */     GL11.glEnable(3042);
/* 110 */     drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
/* 111 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScaledCustomSizeModalRect(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/* 116 */     float f = 1.0F / tileWidth;
/* 117 */     float f1 = 1.0F / tileHeight;
/* 118 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 119 */     WorldRenderer worldrenderer = tessellator.func_178180_c();
/* 120 */     worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 121 */     worldrenderer.func_181662_b(x, y + height, 0.0D).func_181673_a((u * f), ((v + vHeight) * f1)).func_181675_d();
/* 122 */     worldrenderer.func_181662_b(x + width, y + height, 0.0D).func_181673_a(((u + uWidth) * f), ((v + vHeight) * f1)).func_181675_d();
/* 123 */     worldrenderer.func_181662_b(x + width, y, 0.0D).func_181673_a(((u + uWidth) * f), (v * f1)).func_181675_d();
/* 124 */     worldrenderer.func_181662_b(x, y, 0.0D).func_181673_a((u * f), (v * f1)).func_181675_d();
/* 125 */     tessellator.func_78381_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\gui\window\impl\TargetHUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */