package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import java.util.*;
import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.mods.manager.*;

public final class Xray extends ModBase
{
    public static ArrayList blocks;
    public static int opacity;
    private static final FileManager filemanager;
    
    static {
        Xray.blocks = new ArrayList();
        Xray.opacity = 0;
        filemanager = new FileManager();
    }
    
    public Xray() {
        super("Xray", "X", true, ".t xray");
        this.setDescription("Vedi attraverso i blocchi");
    }
    
    public void fastReRender() {
        MorbidWrapper.mcObj();
        if (Minecraft.thePlayer != null) {
            MorbidWrapper.mcObj();
            if (Minecraft.theWorld != null) {
                MorbidWrapper.mcObj();
                final int var1 = (int)Minecraft.thePlayer.posX;
                MorbidWrapper.mcObj();
                final int var2 = (int)Minecraft.thePlayer.posY;
                MorbidWrapper.mcObj();
                final int var3 = (int)Minecraft.thePlayer.posZ;
                MorbidWrapper.mcObj().renderGlobal.markBlockRangeForRenderUpdate(var1 - 200, var2 - 200, var3 - 200, var1 + 200, var2 + 200, var3 + 200);
            }
        }
    }
    
    public static FileManager getFileManager() {
        return Xray.filemanager;
    }
    
    @Override
    public void onEnable() {
        FileManager var10000 = Xray.filemanager;
        if (!FileManager.Attivo) {
            Xray.filemanager.loadXrayList();
            var10000 = Xray.filemanager;
            FileManager.Attivo = true;
        }
        MorbidWrapper.mcObj().renderGlobal.loadRenderers();
        this.fastReRender();
    }
    
    @Override
    public void onDisable() {
        MorbidWrapper.mcObj().renderGlobal.loadRenderers();
        this.fastReRender();
    }
    
    public static boolean isXrayBlock(final int var0) {
        return Xray.blocks.contains(var0);
    }
    
    @Override
    public void onCommand(final String var1) {
        final String[] var2 = var1.split(" ");
        if (var1.toLowerCase().startsWith(".xadd")) {
            try {
                final int var3 = Integer.parseInt(var2[1]);
                Morbid.getManager();
                Xray var4 = (Xray)ModManager.findMod(Xray.class);
                if (!Xray.blocks.contains(var3)) {
                    Morbid.getManager();
                    var4 = (Xray)ModManager.findMod(Xray.class);
                    Xray.blocks.add(var3);
                    MorbidWrapper.addChat("§cAdded " + var3 + " to xray list.");
                    Morbid.getManager();
                    if (ModManager.findMod(Xray.class).isEnabled()) {
                        MorbidWrapper.mcObj().renderGlobal.loadRenderers();
                    }
                    Morbid.getFileManager().saveXrayList();
                }
                else {
                    MorbidWrapper.addChat("§c" + var3 + " is already in the xray list.");
                }
            }
            catch (Exception var7) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .xadd [block id]");
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".xdel")) {
            try {
                final int var3 = Integer.parseInt(var2[1]);
                Morbid.getManager();
                Xray var4 = (Xray)ModManager.findMod(Xray.class);
                if (Xray.blocks.contains(var3)) {
                    Morbid.getManager();
                    var4 = (Xray)ModManager.findMod(Xray.class);
                    final ArrayList var5 = Xray.blocks;
                    Morbid.getManager();
                    final Xray var6 = (Xray)ModManager.findMod(Xray.class);
                    var5.remove(Xray.blocks.indexOf(var3));
                    MorbidWrapper.addChat("§cRemoved " + var3 + " to xray list.");
                    Morbid.getManager();
                    if (ModManager.findMod(Xray.class).isEnabled()) {
                        MorbidWrapper.mcObj().renderGlobal.loadRenderers();
                    }
                    Morbid.getFileManager().saveXrayList();
                }
                else {
                    MorbidWrapper.addChat("§c" + var3 + " is not in the xray list.");
                }
            }
            catch (Exception var8) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .xdel [block id]");
            }
            ModBase.setCommandExists(true);
        }
    }
}
