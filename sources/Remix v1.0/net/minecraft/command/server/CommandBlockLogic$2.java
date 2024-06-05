package net.minecraft.command.server;

import java.util.concurrent.*;

class CommandBlockLogic$2 implements Callable {
    public String func_180326_a() {
        return CommandBlockLogic.this.getName();
    }
    
    @Override
    public Object call() {
        return this.func_180326_a();
    }
}