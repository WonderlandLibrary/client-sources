package optifine;

import net.minecraft.client.renderer.chunk.RenderChunk;

public class RenderInfoLazy
{
  private RenderChunk renderChunk;
  private net.minecraft.client.renderer.RenderGlobal.ContainerLocalRenderInformation renderInfo;
  
  public RenderInfoLazy() {}
  
  public RenderChunk getRenderChunk()
  {
    return renderChunk;
  }
  
  public void setRenderChunk(RenderChunk renderChunk)
  {
    this.renderChunk = renderChunk;
    renderInfo = null;
  }
  
  public net.minecraft.client.renderer.RenderGlobal.ContainerLocalRenderInformation getRenderInfo()
  {
    if (renderInfo == null)
    {
      renderInfo = new net.minecraft.client.renderer.RenderGlobal.ContainerLocalRenderInformation(renderChunk, null, 0);
    }
    
    return renderInfo;
  }
}
