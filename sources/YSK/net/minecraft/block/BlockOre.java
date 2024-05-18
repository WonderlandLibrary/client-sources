package net.minecraft.block;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockOre extends Block
{
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        Item item;
        if (this == Blocks.coal_ore) {
            item = Items.coal;
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else if (this == Blocks.diamond_ore) {
            item = Items.diamond;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (this == Blocks.lapis_ore) {
            item = Items.dye;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (this == Blocks.emerald_ore) {
            item = Items.emerald;
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (this == Blocks.quartz_ore) {
            item = Items.quartz;
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        else {
            item = Item.getItemFromBlock(this);
        }
        return item;
    }
    
    public BlockOre(final MapColor mapColor) {
        super(Material.rock, mapColor);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return "".length();
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        int n;
        if (this == Blocks.lapis_ore) {
            n = EnumDyeColor.BLUE.getDyeDamage();
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        if (n > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), random, n)) {
            int length = random.nextInt(n + "  ".length()) - " ".length();
            if (length < 0) {
                length = "".length();
            }
            return this.quantityDropped(random) * (length + " ".length());
        }
        return this.quantityDropped(random);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        int length;
        if (this == Blocks.lapis_ore) {
            length = (0x61 ^ 0x65) + random.nextInt(0x59 ^ 0x5C);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            length = " ".length();
        }
        return length;
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        if (this.getItemDropped(blockState, world.rand, n2) != Item.getItemFromBlock(this)) {
            int n3 = "".length();
            if (this == Blocks.coal_ore) {
                n3 = MathHelper.getRandomIntegerInRange(world.rand, "".length(), "  ".length());
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
            else if (this == Blocks.diamond_ore) {
                n3 = MathHelper.getRandomIntegerInRange(world.rand, "   ".length(), 0xB7 ^ 0xB0);
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else if (this == Blocks.emerald_ore) {
                n3 = MathHelper.getRandomIntegerInRange(world.rand, "   ".length(), 0x23 ^ 0x24);
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else if (this == Blocks.lapis_ore) {
                n3 = MathHelper.getRandomIntegerInRange(world.rand, "  ".length(), 0x67 ^ 0x62);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else if (this == Blocks.quartz_ore) {
                n3 = MathHelper.getRandomIntegerInRange(world.rand, "  ".length(), 0xA3 ^ 0xA6);
            }
            this.dropXpOnBlockBreak(world, blockPos, n3);
        }
    }
    
    public BlockOre() {
        this(Material.rock.getMaterialMapColor());
    }
}
