package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import baritone.*;
import baritone.api.event.events.type.*;
import baritone.api.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import baritone.api.event.events.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin({ bib.class })
public class MixinMinecraft
{
    @Shadow
    public bud h;
    @Shadow
    public bsb f;
    
    public MixinMinecraft() {
        super();
    }
    
    @Inject(method = { "init" }, at = { @At("RETURN") })
    private void postInit(final CallbackInfo callbackInfo) {
        ((a)BaritoneAPI.getProvider().getPrimaryBaritone()).init();
    }
    
    @Inject(method = { "init" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/Minecraft.startTimerHackThread()V") })
    private void preInit(final CallbackInfo callbackInfo) {
        final cj a = cj.a;
        cj.a();
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "FIELD", opcode = 180, target = "net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;", ordinal = 5, shift = At.Shift.BY, by = -3) })
    private void runTick(final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            baritone.getGameEventHandler().onTick(new TickEvent(EventState.PRE, ((baritone = iterator.next()).getPlayerContext().player() != null && baritone.getPlayerContext().world() != null) ? TickEvent$Type.IN : TickEvent$Type.OUT));
        }
    }
    
    @Inject(method = { "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V" }, at = { @At("HEAD") })
    private void preLoadWorld(final bsb bsb, final String s, final CallbackInfo callbackInfo) {
        if (this.f == null && bsb == null) {
            return;
        }
        BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onWorldEvent(new WorldEvent(bsb, EventState.PRE));
    }
    
    @Inject(method = { "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V" }, at = { @At("RETURN") })
    private void postLoadWorld(final bsb bsb, final String s, final CallbackInfo callbackInfo) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onWorldEvent(new WorldEvent(bsb, EventState.POST));
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "FIELD", opcode = 180, target = "net/minecraft/client/gui/GuiScreen.allowUserInput:Z"))
    private boolean isAllowUserInput(final blk blk) {
        return (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().getCurrent() != null && this.h != null) || blk.p;
    }
    
    @Inject(method = { "clickMouse" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/multiplayer/PlayerControllerMP.clickBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z") }, locals = LocalCapture.CAPTURE_FAILHARD)
    private void onBlockBreak(final CallbackInfo callbackInfo, final et et) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onBlockInteract(new BlockInteractEvent(et, BlockInteractEvent$Type.START_BREAK));
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.swingArm(Lnet/minecraft/util/EnumHand;)V") }, locals = LocalCapture.CAPTURE_FAILHARD)
    private void onBlockUse(final CallbackInfo callbackInfo, final ub[] array, final int n, final int n2, final ub ub, final aip aip, final et et, final int n3, final ud ud) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onBlockInteract(new BlockInteractEvent(et, BlockInteractEvent$Type.USE));
    }
}
