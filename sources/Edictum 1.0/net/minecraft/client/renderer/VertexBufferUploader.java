package net.minecraft.client.renderer;

public class VertexBufferUploader extends WorldVertexBufferUploader
{
    private net.minecraft.client.renderer.vertex.VertexBuffer vertexBuffer;

    public void draw(VertexBuffer vertexBufferIn)
    {
        vertexBufferIn.reset();
        this.vertexBuffer.bufferData(vertexBufferIn.getByteBuffer());
    }

    public void setVertexBuffer(net.minecraft.client.renderer.vertex.VertexBuffer vertexBufferIn)
    {
        this.vertexBuffer = vertexBufferIn;
    }
}
