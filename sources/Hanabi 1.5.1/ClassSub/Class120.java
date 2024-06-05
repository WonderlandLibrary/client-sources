package ClassSub;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public enum Class120
{
    INSTANCE;
    
    public static ArrayList<Class84> notifications;
    public static double addY;
    private static final Class120[] $VALUES;
    
    
    public void drawNotifications() {
        try {
            final double n2;
            double n = n2 = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - 25;
            for (int i = 0; i < Class120.notifications.size(); ++i) {
                final Class84 class84 = Class120.notifications.get(i);
                if (class84.shouldDelete()) {
                    Class120.notifications.remove(i);
                }
                class84.draw(n, n2);
                n -= class84.getHeight() + 1.0;
            }
        }
        catch (Throwable t) {}
    }
    
    public static void sendClientMessage(final String s, final Class84.Class307 class307) {
        if (Class120.notifications.size() > 8) {
            Class120.notifications.remove(0);
        }
        Class120.notifications.add(new Class84(s, class307));
    }
    
    public static int reAlpha(final int n, final float n2) {
        final Color color = new Color(n);
        return new Color(0.003921569f * color.getRed(), 0.003921569f * color.getGreen(), 0.003921569f * color.getBlue(), n2).getRGB();
    }
    
    public static boolean isBlockBetween(final BlockPos blockPos, final BlockPos blockPos2) {
        final Minecraft getMinecraft = Minecraft.getMinecraft();
        final int getX = blockPos.getX();
        final int getY = blockPos.getY();
        final int getZ = blockPos.getZ();
        final int getX2 = blockPos2.getX();
        final int getY2 = blockPos2.getY();
        final int getZ2 = blockPos2.getZ();
        final double n = getX2 - getX;
        final double n2 = getY2 - getY;
        final double n3 = getZ2 - getZ;
        double n4 = getX;
        double n5 = getY;
        double n6 = getZ;
        for (int n7 = (int)Math.max(Math.abs(n), Math.max(Math.abs(n2), Math.abs(n3))) * 4, i = 0; i < n7 - 1; ++i) {
            n4 += n / n7;
            n5 += n2 / n7;
            n6 += n3 / n7;
            if (n4 != getX2 || n5 != getY2 || n6 != getZ2) {
                final Block getBlock = getMinecraft.theWorld.getBlockState(new BlockPos(n4, n5, n6)).getBlock();
                if (getBlock.getMaterial() != Material.air && getBlock.getMaterial() != Material.water && !(getBlock instanceof BlockVine) && !(getBlock instanceof BlockLadder)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static String removeColorCode(final String s) {
        return s.replaceAll("§.", "");
    }
    
    static {
        $VALUES = new Class120[] { Class120.INSTANCE };
        Class120.notifications = new ArrayList<Class84>();
    }
}
