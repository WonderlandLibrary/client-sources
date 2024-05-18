package net.minecraft.src;

import java.math.*;
import java.net.*;
import java.io.*;

class ThreadLoginVerifier extends Thread
{
    final NetLoginHandler loginHandler;
    
    ThreadLoginVerifier(final NetLoginHandler par1NetLoginHandler) {
        this.loginHandler = par1NetLoginHandler;
    }
    
    @Override
    public void run() {
        try {
            final String var1 = new BigInteger(CryptManager.getServerIdHash(NetLoginHandler.getServerId(this.loginHandler), NetLoginHandler.getLoginMinecraftServer(this.loginHandler).getKeyPair().getPublic(), NetLoginHandler.getSharedKey(this.loginHandler))).toString(16);
            final URL var2 = new URL("http://mcssv0.craftlandia.com.br/game/checkserver?user=" + URLEncoder.encode(NetLoginHandler.getClientUsername(this.loginHandler), "UTF-8") + "&serverId=" + URLEncoder.encode(var1, "UTF-8"));
            final BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.openStream()));
            final String var4 = var3.readLine();
            var3.close();
            if (!"YES".equals(var4)) {
                this.loginHandler.raiseErrorAndDisconnect("Failed to verify username!");
                return;
            }
            NetLoginHandler.func_72531_a(this.loginHandler, true);
        }
        catch (Exception var5) {
            this.loginHandler.raiseErrorAndDisconnect("Failed to verify username! [internal error " + var5 + "]");
            var5.printStackTrace();
        }
    }
}
