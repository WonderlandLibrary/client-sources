/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class NewPhisicsFixes {
    public ProtocolVersion version;
    private final Minecraft mc = Minecraft.getMinecraft();
    public static boolean HAS_NEW_VERSION;
    private static final ProtocolVersion[] newWaterAndSneakProtocolsNumbers;

    public static boolean isNewVersion() {
        return HAS_NEW_VERSION;
    }

    public static void updateNewVerionStatus(ProtocolVersion setedVersion) {
        HAS_NEW_VERSION = setedVersion != null && Arrays.stream(newWaterAndSneakProtocolsNumbers).anyMatch(protocol -> protocol.equals(setedVersion));
    }

    public AxisAlignedBB bounded06(AxisAlignedBB prevAABB) {
        double x = prevAABB.minX + (prevAABB.maxX - prevAABB.minX) / 2.0;
        double y = prevAABB.minY;
        double z = prevAABB.minZ + (prevAABB.maxZ - prevAABB.minZ) / 2.0;
        double expand = 0.6;
        return new AxisAlignedBB(x - expand / 2.0, y, z - expand / 2.0, x + expand / 2.0, y + expand, z + expand / 2.0);
    }

    public AxisAlignedBB boundedSneak(AxisAlignedBB prevAABB) {
        double x = prevAABB.minX + (prevAABB.maxX - prevAABB.minX) / 2.0;
        double y = prevAABB.minY;
        double z = prevAABB.minZ + (prevAABB.maxZ - prevAABB.minZ) / 2.0;
        double expandXZ = 0.6;
        double expandY = 1.5;
        return new AxisAlignedBB(x - expandXZ / 2.0, y, z - expandXZ / 2.0, x + expandXZ / 2.0, y + expandY, z + expandXZ / 2.0);
    }

    public AxisAlignedBB yOffAxis(EntityLivingBase baseIn, double addMin, double addMax) {
        return new AxisAlignedBB(baseIn.posX - (double)baseIn.width / 2.0, baseIn.posY + addMin, baseIn.posZ - (double)baseIn.width / 2.0, baseIn.posX + (double)baseIn.width / 2.0, baseIn.posY + addMax, baseIn.posZ + (double)baseIn.width / 2.0);
    }

    public boolean hasCollide(EntityLivingBase baseIn, double addMin, double addMax) {
        return !this.mc.world.getCollisionBoxes(baseIn, this.yOffAxis(baseIn, addMin, addMax)).isEmpty();
    }

    public boolean hasCollide(EntityLivingBase baseIn, AxisAlignedBB axisAlignedBB) {
        return !this.mc.world.getCollisionBoxes(baseIn, axisAlignedBB).isEmpty();
    }

    public boolean isWater(double x, double y, double z) {
        return this.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.WATER;
    }

    public boolean[] updateLayOrShift(EntityLivingBase baseIn) {
        boolean[] has;
        block7: {
            block10: {
                block9: {
                    AxisAlignedBB prevAxisAlignedBB;
                    block8: {
                        prevAxisAlignedBB = baseIn instanceof EntityPlayer ? this.yOffAxis(baseIn, 0.0, baseIn.height) : baseIn.boundingBox;
                        has = new boolean[]{false, false};
                        if (baseIn == null || baseIn.isDead || this.mc.world == null || prevAxisAlignedBB == null) break block7;
                        if (baseIn.isLay) {
                            baseIn.setEntityBoundingBox(prevAxisAlignedBB);
                            baseIn.isLay = false;
                        }
                        baseIn.isNewSneak = false;
                        if (!this.hasCollide(baseIn, prevAxisAlignedBB)) break block8;
                        if (this.hasCollide(baseIn, this.yOffAxis(baseIn, 0.0, 1.4999)) && !this.hasCollide(baseIn, this.yOffAxis(baseIn, 0.0, 0.6))) {
                            has[0] = true;
                            baseIn.isLay = true;
                        }
                        if (!baseIn.isLay && !baseIn.isNewSneak && (this.hasCollide(baseIn, this.yOffAxis(baseIn, -0.01, 1.8)) && !this.hasCollide(baseIn, this.yOffAxis(baseIn, 0.0, 1.499)) && this.hasCollide(baseIn, this.yOffAxis(baseIn, -1.01, 0.0)) || baseIn.isSneaking())) {
                            has[1] = true;
                            baseIn.isNewSneak = true;
                        }
                        break block7;
                    }
                    AxisAlignedBB newLayBox = this.bounded06(prevAxisAlignedBB);
                    if (newLayBox == null || this.hasCollide(baseIn, newLayBox) || !baseIn.isInWater() || !baseIn.isLay && !this.isWater(baseIn.posX, baseIn.posY + (baseIn.isLay ? -0.08 : (double)baseIn.getEyeHeight()), baseIn.posZ)) break block7;
                    if (baseIn.isSprinting()) break block9;
                    if (!(baseIn instanceof EntityPlayerSP)) break block7;
                    EntityPlayerSP sp = (EntityPlayerSP)baseIn;
                    if (!sp.movementInput.forwardKeyDown || sp.movementInput.backKeyDown) break block7;
                }
                if (!(baseIn instanceof EntityPlayer)) break block10;
                EntityPlayer player = (EntityPlayer)baseIn;
                if (player.capabilities.isFlying) break block7;
            }
            has[0] = true;
            baseIn.isLay = true;
            baseIn.isNewSneak = false;
        }
        return has;
    }

    static {
        newWaterAndSneakProtocolsNumbers = new ProtocolVersion[]{ProtocolVersion.v1_13, ProtocolVersion.v1_13_1, ProtocolVersion.v1_13_2, ProtocolVersion.v1_14, ProtocolVersion.v1_14_1, ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_3, ProtocolVersion.v1_14_4, ProtocolVersion.v1_15, ProtocolVersion.v1_15_1, ProtocolVersion.v1_15_2, ProtocolVersion.v1_16, ProtocolVersion.v1_16_1, ProtocolVersion.v1_16_2, ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_4, ProtocolVersion.v1_17, ProtocolVersion.v1_17_1, ProtocolVersion.v1_18, ProtocolVersion.v1_18_2, ProtocolVersion.v1_19, ProtocolVersion.v1_19_1, ProtocolVersion.v1_19_3, ProtocolVersion.v1_19_4, ProtocolVersion.v1_20, ProtocolVersion.v1_20_2};
    }
}

