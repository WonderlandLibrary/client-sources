package pw.latematt.xiv.mod.mods.render;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.BlockModelRenderEvent;
import pw.latematt.xiv.event.events.Render3DEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.RenderUtils;
import pw.latematt.xiv.value.ClampedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;

/**
 * @author Matthew
 */
public class Search extends Mod implements Listener<Render3DEvent>, CommandHandler {
    private final Listener blockModelRenderListener;
    private final List<Block> blockList = new ArrayList<>();
    private final List<Vec3d> blockCache = new CopyOnWriteArrayList<>();
    private final ClampedValue<Float> range = new ClampedValue<>("search_range", 128.0F, 5.0F, 500.0F);

    public Search() {
        super("Search", ModType.RENDER);
        Command.newCommand().cmd("search").description("Base command for the Search mod.").arguments("<action>").handler(this).build();

        blockModelRenderListener = new Listener<BlockModelRenderEvent>() {
            @Override
            public void onEventCalled(BlockModelRenderEvent event) {
                Vec3d blockPos = new Vec3d(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
                if (blockList.contains(event.getBlock()) && !isCached(blockPos)) {
                    blockCache.add(blockPos);
                }
            }
        };
        setEnabled(true);
    }

    private boolean isCached(Vec3d blockPos) {
        for (Vec3d vec3d : blockCache) {
            if (vec3d.x == blockPos.x && vec3d.y == blockPos.y && vec3d.z == blockPos.z) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onEventCalled(Render3DEvent event) {
        RenderUtils.beginGl();
        for (Vec3d vec3d : blockCache) {
            if (mc.thePlayer.getDistance(vec3d.x, vec3d.y, vec3d.z) > range.getValue()) {
                blockCache.remove(vec3d);
                continue;
            }
            Block block = mc.theWorld.getBlockState(new BlockPos(vec3d.x, vec3d.y, vec3d.z)).getBlock();

            renderBox(block, vec3d);
        }
        RenderUtils.endGl();
    }

    private int getBlockColor(Block block) {
        int color = block.getMaterial().getMaterialMapColor().colorValue;
        switch (Block.getIdFromBlock(block)) {
            case 56:
            case 57:
            case 116:
                color = 6155509;
                break;
            case 129:
            case 133:
                color = 1564002;
                break;
            case 14:
            case 41:
                color = 16576075;
                break;
            case 16:
            case 173:
                color = 3618615;
                break;
            case 21:
            case 22:
                color = 1525445;
                break;
            case 73:
            case 74:
            case 152:
                color = 16711680;
                break;
            case 61:
            case 62:
                color = 16658167;
                break;
            case 49:
                color = 3944534;
                break;
            case 146:
                color = 13474867;
                break;
            case 54:
                color = 0xFFBF00;
                break;
            case 130:
                color = 14614999;
                break;
        }
        return color == 0 ? 1216104 : color;
    }

    private void renderBox(Block block, Vec3d vec3d) {
        if (!blockList.contains(block))
            return;
        double x = vec3d.x - mc.getRenderManager().renderPosX;
        double y = vec3d.y - mc.getRenderManager().renderPosY;
        double z = vec3d.z - mc.getRenderManager().renderPosZ;
        AxisAlignedBB box = AxisAlignedBB.fromBounds(x, y, z, x + 1, y + 1, z + 1);
        final int color = getBlockColor(block);
        final float red = (color >> 16 & 0xFF) / 255.0F;
        final float green = (color >> 8 & 0xFF) / 255.0F;
        final float blue = (color & 0xFF) / 255.0F;
        GlStateManager.color(red, green, blue, 0.11F);
        RenderUtils.drawFilledBox(box);
        GlStateManager.color(red, green, blue, 0.60F);
        RenderUtils.drawLines(box);
        RenderGlobal.drawOutlinedBoundingBox(box, -1);
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String mode = arguments[1];
            switch (mode) {
                case "add":
                    if (arguments.length >= 3) {
                        String input = message.substring((String.format("%s %s ", arguments[0], arguments[1])).length());
                        Block block = null;
                        try {
                            block = Block.getBlockById(Integer.parseInt(input));
                        } catch (NumberFormatException e) {
                            for (Object o : Block.blockRegistry) {
                                Block blockk = (Block) o;
                                String name = Block.blockRegistry.getNameForObject(blockk).toString().replaceAll(Matcher.quoteReplacement("minecraft:"), "");
                                if (name.toLowerCase().startsWith(input.toLowerCase())) {
                                    block = blockk;
                                    break;
                                }
                            }
                        }

                        if (block == null) {
                            ChatLogger.print(String.format("Block \"%s\" not found.", input));
                            return;
                        }

                        if (!blockList.contains(block)) {
                            blockList.add(block);
                            blockCache.clear();
                        }

                        mc.renderGlobal.loadRenderers();
                        ChatLogger.print(String.format("Block \"%s\" added.", block.getLocalizedName()));
                    } else {
                        ChatLogger.print("Invalid arguments, valid: search add <block name/id>");
                    }
                    break;
                case "del":
                    if (arguments.length >= 3) {
                        String input = message.substring((String.format("%s %s ", arguments[0], arguments[1])).length());
                        Block block = null;
                        try {
                            block = Block.getBlockById(Integer.parseInt(input));
                        } catch (NumberFormatException e) {
                            for (Object o : Block.blockRegistry) {
                                Block blockk = (Block) o;
                                String name = Block.blockRegistry.getNameForObject(blockk).toString().replaceAll(Matcher.quoteReplacement("minecraft:"), "");
                                if (name.toLowerCase().startsWith(input.toLowerCase())) {
                                    block = blockk;
                                    break;
                                }
                            }
                        }

                        if (block == null) {
                            ChatLogger.print(String.format("Block \"%s\" not found.", input));
                            return;
                        }

                        if (blockList.contains(block)) {
                            blockList.remove(block);
                            blockCache.clear();
                        }

                        mc.renderGlobal.loadRenderers();
                        ChatLogger.print(String.format("Block \"%s\" removed.", block.getLocalizedName()));
                    } else {
                        ChatLogger.print("Invalid arguments, valid: search del <block name/id>");
                    }
                    break;
                case "range":
                case "r":
                    if (arguments.length >= 3) {
                        String newRangeString = arguments[2];
                        try {
                            float newRange = arguments[2].equalsIgnoreCase("-d") ? range.getDefault() : Float.parseFloat(newRangeString);
                            range.setValue(newRange);
                            if (range.getValue() > range.getMax())
                                range.setValue(range.getMax());
                            else if (range.getValue() < range.getMin())
                                range.setValue(range.getMin());

                            ChatLogger.print(String.format("Search Range set to %s", range.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newRangeString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: search range <number>");
                    }
                    break;
                case "clear":
                    blockList.clear();
                    blockCache.clear();
                    mc.renderGlobal.loadRenderers();
                    ChatLogger.print("Search cleared.");
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: add, del, range, clear");
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: search <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this, blockModelRenderListener);
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this, blockModelRenderListener);
    }
}
