package pw.latematt.xiv.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.mod.mods.movement.ClickTeleport;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.EntityUtils;

/**
 * @author Rederpz
 */
public class Forward implements CommandHandler {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String distanceString = arguments[1];
            try {
                double distance = Double.parseDouble(distanceString);

                if (distance > 25) {
                    distance = 25;
                } else if (distance < -25) {
                    distance = -25;
                } else if (distance == 0) {
                    distance = 5;
                }

                float dir = mc.thePlayer.rotationYaw;
                if (mc.thePlayer.moveForward < 0.0F)
                    dir += 180.0F;

                if (mc.thePlayer.moveStrafing > 0.0F)
                    dir -= 90.0F * (mc.thePlayer.moveForward < 0.0F ? -0.5F : mc.thePlayer.moveForward > 0.0F ? 0.5F : 1.0F);

                if (mc.thePlayer.moveStrafing < 0.0F)
                    dir += 90.0F * (mc.thePlayer.moveForward < 0.0F ? -0.5F : mc.thePlayer.moveForward > 0.0F ? 0.5F : 1.0F);

                float xD = (float) Math.cos((dir + 90.0F) * Math.PI / 180.0D);
                float zD = (float) Math.sin((dir + 90.0F) * Math.PI / 180.0D);

                double[] playerPosition = new double[]{EntityUtils.getReference().posX, EntityUtils.getReference().posY, EntityUtils.getReference().posZ};
                BlockPos teleportPosition = new BlockPos(playerPosition[0] + (xD * distance), playerPosition[1], playerPosition[2] + (zD * distance));

                double[] blockPosition = new double[]{teleportPosition.getX() + 0.5F, teleportPosition.getY(), teleportPosition.getZ() + 0.5F};

                EntityUtils.teleportToPosition(playerPosition, blockPosition, 0.25D, 0.0D, true, true);
                mc.thePlayer.setPosition(blockPosition[0], blockPosition[1], blockPosition[2]);

                ChatLogger.print(String.format("Teleported %s blocks %s.", distance, distance > 0 ? "forward" : "backward"));

                ClickTeleport clickTP = (ClickTeleport) XIV.getInstance().getModManager().find("clickteleport");

                if (clickTP != null) {
                    clickTP.setDelay(5);
                }
            } catch (NumberFormatException e) {
                ChatLogger.print(String.format("\"%s\" is not a number.", distanceString));
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: forward <blocks>");
        }
    }
}
