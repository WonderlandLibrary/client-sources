package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import baritone.api.event.events.*;
import baritone.api.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ vp.class })
public abstract class MixinEntityLivingBase extends vg
{
    private RotationMoveEvent jumpRotationEvent;
    
    public MixinEntityLivingBase(final amu amu, final RotationMoveEvent jumpRotationEvent) {
        super(amu);
        this.jumpRotationEvent = jumpRotationEvent;
    }
    
    @Inject(method = { "jump" }, at = { @At("HEAD") })
    private void preMoveRelative(final CallbackInfo callbackInfo) {
        final IBaritone baritoneForPlayer;
        if (bud.class.isInstance(this) && (baritoneForPlayer = BaritoneAPI.getProvider().getBaritoneForPlayer((bud)this)) != null) {
            this.jumpRotationEvent = new RotationMoveEvent(RotationMoveEvent$Type.JUMP, this.v);
            baritoneForPlayer.getGameEventHandler().onPlayerRotationMove(this.jumpRotationEvent);
        }
    }
    
    @Redirect(method = { "jump" }, at = @At(value = "FIELD", opcode = 180, target = "net/minecraft/entity/EntityLivingBase.rotationYaw:F"))
    private float overrideYaw(final vp vp) {
        if (vp instanceof bud && BaritoneAPI.getProvider().getBaritoneForPlayer((bud)this) != null) {
            return this.jumpRotationEvent.getYaw();
        }
        return vp.v;
    }
    
    @Redirect(method = { "travel" }, at = @At(value = "INVOKE", target = "net/minecraft/entity/EntityLivingBase.moveRelative(FFFF)V"))
    private void travel(final vp vp, final float n, final float n2, final float n3, final float n4) {
        if (!bud.class.isInstance(this) || BaritoneAPI.getProvider().getBaritoneForPlayer((bud)this) == null) {
            this.b(n, n2, n3, n4);
            return;
        }
        final RotationMoveEvent rotationMoveEvent = new RotationMoveEvent(RotationMoveEvent$Type.MOTION_UPDATE, this.v);
        BaritoneAPI.getProvider().getBaritoneForPlayer((bud)this).getGameEventHandler().onPlayerRotationMove(rotationMoveEvent);
        final float v = this.v;
        this.v = rotationMoveEvent.getYaw();
        this.b(n, n2, n3, n4);
        this.v = v;
    }
}
