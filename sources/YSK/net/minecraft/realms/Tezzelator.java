package net.minecraft.realms;

import net.minecraft.client.renderer.*;

public class Tezzelator
{
    public static Tessellator t;
    public static final Tezzelator instance;
    
    public void tex2(final short n, final short n2) {
        Tezzelator.t.getWorldRenderer().lightmap(n, n2);
    }
    
    public void color(final float n, final float n2, final float n3, final float n4) {
        Tezzelator.t.getWorldRenderer().color(n, n2, n3, n4);
    }
    
    public void end() {
        Tezzelator.t.draw();
    }
    
    public Tezzelator vertex(final double n, final double n2, final double n3) {
        Tezzelator.t.getWorldRenderer().pos(n, n2, n3);
        return this;
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void endVertex() {
        Tezzelator.t.getWorldRenderer().endVertex();
    }
    
    public Tezzelator tex(final double n, final double n2) {
        Tezzelator.t.getWorldRenderer().tex(n, n2);
        return this;
    }
    
    public RealmsBufferBuilder color(final int n, final int n2, final int n3, final int n4) {
        return new RealmsBufferBuilder(Tezzelator.t.getWorldRenderer().color(n, n2, n3, n4));
    }
    
    public void offset(final double n, final double n2, final double n3) {
        Tezzelator.t.getWorldRenderer().setTranslation(n, n2, n3);
    }
    
    public void begin(final int n, final RealmsVertexFormat realmsVertexFormat) {
        Tezzelator.t.getWorldRenderer().begin(n, realmsVertexFormat.getVertexFormat());
    }
    
    public void normal(final float n, final float n2, final float n3) {
        Tezzelator.t.getWorldRenderer().normal(n, n2, n3);
    }
    
    static {
        Tezzelator.t = Tessellator.getInstance();
        instance = new Tezzelator();
    }
}
