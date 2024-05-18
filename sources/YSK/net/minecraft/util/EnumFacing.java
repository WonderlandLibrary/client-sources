package net.minecraft.util;

import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;

public enum EnumFacing implements IStringSerializable
{
    private static final EnumFacing[] $VALUES;
    private final int opposite;
    
    WEST(EnumFacing.I[0x57 ^ 0x5A], 0x79 ^ 0x7D, EnumFacing.I[0x2 ^ 0xC], 0x64 ^ 0x60, 0x9D ^ 0x99, 0xB0 ^ 0xB5, " ".length(), EnumFacing.I[0x63 ^ 0x6C], AxisDirection.NEGATIVE, Axis.X, new Vec3i(-" ".length(), "".length(), "".length())), 
    NORTH(EnumFacing.I[0x51 ^ 0x56], "  ".length(), EnumFacing.I[0x1 ^ 0x9], "  ".length(), "  ".length(), "   ".length(), "  ".length(), EnumFacing.I[0xAA ^ 0xA3], AxisDirection.NEGATIVE, Axis.Z, new Vec3i("".length(), "".length(), -" ".length()));
    
    private static final String __OBFID;
    private final int index;
    private static final String[] I;
    private final AxisDirection axisDirection;
    
    DOWN(EnumFacing.I[" ".length()], "".length(), EnumFacing.I["  ".length()], "".length(), "".length(), " ".length(), -" ".length(), EnumFacing.I["   ".length()], AxisDirection.NEGATIVE, Axis.Y, new Vec3i("".length(), -" ".length(), "".length()));
    
    private static final EnumFacing[] ENUM$VALUES;
    private static final Map NAME_LOOKUP;
    private final Vec3i directionVec;
    public static final EnumFacing[] VALUES;
    private final int horizontalIndex;
    private final Axis axis;
    private static final EnumFacing[] HORIZONTALS;
    private final String name;
    
    EAST(EnumFacing.I[0x53 ^ 0x43], 0x38 ^ 0x3D, EnumFacing.I[0x8A ^ 0x9B], 0x5E ^ 0x5B, 0x44 ^ 0x41, 0x2F ^ 0x2B, "   ".length(), EnumFacing.I[0x7D ^ 0x6F], AxisDirection.POSITIVE, Axis.X, new Vec3i(" ".length(), "".length(), "".length())), 
    UP(EnumFacing.I[0xAC ^ 0xA8], " ".length(), EnumFacing.I[0x40 ^ 0x45], " ".length(), " ".length(), "".length(), -" ".length(), EnumFacing.I[0x22 ^ 0x24], AxisDirection.POSITIVE, Axis.Y, new Vec3i("".length(), " ".length(), "".length())), 
    SOUTH(EnumFacing.I[0x95 ^ 0x9F], "   ".length(), EnumFacing.I[0x80 ^ 0x8B], "   ".length(), "   ".length(), "  ".length(), "".length(), EnumFacing.I[0xBA ^ 0xB6], AxisDirection.POSITIVE, Axis.Z, new Vec3i("".length(), "".length(), " ".length()));
    
    public int getIndex() {
        return this.index;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public static EnumFacing byName(final String s) {
        EnumFacing enumFacing;
        if (s == null) {
            enumFacing = null;
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            enumFacing = EnumFacing.NAME_LOOKUP.get(s.toLowerCase());
        }
        return enumFacing;
    }
    
    public static EnumFacing getHorizontal(final int n) {
        return EnumFacing.HORIZONTALS[MathHelper.abs_int(n % EnumFacing.HORIZONTALS.length)];
    }
    
    public int getFrontOffsetX() {
        int n;
        if (this.axis == Axis.X) {
            n = this.axisDirection.getOffset();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    public AxisDirection getAxisDirection() {
        return this.axisDirection;
    }
    
    public EnumFacing rotateY() {
        switch (EnumFacing$1.field_179513_b[this.ordinal()]) {
            case 1: {
                return EnumFacing.EAST;
            }
            case 2: {
                return EnumFacing.SOUTH;
            }
            case 3: {
                return EnumFacing.WEST;
            }
            case 4: {
                return EnumFacing.NORTH;
            }
            default: {
                throw new IllegalStateException(EnumFacing.I[0x90 ^ 0x84] + this);
            }
        }
    }
    
    public int getFrontOffsetZ() {
        int n;
        if (this.axis == Axis.Z) {
            n = this.axisDirection.getOffset();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    public int getHorizontalIndex() {
        return this.horizontalIndex;
    }
    
    public Axis getAxis() {
        return this.axis;
    }
    
    private EnumFacing rotateZ() {
        switch (EnumFacing$1.field_179513_b[this.ordinal()]) {
            case 2: {
                return EnumFacing.DOWN;
            }
            default: {
                throw new IllegalStateException(EnumFacing.I[0x2 ^ 0x14] + this);
            }
            case 4: {
                return EnumFacing.UP;
            }
            case 5: {
                return EnumFacing.EAST;
            }
            case 6: {
                return EnumFacing.WEST;
            }
        }
    }
    
    private EnumFacing(final String s, final int n, final String s2, final int n2, final int index, final int opposite, final int horizontalIndex, final String name, final AxisDirection axisDirection, final Axis axis, final Vec3i directionVec) {
        this.index = index;
        this.horizontalIndex = horizontalIndex;
        this.opposite = opposite;
        this.name = name;
        this.axis = axis;
        this.axisDirection = axisDirection;
        this.directionVec = directionVec;
    }
    
    public String getName2() {
        return this.name;
    }
    
    public static EnumFacing random(final Random random) {
        return values()[random.nextInt(values().length)];
    }
    
    private EnumFacing rotateX() {
        switch (EnumFacing$1.field_179513_b[this.ordinal()]) {
            case 1: {
                return EnumFacing.DOWN;
            }
            default: {
                throw new IllegalStateException(EnumFacing.I[0x1 ^ 0x14] + this);
            }
            case 3: {
                return EnumFacing.UP;
            }
            case 5: {
                return EnumFacing.NORTH;
            }
            case 6: {
                return EnumFacing.SOUTH;
            }
        }
    }
    
    public static EnumFacing getFront(final int n) {
        return EnumFacing.VALUES[MathHelper.abs_int(n % EnumFacing.VALUES.length)];
    }
    
    public EnumFacing getOpposite() {
        return getFront(this.opposite);
    }
    
    public Vec3i getDirectionVec() {
        return this.directionVec;
    }
    
    public static EnumFacing getFacingFromVector(final float n, final float n2, final float n3) {
        EnumFacing north = EnumFacing.NORTH;
        float n4 = Float.MIN_VALUE;
        final EnumFacing[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (i < length) {
            final EnumFacing enumFacing = values[i];
            final float n5 = n * enumFacing.directionVec.getX() + n2 * enumFacing.directionVec.getY() + n3 * enumFacing.directionVec.getZ();
            if (n5 > n4) {
                n4 = n5;
                north = enumFacing;
            }
            ++i;
        }
        return north;
    }
    
    public int getFrontOffsetY() {
        int n;
        if (this.axis == Axis.Y) {
            n = this.axisDirection.getOffset();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    public EnumFacing rotateYCCW() {
        switch (EnumFacing$1.field_179513_b[this.ordinal()]) {
            case 1: {
                return EnumFacing.WEST;
            }
            case 2: {
                return EnumFacing.NORTH;
            }
            case 3: {
                return EnumFacing.EAST;
            }
            case 4: {
                return EnumFacing.SOUTH;
            }
            default: {
                throw new IllegalStateException(EnumFacing.I[0xD4 ^ 0xC3] + this);
            }
        }
    }
    
    private static void I() {
        (I = new String[0xA6 ^ 0xBC])["".length()] = I("\u0015;6\\efGX^eg", "VwilU");
        EnumFacing.I[" ".length()] = I("\u0005\u0004\u0016\u0016", "AKAXw");
        EnumFacing.I["  ".length()] = I("/\u0003='", "kLjif");
        EnumFacing.I["   ".length()] = I("\r$1\r", "iKFcp");
        EnumFacing.I[0x56 ^ 0x52] = I("\u0007\u001e", "RNoEw");
        EnumFacing.I[0x7D ^ 0x78] = I("6(", "cxBYg");
        EnumFacing.I[0x5E ^ 0x58] = I("\"\"", "WRMdg");
        EnumFacing.I[0x94 ^ 0x93] = I("\n\u0005% \u001f", "DJwtW");
        EnumFacing.I[0x1A ^ 0x12] = I("\f\u001b%\u001d:", "BTwIr");
        EnumFacing.I[0x63 ^ 0x6A] = I(">%0\u0019,", "PJBmD");
        EnumFacing.I[0x67 ^ 0x6D] = I("%\u00042\u001c;", "vKgHs");
        EnumFacing.I[0x66 ^ 0x6D] = I("$\u001d\u001b\u0016,", "wRNBd");
        EnumFacing.I[0x35 ^ 0x39] = I("\u0003>!#\u0019", "pQTWq");
        EnumFacing.I[0x9A ^ 0x97] = I("\u0013\u0012$:", "DWwnx");
        EnumFacing.I[0x3B ^ 0x35] = I("\"\b9\u0017", "uMjCg");
        EnumFacing.I[0x2C ^ 0x23] = I("\u000727\u001a", "pWDnk");
        EnumFacing.I[0xAE ^ 0xBE] = I("#\u00188\r", "fYkYy");
        EnumFacing.I[0x9F ^ 0x8E] = I("73\u0018\u001a", "rrKNZ");
        EnumFacing.I[0x47 ^ 0x55] = I("5,\u0006-", "PMuYq");
        EnumFacing.I[0x68 ^ 0x7B] = I("%\r\n*\u0003\u0015C\u001f'O\u0017\u0006\u001fh,'C\r)\f\u0019\r\fh\t\u001f\u0011K)\u0017\u0019\u0010K", "pckHo");
        EnumFacing.I[0xB ^ 0x1F] = I("?=\u0015%>\u000fs\u0000(r\r6\u0000g\u000bG!\u001b33\u001e6\u0010g4\u000b0\u001d)5J<\u0012g", "jStGR");
        EnumFacing.I[0x1F ^ 0xA] = I("345#-\u0003z .a\u0001? a\u0019K(;5 \u0012?0a'\u00079=/&F52a", "fZTAA");
        EnumFacing.I[0x70 ^ 0x66] = I("\u0001\u001a\u0018*\u00011T\r'M3\u0011\rh7y\u0006\u0016<\f \u0011\u001dh\u000b5\u0017\u0010&\nt\u001b\u001fh", "TtyHm");
        EnumFacing.I[0x5C ^ 0x4B] = I("\f\u001a&)\u0014<T3$X>\u00113k;\u001a#g-\u0019:\u001d),X6\u0012g", "YtGKx");
        EnumFacing.I[0x47 ^ 0x5F] = I(" \u0001a$;\r\u0006a3'\u001c\u000b\"#'\u0001\u0000{w", "nnAWN");
        EnumFacing.I[0x8B ^ 0x92] = I("Z", "zBwUh");
    }
    
    public static EnumFacing fromAngle(final double n) {
        return getHorizontal(MathHelper.floor_double(n / 90.0 + 0.5) & "   ".length());
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    static {
        I();
        __OBFID = EnumFacing.I["".length()];
        final EnumFacing[] enum$VALUES = new EnumFacing[0xB2 ^ 0xB4];
        enum$VALUES["".length()] = EnumFacing.DOWN;
        enum$VALUES[" ".length()] = EnumFacing.UP;
        enum$VALUES["  ".length()] = EnumFacing.NORTH;
        enum$VALUES["   ".length()] = EnumFacing.SOUTH;
        enum$VALUES[0x2D ^ 0x29] = EnumFacing.WEST;
        enum$VALUES[0x86 ^ 0x83] = EnumFacing.EAST;
        ENUM$VALUES = enum$VALUES;
        VALUES = new EnumFacing[0xC4 ^ 0xC2];
        HORIZONTALS = new EnumFacing[0x56 ^ 0x52];
        NAME_LOOKUP = Maps.newHashMap();
        final EnumFacing[] $values = new EnumFacing[0x9A ^ 0x9C];
        $values["".length()] = EnumFacing.DOWN;
        $values[" ".length()] = EnumFacing.UP;
        $values["  ".length()] = EnumFacing.NORTH;
        $values["   ".length()] = EnumFacing.SOUTH;
        $values[0x14 ^ 0x10] = EnumFacing.WEST;
        $values[0xA2 ^ 0xA7] = EnumFacing.EAST;
        $VALUES = $values;
        final EnumFacing[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < length) {
            final EnumFacing enumFacing = values[i];
            EnumFacing.VALUES[enumFacing.index] = enumFacing;
            if (enumFacing.getAxis().isHorizontal()) {
                EnumFacing.HORIZONTALS[enumFacing.horizontalIndex] = enumFacing;
            }
            EnumFacing.NAME_LOOKUP.put(enumFacing.getName2().toLowerCase(), enumFacing);
            ++i;
        }
    }
    
    public static EnumFacing func_181076_a(final AxisDirection axisDirection, final Axis axis) {
        final EnumFacing[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i < length) {
            final EnumFacing enumFacing = values[i];
            if (enumFacing.getAxisDirection() == axisDirection && enumFacing.getAxis() == axis) {
                return enumFacing;
            }
            ++i;
        }
        throw new IllegalArgumentException(EnumFacing.I[0x88 ^ 0x90] + axisDirection + EnumFacing.I[0x21 ^ 0x38] + axis);
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
    
    public EnumFacing rotateAround(final Axis axis) {
        switch (EnumFacing$1.field_179515_a[axis.ordinal()]) {
            case 1: {
                if (this != EnumFacing.WEST && this != EnumFacing.EAST) {
                    return this.rotateX();
                }
                return this;
            }
            case 2: {
                if (this != EnumFacing.UP && this != EnumFacing.DOWN) {
                    return this.rotateY();
                }
                return this;
            }
            case 3: {
                if (this != EnumFacing.NORTH && this != EnumFacing.SOUTH) {
                    return this.rotateZ();
                }
                return this;
            }
            default: {
                throw new IllegalStateException(EnumFacing.I[0x29 ^ 0x3A] + axis);
            }
        }
    }
    
    static final class EnumFacing$1
    {
        static final int[] field_179514_c;
        static final int[] field_179513_b;
        static final int[] field_179515_a;
        private static final String __OBFID;
        private static final String[] I;
        
        static {
            I();
            __OBFID = EnumFacing$1.I["".length()];
            field_179514_c = new int[Plane.values().length];
            try {
                EnumFacing$1.field_179514_c[Plane.HORIZONTAL.ordinal()] = " ".length();
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                EnumFacing$1.field_179514_c[Plane.VERTICAL.ordinal()] = "  ".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            field_179513_b = new int[EnumFacing.values().length];
            try {
                EnumFacing$1.field_179513_b[EnumFacing.NORTH.ordinal()] = " ".length();
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                EnumFacing$1.field_179513_b[EnumFacing.EAST.ordinal()] = "  ".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                EnumFacing$1.field_179513_b[EnumFacing.SOUTH.ordinal()] = "   ".length();
                "".length();
                if (1 == 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                EnumFacing$1.field_179513_b[EnumFacing.WEST.ordinal()] = (0x74 ^ 0x70);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                EnumFacing$1.field_179513_b[EnumFacing.UP.ordinal()] = (0x4D ^ 0x48);
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                EnumFacing$1.field_179513_b[EnumFacing.DOWN.ordinal()] = (0xF ^ 0x9);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            field_179515_a = new int[Axis.values().length];
            try {
                EnumFacing$1.field_179515_a[Axis.X.ordinal()] = " ".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                EnumFacing$1.field_179515_a[Axis.Y.ordinal()] = "  ".length();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                EnumFacing$1.field_179515_a[Axis.Z.ordinal()] = "   ".length();
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
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
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\f=<D^\u007fAQG\\}", "Oqctn");
        }
    }
    
    public enum Plane implements Predicate, Iterable
    {
        HORIZONTAL(Plane.I[" ".length()], "".length(), Plane.I["  ".length()], "".length());
        
        private static final Plane[] $VALUES;
        private static final Plane[] ENUM$VALUES;
        private static final String[] I;
        
        VERTICAL(Plane.I["   ".length()], " ".length(), Plane.I[0x8E ^ 0x8A], " ".length());
        
        private static final String __OBFID;
        
        public boolean apply(final EnumFacing enumFacing) {
            if (enumFacing != null && enumFacing.getAxis().getPlane() == this) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public Iterator iterator() {
            return (Iterator)Iterators.forArray((Object[])this.facings());
        }
        
        private static void I() {
            (I = new String[0xC2 ^ 0xC4])["".length()] = I("\u0012\u0004+qDaxFrEh", "QHtAt");
            Plane.I[" ".length()] = I("\u0012\r;\b\u0011\u0015\f=\u0000\u0007", "ZBiAK");
            Plane.I["  ".length()] = I("\u001b\t8\u00188\u001c\b>\u0010.", "SFjQb");
            Plane.I["   ".length()] = I("=03\u00121(4-", "kuaFx");
            Plane.I[0x8 ^ 0xC] = I(".(\u00031.;,\u001d", "xmQeg");
            Plane.I[0x94 ^ 0x91] = I("6\u0001)++\u000b\u000bc=d\u0007\u000b! d\u0011\u000f)>!\u0017\u0007*)d\u0012\u00070&d\u0011\u0006!n1\u000b\u00072+6\u0016\u000be", "enDND");
        }
        
        public EnumFacing random(final Random random) {
            final EnumFacing[] facings = this.facings();
            return facings[random.nextInt(facings.length)];
        }
        
        public boolean apply(final Object o) {
            return this.apply((EnumFacing)o);
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
        
        static {
            I();
            __OBFID = Plane.I["".length()];
            final Plane[] enum$VALUES = new Plane["  ".length()];
            enum$VALUES["".length()] = Plane.HORIZONTAL;
            enum$VALUES[" ".length()] = Plane.VERTICAL;
            ENUM$VALUES = enum$VALUES;
            final Plane[] $values = new Plane["  ".length()];
            $values["".length()] = Plane.HORIZONTAL;
            $values[" ".length()] = Plane.VERTICAL;
            $VALUES = $values;
        }
        
        public EnumFacing[] facings() {
            switch (EnumFacing$1.field_179514_c[this.ordinal()]) {
                case 1: {
                    final EnumFacing[] array = new EnumFacing[0x3D ^ 0x39];
                    array["".length()] = EnumFacing.NORTH;
                    array[" ".length()] = EnumFacing.EAST;
                    array["  ".length()] = EnumFacing.SOUTH;
                    array["   ".length()] = EnumFacing.WEST;
                    return array;
                }
                case 2: {
                    final EnumFacing[] array2 = new EnumFacing["  ".length()];
                    array2["".length()] = EnumFacing.UP;
                    array2[" ".length()] = EnumFacing.DOWN;
                    return array2;
                }
                default: {
                    throw new Error(Plane.I[0x55 ^ 0x50]);
                }
            }
        }
        
        private Plane(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    public enum Axis implements Predicate, IStringSerializable
    {
        private static final Axis[] ENUM$VALUES;
        
        Z(Axis.I[0x5D ^ 0x5A], "  ".length(), Axis.I[0xBA ^ 0xB2], "  ".length(), Axis.I[0xB3 ^ 0xBA], Plane.HORIZONTAL);
        
        private static final String __OBFID;
        
        Y(Axis.I[0x5E ^ 0x5A], " ".length(), Axis.I[0x2B ^ 0x2E], " ".length(), Axis.I[0x3B ^ 0x3D], Plane.VERTICAL), 
        X(Axis.I[" ".length()], "".length(), Axis.I["  ".length()], "".length(), Axis.I["   ".length()], Plane.HORIZONTAL);
        
        private static final Map NAME_LOOKUP;
        private static final Axis[] $VALUES;
        private final Plane plane;
        private static final String[] I;
        private final String name;
        
        public boolean apply(final EnumFacing enumFacing) {
            if (enumFacing != null && enumFacing.getAxis() == this) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private static void I() {
            (I = new String[0x49 ^ 0x43])["".length()] = I("\u0005\u0004=DXvxPGZw", "FHbth");
            Axis.I[" ".length()] = I("\u0002", "ZuydW");
            Axis.I["  ".length()] = I(" ", "xHRia");
            Axis.I["   ".length()] = I(",", "TTTOc");
            Axis.I[0x1A ^ 0x1E] = I("4", "mDZlr");
            Axis.I[0x35 ^ 0x30] = I("\u001b", "BFMML");
            Axis.I[0x20 ^ 0x26] = I("\u0015", "ljqCe");
            Axis.I[0x92 ^ 0x95] = I("\u0014", "Njptu");
            Axis.I[0x2A ^ 0x22] = I("\u000f", "URivC");
            Axis.I[0x77 ^ 0x7E] = I("5", "OyYcY");
        }
        
        public String getName2() {
            return this.name;
        }
        
        public boolean isHorizontal() {
            if (this.plane == Plane.HORIZONTAL) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public static Axis byName(final String s) {
            Axis axis;
            if (s == null) {
                axis = null;
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                axis = Axis.NAME_LOOKUP.get(s.toLowerCase());
            }
            return axis;
        }
        
        public boolean apply(final Object o) {
            return this.apply((EnumFacing)o);
        }
        
        public boolean isVertical() {
            if (this.plane == Plane.VERTICAL) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        private Axis(final String s, final int n, final String s2, final int n2, final String name, final Plane plane) {
            this.name = name;
            this.plane = plane;
        }
        
        public Plane getPlane() {
            return this.plane;
        }
        
        static {
            I();
            __OBFID = Axis.I["".length()];
            final Axis[] enum$VALUES = new Axis["   ".length()];
            enum$VALUES["".length()] = Axis.X;
            enum$VALUES[" ".length()] = Axis.Y;
            enum$VALUES["  ".length()] = Axis.Z;
            ENUM$VALUES = enum$VALUES;
            NAME_LOOKUP = Maps.newHashMap();
            final Axis[] $values = new Axis["   ".length()];
            $values["".length()] = Axis.X;
            $values[" ".length()] = Axis.Y;
            $values["  ".length()] = Axis.Z;
            $VALUES = $values;
            final Axis[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
            while (i < length) {
                final Axis axis = values[i];
                Axis.NAME_LOOKUP.put(axis.getName2().toLowerCase(), axis);
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
                if (1 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public enum AxisDirection
    {
        private static final String[] I;
        
        POSITIVE(AxisDirection.I[" ".length()], "".length(), AxisDirection.I["  ".length()], "".length(), " ".length(), AxisDirection.I["   ".length()]);
        
        private final String description;
        
        NEGATIVE(AxisDirection.I[0x16 ^ 0x12], " ".length(), AxisDirection.I[0x23 ^ 0x26], " ".length(), -" ".length(), AxisDirection.I[0xBB ^ 0xBD]);
        
        private static final String __OBFID;
        private static final AxisDirection[] $VALUES;
        private final int offset;
        private static final AxisDirection[] ENUM$VALUES;
        
        private static void I() {
            (I = new String[0x64 ^ 0x63])["".length()] = I("\u0006;\u0005DxuGhGzu", "EwZtH");
            AxisDirection.I[" ".length()] = I("2,\"*\u0005+54", "bcqcQ");
            AxisDirection.I["  ".length()] = I("37\u0000\u001e>*.\u0016", "cxSWj");
            AxisDirection.I["   ".length()] = I("\u000e\u0007\u0016\u000b=>\u001bA\u001a )\u0001\u0015\u00039?", "ZhajO");
            AxisDirection.I[0x7E ^ 0x7A] = I("7\n\u0015\u0013,0\u0019\u0017", "yORRx");
            AxisDirection.I[0x8E ^ 0x8B] = I("\u000f\n\u000e-\u0017\b\u0019\f", "AOIlC");
            AxisDirection.I[0xB2 ^ 0xB4] = I("\u001c\u0001%\"?,\u001dr-(/\u000f&*;-", "HnRCM");
        }
        
        @Override
        public String toString() {
            return this.description;
        }
        
        static {
            I();
            __OBFID = AxisDirection.I["".length()];
            final AxisDirection[] enum$VALUES = new AxisDirection["  ".length()];
            enum$VALUES["".length()] = AxisDirection.POSITIVE;
            enum$VALUES[" ".length()] = AxisDirection.NEGATIVE;
            ENUM$VALUES = enum$VALUES;
            final AxisDirection[] $values = new AxisDirection["  ".length()];
            $values["".length()] = AxisDirection.POSITIVE;
            $values[" ".length()] = AxisDirection.NEGATIVE;
            $VALUES = $values;
        }
        
        private AxisDirection(final String s, final int n, final String s2, final int n2, final int offset, final String description) {
            this.offset = offset;
            this.description = description;
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
                if (-1 == 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public int getOffset() {
            return this.offset;
        }
    }
}
