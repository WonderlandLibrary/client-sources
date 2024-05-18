package net.minecraft.src;

import net.minecraft.server.*;
import java.net.*;

public class IntegratedPlayerList extends ServerConfigurationManager
{
    private NBTTagCompound hostPlayerData;
    
    public IntegratedPlayerList(final IntegratedServer par1IntegratedServer) {
        super(par1IntegratedServer);
        this.hostPlayerData = null;
        this.viewDistance = 10;
    }
    
    @Override
    protected void writePlayerData(final EntityPlayerMP par1EntityPlayerMP) {
        if (par1EntityPlayerMP.getCommandSenderName().equals(this.getIntegratedServer().getServerOwner())) {
            par1EntityPlayerMP.writeToNBT(this.hostPlayerData = new NBTTagCompound());
        }
        super.writePlayerData(par1EntityPlayerMP);
    }
    
    @Override
    public String allowUserToConnect(final SocketAddress par1SocketAddress, final String par2Str) {
        return par2Str.equalsIgnoreCase(this.getIntegratedServer().getServerOwner()) ? "That name is already taken." : super.allowUserToConnect(par1SocketAddress, par2Str);
    }
    
    public IntegratedServer getIntegratedServer() {
        return (IntegratedServer)super.getServerInstance();
    }
    
    @Override
    public NBTTagCompound getHostPlayerData() {
        return this.hostPlayerData;
    }
    
    @Override
    public MinecraftServer getServerInstance() {
        return this.getIntegratedServer();
    }
}
