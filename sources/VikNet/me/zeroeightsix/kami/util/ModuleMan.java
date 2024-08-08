package me.zeroeightsix.kami.util;

import java.util.function.ToIntFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class ModuleMan {
    public Integer totems;
    private String holeType;
    private BlockPos pos;

    public ModuleMan() {
        this.holeType = "§4 0";
        this.getPlayerPos();
    }

    public Boolean getPlayerPos() {
        try {
            this.pos = new BlockPos(Math.floor(Minecraft.getMinecraft().player.posX), Math.floor(Minecraft.getMinecraft().player.posY), Math.floor(Minecraft.getMinecraft().player.posZ));
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public String getHoleType() {
        if (this.getPlayerPos()) {
            return "§4 0";
        }
        this.getPlayerPos();
        if (Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK && Minecraft.getMinecraft().world.getBlockState(this.pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK && Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK && Minecraft.getMinecraft().world.getBlockState(this.pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK && Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) {
            return this.holeType = "\u00A7a Safe";
        }
        if ((Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK | Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN) && (Minecraft.getMinecraft().world.getBlockState(this.pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK | Minecraft.getMinecraft().world.getBlockState(this.pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK | Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) && (Minecraft.getMinecraft().world.getBlockState(this.pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK | Minecraft.getMinecraft().world.getBlockState(this.pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK | Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN)) {
            return this.holeType = "\u00A73 Unsafe";
        }
        return this.holeType = "\u00A74 None";
    }
}
  