package dev.stephen.nexus.mixin.render;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.impl.render.EventRender3D;
import dev.stephen.nexus.module.modules.combat.AntiBot;
import dev.stephen.nexus.module.modules.render.ESP;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11C.*;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer {

    @Inject(at = @At("HEAD"), method = "renderChunkDebugInfo")
    private void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Camera camera, CallbackInfo ci) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrices.push();
        Vec3d camPos = MinecraftClient.getInstance().getBlockEntityRenderDispatcher().camera.getPos();
        matrices.translate(-camPos.x, -camPos.y, -camPos.z);
        Client.INSTANCE.getEventManager().post(new EventRender3D(matrices));
        matrices.pop();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    @Unique
    private boolean renderingChams = false;

    @Inject(method = "renderEntity", at = @At("HEAD"))
    private void renderEntityHead(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (Client.INSTANCE.getModuleManager().getModule(ESP.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(ESP.class).chams.getValue() && entity instanceof PlayerEntity && !Client.INSTANCE.getModuleManager().getModule(AntiBot.class).isBot((PlayerEntity) entity)) {
            glEnable(GL_POLYGON_OFFSET_FILL);
            glPolygonOffset(1f, -1000000F);
            this.renderingChams = true;
        }
    }

    @Inject(method = "renderEntity", at = @At("RETURN"))
    private void renderEntityReturn(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (Client.INSTANCE.getModuleManager().getModule(ESP.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(ESP.class).chams.getValue() && entity instanceof PlayerEntity && !Client.INSTANCE.getModuleManager().getModule(AntiBot.class).isBot((PlayerEntity) entity) && this.renderingChams) {
            glPolygonOffset(1f, 1000000F);
            glDisable(GL_POLYGON_OFFSET_FILL);
            this.renderingChams = false;
        }
    }
}