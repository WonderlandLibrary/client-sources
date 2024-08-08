package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.GeometryMasks;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

/**
 * @author 086
 */
@Module.Info(name = "EyeFinder", description = "Draw lines from entity's heads to where they are looking", category = Module.Category.HIDDEN)
public class EyeFinder extends Module {

    private Setting<Boolean> players = register(Settings.b("Players", true));
    private Setting<Boolean> friends = register(Settings.b("Friends", false));
    private Setting<Boolean> mobs = register(Settings.b("Mobs", false));
    private Setting<Boolean> animals = register(Settings.b("Animals", false));
    private Setting<Integer> renderAlphaTracer = register(Settings.integerBuilder("Render Alpha").withMinimum(0).withValue(128).withMaximum(255).build());
    private Setting<Boolean> block = register(Settings.b("Block", true));
    private Setting<Integer> renderAlphaBlock = register(Settings.integerBuilder("Render Alpha").withMinimum(0).withValue(128).withMaximum(255).build());

    @Override
    public void onWorldRender(RenderEvent event) {
        mc.world.loadedEntityList.stream()
                .filter(EntityUtil::isLiving)
                .filter(entity -> mc.player != entity)
                .map(entity -> (EntityLivingBase) entity)
                .filter(entityLivingBase -> !entityLivingBase.isDead)
                .filter(entity -> (players.getValue() && entity instanceof EntityPlayer) || (EntityUtil.isPassive(entity) ? animals.getValue() : mobs.getValue()))
                .forEach(this::drawRender);
    }

    private void drawRender(EntityLivingBase entity) {
        RayTraceResult result = entity.rayTrace(6, Minecraft.getMinecraft().getRenderPartialTicks());
        if (result == null) {
            return;
        }

        int red = 104;
        int green = 12;
        int blue = 35;

        if (entity instanceof EntityPlayer) {
            if (Friends.isFriend(entity.getName())) {
                if (!friends.getValue()) {
                    return;
                }
                red = 81;
                green = 12;
                blue = 104;
            }
        }

        Vec3d eyes = entity.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks());

        GlStateManager.enableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();

        double posx = eyes.x - mc.getRenderManager().renderPosX;
        double posy = eyes.y - mc.getRenderManager().renderPosY;
        double posz = eyes.z - mc.getRenderManager().renderPosZ;
        double posx2 = result.hitVec.x - mc.getRenderManager().renderPosX;
        double posy2 = result.hitVec.y - mc.getRenderManager().renderPosY;
        double posz2 = result.hitVec.z - mc.getRenderManager().renderPosZ;
        //GL11.glColor4f(.2f, .1f, .3f, .8f);
        GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, renderAlphaTracer.getValue() / 255.0F);
        GlStateManager.glLineWidth(1.5f);

        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex3d(posx, posy, posz);
            GL11.glVertex3d(posx2, posy2, posz2);
            GL11.glVertex3d(posx2, posy2, posz2);
            GL11.glVertex3d(posx2, posy2, posz2);
        }
        GL11.glEnd();

            if (block.getValue() && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                KamiTessellator.prepare(GL11.GL_QUADS);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                BlockPos b = result.getBlockPos();
                float x = b.x;
                float y = b.y;
                float z = b.z;
                KamiTessellator.drawBox(KamiTessellator.getBufferBuilder(), x, y, z, 1.01f, 1.01f, 1.01f, red, green, blue, renderAlphaBlock.getValue(), GeometryMasks.Quad.ALL);
                KamiTessellator.release();
            }

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
    }
}
