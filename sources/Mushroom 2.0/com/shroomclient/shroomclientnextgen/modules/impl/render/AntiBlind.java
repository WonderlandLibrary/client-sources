package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

@RegisterModule(
    name = "Anti-Effects",
    uniqueId = "antiblind",
    description = "Remove Certain Effects",
    category = ModuleCategory.Render
)
public class AntiBlind extends Module {

    @ConfigParentId("hidefire")
    @ConfigOption(name = "Hide Fire", description = "", order = 2)
    public static Boolean hideFire = true;

    @ConfigOption(
        name = "Hide Blindness",
        description = "Hides Blindness & Nausea",
        order = 2.1
    )
    public static Boolean gnblind = true;

    @ConfigChild("hidefire")
    @ConfigOption(
        name = "Fire Opacity",
        description = "",
        order = 3,
        max = 1,
        precision = 2
    )
    public static Float fireOpacity = 0.5f;

    @ConfigChild("hidefire")
    @ConfigOption(
        name = "Fire Y Offset",
        description = "",
        order = 4,
        max = 0.5f,
        precision = 2
    )
    public static Float fireOffset = 1f;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent(EventBus.Priority.HIGHEST)
    public void onRenderTick(RenderTickEvent e) {
        if (gnblind) {
            if (C.p() == null) return;
            StatusEffect blindnessEffect = StatusEffects.BLINDNESS;
            StatusEffect nauseaEffect = StatusEffects.NAUSEA;
            if (C.p().hasStatusEffect(blindnessEffect)) {
                C.p().removeStatusEffect(blindnessEffect);
            }
            if (C.p().hasStatusEffect(nauseaEffect)) {
                C.p().removeStatusEffect(nauseaEffect);
            }
        }
    }

    public static float fireOpacity() {
        return ModuleManager.isEnabled(AntiBlind.class) && hideFire
            ? fireOpacity
            : 1;
    }

    public static float fireOffset() {
        return ModuleManager.isEnabled(AntiBlind.class) && hideFire
            ? fireOffset
            : 0;
    }
}
