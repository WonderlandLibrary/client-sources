package net.minecraft.server.integrated;

import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import java.util.*;

class IntegratedServer$3 implements Runnable {
    @Override
    public void run() {
        final ArrayList var1 = Lists.newArrayList((Iterable)IntegratedServer.this.getConfigurationManager().playerEntityList);
        for (final EntityPlayerMP var3 : var1) {
            IntegratedServer.this.getConfigurationManager().playerLoggedOut(var3);
        }
    }
}