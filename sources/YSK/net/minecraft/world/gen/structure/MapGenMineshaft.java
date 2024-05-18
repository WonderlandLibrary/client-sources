package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;

public class MapGenMineshaft extends MapGenStructure
{
    private double field_82673_e;
    private static final String[] I;
    
    public MapGenMineshaft(final Map<String, String> map) {
        this.field_82673_e = 0.004;
        final Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, String> entry = iterator.next();
            if (entry.getKey().equals(MapGenMineshaft.I[" ".length()])) {
                this.field_82673_e = MathHelper.parseDoubleWithDefault(entry.getValue(), this.field_82673_e);
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(final int n, final int n2) {
        if (this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(0x68 ^ 0x38) < Math.max(Math.abs(n), Math.abs(n2))) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String getStructureName() {
        return MapGenMineshaft.I["".length()];
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u000e\u0002\u000f\u0012\u001f+\n\u0007\u0003", "Ckawl");
        MapGenMineshaft.I[" ".length()] = I("\r\u0007\u001b<\b\u000b", "nozRk");
    }
    
    static {
        I();
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        return new StructureMineshaftStart(this.worldObj, this.rand, n, n2);
    }
    
    public MapGenMineshaft() {
        this.field_82673_e = 0.004;
    }
}
