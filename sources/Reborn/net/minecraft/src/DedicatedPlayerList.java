package net.minecraft.src;

import net.minecraft.server.*;
import java.io.*;
import java.util.*;

public class DedicatedPlayerList extends ServerConfigurationManager
{
    private File opsList;
    private File whiteList;
    
    public DedicatedPlayerList(final DedicatedServer par1DedicatedServer) {
        super(par1DedicatedServer);
        this.opsList = par1DedicatedServer.getFile("ops.txt");
        this.whiteList = par1DedicatedServer.getFile("white-list.txt");
        this.viewDistance = par1DedicatedServer.getIntProperty("view-distance", 10);
        this.maxPlayers = par1DedicatedServer.getIntProperty("max-players", 20);
        this.setWhiteListEnabled(par1DedicatedServer.getBooleanProperty("white-list", false));
        if (!par1DedicatedServer.isSinglePlayer()) {
            this.getBannedPlayers().setListActive(true);
            this.getBannedIPs().setListActive(true);
        }
        this.getBannedPlayers().loadBanList();
        this.getBannedPlayers().saveToFileWithHeader();
        this.getBannedIPs().loadBanList();
        this.getBannedIPs().saveToFileWithHeader();
        this.loadOpsList();
        this.readWhiteList();
        this.saveOpsList();
        if (!this.whiteList.exists()) {
            this.saveWhiteList();
        }
    }
    
    @Override
    public void setWhiteListEnabled(final boolean par1) {
        super.setWhiteListEnabled(par1);
        this.getDedicatedServerInstance().setProperty("white-list", par1);
        this.getDedicatedServerInstance().saveProperties();
    }
    
    @Override
    public void addOp(final String par1Str) {
        super.addOp(par1Str);
        this.saveOpsList();
    }
    
    @Override
    public void removeOp(final String par1Str) {
        super.removeOp(par1Str);
        this.saveOpsList();
    }
    
    @Override
    public void removeFromWhitelist(final String par1Str) {
        super.removeFromWhitelist(par1Str);
        this.saveWhiteList();
    }
    
    @Override
    public void addToWhiteList(final String par1Str) {
        super.addToWhiteList(par1Str);
        this.saveWhiteList();
    }
    
    @Override
    public void loadWhiteList() {
        this.readWhiteList();
    }
    
    private void loadOpsList() {
        try {
            this.getOps().clear();
            final BufferedReader var1 = new BufferedReader(new FileReader(this.opsList));
            String var2 = "";
            while ((var2 = var1.readLine()) != null) {
                this.getOps().add(var2.trim().toLowerCase());
            }
            var1.close();
        }
        catch (Exception var3) {
            this.getDedicatedServerInstance().getLogAgent().logWarning("Failed to load operators list: " + var3);
        }
    }
    
    private void saveOpsList() {
        try {
            final PrintWriter var1 = new PrintWriter(new FileWriter(this.opsList, false));
            for (final String var3 : this.getOps()) {
                var1.println(var3);
            }
            var1.close();
        }
        catch (Exception var4) {
            this.getDedicatedServerInstance().getLogAgent().logWarning("Failed to save operators list: " + var4);
        }
    }
    
    private void readWhiteList() {
        try {
            this.getWhiteListedPlayers().clear();
            final BufferedReader var1 = new BufferedReader(new FileReader(this.whiteList));
            String var2 = "";
            while ((var2 = var1.readLine()) != null) {
                this.getWhiteListedPlayers().add(var2.trim().toLowerCase());
            }
            var1.close();
        }
        catch (Exception var3) {
            this.getDedicatedServerInstance().getLogAgent().logWarning("Failed to load white-list: " + var3);
        }
    }
    
    private void saveWhiteList() {
        try {
            final PrintWriter var1 = new PrintWriter(new FileWriter(this.whiteList, false));
            for (final String var3 : this.getWhiteListedPlayers()) {
                var1.println(var3);
            }
            var1.close();
        }
        catch (Exception var4) {
            this.getDedicatedServerInstance().getLogAgent().logWarning("Failed to save white-list: " + var4);
        }
    }
    
    @Override
    public boolean isAllowedToLogin(String par1Str) {
        par1Str = par1Str.trim().toLowerCase();
        return !this.isWhiteListEnabled() || this.areCommandsAllowed(par1Str) || this.getWhiteListedPlayers().contains(par1Str);
    }
    
    public DedicatedServer getDedicatedServerInstance() {
        return (DedicatedServer)super.getServerInstance();
    }
    
    @Override
    public MinecraftServer getServerInstance() {
        return this.getDedicatedServerInstance();
    }
}
