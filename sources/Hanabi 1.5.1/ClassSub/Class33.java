package ClassSub;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import cn.Hanabi.modules.*;
import java.awt.*;
import cn.Hanabi.*;
import java.util.*;
import cn.Hanabi.utils.fontmanager.*;

public class Class33
{
    Minecraft mc;
    ScaledResolution sr;
    int length;
    public float animationY;
    public List<Class81> button;
    
    
    public Class33() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokespecial   java/lang/Object.<init>:()V
        //     8: aload_0        
        //     9: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    12: putfield        ClassSub/Class33.mc:Lnet/minecraft/client/Minecraft;
        //    15: aload_0        
        //    16: new             Ljava/util/ArrayList;
        //    19: dup            
        //    20: invokespecial   java/util/ArrayList.<init>:()V
        //    23: putfield        ClassSub/Class33.button:Ljava/util/List;
        //    26: aload_0        
        //    27: new             Lnet/minecraft/client/gui/ScaledResolution;
        //    30: dup            
        //    31: aload_0        
        //    32: getfield        ClassSub/Class33.mc:Lnet/minecraft/client/Minecraft;
        //    35: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;)V
        //    38: putfield        ClassSub/Class33.sr:Lnet/minecraft/client/gui/ScaledResolution;
        //    41: aload_0        
        //    42: aload_0        
        //    43: getfield        ClassSub/Class33.sr:Lnet/minecraft/client/gui/ScaledResolution;
        //    46: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //    49: i2d            
        //    50: ldc2_w          0.155
        //    53: dmul           
        //    54: d2i            
        //    55: putfield        ClassSub/Class33.length:I
        //    58: ldc             0
        //    60: istore_1       
        //    61: iload_1        
        //    62: invokestatic    cn/Hanabi/modules/Category.values:()[Lcn/Hanabi/modules/Category;
        //    65: checkcast       [Lcn/Hanabi/modules/Category;
        //    68: arraylength    
        //    69: if_icmpge       173
        //    72: new             LClassSub/Class336;
        //    75: dup            
        //    76: aload_0        
        //    77: aload_0        
        //    78: getfield        ClassSub/Class33.length:I
        //    81: ldc             2
        //    83: idiv           
        //    84: i2f            
        //    85: aload_0        
        //    86: getfield        ClassSub/Class33.sr:Lnet/minecraft/client/gui/ScaledResolution;
        //    89: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledHeight:()I
        //    92: i2f            
        //    93: ldc             0.342
        //    95: fmul           
        //    96: iload_1        
        //    97: ldc             1
        //    99: iadd           
        //   100: aload_0        
        //   101: getfield        ClassSub/Class33.sr:Lnet/minecraft/client/gui/ScaledResolution;
        //   104: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledHeight:()I
        //   107: imul           
        //   108: i2f            
        //   109: ldc             0.078
        //   111: fmul           
        //   112: fadd           
        //   113: aload_0        
        //   114: getfield        ClassSub/Class33.length:I
        //   117: ldc             2
        //   119: idiv           
        //   120: i2f            
        //   121: invokestatic    cn/Hanabi/modules/Category.values:()[Lcn/Hanabi/modules/Category;
        //   124: checkcast       [Lcn/Hanabi/modules/Category;
        //   127: iload_1        
        //   128: aaload         
        //   129: invokevirtual   cn/Hanabi/modules/Category.toString:()Ljava/lang/String;
        //   132: aload_0        
        //   133: getfield        ClassSub/Class33.sr:Lnet/minecraft/client/gui/ScaledResolution;
        //   136: aload_0        
        //   137: invokespecial   ClassSub/Class336.<init>:(LClassSub/Class33;FFFLjava/lang/String;Lnet/minecraft/client/gui/ScaledResolution;LClassSub/Class33;)V
        //   140: astore_2       
        //   141: aload_2        
        //   142: ldc             0
        //   144: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   147: putfield        ClassSub/Class81.active:Ljava/lang/Boolean;
        //   150: aload_0        
        //   151: getfield        ClassSub/Class33.button:Ljava/util/List;
        //   154: aload_2        
        //   155: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   160: pop            
        //   161: iinc            1, 1
        //   164: goto            61
        //   167: nop            
        //   168: nop            
        //   169: nop            
        //   170: nop            
        //   171: nop            
        //   172: athrow         
        //   173: aload_0        
        //   174: getfield        ClassSub/Class33.button:Ljava/util/List;
        //   177: ldc             0
        //   179: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   184: checkcast       LClassSub/Class81;
        //   187: ldc             1
        //   189: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   192: putfield        ClassSub/Class81.active:Ljava/lang/Boolean;
        //   195: return         
        //   196: nop            
        //   197: nop            
        //   198: nop            
        //   199: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void draw() {
        this.sr = new ScaledResolution(this.mc);
        Class246.drawRect(0.0f, 0.0f, this.length, this.sr.getScaledHeight(), Class135.theme.isCurrentMode("Light") ? -1118482 : new Color(22, 22, 22).getRGB());
        Class246.drawRect(this.length, 0.0f, this.length + 2, this.sr.getScaledHeight(), Class135.theme.isCurrentMode("Light") ? new Color(-14848033).brighter().getRGB() : new Color(47, 116, 253).getRGB());
        Class81 class81 = null;
        this.length = (int)(this.sr.getScaledWidth() * 0.155);
        int n = 0;
        for (final Class81 class82 : this.button) {
            class82.x = this.length / 2;
            class82.y = this.sr.getScaledHeight() * 0.342f + (n + 1) * this.sr.getScaledHeight() * 0.078f - 40.0f;
            class82.radius = this.length / 2;
            ++n;
            if (class82.active) {
                class81 = class82;
            }
        }
        this.animationY = (float)Class246.getAnimationState(this.animationY, class81.y, Math.max(10.0, Math.abs(this.animationY - class81.y) * 50.0f * 0.3));
        Class246.drawRoundedRect((int)class81.x - 50, (int)this.animationY - 15, (int)class81.x + 50, (int)this.animationY + 18, 15.0f, Class135.theme.isCurrentMode("Light") ? new Color(-14848033).brighter().getRGB() : new Color(47, 116, 253).getRGB());
        final Iterator<Class81> iterator2 = this.button.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().drawOther();
        }
        UnicodeFontRenderer unicodeFontRenderer = Hanabi.INSTANCE.fontManager.icon50;
        if (this.sr.getScaledHeight() < 330) {
            unicodeFontRenderer = Hanabi.INSTANCE.fontManager.icon50;
        }
        else if (this.sr.getScaledHeight() < 370) {
            unicodeFontRenderer = Hanabi.INSTANCE.fontManager.icon70;
        }
        else if (this.sr.getScaledHeight() < 450) {
            unicodeFontRenderer = Hanabi.INSTANCE.fontManager.icon100;
        }
        else if (this.sr.getScaledHeight() < 550) {
            unicodeFontRenderer = Hanabi.INSTANCE.fontManager.icon130;
        }
        unicodeFontRenderer.drawCenteredString(HanabiFonts.ICON_HANABI_LOGO, (int)(this.length / 2.1), (int)(this.sr.getScaledHeight() * 0.11), Class135.theme.isCurrentMode("Light") ? new Color(-14848033).brighter().getRGB() : new Color(47, 116, 253).getRGB());
    }
    
    public void onMouseClick(final int n, final int n2) {
        for (final Class81 class81 : this.button) {
            if (class81.isPressed(n, n2)) {
                class81.onPress();
                Class77.wheelStateMod = 0;
            }
        }
    }
}
