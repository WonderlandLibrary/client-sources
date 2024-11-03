package net.silentclient.client.cosmetics.model;

import java.nio.FloatBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import net.minecraft.util.ResourceLocation;

public class ModelBuffer
{
    private static final ExecutorService EXECUTOR_SERVICE;
    private final ResourceLocation location;
    private final boolean flipUVs;
    private Model model;
    private Integer bufferId;
    private Future<FutureResult> loaded;
    private int totalVertices;
    
    public ModelBuffer(final ResourceLocation aj) {
        this(aj, false);
    }
    
    public ModelBuffer(final ResourceLocation location, final boolean flipUVs) {
        this.location = location;
        this.flipUVs = flipUVs;
    }
    
    public boolean loadModel() {
        if (this.isLoaded()) {
            return true;
        }
        if (this.loaded != null && this.loaded.isDone()) {
            FloatBuffer access$000;
            try {
                final FutureResult futureResult = this.loaded.get();
                access$000 = futureResult.getBuffer();
                this.model = futureResult.getModel();
                this.totalVertices = futureResult.getTotalVertices();
            }
            catch (final Exception cause) {
                throw new RuntimeException(cause);
            }
            this.bufferId = GL15.glGenBuffers();
            GL15.glBindBuffer(34962, (int)this.bufferId);
            GL15.glBufferData(34962, access$000, 35044);
            GL15.glBindBuffer(34962, 0);
            return true;
        }
        if (this.model == null && this.loaded == null) {
            this.loaded = ModelBuffer.EXECUTOR_SERVICE.submit((Callable<FutureResult>)new Callable<FutureResult>() {
                @Override
                public FutureResult call() {
                    final Model loadModel = ModelLoader.loadModel(ModelBuffer.this.location, ModelBuffer.this.flipUVs);
                    final int n = loadModel.getFaces().size() * 3;
                    final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(n * 8);
                    for (final Face face : loadModel.getFaces()) {
                        final Vertex3f[] array = { loadModel.getNormals().get(face.getNormalIndices()[0] - 1), loadModel.getNormals().get(face.getNormalIndices()[1] - 1), loadModel.getNormals().get(face.getNormalIndices()[2] - 1) };
                        final Vertex2f[] array2 = { loadModel.getTextureCoordinates().get(face.getTextureCoordinateIndices()[0] - 1), loadModel.getTextureCoordinates().get(face.getTextureCoordinateIndices()[1] - 1), loadModel.getTextureCoordinates().get(face.getTextureCoordinateIndices()[2] - 1) };
                        final Vertex3f[] array3 = { loadModel.getVertices().get(face.getVertexIndices()[0] - 1), loadModel.getVertices().get(face.getVertexIndices()[1] - 1), loadModel.getVertices().get(face.getVertexIndices()[2] - 1) };
                        for (int i = 0; i < 3; ++i) {
                            floatBuffer.put(new float[] { array3[i].getX(), array3[i].getY(), array3[i].getZ() });
                            floatBuffer.put(new float[] { array2[i].getX(), array2[i].getY() });
                            floatBuffer.put(new float[] { array[i].getX(), array[i].getY(), array[i].getZ() });
                        }
                    }
                    floatBuffer.flip();
                    return new FutureResult(loadModel, floatBuffer, n);
                }
            });
        }
        return false;
    }
    
    public void renderModel() {
        if (!this.isLoaded()) {
            this.loadModel();
        }
        else {
            GL15.glBindBuffer(34962, (int)this.bufferId);
            GL11.glVertexPointer(3, 5126, 32, 0L);
            GL11.glEnableClientState(32884);
            GL11.glTexCoordPointer(2, 5126, 32, 12L);
            GL11.glEnableClientState(32888);
            GL11.glNormalPointer(5126, 32, 20L);
            GL11.glEnableClientState(32885);
            GL11.glDrawArrays(4, 0, this.totalVertices);
            GL11.glDisableClientState(32884);
            GL11.glDisableClientState(32888);
            GL11.glDisableClientState(32885);
            GL15.glBindBuffer(34962, 0);
        }
    }
    
    public void deleteData() {
        if (this.loaded != null) {
            if (this.loaded.isDone()) {
                GL15.glDeleteBuffers((int)this.bufferId);
            }
            else {
                this.loaded.cancel(true);
            }
        }
        this.bufferId = null;
        this.model = null;
        this.loaded = null;
    }
    
    private boolean isLoaded() {
        return this.bufferId != null;
    }
    
    static {
        EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    }
    
    public static class Vertex2f
    {
        public float x;
        public float y;
        
        public Vertex2f(final float n, final float n2) {
            this.set(n, n2);
        }
        
        public void set(final float x, final float y) {
            this.x = x;
            this.y = y;
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setX(final float x) {
            this.x = x;
        }
        
        public void setY(final float y) {
            this.y = y;
        }
    }
    
    public static class Vertex3f
    {
        public float x;
        public float y;
        public float z;
        
        public Vertex3f() {
        }
        
        public Vertex3f(final float n, final float n2, final float n3) {
            this.set(n, n2, n3);
        }
        
        public void set(final float x, final float y, final float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public float getX() {
            return this.x;
        }
        
        public void setX(final float x) {
            this.x = x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setY(final float y) {
            this.y = y;
        }
        
        public float getZ() {
            return this.z;
        }
        
        public void setZ(final float z) {
            this.z = z;
        }
    }
    
    private static class FutureResult
    {
        private Model model;
        private FloatBuffer buffer;
        private int totalVertices;
        
        private FutureResult(final Model model, final FloatBuffer buffer, final int totalVertices) {
            this.model = model;
            this.buffer = buffer;
            this.totalVertices = totalVertices;
        }
        
        private Model getModel() {
            return this.model;
        }
        
        private FloatBuffer getBuffer() {
            return this.buffer;
        }
        
        private int getTotalVertices() {
            return this.totalVertices;
        }
    }
}
