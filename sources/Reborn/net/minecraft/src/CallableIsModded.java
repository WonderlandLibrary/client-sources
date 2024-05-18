package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;

class CallableIsModded implements Callable
{
    final IntegratedServer theIntegratedServer;
    
    CallableIsModded(final IntegratedServer par1IntegratedServer) {
        this.theIntegratedServer = par1IntegratedServer;
    }
    
    public String getMinecraftIsModded() {
        String var1 = ClientBrandRetriever.getClientModName();
        if (!var1.equals("vanilla")) {
            return "Definitely; Client brand changed to '" + var1 + "'";
        }
        var1 = this.theIntegratedServer.getServerModName();
        return var1.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.") : ("Definitely; Server brand changed to '" + var1 + "'");
    }
    
    @Override
    public Object call() {
        return this.getMinecraftIsModded();
    }
}
