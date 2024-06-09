package com.client.glowclient.modules.combat;

import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.events.*;
import com.client.glowclient.modules.*;
import net.minecraft.entity.player.*;
import com.client.glowclient.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;

public class KillAura extends ModuleContainer
{
    public static BooleanValue throughWalls;
    public static BooleanValue players;
    public static nB mode;
    public static final NumberValue range;
    public static BooleanValue silent;
    public static BooleanValue tPSsync;
    public static BooleanValue friendDetect;
    public static BooleanValue manualClick;
    public static BooleanValue passive;
    public static BooleanValue attackBox;
    public static nB priority;
    public static BooleanValue mobs;
    
    static {
        KillAura.players = ValueFactory.M("KillAura", "Players", "Target players", true);
        KillAura.mobs = ValueFactory.M("KillAura", "Mobs", "Target hostile mobs", true);
        KillAura.passive = ValueFactory.M("KillAura", "Passive", "Target passive mobs", true);
        KillAura.mode = ValueFactory.M("KillAura", "Mode", "KillAura source", "Single", "Single", "Switch");
        KillAura.priority = ValueFactory.M("KillAura", "Priority", "KillAura attack priority", "Distance", "Distance");
        KillAura.throughWalls = ValueFactory.M("KillAura", "ThroughWalls", "Hit through walls", false);
        KillAura.silent = ValueFactory.M("KillAura", "Silent", "Doesn't look at target", true);
        KillAura.tPSsync = ValueFactory.M("KillAura", "TPSsync", "KillAura TPS sync", true);
        KillAura.manualClick = ValueFactory.M("KillAura", "ManualClick", "Click to attack an entity around you. Not automatic", false);
        KillAura.friendDetect = ValueFactory.M("KillAura", "FriendDetect", "Does not attack friended entities", true);
        KillAura.attackBox = ValueFactory.M("KillAura", "AttackBox", "Shows a outline around targeted entity", true);
        range = ValueFactory.M("KillAura", "Range", "Explode hit range", 3.5, 0.5, 0.0, 10.0);
    }
    
    public KillAura() {
        super(Category.COMBAT, "KillAura", false, -1, "Attacks given entities around you");
    }
    
    private Entity M(final Vec3d p0, final Vec3d p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //     6: astore_3       
        //     7: aload_2        
        //     8: invokevirtual   net/minecraft/util/math/Vec3d.normalize:()Lnet/minecraft/util/math/Vec3d;
        //    11: astore_2       
        //    12: aconst_null    
        //    13: astore          4
        //    15: ldc2_w          -1.0
        //    18: dstore          5
        //    20: aload_3        
        //    21: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //    24: dup            
        //    25: astore          7
        //    27: monitorenter   
        //    28: aload_3        
        //    29: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //    32: invokestatic    com/google/common/collect/Lists.newArrayList:(Ljava/lang/Iterable;)Ljava/util/ArrayList;
        //    35: invokestatic    java/util/Collections.synchronizedList:(Ljava/util/List;)Ljava/util/List;
        //    38: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    43: astore_3       
        //    44: aload_3        
        //    45: invokeinterface java/util/Iterator.hasNext:()Z
        //    50: ifeq            148
        //    53: aload_3        
        //    54: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    59: checkcast       Lnet/minecraft/entity/Entity;
        //    62: dup            
        //    63: astore          8
        //    65: ifnull          44
        //    68: aload           8
        //    70: invokestatic    com/client/glowclient/utils/EntityUtils.M:(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/math/Vec3d;
        //    73: astore          9
        //    75: aload_0        
        //    76: aload           8
        //    78: aload           9
        //    80: aload_1        
        //    81: invokespecial   com/client/glowclient/modules/combat/KillAura.M:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Z
        //    84: ifeq            44
        //    87: getstatic       com/client/glowclient/modules/combat/KillAura.priority:Lcom/client/glowclient/nB;
        //    90: invokevirtual   com/client/glowclient/nB.e:()Ljava/lang/String;
        //    93: ldc             "Distance"
        //    95: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    98: ifeq            44
        //   101: aload           9
        //   103: aload_1        
        //   104: invokevirtual   net/minecraft/util/math/Vec3d.subtract:(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
        //   107: invokevirtual   net/minecraft/util/math/Vec3d.normalize:()Lnet/minecraft/util/math/Vec3d;
        //   110: aload_2        
        //   111: invokevirtual   net/minecraft/util/math/Vec3d.subtract:(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
        //   114: invokevirtual   net/minecraft/util/math/Vec3d.length:()D
        //   117: dstore          10
        //   119: dload           5
        //   121: ldc2_w          -1.0
        //   124: dcmpl          
        //   125: ifeq            136
        //   128: dload           10
        //   130: dload           5
        //   132: dcmpg          
        //   133: ifge            44
        //   136: aload           8
        //   138: astore          4
        //   140: dload           10
        //   142: dstore          5
        //   144: goto            44
        //   147: athrow         
        //   148: aload           7
        //   150: monitorexit    
        //   151: aload           4
        //   153: goto            167
        //   156: athrow         
        //   157: astore          12
        //   159: aload           7
        //   161: monitorexit    
        //   162: aload           12
        //   164: athrow         
        //   165: nop            
        //   166: athrow         
        //   167: invokestatic    com/client/glowclient/Ha.M:(Lnet/minecraft/entity/Entity;)V
        //   170: aload           4
        //   172: areturn        
        //    StackMapTable: 00 08 FF 00 2C 00 07 07 00 B3 07 00 93 07 00 93 07 00 B5 07 00 B7 03 07 00 AD 00 00 FE 00 5B 07 00 B7 07 00 93 03 FF 00 0A 00 00 00 01 07 00 DB FF 00 00 00 07 07 00 B3 07 00 93 07 00 93 07 00 B5 07 00 B7 03 07 00 AD 00 00 FF 00 07 00 00 00 01 07 00 DB FF 00 00 00 07 07 00 B3 07 00 93 07 00 93 07 00 DD 07 00 B7 03 07 00 AD 00 01 07 00 DB FF 00 07 00 00 00 01 07 00 DB FF 00 01 00 07 07 00 B3 07 00 93 07 00 93 07 00 B5 07 00 B7 03 07 00 AD 00 01 07 00 B7
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type
        //  -----  -----  -----  -----  ----
        //  28     147    157    165    Any
        //  148    151    157    165    Any
        //  157    162    157    165    Any
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public String M() {
        return String.format("%.1f", KillAura.range.k());
    }
    
    private double M() {
        if (KillAura.tPSsync.M()) {
            return -(20.0 - me.M().M().M());
        }
        return 0.0;
    }
    
    private boolean M(final Entity entity, final Vec3d vec3d, final Vec3d vec3d2) {
        final boolean b = EntityUtils.i(entity) && EntityUtils.a(entity) && !entity.equals((Object)Wrapper.mc.player) && EntityUtils.j(entity) && ((EntityUtils.e(entity) && KillAura.players.M()) || (EntityUtils.A(entity) && KillAura.mobs.M()) || (EntityUtils.K(entity) && KillAura.passive.M())) && (KillAura.range.k() <= 0.0 || vec3d.distanceTo(vec3d2) <= KillAura.range.k()) && (KillAura.throughWalls.M() || Wrapper.mc.player.canEntityBeSeen(entity));
        if (KillAura.friendDetect.M()) {
            return !Va.M().M(entity.getName()) && b;
        }
        return b;
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void M(final EventRenderWorld eventRenderWorld) {
        final Entity m;
        if ((m = Ha.M()) != null && KillAura.attackBox.M()) {
            final Entity entity = m;
            final int n = 255;
            final int n2 = 0;
            Ma.D(entity, n, n2, n2);
        }
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        final EntityPlayerSP player = Wrapper.mc.player;
        Entity entity = Ha.M();
        final EntityPlayer entityPlayer = (EntityPlayer)player;
        final Vec3d d = EntityUtils.D((Entity)entityPlayer);
        final Vec3d lookVec = entityPlayer.getLookVec();
        Entity entity3 = null;
        Label_0089: {
            Label_0088: {
                if (KillAura.mode.e().equals("Single")) {
                    if (entity != null) {
                        final Entity entity2 = entity;
                        if (this.M(entity2, EntityUtils.M(entity2), d)) {
                            break Label_0088;
                        }
                    }
                    entity3 = (entity = this.M(d, lookVec));
                    break Label_0089;
                }
                if (KillAura.mode.e().equals("Switch")) {
                    entity = this.M(d, lookVec);
                }
            }
            entity3 = entity;
        }
        if (entity3 != null) {
            if (!KillAura.manualClick.M()) {
                EntityPlayerSP entityPlayerSP;
                if (KillAura.silent.M()) {
                    entityPlayerSP = player;
                    final Entity entity4 = entity;
                    this.k = true;
                    y.M(entity4, this);
                }
                else {
                    this.k = false;
                    entityPlayerSP = player;
                    y.M(entity);
                }
                if (((EntityPlayer)entityPlayerSP).getCooledAttackStrength((float)this.M()) >= 1.0f) {
                    final ItemStack heldItemMainhand = Wrapper.mc.player.getHeldItemMainhand();
                    final ItemStack heldItemOffhand = Wrapper.mc.player.getHeldItemOffhand();
                    if (heldItemMainhand == null || (!(heldItemMainhand.getItem() instanceof ItemFood) && !(heldItemMainhand.getItem() instanceof ItemAppleGold)) || !Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                        if (KillAura.silent.M()) {
                            Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, entity);
                            Ob.M().sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                            return;
                        }
                        if (heldItemOffhand == null || (!(heldItemOffhand.getItem() instanceof ItemFood) && !(heldItemOffhand.getItem() instanceof ItemAppleGold)) || !Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                            ((EntityPlayer)player).swingArm(EnumHand.MAIN_HAND);
                            Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, entity);
                        }
                    }
                }
            }
            else if (Wrapper.mc.gameSettings.keyBindAttack.isKeyDown()) {
                final ItemStack heldItemMainhand2 = Wrapper.mc.player.getHeldItemMainhand();
                final ItemStack heldItemOffhand2 = Wrapper.mc.player.getHeldItemOffhand();
                if ((heldItemMainhand2 == null || (!(heldItemMainhand2.getItem() instanceof ItemFood) && !(heldItemMainhand2.getItem() instanceof ItemAppleGold)) || !Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown()) && (heldItemOffhand2 == null || (!(heldItemOffhand2.getItem() instanceof ItemFood) && !(heldItemOffhand2.getItem() instanceof ItemAppleGold)) || !Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown())) {
                    final boolean m = KillAura.silent.M();
                    final Entity entity5 = entity;
                    if (m) {
                        y.M(entity5, this);
                    }
                    else {
                        y.M(entity5);
                        y.M(this);
                    }
                    Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, entity);
                    ((EntityPlayer)player).swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
        else {
            y.M(this);
        }
    }
    
    @Override
    public void E() {
        final Entity entity = null;
        y.M(this);
        Ha.M(entity);
    }
}
