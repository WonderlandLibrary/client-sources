package im.expensive.events;

import com.mojang.blaze3d.matrix.MatrixStack;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class WorldEvent {
    private MatrixStack stack;
    private float partialTicks;
    
    public WorldEvent(MatrixStack stack, float partialTicks)
    {
        this.stack = stack;
        this.partialTicks = partialTicks;
    }
    
  
}
