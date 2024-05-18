package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import java.util.*;
import me.darkmagician6.morbid.*;
import me.darkmagician6.morbid.mods.manager.*;

public final class Xray extends ModBase
{
    public static ArrayList blocks;
    public static int opacity;
    private static final FileManager filemanager;
    
    public Xray() {
        super("Xray", "X", true, ".t xray");
        this.setDescription("Vedi attraverso i blocchi");
    }
    
    public void fastReRender() {
        if (MorbidWrapper.mcObj().g != null && MorbidWrapper.mcObj().e != null) {
            final int var0 = (int)MorbidWrapper.mcObj().g.u;
            final int var2 = (int)MorbidWrapper.mcObj().g.v;
            final int var3 = (int)MorbidWrapper.mcObj().g.w;
            MorbidWrapper.mcObj().f.a(var0 - 200, var2 - 200, var3 - 200, var0 + 200, var2 + 200, var3 + 200);
        }
    }
    
    public static FileManager getFileManager() {
        return Xray.filemanager;
    }
    
    @Override
    public void onEnable() {
        final FileManager filemanager = Xray.filemanager;
        if (!FileManager.Attivo) {
            Xray.filemanager.loadXrayList();
            final FileManager filemanager2 = Xray.filemanager;
            FileManager.Attivo = true;
        }
        MorbidWrapper.mcObj().f.a();
        this.fastReRender();
    }
    
    @Override
    public void onDisable() {
        MorbidWrapper.mcObj().f.a();
        this.fastReRender();
    }
    
    public static boolean isXrayBlock(final int id) {
        return Xray.blocks.contains(id);
    }
    
    @Override
    public void onCommand(final String paramString) {
        final String[] arrayOfString = paramString.split(" ");
        if (paramString.toLowerCase().startsWith(".xadd")) {
            try {
                final int d1 = Integer.parseInt(arrayOfString[1]);
                Morbid.getManager();
                final Xray xray = (Xray)ModManager.findMod(Xray.class);
                if (!Xray.blocks.contains(d1)) {
                    Morbid.getManager();
                    final Xray xray2 = (Xray)ModManager.findMod(Xray.class);
                    Xray.blocks.add(d1);
                    MorbidWrapper.addChat("§cAdded " + d1 + " to xray list.");
                    Morbid.getManager();
                    if (ModManager.findMod(Xray.class).isEnabled()) {
                        MorbidWrapper.mcObj().f.a();
                    }
                    Morbid.getFileManager().saveXrayList();
                }
                else {
                    MorbidWrapper.addChat("§c" + d1 + " is already in the xray list.");
                }
            }
            catch (Exception localException1) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .xadd [block id]");
            }
            ModBase.setCommandExists(true);
        }
        if (paramString.toLowerCase().startsWith(".xdel")) {
            try {
                final int d1 = Integer.parseInt(arrayOfString[1]);
                Morbid.getManager();
                final Xray xray3 = (Xray)ModManager.findMod(Xray.class);
                if (Xray.blocks.contains(d1)) {
                    Morbid.getManager();
                    final Xray xray4 = (Xray)ModManager.findMod(Xray.class);
                    final ArrayList blocks = Xray.blocks;
                    Morbid.getManager();
                    final Xray xray5 = (Xray)ModManager.findMod(Xray.class);
                    blocks.remove(Xray.blocks.indexOf(d1));
                    MorbidWrapper.addChat("§cRemoved " + d1 + " to xray list.");
                    Morbid.getManager();
                    if (ModManager.findMod(Xray.class).isEnabled()) {
                        MorbidWrapper.mcObj().f.a();
                    }
                    Morbid.getFileManager().saveXrayList();
                }
                else {
                    MorbidWrapper.addChat("§c" + d1 + " is not in the xray list.");
                }
            }
            catch (Exception localException1) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .xdel [block id]");
            }
            ModBase.setCommandExists(true);
        }
    }
    
    static {
        Xray.blocks = new ArrayList();
        Xray.opacity = 0;
        filemanager = new FileManager();
    }
}
