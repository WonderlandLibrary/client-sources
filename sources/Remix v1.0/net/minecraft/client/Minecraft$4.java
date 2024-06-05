package net.minecraft.client;

import java.util.concurrent.*;

class Minecraft$4 implements Callable {
    @Override
    public String call() {
        return Minecraft.this.currentScreen.getClass().getCanonicalName();
    }
    
    public Object call1() {
        return this.call();
    }
}