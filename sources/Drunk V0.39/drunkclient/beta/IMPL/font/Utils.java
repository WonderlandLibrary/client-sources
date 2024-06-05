/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.font;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Utils {
    public static boolean fuck = true;
    private static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isContainerEmpty(Container container) {
        int i = 0;
        int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        while (i < slotAmount) {
            if (container.getSlot(i).getHasStack()) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public static Minecraft getMinecraft() {
        return mc;
    }

    public static boolean canBlock() {
        if (mc == null) {
            mc = Minecraft.getMinecraft();
        }
        if (Minecraft.thePlayer.getHeldItem() == null) {
            return false;
        }
        if (Minecraft.thePlayer.isBlocking()) return true;
        if (Minecraft.thePlayer.isUsingItem()) {
            if (Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                return true;
            }
        }
        if (!(Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return false;
        if (!Minecraft.getMinecraft().gameSettings.keyBindUseItem.isPressed()) return false;
        return true;
    }

    public static String getMD5(String input) {
        StringBuilder res = new StringBuilder();
        try {
            byte[] md5;
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(input.getBytes());
            byte[] arrby = md5 = algorithm.digest();
            int n = arrby.length;
            int n2 = 0;
            while (n2 < n) {
                byte aMd5 = arrby[n2];
                String tmp = Integer.toHexString(0xFF & aMd5);
                if (tmp.length() == 1) {
                    res.append("0").append(tmp);
                } else {
                    res.append(tmp);
                }
                ++n2;
            }
            return res.toString();
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        return res.toString();
    }

    public static void breakAnticheats() {
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX + Minecraft.thePlayer.motionX, Minecraft.thePlayer.posY - 110.0, Minecraft.thePlayer.posZ + Minecraft.thePlayer.motionZ, true));
    }

    public static int add(int number, int add, int max) {
        int n;
        if (number + add > max) {
            n = max;
            return n;
        }
        n = number + add;
        return n;
    }

    public static int remove(int number, int remove, int min) {
        int n;
        if (number - remove < min) {
            n = min;
            return n;
        }
        n = number - remove;
        return n;
    }

    public static int check(int number) {
        if (number <= 0) {
            return 1;
        }
        if (number > 255) {
            return 255;
        }
        int n = number;
        return n;
    }

    public static double getDist() {
        double distance = 0.0;
        double i = Minecraft.thePlayer.posY;
        while (i > 0.0) {
            if (i < 0.0) {
                return Minecraft.thePlayer.posY - distance;
            }
            Block block = Utils.mc.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX, i, Minecraft.thePlayer.posZ)).getBlock();
            if (block.getMaterial() != Material.air && block.isCollidable() && (block.isFullBlock() || block instanceof BlockSlab || block instanceof BlockBarrier || block instanceof BlockStairs || block instanceof BlockGlass || block instanceof BlockStainedGlass)) {
                if (block instanceof BlockSlab) {
                    i -= 0.5;
                }
                distance = i;
                return Minecraft.thePlayer.posY - distance;
            }
            i -= 0.1;
        }
        return Minecraft.thePlayer.posY - distance;
    }
}

