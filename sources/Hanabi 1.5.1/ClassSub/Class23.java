package ClassSub;

public class Class23
{
    private int chunkX;
    private int chunkZ;
    private byte[] topLayerData;
    
    
    public Class23(final byte[] topLayerData, final int n, final int n2) {
        this.topLayerData = new byte[256];
        this.topLayerData = topLayerData;
    }
    
    public int getChunkX() {
        return this.chunkX;
    }
    
    public int getChunkZ() {
        return this.chunkZ;
    }
    
    public byte[] getTopLayerData() {
        return this.topLayerData;
    }
    
    public void setTopLayerData(final byte[] topLayerData) {
        this.topLayerData = topLayerData;
    }
}
