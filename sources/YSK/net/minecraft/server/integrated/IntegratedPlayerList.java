package net.minecraft.server.integrated;

import net.minecraft.server.management.*;
import net.minecraft.nbt.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.net.*;
import com.mojang.authlib.*;

public class IntegratedPlayerList extends ServerConfigurationManager
{
    private NBTTagCompound hostPlayerData;
    private static final String[] I;
    
    public IntegratedPlayerList(final IntegratedServer integratedServer) {
        super(integratedServer);
        this.setViewDistance(0xB1 ^ 0xBB);
    }
    
    static {
        I();
    }
    
    @Override
    protected void writePlayerData(final EntityPlayerMP entityPlayerMP) {
        if (entityPlayerMP.getName().equals(this.getServerInstance().getServerOwner())) {
            entityPlayerMP.writeToNBT(this.hostPlayerData = new NBTTagCompound());
        }
        super.writePlayerData(entityPlayerMP);
    }
    
    @Override
    public IntegratedServer getServerInstance() {
        return (IntegratedServer)super.getServerInstance();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("?<\u00133V\u00055\u001f\"V\u0002'R&\u001a\u00191\u0013#\u000fK \u0013,\u0013\u0005z", "kTrGv");
    }
    
    @Override
    public MinecraftServer getServerInstance() {
        return this.getServerInstance();
    }
    
    @Override
    public NBTTagCompound getHostPlayerData() {
        return this.hostPlayerData;
    }
    
    @Override
    public String allowUserToConnect(final SocketAddress socketAddress, final GameProfile gameProfile) {
        String allowUserToConnect;
        if (gameProfile.getName().equalsIgnoreCase(this.getServerInstance().getServerOwner()) && this.getPlayerByUsername(gameProfile.getName()) != null) {
            allowUserToConnect = IntegratedPlayerList.I["".length()];
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            allowUserToConnect = super.allowUserToConnect(socketAddress, gameProfile);
        }
        return allowUserToConnect;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
