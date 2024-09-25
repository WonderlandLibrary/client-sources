package eze.modules.player;

import eze.modules.*;
import eze.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.*;
import eze.events.*;

public class Scaffold extends Module
{
    Timer timer;
    private BlockInfo data;
    private int slot;
    private int newSlot;
    private int oldSlot;
    private int block;
    private static List<Block> blockBlacklist;
    
    static {
        Scaffold.blockBlacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.tnt, Blocks.chest, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.tnt, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.sand, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.enchanting_table, Blocks.red_flower, Blocks.double_plant, Blocks.yellow_flower);
    }
    
    public float[] getRotations(final Entity e) {
        final double deltaX = e.posX + (e.posX - e.lastTickPosX) - this.mc.thePlayer.posX;
        final double deltaY = e.posY - 3.5 + e.getEyeHeight() - this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight();
        final double deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - this.mc.thePlayer.posZ;
        final double distance = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaZ, 2.0));
        float yaw = (float)Math.toDegrees(-Math.atan(deltaX / deltaZ));
        final float pitch = (float)(-Math.toDegrees(Math.atan(deltaY / distance)));
        if (deltaX < 0.0 && deltaZ < 0.0) {
            yaw = (float)(90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        else if (deltaX > 0.0 && deltaZ < 0.0) {
            yaw = (float)(-90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        return new float[] { yaw, pitch };
    }
    
    public Scaffold() {
        super("Scaffold", 0, Category.PLAYER);
        this.timer = new Timer();
    }
    
    @Override
    public void onEvent(final Event event) {
        throw new Error("Unresolved compilation problem: \n\trotationPitchHead cannot be resolved or is not a field\n");
    }
}
