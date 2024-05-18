package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.world.biome.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class StructureVillagePieces
{
    private static final String[] I;
    
    private static StructureComponent func_176066_d(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        if (n4 > (0x16 ^ 0x24)) {
            return null;
        }
        if (Math.abs(n - start.getBoundingBox().minX) <= (0x63 ^ 0x13) && Math.abs(n3 - start.getBoundingBox().minZ) <= (0xC4 ^ 0xB4)) {
            final Village func_176067_c = func_176067_c(start, list, random, n, n2, n3, enumFacing, n4 + " ".length());
            if (func_176067_c != null) {
                final int n5 = (func_176067_c.boundingBox.minX + func_176067_c.boundingBox.maxX) / "  ".length();
                final int n6 = (func_176067_c.boundingBox.minZ + func_176067_c.boundingBox.maxZ) / "  ".length();
                final int n7 = func_176067_c.boundingBox.maxX - func_176067_c.boundingBox.minX;
                final int n8 = func_176067_c.boundingBox.maxZ - func_176067_c.boundingBox.minZ;
                int n9;
                if (n7 > n8) {
                    n9 = n7;
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    n9 = n8;
                }
                if (start.getWorldChunkManager().areBiomesViable(n5, n6, n9 / "  ".length() + (0x3D ^ 0x39), MapGenVillage.villageSpawnBiomes)) {
                    list.add(func_176067_c);
                    start.field_74932_i.add(func_176067_c);
                    return func_176067_c;
                }
            }
            return null;
        }
        return null;
    }
    
    public static void registerVillagePieces() {
        MapGenStructureIO.registerStructureComponent(House1.class, StructureVillagePieces.I["".length()]);
        MapGenStructureIO.registerStructureComponent(Field1.class, StructureVillagePieces.I[" ".length()]);
        MapGenStructureIO.registerStructureComponent(Field2.class, StructureVillagePieces.I["  ".length()]);
        MapGenStructureIO.registerStructureComponent(Torch.class, StructureVillagePieces.I["   ".length()]);
        MapGenStructureIO.registerStructureComponent(Hall.class, StructureVillagePieces.I[0xA8 ^ 0xAC]);
        MapGenStructureIO.registerStructureComponent(House4Garden.class, StructureVillagePieces.I[0xAB ^ 0xAE]);
        MapGenStructureIO.registerStructureComponent(WoodHut.class, StructureVillagePieces.I[0xC ^ 0xA]);
        MapGenStructureIO.registerStructureComponent(Church.class, StructureVillagePieces.I[0x9E ^ 0x99]);
        MapGenStructureIO.registerStructureComponent(House2.class, StructureVillagePieces.I[0x2A ^ 0x22]);
        MapGenStructureIO.registerStructureComponent(Start.class, StructureVillagePieces.I[0x1B ^ 0x12]);
        MapGenStructureIO.registerStructureComponent(Path.class, StructureVillagePieces.I[0xC9 ^ 0xC3]);
        MapGenStructureIO.registerStructureComponent(House3.class, StructureVillagePieces.I[0xCC ^ 0xC7]);
        MapGenStructureIO.registerStructureComponent(Well.class, StructureVillagePieces.I[0xAA ^ 0xA6]);
    }
    
    static {
        I();
    }
    
    private static StructureComponent func_176069_e(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        if (n4 > "   ".length() + start.terrainType) {
            return null;
        }
        if (Math.abs(n - start.getBoundingBox().minX) <= (0x1D ^ 0x6D) && Math.abs(n3 - start.getBoundingBox().minZ) <= (0xA ^ 0x7A)) {
            final StructureBoundingBox func_175848_a = Path.func_175848_a(start, list, random, n, n2, n3, enumFacing);
            if (func_175848_a != null && func_175848_a.minY > (0x20 ^ 0x2A)) {
                final Path path = new Path(start, n4, random, func_175848_a, enumFacing);
                final int n5 = (path.boundingBox.minX + path.boundingBox.maxX) / "  ".length();
                final int n6 = (path.boundingBox.minZ + path.boundingBox.maxZ) / "  ".length();
                final int n7 = path.boundingBox.maxX - path.boundingBox.minX;
                final int n8 = path.boundingBox.maxZ - path.boundingBox.minZ;
                int n9;
                if (n7 > n8) {
                    n9 = n7;
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                }
                else {
                    n9 = n8;
                }
                if (start.getWorldChunkManager().areBiomesViable(n5, n6, n9 / "  ".length() + (0x34 ^ 0x30), MapGenVillage.villageSpawnBiomes)) {
                    list.add(path);
                    start.field_74930_j.add(path);
                    return path;
                }
            }
            return null;
        }
        return null;
    }
    
    static StructureComponent access$0(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        return func_176069_e(start, list, random, n, n2, n3, enumFacing, n4);
    }
    
    public static List<PieceWeight> getStructureVillageWeightedPieceList(final Random random, final int n) {
        final ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new PieceWeight(House4Garden.class, 0xAF ^ 0xAB, MathHelper.getRandomIntegerInRange(random, "  ".length() + n, (0xA ^ 0xE) + n * "  ".length())));
        arrayList.add(new PieceWeight(Church.class, 0x9A ^ 0x8E, MathHelper.getRandomIntegerInRange(random, "".length() + n, " ".length() + n)));
        arrayList.add(new PieceWeight(House1.class, 0x66 ^ 0x72, MathHelper.getRandomIntegerInRange(random, "".length() + n, "  ".length() + n)));
        arrayList.add(new PieceWeight(WoodHut.class, "   ".length(), MathHelper.getRandomIntegerInRange(random, "  ".length() + n, (0x61 ^ 0x64) + n * "   ".length())));
        arrayList.add(new PieceWeight(Hall.class, 0xBE ^ 0xB1, MathHelper.getRandomIntegerInRange(random, "".length() + n, "  ".length() + n)));
        arrayList.add(new PieceWeight(Field1.class, "   ".length(), MathHelper.getRandomIntegerInRange(random, " ".length() + n, (0x81 ^ 0x85) + n)));
        arrayList.add(new PieceWeight(Field2.class, "   ".length(), MathHelper.getRandomIntegerInRange(random, "  ".length() + n, (0x95 ^ 0x91) + n * "  ".length())));
        arrayList.add(new PieceWeight(House2.class, 0x66 ^ 0x69, MathHelper.getRandomIntegerInRange(random, "".length(), " ".length() + n)));
        arrayList.add(new PieceWeight(House3.class, 0xB1 ^ 0xB9, MathHelper.getRandomIntegerInRange(random, "".length() + n, "   ".length() + n * "  ".length())));
        final Iterator<PieceWeight> iterator = (Iterator<PieceWeight>)arrayList.iterator();
        "".length();
        if (3 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (iterator.next().villagePiecesLimit == 0) {
                iterator.remove();
            }
        }
        return (List<PieceWeight>)arrayList;
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
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static int func_75079_a(final List<PieceWeight> list) {
        int n = "".length();
        int length = "".length();
        final Iterator<PieceWeight> iterator = list.iterator();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final PieceWeight pieceWeight = iterator.next();
            if (pieceWeight.villagePiecesLimit > 0 && pieceWeight.villagePiecesSpawned < pieceWeight.villagePiecesLimit) {
                n = " ".length();
            }
            length += pieceWeight.villagePieceWeight;
        }
        int n2;
        if (n != 0) {
            n2 = length;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n2 = -" ".length();
        }
        return n2;
    }
    
    static StructureComponent access$1(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        return func_176066_d(start, list, random, n, n2, n3, enumFacing, n4);
    }
    
    private static Village func_176065_a(final Start start, final PieceWeight pieceWeight, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        final Class<? extends Village> villagePieceClass = pieceWeight.villagePieceClass;
        Village village = null;
        if (villagePieceClass == House4Garden.class) {
            village = House4Garden.func_175858_a(start, list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else if (villagePieceClass == Church.class) {
            village = Church.func_175854_a(start, list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (villagePieceClass == House1.class) {
            village = House1.func_175850_a(start, list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (villagePieceClass == WoodHut.class) {
            village = WoodHut.func_175853_a(start, list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (villagePieceClass == Hall.class) {
            village = Hall.func_175857_a(start, list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else if (villagePieceClass == Field1.class) {
            village = Field1.func_175851_a(start, list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else if (villagePieceClass == Field2.class) {
            village = Field2.func_175852_a(start, list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else if (villagePieceClass == House2.class) {
            village = House2.func_175855_a(start, list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (villagePieceClass == House3.class) {
            village = House3.func_175849_a(start, list, random, n, n2, n3, enumFacing, n4);
        }
        return village;
    }
    
    private static Village func_176067_c(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        final int func_75079_a = func_75079_a(start.structureVillageWeightedPieceList);
        if (func_75079_a <= 0) {
            return null;
        }
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < (0x4C ^ 0x49)) {
            ++i;
            int nextInt = random.nextInt(func_75079_a);
            final Iterator<PieceWeight> iterator = start.structureVillageWeightedPieceList.iterator();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final PieceWeight structVillagePieceWeight = iterator.next();
                nextInt -= structVillagePieceWeight.villagePieceWeight;
                if (nextInt < 0) {
                    if (!structVillagePieceWeight.canSpawnMoreVillagePiecesOfType(n4)) {
                        break;
                    }
                    if (structVillagePieceWeight == start.structVillagePieceWeight && start.structureVillageWeightedPieceList.size() > " ".length()) {
                        "".length();
                        if (0 == -1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        final Village func_176065_a = func_176065_a(start, structVillagePieceWeight, list, random, n, n2, n3, enumFacing, n4);
                        if (func_176065_a != null) {
                            final PieceWeight pieceWeight = structVillagePieceWeight;
                            pieceWeight.villagePiecesSpawned += " ".length();
                            start.structVillagePieceWeight = structVillagePieceWeight;
                            if (!structVillagePieceWeight.canSpawnMoreVillagePieces()) {
                                start.structureVillageWeightedPieceList.remove(structVillagePieceWeight);
                            }
                            return func_176065_a;
                        }
                        continue;
                    }
                }
            }
        }
        final StructureBoundingBox func_175856_a = Torch.func_175856_a(start, list, random, n, n2, n3, enumFacing);
        if (func_175856_a != null) {
            return new Torch(start, n4, random, func_175856_a, enumFacing);
        }
        return null;
    }
    
    private static void I() {
        (I = new String[0x85 ^ 0x88])["".length()] = I("&.\u0010\u0006", "pGRNZ");
        StructureVillagePieces.I[" ".length()] = I("\u0005\u0013\u0013-", "SzWkh");
        StructureVillagePieces.I["  ".length()] = I("\u000f\u0019\u0005", "YpChO");
        StructureVillagePieces.I["   ".length()] = I("\u0011,.", "GEbMS");
        StructureVillagePieces.I[0xA1 ^ 0xA5] = I("\u00101\u0019,", "FXIdi");
        StructureVillagePieces.I[0x69 ^ 0x6C] = I("882)", "nQaaf");
        StructureVillagePieces.I[0x8 ^ 0xE] = I("\u0002\u0001\t\"\u0000", "ThZOH");
        StructureVillagePieces.I[0xE ^ 0x9] = I("59>'", "cPmsw");
        StructureVillagePieces.I[0x81 ^ 0x89] = I("\f\u00186", "ZqewT");
        StructureVillagePieces.I[0xB6 ^ 0xBF] = I("\u0010\u001e\u001c\"\u00134\u0003", "FwOVr");
        StructureVillagePieces.I[0x41 ^ 0x4B] = I("!:\u00115", "wSBgw");
        StructureVillagePieces.I[0xAB ^ 0xA0] = I("2-,\u0017\u001b", "dDxES");
        StructureVillagePieces.I[0x28 ^ 0x24] = I(".\u001f\u001c", "xvKFE");
    }
    
    public static class House4Garden extends Village
    {
        private static final String[] I;
        private boolean isRoofAccessible;
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I(" \u0003#+1\u0017\u0003", "tfQYP");
            House4Garden.I[" ".length()] = I("\u00024>\u0018$54", "VQLjE");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(House4Garden.I["".length()], this.isRoofAccessible);
        }
        
        static {
            I();
        }
        
        public House4Garden() {
        }
        
        public static House4Garden func_175858_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), 0x91 ^ 0x94, 0x3C ^ 0x3A, 0x82 ^ 0x87, enumFacing);
            House4Garden house4Garden;
            if (StructureComponent.findIntersecting(list, componentToAddBoundingBox) != null) {
                house4Garden = null;
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                house4Garden = new House4Garden(start, n4, random, componentToAddBoundingBox, enumFacing);
            }
            return house4Garden;
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
                if (3 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + (0x87 ^ 0x81) - " ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x40 ^ 0x44, "".length(), 0x2C ^ 0x28, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x2E ^ 0x2A, "".length(), 0x13 ^ 0x17, 0x87 ^ 0x83, 0x4B ^ 0x4F, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x36 ^ 0x32, " ".length(), "   ".length(), 0x50 ^ 0x54, "   ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "".length(), " ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "".length(), "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "".length(), "   ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x57 ^ 0x53, " ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x7F ^ 0x7B, "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x7A ^ 0x7E, "   ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "".length(), " ".length(), 0x33 ^ 0x37, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "".length(), "  ".length(), 0x86 ^ 0x82, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "".length(), "   ".length(), 0xBF ^ 0xBB, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x70 ^ 0x74, " ".length(), 0x46 ^ 0x42, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x5F ^ 0x5B, "  ".length(), 0x39 ^ 0x3D, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0xAE ^ 0xAA, "   ".length(), 0x2C ^ 0x28, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), " ".length(), "".length(), "   ".length(), "   ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x2 ^ 0x6, " ".length(), " ".length(), 0xA7 ^ 0xA3, "   ".length(), "   ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0xC7 ^ 0xC3, "   ".length(), "   ".length(), 0x5 ^ 0x1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), "  ".length(), 0x96 ^ 0x92, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x8A ^ 0x8E, "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), " ".length(), " ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), " ".length(), "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), " ".length(), "   ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), "  ".length(), "   ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), "   ".length(), "   ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), "   ".length(), "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), "   ".length(), " ".length(), "".length(), structureBoundingBox);
            if (this.getBlockStateFromPos(world, "  ".length(), "".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(world, "  ".length(), -" ".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() != Material.air) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length())), "  ".length(), "".length(), -" ".length(), structureBoundingBox);
            }
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), " ".length(), "   ".length(), "   ".length(), "   ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            if (this.isRoofAccessible) {
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "".length(), 0xC0 ^ 0xC5, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), " ".length(), 0x56 ^ 0x53, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "  ".length(), 0x16 ^ 0x13, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "   ".length(), 0x30 ^ 0x35, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0xA3 ^ 0xA7, 0x72 ^ 0x77, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "".length(), 0x23 ^ 0x26, 0x56 ^ 0x52, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), " ".length(), 0x42 ^ 0x47, 0x69 ^ 0x6D, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "  ".length(), 0x3F ^ 0x3A, 0x55 ^ 0x51, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "   ".length(), 0x74 ^ 0x71, 0x60 ^ 0x64, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0xAD ^ 0xA9, 0x1B ^ 0x1E, 0x78 ^ 0x7C, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0x4E ^ 0x4A, 0xAE ^ 0xAB, " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0x1E ^ 0x1A, 0x5A ^ 0x5F, "  ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0xB8 ^ 0xBC, 0x86 ^ 0x83, "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "".length(), 0xB4 ^ 0xB1, " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "".length(), 0x6C ^ 0x69, "  ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "".length(), 0x41 ^ 0x44, "   ".length(), structureBoundingBox);
            }
            if (this.isRoofAccessible) {
                final int metadataWithOffset = this.getMetadataWithOffset(Blocks.ladder, "   ".length());
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), "   ".length(), " ".length(), "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), "   ".length(), "  ".length(), "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), "   ".length(), "   ".length(), "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), "   ".length(), 0x4F ^ 0x4B, "   ".length(), structureBoundingBox);
            }
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode), "  ".length(), "   ".length(), " ".length(), structureBoundingBox);
            int i = "".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (i < (0x81 ^ 0x84)) {
                int j = "".length();
                "".length();
                if (3 < -1) {
                    throw null;
                }
                while (j < (0x7B ^ 0x7E)) {
                    this.clearCurrentPositionBlocksUpwards(world, j, 0x82 ^ 0x84, i, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), j, -" ".length(), i, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            this.spawnVillagers(world, structureBoundingBox, " ".length(), " ".length(), "  ".length(), " ".length());
            return " ".length() != 0;
        }
        
        public House4Garden(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.isRoofAccessible = random.nextBoolean();
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.isRoofAccessible = nbtTagCompound.getBoolean(House4Garden.I[" ".length()]);
        }
    }
    
    abstract static class Village extends StructureComponent
    {
        private int villagersSpawned;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        private boolean isDesertVillage;
        protected int field_143015_k;
        private static final String[] I;
        
        static {
            I();
        }
        
        protected Village(final Start start, final int n) {
            super(n);
            this.field_143015_k = -" ".length();
            if (start != null) {
                this.isDesertVillage = start.inDesert;
            }
        }
        
        protected int func_180779_c(final int n, final int n2) {
            return n2;
        }
        
        public Village() {
            this.field_143015_k = -" ".length();
        }
        
        protected static boolean canVillageGoDeeper(final StructureBoundingBox structureBoundingBox) {
            if (structureBoundingBox != null && structureBoundingBox.minY > (0x9A ^ 0x90)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        protected void setBlockState(final World world, final IBlockState blockState, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
            super.setBlockState(world, this.func_175847_a(blockState), n, n2, n3, structureBoundingBox);
        }
        
        protected void spawnVillagers(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4) {
            if (this.villagersSpawned < n4) {
                int i = this.villagersSpawned;
                "".length();
                if (0 >= 1) {
                    throw null;
                }
                while (i < n4) {
                    final int xWithOffset = this.getXWithOffset(n + i, n3);
                    final int yWithOffset = this.getYWithOffset(n2);
                    final int zWithOffset = this.getZWithOffset(n + i, n3);
                    if (!structureBoundingBox.isVecInside(new BlockPos(xWithOffset, yWithOffset, zWithOffset))) {
                        "".length();
                        if (2 == 4) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        this.villagersSpawned += " ".length();
                        final EntityVillager entityVillager = new EntityVillager(world);
                        entityVillager.setLocationAndAngles(xWithOffset + 0.5, yWithOffset, zWithOffset + 0.5, 0.0f, 0.0f);
                        entityVillager.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityVillager)), null);
                        entityVillager.setProfession(this.func_180779_c(i, entityVillager.getProfession()));
                        world.spawnEntityInWorld(entityVillager);
                        ++i;
                    }
                }
            }
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = Village.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x7D ^ 0x7B);
                "".length();
                if (-1 == 4) {
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
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x4F ^ 0x4B);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0xBA ^ 0xBF);
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Village.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            this.field_143015_k = nbtTagCompound.getInteger(Village.I["   ".length()]);
            this.villagersSpawned = nbtTagCompound.getInteger(Village.I[0xBC ^ 0xB8]);
            this.isDesertVillage = nbtTagCompound.getBoolean(Village.I[0x90 ^ 0x95]);
        }
        
        private static void I() {
            (I = new String[0x7A ^ 0x7C])["".length()] = I("\u0012\u0003\u0002*", "ZSmYE");
            Village.I[" ".length()] = I("\u001e\n\u001c6\u0002<", "HIsCl");
            Village.I["  ".length()] = I("\u0002\u000b\u0003\u001d;2", "FnpxI");
            Village.I["   ".length()] = I("\u001c\u0002\u0007>", "TRhMx");
            Village.I[0xBB ^ 0xBF] = I("\u0013\u0012)\u0011?1", "EQFdQ");
            Village.I[0x6 ^ 0x3] = I("\u00000\u001d\u0017<0", "DUnrN");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            nbtTagCompound.setInteger(Village.I["".length()], this.field_143015_k);
            nbtTagCompound.setInteger(Village.I[" ".length()], this.villagersSpawned);
            nbtTagCompound.setBoolean(Village.I["  ".length()], this.isDesertVillage);
        }
        
        @Override
        protected void replaceAirAndLiquidDownwards(final World world, final IBlockState blockState, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
            super.replaceAirAndLiquidDownwards(world, this.func_175847_a(blockState), n, n2, n3, structureBoundingBox);
        }
        
        protected StructureComponent getNextComponentNN(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2) {
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        return StructureVillagePieces.access$1(start, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType());
                    }
                    case 4: {
                        return StructureVillagePieces.access$1(start, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType());
                    }
                    case 5: {
                        return StructureVillagePieces.access$1(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, this.getComponentType());
                    }
                    case 6: {
                        return StructureVillagePieces.access$1(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, this.getComponentType());
                    }
                }
            }
            return null;
        }
        
        protected IBlockState func_175847_a(final IBlockState blockState) {
            if (this.isDesertVillage) {
                if (blockState.getBlock() == Blocks.log || blockState.getBlock() == Blocks.log2) {
                    return Blocks.sandstone.getDefaultState();
                }
                if (blockState.getBlock() == Blocks.cobblestone) {
                    return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
                }
                if (blockState.getBlock() == Blocks.planks) {
                    return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
                }
                if (blockState.getBlock() == Blocks.oak_stairs) {
                    return Blocks.sandstone_stairs.getDefaultState().withProperty((IProperty<Comparable>)BlockStairs.FACING, (EnumFacing)blockState.getValue((IProperty<V>)BlockStairs.FACING));
                }
                if (blockState.getBlock() == Blocks.stone_stairs) {
                    return Blocks.sandstone_stairs.getDefaultState().withProperty((IProperty<Comparable>)BlockStairs.FACING, (EnumFacing)blockState.getValue((IProperty<V>)BlockStairs.FACING));
                }
                if (blockState.getBlock() == Blocks.gravel) {
                    return Blocks.sandstone.getDefaultState();
                }
            }
            return blockState;
        }
        
        protected StructureComponent getNextComponentPP(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2) {
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        return StructureVillagePieces.access$1(start, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType());
                    }
                    case 4: {
                        return StructureVillagePieces.access$1(start, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType());
                    }
                    case 5: {
                        return StructureVillagePieces.access$1(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, this.getComponentType());
                    }
                    case 6: {
                        return StructureVillagePieces.access$1(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, this.getComponentType());
                    }
                }
            }
            return null;
        }
        
        protected int getAverageGroundLevel(final World world, final StructureBoundingBox structureBoundingBox) {
            int length = "".length();
            int length2 = "".length();
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int i = this.boundingBox.minZ;
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (i <= this.boundingBox.maxZ) {
                int j = this.boundingBox.minX;
                "".length();
                if (true != true) {
                    throw null;
                }
                while (j <= this.boundingBox.maxX) {
                    mutableBlockPos.func_181079_c(j, 0x28 ^ 0x68, i);
                    if (structureBoundingBox.isVecInside(mutableBlockPos)) {
                        length += Math.max(world.getTopSolidOrLiquidBlock(mutableBlockPos).getY(), world.provider.getAverageGroundLevel());
                        ++length2;
                    }
                    ++j;
                }
                ++i;
            }
            if (length2 == 0) {
                return -" ".length();
            }
            return length / length2;
        }
        
        @Override
        protected void fillWithBlocks(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IBlockState blockState, final IBlockState blockState2, final boolean b) {
            super.fillWithBlocks(world, structureBoundingBox, n, n2, n3, n4, n5, n6, this.func_175847_a(blockState), this.func_175847_a(blockState2), b);
        }
        
        protected void func_175846_a(final boolean isDesertVillage) {
            this.isDesertVillage = isDesertVillage;
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
    }
    
    public static class Start extends Well
    {
        public List<PieceWeight> structureVillageWeightedPieceList;
        public boolean inDesert;
        public List<StructureComponent> field_74930_j;
        public WorldChunkManager worldChunkMngr;
        public PieceWeight structVillagePieceWeight;
        public int terrainType;
        public List<StructureComponent> field_74932_i;
        
        public WorldChunkManager getWorldChunkManager() {
            return this.worldChunkMngr;
        }
        
        public Start() {
            this.field_74932_i = (List<StructureComponent>)Lists.newArrayList();
            this.field_74930_j = (List<StructureComponent>)Lists.newArrayList();
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
                if (true != true) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Start(final WorldChunkManager worldChunkMngr, final int n, final Random random, final int n2, final int n3, final List<PieceWeight> structureVillageWeightedPieceList, final int terrainType) {
            super(null, "".length(), random, n2, n3);
            this.field_74932_i = (List<StructureComponent>)Lists.newArrayList();
            this.field_74930_j = (List<StructureComponent>)Lists.newArrayList();
            this.worldChunkMngr = worldChunkMngr;
            this.structureVillageWeightedPieceList = structureVillageWeightedPieceList;
            this.terrainType = terrainType;
            final BiomeGenBase biomeGenerator = worldChunkMngr.getBiomeGenerator(new BlockPos(n2, "".length(), n3), BiomeGenBase.field_180279_ad);
            int inDesert;
            if (biomeGenerator != BiomeGenBase.desert && biomeGenerator != BiomeGenBase.desertHills) {
                inDesert = "".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else {
                inDesert = " ".length();
            }
            this.func_175846_a(this.inDesert = (inDesert != 0));
        }
    }
    
    public static class PieceWeight
    {
        public int villagePiecesSpawned;
        public int villagePiecesLimit;
        public final int villagePieceWeight;
        public Class<? extends Village> villagePieceClass;
        
        public PieceWeight(final Class<? extends Village> villagePieceClass, final int villagePieceWeight, final int villagePiecesLimit) {
            this.villagePieceClass = villagePieceClass;
            this.villagePieceWeight = villagePieceWeight;
            this.villagePiecesLimit = villagePiecesLimit;
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
                if (4 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public boolean canSpawnMoreVillagePieces() {
            if (this.villagePiecesLimit != 0 && this.villagePiecesSpawned >= this.villagePiecesLimit) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        public boolean canSpawnMoreVillagePiecesOfType(final int n) {
            if (this.villagePiecesLimit != 0 && this.villagePiecesSpawned >= this.villagePiecesLimit) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
    }
    
    public static class Well extends Village
    {
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        
        public Well(final Start start, final int n, final Random random, final int n2, final int n3) {
            super(start, n);
            this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                case 3:
                case 4: {
                    this.boundingBox = new StructureBoundingBox(n2, 0xEB ^ 0xAB, n3, n2 + (0x39 ^ 0x3F) - " ".length(), 0xCC ^ 0x82, n3 + (0xB6 ^ 0xB0) - " ".length());
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(n2, 0xE0 ^ 0xA0, n3, n2 + (0x3 ^ 0x5) - " ".length(), 0x60 ^ 0x2E, n3 + (0xA3 ^ 0xA5) - " ".length());
                    break;
                }
            }
        }
        
        public Well() {
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = Well.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xB8 ^ 0xBE);
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x33 ^ 0x37);
                "".length();
                if (1 < 0) {
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
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x73 ^ 0x76);
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Well.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.maxY - (0x4C ^ 0x48), this.boundingBox.minZ + " ".length(), EnumFacing.WEST, this.getComponentType());
            StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.maxY - (0x61 ^ 0x65), this.boundingBox.minZ + " ".length(), EnumFacing.EAST, this.getComponentType());
            StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.minX + " ".length(), this.boundingBox.maxY - (0xC6 ^ 0xC2), this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, this.getComponentType());
            StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.minX + " ".length(), this.boundingBox.maxY - (0x50 ^ 0x54), this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, this.getComponentType());
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + "   ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), " ".length(), 0x49 ^ 0x4D, 0x26 ^ 0x2A, 0x35 ^ 0x31, Blocks.cobblestone.getDefaultState(), Blocks.flowing_water.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.air.getDefaultState(), "  ".length(), 0x63 ^ 0x6F, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), "   ".length(), 0x76 ^ 0x7A, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), "  ".length(), 0xA0 ^ 0xAC, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), "   ".length(), 0xBB ^ 0xB7, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), " ".length(), 0x98 ^ 0x95, " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), " ".length(), 0x8 ^ 0x6, " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0x13 ^ 0x17, 0x41 ^ 0x4C, " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0x79 ^ 0x7D, 0x69 ^ 0x67, " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), " ".length(), 0xAB ^ 0xA6, 0xBA ^ 0xBE, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), " ".length(), 0x28 ^ 0x26, 0xB3 ^ 0xB7, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0xA4 ^ 0xA0, 0xBA ^ 0xB7, 0x8B ^ 0x8F, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0x2 ^ 0x6, 0x6 ^ 0x8, 0x14 ^ 0x10, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x7D ^ 0x72, " ".length(), 0xAF ^ 0xAB, 0xA5 ^ 0xAA, 0xC4 ^ 0xC0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            int i = "".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
            while (i <= (0x14 ^ 0x11)) {
                int j = "".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                while (j <= (0xBB ^ 0xBE)) {
                    if (j == 0 || j == (0x8E ^ 0x8B) || i == 0 || i == (0xBD ^ 0xB8)) {
                        this.setBlockState(world, Blocks.gravel.getDefaultState(), j, 0x22 ^ 0x29, i, structureBoundingBox);
                        this.clearCurrentPositionBlocksUpwards(world, j, 0xA ^ 0x6, i, structureBoundingBox);
                    }
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
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
                if (3 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class Field1 extends Village
    {
        private Block cropTypeB;
        private Block cropTypeC;
        private Block cropTypeA;
        private Block cropTypeD;
        private static final String[] I;
        
        static {
            I();
        }
        
        public static Field1 func_175851_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), 0x4D ^ 0x40, 0x0 ^ 0x4, 0x1B ^ 0x12, enumFacing);
            Field1 field1;
            if (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                field1 = new Field1(start, n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                field1 = null;
            }
            return field1;
        }
        
        private Block func_151559_a(final Random random) {
            switch (random.nextInt(0x3C ^ 0x39)) {
                case 0: {
                    return Blocks.carrots;
                }
                case 1: {
                    return Blocks.potatoes;
                }
                default: {
                    return Blocks.wheat;
                }
            }
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
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + (0x0 ^ 0x4) - " ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), 0xB3 ^ 0xBF, 0x2B ^ 0x2F, 0x5C ^ 0x54, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), " ".length(), "  ".length(), "".length(), 0x1B ^ 0x1C, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x16 ^ 0x12, "".length(), " ".length(), 0x59 ^ 0x5C, "".length(), 0x19 ^ 0x1E, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x75 ^ 0x72, "".length(), " ".length(), 0x7F ^ 0x77, "".length(), 0x9B ^ 0x9C, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x83 ^ 0x89, "".length(), " ".length(), 0x42 ^ 0x49, "".length(), 0x20 ^ 0x27, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), "".length(), "".length(), 0xB4 ^ 0xBC, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xE ^ 0x8, "".length(), "".length(), 0x2 ^ 0x4, "".length(), 0x49 ^ 0x41, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xCB ^ 0xC7, "".length(), "".length(), 0x7D ^ 0x71, "".length(), 0xE ^ 0x6, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), "".length(), 0x4B ^ 0x40, "".length(), "".length(), Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), 0x16 ^ 0x1E, 0x7C ^ 0x77, "".length(), 0x2A ^ 0x22, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "".length(), " ".length(), "   ".length(), "".length(), 0xA0 ^ 0xA7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5C ^ 0x55, "".length(), " ".length(), 0x8 ^ 0x1, "".length(), 0x8 ^ 0xF, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), "".length() != 0);
            int i = " ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (i <= (0x9E ^ 0x99)) {
                this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x62 ^ 0x65)), " ".length(), " ".length(), i, structureBoundingBox);
                this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x75 ^ 0x72)), "  ".length(), " ".length(), i, structureBoundingBox);
                this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x6D ^ 0x6A)), 0xAB ^ 0xAF, " ".length(), i, structureBoundingBox);
                this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x13 ^ 0x14)), 0x20 ^ 0x25, " ".length(), i, structureBoundingBox);
                this.setBlockState(world, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x54 ^ 0x53)), 0xC2 ^ 0xC5, " ".length(), i, structureBoundingBox);
                this.setBlockState(world, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x1C ^ 0x1B)), 0x6F ^ 0x67, " ".length(), i, structureBoundingBox);
                this.setBlockState(world, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0xB1 ^ 0xB6)), 0x43 ^ 0x49, " ".length(), i, structureBoundingBox);
                this.setBlockState(world, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x7C ^ 0x7B)), 0x93 ^ 0x98, " ".length(), i, structureBoundingBox);
                ++i;
            }
            int j = "".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
            while (j < (0x28 ^ 0x21)) {
                int k = "".length();
                "".length();
                if (4 < 3) {
                    throw null;
                }
                while (k < (0x5B ^ 0x56)) {
                    this.clearCurrentPositionBlocksUpwards(world, k, 0xB6 ^ 0xB2, j, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.dirt.getDefaultState(), k, -" ".length(), j, structureBoundingBox);
                    ++k;
                }
                ++j;
            }
            return " ".length() != 0;
        }
        
        private static void I() {
            (I = new String[0x62 ^ 0x6A])["".length()] = I("$0", "gqEAS");
            Field1.I[" ".length()] = I("\u00068", "EzmJN");
            Field1.I["  ".length()] = I("\u0000\t", "CJSxc");
            Field1.I["   ".length()] = I("*=", "iypvf");
            Field1.I[0x9A ^ 0x9E] = I("\u0007\u0003", "DBosW");
            Field1.I[0x6E ^ 0x6B] = I("0\u0000", "sBrHH");
            Field1.I[0xC4 ^ 0xC2] = I("\u0001\u0014", "BWQKe");
            Field1.I[0x65 ^ 0x62] = I("\"%", "aaipG");
        }
        
        public Field1(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.cropTypeA = this.func_151559_a(random);
            this.cropTypeB = this.func_151559_a(random);
            this.cropTypeC = this.func_151559_a(random);
            this.cropTypeD = this.func_151559_a(random);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.cropTypeA = Block.getBlockById(nbtTagCompound.getInteger(Field1.I[0x50 ^ 0x54]));
            this.cropTypeB = Block.getBlockById(nbtTagCompound.getInteger(Field1.I[0x21 ^ 0x24]));
            this.cropTypeC = Block.getBlockById(nbtTagCompound.getInteger(Field1.I[0x1B ^ 0x1D]));
            this.cropTypeD = Block.getBlockById(nbtTagCompound.getInteger(Field1.I[0x9E ^ 0x99]));
        }
        
        public Field1() {
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger(Field1.I["".length()], Block.blockRegistry.getIDForObject(this.cropTypeA));
            nbtTagCompound.setInteger(Field1.I[" ".length()], Block.blockRegistry.getIDForObject(this.cropTypeB));
            nbtTagCompound.setInteger(Field1.I["  ".length()], Block.blockRegistry.getIDForObject(this.cropTypeC));
            nbtTagCompound.setInteger(Field1.I["   ".length()], Block.blockRegistry.getIDForObject(this.cropTypeD));
        }
    }
    
    public static class Field2 extends Village
    {
        private Block cropTypeA;
        private Block cropTypeB;
        private static final String[] I;
        
        public Field2(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.cropTypeA = this.func_151560_a(random);
            this.cropTypeB = this.func_151560_a(random);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.cropTypeA = Block.getBlockById(nbtTagCompound.getInteger(Field2.I["  ".length()]));
            this.cropTypeB = Block.getBlockById(nbtTagCompound.getInteger(Field2.I["   ".length()]));
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + (0x2B ^ 0x2F) - " ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), 0x93 ^ 0x95, 0x3D ^ 0x39, 0x2 ^ 0xA, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), " ".length(), "  ".length(), "".length(), 0x7 ^ 0x0, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x1A ^ 0x1E, "".length(), " ".length(), 0x86 ^ 0x83, "".length(), 0xC6 ^ 0xC1, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), "".length(), "".length(), 0x57 ^ 0x5F, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xC2 ^ 0xC4, "".length(), "".length(), 0x55 ^ 0x53, "".length(), 0xE ^ 0x6, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), "".length(), 0xA3 ^ 0xA6, "".length(), "".length(), Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), 0x20 ^ 0x28, 0xBC ^ 0xB9, "".length(), 0xA5 ^ 0xAD, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "".length(), " ".length(), "   ".length(), "".length(), 0x95 ^ 0x92, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), "".length() != 0);
            int i = " ".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (i <= (0x89 ^ 0x8E)) {
                this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x8C ^ 0x8B)), " ".length(), " ".length(), i, structureBoundingBox);
                this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x3D ^ 0x3A)), "  ".length(), " ".length(), i, structureBoundingBox);
                this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x1A ^ 0x1D)), 0x83 ^ 0x87, " ".length(), i, structureBoundingBox);
                this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, "  ".length(), 0x8E ^ 0x89)), 0x9F ^ 0x9A, " ".length(), i, structureBoundingBox);
                ++i;
            }
            int j = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (j < (0x10 ^ 0x19)) {
                int k = "".length();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
                while (k < (0xAD ^ 0xAA)) {
                    this.clearCurrentPositionBlocksUpwards(world, k, 0x8C ^ 0x88, j, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.dirt.getDefaultState(), k, -" ".length(), j, structureBoundingBox);
                    ++k;
                }
                ++j;
            }
            return " ".length() != 0;
        }
        
        static {
            I();
        }
        
        private Block func_151560_a(final Random random) {
            switch (random.nextInt(0xC4 ^ 0xC1)) {
                case 0: {
                    return Blocks.carrots;
                }
                case 1: {
                    return Blocks.potatoes;
                }
                default: {
                    return Blocks.wheat;
                }
            }
        }
        
        private static void I() {
            (I = new String[0xC3 ^ 0xC7])["".length()] = I("\u00142", "WslOT");
            Field2.I[" ".length()] = I("1;", "ryKgV");
            Field2.I["  ".length()] = I("%\t", "fHVHv");
            Field2.I["   ".length()] = I("(%", "kgABn");
        }
        
        public static Field2 func_175852_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), 0xB1 ^ 0xB6, 0x36 ^ 0x32, 0x8B ^ 0x82, enumFacing);
            Field2 field2;
            if (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                field2 = new Field2(start, n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                field2 = null;
            }
            return field2;
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
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger(Field2.I["".length()], Block.blockRegistry.getIDForObject(this.cropTypeA));
            nbtTagCompound.setInteger(Field2.I[" ".length()], Block.blockRegistry.getIDForObject(this.cropTypeB));
        }
        
        public Field2() {
        }
    }
    
    public static class House2 extends Village
    {
        private static final List<WeightedRandomChestContent> villageBlacksmithChestContents;
        private boolean hasMadeChest;
        private static final String[] I;
        
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
                if (-1 == 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public static House2 func_175855_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), 0xF ^ 0x5, 0x76 ^ 0x70, 0xAA ^ 0xAD, enumFacing);
            House2 house2;
            if (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                house2 = new House2(start, n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                house2 = null;
            }
            return house2;
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\b\u0010\u0004\u0001\u001f", "Kxark");
            House2.I[" ".length()] = I(":\u001b*&\u001d", "ysOUi");
        }
        
        static {
            I();
            final WeightedRandomChestContent[] array = new WeightedRandomChestContent[0x44 ^ 0x55];
            array["".length()] = new WeightedRandomChestContent(Items.diamond, "".length(), " ".length(), "   ".length(), "   ".length());
            array[" ".length()] = new WeightedRandomChestContent(Items.iron_ingot, "".length(), " ".length(), 0x23 ^ 0x26, 0x96 ^ 0x9C);
            array["  ".length()] = new WeightedRandomChestContent(Items.gold_ingot, "".length(), " ".length(), "   ".length(), 0xC0 ^ 0xC5);
            array["   ".length()] = new WeightedRandomChestContent(Items.bread, "".length(), " ".length(), "   ".length(), 0x77 ^ 0x78);
            array[0x62 ^ 0x66] = new WeightedRandomChestContent(Items.apple, "".length(), " ".length(), "   ".length(), 0x6B ^ 0x64);
            array[0x4E ^ 0x4B] = new WeightedRandomChestContent(Items.iron_pickaxe, "".length(), " ".length(), " ".length(), 0x3B ^ 0x3E);
            array[0x50 ^ 0x56] = new WeightedRandomChestContent(Items.iron_sword, "".length(), " ".length(), " ".length(), 0xBA ^ 0xBF);
            array[0x5D ^ 0x5A] = new WeightedRandomChestContent(Items.iron_chestplate, "".length(), " ".length(), " ".length(), 0x13 ^ 0x16);
            array[0x98 ^ 0x90] = new WeightedRandomChestContent(Items.iron_helmet, "".length(), " ".length(), " ".length(), 0x5B ^ 0x5E);
            array[0xB9 ^ 0xB0] = new WeightedRandomChestContent(Items.iron_leggings, "".length(), " ".length(), " ".length(), 0x5B ^ 0x5E);
            array[0x94 ^ 0x9E] = new WeightedRandomChestContent(Items.iron_boots, "".length(), " ".length(), " ".length(), 0x36 ^ 0x33);
            array[0x13 ^ 0x18] = new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), "".length(), "   ".length(), 0x68 ^ 0x6F, 0x85 ^ 0x80);
            array[0xA1 ^ 0xAD] = new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), "".length(), "   ".length(), 0x7D ^ 0x7A, 0xF ^ 0xA);
            array[0xA3 ^ 0xAE] = new WeightedRandomChestContent(Items.saddle, "".length(), " ".length(), " ".length(), "   ".length());
            array[0x1 ^ 0xF] = new WeightedRandomChestContent(Items.iron_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            array[0x49 ^ 0x46] = new WeightedRandomChestContent(Items.golden_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            array[0x2B ^ 0x3B] = new WeightedRandomChestContent(Items.diamond_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            villageBlacksmithChestContents = Lists.newArrayList((Object[])array);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.hasMadeChest = nbtTagCompound.getBoolean(House2.I[" ".length()]);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + (0x8A ^ 0x8C) - " ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), 0x5B ^ 0x52, 0x47 ^ 0x43, 0xBA ^ 0xBC, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x64 ^ 0x6D, "".length(), 0x57 ^ 0x51, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x7A ^ 0x7E, "".length(), 0x77 ^ 0x7E, 0x1A ^ 0x1E, 0xE ^ 0x8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x42 ^ 0x47, "".length(), 0x44 ^ 0x4D, 0x2A ^ 0x2F, 0x33 ^ 0x35, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x9E ^ 0x9B, " ".length(), 0x15 ^ 0x1D, 0x5A ^ 0x5F, 0xD ^ 0x8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "".length(), "  ".length(), "   ".length(), "".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), "".length(), 0x94 ^ 0x90, "".length(), Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), "   ".length(), 0x11 ^ 0x15, "".length(), Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), 0x16 ^ 0x10, "".length(), 0xC2 ^ 0xC6, 0xBC ^ 0xBA, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.planks.getDefaultState(), "   ".length(), "   ".length(), " ".length(), structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), "  ".length(), "   ".length(), "   ".length(), "  ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x57 ^ 0x53, " ".length(), "   ".length(), 0x1 ^ 0x4, "   ".length(), "   ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), " ".length(), "".length(), "   ".length(), 0xB9 ^ 0xBC, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x8D ^ 0x8B, 0x3F ^ 0x3A, "   ".length(), 0x9F ^ 0x99, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x11 ^ 0x14, " ".length(), "".length(), 0xC6 ^ 0xC3, "   ".length(), "".length(), Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x62 ^ 0x6B, " ".length(), "".length(), 0x3E ^ 0x37, "   ".length(), "".length(), Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x11 ^ 0x17, " ".length(), 0x2C ^ 0x28, 0x84 ^ 0x8D, 0x5F ^ 0x5B, 0x41 ^ 0x47, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.flowing_lava.getDefaultState(), 0x79 ^ 0x7E, " ".length(), 0xAF ^ 0xAA, structureBoundingBox);
            this.setBlockState(world, Blocks.flowing_lava.getDefaultState(), 0xAC ^ 0xA4, " ".length(), 0xAD ^ 0xA8, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 0x3E ^ 0x37, "  ".length(), 0x9D ^ 0x98, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 0x73 ^ 0x7A, "  ".length(), 0x7E ^ 0x7A, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0x61 ^ 0x66, "  ".length(), 0x4C ^ 0x48, 0x86 ^ 0x8E, "  ".length(), 0xB8 ^ 0xBD, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0xA8 ^ 0xAE, " ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.furnace.getDefaultState(), 0x40 ^ 0x46, "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.furnace.getDefaultState(), 0x9B ^ 0x9D, "   ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.double_stone_slab.getDefaultState(), 0xAA ^ 0xA2, " ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), 0x1B ^ 0x1F, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), "  ".length(), 0x50 ^ 0x56, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x3A ^ 0x3E, "  ".length(), 0x94 ^ 0x92, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "  ".length(), " ".length(), 0xC ^ 0x8, structureBoundingBox);
            this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), "  ".length(), "  ".length(), 0xBB ^ 0xBF, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), " ".length(), " ".length(), 0x96 ^ 0x93, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, "   ".length())), "  ".length(), " ".length(), 0x8B ^ 0x8E, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, " ".length())), " ".length(), " ".length(), 0x94 ^ 0x90, structureBoundingBox);
            if (!this.hasMadeChest && structureBoundingBox.isVecInside(new BlockPos(this.getXWithOffset(0x2A ^ 0x2F, 0x99 ^ 0x9C), this.getYWithOffset(" ".length()), this.getZWithOffset(0xF ^ 0xA, 0x53 ^ 0x56)))) {
                this.hasMadeChest = (" ".length() != 0);
                this.generateChestContents(world, structureBoundingBox, random, 0x46 ^ 0x43, " ".length(), 0x5B ^ 0x5E, House2.villageBlacksmithChestContents, "   ".length() + random.nextInt(0x88 ^ 0x8E));
            }
            int i = 0x57 ^ 0x51;
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (i <= (0x51 ^ 0x59)) {
                if (this.getBlockStateFromPos(world, i, "".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(world, i, -" ".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() != Material.air) {
                    this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length())), i, "".length(), -" ".length(), structureBoundingBox);
                }
                ++i;
            }
            int j = "".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (j < (0x7D ^ 0x7A)) {
                int k = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (k < (0x10 ^ 0x1A)) {
                    this.clearCurrentPositionBlocksUpwards(world, k, 0x35 ^ 0x33, j, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), k, -" ".length(), j, structureBoundingBox);
                    ++k;
                }
                ++j;
            }
            this.spawnVillagers(world, structureBoundingBox, 0x2B ^ 0x2C, " ".length(), " ".length(), " ".length());
            return " ".length() != 0;
        }
        
        public House2() {
        }
        
        public House2(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        protected int func_180779_c(final int n, final int n2) {
            return "   ".length();
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(House2.I["".length()], this.hasMadeChest);
        }
    }
    
    public static class House3 extends Village
    {
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
                if (2 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public House3(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public House3() {
        }
        
        public static House3 func_175849_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), 0x1A ^ 0x13, 0x12 ^ 0x15, 0xA2 ^ 0xAE, enumFacing);
            House3 house3;
            if (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                house3 = new House3(start, n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (-1 == 1) {
                    throw null;
                }
            }
            else {
                house3 = null;
            }
            return house3;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + (0x1C ^ 0x1B) - " ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), " ".length(), 0x6E ^ 0x69, 0x36 ^ 0x32, 0x63 ^ 0x67, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), 0x48 ^ 0x4E, 0x38 ^ 0x30, 0x74 ^ 0x70, 0x73 ^ 0x79, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), "".length(), 0x33 ^ 0x36, 0x34 ^ 0x3C, "".length(), 0x17 ^ 0x1D, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), " ".length(), 0x25 ^ 0x22, "".length(), 0xC5 ^ 0xC1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), "".length(), "   ".length(), 0x50 ^ 0x55, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x6 ^ 0xE, "".length(), "".length(), 0x2B ^ 0x23, "   ".length(), 0x5C ^ 0x56, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), "".length(), 0x1A ^ 0x1D, "  ".length(), "".length(), Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), 0xB0 ^ 0xB5, "  ".length(), " ".length(), 0x1E ^ 0x1B, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), "".length(), 0x45 ^ 0x43, "  ".length(), "   ".length(), 0xA1 ^ 0xAB, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "".length(), 0x12 ^ 0x18, 0x64 ^ 0x63, "   ".length(), 0xA2 ^ 0xA8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "".length(), 0xAF ^ 0xA8, "   ".length(), "".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0x5F ^ 0x5A, "  ".length(), "   ".length(), 0x19 ^ 0x1C, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x23 ^ 0x27, " ".length(), 0xBF ^ 0xB7, 0xB4 ^ 0xB0, " ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x90 ^ 0x94, 0x97 ^ 0x93, "   ".length(), 0xAE ^ 0xAA, 0xA6 ^ 0xA2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x65 ^ 0x60, "  ".length(), 0xA2 ^ 0xAA, 0x6A ^ 0x6F, "   ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.planks.getDefaultState(), "".length(), 0x4F ^ 0x4B, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), "".length(), 0xA7 ^ 0xA3, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0xA1 ^ 0xA9, 0x95 ^ 0x91, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x77 ^ 0x7F, 0x51 ^ 0x55, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x12 ^ 0x1A, 0x27 ^ 0x23, 0x75 ^ 0x71, structureBoundingBox);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.oak_stairs, "   ".length());
            final int metadataWithOffset2 = this.getMetadataWithOffset(Blocks.oak_stairs, "  ".length());
            int i = -" ".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (i <= "  ".length()) {
                int j = "".length();
                "".length();
                if (3 < 0) {
                    throw null;
                }
                while (j <= (0x8 ^ 0x0)) {
                    this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset), j, (0x3 ^ 0x7) + i, i, structureBoundingBox);
                    if ((i > -" ".length() || j <= " ".length()) && (i > 0 || j <= "   ".length()) && (i > " ".length() || j <= (0x60 ^ 0x64) || j >= (0x31 ^ 0x37))) {
                        this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset2), j, (0x5A ^ 0x5E) + i, (0x87 ^ 0x82) - i, structureBoundingBox);
                    }
                    ++j;
                }
                ++i;
            }
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), 0x31 ^ 0x35, 0xC ^ 0x9, "   ".length(), 0x5B ^ 0x5F, 0x69 ^ 0x63, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x70 ^ 0x77, 0x78 ^ 0x7C, "  ".length(), 0x3E ^ 0x39, 0xB8 ^ 0xBC, 0xCB ^ 0xC1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7B ^ 0x7F, 0xA ^ 0xF, 0x98 ^ 0x9C, 0x62 ^ 0x66, 0x4C ^ 0x49, 0x77 ^ 0x7D, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x9B ^ 0x9D, 0xB8 ^ 0xBD, 0xB4 ^ 0xB0, 0xC4 ^ 0xC2, 0xC4 ^ 0xC1, 0xC8 ^ 0xC2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xAB ^ 0xAE, 0x1B ^ 0x1D, "   ".length(), 0x5D ^ 0x58, 0x57 ^ 0x51, 0x30 ^ 0x3A, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            final int metadataWithOffset3 = this.getMetadataWithOffset(Blocks.oak_stairs, "".length());
            int k = 0x4C ^ 0x48;
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (k >= " ".length()) {
                this.setBlockState(world, Blocks.planks.getDefaultState(), k, "  ".length() + k, (0x9A ^ 0x9D) - k, structureBoundingBox);
                int l = (0x76 ^ 0x7E) - k;
                "".length();
                if (3 <= 2) {
                    throw null;
                }
                while (l <= (0x6A ^ 0x60)) {
                    this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset3), k, "  ".length() + k, l, structureBoundingBox);
                    ++l;
                }
                --k;
            }
            final int metadataWithOffset4 = this.getMetadataWithOffset(Blocks.oak_stairs, " ".length());
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0xA9 ^ 0xAF, 0x70 ^ 0x76, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x97 ^ 0x90, 0x23 ^ 0x26, 0x1E ^ 0x1A, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset4), 0x90 ^ 0x96, 0xA ^ 0xC, 0x18 ^ 0x1C, structureBoundingBox);
            int n = 0xB3 ^ 0xB5;
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (n <= (0xA9 ^ 0xA1)) {
                int n2 = 0xBD ^ 0xB8;
                "".length();
                if (2 == 0) {
                    throw null;
                }
                while (n2 <= (0x42 ^ 0x48)) {
                    this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset4), n, (0xB9 ^ 0xB5) - n, n2, structureBoundingBox);
                    ++n2;
                }
                ++n;
            }
            this.setBlockState(world, Blocks.log.getDefaultState(), "".length(), "  ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "".length(), "  ".length(), 0x66 ^ 0x62, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0xA4 ^ 0xA0, "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0xA6 ^ 0xA3, "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0x15 ^ 0x13, "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0x8C ^ 0x84, "  ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x1E ^ 0x16, "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0xB ^ 0x3, "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0x82 ^ 0x8A, "  ".length(), 0x77 ^ 0x73, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x7B ^ 0x73, "  ".length(), 0x68 ^ 0x6D, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0x46 ^ 0x4E, "  ".length(), 0x91 ^ 0x97, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x9C ^ 0x94, "  ".length(), 0x59 ^ 0x5E, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x24 ^ 0x2C, "  ".length(), 0x1D ^ 0x15, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0x26 ^ 0x2E, "  ".length(), 0xA6 ^ 0xAF, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "  ".length(), "  ".length(), 0x98 ^ 0x9E, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), "  ".length(), 0x5D ^ 0x5A, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), "  ".length(), 0x18 ^ 0x10, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "  ".length(), "  ".length(), 0x6A ^ 0x63, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0xC3 ^ 0xC7, 0x31 ^ 0x35, 0x90 ^ 0x9A, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x92 ^ 0x97, 0x70 ^ 0x74, 0xB4 ^ 0xBE, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0xB9 ^ 0xBF, 0x89 ^ 0x8D, 0x74 ^ 0x7E, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x8C ^ 0x89, 0x7B ^ 0x7E, 0xCE ^ 0xC4, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), "  ".length(), " ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), "  ".length(), "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode), "  ".length(), "   ".length(), " ".length(), structureBoundingBox);
            this.placeDoorCurrentPosition(world, structureBoundingBox, random, "  ".length(), " ".length(), "".length(), EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, " ".length())));
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), -" ".length(), "   ".length(), "  ".length(), -" ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            if (this.getBlockStateFromPos(world, "  ".length(), "".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(world, "  ".length(), -" ".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() != Material.air) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length())), "  ".length(), "".length(), -" ".length(), structureBoundingBox);
            }
            int length = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (length < (0x4A ^ 0x4F)) {
                int length2 = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (length2 < (0x2B ^ 0x22)) {
                    this.clearCurrentPositionBlocksUpwards(world, length2, 0xB ^ 0xC, length, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), length2, -" ".length(), length, structureBoundingBox);
                    ++length2;
                }
                ++length;
            }
            int n3 = 0x72 ^ 0x77;
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (n3 < (0xCB ^ 0xC0)) {
                int length3 = "  ".length();
                "".length();
                if (4 == 3) {
                    throw null;
                }
                while (length3 < (0xBD ^ 0xB4)) {
                    this.clearCurrentPositionBlocksUpwards(world, length3, 0x61 ^ 0x66, n3, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), length3, -" ".length(), n3, structureBoundingBox);
                    ++length3;
                }
                ++n3;
            }
            this.spawnVillagers(world, structureBoundingBox, 0x74 ^ 0x70, " ".length(), "  ".length(), "  ".length());
            return " ".length() != 0;
        }
    }
    
    public static class Torch extends Village
    {
        public static StructureBoundingBox func_175856_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), "   ".length(), 0x79 ^ 0x7D, "  ".length(), enumFacing);
            StructureBoundingBox structureBoundingBox;
            if (StructureComponent.findIntersecting(list, componentToAddBoundingBox) != null) {
                structureBoundingBox = null;
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            else {
                structureBoundingBox = componentToAddBoundingBox;
            }
            return structureBoundingBox;
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
        
        public Torch() {
        }
        
        public Torch(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + (0x6 ^ 0x2) - " ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), "  ".length(), "   ".length(), " ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), " ".length(), "".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), " ".length(), " ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), " ".length(), "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.wool.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), " ".length(), "   ".length(), "".length(), structureBoundingBox);
            int n;
            if (this.coordBaseMode != EnumFacing.EAST && this.coordBaseMode != EnumFacing.NORTH) {
                n = "".length();
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            final int n2 = n;
            final IBlockState withProperty = Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode.rotateY());
            int n3;
            if (n2 != 0) {
                n3 = "  ".length();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            this.setBlockState(world, withProperty, n3, "   ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode), " ".length(), "   ".length(), " ".length(), structureBoundingBox);
            final IBlockState withProperty2 = Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode.rotateYCCW());
            int n4;
            if (n2 != 0) {
                n4 = "".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else {
                n4 = "  ".length();
            }
            this.setBlockState(world, withProperty2, n4, "   ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode.getOpposite()), " ".length(), "   ".length(), -" ".length(), structureBoundingBox);
            return " ".length() != 0;
        }
    }
    
    public static class WoodHut extends Village
    {
        private int tablePosition;
        private static final String[] I;
        private boolean isTallHouse;
        
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
        
        public WoodHut(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.isTallHouse = random.nextBoolean();
            this.tablePosition = random.nextInt("   ".length());
        }
        
        public static WoodHut func_175853_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), 0x54 ^ 0x50, 0x95 ^ 0x93, 0x61 ^ 0x64, enumFacing);
            WoodHut woodHut;
            if (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                woodHut = new WoodHut(start, n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                woodHut = null;
            }
            return woodHut;
        }
        
        static {
            I();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + (0x7C ^ 0x7A) - " ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), " ".length(), "   ".length(), 0xBC ^ 0xB9, 0xA2 ^ 0xA6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), "   ".length(), "".length(), 0x2F ^ 0x2B, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), " ".length(), "  ".length(), "".length(), "   ".length(), Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), "".length() != 0);
            if (this.isTallHouse) {
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x47 ^ 0x43, " ".length(), "  ".length(), 0x61 ^ 0x65, "   ".length(), Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            else {
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x8C ^ 0x89, " ".length(), "  ".length(), 0x95 ^ 0x90, "   ".length(), Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            }
            this.setBlockState(world, Blocks.log.getDefaultState(), " ".length(), 0xA4 ^ 0xA0, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "  ".length(), 0xB9 ^ 0xBD, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), " ".length(), 0x12 ^ 0x16, 0x9B ^ 0x9F, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "  ".length(), 0x96 ^ 0x92, 0xB1 ^ 0xB5, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "".length(), 0x13 ^ 0x17, " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "".length(), 0x85 ^ 0x81, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "".length(), 0x58 ^ 0x5C, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "   ".length(), 0xE ^ 0xA, " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "   ".length(), 0x27 ^ 0x23, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "   ".length(), 0x15 ^ 0x11, "   ".length(), structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), "".length(), "   ".length(), "".length(), Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), "   ".length(), "   ".length(), "".length(), Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), 0x95 ^ 0x91, "".length(), "   ".length(), 0xBE ^ 0xBA, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), 0x99 ^ 0x9D, "   ".length(), "   ".length(), 0x16 ^ 0x12, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), " ".length(), "".length(), "   ".length(), "   ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), " ".length(), "   ".length(), "   ".length(), "   ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "".length(), "  ".length(), "   ".length(), "".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x65 ^ 0x61, "  ".length(), "   ".length(), 0x22 ^ 0x26, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "   ".length(), "  ".length(), "  ".length(), structureBoundingBox);
            if (this.tablePosition > 0) {
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), this.tablePosition, " ".length(), "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), this.tablePosition, "  ".length(), "   ".length(), structureBoundingBox);
            }
            this.setBlockState(world, Blocks.air.getDefaultState(), " ".length(), " ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), " ".length(), "  ".length(), "".length(), structureBoundingBox);
            this.placeDoorCurrentPosition(world, structureBoundingBox, random, " ".length(), " ".length(), "".length(), EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, " ".length())));
            if (this.getBlockStateFromPos(world, " ".length(), "".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(world, " ".length(), -" ".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() != Material.air) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length())), " ".length(), "".length(), -" ".length(), structureBoundingBox);
            }
            int i = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (i < (0x32 ^ 0x37)) {
                int j = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (j < (0x73 ^ 0x77)) {
                    this.clearCurrentPositionBlocksUpwards(world, j, 0x91 ^ 0x97, i, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), j, -" ".length(), i, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            this.spawnVillagers(world, structureBoundingBox, " ".length(), " ".length(), "  ".length(), " ".length());
            return " ".length() != 0;
        }
        
        public WoodHut() {
        }
        
        private static void I() {
            (I = new String[0x72 ^ 0x76])["".length()] = I(":", "newsA");
            WoodHut.I[" ".length()] = I("\u0013", "PBfof");
            WoodHut.I["  ".length()] = I("\u0016", "BhgeL");
            WoodHut.I["   ".length()] = I("-", "nIYqf");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger(WoodHut.I["".length()], this.tablePosition);
            nbtTagCompound.setBoolean(WoodHut.I[" ".length()], this.isTallHouse);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.tablePosition = nbtTagCompound.getInteger(WoodHut.I["  ".length()]);
            this.isTallHouse = nbtTagCompound.getBoolean(WoodHut.I["   ".length()]);
        }
    }
    
    public abstract static class Road extends Village
    {
        protected Road(final Start start, final int n) {
            super(start, n);
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
                if (3 != 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Road() {
        }
    }
    
    public static class Hall extends Village
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + (0x1 ^ 0x6) - " ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), " ".length(), 0x6E ^ 0x69, 0x2D ^ 0x29, 0x91 ^ 0x95, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), 0x4F ^ 0x49, 0x7E ^ 0x76, 0x69 ^ 0x6D, 0xA8 ^ 0xA2, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), "".length(), 0xC7 ^ 0xC1, 0x20 ^ 0x28, "".length(), 0x11 ^ 0x1B, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x18 ^ 0x1E, "".length(), 0xC1 ^ 0xC7, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), 0xB5 ^ 0xB3, "  ".length(), " ".length(), 0x77 ^ 0x7D, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xAE ^ 0xA6, " ".length(), 0x39 ^ 0x3F, 0x91 ^ 0x99, " ".length(), 0x2E ^ 0x24, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), 0x87 ^ 0x8D, 0x3F ^ 0x38, " ".length(), 0x46 ^ 0x4C, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), " ".length(), 0x91 ^ 0x96, "".length(), 0x8 ^ 0xC, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), "".length(), "   ".length(), 0x0 ^ 0x5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x84 ^ 0x8C, "".length(), "".length(), 0x7B ^ 0x73, "   ".length(), 0x5B ^ 0x5E, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), "".length(), 0xB1 ^ 0xB6, " ".length(), "".length(), Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), 0xB ^ 0xE, 0xC6 ^ 0xC1, " ".length(), 0x87 ^ 0x82, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "".length(), 0xB8 ^ 0xBF, "   ".length(), "".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0xA7 ^ 0xA2, 0x97 ^ 0x90, "   ".length(), 0x28 ^ 0x2D, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x16 ^ 0x12, " ".length(), 0x47 ^ 0x4F, 0x64 ^ 0x60, " ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x7F ^ 0x7B, 0x85 ^ 0x81, 0x2D ^ 0x25, 0x9A ^ 0x9E, 0x3E ^ 0x3A, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x4E ^ 0x4B, "  ".length(), 0x3E ^ 0x36, 0xAB ^ 0xAE, "   ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.planks.getDefaultState(), "".length(), 0x38 ^ 0x3C, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), "".length(), 0xC4 ^ 0xC0, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x19 ^ 0x11, 0x56 ^ 0x52, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x3F ^ 0x37, 0x54 ^ 0x50, "   ".length(), structureBoundingBox);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.oak_stairs, "   ".length());
            final int metadataWithOffset2 = this.getMetadataWithOffset(Blocks.oak_stairs, "  ".length());
            int i = -" ".length();
            "".length();
            if (false == true) {
                throw null;
            }
            while (i <= "  ".length()) {
                int j = "".length();
                "".length();
                if (3 == 4) {
                    throw null;
                }
                while (j <= (0xBD ^ 0xB5)) {
                    this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset), j, (0x59 ^ 0x5D) + i, i, structureBoundingBox);
                    this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset2), j, (0xA4 ^ 0xA0) + i, (0x9F ^ 0x9A) - i, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            this.setBlockState(world, Blocks.log.getDefaultState(), "".length(), "  ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), "".length(), "  ".length(), 0xBB ^ 0xBF, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0x1 ^ 0x9, "  ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0x56 ^ 0x5E, "  ".length(), 0x15 ^ 0x11, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0xA0 ^ 0xA8, "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x4E ^ 0x46, "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), "  ".length(), 0x13 ^ 0x16, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "   ".length(), "  ".length(), 0x9C ^ 0x99, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x2A ^ 0x2F, "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x8B ^ 0x8D, "  ".length(), 0x80 ^ 0x85, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "  ".length(), " ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), "  ".length(), "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), " ".length(), " ".length(), 0x65 ^ 0x61, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, "   ".length())), "  ".length(), " ".length(), 0x62 ^ 0x66, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, " ".length())), " ".length(), " ".length(), "   ".length(), structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0x37 ^ 0x32, "".length(), " ".length(), 0x6C ^ 0x6B, "".length(), "   ".length(), Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.double_stone_slab.getDefaultState(), 0x2C ^ 0x2A, " ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.double_stone_slab.getDefaultState(), 0xAC ^ 0xAA, " ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), "  ".length(), " ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), "  ".length(), "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode), "  ".length(), "   ".length(), " ".length(), structureBoundingBox);
            this.placeDoorCurrentPosition(world, structureBoundingBox, random, "  ".length(), " ".length(), "".length(), EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, " ".length())));
            if (this.getBlockStateFromPos(world, "  ".length(), "".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(world, "  ".length(), -" ".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() != Material.air) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length())), "  ".length(), "".length(), -" ".length(), structureBoundingBox);
            }
            this.setBlockState(world, Blocks.air.getDefaultState(), 0xBF ^ 0xB9, " ".length(), 0x86 ^ 0x83, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x70 ^ 0x76, "  ".length(), 0x1 ^ 0x4, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode.getOpposite()), 0xB9 ^ 0xBF, "   ".length(), 0x20 ^ 0x24, structureBoundingBox);
            this.placeDoorCurrentPosition(world, structureBoundingBox, random, 0x62 ^ 0x64, " ".length(), 0x3D ^ 0x38, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, " ".length())));
            int k = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
            while (k < (0xAA ^ 0xAF)) {
                int l = "".length();
                "".length();
                if (4 < 3) {
                    throw null;
                }
                while (l < (0xAB ^ 0xA2)) {
                    this.clearCurrentPositionBlocksUpwards(world, l, 0xF ^ 0x8, k, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), l, -" ".length(), k, structureBoundingBox);
                    ++l;
                }
                ++k;
            }
            this.spawnVillagers(world, structureBoundingBox, 0x6B ^ 0x6F, " ".length(), "  ".length(), "  ".length());
            return " ".length() != 0;
        }
        
        public static Hall func_175857_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), 0x24 ^ 0x2D, 0xAC ^ 0xAB, 0x14 ^ 0x1F, enumFacing);
            Hall hall;
            if (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                hall = new Hall(start, n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                hall = null;
            }
            return hall;
        }
        
        public Hall(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public Hall() {
        }
        
        @Override
        protected int func_180779_c(final int n, final int n2) {
            int func_180779_c;
            if (n == 0) {
                func_180779_c = (0xB6 ^ 0xB2);
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                func_180779_c = super.func_180779_c(n, n2);
            }
            return func_180779_c;
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
                if (-1 >= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class House1 extends Village
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + (0x40 ^ 0x49) - " ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), " ".length(), 0x12 ^ 0x15, 0xB0 ^ 0xB5, 0x7C ^ 0x78, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0xCC ^ 0xC4, "".length(), 0x75 ^ 0x70, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0xB4 ^ 0xB1, "".length(), 0x9B ^ 0x93, 0x8 ^ 0xD, 0x65 ^ 0x60, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x44 ^ 0x42, " ".length(), 0x17 ^ 0x1F, 0xA8 ^ 0xAE, 0x1 ^ 0x5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x5 ^ 0x2, "  ".length(), 0x5 ^ 0xD, 0x68 ^ 0x6F, "   ".length(), Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.oak_stairs, "   ".length());
            final int metadataWithOffset2 = this.getMetadataWithOffset(Blocks.oak_stairs, "  ".length());
            int i = -" ".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
            while (i <= "  ".length()) {
                int j = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (j <= (0x8C ^ 0x84)) {
                    this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset), j, (0xAB ^ 0xAD) + i, i, structureBoundingBox);
                    this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset2), j, (0x8A ^ 0x8C) + i, (0x87 ^ 0x82) - i, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), "".length(), " ".length(), 0x41 ^ 0x44, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x7D ^ 0x78, 0x2F ^ 0x27, " ".length(), 0x7 ^ 0x2, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x83 ^ 0x8B, " ".length(), "".length(), 0x3A ^ 0x32, " ".length(), 0x20 ^ 0x24, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), "".length(), 0xBD ^ 0xBA, " ".length(), "".length(), Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), 0x87 ^ 0x83, "".length(), Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0x30 ^ 0x35, "".length(), 0x85 ^ 0x81, 0x7B ^ 0x7E, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x76 ^ 0x7E, "  ".length(), 0x90 ^ 0x95, 0x61 ^ 0x69, 0x8D ^ 0x89, 0x96 ^ 0x93, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x1A ^ 0x12, "  ".length(), "".length(), 0xAB ^ 0xA3, 0x0 ^ 0x4, "".length(), Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), " ".length(), "".length(), 0x48 ^ 0x4C, 0x7C ^ 0x78, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0x13 ^ 0x16, 0x7D ^ 0x7A, 0x69 ^ 0x6D, 0xE ^ 0xB, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xC8 ^ 0xC0, "  ".length(), " ".length(), 0x34 ^ 0x3C, 0xA8 ^ 0xAC, 0x2C ^ 0x28, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "".length(), 0xC4 ^ 0xC3, 0x5D ^ 0x59, "".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0xBB ^ 0xBF, "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0xA ^ 0xF, "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x72 ^ 0x74, "  ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x89 ^ 0x8D, "   ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x13 ^ 0x16, "   ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x33 ^ 0x35, "   ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "   ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "   ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x32 ^ 0x3A, "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x23 ^ 0x2B, "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x66 ^ 0x6E, "   ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x26 ^ 0x2E, "   ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), "  ".length(), 0x45 ^ 0x40, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "   ".length(), "  ".length(), 0x60 ^ 0x65, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x42 ^ 0x47, "  ".length(), 0xA ^ 0xF, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x89 ^ 0x8F, "  ".length(), 0x25 ^ 0x20, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x8F ^ 0x8B, " ".length(), 0x27 ^ 0x20, 0x7 ^ 0x3, " ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x39 ^ 0x3D, 0x38 ^ 0x3C, 0x57 ^ 0x50, 0x2B ^ 0x2F, 0x1D ^ 0x19, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0x3C ^ 0x38, 0x8B ^ 0x8C, "   ".length(), 0x3B ^ 0x3F, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x89 ^ 0x8E, " ".length(), 0xA7 ^ 0xA3, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, "".length())), 0x2F ^ 0x28, " ".length(), "   ".length(), structureBoundingBox);
            final int metadataWithOffset3 = this.getMetadataWithOffset(Blocks.oak_stairs, "   ".length());
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset3), 0x15 ^ 0x13, " ".length(), 0x56 ^ 0x52, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset3), 0xB7 ^ 0xB2, " ".length(), 0x8A ^ 0x8E, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset3), 0xAF ^ 0xAB, " ".length(), 0x79 ^ 0x7D, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset3), "   ".length(), " ".length(), 0x73 ^ 0x77, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0x35 ^ 0x33, " ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), 0x6A ^ 0x6C, "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0xA5 ^ 0xA1, " ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), 0x52 ^ 0x56, "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.crafting_table.getDefaultState(), 0x59 ^ 0x5E, " ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), " ".length(), " ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), " ".length(), "  ".length(), "".length(), structureBoundingBox);
            this.placeDoorCurrentPosition(world, structureBoundingBox, random, " ".length(), " ".length(), "".length(), EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, " ".length())));
            if (this.getBlockStateFromPos(world, " ".length(), "".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(world, " ".length(), -" ".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() != Material.air) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length())), " ".length(), "".length(), -" ".length(), structureBoundingBox);
            }
            int k = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (k < (0x5 ^ 0x3)) {
                int l = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (l < (0x1B ^ 0x12)) {
                    this.clearCurrentPositionBlocksUpwards(world, l, 0x44 ^ 0x4D, k, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), l, -" ".length(), k, structureBoundingBox);
                    ++l;
                }
                ++k;
            }
            this.spawnVillagers(world, structureBoundingBox, "  ".length(), " ".length(), "  ".length(), " ".length());
            return " ".length() != 0;
        }
        
        public House1(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public House1() {
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
        
        public static House1 func_175850_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), 0x49 ^ 0x40, 0x70 ^ 0x79, 0x3B ^ 0x3D, enumFacing);
            House1 house1;
            if (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                house1 = new House1(start, n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                house1 = null;
            }
            return house1;
        }
        
        @Override
        protected int func_180779_c(final int n, final int n2) {
            return " ".length();
        }
    }
    
    public static class Church extends Village
    {
        public static Church func_175854_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), 0xA2 ^ 0xA7, 0xA1 ^ 0xAD, 0x9C ^ 0x95, enumFacing);
            Church church;
            if (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                church = new Church(start, n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else {
                church = null;
            }
            return church;
        }
        
        @Override
        protected int func_180779_c(final int n, final int n2) {
            return "  ".length();
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
                if (4 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return " ".length() != 0;
                }
                this.boundingBox.offset("".length(), this.field_143015_k - this.boundingBox.maxY + (0x85 ^ 0x89) - " ".length(), "".length());
            }
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), " ".length(), "   ".length(), "   ".length(), 0x58 ^ 0x5F, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x6D ^ 0x68, " ".length(), "   ".length(), 0x3D ^ 0x34, "   ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), "".length(), "   ".length(), "".length(), 0xB6 ^ 0xBE, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "".length(), "   ".length(), 0xB4 ^ 0xBE, "".length(), Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), " ".length(), "".length(), 0x4F ^ 0x45, "   ".length(), Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x9B ^ 0x9F, " ".length(), " ".length(), 0x74 ^ 0x70, 0x68 ^ 0x62, "   ".length(), Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), 0x24 ^ 0x20, "".length(), 0x45 ^ 0x41, 0x74 ^ 0x73, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xC1 ^ 0xC5, "".length(), 0x4B ^ 0x4F, 0x78 ^ 0x7C, 0x38 ^ 0x3C, 0x1E ^ 0x19, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0xBA ^ 0xB2, "   ".length(), 0xC3 ^ 0xC7, 0x57 ^ 0x5F, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x50 ^ 0x55, 0x54 ^ 0x50, "   ".length(), 0x25 ^ 0x2F, 0x4F ^ 0x4B, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0xC3 ^ 0xC6, 0x5A ^ 0x5F, "   ".length(), 0x81 ^ 0x84, 0xC3 ^ 0xC4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x68 ^ 0x61, "".length(), 0x24 ^ 0x20, 0x2D ^ 0x24, 0x31 ^ 0x35, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0xA0 ^ 0xA4, "".length(), 0x88 ^ 0x8C, 0xB9 ^ 0xBD, 0x5C ^ 0x58, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "".length(), 0x7F ^ 0x74, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x4B ^ 0x4F, 0x87 ^ 0x8C, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "  ".length(), 0xF ^ 0x4, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "  ".length(), 0xB2 ^ 0xB9, 0x4C ^ 0x48, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), " ".length(), " ".length(), 0xC5 ^ 0xC3, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), " ".length(), " ".length(), 0x45 ^ 0x42, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "  ".length(), " ".length(), 0x79 ^ 0x7E, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "   ".length(), " ".length(), 0x5F ^ 0x59, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), "   ".length(), " ".length(), 0x29 ^ 0x2E, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length())), " ".length(), " ".length(), 0x1 ^ 0x4, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length())), "  ".length(), " ".length(), 0xA3 ^ 0xA5, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length())), "   ".length(), " ".length(), 0x47 ^ 0x42, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, " ".length())), " ".length(), "  ".length(), 0x11 ^ 0x16, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "".length())), "   ".length(), "  ".length(), 0x7E ^ 0x79, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "   ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x54 ^ 0x50, "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x98 ^ 0x9C, "   ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), 0x51 ^ 0x57, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), 0xAE ^ 0xA9, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x95 ^ 0x91, 0x22 ^ 0x24, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0x48 ^ 0x4C, 0x65 ^ 0x62, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), 0x1C ^ 0x1A, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), 0x40 ^ 0x47, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), 0x85 ^ 0x83, 0x1 ^ 0x5, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), 0x36 ^ 0x31, 0xC0 ^ 0xC4, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "".length(), "   ".length(), 0x44 ^ 0x42, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0xA4 ^ 0xA0, "   ".length(), 0x1D ^ 0x1B, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), "  ".length(), "   ".length(), 0xCB ^ 0xC3, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode.getOpposite()), "  ".length(), 0x4 ^ 0x0, 0xA6 ^ 0xA1, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode.rotateY()), " ".length(), 0x8E ^ 0x8A, 0x3E ^ 0x38, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode.rotateYCCW()), "   ".length(), 0xD ^ 0x9, 0xB5 ^ 0xB3, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, this.coordBaseMode), "  ".length(), 0xD ^ 0x9, 0x2E ^ 0x2B, structureBoundingBox);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.ladder, 0x55 ^ 0x51);
            int i = " ".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (i <= (0x39 ^ 0x30)) {
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), "   ".length(), i, "   ".length(), structureBoundingBox);
                ++i;
            }
            this.setBlockState(world, Blocks.air.getDefaultState(), "  ".length(), " ".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), "  ".length(), "  ".length(), "".length(), structureBoundingBox);
            this.placeDoorCurrentPosition(world, structureBoundingBox, random, "  ".length(), " ".length(), "".length(), EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, " ".length())));
            if (this.getBlockStateFromPos(world, "  ".length(), "".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(world, "  ".length(), -" ".length(), -" ".length(), structureBoundingBox).getBlock().getMaterial() != Material.air) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length())), "  ".length(), "".length(), -" ".length(), structureBoundingBox);
            }
            int j = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (j < (0x6B ^ 0x62)) {
                int k = "".length();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                while (k < (0xA2 ^ 0xA7)) {
                    this.clearCurrentPositionBlocksUpwards(world, k, 0x28 ^ 0x24, j, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), k, -" ".length(), j, structureBoundingBox);
                    ++k;
                }
                ++j;
            }
            this.spawnVillagers(world, structureBoundingBox, "  ".length(), " ".length(), "  ".length(), " ".length());
            return " ".length() != 0;
        }
        
        public Church() {
        }
        
        public Church(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    public static class Path extends Road
    {
        private static final String[] I;
        private int length;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        
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
                if (4 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            int n = "".length();
            int i = random.nextInt(0x86 ^ 0x83);
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (i < this.length - (0x4D ^ 0x45)) {
                final StructureComponent nextComponentNN = this.getNextComponentNN((Start)structureComponent, list, random, "".length(), i);
                if (nextComponentNN != null) {
                    i += Math.max(nextComponentNN.boundingBox.getXSize(), nextComponentNN.boundingBox.getZSize());
                    n = " ".length();
                }
                i += "  ".length() + random.nextInt(0xAB ^ 0xAE);
            }
            int j = random.nextInt(0x3E ^ 0x3B);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (j < this.length - (0x5D ^ 0x55)) {
                final StructureComponent nextComponentPP = this.getNextComponentPP((Start)structureComponent, list, random, "".length(), j);
                if (nextComponentPP != null) {
                    j += Math.max(nextComponentPP.boundingBox.getXSize(), nextComponentPP.boundingBox.getZSize());
                    n = " ".length();
                }
                j += "  ".length() + random.nextInt(0x32 ^ 0x37);
            }
            if (n != 0 && random.nextInt("   ".length()) > 0 && this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, this.getComponentType());
                        "".length();
                        if (4 == -1) {
                            throw null;
                        }
                        break;
                    }
                    case 4: {
                        StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY, this.boundingBox.maxZ - "  ".length(), EnumFacing.WEST, this.getComponentType());
                        "".length();
                        if (4 < 2) {
                            throw null;
                        }
                        break;
                    }
                    case 5: {
                        StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, this.getComponentType());
                        "".length();
                        if (4 == 2) {
                            throw null;
                        }
                        break;
                    }
                    case 6: {
                        StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.maxX - "  ".length(), this.boundingBox.minY, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, this.getComponentType());
                        break;
                    }
                }
            }
            if (n != 0 && random.nextInt("   ".length()) > 0 && this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType());
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        break;
                    }
                    case 4: {
                        StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY, this.boundingBox.maxZ - "  ".length(), EnumFacing.EAST, this.getComponentType());
                        "".length();
                        if (0 >= 2) {
                            throw null;
                        }
                        break;
                    }
                    case 5: {
                        StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, this.getComponentType());
                        "".length();
                        if (0 == 3) {
                            throw null;
                        }
                        break;
                    }
                    case 6: {
                        StructureVillagePieces.access$0((Start)structureComponent, list, random, this.boundingBox.maxX - "  ".length(), this.boundingBox.minY, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, this.getComponentType());
                        break;
                    }
                }
            }
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I(".\u000f\u0019\u0016\u0013\n", "bjwqg");
            Path.I[" ".length()] = I("\u001b.>2$?", "WKPUP");
        }
        
        public Path(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.length = Math.max(boundingBox.getXSize(), boundingBox.getZSize());
        }
        
        public static StructureBoundingBox func_175848_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            int i = (0x31 ^ 0x36) * MathHelper.getRandomIntegerInRange(random, "   ".length(), 0x16 ^ 0x13);
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (i >= (0x81 ^ 0x86)) {
                final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, "".length(), "".length(), "".length(), "   ".length(), "   ".length(), i, enumFacing);
                if (StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                    return componentToAddBoundingBox;
                }
                i -= 7;
            }
            return null;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final IBlockState func_175847_a = this.func_175847_a(Blocks.gravel.getDefaultState());
            final IBlockState func_175847_a2 = this.func_175847_a(Blocks.cobblestone.getDefaultState());
            int i = this.boundingBox.minX;
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (i <= this.boundingBox.maxX) {
                int j = this.boundingBox.minZ;
                "".length();
                if (4 == 2) {
                    throw null;
                }
                while (j <= this.boundingBox.maxZ) {
                    final BlockPos blockPos = new BlockPos(i, 0x6E ^ 0x2E, j);
                    if (structureBoundingBox.isVecInside(blockPos)) {
                        final BlockPos down = world.getTopSolidOrLiquidBlock(blockPos).down();
                        world.setBlockState(down, func_175847_a, "  ".length());
                        world.setBlockState(down.down(), func_175847_a2, "  ".length());
                    }
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.length = nbtTagCompound.getInteger(Path.I[" ".length()]);
        }
        
        static {
            I();
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger(Path.I["".length()], this.length);
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = Path.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xC7 ^ 0xC1);
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x4F ^ 0x4B);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x9B ^ 0x9E);
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Path.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        public Path() {
        }
    }
}
