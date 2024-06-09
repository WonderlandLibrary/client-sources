// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import java.util.Iterator;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import java.util.Arrays;
import java.lang.reflect.Method;
import com.krazzzzymonkey.catalyst.utils.system.Mapping;
import net.minecraft.block.material.Material;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import com.mojang.authlib.GameProfile;
import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import com.krazzzzymonkey.catalyst.managers.EnemyManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EntityLivingBase;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.util.math.MathHelper;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraft.util.math.Vec3d;
import java.util.Random;

public class Utils
{
    public static /* synthetic */ float[] rotationsToBlock;
    private static final /* synthetic */ Random RANDOM;
    private static final /* synthetic */ int[] llll;
    private static final /* synthetic */ String[] I;
    
    public static float[] getNeededRotations(final Vec3d llllIlIIllllIlI) {
        final Vec3d llllIlIIllllIIl = getEyesPos();
        final double llllIlIIllllIII = llllIlIIllllIlI.x - llllIlIIllllIIl.x;
        final double llllIlIIlllIlll = llllIlIIllllIlI.y - llllIlIIllllIIl.y;
        final double llllIlIIlllIllI = llllIlIIllllIlI.z - llllIlIIllllIIl.z;
        final double llllIlIIlllIlIl = Math.sqrt(llllIlIIllllIII * llllIlIIllllIII + llllIlIIlllIllI * llllIlIIlllIllI);
        final float llllIlIIlllIlII = (float)Math.toDegrees(Math.atan2(llllIlIIlllIllI, llllIlIIllllIII)) - 90.0f;
        final float llllIlIIlllIIll = (float)(-Math.toDegrees(Math.atan2(llllIlIIlllIlll, llllIlIIlllIlIl)));
        final float[] array = new float[Utils.llll[2]];
        array[Utils.llll[1]] = Wrapper.INSTANCE.player().rotationYaw + MathHelper.wrapDegrees(llllIlIIlllIlII - Wrapper.INSTANCE.player().rotationYaw);
        array[Utils.llll[0]] = Wrapper.INSTANCE.player().rotationPitch + MathHelper.wrapDegrees(llllIlIIlllIIll - Wrapper.INSTANCE.player().rotationPitch);
        return array;
    }
    
    public static double round(final double llllIlIlllIlIlI, final int llllIlIlllIlIIl) {
        if (lIIIIIl(llllIlIlllIlIIl)) {
            throw new IllegalArgumentException();
        }
        BigDecimal llllIlIlllIlIII = new BigDecimal(llllIlIlllIlIlI);
        llllIlIlllIlIII = llllIlIlllIlIII.setScale(llllIlIlllIlIIl, RoundingMode.HALF_UP);
        return llllIlIlllIlIII.doubleValue();
    }
    
    public static Vec3d getRandomCenter(final AxisAlignedBB llllIlllIlIllII) {
        return new Vec3d(llllIlllIlIllII.minX + (llllIlllIlIllII.maxX - llllIlllIlIllII.minX) * 0.8 * Math.random(), llllIlllIlIllII.minY + (llllIlllIlIllII.maxY - llllIlllIlIllII.minY) * Math.random() + 0.1 * Math.random(), llllIlllIlIllII.minZ + (llllIlllIlIllII.maxZ - llllIlllIlIllII.minZ) * 0.8 * Math.random());
    }
    
    public static float[] getDirectionToBlock(final int llllIlIllIlIIIl, final int llllIlIllIlIIII, final int llllIlIllIIllll, final EnumFacing llllIlIllIIlllI) {
        final EntityEgg llllIlIllIIllIl = new EntityEgg((World)Wrapper.INSTANCE.world());
        llllIlIllIIllIl.posX = llllIlIllIlIIIl + 0.5;
        llllIlIllIIllIl.posY = llllIlIllIlIIII + 0.5;
        llllIlIllIIllIl.posZ = llllIlIllIIllll + 0.5;
        final EntityEgg entityEgg = llllIlIllIIllIl;
        entityEgg.posX += llllIlIllIIlllI.getDirectionVec().getX() * 0.25;
        final EntityEgg entityEgg2 = llllIlIllIIllIl;
        entityEgg2.posY += llllIlIllIIlllI.getDirectionVec().getY() * 0.25;
        final EntityEgg entityEgg3 = llllIlIllIIllIl;
        entityEgg3.posZ += llllIlIllIIlllI.getDirectionVec().getZ() * 0.25;
        return getDirectionToEntity((Entity)llllIlIllIIllIl);
    }
    
    public static void faceVectorPacketInstant(final Vec3d llllIlllIlIIIll) {
        Utils.rotationsToBlock = getNeededRotations(llllIlllIlIIIll);
    }
    
    private static String lIlI(final String llllIIlIIllIIIl, final String llllIIlIIlIlllI) {
        try {
            final SecretKeySpec llllIIlIIllIlII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIIlIIlIlllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llllIIlIIllIIll = Cipher.getInstance("Blowfish");
            llllIIlIIllIIll.init(Utils.llll[2], llllIIlIIllIlII);
            return new String(llllIIlIIllIIll.doFinal(Base64.getDecoder().decode(llllIIlIIllIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIIlIIllIIlI) {
            llllIIlIIllIIlI.printStackTrace();
            return null;
        }
    }
    
    public static boolean isMoving(final Entity llllIlllIlIlIlI) {
        int n;
        if (lllIll(lllIlI(llllIlllIlIlIlI.motionX, 0.0)) && lllIll(lllIlI(llllIlllIlIlIlI.motionZ, 0.0)) && (!llllII(lllIlI(llllIlllIlIlIlI.motionY, 0.0)) || llllIl(lllIlI(llllIlllIlIlIlI.motionY, 0.0)))) {
            n = Utils.llll[0];
            "".length();
            if ("   ".length() == 0) {
                return ((0x64 ^ 0x49 ^ (0x70 ^ 0x5A)) & (0x3B ^ 0x71 ^ (0xFC ^ 0xB1) ^ -" ".length())) != 0x0;
            }
        }
        else {
            n = Utils.llll[1];
        }
        return n != 0;
    }
    
    private static int lIlIIll(final float n, final float n2) {
        return fcmpl(n, n2);
    }
    
    public static boolean isInsideBlock(final EntityLivingBase llllIlIIIlIIlll) {
        int llllIlIIIlIlIIl = MathHelper.floor(llllIlIIIlIIlll.getEntityBoundingBox().minX);
        while (lIIIlII(llllIlIIIlIlIIl, MathHelper.floor(llllIlIIIlIIlll.getEntityBoundingBox().maxX) + Utils.llll[0])) {
            int llllIlIIIlIlIlI = MathHelper.floor(llllIlIIIlIIlll.getEntityBoundingBox().minY);
            while (lIIIlII(llllIlIIIlIlIlI, MathHelper.floor(llllIlIIIlIIlll.getEntityBoundingBox().maxY) + Utils.llll[0])) {
                int llllIlIIIlIlIll = MathHelper.floor(llllIlIIIlIIlll.getEntityBoundingBox().minZ);
                while (lIIIlII(llllIlIIIlIlIll, MathHelper.floor(llllIlIIIlIIlll.getEntityBoundingBox().maxZ) + Utils.llll[0])) {
                    final Block llllIlIIIlIllIl = Wrapper.INSTANCE.world().getBlockState(new BlockPos(llllIlIIIlIlIIl, llllIlIIIlIlIlI, llllIlIIIlIlIll)).getBlock();
                    final AxisAlignedBB llllIlIIIlIllII;
                    if (lIIIIll(llllIlIIIlIllIl) && llllII((llllIlIIIlIllIl instanceof BlockAir) ? 1 : 0) && lIIIIll(llllIlIIIlIllII = llllIlIIIlIllIl.getCollisionBoundingBox(Wrapper.INSTANCE.world().getBlockState(new BlockPos(llllIlIIIlIlIIl, llllIlIIIlIlIlI, llllIlIIIlIlIll)), (IBlockAccess)Wrapper.INSTANCE.world(), new BlockPos(llllIlIIIlIlIIl, llllIlIIIlIlIlI, llllIlIIIlIlIll))) && lllIll(llllIlIIIlIIlll.getEntityBoundingBox().intersects(llllIlIIIlIllII) ? 1 : 0)) {
                        return Utils.llll[0] != 0;
                    }
                    ++llllIlIIIlIlIll;
                    "".length();
                    if (" ".length() >= "  ".length()) {
                        return ((0x58 ^ 0x6D) & ~(0x8F ^ 0xBA)) != 0x0;
                    }
                }
                ++llllIlIIIlIlIlI;
                "".length();
                if (((51 + 11 + 49 + 66 ^ 174 + 35 - 140 + 119) & (0x8E ^ 0x9E ^ (0x47 ^ 0x5A) ^ -" ".length())) != 0x0) {
                    return ((0xDA ^ 0xBA ^ (0x35 ^ 0x72)) & (0xC9 ^ 0xA9 ^ (0x71 ^ 0x36) ^ -" ".length())) != 0x0;
                }
            }
            ++llllIlIIIlIlIIl;
            "".length();
            if (-"   ".length() >= 0) {
                return ((0x1B ^ 0x12) & ~(0xA3 ^ 0xAA)) != 0x0;
            }
        }
        return Utils.llll[1] != 0;
    }
    
    public static boolean checkScreen() {
        if (!llllII((Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer) ? 1 : 0) || !llllII((Wrapper.INSTANCE.mc().currentScreen instanceof GuiChat) ? 1 : 0) || lllIll((Wrapper.INSTANCE.mc().currentScreen instanceof GuiScreen) ? 1 : 0)) {
            return Utils.llll[1] != 0;
        }
        return Utils.llll[0] != 0;
    }
    
    public static float getYaw(final Entity llllIlIlIIllIIl) {
        final double llllIlIlIIllIII = llllIlIlIIllIIl.posX - Wrapper.INSTANCE.player().posX;
        final double llllIlIlIIlIlll = llllIlIlIIllIIl.posY - Wrapper.INSTANCE.player().posY;
        final double llllIlIlIIlIllI = llllIlIlIIllIIl.posZ - Wrapper.INSTANCE.player().posZ;
        double llllIlIlIIlIlIl = Math.atan2(llllIlIlIIllIII, llllIlIlIIlIllI) * 57.29577951308232;
        llllIlIlIIlIlIl = -llllIlIlIIlIlIl;
        return (float)llllIlIlIIlIlIl;
    }
    
    private static boolean lIIIllI(final int llllIIIlllIIllI, final int llllIIIlllIIlIl) {
        return llllIIIlllIIllI == llllIIIlllIIlIl;
    }
    
    public static void mysteryFind(final EntityLivingBase llllIllIIlIIlIl, final int llllIllIIlIlIII) {
        if (llllII(llllIllIIlIlIII)) {
            if (llllII(EnemyManager.murders.isEmpty() ? 1 : 0)) {
                int llllIllIIllIIIl = Utils.llll[1];
                while (lIIIlII(llllIllIIllIIIl, EnemyManager.murders.size())) {
                    final EntityLivingBase llllIllIIllIIlI = getWorldEntityByName(EnemyManager.murders.get(llllIllIIllIIIl));
                    if (lIIIlIl(llllIllIIllIIlI)) {
                        EnemyManager.murders.remove(llllIllIIllIIIl);
                        "".length();
                    }
                    ++llllIllIIllIIIl;
                    "".length();
                    if ("   ".length() <= " ".length()) {
                        return;
                    }
                }
                "".length();
                if (-" ".length() != -" ".length()) {
                    return;
                }
            }
        }
        else if (lIIIllI(llllIllIIlIlIII, Utils.llll[0]) && llllII(EnemyManager.detects.isEmpty() ? 1 : 0)) {
            int llllIllIIlIllll = Utils.llll[1];
            while (lIIIlII(llllIllIIlIllll, EnemyManager.detects.size())) {
                final EntityLivingBase llllIllIIllIIII = getWorldEntityByName(EnemyManager.detects.get(llllIllIIlIllll));
                if (lIIIlIl(llllIllIIllIIII)) {
                    EnemyManager.detects.remove(llllIllIIlIllll);
                    "".length();
                }
                ++llllIllIIlIllll;
                "".length();
                if (-" ".length() >= "   ".length()) {
                    return;
                }
            }
        }
        if (lllIll((llllIllIIlIIlIl instanceof EntityPlayerSP) ? 1 : 0)) {
            return;
        }
        if (llllII((llllIllIIlIIlIl instanceof EntityPlayer) ? 1 : 0)) {
            return;
        }
        final EntityPlayer llllIllIIlIIlll = (EntityPlayer)llllIllIIlIIlIl;
        if (lIIIlIl(llllIllIIlIIlll.getGameProfile())) {
            return;
        }
        final GameProfile llllIllIIlIIllI = llllIllIIlIIlll.getGameProfile();
        if (lIIIlIl(llllIllIIlIIllI.getName())) {
            return;
        }
        if (!llllII(EnemyManager.murders.contains(llllIllIIlIIllI.getName()) ? 1 : 0) || lllIll(EnemyManager.detects.contains(llllIllIIlIIllI.getName()) ? 1 : 0)) {
            return;
        }
        if (lIIIlIl(llllIllIIlIIlll.inventory)) {
            return;
        }
        int llllIllIIlIlIlI = Utils.llll[1];
        while (lIIIlII(llllIllIIlIlIlI, Utils.llll[5])) {
            final ItemStack llllIllIIlIllII = llllIllIIlIIlll.inventory.getStackInSlot(llllIllIIlIlIlI);
            if (lIIIlIl(llllIllIIlIllII)) {
                "".length();
                if (((0x3A ^ 0x6D ^ (0x12 ^ 0x16)) & (53 + 107 - 112 + 150 ^ 73 + 108 - 90 + 58 ^ -" ".length())) != 0x0) {
                    return;
                }
            }
            else {
                final Item llllIllIIlIlIll = llllIllIIlIllII.getItem();
                if (lIIIlIl(llllIllIIlIlIll)) {
                    "".length();
                    if (" ".length() < -" ".length()) {
                        return;
                    }
                }
                else if (llllII(llllIllIIlIlIII)) {
                    if (!lIIIlll(llllIllIIlIlIll, Items.IRON_SWORD) || !lIIIlll(llllIllIIlIlIll, Items.DIAMOND_SWORD) || !lIIIlll(llllIllIIlIlIll, Items.GOLDEN_SWORD) || !lIIIlll(llllIllIIlIlIll, Items.STONE_SWORD) || !lIIIlll(llllIllIIlIlIll, Items.WOODEN_SWORD) || !lIIIlll(llllIllIIlIlIll, Items.IRON_SHOVEL) || !lIIIlll(llllIllIIlIlIll, Items.DIAMOND_SHOVEL) || !lIIIlll(llllIllIIlIlIll, Items.GOLDEN_SHOVEL) || !lIIIlll(llllIllIIlIlIll, Items.STONE_SHOVEL) || !lIIIlll(llllIllIIlIlIll, Items.WOODEN_SHOVEL) || !lIIIlll(llllIllIIlIlIll, Items.IRON_AXE) || !lIIIlll(llllIllIIlIlIll, Items.DIAMOND_AXE) || !lIIIlll(llllIllIIlIlIll, Items.GOLDEN_AXE) || !lIIIlll(llllIllIIlIlIll, Items.STONE_AXE) || !lIIIlll(llllIllIIlIlIll, Items.WOODEN_AXE) || !lIIIlll(llllIllIIlIlIll, Items.IRON_PICKAXE) || !lIIIlll(llllIllIIlIlIll, Items.DIAMOND_PICKAXE) || !lIIIlll(llllIllIIlIlIll, Items.GOLDEN_PICKAXE) || !lIIIlll(llllIllIIlIlIll, Items.STONE_PICKAXE) || !lIIIlll(llllIllIIlIlIll, Items.WOODEN_PICKAXE) || !lIIIlll(llllIllIIlIlIll, Items.IRON_HOE) || !lIIIlll(llllIllIIlIlIll, Items.DIAMOND_HOE) || !lIIIlll(llllIllIIlIlIll, Items.GOLDEN_HOE) || !lIIIlll(llllIllIIlIlIll, Items.STONE_HOE) || !lIIIlll(llllIllIIlIlIll, Items.WOODEN_HOE) || !lIIIlll(llllIllIIlIlIll, Items.STICK) || !lIIIlll(llllIllIIlIlIll, Items.BLAZE_ROD) || !lIIIlll(llllIllIIlIlIll, Items.FISHING_ROD) || !lIIIlll(llllIllIIlIlIll, Items.CARROT) || !lIIIlll(llllIllIIlIlIll, Items.GOLDEN_CARROT) || !lIIIlll(llllIllIIlIlIll, Items.BONE) || !lIIIlll(llllIllIIlIlIll, Items.COOKIE) || !lIIIlll(llllIllIIlIlIll, Items.FEATHER) || !lIIIlll(llllIllIIlIlIll, Items.PUMPKIN_PIE) || !lIIIlll(llllIllIIlIlIll, Items.COOKED_FISH) || !lIIIlll(llllIllIIlIlIll, Items.FISH) || !lIIIlll(llllIllIIlIlIll, Items.SHEARS) || lIIIIlI(llllIllIIlIlIll, Items.CARROT_ON_A_STICK)) {
                        final String llllIllIIlIlllI = llllIllIIlIIlll.getGameProfile().getName();
                        EnemyManager.murders.add(llllIllIIlIlllI);
                        "".length();
                        "".length();
                        if (-"  ".length() >= 0) {
                            return;
                        }
                    }
                }
                else if (lIIIllI(llllIllIIlIlIII, Utils.llll[0]) && lIIIIlI(llllIllIIlIlIll, Items.BOW)) {
                    final String llllIllIIlIllIl = llllIllIIlIIlll.getGameProfile().getName();
                    EnemyManager.detects.add(llllIllIIlIllIl);
                    "".length();
                }
            }
            ++llllIllIIlIlIlI;
            "".length();
            if (-" ".length() >= " ".length()) {
                return;
            }
        }
    }
    
    private static boolean lllIll(final int llllIIIllIIllll) {
        return llllIIIllIIllll != 0;
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + Wrapper.INSTANCE.player().getEyeHeight(), Wrapper.INSTANCE.player().posZ);
    }
    
    private static boolean lIIIlIl(final Object llllIIIllIlIIIl) {
        return llllIIIllIlIIIl == null;
    }
    
    public static boolean checkEnemyColor(final EntityPlayer llllIlIllllIllI) {
        final int llllIlIlllllllI = getPlayerArmorColor(llllIlIllllIllI, llllIlIllllIllI.inventory.armorItemInSlot(Utils.llll[1]));
        final int llllIlIllllllIl = getPlayerArmorColor(llllIlIllllIllI, llllIlIllllIllI.inventory.armorItemInSlot(Utils.llll[0]));
        final int llllIlIllllllII = getPlayerArmorColor(llllIlIllllIllI, llllIlIllllIllI.inventory.armorItemInSlot(Utils.llll[2]));
        final int llllIlIlllllIll = getPlayerArmorColor(llllIlIllllIllI, llllIlIllllIllI.inventory.armorItemInSlot(Utils.llll[4]));
        final int llllIlIlllllIlI = getPlayerArmorColor((EntityPlayer)Wrapper.INSTANCE.player(), Wrapper.INSTANCE.player().inventory.armorItemInSlot(Utils.llll[1]));
        final int llllIlIlllllIIl = getPlayerArmorColor((EntityPlayer)Wrapper.INSTANCE.player(), Wrapper.INSTANCE.player().inventory.armorItemInSlot(Utils.llll[0]));
        final int llllIlIlllllIII = getPlayerArmorColor((EntityPlayer)Wrapper.INSTANCE.player(), Wrapper.INSTANCE.player().inventory.armorItemInSlot(Utils.llll[2]));
        final int llllIlIllllIlll = getPlayerArmorColor((EntityPlayer)Wrapper.INSTANCE.player(), Wrapper.INSTANCE.player().inventory.armorItemInSlot(Utils.llll[4]));
        if ((lIIIllI(llllIlIlllllllI, llllIlIlllllIlI) && lIIlIIl(llllIlIlllllIlI, Utils.llll[36]) && !lIIIllI(llllIlIlllllllI, Utils.llll[0])) || (lIIIllI(llllIlIllllllIl, llllIlIlllllIIl) && lIIlIIl(llllIlIlllllIIl, Utils.llll[36]) && !lIIIllI(llllIlIllllllIl, Utils.llll[0])) || (lIIIllI(llllIlIllllllII, llllIlIlllllIII) && lIIlIIl(llllIlIlllllIII, Utils.llll[36]) && !lIIIllI(llllIlIllllllII, Utils.llll[0])) || (lIIIllI(llllIlIlllllIll, llllIlIllllIlll) && lIIlIIl(llllIlIllllIlll, Utils.llll[36]) && lIIlIIl(llllIlIlllllIll, Utils.llll[0]))) {
            return Utils.llll[1] != 0;
        }
        return Utils.llll[0] != 0;
    }
    
    public static float[] getRotationsNeeded(final Entity llllIIlIlIllllI) {
        if (lIIIlIl(llllIIlIlIllllI)) {
            return null;
        }
        final double llllIIlIlIlllIl = llllIIlIlIllllI.posX - Wrapper.INSTANCE.mc().player.posX;
        final double llllIIlIlIlllII = llllIIlIlIllllI.posZ - Wrapper.INSTANCE.mc().player.posZ;
        double llllIIlIlIllIll;
        if (lllIll((llllIIlIlIllllI instanceof EntityLivingBase) ? 1 : 0)) {
            final EntityLivingBase llllIIlIllIIIII = (EntityLivingBase)llllIIlIlIllllI;
            final double llllIIlIlIlllll = llllIIlIllIIIII.posY + llllIIlIllIIIII.getEyeHeight() - (Wrapper.INSTANCE.mc().player.posY + Wrapper.INSTANCE.mc().player.getEyeHeight());
            "".length();
            if (((0xD6 ^ 0xB9 ^ (0x74 ^ 0x3D)) & (52 + 104 - 142 + 118 ^ 65 + 76 + 16 + 5 ^ -" ".length())) != 0x0) {
                return null;
            }
        }
        else {
            llllIIlIlIllIll = (llllIIlIlIllllI.getEntityBoundingBox().minY + llllIIlIlIllllI.getEntityBoundingBox().maxY) / 2.0 - (Wrapper.INSTANCE.mc().player.posY + Wrapper.INSTANCE.mc().player.getEyeHeight());
        }
        final double llllIIlIlIllIlI = MathHelper.sqrt(llllIIlIlIlllIl * llllIIlIlIlllIl + llllIIlIlIlllII * llllIIlIlIlllII);
        final float llllIIlIlIllIIl = (float)(Math.atan2(llllIIlIlIlllII, llllIIlIlIlllIl) * 180.0 / 3.141592653589793) - 90.0f;
        final float llllIIlIlIllIII = (float)(-(Math.atan2(llllIIlIlIllIll, llllIIlIlIllIlI) * 180.0 / 3.141592653589793));
        final float[] array = new float[Utils.llll[2]];
        array[Utils.llll[1]] = Wrapper.INSTANCE.mc().player.rotationYaw + MathHelper.wrapDegrees(llllIIlIlIllIIl - Wrapper.INSTANCE.mc().player.rotationYaw);
        array[Utils.llll[0]] = Wrapper.INSTANCE.mc().player.rotationPitch + MathHelper.wrapDegrees(llllIIlIlIllIII - Wrapper.INSTANCE.mc().player.rotationPitch);
        return array;
    }
    
    public static boolean placeBlockScaffold(final BlockPos llllIlIIlIIIIII) {
        final Vec3d llllIlIIIllllll = new Vec3d(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + Wrapper.INSTANCE.player().getEyeHeight(), Wrapper.INSTANCE.player().posZ);
        final EnumFacing[] llllIlIIIlllllI;
        final int llllIlIIlIIIIlI = (llllIlIIIlllllI = EnumFacing.values()).length;
        int llllIlIIlIIIIIl = Utils.llll[1];
        while (lIIIlII(llllIlIIlIIIIIl, llllIlIIlIIIIlI)) {
            final EnumFacing llllIlIIlIIIlIl = llllIlIIIlllllI[llllIlIIlIIIIIl];
            final BlockPos llllIlIIlIIIlII = llllIlIIlIIIIII.offset(llllIlIIlIIIlIl);
            final EnumFacing llllIlIIlIIIIll = llllIlIIlIIIlIl.getOpposite();
            if (lIIIIIl(lIIlllI(llllIlIIIllllll.squareDistanceTo(new Vec3d((Vec3i)llllIlIIlIIIIII).add(0.5, 0.5, 0.5)), llllIlIIIllllll.squareDistanceTo(new Vec3d((Vec3i)llllIlIIlIIIlII).add(0.5, 0.5, 0.5)))) && lllIll(canBeClicked(llllIlIIlIIIlII) ? 1 : 0)) {
                final Vec3d llllIlIIlIIIllI = new Vec3d((Vec3i)llllIlIIlIIIlII).add(0.5, 0.5, 0.5).add(new Vec3d(llllIlIIlIIIIll.getDirectionVec()).scale(0.5));
                if (lIlIIII(lIIlllI(llllIlIIIllllll.squareDistanceTo(llllIlIIlIIIllI), 18.0625))) {
                    faceVectorPacketInstant(llllIlIIlIIIllI);
                    Wrapper.INSTANCE.player().swingArm(EnumHand.MAIN_HAND);
                    Wrapper.INSTANCE.mc().playerController.processRightClickBlock(Wrapper.INSTANCE.player(), Wrapper.INSTANCE.world(), llllIlIIlIIIlII, llllIlIIlIIIIll, llllIlIIlIIIllI, EnumHand.MAIN_HAND);
                    "".length();
                    try {
                        final Field llllIlIIlIIlIII = Minecraft.class.getDeclaredField(Utils.I[Utils.llll[37]]);
                        llllIlIIlIIlIII.setAccessible((boolean)(Utils.llll[0] != 0));
                        llllIlIIlIIlIII.set(Wrapper.INSTANCE.mc(), Utils.llll[6]);
                        "".length();
                        if ("  ".length() == (64 + 137 - 96 + 83 ^ 62 + 42 + 34 + 46)) {
                            return ((13 + 69 + 61 + 30 ^ 66 + 57 - 69 + 84) & (38 + 180 - 185 + 157 ^ 121 + 70 - 76 + 38 ^ -" ".length())) != 0x0;
                        }
                    }
                    catch (Exception llllIlIIlIIIlll) {
                        llllIlIIlIIIlll.printStackTrace();
                    }
                    return Utils.llll[0] != 0;
                }
            }
            ++llllIlIIlIIIIIl;
            "".length();
            if (" ".length() <= 0) {
                return ((0x12 ^ 0x74 ^ (0x5E ^ 0x67)) & (0x1B ^ 0x37 ^ (0x53 ^ 0x20) ^ -" ".length())) != 0x0;
            }
        }
        return Utils.llll[1] != 0;
    }
    
    private static boolean lIIIIlI(final Object llllIIIllIlIllI, final Object llllIIIllIlIlIl) {
        return llllIIIllIlIllI == llllIIIllIlIlIl;
    }
    
    public static void faceEntity(final EntityLivingBase llllIlIIIIlIIIl) {
        if (lIIIlIl(llllIlIIIIlIIIl)) {
            return;
        }
        final double llllIlIIIIllIII = llllIlIIIIlIIIl.posX - Wrapper.INSTANCE.player().posX;
        final double llllIlIIIIlIlll = llllIlIIIIlIIIl.posY - Wrapper.INSTANCE.player().posY;
        final double llllIlIIIIlIllI = llllIlIIIIlIIIl.posZ - Wrapper.INSTANCE.player().posZ;
        final double llllIlIIIIlIlIl = Wrapper.INSTANCE.player().posY + Wrapper.INSTANCE.player().getEyeHeight() - (llllIlIIIIlIIIl.posY + llllIlIIIIlIIIl.getEyeHeight());
        final double llllIlIIIIlIlII = MathHelper.sqrt(llllIlIIIIllIII * llllIlIIIIllIII + llllIlIIIIlIllI * llllIlIIIIlIllI);
        final float llllIlIIIIlIIll = (float)(Math.atan2(llllIlIIIIlIllI, llllIlIIIIllIII) * 180.0 / 3.141592653589793) - 90.0f;
        final float llllIlIIIIlIIlI = (float)(-(Math.atan2(llllIlIIIIlIlIl, llllIlIIIIlIlII) * 180.0 / 3.141592653589793));
        Wrapper.INSTANCE.player().rotationYaw = llllIlIIIIlIIll;
        Wrapper.INSTANCE.player().rotationPitch = llllIlIIIIlIIlI;
    }
    
    private static boolean lIIIlII(final int llllIIIlllIIIlI, final int llllIIIlllIIIIl) {
        return llllIIIlllIIIlI < llllIIIlllIIIIl;
    }
    
    public static boolean isBlockMaterial(final BlockPos llllIllIllIIIll, final Material llllIllIllIIIII) {
        int n;
        if (lIIIIlI(Wrapper.INSTANCE.world().getBlockState(llllIllIllIIIll).getMaterial(), llllIllIllIIIII)) {
            n = Utils.llll[0];
            "".length();
            if ("  ".length() != "  ".length()) {
                return ((0x23 ^ 0x3E ^ (0xDF ^ 0xC7)) & (80 + 40 - 56 + 102 ^ 47 + 156 - 87 + 47 ^ -" ".length())) != 0x0;
            }
        }
        else {
            n = Utils.llll[1];
        }
        return n != 0;
    }
    
    private static boolean llllII(final int llllIIIllIIllIl) {
        return llllIIIllIIllIl == 0;
    }
    
    private static void lllIII() {
        (I = new String[Utils.llll[38]])[Utils.llll[1]] = l("HBW9mzgJAOg=", "XbUxY");
        Utils.I[Utils.llll[0]] = lIlI("DbywsH0h1Dc=", "hWxgd");
        Utils.I[Utils.llll[2]] = lIllll("w7VV", "RdCKX");
        Utils.I[Utils.llll[4]] = lIllll("w4xg", "kRytT");
        Utils.I[Utils.llll[6]] = lIlI("zXBF4SvBceI=", "yZxuD");
        Utils.I[Utils.llll[7]] = l("CRIILqGSyrk=", "hoKXC");
        Utils.I[Utils.llll[8]] = l("Uq2iuX0QW7U=", "vOTws");
        Utils.I[Utils.llll[9]] = lIlI("9vZK+HX79Vc=", "RYpww");
        Utils.I[Utils.llll[10]] = lIllll("w69i", "HVDox");
        Utils.I[Utils.llll[11]] = l("I7DxduBGPbA=", "nkict");
        Utils.I[Utils.llll[12]] = lIlI("T63YOutGAac=", "QXyNK");
        Utils.I[Utils.llll[13]] = l("rZVaG45kyEk=", "VBuNr");
        Utils.I[Utils.llll[14]] = l("+MpgxDVDXaM=", "IWZZu");
        Utils.I[Utils.llll[15]] = lIllll("w5d+", "pIrNs");
        Utils.I[Utils.llll[16]] = l("G+h++HZhcOg=", "pQatA");
        Utils.I[Utils.llll[17]] = l("soiDykEnMvY=", "oYfie");
        Utils.I[Utils.llll[18]] = lIlI("DGcgSC2L2do=", "bSntQ");
        Utils.I[Utils.llll[19]] = lIllll("w4hB", "oxmHH");
        Utils.I[Utils.llll[20]] = l("Dx7kFd2jf6w=", "jaUqE");
        Utils.I[Utils.llll[21]] = l("lCua3XWNKR8=", "jiciB");
        Utils.I[Utils.llll[22]] = lIllll("w5JX", "ugcHg");
        Utils.I[Utils.llll[23]] = lIlI("wtS1YSkAznQ=", "DAMBw");
        Utils.I[Utils.llll[24]] = l("GM48baADwQQ=", "ZcaWF");
        Utils.I[Utils.llll[25]] = lIllll("w6IL", "EoRLl");
        Utils.I[Utils.llll[26]] = lIlI("j2WAHtVEFw8=", "Zjxpz");
        Utils.I[Utils.llll[27]] = lIllll("w6AJ", "GhZMG");
        Utils.I[Utils.llll[28]] = l("OjgVi85SVas=", "SCKBm");
        Utils.I[Utils.llll[29]] = l("MGbUXG6SxFQ=", "PVDGB");
        Utils.I[Utils.llll[30]] = lIlI("zCJWejvxg2g=", "TASiU");
        Utils.I[Utils.llll[31]] = lIlI("/ebv83n/yd4=", "VoZjq");
        Utils.I[Utils.llll[32]] = l("uyK2mnL2DwI=", "budjN");
        Utils.I[Utils.llll[33]] = lIllll("w4I0", "eROap");
        Utils.I[Utils.llll[34]] = lIlI("V7iz72LE80A=", "kNsgX");
        Utils.I[Utils.llll[35]] = lIllll("PR0kHw==", "ShHss");
        Utils.I[Utils.llll[37]] = lIllll("OyEKCS0KJAQCMg0tAQAgHSEABCs=", "IHmaY");
    }
    
    private static boolean lIIIlll(final Object llllIIIllIllIlI, final Object llllIIIllIllIIl) {
        return llllIIIllIllIlI != llllIIIllIllIIl;
    }
    
    private static boolean lIIIIII(final int llllIIIllIllllI, final int llllIIIllIlllIl) {
        return llllIIIllIllllI > llllIIIllIlllIl;
    }
    
    public static float updateRotation(final float llllIIllllIIlIl, final float llllIIllllIIIII, final float llllIIlllIlllll) {
        float llllIIllllIIIlI = MathHelper.wrapDegrees(llllIIllllIIIII - llllIIllllIIlIl);
        if (llllIl(lIlIlII(llllIIllllIIIlI, llllIIlllIlllll))) {
            llllIIllllIIIlI = llllIIlllIlllll;
        }
        if (lIIIIIl(lIlIlIl(llllIIllllIIIlI, -llllIIlllIlllll))) {
            llllIIllllIIIlI = -llllIIlllIlllll;
        }
        return llllIIllllIIlIl + llllIIllllIIIlI;
    }
    
    public static boolean canBeClicked(final BlockPos llllIlllIlIIlll) {
        return Wrapper.INSTANCE.world().getBlockState(llllIlllIlIIlll).getBlock().canCollideCheck(Wrapper.INSTANCE.world().getBlockState(llllIlllIlIIlll), (boolean)(Utils.llll[1] != 0));
    }
    
    public static void faceVectorPacket(final Vec3d llllIlIIlIllIII) {
        final float[] llllIlIIlIlllII = getNeededRotations(llllIlIIlIllIII);
        final EntityPlayerSP llllIlIIlIllIll = Minecraft.getMinecraft().player;
        final float llllIlIIlIllIlI = llllIlIIlIllIll.rotationYaw;
        final float llllIlIIlIllIIl = llllIlIIlIllIll.rotationPitch;
        llllIlIIlIllIll.rotationYaw = llllIlIIlIlllII[Utils.llll[1]];
        llllIlIIlIllIll.rotationPitch = llllIlIIlIlllII[Utils.llll[0]];
        try {
            final Method llllIlIIlIllllI = llllIlIIlIllIll.getClass().getDeclaredMethod(Mapping.onUpdateWalkingPlayer, (Class<?>[])new Class[Utils.llll[1]]);
            llllIlIIlIllllI.setAccessible((boolean)(Utils.llll[0] != 0));
            llllIlIIlIllllI.invoke(llllIlIIlIllIll, new Object[Utils.llll[1]]);
            "".length();
            "".length();
            if (((0x88 ^ 0x85) & ~(0xAB ^ 0xA6)) < 0) {
                return;
            }
        }
        catch (Exception ex) {}
        llllIlIIlIllIll.rotationYaw = llllIlIIlIllIlI;
        llllIlIIlIllIll.rotationPitch = llllIlIIlIllIIl;
    }
    
    private static String l(final String llllIIlIIIllIIl, final String llllIIlIIIlIllI) {
        try {
            final SecretKeySpec llllIIlIIIlllII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIIlIIIlIllI.getBytes(StandardCharsets.UTF_8)), Utils.llll[10]), "DES");
            final Cipher llllIIlIIIllIll = Cipher.getInstance("DES");
            llllIIlIIIllIll.init(Utils.llll[2], llllIIlIIIlllII);
            return new String(llllIIlIIIllIll.doFinal(Base64.getDecoder().decode(llllIIlIIIllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIIlIIIllIlI) {
            llllIIlIIIllIlI.printStackTrace();
            return null;
        }
    }
    
    private static int lIIlllI(final double n, final double n2) {
        return dcmpg(n, n2);
    }
    
    public static float[] getSmoothNeededRotations(final Vec3d llllIIllIIlllII, final float llllIIllIIlIIIl, final float llllIIllIIlIIII) {
        final Vec3d llllIIllIIllIIl = getEyesPos();
        final double llllIIllIIllIII = llllIIllIIlllII.x - llllIIllIIllIIl.x;
        final double llllIIllIIlIlll = llllIIllIIlllII.y - llllIIllIIllIIl.y;
        final double llllIIllIIlIllI = llllIIllIIlllII.z - llllIIllIIllIIl.z;
        final double llllIIllIIlIlIl = Math.sqrt(llllIIllIIllIII * llllIIllIIllIII + llllIIllIIlIllI * llllIIllIIlIllI);
        final float llllIIllIIlIlII = (float)Math.toDegrees(Math.atan2(llllIIllIIlIllI, llllIIllIIllIII)) - 90.0f;
        final float llllIIllIIlIIll = (float)(-Math.toDegrees(Math.atan2(llllIIllIIlIlll, llllIIllIIlIlIl)));
        final float[] array = new float[Utils.llll[2]];
        array[Utils.llll[1]] = updateRotation(Wrapper.INSTANCE.player().rotationYaw, llllIIllIIlIlII, llllIIllIIlIIIl / 4.0f);
        array[Utils.llll[0]] = updateRotation(Wrapper.INSTANCE.player().rotationPitch, llllIIllIIlIIll, llllIIllIIlIIII / 4.0f);
        return array;
    }
    
    public static int random(final int llllIlllIllIIII, final int llllIlllIllIIIl) {
        return Utils.RANDOM.nextInt(llllIlllIllIIIl - llllIlllIllIIII) + llllIlllIllIIII;
    }
    
    private static float[] getDirectionToEntity(final Entity llllIlIllIIIlIl) {
        final float[] array = new float[Utils.llll[2]];
        array[Utils.llll[1]] = getYaw(llllIlIllIIIlIl) + Wrapper.INSTANCE.player().rotationYaw;
        array[Utils.llll[0]] = getPitch(llllIlIllIIIlIl) + Wrapper.INSTANCE.player().rotationPitch;
        return array;
    }
    
    private static int lIIlIll(final float n, final float n2) {
        return fcmpg(n, n2);
    }
    
    public static boolean isMurder(final EntityLivingBase llllIllIlIIlIlI) {
        mysteryFind(llllIllIlIIlIlI, Utils.llll[1]);
        if (llllII(EnemyManager.murders.isEmpty() ? 1 : 0) && lllIll((llllIllIlIIlIlI instanceof EntityPlayer) ? 1 : 0)) {
            final EntityPlayer llllIllIlIIlIll = (EntityPlayer)llllIllIlIIlIlI;
            final short llllIllIlIIIlll = (short)EnemyManager.murders.iterator();
            while (lllIll(((Iterator)llllIllIlIIIlll).hasNext() ? 1 : 0)) {
                final String llllIllIlIIllII = ((Iterator<String>)llllIllIlIIIlll).next();
                if (lllIll(llllIllIlIIlIll.getGameProfile().getName().equals(llllIllIlIIllII) ? 1 : 0)) {
                    return Utils.llll[0] != 0;
                }
                "".length();
                if (((45 + 106 - 16 + 36 ^ 47 + 44 - 41 + 139) & (0xD0 ^ 0xA7 ^ (0x1D ^ 0x7C) ^ -" ".length())) > "  ".length()) {
                    return (" ".length() & (" ".length() ^ -" ".length())) != 0x0;
                }
            }
        }
        return Utils.llll[1] != 0;
    }
    
    public static void assistFaceEntity(final Entity llllIIllllllllI, final float llllIIlllllllIl, final float llllIIlllllllII) {
        if (lIIIlIl(llllIIllllllllI)) {
            return;
        }
        final double llllIIllllllIll = llllIIllllllllI.posX - Wrapper.INSTANCE.player().posX;
        final double llllIIllllllIlI = llllIIllllllllI.posZ - Wrapper.INSTANCE.player().posZ;
        double llllIIllllllIIl;
        if (lllIll((llllIIllllllllI instanceof EntityLivingBase) ? 1 : 0)) {
            final EntityLivingBase llllIlIIIIIIIII = (EntityLivingBase)llllIIllllllllI;
            final double llllIIlllllllll = llllIlIIIIIIIII.posY + llllIlIIIIIIIII.getEyeHeight() - (Wrapper.INSTANCE.player().posY + Wrapper.INSTANCE.player().getEyeHeight());
            "".length();
            if ("  ".length() >= "   ".length()) {
                return;
            }
        }
        else {
            llllIIllllllIIl = (llllIIllllllllI.getEntityBoundingBox().minY + llllIIllllllllI.getEntityBoundingBox().maxY) / 2.0 - (Wrapper.INSTANCE.player().posY + Wrapper.INSTANCE.player().getEyeHeight());
        }
        final double llllIIlllllIlll = MathHelper.sqrt(llllIIllllllIll * llllIIllllllIll + llllIIllllllIlI * llllIIllllllIlI);
        final float llllIIlllllIllI = (float)(Math.atan2(llllIIllllllIlI, llllIIllllllIll) * 180.0 / 3.141592653589793) - 90.0f;
        final float llllIIlllllIlIl = (float)(-(Math.atan2(llllIIllllllIIl, llllIIlllllIlll) * 180.0 / 3.141592653589793));
        if (llllIl(lIlIIll(llllIIlllllllIl, 0.0f))) {
            Wrapper.INSTANCE.player().rotationYaw = updateRotation(Wrapper.INSTANCE.player().rotationYaw, llllIIlllllIllI, llllIIlllllllIl / 4.0f);
        }
        if (llllIl(lIlIIll(llllIIlllllllII, 0.0f))) {
            Wrapper.INSTANCE.player().rotationPitch = updateRotation(Wrapper.INSTANCE.player().rotationPitch, llllIIlllllIlIl, llllIIlllllllII / 4.0f);
        }
    }
    
    private static void lllIIl() {
        (llll = new int[39])[0] = " ".length();
        Utils.llll[1] = ((0xC7 ^ 0xA4 ^ (0xD ^ 0x60)) & (0xED ^ 0xC6 ^ (0x6 ^ 0x23) ^ -" ".length()));
        Utils.llll[2] = "  ".length();
        Utils.llll[3] = (0x74 ^ 0xC);
        Utils.llll[4] = "   ".length();
        Utils.llll[5] = (0x58 ^ 0x7C);
        Utils.llll[6] = (0x81 ^ 0x85);
        Utils.llll[7] = (0x59 ^ 0xF ^ (0x47 ^ 0x14));
        Utils.llll[8] = (12 + 89 + 39 + 26 ^ 22 + 98 - 4 + 44);
        Utils.llll[9] = (0x42 ^ 0x5 ^ (0x70 ^ 0x30));
        Utils.llll[10] = (115 + 66 - 72 + 21 ^ 95 + 52 - 101 + 92);
        Utils.llll[11] = (19 + 63 - 21 + 90 ^ 157 + 89 - 168 + 80);
        Utils.llll[12] = (0xB4 ^ 0x8C ^ (0x19 ^ 0x2B));
        Utils.llll[13] = (0x1B ^ 0x10);
        Utils.llll[14] = (96 + 1 - 39 + 88 ^ 123 + 3 - 37 + 69);
        Utils.llll[15] = (44 + 23 + 94 + 28 ^ 148 + 48 - 108 + 88);
        Utils.llll[16] = (0xB ^ 0x5);
        Utils.llll[17] = (0x0 ^ 0xF);
        Utils.llll[18] = (0x65 ^ 0x75);
        Utils.llll[19] = (0x11 ^ 0x76 ^ (0x70 ^ 0x6));
        Utils.llll[20] = (0x48 ^ 0x5A);
        Utils.llll[21] = (0x67 ^ 0x74);
        Utils.llll[22] = (0x4 ^ 0x4C ^ (0xC6 ^ 0x9A));
        Utils.llll[23] = (0x47 ^ 0x52);
        Utils.llll[24] = (0x6A ^ 0x7C);
        Utils.llll[25] = (0x18 ^ 0xF);
        Utils.llll[26] = (0x33 ^ 0x2B);
        Utils.llll[27] = (61 + 142 - 47 + 14 ^ 163 + 81 - 230 + 165);
        Utils.llll[28] = (0x96 ^ 0x8C);
        Utils.llll[29] = (0xB4 ^ 0xBA ^ (0x0 ^ 0x15));
        Utils.llll[30] = (0x61 ^ 0x7D);
        Utils.llll[31] = (0x40 ^ 0x52 ^ (0x9C ^ 0x93));
        Utils.llll[32] = (0x84 ^ 0x9A);
        Utils.llll[33] = (0x4B ^ 0x54);
        Utils.llll[34] = (0xE ^ 0x2E);
        Utils.llll[35] = (0xBD ^ 0x9C);
        Utils.llll[36] = -" ".length();
        Utils.llll[37] = (0x98 ^ 0xA7 ^ (0xB2 ^ 0xAF));
        Utils.llll[38] = (0xB ^ 0x28);
    }
    
    public static boolean isDetect(final EntityLivingBase llllIllIIlllllI) {
        mysteryFind(llllIllIIlllllI, Utils.llll[0]);
        if (llllII(EnemyManager.detects.isEmpty() ? 1 : 0) && lllIll((llllIllIIlllllI instanceof EntityPlayer) ? 1 : 0)) {
            final EntityPlayer llllIllIlIIIIII = (EntityPlayer)llllIllIIlllllI;
            final boolean llllIllIIllllII = (boolean)EnemyManager.detects.iterator();
            while (lllIll(((Iterator)llllIllIIllllII).hasNext() ? 1 : 0)) {
                final String llllIllIlIIIIIl = ((Iterator<String>)llllIllIIllllII).next();
                if (lllIll(llllIllIlIIIIII.getGameProfile().getName().equals(llllIllIlIIIIIl) ? 1 : 0)) {
                    return Utils.llll[0] != 0;
                }
                "".length();
                if (" ".length() < 0) {
                    return ((0xEC ^ 0xC5) & ~(0x5B ^ 0x72)) != 0x0;
                }
            }
        }
        return Utils.llll[1] != 0;
    }
    
    private static String lIllll(String llllIIIllllIlII, final String llllIIIlllllIII) {
        llllIIIllllIlII = new String(Base64.getDecoder().decode(llllIIIllllIlII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llllIIIllllIlll = new StringBuilder();
        final char[] llllIIIllllIllI = llllIIIlllllIII.toCharArray();
        int llllIIIllllIlIl = Utils.llll[1];
        final long llllIIIlllIlllI = (Object)llllIIIllllIlII.toCharArray();
        final byte llllIIIlllIllIl = (byte)llllIIIlllIlllI.length;
        char llllIIIlllIllII = (char)Utils.llll[1];
        while (lIIIlII(llllIIIlllIllII, llllIIIlllIllIl)) {
            final char llllIIIlllllIll = llllIIIlllIlllI[llllIIIlllIllII];
            llllIIIllllIlll.append((char)(llllIIIlllllIll ^ llllIIIllllIllI[llllIIIllllIlIl % llllIIIllllIllI.length]));
            "".length();
            ++llllIIIllllIlIl;
            ++llllIIIlllIllII;
            "".length();
            if (null != null) {
                return null;
            }
        }
        return String.valueOf(llllIIIllllIlll);
    }
    
    private static int lllllI(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    private static boolean lIIlIIl(final int llllIIIllIIIlII, final int llllIIIllIIIIll) {
        return llllIIIllIIIlII != llllIIIllIIIIll;
    }
    
    public static boolean isPlayer(final Entity llllIllIlIlIlII) {
        if (lllIll((llllIllIlIlIlII instanceof EntityPlayer) ? 1 : 0)) {
            final EntityPlayer llllIllIlIllIII = (EntityPlayer)llllIllIlIlIlII;
            final String llllIllIlIlIlll = getPlayerName(llllIllIlIllIII);
            final String llllIllIlIlIllI = getPlayerName((EntityPlayer)Wrapper.INSTANCE.player());
            if (lllIll(llllIllIlIlIlll.equals(llllIllIlIlIllI) ? 1 : 0)) {
                return Utils.llll[0] != 0;
            }
        }
        return Utils.llll[1] != 0;
    }
    
    private static boolean llllIl(final int llllIIIllIIIlll) {
        return llllIIIllIIIlll > 0;
    }
    
    public static boolean checkEnemyNameColor(final EntityLivingBase llllIllIIIllIIl) {
        final String llllIllIIIllIlI = llllIllIIIllIIl.getDisplayName().getFormattedText();
        if (lllIll(getEntityNameColor((EntityLivingBase)Wrapper.INSTANCE.player()).equals(getEntityNameColor(llllIllIIIllIIl)) ? 1 : 0)) {
            return Utils.llll[1] != 0;
        }
        return Utils.llll[0] != 0;
    }
    
    public static float getDirection() {
        float llllIlIIllIlIII = Wrapper.INSTANCE.player().rotationYaw;
        if (lIIIIIl(lIIlIll(Wrapper.INSTANCE.player().moveForward, 0.0f))) {
            llllIlIIllIlIII += 180.0f;
        }
        float llllIlIIllIIlll = 1.0f;
        if (lIIIIIl(lIIlIll(Wrapper.INSTANCE.player().moveForward, 0.0f))) {
            llllIlIIllIIlll = -0.5f;
            "".length();
            if ((0x90 ^ 0x94) < (0x5 ^ 0x1)) {
                return 0.0f;
            }
        }
        else if (llllIl(lIIllII(Wrapper.INSTANCE.player().moveForward, 0.0f))) {
            llllIlIIllIIlll = 0.5f;
        }
        if (llllIl(lIIllII(Wrapper.INSTANCE.player().moveStrafing, 0.0f))) {
            llllIlIIllIlIII -= 90.0f * llllIlIIllIIlll;
        }
        if (lIIIIIl(lIIlIll(Wrapper.INSTANCE.player().moveStrafing, 0.0f))) {
            llllIlIIllIlIII += 90.0f * llllIlIIllIIlll;
        }
        llllIlIIllIlIII *= 0.017453292f;
        return llllIlIIllIlIII;
    }
    
    private static int lIlIlII(final float n, final float n2) {
        return fcmpl(n, n2);
    }
    
    static {
        lllIIl();
        lllIII();
        Utils.rotationsToBlock = null;
        RANDOM = new Random();
    }
    
    private static boolean lIIIIIl(final int llllIIIllIIlIll) {
        return llllIIIllIIlIll < 0;
    }
    
    public static int getPlayerArmorColor(final EntityPlayer llllIllIIIIlIll, final ItemStack llllIllIIIIlIlI) {
        if (!lIIIIll(llllIllIIIIlIll) || !lIIIIll(llllIllIIIIlIlI) || !lIIIIll(llllIllIIIIlIlI.getItem()) || llllII((llllIllIIIIlIlI.getItem() instanceof ItemArmor) ? 1 : 0)) {
            return Utils.llll[36];
        }
        final ItemArmor llllIllIIIIllII = (ItemArmor)llllIllIIIIlIlI.getItem();
        if (!lIIIIll(llllIllIIIIllII) || lIIIlll(llllIllIIIIllII.getArmorMaterial(), ItemArmor.ArmorMaterial.LEATHER)) {
            return Utils.llll[36];
        }
        return llllIllIIIIllII.getColor(llllIllIIIIlIlI);
    }
    
    private static boolean lIIIIll(final Object llllIIIllIlIIll) {
        return llllIIIllIlIIll != null;
    }
    
    private static int llllll(final double n, final double n2) {
        return dcmpg(n, n2);
    }
    
    public static String getEntityNameColor(final EntityLivingBase llllIllIIIlIIll) {
        final String llllIllIIIlIlII = llllIllIIIlIIll.getDisplayName().getFormattedText();
        if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[1]]) ? 1 : 0)) {
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[0]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[2]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[4]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[6]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[7]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[8]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[9]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[10]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[11]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[12]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[13]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[14]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[15]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[16]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[17]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[18]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[19]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[20]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[21]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[22]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[23]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[24]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[25]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[26]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[27]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[28]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[29]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[30]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[31]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[32]];
            }
            if (lllIll(llllIllIIIlIlII.contains(Utils.I[Utils.llll[33]]) ? 1 : 0)) {
                return Utils.I[Utils.llll[34]];
            }
        }
        return Utils.I[Utils.llll[35]];
    }
    
    private static int lIlIlIl(final float n, final float n2) {
        return fcmpg(n, n2);
    }
    
    public static boolean isBlockMaterial(final BlockPos llllIllIllIIllI, final Block llllIllIllIIlll) {
        int n;
        if (lIIIIlI(Wrapper.INSTANCE.world().getBlockState(llllIllIllIIllI).getBlock(), Blocks.AIR)) {
            n = Utils.llll[0];
            "".length();
            if (" ".length() <= -" ".length()) {
                return ((0x93 ^ 0x8A ^ (0xCA ^ 0x90)) & (0x45 ^ 0x34 ^ (0x95 ^ 0xA7) ^ -" ".length())) != 0x0;
            }
        }
        else {
            n = Utils.llll[1];
        }
        return n != 0;
    }
    
    public static int getDistanceFromMouse(final EntityLivingBase llllIIlllIlIlII) {
        final float[] llllIIlllIlIIll = getRotationsNeeded((Entity)llllIIlllIlIlII);
        if (lIIIIll(llllIIlllIlIIll)) {
            final float llllIIlllIllIII = Wrapper.INSTANCE.player().rotationYaw - llllIIlllIlIIll[Utils.llll[1]];
            final float llllIIlllIlIllI = Wrapper.INSTANCE.player().rotationPitch - llllIIlllIlIIll[Utils.llll[0]];
            final float llllIIlllIlIlIl = MathHelper.sqrt(llllIIlllIllIII * llllIIlllIllIII + llllIIlllIlIllI * llllIIlllIlIllI * 2.0f);
            return (int)llllIIlllIlIlIl;
        }
        return Utils.llll[36];
    }
    
    private static int lllIlI(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    public static EntityLivingBase getWorldEntityByName(final String llllIlIllIlllIl) {
        EntityLivingBase llllIlIllIlllII = null;
        final int llllIlIllIllIIl = (int)Wrapper.INSTANCE.world().loadedEntityList.iterator();
        while (lllIll(((Iterator)llllIlIllIllIIl).hasNext() ? 1 : 0)) {
            final Object llllIlIllIllllI = ((Iterator<Object>)llllIlIllIllIIl).next();
            if (lllIll((llllIlIllIllllI instanceof EntityLivingBase) ? 1 : 0)) {
                final EntityLivingBase llllIlIllIlllll = (EntityLivingBase)llllIlIllIllllI;
                if (lllIll(llllIlIllIlllll.getName().contains(llllIlIllIlllIl) ? 1 : 0)) {
                    llllIlIllIlllII = llllIlIllIlllll;
                }
            }
            "".length();
            if (" ".length() < 0) {
                return null;
            }
        }
        return llllIlIllIlllII;
    }
    
    public static double[] teleportToPosition(final double[] llllIlllIIIlIll, final double[] llllIllIllllIll, final double llllIllIllllIlI, final double llllIllIllllIIl, final boolean llllIllIllllIII, final boolean llllIlllIIIIllI) {
        boolean llllIlllIIIIlIl = Utils.llll[1] != 0;
        if (lllIll(Wrapper.INSTANCE.player().isSneaking() ? 1 : 0)) {
            llllIlllIIIIlIl = (Utils.llll[0] != 0);
        }
        double llllIlllIIIIlII = llllIlllIIIlIll[Utils.llll[1]];
        double llllIlllIIIIIll = llllIlllIIIlIll[Utils.llll[0]];
        double llllIlllIIIIIlI = llllIlllIIIlIll[Utils.llll[2]];
        final double llllIlllIIIIIIl = llllIllIllllIll[Utils.llll[1]];
        final double llllIlllIIIIIII = llllIllIllllIll[Utils.llll[0]];
        final double llllIllIlllllll = llllIllIllllIll[Utils.llll[2]];
        double llllIllIllllllI = Math.abs(llllIlllIIIIlII - llllIlllIIIIIll) + Math.abs(llllIlllIIIIIll - llllIlllIIIIIII) + Math.abs(llllIlllIIIIIlI - llllIllIlllllll);
        int llllIllIlllllIl = Utils.llll[1];
        while (llllIl(lllllI(llllIllIllllllI, llllIllIllllIIl))) {
            llllIllIllllllI = Math.abs(llllIlllIIIIlII - llllIlllIIIIIIl) + Math.abs(llllIlllIIIIIll - llllIlllIIIIIII) + Math.abs(llllIlllIIIIIlI - llllIllIlllllll);
            if (lIIIIII(llllIllIlllllIl, Utils.llll[3])) {
                "".length();
                if ("  ".length() >= (0x37 ^ 0x26 ^ (0x67 ^ 0x72))) {
                    return null;
                }
                break;
            }
            else {
                double n;
                if (lllIll(llllIllIllllIII ? 1 : 0) && llllII(llllIllIlllllIl & Utils.llll[0])) {
                    n = llllIllIllllIlI + 0.15;
                    "".length();
                    if ("   ".length() <= 0) {
                        return null;
                    }
                }
                else {
                    n = llllIllIllllIlI;
                }
                final double llllIlllIIIllll = n;
                final double llllIlllIIIlllI = llllIlllIIIIlII - llllIlllIIIIIIl;
                final double llllIlllIIIllIl = llllIlllIIIIIll - llllIlllIIIIIII;
                final double llllIlllIIIllII = llllIlllIIIIIlI - llllIllIlllllll;
                if (lIIIIIl(llllll(llllIlllIIIlllI, 0.0))) {
                    if (llllIl(lllllI(Math.abs(llllIlllIIIlllI), llllIlllIIIllll))) {
                        llllIlllIIIIlII += llllIlllIIIllll;
                        "".length();
                        if (((0xBC ^ 0x95) & ~(0x54 ^ 0x7D)) > "   ".length()) {
                            return null;
                        }
                    }
                    else {
                        llllIlllIIIIlII += Math.abs(llllIlllIIIlllI);
                    }
                }
                if (llllIl(lllllI(llllIlllIIIlllI, 0.0))) {
                    if (llllIl(lllllI(Math.abs(llllIlllIIIlllI), llllIlllIIIllll))) {
                        llllIlllIIIIlII -= llllIlllIIIllll;
                        "".length();
                        if (((0x68 ^ 0x2C) & ~(0xDE ^ 0x9A)) != 0x0) {
                            return null;
                        }
                    }
                    else {
                        llllIlllIIIIlII -= Math.abs(llllIlllIIIlllI);
                    }
                }
                if (lIIIIIl(llllll(llllIlllIIIllIl, 0.0))) {
                    if (llllIl(lllllI(Math.abs(llllIlllIIIllIl), llllIlllIIIllll))) {
                        llllIlllIIIIIll += llllIlllIIIllll;
                        "".length();
                        if (" ".length() >= (0x98 ^ 0x9C)) {
                            return null;
                        }
                    }
                    else {
                        llllIlllIIIIIll += Math.abs(llllIlllIIIllIl);
                    }
                }
                if (llllIl(lllllI(llllIlllIIIllIl, 0.0))) {
                    if (llllIl(lllllI(Math.abs(llllIlllIIIllIl), llllIlllIIIllll))) {
                        llllIlllIIIIIll -= llllIlllIIIllll;
                        "".length();
                        if (((0x5D ^ 0x76) & ~(0x67 ^ 0x4C)) != ((0x82 ^ 0xAA) & ~(0xED ^ 0xC5))) {
                            return null;
                        }
                    }
                    else {
                        llllIlllIIIIIll -= Math.abs(llllIlllIIIllIl);
                    }
                }
                if (lIIIIIl(llllll(llllIlllIIIllII, 0.0))) {
                    if (llllIl(lllllI(Math.abs(llllIlllIIIllII), llllIlllIIIllll))) {
                        llllIlllIIIIIlI += llllIlllIIIllll;
                        "".length();
                        if (((0x85 ^ 0x80 ^ (0x18 ^ 0x57)) & (133 + 212 - 280 + 159 ^ 37 + 85 - 54 + 102 ^ -" ".length())) == "   ".length()) {
                            return null;
                        }
                    }
                    else {
                        llllIlllIIIIIlI += Math.abs(llllIlllIIIllII);
                    }
                }
                if (llllIl(lllllI(llllIlllIIIllII, 0.0))) {
                    if (llllIl(lllllI(Math.abs(llllIlllIIIllII), llllIlllIIIllll))) {
                        llllIlllIIIIIlI -= llllIlllIIIllll;
                        "".length();
                        if ((0x91 ^ 0x95) == 0x0) {
                            return null;
                        }
                    }
                    else {
                        llllIlllIIIIIlI -= Math.abs(llllIlllIIIllII);
                    }
                }
                if (lllIll(llllIlllIIIIlIl ? 1 : 0)) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.INSTANCE.player(), CPacketEntityAction.Action.STOP_SNEAKING));
                }
                Wrapper.INSTANCE.mc().getConnection().getNetworkManager().sendPacket((Packet)new CPacketPlayer.Position(llllIlllIIIIlII, llllIlllIIIIIll, llllIlllIIIIIlI, llllIlllIIIIllI));
                ++llllIllIlllllIl;
                "".length();
                if (-"  ".length() >= 0) {
                    return null;
                }
                continue;
            }
        }
        if (lllIll(llllIlllIIIIlIl ? 1 : 0)) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.INSTANCE.player(), CPacketEntityAction.Action.START_SNEAKING));
        }
        final double[] array = new double[Utils.llll[4]];
        array[Utils.llll[1]] = llllIlllIIIIlII;
        array[Utils.llll[0]] = llllIlllIIIIIll;
        array[Utils.llll[2]] = llllIlllIIIIIlI;
        return array;
    }
    
    public static float getPitch(final Entity llllIlIlIlllIIl) {
        final double llllIlIlIllIlll = llllIlIlIlllIIl.posX - Wrapper.INSTANCE.player().posX;
        double llllIlIlIllIllI = llllIlIlIlllIIl.posY - Wrapper.INSTANCE.player().posY;
        final double llllIlIlIllIlIl = llllIlIlIlllIIl.posZ - Wrapper.INSTANCE.player().posZ;
        llllIlIlIllIllI /= Wrapper.INSTANCE.player().getDistance(llllIlIlIlllIIl);
        double llllIlIlIllIIll = Math.asin(llllIlIlIllIllI) * 57.29577951308232;
        llllIlIlIllIIll = -llllIlIlIllIIll;
        return (float)llllIlIlIllIIll;
    }
    
    public static String getPlayerName(final EntityPlayer llllIllIlIllllI) {
        String s;
        if (lIIIIll(llllIllIlIllllI.getGameProfile())) {
            s = llllIllIlIllllI.getGameProfile().getName();
            "".length();
            if (((0x8 ^ 0x1) & ~(0x63 ^ 0x6A)) != 0x0) {
                return null;
            }
        }
        else {
            s = llllIllIlIllllI.getName();
        }
        return s;
    }
    
    private static int lIIllII(final float n, final float n2) {
        return fcmpl(n, n2);
    }
    
    private static boolean lIlIIII(final int llllIIIllIIlIIl) {
        return llllIIIllIIlIIl <= 0;
    }
}
