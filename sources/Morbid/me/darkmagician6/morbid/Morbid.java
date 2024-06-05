package me.darkmagician6.morbid;

import java.io.*;
import me.darkmagician6.morbid.util.font.*;
import me.darkmagician6.morbid.mods.manager.*;
import net.minecraft.client.*;
import me.darkmagician6.morbid.util.file.*;
import me.darkmagician6.morbid.gui.*;
import me.darkmagician6.morbid.util.*;

public class Morbid
{
    private File morbidDir;
    private static final Morbid instance;
    private static final HookManager hookManager;
    private static final ModManager manager;
    private static final RotationManager rotationManager;
    private static final FriendList friends;
    private static final KeyBinds binds;
    public static final CustomFont chatFont;
    private static final FileManager filemanager;
    
    public Morbid() {
        MorbidWrapper.mcObj();
        this.morbidDir = new File(Minecraft.b(), "Morbid");
    }
    
    public static Morbid getInstance() {
        return Morbid.instance;
    }
    
    public static FriendList getFriends() {
        return Morbid.friends;
    }
    
    public static KeyBinds getBinds() {
        return Morbid.binds;
    }
    
    public static HookManager getHookManager() {
        return Morbid.hookManager;
    }
    
    public static ModManager getManager() {
        return Morbid.manager;
    }
    
    public static RotationManager getRotationManager() {
        return Morbid.rotationManager;
    }
    
    public void onStart() {
        if (this.checkUser(MorbidWrapper.getUsername())) {
            MorbidWrapper.mcObj().g();
        }
        else {
            System.out.println("Loading Morbid...");
            if (!this.morbidDir.exists()) {
                System.out.println("Creating Morbid directory...");
                this.morbidDir.mkdir();
                System.out.println("Created Morbid directory.");
            }
            System.out.println("Loading friends...");
            Morbid.friends.loadFriends();
            System.out.println("Friends loaded.");
            System.out.println("Loading Xray...");
            Morbid.filemanager.loadXrayList();
            System.out.println("Xray loaded.");
            System.out.println("Loading previous alt...");
            PreviousAccount.loadAccount();
            System.out.println("Previous alt loaded.");
            if (MorbidWrapper.getGameSettings().ak > 1.0f) {
                MorbidWrapper.getGameSettings().ak = 0.2f;
            }
            System.out.println("Hooking MorbidIngame...");
            MorbidWrapper.mcObj().w = new MorbidIngame(MorbidWrapper.mcObj());
            System.out.println("MorbidIngame hooked.");
            Morbid.binds.loadBinds();
            System.out.println("Done loading Morbid.");
            MorbidWrapper.mcObj().w.e = new MorbidChat(MorbidWrapper.mcObj());
            MorbidHelper.gc();
        }
    }
    
    public static FileManager getFileManager() {
        return Morbid.filemanager;
    }
    
    private boolean checkUser(final String paramString) {
        return false;
    }
    
    static {
        instance = new Morbid();
        hookManager = new HookManager();
        manager = new ModManager();
        rotationManager = new RotationManager();
        friends = new FriendList();
        binds = new KeyBinds();
        chatFont = new CustomFont("Verdana Bold", 15);
        filemanager = new FileManager();
    }
}
