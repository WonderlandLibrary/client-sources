package net.minecraft.client.renderer;

public class Tessellator
{
    private static final Tessellator instance;
    private WorldVertexBufferUploader vboUploader;
    private WorldRenderer worldRenderer;
    
    public void draw() {
        this.worldRenderer.finishDrawing();
        this.vboUploader.func_181679_a(this.worldRenderer);
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Tessellator(final int n) {
        this.vboUploader = new WorldVertexBufferUploader();
        this.worldRenderer = new WorldRenderer(n);
    }
    
    public static Tessellator getInstance() {
        return Tessellator.instance;
    }
    
    public WorldRenderer getWorldRenderer() {
        return this.worldRenderer;
    }
    
    static {
        instance = new Tessellator(56201 + 1617319 - 1593497 + 2017129);
    }
}
