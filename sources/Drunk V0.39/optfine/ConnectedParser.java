/*
 * Decompiled with CFR 0.152.
 */
package optfine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.BiomeGenBase;
import optfine.Config;
import optfine.ConnectedProperties;
import optfine.MatchBlock;
import optfine.RangeInt;
import optfine.RangeListInt;

public class ConnectedParser {
    private String context = null;

    public ConnectedParser(String p_i28_1_) {
        this.context = p_i28_1_;
    }

    public String parseName(String p_parseName_1_) {
        int j;
        String s = p_parseName_1_;
        int i = p_parseName_1_.lastIndexOf(47);
        if (i >= 0) {
            s = p_parseName_1_.substring(i + 1);
        }
        if ((j = s.lastIndexOf(46)) < 0) return s;
        return s.substring(0, j);
    }

    public String parseBasePath(String p_parseBasePath_1_) {
        int i = p_parseBasePath_1_.lastIndexOf(47);
        if (i < 0) {
            return "";
        }
        String string = p_parseBasePath_1_.substring(0, i);
        return string;
    }

    public MatchBlock[] parseMatchBlocks(String p_parseMatchBlocks_1_) {
        if (p_parseMatchBlocks_1_ == null) {
            return null;
        }
        ArrayList<MatchBlock> list = new ArrayList<MatchBlock>();
        String[] astring = Config.tokenize(p_parseMatchBlocks_1_, " ");
        int i = 0;
        while (i < astring.length) {
            String s = astring[i];
            MatchBlock[] amatchblock = this.parseMatchBlock(s);
            if (amatchblock == null) {
                return null;
            }
            list.addAll(Arrays.asList(amatchblock));
            ++i;
        }
        return list.toArray(new MatchBlock[list.size()]);
    }

    public MatchBlock[] parseMatchBlock(String p_parseMatchBlock_1_) {
        if (p_parseMatchBlock_1_ == null) {
            return null;
        }
        if ((p_parseMatchBlock_1_ = p_parseMatchBlock_1_.trim()).length() <= 0) {
            return null;
        }
        String[] astring = Config.tokenize(p_parseMatchBlock_1_, ":");
        String s = "minecraft";
        int i = 0;
        if (astring.length > 1 && this.isFullBlockName(astring)) {
            s = astring[0];
            i = 1;
        } else {
            s = "minecraft";
            i = 0;
        }
        String s1 = astring[i];
        String[] astring1 = Arrays.copyOfRange(astring, i + 1, astring.length);
        Block[] ablock = this.parseBlockPart(s, s1);
        MatchBlock[] amatchblock = new MatchBlock[ablock.length];
        int j = 0;
        while (j < ablock.length) {
            MatchBlock matchblock;
            Block block = ablock[j];
            int k = Block.getIdFromBlock(block);
            int[] aint = this.parseBlockMetadatas(block, astring1);
            amatchblock[j] = matchblock = new MatchBlock(k, aint);
            ++j;
        }
        return amatchblock;
    }

    public boolean isFullBlockName(String[] p_isFullBlockName_1_) {
        if (p_isFullBlockName_1_.length < 2) {
            return false;
        }
        String s = p_isFullBlockName_1_[1];
        if (s.length() < 1) {
            return false;
        }
        if (this.startsWithDigit(s)) {
            return false;
        }
        if (s.contains("=")) return false;
        return true;
    }

    public boolean startsWithDigit(String p_startsWithDigit_1_) {
        if (p_startsWithDigit_1_ == null) {
            return false;
        }
        if (p_startsWithDigit_1_.length() < 1) {
            return false;
        }
        char c0 = p_startsWithDigit_1_.charAt(0);
        return Character.isDigit(c0);
    }

    public Block[] parseBlockPart(String p_parseBlockPart_1_, String p_parseBlockPart_2_) {
        int[] aint;
        if (this.startsWithDigit(p_parseBlockPart_2_)) {
            aint = this.parseIntList(p_parseBlockPart_2_);
            if (aint == null) {
                return null;
            }
        } else {
            String s = p_parseBlockPart_1_ + ":" + p_parseBlockPart_2_;
            Block block = Block.getBlockFromName(s);
            if (block != null) return new Block[]{block};
            this.warn("Block not found for name: " + s);
            return null;
        }
        Block[] ablock1 = new Block[aint.length];
        int j = 0;
        while (j < aint.length) {
            int i = aint[j];
            Block block1 = Block.getBlockById(i);
            if (block1 == null) {
                this.warn("Block not found for id: " + i);
                return null;
            }
            ablock1[j] = block1;
            ++j;
        }
        return ablock1;
    }

    public int[] parseBlockMetadatas(Block p_parseBlockMetadatas_1_, String[] p_parseBlockMetadatas_2_) {
        if (p_parseBlockMetadatas_2_.length <= 0) {
            return null;
        }
        String s = p_parseBlockMetadatas_2_[0];
        if (this.startsWithDigit(s)) {
            return this.parseIntList(s);
        }
        IBlockState iblockstate = p_parseBlockMetadatas_1_.getDefaultState();
        Collection<IProperty> collection = iblockstate.getPropertyNames();
        HashMap map = new HashMap();
        int i = 0;
        while (true) {
            block13: {
                String[] astring1;
                ArrayList<Comparable> list;
                IProperty iproperty;
                String s2;
                block14: {
                    block12: {
                        if (i >= p_parseBlockMetadatas_2_.length) break block12;
                        String s1 = p_parseBlockMetadatas_2_[i];
                        if (s1.length() <= 0) break block13;
                        String[] astring = Config.tokenize(s1, "=");
                        if (astring.length != 2) {
                            this.warn("Invalid block property: " + s1);
                            return null;
                        }
                        s2 = astring[0];
                        String s3 = astring[1];
                        iproperty = ConnectedProperties.getProperty(s2, collection);
                        if (iproperty == null) {
                            this.warn("Property not found: " + s2 + ", block: " + p_parseBlockMetadatas_1_);
                            return null;
                        }
                        list = (ArrayList<Comparable>)map.get(s2);
                        if (list == null) {
                            list = new ArrayList<Comparable>();
                            map.put(iproperty, list);
                        }
                        astring1 = Config.tokenize(s3, ",");
                        break block14;
                    }
                    if (map.isEmpty()) {
                        return null;
                    }
                    ArrayList<Integer> list1 = new ArrayList<Integer>();
                    for (int k = 0; k < 16; ++k) {
                        IBlockState iblockstate1 = p_parseBlockMetadatas_1_.getStateFromMeta(k);
                        if (!this.matchState(iblockstate1, map)) continue;
                        list1.add(k);
                    }
                    if (list1.size() == 16) {
                        return null;
                    }
                    int[] aint1 = new int[list1.size()];
                    int l = 0;
                    while (l < aint1.length) {
                        aint1[l] = (Integer)list1.get(l);
                        ++l;
                    }
                    return aint1;
                }
                for (int j = 0; j < astring1.length; ++j) {
                    String s4 = astring1[j];
                    Comparable comparable = ConnectedParser.parsePropertyValue(iproperty, s4);
                    if (comparable == null) {
                        this.warn("Property value not found: " + s4 + ", property: " + s2 + ", block: " + p_parseBlockMetadatas_1_);
                        return null;
                    }
                    list.add(comparable);
                }
            }
            ++i;
        }
    }

    public static Comparable parsePropertyValue(IProperty p_parsePropertyValue_0_, String p_parsePropertyValue_1_) {
        Class oclass = p_parsePropertyValue_0_.getValueClass();
        Comparable comparable = ConnectedParser.parseValue(p_parsePropertyValue_1_, oclass);
        if (comparable != null) return comparable;
        Collection collection = p_parsePropertyValue_0_.getAllowedValues();
        return ConnectedParser.getPropertyValue(p_parsePropertyValue_1_, collection);
    }

    public static Comparable getPropertyValue(String p_getPropertyValue_0_, Collection p_getPropertyValue_1_) {
        Object comparable;
        Iterator iterator = p_getPropertyValue_1_.iterator();
        do {
            if (!iterator.hasNext()) return null;
        } while (!String.valueOf(comparable = iterator.next()).equals(p_getPropertyValue_0_));
        return (Comparable)comparable;
    }

    public static Comparable parseValue(String p_parseValue_0_, Class p_parseValue_1_) {
        Object object;
        if (p_parseValue_1_ == String.class) {
            object = p_parseValue_0_;
            return (Comparable)object;
        }
        if (p_parseValue_1_ == Boolean.class) {
            object = Boolean.valueOf(p_parseValue_0_);
            return (Comparable)object;
        }
        object = p_parseValue_1_ == Float.class ? (double)Float.valueOf(p_parseValue_0_).floatValue() : (p_parseValue_1_ == Double.class ? Double.valueOf(p_parseValue_0_) : (double)(p_parseValue_1_ == Integer.class ? (long)Integer.valueOf(p_parseValue_0_).intValue() : (p_parseValue_1_ == Long.class ? Long.valueOf(p_parseValue_0_) : null)));
        return (Comparable)object;
    }

    public boolean matchState(IBlockState p_matchState_1_, Map p_matchState_2_) {
        Object comparable;
        List list;
        Iterator iterator = p_matchState_2_.entrySet().iterator();
        do {
            if (!iterator.hasNext()) return true;
            Map.Entry entry = iterator.next();
            IProperty iproperty = (IProperty)entry.getKey();
            list = (List)entry.getValue();
            comparable = p_matchState_1_.getValue(iproperty);
            if (comparable != null) continue;
            return false;
        } while (list.contains(comparable));
        return false;
    }

    public BiomeGenBase[] parseBiomes(String p_parseBiomes_1_) {
        if (p_parseBiomes_1_ == null) {
            return null;
        }
        String[] astring = Config.tokenize(p_parseBiomes_1_, " ");
        ArrayList<BiomeGenBase> list = new ArrayList<BiomeGenBase>();
        int i = 0;
        while (i < astring.length) {
            String s = astring[i];
            BiomeGenBase biomegenbase = this.findBiome(s);
            if (biomegenbase == null) {
                this.warn("Biome not found: " + s);
            } else {
                list.add(biomegenbase);
            }
            ++i;
        }
        return list.toArray(new BiomeGenBase[list.size()]);
    }

    public BiomeGenBase findBiome(String p_findBiome_1_) {
        p_findBiome_1_ = p_findBiome_1_.toLowerCase();
        BiomeGenBase[] abiomegenbase = BiomeGenBase.getBiomeGenArray();
        int i = 0;
        while (i < abiomegenbase.length) {
            String s;
            BiomeGenBase biomegenbase = abiomegenbase[i];
            if (biomegenbase != null && (s = biomegenbase.biomeName.replace(" ", "").toLowerCase()).equals(p_findBiome_1_)) {
                return biomegenbase;
            }
            ++i;
        }
        return null;
    }

    public int parseInt(String p_parseInt_1_) {
        if (p_parseInt_1_ == null) {
            return -1;
        }
        int i = Config.parseInt(p_parseInt_1_, -1);
        if (i >= 0) return i;
        this.warn("Invalid number: " + p_parseInt_1_);
        return i;
    }

    public int parseInt(String p_parseInt_1_, int p_parseInt_2_) {
        if (p_parseInt_1_ == null) {
            return p_parseInt_2_;
        }
        int i = Config.parseInt(p_parseInt_1_, -1);
        if (i >= 0) return i;
        this.warn("Invalid number: " + p_parseInt_1_);
        return p_parseInt_2_;
    }

    public int[] parseIntList(String p_parseIntList_1_) {
        if (p_parseIntList_1_ == null) {
            return null;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        String[] astring = Config.tokenize(p_parseIntList_1_, " ,");
        for (int i = 0; i < astring.length; ++i) {
            String s = astring[i];
            if (s.contains("-")) {
                String[] astring1 = Config.tokenize(s, "-");
                if (astring1.length != 2) {
                    this.warn("Invalid interval: " + s + ", when parsing: " + p_parseIntList_1_);
                    continue;
                }
                int k = Config.parseInt(astring1[0], -1);
                int l = Config.parseInt(astring1[1], -1);
                if (k >= 0 && l >= 0 && k <= l) {
                    for (int i1 = k; i1 <= l; ++i1) {
                        list.add(i1);
                    }
                    continue;
                }
                this.warn("Invalid interval: " + s + ", when parsing: " + p_parseIntList_1_);
                continue;
            }
            int j = Config.parseInt(s, -1);
            if (j < 0) {
                this.warn("Invalid number: " + s + ", when parsing: " + p_parseIntList_1_);
                continue;
            }
            list.add(j);
        }
        int[] aint = new int[list.size()];
        int j1 = 0;
        while (j1 < aint.length) {
            aint[j1] = (Integer)list.get(j1);
            ++j1;
        }
        return aint;
    }

    public boolean[] parseFaces(String p_parseFaces_1_, boolean[] p_parseFaces_2_) {
        if (p_parseFaces_1_ == null) {
            return p_parseFaces_2_;
        }
        EnumSet<EnumFacing> enumset = EnumSet.allOf(EnumFacing.class);
        String[] astring = Config.tokenize(p_parseFaces_1_, " ,");
        for (int i = 0; i < astring.length; ++i) {
            String s = astring[i];
            if (s.equals("sides")) {
                enumset.add(EnumFacing.NORTH);
                enumset.add(EnumFacing.SOUTH);
                enumset.add(EnumFacing.WEST);
                enumset.add(EnumFacing.EAST);
                continue;
            }
            if (s.equals("all")) {
                enumset.addAll(Arrays.asList(EnumFacing.VALUES));
                continue;
            }
            EnumFacing enumfacing = this.parseFace(s);
            if (enumfacing == null) continue;
            enumset.add(enumfacing);
        }
        boolean[] aboolean = new boolean[EnumFacing.VALUES.length];
        int j = 0;
        while (j < aboolean.length) {
            aboolean[j] = enumset.contains(EnumFacing.VALUES[j]);
            ++j;
        }
        return aboolean;
    }

    public EnumFacing parseFace(String p_parseFace_1_) {
        if ((p_parseFace_1_ = p_parseFace_1_.toLowerCase()).equals("bottom")) return EnumFacing.DOWN;
        if (p_parseFace_1_.equals("down")) return EnumFacing.DOWN;
        if (p_parseFace_1_.equals("top")) return EnumFacing.UP;
        if (p_parseFace_1_.equals("up")) return EnumFacing.UP;
        if (p_parseFace_1_.equals("north")) {
            return EnumFacing.NORTH;
        }
        if (p_parseFace_1_.equals("south")) {
            return EnumFacing.SOUTH;
        }
        if (p_parseFace_1_.equals("east")) {
            return EnumFacing.EAST;
        }
        if (p_parseFace_1_.equals("west")) {
            return EnumFacing.WEST;
        }
        Config.warn("Unknown face: " + p_parseFace_1_);
        return null;
    }

    public void dbg(String p_dbg_1_) {
        Config.dbg("" + this.context + ": " + p_dbg_1_);
    }

    public void warn(String p_warn_1_) {
        Config.warn("" + this.context + ": " + p_warn_1_);
    }

    public RangeListInt parseRangeListInt(String p_parseRangeListInt_1_) {
        if (p_parseRangeListInt_1_ == null) {
            return null;
        }
        RangeListInt rangelistint = new RangeListInt();
        String[] astring = Config.tokenize(p_parseRangeListInt_1_, " ,");
        int i = 0;
        while (i < astring.length) {
            String s = astring[i];
            RangeInt rangeint = this.parseRangeInt(s);
            if (rangeint == null) {
                return null;
            }
            rangelistint.addRange(rangeint);
            ++i;
        }
        return rangelistint;
    }

    private RangeInt parseRangeInt(String p_parseRangeInt_1_) {
        if (p_parseRangeInt_1_ == null) {
            return null;
        }
        if (p_parseRangeInt_1_.indexOf(45) < 0) {
            int i = Config.parseInt(p_parseRangeInt_1_, -1);
            if (i >= 0) return new RangeInt(i, i);
            this.warn("Invalid integer: " + p_parseRangeInt_1_);
            return null;
        }
        String[] astring = Config.tokenize(p_parseRangeInt_1_, "-");
        if (astring.length != 2) {
            this.warn("Invalid range: " + p_parseRangeInt_1_);
            return null;
        }
        int j = Config.parseInt(astring[0], -1);
        int k = Config.parseInt(astring[1], -1);
        if (j >= 0 && k >= 0) {
            return new RangeInt(j, k);
        }
        this.warn("Invalid range: " + p_parseRangeInt_1_);
        return null;
    }
}

