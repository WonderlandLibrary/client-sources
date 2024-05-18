package net.minecraft.client.renderer.chunk;

import net.minecraft.util.*;
import java.util.*;
import optfine.*;

public class VisGraph
{
    private int field_178611_f;
    private static final int field_178615_c;
    private static final int field_178616_a;
    private static final int field_178614_b;
    private static final int[] field_178613_e;
    private static final String[] I;
    private static final String __OBFID;
    private final BitSet field_178612_d;
    
    static {
        I();
        __OBFID = VisGraph.I["".length()];
        field_178616_a = (int)Math.pow(16.0, 0.0);
        field_178614_b = (int)Math.pow(16.0, 1.0);
        field_178615_c = (int)Math.pow(16.0, 2.0);
        field_178613_e = new int[572 + 261 + 157 + 362];
        "".length();
        " ".length();
        int length = "".length();
        int i = "".length();
        "".length();
        if (4 == 1) {
            throw null;
        }
        while (i < (0x1A ^ 0xA)) {
            int j = "".length();
            "".length();
            if (2 == 0) {
                throw null;
            }
            while (j < (0x42 ^ 0x52)) {
                int k = "".length();
                "".length();
                if (3 < 3) {
                    throw null;
                }
                while (k < (0xB6 ^ 0xA6)) {
                    if (i == 0 || i == (0xAF ^ 0xA0) || j == 0 || j == (0x59 ^ 0x56) || k == 0 || k == (0x6E ^ 0x61)) {
                        VisGraph.field_178613_e[length++] = getIndex(i, j, k);
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    private static int getIndex(final BlockPos blockPos) {
        return getIndex(blockPos.getX() & (0x51 ^ 0x5E), blockPos.getY() & (0x9B ^ 0x94), blockPos.getZ() & (0xBD ^ 0xB2));
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("5<\bArF@eEwF", "vpWqB");
    }
    
    private static int getIndex(final int n, final int n2, final int n3) {
        return n << "".length() | n2 << (0x2E ^ 0x26) | n3 << (0x27 ^ 0x23);
    }
    
    private void func_178610_a(final int n, final Set set) {
        final int n2 = n >> "".length() & (0xB3 ^ 0xBC);
        if (n2 == 0) {
            set.add(EnumFacing.WEST);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else if (n2 == (0xAE ^ 0xA1)) {
            set.add(EnumFacing.EAST);
        }
        final int n3 = n >> (0x34 ^ 0x3C) & (0xBF ^ 0xB0);
        if (n3 == 0) {
            set.add(EnumFacing.DOWN);
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else if (n3 == (0x8B ^ 0x84)) {
            set.add(EnumFacing.UP);
        }
        final int n4 = n >> (0xBF ^ 0xBB) & (0x8 ^ 0x7);
        if (n4 == 0) {
            set.add(EnumFacing.NORTH);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (n4 == (0x5B ^ 0x54)) {
            set.add(EnumFacing.SOUTH);
        }
    }
    
    public void func_178606_a(final BlockPos blockPos) {
        this.field_178612_d.set(getIndex(blockPos), " ".length() != 0);
        this.field_178611_f -= " ".length();
    }
    
    public VisGraph() {
        this.field_178612_d = new BitSet(99 + 2593 - 2383 + 3787);
        this.field_178611_f = 539 + 1411 + 837 + 1309;
    }
    
    public Set func_178609_b(final BlockPos blockPos) {
        return this.func_178604_a(getIndex(blockPos));
    }
    
    private Set func_178604_a(final int n) {
        final EnumSet<EnumFacing> none = EnumSet.noneOf(EnumFacing.class);
        final ArrayDeque<Integer> arrayDeque = new ArrayDeque<Integer>(254 + 95 + 12 + 23);
        arrayDeque.add(IntegerCache.valueOf(n));
        this.field_178612_d.set(n, " ".length() != 0);
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (!arrayDeque.isEmpty()) {
            final int intValue = arrayDeque.poll();
            this.func_178610_a(intValue, none);
            final EnumFacing[] values;
            final int length = (values = EnumFacing.VALUES).length;
            int i = "".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
            while (i < length) {
                final int func_178603_a = this.func_178603_a(intValue, values[i]);
                if (func_178603_a >= 0 && !this.field_178612_d.get(func_178603_a)) {
                    this.field_178612_d.set(func_178603_a, " ".length() != 0);
                    arrayDeque.add(IntegerCache.valueOf(func_178603_a));
                }
                ++i;
            }
        }
        return none;
    }
    
    private int func_178603_a(final int n, final EnumFacing enumFacing) {
        switch (VisGraph$1.field_178617_a[enumFacing.ordinal()]) {
            case 1: {
                if ((n >> (0x19 ^ 0x11) & (0x53 ^ 0x5C)) == 0x0) {
                    return -" ".length();
                }
                return n - VisGraph.field_178615_c;
            }
            case 2: {
                if ((n >> (0x21 ^ 0x29) & (0x2F ^ 0x20)) == (0x8C ^ 0x83)) {
                    return -" ".length();
                }
                return n + VisGraph.field_178615_c;
            }
            case 3: {
                if ((n >> (0x8E ^ 0x8A) & (0xB2 ^ 0xBD)) == 0x0) {
                    return -" ".length();
                }
                return n - VisGraph.field_178614_b;
            }
            case 4: {
                if ((n >> (0x57 ^ 0x53) & (0xBA ^ 0xB5)) == (0x68 ^ 0x67)) {
                    return -" ".length();
                }
                return n + VisGraph.field_178614_b;
            }
            case 5: {
                if ((n >> "".length() & (0x32 ^ 0x3D)) == 0x0) {
                    return -" ".length();
                }
                return n - VisGraph.field_178616_a;
            }
            case 6: {
                if ((n >> "".length() & (0x6E ^ 0x61)) == (0x31 ^ 0x3E)) {
                    return -" ".length();
                }
                return n + VisGraph.field_178616_a;
            }
            default: {
                return -" ".length();
            }
        }
    }
    
    public SetVisibility computeVisibility() {
        final SetVisibility setVisibility = new SetVisibility();
        if (1488 + 1694 + 202 + 712 - this.field_178611_f < 110 + 145 - 161 + 162) {
            setVisibility.setAllVisible(" ".length() != 0);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else if (this.field_178611_f == 0) {
            setVisibility.setAllVisible("".length() != 0);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            final int[] field_178613_e;
            final int length = (field_178613_e = VisGraph.field_178613_e).length;
            int i = "".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (i < length) {
                final int n = field_178613_e[i];
                if (!this.field_178612_d.get(n)) {
                    setVisibility.setManyVisible(this.func_178604_a(n));
                }
                ++i;
            }
        }
        return setVisibility;
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
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static final class VisGraph$1
    {
        private static final String[] I;
        private static final String __OBFID;
        static final int[] field_178617_a;
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("3\u000f\u0016}R@s{yVI", "pCIMb");
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
                if (-1 >= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            __OBFID = VisGraph$1.I["".length()];
            field_178617_a = new int[EnumFacing.values().length];
            try {
                VisGraph$1.field_178617_a[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (-1 == 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                VisGraph$1.field_178617_a[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                VisGraph$1.field_178617_a[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                VisGraph$1.field_178617_a[EnumFacing.SOUTH.ordinal()] = (0x5A ^ 0x5E);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                VisGraph$1.field_178617_a[EnumFacing.WEST.ordinal()] = (0x8E ^ 0x8B);
                "".length();
                if (false) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                VisGraph$1.field_178617_a[EnumFacing.EAST.ordinal()] = (0x9E ^ 0x98);
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
