package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;

public class WorldGenBigTree extends WorldGenAbstractTree
{
    int height;
    List<FoliageCoordinates> field_175948_j;
    double branchSlope;
    double leafDensity;
    int heightLimit;
    private World world;
    double scaleWidth;
    private Random rand;
    double heightAttenuation;
    int heightLimitLimit;
    int leafDistanceLimit;
    int trunkSize;
    private BlockPos basePos;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos basePos) {
        this.world = world;
        this.basePos = basePos;
        this.rand = new Random(random.nextLong());
        if (this.heightLimit == 0) {
            this.heightLimit = (0x67 ^ 0x62) + this.rand.nextInt(this.heightLimitLimit);
        }
        if (!this.validTreeLocation()) {
            return "".length() != 0;
        }
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateTrunk();
        this.generateLeafNodeBases();
        return " ".length() != 0;
    }
    
    float layerSize(final int n) {
        if (n < this.heightLimit * 0.3f) {
            return -1.0f;
        }
        final float n2 = this.heightLimit / 2.0f;
        final float n3 = n2 - n;
        float sqrt_float = MathHelper.sqrt_float(n2 * n2 - n3 * n3);
        if (n3 == 0.0f) {
            sqrt_float = n2;
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else if (Math.abs(n3) >= n2) {
            return 0.0f;
        }
        return sqrt_float * 0.5f;
    }
    
    @Override
    public void func_175904_e() {
        this.leafDistanceLimit = (0x13 ^ 0x16);
    }
    
    boolean leafNodeNeedsBase(final int n) {
        if (n >= this.heightLimit * 0.2) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    void generateLeaves() {
        final Iterator<FoliageCoordinates> iterator = this.field_175948_j.iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.generateLeafNode(iterator.next());
        }
    }
    
    public WorldGenBigTree(final boolean b) {
        super(b);
        this.basePos = BlockPos.ORIGIN;
        this.heightAttenuation = 0.618;
        this.branchSlope = 0.381;
        this.scaleWidth = 1.0;
        this.leafDensity = 1.0;
        this.trunkSize = " ".length();
        this.heightLimitLimit = (0xBE ^ 0xB2);
        this.leafDistanceLimit = (0x2B ^ 0x2F);
    }
    
    private BlockLog.EnumAxis func_175938_b(final BlockPos blockPos, final BlockPos blockPos2) {
        BlockLog.EnumAxis enumAxis = BlockLog.EnumAxis.Y;
        final int abs = Math.abs(blockPos2.getX() - blockPos.getX());
        final int abs2 = Math.abs(blockPos2.getZ() - blockPos.getZ());
        final int max = Math.max(abs, abs2);
        if (max > 0) {
            if (abs == max) {
                enumAxis = BlockLog.EnumAxis.X;
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else if (abs2 == max) {
                enumAxis = BlockLog.EnumAxis.Z;
            }
        }
        return enumAxis;
    }
    
    private int getGreatestDistance(final BlockPos blockPos) {
        final int abs_int = MathHelper.abs_int(blockPos.getX());
        final int abs_int2 = MathHelper.abs_int(blockPos.getY());
        final int abs_int3 = MathHelper.abs_int(blockPos.getZ());
        int n;
        if (abs_int3 > abs_int && abs_int3 > abs_int2) {
            n = abs_int3;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (abs_int2 > abs_int) {
            n = abs_int2;
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            n = abs_int;
        }
        return n;
    }
    
    void generateTrunk() {
        final BlockPos basePos = this.basePos;
        final BlockPos up = this.basePos.up(this.height);
        final Block log = Blocks.log;
        this.func_175937_a(basePos, up, log);
        if (this.trunkSize == "  ".length()) {
            this.func_175937_a(basePos.east(), up.east(), log);
            this.func_175937_a(basePos.east().south(), up.east().south(), log);
            this.func_175937_a(basePos.south(), up.south(), log);
        }
    }
    
    void generateLeafNode(final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i < this.leafDistanceLimit) {
            this.func_181631_a(blockPos.up(i), this.leafSize(i), Blocks.leaves.getDefaultState().withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, (boolean)("".length() != 0)));
            ++i;
        }
    }
    
    void func_175937_a(final BlockPos blockPos, final BlockPos blockPos2, final Block block) {
        final BlockPos add = blockPos2.add(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        final int greatestDistance = this.getGreatestDistance(add);
        final float n = add.getX() / greatestDistance;
        final float n2 = add.getY() / greatestDistance;
        final float n3 = add.getZ() / greatestDistance;
        int i = "".length();
        "".length();
        if (0 < -1) {
            throw null;
        }
        while (i <= greatestDistance) {
            final BlockPos add2 = blockPos.add(0.5f + i * n, 0.5f + i * n2, 0.5f + i * n3);
            this.setBlockAndNotifyAdequately(this.world, add2, block.getDefaultState().withProperty(BlockLog.LOG_AXIS, this.func_175938_b(blockPos, add2)));
            ++i;
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
            if (3 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    int checkBlockLine(final BlockPos blockPos, final BlockPos blockPos2) {
        final BlockPos add = blockPos2.add(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        final int greatestDistance = this.getGreatestDistance(add);
        final float n = add.getX() / greatestDistance;
        final float n2 = add.getY() / greatestDistance;
        final float n3 = add.getZ() / greatestDistance;
        if (greatestDistance == 0) {
            return -" ".length();
        }
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i <= greatestDistance) {
            if (!this.func_150523_a(this.world.getBlockState(blockPos.add(0.5f + i * n, 0.5f + i * n2, 0.5f + i * n3)).getBlock())) {
                return i;
            }
            ++i;
        }
        return -" ".length();
    }
    
    float leafSize(final int n) {
        float n2;
        if (n >= 0 && n < this.leafDistanceLimit) {
            if (n != 0 && n != this.leafDistanceLimit - " ".length()) {
                n2 = 3.0f;
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                n2 = 2.0f;
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
        }
        else {
            n2 = -1.0f;
        }
        return n2;
    }
    
    void func_181631_a(final BlockPos blockPos, final float n, final IBlockState blockState) {
        final int n2 = (int)(n + 0.618);
        int i = -n2;
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i <= n2) {
            int j = -n2;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (j <= n2) {
                if (Math.pow(Math.abs(i) + 0.5, 2.0) + Math.pow(Math.abs(j) + 0.5, 2.0) <= n * n) {
                    final BlockPos add = blockPos.add(i, "".length(), j);
                    final Material material = this.world.getBlockState(add).getBlock().getMaterial();
                    if (material == Material.air || material == Material.leaves) {
                        this.setBlockAndNotifyAdequately(this.world, add, blockState);
                    }
                }
                ++j;
            }
            ++i;
        }
    }
    
    void generateLeafNodeBases() {
        final Iterator<FoliageCoordinates> iterator = this.field_175948_j.iterator();
        "".length();
        if (4 == 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final FoliageCoordinates foliageCoordinates = iterator.next();
            final int func_177999_q = foliageCoordinates.func_177999_q();
            final BlockPos blockPos = new BlockPos(this.basePos.getX(), func_177999_q, this.basePos.getZ());
            if (!blockPos.equals(foliageCoordinates) && this.leafNodeNeedsBase(func_177999_q - this.basePos.getY())) {
                this.func_175937_a(blockPos, foliageCoordinates, Blocks.log);
            }
        }
    }
    
    private boolean validTreeLocation() {
        final Block block = this.world.getBlockState(this.basePos.down()).getBlock();
        if (block != Blocks.dirt && block != Blocks.grass && block != Blocks.farmland) {
            return "".length() != 0;
        }
        final int checkBlockLine = this.checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - " ".length()));
        if (checkBlockLine == -" ".length()) {
            return " ".length() != 0;
        }
        if (checkBlockLine < (0x21 ^ 0x27)) {
            return "".length() != 0;
        }
        this.heightLimit = checkBlockLine;
        return " ".length() != 0;
    }
    
    void generateLeafNodeList() {
        this.height = (int)(this.heightLimit * this.heightAttenuation);
        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - " ".length();
        }
        int length = (int)(1.382 + Math.pow(this.leafDensity * this.heightLimit / 13.0, 2.0));
        if (length < " ".length()) {
            length = " ".length();
        }
        final int n = this.basePos.getY() + this.height;
        int i = this.heightLimit - this.leafDistanceLimit;
        (this.field_175948_j = (List<FoliageCoordinates>)Lists.newArrayList()).add(new FoliageCoordinates(this.basePos.up(i), n));
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i >= 0) {
            final float layerSize = this.layerSize(i);
            if (layerSize >= 0.0f) {
                int j = "".length();
                "".length();
                if (3 == 2) {
                    throw null;
                }
                while (j < length) {
                    final double n2 = this.scaleWidth * layerSize * (this.rand.nextFloat() + 0.328);
                    final double n3 = this.rand.nextFloat() * 2.0f * 3.141592653589793;
                    final BlockPos add = this.basePos.add(n2 * Math.sin(n3) + 0.5, i - " ".length(), n2 * Math.cos(n3) + 0.5);
                    if (this.checkBlockLine(add, add.up(this.leafDistanceLimit)) == -" ".length()) {
                        final int n4 = this.basePos.getX() - add.getX();
                        final int n5 = this.basePos.getZ() - add.getZ();
                        final double n6 = add.getY() - Math.sqrt(n4 * n4 + n5 * n5) * this.branchSlope;
                        int n7;
                        if (n6 > n) {
                            n7 = n;
                            "".length();
                            if (3 < 2) {
                                throw null;
                            }
                        }
                        else {
                            n7 = (int)n6;
                        }
                        final BlockPos blockPos = new BlockPos(this.basePos.getX(), n7, this.basePos.getZ());
                        if (this.checkBlockLine(blockPos, add) == -" ".length()) {
                            this.field_175948_j.add(new FoliageCoordinates(add, blockPos.getY()));
                        }
                    }
                    ++j;
                }
            }
            --i;
        }
    }
    
    static class FoliageCoordinates extends BlockPos
    {
        private final int field_178000_b;
        
        public FoliageCoordinates(final BlockPos blockPos, final int field_178000_b) {
            super(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            this.field_178000_b = field_178000_b;
        }
        
        public int func_177999_q() {
            return this.field_178000_b;
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
                if (1 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
