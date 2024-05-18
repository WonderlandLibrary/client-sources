package net.minecraft.world.gen.structure;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class StructureStrongholdPieces
{
    private static final Stones strongholdStones;
    private static Class<? extends Stronghold> strongComponentType;
    private static final PieceWeight[] pieceWeightArray;
    private static List<PieceWeight> structurePieceList;
    static int totalWeight;
    private static final String[] I;
    
    private static Stronghold func_175955_b(final Stairs2 stairs2, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        if (!canAddStructurePieces()) {
            return null;
        }
        if (StructureStrongholdPieces.strongComponentType != null) {
            final Stronghold func_175954_a = func_175954_a(StructureStrongholdPieces.strongComponentType, list, random, n, n2, n3, enumFacing, n4);
            StructureStrongholdPieces.strongComponentType = null;
            if (func_175954_a != null) {
                return func_175954_a;
            }
        }
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < (0x57 ^ 0x52)) {
            ++i;
            int nextInt = random.nextInt(StructureStrongholdPieces.totalWeight);
            final Iterator<PieceWeight> iterator = StructureStrongholdPieces.structurePieceList.iterator();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final PieceWeight strongholdPieceWeight = iterator.next();
                nextInt -= strongholdPieceWeight.pieceWeight;
                if (nextInt < 0) {
                    if (!strongholdPieceWeight.canSpawnMoreStructuresOfType(n4)) {
                        break;
                    }
                    if (strongholdPieceWeight == stairs2.strongholdPieceWeight) {
                        "".length();
                        if (1 < -1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        final Stronghold func_175954_a2 = func_175954_a(strongholdPieceWeight.pieceClass, list, random, n, n2, n3, enumFacing, n4);
                        if (func_175954_a2 != null) {
                            final PieceWeight pieceWeight = strongholdPieceWeight;
                            pieceWeight.instancesSpawned += " ".length();
                            stairs2.strongholdPieceWeight = strongholdPieceWeight;
                            if (!strongholdPieceWeight.canSpawnMoreStructures()) {
                                StructureStrongholdPieces.structurePieceList.remove(strongholdPieceWeight);
                            }
                            return func_175954_a2;
                        }
                        continue;
                    }
                }
            }
        }
        final StructureBoundingBox func_175869_a = Corridor.func_175869_a(list, random, n, n2, n3, enumFacing);
        if (func_175869_a != null && func_175869_a.minY > " ".length()) {
            return new Corridor(n4, random, func_175869_a, enumFacing);
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static void access$2(final Class strongComponentType) {
        StructureStrongholdPieces.strongComponentType = (Class<? extends Stronghold>)strongComponentType;
    }
    
    static Stones access$0() {
        return StructureStrongholdPieces.strongholdStones;
    }
    
    public static void registerStrongholdPieces() {
        MapGenStructureIO.registerStructureComponent(ChestCorridor.class, StructureStrongholdPieces.I["".length()]);
        MapGenStructureIO.registerStructureComponent(Corridor.class, StructureStrongholdPieces.I[" ".length()]);
        MapGenStructureIO.registerStructureComponent(Crossing.class, StructureStrongholdPieces.I["  ".length()]);
        MapGenStructureIO.registerStructureComponent(LeftTurn.class, StructureStrongholdPieces.I["   ".length()]);
        MapGenStructureIO.registerStructureComponent(Library.class, StructureStrongholdPieces.I[0x6A ^ 0x6E]);
        MapGenStructureIO.registerStructureComponent(PortalRoom.class, StructureStrongholdPieces.I[0x42 ^ 0x47]);
        MapGenStructureIO.registerStructureComponent(Prison.class, StructureStrongholdPieces.I[0x5E ^ 0x58]);
        MapGenStructureIO.registerStructureComponent(RightTurn.class, StructureStrongholdPieces.I[0xB5 ^ 0xB2]);
        MapGenStructureIO.registerStructureComponent(RoomCrossing.class, StructureStrongholdPieces.I[0xA ^ 0x2]);
        MapGenStructureIO.registerStructureComponent(Stairs.class, StructureStrongholdPieces.I[0x61 ^ 0x68]);
        MapGenStructureIO.registerStructureComponent(Stairs2.class, StructureStrongholdPieces.I[0x8F ^ 0x85]);
        MapGenStructureIO.registerStructureComponent(Straight.class, StructureStrongholdPieces.I[0x53 ^ 0x58]);
        MapGenStructureIO.registerStructureComponent(StairsStraight.class, StructureStrongholdPieces.I[0xC ^ 0x0]);
    }
    
    static Class access$1() {
        return StructureStrongholdPieces.strongComponentType;
    }
    
    private static void I() {
        (I = new String[0x76 ^ 0x7B])["".length()] = I("0\u0005\u0002 ", "cMAcw");
        StructureStrongholdPieces.I[" ".length()] = I("$8\u000b;", "wpMxj");
        StructureStrongholdPieces.I["  ".length()] = I("\t+z/", "ZcOlv");
        StructureStrongholdPieces.I["   ".length()] = I("\u000b$\u00006", "XlLbH");
        StructureStrongholdPieces.I[0x9F ^ 0x9B] = I("\u0016>\u0015\u0005", "EvYld");
        StructureStrongholdPieces.I[0x6 ^ 0x3] = I("0'\u0004\u0007", "coTUo");
        StructureStrongholdPieces.I[0x74 ^ 0x72] = I("\u001e<\u001a\u000e", "MtJFn");
        StructureStrongholdPieces.I[0xA0 ^ 0xA7] = I("\u001f\u001c\u0007'", "LTUss");
        StructureStrongholdPieces.I[0x92 ^ 0x9A] = I("'0\u001e:", "txLyR");
        StructureStrongholdPieces.I[0x25 ^ 0x2C] = I("!=%\u0000", "ruvDD");
        StructureStrongholdPieces.I[0x2A ^ 0x20] = I("7\u00028:\u0016\u0016>", "dJkNw");
        StructureStrongholdPieces.I[0x98 ^ 0x93] = I("\u000b91", "XqbBQ");
        StructureStrongholdPieces.I[0x60 ^ 0x6C] = I("\u0001\"$$\u0001", "RjwwE");
    }
    
    public static void prepareStructurePieces() {
        StructureStrongholdPieces.structurePieceList = (List<PieceWeight>)Lists.newArrayList();
        final PieceWeight[] pieceWeightArray;
        final int length = (pieceWeightArray = StructureStrongholdPieces.pieceWeightArray).length;
        int i = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < length) {
            final PieceWeight pieceWeight = pieceWeightArray[i];
            pieceWeight.instancesSpawned = "".length();
            StructureStrongholdPieces.structurePieceList.add(pieceWeight);
            ++i;
        }
        StructureStrongholdPieces.strongComponentType = null;
    }
    
    private static boolean canAddStructurePieces() {
        int n = "".length();
        StructureStrongholdPieces.totalWeight = "".length();
        final Iterator<PieceWeight> iterator = StructureStrongholdPieces.structurePieceList.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final PieceWeight pieceWeight = iterator.next();
            if (pieceWeight.instancesLimit > 0 && pieceWeight.instancesSpawned < pieceWeight.instancesLimit) {
                n = " ".length();
            }
            StructureStrongholdPieces.totalWeight += pieceWeight.pieceWeight;
        }
        return n != 0;
    }
    
    static StructureComponent access$3(final Stairs2 stairs2, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        return func_175953_c(stairs2, list, random, n, n2, n3, enumFacing, n4);
    }
    
    private static Stronghold func_175954_a(final Class<? extends Stronghold> clazz, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        Stronghold stronghold = null;
        if (clazz == Straight.class) {
            stronghold = Straight.func_175862_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else if (clazz == Prison.class) {
            stronghold = Prison.func_175860_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (clazz == LeftTurn.class) {
            stronghold = LeftTurn.func_175867_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (clazz == RightTurn.class) {
            stronghold = LeftTurn.func_175867_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else if (clazz == RoomCrossing.class) {
            stronghold = RoomCrossing.func_175859_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else if (clazz == StairsStraight.class) {
            stronghold = StairsStraight.func_175861_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (clazz == Stairs.class) {
            stronghold = Stairs.func_175863_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (clazz == Crossing.class) {
            stronghold = Crossing.func_175866_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (clazz == ChestCorridor.class) {
            stronghold = ChestCorridor.func_175868_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (clazz == Library.class) {
            stronghold = Library.func_175864_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else if (clazz == PortalRoom.class) {
            stronghold = PortalRoom.func_175865_a(list, random, n, n2, n3, enumFacing, n4);
        }
        return stronghold;
    }
    
    private static StructureComponent func_175953_c(final Stairs2 stairs2, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        if (n4 > (0xB2 ^ 0x80)) {
            return null;
        }
        if (Math.abs(n - stairs2.getBoundingBox().minX) <= (0x72 ^ 0x2) && Math.abs(n3 - stairs2.getBoundingBox().minZ) <= (0x13 ^ 0x63)) {
            final Stronghold func_175955_b = func_175955_b(stairs2, list, random, n, n2, n3, enumFacing, n4 + " ".length());
            if (func_175955_b != null) {
                list.add(func_175955_b);
                stairs2.field_75026_c.add(func_175955_b);
            }
            return func_175955_b;
        }
        return null;
    }
    
    static {
        I();
        final PieceWeight[] pieceWeightArray2 = new PieceWeight[0x8D ^ 0x86];
        pieceWeightArray2["".length()] = new PieceWeight(Straight.class, 0xC ^ 0x24, "".length());
        pieceWeightArray2[" ".length()] = new PieceWeight(Prison.class, 0x6D ^ 0x68, 0x99 ^ 0x9C);
        pieceWeightArray2["  ".length()] = new PieceWeight(LeftTurn.class, 0xB1 ^ 0xA5, "".length());
        pieceWeightArray2["   ".length()] = new PieceWeight(RightTurn.class, 0x94 ^ 0x80, "".length());
        pieceWeightArray2[0x9B ^ 0x9F] = new PieceWeight(RoomCrossing.class, 0x5C ^ 0x56, 0x1E ^ 0x18);
        pieceWeightArray2[0x49 ^ 0x4C] = new PieceWeight(StairsStraight.class, 0x5F ^ 0x5A, 0x8B ^ 0x8E);
        pieceWeightArray2[0x91 ^ 0x97] = new PieceWeight(Stairs.class, 0x69 ^ 0x6C, 0x15 ^ 0x10);
        pieceWeightArray2[0xA7 ^ 0xA0] = new PieceWeight(Crossing.class, 0x8D ^ 0x88, 0xAE ^ 0xAA);
        pieceWeightArray2[0xCF ^ 0xC7] = new PieceWeight(ChestCorridor.class, 0x55 ^ 0x50, 0x65 ^ 0x61);
        pieceWeightArray2[0x1D ^ 0x14] = new PieceWeight(0x37 ^ 0x3D, "  ".length()) {
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
                    if (4 < 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public boolean canSpawnMoreStructuresOfType(final int n) {
                if (super.canSpawnMoreStructuresOfType(n) && n > (0x79 ^ 0x7D)) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
        };
        pieceWeightArray2[0x96 ^ 0x9C] = new PieceWeight(0xB7 ^ 0xA3, " ".length()) {
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
                    if (-1 == 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public boolean canSpawnMoreStructuresOfType(final int n) {
                if (super.canSpawnMoreStructuresOfType(n) && n > (0xA4 ^ 0xA1)) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
        };
        pieceWeightArray = pieceWeightArray2;
        strongholdStones = new Stones(null);
    }
    
    public static class LeftTurn extends Stronghold
    {
        public LeftTurn() {
        }
        
        public static LeftTurn func_175867_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -" ".length(), "".length(), 0x8F ^ 0x8A, 0x5D ^ 0x58, 0x31 ^ 0x34, enumFacing);
            LeftTurn leftTurn;
            if (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                leftTurn = new LeftTurn(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                leftTurn = null;
            }
            return leftTurn;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
                this.getNextComponentZ((Stairs2)structureComponent, list, random, " ".length(), " ".length());
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            else {
                this.getNextComponentX((Stairs2)structureComponent, list, random, " ".length(), " ".length());
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
                if (1 >= 3) {
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
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x27 ^ 0x23, 0x93 ^ 0x97, 0xAA ^ 0xAE, " ".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, " ".length(), " ".length(), "".length());
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
                this.fillWithBlocks(world, structureBoundingBox, 0x95 ^ 0x91, " ".length(), " ".length(), 0x2E ^ 0x2A, "   ".length(), "   ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else {
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), " ".length(), "".length(), "   ".length(), "   ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            return " ".length() != 0;
        }
        
        public LeftTurn(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
        }
    }
    
    abstract static class Stronghold extends StructureComponent
    {
        protected Door field_143013_d;
        private static final String[] I;
        private static int[] $SWITCH_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        
        protected Stronghold(final int n) {
            super(n);
            this.field_143013_d = Door.OPENING;
        }
        
        protected StructureComponent getNextComponentNormal(final Stairs2 stairs2, final List<StructureComponent> list, final Random random, final int n, final int n2) {
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.minZ - " ".length(), this.coordBaseMode, this.getComponentType());
                    }
                    case 4: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.maxZ + " ".length(), this.coordBaseMode, this.getComponentType());
                    }
                    case 5: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY + n2, this.boundingBox.minZ + n, this.coordBaseMode, this.getComponentType());
                    }
                    case 6: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY + n2, this.boundingBox.minZ + n, this.coordBaseMode, this.getComponentType());
                    }
                }
            }
            return null;
        }
        
        protected StructureComponent getNextComponentX(final Stairs2 stairs2, final List<StructureComponent> list, final Random random, final int n, final int n2) {
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType());
                    }
                    case 4: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType());
                    }
                    case 5: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, this.getComponentType());
                    }
                    case 6: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, this.getComponentType());
                    }
                }
            }
            return null;
        }
        
        public Stronghold() {
            this.field_143013_d = Door.OPENING;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            nbtTagCompound.setString(Stronghold.I["".length()], this.field_143013_d.name());
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
                if (1 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        protected static boolean canStrongholdGoDeeper(final StructureBoundingBox structureBoundingBox) {
            if (structureBoundingBox != null && structureBoundingBox.minY > (0xBF ^ 0xB5)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        protected void placeDoor(final World world, final Random random, final StructureBoundingBox structureBoundingBox, final Door door, final int n, final int n2, final int n3) {
            switch ($SWITCH_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door()[door.ordinal()]) {
                default: {
                    this.fillWithBlocks(world, structureBoundingBox, n, n2, n3, n + "   ".length() - " ".length(), n2 + "   ".length() - " ".length(), n3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2 + " ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2 + "  ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + " ".length(), n2 + "  ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + "  ".length(), n2 + "  ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + "  ".length(), n2 + " ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + "  ".length(), n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.oak_door.getDefaultState(), n + " ".length(), n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.oak_door.getStateFromMeta(0x12 ^ 0x1A), n + " ".length(), n2 + " ".length(), n3, structureBoundingBox);
                    "".length();
                    if (-1 == 3) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    this.setBlockState(world, Blocks.air.getDefaultState(), n + " ".length(), n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.air.getDefaultState(), n + " ".length(), n2 + " ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n, n2 + " ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n, n2 + "  ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n + " ".length(), n2 + "  ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n + "  ".length(), n2 + "  ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n + "  ".length(), n2 + " ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n + "  ".length(), n2, n3, structureBoundingBox);
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2 + " ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2 + "  ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + " ".length(), n2 + "  ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + "  ".length(), n2 + "  ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + "  ".length(), n2 + " ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + "  ".length(), n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_door.getDefaultState(), n + " ".length(), n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_door.getStateFromMeta(0xB6 ^ 0xBE), n + " ".length(), n2 + " ".length(), n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 0x73 ^ 0x77)), n + "  ".length(), n2 + " ".length(), n3 + " ".length(), structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, "   ".length())), n + "  ".length(), n2 + " ".length(), n3 - " ".length(), structureBoundingBox);
                    break;
                }
            }
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            this.field_143013_d = Door.valueOf(nbtTagCompound.getString(Stronghold.I[" ".length()]));
        }
        
        protected Door getRandomDoor(final Random random) {
            switch (random.nextInt(0x1F ^ 0x1A)) {
                default: {
                    return Door.OPENING;
                }
                case 2: {
                    return Door.WOOD_DOOR;
                }
                case 3: {
                    return Door.GRATES;
                }
                case 4: {
                    return Door.IRON_DOOR;
                }
            }
        }
        
        protected StructureComponent getNextComponentZ(final Stairs2 stairs2, final List<StructureComponent> list, final Random random, final int n, final int n2) {
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType());
                    }
                    case 4: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType());
                    }
                    case 5: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, this.getComponentType());
                    }
                    case 6: {
                        return StructureStrongholdPieces.access$3(stairs2, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, this.getComponentType());
                    }
                }
            }
            return null;
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u0013(\u0000\u0004=\u0012)\u001b\u0004", "VFtvD");
            Stronghold.I[" ".length()] = I("$\u0017\u00104)%\u0016\u000b4", "aydFP");
        }
        
        static {
            I();
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door() {
            final int[] $switch_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door = Stronghold.$SWITCH_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door;
            if ($switch_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door != null) {
                return $switch_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door;
            }
            final int[] $switch_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door2 = new int[Door.values().length];
            try {
                $switch_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door2[Door.GRATES.ordinal()] = "   ".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door2[Door.IRON_DOOR.ordinal()] = (0xB ^ 0xF);
                "".length();
                if (4 == 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door2[Door.OPENING.ordinal()] = " ".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door2[Door.WOOD_DOOR.ordinal()] = "  ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            return Stronghold.$SWITCH_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door = $switch_TABLE$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door2;
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = Stronghold.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x5B ^ 0x5D);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x87 ^ 0x83);
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0xC1 ^ 0xC4);
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Stronghold.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        public enum Door
        {
            GRATES(Door.I["  ".length()], "  ".length());
            
            private static final String[] I;
            
            OPENING(Door.I["".length()], "".length()), 
            IRON_DOOR(Door.I["   ".length()], "   ".length());
            
            private static final Door[] ENUM$VALUES;
            
            WOOD_DOOR(Door.I[" ".length()], " ".length());
            
            private static void I() {
                (I = new String[0xC3 ^ 0xC7])["".length()] = I("\u0007\u0004\"=\u000b\u0006\u0013", "HTgsB");
                Door.I[" ".length()] = I("\u0010\t\u0015\u00179\u0003\t\u0015\u0001", "GFZSf");
                Door.I["  ".length()] = I("\u0016\u001f\u00156\u0003\u0002", "QMTbF");
                Door.I["   ".length()] = I(">:8\u000783'8\u001b", "whwIg");
            }
            
            private Door(final String s, final int n) {
            }
            
            static {
                I();
                final Door[] enum$VALUES = new Door[0x21 ^ 0x25];
                enum$VALUES["".length()] = Door.OPENING;
                enum$VALUES[" ".length()] = Door.WOOD_DOOR;
                enum$VALUES["  ".length()] = Door.GRATES;
                enum$VALUES["   ".length()] = Door.IRON_DOOR;
                ENUM$VALUES = enum$VALUES;
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
                    if (3 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }
    }
    
    public static class Stairs2 extends Stairs
    {
        public PortalRoom strongholdPortalRoom;
        public List<StructureComponent> field_75026_c;
        public PieceWeight strongholdPieceWeight;
        
        @Override
        public BlockPos getBoundingBoxCenter() {
            BlockPos blockPos;
            if (this.strongholdPortalRoom != null) {
                blockPos = this.strongholdPortalRoom.getBoundingBoxCenter();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                blockPos = super.getBoundingBoxCenter();
            }
            return blockPos;
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
        
        public Stairs2() {
            this.field_75026_c = (List<StructureComponent>)Lists.newArrayList();
        }
        
        public Stairs2(final int n, final Random random, final int n2, final int n3) {
            super("".length(), random, n2, n3);
            this.field_75026_c = (List<StructureComponent>)Lists.newArrayList();
        }
    }
    
    public static class Stairs extends Stronghold
    {
        private boolean field_75024_a;
        private static final String[] I;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        
        public static Stairs func_175863_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -(0x68 ^ 0x6F), "".length(), 0xD ^ 0x8, 0x9C ^ 0x97, 0x2D ^ 0x28, enumFacing);
            Stairs stairs;
            if (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                stairs = new Stairs(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                stairs = null;
            }
            return stairs;
        }
        
        public Stairs(final int n, final Random random, final int n2, final int n3) {
            super(n);
            this.field_75024_a = (" ".length() != 0);
            this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
            this.field_143013_d = Door.OPENING;
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                case 3:
                case 4: {
                    this.boundingBox = new StructureBoundingBox(n2, 0x1A ^ 0x5A, n3, n2 + (0x19 ^ 0x1C) - " ".length(), 0x38 ^ 0x72, n3 + (0x19 ^ 0x1C) - " ".length());
                    "".length();
                    if (1 < 0) {
                        throw null;
                    }
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(n2, 0x3E ^ 0x7E, n3, n2 + (0xC6 ^ 0xC3) - " ".length(), 0x29 ^ 0x63, n3 + (0x12 ^ 0x17) - " ".length());
                    break;
                }
            }
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(Stairs.I["".length()], this.field_75024_a);
        }
        
        static {
            I();
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_75024_a = nbtTagCompound.getBoolean(Stairs.I[" ".length()]);
        }
        
        public Stairs() {
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("48\u001b:)\u0002", "gWnHJ");
            Stairs.I[" ".length()] = I("\u0014,\u001a\u00131\"", "GCoaR");
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x2C ^ 0x28, 0x7 ^ 0xD, 0x3B ^ 0x3F, " ".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, " ".length(), 0xAE ^ 0xA9, "".length());
            this.placeDoor(world, random, structureBoundingBox, Door.OPENING, " ".length(), " ".length(), 0x25 ^ 0x21);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "  ".length(), 0x5F ^ 0x59, " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), " ".length(), 0x6 ^ 0x3, " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), " ".length(), 0x6A ^ 0x6C, " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), " ".length(), 0x53 ^ 0x56, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), " ".length(), 0x1D ^ 0x19, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), " ".length(), 0x28 ^ 0x2D, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "  ".length(), 0xA3 ^ 0xA7, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "   ".length(), "   ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), "   ".length(), 0x10 ^ 0x14, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "   ".length(), "   ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "   ".length(), "  ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), "   ".length(), "   ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "  ".length(), "  ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), " ".length(), " ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), " ".length(), "  ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), " ".length(), " ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), " ".length(), " ".length(), "   ".length(), structureBoundingBox);
            return " ".length() != 0;
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
                if (3 != 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xBD ^ 0xBB);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x37 ^ 0x33);
                "".length();
                if (-1 == 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x6A ^ 0x6F);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Stairs.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            if (this.field_75024_a) {
                StructureStrongholdPieces.access$2(Crossing.class);
            }
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, " ".length(), " ".length());
        }
        
        public Stairs(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.field_75024_a = ("".length() != 0);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
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
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class Crossing extends Stronghold
    {
        private static final String[] I;
        private boolean field_74999_h;
        private boolean field_74995_d;
        private boolean field_74996_b;
        private boolean field_74997_c;
        
        static {
            I();
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            int length = "   ".length();
            int n = 0x29 ^ 0x2C;
            if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {
                length = (0xBA ^ 0xB2) - length;
                n = (0xC8 ^ 0xC0) - n;
            }
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, 0x3D ^ 0x38, " ".length());
            if (this.field_74996_b) {
                this.getNextComponentX((Stairs2)structureComponent, list, random, length, " ".length());
            }
            if (this.field_74997_c) {
                this.getNextComponentX((Stairs2)structureComponent, list, random, n, 0x9C ^ 0x9B);
            }
            if (this.field_74995_d) {
                this.getNextComponentZ((Stairs2)structureComponent, list, random, length, " ".length());
            }
            if (this.field_74999_h) {
                this.getNextComponentZ((Stairs2)structureComponent, list, random, n, 0x6 ^ 0x1);
            }
        }
        
        public Crossing() {
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
        
        private static void I() {
            (I = new String[0x5B ^ 0x53])["".length()] = I("\u0019)\u001f0\u0014\u001a;", "uLyDX");
            Crossing.I[" ".length()] = I("=,?\u0006=8.1", "QIYru");
            Crossing.I["  ".length()] = I("*\u0018*&1\u0014\u001e:", "XqMNE");
            Crossing.I["   ".length()] = I("9\u0004\"\u001e-\u0003\u0004\"\u001e", "KmEvY");
            Crossing.I[0xC7 ^ 0xC3] = I(">\u0002-2\u0001=\u0010", "RgKFM");
            Crossing.I[0x25 ^ 0x20] = I("/6#\u0006<*4-", "CSErt");
            Crossing.I[0xB5 ^ 0xB3] = I("\u0006\u000b\u0006?\u00188\r\u0016", "tbaWl");
            Crossing.I[0x4D ^ 0x4A] = I("\u001c9\u0000\u001c\u0000&9\u0000\u001c", "nPgtt");
        }
        
        public static Crossing func_175866_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -(0x1A ^ 0x1E), -"   ".length(), "".length(), 0x9F ^ 0x95, 0x35 ^ 0x3C, 0x59 ^ 0x52, enumFacing);
            Crossing crossing;
            if (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                crossing = new Crossing(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                crossing = null;
            }
            return crossing;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_74996_b = nbtTagCompound.getBoolean(Crossing.I[0x8C ^ 0x88]);
            this.field_74997_c = nbtTagCompound.getBoolean(Crossing.I[0xA3 ^ 0xA6]);
            this.field_74995_d = nbtTagCompound.getBoolean(Crossing.I[0x3A ^ 0x3C]);
            this.field_74999_h = nbtTagCompound.getBoolean(Crossing.I[0xC ^ 0xB]);
        }
        
        public Crossing(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
            this.field_74996_b = random.nextBoolean();
            this.field_74997_c = random.nextBoolean();
            this.field_74995_d = random.nextBoolean();
            int field_74999_h;
            if (random.nextInt("   ".length()) > 0) {
                field_74999_h = " ".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else {
                field_74999_h = "".length();
            }
            this.field_74999_h = (field_74999_h != 0);
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(Crossing.I["".length()], this.field_74996_b);
            nbtTagCompound.setBoolean(Crossing.I[" ".length()], this.field_74997_c);
            nbtTagCompound.setBoolean(Crossing.I["  ".length()], this.field_74995_d);
            nbtTagCompound.setBoolean(Crossing.I["   ".length()], this.field_74999_h);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x7B ^ 0x72, 0x75 ^ 0x7D, 0x87 ^ 0x8D, " ".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 0x25 ^ 0x21, "   ".length(), "".length());
            if (this.field_74996_b) {
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), " ".length(), "".length(), 0x4C ^ 0x49, "   ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            if (this.field_74995_d) {
                this.fillWithBlocks(world, structureBoundingBox, 0x24 ^ 0x2D, "   ".length(), " ".length(), 0x10 ^ 0x19, 0x4F ^ 0x4A, "   ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            if (this.field_74997_c) {
                this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x55 ^ 0x50, 0x35 ^ 0x32, "".length(), 0x4A ^ 0x4D, 0x52 ^ 0x5B, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            if (this.field_74999_h) {
                this.fillWithBlocks(world, structureBoundingBox, 0x13 ^ 0x1A, 0x89 ^ 0x8C, 0xC ^ 0xB, 0xC9 ^ 0xC0, 0x71 ^ 0x76, 0xA6 ^ 0xAF, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 0xC4 ^ 0xC1, " ".length(), 0xA2 ^ 0xA8, 0x4 ^ 0x3, "   ".length(), 0x78 ^ 0x72, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, " ".length(), "  ".length(), " ".length(), 0xB2 ^ 0xBA, "  ".length(), 0x5B ^ 0x5D, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x65 ^ 0x61, " ".length(), 0x2D ^ 0x28, 0xC2 ^ 0xC6, 0x1D ^ 0x19, 0x9F ^ 0x96, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x5C ^ 0x54, " ".length(), 0x45 ^ 0x40, 0xCB ^ 0xC3, 0x5 ^ 0x1, 0x83 ^ 0x8A, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, " ".length(), 0xC0 ^ 0xC4, 0x36 ^ 0x31, "   ".length(), 0xC2 ^ 0xC6, 0x70 ^ 0x79, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0x56 ^ 0x53, "   ".length(), "   ".length(), 0x36 ^ 0x30, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0xA ^ 0xE, "   ".length(), "   ".length(), 0x80 ^ 0x84, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x58 ^ 0x5C, 0x98 ^ 0x9E, "   ".length(), 0xA7 ^ 0xA3, 0x49 ^ 0x4F, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), "".length() != 0);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x24 ^ 0x21, " ".length(), 0xC6 ^ 0xC1, 0x6A ^ 0x6D, " ".length(), 0xCF ^ 0xC7, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithBlocks(world, structureBoundingBox, 0x5E ^ 0x5B, " ".length(), 0x91 ^ 0x98, 0x63 ^ 0x64, " ".length(), 0x83 ^ 0x8A, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xA6 ^ 0xA3, "  ".length(), 0x60 ^ 0x67, 0x65 ^ 0x62, "  ".length(), 0x79 ^ 0x7E, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x4B ^ 0x4F, 0x45 ^ 0x40, 0xBB ^ 0xBC, 0x3A ^ 0x3E, 0x61 ^ 0x64, 0xCD ^ 0xC4, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xD ^ 0x5, 0x63 ^ 0x66, 0x11 ^ 0x16, 0x32 ^ 0x3A, 0x92 ^ 0x97, 0x46 ^ 0x4F, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xA4 ^ 0xA1, 0x4B ^ 0x4E, 0xB8 ^ 0xBF, 0x91 ^ 0x96, 0x56 ^ 0x53, 0x12 ^ 0x1B, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.torch.getDefaultState(), 0x45 ^ 0x43, 0xAA ^ 0xAF, 0x27 ^ 0x21, structureBoundingBox);
            return " ".length() != 0;
        }
    }
    
    static class Stones extends StructureComponent.BlockSelector
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
                if (3 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private Stones() {
        }
        
        Stones(final Stones stones) {
            this();
        }
        
        @Override
        public void selectBlocks(final Random random, final int n, final int n2, final int n3, final boolean b) {
            if (b) {
                final float nextFloat = random.nextFloat();
                if (nextFloat < 0.2f) {
                    this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CRACKED_META);
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                else if (nextFloat < 0.5f) {
                    this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.MOSSY_META);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (nextFloat < 0.55f) {
                    this.blockstate = Blocks.monster_egg.getStateFromMeta(BlockSilverfish.EnumType.STONEBRICK.getMetadata());
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                }
                else {
                    this.blockstate = Blocks.stonebrick.getDefaultState();
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
            }
            else {
                this.blockstate = Blocks.air.getDefaultState();
            }
        }
    }
    
    public static class PortalRoom extends Stronghold
    {
        private static final String[] I;
        private boolean hasSpawner;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        
        public static PortalRoom func_175865_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -(0x11 ^ 0x15), -" ".length(), "".length(), 0x23 ^ 0x28, 0x29 ^ 0x21, 0xAB ^ 0xBB, enumFacing);
            PortalRoom portalRoom;
            if (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                portalRoom = new PortalRoom(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else {
                portalRoom = null;
            }
            return portalRoom;
        }
        
        public PortalRoom() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x7D ^ 0x77, 0x4C ^ 0x4B, 0x4 ^ 0xB, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, Door.GRATES, 0xBB ^ 0xBF, " ".length(), "".length());
            final int n = 0x40 ^ 0x46;
            this.fillWithRandomizedBlocks(world, structureBoundingBox, " ".length(), n, " ".length(), " ".length(), n, 0x88 ^ 0x86, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x7C ^ 0x75, n, " ".length(), 0x50 ^ 0x59, n, 0x98 ^ 0x96, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), n, " ".length(), 0x24 ^ 0x2C, n, "  ".length(), "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), n, 0xC8 ^ 0xC6, 0x96 ^ 0x9E, n, 0x65 ^ 0x6B, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, " ".length(), " ".length(), " ".length(), "  ".length(), " ".length(), 0x30 ^ 0x34, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x11 ^ 0x19, " ".length(), " ".length(), 0x60 ^ 0x69, " ".length(), 0x37 ^ 0x33, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), " ".length(), " ".length(), " ".length(), "   ".length(), Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x81 ^ 0x88, " ".length(), " ".length(), 0xBB ^ 0xB2, " ".length(), "   ".length(), Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), "".length() != 0);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "   ".length(), " ".length(), 0x22 ^ 0x2A, 0x47 ^ 0x40, " ".length(), 0xA6 ^ 0xAA, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithBlocks(world, structureBoundingBox, 0x1F ^ 0x1B, " ".length(), 0x8B ^ 0x82, 0xA4 ^ 0xA2, " ".length(), 0x9E ^ 0x95, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), "".length() != 0);
            int i = "   ".length();
            "".length();
            if (4 == 1) {
                throw null;
            }
            while (i < (0x8A ^ 0x84)) {
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), i, "".length(), 0x5F ^ 0x5B, i, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x20 ^ 0x2A, "   ".length(), i, 0xB5 ^ 0xBF, 0x3D ^ 0x39, i, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), "".length() != 0);
                i += 2;
            }
            int j = "  ".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (j < (0xAE ^ 0xA7)) {
                this.fillWithBlocks(world, structureBoundingBox, j, "   ".length(), 0x44 ^ 0x4B, j, 0x4 ^ 0x0, 0x1E ^ 0x11, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), "".length() != 0);
                j += 2;
            }
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.stone_brick_stairs, "   ".length());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x24 ^ 0x20, " ".length(), 0x84 ^ 0x81, 0x19 ^ 0x1F, " ".length(), 0xB0 ^ 0xB7, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x63 ^ 0x67, "  ".length(), 0xC2 ^ 0xC4, 0xBE ^ 0xB8, "  ".length(), 0x61 ^ 0x66, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x5B ^ 0x5F, "   ".length(), 0x43 ^ 0x44, 0x87 ^ 0x81, "   ".length(), 0xBF ^ 0xB8, "".length() != 0, random, StructureStrongholdPieces.access$0());
            int k = 0xA8 ^ 0xAC;
            "".length();
            if (false) {
                throw null;
            }
            while (k <= (0x7B ^ 0x7D)) {
                this.setBlockState(world, Blocks.stone_brick_stairs.getStateFromMeta(metadataWithOffset), k, " ".length(), 0x75 ^ 0x71, structureBoundingBox);
                this.setBlockState(world, Blocks.stone_brick_stairs.getStateFromMeta(metadataWithOffset), k, "  ".length(), 0x75 ^ 0x70, structureBoundingBox);
                this.setBlockState(world, Blocks.stone_brick_stairs.getStateFromMeta(metadataWithOffset), k, "   ".length(), 0x84 ^ 0x82, structureBoundingBox);
                ++k;
            }
            int n2 = EnumFacing.NORTH.getHorizontalIndex();
            int n3 = EnumFacing.SOUTH.getHorizontalIndex();
            int n4 = EnumFacing.EAST.getHorizontalIndex();
            int n5 = EnumFacing.WEST.getHorizontalIndex();
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 4: {
                        n2 = EnumFacing.SOUTH.getHorizontalIndex();
                        n3 = EnumFacing.NORTH.getHorizontalIndex();
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                        break;
                    }
                    case 5: {
                        n2 = EnumFacing.WEST.getHorizontalIndex();
                        n3 = EnumFacing.EAST.getHorizontalIndex();
                        n4 = EnumFacing.SOUTH.getHorizontalIndex();
                        n5 = EnumFacing.NORTH.getHorizontalIndex();
                        "".length();
                        if (4 < 1) {
                            throw null;
                        }
                        break;
                    }
                    case 6: {
                        n2 = EnumFacing.EAST.getHorizontalIndex();
                        n3 = EnumFacing.WEST.getHorizontalIndex();
                        n4 = EnumFacing.SOUTH.getHorizontalIndex();
                        n5 = EnumFacing.NORTH.getHorizontalIndex();
                        break;
                    }
                }
            }
            final IBlockState stateFromMeta = Blocks.end_portal_frame.getStateFromMeta(n2);
            final PropertyBool eye = BlockEndPortalFrame.EYE;
            int n6;
            if (random.nextFloat() > 0.9f) {
                n6 = " ".length();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                n6 = "".length();
            }
            this.setBlockState(world, stateFromMeta.withProperty((IProperty<Comparable>)eye, (boolean)(n6 != 0)), 0x48 ^ 0x4C, "   ".length(), 0xA0 ^ 0xA8, structureBoundingBox);
            final IBlockState stateFromMeta2 = Blocks.end_portal_frame.getStateFromMeta(n2);
            final PropertyBool eye2 = BlockEndPortalFrame.EYE;
            int n7;
            if (random.nextFloat() > 0.9f) {
                n7 = " ".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                n7 = "".length();
            }
            this.setBlockState(world, stateFromMeta2.withProperty((IProperty<Comparable>)eye2, (boolean)(n7 != 0)), 0x53 ^ 0x56, "   ".length(), 0x27 ^ 0x2F, structureBoundingBox);
            final IBlockState stateFromMeta3 = Blocks.end_portal_frame.getStateFromMeta(n2);
            final PropertyBool eye3 = BlockEndPortalFrame.EYE;
            int n8;
            if (random.nextFloat() > 0.9f) {
                n8 = " ".length();
                "".length();
                if (3 == 0) {
                    throw null;
                }
            }
            else {
                n8 = "".length();
            }
            this.setBlockState(world, stateFromMeta3.withProperty((IProperty<Comparable>)eye3, (boolean)(n8 != 0)), 0x46 ^ 0x40, "   ".length(), 0xAC ^ 0xA4, structureBoundingBox);
            final IBlockState stateFromMeta4 = Blocks.end_portal_frame.getStateFromMeta(n3);
            final PropertyBool eye4 = BlockEndPortalFrame.EYE;
            int n9;
            if (random.nextFloat() > 0.9f) {
                n9 = " ".length();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                n9 = "".length();
            }
            this.setBlockState(world, stateFromMeta4.withProperty((IProperty<Comparable>)eye4, (boolean)(n9 != 0)), 0xA2 ^ 0xA6, "   ".length(), 0x66 ^ 0x6A, structureBoundingBox);
            final IBlockState stateFromMeta5 = Blocks.end_portal_frame.getStateFromMeta(n3);
            final PropertyBool eye5 = BlockEndPortalFrame.EYE;
            int n10;
            if (random.nextFloat() > 0.9f) {
                n10 = " ".length();
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else {
                n10 = "".length();
            }
            this.setBlockState(world, stateFromMeta5.withProperty((IProperty<Comparable>)eye5, (boolean)(n10 != 0)), 0x9E ^ 0x9B, "   ".length(), 0xA0 ^ 0xAC, structureBoundingBox);
            final IBlockState stateFromMeta6 = Blocks.end_portal_frame.getStateFromMeta(n3);
            final PropertyBool eye6 = BlockEndPortalFrame.EYE;
            int n11;
            if (random.nextFloat() > 0.9f) {
                n11 = " ".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                n11 = "".length();
            }
            this.setBlockState(world, stateFromMeta6.withProperty((IProperty<Comparable>)eye6, (boolean)(n11 != 0)), 0x75 ^ 0x73, "   ".length(), 0x9D ^ 0x91, structureBoundingBox);
            final IBlockState stateFromMeta7 = Blocks.end_portal_frame.getStateFromMeta(n4);
            final PropertyBool eye7 = BlockEndPortalFrame.EYE;
            int n12;
            if (random.nextFloat() > 0.9f) {
                n12 = " ".length();
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                n12 = "".length();
            }
            this.setBlockState(world, stateFromMeta7.withProperty((IProperty<Comparable>)eye7, (boolean)(n12 != 0)), "   ".length(), "   ".length(), 0x49 ^ 0x40, structureBoundingBox);
            final IBlockState stateFromMeta8 = Blocks.end_portal_frame.getStateFromMeta(n4);
            final PropertyBool eye8 = BlockEndPortalFrame.EYE;
            int n13;
            if (random.nextFloat() > 0.9f) {
                n13 = " ".length();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                n13 = "".length();
            }
            this.setBlockState(world, stateFromMeta8.withProperty((IProperty<Comparable>)eye8, (boolean)(n13 != 0)), "   ".length(), "   ".length(), 0x17 ^ 0x1D, structureBoundingBox);
            final IBlockState stateFromMeta9 = Blocks.end_portal_frame.getStateFromMeta(n4);
            final PropertyBool eye9 = BlockEndPortalFrame.EYE;
            int n14;
            if (random.nextFloat() > 0.9f) {
                n14 = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n14 = "".length();
            }
            this.setBlockState(world, stateFromMeta9.withProperty((IProperty<Comparable>)eye9, (boolean)(n14 != 0)), "   ".length(), "   ".length(), 0x3A ^ 0x31, structureBoundingBox);
            final IBlockState stateFromMeta10 = Blocks.end_portal_frame.getStateFromMeta(n5);
            final PropertyBool eye10 = BlockEndPortalFrame.EYE;
            int n15;
            if (random.nextFloat() > 0.9f) {
                n15 = " ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                n15 = "".length();
            }
            this.setBlockState(world, stateFromMeta10.withProperty((IProperty<Comparable>)eye10, (boolean)(n15 != 0)), 0x78 ^ 0x7F, "   ".length(), 0x52 ^ 0x5B, structureBoundingBox);
            final IBlockState stateFromMeta11 = Blocks.end_portal_frame.getStateFromMeta(n5);
            final PropertyBool eye11 = BlockEndPortalFrame.EYE;
            int n16;
            if (random.nextFloat() > 0.9f) {
                n16 = " ".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                n16 = "".length();
            }
            this.setBlockState(world, stateFromMeta11.withProperty((IProperty<Comparable>)eye11, (boolean)(n16 != 0)), 0x24 ^ 0x23, "   ".length(), 0x2A ^ 0x20, structureBoundingBox);
            final IBlockState stateFromMeta12 = Blocks.end_portal_frame.getStateFromMeta(n5);
            final PropertyBool eye12 = BlockEndPortalFrame.EYE;
            int n17;
            if (random.nextFloat() > 0.9f) {
                n17 = " ".length();
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else {
                n17 = "".length();
            }
            this.setBlockState(world, stateFromMeta12.withProperty((IProperty<Comparable>)eye12, (boolean)(n17 != 0)), 0x2 ^ 0x5, "   ".length(), 0x53 ^ 0x58, structureBoundingBox);
            if (!this.hasSpawner) {
                final BlockPos blockPos = new BlockPos(this.getXWithOffset(0xB7 ^ 0xB2, 0x5C ^ 0x5A), this.getYWithOffset("   ".length()), this.getZWithOffset(0x1D ^ 0x18, 0x1E ^ 0x18));
                if (structureBoundingBox.isVecInside(blockPos)) {
                    this.hasSpawner = (" ".length() != 0);
                    world.setBlockState(blockPos, Blocks.mob_spawner.getDefaultState(), "  ".length());
                    final TileEntity tileEntity = world.getTileEntity(blockPos);
                    if (tileEntity instanceof TileEntityMobSpawner) {
                        ((TileEntityMobSpawner)tileEntity).getSpawnerBaseLogic().setEntityName(PortalRoom.I["  ".length()]);
                    }
                }
            }
            return " ".length() != 0;
        }
        
        static {
            I();
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(PortalRoom.I["".length()], this.hasSpawner);
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
                if (4 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = PortalRoom.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x64 ^ 0x62);
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0xB8 ^ 0xBC);
                "".length();
                if (false) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x2A ^ 0x2F);
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return PortalRoom.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("/\u001b\u0005", "btgAB");
            PortalRoom.I[" ".length()] = I("#\u0016\u0014", "nyvBn");
            PortalRoom.I["  ".length()] = I("\u0018\"\u00164 9-\u00131-", "KKzBE");
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            if (structureComponent != null) {
                ((Stairs2)structureComponent).strongholdPortalRoom = this;
            }
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.hasSpawner = nbtTagCompound.getBoolean(PortalRoom.I[" ".length()]);
        }
        
        public PortalRoom(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    static class PieceWeight
    {
        public Class<? extends Stronghold> pieceClass;
        public int instancesLimit;
        public int instancesSpawned;
        public final int pieceWeight;
        
        public PieceWeight(final Class<? extends Stronghold> pieceClass, final int pieceWeight, final int instancesLimit) {
            this.pieceClass = pieceClass;
            this.pieceWeight = pieceWeight;
            this.instancesLimit = instancesLimit;
        }
        
        public boolean canSpawnMoreStructures() {
            if (this.instancesLimit != 0 && this.instancesSpawned >= this.instancesLimit) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        public boolean canSpawnMoreStructuresOfType(final int n) {
            if (this.instancesLimit != 0 && this.instancesSpawned >= this.instancesLimit) {
                return "".length() != 0;
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
                if (1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class RightTurn extends LeftTurn
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
                if (4 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
                this.getNextComponentX((Stairs2)structureComponent, list, random, " ".length(), " ".length());
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                this.getNextComponentZ((Stairs2)structureComponent, list, random, " ".length(), " ".length());
            }
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x36 ^ 0x32, 0x82 ^ 0x86, 0x8B ^ 0x8F, " ".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, " ".length(), " ".length(), "".length());
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), " ".length(), "".length(), "   ".length(), "   ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                "".length();
                if (1 == 4) {
                    throw null;
                }
            }
            else {
                this.fillWithBlocks(world, structureBoundingBox, 0x5 ^ 0x1, " ".length(), " ".length(), 0x11 ^ 0x15, "   ".length(), "   ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            return " ".length() != 0;
        }
    }
    
    public static class Library extends Stronghold
    {
        private static final List<WeightedRandomChestContent> strongholdLibraryChestContents;
        private boolean isLargeRoom;
        private static final String[] I;
        
        public Library(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
            int isLargeRoom;
            if (boundingBox.getYSize() > (0xAC ^ 0xAA)) {
                isLargeRoom = " ".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                isLargeRoom = "".length();
            }
            this.isLargeRoom = (isLargeRoom != 0);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.isLargeRoom = nbtTagCompound.getBoolean(Library.I[" ".length()]);
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
                if (-1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public static Library func_175864_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -(0x19 ^ 0x1D), -" ".length(), "".length(), 0x79 ^ 0x77, 0xB5 ^ 0xBE, 0x0 ^ 0xF, enumFacing);
            if (!Stronghold.canStrongholdGoDeeper(structureBoundingBox) || StructureComponent.findIntersecting(list, structureBoundingBox) != null) {
                structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -(0xA1 ^ 0xA5), -" ".length(), "".length(), 0xA4 ^ 0xAA, 0x64 ^ 0x62, 0x31 ^ 0x3E, enumFacing);
                if (!Stronghold.canStrongholdGoDeeper(structureBoundingBox) || StructureComponent.findIntersecting(list, structureBoundingBox) != null) {
                    return null;
                }
            }
            return new Library(n4, random, structureBoundingBox, enumFacing);
        }
        
        static {
            I();
            final WeightedRandomChestContent[] array = new WeightedRandomChestContent[0xC0 ^ 0xC4];
            array["".length()] = new WeightedRandomChestContent(Items.book, "".length(), " ".length(), "   ".length(), 0x69 ^ 0x7D);
            array[" ".length()] = new WeightedRandomChestContent(Items.paper, "".length(), "  ".length(), 0x49 ^ 0x4E, 0x45 ^ 0x51);
            array["  ".length()] = new WeightedRandomChestContent(Items.map, "".length(), " ".length(), " ".length(), " ".length());
            array["   ".length()] = new WeightedRandomChestContent(Items.compass, "".length(), " ".length(), " ".length(), " ".length());
            strongholdLibraryChestContents = Lists.newArrayList((Object[])array);
        }
        
        public Library() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            int n = 0x1F ^ 0x14;
            if (!this.isLargeRoom) {
                n = (0x49 ^ 0x4F);
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0xA1 ^ 0xAC, n - " ".length(), 0x87 ^ 0x89, " ".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 0x84 ^ 0x80, " ".length(), "".length());
            this.func_175805_a(world, structureBoundingBox, random, 0.07f, "  ".length(), " ".length(), " ".length(), 0x4B ^ 0x40, 0x67 ^ 0x63, 0x49 ^ 0x44, Blocks.web.getDefaultState(), Blocks.web.getDefaultState(), "".length() != 0);
            " ".length();
            int i = " ".length();
            "".length();
            if (3 == 0) {
                throw null;
            }
            while (i <= (0xCB ^ 0xC6)) {
                if ((i - " ".length()) % (0xF ^ 0xB) == 0) {
                    this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), i, " ".length(), 0xAE ^ 0xAA, i, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0xB5 ^ 0xB9, " ".length(), i, 0x33 ^ 0x3F, 0x75 ^ 0x71, i, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
                    this.setBlockState(world, Blocks.torch.getDefaultState(), "  ".length(), "   ".length(), i, structureBoundingBox);
                    this.setBlockState(world, Blocks.torch.getDefaultState(), 0x15 ^ 0x1E, "   ".length(), i, structureBoundingBox);
                    if (this.isLargeRoom) {
                        this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x62 ^ 0x64, i, " ".length(), 0xAA ^ 0xA3, i, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
                        this.fillWithBlocks(world, structureBoundingBox, 0x86 ^ 0x8A, 0x22 ^ 0x24, i, 0xA7 ^ 0xAB, 0x86 ^ 0x8F, i, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
                        "".length();
                        if (2 <= 0) {
                            throw null;
                        }
                    }
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), i, " ".length(), 0x73 ^ 0x77, i, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0x51 ^ 0x5D, " ".length(), i, 0x90 ^ 0x9C, 0xBE ^ 0xBA, i, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), "".length() != 0);
                    if (this.isLargeRoom) {
                        this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0xBA ^ 0xBC, i, " ".length(), 0x2A ^ 0x23, i, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), "".length() != 0);
                        this.fillWithBlocks(world, structureBoundingBox, 0xF ^ 0x3, 0x60 ^ 0x66, i, 0x5C ^ 0x50, 0x74 ^ 0x7D, i, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), "".length() != 0);
                    }
                }
                ++i;
            }
            int j = "   ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (j < (0x5E ^ 0x52)) {
                this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), j, 0xBE ^ 0xBA, "   ".length(), j, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x8C ^ 0x8A, " ".length(), j, 0x41 ^ 0x46, "   ".length(), j, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xF ^ 0x6, " ".length(), j, 0x56 ^ 0x5C, "   ".length(), j, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), "".length() != 0);
                j += 2;
            }
            if (this.isLargeRoom) {
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x19 ^ 0x1C, " ".length(), "   ".length(), 0xB1 ^ 0xB4, 0x1 ^ 0xC, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xB3 ^ 0xB9, 0xB8 ^ 0xBD, " ".length(), 0xCA ^ 0xC6, 0x26 ^ 0x23, 0x3B ^ 0x36, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x90 ^ 0x94, 0x0 ^ 0x5, " ".length(), 0x3 ^ 0xA, 0x60 ^ 0x65, "  ".length(), Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x94 ^ 0x90, 0x71 ^ 0x74, 0xC ^ 0x0, 0x97 ^ 0x9E, 0x78 ^ 0x7D, 0x9A ^ 0x97, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), "".length() != 0);
                this.setBlockState(world, Blocks.planks.getDefaultState(), 0x32 ^ 0x3B, 0xAD ^ 0xA8, 0xBF ^ 0xB4, structureBoundingBox);
                this.setBlockState(world, Blocks.planks.getDefaultState(), 0x8E ^ 0x86, 0x8A ^ 0x8F, 0xBB ^ 0xB0, structureBoundingBox);
                this.setBlockState(world, Blocks.planks.getDefaultState(), 0x94 ^ 0x9D, 0xC1 ^ 0xC4, 0x3E ^ 0x34, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, "   ".length(), 0xAC ^ 0xAA, "  ".length(), "   ".length(), 0x70 ^ 0x76, 0x7C ^ 0x70, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x1B ^ 0x11, 0x8C ^ 0x8A, "  ".length(), 0x61 ^ 0x6B, 0x19 ^ 0x1F, 0x8D ^ 0x87, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x9C ^ 0x98, 0x83 ^ 0x85, "  ".length(), 0x2D ^ 0x24, 0x8F ^ 0x89, "  ".length(), Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x12 ^ 0x16, 0xA6 ^ 0xA0, 0xCA ^ 0xC6, 0x67 ^ 0x6F, 0x71 ^ 0x77, 0x4F ^ 0x43, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), "".length() != 0);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0x25 ^ 0x2C, 0x54 ^ 0x52, 0x7E ^ 0x75, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0x1C ^ 0x14, 0x79 ^ 0x7F, 0x69 ^ 0x62, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0xE ^ 0x7, 0x32 ^ 0x34, 0x66 ^ 0x6C, structureBoundingBox);
                final int metadataWithOffset = this.getMetadataWithOffset(Blocks.ladder, "   ".length());
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), 0xA9 ^ 0xA3, " ".length(), 0x60 ^ 0x6D, structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), 0x4C ^ 0x46, "  ".length(), 0x99 ^ 0x94, structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), 0x67 ^ 0x6D, "   ".length(), 0x27 ^ 0x2A, structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), 0xA4 ^ 0xAE, 0x6 ^ 0x2, 0x9F ^ 0x92, structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), 0x87 ^ 0x8D, 0x79 ^ 0x7C, 0x34 ^ 0x39, structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), 0xB1 ^ 0xBB, 0x82 ^ 0x84, 0x1A ^ 0x17, structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), 0x6A ^ 0x60, 0x83 ^ 0x84, 0x18 ^ 0x15, structureBoundingBox);
                final int n2 = 0x99 ^ 0x9E;
                final int n3 = 0x4 ^ 0x3;
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2 - " ".length(), 0x3F ^ 0x36, n3, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2, 0x3 ^ 0xA, n3, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2 - " ".length(), 0x4A ^ 0x42, n3, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2, 0xAE ^ 0xA6, n3, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2 - " ".length(), 0x34 ^ 0x33, n3, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2, 0x1E ^ 0x19, n3, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2 - "  ".length(), 0x78 ^ 0x7F, n3, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2 + " ".length(), 0x97 ^ 0x90, n3, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2 - " ".length(), 0x9B ^ 0x9C, n3 - " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2 - " ".length(), 0x5A ^ 0x5D, n3 + " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2, 0x46 ^ 0x41, n3 - " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), n2, 0x15 ^ 0x12, n3 + " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.torch.getDefaultState(), n2 - "  ".length(), 0x9E ^ 0x96, n3, structureBoundingBox);
                this.setBlockState(world, Blocks.torch.getDefaultState(), n2 + " ".length(), 0x84 ^ 0x8C, n3, structureBoundingBox);
                this.setBlockState(world, Blocks.torch.getDefaultState(), n2 - " ".length(), 0x8C ^ 0x84, n3 - " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.torch.getDefaultState(), n2 - " ".length(), 0x3B ^ 0x33, n3 + " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.torch.getDefaultState(), n2, 0x8B ^ 0x83, n3 - " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.torch.getDefaultState(), n2, 0x46 ^ 0x4E, n3 + " ".length(), structureBoundingBox);
            }
            final int length = "   ".length();
            final int length2 = "   ".length();
            final int n4 = 0x9E ^ 0x9B;
            final List<WeightedRandomChestContent> strongholdLibraryChestContents = Library.strongholdLibraryChestContents;
            final WeightedRandomChestContent[] array = new WeightedRandomChestContent[" ".length()];
            array["".length()] = Items.enchanted_book.getRandom(random, " ".length(), 0x9C ^ 0x99, "  ".length());
            this.generateChestContents(world, structureBoundingBox, random, length, length2, n4, WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, array), " ".length() + random.nextInt(0xB0 ^ 0xB4));
            if (this.isLargeRoom) {
                this.setBlockState(world, Blocks.air.getDefaultState(), 0x9B ^ 0x97, 0x5B ^ 0x52, " ".length(), structureBoundingBox);
                final int n5 = 0x9A ^ 0x96;
                final int n6 = 0xA4 ^ 0xAC;
                final int length3 = " ".length();
                final List<WeightedRandomChestContent> strongholdLibraryChestContents2 = Library.strongholdLibraryChestContents;
                final WeightedRandomChestContent[] array2 = new WeightedRandomChestContent[" ".length()];
                array2["".length()] = Items.enchanted_book.getRandom(random, " ".length(), 0x4 ^ 0x1, "  ".length());
                this.generateChestContents(world, structureBoundingBox, random, n5, n6, length3, WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents2, array2), " ".length() + random.nextInt(0xB ^ 0xF));
            }
            return " ".length() != 0;
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\f,\u0019\u0002", "XMunk");
            Library.I[" ".length()] = I("\u001e\u00126\u0019", "JsZuV");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(Library.I["".length()], this.isLargeRoom);
        }
    }
    
    public static class Prison extends Stronghold
    {
        public static Prison func_175860_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -" ".length(), "".length(), 0x29 ^ 0x20, 0x94 ^ 0x91, 0x8 ^ 0x3, enumFacing);
            Prison prison;
            if (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                prison = new Prison(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (-1 == 2) {
                    throw null;
                }
            }
            else {
                prison = null;
            }
            return prison;
        }
        
        public Prison(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
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
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x6A ^ 0x62, 0xAC ^ 0xA8, 0x58 ^ 0x52, " ".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, " ".length(), " ".length(), "".length());
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x86 ^ 0x8C, "   ".length(), "   ".length(), 0x2F ^ 0x25, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x11 ^ 0x15, " ".length(), " ".length(), 0x8F ^ 0x8B, "   ".length(), " ".length(), "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x1B ^ 0x1F, " ".length(), "   ".length(), 0x38 ^ 0x3C, "   ".length(), "   ".length(), "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x70 ^ 0x74, " ".length(), 0x23 ^ 0x24, 0x8D ^ 0x89, "   ".length(), 0x66 ^ 0x61, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x68 ^ 0x6C, " ".length(), 0xBA ^ 0xB3, 0xC1 ^ 0xC5, "   ".length(), 0x4B ^ 0x42, "".length() != 0, random, StructureStrongholdPieces.access$0());
            this.fillWithBlocks(world, structureBoundingBox, 0x3A ^ 0x3E, " ".length(), 0x21 ^ 0x25, 0xB ^ 0xF, "   ".length(), 0x13 ^ 0x15, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x1 ^ 0x4, " ".length(), 0x72 ^ 0x77, 0xBA ^ 0xBD, "   ".length(), 0x68 ^ 0x6D, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 0x17 ^ 0x13, "   ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 0x7E ^ 0x7A, "   ".length(), 0x61 ^ 0x69, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, "   ".length())), 0x6A ^ 0x6E, " ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, "   ".length()) + (0x4E ^ 0x46)), 0x41 ^ 0x45, "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, "   ".length())), 0x13 ^ 0x17, " ".length(), 0x33 ^ 0x3B, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, "   ".length()) + (0x0 ^ 0x8)), 0xA9 ^ 0xAD, "  ".length(), 0x8 ^ 0x0, structureBoundingBox);
            return " ".length() != 0;
        }
        
        public Prison() {
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, " ".length(), " ".length());
        }
    }
    
    public static class StairsStraight extends Stronghold
    {
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, " ".length(), " ".length());
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x56 ^ 0x52, 0x47 ^ 0x4D, 0x1A ^ 0x1D, " ".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, " ".length(), 0x50 ^ 0x57, "".length());
            this.placeDoor(world, random, structureBoundingBox, Door.OPENING, " ".length(), " ".length(), 0x61 ^ 0x66);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.stone_stairs, "  ".length());
            int i = "".length();
            "".length();
            if (1 == -1) {
                throw null;
            }
            while (i < (0x12 ^ 0x14)) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), " ".length(), (0x81 ^ 0x87) - i, " ".length() + i, structureBoundingBox);
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), "  ".length(), (0x13 ^ 0x15) - i, " ".length() + i, structureBoundingBox);
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), "   ".length(), (0x4B ^ 0x4D) - i, " ".length() + i, structureBoundingBox);
                if (i < (0x2E ^ 0x2B)) {
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), " ".length(), (0x15 ^ 0x10) - i, " ".length() + i, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "  ".length(), (0x76 ^ 0x73) - i, " ".length() + i, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "   ".length(), (0x65 ^ 0x60) - i, " ".length() + i, structureBoundingBox);
                }
                ++i;
            }
            return " ".length() != 0;
        }
        
        public StairsStraight() {
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
                if (4 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public StairsStraight(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
        }
        
        public static StairsStraight func_175861_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -(0x6F ^ 0x68), "".length(), 0xD ^ 0x8, 0x49 ^ 0x42, 0x48 ^ 0x40, enumFacing);
            StairsStraight stairsStraight;
            if (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                stairsStraight = new StairsStraight(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                stairsStraight = null;
            }
            return stairsStraight;
        }
    }
    
    public static class Corridor extends Stronghold
    {
        private int field_74993_a;
        private static final String[] I;
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_74993_a = nbtTagCompound.getInteger(Corridor.I[" ".length()]);
        }
        
        static {
            I();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            int i = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (i < this.field_74993_a) {
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "".length(), "".length(), i, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), " ".length(), "".length(), i, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "  ".length(), "".length(), i, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "   ".length(), "".length(), i, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0xC6 ^ 0xC2, "".length(), i, structureBoundingBox);
                int j = " ".length();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                while (j <= "   ".length()) {
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "".length(), j, i, structureBoundingBox);
                    this.setBlockState(world, Blocks.air.getDefaultState(), " ".length(), j, i, structureBoundingBox);
                    this.setBlockState(world, Blocks.air.getDefaultState(), "  ".length(), j, i, structureBoundingBox);
                    this.setBlockState(world, Blocks.air.getDefaultState(), "   ".length(), j, i, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0xA9 ^ 0xAD, j, i, structureBoundingBox);
                    ++j;
                }
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "".length(), 0xB2 ^ 0xB6, i, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), " ".length(), 0x5C ^ 0x58, i, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "  ".length(), 0x68 ^ 0x6C, i, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "   ".length(), 0xA8 ^ 0xAC, i, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0xB5 ^ 0xB1, 0x80 ^ 0x84, i, structureBoundingBox);
                ++i;
            }
            return " ".length() != 0;
        }
        
        public Corridor() {
        }
        
        public Corridor(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            int field_74993_a;
            if (coordBaseMode != EnumFacing.NORTH && coordBaseMode != EnumFacing.SOUTH) {
                field_74993_a = boundingBox.getXSize();
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else {
                field_74993_a = boundingBox.getZSize();
            }
            this.field_74993_a = field_74993_a;
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
                if (4 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger(Corridor.I["".length()], this.field_74993_a);
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u001b\u001c*\u00111", "HhOaB");
            Corridor.I[" ".length()] = I("\u001f\u0012\r\"*", "LfhRY");
        }
        
        public static StructureBoundingBox func_175869_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            "   ".length();
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -" ".length(), "".length(), 0x89 ^ 0x8C, 0x1E ^ 0x1B, 0x9D ^ 0x99, enumFacing);
            final StructureComponent intersecting = StructureComponent.findIntersecting(list, componentToAddBoundingBox);
            if (intersecting == null) {
                return null;
            }
            if (intersecting.getBoundingBox().minY == componentToAddBoundingBox.minY) {
                int i = "   ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (i >= " ".length()) {
                    if (!intersecting.getBoundingBox().intersectsWith(StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -" ".length(), "".length(), 0x57 ^ 0x52, 0x3D ^ 0x38, i - " ".length(), enumFacing))) {
                        return StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -" ".length(), "".length(), 0x62 ^ 0x67, 0x46 ^ 0x43, i, enumFacing);
                    }
                    --i;
                }
            }
            return null;
        }
    }
    
    public static class Straight extends Stronghold
    {
        private static final String[] I;
        private boolean expandsX;
        private boolean expandsZ;
        
        public static Straight func_175862_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -" ".length(), "".length(), 0x5F ^ 0x5A, 0x4D ^ 0x48, 0x38 ^ 0x3F, enumFacing);
            Straight straight;
            if (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                straight = new Straight(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                straight = null;
            }
            return straight;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, " ".length(), " ".length());
            if (this.expandsX) {
                this.getNextComponentX((Stairs2)structureComponent, list, random, " ".length(), "  ".length());
            }
            if (this.expandsZ) {
                this.getNextComponentZ((Stairs2)structureComponent, list, random, " ".length(), "  ".length());
            }
        }
        
        private static void I() {
            (I = new String[0x4E ^ 0x4A])["".length()] = I("\u0015\u000e4\u0018", "YkRlE");
            Straight.I[" ".length()] = I("\u001e\u0007\u001d\n\u0010", "Lnzbd");
            Straight.I["  ".length()] = I("\r\b76", "AmQBf");
            Straight.I["   ".length()] = I("\u0015,\u0000\u001e\u001c", "GEgvh");
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.expandsX = nbtTagCompound.getBoolean(Straight.I["  ".length()]);
            this.expandsZ = nbtTagCompound.getBoolean(Straight.I["   ".length()]);
        }
        
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
                if (3 == 4) {
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
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x89 ^ 0x8D, 0x73 ^ 0x77, 0x2B ^ 0x2D, " ".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, " ".length(), " ".length(), "".length());
            this.placeDoor(world, random, structureBoundingBox, Door.OPENING, " ".length(), " ".length(), 0x8F ^ 0x89);
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, " ".length(), "  ".length(), " ".length(), Blocks.torch.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, "   ".length(), "  ".length(), " ".length(), Blocks.torch.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, " ".length(), "  ".length(), 0x65 ^ 0x60, Blocks.torch.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, "   ".length(), "  ".length(), 0x4E ^ 0x4B, Blocks.torch.getDefaultState());
            if (this.expandsX) {
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "  ".length(), "".length(), "   ".length(), 0x33 ^ 0x37, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            if (this.expandsZ) {
                this.fillWithBlocks(world, structureBoundingBox, 0xC7 ^ 0xC3, " ".length(), "  ".length(), 0x8 ^ 0xC, "   ".length(), 0x43 ^ 0x47, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            }
            return " ".length() != 0;
        }
        
        public Straight(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
            int expandsX;
            if (random.nextInt("  ".length()) == 0) {
                expandsX = " ".length();
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            else {
                expandsX = "".length();
            }
            this.expandsX = (expandsX != 0);
            int expandsZ;
            if (random.nextInt("  ".length()) == 0) {
                expandsZ = " ".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                expandsZ = "".length();
            }
            this.expandsZ = (expandsZ != 0);
        }
        
        public Straight() {
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(Straight.I["".length()], this.expandsX);
            nbtTagCompound.setBoolean(Straight.I[" ".length()], this.expandsZ);
        }
    }
    
    public static class RoomCrossing extends Stronghold
    {
        private static final String[] I;
        protected int roomType;
        private static final List<WeightedRandomChestContent> strongholdRoomCrossingChestContents;
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger(RoomCrossing.I["".length()], this.roomType);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x56 ^ 0x5C, 0x10 ^ 0x16, 0x93 ^ 0x99, " ".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 0x8F ^ 0x8B, " ".length(), "".length());
            this.fillWithBlocks(world, structureBoundingBox, 0xAB ^ 0xAF, " ".length(), 0x15 ^ 0x1F, 0x4B ^ 0x4D, "   ".length(), 0x1E ^ 0x14, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), 0x64 ^ 0x60, "".length(), "   ".length(), 0xA9 ^ 0xAF, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x4D ^ 0x47, " ".length(), 0x98 ^ 0x9C, 0x57 ^ 0x5D, "   ".length(), 0x74 ^ 0x72, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            switch (this.roomType) {
                case 0: {
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0x4C ^ 0x49, " ".length(), 0x48 ^ 0x4D, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0x61 ^ 0x64, "  ".length(), 0x2D ^ 0x28, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0x50 ^ 0x55, "   ".length(), 0x81 ^ 0x84, structureBoundingBox);
                    this.setBlockState(world, Blocks.torch.getDefaultState(), 0x5E ^ 0x5A, "   ".length(), 0x67 ^ 0x62, structureBoundingBox);
                    this.setBlockState(world, Blocks.torch.getDefaultState(), 0x3E ^ 0x38, "   ".length(), 0x5C ^ 0x59, structureBoundingBox);
                    this.setBlockState(world, Blocks.torch.getDefaultState(), 0xA ^ 0xF, "   ".length(), 0xB6 ^ 0xB2, structureBoundingBox);
                    this.setBlockState(world, Blocks.torch.getDefaultState(), 0x3D ^ 0x38, "   ".length(), 0x7F ^ 0x79, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 0xA6 ^ 0xA2, " ".length(), 0x8E ^ 0x8A, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 0x7F ^ 0x7B, " ".length(), 0x2C ^ 0x29, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 0x41 ^ 0x45, " ".length(), 0x1B ^ 0x1D, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 0xC0 ^ 0xC6, " ".length(), 0x8F ^ 0x8B, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 0xA2 ^ 0xA4, " ".length(), 0xB7 ^ 0xB2, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 0x49 ^ 0x4F, " ".length(), 0x8B ^ 0x8D, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 0x4D ^ 0x48, " ".length(), 0x54 ^ 0x50, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 0x65 ^ 0x60, " ".length(), 0x5A ^ 0x5C, structureBoundingBox);
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    int i = "".length();
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                    while (i < (0x24 ^ 0x21)) {
                        this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "   ".length(), " ".length(), "   ".length() + i, structureBoundingBox);
                        this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0x5 ^ 0x2, " ".length(), "   ".length() + i, structureBoundingBox);
                        this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "   ".length() + i, " ".length(), "   ".length(), structureBoundingBox);
                        this.setBlockState(world, Blocks.stonebrick.getDefaultState(), "   ".length() + i, " ".length(), 0xBC ^ 0xBB, structureBoundingBox);
                        ++i;
                    }
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0x6D ^ 0x68, " ".length(), 0x63 ^ 0x66, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0x96 ^ 0x93, "  ".length(), 0x45 ^ 0x40, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0x34 ^ 0x31, "   ".length(), 0x7B ^ 0x7E, structureBoundingBox);
                    this.setBlockState(world, Blocks.flowing_water.getDefaultState(), 0x18 ^ 0x1D, 0x73 ^ 0x77, 0x3A ^ 0x3F, structureBoundingBox);
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    int j = " ".length();
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    while (j <= (0x7A ^ 0x73)) {
                        this.setBlockState(world, Blocks.cobblestone.getDefaultState(), " ".length(), "   ".length(), j, structureBoundingBox);
                        this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0xA6 ^ 0xAF, "   ".length(), j, structureBoundingBox);
                        ++j;
                    }
                    int k = " ".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    while (k <= (0x1E ^ 0x17)) {
                        this.setBlockState(world, Blocks.cobblestone.getDefaultState(), k, "   ".length(), " ".length(), structureBoundingBox);
                        this.setBlockState(world, Blocks.cobblestone.getDefaultState(), k, "   ".length(), 0x73 ^ 0x7A, structureBoundingBox);
                        ++k;
                    }
                    this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0xA8 ^ 0xAD, " ".length(), 0x1B ^ 0x1F, structureBoundingBox);
                    this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x86 ^ 0x83, " ".length(), 0x9F ^ 0x99, structureBoundingBox);
                    this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x3 ^ 0x6, "   ".length(), 0x82 ^ 0x86, structureBoundingBox);
                    this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0xA1 ^ 0xA4, "   ".length(), 0x38 ^ 0x3E, structureBoundingBox);
                    this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x69 ^ 0x6D, " ".length(), 0xA2 ^ 0xA7, structureBoundingBox);
                    this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x2E ^ 0x28, " ".length(), 0x12 ^ 0x17, structureBoundingBox);
                    this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0xA1 ^ 0xA5, "   ".length(), 0x2F ^ 0x2A, structureBoundingBox);
                    this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0xB1 ^ 0xB7, "   ".length(), 0xC4 ^ 0xC1, structureBoundingBox);
                    int l = " ".length();
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                    while (l <= "   ".length()) {
                        this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x9B ^ 0x9F, l, 0x21 ^ 0x25, structureBoundingBox);
                        this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x53 ^ 0x55, l, 0x89 ^ 0x8D, structureBoundingBox);
                        this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0xC7 ^ 0xC3, l, 0x6C ^ 0x6A, structureBoundingBox);
                        this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0x3B ^ 0x3D, l, 0x12 ^ 0x14, structureBoundingBox);
                        ++l;
                    }
                    this.setBlockState(world, Blocks.torch.getDefaultState(), 0xAA ^ 0xAF, "   ".length(), 0x31 ^ 0x34, structureBoundingBox);
                    int length = "  ".length();
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                    while (length <= (0x49 ^ 0x41)) {
                        this.setBlockState(world, Blocks.planks.getDefaultState(), "  ".length(), "   ".length(), length, structureBoundingBox);
                        this.setBlockState(world, Blocks.planks.getDefaultState(), "   ".length(), "   ".length(), length, structureBoundingBox);
                        if (length <= "   ".length() || length >= (0x88 ^ 0x8F)) {
                            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x9B ^ 0x9F, "   ".length(), length, structureBoundingBox);
                            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x6A ^ 0x6F, "   ".length(), length, structureBoundingBox);
                            this.setBlockState(world, Blocks.planks.getDefaultState(), 0x86 ^ 0x80, "   ".length(), length, structureBoundingBox);
                        }
                        this.setBlockState(world, Blocks.planks.getDefaultState(), 0xB4 ^ 0xB3, "   ".length(), length, structureBoundingBox);
                        this.setBlockState(world, Blocks.planks.getDefaultState(), 0x4 ^ 0xC, "   ".length(), length, structureBoundingBox);
                        ++length;
                    }
                    this.setBlockState(world, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 0x18 ^ 0x11, " ".length(), "   ".length(), structureBoundingBox);
                    this.setBlockState(world, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 0x39 ^ 0x30, "  ".length(), "   ".length(), structureBoundingBox);
                    this.setBlockState(world, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 0xAD ^ 0xA4, "   ".length(), "   ".length(), structureBoundingBox);
                    final int length2 = "   ".length();
                    final int n = 0x77 ^ 0x73;
                    final int n2 = 0xB4 ^ 0xBC;
                    final List<WeightedRandomChestContent> strongholdRoomCrossingChestContents = RoomCrossing.strongholdRoomCrossingChestContents;
                    final WeightedRandomChestContent[] array = new WeightedRandomChestContent[" ".length()];
                    array["".length()] = Items.enchanted_book.getRandom(random);
                    this.generateChestContents(world, structureBoundingBox, random, length2, n, n2, WeightedRandomChestContent.func_177629_a(strongholdRoomCrossingChestContents, array), " ".length() + random.nextInt(0x92 ^ 0x96));
                    break;
                }
            }
            return " ".length() != 0;
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u0018=\u0018\u001c", "LDhyd");
            RoomCrossing.I[" ".length()] = I("\u0005\u000e\u00004", "QwpQm");
        }
        
        public RoomCrossing(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
            this.roomType = random.nextInt(0x77 ^ 0x72);
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
        
        static {
            I();
            final WeightedRandomChestContent[] array = new WeightedRandomChestContent[0x10 ^ 0x17];
            array["".length()] = new WeightedRandomChestContent(Items.iron_ingot, "".length(), " ".length(), 0xA0 ^ 0xA5, 0x69 ^ 0x63);
            array[" ".length()] = new WeightedRandomChestContent(Items.gold_ingot, "".length(), " ".length(), "   ".length(), 0x6C ^ 0x69);
            array["  ".length()] = new WeightedRandomChestContent(Items.redstone, "".length(), 0x7C ^ 0x78, 0x6E ^ 0x67, 0x12 ^ 0x17);
            array["   ".length()] = new WeightedRandomChestContent(Items.coal, "".length(), "   ".length(), 0xBB ^ 0xB3, 0x12 ^ 0x18);
            array[0x5 ^ 0x1] = new WeightedRandomChestContent(Items.bread, "".length(), " ".length(), "   ".length(), 0x41 ^ 0x4E);
            array[0x9B ^ 0x9E] = new WeightedRandomChestContent(Items.apple, "".length(), " ".length(), "   ".length(), 0xCA ^ 0xC5);
            array[0x55 ^ 0x53] = new WeightedRandomChestContent(Items.iron_pickaxe, "".length(), " ".length(), " ".length(), " ".length());
            strongholdRoomCrossingChestContents = Lists.newArrayList((Object[])array);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.roomType = nbtTagCompound.getInteger(RoomCrossing.I[" ".length()]);
        }
        
        public RoomCrossing() {
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, 0x35 ^ 0x31, " ".length());
            this.getNextComponentX((Stairs2)structureComponent, list, random, " ".length(), 0xBE ^ 0xBA);
            this.getNextComponentZ((Stairs2)structureComponent, list, random, " ".length(), 0x52 ^ 0x56);
        }
        
        public static RoomCrossing func_175859_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -(0xB1 ^ 0xB5), -" ".length(), "".length(), 0xB5 ^ 0xBE, 0x11 ^ 0x16, 0xBA ^ 0xB1, enumFacing);
            RoomCrossing roomCrossing;
            if (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                roomCrossing = new RoomCrossing(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                roomCrossing = null;
            }
            return roomCrossing;
        }
    }
    
    public static class ChestCorridor extends Stronghold
    {
        private static final String[] I;
        private boolean hasMadeChest;
        private static final List<WeightedRandomChestContent> strongholdChestContents;
        
        public static ChestCorridor func_175868_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -" ".length(), "".length(), 0x6 ^ 0x3, 0x8F ^ 0x8A, 0xC ^ 0xB, enumFacing);
            ChestCorridor chestCorridor;
            if (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                chestCorridor = new ChestCorridor(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                chestCorridor = null;
            }
            return chestCorridor;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.hasMadeChest = nbtTagCompound.getBoolean(ChestCorridor.I[" ".length()]);
        }
        
        static {
            I();
            final WeightedRandomChestContent[] array = new WeightedRandomChestContent[0x7B ^ 0x69];
            array["".length()] = new WeightedRandomChestContent(Items.ender_pearl, "".length(), " ".length(), " ".length(), 0xAF ^ 0xA5);
            array[" ".length()] = new WeightedRandomChestContent(Items.diamond, "".length(), " ".length(), "   ".length(), "   ".length());
            array["  ".length()] = new WeightedRandomChestContent(Items.iron_ingot, "".length(), " ".length(), 0x67 ^ 0x62, 0x4C ^ 0x46);
            array["   ".length()] = new WeightedRandomChestContent(Items.gold_ingot, "".length(), " ".length(), "   ".length(), 0xAB ^ 0xAE);
            array[0x1 ^ 0x5] = new WeightedRandomChestContent(Items.redstone, "".length(), 0x9E ^ 0x9A, 0xAE ^ 0xA7, 0x7D ^ 0x78);
            array[0x35 ^ 0x30] = new WeightedRandomChestContent(Items.bread, "".length(), " ".length(), "   ".length(), 0x97 ^ 0x98);
            array[0xA7 ^ 0xA1] = new WeightedRandomChestContent(Items.apple, "".length(), " ".length(), "   ".length(), 0xBF ^ 0xB0);
            array[0x60 ^ 0x67] = new WeightedRandomChestContent(Items.iron_pickaxe, "".length(), " ".length(), " ".length(), 0x1D ^ 0x18);
            array[0x74 ^ 0x7C] = new WeightedRandomChestContent(Items.iron_sword, "".length(), " ".length(), " ".length(), 0xBD ^ 0xB8);
            array[0x79 ^ 0x70] = new WeightedRandomChestContent(Items.iron_chestplate, "".length(), " ".length(), " ".length(), 0x3E ^ 0x3B);
            array[0x5B ^ 0x51] = new WeightedRandomChestContent(Items.iron_helmet, "".length(), " ".length(), " ".length(), 0xAD ^ 0xA8);
            array[0x88 ^ 0x83] = new WeightedRandomChestContent(Items.iron_leggings, "".length(), " ".length(), " ".length(), 0x19 ^ 0x1C);
            array[0x55 ^ 0x59] = new WeightedRandomChestContent(Items.iron_boots, "".length(), " ".length(), " ".length(), 0x91 ^ 0x94);
            array[0x63 ^ 0x6E] = new WeightedRandomChestContent(Items.golden_apple, "".length(), " ".length(), " ".length(), " ".length());
            array[0x3C ^ 0x32] = new WeightedRandomChestContent(Items.saddle, "".length(), " ".length(), " ".length(), " ".length());
            array[0x56 ^ 0x59] = new WeightedRandomChestContent(Items.iron_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            array[0x2E ^ 0x3E] = new WeightedRandomChestContent(Items.golden_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            array[0x60 ^ 0x71] = new WeightedRandomChestContent(Items.diamond_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            strongholdChestContents = Lists.newArrayList((Object[])array);
        }
        
        public ChestCorridor(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
        }
        
        public ChestCorridor() {
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
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, " ".length(), " ".length());
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(ChestCorridor.I["".length()], this.hasMadeChest);
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u0016-\u001f8\u0010", "UEzKd");
            ChestCorridor.I[" ".length()] = I("\u0004:!\u00109", "GRDcM");
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return "".length() != 0;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x48 ^ 0x4C, 0x1D ^ 0x19, 0xBA ^ 0xBC, " ".length() != 0, random, StructureStrongholdPieces.access$0());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, " ".length(), " ".length(), "".length());
            this.placeDoor(world, random, structureBoundingBox, Door.OPENING, " ".length(), " ".length(), 0xC3 ^ 0xC5);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), "  ".length(), "   ".length(), " ".length(), 0x68 ^ 0x6C, Blocks.stonebrick.getDefaultState(), Blocks.stonebrick.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), "   ".length(), " ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), "   ".length(), " ".length(), 0x86 ^ 0x83, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), "   ".length(), "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), "   ".length(), "  ".length(), 0x36 ^ 0x32, structureBoundingBox);
            int i = "  ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
            while (i <= (0x61 ^ 0x65)) {
                this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), "  ".length(), " ".length(), i, structureBoundingBox);
                ++i;
            }
            if (!this.hasMadeChest && structureBoundingBox.isVecInside(new BlockPos(this.getXWithOffset("   ".length(), "   ".length()), this.getYWithOffset("  ".length()), this.getZWithOffset("   ".length(), "   ".length())))) {
                this.hasMadeChest = (" ".length() != 0);
                final int length = "   ".length();
                final int length2 = "  ".length();
                final int length3 = "   ".length();
                final List<WeightedRandomChestContent> strongholdChestContents = ChestCorridor.strongholdChestContents;
                final WeightedRandomChestContent[] array = new WeightedRandomChestContent[" ".length()];
                array["".length()] = Items.enchanted_book.getRandom(random);
                this.generateChestContents(world, structureBoundingBox, random, length, length2, length3, WeightedRandomChestContent.func_177629_a(strongholdChestContents, array), "  ".length() + random.nextInt("  ".length()));
            }
            return " ".length() != 0;
        }
    }
}
