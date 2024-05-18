package com.krazzzzymonkey.catalyst.module.modules.player;

import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraft.entity.Entity;
import com.krazzzzymonkey.catalyst.utils.Utils;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import net.minecraft.init.MobEffects;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.module.Modules;

public class AutoSprint extends Modules
{
    boolean canSprint() {
        if (!(Wrapper.INSTANCE.player().onGround)) {
            return false;
        }
        if ((Wrapper.INSTANCE.player().isSprinting())) {
            return false;
        }
        if ((Wrapper.INSTANCE.player().isOnLadder())) {
            return false;
        }
        if ((Wrapper.INSTANCE.player().isInWater())) {
            return false;
        }
        if ((Wrapper.INSTANCE.player().isInLava())) {
            return false;
        }
        if ((Wrapper.INSTANCE.player().collidedHorizontally)) {
            return false;
        }
        if (fcmpg(Wrapper.INSTANCE.player().moveForward < 0.1f)) {
            return false;
        }
        if ((Wrapper.INSTANCE.player().isSneaking())) {
            return false;
        }
        if ((Wrapper.INSTANCE.player().getFoodStats().getFoodLevel() < 6)) {
            return false;
        }
        if ((Wrapper.INSTANCE.player().isRiding())) {
            return false;
        }
        if ((Wrapper.INSTANCE.player().isPotionActive(MobEffects.BLINDNESS))) {
            return false;
        }
        return true;
    }
    
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent tickEvent) {
        if (this.canSprint()) {
            Wrapper.INSTANCE.player().setSprinting(Utils.isMoving((Entity)Wrapper.INSTANCE.player()));
        }
        super.onClientTick(tickEvent);
    }
    
    public AutoSprint() {
        super("AutoSprint", ModuleCategory.PLAYER);
    }
    
}
