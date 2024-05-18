package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import com.google.common.base.*;
import net.minecraft.potion.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;

public class Item
{
    private static final Map<Block, Item> BLOCK_TO_ITEM;
    private Item containerItem;
    protected boolean bFull3D;
    protected int maxStackSize;
    private String unlocalizedName;
    private String potionEffect;
    private int maxDamage;
    public static final RegistryNamespaced<ResourceLocation, Item> itemRegistry;
    protected static Random itemRand;
    private CreativeTabs tabToDisplayOn;
    private static final String[] I;
    protected boolean hasSubtypes;
    protected static final UUID itemModifierUUID;
    
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return 14019937 + 7785759 - 10657615 + 5629134;
    }
    
    public static Item getByNameOrId(final String s) {
        final Item item = Item.itemRegistry.getObject(new ResourceLocation(s));
        if (item == null) {
            try {
                return getItemById(Integer.parseInt(s));
            }
            catch (NumberFormatException ex) {}
        }
        return item;
    }
    
    public static int getIdFromItem(final Item item) {
        int n;
        if (item == null) {
            n = "".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            n = Item.itemRegistry.getIDForObject(item);
        }
        return n;
    }
    
    public boolean isItemTool(final ItemStack itemStack) {
        if (this.getItemStackLimit() == " ".length() && this.isDamageable()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return itemStack;
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer, final int n) {
    }
    
    public EnumRarity getRarity(final ItemStack itemStack) {
        EnumRarity enumRarity;
        if (itemStack.isItemEnchanted()) {
            enumRarity = EnumRarity.RARE;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            enumRarity = EnumRarity.COMMON;
        }
        return enumRarity;
    }
    
    protected Item setHasSubtypes(final boolean hasSubtypes) {
        this.hasSubtypes = hasSubtypes;
        return this;
    }
    
    public boolean hasEffect(final ItemStack itemStack) {
        return itemStack.isItemEnchanted();
    }
    
    public boolean itemInteractionForEntity(final ItemStack itemStack, final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        return "".length() != 0;
    }
    
    public static void registerItems() {
        registerItemBlock(Blocks.stone, new ItemMultiTexture(Blocks.stone, Blocks.stone, (Function<ItemStack, String>)new Function<ItemStack, String>() {
            public String apply(final ItemStack itemStack) {
                return BlockStone.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
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
                    if (3 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }).setUnlocalizedName(Item.I[0x6C ^ 0x69]));
        registerItemBlock(Blocks.grass, new ItemColored(Blocks.grass, (boolean)("".length() != 0)));
        registerItemBlock(Blocks.dirt, new ItemMultiTexture(Blocks.dirt, Blocks.dirt, (Function<ItemStack, String>)new Function<ItemStack, String>() {
            public String apply(final ItemStack itemStack) {
                return BlockDirt.DirtType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
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
                    if (0 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
        }).setUnlocalizedName(Item.I[0x3E ^ 0x38]));
        registerItemBlock(Blocks.cobblestone);
        registerItemBlock(Blocks.planks, new ItemMultiTexture(Blocks.planks, Blocks.planks, (Function<ItemStack, String>)new Function<ItemStack, String>() {
            public String apply(final ItemStack itemStack) {
                return BlockPlanks.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
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
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
        }).setUnlocalizedName(Item.I[0x88 ^ 0x8F]));
        registerItemBlock(Blocks.sapling, new ItemMultiTexture(Blocks.sapling, Blocks.sapling, (Function<ItemStack, String>)new Function<ItemStack, String>() {
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
                    if (2 != 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
            
            public String apply(final ItemStack itemStack) {
                return BlockPlanks.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
            }
        }).setUnlocalizedName(Item.I[0xB9 ^ 0xB1]));
        registerItemBlock(Blocks.bedrock);
        registerItemBlock(Blocks.sand, new ItemMultiTexture(Blocks.sand, Blocks.sand, (Function<ItemStack, String>)new Function<ItemStack, String>() {
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
                    if (0 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public String apply(final ItemStack itemStack) {
                return BlockSand.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
        }).setUnlocalizedName(Item.I[0xA ^ 0x3]));
        registerItemBlock(Blocks.gravel);
        registerItemBlock(Blocks.gold_ore);
        registerItemBlock(Blocks.iron_ore);
        registerItemBlock(Blocks.coal_ore);
        registerItemBlock(Blocks.log, new ItemMultiTexture(Blocks.log, Blocks.log, (Function<ItemStack, String>)new Function<ItemStack, String>() {
            public String apply(final ItemStack itemStack) {
                return BlockPlanks.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
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
                    if (-1 != -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }).setUnlocalizedName(Item.I[0xCC ^ 0xC6]));
        registerItemBlock(Blocks.log2, new ItemMultiTexture(Blocks.log2, Blocks.log2, (Function<ItemStack, String>)new Function<ItemStack, String>() {
            public String apply(final ItemStack itemStack) {
                return BlockPlanks.EnumType.byMetadata(itemStack.getMetadata() + (0x88 ^ 0x8C)).getUnlocalizedName();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
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
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }).setUnlocalizedName(Item.I[0x97 ^ 0x9C]));
        registerItemBlock(Blocks.leaves, new ItemLeaves(Blocks.leaves).setUnlocalizedName(Item.I[0x50 ^ 0x5C]));
        registerItemBlock(Blocks.leaves2, new ItemLeaves(Blocks.leaves2).setUnlocalizedName(Item.I[0xA0 ^ 0xAD]));
        registerItemBlock(Blocks.sponge, new ItemMultiTexture(Blocks.sponge, Blocks.sponge, (Function<ItemStack, String>)new Function<ItemStack, String>() {
            private static final String[] I;
            
            static {
                I();
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
                    if (2 == 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
            
            public String apply(final ItemStack itemStack) {
                String s;
                if ((itemStack.getMetadata() & " ".length()) == " ".length()) {
                    s = Item$8.I["".length()];
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    s = Item$8.I[" ".length()];
                }
                return s;
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("\u000047", "wQCuv");
                Item$8.I[" ".length()] = I("%:+", "AHRVb");
            }
        }).setUnlocalizedName(Item.I[0x28 ^ 0x26]));
        registerItemBlock(Blocks.glass);
        registerItemBlock(Blocks.lapis_ore);
        registerItemBlock(Blocks.lapis_block);
        registerItemBlock(Blocks.dispenser);
        registerItemBlock(Blocks.sandstone, new ItemMultiTexture(Blocks.sandstone, Blocks.sandstone, (Function<ItemStack, String>)new Function<ItemStack, String>() {
            public String apply(final ItemStack itemStack) {
                return BlockSandStone.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
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
        }).setUnlocalizedName(Item.I[0x70 ^ 0x7F]));
        registerItemBlock(Blocks.noteblock);
        registerItemBlock(Blocks.golden_rail);
        registerItemBlock(Blocks.detector_rail);
        registerItemBlock(Blocks.sticky_piston, new ItemPiston(Blocks.sticky_piston));
        registerItemBlock(Blocks.web);
        final BlockTallGrass tallgrass = Blocks.tallgrass;
        final ItemColored itemColored = new ItemColored(Blocks.tallgrass, " ".length() != 0);
        final String[] subtypeNames = new String["   ".length()];
        subtypeNames["".length()] = Item.I[0x3E ^ 0x2E];
        subtypeNames[" ".length()] = Item.I[0x2D ^ 0x3C];
        subtypeNames["  ".length()] = Item.I[0x5B ^ 0x49];
        registerItemBlock(tallgrass, itemColored.setSubtypeNames(subtypeNames));
        registerItemBlock(Blocks.deadbush);
        registerItemBlock(Blocks.piston, new ItemPiston(Blocks.piston));
        registerItemBlock(Blocks.wool, new ItemCloth(Blocks.wool).setUnlocalizedName(Item.I[0x62 ^ 0x71]));
        registerItemBlock(Blocks.yellow_flower, new ItemMultiTexture(Blocks.yellow_flower, Blocks.yellow_flower, (Function<ItemStack, String>)new Function<ItemStack, String>() {
            public String apply(final ItemStack itemStack) {
                return BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.YELLOW, itemStack.getMetadata()).getUnlocalizedName();
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
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
        }).setUnlocalizedName(Item.I[0xA3 ^ 0xB7]));
        registerItemBlock(Blocks.red_flower, new ItemMultiTexture(Blocks.red_flower, Blocks.red_flower, (Function<ItemStack, String>)new Function<ItemStack, String>() {
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
                    if (3 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
            
            public String apply(final ItemStack itemStack) {
                return BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, itemStack.getMetadata()).getUnlocalizedName();
            }
        }).setUnlocalizedName(Item.I[0x52 ^ 0x47]));
        registerItemBlock(Blocks.brown_mushroom);
        registerItemBlock(Blocks.red_mushroom);
        registerItemBlock(Blocks.gold_block);
        registerItemBlock(Blocks.iron_block);
        registerItemBlock(Blocks.stone_slab, new ItemSlab(Blocks.stone_slab, Blocks.stone_slab, Blocks.double_stone_slab).setUnlocalizedName(Item.I[0x6 ^ 0x10]));
        registerItemBlock(Blocks.brick_block);
        registerItemBlock(Blocks.tnt);
        registerItemBlock(Blocks.bookshelf);
        registerItemBlock(Blocks.mossy_cobblestone);
        registerItemBlock(Blocks.obsidian);
        registerItemBlock(Blocks.torch);
        registerItemBlock(Blocks.mob_spawner);
        registerItemBlock(Blocks.oak_stairs);
        registerItemBlock(Blocks.chest);
        registerItemBlock(Blocks.diamond_ore);
        registerItemBlock(Blocks.diamond_block);
        registerItemBlock(Blocks.crafting_table);
        registerItemBlock(Blocks.farmland);
        registerItemBlock(Blocks.furnace);
        registerItemBlock(Blocks.lit_furnace);
        registerItemBlock(Blocks.ladder);
        registerItemBlock(Blocks.rail);
        registerItemBlock(Blocks.stone_stairs);
        registerItemBlock(Blocks.lever);
        registerItemBlock(Blocks.stone_pressure_plate);
        registerItemBlock(Blocks.wooden_pressure_plate);
        registerItemBlock(Blocks.redstone_ore);
        registerItemBlock(Blocks.redstone_torch);
        registerItemBlock(Blocks.stone_button);
        registerItemBlock(Blocks.snow_layer, new ItemSnow(Blocks.snow_layer));
        registerItemBlock(Blocks.ice);
        registerItemBlock(Blocks.snow);
        registerItemBlock(Blocks.cactus);
        registerItemBlock(Blocks.clay);
        registerItemBlock(Blocks.jukebox);
        registerItemBlock(Blocks.oak_fence);
        registerItemBlock(Blocks.spruce_fence);
        registerItemBlock(Blocks.birch_fence);
        registerItemBlock(Blocks.jungle_fence);
        registerItemBlock(Blocks.dark_oak_fence);
        registerItemBlock(Blocks.acacia_fence);
        registerItemBlock(Blocks.pumpkin);
        registerItemBlock(Blocks.netherrack);
        registerItemBlock(Blocks.soul_sand);
        registerItemBlock(Blocks.glowstone);
        registerItemBlock(Blocks.lit_pumpkin);
        registerItemBlock(Blocks.trapdoor);
        registerItemBlock(Blocks.monster_egg, new ItemMultiTexture(Blocks.monster_egg, Blocks.monster_egg, (Function<ItemStack, String>)new Function<ItemStack, String>() {
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
            
            public String apply(final ItemStack itemStack) {
                return BlockSilverfish.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
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
                    if (-1 != -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }).setUnlocalizedName(Item.I[0x3F ^ 0x28]));
        registerItemBlock(Blocks.stonebrick, new ItemMultiTexture(Blocks.stonebrick, Blocks.stonebrick, (Function<ItemStack, String>)new Function<ItemStack, String>() {
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
                    if (1 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
            
            public String apply(final ItemStack itemStack) {
                return BlockStoneBrick.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
            }
        }).setUnlocalizedName(Item.I[0x32 ^ 0x2A]));
        registerItemBlock(Blocks.brown_mushroom_block);
        registerItemBlock(Blocks.red_mushroom_block);
        registerItemBlock(Blocks.iron_bars);
        registerItemBlock(Blocks.glass_pane);
        registerItemBlock(Blocks.melon_block);
        registerItemBlock(Blocks.vine, new ItemColored(Blocks.vine, (boolean)("".length() != 0)));
        registerItemBlock(Blocks.oak_fence_gate);
        registerItemBlock(Blocks.spruce_fence_gate);
        registerItemBlock(Blocks.birch_fence_gate);
        registerItemBlock(Blocks.jungle_fence_gate);
        registerItemBlock(Blocks.dark_oak_fence_gate);
        registerItemBlock(Blocks.acacia_fence_gate);
        registerItemBlock(Blocks.brick_stairs);
        registerItemBlock(Blocks.stone_brick_stairs);
        registerItemBlock(Blocks.mycelium);
        registerItemBlock(Blocks.waterlily, new ItemLilyPad(Blocks.waterlily));
        registerItemBlock(Blocks.nether_brick);
        registerItemBlock(Blocks.nether_brick_fence);
        registerItemBlock(Blocks.nether_brick_stairs);
        registerItemBlock(Blocks.enchanting_table);
        registerItemBlock(Blocks.end_portal_frame);
        registerItemBlock(Blocks.end_stone);
        registerItemBlock(Blocks.dragon_egg);
        registerItemBlock(Blocks.redstone_lamp);
        registerItemBlock(Blocks.wooden_slab, new ItemSlab(Blocks.wooden_slab, Blocks.wooden_slab, Blocks.double_wooden_slab).setUnlocalizedName(Item.I[0x44 ^ 0x5D]));
        registerItemBlock(Blocks.sandstone_stairs);
        registerItemBlock(Blocks.emerald_ore);
        registerItemBlock(Blocks.ender_chest);
        registerItemBlock(Blocks.tripwire_hook);
        registerItemBlock(Blocks.emerald_block);
        registerItemBlock(Blocks.spruce_stairs);
        registerItemBlock(Blocks.birch_stairs);
        registerItemBlock(Blocks.jungle_stairs);
        registerItemBlock(Blocks.command_block);
        registerItemBlock(Blocks.beacon);
        registerItemBlock(Blocks.cobblestone_wall, new ItemMultiTexture(Blocks.cobblestone_wall, Blocks.cobblestone_wall, (Function<ItemStack, String>)new Function<ItemStack, String>() {
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
                    if (0 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
            
            public String apply(final ItemStack itemStack) {
                return BlockWall.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
            }
        }).setUnlocalizedName(Item.I[0x2A ^ 0x30]));
        registerItemBlock(Blocks.wooden_button);
        registerItemBlock(Blocks.anvil, new ItemAnvilBlock(Blocks.anvil).setUnlocalizedName(Item.I[0x67 ^ 0x7C]));
        registerItemBlock(Blocks.trapped_chest);
        registerItemBlock(Blocks.light_weighted_pressure_plate);
        registerItemBlock(Blocks.heavy_weighted_pressure_plate);
        registerItemBlock(Blocks.daylight_detector);
        registerItemBlock(Blocks.redstone_block);
        registerItemBlock(Blocks.quartz_ore);
        registerItemBlock(Blocks.hopper);
        final Block quartz_block = Blocks.quartz_block;
        final Block quartz_block2 = Blocks.quartz_block;
        final Block quartz_block3 = Blocks.quartz_block;
        final String[] array = new String["   ".length()];
        array["".length()] = Item.I[0x52 ^ 0x4E];
        array[" ".length()] = Item.I[0x54 ^ 0x49];
        array["  ".length()] = Item.I[0x8E ^ 0x90];
        registerItemBlock(quartz_block, new ItemMultiTexture(quartz_block2, quartz_block3, array).setUnlocalizedName(Item.I[0x89 ^ 0x96]));
        registerItemBlock(Blocks.quartz_stairs);
        registerItemBlock(Blocks.activator_rail);
        registerItemBlock(Blocks.dropper);
        registerItemBlock(Blocks.stained_hardened_clay, new ItemCloth(Blocks.stained_hardened_clay).setUnlocalizedName(Item.I[0x16 ^ 0x36]));
        registerItemBlock(Blocks.barrier);
        registerItemBlock(Blocks.iron_trapdoor);
        registerItemBlock(Blocks.hay_block);
        registerItemBlock(Blocks.carpet, new ItemCloth(Blocks.carpet).setUnlocalizedName(Item.I[0x86 ^ 0xA7]));
        registerItemBlock(Blocks.hardened_clay);
        registerItemBlock(Blocks.coal_block);
        registerItemBlock(Blocks.packed_ice);
        registerItemBlock(Blocks.acacia_stairs);
        registerItemBlock(Blocks.dark_oak_stairs);
        registerItemBlock(Blocks.slime_block);
        registerItemBlock(Blocks.double_plant, new ItemDoublePlant(Blocks.double_plant, Blocks.double_plant, (Function<ItemStack, String>)new Function<ItemStack, String>() {
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
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
                    if (4 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public String apply(final ItemStack itemStack) {
                return BlockDoublePlant.EnumPlantType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
            }
        }).setUnlocalizedName(Item.I[0x1 ^ 0x23]));
        registerItemBlock(Blocks.stained_glass, new ItemCloth(Blocks.stained_glass).setUnlocalizedName(Item.I[0x6E ^ 0x4D]));
        registerItemBlock(Blocks.stained_glass_pane, new ItemCloth(Blocks.stained_glass_pane).setUnlocalizedName(Item.I[0x44 ^ 0x60]));
        registerItemBlock(Blocks.prismarine, new ItemMultiTexture(Blocks.prismarine, Blocks.prismarine, (Function<ItemStack, String>)new Function<ItemStack, String>() {
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
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
            
            public String apply(final ItemStack itemStack) {
                return BlockPrismarine.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
            }
        }).setUnlocalizedName(Item.I[0x9F ^ 0xBA]));
        registerItemBlock(Blocks.sea_lantern);
        registerItemBlock(Blocks.red_sandstone, new ItemMultiTexture(Blocks.red_sandstone, Blocks.red_sandstone, (Function<ItemStack, String>)new Function<ItemStack, String>() {
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
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public String apply(final ItemStack itemStack) {
                return BlockRedSandstone.EnumType.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
        }).setUnlocalizedName(Item.I[0x57 ^ 0x71]));
        registerItemBlock(Blocks.red_sandstone_stairs);
        registerItemBlock(Blocks.stone_slab2, new ItemSlab(Blocks.stone_slab2, Blocks.stone_slab2, Blocks.double_stone_slab2).setUnlocalizedName(Item.I[0x5A ^ 0x7D]));
        registerItem(248 + 215 - 369 + 162, Item.I[0x5E ^ 0x76], new ItemSpade(ToolMaterial.IRON).setUnlocalizedName(Item.I[0x68 ^ 0x41]));
        registerItem(232 + 43 - 253 + 235, Item.I[0x16 ^ 0x3C], new ItemPickaxe(ToolMaterial.IRON).setUnlocalizedName(Item.I[0x4D ^ 0x66]));
        registerItem(113 + 135 - 55 + 65, Item.I[0x81 ^ 0xAD], new ItemAxe(ToolMaterial.IRON).setUnlocalizedName(Item.I[0x6E ^ 0x43]));
        registerItem(62 + 54 - 81 + 224, Item.I[0x30 ^ 0x1E], new ItemFlintAndSteel().setUnlocalizedName(Item.I[0x6F ^ 0x40]));
        registerItem(142 + 128 - 99 + 89, Item.I[0x80 ^ 0xB0], new ItemFood(0x28 ^ 0x2C, 0.3f, "".length() != 0).setUnlocalizedName(Item.I[0xA4 ^ 0x95]));
        registerItem(252 + 115 - 127 + 21, Item.I[0x98 ^ 0xAA], new ItemBow().setUnlocalizedName(Item.I[0x5C ^ 0x6F]));
        registerItem(186 + 180 - 186 + 82, Item.I[0x38 ^ 0xC], new Item().setUnlocalizedName(Item.I[0x85 ^ 0xB0]).setCreativeTab(CreativeTabs.tabCombat));
        registerItem(247 + 37 - 116 + 95, Item.I[0x27 ^ 0x11], new ItemCoal().setUnlocalizedName(Item.I[0x1D ^ 0x2A]));
        registerItem(164 + 40 + 54 + 6, Item.I[0xA3 ^ 0x9B], new Item().setUnlocalizedName(Item.I[0x35 ^ 0xC]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(146 + 27 + 38 + 54, Item.I[0x5B ^ 0x61], new Item().setUnlocalizedName(Item.I[0x8A ^ 0xB1]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(178 + 88 - 143 + 143, Item.I[0x8E ^ 0xB2], new Item().setUnlocalizedName(Item.I[0xFD ^ 0xC0]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(67 + 233 - 67 + 34, Item.I[0xAD ^ 0x93], new ItemSword(ToolMaterial.IRON).setUnlocalizedName(Item.I[0xA1 ^ 0x9E]));
        registerItem(138 + 172 - 251 + 209, Item.I[0x77 ^ 0x37], new ItemSword(ToolMaterial.WOOD).setUnlocalizedName(Item.I[0x35 ^ 0x74]));
        registerItem(153 + 223 - 153 + 46, Item.I[0x4D ^ 0xF], new ItemSpade(ToolMaterial.WOOD).setUnlocalizedName(Item.I[0x78 ^ 0x3B]));
        registerItem(173 + 184 - 181 + 94, Item.I[0x69 ^ 0x2D], new ItemPickaxe(ToolMaterial.WOOD).setUnlocalizedName(Item.I[0x5 ^ 0x40]));
        registerItem(124 + 238 - 161 + 70, Item.I[0x30 ^ 0x76], new ItemAxe(ToolMaterial.WOOD).setUnlocalizedName(Item.I[0xDF ^ 0x98]));
        registerItem(223 + 28 - 246 + 267, Item.I[0x2C ^ 0x64], new ItemSword(ToolMaterial.STONE).setUnlocalizedName(Item.I[0x28 ^ 0x61]));
        registerItem(149 + 179 - 70 + 15, Item.I[0xCE ^ 0x84], new ItemSpade(ToolMaterial.STONE).setUnlocalizedName(Item.I[0x8F ^ 0xC4]));
        registerItem(152 + 70 - 104 + 156, Item.I[0xCA ^ 0x86], new ItemPickaxe(ToolMaterial.STONE).setUnlocalizedName(Item.I[0x6B ^ 0x26]));
        registerItem(146 + 203 - 288 + 214, Item.I[0xCE ^ 0x80], new ItemAxe(ToolMaterial.STONE).setUnlocalizedName(Item.I[0x1 ^ 0x4E]));
        registerItem(41 + 270 - 102 + 67, Item.I[0x6F ^ 0x3F], new ItemSword(ToolMaterial.EMERALD).setUnlocalizedName(Item.I[0xCB ^ 0x9A]));
        registerItem(239 + 231 - 447 + 254, Item.I[0xF3 ^ 0xA1], new ItemSpade(ToolMaterial.EMERALD).setUnlocalizedName(Item.I[0x38 ^ 0x6B]));
        registerItem(68 + 12 - 28 + 226, Item.I[0xF7 ^ 0xA3], new ItemPickaxe(ToolMaterial.EMERALD).setUnlocalizedName(Item.I[0xE6 ^ 0xB3]));
        registerItem(107 + 63 + 98 + 11, Item.I[0xC0 ^ 0x96], new ItemAxe(ToolMaterial.EMERALD).setUnlocalizedName(Item.I[0x6 ^ 0x51]));
        registerItem(46 + 167 + 18 + 49, Item.I[0x65 ^ 0x3D], new Item().setFull3D().setUnlocalizedName(Item.I[0x3 ^ 0x5A]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(159 + 61 - 117 + 178, Item.I[0x4 ^ 0x5E], new Item().setUnlocalizedName(Item.I[0xDE ^ 0x85]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(32 + 213 - 22 + 59, Item.I[0xC6 ^ 0x9A], new ItemSoup(0xC5 ^ 0xC3).setUnlocalizedName(Item.I[0xE0 ^ 0xBD]));
        registerItem(95 + 15 + 134 + 39, Item.I[0xFF ^ 0xA1], new ItemSword(ToolMaterial.GOLD).setUnlocalizedName(Item.I[0x8 ^ 0x57]));
        registerItem(225 + 82 - 148 + 125, Item.I[0xE9 ^ 0x89], new ItemSpade(ToolMaterial.GOLD).setUnlocalizedName(Item.I[0x43 ^ 0x22]));
        registerItem(198 + 62 + 21 + 4, Item.I[0xFD ^ 0x9F], new ItemPickaxe(ToolMaterial.GOLD).setUnlocalizedName(Item.I[0x5E ^ 0x3D]));
        registerItem(169 + 26 - 166 + 257, Item.I[0x4F ^ 0x2B], new ItemAxe(ToolMaterial.GOLD).setUnlocalizedName(Item.I[0x4 ^ 0x61]));
        registerItem(241 + 262 - 420 + 204, Item.I[0x3B ^ 0x5D], new ItemReed(Blocks.tripwire).setUnlocalizedName(Item.I[0x0 ^ 0x67]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(251 + 178 - 193 + 52, Item.I[0xE ^ 0x66], new Item().setUnlocalizedName(Item.I[0x7F ^ 0x16]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(74 + 112 - 119 + 222, Item.I[0x74 ^ 0x1E], new Item().setUnlocalizedName(Item.I[0xF1 ^ 0x9A]).setPotionEffect(Item.I[0x75 ^ 0x19]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(166 + 25 - 95 + 194, Item.I[0x22 ^ 0x4F], new ItemHoe(ToolMaterial.WOOD).setUnlocalizedName(Item.I[0x42 ^ 0x2C]));
        registerItem(50 + 284 - 305 + 262, Item.I[0xE8 ^ 0x87], new ItemHoe(ToolMaterial.STONE).setUnlocalizedName(Item.I[0x38 ^ 0x48]));
        registerItem(121 + 215 - 290 + 246, Item.I[0x6C ^ 0x1D], new ItemHoe(ToolMaterial.IRON).setUnlocalizedName(Item.I[0x7 ^ 0x75]));
        registerItem(148 + 51 - 188 + 282, Item.I[0x71 ^ 0x2], new ItemHoe(ToolMaterial.EMERALD).setUnlocalizedName(Item.I[0x69 ^ 0x1D]));
        registerItem(148 + 162 - 58 + 42, Item.I[0xEF ^ 0x9A], new ItemHoe(ToolMaterial.GOLD).setUnlocalizedName(Item.I[0x34 ^ 0x42]));
        registerItem(101 + 178 - 150 + 166, Item.I[0xEE ^ 0x99], new ItemSeeds(Blocks.wheat, Blocks.farmland).setUnlocalizedName(Item.I[0x78 ^ 0x0]));
        registerItem(139 + 198 - 140 + 99, Item.I[0x24 ^ 0x5D], new Item().setUnlocalizedName(Item.I[0xDC ^ 0xA6]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(221 + 256 - 343 + 163, Item.I[0xC8 ^ 0xB3], new ItemFood(0xC ^ 0x9, 0.6f, "".length() != 0).setUnlocalizedName(Item.I[0xE3 ^ 0x9F]));
        registerItem(265 + 130 - 172 + 75, Item.I[0x11 ^ 0x6C], new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, "".length(), "".length()).setUnlocalizedName(Item.I[0xE6 ^ 0x98]));
        registerItem(298 + 106 - 214 + 109, Item.I[64 + 123 - 91 + 31], new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, "".length(), " ".length()).setUnlocalizedName(Item.I[85 + 84 - 68 + 27]));
        registerItem(4 + 138 + 56 + 102, Item.I[121 + 36 - 139 + 111], new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, "".length(), "  ".length()).setUnlocalizedName(Item.I[71 + 4 - 29 + 84]));
        registerItem(97 + 262 - 266 + 208, Item.I[22 + 61 + 16 + 32], new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, "".length(), "   ".length()).setUnlocalizedName(Item.I[41 + 48 + 9 + 34]));
        registerItem(255 + 235 - 257 + 69, Item.I[50 + 121 - 116 + 78], new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, " ".length(), "".length()).setUnlocalizedName(Item.I[81 + 83 - 119 + 89]));
        registerItem(132 + 20 + 148 + 3, Item.I[104 + 101 - 137 + 67], new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, " ".length(), " ".length()).setUnlocalizedName(Item.I[68 + 2 - 32 + 98]));
        registerItem(15 + 229 - 165 + 225, Item.I[36 + 84 - 17 + 34], new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, " ".length(), "  ".length()).setUnlocalizedName(Item.I[8 + 14 - 3 + 119]));
        registerItem(111 + 152 - 161 + 203, Item.I[12 + 43 - 15 + 99], new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, " ".length(), "   ".length()).setUnlocalizedName(Item.I[136 + 75 - 108 + 37]));
        registerItem(91 + 199 - 287 + 303, Item.I[80 + 23 - 27 + 65], new ItemArmor(ItemArmor.ArmorMaterial.IRON, "  ".length(), "".length()).setUnlocalizedName(Item.I[107 + 130 - 141 + 46]));
        registerItem(260 + 255 - 209 + 1, Item.I[14 + 27 - 39 + 141], new ItemArmor(ItemArmor.ArmorMaterial.IRON, "  ".length(), " ".length()).setUnlocalizedName(Item.I[6 + 112 - 79 + 105]));
        registerItem(140 + 99 - 28 + 97, Item.I[143 + 58 - 64 + 8], new ItemArmor(ItemArmor.ArmorMaterial.IRON, "  ".length(), "  ".length()).setUnlocalizedName(Item.I[138 + 124 - 229 + 113]));
        registerItem(193 + 241 - 300 + 175, Item.I[35 + 28 - 40 + 124], new ItemArmor(ItemArmor.ArmorMaterial.IRON, "  ".length(), "   ".length()).setUnlocalizedName(Item.I[61 + 89 - 78 + 76]));
        registerItem(98 + 286 - 284 + 210, Item.I[44 + 31 - 33 + 107], new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, "   ".length(), "".length()).setUnlocalizedName(Item.I[113 + 138 - 228 + 127]));
        registerItem(52 + 143 - 30 + 146, Item.I[13 + 18 + 16 + 104], new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, "   ".length(), " ".length()).setUnlocalizedName(Item.I[36 + 58 - 86 + 144]));
        registerItem(132 + 120 - 185 + 245, Item.I[45 + 38 - 68 + 138], new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, "   ".length(), "  ".length()).setUnlocalizedName(Item.I[111 + 19 - 105 + 129]));
        registerItem(101 + 203 - 70 + 79, Item.I[146 + 142 - 136 + 3], new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, "   ".length(), "   ".length()).setUnlocalizedName(Item.I[132 + 143 - 213 + 94]));
        registerItem(175 + 1 - 33 + 171, Item.I[114 + 49 - 36 + 30], new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 0x4E ^ 0x4A, "".length()).setUnlocalizedName(Item.I[123 + 57 - 140 + 118]));
        registerItem(137 + 267 - 289 + 200, Item.I[138 + 5 - 50 + 66], new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 0xC3 ^ 0xC7, " ".length()).setUnlocalizedName(Item.I[41 + 81 - 24 + 62]));
        registerItem(28 + 130 + 149 + 9, Item.I[72 + 43 + 1 + 45], new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 0x3D ^ 0x39, "  ".length()).setUnlocalizedName(Item.I[145 + 111 - 122 + 28]));
        registerItem(173 + 2 - 134 + 276, Item.I[99 + 23 + 19 + 22], new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 0x6A ^ 0x6E, "   ".length()).setUnlocalizedName(Item.I[120 + 145 - 239 + 138]));
        registerItem(283 + 79 - 281 + 237, Item.I[46 + 80 - 99 + 138], new Item().setUnlocalizedName(Item.I[87 + 92 - 118 + 105]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(121 + 237 - 189 + 150, Item.I[123 + 64 - 180 + 160], new ItemFood("   ".length(), 0.3f, " ".length() != 0).setUnlocalizedName(Item.I[55 + 139 - 147 + 121]));
        registerItem(289 + 118 - 152 + 65, Item.I[143 + 141 - 207 + 92], new ItemFood(0x7E ^ 0x76, 0.8f, " ".length() != 0).setUnlocalizedName(Item.I[42 + 62 - 90 + 156]));
        registerItem(208 + 15 - 205 + 303, Item.I[144 + 144 - 269 + 152], new ItemHangingEntity(EntityPainting.class).setUnlocalizedName(Item.I[36 + 26 + 30 + 80]));
        registerItem(205 + 120 - 256 + 253, Item.I[131 + 40 - 93 + 95], new ItemAppleGold(0x53 ^ 0x57, 1.2f, "".length() != 0).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 0x9C ^ 0x99, " ".length(), 1.0f).setUnlocalizedName(Item.I[56 + 77 - 126 + 167]));
        registerItem(290 + 121 - 200 + 112, Item.I[131 + 129 - 91 + 6], new ItemSign().setUnlocalizedName(Item.I[154 + 162 - 232 + 92]));
        registerItem(127 + 241 - 305 + 261, Item.I[159 + 2 - 25 + 41], new ItemDoor(Blocks.oak_door).setUnlocalizedName(Item.I[145 + 123 - 177 + 87]));
        final Item setMaxStackSize = new ItemBucket(Blocks.air).setUnlocalizedName(Item.I[81 + 19 - 16 + 95]).setMaxStackSize(0x84 ^ 0x94);
        registerItem(62 + 43 + 163 + 57, Item.I[176 + 18 - 92 + 78], setMaxStackSize);
        registerItem(129 + 132 - 22 + 87, Item.I[174 + 53 - 186 + 140], new ItemBucket(Blocks.flowing_water).setUnlocalizedName(Item.I[62 + 60 - 86 + 146]).setContainerItem(setMaxStackSize));
        registerItem(315 + 225 - 497 + 284, Item.I[19 + 4 - 4 + 164], new ItemBucket(Blocks.flowing_lava).setUnlocalizedName(Item.I[9 + 152 + 1 + 22]).setContainerItem(setMaxStackSize));
        registerItem(117 + 244 - 67 + 34, Item.I[35 + 177 - 95 + 68], new ItemMinecart(EntityMinecart.EnumMinecartType.RIDEABLE).setUnlocalizedName(Item.I[42 + 18 + 105 + 21]));
        registerItem(225 + 224 - 214 + 94, Item.I[30 + 13 + 72 + 72], new ItemSaddle().setUnlocalizedName(Item.I[122 + 145 - 190 + 111]));
        registerItem(306 + 299 - 511 + 236, Item.I[46 + 64 - 74 + 153], new ItemDoor(Blocks.iron_door).setUnlocalizedName(Item.I[90 + 63 - 93 + 130]));
        registerItem(289 + 13 - 28 + 57, Item.I[115 + 162 - 184 + 98], new ItemRedstone().setUnlocalizedName(Item.I[77 + 41 + 47 + 27]).setPotionEffect(Item.I[26 + 109 - 63 + 121]));
        registerItem(15 + 241 - 77 + 153, Item.I[23 + 72 + 75 + 24], new ItemSnowball().setUnlocalizedName(Item.I[17 + 53 + 26 + 99]));
        registerItem(252 + 11 - 247 + 317, Item.I[39 + 162 - 166 + 161], new ItemBoat().setUnlocalizedName(Item.I[195 + 84 - 245 + 163]));
        registerItem(268 + 146 - 122 + 42, Item.I[86 + 84 - 88 + 116], new Item().setUnlocalizedName(Item.I[96 + 29 - 37 + 111]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(194 + 220 - 291 + 212, Item.I[43 + 175 - 99 + 81], new ItemBucketMilk().setUnlocalizedName(Item.I[163 + 22 - 160 + 176]).setContainerItem(setMaxStackSize));
        registerItem(15 + 153 - 85 + 253, Item.I[170 + 104 - 166 + 94], new Item().setUnlocalizedName(Item.I[78 + 150 - 223 + 198]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(120 + 225 - 90 + 82, Item.I[96 + 158 - 126 + 76], new Item().setUnlocalizedName(Item.I[14 + 14 + 27 + 150]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(243 + 92 - 0 + 3, Item.I[77 + 168 - 190 + 151], new ItemReed(Blocks.reeds).setUnlocalizedName(Item.I[166 + 59 - 207 + 189]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(14 + 266 - 118 + 177, Item.I[202 + 193 - 226 + 39], new Item().setUnlocalizedName(Item.I[204 + 138 - 211 + 78]).setCreativeTab(CreativeTabs.tabMisc));
        registerItem(0 + 82 + 43 + 215, Item.I[27 + 105 - 11 + 89], new ItemBook().setUnlocalizedName(Item.I[162 + 45 - 25 + 29]).setCreativeTab(CreativeTabs.tabMisc));
        registerItem(25 + 134 - 130 + 312, Item.I[113 + 27 + 29 + 43], new Item().setUnlocalizedName(Item.I[21 + 73 - 23 + 142]).setCreativeTab(CreativeTabs.tabMisc));
        registerItem(107 + 76 - 27 + 186, Item.I[39 + 73 + 29 + 73], new ItemMinecart(EntityMinecart.EnumMinecartType.CHEST).setUnlocalizedName(Item.I[100 + 82 - 167 + 200]));
        registerItem(170 + 304 - 425 + 294, Item.I[186 + 83 - 197 + 144], new ItemMinecart(EntityMinecart.EnumMinecartType.FURNACE).setUnlocalizedName(Item.I[157 + 18 - 33 + 75]));
        registerItem(44 + 247 - 216 + 269, Item.I[172 + 34 - 84 + 96], new ItemEgg().setUnlocalizedName(Item.I[168 + 214 - 355 + 192]));
        registerItem(79 + 234 - 181 + 213, Item.I[6 + 206 - 203 + 211], new Item().setUnlocalizedName(Item.I[35 + 29 + 150 + 7]).setCreativeTab(CreativeTabs.tabTools));
        registerItem(263 + 316 - 559 + 326, Item.I[46 + 83 - 52 + 145], new ItemFishingRod().setUnlocalizedName(Item.I[40 + 137 + 38 + 8]));
        registerItem(34 + 0 - 12 + 325, Item.I[86 + 29 - 91 + 200], new Item().setUnlocalizedName(Item.I[119 + 69 - 70 + 107]).setCreativeTab(CreativeTabs.tabTools));
        registerItem(49 + 49 + 103 + 147, Item.I[131 + 115 - 222 + 202], new Item().setUnlocalizedName(Item.I[131 + 54 + 30 + 12]).setPotionEffect(Item.I[189 + 65 - 244 + 218]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(278 + 182 - 148 + 37, Item.I[198 + 148 - 144 + 27], new ItemFishFood("".length() != 0).setUnlocalizedName(Item.I[71 + 207 - 68 + 20]).setHasSubtypes((boolean)(" ".length() != 0)));
        registerItem(48 + 122 - 90 + 270, Item.I[157 + 24 + 19 + 31], new ItemFishFood(" ".length() != 0).setUnlocalizedName(Item.I[35 + 165 - 2 + 34]).setHasSubtypes((boolean)(" ".length() != 0)));
        registerItem(178 + 268 - 280 + 185, Item.I[156 + 126 - 154 + 105], new ItemDye().setUnlocalizedName(Item.I[170 + 193 - 308 + 179]));
        registerItem(295 + 200 - 479 + 336, Item.I[18 + 18 + 179 + 20], new Item().setUnlocalizedName(Item.I[1 + 123 + 15 + 97]).setFull3D().setCreativeTab(CreativeTabs.tabMisc));
        registerItem(261 + 225 - 292 + 159, Item.I[155 + 52 - 85 + 115], new Item().setUnlocalizedName(Item.I[24 + 96 - 30 + 148]).setPotionEffect(Item.I[42 + 220 - 243 + 220]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(353 + 275 - 510 + 236, Item.I[22 + 160 - 159 + 217], new ItemReed(Blocks.cake).setMaxStackSize(" ".length()).setUnlocalizedName(Item.I[226 + 134 - 233 + 114]).setCreativeTab(CreativeTabs.tabFood));
        registerItem(15 + 178 - 63 + 225, Item.I[68 + 217 - 183 + 140], new ItemBed().setMaxStackSize(" ".length()).setUnlocalizedName(Item.I[158 + 21 - 131 + 195]));
        registerItem(304 + 99 - 350 + 303, Item.I[35 + 68 - 22 + 163], new ItemReed(Blocks.unpowered_repeater).setUnlocalizedName(Item.I[100 + 198 - 132 + 79]).setCreativeTab(CreativeTabs.tabRedstone));
        registerItem(352 + 128 - 372 + 249, Item.I[58 + 151 + 22 + 15], new ItemFood("  ".length(), 0.1f, "".length() != 0).setUnlocalizedName(Item.I[192 + 122 - 205 + 138]));
        registerItem(18 + 62 + 271 + 7, Item.I[22 + 187 - 179 + 218], new ItemMap().setUnlocalizedName(Item.I[209 + 88 - 279 + 231]));
        registerItem(307 + 267 - 229 + 14, Item.I[115 + 72 - 161 + 224], new ItemShears().setUnlocalizedName(Item.I[154 + 113 - 219 + 203]));
        registerItem(116 + 68 + 111 + 65, Item.I[169 + 197 - 178 + 64], new ItemFood("  ".length(), 0.3f, "".length() != 0).setUnlocalizedName(Item.I[73 + 47 + 20 + 113]));
        registerItem(71 + 259 - 284 + 315, Item.I[146 + 20 - 140 + 228], new ItemSeeds(Blocks.pumpkin_stem, Blocks.farmland).setUnlocalizedName(Item.I[73 + 114 - 94 + 162]));
        registerItem(33 + 113 - 34 + 250, Item.I[112 + 115 - 168 + 197], new ItemSeeds(Blocks.melon_stem, Blocks.farmland).setUnlocalizedName(Item.I[154 + 139 - 121 + 85]));
        registerItem(5 + 186 - 48 + 220, Item.I[51 + 33 + 47 + 127], new ItemFood("   ".length(), 0.3f, " ".length() != 0).setUnlocalizedName(Item.I[129 + 156 - 279 + 253]));
        registerItem(148 + 164 - 58 + 110, Item.I[63 + 224 - 143 + 116], new ItemFood(0x3 ^ 0xB, 0.8f, " ".length() != 0).setUnlocalizedName(Item.I[187 + 60 - 228 + 242]));
        registerItem(246 + 67 - 19 + 71, Item.I[208 + 52 - 234 + 236], new ItemFood("  ".length(), 0.3f, " ".length() != 0).setPotionEffect(Potion.hunger.id, 0x88 ^ 0x96, "".length(), 0.3f).setUnlocalizedName(Item.I[204 + 161 - 173 + 71]));
        registerItem(224 + 252 - 178 + 68, Item.I[16 + 245 - 209 + 212], new ItemFood(0xBE ^ 0xB8, 0.6f, " ".length() != 0).setUnlocalizedName(Item.I[181 + 263 - 218 + 39]));
        registerItem(193 + 131 - 82 + 125, Item.I[113 + 204 - 218 + 167], new ItemFood(0x7A ^ 0x7E, 0.1f, " ".length() != 0).setPotionEffect(Potion.hunger.id, 0x1A ^ 0x4, "".length(), 0.8f).setUnlocalizedName(Item.I[156 + 115 - 19 + 15]));
        registerItem(342 + 206 - 513 + 333, Item.I[125 + 253 - 258 + 148], new ItemEnderPearl().setUnlocalizedName(Item.I[95 + 46 - 128 + 256]));
        registerItem(143 + 264 - 328 + 290, Item.I[192 + 150 - 102 + 30], new Item().setUnlocalizedName(Item.I[218 + 196 - 349 + 206]).setCreativeTab(CreativeTabs.tabMaterials).setFull3D());
        registerItem(143 + 94 - 8 + 141, Item.I[179 + 198 - 312 + 207], new Item().setUnlocalizedName(Item.I[237 + 83 - 166 + 119]).setPotionEffect(Item.I[140 + 46 + 88 + 0]).setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(362 + 289 - 585 + 305, Item.I[55 + 209 - 198 + 209], new Item().setUnlocalizedName(Item.I[195 + 196 - 124 + 9]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(180 + 360 - 347 + 179, Item.I[135 + 20 - 27 + 149], new ItemSeeds(Blocks.nether_wart, Blocks.soul_sand).setUnlocalizedName(Item.I[181 + 213 - 313 + 197]).setPotionEffect(Item.I[138 + 118 - 158 + 181]));
        registerItem(342 + 115 - 222 + 138, Item.I[197 + 251 - 438 + 270], new ItemPotion().setUnlocalizedName(Item.I[108 + 37 + 91 + 45]));
        registerItem(209 + 184 - 132 + 113, Item.I[35 + 53 - 38 + 232], new ItemGlassBottle().setUnlocalizedName(Item.I[247 + 119 - 175 + 92]));
        registerItem(358 + 302 - 412 + 127, Item.I[150 + 242 - 113 + 5], new ItemFood("  ".length(), 0.8f, "".length() != 0).setPotionEffect(Potion.poison.id, 0x3D ^ 0x38, "".length(), 1.0f).setUnlocalizedName(Item.I[235 + 118 - 102 + 34]).setPotionEffect(Item.I[192 + 59 + 30 + 5]));
        registerItem(28 + 258 + 68 + 22, Item.I[244 + 89 - 260 + 214], new Item().setUnlocalizedName(Item.I[185 + 62 - 148 + 189]).setPotionEffect(Item.I[11 + 248 - 26 + 56]).setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(148 + 317 - 386 + 298, Item.I[117 + 95 + 43 + 35], new Item().setUnlocalizedName(Item.I[142 + 97 - 16 + 68]).setPotionEffect(Item.I[243 + 219 - 221 + 51]).setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(368 + 261 - 449 + 198, Item.I[283 + 145 - 306 + 171], new Item().setUnlocalizedName(Item.I[94 + 13 - 14 + 201]).setPotionEffect(Item.I[233 + 121 - 168 + 109]).setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(358 + 86 - 121 + 56, Item.I[47 + 212 - 216 + 253], new ItemReed(Blocks.brewing_stand).setUnlocalizedName(Item.I[241 + 23 - 174 + 207]).setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(378 + 124 - 453 + 331, Item.I[154 + 36 + 24 + 84], new ItemReed(Blocks.cauldron).setUnlocalizedName(Item.I[219 + 50 - 149 + 179]).setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(319 + 23 + 25 + 14, Item.I[5 + 39 + 128 + 128], new ItemEnderEye().setUnlocalizedName(Item.I[172 + 108 + 15 + 6]));
        registerItem(279 + 22 - 208 + 289, Item.I[278 + 154 - 147 + 17], new Item().setUnlocalizedName(Item.I[18 + 302 - 118 + 101]).setPotionEffect(Item.I[172 + 158 - 140 + 114]).setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(382 + 50 - 186 + 137, Item.I[298 + 191 - 458 + 274], new ItemMonsterPlacer().setUnlocalizedName(Item.I[182 + 298 - 358 + 184]));
        registerItem(361 + 285 - 572 + 310, Item.I[81 + 19 - 33 + 240], new ItemExpBottle().setUnlocalizedName(Item.I[282 + 88 - 115 + 53]));
        registerItem(284 + 190 - 278 + 189, Item.I[92 + 128 - 105 + 194], new ItemFireball().setUnlocalizedName(Item.I[257 + 290 - 244 + 7]));
        registerItem(368 + 311 - 380 + 87, Item.I[27 + 117 - 32 + 199], new ItemWritableBook().setUnlocalizedName(Item.I[290 + 105 - 336 + 253]).setCreativeTab(CreativeTabs.tabMisc));
        registerItem(27 + 158 - 144 + 346, Item.I[86 + 150 - 38 + 115], new ItemEditableBook().setUnlocalizedName(Item.I[107 + 86 - 147 + 268]).setMaxStackSize(0x92 ^ 0x82));
        registerItem(239 + 288 - 469 + 330, Item.I[134 + 180 - 215 + 216], new Item().setUnlocalizedName(Item.I[239 + 261 - 495 + 311]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(144 + 55 + 163 + 27, Item.I[221 + 115 - 262 + 243], new ItemHangingEntity(EntityItemFrame.class).setUnlocalizedName(Item.I[82 + 239 - 214 + 211]));
        registerItem(185 + 365 - 286 + 126, Item.I[216 + 215 - 427 + 315], new ItemReed(Blocks.flower_pot).setUnlocalizedName(Item.I[285 + 277 - 542 + 300]).setCreativeTab(CreativeTabs.tabDecorations));
        registerItem(315 + 181 - 242 + 137, Item.I[222 + 169 - 272 + 202], new ItemSeedFood("   ".length(), 0.6f, Blocks.carrots, Blocks.farmland).setUnlocalizedName(Item.I[236 + 154 - 300 + 232]));
        registerItem(284 + 96 - 52 + 64, Item.I[51 + 228 - 143 + 187], new ItemSeedFood(" ".length(), 0.3f, Blocks.potatoes, Blocks.farmland).setUnlocalizedName(Item.I[155 + 158 - 204 + 215]));
        registerItem(23 + 379 - 93 + 84, Item.I[230 + 261 - 376 + 210], new ItemFood(0x86 ^ 0x83, 0.6f, "".length() != 0).setUnlocalizedName(Item.I[197 + 31 + 44 + 54]));
        registerItem(74 + 352 - 360 + 328, Item.I[1 + 10 + 45 + 271], new ItemFood("  ".length(), 0.3f, "".length() != 0).setPotionEffect(Potion.poison.id, 0x6A ^ 0x6F, "".length(), 0.6f).setUnlocalizedName(Item.I[0 + 184 + 121 + 23]));
        registerItem(98 + 161 - 119 + 255, Item.I[126 + 127 - 13 + 89], new ItemEmptyMap().setUnlocalizedName(Item.I[58 + 322 - 211 + 161]));
        registerItem(111 + 188 - 242 + 339, Item.I[326 + 0 - 270 + 275], new ItemFood(0x7F ^ 0x79, 1.2f, "".length() != 0).setUnlocalizedName(Item.I[258 + 266 - 225 + 33]).setPotionEffect(Item.I[38 + 3 + 5 + 287]).setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(88 + 125 - 41 + 225, Item.I[91 + 127 + 66 + 50], new ItemSkull().setUnlocalizedName(Item.I[233 + 76 - 248 + 274]));
        registerItem(356 + 305 - 491 + 228, Item.I[269 + 259 - 371 + 179], new ItemCarrotOnAStick().setUnlocalizedName(Item.I[128 + 296 - 302 + 215]));
        registerItem(375 + 143 - 209 + 90, Item.I[312 + 287 - 369 + 108], new ItemSimpleFoiled().setUnlocalizedName(Item.I[55 + 38 + 47 + 199]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(294 + 293 - 496 + 309, Item.I[193 + 36 - 10 + 121], new ItemFood(0x77 ^ 0x7F, 0.3f, "".length() != 0).setUnlocalizedName(Item.I[113 + 92 - 116 + 252]).setCreativeTab(CreativeTabs.tabFood));
        registerItem(109 + 77 + 209 + 6, Item.I[41 + 49 + 22 + 230], new ItemFirework().setUnlocalizedName(Item.I[189 + 247 - 268 + 175]));
        registerItem(162 + 334 - 191 + 97, Item.I[2 + 260 + 57 + 25], new ItemFireworkCharge().setUnlocalizedName(Item.I[323 + 35 - 323 + 310]).setCreativeTab(CreativeTabs.tabMisc));
        registerItem(99 + 154 + 99 + 51, Item.I[243 + 342 - 291 + 52], new ItemEnchantedBook().setMaxStackSize(" ".length()).setUnlocalizedName(Item.I[85 + 199 - 92 + 155]));
        registerItem(394 + 123 - 406 + 293, Item.I[80 + 168 - 133 + 233], new ItemReed(Blocks.unpowered_comparator).setUnlocalizedName(Item.I[19 + 255 - 94 + 169]).setCreativeTab(CreativeTabs.tabRedstone));
        registerItem(116 + 227 - 50 + 112, Item.I[324 + 118 - 432 + 340], new Item().setUnlocalizedName(Item.I[236 + 270 - 215 + 60]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(390 + 12 - 289 + 293, Item.I[265 + 319 - 377 + 145], new Item().setUnlocalizedName(Item.I[46 + 260 - 271 + 318]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(168 + 309 - 107 + 37, Item.I[25 + 346 - 45 + 28], new ItemMinecart(EntityMinecart.EnumMinecartType.TNT).setUnlocalizedName(Item.I[346 + 158 - 424 + 275]));
        registerItem(63 + 66 + 175 + 104, Item.I[223 + 62 - 117 + 188], new ItemMinecart(EntityMinecart.EnumMinecartType.HOPPER).setUnlocalizedName(Item.I[49 + 310 - 178 + 176]));
        registerItem(76 + 365 - 396 + 364, Item.I[202 + 36 + 21 + 99], new Item().setUnlocalizedName(Item.I[292 + 300 - 517 + 284]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(374 + 280 - 273 + 29, Item.I[211 + 294 - 282 + 137], new Item().setUnlocalizedName(Item.I[258 + 247 - 425 + 281]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(202 + 207 - 287 + 289, Item.I[346 + 260 - 497 + 253], new ItemFood("   ".length(), 0.3f, " ".length() != 0).setUnlocalizedName(Item.I[163 + 262 - 272 + 210]));
        registerItem(129 + 91 - 132 + 324, Item.I[190 + 26 - 32 + 180], new ItemFood(0x0 ^ 0x5, 0.6f, " ".length() != 0).setUnlocalizedName(Item.I[351 + 232 - 269 + 51]));
        registerItem(310 + 122 - 402 + 383, Item.I[347 + 364 - 484 + 139], new ItemSoup(0x58 ^ 0x52).setUnlocalizedName(Item.I[76 + 36 + 236 + 19]));
        registerItem(99 + 218 + 96 + 1, Item.I[295 + 163 - 416 + 326], new Item().setUnlocalizedName(Item.I[262 + 74 - 20 + 53]).setPotionEffect(Item.I[209 + 323 - 263 + 101]).setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(58 + 236 + 46 + 75, Item.I[6 + 260 + 103 + 2], new Item().setUnlocalizedName(Item.I[204 + 32 - 229 + 365]).setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(96 + 195 - 215 + 340, Item.I[33 + 361 - 66 + 45], new ItemArmorStand().setUnlocalizedName(Item.I[29 + 215 - 150 + 280]).setMaxStackSize(0x28 ^ 0x38));
        registerItem(175 + 247 - 319 + 314, Item.I[303 + 49 - 192 + 215], new Item().setUnlocalizedName(Item.I[192 + 65 - 184 + 303]).setMaxStackSize(" ".length()).setCreativeTab(CreativeTabs.tabMisc));
        registerItem(97 + 369 - 363 + 315, Item.I[66 + 177 - 47 + 181], new Item().setUnlocalizedName(Item.I[168 + 54 - 177 + 333]).setMaxStackSize(" ".length()).setCreativeTab(CreativeTabs.tabMisc));
        registerItem(49 + 283 - 235 + 322, Item.I[51 + 315 - 157 + 170], new Item().setUnlocalizedName(Item.I[219 + 213 - 65 + 13]).setMaxStackSize(" ".length()).setCreativeTab(CreativeTabs.tabMisc));
        registerItem(200 + 234 - 80 + 66, Item.I[337 + 371 - 437 + 110], new ItemLead().setUnlocalizedName(Item.I[102 + 312 - 260 + 228]));
        registerItem(407 + 152 - 251 + 113, Item.I[258 + 364 - 328 + 89], new ItemNameTag().setUnlocalizedName(Item.I[83 + 293 - 128 + 136]));
        registerItem(68 + 163 - 0 + 191, Item.I[105 + 217 - 316 + 379], new ItemMinecart(EntityMinecart.EnumMinecartType.COMMAND_BLOCK).setUnlocalizedName(Item.I[139 + 209 - 286 + 324]).setCreativeTab(null));
        registerItem(353 + 287 - 437 + 220, Item.I[351 + 42 - 288 + 282], new ItemFood("  ".length(), 0.3f, " ".length() != 0).setUnlocalizedName(Item.I[66 + 239 - 284 + 367]));
        registerItem(191 + 423 - 330 + 140, Item.I[104 + 371 - 471 + 385], new ItemFood(0x1D ^ 0x1B, 0.8f, " ".length() != 0).setUnlocalizedName(Item.I[19 + 101 + 212 + 58]));
        registerItem(308 + 255 - 235 + 97, Item.I[278 + 63 - 104 + 154], new ItemBanner().setUnlocalizedName(Item.I[354 + 173 - 142 + 7]));
        registerItem(310 + 223 - 106 + 0, Item.I[237 + 259 - 305 + 202], new ItemDoor(Blocks.spruce_door).setUnlocalizedName(Item.I[180 + 351 - 206 + 69]));
        registerItem(137 + 271 - 242 + 262, Item.I[243 + 1 + 123 + 28], new ItemDoor(Blocks.birch_door).setUnlocalizedName(Item.I[153 + 173 + 20 + 50]));
        registerItem(153 + 304 - 346 + 318, Item.I[162 + 76 + 110 + 49], new ItemDoor(Blocks.jungle_door).setUnlocalizedName(Item.I[156 + 242 - 17 + 17]));
        registerItem(191 + 91 + 123 + 25, Item.I[161 + 246 - 97 + 89], new ItemDoor(Blocks.acacia_door).setUnlocalizedName(Item.I[134 + 67 - 101 + 300]));
        registerItem(23 + 314 - 106 + 200, Item.I[45 + 198 - 127 + 285], new ItemDoor(Blocks.dark_oak_door).setUnlocalizedName(Item.I[227 + 71 - 28 + 132]));
        registerItem(2059 + 255 - 2251 + 2193, Item.I[384 + 187 - 267 + 99], new ItemRecord(Item.I[247 + 239 - 420 + 338]).setUnlocalizedName(Item.I[104 + 18 + 82 + 201]));
        registerItem(545 + 156 + 1123 + 433, Item.I[37 + 310 - 35 + 94], new ItemRecord(Item.I[43 + 320 - 47 + 91]).setUnlocalizedName(Item.I[298 + 156 - 179 + 133]));
        registerItem(78 + 97 + 569 + 1514, Item.I[244 + 393 - 398 + 170], new ItemRecord(Item.I[258 + 180 - 391 + 363]).setUnlocalizedName(Item.I[376 + 128 - 199 + 106]));
        registerItem(128 + 1735 - 1234 + 1630, Item.I[153 + 75 - 0 + 184], new ItemRecord(Item.I[364 + 281 - 524 + 292]).setUnlocalizedName(Item.I[179 + 14 + 78 + 143]));
        registerItem(1664 + 1492 - 3149 + 2253, Item.I[157 + 225 - 200 + 233], new ItemRecord(Item.I[373 + 85 - 226 + 184]).setUnlocalizedName(Item.I[364 + 173 - 303 + 183]));
        registerItem(489 + 2191 - 1665 + 1246, Item.I[130 + 69 + 140 + 79], new ItemRecord(Item.I[35 + 365 - 128 + 147]).setUnlocalizedName(Item.I[105 + 390 - 376 + 301]));
        registerItem(857 + 376 - 821 + 1850, Item.I[373 + 91 - 383 + 340], new ItemRecord(Item.I[70 + 289 - 3 + 66]).setUnlocalizedName(Item.I[116 + 81 - 39 + 265]));
        registerItem(2094 + 694 - 2387 + 1862, Item.I[370 + 364 - 569 + 259], new ItemRecord(Item.I[115 + 247 - 158 + 221]).setUnlocalizedName(Item.I[133 + 173 - 267 + 387]));
        registerItem(713 + 1330 - 1989 + 2210, Item.I[93 + 204 - 256 + 386], new ItemRecord(Item.I[113 + 312 - 329 + 332]).setUnlocalizedName(Item.I[247 + 141 - 153 + 194]));
        registerItem(1587 + 1556 - 2938 + 2060, Item.I[339 + 250 - 206 + 47], new ItemRecord(Item.I[179 + 193 + 13 + 46]).setUnlocalizedName(Item.I[382 + 387 - 401 + 64]));
        registerItem(804 + 1891 - 2013 + 1584, Item.I[7 + 169 + 20 + 237], new ItemRecord(Item.I[190 + 84 - 23 + 183]).setUnlocalizedName(Item.I[389 + 124 - 167 + 89]));
        registerItem(1813 + 1295 - 1195 + 354, Item.I[5 + 284 - 88 + 235], new ItemRecord(Item.I[86 + 134 + 11 + 206]).setUnlocalizedName(Item.I[53 + 283 - 93 + 195]));
    }
    
    public String getPotionEffect(final ItemStack itemStack) {
        return this.potionEffect;
    }
    
    public int getItemEnchantability() {
        return "".length();
    }
    
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        return 1.0f;
    }
    
    protected Item setMaxDamage(final int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }
    
    public Item getContainerItem() {
        return this.containerItem;
    }
    
    private static void I() {
        (I = new String[113 + 352 - 450 + 424])["".length()] = I(");\\'^_=\\L]^L,L_,JWL*^@XLR)H\\ XY=-T(,", "jyoak");
        Item.I[" ".length()] = I("", "CsJug");
        Item.I["  ".length()] = I("\u000f\u0013-\"z", "fgHOT");
        Item.I["   ".length()] = I(";\u001f< D", "RkYMj");
        Item.I[0x86 ^ 0x82] = I("T\u0001\u0002<'", "zocQB");
        Item.I[0x68 ^ 0x6D] = I("\u0011,\u0017'!", "bXxID");
        Item.I[0x4B ^ 0x4D] = I("6&%\u0007", "ROWsz");
        Item.I[0x8 ^ 0xF] = I("\u0018\u001b\u00046", "otkRl");
        Item.I[0x2 ^ 0xA] = I("\"%\u0016\u0003 ?#", "QDfoI");
        Item.I[0x8C ^ 0x85] = I("\u0000\u001b\u0017\"", "szyFE");
        Item.I[0xA4 ^ 0xAE] = I("8\u001f\u0014", "TpsBh");
        Item.I[0x6C ^ 0x67] = I("\u0016),", "zFKET");
        Item.I[0xA2 ^ 0xAE] = I("/*\u0003#\u00040", "CObUa");
        Item.I[0x55 ^ 0x58] = I("\t0-\u0015\u0015\u0016", "eULcp");
        Item.I[0x40 ^ 0x4E] = I("\u001b!\b\b4\r", "hQgfS");
        Item.I[0x34 ^ 0x3B] = I("\u0014\u0002<\u000b9\u0013\f<\n", "gcRoj");
        Item.I[0xAA ^ 0xBA] = I("9.\u0010?\u000e", "JFbJl");
        Item.I[0x8F ^ 0x9E] = I("$35%6", "CATVE");
        Item.I[0x81 ^ 0x93] = I("%\u0013\u001d\u0007", "Cvoiu");
        Item.I[0x97 ^ 0x84] = I("\"\u0002:<\u0000", "AnUHh");
        Item.I[0x64 ^ 0x70] = I(" 4.\u0013&4", "FXAdC");
        Item.I[0x1C ^ 0x9] = I("#! 1", "QNSTa");
        Item.I[0x6E ^ 0x78] = I("\u0014 &\u0001\u000348(\r", "gTIof");
        Item.I[0x74 ^ 0x63] = I(".8\"\u00143&%\u001f\u0013(-2\t\u0000 ", "CWLgG");
        Item.I[0x8 ^ 0x10] = I("\u0006\u0017<\u0017 \u0017\u0011:\u001a.\u0006\u000e<\u00161\u001d", "ucSyE");
        Item.I[0x2B ^ 0x32] = I(";:\u001b>\u0005 4\u0016", "LUtZV");
        Item.I[0x70 ^ 0x6A] = I("\u000f#\u0004/\u0005\t\u001b\u0007!\u0005", "lLfMi");
        Item.I[0x5 ^ 0x1E] = I("\t\u001b\u0012!-", "hudHA");
        Item.I[0x63 ^ 0x7F] = I("\u000f7\u0011\u0018\u0010\u0007&", "kRwye");
        Item.I[0x41 ^ 0x5C] = I("\u000b\u001f\u0004'\u0015\u0004\u0012\t", "hwmTp");
        Item.I[0x2F ^ 0x31] = I("*\u001f\u0014$2", "FvzAA");
        Item.I[0xE ^ 0x11] = I("+0\f3\u0017 \u0007\u0001.\u00001", "ZEmAc");
        Item.I[0x82 ^ 0xA2] = I("!\u0015\b\r\u001c#\u000b\r\u0011:'\u001d:\u00005+\u0017\f\u0010", "ByitT");
        Item.I[0x8F ^ 0xAE] = I("?#\u0019>1)>\u00067\u0006", "HLvRr");
        Item.I[0x3B ^ 0x19] = I("&\r\u0012#5'2\u000b 76", "BbgAY");
        Item.I[0xA2 ^ 0x81] = I(")1%\f\u0018?!\u0003\t\u0017)6", "ZEDev");
        Item.I[0x71 ^ 0x55] = I("'?1\u000b?1/\u0017\u000e0'8\u0000\u0003?1", "TKPbQ");
        Item.I[0x8C ^ 0xA9] = I("\u001c\u0018\u00101\u001c\r\u0018\u0010,\u0014", "ljyBq");
        Item.I[0x32 ^ 0x14] = I("\u000b-\u001d\u0017\t\u0017,*0\u0007\u0017-", "yHyDh");
        Item.I[0x3A ^ 0x1D] = I("\u0001\f*\u001b\u0003!\u0014$\u0017T", "rxEuf");
        Item.I[0x84 ^ 0xAC] = I("+8\u000e\u0004\u00071\"\u000e\u001c=.", "BJajX");
        Item.I[0xB8 ^ 0x91] = I("\n>\t4\u000b\u0015\u001f\u0014-\u0000", "yVfBn");
        Item.I[0x7 ^ 0x2D] = I("\u0007\u0018\f/\f\u001e\u0003\u0000*2\u0016\u000f", "njcAS");
        Item.I[0xAE ^ 0x85] = I("%+(\t\u0010-'\u0002\u0010\u001e;", "UBKbq");
        Item.I[0x8 ^ 0x24] = I("\u0007\u0018<$<\u000f\u00126", "njSJc");
        Item.I[0x74 ^ 0x59] = I("\u001e\f.\u00041\u0013\u0019\u0013\u00156\u0018", "vmZgY");
        Item.I[0x12 ^ 0x3C] = I("<\r\u001d4\u0016\u0005\u0000\u001a>=)\u0015\u0011?\u000e", "ZatZb");
        Item.I[0x3B ^ 0x14] = I("/6\u0013\u00165\b4\u001e+5,?\u0016", "IZzxA");
        Item.I[0xF7 ^ 0xC7] = I("\u0004\u0014\u0011!(", "edaMM");
        Item.I[0x46 ^ 0x77] = I("6\u0015'\u0002\t", "WeWnl");
        Item.I[0x3 ^ 0x31] = I("\u0012\u0017\u0006", "pxqhH");
        Item.I[0x61 ^ 0x52] = I("&\t\r", "Dfzen");
        Item.I[0x1F ^ 0x2B] = I("\u0004\u001a\u001c\u0005$", "ehnjS");
        Item.I[0xB ^ 0x3E] = I("\r*=(\u001d", "lXOGj");
        Item.I[0x81 ^ 0xB7] = I("\u001b\u001b\u0015%", "xttIp");
        Item.I[0xA5 ^ 0x92] = I("+\u001c4 ", "HsULQ");
        Item.I[0x76 ^ 0x4E] = I("\u0010\u001b#\u00039\u001a\u0016", "trBnV");
        Item.I[0x22 ^ 0x1B] = I("\u000b-0\u0006\u001a\u0001 ", "oDQku");
        Item.I[0x2C ^ 0x16] = I("30\u0003\u001e\u00163,\u000b\u001f=", "ZBlpI");
        Item.I[0xA8 ^ 0x93] = I("\u0013\u0003\u0003'\u00183\u001f\u000b&", "zmdHl");
        Item.I[0xAC ^ 0x90] = I("1\u0001#6\u001a?\u0000(=1", "VnORE");
        Item.I[0x2 ^ 0x3F] = I("?\u0019!+\u0015\u0011\u0018* ", "VwFDa");
        Item.I[0x9A ^ 0xA4] = I("#\u0013*6\u001c9\u0016**'", "JaEXC");
        Item.I[0x66 ^ 0x59] = I("\u001d\u000f-\u0006\u0010'\n-\u001a", "nxBtt");
        Item.I[0x2D ^ 0x6D] = I("49\u001e\u00156-\t\u0002\u0006<12", "CVqqS");
        Item.I[0x53 ^ 0x12] = I("?\u0013\u0003'\u000e\u001b\u000b\u00031", "LdlUj");
        Item.I[0x78 ^ 0x3A] = I("3\u0017\u0005\b\u0010*'\u0019\u0004\u001a2\u001d\u0006", "Dxjlu");
        Item.I[0xE8 ^ 0xAB] = I("\u0003\u001d\u0001?\u0002\u001c\"\u0001&\u0003", "punIg");
        Item.I[0x7A ^ 0x3E] = I("\r%\f.\f\u0014\u0015\u0013#\n\u0011+\u001b/", "zJcJi");
        Item.I[0x6C ^ 0x29] = I("\u0011//\n\u0010\u0019#\u001b\u000e\u001e\u0005", "aFLaq");
        Item.I[0x1 ^ 0x47] = I("=\u0000\u000e).$0\u00005.", "JoaMK");
        Item.I[0x58 ^ 0x1F] = I("); *:$.\u0003&=%", "AZTIR");
        Item.I[0x22 ^ 0x6A] = I("\"\u0013\u0016(\u0012\u000e\u0014\u000e)\u00055", "QgyFw");
        Item.I[0x1F ^ 0x56] = I("\u00053.#\n%0.?\u000b", "vDAQn");
        Item.I[0x49 ^ 0x3] = I("\u0007\u0000(\u0000\u0006+\u0007/\u0001\u0015\u0011\u0018", "ttGnc");
        Item.I[0xC1 ^ 0x8A] = I("6\u0018\u0000,\u0004)#\u001b5\u000f ", "EpoZa");
        Item.I[0x32 ^ 0x7E] = I("\u0000\u001c\u0019'\u001c,\u0018\u001f*\u0012\u0012\u0010\u0013", "shvIy");
        Item.I[0xD3 ^ 0x9E] = I("\u0006\u000e%-\t\u000e\u0002\u00152\u0007\u0018\u0002", "vgFFh");
        Item.I[0x3C ^ 0x72] = I("\u0006!\u0019\u001c\u0012*4\u000e\u0017", "uUvrw");
        Item.I[0xE3 ^ 0xAC] = I("\u0006%\u001e;<\u000b09,;\u0000!", "nDjXT");
        Item.I[0x6E ^ 0x3E] = I("\"#*\u0006-(.\u0014\u00185)8/", "FJKkB");
        Item.I[0x28 ^ 0x79] = I("\u00169+\u00161!'%\t:\u000b*", "eNDdU");
        Item.I[0x71 ^ 0x23] = I("4\u000b&/\u0019>\u0006\u00181\u001e?\u0014\".", "PbGBv");
        Item.I[0xF4 ^ 0xA7] = I("\u001a\u001b9<.\u00057?+&\u0006\u001d2", "isVJK");
        Item.I[0x2 ^ 0x56] = I("\u000b+%'+\u0001&\u001b:-\f)%2!", "oBDJD");
        Item.I[0xDF ^ 0x8A] = I("7>)<\u0003?2\u000e>\u0003*8$3", "GWJWb");
        Item.I[0xD ^ 0x5B] = I("=/\u0015\t\t7\"+\u0005\u001e<", "YFtdf");
        Item.I[0xD7 ^ 0x80] = I("\u000b9>\u0005\u0002\u0006,\u000e\u000f\u000b\u000e7$\u0002", "cXJfj");
        Item.I[0x1A ^ 0x42] = I("\u0010\u000e\u001c\u0001\n", "czuba");
        Item.I[0x64 ^ 0x3D] = I("\u001e\u0000\u0005\u0011\u0001", "mtlrj");
        Item.I[0xE1 ^ 0xBB] = I("\u000e\"\u001c\u001f", "lMksX");
        Item.I[0xEC ^ 0xB7] = I(",#\u0016%", "NLaIE");
        Item.I[0xEB ^ 0xB7] = I("\u001b%\u0014#\u0011\u0019?\n\u0014\u0010\u00025\u0010", "vPgKc");
        Item.I[0x9D ^ 0xC0] = I("*\u001d;#3(\u0007%\u00185\"\u001f", "GhHKA");
        Item.I[0x4D ^ 0x13] = I("/\f\u00076!&<\u0018%+:\u0007", "HckRD");
        Item.I[0x36 ^ 0x69] = I("\u0011$&$7%<%2", "bSIVS");
        Item.I[0x24 ^ 0x44] = I("\b\u0018\u00012\u0014\u0001(\u001e>\u001e\u0019\u0012\u0001", "owmVq");
        Item.I[0x33 ^ 0x52] = I("\u001c\u0011\u0019'=\u0003>\u0019=<", "oyvQX");
        Item.I[0xB ^ 0x69] = I(")%\u0006>\u0011 \u0015\u001a3\u0017%+\u0012?", "NJjZt");
        Item.I[0xC6 ^ 0xA5] = I("\u001a\u001f1\u00188\u0012\u0013\u0015\u001c5\u000e", "jvRsY");
        Item.I[0x5C ^ 0x38] = I(">\t 1\n79--\n", "YfLUo");
        Item.I[0x24 ^ 0x41] = I("2\u0000\u0010\t\n?\u0015#\u0005\u000e>", "Zadjb");
        Item.I[0xEC ^ 0x8A] = I("\u0011\u0007$&\b\u0005", "bsVOf");
        Item.I[0x2C ^ 0x4B] = I("\u001a\r6+\u0000\u000e", "iyDBn");
        Item.I[0x29 ^ 0x41] = I("7\u0006\u0010\u001d#4\u0011", "QcqiK");
        Item.I[0xC9 ^ 0xA0] = I("-\u0014\u001b>\f.\u0003", "KqzJd");
        Item.I[0x66 ^ 0xC] = I("\u0005\u0018\u0003$\"\u0015\t\b&", "bmmTM");
        Item.I[0x44 ^ 0x2F] = I("\u0006,\u000b?;\u0000+", "uYgOS");
        Item.I[0x1A ^ 0x76] = I("XtCLY@hFY", "sEwjh");
        Item.I[0x69 ^ 0x4] = I("<\u0002\r<\u000e%2\n7\u000e", "KmbXk");
        Item.I[0x18 ^ 0x76] = I("\r\u0002\u001c\u0004\u0002\n\t", "emySm");
        Item.I[0x7A ^ 0x15] = I("\u0018\u0006?\u001b-4\u001a?\u0010", "krPuH");
        Item.I[0x1A ^ 0x6A] = I("+7\u0007\u0004\u0003,6\u0007", "CXbWw");
        Item.I[0xC1 ^ 0xB0] = I("&\u0013?(.'\u000e5", "OaPFq");
        Item.I[0x6 ^ 0x74] = I("\u0002<-0%\u0005=", "jSHyW");
        Item.I[0x1C ^ 0x6F] = I("\u000b\u0001\u000f)\u0000\u0001\f1,\u0000\n", "ohnDo");
        Item.I[0xE2 ^ 0x96] = I("\t?\u00042\u0000\u0000=\u000e\u0018\r", "aPavi");
        Item.I[0x3A ^ 0x4F] = I("6\u001a\u001e\u0017!?*\u001a\u001c!", "QursD");
        Item.I[0x6C ^ 0x1A] = I("\u0012<\f\u001f7\u00167", "zSiXX");
        Item.I[0xE5 ^ 0x92] = I("!'\b\u00106\t<\b\u0014&%", "VOmqB");
        Item.I[0x61 ^ 0x19] = I("06*\f$", "CSOhW");
        Item.I[0xF7 ^ 0x8E] = I("\u0000\u0019.\f0", "wqKmD");
        Item.I[0x67 ^ 0x1D] = I("'=\u000f$\u0018", "PUjEl");
        Item.I[0xEC ^ 0x97] = I("&#\u0017\r\u0016", "DQrlr");
        Item.I[0x12 ^ 0x6E] = I(".\u0006\u000e\u00127", "LtksS");
        Item.I[0x31 ^ 0x4C] = I("6 +0??7\u0015,26(/0", "ZEJDW");
        Item.I[0xC7 ^ 0xB9] = I("\u0011\u001f\u0002\u001d-\r9\u0002\u001f<\u0011", "yznpH");
        Item.I[33 + 82 - 82 + 94] = I("<\u0004$\u0007/5\u0013\u001a\u0010/5\u00121\u0003+1\u0015 ", "PaEsG");
        Item.I[55 + 83 - 110 + 100] = I("1\u000e.\u0017#\"\n*\u00102\u0011\n$\u0010?", "RfKdW");
        Item.I[52 + 20 - 51 + 108] = I("\u000f<\r\u0000\u0006\u0006+3\u0018\u000b\u0004>\u0005\u001a\t\u0010", "cYltn");
        Item.I[3 + 39 + 27 + 61] = I("\u001b1\u001f\u0000\n\u00193\u000b$\u000f\u0018 \u0010", "wTxgc");
        Item.I[45 + 85 - 60 + 61] = I("/\u0011\n\u0000 &\u00064\u0016',\u0000\u0018", "CtktH");
        Item.I[22 + 112 - 50 + 48] = I("\u0005\u0007\u0005\u0001\u0014$\u0004\u0005\u0001\u000f", "ghjug");
        Item.I[73 + 124 - 124 + 60] = I("\u0001\u0000\u001b-<\u000f\t\u0013(\r\n\r\u0016)7\u0016", "bhzDR");
        Item.I[81 + 11 - 78 + 120] = I("\u0004\u001f*\u0018-\u00189.\u0014!\u0002", "lzFuH");
        Item.I[24 + 74 + 5 + 32] = I("\u0016\u001a3\r \u0018\u0013;\b\u0011\u0016\u001a7\u0017:\u0005\u001e3\u0010+", "urRdN");
        Item.I[71 + 6 - 32 + 91] = I("\u0000!\u001c\u001f\f\u0013%\u0018\u0018\u001d !\u0018\u0005\u0016", "cIylx");
        Item.I[6 + 16 + 56 + 59] = I("5>-\u001f\u0005;7%\u001a4:3+\u0011\u000281?", "VVLvk");
        Item.I[22 + 4 - 16 + 128] = I("4\u00175\u000e:6\u0015!*;9\u001b<", "XrRiS");
        Item.I[5 + 104 + 30 + 0] = I("\u0013+\u001b \u0004\u001d\"\u0013%5\u0012,\u0015=\u0019", "pCzIj");
        Item.I[78 + 1 - 27 + 88] = I("\t.\t&\u0011()\u0007;\f", "kAfRb");
        Item.I[51 + 120 - 45 + 15] = I("\r=\u001a<7\f*\u0019?\r\u0010", "dOuRh");
        Item.I[28 + 77 - 101 + 138] = I("\u0001 \r\u0019\u0002\u001d\f\u0013\u001b\t", "iEatg");
        Item.I[21 + 117 - 26 + 31] = I("<8\u0017\u001f86\"\u001d\u0002\u0013%&\u0019\u0005\u0002", "UJxqg");
        Item.I[142 + 94 - 220 + 128] = I("\u0013\n3\u001b\u0011\u0000\u000e7\u001c\u00009\u00109\u0006", "pbVhe");
        Item.I[132 + 138 - 216 + 91] = I("*\u0002-\u0001\u0018/\u0015%\b.-\u00171", "CpBoG");
        Item.I[26 + 7 + 60 + 53] = I("*\u0000\u0015\u0001\u000b(\u0002\u0001/\u0010)\u000b", "Ferfb");
        Item.I[71 + 59 + 6 + 11] = I("\u0005\u0007\f\t\u000f\u000e\u001a\f\u0013#", "lucgP");
        Item.I[57 + 53 - 44 + 82] = I("\u000e;5\u0001\u0017%&5\u001b", "lTZud");
        Item.I[63 + 2 - 9 + 93] = I("\u0006'-\u00075\f*\u0013\u0002?\u000e#)\u001e", "bNLjZ");
        Item.I[102 + 68 - 84 + 64] = I(" \u0006\u000550<'\u000098'\r\r", "HciXU");
        Item.I[146 + 102 - 133 + 36] = I(">\u001f6\u0000%4\u0012\b\u000e\"?\u0005#\u001d&;\u00022", "ZvWmJ");
        Item.I[86 + 137 - 88 + 17] = I("()\u0002\u0005\u001d;-\u0006\u0002\f\u000f(\u0006\u001b\u0006%%", "KAgvi");
        Item.I[58 + 135 - 113 + 73] = I("1\"\u0010\u0000\u001a;/.\u0001\u00102,\u0018\u0003\u0012&", "UKqmu");
        Item.I[97 + 26 - 59 + 90] = I("\u0014)\t?\u0006\u0016+\u001d\u001c\u0006\u0019!\u00016\u000b", "xLnXo");
        Item.I[119 + 108 - 90 + 18] = I("\u001c!7\u001c.\u0016,\t\u0013.\u0017<%", "xHVqA");
        Item.I[136 + 105 - 109 + 24] = I("\r?. 0+9 9,\u00014", "oPATC");
        Item.I[115 + 97 - 106 + 51] = I("->\u000e\u001e\u0003$\u000e\n\u001f\n'4\u0016", "JQbzf");
        Item.I[150 + 21 - 36 + 23] = I("\u00063\b\u001e \u001a\u0011\u000b\u001f!", "nVdsE");
        Item.I[125 + 10 - 0 + 24] = I("\t\u001e/\u0011\u0011\u0000. \u001d\u0011\u001d\u00053\u0019\u0015\u001a\u0014", "nqCut");
        Item.I[94 + 113 - 134 + 87] = I(";)\u0002?\u001e(-\u00068\u000f\u001f.\u000b(", "XAgLj");
        Item.I[73 + 71 - 101 + 118] = I("\u0003!(\u0017\u0012\n\u0011(\u0016\u0010\u0003'*\u0014\u0004", "dNDsw");
        Item.I[9 + 98 - 2 + 57] = I("8\u001d\u0010)+:\u001f\u0004\t-8\u001c", "TxwNB");
        Item.I[20 + 33 + 17 + 93] = I("(%552!\u0015;>8;9", "OJYQW");
        Item.I[84 + 127 - 128 + 81] = I("&\b\u0002\u0001\u0010\u0003\b\u0001\u0011", "Dgmuc");
        Item.I[34 + 106 + 18 + 7] = I("\u0000/\u001b=%", "fCrSQ");
        Item.I[144 + 152 - 264 + 134] = I("+639\u0019", "MZZWm");
        Item.I[123 + 143 - 247 + 148] = I("$\f\u0016\u001a:<\f\u0014", "TcdqY");
        Item.I[49 + 130 - 160 + 149] = I("\u001d6\u0004\t,\u00056\u00060.\u001a", "mYvbO");
        Item.I[158 + 43 - 118 + 86] = I("\n\u0016\"\b/\r&=\f8\u0002\u001a%\f:", "iyMcJ");
        Item.I[126 + 133 - 134 + 45] = I("\u001b\u00186)\u0017\u0003\u00184\u0001\u001b\u0004\u001c!&", "kwDBt");
        Item.I[8 + 8 + 68 + 87] = I("\n%/\u001e\u0010\u0013*!", "zDFpd");
        Item.I[150 + 63 - 111 + 70] = I("1\r\u0001=\f(\u0002\u000f", "AlhSx");
        Item.I[142 + 171 - 255 + 115] = I("\b\u000e<)$\u0001>1=1\u0003\u0004", "oaPMA");
        Item.I[116 + 127 - 188 + 119] = I("#\u001a\n5.\u0005\u0005\u0016=", "BjzYK");
        Item.I[146 + 85 - 78 + 22] = I(" <,&", "SUKHy");
        Item.I[130 + 103 - 178 + 121] = I("\u0016\u0002!&", "ekFHD");
        Item.I[126 + 36 - 27 + 42] = I("08\u001b\u0005')\b\u0010\u000e-5", "GWtaB");
        Item.I[113 + 75 - 134 + 124] = I("\u0015\u000e81!\u0010\n", "qaWCn");
        Item.I[130 + 162 - 168 + 55] = I("\u000e2\u00079\u0007\u0018", "lGdRb");
        Item.I[52 + 105 - 23 + 46] = I("4,\b:\u0010\"", "VYkQu");
        Item.I[113 + 162 - 115 + 21] = I("\u001b#\u0015\u0002\u00033 \u0014\u0004\u001a\t6", "lBagq");
        Item.I[27 + 135 - 50 + 70] = I("\u0001\u0003\u0015\u00001\u0017!\u0017\u001f1\u0011", "cvvkT");
        Item.I[168 + 92 - 111 + 34] = I("&&\u001c\n\u000b(2\t\u00001>", "JGjkT");
        Item.I[143 + 129 - 210 + 122] = I("\u000b\u00026<\u0015\u001d;4!\u0011", "iwUWp");
        Item.I[32 + 43 + 60 + 50] = I("< 4\r\u00070;.", "QIZhd");
        Item.I[25 + 168 - 80 + 73] = I("\u0000\u000f\t)\u001a\f\u0014\u0013", "mfgLy");
        Item.I[25 + 81 + 21 + 60] = I(":9 \u000b\u000e,", "IXDob");
        Item.I[158 + 3 + 3 + 24] = I("\u001d\u0006.\u0016\u001b\u000b", "ngJrw");
        Item.I[21 + 136 - 152 + 184] = I("\"<\u0004\u000b4/!\u0004\u0017", "KNkek");
        Item.I[70 + 107 - 160 + 173] = I("\t\u0003$\u001e:\u001f\u0003%", "mlKls");
        Item.I[188 + 43 - 102 + 62] = I(" \u00115<\u001a=\u001a4", "RtQOn");
        Item.I[97 + 113 - 28 + 10] = I("\n\"#\u0015\u0013\u0017)\"", "xGGfg");
        Item.I[3 + 182 - 176 + 184] = I("NzdXgT", "cOOnJ");
        Item.I[22 + 181 - 102 + 93] = I("0 \u00044!\"\"\u0007", "CNkCC");
        Item.I[164 + 123 - 271 + 179] = I("7\t\u0016#\u000e%\u000b\u0015", "DgyTl");
        Item.I[95 + 175 - 173 + 99] = I("\u000e$\u0010-", "lKqYu");
        Item.I[60 + 123 - 159 + 173] = I("))\u0010\u0019", "KFqmI");
        Item.I[44 + 117 - 149 + 186] = I(" \u0004\u00053\f)\u0013", "LadGd");
        Item.I[167 + 25 - 0 + 7] = I("\u0019\u000b\u000b;\u0006\u0010\u001c", "unjOn");
        Item.I[1 + 9 + 59 + 131] = I("?\u001e(:;0\u0002':\u0001&", "RwDQd");
        Item.I[130 + 115 - 68 + 24] = I("*9'/", "GPKDH");
        Item.I[95 + 193 - 149 + 63] = I(")?1+\u0011", "KMXHz");
        Item.I[84 + 178 - 111 + 52] = I("&\u0014\u0006\u0010\u0011", "Dfosz");
        Item.I[196 + 60 - 176 + 124] = I("7\u001d&*\u00066\u0010+?", "TqGSY");
        Item.I[172 + 114 - 127 + 46] = I("\u001b.6-", "xBWTN");
        Item.I[76 + 110 + 2 + 18] = I("4/+1\u001f", "FJNUl");
        Item.I[81 + 205 - 276 + 197] = I("\u0004,\u0000)!", "vIeMR");
        Item.I[195 + 95 - 143 + 61] = I("\b\"3\t\u001f", "xCClm");
        Item.I[62 + 192 - 154 + 109] = I("\u0018;\u0012\u0006\u0003", "hZbcq");
        Item.I[203 + 64 - 153 + 96] = I("\u001a\t51", "xfZZC");
        Item.I[139 + 190 - 225 + 107] = I("'+\u000b\u0007", "EDdlN");
        Item.I[28 + 157 - 177 + 204] = I("\u001b!\u0003\u0017 7/\u000b\u0016)", "hMjzE");
        Item.I[117 + 73 - 39 + 62] = I("\u0017*\u000e:3\u0006'\u000b;", "dFgWV");
        Item.I[186 + 41 - 209 + 196] = I("\u0013<1\u001d#/9=\u00002\u00135&\u001a", "pTTnW");
        Item.I[53 + 178 - 20 + 4] = I("4(\u00145\u000183\u000e\u0013\n<2\u000e", "YAzPb");
        Item.I[61 + 91 + 19 + 45] = I("(\u0014\u0010\u0016\u0012-\u0004=\u0015\u001a \u0004\u0001\u0019\u0001:", "Nabxs");
        Item.I[21 + 96 - 107 + 207] = I("7\u0019\u0001\n3;\u0002\u001b)%(\u001e\u000e\f5", "ZpooP");
        Item.I[41 + 74 + 62 + 41] = I("\u000b!\u0003", "nFdKP");
        Item.I[16 + 38 - 31 + 196] = I("5\f(", "PkOxR");
        Item.I[63 + 112 - 17 + 62] = I("\u0011**\u00130\u00016", "rEGcQ");
        Item.I[113 + 24 - 136 + 220] = I("!'/\u0015#1;", "BHBeB");
        Item.I[82 + 147 - 19 + 12] = I("\u0014\u001a4\"*\u001c\u0014\u00188,\u0016", "rsGJC");
        Item.I[216 + 104 - 179 + 82] = I("\",\t'\u0006*\"( \u000b", "DEzOo");
        Item.I[111 + 165 - 122 + 70] = I(",\u0019\u00052\n", "OujQa");
        Item.I[207 + 131 - 318 + 205] = I("\u0013\u0002533", "pnZPX");
        Item.I[5 + 70 + 13 + 138] = I("-\u0007'\u000f\u0005>\u0004&\u001d).\u001e;\f", "JkHxv");
        Item.I[193 + 177 - 332 + 189] = I(";\u0016\u0000-\u001c57\u00192\u0007", "BslAs");
        Item.I[57 + 46 + 43 + 82] = I("zV`Qtf", "QcMgY");
        Item.I[157 + 183 - 216 + 105] = I("\u0014\u000b7-", "rbDEJ");
        Item.I[10 + 60 + 41 + 119] = I("#\u001c\u00061", "EuuYC");
        Item.I[226 + 60 - 132 + 77] = I("/-\u0005\u000e\u001d(\u001d\f\f\u000b$", "LBjex");
        Item.I[171 + 184 - 213 + 90] = I("4\r2\u001e", "RdAvh");
        Item.I[54 + 109 - 132 + 202] = I("+3\f", "OJiLr");
        Item.I[50 + 175 - 87 + 96] = I("\u0015 .\u001b\u0002\u0006=.9", "qYKKm");
        Item.I[2 + 85 + 138 + 10] = I("\u000f\u0002\u0004\u0010", "mmjuG");
        Item.I[187 + 205 - 269 + 113] = I("\u000b\u0017\u0002\u0014", "ixlqe");
        Item.I[2 + 102 + 27 + 106] = I(" 4\u0012-\u001c", "SAuLn");
        Item.I[81 + 207 - 240 + 190] = I("2,.4\u0000", "AYIUr");
        Item.I[4 + 113 + 65 + 57] = I("egS~AzzKiXecS~_", "HWxOl");
        Item.I[109 + 2 + 105 + 24] = I("\n\u0000\u0004-", "iaoHo");
        Item.I[14 + 20 + 50 + 157] = I("13' ", "RRLEl");
        Item.I[12 + 99 - 39 + 170] = I("%4\u0017", "GQsqs");
        Item.I[41 + 176 + 25 + 1] = I("\u0014 5", "vEQEF");
        Item.I[75 + 42 + 45 + 82] = I("43\u0004\f\u001423\u0006", "FVtiu");
        Item.I[22 + 207 - 170 + 186] = I("!.&7\u0006", "EGISc");
        Item.I[123 + 102 - 109 + 130] = I("\u0015*\u0004'\u0003\u0013", "vEkLj");
        Item.I[193 + 173 - 228 + 109] = I("\u0001-.13\u0007", "bBAZZ");
        Item.I[42 + 178 - 154 + 182] = I("\u00109\n\u0000\f\u0012\u000f\u000b\r\u0019", "vPfli");
        Item.I[116 + 38 - 3 + 98] = I("\u000e\u0011\u0014", "cpdfV");
        Item.I[178 + 90 - 244 + 226] = I("\u0015=\b*4\u0015", "fUmKF");
        Item.I[185 + 115 - 133 + 84] = I("5\t0\u0012%5", "FaUsW");
        Item.I[91 + 64 - 20 + 117] = I("+&*\u0001-", "FCFnC");
        Item.I[93 + 235 - 83 + 8] = I("+1\r \f", "FTaOb");
        Item.I[110 + 59 + 22 + 63] = I("\u0001\u0012\u0018\u0016\u0005\u0018\t*\u0015\u000b\u0014\u0003\u0006", "qgufn");
        Item.I[240 + 53 - 275 + 237] = I("\u0007\b5\u0017\u0017+\u001d%\u001e\u0014\u001f\u0004>", "tmPsd");
        Item.I[123 + 157 - 227 + 203] = I("\u001a2-?\n($$5\u0000\u0004", "wWAPd");
        Item.I[88 + 27 + 9 + 133] = I(":\u0006\n.\u0003\u0016\u000e\n&\u001f'", "IcoJp");
        Item.I[239 + 204 - 311 + 126] = I(" 3.\u0004", "BVKbW");
        Item.I[200 + 182 - 226 + 103] = I(" *\f\u0010\u0005#8", "BOivW");
        Item.I[122 + 28 - 19 + 129] = I("7$\u0000\u001d/0\u0014\r\u0013/2", "TKovJ");
        Item.I[46 + 166 - 19 + 68] = I("\u001b\u001f \u001f\u000f\u0016\u0015.\u001c(", "yzEyL");
        Item.I[48 + 151 - 131 + 194] = I("!\u0019\b\u0019/'\u001f", "BqazD");
        Item.I[13 + 0 + 164 + 86] = I("):\r\u0006:/<6\u0004&", "JRdeQ");
        Item.I[259 + 243 - 371 + 133] = I("0#\u0016%\u00137\u0013\u001a&\u001f0'\u001c ", "SLyNv");
        Item.I[152 + 164 - 234 + 183] = I(".8\b\u0012\f(>\"\u001e\b&5\u0005", "MPaqg");
        Item.I[200 + 47 - 177 + 196] = I("\u0010\"\u00126-\f\u0012\u0000.-\u0011%", "bMfBH");
        Item.I[42 + 145 - 161 + 241] = I("\u0017\u0000\u0012.\b\u000b)\n?\u001e\r", "eofZm");
        Item.I[263 + 96 - 305 + 214] = I("4\u0019\u001e4\u0016\u000e\u0007\u001f0\u0016=", "QwzQd");
        Item.I[169 + 225 - 151 + 26] = I("\u0017-1)\u0005\"&4>\u001b", "rCULw");
        Item.I[48 + 254 - 289 + 257] = I("\u0010\u001e;\n.-\u00005\u0014", "rrZpK");
        Item.I[113 + 108 + 32 + 18] = I("\u0003\u00159 =3\u0016<", "ayXZX");
        Item.I[28 + 268 - 78 + 54] = I("- \n\u0000\u0013\u0015<\u000e\u0012\u0015", "JHksg");
        Item.I[77 + 127 + 22 + 47] = I("&$\f\u0010$\u0015)\f\u0011", "ALmcP");
        Item.I[237 + 247 - 388 + 178] = I("[RDCtBOZTm]VBCj", "pbirY");
        Item.I[196 + 129 - 201 + 151] = I("\u0014%(+\u000f\u001d?#(5\u0007", "sJDOP");
        Item.I[64 + 246 - 244 + 210] = I("\u0001=4&\u0001\u00135?';", "fRXBO");
        Item.I[221 + 101 - 98 + 53] = I("/\"\u0006=\u00103\u0018\u00054\u00075", "AGrUu");
        Item.I[10 + 38 + 75 + 155] = I("\u001f\u0003\u001b\u000f3\u00035\u001b\u0006:\u001a5\n\u00022\u0002", "qfogV");
        Item.I[230 + 27 - 40 + 62] = I("`W", "KcgsS");
        Item.I[276 + 196 - 274 + 82] = I("\u00038#\u0004\f\u001d", "sWWmc");
        Item.I[90 + 268 - 310 + 233] = I("\n#7\u000f\u0002\u0014", "zLCfm");
        Item.I[126 + 186 - 70 + 40] = I("\u0016\u00148\u001c\u001e.\u001a6\u001b\u0019\u001d\u001d", "qxYom");
        Item.I[176 + 167 - 205 + 145] = I("&6(\u001e \u00035=\u0019?$", "AZImS");
        Item.I[78 + 76 + 116 + 14] = I(">\u001f\n\u000e.?0\u0006\u0013.", "MocjK");
        Item.I[233 + 183 - 144 + 13] = I("\t).\u0013$\b\u001c>\u0012", "zYGwA");
        Item.I[270 + 12 - 211 + 215] = I("~\u007fxaGabfvX~{~a_", "SOUPl");
        Item.I[5 + 264 - 226 + 244] = I("0\u00123\u0002$8\u0003$\u000b\u001e%\u0007(\u000b$$($\u0016$", "VwAoA");
        Item.I[188 + 59 - 224 + 265] = I("1\u0011\u0019\u000f-9\u0000\u000e\u0006\u001b'\u001d\u000f\u0007:\u0012\r\u000e", "WtkbH");
        Item.I[85 + 138 - 12 + 78] = I("cSQc`zHKc", "NczPM");
        Item.I[68 + 37 + 64 + 121] = I("4\"\u0006<3\t>\b123<", "VNgFV");
        Item.I[59 + 75 + 61 + 96] = I("5\u001a4=\u000e\u0007\u0019\"#\u000e%", "WvUGk");
        Item.I[1 + 287 - 157 + 161] = I("b`giF{{y~_ddaiX", "IPJXk");
        Item.I[3 + 225 - 18 + 83] = I(")\b$\u0006\u0018\u001b\n1\u000e\u0018)", "DiCky");
        Item.I[60 + 236 - 62 + 60] = I("\u0007/,\n\n)<.\u0006\u0006", "jNKgk");
        Item.I[138 + 113 - 117 + 161] = I("xXC~[aE[iB~\\C~E", "ShhOv");
        Item.I[245 + 62 - 259 + 248] = I("\u0010\u001e(\u001a&\u001c\u000b\u0012\u001e;\u0013\u0002)", "rlMmO");
        Item.I[28 + 214 - 89 + 144] = I("\u001a\b\u0002\u0015\b\u0016\u001d4\u0016\u0000\u0016\u001e", "xzgba");
        Item.I[296 + 44 - 161 + 119] = I("\u000b9!4\t\u001a7:", "hXTXm");
        Item.I[45 + 133 - 102 + 223] = I("-/&(/<!=", "NNSDK");
        Item.I[225 + 296 - 503 + 282] = I("\t \u0000&\u001c3+\u001d&", "lNdCn");
        Item.I[5 + 2 + 195 + 99] = I("\u000f)4\u001e\u000b/>54\u001f", "jPQQm");
        Item.I[168 + 74 - 92 + 152] = I("$#2\u0016(;63*.2?8\u001b", "WSWuC");
        Item.I[282 + 127 - 395 + 289] = I("#8(\u0013\u0002<-)=\f<'#", "PHMpi");
        Item.I[11 + 136 - 6 + 163] = I("MEAwZTX_`EKAGwB", "fulFq");
        Item.I[71 + 33 - 78 + 279] = I(">\u0016\f\u0012\u0019\u0012\u0003\n\u0002", "Mfmew");
        Item.I[247 + 13 - 137 + 183] = I("\u000b$\f%\u001b\u000392:\u000e\u0005.\u0010", "fKbVo");
        Item.I[20 + 9 + 248 + 30] = I("\u0015;>4\u001f\u0019& 2\b/!!%\u0019\u001c&", "pCNQm");
        Item.I[140 + 172 - 241 + 237] = I("03\u0014\u000f\n!?\b(", "UKdMe");
        Item.I[71 + 171 - 130 + 197] = I("\u0001\u0013\u001e\u000f.\u0004\u0012\r\u0018\u0016\u0002", "gzljq");
        Item.I[179 + 57 - 157 + 231] = I("\u001f%\u0007\u0013,\u0018 \u0019", "yLuvN");
        Item.I[58 + 170 - 36 + 119] = I("\u0000\u001e\u0006\f\u0019\u0015\u0000\n'\u001a\u0018\u0003\u0004", "wloxx");
        Item.I[21 + 299 - 179 + 171] = I("/7\n\u0013&6\"!\b 3", "XEcgO");
        Item.I[171 + 64 - 104 + 182] = I("?\n?\u00010-\u0016\t\u0017+'\u0013", "HxVuD");
        Item.I[76 + 295 - 64 + 7] = I("\"\u00151\u0017,0\t\u001a\f7>", "UgXcX");
        Item.I[38 + 1 - 38 + 314] = I("\u0012\u001d\u000b\u00164\u001b\u0014", "wpndU");
        Item.I[277 + 87 - 237 + 189] = I("\r\u0006$\" \u0004\u000f", "hkAPA");
        Item.I[17 + 242 + 37 + 21] = I("\u0010?*(+\u001f9.(\u0011", "yKOEt");
        Item.I[130 + 156 - 275 + 307] = I("3\u0018+**", "UjJGO");
        Item.I[154 + 232 - 269 + 202] = I("<\u001c\u001f\u0000\u001f(/\u0000\u0018\u000e", "Zppwz");
        Item.I[114 + 309 - 112 + 9] = I("\u00145;\u0016\u0003\u0000\t;\u0015", "rYTaf");
        Item.I[115 + 185 - 262 + 283] = I(".716\u00199", "MVCDv");
        Item.I[41 + 160 + 1 + 120] = I("-\u000e\u001b\u0016<:\u001c", "NoidS");
        Item.I[285 + 74 - 57 + 21] = I("\u001e5\u0006\u00076\u0001", "nZrfB");
        Item.I[142 + 97 - 76 + 161] = I("<!.6\u0016#", "LNZWb");
        Item.I[193 + 117 - 98 + 113] = I("+6\u000f\u001d2\u0016'\u000b\f7=8", "IWdxV");
        Item.I[165 + 181 - 287 + 267] = I(">\u001a'\t5!72\u0003$*", "NuShA");
        Item.I[139 + 55 + 83 + 50] = I("\u001a)\u00001#\u0004)\u001c1\u0013\u001a)\u001d#8\u0005", "jFiBL");
        Item.I[221 + 252 - 248 + 103] = I("37>3\u0012,\b%;\u0015,6%'\u0015", "CXJRf");
        Item.I[48 + 50 + 189 + 42] = I("/\u0007<", "BfLSF");
        Item.I[260 + 224 - 184 + 30] = I("\u0001\u0004\b\u0015\u0010)\b\b", "dixai");
        Item.I[217 + 121 - 44 + 37] = I("!\u0017)\u000f\u000f('&\n\u00184\u00171", "FxEkj");
        Item.I[60 + 269 - 231 + 234] = I("4\f76\u0016#**(\u001d2\u0003", "WmEDy");
        Item.I[50 + 184 + 94 + 5] = I("TV_kQKKGqKJ@@wN", "yftZz");
        Item.I[145 + 165 - 186 + 210] = I("6\n0!\u0014", "EaEMx");
        Item.I[35 + 212 - 231 + 319] = I("#9 *+", "PRUFG");
        Item.I[104 + 260 - 184 + 156] = I("\u00122&9*\u0005\f;%\u001a\u0010\f'?,\u00128", "qSTKE");
        Item.I[212 + 5 + 26 + 94] = I("\t,4?\u001a\u001e\u0002(\f&\u001e$%&", "jMFMu");
        Item.I[60 + 112 - 5 + 171] = I("63=\u000f2*\t:\u00136*", "XVIgW");
        Item.I[228 + 50 + 29 + 32] = I("$\u001f7\u0010\u00038)7\u0019\u0014", "JzCxf");
        Item.I[17 + 5 + 148 + 170] = I("\u0005\r=8\b\u001c\u0016\u000f8\n\u0010", "uxPHc");
        Item.I[275 + 305 - 441 + 202] = I("\u001c\u0011\u001d\u0006(\u0005\n \u001f&", "ldpvC");
        Item.I[272 + 231 - 376 + 215] = I("\u0000\u001a$\u001f4\t\u0001=\t", "fsVzC");
        Item.I[3 + 205 - 194 + 329] = I("\u001c\f\u0001(\u0013\u0015\u0017\u0018>", "zesMd");
        Item.I[138 + 43 + 4 + 159] = I("1\u001f\b\b\u00198\u0004\u00112\r?\u0017\b\n\u000b", "Wvzmn");
        Item.I[185 + 286 - 377 + 251] = I("\f?\u001d\t\u0019\u0005$\u0004\u001f-\u00027\u001d\u000b\u000b", "jVoln");
        Item.I[63 + 341 - 290 + 232] = I("\u0004\u0001,\u0019(\u000f\u001b*\u0015\u0016\u0003\u0000 \u001a", "aoOqI");
        Item.I[56 + 109 + 88 + 94] = I("1\u0014/$\u0019:\u000e)(:;\u0015'", "TzLLx");
        Item.I[336 + 190 - 199 + 21] = I("\u0000\r;\b\u0016\u0011\u0003\"\u0017\u0005", "cbVxw");
        Item.I[177 + 156 - 236 + 252] = I("\u0011\u0007;\u00167\u0000\t\"\t$", "rhVfV");
        Item.I[157 + 121 - 77 + 149] = I("\u0005\u00106<\u0001\u0019\u00170=\u0007\u0000", "kuBTd");
        Item.I[149 + 38 + 125 + 39] = I("$\u001c\u0007\u0005\u00018\u001b\u0001\u0004\u0007!", "Jysmd");
        Item.I[324 + 309 - 538 + 257] = I("\u001d\"+\u001a\u0016\u0016", "lWJhb");
        Item.I[32 + 24 + 224 + 73] = I(" \t>\u000e\u0004<\u001d?\u0007\u0013:\u0016", "NlJfa");
        Item.I[182 + 177 - 92 + 87] = I("\u0015<$\u000e9\b<525\u0013&", "aRPQT");
        Item.I[289 + 14 - 22 + 74] = I("\">\u0019'(.%\u0003\u0016%;", "OWwBK");
        Item.I[45 + 29 - 33 + 315] = I("\"+\u001d\u0016\r8\u001b\u0000\u000f\u0006/'\f\u0014\u001c", "JDmfh");
        Item.I[263 + 98 - 14 + 10] = I("\u001c/'\u0014\n\u00104=9\u0006\u00016,\u0003", "qFIqi");
        Item.I[66 + 144 + 108 + 40] = I("\t\u0014\u0004 *\u0018\u0014\u0004=\"&\u0015\u000525\u001d", "yfmSG");
        Item.I[4 + 95 + 86 + 174] = I("8\u0013\f\u0010\u0004)\u0013\f\r\f\u001b\t\u0004\u0011\r", "Haeci");
        Item.I[6 + 330 - 110 + 134] = I("\u00011,\u0015\u001d\u00101,\b\u0015. 7\u001f\u0003\u0005\")\u0015", "qCEfp");
        Item.I[360 + 268 - 481 + 214] = I("5\u0011-\u0011\u001f$\u0011-\f\u0017\u0006\u0011=\u0011\u0006$\u000f7", "EcDbr");
        Item.I[231 + 181 - 241 + 191] = I("0\u0011)\u0005,6", "BpKgE");
        Item.I[229 + 308 - 416 + 242] = I("7\n*5'19) ", "EkHWN");
        Item.I[333 + 31 - 86 + 86] = I("+&+?\u0012,\u001665\u0015* 0", "HIDTw");
        Item.I[48 + 343 - 34 + 8] = I("0\u0017/\u0014#65\"\u0019!'\u0012", "BvMvJ");
        Item.I[169 + 357 - 490 + 330] = I("*\u0019\u0018-3,'\t;?/", "XxzOZ");
        Item.I[288 + 332 - 614 + 361] = I("$2\u00175\u000b\"\u0000\u00012\u0015", "VSuWb");
        Item.I[45 + 106 + 157 + 60] = I("\u0014\u000b!\u001b-\u00125%\u0016+\u0012", "fjCyD");
        Item.I[21 + 89 + 159 + 100] = I("!4\u000b\t\u0007'\u0013\u0006\u0004\u001a", "SUikn");
        Item.I[27 + 125 - 59 + 277] = I("mTy^GtOaI^kPy^Y", "FdRoj");
        Item.I[284 + 39 - 226 + 274] = I("\u0015\u000260/\u0013<<;\"\u0002", "gcTRF");
        Item.I[194 + 31 - 59 + 206] = I("\u0006*#\u0013\u001b\u0000\u0003(\u0015\u0017", "tKAqr");
        Item.I[230 + 295 - 442 + 290] = I("\u0010\u00138\u0007\u0005.\u0012!\t\u0019\u0015", "qaUhw");
        Item.I[30 + 120 - 2 + 226] = I("\n\u0003\u00035\u00058\u0005\u000f4\u0013", "kqnZw");
        Item.I[330 + 268 - 386 + 163] = I("\u001e7\u001c'\u001a\u001f*\u0001: ($\u0001$*\u0005", "wEsIE");
        Item.I[216 + 290 - 291 + 161] = I("\u0007$86\u0007\u000e9'*\u0010\u0002.>$\u000e", "oKJEb");
        Item.I[160 + 358 - 295 + 154] = I("\u000b\b\u0005\"&\u00028\u0001)1\u001f\u00026'1\u0001\b\u001b", "lgiFC");
        Item.I[185 + 134 - 144 + 203] = I("8(\u00020\t15\u001d,\u001e7(\u001c'", "PGpCl");
        Item.I[144 + 157 - 195 + 273] = I("\u0001;0)\"\u000b6\u000e,\"\u0017!4\u001b,\u0017?>6", "eRQDM");
        Item.I[298 + 231 - 513 + 364] = I("--\u0017>&$0\b\"1!+\u0004 ,+&", "EBeMC");
        Item.I[331 + 227 - 303 + 126] = I("\b\u0014\t6", "dqhRK");
        Item.I[86 + 209 + 17 + 70] = I("\u0001\u000f;+,", "mjZXD");
        Item.I[19 + 105 + 108 + 151] = I(">59\u00022$53", "PTTgm");
        Item.I[108 + 356 - 447 + 367] = I("* \u001456%&", "DAyPb");
        Item.I[213 + 212 - 189 + 149] = I("\u0015&7+\u0004\u0018-\u0005$\t\u0019*1\u0019\b\u001f'?%\u0004\u0004=", "vIZFe");
        Item.I[352 + 10 - 207 + 231] = I("5'\u0006\u0010.9<\u001c6\"5#\t\u001b)\u001a\"\u0007\u0016&", "XNhuM");
        Item.I[259 + 36 - 149 + 241] = I(">$\u00191;=", "SQmET");
        Item.I[87 + 387 - 218 + 132] = I("\u0019<6\u001d=\u001a\u001b#\u001e", "tIBiR");
        Item.I[96 + 373 - 255 + 175] = I("\u0019\u0016'\u001d\u000f\u001e&%\u0003\u001e\u000e\u0016&", "zyHvj");
        Item.I[8 + 211 + 138 + 33] = I("\t,73=\n\u001a,(9\u0001=", "dYCGR");
        Item.I[113 + 177 - 243 + 344] = I(";\u0017\u001c*?+", "YvrDZ");
        Item.I[270 + 300 - 508 + 330] = I(":\u0005\u000b>\r*", "XdePh");
        Item.I[382 + 313 - 416 + 114] = I("5\u0005\u0015\u0014)#*\u0003\u000e%4", "FugaJ");
        Item.I[144 + 214 - 344 + 380] = I("!#,\b85>6\u0019\u000e", "ELCzk");
        Item.I[129 + 307 - 176 + 135] = I("\u000e\u001c13,3\u0011,?6", "luCPD");
        Item.I[175 + 283 - 195 + 133] = I("\u0012\u0005)\u001f\n\u001f\u0018%\u0005", "vjFmH");
        Item.I[220 + 313 - 160 + 24] = I("\u0018\"\n\u0013\u0004\u0017\b\u0000\u001b\u0007\u0000", "rWdth");
        Item.I[322 + 12 - 261 + 325] = I("&)\"\u0011\"7(*\u000f\r", "BFMch");
        Item.I[11 + 134 + 196 + 58] = I("\u0014\u0019\u0002\b\u0005\u0014%\u0007\u0004\u0003\u0007", "uzckl");
        Item.I[360 + 230 - 428 + 238] = I("\u0002\n+\u000b\u0016\u0005\u0004'\u00106", "feDyW");
        Item.I[233 + 250 - 306 + 224] = I(".\u00110\b\u0007%\u0011)<<%\u001f0", "JpBcX");
        Item.I[58 + 329 - 73 + 88] = I("\u0000$\u000b<\u0013\u00059\u000f\u00016\u000f", "dKdNW");
        Item.I[398 + 175 - 379 + 209] = I("\u000b\r\u0004%<\u001d7Vy", "yhgJN");
        Item.I[388 + 121 - 158 + 53] = I("ti", "EZhez");
        Item.I[161 + 34 - 179 + 389] = I("3 \u001a!=%", "AEyNO");
        Item.I[38 + 370 - 398 + 396] = I("5?3&\u0004#\u00053(\u0002", "GZPIv");
        Item.I[27 + 164 + 74 + 142] = I("\u0006\u0011\u001f", "epkHR");
        Item.I[17 + 98 + 120 + 173] = I("\n-\u000f\f\u0010\u001c", "xHlcb");
        Item.I[254 + 289 - 431 + 297] = I("\u0015\u0014*\f\u0016\u0003.+\u000f\u000b\u0004\u001a:", "gqIcd");
        Item.I[9 + 131 + 64 + 206] = I(":;\u001e\u0014%+", "XWqwN");
        Item.I[104 + 212 - 128 + 223] = I("\u0003(9\u001a\u0010\u0015", "qMZub");
        Item.I[167 + 356 - 134 + 23] = I(" \u0013\u0017\u001f\u00136)\u0017\u0018\b \u0006", "Rvtpa");
        Item.I[278 + 171 - 146 + 110] = I("\f=\u001a 4", "oUsRD");
        Item.I[143 + 64 + 124 + 83] = I("\u001a\u001c;\u001b\u001c\f", "hyXtn");
        Item.I[51 + 179 + 64 + 121] = I(";\u0004\u0010+\u0014->\u0015%\u0014", "IasDf");
        Item.I[72 + 113 - 39 + 270] = I("<'*", "ZFXKo");
        Item.I[339 + 324 - 460 + 214] = I("\u0015\u0006&\u001d\u0017\u0003", "gcEre");
        Item.I[308 + 336 - 280 + 54] = I("\u0005,2\t*\u0013\u0016<\u00074\u001b", "wIQfX");
        Item.I[88 + 117 + 174 + 40] = I("\u001a\t'\u0004", "whKhU");
        Item.I[396 + 388 - 431 + 67] = I("36\u0007\r4%", "ASdbF");
        Item.I[359 + 352 - 368 + 78] = I("!.*\u0016\u00027\u0014$\u001c\u001c?$!\u0010", "SKIyp");
        Item.I[130 + 22 + 252 + 18] = I("%\u00174&\u000e \u001b", "HrXJa");
        Item.I[141 + 348 - 461 + 395] = I("\b\u0015\u0005$(\u001e", "zpfKZ");
        Item.I[135 + 86 - 58 + 261] = I("$ \u000b\r52\u001a\u001b\u0016&:", "VEhbG");
        Item.I[172 + 413 - 566 + 406] = I("\u001a\u0000\f\u001a", "itmvF");
        Item.I[353 + 403 - 573 + 243] = I("\u0010\u001d\u000455\u0006", "bxgZG");
        Item.I[341 + 11 - 133 + 208] = I("((0\u0016\u001f>\u0012 \r\u001f;)", "ZMSym");
        Item.I[114 + 344 - 387 + 357] = I("\u0019\u0006$+\u0001", "jrVJe");
        Item.I[416 + 378 - 521 + 156] = I("\u0001(7:*\u0017", "sMTUX");
        Item.I[223 + 334 - 300 + 173] = I("\u001c(\u0013'\u001f\n\u0012\u0007)\u001f\n", "nMpHm");
        Item.I[300 + 378 - 401 + 154] = I("8\u0007'\u000e", "OfUjg");
        Item.I[195 + 342 - 185 + 80] = I(";\u0004)9\u0003-", "IaJVq");
        Item.I[376 + 171 - 231 + 117] = I("?$/$4)\u001e}z", "MALKF");
        Item.I[170 + 284 - 364 + 344] = I("dB", "UsFHJ");
        Item.I[420 + 238 - 489 + 266] = I("\u0001.\u0010\u0018\u001c\u0017", "sKswn");
        Item.I[218 + 299 - 431 + 350] = I(";\u001c+\u0006\b-&?\b\u0013=", "IyHiz");
        Item.I[244 + 310 - 211 + 94] = I(" \u0014>\u0011", "WuWea");
        Item.I[418 + 415 - 447 + 52] = I("\u001e'\u0000>3\b", "lBcQA");
    }
    
    protected MovingObjectPosition getMovingObjectPositionFromPlayer(final World world, final EntityPlayer entityPlayer, final boolean b) {
        final float rotationPitch = entityPlayer.rotationPitch;
        final float rotationYaw = entityPlayer.rotationYaw;
        final Vec3 vec3 = new Vec3(entityPlayer.posX, entityPlayer.posY + entityPlayer.getEyeHeight(), entityPlayer.posZ);
        final float cos = MathHelper.cos(-rotationYaw * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-rotationYaw * 0.017453292f - 3.1415927f);
        final float n = -MathHelper.cos(-rotationPitch * 0.017453292f);
        final float sin2 = MathHelper.sin(-rotationPitch * 0.017453292f);
        final float n2 = sin * n;
        final float n3 = cos * n;
        final double n4 = 5.0;
        final Vec3 addVector = vec3.addVector(n2 * n4, sin2 * n4, n3 * n4);
        final Vec3 vec4 = vec3;
        final Vec3 vec5 = addVector;
        int n5;
        if (b) {
            n5 = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            n5 = " ".length();
        }
        return world.rayTraceBlocks(vec4, vec5, b, n5 != 0, "".length() != 0);
    }
    
    public CreativeTabs getCreativeTab() {
        return this.tabToDisplayOn;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return itemStack;
    }
    
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        return "".length() != 0;
    }
    
    public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
        return (Multimap<String, AttributeModifier>)HashMultimap.create();
    }
    
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return "".length();
    }
    
    public boolean getIsRepairable(final ItemStack itemStack, final ItemStack itemStack2) {
        return "".length() != 0;
    }
    
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        return "".length() != 0;
    }
    
    public Item setUnlocalizedName(final String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        return this;
    }
    
    public Item() {
        this.maxStackSize = (0x5E ^ 0x1E);
    }
    
    public String getUnlocalizedNameInefficiently(final ItemStack itemStack) {
        final String unlocalizedName = this.getUnlocalizedName(itemStack);
        String translateToLocal;
        if (unlocalizedName == null) {
            translateToLocal = Item.I[" ".length()];
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            translateToLocal = StatCollector.translateToLocal(unlocalizedName);
        }
        return translateToLocal;
    }
    
    private static void registerItem(final int n, final String s, final Item item) {
        registerItem(n, new ResourceLocation(s), item);
    }
    
    public boolean canItemEditBlocks() {
        return "".length() != 0;
    }
    
    public boolean hitEntity(final ItemStack itemStack, final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        return "".length() != 0;
    }
    
    public boolean isFull3D() {
        return this.bFull3D;
    }
    
    public static Item getItemFromBlock(final Block block) {
        return Item.BLOCK_TO_ITEM.get(block);
    }
    
    public int getItemStackLimit() {
        return this.maxStackSize;
    }
    
    public boolean hasContainerItem() {
        if (this.containerItem != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static Item getItemById(final int n) {
        return Item.itemRegistry.getObjectById(n);
    }
    
    public boolean getShareTag() {
        return " ".length() != 0;
    }
    
    public void onCreated(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
    }
    
    protected static void registerItemBlock(final Block block, final Item item) {
        registerItem(Block.getIdFromBlock(block), Block.blockRegistry.getNameForObject(block), item);
        Item.BLOCK_TO_ITEM.put(block, item);
    }
    
    public int getMaxDamage() {
        return this.maxDamage;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.NONE;
    }
    
    public boolean isMap() {
        return "".length() != 0;
    }
    
    public boolean isPotionIngredient(final ItemStack itemStack) {
        if (this.getPotionEffect(itemStack) != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isDamageable() {
        if (this.maxDamage > 0 && !this.hasSubtypes) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (0 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public String getUnlocalizedName() {
        return Item.I["  ".length()] + this.unlocalizedName;
    }
    
    public boolean getHasSubtypes() {
        return this.hasSubtypes;
    }
    
    public Item setContainerItem(final Item containerItem) {
        this.containerItem = containerItem;
        return this;
    }
    
    private static void registerItem(final int n, final ResourceLocation resourceLocation, final Item item) {
        Item.itemRegistry.register(n, resourceLocation, item);
    }
    
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), "".length()));
    }
    
    protected Item setPotionEffect(final String potionEffect) {
        this.potionEffect = potionEffect;
        return this;
    }
    
    static {
        I();
        itemRegistry = new RegistryNamespaced<ResourceLocation, Item>();
        BLOCK_TO_ITEM = Maps.newHashMap();
        itemModifierUUID = UUID.fromString(Item.I["".length()]);
        Item.itemRand = new Random();
    }
    
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int n, final boolean b) {
    }
    
    private static void registerItemBlock(final Block block) {
        registerItemBlock(block, new ItemBlock(block));
    }
    
    public Item setFull3D() {
        this.bFull3D = (" ".length() != 0);
        return this;
    }
    
    public boolean updateItemStackNBT(final NBTTagCompound nbtTagCompound) {
        return "".length() != 0;
    }
    
    public boolean shouldRotateAroundWhenRendering() {
        return "".length() != 0;
    }
    
    public String getUnlocalizedName(final ItemStack itemStack) {
        return Item.I["   ".length()] + this.unlocalizedName;
    }
    
    public Item setMaxStackSize(final int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }
    
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List<String> list, final boolean b) {
    }
    
    public Item setCreativeTab(final CreativeTabs tabToDisplayOn) {
        this.tabToDisplayOn = tabToDisplayOn;
        return this;
    }
    
    public int getMetadata(final int n) {
        return "".length();
    }
    
    public String getItemStackDisplayName(final ItemStack itemStack) {
        return new StringBuilder().append(StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedNameInefficiently(itemStack)) + Item.I[0x31 ^ 0x35])).toString().trim();
    }
    
    public boolean canHarvestBlock(final Block block) {
        return "".length() != 0;
    }
    
    public enum ToolMaterial
    {
        private final int harvestLevel;
        
        WOOD(ToolMaterial.I["".length()], "".length(), "".length(), 0x69 ^ 0x52, 2.0f, 0.0f, 0x1 ^ 0xE);
        
        private final int enchantability;
        private static final String[] I;
        
        GOLD(ToolMaterial.I[0xA6 ^ 0xA2], 0x34 ^ 0x30, "".length(), 0xE1 ^ 0xC1, 12.0f, 0.0f, 0x69 ^ 0x7F);
        
        private final int maxUses;
        
        IRON(ToolMaterial.I["  ".length()], "  ".length(), "  ".length(), 88 + 139 - 160 + 183, 6.0f, 2.0f, 0xB6 ^ 0xB8);
        
        private final float damageVsEntity;
        private final float efficiencyOnProperMaterial;
        private static final ToolMaterial[] ENUM$VALUES;
        
        EMERALD(ToolMaterial.I["   ".length()], "   ".length(), "   ".length(), 533 + 1508 - 1849 + 1369, 8.0f, 3.0f, 0xAB ^ 0xA1), 
        STONE(ToolMaterial.I[" ".length()], " ".length(), " ".length(), 88 + 119 - 161 + 85, 4.0f, 1.0f, 0x5A ^ 0x5F);
        
        public int getHarvestLevel() {
            return this.harvestLevel;
        }
        
        private static void I() {
            (I = new String[0x9D ^ 0x98])["".length()] = I("1\u0017\u0005\b", "fXJLm");
            ToolMaterial.I[" ".length()] = I("\u0019\u0000\u00078'", "JTHvb");
            ToolMaterial.I["  ".length()] = I("\u001a39\u0014", "SavZf");
            ToolMaterial.I["   ".length()] = I("\u00019\u0003\u001d'\b0", "DtFOf");
            ToolMaterial.I[0xA1 ^ 0xA5] = I("\u000b\u0003<0", "LLptR");
        }
        
        public int getEnchantability() {
            return this.enchantability;
        }
        
        public float getDamageVsEntity() {
            return this.damageVsEntity;
        }
        
        static {
            I();
            final ToolMaterial[] enum$VALUES = new ToolMaterial[0x2A ^ 0x2F];
            enum$VALUES["".length()] = ToolMaterial.WOOD;
            enum$VALUES[" ".length()] = ToolMaterial.STONE;
            enum$VALUES["  ".length()] = ToolMaterial.IRON;
            enum$VALUES["   ".length()] = ToolMaterial.EMERALD;
            enum$VALUES[0xE ^ 0xA] = ToolMaterial.GOLD;
            ENUM$VALUES = enum$VALUES;
        }
        
        private ToolMaterial(final String s, final int n, final int harvestLevel, final int maxUses, final float efficiencyOnProperMaterial, final float damageVsEntity, final int enchantability) {
            this.harvestLevel = harvestLevel;
            this.maxUses = maxUses;
            this.efficiencyOnProperMaterial = efficiencyOnProperMaterial;
            this.damageVsEntity = damageVsEntity;
            this.enchantability = enchantability;
        }
        
        public int getMaxUses() {
            return this.maxUses;
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
                if (2 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Item getRepairItem() {
            Item item;
            if (this == ToolMaterial.WOOD) {
                item = Item.getItemFromBlock(Blocks.planks);
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else if (this == ToolMaterial.STONE) {
                item = Item.getItemFromBlock(Blocks.cobblestone);
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            else if (this == ToolMaterial.GOLD) {
                item = Items.gold_ingot;
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else if (this == ToolMaterial.IRON) {
                item = Items.iron_ingot;
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else if (this == ToolMaterial.EMERALD) {
                item = Items.diamond;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                item = null;
            }
            return item;
        }
        
        public float getEfficiencyOnProperMaterial() {
            return this.efficiencyOnProperMaterial;
        }
    }
}
