package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Iterators;
import java.util.Iterator;
import com.google.common.base.Predicate;
import java.util.Random;
import com.google.common.collect.Maps;
import java.util.Map;

public enum EnumFacing implements IStringSerializable
{
    HorizonCode_Horizon_È("DOWN", 0, "DOWN", 0, 0, 1, -1, "down", Â.Â, HorizonCode_Horizon_È.Â, new Vec3i(0, -1, 0)), 
    Â("UP", 1, "UP", 1, 1, 0, -1, "up", Â.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, new Vec3i(0, 1, 0)), 
    Ý("NORTH", 2, "NORTH", 2, 2, 3, 2, "north", Â.Â, HorizonCode_Horizon_È.Ý, new Vec3i(0, 0, -1)), 
    Ø­áŒŠá("SOUTH", 3, "SOUTH", 3, 3, 2, 0, "south", Â.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Ý, new Vec3i(0, 0, 1)), 
    Âµá€("WEST", 4, "WEST", 4, 4, 5, 1, "west", Â.Â, HorizonCode_Horizon_È.HorizonCode_Horizon_È, new Vec3i(-1, 0, 0)), 
    Ó("EAST", 5, "EAST", 5, 5, 4, 3, "east", Â.HorizonCode_Horizon_È, HorizonCode_Horizon_È.HorizonCode_Horizon_È, new Vec3i(1, 0, 0));
    
    private final int Ø;
    private final int áŒŠÆ;
    private final int áˆºÑ¢Õ;
    private final String ÂµÈ;
    private final HorizonCode_Horizon_È á;
    private final Â ˆÏ­;
    private final Vec3i £á;
    public static final EnumFacing[] à;
    private static final EnumFacing[] Å;
    private static final Map £à;
    private static final String µà = "CL_00001201";
    private static final EnumFacing[] ˆà;
    
    static {
        ¥Æ = new EnumFacing[] { EnumFacing.HorizonCode_Horizon_È, EnumFacing.Â, EnumFacing.Ý, EnumFacing.Ø­áŒŠá, EnumFacing.Âµá€, EnumFacing.Ó };
        à = new EnumFacing[6];
        Å = new EnumFacing[4];
        £à = Maps.newHashMap();
        ˆà = new EnumFacing[] { EnumFacing.HorizonCode_Horizon_È, EnumFacing.Â, EnumFacing.Ý, EnumFacing.Ø­áŒŠá, EnumFacing.Âµá€, EnumFacing.Ó };
        for (final EnumFacing var4 : values()) {
            EnumFacing.à[var4.Ø] = var4;
            if (var4.á().Ø­áŒŠá()) {
                EnumFacing.Å[var4.áˆºÑ¢Õ] = var4;
            }
            EnumFacing.£à.put(var4.ÂµÈ().toLowerCase(), var4);
        }
    }
    
    private EnumFacing(final String s, final int n, final String p_i46016_1_, final int p_i46016_2_, final int indexIn, final int oppositeIn, final int horizontalIndexIn, final String nameIn, final Â axisDirectionIn, final HorizonCode_Horizon_È axisIn, final Vec3i directionVecIn) {
        this.Ø = indexIn;
        this.áˆºÑ¢Õ = horizontalIndexIn;
        this.áŒŠÆ = oppositeIn;
        this.ÂµÈ = nameIn;
        this.á = axisIn;
        this.ˆÏ­ = axisDirectionIn;
        this.£á = directionVecIn;
    }
    
    public int Â() {
        return this.Ø;
    }
    
    public int Ý() {
        return this.áˆºÑ¢Õ;
    }
    
    public Â Ø­áŒŠá() {
        return this.ˆÏ­;
    }
    
    public EnumFacing Âµá€() {
        return HorizonCode_Horizon_È(this.áŒŠÆ);
    }
    
    public EnumFacing HorizonCode_Horizon_È(final HorizonCode_Horizon_È axis) {
        switch (Ø­áŒŠá.HorizonCode_Horizon_È[axis.ordinal()]) {
            case 1: {
                if (this != EnumFacing.Âµá€ && this != EnumFacing.Ó) {
                    return this.£á();
                }
                return this;
            }
            case 2: {
                if (this != EnumFacing.Â && this != EnumFacing.HorizonCode_Horizon_È) {
                    return this.Ó();
                }
                return this;
            }
            case 3: {
                if (this != EnumFacing.Ý && this != EnumFacing.Ø­áŒŠá) {
                    return this.Å();
                }
                return this;
            }
            default: {
                throw new IllegalStateException("Unable to get CW facing for axis " + axis);
            }
        }
    }
    
    public EnumFacing Ó() {
        switch (Ø­áŒŠá.Â[this.ordinal()]) {
            case 1: {
                return EnumFacing.Ó;
            }
            case 2: {
                return EnumFacing.Ø­áŒŠá;
            }
            case 3: {
                return EnumFacing.Âµá€;
            }
            case 4: {
                return EnumFacing.Ý;
            }
            default: {
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
            }
        }
    }
    
    private EnumFacing £á() {
        switch (Ø­áŒŠá.Â[this.ordinal()]) {
            case 1: {
                return EnumFacing.HorizonCode_Horizon_È;
            }
            default: {
                throw new IllegalStateException("Unable to get X-rotated facing of " + this);
            }
            case 3: {
                return EnumFacing.Â;
            }
            case 5: {
                return EnumFacing.Ý;
            }
            case 6: {
                return EnumFacing.Ø­áŒŠá;
            }
        }
    }
    
    private EnumFacing Å() {
        switch (Ø­áŒŠá.Â[this.ordinal()]) {
            case 2: {
                return EnumFacing.HorizonCode_Horizon_È;
            }
            default: {
                throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
            }
            case 4: {
                return EnumFacing.Â;
            }
            case 5: {
                return EnumFacing.Ó;
            }
            case 6: {
                return EnumFacing.Âµá€;
            }
        }
    }
    
    public EnumFacing à() {
        switch (Ø­áŒŠá.Â[this.ordinal()]) {
            case 1: {
                return EnumFacing.Âµá€;
            }
            case 2: {
                return EnumFacing.Ý;
            }
            case 3: {
                return EnumFacing.Ó;
            }
            case 4: {
                return EnumFacing.Ø­áŒŠá;
            }
            default: {
                throw new IllegalStateException("Unable to get CCW facing of " + this);
            }
        }
    }
    
    public int Ø() {
        return (this.á == HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? this.ˆÏ­.HorizonCode_Horizon_È() : 0;
    }
    
    public int áŒŠÆ() {
        return (this.á == HorizonCode_Horizon_È.Â) ? this.ˆÏ­.HorizonCode_Horizon_È() : 0;
    }
    
    public int áˆºÑ¢Õ() {
        return (this.á == HorizonCode_Horizon_È.Ý) ? this.ˆÏ­.HorizonCode_Horizon_È() : 0;
    }
    
    public String ÂµÈ() {
        return this.ÂµÈ;
    }
    
    public HorizonCode_Horizon_È á() {
        return this.á;
    }
    
    public static EnumFacing HorizonCode_Horizon_È(final String name) {
        return (name == null) ? null : EnumFacing.£à.get(name.toLowerCase());
    }
    
    public static EnumFacing HorizonCode_Horizon_È(final int index) {
        return EnumFacing.à[MathHelper.HorizonCode_Horizon_È(index % EnumFacing.à.length)];
    }
    
    public static EnumFacing Â(final int p_176731_0_) {
        return EnumFacing.Å[MathHelper.HorizonCode_Horizon_È(p_176731_0_ % EnumFacing.Å.length)];
    }
    
    public static EnumFacing HorizonCode_Horizon_È(final double angle) {
        return Â(MathHelper.Ý(angle / 90.0 + 0.5) & 0x3);
    }
    
    public static EnumFacing HorizonCode_Horizon_È(final Random rand) {
        return values()[rand.nextInt(values().length)];
    }
    
    public static EnumFacing HorizonCode_Horizon_È(final float p_176737_0_, final float p_176737_1_, final float p_176737_2_) {
        EnumFacing var3 = EnumFacing.Ý;
        float var4 = Float.MIN_VALUE;
        for (final EnumFacing var8 : values()) {
            final float var9 = p_176737_0_ * var8.£á.HorizonCode_Horizon_È() + p_176737_1_ * var8.£á.Â() + p_176737_2_ * var8.£á.Ý();
            if (var9 > var4) {
                var4 = var9;
                var3 = var8;
            }
        }
        return var3;
    }
    
    @Override
    public String toString() {
        return this.ÂµÈ;
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return this.ÂµÈ;
    }
    
    public Vec3i ˆÏ­() {
        return this.£á;
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable, Predicate
    {
        HorizonCode_Horizon_È("X", 0, "X", 0, "X", 0, "x", EnumFacing.Ý.HorizonCode_Horizon_È), 
        Â("Y", 1, "Y", 1, "Y", 1, "y", EnumFacing.Ý.Â), 
        Ý("Z", 2, "Z", 2, "Z", 2, "z", EnumFacing.Ý.HorizonCode_Horizon_È);
        
        private static final Map Ø­áŒŠá;
        private final String Âµá€;
        private final Ý Ó;
        private static final HorizonCode_Horizon_È[] à;
        private static final String Ø = "CL_00002321";
        private static final HorizonCode_Horizon_È[] áŒŠÆ;
        
        static {
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = Maps.newHashMap();
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            áŒŠÆ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ø­áŒŠá.put(var4.Â().toLowerCase(), var4);
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i46398_1_, final int p_i46398_2_, final String p_i46015_1_, final int p_i46015_2_, final String name, final Ý plane) {
            this.Âµá€ = name;
            this.Ó = plane;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String name) {
            return (name == null) ? null : HorizonCode_Horizon_È.Ø­áŒŠá.get(name.toLowerCase());
        }
        
        public String Â() {
            return this.Âµá€;
        }
        
        public boolean Ý() {
            return this.Ó == EnumFacing.Ý.Â;
        }
        
        public boolean Ø­áŒŠá() {
            return this.Ó == EnumFacing.Ý.HorizonCode_Horizon_È;
        }
        
        @Override
        public String toString() {
            return this.Âµá€;
        }
        
        public boolean HorizonCode_Horizon_È(final EnumFacing facing) {
            return facing != null && facing.á() == this;
        }
        
        public Ý Âµá€() {
            return this.Ó;
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Âµá€;
        }
        
        public boolean apply(final Object p_apply_1_) {
            return this.HorizonCode_Horizon_È((EnumFacing)p_apply_1_);
        }
    }
    
    public enum Ý implements Predicate, Iterable
    {
        HorizonCode_Horizon_È("HORIZONTAL", 0, "HORIZONTAL", 0, "HORIZONTAL", 0), 
        Â("VERTICAL", 1, "VERTICAL", 1, "VERTICAL", 1);
        
        private static final Ý[] Ý;
        private static final String Ø­áŒŠá = "CL_00002319";
        private static final Ý[] Âµá€;
        
        static {
            Ó = new Ý[] { EnumFacing.Ý.HorizonCode_Horizon_È, EnumFacing.Ý.Â };
            Ý = new Ý[] { EnumFacing.Ý.HorizonCode_Horizon_È, EnumFacing.Ý.Â };
            Âµá€ = new Ý[] { EnumFacing.Ý.HorizonCode_Horizon_È, EnumFacing.Ý.Â };
        }
        
        private Ý(final String s, final int n, final String p_i46400_1_, final int p_i46400_2_, final String p_i46013_1_, final int p_i46013_2_) {
        }
        
        public EnumFacing[] HorizonCode_Horizon_È() {
            switch (EnumFacing.Ø­áŒŠá.Ý[this.ordinal()]) {
                case 1: {
                    return new EnumFacing[] { EnumFacing.Ý, EnumFacing.Ó, EnumFacing.Ø­áŒŠá, EnumFacing.Âµá€ };
                }
                case 2: {
                    return new EnumFacing[] { EnumFacing.Â, EnumFacing.HorizonCode_Horizon_È };
                }
                default: {
                    throw new Error("Someone's been tampering with the universe!");
                }
            }
        }
        
        public EnumFacing HorizonCode_Horizon_È(final Random rand) {
            final EnumFacing[] var2 = this.HorizonCode_Horizon_È();
            return var2[rand.nextInt(var2.length)];
        }
        
        public boolean HorizonCode_Horizon_È(final EnumFacing facing) {
            return facing != null && facing.á().Âµá€() == this;
        }
        
        public Iterator iterator() {
            return (Iterator)Iterators.forArray((Object[])this.HorizonCode_Horizon_È());
        }
        
        public boolean apply(final Object p_apply_1_) {
            return this.HorizonCode_Horizon_È((EnumFacing)p_apply_1_);
        }
    }
    
    public enum Â
    {
        HorizonCode_Horizon_È("POSITIVE", 0, "POSITIVE", 0, "POSITIVE", 0, 1, "Towards positive"), 
        Â("NEGATIVE", 1, "NEGATIVE", 1, "NEGATIVE", 1, -1, "Towards negative");
        
        private final int Ý;
        private final String Ø­áŒŠá;
        private static final Â[] Âµá€;
        private static final String Ó = "CL_00002320";
        private static final Â[] à;
        
        static {
            Ø = new Â[] { Â.HorizonCode_Horizon_È, Â.Â };
            Âµá€ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â };
            à = new Â[] { Â.HorizonCode_Horizon_È, Â.Â };
        }
        
        private Â(final String s, final int n, final String p_i46399_1_, final int p_i46399_2_, final String p_i46014_1_, final int p_i46014_2_, final int offset, final String description) {
            this.Ý = offset;
            this.Ø­áŒŠá = description;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.Ý;
        }
        
        @Override
        public String toString() {
            return this.Ø­áŒŠá;
        }
    }
    
    static final class Ø­áŒŠá
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        static final int[] Ý;
        private static final String Ø­áŒŠá = "CL_00002322";
        
        static {
            Ý = new int[EnumFacing.Ý.values().length];
            try {
                EnumFacing.Ø­áŒŠá.Ý[EnumFacing.Ý.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                EnumFacing.Ø­áŒŠá.Ý[EnumFacing.Ý.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            Â = new int[EnumFacing.values().length];
            try {
                EnumFacing.Ø­áŒŠá.Â[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                EnumFacing.Ø­áŒŠá.Â[EnumFacing.Ó.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                EnumFacing.Ø­áŒŠá.Â[EnumFacing.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                EnumFacing.Ø­áŒŠá.Â[EnumFacing.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                EnumFacing.Ø­áŒŠá.Â[EnumFacing.Â.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                EnumFacing.Ø­áŒŠá.Â[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            HorizonCode_Horizon_È = new int[EnumFacing.HorizonCode_Horizon_È.values().length];
            try {
                EnumFacing.Ø­áŒŠá.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                EnumFacing.Ø­áŒŠá.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                EnumFacing.Ø­áŒŠá.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
        }
    }
}
