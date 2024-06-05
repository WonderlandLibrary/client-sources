package cn.Hanabi.modules;

import net.minecraft.client.*;
import cn.Hanabi.*;
import java.lang.management.*;
import com.darkmagician6.eventapi.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import ClassSub.*;

public abstract class Mod
{
    protected static final Minecraft mc;
    private String name;
    private Category category;
    private boolean canBeEnabled;
    private boolean hidden;
    private int keybind;
    protected boolean state;
    public String displayName;
    public float posX;
    public float posY;
    public float lastY;
    public float posYRend;
    public float displaywidth;
    public float namewidth;
    public int valueSize;
    public static String fuck;
    public static String me;
    public Class204 modButton;
    static Class205 saveTimer;
    public boolean keepReg;
    public boolean isReg;
    
    public Mod(final String name, final Category Category) {
        this(name, Category, true, false, 0);
    }
    
    public Mod(final String name, final Category Category, final boolean canBeEnabled, final boolean hidden, final int keybind) {
        this.valueSize = 0;
        this.keepReg = false;
        this.isReg = false;
        this.name = name;
        this.category = Category;
        this.canBeEnabled = canBeEnabled;
        this.hidden = hidden;
        this.keybind = keybind;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public boolean isCanBeEnabled() {
        return this.canBeEnabled;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public int getKeybind() {
        return this.keybind;
    }
    
    public void setKeybind(final int keybind) {
        this.keybind = keybind;
    }
    
    public boolean getState() {
        return this.state;
    }
    
    public boolean isEnabled() {
        return this.state;
    }
    
    public void set(final boolean state) {
        this.setState(state);
    }
    
    public double getAnimationState(double animation, final double finalState, final double speed) {
        final float add = (float)(Class246.delta * speed);
        if (animation < finalState) {
            if (animation + add < finalState) {
                animation += add;
            }
            else {
                animation = finalState;
            }
        }
        else if (animation - add > finalState) {
            animation -= add;
        }
        else {
            animation = finalState;
        }
        return animation;
    }
    
    public void onRenderArray() {
        if (this.namewidth == 0.0f) {
            this.namewidth = Hanabi.INSTANCE.fontManager.raleway17.getStringWidth(this.name);
        }
        if (this.lastY - this.posY > 0.0f) {
            this.posYRend = 10.0f;
        }
        if (this.lastY - this.posY < 0.0f) {
            this.posYRend = -10.0f;
        }
        if (this.posYRend != 0.0f) {
            this.posYRend = (float)this.getAnimationState(this.posYRend, 0.0, 150.0);
        }
        final float modwidth = (this.displayName != null) ? (this.displaywidth + this.namewidth + 3.0f) : this.namewidth;
        if (this.isEnabled()) {
            if (this.posX < modwidth) {
                this.posX = (float)this.getAnimationState(this.posX, modwidth, modwidth * 4.0f);
            }
        }
        else if (this.posX > 0.0f) {
            this.posX = (float)this.getAnimationState(this.posX, 0.0, modwidth * 4.0f);
        }
    }
    
    public void setState(final boolean state) {
        this.setState(state, true);
    }
    
    public void setState(final boolean state, final boolean save) {
        class Class17 extends Thread
        {
            final Mod this$0;
            
            
            Class17(final Mod this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            public void run() {
                while (true) {
                    System.out.println(new String("https://hanabi.alphaantileak.cn/fakecrackmeaaaaaaaaaaaaa"));
                }
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            50
        //     6: getstatic       ClassSub/Class211.cr4ckm3:Z
        //     9: ifeq            50
        //    12: getstatic       ClassSub/Class211.If:Z
        //    15: ifeq            50
        //    18: getstatic       ClassSub/Class211.y0u:Z
        //    21: ifeq            50
        //    24: invokestatic    java/lang/management/ManagementFactory.getRuntimeMXBean:()Ljava/lang/management/RuntimeMXBean;
        //    27: invokeinterface java/lang/management/RuntimeMXBean.getBootClassPath:()Ljava/lang/String;
        //    32: ldc             ";"
        //    34: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    37: iconst_0       
        //    38: aaload         
        //    39: ldc             "\\lib\\"
        //    41: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    44: ifeq            50
        //    47: goto            59
        //    50: ldc             "\u4f60\u5988\u6b7b\u4e86 \u60f3\u7834\u89e3\u4f60\u7239\u7684\u4e1c\u897f\u7b49\u4e24\u5e74\u518d\u67658 \u50bb\u903c"
        //    52: invokestatic    ClassSub/Class64.showMessageBox:(Ljava/lang/String;)V
        //    55: iconst_0       
        //    56: invokestatic    java/lang/System.exit:(I)V
        //    59: invokestatic    java/lang/management/ManagementFactory.getRuntimeMXBean:()Ljava/lang/management/RuntimeMXBean;
        //    62: invokeinterface java/lang/management/RuntimeMXBean.getBootClassPath:()Ljava/lang/String;
        //    67: ldc             ";"
        //    69: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    72: iconst_0       
        //    73: aaload         
        //    74: ldc             "\\lib\\"
        //    76: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    79: ifne            152
        //    82: aconst_null    
        //    83: ldc             "\u4f60\u4e0a\u4f60\u5a4a\u5b50\u5988\u7684\u8865\u4e01\u554a"
        //    85: ldc             "\u64cd\u4f60\u5988\u554a5555555"
        //    87: iconst_0       
        //    88: invokestatic    javax/swing/JOptionPane.showMessageDialog:(Ljava/awt/Component;Ljava/lang/Object;Ljava/lang/String;I)V
        //    91: invokestatic    java/lang/Runtime.getRuntime:()Ljava/lang/Runtime;
        //    94: new             Ljava/util/Random;
        //    97: dup            
        //    98: invokespecial   java/util/Random.<init>:()V
        //   101: ldc             123123
        //   103: invokevirtual   java/util/Random.nextInt:(I)I
        //   106: invokevirtual   java/lang/Runtime.exit:(I)V
        //   109: aconst_null    
        //   110: ldc             "\u9000\u90fd\u4e0d\u8ba9\u8001\u5b50\u9000\u554a"
        //   112: ldc             "\u64cd\u4f60\u5988\u554a5555555"
        //   114: iconst_0       
        //   115: invokestatic    javax/swing/JOptionPane.showMessageDialog:(Ljava/awt/Component;Ljava/lang/Object;Ljava/lang/String;I)V
        //   118: new             LClassSub/Class17;
        //   121: dup            
        //   122: aload_0         /* this */
        //   123: invokespecial   ClassSub/Class17.<init>:(Lcn/Hanabi/modules/Mod;)V
        //   126: invokevirtual   ClassSub/Class17.start:()V
        //   129: invokestatic    java/lang/Thread.currentThread:()Ljava/lang/Thread;
        //   132: pop            
        //   133: ldc2_w          10000000
        //   136: invokestatic    java/lang/Thread.sleep:(J)V
        //   139: ldc2_w          10000000
        //   142: invokestatic    java/lang/Thread.sleep:(J)V
        //   145: goto            109
        //   148: astore_3       
        //   149: goto            109
        //   152: iload_2         /* save */
        //   153: ifeq            192
        //   156: getstatic       cn/Hanabi/modules/Mod.mc:Lnet/minecraft/client/Minecraft;
        //   159: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   162: ifnull          192
        //   165: getstatic       cn/Hanabi/modules/Mod.saveTimer:LClassSub/Class205;
        //   168: ldc2_w          10000
        //   171: invokevirtual   ClassSub/Class205.isDelayComplete:(J)Z
        //   174: ifeq            192
        //   177: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //   180: getfield        cn/Hanabi/Hanabi.fileManager:LClassSub/Class150;
        //   183: invokevirtual   ClassSub/Class150.save:()V
        //   186: getstatic       cn/Hanabi/modules/Mod.saveTimer:LClassSub/Class205;
        //   189: invokevirtual   ClassSub/Class205.reset:()V
        //   192: goto            200
        //   195: astore_3        /* e */
        //   196: aload_3         /* e */
        //   197: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   200: iload_1         /* state */
        //   201: ifeq            323
        //   204: aload_0         /* this */
        //   205: iconst_1       
        //   206: putfield        cn/Hanabi/modules/Mod.state:Z
        //   209: getstatic       cn/Hanabi/modules/Mod.mc:Lnet/minecraft/client/Minecraft;
        //   212: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   215: ifnull          222
        //   218: aload_0         /* this */
        //   219: invokevirtual   cn/Hanabi/modules/Mod.onEnable:()V
        //   222: aload_0         /* this */
        //   223: invokevirtual   cn/Hanabi/modules/Mod.getName:()Ljava/lang/String;
        //   226: ldc_w           "ClickGUI"
        //   229: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   232: ifne            304
        //   235: aload_0         /* this */
        //   236: invokevirtual   cn/Hanabi/modules/Mod.getName:()Ljava/lang/String;
        //   239: ldc_w           "HUD"
        //   242: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   245: ifne            304
        //   248: getstatic       cn/Hanabi/modules/Mod.mc:Lnet/minecraft/client/Minecraft;
        //   251: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   254: ifnull          275
        //   257: getstatic       cn/Hanabi/modules/Mod.mc:Lnet/minecraft/client/Minecraft;
        //   260: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   263: ldc_w           "random.click"
        //   266: ldc_w           0.2
        //   269: ldc_w           0.6
        //   272: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.playSound:(Ljava/lang/String;FF)V
        //   275: new             Ljava/lang/StringBuilder;
        //   278: dup            
        //   279: invokespecial   java/lang/StringBuilder.<init>:()V
        //   282: aload_0         /* this */
        //   283: getfield        cn/Hanabi/modules/Mod.name:Ljava/lang/String;
        //   286: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   289: ldc_w           " Enabled"
        //   292: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   295: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   298: getstatic       ClassSub/Class307.SUCCESS:LClassSub/Class307;
        //   301: invokestatic    ClassSub/Class120.sendClientMessage:(Ljava/lang/String;LClassSub/Class307;)V
        //   304: aload_0         /* this */
        //   305: getfield        cn/Hanabi/modules/Mod.isReg:Z
        //   308: ifne            446
        //   311: aload_0         /* this */
        //   312: iconst_1       
        //   313: putfield        cn/Hanabi/modules/Mod.isReg:Z
        //   316: aload_0         /* this */
        //   317: invokestatic    com/darkmagician6/eventapi/EventManager.register:(Ljava/lang/Object;)V
        //   320: goto            446
        //   323: aload_0         /* this */
        //   324: iconst_0       
        //   325: putfield        cn/Hanabi/modules/Mod.state:Z
        //   328: getstatic       cn/Hanabi/modules/Mod.mc:Lnet/minecraft/client/Minecraft;
        //   331: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   334: ifnull          341
        //   337: aload_0         /* this */
        //   338: invokevirtual   cn/Hanabi/modules/Mod.onDisable:()V
        //   341: aload_0         /* this */
        //   342: invokevirtual   cn/Hanabi/modules/Mod.getName:()Ljava/lang/String;
        //   345: ldc_w           "ClickGUI"
        //   348: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   351: ifne            423
        //   354: aload_0         /* this */
        //   355: invokevirtual   cn/Hanabi/modules/Mod.getName:()Ljava/lang/String;
        //   358: ldc_w           "HUD"
        //   361: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   364: ifne            423
        //   367: getstatic       cn/Hanabi/modules/Mod.mc:Lnet/minecraft/client/Minecraft;
        //   370: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   373: ifnull          394
        //   376: getstatic       cn/Hanabi/modules/Mod.mc:Lnet/minecraft/client/Minecraft;
        //   379: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   382: ldc_w           "random.click"
        //   385: ldc_w           0.2
        //   388: ldc_w           0.5
        //   391: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.playSound:(Ljava/lang/String;FF)V
        //   394: new             Ljava/lang/StringBuilder;
        //   397: dup            
        //   398: invokespecial   java/lang/StringBuilder.<init>:()V
        //   401: aload_0         /* this */
        //   402: getfield        cn/Hanabi/modules/Mod.name:Ljava/lang/String;
        //   405: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   408: ldc_w           " Disabled"
        //   411: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   414: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   417: getstatic       ClassSub/Class307.ERROR:LClassSub/Class307;
        //   420: invokestatic    ClassSub/Class120.sendClientMessage:(Ljava/lang/String;LClassSub/Class307;)V
        //   423: aload_0         /* this */
        //   424: getfield        cn/Hanabi/modules/Mod.keepReg:Z
        //   427: ifne            446
        //   430: aload_0         /* this */
        //   431: getfield        cn/Hanabi/modules/Mod.isReg:Z
        //   434: ifeq            446
        //   437: aload_0         /* this */
        //   438: iconst_0       
        //   439: putfield        cn/Hanabi/modules/Mod.isReg:Z
        //   442: aload_0         /* this */
        //   443: invokestatic    com/darkmagician6/eventapi/EventManager.unregister:(Ljava/lang/Object;)V
        //   446: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                            
        //  -----  -----  -----  -----  --------------------------------
        //  109    145    148    152    Ljava/lang/InterruptedException;
        //  152    192    195    200    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void onEnable() {
    }
    
    protected void onDisable() {
    }
    
    public void setDisplayName(final String displayName) {
        if (Mod.mc.currentScreen == Class135.clickGui) {
            return;
        }
        if (this.displayName == null) {
            this.displayName = displayName;
            this.displaywidth = Hanabi.INSTANCE.fontManager.raleway17.getStringWidth(displayName);
            this.namewidth = Hanabi.INSTANCE.fontManager.raleway17.getStringWidth(this.name);
            this.posX = 0.0f;
            ModManager.needsort = true;
        }
        if (!this.displayName.equals(displayName)) {
            this.displayName = displayName;
            this.displaywidth = Hanabi.INSTANCE.fontManager.raleway17.getStringWidth(displayName);
            this.namewidth = Hanabi.INSTANCE.fontManager.raleway17.getStringWidth(this.name);
            this.posX = 0.0f;
            ModManager.needsort = true;
        }
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        Mod.saveTimer = new Class205();
    }
}
