package maxstats.weave.mixin;

import maxstats.weave.event.EventTick;
import maxstats.weave.listener.TickListener;
import maxstats.weave.event.EventRenderTick;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.misc.MathUtils;
import me.sleepyfish.smok.utils.misc.MouseUtils;
import me.sleepyfish.smok.rats.impl.other.Hit_Delay;
import me.sleepyfish.smok.rats.impl.other.FPS_Boost;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import me.sleepyfish.smok.utils.render.notifications.NotificationManager;
import net.minecraft.client.Minecraft;
import net.weavemc.loader.api.event.EventBus;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Class from SMok Client by SleepyFish
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow
    private static int debugFPS;

    @Shadow
    private int leftClickCounter;

    private boolean loaded = false;

    @Inject(method = "runGameLoop", at = @At("TAIL"))
    public void runTick(CallbackInfo ci) {
        if (Smok.inst.ratManager.getRatByClass(Hit_Delay.class).isEnabled() && MouseUtils.isButtonDown(0))
            this.leftClickCounter = 0;
    }

    @Inject(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/achievement/GuiAchievement;updateAchievementWindow()V", shift = Shift.AFTER))
    public void renderTick(CallbackInfo ci) {
        NotificationManager.render();
        GL11.glPushMatrix();
        ColorUtils.clearColor();
        EventRenderTick event = new EventRenderTick();
        event.call();
        GL11.glPopMatrix();
    }

    @Inject(method = "startGame", at = @At("TAIL"))
    public void startGame(CallbackInfo ci) {
        if (!this.loaded) {
            Smok.inst.inject();
            EventBus.subscribe(new TickListener());
            this.loaded = true;
        }
    }

    /**
     * @author sleepy fish
     * @reason fps booster (real)
     */
    @Overwrite
    public static int getDebugFPS() {
        if (Smok.inst.ratManager.getRatByClass(FPS_Boost.class).isEnabled())
            debugFPS = MathUtils.randomInt(debugFPS + MathUtils.randomInt(50, 20), debugFPS + MathUtils.randomInt(80, 140));

        return debugFPS;
    }

}