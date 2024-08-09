package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Chams;
import xyz.mathax.mathaxclient.systems.modules.render.Freecam;
import xyz.mathax.mathaxclient.systems.modules.render.NoRender;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static xyz.mathax.mathaxclient.MatHax.mc;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Shadow
    @Nullable
    protected abstract RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline);

    @Redirect(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getCameraEntity()Lnet/minecraft/entity/Entity;"))
    private Entity hasLabelGetCameraEntityProxy(MinecraftClient mc) {
        if (Modules.get().isEnabled(Freecam.class)) {
            return null;
        }

        return mc.getCameraEntity();
    }

    @ModifyVariable(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 2, at = @At(value = "STORE", ordinal = 0))
    public float changeYaw(float oldValue, LivingEntity entity) {
        if (entity.equals(mc.player) && Rotations.rotationTimer < 10) {
            return Rotations.serverYaw;
        }

        return oldValue;
    }

    @ModifyVariable(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 3, at = @At(value = "STORE", ordinal = 0))
    public float changeHeadYaw(float oldValue, LivingEntity entity) {
        if (entity.equals(mc.player) && Rotations.rotationTimer < 10) {
            return Rotations.serverYaw;
        }

        return oldValue;
    }

    @ModifyVariable(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 5, at = @At(value = "STORE", ordinal = 3))
    public float changePitch(float oldValue, LivingEntity entity) {
        if (entity.equals(mc.player) && Rotations.rotationTimer < 10) {
            return Rotations.serverPitch;
        }

        return oldValue;
    }

    @Redirect(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getScoreboardTeam()Lnet/minecraft/scoreboard/AbstractTeam;"))
    private AbstractTeam hasLabelClientPlayerEntityGetScoreboardTeamProxy(ClientPlayerEntity player) {
        if (player == null) {
            return null;
        }

        return player.getScoreboardTeam();
    }

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void renderHead(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noDeadEntities() && livingEntity.isDead()) {
            info.cancel();
        }

        Chams chams = Modules.get().get(Chams.class);
        if (chams.isEnabled() && chams.shouldRender(livingEntity)) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("TAIL"))
    private void renderTail(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info) {
        Chams chams = Modules.get().get(Chams.class);
        if (chams.isEnabled() && chams.shouldRender(livingEntity)) {
            GL11.glPolygonOffset(1.0f, 1100000.0f);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }

    @ModifyArgs(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V"))
    private void modifyScale(Args args, T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        Chams chams = Modules.get().get(Chams.class);
        if (!chams.isEnabled() || !chams.playersSetting.get() || !(livingEntity instanceof PlayerEntity player)) {
            return;
        }

        if (player == mc.player && chams.ignoreSelfSetting.get()) {
            return;
        } else if (Friends.get().contains(player) && chams.ignoreFriendsSetting.get()) {
            return;
        } else if (Enemies.get().contains(player) && chams.ignoreEnemiesSetting.get()) {
            return;
        }

        args.set(0, -chams.playersScaleSetting.get().floatValue());
        args.set(1, -chams.playersScaleSetting.get().floatValue());
        args.set(2, chams.playersScaleSetting.get().floatValue());
    }

    @ModifyArgs(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    private void modifyColor(Args args, T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        Chams chams = Modules.get().get(Chams.class);
        if (!chams.isEnabled() || !chams.playersSetting.get() || !(livingEntity instanceof PlayerEntity player)) {
            return;
        }

        if (player == mc.player && chams.ignoreSelfSetting.get()) {
            return;
        } else if (Friends.get().contains(player) && chams.ignoreFriendsSetting.get()) {
            return;
        } else if (Enemies.get().contains(player) && chams.ignoreEnemiesSetting.get()) {
            return;
        }

        Color color = PlayerUtils.getPlayerColor(((PlayerEntity) livingEntity), chams.playersColorSetting.get());
        args.set(4, color.r / 255f);
        args.set(5, color.g / 255f);
        args.set(6, color.b / 255f);
        args.set(7, chams.playersColorSetting.get().a / 255f);
    }

    @Redirect(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getRenderLayer(Lnet/minecraft/entity/LivingEntity;ZZZ)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer getRenderLayer(LivingEntityRenderer<T, M> livingEntityRenderer, T livingEntity, boolean showBody, boolean translucent, boolean showOutline) {
        Chams chams = Modules.get().get(Chams.class);
        if (!chams.isEnabled() || !chams.playersSetting.get() || !(livingEntity instanceof PlayerEntity player) || chams.playersTextureSetting.get()) {
            return getRenderLayer(livingEntity, showBody, translucent, showOutline);
        }

        if (player == mc.player && chams.ignoreSelfSetting.get()) {
            return getRenderLayer(livingEntity, showBody, translucent, showOutline);
        } else if (Friends.get().contains(player) && chams.ignoreFriendsSetting.get()) {
            return getRenderLayer(livingEntity, showBody, translucent, showOutline);
        } else if (Enemies.get().contains(player) && chams.ignoreEnemiesSetting.get()) {
            return getRenderLayer(livingEntity, showBody, translucent, showOutline);
        }

        return RenderLayer.getItemEntityTranslucentCull(Chams.BLANK);
    }
}
