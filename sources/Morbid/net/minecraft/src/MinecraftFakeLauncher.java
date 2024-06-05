package net.minecraft.src;

import java.applet.*;
import java.util.*;
import java.net.*;

public class MinecraftFakeLauncher extends Applet implements AppletStub
{
    final Map arguments;
    
    public MinecraftFakeLauncher(final Map par1Map) {
        this.arguments = par1Map;
    }
    
    @Override
    public void appletResize(final int par1, final int par2) {
    }
    
    @Override
    public boolean isActive() {
        return true;
    }
    
    @Override
    public URL getDocumentBase() {
        try {
            return new URL("http://www.minecraft.net/game/");
        }
        catch (MalformedURLException var2) {
            var2.printStackTrace();
            return null;
        }
    }
    
    @Override
    public String getParameter(final String par1Str) {
        if (this.arguments.containsKey(par1Str)) {
            return this.arguments.get(par1Str);
        }
        System.err.println("Client asked for parameter: " + par1Str);
        return null;
    }
}
