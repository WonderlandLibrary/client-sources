/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 */
package pw.vertexcode.nemphis;

import java.io.File;
import java.io.IOException;
import java.util.List;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import pw.vertexcode.nemphis.command.CommandManager;
import pw.vertexcode.nemphis.events.GameStartEvent;
import pw.vertexcode.nemphis.events.GameStopEvent;
import pw.vertexcode.nemphis.friend.FriendManager;
import pw.vertexcode.nemphis.module.ModuleManager;
import pw.vertexcode.nemphis.utils.BindManager;
import pw.vertexcode.util.Nameable;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.module.types.ToggleableModule;

public class Nemphis
implements Nameable {
    public String domain = "http://razex.pw";
    public static Nemphis instance;
    public double clientVersion;
    public ResourceLocation background = new ResourceLocation("nemphis/bg.jpg");
    public File directory;
    public ModuleManager modulemanager;
    public CommandManager commandManager;
    public BindManager bindManager;
    public FriendManager friendManager;

    @EventListener
    public void onStart(GameStartEvent e) {
        instance = this;
        this.clientVersion = 0.3;
        this.commandManager = new CommandManager();
        try {
            this.friendManager = new FriendManager();
            this.friendManager.load();
        }
        catch (ClassNotFoundException var2_2) {
        }
        catch (IOException var2_3) {
            // empty catch block
        }
        this.directory = new File(System.getProperty("user.home"), "Nemphis");
        if (!this.directory.exists()) {
            this.directory.mkdir();
        }
        Display.setTitle((String)(String.valueOf(this.getName()) + " v" + this.clientVersion));
        this.modulemanager = new ModuleManager();
        for (ToggleableModule module : this.modulemanager.getMods()) {
            try {
                module.load();
                continue;
            }
            catch (ClassNotFoundException e1) {
                e1.printStackTrace();
                continue;
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @EventListener
    public void onStop(GameStopEvent e) {
        Display.setTitle((String)"Minecraft 1.8.8");
    }

    @Override
    public String getName() {
        return "Nemphis";
    }

    public String getPrefix() {
        return "\u00a74[Nemphis] \u00a77";
    }
}

