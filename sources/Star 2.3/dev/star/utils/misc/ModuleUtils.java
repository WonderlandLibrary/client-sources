package dev.star.utils.misc;

import dev.star.Client;
import dev.star.module.api.ModuleCollection;
import dev.star.module.impl.combat.KillAura;
import dev.star.module.impl.movement.LongJump;
import dev.star.module.impl.movement.Scaffold;
import dev.star.module.impl.movement.Speed;
import dev.star.module.impl.player.Blink;
import dev.star.module.impl.render.Animations;
import dev.star.module.impl.display.HUDMod;

public class ModuleUtils
{

    private static ModuleCollection moduleManager;

    private static ModuleCollection getModuleManager() {
        if(moduleManager == null) {
            moduleManager = Client.INSTANCE.getModuleCollection();
        }

        return moduleManager;
    }

    public static KillAura getKillaura() {
        return getModuleManager().getModule(KillAura.class);
    }





    public static Speed getSpeed() {
        return getModuleManager().getModule(Speed.class);
    }

    public static Scaffold getScaffold() {
        return getModuleManager().getModule(Scaffold.class);
    }

    public static HUDMod getHUD() {
        return getModuleManager().getModule(HUDMod.class);
    }

    public static LongJump getLongjump() {
        return getModuleManager().getModule(LongJump.class);
    }
    public static Animations getAnimations() {
        return getModuleManager().getModule(Animations.class);
    }

//    public static IncomingFakelag getIncomingFakelag() {
//        return getModuleManager().getModule(IncomingFakelag.class);
//    }
//
//    public static OutgoingFakelag getOutgoingFakelag() {
//        return getModuleManager().getModule(OutgoingFakelag.class);
//    }

    public static Blink getBlink() {
        return getModuleManager().getModule(Blink.class);
    }




}
