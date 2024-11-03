package net.silentclient.client.cosmetics.model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class Model
{
    private String name;
    private List<ModelBuffer.Vertex3f> vertices;
    private List<ModelBuffer.Vertex2f> textureCoordinates;
    private List<ModelBuffer.Vertex3f> normals;
    private List<Face> faces;
    private Map<String, Material> materials;
    
    public Model() {
        this.vertices = new ArrayList<ModelBuffer.Vertex3f>();
        this.textureCoordinates = new ArrayList<ModelBuffer.Vertex2f>();
        this.normals = new ArrayList<ModelBuffer.Vertex3f>();
        this.faces = new ArrayList<Face>();
        this.materials = new HashMap<String, Material>();
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
		return name;
	}
    
    public List<ModelBuffer.Vertex3f> getVertices() {
        return this.vertices;
    }
    
    public List<ModelBuffer.Vertex2f> getTextureCoordinates() {
        return this.textureCoordinates;
    }
    
    public List<ModelBuffer.Vertex3f> getNormals() {
        return this.normals;
    }
    
    public List<Face> getFaces() {
        return this.faces;
    }
    
    public Map<String, Material> getMaterials() {
        return this.materials;
    }
}
