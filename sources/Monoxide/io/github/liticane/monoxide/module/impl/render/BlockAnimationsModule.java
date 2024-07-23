package io.github.liticane.monoxide.module.impl.render;

import io.github.liticane.monoxide.module.ModuleManager;
import net.minecraft.item.ItemSword;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.impl.combat.KillAuraModule;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.ModeValue;

//Hooked in ItemRenderer class && Minecraft class
@ModuleData(name = "BlockAnimations", description = "Changes your item blocking animation", category = ModuleCategory.RENDER)
public class BlockAnimationsModule extends Module {

    public final ModeValue mode = new ModeValue("Style", this, new String[]{"1.7", "Atani", "Atani 2", "Smooth", "Exhibition", "Flux", "Swang", "Swong", "Remix", "Spin", "Warped", "Push", "Slide"});
    public final BooleanValue blockHit = new BooleanValue("Block Hit", this, true);
    public final BooleanValue fake = new BooleanValue("Fake", this, false);
    public final ModeValue fakeMode = new ModeValue("Fake Time", this, new String[] {"Hitting", "KillAura", "Always"});
    public final BooleanValue swordOnly = new BooleanValue("Swords Only", this, true);

    private KillAuraModule killAura;

    public boolean shouldFake() {
        if(killAura == null)
            killAura = ModuleManager.getInstance().getModule(KillAuraModule.class);
        if(fake.getValue()) {
            if(mc.thePlayer.getHeldItem() == null)
                return false;
            if(swordOnly.getValue() && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword))
                return  false;
            switch (fakeMode.getValue()) {
                case "Always":
                    return true;
                case "KillAura":
                    return killAura.isEnabled() && KillAuraModule.currentTarget != null;
                case "Hitting":
                    return mc.thePlayer.swingProgress > 0;

            }
        }
        return false;
    }

}