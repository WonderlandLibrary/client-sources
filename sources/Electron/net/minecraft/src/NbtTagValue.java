package net.minecraft.src;

import net.minecraft.nbt.*;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Arrays;
import java.util.regex.Pattern;

public class NbtTagValue {
    private String[] parents = null;
    private String name = null;
    private boolean negative = false;
    private int type = 0;
    private String value = null;
    private int valueFormat = 0;
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_PATTERN = 1;
    private static final int TYPE_IPATTERN = 2;
    private static final int TYPE_REGEX = 3;
    private static final int TYPE_IREGEX = 4;
    private static final String PREFIX_PATTERN = "pattern:";
    private static final String PREFIX_IPATTERN = "ipattern:";
    private static final String PREFIX_REGEX = "regex:";
    private static final String PREFIX_IREGEX = "iregex:";
    private static final int FORMAT_DEFAULT = 0;
    private static final int FORMAT_HEX_COLOR = 1;
    private static final String PREFIX_HEX_COLOR = "#";
    private static final Pattern PATTERN_HEX_COLOR = Pattern.compile("^#[0-9a-f]{6}+$");

    public NbtTagValue(String p_i78_1_, String p_i78_2_) {
        String[] astring = Config.tokenize(p_i78_1_, ".");
        this.parents = Arrays.copyOfRange(astring, 0, astring.length - 1);
        this.name = astring[astring.length - 1];

        if (p_i78_2_.startsWith("!")) {
            this.negative = true;
            p_i78_2_ = p_i78_2_.substring(1);
        }

        if (p_i78_2_.startsWith("pattern:")) {
            this.type = 1;
            p_i78_2_ = p_i78_2_.substring("pattern:".length());
        } else if (p_i78_2_.startsWith("ipattern:")) {
            this.type = 2;
            p_i78_2_ = p_i78_2_.substring("ipattern:".length()).toLowerCase();
        } else if (p_i78_2_.startsWith("regex:")) {
            this.type = 3;
            p_i78_2_ = p_i78_2_.substring("regex:".length());
        } else if (p_i78_2_.startsWith("iregex:")) {
            this.type = 4;
            p_i78_2_ = p_i78_2_.substring("iregex:".length()).toLowerCase();
        } else {
            this.type = 0;
        }

        p_i78_2_ = StringEscapeUtils.unescapeJava(p_i78_2_);

        if (this.type == 0 && PATTERN_HEX_COLOR.matcher(p_i78_2_).matches()) {
            this.valueFormat = 1;
        }

        this.value = p_i78_2_;
    }

    public boolean matches(NBTTagCompound p_matches_1_) {
        return this.negative != this.matchesCompound(p_matches_1_);
    }

    public boolean matchesCompound(NBTTagCompound p_matchesCompound_1_) {
        if (p_matchesCompound_1_ == null) {
            return false;
        } else {
            NBTBase nbtbase = p_matchesCompound_1_;

            for (int i = 0; i < this.parents.length; ++i) {
                String s = this.parents[i];
                nbtbase = getChildTag(nbtbase, s);

                if (nbtbase == null) {
                    return false;
                }
            }

            if (this.name.equals("*")) {
                return this.matchesAnyChild(nbtbase);
            } else {
                nbtbase = getChildTag(nbtbase, this.name);

                if (nbtbase == null) {
                    return false;
                } else return this.matchesBase(nbtbase);
            }
        }
    }

    private boolean matchesAnyChild(NBTBase p_matchesAnyChild_1_) {
        if (p_matchesAnyChild_1_ instanceof NBTTagCompound nbttagcompound) {

            for (String s : nbttagcompound.getKeySet()) {
                NBTBase nbtbase = nbttagcompound.getTag(s);

                if (this.matchesBase(nbtbase)) {
                    return true;
                }
            }
        }

        if (p_matchesAnyChild_1_ instanceof NBTTagList nbttaglist) {
            int i = nbttaglist.tagCount();

            for (int j = 0; j < i; ++j) {
                NBTBase nbtbase1 = nbttaglist.get(j);

                if (this.matchesBase(nbtbase1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static NBTBase getChildTag(NBTBase p_getChildTag_0_, String p_getChildTag_1_) {
        if (p_getChildTag_0_ instanceof NBTTagCompound nbttagcompound) {
            return nbttagcompound.getTag(p_getChildTag_1_);
        } else if (p_getChildTag_0_ instanceof NBTTagList nbttaglist) {

            if (p_getChildTag_1_.equals("count")) {
                return new NBTTagInt(nbttaglist.tagCount());
            } else {
                int i = Config.parseInt(p_getChildTag_1_, -1);
                return i < 0 ? null : nbttaglist.get(i);
            }
        } else {
            return null;
        }
    }

    public boolean matchesBase(NBTBase p_matchesBase_1_) {
        if (p_matchesBase_1_ == null) {
            return false;
        } else {
            String s = getNbtString(p_matchesBase_1_, this.valueFormat);
            return this.matchesValue(s);
        }
    }

    public boolean matchesValue(String p_matchesValue_1_) {
        if (p_matchesValue_1_ == null) {
            return false;
        } else {
            switch (this.type) {
                case 0:
                    return p_matchesValue_1_.equals(this.value);

                case 1:
                    return this.matchesPattern(p_matchesValue_1_, this.value);

                case 2:
                    return this.matchesPattern(p_matchesValue_1_.toLowerCase(), this.value);

                case 3:
                    return this.matchesRegex(p_matchesValue_1_, this.value);

                case 4:
                    return this.matchesRegex(p_matchesValue_1_.toLowerCase(), this.value);

                default:
                    throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
            }
        }
    }

    private boolean matchesPattern(String p_matchesPattern_1_, String p_matchesPattern_2_) {
        return StrUtils.equalsMask(p_matchesPattern_1_, p_matchesPattern_2_, '*', '?');
    }

    private boolean matchesRegex(String p_matchesRegex_1_, String p_matchesRegex_2_) {
        return p_matchesRegex_1_.matches(p_matchesRegex_2_);
    }

    private static String getNbtString(NBTBase p_getNbtString_0_, int p_getNbtString_1_) {
        if (p_getNbtString_0_ == null) {
            return null;
        } else if (p_getNbtString_0_ instanceof NBTTagString nbttagstring) {
            return nbttagstring.getString();
        } else if (p_getNbtString_0_ instanceof NBTTagInt nbttagint) {
            return p_getNbtString_1_ == 1 ? "#" + StrUtils.fillLeft(Integer.toHexString(nbttagint.getInt()), 6, '0') : Integer.toString(nbttagint.getInt());
        } else if (p_getNbtString_0_ instanceof NBTTagByte nbttagbyte) {
            return Byte.toString(nbttagbyte.getByte());
        } else if (p_getNbtString_0_ instanceof NBTTagShort nbttagshort) {
            return Short.toString(nbttagshort.getShort());
        } else if (p_getNbtString_0_ instanceof NBTTagLong nbttaglong) {
            return Long.toString(nbttaglong.getLong());
        } else if (p_getNbtString_0_ instanceof NBTTagFloat nbttagfloat) {
            return Float.toString(nbttagfloat.getFloat());
        } else if (p_getNbtString_0_ instanceof NBTTagDouble nbttagdouble) {
            return Double.toString(nbttagdouble.getDouble());
        } else {
            return p_getNbtString_0_.toString();
        }
    }

    public String toString() {
        StringBuffer stringbuffer = new StringBuffer();

        for (int i = 0; i < this.parents.length; ++i) {
            String s = this.parents[i];

            if (i > 0) {
                stringbuffer.append(".");
            }

            stringbuffer.append(s);
        }

        if (stringbuffer.length() > 0) {
            stringbuffer.append(".");
        }

        stringbuffer.append(this.name);
        stringbuffer.append(" = ");
        stringbuffer.append(this.value);
        return stringbuffer.toString();
    }
}
