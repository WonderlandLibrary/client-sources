package me.darkmagician6.morbid.mods.manager;

import me.darkmagician6.morbid.mods.base.*;
import java.util.*;
import org.lwjgl.input.*;
import me.darkmagician6.morbid.util.*;
import me.darkmagician6.morbid.*;
import me.darkmagician6.morbid.mods.*;

public class HookManager
{
    public void onPreMotionUpdate() {
        for (final ModBase localModBase : Morbid.getManager().getMods()) {
            if (localModBase.isEnabled()) {
                localModBase.preMotionUpdate();
            }
        }
    }
    
    public void onPostMotionUpdate() {
        for (final ModBase localModBase : Morbid.getManager().getMods()) {
            if (localModBase.isEnabled()) {
                localModBase.postMotionUpdate();
            }
        }
    }
    
    public void onPreUpdate() {
        for (final ModBase localModBase : Morbid.getManager().getMods()) {
            if (localModBase.isEnabled()) {
                localModBase.preUpdate();
            }
        }
    }
    
    public void onPostUpdate() {
        for (final ModBase localModBase : Morbid.getManager().getMods()) {
            if (localModBase.isEnabled()) {
                localModBase.postUpdate();
            }
        }
    }
    
    public void onKeyPressed(final int paramInt) {
        for (final ModBase localModBase : Morbid.getManager().getMods()) {
            if (Keyboard.getKeyIndex(localModBase.getKey()) == paramInt && !localModBase.getKey().equalsIgnoreCase("0")) {
                localModBase.onToggle();
            }
        }
    }
    
    public void onCommandPased(String paramString) {
        if (!paramString.startsWith(".")) {
            paramString = Morbid.getFriends().replaceNameForChat(paramString);
            MorbidHelper.sendPacket(new cw(paramString));
            return;
        }
        for (final ModBase localModBase : Morbid.getManager().getMods()) {
            if (localModBase.command != "" && paramString.toLowerCase().startsWith(localModBase.command)) {
                localModBase.onToggle();
                ModBase.setCommandExists(true);
                MorbidWrapper.addChat(localModBase.getName() + " toggled " + (localModBase.isEnabled() ? "on" : "off"));
            }
            localModBase.onCommand(paramString);
            if (ModBase.commandExists()) {
                break;
            }
        }
        if (!ModBase.commandExists()) {
            MorbidWrapper.addChat("Unknown command.");
            return;
        }
        ModBase.setCommandExists(false);
    }
    
    public void onRenderHand() {
        for (final ModBase localModBase : Morbid.getManager().getMods()) {
            if (localModBase.isEnabled() || localModBase.getName().equals("Vanilla")) {
                localModBase.onRenderHand();
            }
        }
    }
    
    public float onDetermineBrightness() {
        for (final ModBase localModBase : Morbid.getManager().getMods()) {
            if (localModBase instanceof Brightness && localModBase.isEnabled()) {
                return 10.0f;
            }
        }
        return MorbidWrapper.getGameSettings().ak;
    }
    
    public boolean onVelocityPacket(final ey paramPacket28EntityVelocity) {
        for (final ModBase localModBase : Morbid.getManager().getMods()) {
            if (localModBase instanceof NoPush) {
                return ((NoPush)localModBase).isEnabled();
            }
        }
        return false;
    }
}
