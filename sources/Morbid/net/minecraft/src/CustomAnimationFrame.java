package net.minecraft.src;

public class CustomAnimationFrame
{
    public int index;
    public int duration;
    public int counter;
    
    public CustomAnimationFrame(final int var1, final int var2) {
        this.index = 0;
        this.duration = 0;
        this.counter = 0;
        this.index = var1;
        this.duration = var2;
    }
}
