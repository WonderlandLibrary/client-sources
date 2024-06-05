package net.minecraft.server.integrated;

import java.util.concurrent.*;
import net.minecraft.client.*;

class IntegratedServer$2 implements Callable {
    @Override
    public String call() {
        String var1 = ClientBrandRetriever.getClientModName();
        if (!var1.equals("vanilla")) {
            return "Definitely; Client brand changed to '" + var1 + "'";
        }
        var1 = IntegratedServer.this.getServerModName();
        return var1.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.") : ("Definitely; Server brand changed to '" + var1 + "'");
    }
}