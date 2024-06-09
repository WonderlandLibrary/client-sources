package events;

import darkmagician6.EventCancellable;


public class EventRenderAsNormalBlock extends EventCancellable
{
  private boolean renderAsNormalBlock;
  
  public EventRenderAsNormalBlock(boolean renderAsNormalBlock)
  {
    this.renderAsNormalBlock = renderAsNormalBlock;
  }
  
  public void setRenderAsNormalBlock(boolean renderAsNormalBlock)
  {
    this.renderAsNormalBlock = renderAsNormalBlock;
  }
  
  public boolean shouldRenderAsNormalBlock()
  {
    return this.renderAsNormalBlock;
  }
}
