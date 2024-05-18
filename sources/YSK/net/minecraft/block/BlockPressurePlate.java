package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.material.*;

public class BlockPressurePlate extends BlockBasePressurePlate
{
    private final Sensitivity sensitivity;
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity;
    public static final PropertyBool POWERED;
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockPressurePlate.POWERED;
        return new BlockState(this, array);
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
            if (-1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected IBlockState setRedstoneStrength(final IBlockState blockState, final int n) {
        final PropertyBool powered = BlockPressurePlate.POWERED;
        int n2;
        if (n > 0) {
            n2 = " ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return blockState.withProperty((IProperty<Comparable>)powered, n2 != 0);
    }
    
    @Override
    protected int getRedstoneStrength(final IBlockState blockState) {
        int length;
        if (blockState.getValue((IProperty<Boolean>)BlockPressurePlate.POWERED)) {
            length = (0x2B ^ 0x24);
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    @Override
    protected int computeRedstoneStrength(final World world, final BlockPos blockPos) {
        final AxisAlignedBB sensitiveAABB = this.getSensitiveAABB(blockPos);
        List<Entity> list = null;
        switch ($SWITCH_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity()[this.sensitivity.ordinal()]) {
            case 1: {
                list = world.getEntitiesWithinAABBExcludingEntity(null, sensitiveAABB);
                "".length();
                if (3 < 0) {
                    throw null;
                }
                break;
            }
            case 2: {
                list = world.getEntitiesWithinAABB((Class<? extends Entity>)EntityLivingBase.class, sensitiveAABB);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                break;
            }
            default: {
                return "".length();
            }
        }
        if (!list.isEmpty()) {
            final Iterator<Entity> iterator = list.iterator();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                if (!iterator.next().doesEntityNotTriggerPressurePlate()) {
                    return 0x97 ^ 0x98;
                }
            }
        }
        return "".length();
    }
    
    protected BlockPressurePlate(final Material material, final Sensitivity sensitivity) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockPressurePlate.POWERED, (boolean)("".length() != 0)));
        this.sensitivity = sensitivity;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity() {
        final int[] $switch_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity = BlockPressurePlate.$SWITCH_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity;
        if ($switch_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity != null) {
            return $switch_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity2 = new int[Sensitivity.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity2[Sensitivity.EVERYTHING.ordinal()] = " ".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity2[Sensitivity.MOBS.ordinal()] = "  ".length();
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        return BlockPressurePlate.$SWITCH_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity = $switch_TABLE$net$minecraft$block$BlockPressurePlate$Sensitivity2;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        final PropertyBool powered = BlockPressurePlate.POWERED;
        int n2;
        if (n == " ".length()) {
            n2 = " ".length();
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return defaultState.withProperty((IProperty<Comparable>)powered, n2 != 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n;
        if (blockState.getValue((IProperty<Boolean>)BlockPressurePlate.POWERED)) {
            n = " ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    static {
        I();
        POWERED = PropertyBool.create(BlockPressurePlate.I["".length()]);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0017!\u001d\r6\u0002*", "gNjhD");
    }
    
    public enum Sensitivity
    {
        private static final String[] I;
        private static final Sensitivity[] ENUM$VALUES;
        
        EVERYTHING(Sensitivity.I["".length()], "".length()), 
        MOBS(Sensitivity.I[" ".length()], " ".length());
        
        static {
            I();
            final Sensitivity[] enum$VALUES = new Sensitivity["  ".length()];
            enum$VALUES["".length()] = Sensitivity.EVERYTHING;
            enum$VALUES[" ".length()] = Sensitivity.MOBS;
            ENUM$VALUES = enum$VALUES;
        }
        
        private Sensitivity(final String s, final int n) {
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
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u0007:\u0013=)\u0016$\u001f!7", "BlVop");
            Sensitivity.I[" ".length()] = I("\u001c)#\u001c", "QfaOC");
        }
    }
}
