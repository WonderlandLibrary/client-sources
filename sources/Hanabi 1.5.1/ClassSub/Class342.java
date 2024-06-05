package ClassSub;

public class Class342
{
    private final int height;
    private final int width;
    int posXCenter;
    int posZCenter;
    int posXStart;
    int posZStart;
    byte[] blockData;
    
    
    public Class342(final int n) {
        this.posXCenter = 0;
        this.posZCenter = 0;
        this.posXStart = 0;
        this.posZStart = 0;
        this.height = (1 + n * 2) * 16;
        this.width = (1 + n * 2) * 16;
        this.blockData = new byte[this.height * this.width];
    }
    
    public void fillInChunkData(final Class23 class23) {
        int n = Math.abs((class23.getChunkX() << 4) - this.posXStart) + Math.abs((class23.getChunkZ() << 4) - this.posZStart);
        int n2 = 0;
        for (final byte b : class23.getTopLayerData()) {
            if (n2 != 0 && n2 % 16 == 0) {
                n += this.width - 16;
            }
            this.blockData[n] = b;
            ++n2;
        }
    }
    
    public byte[] getBlockData() {
        return this.blockData;
    }
}
