package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;

public class CallableType2 implements Callable
{
    final Minecraft mc;
    
    public CallableType2(final Minecraft par1Minecraft) {
        this.mc = par1Minecraft;
    }
    
    public String func_82886_a() {
        return "Client (map_client.txt)";
    }
    
    @Override
    public Object call() {
        return this.func_82886_a();
    }
}
