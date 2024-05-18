package net.minecraft.src;

import java.util.*;
import java.io.*;

public class PropertyManager
{
    private final Properties properties;
    private final ILogAgent logger;
    private final File associatedFile;
    
    public PropertyManager(final File par1File, final ILogAgent par2ILogAgent) {
        this.properties = new Properties();
        this.associatedFile = par1File;
        this.logger = par2ILogAgent;
        if (par1File.exists()) {
            FileInputStream var3 = null;
            try {
                var3 = new FileInputStream(par1File);
                this.properties.load(var3);
            }
            catch (Exception var4) {
                par2ILogAgent.logWarningException("Failed to load " + par1File, var4);
                this.logMessageAndSave();
            }
            finally {
                if (var3 != null) {
                    try {
                        var3.close();
                    }
                    catch (IOException ex) {}
                }
            }
            if (var3 != null) {
                try {
                    var3.close();
                }
                catch (IOException ex2) {}
            }
        }
        else {
            par2ILogAgent.logWarning(par1File + " does not exist");
            this.logMessageAndSave();
        }
    }
    
    public void logMessageAndSave() {
        this.logger.logInfo("Generating new properties file");
        this.saveProperties();
    }
    
    public void saveProperties() {
        FileOutputStream var1 = null;
        try {
            var1 = new FileOutputStream(this.associatedFile);
            this.properties.store(var1, "Minecraft server properties");
        }
        catch (Exception var2) {
            this.logger.logWarningException("Failed to save " + this.associatedFile, var2);
            this.logMessageAndSave();
        }
        finally {
            if (var1 != null) {
                try {
                    var1.close();
                }
                catch (IOException ex) {}
            }
        }
        if (var1 != null) {
            try {
                var1.close();
            }
            catch (IOException ex2) {}
        }
    }
    
    public File getPropertiesFile() {
        return this.associatedFile;
    }
    
    public String getProperty(final String par1Str, final String par2Str) {
        if (!this.properties.containsKey(par1Str)) {
            this.properties.setProperty(par1Str, par2Str);
            this.saveProperties();
        }
        return this.properties.getProperty(par1Str, par2Str);
    }
    
    public int getIntProperty(final String par1Str, final int par2) {
        try {
            return Integer.parseInt(this.getProperty(par1Str, new StringBuilder().append(par2).toString()));
        }
        catch (Exception var4) {
            this.properties.setProperty(par1Str, new StringBuilder().append(par2).toString());
            return par2;
        }
    }
    
    public boolean getBooleanProperty(final String par1Str, final boolean par2) {
        try {
            return Boolean.parseBoolean(this.getProperty(par1Str, new StringBuilder().append(par2).toString()));
        }
        catch (Exception var4) {
            this.properties.setProperty(par1Str, new StringBuilder().append(par2).toString());
            return par2;
        }
    }
    
    public void setProperty(final String par1Str, final Object par2Obj) {
        this.properties.setProperty(par1Str, new StringBuilder().append(par2Obj).toString());
    }
}
