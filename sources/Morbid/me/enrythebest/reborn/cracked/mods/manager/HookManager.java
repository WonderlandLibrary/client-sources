package me.enrythebest.reborn.cracked.mods.manager;

import me.enrythebest.reborn.cracked.mods.base.*;
import java.util.*;
import org.lwjgl.input.*;
import me.enrythebest.reborn.cracked.util.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.src.*;
import me.enrythebest.reborn.cracked.mods.*;

public class HookManager
{
    public void onPreMotionUpdate() {
        Morbid.getManager();
        for (final ModBase var2 : ModManager.getMods()) {
            if (var2.isEnabled()) {
                var2.preMotionUpdate();
            }
        }
    }
    
    public void onPostMotionUpdate() {
        Morbid.getManager();
        for (final ModBase var2 : ModManager.getMods()) {
            if (var2.isEnabled()) {
                var2.postMotionUpdate();
            }
        }
    }
    
    public void onPreUpdate() {
        Morbid.getManager();
        for (final ModBase var2 : ModManager.getMods()) {
            if (var2.isEnabled()) {
                var2.preUpdate();
            }
        }
    }
    
    public void onPostUpdate() {
        Morbid.getManager();
        for (final ModBase var2 : ModManager.getMods()) {
            if (var2.isEnabled()) {
                var2.postUpdate();
            }
        }
    }
    
    public void onKeyPressed(final int var1) {
        Morbid.getManager();
        for (final ModBase var3 : ModManager.getMods()) {
            if (Keyboard.getKeyIndex(var3.getKey()) == var1 && !var3.getKey().equalsIgnoreCase("0")) {
                var3.onToggle();
            }
        }
    }
    
    public void onCommandPased(String var1) {
        if (!var1.startsWith(".")) {
            var1 = Morbid.getFriends().replaceNameForChat(var1);
            MorbidHelper.sendPacket(new Packet3Chat(var1));
        }
        else {
            Morbid.getManager();
            for (final ModBase var3 : ModManager.getMods()) {
                if (var3.command != "" && var1.toLowerCase().startsWith(var3.command)) {
                    var3.onToggle();
                    ModBase.setCommandExists(true);
                }
                var3.onCommand(var1);
                if (ModBase.commandExists()) {
                    break;
                }
            }
            if (!ModBase.commandExists()) {
                MorbidWrapper.addChat("Unknown command.");
            }
            else {
                ModBase.setCommandExists(false);
            }
        }
    }
    
    public void onRenderHand() {
        Morbid.getManager();
        for (final ModBase var2 : ModManager.getMods()) {
            if (var2.isEnabled() || var2.getName().equals("Vanilla")) {
                var2.onRenderHand();
            }
        }
    }
    
    public float onDetermineBrightness() {
        Morbid.getManager();
        for (final ModBase var2 : ModManager.getMods()) {
            if (var2 instanceof Brightness && var2.isEnabled()) {
                return 10.0f;
            }
        }
        return MorbidWrapper.getGameSettings().gammaSetting;
    }
    
    public boolean onVelocityPacket(final Packet28EntityVelocity var1) {
        Morbid.getManager();
        for (final ModBase var3 : ModManager.getMods()) {
            if (var3 instanceof NoPush) {
                return ((NoPush)var3).isEnabled();
            }
        }
        return false;
    }
}
