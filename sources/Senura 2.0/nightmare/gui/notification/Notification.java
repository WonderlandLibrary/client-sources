/*     */ package nightmare.gui.notification;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.fonts.impl.Fonts;
/*     */ import nightmare.utils.AnimationUtils;
/*     */ import nightmare.utils.ColorUtils;
/*     */ import nightmare.utils.TimerUtils;
/*     */ import nightmare.utils.render.BlurUtils;
/*     */ import nightmare.utils.render.RenderUtils;
/*     */ 
/*     */ 
/*     */ public class Notification
/*     */ {
/*  17 */   public static Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*     */   public String message;
/*     */   
/*     */   public String title;
/*     */   
/*     */   public String icon;
/*     */   
/*     */   private NotificationType notificationType;
/*     */   
/*     */   private TimerUtils timer;
/*     */   private float animationX;
/*     */   private float animationY;
/*  30 */   public static float rotateDirection = 0.0F; private float width; private final float height; private int delay; private float x1; private float x2; private float y1;
/*     */   private float y2;
/*     */   public static boolean animationDone = false;
/*     */   private int notificationColor;
/*     */   
/*     */   public Notification(NotificationType notificationType, String title, String message, int delay) {
/*  36 */     this.notificationType = notificationType;
/*  37 */     this.message = message;
/*  38 */     this.title = title;
/*  39 */     this.delay = delay;
/*     */     
/*  41 */     if (Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(title) < Fonts.REGULAR.REGULAR_16.REGULAR_16.getStringWidth(message)) {
/*  42 */       this.width = (Fonts.REGULAR.REGULAR_16.REGULAR_16.getStringWidth(message) + 45);
/*     */     } else {
/*  44 */       this.width = (Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(title) + 45);
/*     */     } 
/*     */     
/*  47 */     this.height = 22.0F;
/*  48 */     this.animationX = 140.0F;
/*  49 */     this.timer = new TimerUtils();
/*  50 */     this.timer.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float offsetY) {
/*  55 */     float target = isFinished() ? this.width : 0.0F;
/*     */     
/*  57 */     if (this.animationY == 0.0F) {
/*  58 */       this.animationY = offsetY;
/*     */     }
/*     */     
/*  61 */     if (this.notificationType.equals(NotificationType.INFO)) {
/*  62 */       this.notificationColor = (new Color(220, 220, 220)).getRGB();
/*  63 */       this.icon = "F";
/*  64 */     } else if (this.notificationType.equals(NotificationType.SUCCESS)) {
/*  65 */       this.notificationColor = (new Color(80, 200, 150)).getRGB();
/*  66 */       this.icon = "A";
/*  67 */     } else if (this.notificationType.equals(NotificationType.WARNING)) {
/*  68 */       this.notificationColor = (new Color(220, 220, 100)).getRGB();
/*  69 */       this.icon = "C";
/*  70 */     } else if (this.notificationType.equals(NotificationType.ERROR)) {
/*  71 */       this.notificationColor = (new Color(250, 55, 55)).getRGB();
/*  72 */       this.icon = "B";
/*     */     } 
/*     */     
/*  75 */     this.animationX = AnimationUtils.setAnimation(this.animationX, target, Math.max(10.0F, Math.abs(this.animationX - target) * 40.0F) * 0.4F);
/*  76 */     this.animationY = AnimationUtils.setAnimation(this.animationY, offsetY, Math.max(10.0F, Math.abs(this.animationY - offsetY) * 40.0F) * 0.3F);
/*     */     
/*  78 */     this.x1 = x - this.width + this.animationX;
/*  79 */     this.x2 = x + this.animationX + 0.0F;
/*     */     
/*  81 */     this.y1 = this.animationY - 2.0F;
/*  82 */     this.y2 = this.y1 + this.height;
/*     */     
/*  84 */     float f1 = MathHelper.func_76131_a((float)(this.timer.getCurrentMS() - this.timer.getLastMS()), 0.0F, this.delay);
/*  85 */     float f2 = f1 / this.delay;
/*     */     
/*  87 */     if (Nightmare.instance.moduleManager.getModuleByName("Blur").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Blur"), "Notification").getValBoolean()) {
/*  88 */       BlurUtils.drawBlurRect(this.x1 + 35.0F, this.y1, this.x2, this.y2);
/*     */     }
/*     */     
/*  91 */     RenderUtils.drawRect(this.x1 + 35.0F, this.y1, this.x2, this.y2, ColorUtils.getBackgroundColor());
/*  92 */     RenderUtils.drawRect(this.x1 + 35.0F, this.y2 - 1.0F, this.x1 + 35.0F + f2 * (this.width - 29.0F), this.y2, this.notificationColor);
/*  93 */     Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.title, this.x1 + 40.0F, this.y1 + 3.0F, -1);
/*  94 */     Fonts.REGULAR.REGULAR_16.REGULAR_16.drawString(this.message, this.x1 + 40.0F, this.y1 + 14.0F, -1);
/*  95 */     Fonts.ICON.ICON_16.ICON_16.drawString(this.icon, this.x1 + Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(this.title) + 41.5F, this.y1 + 5.0F, this.notificationColor);
/*     */   }
/*     */   
/*     */   public boolean shouldDelete() {
/*  99 */     return (isFinished() && this.animationX == this.width);
/*     */   }
/*     */   
/*     */   public float getHeight() {
/* 103 */     return this.height;
/*     */   }
/*     */   
/*     */   private boolean isFinished() {
/* 107 */     return this.timer.delay(this.delay);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\gui\notification\Notification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */