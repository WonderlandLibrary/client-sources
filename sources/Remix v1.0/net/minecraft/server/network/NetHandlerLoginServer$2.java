package net.minecraft.server.network;

import net.minecraft.util.*;
import java.math.*;
import java.util.*;
import com.mojang.authlib.*;
import com.mojang.authlib.exceptions.*;

class NetHandlerLoginServer$2 extends Thread {
    @Override
    public void run() {
        final GameProfile var1 = NetHandlerLoginServer.access$100(NetHandlerLoginServer.this);
        try {
            final String var2 = new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.access$200(NetHandlerLoginServer.this), NetHandlerLoginServer.access$000(NetHandlerLoginServer.this).getKeyPair().getPublic(), NetHandlerLoginServer.access$300(NetHandlerLoginServer.this))).toString(16);
            NetHandlerLoginServer.access$102(NetHandlerLoginServer.this, NetHandlerLoginServer.access$000(NetHandlerLoginServer.this).getMinecraftSessionService().hasJoinedServer(new GameProfile((UUID)null, var1.getName()), var2));
            if (NetHandlerLoginServer.access$100(NetHandlerLoginServer.this) != null) {
                NetHandlerLoginServer.access$400().info("UUID of player " + NetHandlerLoginServer.access$100(NetHandlerLoginServer.this).getName() + " is " + NetHandlerLoginServer.access$100(NetHandlerLoginServer.this).getId());
                NetHandlerLoginServer.access$502(NetHandlerLoginServer.this, LoginState.READY_TO_ACCEPT);
            }
            else if (NetHandlerLoginServer.access$000(NetHandlerLoginServer.this).isSinglePlayer()) {
                NetHandlerLoginServer.access$400().warn("Failed to verify username but will let them in anyway!");
                NetHandlerLoginServer.access$102(NetHandlerLoginServer.this, NetHandlerLoginServer.this.getOfflineProfile(var1));
                NetHandlerLoginServer.access$502(NetHandlerLoginServer.this, LoginState.READY_TO_ACCEPT);
            }
            else {
                NetHandlerLoginServer.this.closeConnection("Failed to verify username!");
                NetHandlerLoginServer.access$400().error("Username '" + NetHandlerLoginServer.access$100(NetHandlerLoginServer.this).getName() + "' tried to join with an invalid session");
            }
        }
        catch (AuthenticationUnavailableException var3) {
            if (NetHandlerLoginServer.access$000(NetHandlerLoginServer.this).isSinglePlayer()) {
                NetHandlerLoginServer.access$400().warn("Authentication servers are down but will let them in anyway!");
                NetHandlerLoginServer.access$102(NetHandlerLoginServer.this, NetHandlerLoginServer.this.getOfflineProfile(var1));
                NetHandlerLoginServer.access$502(NetHandlerLoginServer.this, LoginState.READY_TO_ACCEPT);
            }
            else {
                NetHandlerLoginServer.this.closeConnection("Authentication servers are down. Please try again later, sorry!");
                NetHandlerLoginServer.access$400().error("Couldn't verify username because servers are unavailable");
            }
        }
    }
}