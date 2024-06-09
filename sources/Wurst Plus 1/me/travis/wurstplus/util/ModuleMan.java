package me.travis.wurstplus.util;

import me.travis.wurstplus.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;

// made by travis -_-

public class ModuleMan {

    public Integer totems;
    private String holeType;
    private BlockPos pos;

    public ModuleMan() {
        this.holeType = "\u00A74 0";
        getPlayerPos();
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

        if (getPlayerPos()) {
            return "\u00A74 0";
        }

        getPlayerPos();

        if ((Minecraft.getMinecraft().world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK)
                && (Minecraft.getMinecraft().world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK)
                && (Minecraft.getMinecraft().world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK)
                && (Minecraft.getMinecraft().world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK)
                && (Minecraft.getMinecraft().world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK)) {
            this.holeType = "\u00A7a Safe";
            return this.holeType;
        }

        else if ((Minecraft.getMinecraft().world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK
                | Minecraft.getMinecraft().world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN)
                && (Minecraft.getMinecraft().world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK
                        | Minecraft.getMinecraft().world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN)
                && (Minecraft.getMinecraft().world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK
                        | Minecraft.getMinecraft().world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN)
                && (Minecraft.getMinecraft().world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK
                        | Minecraft.getMinecraft().world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN)
                && (Minecraft.getMinecraft().world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK
                        | Minecraft.getMinecraft().world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN)) {
            this.holeType = "\u00A73 Unsafe";
            return this.holeType;
        }

        else {
            this.holeType = "\u00A74 None";
            return this.holeType;
        }
    }

    public String isAura() {
        try {
            if (ModuleManager.getModuleByName("Travis Aura").isEnabled()) {
                return "\u00A7a 1";
            }
            return "\u00A74 0";
        } catch (Exception e) {
            return "lack of games: "+e;
        }
    }

    public String isTrap() {
        try {
            if (ModuleManager.getModuleByName("AutoTrap").isEnabled()) {
                return "\u00A7a 1";
            }
            return "\u00A74 0";
        } catch (Exception e) {
            return "lack of games: "+e;
        }
    }

    public String isSurround() {
        try {
            if (ModuleManager.getModuleByName("Surround").isEnabled()) {
                return "\u00A7a 1";
            }
            return "\u00A74 0";
        } catch (Exception e) {
            return "lack of games: "+e;
        }
    }

    public String isFill() {
        try {
            if (ModuleManager.getModuleByName("HoleFill").isEnabled()) {
                return "\u00A7a 1";
            }
            return "\u00A74 0";
        } catch (Exception e) {
            return "lack of games: "+e;
        }
    }

    public int getTotemsInt() {
        return offhand() +  Minecraft.getMinecraft().player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
    }

    public String getTotems() {
        try {
            totems = offhand() + Minecraft.getMinecraft().player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();

            if (totems > 1) {
                return "\u00A7a "+totems;
            } else {
                return "\u00A74 "+totems;
            }
            
        } catch (Exception e) {
            return "0";
        }
    }

    public Integer offhand() {
        if (Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            return 1;
        }
        return 0;
    }

}