package pw.latematt.xiv.mod.mods.movement;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.BlockReachEvent;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.event.events.Render3DEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.mod.mods.render.Freecam;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.EntityUtils;
import pw.latematt.xiv.utils.RenderUtils;
import pw.latematt.xiv.value.ClampedValue;

/**
 * @author Klintos
 * @author Rederpz
 */
public class ClickTeleport extends Mod implements Listener<MotionUpdateEvent>, CommandHandler {
    private final ClampedValue<Double> range = new ClampedValue<>("clickteleport_range", 35.0, 15.0, 120.0);

    private BlockPos teleportPosition;
    private boolean canDraw;
    private int delay = 0;

    private Listener blockReachListener, renderListener, sendPacketListener;

    public ClickTeleport() {
        super("Click Teleport", ModType.MOVEMENT, Keyboard.KEY_NONE, 0xFFFF697C);

        Command.newCommand().cmd("clickteleport").description("Base command for the Click Teleport mod.").aliases("clicktp", "cteleport", "ct").arguments("<action>").handler(this).build();

        blockReachListener = new Listener<BlockReachEvent>() {
            @Override
            public void onEventCalled(BlockReachEvent event) {
                if ((!Mouse.isButtonDown(0) && mc.inGameHasFocus || !mc.inGameHasFocus) && mc.thePlayer.getItemInUse() == null) {
                    event.setRange(range.getValue().floatValue() + 1);

                    canDraw = true;
                } else {
                    canDraw = false;
                }
            }
        };

        sendPacketListener = new Listener<SendPacketEvent>() {
            @Override
            public void onEventCalled(SendPacketEvent event) {
                if (event.getPacket() instanceof C08PacketPlayerBlockPlacement && (canDraw)) {
                    event.setCancelled(true);
                }
            }
        };

        renderListener = new Listener<Render3DEvent>() {
            @Override
            public void onEventCalled(Render3DEvent event) {
                if (mc.objectMouseOver != null && mc.objectMouseOver.func_178782_a() != null && canDraw) {
                    for (float offset = -2.0F; offset < 18.0F; offset++) {
                        double[] mouseOverPos = new double[]{mc.objectMouseOver.func_178782_a().getX(), mc.objectMouseOver.func_178782_a().getY() + offset, mc.objectMouseOver.func_178782_a().getZ()};

                        BlockPos blockBelowPos = new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]);
                        Block blockBelow = mc.theWorld.getBlockState(blockBelowPos).getBlock();

                        if (canRenderBox(mouseOverPos)) {
                            RenderUtils.beginGl();
                            drawBox(blockBelow, new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]));
                            drawNametags(blockBelow, new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]));
                            RenderUtils.endGl();

                            if (mc.inGameHasFocus) {
                                teleportPosition = blockBelowPos;
                                break;
                            } else {
                                teleportPosition = null;
                            }
                        }
                    }
                } else if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
                    for (float offset = -2.0F; offset < 18.0F; offset++) {
                        double[] mouseOverPos = new double[]{mc.objectMouseOver.entityHit.posX, mc.objectMouseOver.entityHit.posY + offset, mc.objectMouseOver.entityHit.posZ};

                        BlockPos blockBelowPos = new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]);
                        Block blockBelow = mc.theWorld.getBlockState(blockBelowPos).getBlock();

                        if (canRenderBox(mouseOverPos)) {
                            RenderUtils.beginGl();
                            drawBox(blockBelow, new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]));
                            drawNametags(blockBelow, new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]));
                            RenderUtils.endGl();

                            if (mc.inGameHasFocus) {
                                teleportPosition = blockBelowPos;
                                break;
                            } else {
                                teleportPosition = null;
                            }
                        }
                    }
                } else {
                    teleportPosition = null;
                }
            }
        };
    }

    public boolean canRenderBox(double[] mouseOverPos) {
        boolean canTeleport = false;

        Block blockBelowPos = mc.theWorld.getBlockState(new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0F, mouseOverPos[2])).getBlock();
        Block blockPos = mc.theWorld.getBlockState(new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2])).getBlock();
        Block blockAbovePos = mc.theWorld.getBlockState(new BlockPos(mouseOverPos[0], mouseOverPos[1] + 1.0F, mouseOverPos[2])).getBlock();

        boolean validBlockBelow = blockBelowPos.getCollisionBoundingBox(mc.theWorld, new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0F, mouseOverPos[2]), mc.theWorld.getBlockState(new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0F, mouseOverPos[2]))) != null;
        boolean validBlock = isValidBlock(blockPos);
        boolean validBlockAbove = isValidBlock(blockAbovePos);

        if ((validBlockBelow && validBlock && validBlockAbove)) {
            canTeleport = true;
        }

        return canTeleport;
    }

    public double getOffset(Block block, BlockPos pos) {
        IBlockState state = mc.theWorld.getBlockState(pos);

        double offset = 0;

        if (block instanceof BlockSlab && !((BlockSlab) block).isDouble()) {
            offset -= 0.5F;
        } else if (block instanceof BlockEndPortalFrame) {
            offset -= 0.2F;
        } else if (block instanceof BlockBed) {
            offset -= 0.44F;
        } else if (block instanceof BlockCake) {
            offset -= 0.5F;
        } else if (block instanceof BlockDaylightDetector) {
            offset -= 0.625F;
        } else if (block instanceof BlockRedstoneComparator || block instanceof BlockRedstoneRepeater) {
            offset -= 0.875F;
        } else if (block instanceof BlockChest || block == Blocks.ender_chest) {
            offset -= 0.125F;
        } else if (block == Blocks.snow_layer) {
            offset -= 0.875F;
            offset += 0.125F * (((Integer) state.getValue(BlockSnow.LAYERS_PROP)).intValue() - 1);
        } else if (isValidBlock(block)) {
            offset -= 1.0F;
        }

        return offset;
    }

    public boolean isValidBlock(Block block) {
        return block == Blocks.portal || block == Blocks.snow_layer || block instanceof BlockTripWireHook || block instanceof BlockTripWire || block instanceof BlockDaylightDetector || block instanceof BlockRedstoneComparator || block instanceof BlockRedstoneRepeater || block instanceof BlockSign || block instanceof BlockAir || block instanceof BlockPressurePlate || block instanceof BlockTallGrass || block instanceof BlockFlower || block instanceof BlockMushroom || block instanceof BlockDoublePlant || block instanceof BlockReed || block instanceof BlockSapling || block == Blocks.carrots || block == Blocks.wheat || block == Blocks.nether_wart || block == Blocks.potatoes || block == Blocks.pumpkin_stem || block == Blocks.melon_stem || block == Blocks.heavy_weighted_pressure_plate || block == Blocks.light_weighted_pressure_plate || block == Blocks.redstone_wire || block instanceof BlockTorch || block instanceof BlockRedstoneTorch || block == Blocks.lever || block instanceof BlockButton;
    }

    private void drawNametags(Block block, BlockPos pos) {
        BlockPos blockPosBelow = new BlockPos(pos.getX(), pos.getY() - 1.0F, pos.getZ());
        Block blockBelow = mc.theWorld.getBlockState(blockPosBelow).getBlock();
        double offset = getOffset(blockBelow, blockPosBelow);

        double x = pos.getX() + 0.5F - mc.getRenderManager().renderPosX;
        double y = pos.getY() - 1.0F - mc.getRenderManager().renderPosY + offset;
        double z = pos.getZ() + 0.5F - mc.getRenderManager().renderPosZ;
        double dist = EntityUtils.getReference().getDistance(pos.getX(), pos.getY(), pos.getZ());
        final String text = Math.round(dist) + "m";
        double far = this.mc.gameSettings.renderDistanceChunks * 12.8D;
        double dl = Math.sqrt(x * x + z * z + y * y);
        double d;

        if (dl > far) {
            d = far / dl;
            dist *= d;
            x *= d;
            y *= d;
            z *= d;
        }

        float var13 = 2.5F + ((float) dist / 5 <= 2 ? 2.0F : (float) dist / 5) * RenderUtils.getNametagSize().getValue();
        float var14 = 0.016666668F * var13;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + 1.5F, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        if (mc.gameSettings.thirdPersonView == 2) {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, -1.0F, 0.0F, 0.0F);
        } else {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        }
        GlStateManager.scale(-var14, -var14, var14);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GlStateManager.func_179090_x();
        worldRenderer.startDrawingQuads();
        int var18 = mc.fontRendererObj.getStringWidth(text) / 2;
        worldRenderer.func_178960_a(0.0F, 0.0F, 0.0F, RenderUtils.getNametagOpacity().getValue());
        worldRenderer.addVertex(-var18 - 2, -2, 0.0D);
        worldRenderer.addVertex(-var18 - 2, 9, 0.0D);
        worldRenderer.addVertex(var18 + 2, 9, 0.0D);
        worldRenderer.addVertex(var18 + 2, -2, 0.0D);
        tessellator.draw();
        GlStateManager.func_179098_w();
        mc.fontRendererObj.drawStringWithShadow(text, -var18, 0, 0xFFFFFFFF);
        GlStateManager.popMatrix();
    }

    private void drawBox(Block block, BlockPos pos) {
        double x = pos.getX() - mc.getRenderManager().renderPosX;
        double y = pos.getY() - mc.getRenderManager().renderPosY;
        double z = pos.getZ() - mc.getRenderManager().renderPosZ;

        BlockPos blockPosBelow = new BlockPos(pos.getX(), pos.getY() - 1.0F, pos.getZ());
        Block blockBelow = mc.theWorld.getBlockState(blockPosBelow).getBlock();

        double offset = getOffset(blockBelow, blockPosBelow);

        AxisAlignedBB box = AxisAlignedBB.fromBounds(x, y + offset, z, x + 1, y + offset + 0.06F, z + 1);
        final int color = this.getColor();
        final float red = (color >> 16 & 0xFF) / 255.0F;
        final float green = (color >> 8 & 0xFF) / 255.0F;
        final float blue = (color & 0xFF) / 255.0F;
        GlStateManager.color(red, green, blue, 0.11F);
        RenderUtils.drawFilledBox(box);
        GlStateManager.color(red, green, blue, 0.60F);
        RenderUtils.drawLines(box);
        RenderGlobal.drawOutlinedBoundingBox(box, -1);
    }

    public void onEventCalled(MotionUpdateEvent event) {
        if (event.getCurrentState() == MotionUpdateEvent.State.PRE) {
            if (teleportPosition != null && delay == 0 && Mouse.isButtonDown(1)) {
                double[] playerPosition = new double[]{EntityUtils.getReference().posX, EntityUtils.getReference().posY, EntityUtils.getReference().posZ};
                double[] blockPosition = new double[]{teleportPosition.getX() + 0.5F, teleportPosition.getY() + getOffset(mc.theWorld.getBlockState(teleportPosition).getBlock(), teleportPosition) + 1.0F, teleportPosition.getZ() + 0.5F};

                Freecam freecam = (Freecam) XIV.getInstance().getModManager().find("freecam");

                if (freecam != null && freecam.isEnabled()) {
                    freecam.setEnabled(false);
                }


                EntityUtils.teleportToPosition(playerPosition, blockPosition, 0.25D, 0.0D, true, true);
                mc.thePlayer.setPosition(blockPosition[0], blockPosition[1], blockPosition[2]);

                teleportPosition = null;
                delay = 5;
            }

            if (delay > 0) {
                delay--;
            }
        }
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this, renderListener, blockReachListener, sendPacketListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this, renderListener, blockReachListener, sendPacketListener);
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "range":
                case "reach":
                    if (arguments.length >= 3) {
                        String newRangeString = arguments[2];
                        try {
                            double newRange = arguments[2].equalsIgnoreCase("-d") ? range.getDefault() : Double.parseDouble(newRangeString);

                            range.setValue(newRange);
                            if (range.getValue() > range.getMax())
                                range.setValue(range.getMax());
                            else if (range.getValue() < range.getMin())
                                range.setValue(range.getMin());

                            ChatLogger.print(String.format("Click Teleport Range set to %s", range.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newRangeString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: clickteleport range <number>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: range");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: clickteleport <action>");
        }
    }
}
