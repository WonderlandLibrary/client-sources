package cn.Hanabi;

import org.jetbrains.annotations.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.command.*;
import cn.Hanabi.utils.fontmanager.*;
import cn.Hanabi.hmlProject.*;
import java.text.*;
import java.lang.management.*;
import com.darkmagician6.eventapi.*;
import ClassSub.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class Hanabi
{
    @NotNull
    public static final String CLIENT_NAME = "Hanabi";
    @NotNull
    public static final String CLIENT_AUTHOR = "Margele";
    public static final double CLIENT_VERSION_NUMBER = 1.5;
    @NotNull
    public static final String CLIENT_VERSION = "1.5.1";
    @NotNull
    public static final String CLIENT_INITIALS;
    public static Hanabi INSTANCE;
    public ModManager moduleManager;
    public CommandManager commandManager;
    public Class150 fileManager;
    public FontManager fontManager;
    public Class272 rotate;
    public boolean loadFont;
    public Class275 altFileMgr;
    public Class286 musicMgr;
    public Class287 sbm;
    public static int flag;
    public HMLManager hmlManager;
    
    
    public Hanabi() {
        this.loadFont = true;
        Hanabi.INSTANCE = this;
    }
    
    public void startClient() {
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Hanabi.flag = simpleDateFormat.parse(Class211.fuckman).compareTo(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
        if (!ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";")[0].contains("\\lib\\") || ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";")[0].replace("l", "I").contains("\\lib\\")) {
            Hanabi.flag = -new Random().nextInt(20);
        }
        if (!Class211.c4n || !Class211.cr4ckm3 || !Class211.If || !Class211.y0u || !ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";")[0].contains("\\lib\\")) {
            Hanabi.flag = -new Random().nextInt(20);
        }
        final Iterator<Map.Entry<Object, Object>> iterator = System.getProperties().entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey().toString().contains("http.proxy")) {
                Hanabi.flag = -new Random().nextInt(20);
            }
        }
        (this.hmlManager = new HMLManager()).onClientStarted(this);
        if (Class211.c4n && ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";")[0].contains("\\lib\\")) {
            this.fileManager = new Class150();
        }
        if (Class211.cr4ckm3 && ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";")[0].contains("\\lib\\")) {
            this.commandManager = new CommandManager();
        }
        if (Class211.y0u && ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";")[0].contains("\\lib\\")) {
            this.moduleManager = new ModManager();
        }
        if (Class211.If && ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";")[0].contains("\\lib\\")) {
            this.fontManager = new FontManager();
        }
        this.fontManager.initFonts();
        EventManager.register(this.rotate = new Class272());
        EventManager.register(new Class190());
        this.commandManager.addCommands();
        if (Class211.c4n && Class211.cr4ckm3 && Class211.If && Class211.y0u && ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";")[0].contains("\\lib\\")) {
            this.moduleManager.addModules();
        }
        this.fileManager.load();
        (this.altFileMgr = new Class275()).loadFiles();
        this.musicMgr = new Class286();
        Class120.notifications.clear();
        Display.setTitle("Hanabi 1.5.1");
    }
    
    public void stopClient() {
        try {
            this.fileManager.save();
        }
        catch (Exception ex) {
            System.err.println("Failed to save settings:");
            ex.printStackTrace();
        }
    }
    
    static {
        final ArrayList<Character> list = new ArrayList<Character>();
        for (final char c : "Hanabi".toCharArray()) {
            if (Character.toUpperCase(c) == c) {
                list.add(c);
            }
        }
        final char[] array2 = new char[list.size()];
        for (int j = 0; j < list.size(); ++j) {
            array2[j] = (char)list.get(j);
        }
        CLIENT_INITIALS = new String(array2);
    }
}
