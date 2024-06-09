/*
 * Decompiled with CFR 0_122.
 */
package winter;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import winter.console.Console;
import winter.module.Manager;
import winter.module.Module;
import winter.module.modules.waypoint.WaypointUtil;
import winter.utils.file.FileManager;
import winter.utils.friend.FriendUtil;
import winter.utils.other.alts.AltManager;
import winter.utils.other.alts.files.AltFileManager;
import winter.utils.other.alts.files.CustomFile;
import winter.utils.render.click.ClickGui;
import winter.utils.render.menu.Menu;
import winter.utils.render.overlays.Hud;
import winter.utils.render.overlays.TabGui;

public class Client {
    public static Hud hud;
    public static Manager mods;
    public static final String NAME = "Winter";
    public static final String VERSION = "7.2";
    public static final String CREATOR = "Lemon";
    public static ClickGui gui;
    public static Menu menu;
    public static FileManager fileManager;
    public static AltManager altManager;
    private static CustomFile customFile;
    public static AltFileManager altFiles;
    public static Console console;

    public static Hud getHud() {
        return hud;
    }

    public static Manager getManager() {
        return mods;
    }

    public static boolean isEnabled(String mod) {
        return Client.getManager().getMod(mod).isEnabled();
    }

    public static boolean clientSetup() {
        hud = new Hud();
        mods = new Manager();
        mods.setup();
        gui = new ClickGui();
        menu = new Menu();
        fileManager = new FileManager();
        fileManager.loadFiles();
        altManager = new AltManager();
        altFiles = new AltFileManager();
        altManager = new AltManager();
        console = new Console();
        TabGui.setup();
        FriendUtil.setup();
        WaypointUtil.setup();
        Client.loadFiles();
        return true;
    }

    public static void loadFiles() {
        for (CustomFile f2 : AltFileManager.Files) {
            f2.loadFile();
        }
    }

    public static void saveFiles() {
        for (CustomFile f2 : AltFileManager.Files) {
            f2.saveFile();
        }
    }

    public static Block getBlock(double x2, double y2, double z2) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x2, y2, z2)).getBlock();
    }

    public static Block getBlock(BlockPos block) {
        return Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock();
    }
}

