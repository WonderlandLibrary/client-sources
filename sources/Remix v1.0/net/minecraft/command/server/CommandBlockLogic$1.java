package net.minecraft.command.server;

import java.util.concurrent.*;

class CommandBlockLogic$1 implements Callable {
    public String func_180324_a() {
        return CommandBlockLogic.this.getCustomName();
    }
    
    @Override
    public Object call() {
        return this.func_180324_a();
    }
}