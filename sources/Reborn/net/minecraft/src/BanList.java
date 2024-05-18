package net.minecraft.src;

import net.minecraft.server.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class BanList
{
    private final LowerStringMap theBanList;
    private final File fileName;
    private boolean listActive;
    
    public BanList(final File par1File) {
        this.theBanList = new LowerStringMap();
        this.listActive = true;
        this.fileName = par1File;
    }
    
    public boolean isListActive() {
        return this.listActive;
    }
    
    public void setListActive(final boolean par1) {
        this.listActive = par1;
    }
    
    public Map getBannedList() {
        this.removeExpiredBans();
        return this.theBanList;
    }
    
    public boolean isBanned(final String par1Str) {
        if (!this.isListActive()) {
            return false;
        }
        this.removeExpiredBans();
        return this.theBanList.containsKey(par1Str);
    }
    
    public void put(final BanEntry par1BanEntry) {
        this.theBanList.putLower(par1BanEntry.getBannedUsername(), par1BanEntry);
        this.saveToFileWithHeader();
    }
    
    public void remove(final String par1Str) {
        this.theBanList.remove(par1Str);
        this.saveToFileWithHeader();
    }
    
    public void removeExpiredBans() {
        final Iterator var1 = this.theBanList.values().iterator();
        while (var1.hasNext()) {
            final BanEntry var2 = var1.next();
            if (var2.hasBanExpired()) {
                var1.remove();
            }
        }
    }
    
    public void loadBanList() {
        if (this.fileName.isFile()) {
            BufferedReader var1;
            try {
                var1 = new BufferedReader(new FileReader(this.fileName));
            }
            catch (FileNotFoundException ex) {
                throw new Error();
            }
            try {
                String var2 = null;
                do {
                    if (!var2.startsWith("#")) {
                        final BanEntry var3 = BanEntry.parse(var2);
                        if (var3 == null) {
                            continue;
                        }
                        this.theBanList.putLower(var3.getBannedUsername(), var3);
                    }
                } while ((var2 = var1.readLine()) != null);
            }
            catch (IOException var4) {
                MinecraftServer.getServer().getLogAgent().logSevereException("Could not load ban list", var4);
            }
        }
    }
    
    public void saveToFileWithHeader() {
        this.saveToFile(true);
    }
    
    public void saveToFile(final boolean par1) {
        this.removeExpiredBans();
        try {
            final PrintWriter var2 = new PrintWriter(new FileWriter(this.fileName, false));
            if (par1) {
                var2.println("# Updated " + new SimpleDateFormat().format(new Date()) + " by Minecraft " + "1.5.2");
                var2.println("# victim name | ban date | banned by | banned until | reason");
                var2.println();
            }
            for (final BanEntry var4 : this.theBanList.values()) {
                var2.println(var4.buildBanString());
            }
            var2.close();
        }
        catch (IOException var5) {
            MinecraftServer.getServer().getLogAgent().logSevereException("Could not save ban list", var5);
        }
    }
}
