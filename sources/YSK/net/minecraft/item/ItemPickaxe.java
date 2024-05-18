package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;

public class ItemPickaxe extends ItemTool
{
    private static final Set<Block> EFFECTIVE_ON;
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        int n;
        if (block == Blocks.obsidian) {
            if (this.toolMaterial.getHarvestLevel() == "   ".length()) {
                n = " ".length();
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
        }
        else if (block != Blocks.diamond_block && block != Blocks.diamond_ore) {
            if (block != Blocks.emerald_ore && block != Blocks.emerald_block) {
                if (block != Blocks.gold_block && block != Blocks.gold_ore) {
                    if (block != Blocks.iron_block && block != Blocks.iron_ore) {
                        if (block != Blocks.lapis_block && block != Blocks.lapis_ore) {
                            if (block != Blocks.redstone_ore && block != Blocks.lit_redstone_ore) {
                                if (block.getMaterial() == Material.rock) {
                                    n = " ".length();
                                    "".length();
                                    if (1 < 1) {
                                        throw null;
                                    }
                                }
                                else if (block.getMaterial() == Material.iron) {
                                    n = " ".length();
                                    "".length();
                                    if (3 <= 0) {
                                        throw null;
                                    }
                                }
                                else if (block.getMaterial() == Material.anvil) {
                                    n = " ".length();
                                    "".length();
                                    if (4 <= 2) {
                                        throw null;
                                    }
                                }
                                else {
                                    n = "".length();
                                    "".length();
                                    if (2 <= 0) {
                                        throw null;
                                    }
                                }
                            }
                            else if (this.toolMaterial.getHarvestLevel() >= "  ".length()) {
                                n = " ".length();
                                "".length();
                                if (3 != 3) {
                                    throw null;
                                }
                            }
                            else {
                                n = "".length();
                                "".length();
                                if (true != true) {
                                    throw null;
                                }
                            }
                        }
                        else if (this.toolMaterial.getHarvestLevel() >= " ".length()) {
                            n = " ".length();
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                        else {
                            n = "".length();
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                    }
                    else if (this.toolMaterial.getHarvestLevel() >= " ".length()) {
                        n = " ".length();
                        "".length();
                        if (2 == 1) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                }
                else if (this.toolMaterial.getHarvestLevel() >= "  ".length()) {
                    n = " ".length();
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                    "".length();
                    if (4 == -1) {
                        throw null;
                    }
                }
            }
            else if (this.toolMaterial.getHarvestLevel() >= "  ".length()) {
                n = " ".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
        }
        else if (this.toolMaterial.getHarvestLevel() >= "  ".length()) {
            n = " ".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    protected ItemPickaxe(final ToolMaterial toolMaterial) {
        super(2.0f, toolMaterial, ItemPickaxe.EFFECTIVE_ON);
    }
    
    static {
        final Block[] array = new Block[0x73 ^ 0x6A];
        array["".length()] = Blocks.activator_rail;
        array[" ".length()] = Blocks.coal_ore;
        array["  ".length()] = Blocks.cobblestone;
        array["   ".length()] = Blocks.detector_rail;
        array[0x51 ^ 0x55] = Blocks.diamond_block;
        array[0x0 ^ 0x5] = Blocks.diamond_ore;
        array[0x2B ^ 0x2D] = Blocks.double_stone_slab;
        array[0xB7 ^ 0xB0] = Blocks.golden_rail;
        array[0xAA ^ 0xA2] = Blocks.gold_block;
        array[0x94 ^ 0x9D] = Blocks.gold_ore;
        array[0x79 ^ 0x73] = Blocks.ice;
        array[0x4D ^ 0x46] = Blocks.iron_block;
        array[0xCF ^ 0xC3] = Blocks.iron_ore;
        array[0x3B ^ 0x36] = Blocks.lapis_block;
        array[0x47 ^ 0x49] = Blocks.lapis_ore;
        array[0x11 ^ 0x1E] = Blocks.lit_redstone_ore;
        array[0x49 ^ 0x59] = Blocks.mossy_cobblestone;
        array[0x44 ^ 0x55] = Blocks.netherrack;
        array[0x18 ^ 0xA] = Blocks.packed_ice;
        array[0x4F ^ 0x5C] = Blocks.rail;
        array[0x64 ^ 0x70] = Blocks.redstone_ore;
        array[0x81 ^ 0x94] = Blocks.sandstone;
        array[0x4E ^ 0x58] = Blocks.red_sandstone;
        array[0xB6 ^ 0xA1] = Blocks.stone;
        array[0x9B ^ 0x83] = Blocks.stone_slab;
        EFFECTIVE_ON = Sets.newHashSet((Object[])array);
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        float n;
        if (block.getMaterial() != Material.iron && block.getMaterial() != Material.anvil && block.getMaterial() != Material.rock) {
            n = super.getStrVsBlock(itemStack, block);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            n = this.efficiencyOnProperMaterial;
        }
        return n;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
}
