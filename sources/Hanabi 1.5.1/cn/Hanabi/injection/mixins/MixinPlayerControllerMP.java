package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;
import org.spongepowered.asm.mixin.injection.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.modules.Combat.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ PlayerControllerMP.class })
public class MixinPlayerControllerMP implements IPlayerControllerMP
{
    @Shadow
    private WorldSettings.GameType currentGameType;
    @Shadow
    private float curBlockDamageMP;
    @Shadow
    private int blockHitDelay;
    
    @Inject(method = { "attackEntity" }, at = { @At("HEAD") })
    public void attack(final EntityPlayer playerIn, final Entity targetEntity, final CallbackInfo info) {
        EventManager.call(new EventAttack(targetEntity));
    }
    
    @Overwrite
    public float getBlockReachDistance() {
        if (ModManager.getModule("Reach").isEnabled() && !ModManager.getModule("TPHit").isEnabled()) {
            return (float)Reach.getReach() + 1.5f;
        }
        return this.currentGameType.isCreative() ? 5.0f : 4.5f;
    }
    
    @Override
    public float getCurBlockDamageMP() {
        return this.curBlockDamageMP;
    }
    
    @Override
    public void setCurBlockDamageMP(final float f) {
        this.curBlockDamageMP = f;
    }
    
    @Override
    public void setBlockHitDelay(final int i) {
        this.blockHitDelay = i;
    }
}
