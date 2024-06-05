package cn.Hanabi.modules.Combat;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.injection.interfaces.*;
import ClassSub.*;
import java.util.*;
import net.minecraft.entity.player.*;
import cn.Hanabi.events.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.client.*;

public class TPHit extends Mod
{
    int count;
    int aacCount;
    int aacPackets;
    int aacState;
    EntityLivingBase entity;
    Value<Boolean> cancelPackets;
    Value<Boolean> aac;
    List<Class108> posList;
    Value<Double> packets;
    
    
    public TPHit() {
        super("TPHit", Category.COMBAT);
        this.cancelPackets = new Value<Boolean>("TPHit_CancelPackets", false);
        this.aac = new Value<Boolean>("TPHit_AAC", true);
        this.posList = new ArrayList<Class108>();
        this.packets = new Value<Double>("TPHit_Packets", 8.0, 1.0, 40.0, 1.0);
    }
    
    public void onEnable() {
        if (this.aac.getValueState()) {
            this.aacCount = 30;
            this.aacPackets = 2;
        }
        this.count = 0;
        this.entity = null;
        this.posList.clear();
        if (!ModManager.getModule("Reach").isEnabled()) {
            ModManager.getModule("Reach").set(true);
            Class200.tellPlayer("§b[Hanabi]§a要使用TPHit，必须先启用Reach�?");
        }
    }
    
    public void onDisable() {
        Class211.getTimer().timerSpeed = 1.0f;
    }
    
    @EventTarget
    public void onPacket(final EventPacket eventPacket) {
        final Packet packet = eventPacket.getPacket();
        if (packet instanceof C03PacketPlayer && (this.count < (int)(Object)this.packets.getValueState() * 2 || this.entity == null)) {
            ++this.count;
            if (this.cancelPackets.getValueState()) {
                eventPacket.setCancelled(true);
            }
        }
        else if ((this.entity == null || this.count < (int)(Object)this.packets.getValueState() * 2) && packet instanceof C02PacketUseEntity) {
            eventPacket.setCancelled(true);
        }
    }
    
    @EventTarget
    public void onMove(final EventMove eventMove) {
        if (this.count < (int)(Object)this.packets.getValueState() * 2 || this.entity == null) {}
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        if (this.posList.size() > 0) {
            for (final Class108 class108 : this.posList) {
                Class246.drawEntityESP(class108.x - ((IRenderManager)TPHit.mc.getRenderManager()).getRenderPosX(), class108.y - ((IRenderManager)TPHit.mc.getRenderManager()).getRenderPosY(), class108.z - ((IRenderManager)TPHit.mc.getRenderManager()).getRenderPosZ(), 0.25, 1.25, 0.0f, 1.0f, 0.0f, 0.2f, 0.0f, 1.0f, 0.0f, 1.0f, 2.0f);
            }
        }
    }
    
    @EventTarget
    public void onAttack(final EventAttack eventAttack) {
        class Class223 extends Thread
        {
            final TPHit this$0;
            
            
            Class223(final TPHit this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            public void run() {
                try {
                    Thread.sleep(1430L);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                TPHit.access$4300().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$4000().thePlayer.posX + 0.08, TPHit.access$4100().thePlayer.posY, TPHit.access$4200().thePlayer.posZ, false));
            }
        }
        class Class242 extends Thread
        {
            final EventAttack val$e;
            final TPHit this$0;
            
            
            Class242(final TPHit this$0, final EventAttack val$e) {
                this.this$0 = this$0;
                this.val$e = val$e;
            }
            
            @Override
            public void run() {
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                final int getX = this.val$e.getEntity().getPosition().getX();
                final int getY = this.val$e.getEntity().getPosition().getY();
                final int getZ = this.val$e.getEntity().getPosition().getZ();
                final double n = getX - TPHit.access$000().thePlayer.posX + 0.5;
                final double n2 = getY - TPHit.access$100().thePlayer.posY + 0.08;
                final double n3 = getZ - TPHit.access$200().thePlayer.posZ + 0.5;
                final double ceil = Math.ceil(Math.sqrt(Math.pow(n, 2.0) + Math.pow(n2, 2.0) + Math.pow(n3, 2.0)) / 9.8);
                TPHit.access$600().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$300().thePlayer.posX, TPHit.access$400().thePlayer.posY - 0.32, TPHit.access$500().thePlayer.posZ, false));
                TPHit.access$1000().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$700().thePlayer.posX, TPHit.access$800().thePlayer.posY - 0.32, TPHit.access$900().thePlayer.posZ, false));
                TPHit.access$1400().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$1100().thePlayer.posX, TPHit.access$1200().thePlayer.posY, TPHit.access$1300().thePlayer.posZ, false));
                TPHit.access$1800().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$1500().thePlayer.posX, TPHit.access$1600().thePlayer.posY, TPHit.access$1700().thePlayer.posZ, false));
                TPHit.access$2200().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$1900().thePlayer.posX, TPHit.access$2000().thePlayer.posY + 1.1, TPHit.access$2100().thePlayer.posZ, false));
                TPHit.access$2600().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$2300().thePlayer.posX, TPHit.access$2400().thePlayer.posY + 1.1, TPHit.access$2500().thePlayer.posZ, false));
                for (int n4 = 1; n4 <= ceil; ++n4) {
                    TPHit.access$3000().getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TPHit.access$2700().thePlayer.posX + n / ceil * n4, TPHit.access$2800().thePlayer.posY + n2 / ceil * n4, TPHit.access$2900().thePlayer.posZ + n3 / ceil * n4, false));
                }
                TPHit.access$3200().playerController.attackEntity((EntityPlayer)TPHit.access$3100().thePlayer, this.val$e.getEntity());
                TPHit.access$3300().thePlayer.swingItem();
            }
        }
        class Class101 extends Thread
        {
            final EventAttack val$e;
            final TPHit this$0;
            
            
            Class101(final TPHit this$0, final EventAttack val$e) {
                this.this$0 = this$0;
                this.val$e = val$e;
            }
            
            @Override
            public void run() {
                try {
                    Thread.sleep(175L);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                this.this$0.aacState = 2;
                TPHit.access$3500().playerController.attackEntity((EntityPlayer)TPHit.access$3400().thePlayer, this.val$e.getEntity());
                TPHit.access$3600().thePlayer.swingItem();
            }
        }
        class Class168 extends Thread
        {
            final EventAttack val$e;
            final TPHit this$0;
            
            
            Class168(final TPHit this$0, final EventAttack val$e) {
                this.this$0 = this$0;
                this.val$e = val$e;
            }
            
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                this.this$0.aacCount = 0;
                TPHit.access$3800().playerController.attackEntity((EntityPlayer)TPHit.access$3700().thePlayer, this.val$e.getEntity());
                TPHit.access$3900().thePlayer.swingItem();
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: getfield        cn/Hanabi/modules/Combat/TPHit.aac:Lcn/Hanabi/value/Value;
        //     8: invokevirtual   cn/Hanabi/value/Value.getValueState:()Ljava/lang/Object;
        //    11: checkcast       Ljava/lang/Boolean;
        //    14: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    17: ifeq            238
        //    20: aload_0        
        //    21: getfield        cn/Hanabi/modules/Combat/TPHit.aacState:I
        //    24: ldc             2
        //    26: if_icmpne       47
        //    29: aload_0        
        //    30: ldc             0
        //    32: putfield        cn/Hanabi/modules/Combat/TPHit.aacState:I
        //    35: new             LClassSub/Class242;
        //    38: dup            
        //    39: aload_0        
        //    40: aload_1        
        //    41: invokespecial   ClassSub/Class242.<init>:(Lcn/Hanabi/modules/Combat/TPHit;Lcn/Hanabi/events/EventAttack;)V
        //    44: invokevirtual   ClassSub/Class242.start:()V
        //    47: aload_0        
        //    48: getfield        cn/Hanabi/modules/Combat/TPHit.aacState:I
        //    51: ldc             1
        //    53: if_icmpne       75
        //    56: invokestatic    ClassSub/Class211.getTimer:()Lnet/minecraft/util/Timer;
        //    59: fconst_1       
        //    60: putfield        net/minecraft/util/Timer.timerSpeed:F
        //    63: new             LClassSub/Class101;
        //    66: dup            
        //    67: aload_0        
        //    68: aload_1        
        //    69: invokespecial   ClassSub/Class101.<init>:(Lcn/Hanabi/modules/Combat/TPHit;Lcn/Hanabi/events/EventAttack;)V
        //    72: invokevirtual   ClassSub/Class101.start:()V
        //    75: aload_0        
        //    76: getfield        cn/Hanabi/modules/Combat/TPHit.aacCount:I
        //    79: ldc             30
        //    81: if_icmple       160
        //    84: aload_0        
        //    85: getfield        cn/Hanabi/modules/Combat/TPHit.aacState:I
        //    88: ifne            288
        //    91: getstatic       cn/Hanabi/modules/Combat/TPHit.mc:Lnet/minecraft/client/Minecraft;
        //    94: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    97: getfield        net/minecraft/client/settings/GameSettings.keyBindAttack:Lnet/minecraft/client/settings/KeyBinding;
        //   100: invokevirtual   net/minecraft/client/settings/KeyBinding.isKeyDown:()Z
        //   103: ifeq            288
        //   106: invokestatic    ClassSub/Class211.getTimer:()Lnet/minecraft/util/Timer;
        //   109: ldc_w           0.01
        //   112: putfield        net/minecraft/util/Timer.timerSpeed:F
        //   115: ldc_w           "§7§l[§c§lTPHit§7§l]§7 Trying to hit entity..."
        //   118: invokestatic    ClassSub/Class200.tellPlayer:(Ljava/lang/String;)V
        //   121: aload_0        
        //   122: ldc             1
        //   124: putfield        cn/Hanabi/modules/Combat/TPHit.aacState:I
        //   127: new             LClassSub/Class168;
        //   130: dup            
        //   131: aload_0        
        //   132: aload_1        
        //   133: invokespecial   ClassSub/Class168.<init>:(Lcn/Hanabi/modules/Combat/TPHit;Lcn/Hanabi/events/EventAttack;)V
        //   136: invokevirtual   ClassSub/Class168.start:()V
        //   139: new             LClassSub/Class223;
        //   142: dup            
        //   143: aload_0        
        //   144: invokespecial   ClassSub/Class223.<init>:(Lcn/Hanabi/modules/Combat/TPHit;)V
        //   147: invokevirtual   ClassSub/Class223.start:()V
        //   150: goto            288
        //   153: nop            
        //   154: nop            
        //   155: nop            
        //   156: nop            
        //   157: nop            
        //   158: nop            
        //   159: athrow         
        //   160: aload_0        
        //   161: getfield        cn/Hanabi/modules/Combat/TPHit.aacState:I
        //   164: ifne            288
        //   167: getstatic       cn/Hanabi/modules/Combat/TPHit.mc:Lnet/minecraft/client/Minecraft;
        //   170: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   173: getfield        net/minecraft/client/settings/GameSettings.keyBindAttack:Lnet/minecraft/client/settings/KeyBinding;
        //   176: invokevirtual   net/minecraft/client/settings/KeyBinding.isKeyDown:()Z
        //   179: ifeq            288
        //   182: ldc2_w          1.5
        //   185: aload_0        
        //   186: getfield        cn/Hanabi/modules/Combat/TPHit.aacCount:I
        //   189: ldc             1
        //   191: isub           
        //   192: i2d            
        //   193: ldc2_w          0.05
        //   196: dmul           
        //   197: dsub           
        //   198: dstore_2       
        //   199: new             Ljava/lang/StringBuilder;
        //   202: dup            
        //   203: invokespecial   java/lang/StringBuilder.<init>:()V
        //   206: ldc_w           "§7§l[§c§lTPHit§7§l]§7 Wait §a "
        //   209: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   212: dload_2        
        //   213: invokevirtual   java/lang/StringBuilder.append:(D)Ljava/lang/StringBuilder;
        //   216: ldc_w           " §7 seconds before hitting again!"
        //   219: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   222: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   225: invokestatic    ClassSub/Class200.tellPlayer:(Ljava/lang/String;)V
        //   228: goto            288
        //   231: nop            
        //   232: nop            
        //   233: nop            
        //   234: nop            
        //   235: nop            
        //   236: nop            
        //   237: athrow         
        //   238: aload_1        
        //   239: invokevirtual   cn/Hanabi/events/EventAttack.getEntity:()Lnet/minecraft/entity/Entity;
        //   242: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   245: ifeq            288
        //   248: aload_0        
        //   249: aload_1        
        //   250: invokevirtual   cn/Hanabi/events/EventAttack.getEntity:()Lnet/minecraft/entity/Entity;
        //   253: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   256: putfield        cn/Hanabi/modules/Combat/TPHit.entity:Lnet/minecraft/entity/EntityLivingBase;
        //   259: new             Ljava/lang/StringBuilder;
        //   262: dup            
        //   263: invokespecial   java/lang/StringBuilder.<init>:()V
        //   266: ldc_w           "§b[Hanabi]§a\u5df2\u6210\u529f\u9009\u5b9a\u5b9e\u4f53\uff1a"
        //   269: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   272: aload_0        
        //   273: getfield        cn/Hanabi/modules/Combat/TPHit.entity:Lnet/minecraft/entity/EntityLivingBase;
        //   276: invokevirtual   net/minecraft/entity/EntityLivingBase.getName:()Ljava/lang/String;
        //   279: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   282: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   285: invokestatic    ClassSub/Class200.tellPlayer:(Ljava/lang/String;)V
        //   288: return         
        //   289: nop            
        //   290: nop            
        //   291: nop            
        //   292: nop            
        //   293: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @EventTarget
    public void onPullback(final EventPullback eventPullback) {
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (this.aac.getValueState()) {
            ++this.aacCount;
        }
        else {
            this.setDisplayName("Canceled:" + this.count);
            if (this.entity != null && (this.entity.isDead || this.entity.getHealth() <= 0.0f)) {
                this.set(false);
            }
            if ((this.count >= (int)(Object)this.packets.getValueState() * 2 || this.cancelPackets.getValueState()) && this.entity != null && this.entity.hurtTime == 0) {
                final double n = this.entity.posX - TPHit.mc.thePlayer.posX;
                final double n2 = this.entity.posY - TPHit.mc.thePlayer.posY;
                final double n3 = this.entity.posZ - TPHit.mc.thePlayer.posZ;
                TPHit.mc.thePlayer.fallDistance = 5.0f;
                this.posList.clear();
                try {
                    for (int i = 1; i <= (int)(Object)this.packets.getValueState(); ++i) {
                        final Class108 class108 = new Class108(TPHit.mc.thePlayer.posX + n / (int)(Object)this.packets.getValueState() * i, TPHit.mc.thePlayer.posY + n2 / (int)(Object)this.packets.getValueState() * i, TPHit.mc.thePlayer.posZ + n3 / (int)(Object)this.packets.getValueState() * i);
                        final Block getBlock = TPHit.mc.theWorld.getBlockState(new BlockPos(class108.x, class108.y, class108.z)).getBlock();
                        if (getBlock instanceof BlockAir || getBlock instanceof BlockLiquid) {
                            this.posList.add(class108);
                            TPHit.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(class108.getX(), class108.getY() + 0.1 * i + 0.5, class108.getZ(), false));
                        }
                    }
                    TPHit.mc.thePlayer.swingItem();
                    TPHit.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C02PacketUseEntity((Entity)this.entity, C02PacketUseEntity.Action.ATTACK));
                    for (int j = this.posList.size() - 1; j >= 0; --j) {
                        final Class108 class109 = this.posList.get(j);
                        TPHit.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(class109.getX(), class109.getY() + 0.1 * j + 0.5, class109.getZ(), false));
                    }
                }
                catch (Throwable t) {
                    Class200.tellPlayer("§b[Hanabi]§a出现异常:" + t.getMessage());
                }
                TPHit.mc.thePlayer.fallDistance = 1.0f;
            }
        }
    }
    
    static Minecraft access$000() {
        return TPHit.mc;
    }
    
    static Minecraft access$100() {
        return TPHit.mc;
    }
    
    static Minecraft access$200() {
        return TPHit.mc;
    }
    
    static Minecraft access$300() {
        return TPHit.mc;
    }
    
    static Minecraft access$400() {
        return TPHit.mc;
    }
    
    static Minecraft access$500() {
        return TPHit.mc;
    }
    
    static Minecraft access$600() {
        return TPHit.mc;
    }
    
    static Minecraft access$700() {
        return TPHit.mc;
    }
    
    static Minecraft access$800() {
        return TPHit.mc;
    }
    
    static Minecraft access$900() {
        return TPHit.mc;
    }
    
    static Minecraft access$1000() {
        return TPHit.mc;
    }
    
    static Minecraft access$1100() {
        return TPHit.mc;
    }
    
    static Minecraft access$1200() {
        return TPHit.mc;
    }
    
    static Minecraft access$1300() {
        return TPHit.mc;
    }
    
    static Minecraft access$1400() {
        return TPHit.mc;
    }
    
    static Minecraft access$1500() {
        return TPHit.mc;
    }
    
    static Minecraft access$1600() {
        return TPHit.mc;
    }
    
    static Minecraft access$1700() {
        return TPHit.mc;
    }
    
    static Minecraft access$1800() {
        return TPHit.mc;
    }
    
    static Minecraft access$1900() {
        return TPHit.mc;
    }
    
    static Minecraft access$2000() {
        return TPHit.mc;
    }
    
    static Minecraft access$2100() {
        return TPHit.mc;
    }
    
    static Minecraft access$2200() {
        return TPHit.mc;
    }
    
    static Minecraft access$2300() {
        return TPHit.mc;
    }
    
    static Minecraft access$2400() {
        return TPHit.mc;
    }
    
    static Minecraft access$2500() {
        return TPHit.mc;
    }
    
    static Minecraft access$2600() {
        return TPHit.mc;
    }
    
    static Minecraft access$2700() {
        return TPHit.mc;
    }
    
    static Minecraft access$2800() {
        return TPHit.mc;
    }
    
    static Minecraft access$2900() {
        return TPHit.mc;
    }
    
    static Minecraft access$3000() {
        return TPHit.mc;
    }
    
    static Minecraft access$3100() {
        return TPHit.mc;
    }
    
    static Minecraft access$3200() {
        return TPHit.mc;
    }
    
    static Minecraft access$3300() {
        return TPHit.mc;
    }
    
    static Minecraft access$3400() {
        return TPHit.mc;
    }
    
    static Minecraft access$3500() {
        return TPHit.mc;
    }
    
    static Minecraft access$3600() {
        return TPHit.mc;
    }
    
    static Minecraft access$3700() {
        return TPHit.mc;
    }
    
    static Minecraft access$3800() {
        return TPHit.mc;
    }
    
    static Minecraft access$3900() {
        return TPHit.mc;
    }
    
    static Minecraft access$4000() {
        return TPHit.mc;
    }
    
    static Minecraft access$4100() {
        return TPHit.mc;
    }
    
    static Minecraft access$4200() {
        return TPHit.mc;
    }
    
    static Minecraft access$4300() {
        return TPHit.mc;
    }
}
