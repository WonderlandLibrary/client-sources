package com.client.glowclient.commands;

import com.client.glowclient.*;
import net.minecraftforge.fml.common.*;

public class Shutdown extends Command
{
    @Override
    public void M(final String s, final String[] array) {
        Ob.M().M();
        final FMLCommonHandler instance = FMLCommonHandler.instance();
        final int n = 0;
        instance.exitJava(n, (boolean)(n != 0));
    }
    
    public Shutdown() {
        super("shutdown");
    }
    
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("shutdown").toString();
    }
}
