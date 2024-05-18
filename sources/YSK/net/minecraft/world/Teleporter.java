package net.minecraft.world;

import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.block.state.pattern.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import java.util.*;

public class Teleporter
{
    private final LongHashMap destinationCoordinateCache;
    private final Random random;
    private final List<Long> destinationCoordinateKeys;
    private final WorldServer worldServerInstance;
    
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void placeInPortal(final Entity entity, final float n) {
        if (this.worldServerInstance.provider.getDimensionId() != " ".length()) {
            if (!this.placeInExistingPortal(entity, n)) {
                this.makePortal(entity);
                this.placeInExistingPortal(entity, n);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
        }
        else {
            final int floor_double = MathHelper.floor_double(entity.posX);
            final int n2 = MathHelper.floor_double(entity.posY) - " ".length();
            final int floor_double2 = MathHelper.floor_double(entity.posZ);
            final int length = " ".length();
            final int length2 = "".length();
            int i = -"  ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
            while (i <= "  ".length()) {
                int j = -"  ".length();
                "".length();
                if (4 <= 1) {
                    throw null;
                }
                while (j <= "  ".length()) {
                    int k = -" ".length();
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    while (k < "   ".length()) {
                        final int n3 = floor_double + j * length + i * length2;
                        final int n4 = n2 + k;
                        final int n5 = floor_double2 + j * length2 - i * length;
                        int n6;
                        if (k < 0) {
                            n6 = " ".length();
                            "".length();
                            if (4 == 1) {
                                throw null;
                            }
                        }
                        else {
                            n6 = "".length();
                        }
                        final int n7 = n6;
                        final WorldServer worldServerInstance = this.worldServerInstance;
                        final BlockPos blockPos = new BlockPos(n3, n4, n5);
                        IBlockState blockState;
                        if (n7 != 0) {
                            blockState = Blocks.obsidian.getDefaultState();
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                        }
                        else {
                            blockState = Blocks.air.getDefaultState();
                        }
                        worldServerInstance.setBlockState(blockPos, blockState);
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
            entity.setLocationAndAngles(floor_double, n2, floor_double2, entity.rotationYaw, 0.0f);
            final double motionX = 0.0;
            entity.motionZ = motionX;
            entity.motionY = motionX;
            entity.motionX = motionX;
        }
    }
    
    public boolean placeInExistingPortal(final Entity entity, final float n) {
        double n2 = -1.0;
        final int floor_double = MathHelper.floor_double(entity.posX);
        final int floor_double2 = MathHelper.floor_double(entity.posZ);
        int n3 = " ".length();
        BlockPos origin = BlockPos.ORIGIN;
        final long chunkXZ2Int = ChunkCoordIntPair.chunkXZ2Int(floor_double, floor_double2);
        if (this.destinationCoordinateCache.containsItem(chunkXZ2Int)) {
            final PortalPosition portalPosition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(chunkXZ2Int);
            n2 = 0.0;
            origin = portalPosition;
            portalPosition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
            n3 = "".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            final BlockPos blockPos = new BlockPos(entity);
            int i = -(126 + 85 - 146 + 63);
            "".length();
            if (1 <= -1) {
                throw null;
            }
            while (i <= 11 + 112 - 102 + 107) {
                int j = -(72 + 87 - 66 + 35);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
                while (j <= 112 + 27 - 77 + 66) {
                    BlockPos add = blockPos.add(i, this.worldServerInstance.getActualHeight() - " ".length() - blockPos.getY(), j);
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                    while (add.getY() >= 0) {
                        BlockPos blockPos2 = add.down();
                        if (this.worldServerInstance.getBlockState(add).getBlock() == Blocks.portal) {
                            "".length();
                            if (4 <= 0) {
                                throw null;
                            }
                            while (this.worldServerInstance.getBlockState(blockPos2 = add.down()).getBlock() == Blocks.portal) {
                                add = blockPos2;
                            }
                            final double distanceSq = add.distanceSq(blockPos);
                            if (n2 < 0.0 || distanceSq < n2) {
                                n2 = distanceSq;
                                origin = add;
                            }
                        }
                        add = blockPos2;
                    }
                    ++j;
                }
                ++i;
            }
        }
        if (n2 >= 0.0) {
            if (n3 != 0) {
                this.destinationCoordinateCache.add(chunkXZ2Int, new PortalPosition(origin, this.worldServerInstance.getTotalWorldTime()));
                this.destinationCoordinateKeys.add(chunkXZ2Int);
            }
            double n4 = origin.getX() + 0.5;
            final double n5 = origin.getY() + 0.5;
            double n6 = origin.getZ() + 0.5;
            final BlockPattern.PatternHelper func_181089_f = Blocks.portal.func_181089_f(this.worldServerInstance, origin);
            int n7;
            if (func_181089_f.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) {
                n7 = " ".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                n7 = "".length();
            }
            final int n8 = n7;
            double n9;
            if (func_181089_f.getFinger().getAxis() == EnumFacing.Axis.X) {
                n9 = func_181089_f.func_181117_a().getZ();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                n9 = func_181089_f.func_181117_a().getX();
            }
            double n10 = n9;
            final double n11 = func_181089_f.func_181117_a().getY() + " ".length() - entity.func_181014_aG().yCoord * func_181089_f.func_181119_e();
            if (n8 != 0) {
                ++n10;
            }
            if (func_181089_f.getFinger().getAxis() == EnumFacing.Axis.X) {
                n6 = n10 + (1.0 - entity.func_181014_aG().xCoord) * func_181089_f.func_181118_d() * func_181089_f.getFinger().rotateY().getAxisDirection().getOffset();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                n4 = n10 + (1.0 - entity.func_181014_aG().xCoord) * func_181089_f.func_181118_d() * func_181089_f.getFinger().rotateY().getAxisDirection().getOffset();
            }
            float n12 = 0.0f;
            float n13 = 0.0f;
            float n14 = 0.0f;
            float n15 = 0.0f;
            if (func_181089_f.getFinger().getOpposite() == entity.func_181012_aH()) {
                n12 = 1.0f;
                n13 = 1.0f;
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (func_181089_f.getFinger().getOpposite() == entity.func_181012_aH().getOpposite()) {
                n12 = -1.0f;
                n13 = -1.0f;
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else if (func_181089_f.getFinger().getOpposite() == entity.func_181012_aH().rotateY()) {
                n14 = 1.0f;
                n15 = -1.0f;
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                n14 = -1.0f;
                n15 = 1.0f;
            }
            final double motionX = entity.motionX;
            final double motionZ = entity.motionZ;
            entity.motionX = motionX * n12 + motionZ * n15;
            entity.motionZ = motionX * n14 + motionZ * n13;
            entity.setLocationAndAngles(n4, n11, n6, entity.rotationYaw = n - entity.func_181012_aH().getOpposite().getHorizontalIndex() * (0x47 ^ 0x1D) + func_181089_f.getFinger().getHorizontalIndex() * (0x2D ^ 0x77), entity.rotationPitch);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Teleporter(final WorldServer worldServerInstance) {
        this.destinationCoordinateCache = new LongHashMap();
        this.destinationCoordinateKeys = (List<Long>)Lists.newArrayList();
        this.worldServerInstance = worldServerInstance;
        this.random = new Random(worldServerInstance.getSeed());
    }
    
    public boolean makePortal(final Entity entity) {
        final int n = 0x6E ^ 0x7E;
        double n2 = -1.0;
        final int floor_double = MathHelper.floor_double(entity.posX);
        final int floor_double2 = MathHelper.floor_double(entity.posY);
        final int floor_double3 = MathHelper.floor_double(entity.posZ);
        int n3 = floor_double;
        int n4 = floor_double2;
        int n5 = floor_double3;
        int length = "".length();
        final int nextInt = this.random.nextInt(0x7E ^ 0x7A);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = floor_double - n;
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i <= floor_double + n) {
            final double n6 = i + 0.5 - entity.posX;
            int j = floor_double3 - n;
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (j <= floor_double3 + n) {
                final double n7 = j + 0.5 - entity.posZ;
                int k = this.worldServerInstance.getActualHeight() - " ".length();
                "".length();
                if (4 < 1) {
                    throw null;
                }
                while (k >= 0) {
                    Label_0619: {
                        if (this.worldServerInstance.isAirBlock(mutableBlockPos.func_181079_c(i, k, j))) {
                            "".length();
                            if (0 <= -1) {
                                throw null;
                            }
                            while (k > 0 && this.worldServerInstance.isAirBlock(mutableBlockPos.func_181079_c(i, k - " ".length(), j))) {
                                --k;
                            }
                            int l = nextInt;
                            "".length();
                            if (2 <= 1) {
                                throw null;
                            }
                            while (l < nextInt + (0xAA ^ 0xAE)) {
                                int n8 = l % "  ".length();
                                int n9 = " ".length() - n8;
                                if (l % (0x1B ^ 0x1F) >= "  ".length()) {
                                    n8 = -n8;
                                    n9 = -n9;
                                }
                                int length2 = "".length();
                                "".length();
                                if (3 != 3) {
                                    throw null;
                                }
                                while (length2 < "   ".length()) {
                                    int length3 = "".length();
                                    "".length();
                                    if (3 < 1) {
                                        throw null;
                                    }
                                    while (length3 < (0xBD ^ 0xB9)) {
                                        int n10 = -" ".length();
                                        "".length();
                                        if (-1 >= 0) {
                                            throw null;
                                        }
                                        while (n10 < (0xC4 ^ 0xC0)) {
                                            mutableBlockPos.func_181079_c(i + (length3 - " ".length()) * n8 + length2 * n9, k + n10, j + (length3 - " ".length()) * n9 - length2 * n8);
                                            if (n10 < 0 && !this.worldServerInstance.getBlockState(mutableBlockPos).getBlock().getMaterial().isSolid()) {
                                                break Label_0619;
                                            }
                                            if (n10 >= 0 && !this.worldServerInstance.isAirBlock(mutableBlockPos)) {
                                                "".length();
                                                if (2 >= 3) {
                                                    throw null;
                                                }
                                                break Label_0619;
                                            }
                                            else {
                                                ++n10;
                                            }
                                        }
                                        ++length3;
                                    }
                                    ++length2;
                                }
                                final double n11 = k + 0.5 - entity.posY;
                                final double n12 = n6 * n6 + n11 * n11 + n7 * n7;
                                if (n2 < 0.0 || n12 < n2) {
                                    n2 = n12;
                                    n3 = i;
                                    n4 = k;
                                    n5 = j;
                                    length = l % (0x38 ^ 0x3C);
                                }
                                ++l;
                            }
                        }
                    }
                    --k;
                }
                ++j;
            }
            ++i;
        }
        if (n2 < 0.0) {
            int n13 = floor_double - n;
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (n13 <= floor_double + n) {
                final double n14 = n13 + 0.5 - entity.posX;
                int n15 = floor_double3 - n;
                "".length();
                if (4 < -1) {
                    throw null;
                }
                while (n15 <= floor_double3 + n) {
                    final double n16 = n15 + 0.5 - entity.posZ;
                    int n17 = this.worldServerInstance.getActualHeight() - " ".length();
                    "".length();
                    if (-1 == 2) {
                        throw null;
                    }
                    while (n17 >= 0) {
                        Label_1122: {
                            if (this.worldServerInstance.isAirBlock(mutableBlockPos.func_181079_c(n13, n17, n15))) {
                                "".length();
                                if (1 <= -1) {
                                    throw null;
                                }
                                while (n17 > 0 && this.worldServerInstance.isAirBlock(mutableBlockPos.func_181079_c(n13, n17 - " ".length(), n15))) {
                                    --n17;
                                }
                                int n18 = nextInt;
                                "".length();
                                if (1 < -1) {
                                    throw null;
                                }
                                while (n18 < nextInt + "  ".length()) {
                                    final int n19 = n18 % "  ".length();
                                    final int n20 = " ".length() - n19;
                                    int length4 = "".length();
                                    "".length();
                                    if (3 <= 0) {
                                        throw null;
                                    }
                                    while (length4 < (0x8E ^ 0x8A)) {
                                        int n21 = -" ".length();
                                        "".length();
                                        if (2 <= 0) {
                                            throw null;
                                        }
                                        while (n21 < (0x42 ^ 0x46)) {
                                            mutableBlockPos.func_181079_c(n13 + (length4 - " ".length()) * n19, n17 + n21, n15 + (length4 - " ".length()) * n20);
                                            if (n21 < 0 && !this.worldServerInstance.getBlockState(mutableBlockPos).getBlock().getMaterial().isSolid()) {
                                                break Label_1122;
                                            }
                                            if (n21 >= 0 && !this.worldServerInstance.isAirBlock(mutableBlockPos)) {
                                                "".length();
                                                if (0 < -1) {
                                                    throw null;
                                                }
                                                break Label_1122;
                                            }
                                            else {
                                                ++n21;
                                            }
                                        }
                                        ++length4;
                                    }
                                    final double n22 = n17 + 0.5 - entity.posY;
                                    final double n23 = n14 * n14 + n22 * n22 + n16 * n16;
                                    if (n2 < 0.0 || n23 < n2) {
                                        n2 = n23;
                                        n3 = n13;
                                        n4 = n17;
                                        n5 = n15;
                                        length = n18 % "  ".length();
                                    }
                                    ++n18;
                                }
                            }
                        }
                        --n17;
                    }
                    ++n15;
                }
                ++n13;
            }
        }
        final int n24 = n3;
        int clamp_int = n4;
        final int n25 = n5;
        int n26 = length % "  ".length();
        int n27 = " ".length() - n26;
        if (length % (0x5C ^ 0x58) >= "  ".length()) {
            n26 = -n26;
            n27 = -n27;
        }
        if (n2 < 0.0) {
            clamp_int = MathHelper.clamp_int(n4, 0xFA ^ 0xBC, this.worldServerInstance.getActualHeight() - (0x89 ^ 0x83));
            int n28 = -" ".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (n28 <= " ".length()) {
                int length5 = " ".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                while (length5 < "   ".length()) {
                    int n29 = -" ".length();
                    "".length();
                    if (4 == -1) {
                        throw null;
                    }
                    while (n29 < "   ".length()) {
                        final int n30 = n24 + (length5 - " ".length()) * n26 + n28 * n27;
                        final int n31 = clamp_int + n29;
                        final int n32 = n25 + (length5 - " ".length()) * n27 - n28 * n26;
                        int n33;
                        if (n29 < 0) {
                            n33 = " ".length();
                            "".length();
                            if (-1 < -1) {
                                throw null;
                            }
                        }
                        else {
                            n33 = "".length();
                        }
                        final int n34 = n33;
                        final WorldServer worldServerInstance = this.worldServerInstance;
                        final BlockPos blockPos = new BlockPos(n30, n31, n32);
                        IBlockState blockState;
                        if (n34 != 0) {
                            blockState = Blocks.obsidian.getDefaultState();
                            "".length();
                            if (1 >= 3) {
                                throw null;
                            }
                        }
                        else {
                            blockState = Blocks.air.getDefaultState();
                        }
                        worldServerInstance.setBlockState(blockPos, blockState);
                        ++n29;
                    }
                    ++length5;
                }
                ++n28;
            }
        }
        final IBlockState defaultState = Blocks.portal.getDefaultState();
        final PropertyEnum<EnumFacing.Axis> axis = BlockPortal.AXIS;
        EnumFacing.Axis axis2;
        if (n26 != 0) {
            axis2 = EnumFacing.Axis.X;
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            axis2 = EnumFacing.Axis.Z;
        }
        final IBlockState withProperty = defaultState.withProperty((IProperty<Comparable>)axis, axis2);
        int length6 = "".length();
        "".length();
        if (0 == -1) {
            throw null;
        }
        while (length6 < (0x89 ^ 0x8D)) {
            int length7 = "".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
            while (length7 < (0x4C ^ 0x48)) {
                int n35 = -" ".length();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
                while (n35 < (0x50 ^ 0x54)) {
                    final int n36 = n24 + (length7 - " ".length()) * n26;
                    final int n37 = clamp_int + n35;
                    final int n38 = n25 + (length7 - " ".length()) * n27;
                    int n39;
                    if (length7 != 0 && length7 != "   ".length() && n35 != -" ".length() && n35 != "   ".length()) {
                        n39 = "".length();
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        n39 = " ".length();
                    }
                    final int n40 = n39;
                    final WorldServer worldServerInstance2 = this.worldServerInstance;
                    final BlockPos blockPos2 = new BlockPos(n36, n37, n38);
                    IBlockState defaultState2;
                    if (n40 != 0) {
                        defaultState2 = Blocks.obsidian.getDefaultState();
                        "".length();
                        if (3 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        defaultState2 = withProperty;
                    }
                    worldServerInstance2.setBlockState(blockPos2, defaultState2, "  ".length());
                    ++n35;
                }
                ++length7;
            }
            int length8 = "".length();
            "".length();
            if (2 == 0) {
                throw null;
            }
            while (length8 < (0x6A ^ 0x6E)) {
                int n41 = -" ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (n41 < (0x12 ^ 0x16)) {
                    final BlockPos blockPos3 = new BlockPos(n24 + (length8 - " ".length()) * n26, clamp_int + n41, n25 + (length8 - " ".length()) * n27);
                    this.worldServerInstance.notifyNeighborsOfStateChange(blockPos3, this.worldServerInstance.getBlockState(blockPos3).getBlock());
                    ++n41;
                }
                ++length8;
            }
            ++length6;
        }
        return " ".length() != 0;
    }
    
    public void removeStalePortalLocations(final long n) {
        if (n % 100L == 0L) {
            final Iterator<Long> iterator = this.destinationCoordinateKeys.iterator();
            final long n2 = n - 300L;
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Long n3 = iterator.next();
                final PortalPosition portalPosition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(n3);
                if (portalPosition == null || portalPosition.lastUpdateTime < n2) {
                    iterator.remove();
                    this.destinationCoordinateCache.remove(n3);
                }
            }
        }
    }
    
    public class PortalPosition extends BlockPos
    {
        public long lastUpdateTime;
        final Teleporter this$0;
        
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
        
        public PortalPosition(final Teleporter this$0, final BlockPos blockPos, final long lastUpdateTime) {
            this.this$0 = this$0;
            super(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            this.lastUpdateTime = lastUpdateTime;
        }
    }
}
