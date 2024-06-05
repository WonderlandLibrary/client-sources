package ClassSub;

import java.time.format.*;
import cn.Hanabi.value.*;
import java.text.*;
import com.darkmagician6.eventapi.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import cn.Hanabi.events.*;
import cn.Hanabi.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import cn.Hanabi.modules.Player.*;
import cn.Hanabi.utils.fontmanager.*;
import net.minecraft.entity.boss.*;
import cn.Hanabi.modules.World.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import cn.Hanabi.modules.*;
import java.util.*;

public class Class118 extends Mod
{
    private static final DateTimeFormatter dateFormat;
    private static final DateTimeFormatter timeFormat;
    public Value<Boolean> arraylist;
    public Value<Boolean> logo;
    public static Value<Boolean> hotbar;
    public Value<Boolean> music;
    public Value<Boolean> potion;
    public Value<Boolean> noti;
    public static Value<Double> musicPosX;
    public static Value<Double> musicPosY;
    public static Value<Double> musicPosYlyr;
    SimpleDateFormat f;
    SimpleDateFormat f2;
    private float animationY2;
    Map<Potion, Double> timerMap;
    private int x;
    
    
    public Class118() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore          4
        //     5: goto            9
        //     8: athrow         
        //     9: aload_0        
        //    10: ldc             "HUD"
        //    12: getstatic       cn/Hanabi/modules/Category.RENDER:Lcn/Hanabi/modules/Category;
        //    15: ldc             0
        //    17: ldc             1
        //    19: ldc             0
        //    21: invokespecial   cn/Hanabi/modules/Mod.<init>:(Ljava/lang/String;Lcn/Hanabi/modules/Category;ZZI)V
        //    24: aload_0        
        //    25: new             Lcn/Hanabi/value/Value;
        //    28: dup            
        //    29: ldc             "HUD_ArrayList"
        //    31: ldc             1
        //    33: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    36: invokespecial   cn/Hanabi/value/Value.<init>:(Ljava/lang/String;Ljava/lang/Object;)V
        //    39: putfield        ClassSub/Class118.arraylist:Lcn/Hanabi/value/Value;
        //    42: aload_0        
        //    43: new             Lcn/Hanabi/value/Value;
        //    46: dup            
        //    47: ldc             "HUD_Logo"
        //    49: ldc             1
        //    51: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    54: invokespecial   cn/Hanabi/value/Value.<init>:(Ljava/lang/String;Ljava/lang/Object;)V
        //    57: putfield        ClassSub/Class118.logo:Lcn/Hanabi/value/Value;
        //    60: aload_0        
        //    61: new             Lcn/Hanabi/value/Value;
        //    64: dup            
        //    65: ldc             "HUD_MusicPlayer"
        //    67: ldc             1
        //    69: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    72: invokespecial   cn/Hanabi/value/Value.<init>:(Ljava/lang/String;Ljava/lang/Object;)V
        //    75: putfield        ClassSub/Class118.music:Lcn/Hanabi/value/Value;
        //    78: aload_0        
        //    79: new             Lcn/Hanabi/value/Value;
        //    82: dup            
        //    83: ldc             "HUD_Potion"
        //    85: ldc             1
        //    87: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    90: invokespecial   cn/Hanabi/value/Value.<init>:(Ljava/lang/String;Ljava/lang/Object;)V
        //    93: putfield        ClassSub/Class118.potion:Lcn/Hanabi/value/Value;
        //    96: aload_0        
        //    97: new             Lcn/Hanabi/value/Value;
        //   100: dup            
        //   101: ldc             "HUD_Notification"
        //   103: ldc             1
        //   105: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   108: invokespecial   cn/Hanabi/value/Value.<init>:(Ljava/lang/String;Ljava/lang/Object;)V
        //   111: putfield        ClassSub/Class118.noti:Lcn/Hanabi/value/Value;
        //   114: aload_0        
        //   115: new             Ljava/text/SimpleDateFormat;
        //   118: dup            
        //   119: ldc             "HH:mm"
        //   121: invokespecial   java/text/SimpleDateFormat.<init>:(Ljava/lang/String;)V
        //   124: putfield        ClassSub/Class118.f:Ljava/text/SimpleDateFormat;
        //   127: aload_0        
        //   128: new             Ljava/text/SimpleDateFormat;
        //   131: dup            
        //   132: ldc             "YYYY/MM/dd"
        //   134: invokespecial   java/text/SimpleDateFormat.<init>:(Ljava/lang/String;)V
        //   137: putfield        ClassSub/Class118.f2:Ljava/text/SimpleDateFormat;
        //   140: aload_0        
        //   141: new             Ljava/util/HashMap;
        //   144: dup            
        //   145: invokespecial   java/util/HashMap.<init>:()V
        //   148: putfield        ClassSub/Class118.timerMap:Ljava/util/Map;
        //   151: aload_0        
        //   152: ldc             1
        //   154: invokevirtual   ClassSub/Class118.setState:(Z)V
        //   157: new             Ljava/util/HashMap;
        //   160: dup            
        //   161: invokespecial   java/util/HashMap.<init>:()V
        //   164: astore_1       
        //   165: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //   168: getfield        cn/Hanabi/Hanabi.moduleManager:Lcn/Hanabi/modules/ModManager;
        //   171: invokevirtual   cn/Hanabi/modules/ModManager.getModules:()Ljava/util/List;
        //   174: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   179: astore_2       
        //   180: aload_2        
        //   181: invokeinterface java/util/Iterator.hasNext:()Z
        //   186: ifeq            247
        //   189: aload_2        
        //   190: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   195: checkcast       Lcn/Hanabi/modules/Mod;
        //   198: astore_3       
        //   199: aload_1        
        //   200: aload_3        
        //   201: invokevirtual   cn/Hanabi/modules/Mod.getCategory:()Lcn/Hanabi/modules/Category;
        //   204: invokevirtual   java/util/HashMap.containsKey:(Ljava/lang/Object;)Z
        //   207: ifne            226
        //   210: aload_1        
        //   211: aload_3        
        //   212: invokevirtual   cn/Hanabi/modules/Mod.getCategory:()Lcn/Hanabi/modules/Category;
        //   215: new             Ljava/util/ArrayList;
        //   218: dup            
        //   219: invokespecial   java/util/ArrayList.<init>:()V
        //   222: invokevirtual   java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   225: pop            
        //   226: aload_1        
        //   227: aload_3        
        //   228: invokevirtual   cn/Hanabi/modules/Mod.getCategory:()Lcn/Hanabi/modules/Category;
        //   231: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   234: checkcast       Ljava/util/List;
        //   237: aload_3        
        //   238: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   243: pop            
        //   244: goto            180
        //   247: aload_1        
        //   248: invokevirtual   java/util/HashMap.entrySet:()Ljava/util/Set;
        //   251: invokeinterface java/util/Set.stream:()Ljava/util/stream/Stream;
        //   256: invokedynamic   BootstrapMethod #0, applyAsInt:()Ljava/util/function/ToIntFunction;
        //   261: invokeinterface java/util/Comparator.comparingInt:(Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
        //   266: invokeinterface java/util/stream/Stream.sorted:(Ljava/util/Comparator;)Ljava/util/stream/Stream;
        //   271: invokedynamic   BootstrapMethod #1, accept:()Ljava/util/function/Consumer;
        //   276: invokeinterface java/util/stream/Stream.forEach:(Ljava/util/function/Consumer;)V
        //   281: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @EventTarget
    public void onReload(final EventWorldChange eventWorldChange) {
        class Class14 extends Thread
        {
            final Class118 val$hud;
            final Class118 this$0;
            
            
            Class14(final Class118 this$0, final Class118 val$hud) {
                this.this$0 = this$0;
                this.val$hud = val$hud;
            }
            
            @Override
            public void run() {
                this.val$hud.set(false);
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                this.val$hud.set(true);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: ldc             "HUD"
        //     6: invokestatic    cn/Hanabi/modules/ModManager.getModule:(Ljava/lang/String;)Lcn/Hanabi/modules/Mod;
        //     9: checkcast       LClassSub/Class118;
        //    12: astore_2       
        //    13: new             LClassSub/Class14;
        //    16: dup            
        //    17: aload_0        
        //    18: aload_2        
        //    19: invokespecial   ClassSub/Class14.<init>:(LClassSub/Class118;LClassSub/Class118;)V
        //    22: invokevirtual   ClassSub/Class14.start:()V
        //    25: return         
        //    26: nop            
        //    27: nop            
        //    28: nop            
        //    29: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static int rainbow(final int n) {
        return Color.getHSBColor((float)(Math.ceil((System.currentTimeMillis() + n) / 20.0) % 360.0 / 360.0), 0.8f, 0.7f).getRGB();
    }
    
    public void drawRoundedRect(float n, float n2, float n3, float n4, final int n5, final int n6) {
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(n *= 2.0f, (n2 *= 2.0f) + 1.0f, (n4 *= 2.0f) - 2.0f, n5);
        drawVLine((n3 *= 2.0f) - 1.0f, n2 + 1.0f, n4 - 2.0f, n5);
        drawHLine(n + 2.0f, n3 - 3.0f, n2, n5);
        drawHLine(n + 2.0f, n3 - 3.0f, n4 - 1.0f, n5);
        drawHLine(n + 1.0f, n + 1.0f, n2 + 1.0f, n5);
        drawHLine(n3 - 2.0f, n3 - 2.0f, n2 + 1.0f, n5);
        drawHLine(n3 - 2.0f, n3 - 2.0f, n4 - 2.0f, n5);
        drawHLine(n + 1.0f, n + 1.0f, n4 - 2.0f, n5);
        Class246.drawRect(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n6);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public void color(final int n) {
        GL11.glColor4f((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    @EventTarget
    private void render2D(final EventRender2D eventRender2D) {
        final ScaledResolution scaledResolution = new ScaledResolution(Class118.mc);
        final float n = scaledResolution.getScaledWidth();
        final float n2 = scaledResolution.getScaledHeight();
        if (this.potion.getValueState()) {
            this.renderPotionStatus((int)n, (int)n2);
        }
        if (this.arraylist.getValueState()) {
            this.renderArray(scaledResolution);
        }
        if (this.noti.getValueState()) {
            Class120.INSTANCE.drawNotifications();
        }
        if (this.music.getValueState()) {
            Class344.INSTANCE.renderOverlay();
        }
        if (this.logo.getValueState()) {
            Hanabi.INSTANCE.fontManager.icon130.drawStringWithShadow(HanabiFonts.ICON_HANABI_LOGO, 2.0f, 2.0f, new Color(47, 116, 253).getRGB());
        }
        if (Class118.hotbar.getValueState() && Class118.mc.getRenderViewEntity() instanceof EntityPlayer && !Class118.mc.gameSettings.hideGUI) {
            final float n3 = 0.0f;
            final float n4 = n2 - 20.0f;
            final float n5 = n;
            final float n6 = n2;
            final Class120 instance = Class120.INSTANCE;
            Class246.drawRect(n3, n4, n5, n6, Class120.reAlpha(Class15.BLACK.c, 0.5f));
            final UnicodeFontRenderer comfortaa17 = Hanabi.INSTANCE.fontManager.comfortaa17;
            if (Class118.mc.isSingleplayer()) {
                Class246.drawFilledCircle(10.0f, n2 - 10.0f, 3.0f, Class15.AQUA.c);
                comfortaa17.drawString("PING:N/Ams     FPS:" + Minecraft.getDebugFPS(), 16.0f, n2 - 14.5f, -1);
            }
            else {
                try {
                    final int getResponseTime = Class118.mc.getNetHandler().getPlayerInfo(Class118.mc.getSession().getUsername()).getResponseTime();
                    Class246.drawFilledCircle(10.0f, n2 - 10.0f, 3.0f, (getResponseTime <= 100) ? new Color(3110141).getRGB() : ((getResponseTime <= 250) ? new Color(Class15.ORANGE.c).darker().getRGB() : new Color(Class15.RED.c).getRGB()));
                    comfortaa17.drawString("PING:" + getResponseTime + "ms     FPS:" + Minecraft.getDebugFPS(), 16.0f, n2 - 14.5f, -1);
                }
                catch (Exception ex) {
                    Class246.drawFilledCircle(10.0f, n2 - 10.0f, 3.0f, Class15.AQUA.c);
                    comfortaa17.drawString("PING:N/Ams     FPS:" + Minecraft.getDebugFPS(), 16.0f, n2 - 14.5f, -1);
                }
            }
            final Date date = new Date();
            this.f2.format(date);
            this.f.format(date);
            final String string = "Hanabi Build 1.5.1 - " + Class118.fuck;
            Hanabi.INSTANCE.fontManager.wqy18.drawString(string, scaledResolution.getScaledWidth() - comfortaa17.getStringWidth(string), scaledResolution.getScaledHeight() - 16, -1);
            if (Class118.mc.thePlayer.inventory.currentItem == 0) {
                final float n7 = n / 2.0f - 91.0f;
                final float n8 = n2 - 20.0f;
                final float n9 = n / 2.0f + 90.0f - 160.0f;
                final float n10 = n2;
                final Class120 instance2 = Class120.INSTANCE;
                Class246.drawRect(n7, n8, n9, n10, Class120.reAlpha(Class15.WHITE.c, 0.5f));
            }
            else {
                final float n11 = n / 2.0f - 91.0f + Class118.mc.thePlayer.inventory.currentItem * 20;
                final float n12 = n2 - 20.0f;
                final float n13 = n / 2.0f + 90.0f - 20 * (8 - Class118.mc.thePlayer.inventory.currentItem);
                final float n14 = n2;
                final Class120 instance3 = Class120.INSTANCE;
                Class246.drawRect(n11, n12, n13, n14, Class120.reAlpha(Class15.WHITE.c, 0.5f));
            }
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 0; i < 9; ++i) {
                this.customRenderHotbarItem(i, (int)(n / 2.0f - 90.0f + i * 20 + 2.0f), (int)(n2 - 16.0f - 2.0f), eventRender2D.partialTicks, (EntityPlayer)Class118.mc.thePlayer);
            }
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            RenderHelper.disableStandardItemLighting();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (ModManager.getModule("StaffAnalyzer").isEnabled() && StaffAnalyzer.ui != null) {
                StaffAnalyzer.ui.draw();
            }
            this.renderScaffold();
        }
    }
    
    private void renderScaffold() {
        final int getScaledWidth = new ScaledResolution(Class118.mc).getScaledWidth();
        if (ModManager.getModule("Scaffold").getState()) {
            final int reAlpha = Class120.reAlpha(Class15.WHITE.c, 0.8f);
            if (BossStatus.bossName != null && BossStatus.statusBarTime > 0 && Class118.mc.thePlayer.getHealth() <= 6.0f) {
                this.animationY2 = (float)Class246.getAnimationState(this.animationY2, 50.0, Math.max(10.0f, Math.abs(this.animationY2 - 50.0f) * 25.0f) * 0.3);
            }
            else if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
                this.animationY2 = (float)Class246.getAnimationState(this.animationY2, 20.0, Math.max(10.0f, Math.abs(this.animationY2 - 20.0f) * 25.0f) * 0.3);
            }
            else if (Class118.mc.thePlayer.getHealth() <= 6.0f) {
                this.animationY2 = (float)Class246.getAnimationState(this.animationY2, 30.0, Math.max(10.0f, Math.abs(this.animationY2 - 30.0f) * 25.0f) * 0.3);
            }
            else {
                this.animationY2 = (float)Class246.getAnimationState(this.animationY2, 2.0, Math.max(10.0f, Math.abs(this.animationY2 - 2.0f) * 25.0f) * 0.3);
            }
            drawRoundedRect2(getScaledWidth / 2 - 90, this.animationY2, getScaledWidth / 2 + 90, this.animationY2 + 20.0f, reAlpha, reAlpha);
            if (Scaffold.currentlyHolding != null) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                if (Class118.mc.theWorld != null) {
                    RenderHelper.enableGUIStandardItemLighting();
                }
                GlStateManager.pushMatrix();
                GlStateManager.disableAlpha();
                GlStateManager.clear(256);
                Class118.mc.getRenderItem().zLevel = -150.0f;
                Class118.mc.getRenderItem().renderItemAndEffectIntoGUI(Scaffold.currentlyHolding, getScaledWidth / 2 - 90 + 2, (int)(this.animationY2 + 2.0f));
                Class118.mc.getRenderItem().zLevel = 0.0f;
                GlStateManager.disableBlend();
                GlStateManager.scale(0.5, 0.5, 0.5);
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.enableDepth();
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                GlStateManager.enableAlpha();
                GlStateManager.popMatrix();
                GL11.glPopMatrix();
            }
            else {
                Hanabi.INSTANCE.fontManager.icon30.drawString("c", getScaledWidth / 2 - 88, this.animationY2 + 2.5f, Class15.GREY.c);
            }
            Hanabi.INSTANCE.fontManager.usans18.drawCenteredString(this.getBlockCount() + " blocks left", getScaledWidth / 2 - 2, this.animationY2 + 5.5f, Class15.GREY.c);
        }
        else {
            this.animationY2 += 65.0f;
            final int reAlpha2 = Class120.reAlpha(Class15.WHITE.c, 0.8f);
            this.animationY2 = (float)Class246.getAnimationState(this.animationY2, -25.0, Math.max(10.0f, Math.abs(this.animationY2 + 25.0f) * 15.0f) * 0.3);
            this.animationY2 -= 65.0f;
            drawRoundedRect2(getScaledWidth / 2 - 90, this.animationY2, getScaledWidth / 2 + 90, this.animationY2 + 20.0f, reAlpha2, reAlpha2);
            Hanabi.INSTANCE.fontManager.usans18.drawCenteredString("Scaffold Disabled", getScaledWidth / 2 - 2, this.animationY2 + 5.5f, Class15.GREY.c);
        }
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            this.animationY2 = (float)Class246.getAnimationState(this.animationY2, 20.0, Math.max(10.0f, Math.abs(this.animationY2 - 20.0f) * 25.0f) * 0.3);
        }
        else {
            this.animationY2 = (float)Class246.getAnimationState(this.animationY2, 2.0, Math.max(10.0f, Math.abs(this.animationY2 - 2.0f) * 25.0f) * 0.3);
        }
    }
    
    public static void drawRoundedRect2(float n, float n2, float n3, float n4, final int n5, final int n6) {
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(n *= 2.0f, (n2 *= 2.0f) + 1.0f, (n4 *= 2.0f) - 2.0f, n5);
        drawVLine((n3 *= 2.0f) - 1.0f, n2 + 1.0f, n4 - 2.0f, n5);
        drawHLine(n + 2.0f, n3 - 3.0f, n2, n5);
        drawHLine(n + 2.0f, n3 - 3.0f, n4 - 1.0f, n5);
        drawHLine(n + 1.0f, n + 1.0f, n2 + 1.0f, n5);
        drawHLine(n3 - 2.0f, n3 - 2.0f, n2 + 1.0f, n5);
        drawHLine(n3 - 2.0f, n3 - 2.0f, n4 - 2.0f, n5);
        drawHLine(n + 1.0f, n + 1.0f, n4 - 2.0f, n5);
        Class246.drawRect(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n6);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawHLine(float n, float n2, final float n3, final int n4) {
        if (n2 < n) {
            final float n5 = n;
            n = n2;
            n2 = n5;
        }
        Class246.drawRect(n, n3, n2 + 1.0f, n3 + 1.0f, n4);
    }
    
    public static void drawVLine(final float n, float n2, float n3, final int n4) {
        if (n3 < n2) {
            final float n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        Class246.drawRect(n, n2 + 1.0f, n + 1.0f, n3, n4);
    }
    
    public int getBlockCount() {
        int n = 0;
        for (int i = 36; i < 45; ++i) {
            if (Class118.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack getStack = Class118.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item getItem = getStack.getItem();
                if (getStack.getItem() instanceof ItemBlock && this.isValid(getItem)) {
                    n += getStack.stackSize;
                }
            }
        }
        return n;
    }
    
    private boolean isValid(final Item item) {
        return item instanceof ItemBlock && !Scaffold.blacklistedBlocks.contains(((ItemBlock)item).getBlock());
    }
    
    private void customRenderHotbarItem(final int n, final int n2, final int n3, final float n4, final EntityPlayer entityPlayer) {
        GlStateManager.disableBlend();
        final ItemStack itemStack = entityPlayer.inventory.mainInventory[n];
        if (itemStack != null) {
            final float n5 = itemStack.animationsToGo - n4;
            if (n5 > 0.0f) {
                GlStateManager.pushMatrix();
                final float n6 = 1.0f + n5 / 5.0f;
                GlStateManager.translate((float)(n2 + 8), (float)(n3 + 12), 0.0f);
                GlStateManager.scale(1.0f / n6, (n6 + 1.0f) / 2.0f, 1.0f);
                GlStateManager.translate((float)(-(n2 + 8)), (float)(-(n3 + 12)), 0.0f);
            }
            Class118.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n2, n3);
            if (n5 > 0.0f) {
                GlStateManager.popMatrix();
            }
            Class118.mc.getRenderItem().renderItemOverlays((FontRenderer)Hanabi.INSTANCE.fontManager.comfortaa16, itemStack, n2 - 1, n3);
        }
    }
    
    private void renderArray(final ScaledResolution scaledResolution) {
        final ArrayList<Mod> list = new ArrayList<Mod>(ModManager.getEnabledModListHUD());
        float posY = 3.0f;
        final UnicodeFontRenderer raleway17 = Hanabi.INSTANCE.fontManager.raleway17;
        for (final Mod mod : list) {
            mod.onRenderArray();
            if (!mod.isEnabled() && mod.posX == 0.0f) {
                continue;
            }
            final String name = mod.getName();
            final String displayName = mod.getDisplayName();
            final float posX = mod.posX;
            mod.lastY = mod.posY;
            mod.posY = posY;
            Class246.drawRect(scaledResolution.getScaledWidth() - posX - 7.0f, posY + mod.posYRend - 3.5f, scaledResolution.getScaledWidth(), posY + mod.posYRend + 10.5f, Class120.reAlpha(Class15.BLACK.c, 0.55f));
            Class246.drawRect(scaledResolution.getScaledWidth() - posX - 7.0f, posY + mod.posYRend - 3.5f, scaledResolution.getScaledWidth() - posX - 5.0f, posY + mod.posYRend + 10.5f, new Color(47, 116, 253).getRGB());
            raleway17.drawString(name, scaledResolution.getScaledWidth() - posX - 2.0f, posY + mod.posYRend - 1.5f, new Color(47, 116, 253).getRGB());
            if (displayName != null) {
                raleway17.drawString(displayName, scaledResolution.getScaledWidth() - posX + raleway17.getStringWidth(name), posY + mod.posYRend - 1.5f, new Color(159, 159, 159).getRGB());
            }
            posY += 14.0f;
        }
    }
    
    public void renderPotionStatus(final int n, final int n2) {
        this.x = 0;
        for (final PotionEffect potionEffect : Class118.mc.thePlayer.getActivePotionEffects()) {
            final Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
            String s = I18n.format(potion.getName(), new Object[0]);
            int int1;
            int int2;
            try {
                int1 = Integer.parseInt(Potion.getDurationString(potionEffect).split(":")[0]);
                int2 = Integer.parseInt(Potion.getDurationString(potionEffect).split(":")[1]);
            }
            catch (Exception ex) {
                int1 = 0;
                int2 = 0;
            }
            final double n3 = int1 * 60 + int2;
            if (!this.timerMap.containsKey(potion)) {
                this.timerMap.put(potion, n3);
            }
            if (this.timerMap.get(potion) == 0.0 || n3 > this.timerMap.get(potion)) {
                this.timerMap.replace(potion, n3);
            }
            switch (potionEffect.getAmplifier()) {
                case 1: {
                    s += " II";
                    break;
                }
                case 2: {
                    s += " III";
                    break;
                }
                case 3: {
                    s += " IV";
                    break;
                }
            }
            final int c = Class15.WHITE.c;
            if (potionEffect.getDuration() < 600 && potionEffect.getDuration() > 300) {
                final int c2 = Class15.YELLOW.c;
            }
            else if (potionEffect.getDuration() < 300) {
                final int c3 = Class15.RED.c;
            }
            else if (potionEffect.getDuration() > 600) {
                final int c4 = Class15.WHITE.c;
            }
            final int n4 = (int)((n - 6) * 1.33f);
            final int n5 = (int)((n2 - 52 - Class118.mc.fontRendererObj.FONT_HEIGHT + this.x + 5) * 1.33f);
            final float n6 = n - 120;
            final float n7 = n2 - 60 + this.x;
            final float n8 = n - 10;
            final float n9 = n2 - 30 + this.x;
            final Class120 instance = Class120.INSTANCE;
            Class246.drawRect(n6, n7, n8, n9, Class120.reAlpha(Class15.BLACK.c, 0.41f));
            if (potion.hasStatusIcon()) {
                GlStateManager.pushMatrix();
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glDepthMask(false);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final int getStatusIconIndex = potion.getStatusIconIndex();
                Class118.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                GlStateManager.scale(0.75, 0.75, 0.75);
                Class118.mc.ingameGUI.drawTexturedModalRect(n4 - 138, n5 + 8, 0 + getStatusIconIndex % 8 * 18, 198 + getStatusIconIndex / 8 * 18, 18, 18);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glEnable(2929);
                GlStateManager.popMatrix();
            }
            final int n10 = n2 - Class118.mc.fontRendererObj.FONT_HEIGHT + this.x - 38;
            Hanabi.INSTANCE.fontManager.wqy18.drawString(s.replaceAll("§.", ""), n - 91.0f, n10 - Class118.mc.fontRendererObj.FONT_HEIGHT + 1, new Color(47, 116, 253).getRGB());
            Hanabi.INSTANCE.fontManager.comfortaa16.drawString(Potion.getDurationString(potionEffect).replaceAll("§.", ""), n - 91.0f, n10 + 4, Class120.reAlpha(-1, 0.8f));
            this.x -= 35;
        }
    }
    
    private static void lambda$new$2(final Map.Entry entry) {
        final Class245<Object> class245 = new Class245<Object>(entry.getKey().toString());
        for (final Mod mod : (List)entry.getValue()) {
            class245.addSubTab(new Class176<Object>(mod.getName(), Class118::lambda$null$1, mod));
        }
    }
    
    private static void lambda$null$1(final Class176 class176) {
        class176.getObject().setState(!class176.getObject().getState());
    }
    
    private static int lambda$new$0(final Map.Entry entry) {
        return entry.getKey().toString().hashCode();
    }
    
    static {
        dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        Class118.hotbar = new Value<Boolean>("HUD_Hotbar", true);
        Class118.musicPosX = new Value<Double>("HUD_MusicPlayerX", 70.0, 0.0, 400.0, 1.0);
        Class118.musicPosY = new Value<Double>("HUD_MusicPlayerY", 5.0, 0.0, 200.0, 1.0);
        Class118.musicPosYlyr = new Value<Double>("HUD_MusicPlayerLyricY", 120.0, 0.0, 200.0, 1.0);
    }
}
