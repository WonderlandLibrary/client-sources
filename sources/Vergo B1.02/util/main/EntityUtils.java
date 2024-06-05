package xyz.vergoclient.util.main;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class EntityUtils {

    public static void blinkToPos(double[] startPos, BlockPos endPos, double slack, double[] pOffset) {

        double curX = startPos[0];
        double curY = startPos[1];
        double curZ = startPos[2];
        double endX = endPos.getX();
        double endY = endPos.getY();
        double endZ = endPos.getZ();
        double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
        int count = 0;
        while (distance > slack) {
            distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
            if (count > 120) {
                break;
            }
            boolean next = false;
            double diffX = curX - endX;
            double diffY = curY - endY;
            double diffZ = curZ - endZ;
            double offset = ((count & 0x1) == 0x0) ? pOffset[0] : pOffset[1];
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
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(curX, curY, curZ, true));
            ++count;
        }
    }

}
