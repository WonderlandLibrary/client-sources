package pw.latematt.xiv.mod.mods.render;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.Render3DEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.EntityUtils;
import pw.latematt.xiv.utils.RenderUtils;
import pw.latematt.xiv.value.Value;

/**
 * @author Matthew
 */
public class StorageESP extends Mod implements Listener<Render3DEvent>, CommandHandler {
    public final Value<Boolean> chests = new Value<>("storage_esp_chests", true);
    public final Value<Boolean> enderChests = new Value<>("storage_esp_ender_chests", false);
    public final Value<Boolean> furnaces = new Value<>("storage_esp_furnaces", true);
    public final Value<Boolean> brewingStands = new Value<>("storage_esp_brewing_stands", false);
    public final Value<Boolean> hoppers = new Value<>("storage_esp_hoppers", false);
    public final Value<Boolean> droppers = new Value<>("storage_esp_droppers", false);
    public final Value<Boolean> dispensers = new Value<>("storage_esp_dispensers", false);
    public final Value<Boolean> commandBlocks = new Value<>("storage_esp_command_blocks", false);
    public final Value<Boolean> mobSpawners = new Value<>("storage_esp_mob_spawners", false);
    public final Value<Boolean> enchantmentTables = new Value<>("storage_esp_enchantment_tables", false);
    public final Value<Boolean> boxes = new Value<>("storage_esp_boxes", true);
    public final Value<Boolean> outline = new Value<>("storage_esp_outline", false);
    public final Value<Boolean> tracerLines = new Value<>("storage_esp_tracer_lines", false);

    public StorageESP() {
        super("StorageESP", ModType.RENDER);
        Command.newCommand().cmd("storageesp").description("Base command for Storage ESP mod.").arguments("<action>").aliases("storage", "sesp").handler(this).build();
    }

    @Override
    public void onEventCalled(Render3DEvent event) {
        RenderUtils.beginGl();
        for (Object o : mc.theWorld.loadedTileEntityList) {
            TileEntity tileEntity = (TileEntity) o;
            float partialTicks = event.getPartialTicks();
            Vec3d vec3d = new Vec3d(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ());
            final double x = vec3d.x - mc.getRenderManager().renderPosX;
            final double y = vec3d.y - mc.getRenderManager().renderPosY;
            final double z = vec3d.z - mc.getRenderManager().renderPosZ;

            if (tileEntity instanceof TileEntityChest && chests.getValue()) {
                TileEntityChest tileChest = (TileEntityChest) tileEntity;
                Block chest = mc.theWorld.getBlockState(tileChest.getPos()).getBlock();

                Block border = mc.theWorld.getBlockState(new BlockPos(tileChest.getPos().getX(), tileChest.getPos().getY(), tileChest.getPos().getZ() - 1)).getBlock();
                Block border2 = mc.theWorld.getBlockState(new BlockPos(tileChest.getPos().getX(), tileChest.getPos().getY(), tileChest.getPos().getZ() + 1)).getBlock();
                Block border3 = mc.theWorld.getBlockState(new BlockPos(tileChest.getPos().getX() - 1, tileChest.getPos().getY(), tileChest.getPos().getZ())).getBlock();
                Block border4 = mc.theWorld.getBlockState(new BlockPos(tileChest.getPos().getX() + 1, tileChest.getPos().getY(), tileChest.getPos().getZ())).getBlock();
                if (chest == Blocks.chest) {
                    if (border != Blocks.chest) {
                        if (border2 == Blocks.chest)
                            draw(tileChest.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 2.0D, partialTicks);
                        else if (border4 == Blocks.chest)
                            draw(tileChest.getBlockType(), x, y, z, x + 2.0D, y + 1.0D, z + 1.0D, partialTicks);
                        else if (border4 == Blocks.chest)
                            draw(tileChest.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
                        else if ((border != Blocks.chest) && (border2 != Blocks.chest) && (border3 != Blocks.chest) && (border4 != Blocks.chest))
                            draw(tileChest.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
                    }
                } else if ((chest == Blocks.trapped_chest) && (border != Blocks.trapped_chest)) {
                    if (border2 == Blocks.trapped_chest)
                        draw(tileChest.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 2.0D, partialTicks);
                    else if (border4 == Blocks.trapped_chest)
                        draw(tileChest.getBlockType(), x, y, z, x + 2.0D, y + 1.0D, z + 1.0D, partialTicks);
                    else if (border4 == Blocks.trapped_chest)
                        draw(tileChest.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
                    else if ((border != Blocks.trapped_chest) && (border2 != Blocks.trapped_chest) && (border3 != Blocks.trapped_chest) && (border4 != Blocks.trapped_chest))
                        draw(tileChest.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
                }
            } else if (tileEntity instanceof TileEntityEnderChest && enderChests.getValue())
                draw(tileEntity.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
            else if (tileEntity instanceof TileEntityFurnace && furnaces.getValue())
                draw(tileEntity.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
            else if (tileEntity instanceof TileEntityBrewingStand && brewingStands.getValue())
                draw(tileEntity.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
            else if (tileEntity instanceof TileEntityHopper && hoppers.getValue())
                draw(tileEntity.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
            else if (tileEntity instanceof TileEntityDropper && droppers.getValue())
                draw(tileEntity.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
            else if (tileEntity instanceof TileEntityDispenser && dispensers.getValue())
                draw(tileEntity.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
            else if (tileEntity instanceof TileEntityCommandBlock && commandBlocks.getValue())
                draw(tileEntity.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
            else if (tileEntity instanceof TileEntityMobSpawner && mobSpawners.getValue())
                draw(tileEntity.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
            else if (tileEntity instanceof TileEntityEnchantmentTable && enchantmentTables.getValue())
                draw(tileEntity.getBlockType(), x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, partialTicks);
        }
        RenderUtils.endGl();
    }

    private void draw(Block block, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float partialTicks) {
        float[] color = new float[]{1.0F, 1.0F, 1.0F};
        if (block == Blocks.ender_chest) {
            color = new float[]{0.4F, 0.2F, 1.0F};

            minX += 0.05;
            minZ += 0.05;

            maxX -= 0.05;
            maxY -= 0.1;
            maxZ -= 0.05;
        } else if (block == Blocks.chest) {
            color = new float[]{1.0F, 1.0F, 0.0F};

            minX += 0.05;
            minZ += 0.05;

            maxX -= 0.05;
            maxY -= 0.1;
            maxZ -= 0.05;
        } else if (block == Blocks.trapped_chest) {
            color = new float[]{1.0F, 0.6F, 0.0F};

            minX += 0.05;
            minZ += 0.05;

            maxX -= 0.05;
            maxY -= 0.1;
            maxZ -= 0.05;
        } else if (block == Blocks.brewing_stand) {
            color = new float[]{1.0F, 0.3F, 0.3F};

            minX += 0.1;
            minZ += 0.05;

            maxX -= 0.05;
            maxY -= 0.85;
            maxZ -= 0.05;
        } else if (block == Blocks.furnace) {
            color = new float[]{0.6F, 0.6F, 0.6F};
        } else if (block == Blocks.lit_furnace) {
            color = new float[]{0.2F, 0.6F, 0.6F};
        } else if ((block == Blocks.dropper) || (block == Blocks.dispenser)) {
            color = new float[]{0.4F, 0.4F, 0.4F};
        } else if ((block == Blocks.hopper)) {
            color = new float[]{0.4F, 0.4F, 0.4F};

            minY += 0.625;
        } else if ((block == Blocks.mob_spawner)) {
            color = new float[]{1.0F, 0.2F, 0.2F};
        } else if ((block == Blocks.command_block)) {
            color = new float[]{0.0F, 0.9F, 0.5F};
        } else if ((block == Blocks.enchanting_table)) {
            color = new float[]{0.4F, 0.0F, 1.0F};

            maxY -= 0.25;
        }

        AxisAlignedBB bb = AxisAlignedBB.fromBounds(minX, minY, minZ, maxX, maxY, maxZ);
        if (boxes.getValue()) {
            drawBoxes(bb, color);
        }

        if (outline.getValue()) {
            drawOutline(bb, color);
        }

        if (tracerLines.getValue()) {
            drawTracerLines(minX, minY, minZ, maxX, maxY, maxZ, partialTicks, color);
        }
    }

    private void drawOutline(AxisAlignedBB bb, float[] color) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.0F);
        RenderUtils.renderOne();
        GlStateManager.color(color[0], color[1], color[2], 0.66F);
        RenderUtils.drawFilledBox(bb);
        RenderUtils.renderTwo();
        GlStateManager.color(color[0], color[1], color[2], 0.66F);
        RenderUtils.drawFilledBox(bb);
        RenderUtils.renderThree();
        GlStateManager.color(color[0], color[1], color[2], 0.66F);
        RenderUtils.drawFilledBox(bb);
        RenderUtils.renderFour();
        GlStateManager.color(color[0], color[1], color[2], 0.66F);
        RenderUtils.drawFilledBox(bb);
        RenderUtils.renderFive();
        GlStateManager.color(color[0], color[1], color[2], 0.66F);
        RenderUtils.drawFilledBox(bb);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.0F);
    }

    private void drawBoxes(AxisAlignedBB bb, float[] color) {
        GlStateManager.color(color[0], color[1], color[2], 0.60F);
        RenderUtils.drawLines(bb);
        RenderGlobal.drawOutlinedBoundingBox(bb, -1);
        GlStateManager.color(color[0], color[1], color[2], 0.11F);
        RenderUtils.drawFilledBox(bb);
    }

    private void drawTracerLines(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float partialTicks, float[] color) {
        GlStateManager.color(color[0], color[1], color[2], 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        mc.entityRenderer.orientCamera(partialTicks);

        double x = EntityUtils.isReferenceSet() ? EntityUtils.getReference().lastTickPosX + (EntityUtils.getReference().posX - EntityUtils.getReference().lastTickPosX) * partialTicks - mc.getRenderManager().renderPosX : 0;
        double y = EntityUtils.isReferenceSet() ? EntityUtils.getReference().lastTickPosY + (EntityUtils.getReference().posY - EntityUtils.getReference().lastTickPosY) * partialTicks - mc.getRenderManager().renderPosY : 0;
        double z = EntityUtils.isReferenceSet() ? EntityUtils.getReference().lastTickPosZ + (EntityUtils.getReference().posZ - EntityUtils.getReference().lastTickPosZ) * partialTicks - mc.getRenderManager().renderPosZ : 0;

        Tessellator var2 = Tessellator.getInstance();
        WorldRenderer var3 = var2.getWorldRenderer();
        var3.startDrawing(2);
        var3.addVertex(x, y + mc.thePlayer.getEyeHeight(), z);
        var3.addVertex(maxX - ((maxX - minX) / 2), maxY - ((maxY - minY) / 2), maxZ - ((maxZ - minZ) / 2));
        var2.draw();

        GlStateManager.popMatrix();
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "chests":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            chests.setValue(chests.getDefault());
                        } else {
                            chests.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        chests.setValue(!chests.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s show Chests.", chests.getValue() ? "now" : "no longer"));
                    break;
                case "enderchests":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            enderChests.setValue(enderChests.getDefault());
                        } else {
                            enderChests.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        enderChests.setValue(!enderChests.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s show Ender Chests.", enderChests.getValue() ? "now" : "no longer"));
                    break;
                case "furnaces":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            furnaces.setValue(furnaces.getDefault());
                        } else {
                            furnaces.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        furnaces.setValue(!furnaces.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s show Furnaces.", furnaces.getValue() ? "now" : "no longer"));
                    break;
                case "brewingstands":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            brewingStands.setValue(brewingStands.getDefault());
                        } else {
                            brewingStands.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        brewingStands.setValue(!brewingStands.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s show Brewing Stands.", brewingStands.getValue() ? "now" : "no longer"));
                    break;
                case "hoppers":
                    if (arguments.length >= 3) {
                        hoppers.setValue(Boolean.parseBoolean(arguments[2]));
                    } else {
                        hoppers.setValue(!hoppers.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s show Hoppers.", hoppers.getValue() ? "now" : "no longer"));
                    break;
                case "droppers":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            droppers.setValue(droppers.getDefault());
                        } else {
                            droppers.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        droppers.setValue(!droppers.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s show Droppers.", droppers.getValue() ? "now" : "no longer"));
                    break;
                case "dispensers":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            dispensers.setValue(dispensers.getDefault());
                        } else {
                            dispensers.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        dispensers.setValue(!dispensers.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s show Dispensers.", dispensers.getValue() ? "now" : "no longer"));
                    break;
                case "commandblocks":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            commandBlocks.setValue(commandBlocks.getDefault());
                        } else {
                            commandBlocks.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        commandBlocks.setValue(!commandBlocks.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s show Command Blocks.", commandBlocks.getValue() ? "now" : "no longer"));
                    break;
                case "mobspawners":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            mobSpawners.setValue(mobSpawners.getDefault());
                        } else {
                            mobSpawners.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        mobSpawners.setValue(!mobSpawners.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s show Mob Spawners.", mobSpawners.getValue() ? "now" : "no longer"));
                    break;
                case "enchantmenttables":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            enchantmentTables.setValue(enchantmentTables.getDefault());
                        } else {
                            enchantmentTables.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        enchantmentTables.setValue(!enchantmentTables.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s show Enchantment Tables.", enchantmentTables.getValue() ? "now" : "no longer"));
                    break;
                case "boxes":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            boxes.setValue(boxes.getDefault());
                        } else {
                            boxes.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        boxes.setValue(!boxes.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s render boxes.", boxes.getValue() ? "now" : "no longer"));
                    break;
                case "outline":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            outline.setValue(outline.getDefault());
                        } else {
                            outline.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        outline.setValue(!outline.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s render an outline.", outline.getValue() ? "now" : "no longer"));
                    break;
                case "tracerlines":
                case "tracers":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            tracerLines.setValue(tracerLines.getDefault());
                        } else {
                            tracerLines.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        tracerLines.setValue(!tracerLines.getValue());
                    }
                    ChatLogger.print(String.format("StorageESP will %s render tracer lines.", tracerLines.getValue() ? "now" : "no longer"));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: chests, enderchests, furnaces, brewingstands, hoppers, droppers, dispensers, commandblocks, mobspawners, enchantmenttables, boxes, tracerlines");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: storageesp <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}
