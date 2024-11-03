package net.silentclient.client.cosmetics.model;

public class Face
{
    private final int[] vertexIndices;
    private final int[] normalIndices;
    private final int[] textureCoordinateIndices;
    private Material material;
    
    public Material getMaterial() {
        return this.material;
    }
    
    public boolean hasNormals() {
        return this.normalIndices[0] != -1;
    }
    
    public boolean hasTextureCoordinates() {
        return this.textureCoordinateIndices[0] != -1;
    }
    
    public int[] getVertexIndices() {
        return this.vertexIndices;
    }
    
    public int[] getTextureCoordinateIndices() {
        return this.textureCoordinateIndices;
    }
    
    public int[] getNormalIndices() {
        return this.normalIndices;
    }
    
    public Face(final int[] array) {
        this.vertexIndices = new int[] { -1, -1, -1, -1 };
        this.normalIndices = new int[] { -1, -1, -1, -1 };
        this.textureCoordinateIndices = new int[] { -1, -1, -1, -1 };
        this.vertexIndices[0] = array[0];
        this.vertexIndices[1] = array[1];
        this.vertexIndices[2] = array[2];
    }
    
    public Face(final int[] array, final int[] array2) {
        this.vertexIndices = new int[] { -1, -1, -1, -1 };
        this.normalIndices = new int[] { -1, -1, -1, -1 };
        this.textureCoordinateIndices = new int[] { -1, -1, -1, -1 };
        this.vertexIndices[0] = array[0];
        this.vertexIndices[1] = array[1];
        this.vertexIndices[2] = array[2];
        this.normalIndices[0] = array2[0];
        this.normalIndices[1] = array2[1];
        this.normalIndices[2] = array2[2];
    }
    
    public Face(final int[] array, final int[] array2, final int[] array3, final Material material) {
        this.vertexIndices = new int[] { -1, -1, -1, -1 };
        this.normalIndices = new int[] { -1, -1, -1, -1 };
        this.textureCoordinateIndices = new int[] { -1, -1, -1, -1 };
        this.vertexIndices[0] = array[0];
        this.vertexIndices[1] = array[1];
        this.vertexIndices[2] = array[2];
        this.textureCoordinateIndices[0] = array3[0];
        this.textureCoordinateIndices[1] = array3[1];
        this.textureCoordinateIndices[2] = array3[2];
        this.normalIndices[0] = array2[0];
        this.normalIndices[1] = array2[1];
        this.normalIndices[2] = array2[2];
        this.material = material;
    }
}
