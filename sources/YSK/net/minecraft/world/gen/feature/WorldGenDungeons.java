package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.tileentity.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;

public class WorldGenDungeons extends WorldGenerator
{
    private static final String[] I;
    private static final String[] SPAWNERTYPES;
    private static final List<WeightedRandomChestContent> CHESTCONTENT;
    private static final Logger field_175918_a;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        "   ".length();
        final int n = random.nextInt("  ".length()) + "  ".length();
        final int n2 = -n - " ".length();
        final int n3 = n + " ".length();
        final int n4 = -" ".length();
        final int n5 = random.nextInt("  ".length()) + "  ".length();
        final int n6 = -n5 - " ".length();
        final int n7 = n5 + " ".length();
        int length = "".length();
        int i = n2;
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i <= n3) {
            int j = -" ".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (j <= (0xA7 ^ 0xA3)) {
                int k = n6;
                "".length();
                if (4 < 4) {
                    throw null;
                }
                while (k <= n7) {
                    final BlockPos add = blockPos.add(i, j, k);
                    final boolean solid = world.getBlockState(add).getBlock().getMaterial().isSolid();
                    if (j == -" ".length() && !solid) {
                        return "".length() != 0;
                    }
                    if (j == (0x7 ^ 0x3) && !solid) {
                        return "".length() != 0;
                    }
                    if ((i == n2 || i == n3 || k == n6 || k == n7) && j == 0 && world.isAirBlock(add) && world.isAirBlock(add.up())) {
                        ++length;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        if (length < " ".length() || length > (0x67 ^ 0x62)) {
            return "".length() != 0;
        }
        int l = n2;
        "".length();
        if (2 == 4) {
            throw null;
        }
        while (l <= n3) {
            int length2 = "   ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (length2 >= -" ".length()) {
                int n8 = n6;
                "".length();
                if (3 <= -1) {
                    throw null;
                }
                while (n8 <= n7) {
                    final BlockPos add2 = blockPos.add(l, length2, n8);
                    if (l != n2 && length2 != -" ".length() && n8 != n6 && l != n3 && length2 != (0xB7 ^ 0xB3) && n8 != n7) {
                        if (world.getBlockState(add2).getBlock() != Blocks.chest) {
                            world.setBlockToAir(add2);
                            "".length();
                            if (false) {
                                throw null;
                            }
                        }
                    }
                    else if (add2.getY() >= 0 && !world.getBlockState(add2.down()).getBlock().getMaterial().isSolid()) {
                        world.setBlockToAir(add2);
                        "".length();
                        if (0 >= 4) {
                            throw null;
                        }
                    }
                    else if (world.getBlockState(add2).getBlock().getMaterial().isSolid() && world.getBlockState(add2).getBlock() != Blocks.chest) {
                        if (length2 == -" ".length() && random.nextInt(0xA7 ^ 0xA3) != 0) {
                            world.setBlockState(add2, Blocks.mossy_cobblestone.getDefaultState(), "  ".length());
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                        }
                        else {
                            world.setBlockState(add2, Blocks.cobblestone.getDefaultState(), "  ".length());
                        }
                    }
                    ++n8;
                }
                --length2;
            }
            ++l;
        }
        int length3 = "".length();
        "".length();
        if (4 < 2) {
            throw null;
        }
        while (length3 < "  ".length()) {
            int length4 = "".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (length4 < "   ".length()) {
                final BlockPos blockPos2 = new BlockPos(blockPos.getX() + random.nextInt(n * "  ".length() + " ".length()) - n, blockPos.getY(), blockPos.getZ() + random.nextInt(n5 * "  ".length() + " ".length()) - n5);
                if (world.isAirBlock(blockPos2)) {
                    int length5 = "".length();
                    final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        if (world.getBlockState(blockPos2.offset(iterator.next())).getBlock().getMaterial().isSolid()) {
                            ++length5;
                        }
                    }
                    if (length5 == " ".length()) {
                        world.setBlockState(blockPos2, Blocks.chest.correctFacing(world, blockPos2, Blocks.chest.getDefaultState()), "  ".length());
                        final List<WeightedRandomChestContent> chestcontent = WorldGenDungeons.CHESTCONTENT;
                        final WeightedRandomChestContent[] array = new WeightedRandomChestContent[" ".length()];
                        array["".length()] = Items.enchanted_book.getRandom(random);
                        final List<WeightedRandomChestContent> func_177629_a = WeightedRandomChestContent.func_177629_a(chestcontent, array);
                        final TileEntity tileEntity = world.getTileEntity(blockPos2);
                        if (!(tileEntity instanceof TileEntityChest)) {
                            break;
                        }
                        WeightedRandomChestContent.generateChestContents(random, func_177629_a, (IInventory)tileEntity, 0xA6 ^ 0xAE);
                        "".length();
                        if (1 < -1) {
                            throw null;
                        }
                        break;
                    }
                }
                ++length4;
            }
            ++length3;
        }
        world.setBlockState(blockPos, Blocks.mob_spawner.getDefaultState(), "  ".length());
        final TileEntity tileEntity2 = world.getTileEntity(blockPos);
        if (tileEntity2 instanceof TileEntityMobSpawner) {
            ((TileEntityMobSpawner)tileEntity2).getSpawnerBaseLogic().setEntityName(this.pickMobSpawner(random));
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            WorldGenDungeons.field_175918_a.error(WorldGenDungeons.I[0x94 ^ 0x90] + blockPos.getX() + WorldGenDungeons.I[0x4B ^ 0x4E] + blockPos.getY() + WorldGenDungeons.I[0x73 ^ 0x75] + blockPos.getZ() + WorldGenDungeons.I[0x22 ^ 0x25]);
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x12 ^ 0x1A])["".length()] = I("\u0010/3\t.7+8", "CDVeK");
        WorldGenDungeons.I[" ".length()] = I("(*\f\u0015 \u0017", "rEawI");
        WorldGenDungeons.I["  ".length()] = I("\u0014\u001d\u0017\u0014\u000b+", "Nrzvb");
        WorldGenDungeons.I["   ".length()] = I("'\u001b\u00117\u0012\u0006", "tkxSw");
        WorldGenDungeons.I[0x82 ^ 0x86] = I("\u001c\u0006\u0018\u00020>G\u0005\u0001u<\u0002\u0005\r=z\n\u001e\fu)\u0017\u0010\u0019;?\u0015Q\u000b;.\u000e\u0005\u0017u;\u0013QF", "ZgqnU");
        WorldGenDungeons.I[0x92 ^ 0x97] = I("\\b", "pBqha");
        WorldGenDungeons.I[0x8A ^ 0x8C] = I("xR", "TrJWJ");
        WorldGenDungeons.I[0xBE ^ 0xB9] = I("c", "JLwQV");
    }
    
    static {
        I();
        field_175918_a = LogManager.getLogger();
        final String[] spawnertypes = new String[0x2C ^ 0x28];
        spawnertypes["".length()] = WorldGenDungeons.I["".length()];
        spawnertypes[" ".length()] = WorldGenDungeons.I[" ".length()];
        spawnertypes["  ".length()] = WorldGenDungeons.I["  ".length()];
        spawnertypes["   ".length()] = WorldGenDungeons.I["   ".length()];
        SPAWNERTYPES = spawnertypes;
        final WeightedRandomChestContent[] array = new WeightedRandomChestContent[0xF ^ 0x0];
        array["".length()] = new WeightedRandomChestContent(Items.saddle, "".length(), " ".length(), " ".length(), 0xC ^ 0x6);
        array[" ".length()] = new WeightedRandomChestContent(Items.iron_ingot, "".length(), " ".length(), 0x39 ^ 0x3D, 0x3B ^ 0x31);
        array["  ".length()] = new WeightedRandomChestContent(Items.bread, "".length(), " ".length(), " ".length(), 0x8A ^ 0x80);
        array["   ".length()] = new WeightedRandomChestContent(Items.wheat, "".length(), " ".length(), 0xC5 ^ 0xC1, 0x53 ^ 0x59);
        array[0xB9 ^ 0xBD] = new WeightedRandomChestContent(Items.gunpowder, "".length(), " ".length(), 0xB5 ^ 0xB1, 0x4D ^ 0x47);
        array[0x33 ^ 0x36] = new WeightedRandomChestContent(Items.string, "".length(), " ".length(), 0xB3 ^ 0xB7, 0x1F ^ 0x15);
        array[0x82 ^ 0x84] = new WeightedRandomChestContent(Items.bucket, "".length(), " ".length(), " ".length(), 0x98 ^ 0x92);
        array[0x8E ^ 0x89] = new WeightedRandomChestContent(Items.golden_apple, "".length(), " ".length(), " ".length(), " ".length());
        array[0x4A ^ 0x42] = new WeightedRandomChestContent(Items.redstone, "".length(), " ".length(), 0x9C ^ 0x98, 0x7E ^ 0x74);
        array[0x7B ^ 0x72] = new WeightedRandomChestContent(Items.record_13, "".length(), " ".length(), " ".length(), 0x80 ^ 0x84);
        array[0xCE ^ 0xC4] = new WeightedRandomChestContent(Items.record_cat, "".length(), " ".length(), " ".length(), 0xA1 ^ 0xA5);
        array[0x6D ^ 0x66] = new WeightedRandomChestContent(Items.name_tag, "".length(), " ".length(), " ".length(), 0x29 ^ 0x23);
        array[0x7 ^ 0xB] = new WeightedRandomChestContent(Items.golden_horse_armor, "".length(), " ".length(), " ".length(), "  ".length());
        array[0x9F ^ 0x92] = new WeightedRandomChestContent(Items.iron_horse_armor, "".length(), " ".length(), " ".length(), 0x5D ^ 0x58);
        array[0x3F ^ 0x31] = new WeightedRandomChestContent(Items.diamond_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
        CHESTCONTENT = Lists.newArrayList((Object[])array);
    }
    
    private String pickMobSpawner(final Random random) {
        return WorldGenDungeons.SPAWNERTYPES[random.nextInt(WorldGenDungeons.SPAWNERTYPES.length)];
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
