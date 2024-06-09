package events;

import net.minecraft.block.Block;
import darkmagician6.EventCancellable;

public class EventRenderBlockPass
  extends EventCancellable
{
  private final Block block;
  private int renderBlockPass;
  
  public EventRenderBlockPass(Block block, int renderBlockPass)
  {
    this.block = block;
    this.renderBlockPass = renderBlockPass;
  }
  
  public Block getBlock()
  {
    return this.block;
  }
  
  public int getRenderBlockPass()
  {
    return this.renderBlockPass;
  }
  
  public void setRenderBlockPass(int renderBlockPass)
  {
    this.renderBlockPass = renderBlockPass;
  }
}
