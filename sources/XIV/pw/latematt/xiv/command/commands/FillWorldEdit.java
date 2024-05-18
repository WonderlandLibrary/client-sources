package pw.latematt.xiv.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.Render3DEvent;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.RenderUtils;

/**
 * @author Rederpz
 */
public class FillWorldEdit implements CommandHandler, Listener<Render3DEvent> {
    private final Minecraft mc = Minecraft.getMinecraft();
    private BlockPos pos1, pos2;

    @Override
    public void onEventCalled(Render3DEvent event) {
        if (pos1 != null && pos2 != null) {
            RenderUtils.beginGl();
            drawBoxes(pos1.getX() - mc.getRenderManager().renderPosX, pos1.getY() - mc.getRenderManager().renderPosY, pos1.getZ() - mc.getRenderManager().renderPosZ, pos2.getX() - mc.getRenderManager().renderPosX, pos2.getY() - mc.getRenderManager().renderPosY, pos2.getZ() - mc.getRenderManager().renderPosZ);
            RenderUtils.endGl();
        }
    }

    public void placeBlocks(String blockid) {
        if (pos1 == null || pos2 == null) {
            ChatLogger.print("One or more positions is not set.");
            return;
        }

        String command = "/fill " + pos1.getX() + " " + pos1.getY() + " " + pos1.getZ() + " " + pos2.getX() + " " + pos2.getY() + " " + pos2.getZ() + " " + blockid + " 0 destroy";
        mc.thePlayer.sendChatMessage(command);
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "pos1":
                    try {
                        if (arguments[2].equalsIgnoreCase("clear")) {
                            pos1 = null;
                        } else {
                            pos1 = new BlockPos(Integer.parseInt(arguments[2]), Integer.parseInt(arguments[3]), Integer.parseInt(arguments[4]));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        pos1 = new BlockPos(mc.thePlayer.getPosition());
                    } catch (NumberFormatException e) {
                        ChatLogger.print("Invalid arguments, valid: creativeworldedit pos1 <x> <y> <z>");
                    }

                    if (pos1 != null) {
                        ChatLogger.print("First Position set to: " + pos1);

                        if (pos2 != null) {
                            XIV.getInstance().getListenerManager().add(this);
                        } else {
                            XIV.getInstance().getListenerManager().remove(this);
                        }
                    }
                    break;
                case "pos2":
                    try {
                        if (arguments[2].equalsIgnoreCase("clear")) {
                            pos2 = null;
                        } else {
                            pos2 = new BlockPos(Integer.parseInt(arguments[2]), Integer.parseInt(arguments[3]), Integer.parseInt(arguments[4]));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        pos2 = new BlockPos(mc.thePlayer.getPosition());
                    } catch (NumberFormatException e) {
                        ChatLogger.print("Invalid arguments, valid: creativeworldedit pos2 <x> <y> <z>");
                    }

                    if (pos2 != null) {
                        ChatLogger.print("Second Position set to: " + pos2);

                        if (pos1 != null) {
                            XIV.getInstance().getListenerManager().add(this);
                        } else {
                            XIV.getInstance().getListenerManager().remove(this);
                        }
                    }
                    break;
                case "set":
                    if (arguments.length >= 3) {
                        placeBlocks(arguments[2]);
                    } else {
                        ChatLogger.print("Invalid arguments, valid: fillworldedit set <block>");
                    }
                    break;
                case "clear":
                    pos1 = null;
                    pos2 = null;
                    ChatLogger.print("First and Second Position cleared.");
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: pos1, pos2, set, clear");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: fillworldedit <action>");
        }
    }

    private void drawBoxes(double x, double y, double z, double xo, double yo, double zo) {
        double x1 = x;
        double y1 = y;
        double z1 = z;
        double x2 = xo;
        double y2 = yo;
        double z2 = zo;

        if (x1 > x2) {
            x1 += 1.0D;
        }

        if (x1 == x2) {
            x1 += 1.0D;
        }

        if (x2 > x1) {
            x2 += 1.0D;
        }

        if (y1 > y2) {
            y1 += 1.0D;
        }

        if (y1 == y2) {
            y1 += 1.0D;
        }

        if (y2 > y1) {
            y2 += 1.0D;
        }

        if (z1 > z2) {
            z1 += 1.0D;
        }

        if (z1 == z2) {
            z1 += 1.0D;
        }

        if (z2 > z1) {
            z2 += 1.0D;
        }

        AxisAlignedBB box = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);

        GlStateManager.color(0.7F, 0.4F, 1.0F, 0.6F);
        RenderUtils.drawLines(box);
        GlStateManager.color(0.7F, 0.4F, 1.0F, 0.6F);
        RenderGlobal.drawOutlinedBoundingBox(box, -1);
    }
}
