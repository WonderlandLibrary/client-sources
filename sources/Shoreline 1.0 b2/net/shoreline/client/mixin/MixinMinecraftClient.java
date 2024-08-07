package net.shoreline.client.mixin;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.impl.imixin.IMinecraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.shoreline.client.impl.event.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient implements IMinecraftClient
{
    //
    @Shadow
    public ClientWorld world;
    @Shadow
    public ClientPlayerEntity player;
    @Shadow
    @Nullable
    public ClientPlayerInteractionManager interactionManager;

    /**
     *
     */
    @Shadow
    protected abstract void doItemUse();

    /**
     *
     * @return
     */
    @Shadow
    protected abstract boolean doAttack();

    //
    @Shadow
    protected int attackCooldown;
    @Unique
    private boolean leftClick;
    // https://github.com/MeteorDevelopment/meteor-client/blob/master/src/main/java/meteordevelopment/meteorclient/mixin/MinecraftClientMixin.java#L54
    @Unique
    private boolean rightClick;
    @Unique
    private boolean doAttackCalled;
    @Unique
    private boolean doItemUseCalled;

    /**
     *
     */
    @Override
    public void leftClick()
    {
        leftClick = true;
    }

    /**
     *
     */
    @Override
    public void rightClick()
    {
        rightClick = true;
    }

    /**
     *
     *
     * @param ci
     */
    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet" +
            "/minecraft/client/MinecraftClient;render(Z)V", shift =
            At.Shift.BEFORE))
    private void hookRun(CallbackInfo ci)
    {
        final RunTickEvent runTickEvent = new RunTickEvent();
        Shoreline.EVENT_HANDLER.dispatch(runTickEvent);
    }

    /**
     *
     *
     * @param ci
     */
    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void hookTickPre(CallbackInfo ci)
    {
        doAttackCalled = false;
        doItemUseCalled = false;
        if (player != null && world != null)
        {
            TickEvent tickPreEvent = new TickEvent();
            tickPreEvent.setStage(EventStage.PRE);
            Shoreline.EVENT_HANDLER.dispatch(tickPreEvent);
        }
        if (interactionManager == null)
        {
            return;
        }
        if (leftClick && !doAttackCalled)
        {
            doAttack();
        }
        if (rightClick && !doItemUseCalled)
        {
            doItemUse();
        }
        leftClick = false;
        rightClick = false;
    }

    /**
     *
     *
     * @param ci
     */
    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void hookTickPost(CallbackInfo ci)
    {
        if (player != null && world != null)
        {
            TickEvent tickPostEvent = new TickEvent();
            tickPostEvent.setStage(EventStage.POST);
            Shoreline.EVENT_HANDLER.dispatch(tickPostEvent);
        }
    }

    /**
     *
     * @param screen
     * @param ci
     */
    @Inject(method = "setScreen", at = @At(value = "TAIL"))
    private void hookSetScreen(Screen screen, CallbackInfo ci)
    {
        ScreenOpenEvent screenOpenEvent = new ScreenOpenEvent(screen);
        Shoreline.EVENT_HANDLER.dispatch(screenOpenEvent);
    }

    /**
     *
     * @param ci
     */
    @Inject(method = "doItemUse", at = @At(value = "HEAD"))
    private void hookDoItemUse(CallbackInfo ci)
    {
        doItemUseCalled = true;
    }

    /**
     *
     * @param cir
     */
    @Inject(method = "doAttack", at = @At(value = "HEAD"))
    private void hookDoAttack(CallbackInfoReturnable<Boolean> cir)
    {
        doAttackCalled = true;
        AttackCooldownEvent attackCooldownEvent = new AttackCooldownEvent();
        Shoreline.EVENT_HANDLER.dispatch(attackCooldownEvent);
        if (attackCooldownEvent.isCanceled())
        {
            attackCooldown = 0;
        }
    }

    /**
     *
     * @param instance
     * @return
     */
    @Redirect(method = "handleBlockBreaking",at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean hookIsUsingItem(ClientPlayerEntity instance)
    {
        ItemMultitaskEvent itemMultitaskEvent = new ItemMultitaskEvent();
        Shoreline.EVENT_HANDLER.dispatch(itemMultitaskEvent);
        return !itemMultitaskEvent.isCanceled() && instance.isUsingItem();
    }

    /**
     *
     * @param instance
     * @return
     */
    @Redirect(method = "doItemUse",at = @At(value = "INVOKE", target = "Lnet" +
            "/minecraft/client/network/ClientPlayerInteractionManager;isBreakingBlock()Z"))
    private boolean hookIsBreakingBlock(ClientPlayerInteractionManager instance)
    {
        ItemMultitaskEvent itemMultitaskEvent = new ItemMultitaskEvent();
        Shoreline.EVENT_HANDLER.dispatch(itemMultitaskEvent);
        return !itemMultitaskEvent.isCanceled() && instance.isBreakingBlock();
    }

    /**
     *
     * @param cir
     */
    @Inject(method = "getFramerateLimit", at = @At(value = "HEAD"), cancellable = true)
    private void hookGetFramerateLimit(CallbackInfoReturnable<Integer> cir)
    {
        FramerateLimitEvent framerateLimitEvent = new FramerateLimitEvent();
        Shoreline.EVENT_HANDLER.dispatch(framerateLimitEvent);
        if (framerateLimitEvent.isCanceled())
        {
            cir.cancel();
            cir.setReturnValue(framerateLimitEvent.getFramerateLimit());
        }
    }
}
