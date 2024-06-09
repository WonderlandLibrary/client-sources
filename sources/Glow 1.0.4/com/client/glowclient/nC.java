package com.client.glowclient;

import java.lang.ref.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import java.io.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import java.util.*;

public class nc extends bc
{
    private WeakReference<MinecraftServer> b;
    
    @Override
    public void M(final FMLServerStartingEvent fmlServerStartingEvent) {
        super.M(fmlServerStartingEvent);
        fmlServerStartingEvent.registerServerCommand((ICommand)new Db());
        this.b = new WeakReference<MinecraftServer>(fmlServerStartingEvent.getServer());
    }
    
    @Override
    public boolean M(final EntityPlayer entityPlayer, final File file, final String s) {
        return false;
    }
    
    public nc() {
        final WeakReference<MinecraftServer> b = null;
        super();
        this.b = b;
    }
    
    @Override
    public File M() {
        final MinecraftServer minecraftServer = (this.b != null) ? this.b.get() : null;
        final File file = (minecraftServer != null) ? minecraftServer.getFile(".") : new File(".");
        try {
            return file.getCanonicalFile();
        }
        catch (IOException ex) {
            ld.H.warn("Could not canonize path!", (Throwable)ex);
            return file;
        }
    }
    
    @Override
    public void M(final FMLInitializationEvent fmlInitializationEvent) {
        super.M(fmlInitializationEvent);
        MinecraftForge.EVENT_BUS.register((Object)NB.b);
    }
    
    @Override
    public boolean M(final EntityPlayer entityPlayer) {
        return (0 + this.M(this.M(entityPlayer, true)) + this.M(this.M(entityPlayer, false))) / 1024 > SC.I;
    }
    
    @Override
    public File M(final EntityPlayer entityPlayer, final boolean b) {
        final UUID uniqueID;
        if ((uniqueID = entityPlayer.getUniqueID()) == null) {
            ld.H.warn("Unable to identify player {}", (Object)entityPlayer.toString());
            return null;
        }
        final File file = new File(SC.Ga.getAbsolutePath(), uniqueID.toString());
        if (b) {
            return new File(file, "private");
        }
        return new File(file, "public");
    }
    
    private int M(final File file) {
        int n = 0;
        if (file == null || !file.exists()) {
            return 0;
        }
        File[] listFiles;
        if ((listFiles = file.listFiles()) == null) {
            listFiles = new File[0];
        }
        final File[] array;
        final int length = (array = listFiles).length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            final File file2 = array[n2];
            final long n3 = n;
            final long length2 = file2.length();
            ++n2;
            n = (int)(n3 + length2);
            i = n2;
        }
        return n;
    }
}
