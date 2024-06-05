package alphentus.mod;

import alphentus.event.Event;
import alphentus.event.Type;


import alphentus.mod.mods.combat.*;
import alphentus.mod.mods.hud.*;
import alphentus.mod.mods.movement.*;
import alphentus.mod.mods.player.*;
import alphentus.mod.mods.visuals.*;

import alphentus.mod.mods.world.Scaffold;
import alphentus.mod.mods.world.Tower;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import java.util.ArrayList;

/**
 * @author avox
 * @since on 29/07/2020.
 */
public class ModManager {

    final ArrayList<Mod> modArrayList = new ArrayList<Mod>();

    public ModManager () {
        // COMBAT
        addMod(new Teams());
        addMod(new KillAura());
        addMod(new Velocity());
        addMod(new AntiBots());
        addMod(new NoFriends());
        addMod(new Triggerbot());

        // MOVEMENT
        addMod(new Sprint());
        addMod(new WaterSpeed());
        addMod(new NoFall());
        addMod(new Speed());
        addMod(new LongJump());
        addMod(new Fly());
        addMod(new TargetStrafe());
        addMod(new Step());
        addMod(new StairSpeed());
        addMod(new AirStuck());
        addMod(new InventoryWalk());

        // PLAYER
        addMod(new FastPlace());
        addMod(new FastUse());
        addMod(new ChestStealer());
        addMod(new Teleport());
        addMod(new MouseDelayFix());
        addMod(new AutoArmor());
        addMod(new InventoryManager());
        addMod(new FastBridge());
        addMod(new SafeWalk());
        addMod(new MCF());
        addMod(new AntiVoid());
        addMod(new Spammer());
        addMod(new AutoPotion());

        // VISUALS
        addMod(new ClickGUI());
        addMod(new NoBob());
        addMod(new NoHurtcam());
        addMod(new NoFov());
        addMod(new NameTags());
        addMod(new PlayerESP());
        addMod(new ItemESP());
        addMod(new ChestESP());
        addMod(new CustomHit());
        addMod(new Cosmetics());

        // HUD
        addMod(new HUD());
        addMod(new alphentus.mod.mods.hud.ArrayList());
        addMod(new TabGUI());
        addMod(new Hotbar());
        addMod(new KeyStrokes());
        addMod(new Effects());
        addMod(new Crosshair());
        addMod(new FakeBlock());

        // WORLD
        addMod(new Scaffold());
        addMod(new Tower());

        EventManager.register(this);
    }

    public ArrayList<Mod> getModArrayList () {
        return modArrayList;
    }

    @EventTarget
    public void onKey (Event e) {
        if (e.getType() != Type.KEYPRESS)
            return;

        for (Mod mod : modArrayList) {
            if (mod.getKeybind() == e.getKey()) {
                mod.setState(!mod.getState());
            }
        }

        if (getModuleByClass(TabGUI.class).getState())
            getModuleByClass(TabGUI.class).keyTyped(e.getKey());

    }

    public <T extends Mod> T getModuleByClass (Class<T> tClass) {
        for (Mod mod : getModArrayList()) {
            if (mod.getClass() == tClass) {
                return (T) mod;
            }

        }
        return (T) null;
    }

    public void addMod (Mod mod) {
        this.modArrayList.add(mod);
        EventManager.register(mod);
    }

}
