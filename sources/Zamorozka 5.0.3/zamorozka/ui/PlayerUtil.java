package zamorozka.ui;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.KillAura;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class PlayerUtil implements MCUtil {

    public static boolean isInLiquid() {
        if (Minecraft.player == null) {
            return false;
        }
        for (int x = MathHelper.floor(Minecraft.player.boundingBox.minX); x < MathHelper.floor(Minecraft.player.boundingBox.maxX) + 1; x++) {
            for (int z = MathHelper.floor(Minecraft.player.boundingBox.minZ); z < MathHelper.floor(Minecraft.player.boundingBox.maxZ) + 1; z++) {
                BlockPos pos = new BlockPos(x, (int) Minecraft.player.boundingBox.minY, z);
                Block block = Wrapper.getMinecraft().world.getBlockState(pos).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    return block instanceof BlockLiquid;
                }
            }
        }
        return false;
    }

    public static void playClick() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getRecordSoundRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F, 0.25F, 0.25F));
    }
    
    public int getPlayerPing(final String name) {
        final EntityPlayer player = this.mc.world.getPlayerEntityByName(name);
        if (player instanceof EntityOtherPlayerMP) {
            return ((EntityOtherPlayerMP)player).playerInfo.getResponseTime();
        }
        return 0;
    }

    public static void setKeyPressed(int key) {
        try {
            final Robot robot = new Robot();
            robot.keyPress(key);
            robot.keyRelease(key);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static boolean playeriswalkingforward() {
        return !PlayerUtil.mc.gameSettings.keyBindBack.pressed && !PlayerUtil.mc.gameSettings.keyBindLeft.pressed && !PlayerUtil.mc.gameSettings.keyBindRight.pressed && PlayerUtil.mc.gameSettings.keyBindForward.pressed;
    }

    public static void toFwd(double speed) {
        float f = Minecraft.player.rotationYaw * 0.017453292f;
        Minecraft.player.motionX -= (double) MathHelper.sin(f) * speed;
        Minecraft.player.motionZ += (double) MathHelper.cos(f) * speed;
    }

    public static int getItemDamage(ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    public static float getDamageInPercent(ItemStack stack) {
        return (getItemDamage(stack) / (float) stack.getMaxDamage()) * 100;
    }

    public static int getRoundedDamage(ItemStack stack) {
        return (int) getDamageInPercent(stack);
    }

    
    
    /*public static boolean isInsideBlock() {
        for(int x = MathHelper.floor(Minecraft.getMinecraft().player.boundingBox.minX); x < MathHelper.floor(Minecraft.getMinecraft().player.boundingBox.maxX) + 1; x++) {
            for(int y = MathHelper.floor(Minecraft.getMinecraft().player.boundingBox.minY); y < MathHelper.floor(Minecraft.getMinecraft().player.boundingBox.maxY) + 1; y++) {
                for(int z = MathHelper.floor(Minecraft.getMinecraft().player.boundingBox.minZ); z < MathHelper.floor(Minecraft.getMinecraft().player.boundingBox.maxZ) + 1; z++) {
                    Block block = Minecraft.getMinecraft().world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if(block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(block, new BlockPos(x, y, z), Minecraft.getMinecraft().world.getBlockState(new BlockPos(x, y, z)));
                        if(block instanceof BlockHopper)
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        if(boundingBox != null && Minecraft.getMinecraft().player.boundingBox.intersectsWith(boundingBox))
                            return true;
                    }
                }
            }
        }
        return false;
    }*/

    public static boolean isInsideBlock(EntityLivingBase entity) {
        for (int x = MathHelper.floor(entity.getEntityBoundingBox().minX); x < MathHelper
                .floor(entity.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor(entity.getEntityBoundingBox().minY); y < MathHelper
                    .floor(entity.getEntityBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor(entity.getEntityBoundingBox().minZ); z < MathHelper
                        .floor(entity.getEntityBoundingBox().maxZ) + 1; ++z) {
                    final Block block = (Block) mc.world.getBlockState(new BlockPos(x, y, z));
                    final AxisAlignedBB boundingBox;
                    if (block != null && !(block instanceof BlockAir)
                            && (boundingBox = block.getCollisionBoundingBox(BlockUtils.getState(new BlockPos(x, y, z)),
                            mc.world, new BlockPos(x, y, z))) != null
                            && entity.getEntityBoundingBox().intersectsWith(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isPlayerMovingLegit() {
        return mc.gameSettings.keyBindForward.isKeyDown();
    }

    public static boolean isPlayerMovingKeybind() {
        return mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown();
    }

    public static int findBlock(final Block block) {
        return findItem(new ItemStack(block).getItem());
    }

    public static int findItem(final Item item) {
        try {
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = Minecraft.player.inventory.getStackInSlot(i);
                if (item == stack.getItem()) {
                    return i;
                }
            }
        } catch (Exception ignored) {
        }
        return -1;
    }

    public static float getItemAtkDamage(ItemStack itemStack) {
        Iterator iterator;
        Multimap multimap = itemStack.getAttributeModifiers(null);
        if (!multimap.isEmpty() && (iterator = multimap.entries().iterator()).hasNext()) {
            double damage;
            Map.Entry entry = (Map.Entry) iterator.next();
            AttributeModifier attributeModifier = (AttributeModifier) entry.getValue();
            double d = damage = attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2 ? attributeModifier.getAmount() : attributeModifier.getAmount() * 100.0;
            if (attributeModifier.getAmount() > 1.0) {
                return 1.0f + (float) damage;
            }
            return 1.0f;
        }
        return 1.0f;
    }

    public static int bestWeapon(Entity target) {
        Minecraft mc = Minecraft.getMinecraft();
        Minecraft.player.inventory.currentItem = 0;
        int firstSlot = 0;
        int bestWeapon = -1;
        int j = 1;
        for (int i = 0; i < 9; i = (byte) (i + 1)) {
            Minecraft.player.inventory.currentItem = i;
            ItemStack itemStack = Minecraft.player.getHeldItemMainhand();
            if (itemStack == null) continue;
            int itemAtkDamage = (int) PlayerUtil.getItemAtkDamage(itemStack);
            if ((itemAtkDamage = (int) ((float) itemAtkDamage + EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED))) <= j)
                continue;
            j = itemAtkDamage;
            bestWeapon = i;
        }
        if (bestWeapon != -1) {
            return bestWeapon;
        }
        return firstSlot;
    }

    public static void shiftClick(Item i) {
        for (int i1 = 9; i1 < 37; ++i1) {
            ItemStack itemstack = Minecraft.player.inventoryContainer.getSlot(i1).getStack();
            if (itemstack == null || itemstack.getItem() != i) continue;
            PlayerUtil.mc.playerController.windowClick(0, i1, 1, ClickType.QUICK_MOVE, Minecraft.player);
            break;
        }
    }

    public static boolean hotbarIsFull() {
        for (int i = 0; i <= 36; ++i) {
            ItemStack itemstack = Minecraft.player.inventory.getStackInSlot(i);
            if (itemstack != null) continue;
            return false;
        }
        return true;
    }

    public static BlockPos getHypixelBlockpos(String str) {
        int val = 89;
        if (str != null && str.length() > 1) {
            char[] chs = str.toCharArray();

            int lenght = chs.length;
            for (int i = 0; i < lenght; i++)
                val += (int) chs[i] * str.length() * str.length() + (int) str.charAt(0) + (int) str.charAt(1);
            val /= str.length();
        }
        return new BlockPos(val, -val % 255, val);
    }

    public static boolean isOnLiquid() {
        AxisAlignedBB boundingBox = Minecraft.player.getEntityBoundingBox();
        if (boundingBox == null) {
            return false;
        }
        boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.01D, 0.0D);
        boolean onLiquid = false;
        int y = (int) boundingBox.minY;
        for (int x = MathHelper.floor(boundingBox.minX); x < MathHelper
                .floor(boundingBox.maxX + 1.0D); x++) {
            for (int z = MathHelper.floor(boundingBox.minZ); z < MathHelper
                    .floor(boundingBox.maxZ + 1.0D); z++) {
                Block block = Wrapper.getMinecraft().world.getBlockState((new BlockPos(x, y, z))).getBlock();
                if (block != Blocks.AIR) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }

    public static void blinkToPos(final double[] startPos, final BlockPos endPos, final double slack, final double[] pOffset) {
        double curX = startPos[0];
        double curY = startPos[1];
        double curZ = startPos[2];
        try {
            final double endX = endPos.getX() + 0.5;
            final double endY = endPos.getY() + 1.0;
            final double endZ = endPos.getZ() + 0.5;

            double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
            int count = 0;
            while (distance > slack) {
                distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
                if (count > 120) {
                    break;
                }
                final boolean next = false;
                final double diffX = curX - endX;
                final double diffY = curY - endY;
                final double diffZ = curZ - endZ;
                final double offset = ((count & 0x1) == 0x0) ? pOffset[0] : pOffset[1];
                if (diffX < 0.0) {
                    if (Math.abs(diffX) > offset) {
                        curX += offset;
                    } else {
                        curX += Math.abs(diffX);
                    }
                }
                if (diffX > 0.0) {
                    if (Math.abs(diffX) > offset) {
                        curX -= offset;
                    } else {
                        curX -= Math.abs(diffX);
                    }
                }
                if (diffY < 0.0) {
                    if (Math.abs(diffY) > 0.25) {
                        curY += 0.25;
                    } else {
                        curY += Math.abs(diffY);
                    }
                }
                if (diffY > 0.0) {
                    if (Math.abs(diffY) > 0.25) {
                        curY -= 0.25;
                    } else {
                        curY -= Math.abs(diffY);
                    }
                }
                if (diffZ < 0.0) {
                    if (Math.abs(diffZ) > offset) {
                        curZ += offset;
                    } else {
                        curZ += Math.abs(diffZ);
                    }
                }
                if (diffZ > 0.0) {
                    if (Math.abs(diffZ) > offset) {
                        curZ -= offset;
                    } else {
                        curZ -= Math.abs(diffZ);
                    }
                }
                Minecraft.getConnection().sendPacket((new Position(curX, curY, curZ, true)));
                ++count;
            }
        } catch (Exception e) {

        }
    }

    public static void hypixelTeleport(final double[] startPos, final BlockPos endPos) {

        double distx = startPos[0] - endPos.getX() + 0.5;
        double disty = startPos[1] - endPos.getY();
        double distz = startPos[2] - endPos.getZ() + 0.5;
        double dist = Math.sqrt(Minecraft.player.getDistanceSq(endPos));
        double distanceEntreLesPackets = 0.31 + MovementUtilis.getSpeedEffect() / 20;
        double xtp, ytp, ztp = 0;
        if (dist > distanceEntreLesPackets) {

            double nbPackets = Math.round(dist / distanceEntreLesPackets + 0.49999999999) - 1;

            xtp = Minecraft.player.posX;
            ytp = Minecraft.player.posY;
            ztp = Minecraft.player.posZ;
            double count = 0;
            for (int i = 1; i < nbPackets; i++) {
                double xdi = (endPos.getX() - Minecraft.player.posX) / (nbPackets);
                xtp += xdi;

                double zdi = (endPos.getZ() - Minecraft.player.posZ) / (nbPackets);
                ztp += zdi;

                double ydi = (endPos.getY() - Minecraft.player.posY) / (nbPackets);
                ytp += ydi;
                count++;

                if (!Wrapper.getMinecraft().world.getBlockState(new BlockPos(xtp, ytp - 1, ztp)).getBlock().isBlockSolid(null, endPos, null)) {
                    if (count <= 2) {
                        ytp += 2E-8;
                    } else if (count >= 4) {
                        count = 0;
                    }
                }
                CPacketPlayer.Position Packet = new CPacketPlayer.Position(xtp, ytp, ztp, false);
                Minecraft.player.connection.sendPacket(Packet);
            }

            Minecraft.player.setPosition(endPos.getX() + 0.5, endPos.getY(), endPos.getZ() + 0.5);

        } else {
            Minecraft.player.setPosition(endPos.getX(), endPos.getY(), endPos.getZ());

        }
    }

    public static void teleport(final double[] startPos, final BlockPos endPos) {
        double distx = startPos[0] - endPos.getX() + 0.5;
        double disty = startPos[1] - endPos.getY();
        double distz = startPos[2] - endPos.getZ() + 0.5;
        double dist = Math.sqrt(Minecraft.player.getDistanceSq(endPos));
        double distanceEntreLesPackets = 5;
        double xtp, ytp, ztp = 0;

        if (dist > distanceEntreLesPackets) {
            double nbPackets = Math.round(dist / distanceEntreLesPackets + 0.49999999999) - 1;
            xtp = Minecraft.player.posX;
            ytp = Minecraft.player.posY;
            ztp = Minecraft.player.posZ;
            double count = 0;
            for (int i = 1; i < nbPackets; i++) {
                double xdi = (endPos.getX() - Minecraft.player.posX) / (nbPackets);
                xtp += xdi;

                double zdi = (endPos.getZ() - Minecraft.player.posZ) / (nbPackets);
                ztp += zdi;

                double ydi = (endPos.getY() - Minecraft.player.posY) / (nbPackets);
                ytp += ydi;
                count++;
                CPacketPlayer.Position Packet = new CPacketPlayer.Position(xtp, ytp, ztp, true);

                Minecraft.player.connection.sendPacket(Packet);
            }

            Minecraft.player.setPosition(endPos.getX() + 0.5, endPos.getY(), endPos.getZ() + 0.5);
        } else {
            Minecraft.player.setPosition(endPos.getX(), endPos.getY(), endPos.getZ());
        }
    }

    public static boolean isMoving() {
        if ((!Minecraft.player.isCollidedHorizontally) && (!Minecraft.player.isSneaking())) {

        }
        return ((MovementInput.moveForward != 0.0F || MovementInput.moveStrafe != 0.0F));
    }

    public static boolean isMoving2() {
        return ((Minecraft.player.moveForward != 0.0F || Minecraft.player.moveStrafing != 0.0F));
    }

    public static void damage5() {
        double offset = 0.060100000351667404D;
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;

        for (int i = 0; (double) i < (double) getMaxFallDist() / 0.05510000046342611D + 1.0D; ++i) {
            Minecraft.getConnection().sendPacket(new CPacketPlayer.Position(x, y + 0.060100000351667404D, z, false));
            Minecraft.getConnection().sendPacket(new CPacketPlayer.Position(x, y + 5.000000237487257E-4D, z, false));
            Minecraft.getConnection().sendPacket(new CPacketPlayer.Position(x, y + 0.004999999888241291D + 6.01000003516674E-8D, z, false));

        }
        Minecraft.getConnection().sendPacket(new CPacketPlayer(true));
    }

    public static void damage() {
        NetHandlerPlayClient netHandler = Minecraft.getConnection();
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        for (int i = 0; i < getMaxFallDist() / 0.05510000046342611D + 1.0D; i++) {
            netHandler.sendPacket(new CPacketPlayer.Position(x, y + 0.060100000351667404D, z, false));
            netHandler.sendPacket(new CPacketPlayer.Position(x, y + 5.000000237487257E-4D, z, false));
            netHandler.sendPacket(new CPacketPlayer.Position(x, y + 0.004999999888241291D + 6.01000003516674E-8D, z, false));
        }
        netHandler.sendPacket(new CPacketPlayer(true));
    }

    public static void damage2() {
        for (int i = 0; i < 48; i++) {
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 0.0625D, Minecraft.player.posZ, false));
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
            if (i % 3 == 0)
                Minecraft.player.connection.sendPacket(new CPacketKeepAlive(System.currentTimeMillis()));
        }
        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 1.0E-6D, Minecraft.player.posZ, false));
        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, true));
    }

    public static void damage3() {
        for (int i = 0; (double) i < 29.2D; ++i) {
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 0.0525D, Minecraft.player.posZ, false));
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 0.0525D, Minecraft.player.posZ, false));
        }
        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, true));
    }

    public static void damage4() {
        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 0.0825D, Minecraft.player.posZ, true));
        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 0.0825D, Minecraft.player.posZ, true));
    }

    public static float getMaxFallDist() {
        PotionEffect potioneffect = Minecraft.player.getActivePotionEffect(Potion.getPotionById(8));
        int f = (potioneffect != null) ? (potioneffect.getAmplifier() + 1) : 0;
        return (Minecraft.player.getMaxFallHeight() + f);
    }

    public static double getDoubleRandom(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static BlockPos GetLocalPlayerPosFloored() {
        if (Minecraft.player == null)
            return BlockPos.ORIGIN;

        return new BlockPos(Math.floor(Minecraft.player.posX), Math.floor(Minecraft.player.posY), Math.floor(Minecraft.player.posZ));
    }

    public static boolean IsPlayerTrapped() {
        BlockPos l_PlayerPos = GetLocalPlayerPosFloored();

        final BlockPos[] l_TrapPositions = {
                l_PlayerPos.down(),
                l_PlayerPos.up().up(),
                l_PlayerPos.north(),
                l_PlayerPos.south(),
                l_PlayerPos.east(),
                l_PlayerPos.west(),
                l_PlayerPos.north().up(),
                l_PlayerPos.south().up(),
                l_PlayerPos.east().up(),
                l_PlayerPos.west().up(),
        };

        for (BlockPos l_Pos : l_TrapPositions) {
            IBlockState l_State = mc.world.getBlockState(l_Pos);

            if (l_State.getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(l_Pos).getBlock() != Blocks.BEDROCK)
                return false;
        }

        return true;
    }

    public static FacingDirection GetFacing() {
        switch (MathHelper.floor((double) (Minecraft.player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7) {
            case 0:
            case 1:
                return FacingDirection.South;
            case 2:
            case 3:
                return FacingDirection.West;
            case 4:
            case 5:
                return FacingDirection.North;
            case 6:
            case 7:
                return FacingDirection.East;
        }
        return FacingDirection.North;
    }

    public static void PacketFacePitchAndYaw(float p_Pitch, float p_Yaw) {
        boolean l_IsSprinting = Minecraft.player.isSprinting();

        if (l_IsSprinting != Minecraft.player.serverSprintState) {
            if (l_IsSprinting) {
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SPRINTING));
            } else {
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }

            Minecraft.player.serverSprintState = l_IsSprinting;
            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }

        boolean l_IsSneaking = Minecraft.player.isSneaking();

        if (l_IsSneaking != Minecraft.player.serverSneakState) {
            if (l_IsSneaking) {
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
            } else {
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }

            Minecraft.player.serverSneakState = l_IsSneaking;
        }

        if (PlayerUtil.isCurrentViewEntity()) {
            float l_Pitch = p_Pitch;
            float l_Yaw = p_Yaw;

            AxisAlignedBB axisalignedbb = Minecraft.player.getEntityBoundingBox();
            double l_PosXDifference = Minecraft.player.posX - Minecraft.player.lastReportedPosX;
            double l_PosYDifference = axisalignedbb.minY - Minecraft.player.lastReportedPosY;
            double l_PosZDifference = Minecraft.player.posZ - Minecraft.player.lastReportedPosZ;
            double l_YawDifference = l_Yaw - Minecraft.player.lastReportedYaw;
            double l_RotationDifference = l_Pitch - Minecraft.player.lastReportedPitch;
            ++Minecraft.player.positionUpdateTicks;
            boolean l_MovedXYZ = l_PosXDifference * l_PosXDifference + l_PosYDifference * l_PosYDifference + l_PosZDifference * l_PosZDifference > 9.0E-4D || Minecraft.player.positionUpdateTicks >= 20;
            boolean l_MovedRotation = l_YawDifference != 0.0D || l_RotationDifference != 0.0D;

            if (Minecraft.player.isRiding()) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(Minecraft.player.motionX, -999.0D, Minecraft.player.motionZ, l_Yaw, l_Pitch, Minecraft.player.onGround));
                l_MovedXYZ = false;
            } else if (l_MovedXYZ && l_MovedRotation) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(Minecraft.player.posX, axisalignedbb.minY, Minecraft.player.posZ, l_Yaw, l_Pitch, Minecraft.player.onGround));
            } else if (l_MovedXYZ) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, axisalignedbb.minY, Minecraft.player.posZ, Minecraft.player.onGround));
            } else if (l_MovedRotation) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Rotation(l_Yaw, l_Pitch, Minecraft.player.onGround));
            } else if (Minecraft.player.prevOnGround != Minecraft.player.onGround) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer(Minecraft.player.onGround));
            }

            if (l_MovedXYZ) {
                Minecraft.player.lastReportedPosX = Minecraft.player.posX;
                Minecraft.player.lastReportedPosY = axisalignedbb.minY;
                Minecraft.player.lastReportedPosZ = Minecraft.player.posZ;
                Minecraft.player.positionUpdateTicks = 0;
            }

            if (l_MovedRotation) {
                Minecraft.player.lastReportedYaw = l_Yaw;
                Minecraft.player.lastReportedPitch = l_Pitch;
            }

            Minecraft.player.prevOnGround = Minecraft.player.onGround;
            Minecraft.player.autoJumpEnabled = Minecraft.player.mc.gameSettings.autoJump;
        }
    }

    public static boolean isCurrentViewEntity() {
        return mc.getRenderViewEntity() == Minecraft.player;
    }

    public static int getHealthColor(final EntityLivingBase player) {
        final float f = player.getHealth();
        final float f2 = player.getMaxHealth();
        final float f3 = Math.max(0.0f, Math.min(f, f2) / f2);
        return Color.HSBtoRGB(f3 / 3.0f, 1.0f, 0.75f) | 0xFF000000;
    }

    public boolean isAnyEntityAimingAtMe() {
        if (ModuleManager.getModule(KillAura.class).getState()) {
            final List<Entity> entityList = new ArrayList<>();
            for (Entity entityPlayer : mc.world.loadedEntityList) {
                if (!entityPlayer.equals(Minecraft.player) && entityPlayer.getDistanceToEntity(Minecraft.player) <= 8) {
                    if (!entityList.contains(entityPlayer)) {
                        entityList.add(entityPlayer);
                    }
                }
            }
            if (!entityList.isEmpty()) {
                return entityList.stream().anyMatch(entity -> AimUtil.isAimAtMe(entity));
            }
            return false;
        } else {
            return true;
        }
    }


    public enum FacingDirection {
        North,
        South,
        East,
        West,
    }
}