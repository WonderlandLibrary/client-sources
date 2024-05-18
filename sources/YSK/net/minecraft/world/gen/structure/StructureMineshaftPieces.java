package net.minecraft.world.gen.structure;

import net.minecraft.init.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;

public class StructureMineshaftPieces
{
    private static final List<WeightedRandomChestContent> CHEST_CONTENT_WEIGHT_LIST;
    private static final String[] I;
    
    public static void registerStructurePieces() {
        MapGenStructureIO.registerStructureComponent(Corridor.class, StructureMineshaftPieces.I["".length()]);
        MapGenStructureIO.registerStructureComponent(Cross.class, StructureMineshaftPieces.I[" ".length()]);
        MapGenStructureIO.registerStructureComponent(Room.class, StructureMineshaftPieces.I["  ".length()]);
        MapGenStructureIO.registerStructureComponent(Stairs.class, StructureMineshaftPieces.I["   ".length()]);
    }
    
    static {
        I();
        final WeightedRandomChestContent[] array = new WeightedRandomChestContent[0xCE ^ 0xC3];
        array["".length()] = new WeightedRandomChestContent(Items.iron_ingot, "".length(), " ".length(), 0x51 ^ 0x54, 0xB6 ^ 0xBC);
        array[" ".length()] = new WeightedRandomChestContent(Items.gold_ingot, "".length(), " ".length(), "   ".length(), 0x6B ^ 0x6E);
        array["  ".length()] = new WeightedRandomChestContent(Items.redstone, "".length(), 0x5D ^ 0x59, 0x1D ^ 0x14, 0xC ^ 0x9);
        array["   ".length()] = new WeightedRandomChestContent(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), 0x36 ^ 0x32, 0x5E ^ 0x57, 0x9A ^ 0x9F);
        array[0xB2 ^ 0xB6] = new WeightedRandomChestContent(Items.diamond, "".length(), " ".length(), "  ".length(), "   ".length());
        array[0xC4 ^ 0xC1] = new WeightedRandomChestContent(Items.coal, "".length(), "   ".length(), 0x53 ^ 0x5B, 0x3 ^ 0x9);
        array[0x93 ^ 0x95] = new WeightedRandomChestContent(Items.bread, "".length(), " ".length(), "   ".length(), 0xAD ^ 0xA2);
        array[0x73 ^ 0x74] = new WeightedRandomChestContent(Items.iron_pickaxe, "".length(), " ".length(), " ".length(), " ".length());
        array[0x6E ^ 0x66] = new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), "".length(), 0x2D ^ 0x29, 0x91 ^ 0x99, " ".length());
        array[0x59 ^ 0x50] = new WeightedRandomChestContent(Items.melon_seeds, "".length(), "  ".length(), 0x41 ^ 0x45, 0x60 ^ 0x6A);
        array[0xB2 ^ 0xB8] = new WeightedRandomChestContent(Items.pumpkin_seeds, "".length(), "  ".length(), 0x69 ^ 0x6D, 0xB4 ^ 0xBE);
        array[0x8A ^ 0x81] = new WeightedRandomChestContent(Items.saddle, "".length(), " ".length(), " ".length(), "   ".length());
        array[0x7D ^ 0x71] = new WeightedRandomChestContent(Items.iron_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
        CHEST_CONTENT_WEIGHT_LIST = Lists.newArrayList((Object[])array);
    }
    
    private static StructureComponent func_175890_b(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        if (n4 > (0x9A ^ 0x92)) {
            return null;
        }
        if (Math.abs(n - structureComponent.getBoundingBox().minX) <= (0x7C ^ 0x2C) && Math.abs(n3 - structureComponent.getBoundingBox().minZ) <= (0x31 ^ 0x61)) {
            final StructureComponent func_175892_a = func_175892_a(list, random, n, n2, n3, enumFacing, n4 + " ".length());
            if (func_175892_a != null) {
                list.add(func_175892_a);
                func_175892_a.buildComponent(structureComponent, list, random);
            }
            return func_175892_a;
        }
        return null;
    }
    
    static List access$1() {
        return StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST;
    }
    
    private static void I() {
        (I = new String[0x11 ^ 0x15])["".length()] = I("=)\u0014\u0019\u0017\u0002\u00133\u0019\u0017", "pzWve");
        StructureMineshaftPieces.I[" ".length()] = I("\u001f\u0006\u00133\u0017!&9/\u001f", "RUPAx");
        StructureMineshaftPieces.I["  ".length()] = I("?<\u001e9\u001b\u001f", "roLVt");
        StructureMineshaftPieces.I["   ".length()] = I("\"\u00140\u001c\u000f\u00065\u0010", "oGchn");
    }
    
    private static StructureComponent func_175892_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        final int nextInt = random.nextInt(0xED ^ 0x89);
        if (nextInt >= (0x68 ^ 0x38)) {
            final StructureBoundingBox func_175813_a = Cross.func_175813_a(list, random, n, n2, n3, enumFacing);
            if (func_175813_a != null) {
                return new Cross(n4, random, func_175813_a, enumFacing);
            }
        }
        else if (nextInt >= (0xF0 ^ 0xB6)) {
            final StructureBoundingBox func_175812_a = Stairs.func_175812_a(list, random, n, n2, n3, enumFacing);
            if (func_175812_a != null) {
                return new Stairs(n4, random, func_175812_a, enumFacing);
            }
        }
        else {
            final StructureBoundingBox func_175814_a = Corridor.func_175814_a(list, random, n, n2, n3, enumFacing);
            if (func_175814_a != null) {
                return new Corridor(n4, random, func_175814_a, enumFacing);
            }
        }
        return null;
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static StructureComponent access$0(final StructureComponent structureComponent, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        return func_175890_b(structureComponent, list, random, n, n2, n3, enumFacing, n4);
    }
    
    public static class Stairs extends StructureComponent
    {
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            final int componentType = this.getComponentType();
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, componentType);
                        "".length();
                        if (3 <= 1) {
                            throw null;
                        }
                        break;
                    }
                    case 4: {
                        StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, componentType);
                        "".length();
                        if (2 == 3) {
                            throw null;
                        }
                        break;
                    }
                    case 5: {
                        StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, componentType);
                        "".length();
                        if (4 == -1) {
                            throw null;
                        }
                        break;
                    }
                    case 6: {
                        StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, componentType);
                        break;
                    }
                }
            }
        }
        
        public static StructureBoundingBox func_175812_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(n, n2 - (0xC1 ^ 0xC4), n3, n, n2 + "  ".length(), n3);
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                case 3: {
                    structureBoundingBox.maxX = n + "  ".length();
                    structureBoundingBox.minZ = n3 - (0x42 ^ 0x4A);
                    "".length();
                    if (3 < 1) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    structureBoundingBox.maxX = n + "  ".length();
                    structureBoundingBox.maxZ = n3 + (0xA7 ^ 0xAF);
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    structureBoundingBox.minX = n - (0x16 ^ 0x1E);
                    structureBoundingBox.maxZ = n3 + "  ".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    structureBoundingBox.maxX = n + (0x6B ^ 0x63);
                    structureBoundingBox.maxZ = n3 + "  ".length();
                    break;
                }
            }
            StructureBoundingBox structureBoundingBox2;
            if (StructureComponent.findIntersecting(list, structureBoundingBox) != null) {
                structureBoundingBox2 = null;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                structureBoundingBox2 = structureBoundingBox;
            }
            return structureBoundingBox2;
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = Stairs.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (3 == 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x84 ^ 0x82);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0xE ^ 0xA);
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0xC4 ^ 0xC1);
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Stairs.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x43 ^ 0x46, "".length(), "  ".length(), 0x26 ^ 0x21, " ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), 0x73 ^ 0x74, "  ".length(), "  ".length(), 0x59 ^ 0x51, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            int i = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
            while (i < (0x8C ^ 0x89)) {
                final int length = "".length();
                final int n = (0x5 ^ 0x0) - i;
                int n2;
                if (i < (0xAE ^ 0xAA)) {
                    n2 = " ".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                }
                this.fillWithBlocks(world, structureBoundingBox, length, n - n2, "  ".length() + i, "  ".length(), (0x50 ^ 0x57) - i, "  ".length() + i, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                ++i;
            }
            return " ".length() != 0;
        }
        
        public Stairs() {
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
                if (3 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Stairs(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    public static class Room extends StructureComponent
    {
        private static final String[] I;
        private List<StructureBoundingBox> roomsLinkedToTheRoom;
        
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
        
        @Override
        public void func_181138_a(final int n, final int n2, final int n3) {
            super.func_181138_a(n, n2, n3);
            final Iterator<StructureBoundingBox> iterator = this.roomsLinkedToTheRoom.iterator();
            "".length();
            if (-1 < -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().offset(n, n2, n3);
            }
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.dirt.getDefaultState(), Blocks.air.getDefaultState(), " ".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.minY + " ".length(), this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + "   ".length(), this.boundingBox.maxY), this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            final Iterator<StructureBoundingBox> iterator = this.roomsLinkedToTheRoom.iterator();
            "".length();
            if (3 >= 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final StructureBoundingBox structureBoundingBox2 = iterator.next();
                this.fillWithBlocks(world, structureBoundingBox, structureBoundingBox2.minX, structureBoundingBox2.maxY - "  ".length(), structureBoundingBox2.minZ, structureBoundingBox2.maxX, structureBoundingBox2.maxY, structureBoundingBox2.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            this.randomlyRareFillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.minY + (0x61 ^ 0x65), this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), "".length() != 0);
            return " ".length() != 0;
        }
        
        public Room() {
            this.roomsLinkedToTheRoom = (List<StructureBoundingBox>)Lists.newLinkedList();
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u00064,\u0017\u0011-9=\u0016", "CZXep");
            Room.I[" ".length()] = I("<\n8%\u000b\u0017\u0007)$", "ydLWj");
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            final int componentType = this.getComponentType();
            int length = this.boundingBox.getYSize() - "   ".length() - " ".length();
            if (length <= 0) {
                length = " ".length();
            }
            int i = "".length();
            "".length();
            if (1 == -1) {
                throw null;
            }
            while (i < this.boundingBox.getXSize()) {
                i += random.nextInt(this.boundingBox.getXSize());
                if (i + "   ".length() > this.boundingBox.getXSize()) {
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    break;
                }
                else {
                    final StructureComponent access$0 = StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX + i, this.boundingBox.minY + random.nextInt(length) + " ".length(), this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, componentType);
                    if (access$0 != null) {
                        final StructureBoundingBox boundingBox = access$0.getBoundingBox();
                        this.roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.minX, boundingBox.minY, this.boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, this.boundingBox.minZ + " ".length()));
                    }
                    i += 4;
                }
            }
            int j = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
            while (j < this.boundingBox.getXSize()) {
                j += random.nextInt(this.boundingBox.getXSize());
                if (j + "   ".length() > this.boundingBox.getXSize()) {
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    final StructureComponent access$2 = StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX + j, this.boundingBox.minY + random.nextInt(length) + " ".length(), this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, componentType);
                    if (access$2 != null) {
                        final StructureBoundingBox boundingBox2 = access$2.getBoundingBox();
                        this.roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox2.minX, boundingBox2.minY, this.boundingBox.maxZ - " ".length(), boundingBox2.maxX, boundingBox2.maxY, this.boundingBox.maxZ));
                    }
                    j += 4;
                }
            }
            int k = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (k < this.boundingBox.getZSize()) {
                k += random.nextInt(this.boundingBox.getZSize());
                if (k + "   ".length() > this.boundingBox.getZSize()) {
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                    break;
                }
                else {
                    final StructureComponent access$3 = StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY + random.nextInt(length) + " ".length(), this.boundingBox.minZ + k, EnumFacing.WEST, componentType);
                    if (access$3 != null) {
                        final StructureBoundingBox boundingBox3 = access$3.getBoundingBox();
                        this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, boundingBox3.minY, boundingBox3.minZ, this.boundingBox.minX + " ".length(), boundingBox3.maxY, boundingBox3.maxZ));
                    }
                    k += 4;
                }
            }
            int l = "".length();
            "".length();
            if (4 == 3) {
                throw null;
            }
            while (l < this.boundingBox.getZSize()) {
                l += random.nextInt(this.boundingBox.getZSize());
                if (l + "   ".length() > this.boundingBox.getZSize()) {
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    break;
                }
                else {
                    final StructureComponent access$4 = StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY + random.nextInt(length) + " ".length(), this.boundingBox.minZ + l, EnumFacing.EAST, componentType);
                    if (access$4 != null) {
                        final StructureBoundingBox boundingBox4 = access$4.getBoundingBox();
                        this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - " ".length(), boundingBox4.minY, boundingBox4.minZ, this.boundingBox.maxX, boundingBox4.maxY, boundingBox4.maxZ));
                    }
                    l += 4;
                }
            }
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            final NBTTagList list = new NBTTagList();
            final Iterator<StructureBoundingBox> iterator = this.roomsLinkedToTheRoom.iterator();
            "".length();
            if (1 == -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                list.appendTag(iterator.next().toNBTTagIntArray());
            }
            nbtTagCompound.setTag(Room.I["".length()], list);
        }
        
        static {
            I();
        }
        
        public Room(final int n, final Random random, final int n2, final int n3) {
            super(n);
            this.roomsLinkedToTheRoom = (List<StructureBoundingBox>)Lists.newLinkedList();
            this.boundingBox = new StructureBoundingBox(n2, 0xA2 ^ 0x90, n3, n2 + (0x78 ^ 0x7F) + random.nextInt(0x88 ^ 0x8E), (0x73 ^ 0x45) + random.nextInt(0x50 ^ 0x56), n3 + (0x43 ^ 0x44) + random.nextInt(0x8E ^ 0x88));
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            final NBTTagList tagList = nbtTagCompound.getTagList(Room.I[" ".length()], 0x42 ^ 0x49);
            int i = "".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (i < tagList.tagCount()) {
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(tagList.getIntArrayAt(i)));
                ++i;
            }
        }
    }
    
    public static class Corridor extends StructureComponent
    {
        private boolean hasRails;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        private static final String[] I;
        private boolean hasSpiders;
        private boolean spawnerPlaced;
        private int sectionCount;
        
        public Corridor(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            int hasRails;
            if (random.nextInt("   ".length()) == 0) {
                hasRails = " ".length();
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                hasRails = "".length();
            }
            this.hasRails = (hasRails != 0);
            int hasSpiders;
            if (!this.hasRails && random.nextInt(0x3B ^ 0x2C) == 0) {
                hasSpiders = " ".length();
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            else {
                hasSpiders = "".length();
            }
            this.hasSpiders = (hasSpiders != 0);
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
                this.sectionCount = boundingBox.getXSize() / (0x5C ^ 0x59);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                this.sectionCount = boundingBox.getZSize() / (0x5D ^ 0x58);
            }
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            "".length();
            "  ".length();
            "".length();
            "  ".length();
            final int n = this.sectionCount * (0x97 ^ 0x92) - " ".length();
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), "  ".length(), " ".length(), n, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.func_175805_a(world, structureBoundingBox, random, 0.8f, "".length(), "  ".length(), "".length(), "  ".length(), "  ".length(), n, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            if (this.hasSpiders) {
                this.func_175805_a(world, structureBoundingBox, random, 0.6f, "".length(), "".length(), "".length(), "  ".length(), " ".length(), n, Blocks.web.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            int i = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (i < this.sectionCount) {
                final int n2 = "  ".length() + i * (0x24 ^ 0x21);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), n2, "".length(), " ".length(), n2, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "  ".length(), "".length(), n2, "  ".length(), " ".length(), n2, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                if (random.nextInt(0xA6 ^ 0xA2) == 0) {
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), n2, "".length(), "  ".length(), n2, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "  ".length(), "  ".length(), n2, "  ".length(), "  ".length(), n2, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), n2, "  ".length(), "  ".length(), n2, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                }
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, "".length(), "  ".length(), n2 - " ".length(), Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, "  ".length(), "  ".length(), n2 - " ".length(), Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, "".length(), "  ".length(), n2 + " ".length(), Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, "  ".length(), "  ".length(), n2 + " ".length(), Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, "".length(), "  ".length(), n2 - "  ".length(), Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, "  ".length(), "  ".length(), n2 - "  ".length(), Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, "".length(), "  ".length(), n2 + "  ".length(), Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, "  ".length(), "  ".length(), n2 + "  ".length(), Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, " ".length(), "  ".length(), n2 - " ".length(), Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, " ".length(), "  ".length(), n2 + " ".length(), Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
                if (random.nextInt(0x6C ^ 0x8) == 0) {
                    final int length = "  ".length();
                    final int length2 = "".length();
                    final int n3 = n2 - " ".length();
                    final List access$1 = StructureMineshaftPieces.access$1();
                    final WeightedRandomChestContent[] array = new WeightedRandomChestContent[" ".length()];
                    array["".length()] = Items.enchanted_book.getRandom(random);
                    this.generateChestContents(world, structureBoundingBox, random, length, length2, n3, WeightedRandomChestContent.func_177629_a(access$1, array), "   ".length() + random.nextInt(0x6D ^ 0x69));
                }
                if (random.nextInt(0xA2 ^ 0xC6) == 0) {
                    final int length3 = "".length();
                    final int length4 = "".length();
                    final int n4 = n2 + " ".length();
                    final List access$2 = StructureMineshaftPieces.access$1();
                    final WeightedRandomChestContent[] array2 = new WeightedRandomChestContent[" ".length()];
                    array2["".length()] = Items.enchanted_book.getRandom(random);
                    this.generateChestContents(world, structureBoundingBox, random, length3, length4, n4, WeightedRandomChestContent.func_177629_a(access$2, array2), "   ".length() + random.nextInt(0x7A ^ 0x7E));
                }
                if (this.hasSpiders && !this.spawnerPlaced) {
                    final int yWithOffset = this.getYWithOffset("".length());
                    final int n5 = n2 - " ".length() + random.nextInt("   ".length());
                    final BlockPos blockPos = new BlockPos(this.getXWithOffset(" ".length(), n5), yWithOffset, this.getZWithOffset(" ".length(), n5));
                    if (structureBoundingBox.isVecInside(blockPos)) {
                        this.spawnerPlaced = (" ".length() != 0);
                        world.setBlockState(blockPos, Blocks.mob_spawner.getDefaultState(), "  ".length());
                        final TileEntity tileEntity = world.getTileEntity(blockPos);
                        if (tileEntity instanceof TileEntityMobSpawner) {
                            ((TileEntityMobSpawner)tileEntity).getSpawnerBaseLogic().setEntityName(Corridor.I[0x57 ^ 0x5F]);
                        }
                    }
                }
                ++i;
            }
            int j = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
            while (j <= "  ".length()) {
                int k = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (k <= n) {
                    if (this.getBlockStateFromPos(world, j, -" ".length(), k, structureBoundingBox).getBlock().getMaterial() == Material.air) {
                        this.setBlockState(world, Blocks.planks.getDefaultState(), j, -" ".length(), k, structureBoundingBox);
                    }
                    ++k;
                }
                ++j;
            }
            if (this.hasRails) {
                int l = "".length();
                "".length();
                if (0 < 0) {
                    throw null;
                }
                while (l <= n) {
                    final IBlockState blockStateFromPos = this.getBlockStateFromPos(world, " ".length(), -" ".length(), l, structureBoundingBox);
                    if (blockStateFromPos.getBlock().getMaterial() != Material.air && blockStateFromPos.getBlock().isFullBlock()) {
                        this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.7f, " ".length(), "".length(), l, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, "".length())));
                    }
                    ++l;
                }
            }
            return " ".length() != 0;
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = Corridor.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xB3 ^ 0xB5);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0xB3 ^ 0xB7);
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x60 ^ 0x65);
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Corridor.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            final int componentType = this.getComponentType();
            final int nextInt = random.nextInt(0x38 ^ 0x3C);
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        if (nextInt <= " ".length()) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.minZ - " ".length(), this.coordBaseMode, componentType);
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                            break;
                        }
                        else if (nextInt == "  ".length()) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.minZ, EnumFacing.WEST, componentType);
                            "".length();
                            if (2 == 3) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.minZ, EnumFacing.EAST, componentType);
                            "".length();
                            if (0 >= 3) {
                                throw null;
                            }
                            break;
                        }
                        break;
                    }
                    case 4: {
                        if (nextInt <= " ".length()) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.maxZ + " ".length(), this.coordBaseMode, componentType);
                            "".length();
                            if (1 < 0) {
                                throw null;
                            }
                            break;
                        }
                        else if (nextInt == "  ".length()) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.maxZ - "   ".length(), EnumFacing.WEST, componentType);
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.maxZ - "   ".length(), EnumFacing.EAST, componentType);
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                            break;
                        }
                        break;
                    }
                    case 5: {
                        if (nextInt <= " ".length()) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.minZ, this.coordBaseMode, componentType);
                            "".length();
                            if (4 == 0) {
                                throw null;
                            }
                            break;
                        }
                        else if (nextInt == "  ".length()) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, componentType);
                            "".length();
                            if (2 <= 0) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, componentType);
                            "".length();
                            if (-1 == 4) {
                                throw null;
                            }
                            break;
                        }
                        break;
                    }
                    case 6: {
                        if (nextInt <= " ".length()) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.minZ, this.coordBaseMode, componentType);
                            "".length();
                            if (4 < 3) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            if (nextInt != "  ".length()) {
                                StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX - "   ".length(), this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, componentType);
                                break;
                            }
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX - "   ".length(), this.boundingBox.minY - " ".length() + random.nextInt("   ".length()), this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, componentType);
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                            break;
                        }
                        break;
                    }
                }
            }
            if (componentType < (0xAA ^ 0xA2)) {
                if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
                    int n = this.boundingBox.minX + "   ".length();
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                    while (n + "   ".length() <= this.boundingBox.maxX) {
                        final int nextInt2 = random.nextInt(0x86 ^ 0x83);
                        if (nextInt2 == 0) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, n, this.boundingBox.minY, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, componentType + " ".length());
                            "".length();
                            if (2 == -1) {
                                throw null;
                            }
                        }
                        else if (nextInt2 == " ".length()) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, n, this.boundingBox.minY, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, componentType + " ".length());
                        }
                        n += 5;
                    }
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                else {
                    int n2 = this.boundingBox.minZ + "   ".length();
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    while (n2 + "   ".length() <= this.boundingBox.maxZ) {
                        final int nextInt3 = random.nextInt(0x9F ^ 0x9A);
                        if (nextInt3 == 0) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY, n2, EnumFacing.WEST, componentType + " ".length());
                            "".length();
                            if (0 <= -1) {
                                throw null;
                            }
                        }
                        else if (nextInt3 == " ".length()) {
                            StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY, n2, EnumFacing.EAST, componentType + " ".length());
                        }
                        n2 += 5;
                    }
                }
            }
        }
        
        private static void I() {
            (I = new String[0xAF ^ 0xA6])["".length()] = I("-8", "EJRuh");
            Corridor.I[" ".length()] = I("\u0003\f", "poBSF");
            Corridor.I["  ".length()] = I("\r*&", "eZUlf");
            Corridor.I["   ".length()] = I("\b\u0013\u000f", "FfbuG");
            Corridor.I[0x0 ^ 0x4] = I("\u0011\u0001", "ysCWI");
            Corridor.I[0x75 ^ 0x70] = I("\u0010\u0014", "cwwmT");
            Corridor.I[0x5B ^ 0x5D] = I("\u0010\u0016\u0012", "xfaAR");
            Corridor.I[0xB9 ^ 0xBE] = I("\"\u0017\u0005", "lbhot");
            Corridor.I[0x71 ^ 0x79] = I("\u0013\u0010\u0019#\u0018 \u0018\u000b#9", "PqoFK");
        }
        
        static {
            I();
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            nbtTagCompound.setBoolean(Corridor.I["".length()], this.hasRails);
            nbtTagCompound.setBoolean(Corridor.I[" ".length()], this.hasSpiders);
            nbtTagCompound.setBoolean(Corridor.I["  ".length()], this.spawnerPlaced);
            nbtTagCompound.setInteger(Corridor.I["   ".length()], this.sectionCount);
        }
        
        @Override
        protected boolean generateChestContents(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final List<WeightedRandomChestContent> list, final int n4) {
            final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
            if (structureBoundingBox.isVecInside(blockPos) && world.getBlockState(blockPos).getBlock().getMaterial() == Material.air) {
                int n5;
                if (random.nextBoolean()) {
                    n5 = " ".length();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    n5 = "".length();
                }
                world.setBlockState(blockPos, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, n5)), "  ".length());
                final EntityMinecartChest entityMinecartChest = new EntityMinecartChest(world, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f);
                WeightedRandomChestContent.generateChestContents(random, list, entityMinecartChest, n4);
                world.spawnEntityInWorld(entityMinecartChest);
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
                if (2 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Corridor() {
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            this.hasRails = nbtTagCompound.getBoolean(Corridor.I[0x99 ^ 0x9D]);
            this.hasSpiders = nbtTagCompound.getBoolean(Corridor.I[0x3C ^ 0x39]);
            this.spawnerPlaced = nbtTagCompound.getBoolean(Corridor.I[0x7C ^ 0x7A]);
            this.sectionCount = nbtTagCompound.getInteger(Corridor.I[0x64 ^ 0x63]);
        }
        
        public static StructureBoundingBox func_175814_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(n, n2, n3, n, n2 + "  ".length(), n3);
            int i = random.nextInt("   ".length()) + "  ".length();
            "".length();
            if (1 == -1) {
                throw null;
            }
            while (i > 0) {
                final int n4 = i * (0xB7 ^ 0xB2);
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                    case 3: {
                        structureBoundingBox.maxX = n + "  ".length();
                        structureBoundingBox.minZ = n3 - (n4 - " ".length());
                        "".length();
                        if (4 == 1) {
                            throw null;
                        }
                        break;
                    }
                    case 4: {
                        structureBoundingBox.maxX = n + "  ".length();
                        structureBoundingBox.maxZ = n3 + (n4 - " ".length());
                        "".length();
                        if (2 < 1) {
                            throw null;
                        }
                        break;
                    }
                    case 5: {
                        structureBoundingBox.minX = n - (n4 - " ".length());
                        structureBoundingBox.maxZ = n3 + "  ".length();
                        "".length();
                        if (0 >= 4) {
                            throw null;
                        }
                        break;
                    }
                    case 6: {
                        structureBoundingBox.maxX = n + (n4 - " ".length());
                        structureBoundingBox.maxZ = n3 + "  ".length();
                        break;
                    }
                }
                if (StructureComponent.findIntersecting(list, structureBoundingBox) == null) {
                    "".length();
                    if (1 < 0) {
                        throw null;
                    }
                    break;
                }
                else {
                    --i;
                }
            }
            StructureBoundingBox structureBoundingBox2;
            if (i > 0) {
                structureBoundingBox2 = structureBoundingBox;
                "".length();
                if (-1 == 3) {
                    throw null;
                }
            }
            else {
                structureBoundingBox2 = null;
            }
            return structureBoundingBox2;
        }
    }
    
    public static class Cross extends StructureComponent
    {
        private boolean isMultipleFloors;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        private static final String[] I;
        private EnumFacing corridorDirection;
        
        public Cross(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing corridorDirection) {
            super(n);
            this.corridorDirection = corridorDirection;
            this.boundingBox = boundingBox;
            int isMultipleFloors;
            if (boundingBox.getYSize() > "   ".length()) {
                isMultipleFloors = " ".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                isMultipleFloors = "".length();
            }
            this.isMultipleFloors = (isMultipleFloors != 0);
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            final int componentType = this.getComponentType();
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.corridorDirection.ordinal()]) {
                case 3: {
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, componentType);
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY, this.boundingBox.minZ + " ".length(), EnumFacing.WEST, componentType);
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ + " ".length(), EnumFacing.EAST, componentType);
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX + " ".length(), this.boundingBox.minY, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, componentType);
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY, this.boundingBox.minZ + " ".length(), EnumFacing.WEST, componentType);
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ + " ".length(), EnumFacing.EAST, componentType);
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, componentType);
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX + " ".length(), this.boundingBox.minY, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, componentType);
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY, this.boundingBox.minZ + " ".length(), EnumFacing.WEST, componentType);
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, componentType);
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX + " ".length(), this.boundingBox.minY, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, componentType);
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ + " ".length(), EnumFacing.EAST, componentType);
                    break;
                }
            }
            if (this.isMultipleFloors) {
                if (random.nextBoolean()) {
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX + " ".length(), this.boundingBox.minY + "   ".length() + " ".length(), this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, componentType);
                }
                if (random.nextBoolean()) {
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY + "   ".length() + " ".length(), this.boundingBox.minZ + " ".length(), EnumFacing.WEST, componentType);
                }
                if (random.nextBoolean()) {
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY + "   ".length() + " ".length(), this.boundingBox.minZ + " ".length(), EnumFacing.EAST, componentType);
                }
                if (random.nextBoolean()) {
                    StructureMineshaftPieces.access$0(structureComponent, list, random, this.boundingBox.minX + " ".length(), this.boundingBox.minY + "   ".length() + " ".length(), this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, componentType);
                }
            }
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            this.isMultipleFloors = nbtTagCompound.getBoolean(Cross.I["  ".length()]);
            this.corridorDirection = EnumFacing.getHorizontal(nbtTagCompound.getInteger(Cross.I["   ".length()]));
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            nbtTagCompound.setBoolean(Cross.I["".length()], this.isMultipleFloors);
            nbtTagCompound.setInteger(Cross.I[" ".length()], this.corridorDirection.getHorizontalIndex());
        }
        
        public static StructureBoundingBox func_175813_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(n, n2, n3, n, n2 + "  ".length(), n3);
            if (random.nextInt(0x9C ^ 0x98) == 0) {
                final StructureBoundingBox structureBoundingBox2 = structureBoundingBox;
                structureBoundingBox2.maxY += (0x11 ^ 0x15);
            }
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                case 3: {
                    structureBoundingBox.minX = n - " ".length();
                    structureBoundingBox.maxX = n + "   ".length();
                    structureBoundingBox.minZ = n3 - (0x47 ^ 0x43);
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    structureBoundingBox.minX = n - " ".length();
                    structureBoundingBox.maxX = n + "   ".length();
                    structureBoundingBox.maxZ = n3 + (0x6E ^ 0x6A);
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    structureBoundingBox.minX = n - (0x57 ^ 0x53);
                    structureBoundingBox.minZ = n3 - " ".length();
                    structureBoundingBox.maxZ = n3 + "   ".length();
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    structureBoundingBox.maxX = n + (0xBB ^ 0xBF);
                    structureBoundingBox.minZ = n3 - " ".length();
                    structureBoundingBox.maxZ = n3 + "   ".length();
                    break;
                }
            }
            StructureBoundingBox structureBoundingBox3;
            if (StructureComponent.findIntersecting(list, structureBoundingBox) != null) {
                structureBoundingBox3 = null;
                "".length();
                if (3 == 2) {
                    throw null;
                }
            }
            else {
                structureBoundingBox3 = structureBoundingBox;
            }
            return structureBoundingBox3;
        }
        
        private static void I() {
            (I = new String[0x46 ^ 0x42])["".length()] = I("\u0002\u0004", "vbtAt");
            Cross.I[" ".length()] = I(" ", "dmeFT");
            Cross.I["  ".length()] = I(",-", "XKUAu");
            Cross.I["   ".length()] = I("\u001e", "ZTBEy");
        }
        
        static {
            I();
        }
        
        public Cross() {
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
                if (4 <= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            if (this.isMultipleFloors) {
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - " ".length(), this.boundingBox.minY + "   ".length() - " ".length(), this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + " ".length(), this.boundingBox.maxX, this.boundingBox.minY + "   ".length() - " ".length(), this.boundingBox.maxZ - " ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + " ".length(), this.boundingBox.maxY - "  ".length(), this.boundingBox.minZ, this.boundingBox.maxX - " ".length(), this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.maxY - "  ".length(), this.boundingBox.minZ + " ".length(), this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - " ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + " ".length(), this.boundingBox.minY + "   ".length(), this.boundingBox.minZ + " ".length(), this.boundingBox.maxX - " ".length(), this.boundingBox.minY + "   ".length(), this.boundingBox.maxZ - " ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - " ".length(), this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + " ".length(), this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - " ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ + " ".length(), this.boundingBox.minX + " ".length(), this.boundingBox.maxY, this.boundingBox.minZ + " ".length(), Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + " ".length(), this.boundingBox.minY, this.boundingBox.maxZ - " ".length(), this.boundingBox.minX + " ".length(), this.boundingBox.maxY, this.boundingBox.maxZ - " ".length(), Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.maxX - " ".length(), this.boundingBox.minY, this.boundingBox.minZ + " ".length(), this.boundingBox.maxX - " ".length(), this.boundingBox.maxY, this.boundingBox.minZ + " ".length(), Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.maxX - " ".length(), this.boundingBox.minY, this.boundingBox.maxZ - " ".length(), this.boundingBox.maxX - " ".length(), this.boundingBox.maxY, this.boundingBox.maxZ - " ".length(), Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            int i = this.boundingBox.minX;
            "".length();
            if (false == true) {
                throw null;
            }
            while (i <= this.boundingBox.maxX) {
                int j = this.boundingBox.minZ;
                "".length();
                if (0 < 0) {
                    throw null;
                }
                while (j <= this.boundingBox.maxZ) {
                    if (this.getBlockStateFromPos(world, i, this.boundingBox.minY - " ".length(), j, structureBoundingBox).getBlock().getMaterial() == Material.air) {
                        this.setBlockState(world, Blocks.planks.getDefaultState(), i, this.boundingBox.minY - " ".length(), j, structureBoundingBox);
                    }
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = Cross.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xC2 ^ 0xC4);
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (2 == 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x65 ^ 0x61);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x8D ^ 0x88);
                "".length();
                if (-1 == 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Cross.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
    }
}
