// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import java.util.LinkedList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraft.client.Minecraft;

public final class BlockUtils
{
    private static final /* synthetic */ Minecraft mc;
    private static final /* synthetic */ int[] llIII;
    
    static {
        lIllllI();
        mc = Wrapper.INSTANCE.mc();
    }
    
    public static Block getBlock(final BlockPos llllIIIlIIlllll) {
        return getState(llllIIIlIIlllll).getBlock();
    }
    
    public static Material getMaterial(final BlockPos llllIIIlIIlllII) {
        return getState(llllIIIlIIlllII).getMaterial();
    }
    
    private static boolean llIIIlI(final int lllIlllIllllIlI) {
        return lllIlllIllllIlI == 0;
    }
    
    public static void faceVectorPacket(final Vec3d llllIIIIlIllllI) {
        final double llllIIIIlIlllIl = llllIIIIlIllllI.x - BlockUtils.mc.player.posX;
        final double llllIIIIlIlllII = llllIIIIlIllllI.y - (BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight());
        final double llllIIIIlIllIll = llllIIIIlIllllI.z - BlockUtils.mc.player.posZ;
        final double llllIIIIlIllIlI = MathHelper.sqrt(llllIIIIlIlllIl * llllIIIIlIlllIl + llllIIIIlIllIll * llllIIIIlIllIll);
        final float llllIIIIlIllIIl = (float)Math.toDegrees(Math.atan2(llllIIIIlIllIll, llllIIIIlIlllIl)) - 90.0f;
        final float llllIIIIlIllIII = (float)(-Math.toDegrees(Math.atan2(llllIIIIlIlllII, llllIIIIlIllIlI)));
        BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(BlockUtils.mc.player.rotationYaw + MathHelper.wrapDegrees(llllIIIIlIllIIl - BlockUtils.mc.player.rotationYaw), BlockUtils.mc.player.rotationPitch + MathHelper.wrapDegrees(llllIIIIlIllIII - BlockUtils.mc.player.rotationPitch), BlockUtils.mc.player.onGround));
    }
    
    public static boolean canBeClicked(final BlockPos llllIIIlIIllIIl) {
        return getBlock(llllIIIlIIllIIl).canCollideCheck(getState(llllIIIlIIllIIl), (boolean)(BlockUtils.llIII[0] != 0));
    }
    
    public static void faceBlockPacket(final BlockPos llllIIIIIllIlII) {
        final double llllIIIIIllIIll = llllIIIIIllIlII.getX() + 0.5 - BlockUtils.mc.player.posX;
        final double llllIIIIIllIIlI = llllIIIIIllIlII.getY() + 0.0 - (BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight());
        final double llllIIIIIllIIIl = llllIIIIIllIlII.getZ() + 0.5 - BlockUtils.mc.player.posZ;
        final double llllIIIIIllIIII = MathHelper.sqrt(llllIIIIIllIIll * llllIIIIIllIIll + llllIIIIIllIIIl * llllIIIIIllIIIl);
        final float llllIIIIIlIllll = (float)(Math.atan2(llllIIIIIllIIIl, llllIIIIIllIIll) * 180.0 / 3.141592653589793) - 90.0f;
        final float llllIIIIIlIlllI = (float)(-(Math.atan2(llllIIIIIllIIlI, llllIIIIIllIIII) * 180.0 / 3.141592653589793));
        BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(BlockUtils.mc.player.rotationYaw + MathHelper.wrapDegrees(llllIIIIIlIllll - BlockUtils.mc.player.rotationYaw), BlockUtils.mc.player.rotationPitch + MathHelper.wrapDegrees(llllIIIIIlIlllI - BlockUtils.mc.player.rotationPitch), BlockUtils.mc.player.onGround));
    }
    
    private static int llIIlIl(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    public static void faceBlockClientHorizontally(final BlockPos llllIIIIIIllllI) {
        final double llllIIIIIlIIIIl = llllIIIIIIllllI.getX() + 0.5 - BlockUtils.mc.player.posX;
        final double llllIIIIIlIIIII = llllIIIIIIllllI.getZ() + 0.5 - BlockUtils.mc.player.posZ;
        final float llllIIIIIIlllll = (float)(Math.atan2(llllIIIIIlIIIII, llllIIIIIlIIIIl) * 180.0 / 3.141592653589793) - 90.0f;
        BlockUtils.mc.player.rotationYaw += MathHelper.wrapDegrees(llllIIIIIIlllll - BlockUtils.mc.player.rotationYaw);
    }
    
    public static float getPlayerBlockDistance(final BlockPos llllIIIIIIllIIl) {
        return getPlayerBlockDistance(llllIIIIIIllIIl.getX(), llllIIIIIIllIIl.getY(), llllIIIIIIllIIl.getZ());
    }
    
    private static boolean llIllII(final int lllIllllIIIlIll, final int lllIllllIIIlIlI) {
        return lllIllllIIIlIll == lllIllllIIIlIlI;
    }
    
    private static boolean llIIlll(final Object lllIlllIllllllI) {
        return lllIlllIllllllI == null;
    }
    
    public static void breakBlocksPacketSpam(final Iterable<BlockPos> lllIlllllIIIIIl) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore_1        /* lllIlllllIIIIII */
        //     4: getstatic       com/krazzzzymonkey/catalyst/utils/system/Wrapper.INSTANCE:Lcom/krazzzzymonkey/catalyst/utils/system/Wrapper;
        //     7: invokevirtual   com/krazzzzymonkey/catalyst/utils/system/Wrapper.player:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //    10: getfield        net/minecraft/client/entity/EntityPlayerSP.connection:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //    13: astore_2        /* lllIllllIllllll */
        //    14: aload_0         /* lllIllllIlllllI */
        //    15: invokeinterface java/lang/Iterable.iterator:()Ljava/util/Iterator;
        //    20: astore_3        /* lllIllllIlllIll */
        //    21: aload_3         /* lllIllllIlllIll */
        //    22: invokeinterface java/util/Iterator.hasNext:()Z
        //    27: invokestatic    com/krazzzzymonkey/catalyst/utils/BlockUtils.llIlIIl:(I)Z
        //    30: ifeq            271
        //    33: aload_3         /* lllIllllIlllIll */
        //    34: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    39: checkcast       Lnet/minecraft/util/math/BlockPos;
        //    42: astore          lllIlllllIIIIlI
        //    44: new             Lnet/minecraft/util/math/Vec3d;
        //    47: dup            
        //    48: aload           lllIlllllIIIIlI
        //    50: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //    53: ldc2_w          0.5
        //    56: ldc2_w          0.5
        //    59: ldc2_w          0.5
        //    62: invokevirtual   net/minecraft/util/math/Vec3d.add:(DDD)Lnet/minecraft/util/math/Vec3d;
        //    65: astore          lllIlllllIIIlII
        //    67: aload_1         /* lllIlllllIIIIII */
        //    68: aload           lllIlllllIIIlII
        //    70: invokevirtual   net/minecraft/util/math/Vec3d.squareDistanceTo:(Lnet/minecraft/util/math/Vec3d;)D
        //    73: dstore          lllIlllllIIIIll
        //    75: invokestatic    net/minecraft/util/EnumFacing.values:()[Lnet/minecraft/util/EnumFacing;
        //    78: astore          lllIllllIllIlll
        //    80: aload           lllIllllIllIlll
        //    82: arraylength    
        //    83: istore          lllIllllIllIllI
        //    85: getstatic       com/krazzzzymonkey/catalyst/utils/BlockUtils.llIII:[I
        //    88: iconst_0       
        //    89: iaload         
        //    90: istore          lllIllllIllIlIl
        //    92: iload           lllIllllIllIlIl
        //    94: iload           lllIllllIllIllI
        //    96: invokestatic    com/krazzzzymonkey/catalyst/utils/BlockUtils.llIIIII:(II)Z
        //    99: ifeq            239
        //   102: aload           lllIllllIllIlll
        //   104: iload           lllIllllIllIlIl
        //   106: aaload         
        //   107: astore          lllIlllllIIIlIl
        //   109: aload           lllIlllllIIIlII
        //   111: new             Lnet/minecraft/util/math/Vec3d;
        //   114: dup            
        //   115: aload           lllIlllllIIIlIl
        //   117: invokevirtual   net/minecraft/util/EnumFacing.getDirectionVec:()Lnet/minecraft/util/math/Vec3i;
        //   120: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //   123: ldc2_w          0.5
        //   126: invokevirtual   net/minecraft/util/math/Vec3d.scale:(D)Lnet/minecraft/util/math/Vec3d;
        //   129: invokevirtual   net/minecraft/util/math/Vec3d.add:(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
        //   132: astore          lllIlllllIIIllI
        //   134: aload_1         /* lllIlllllIIIIII */
        //   135: aload           lllIlllllIIIllI
        //   137: invokevirtual   net/minecraft/util/math/Vec3d.squareDistanceTo:(Lnet/minecraft/util/math/Vec3d;)D
        //   140: dload           lllIlllllIIIIll
        //   142: invokestatic    com/krazzzzymonkey/catalyst/utils/BlockUtils.llIlIII:(DD)I
        //   145: invokestatic    com/krazzzzymonkey/catalyst/utils/BlockUtils.llIIIIl:(I)Z
        //   148: ifeq            169
        //   151: ldc_w           ""
        //   154: invokevirtual   java/lang/String.length:()I
        //   157: pop            
        //   158: ldc_w           "   "
        //   161: invokevirtual   java/lang/String.length:()I
        //   164: ineg           
        //   165: iflt            224
        //   168: return         
        //   169: aload_2         /* lllIllllIllllll */
        //   170: new             Lnet/minecraft/network/play/client/CPacketPlayerDigging;
        //   173: dup            
        //   174: getstatic       net/minecraft/network/play/client/CPacketPlayerDigging$Action.START_DESTROY_BLOCK:Lnet/minecraft/network/play/client/CPacketPlayerDigging$Action;
        //   177: aload           lllIlllllIIIIlI
        //   179: aload           lllIlllllIIIlIl
        //   181: invokespecial   net/minecraft/network/play/client/CPacketPlayerDigging.<init>:(Lnet/minecraft/network/play/client/CPacketPlayerDigging$Action;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)V
        //   184: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   187: aload_2         /* lllIllllIllllll */
        //   188: new             Lnet/minecraft/network/play/client/CPacketPlayerDigging;
        //   191: dup            
        //   192: getstatic       net/minecraft/network/play/client/CPacketPlayerDigging$Action.STOP_DESTROY_BLOCK:Lnet/minecraft/network/play/client/CPacketPlayerDigging$Action;
        //   195: aload           lllIlllllIIIIlI
        //   197: aload           lllIlllllIIIlIl
        //   199: invokespecial   net/minecraft/network/play/client/CPacketPlayerDigging.<init>:(Lnet/minecraft/network/play/client/CPacketPlayerDigging$Action;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)V
        //   202: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   205: ldc_w           ""
        //   208: invokevirtual   java/lang/String.length:()I
        //   211: pop            
        //   212: sipush          192
        //   215: sipush          196
        //   218: ixor           
        //   219: ineg           
        //   220: ifle            239
        //   223: return         
        //   224: iinc            lllIllllIllIlIl, 1
        //   227: ldc_w           ""
        //   230: invokevirtual   java/lang/String.length:()I
        //   233: pop            
        //   234: aconst_null    
        //   235: ifnull          92
        //   238: return         
        //   239: ldc_w           ""
        //   242: invokevirtual   java/lang/String.length:()I
        //   245: pop            
        //   246: ldc_w           "   "
        //   249: invokevirtual   java/lang/String.length:()I
        //   252: sipush          183
        //   255: sipush          169
        //   258: ixor           
        //   259: bipush          16
        //   261: bipush          14
        //   263: ixor           
        //   264: iconst_m1      
        //   265: ixor           
        //   266: iand           
        //   267: if_icmpgt       21
        //   270: return         
        //   271: return         
        //    Signature:
        //  (Ljava/lang/Iterable<Lnet/minecraft/util/math/BlockPos;>;)V
        //    StackMapTable: 00 06 FE 00 15 07 00 40 07 00 88 07 00 FD FF 00 46 00 0A 07 00 F7 07 00 40 07 00 88 07 00 FD 07 00 B0 07 00 40 03 07 01 1B 01 01 00 00 FD 00 4C 07 01 15 07 00 40 36 F9 00 0E FF 00 1F 00 04 07 00 F7 07 00 40 07 00 88 07 00 FD 00 00
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.assembler.ir.StackMappingVisitor.push(StackMappingVisitor.java:290)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:833)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static boolean llIIIIl(final int lllIlllIllllIII) {
        return lllIlllIllllIII >= 0;
    }
    
    private static int llIlIII(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    public static boolean placeBlockSimple(final BlockPos llllIIIIlllIIII) {
        final Vec3d llllIIIIllIllll = new Vec3d(BlockUtils.mc.player.posX, BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight(), BlockUtils.mc.player.posZ);
        final boolean llllIIIIllIllII = (Object)EnumFacing.values();
        final char llllIIIIllIlIll = (char)llllIIIIllIllII.length;
        double llllIIIIllIlIlI = BlockUtils.llIII[0];
        while (llIIIII((int)llllIIIIllIlIlI, llllIIIIllIlIll)) {
            final EnumFacing llllIIIIlllIIIl = llllIIIIllIllII[llllIIIIllIlIlI];
            final BlockPos llllIIIIlllIlII = llllIIIIlllIIII.offset(llllIIIIlllIIIl);
            final EnumFacing llllIIIIlllIIll = llllIIIIlllIIIl.getOpposite();
            if (llIIIlI(getBlock(llllIIIIlllIlII).canCollideCheck(BlockUtils.mc.world.getBlockState(llllIIIIlllIlII), (boolean)(BlockUtils.llIII[0] != 0)) ? 1 : 0)) {
                "".length();
                if ("   ".length() == 0) {
                    return ((0x13 ^ 0x1C) & ~(0x89 ^ 0x86)) != 0x0;
                }
            }
            else {
                final Vec3d llllIIIIlllIIlI = new Vec3d((Vec3i)llllIIIIlllIlII).add(0.5, 0.5, 0.5).add(new Vec3d(llllIIIIlllIIll.getDirectionVec()).scale(0.5));
                if (!llIIIll(llIIlII(llllIIIIllIllll.squareDistanceTo(llllIIIIlllIIlI), 36.0))) {
                    BlockUtils.mc.playerController.processRightClickBlock(BlockUtils.mc.player, BlockUtils.mc.world, llllIIIIlllIlII, llllIIIIlllIIll, llllIIIIlllIIlI, EnumHand.MAIN_HAND);
                    "".length();
                    return BlockUtils.llIII[1] != 0;
                }
                "".length();
                if ((0xCE ^ 0xB7 ^ (0x6D ^ 0x10)) < ((0xDC ^ 0x9F ^ (0x91 ^ 0x85)) & (65 + 173 - 145 + 104 ^ 86 + 106 - 122 + 76 ^ -" ".length()))) {
                    return ((173 + 39 - 127 + 97 ^ 98 + 60 - 109 + 95) & (43 + 63 - 94 + 121 ^ 89 + 159 - 107 + 22 ^ -" ".length())) != 0x0;
                }
            }
            ++llllIIIIllIlIlI;
            "".length();
            if ((0x71 ^ 0x75) <= "  ".length()) {
                return ("   ".length() & ~"   ".length()) != 0x0;
            }
        }
        return BlockUtils.llIII[0] != 0;
    }
    
    private static void lIllllI() {
        (llIII = new int[3])[0] = ((28 + 159 - 95 + 98 ^ 134 + 119 - 251 + 161) & (0x32 ^ 0x39 ^ (0x8F ^ 0x99) ^ -" ".length()));
        BlockUtils.llIII[1] = " ".length();
        BlockUtils.llIII[2] = -" ".length();
    }
    
    private static boolean llIIIII(final int lllIllllIIIIlll, final int lllIllllIIIIllI) {
        return lllIllllIIIIlll < lllIllllIIIIllI;
    }
    
    private static int llIIlII(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    public static IBlockState getState(final BlockPos llllIIIlIlIIIlI) {
        return BlockUtils.mc.world.getBlockState(llllIIIlIlIIIlI);
    }
    
    private static int lIlllll(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    private static boolean llIlIIl(final int lllIlllIlllllII) {
        return lllIlllIlllllII != 0;
    }
    
    public static float getBlockDistance(final float lllIlllllllllll, final float lllIllllllllllI, final float lllIlllllllllIl) {
        return MathHelper.sqrt((lllIlllllllllll - 0.5f) * (lllIlllllllllll - 0.5f) + (lllIllllllllllI - 0.5f) * (lllIllllllllllI - 0.5f) + (lllIlllllllllIl - 0.5f) * (lllIlllllllllIl - 0.5f));
    }
    
    public static float getHardness(final BlockPos llllIIIlIIlIlll) {
        return getState(llllIIIlIIlIlll).getPlayerRelativeBlockHardness((EntityPlayer)Wrapper.INSTANCE.player(), (World)Wrapper.INSTANCE.world(), llllIIIlIIlIlll);
    }
    
    private static boolean llIlIll(final int lllIlllIlllIIll, final int lllIlllIlllIIlI) {
        return lllIlllIlllIIll != lllIlllIlllIIlI;
    }
    
    public static float getHorizontalPlayerBlockDistance(final BlockPos lllIlllllllIllI) {
        final float lllIllllllllIII = (float)(BlockUtils.mc.player.posX - lllIlllllllIllI.getX());
        final float lllIlllllllIlll = (float)(BlockUtils.mc.player.posZ - lllIlllllllIllI.getZ());
        return MathHelper.sqrt((lllIllllllllIII - 0.5f) * (lllIllllllllIII - 0.5f) + (lllIlllllllIlll - 0.5f) * (lllIlllllllIlll - 0.5f));
    }
    
    public static boolean breakBlockSimple(final BlockPos lllIlllllIlllII) {
        EnumFacing lllIllllllIIIlI = null;
        final EnumFacing[] lllIllllllIIIIl = EnumFacing.values();
        final Vec3d lllIllllllIIIII = Utils.getEyesPos();
        final Vec3d lllIlllllIlllll = getState(lllIlllllIlllII).getBoundingBox((IBlockAccess)Wrapper.INSTANCE.world(), lllIlllllIlllII).getCenter();
        final Vec3d lllIlllllIllllI = new Vec3d((Vec3i)lllIlllllIlllII).add(lllIlllllIlllll);
        final Vec3d[] lllIlllllIlllIl = new Vec3d[lllIllllllIIIIl.length];
        int lllIllllllIIlll = BlockUtils.llIII[0];
        while (llIIIII(lllIllllllIIlll, lllIllllllIIIIl.length)) {
            final Vec3i lllIllllllIlIIl = lllIllllllIIIIl[lllIllllllIIlll].getDirectionVec();
            final Vec3d lllIllllllIlIII = new Vec3d(lllIlllllIlllll.x * lllIllllllIlIIl.getX(), lllIlllllIlllll.y * lllIllllllIlIIl.getY(), lllIlllllIlllll.z * lllIllllllIlIIl.getZ());
            lllIlllllIlllIl[lllIllllllIIlll] = lllIlllllIllllI.add(lllIllllllIlIII);
            ++lllIllllllIIlll;
            "".length();
            if ("  ".length() == 0) {
                return ((64 + 111 + 9 + 1 ^ 114 + 112 - 115 + 25) & (0x1A ^ 0x6C ^ (0x76 ^ 0x31) ^ -" ".length())) != 0x0;
            }
        }
        int lllIllllllIIllI = BlockUtils.llIII[0];
        while (llIIIII(lllIllllllIIllI, lllIllllllIIIIl.length)) {
            if (llIIllI(Wrapper.INSTANCE.world().rayTraceBlocks(lllIllllllIIIII, lllIlllllIlllIl[lllIllllllIIllI], (boolean)(BlockUtils.llIII[0] != 0), (boolean)(BlockUtils.llIII[1] != 0), (boolean)(BlockUtils.llIII[0] != 0)))) {
                "".length();
                if (" ".length() == "   ".length()) {
                    return ((0x68 ^ 0xB ^ (0xBF ^ 0x97)) & (20 + 44 - 61 + 194 ^ 105 + 43 - 62 + 56 ^ -" ".length())) != 0x0;
                }
                ++lllIllllllIIllI;
                "".length();
                if ("   ".length() < 0) {
                    return ((154 + 13 - 118 + 114 ^ 86 + 125 - 105 + 51) & (19 + 138 - 115 + 102 ^ 71 + 28 - 1 + 76 ^ -" ".length())) != 0x0;
                }
                continue;
            }
            else {
                lllIllllllIIIlI = lllIllllllIIIIl[lllIllllllIIllI];
                "".length();
                if (null != null) {
                    return ((0xCD ^ 0x8D ^ (0x5C ^ 0x2E)) & (0x4D ^ 0x5A ^ (0x96 ^ 0xB3) ^ -" ".length())) != 0x0;
                }
                break;
            }
        }
        if (llIIlll(lllIllllllIIIlI)) {
            final double lllIllllllIIlII = lllIllllllIIIII.squareDistanceTo(lllIlllllIllllI);
            int lllIllllllIIlIl = BlockUtils.llIII[0];
            while (llIIIII(lllIllllllIIlIl, lllIllllllIIIIl.length)) {
                if (llIIIIl(llIIlIl(lllIllllllIIIII.squareDistanceTo(lllIlllllIlllIl[lllIllllllIIlIl]), lllIllllllIIlII))) {
                    "".length();
                    if (-"   ".length() >= 0) {
                        return ((0x7D ^ 0x45) & ~(0x23 ^ 0x1B)) != 0x0;
                    }
                    ++lllIllllllIIlIl;
                    "".length();
                    if (-(0x5E ^ 0x72 ^ (0x2 ^ 0x2A)) > 0) {
                        return ((80 + 1 - 23 + 133 ^ 56 + 106 - 106 + 85) & (0x33 ^ 0x36 ^ (0xF0 ^ 0xC7) ^ -" ".length())) != 0x0;
                    }
                    continue;
                }
                else {
                    lllIllllllIIIlI = lllIllllllIIIIl[lllIllllllIIlIl];
                    "".length();
                    if (" ".length() >= "   ".length()) {
                        return ((0xBF ^ 0xB1 ^ (0xA2 ^ 0xAA)) & (0x46 ^ 0x41 ^ " ".length() ^ -" ".length())) != 0x0;
                    }
                    break;
                }
            }
        }
        if (llIIlll(lllIllllllIIIlI)) {
            lllIllllllIIIlI = lllIllllllIIIIl[BlockUtils.llIII[0]];
        }
        Utils.faceVectorPacket(lllIlllllIlllIl[lllIllllllIIIlI.ordinal()]);
        if (llIIIlI(BlockUtils.mc.playerController.onPlayerDamageBlock(lllIlllllIlllII, lllIllllllIIIlI) ? 1 : 0)) {
            return BlockUtils.llIII[0] != 0;
        }
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        return BlockUtils.llIII[1] != 0;
    }
    
    public static void faceBlockClient(final BlockPos llllIIIIlIIIIlI) {
        final double llllIIIIlIIlIII = llllIIIIlIIIIlI.getX() + 0.5 - BlockUtils.mc.player.posX;
        final double llllIIIIlIIIlll = llllIIIIlIIIIlI.getY() + 0.0 - (BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight());
        final double llllIIIIlIIIllI = llllIIIIlIIIIlI.getZ() + 0.5 - BlockUtils.mc.player.posZ;
        final double llllIIIIlIIIlIl = MathHelper.sqrt(llllIIIIlIIlIII * llllIIIIlIIlIII + llllIIIIlIIIllI * llllIIIIlIIIllI);
        final float llllIIIIlIIIlII = (float)(Math.atan2(llllIIIIlIIIllI, llllIIIIlIIlIII) * 180.0 / 3.141592653589793) - 90.0f;
        final float llllIIIIlIIIIll = (float)(-(Math.atan2(llllIIIIlIIIlll, llllIIIIlIIIlIl) * 180.0 / 3.141592653589793));
        BlockUtils.mc.player.rotationYaw += MathHelper.wrapDegrees(llllIIIIlIIIlII - BlockUtils.mc.player.rotationYaw);
        BlockUtils.mc.player.rotationPitch += MathHelper.wrapDegrees(llllIIIIlIIIIll - BlockUtils.mc.player.rotationPitch);
    }
    
    public static boolean placeBlockLegit(final BlockPos llllIIIlIIIIllI) {
        final Vec3d llllIIIlIIIIlll = new Vec3d(BlockUtils.mc.player.posX, BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight(), BlockUtils.mc.player.posZ);
        final byte llllIIIlIIIIlII = (Object)EnumFacing.values();
        final short llllIIIlIIIIIll = (short)llllIIIlIIIIlII.length;
        float llllIIIlIIIIIlI = BlockUtils.llIII[0];
        while (llIIIII((int)llllIIIlIIIIIlI, llllIIIlIIIIIll)) {
            final EnumFacing llllIIIlIIIlIIl = llllIIIlIIIIlII[llllIIIlIIIIIlI];
            final BlockPos llllIIIlIIIllII = llllIIIlIIIIllI.offset(llllIIIlIIIlIIl);
            final EnumFacing llllIIIlIIIlIll = llllIIIlIIIlIIl.getOpposite();
            if (llIIIIl(lIlllll(llllIIIlIIIIlll.squareDistanceTo(new Vec3d((Vec3i)llllIIIlIIIIllI).add(0.5, 0.5, 0.5)), llllIIIlIIIIlll.squareDistanceTo(new Vec3d((Vec3i)llllIIIlIIIllII).add(0.5, 0.5, 0.5))))) {
                "".length();
                if (" ".length() > "  ".length()) {
                    return ((0x1D ^ 0x5F ^ (0x2A ^ 0x79)) & (0x66 ^ 0x41 ^ (0x6E ^ 0x58) ^ -" ".length())) != 0x0;
                }
            }
            else if (llIIIlI(getBlock(llllIIIlIIIllII).canCollideCheck(BlockUtils.mc.world.getBlockState(llllIIIlIIIllII), (boolean)(BlockUtils.llIII[0] != 0)) ? 1 : 0)) {
                "".length();
                if ((0x86 ^ 0x83) <= 0) {
                    return ((0xB ^ 0x47) & ~(0xEF ^ 0xA3)) != 0x0;
                }
            }
            else {
                final Vec3d llllIIIlIIIlIlI = new Vec3d((Vec3i)llllIIIlIIIllII).add(0.5, 0.5, 0.5).add(new Vec3d(llllIIIlIIIlIll.getDirectionVec()).scale(0.5));
                if (llIIIll(lIlllll(llllIIIlIIIIlll.squareDistanceTo(llllIIIlIIIlIlI), 18.0625))) {
                    "".length();
                    if (((0x65 ^ 0x7F) & ~(0x8 ^ 0x12)) >= "  ".length()) {
                        return ((0x5B ^ 0x6B) & ~(0x45 ^ 0x75)) != 0x0;
                    }
                }
                else {
                    faceVectorPacket(llllIIIlIIIlIlI);
                }
            }
            ++llllIIIlIIIIIlI;
            "".length();
            if ((0x6A ^ 0x6E) < 0) {
                return ((0x6E ^ 0x40) & ~(0x8C ^ 0xA2)) != 0x0;
            }
        }
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        BlockUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
        return BlockUtils.llIII[1] != 0;
    }
    
    public static float getPlayerBlockDistance(final double llllIIIIIIIlIll, final double llllIIIIIIlIIII, final double llllIIIIIIIlIIl) {
        final float llllIIIIIIIlllI = (float)(BlockUtils.mc.player.posX - llllIIIIIIIlIll);
        final float llllIIIIIIIllIl = (float)(BlockUtils.mc.player.posY - llllIIIIIIlIIII);
        final float llllIIIIIIIllII = (float)(BlockUtils.mc.player.posZ - llllIIIIIIIlIIl);
        return getBlockDistance(llllIIIIIIIlllI, llllIIIIIIIllIl, llllIIIIIIIllII);
    }
    
    public static LinkedList<BlockPos> findBlocksNearEntity(final EntityLivingBase lllIllllIIllllI, final int lllIllllIIllIIl, final int lllIllllIIllIII, final int lllIllllIIlIlll) {
        final LinkedList<BlockPos> lllIllllIIllIlI = new LinkedList<BlockPos>();
        int lllIllllIIlllll = (int)Wrapper.INSTANCE.player().posX - lllIllllIIlIlll;
        while (llIlIlI(lllIllllIIlllll, (int)Wrapper.INSTANCE.player().posX + lllIllllIIlIlll)) {
            int lllIllllIlIIIII = (int)Wrapper.INSTANCE.player().posZ - lllIllllIIlIlll;
            while (llIlIlI(lllIllllIlIIIII, (int)Wrapper.INSTANCE.player().posZ + lllIllllIIlIlll)) {
                final int lllIllllIlIIIIl = Wrapper.INSTANCE.world().getHeight(lllIllllIIlllll, lllIllllIlIIIII);
                int lllIllllIlIIIlI = BlockUtils.llIII[0];
                while (llIlIlI(lllIllllIlIIIlI, lllIllllIlIIIIl)) {
                    final BlockPos lllIllllIlIIllI = new BlockPos(lllIllllIIlllll, lllIllllIlIIIlI, lllIllllIlIIIII);
                    final IBlockState lllIllllIlIIlIl = Wrapper.INSTANCE.world().getBlockState(lllIllllIlIIllI);
                    if (!llIlIll(lllIllllIIllIIl, BlockUtils.llIII[2]) || llIllII(lllIllllIIllIII, BlockUtils.llIII[2])) {
                        lllIllllIIllIlI.add(lllIllllIlIIllI);
                        "".length();
                        "".length();
                        if ("   ".length() != "   ".length()) {
                            return null;
                        }
                    }
                    else {
                        final int lllIllllIlIIlII = Block.getIdFromBlock(lllIllllIlIIlIl.getBlock());
                        final int lllIllllIlIIIll = lllIllllIlIIlIl.getBlock().getMetaFromState(lllIllllIlIIlIl);
                        if (llIllII(lllIllllIlIIlII, lllIllllIIllIIl) && llIllII(lllIllllIlIIIll, lllIllllIIllIII)) {
                            lllIllllIIllIlI.add(lllIllllIlIIllI);
                            "".length();
                            "".length();
                            if (null != null) {
                                return null;
                            }
                        }
                    }
                    ++lllIllllIlIIIlI;
                    "".length();
                    if (((0x4 ^ 0x3A) & ~(0x40 ^ 0x7E)) >= "  ".length()) {
                        return null;
                    }
                }
                ++lllIllllIlIIIII;
                "".length();
                if ((" ".length() & ~" ".length()) >= "  ".length()) {
                    return null;
                }
            }
            ++lllIllllIIlllll;
            "".length();
            if ((146 + 149 - 142 + 35 ^ 120 + 23 + 20 + 21) <= 0) {
                return null;
            }
        }
        return lllIllllIIllIlI;
    }
    
    private static boolean llIIllI(final Object lllIllllIIIIIII) {
        return lllIllllIIIIIII != null;
    }
    
    private static boolean llIlIlI(final int lllIllllIIIIIll, final int lllIllllIIIIIlI) {
        return lllIllllIIIIIll <= lllIllllIIIIIlI;
    }
    
    private static boolean llIIIll(final int lllIlllIlllIllI) {
        return lllIlllIlllIllI > 0;
    }
}
