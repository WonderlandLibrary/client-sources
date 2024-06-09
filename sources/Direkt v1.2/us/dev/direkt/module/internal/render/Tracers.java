package us.dev.direkt.module.internal.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.render.EventRender2D;
import us.dev.direkt.event.internal.events.game.render.EventRender3D;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.internal.core.listeners.EntityPositionListener;
import us.dev.direkt.util.client.PlayerUtils;
import us.dev.direkt.util.render.OGLRender;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.Collections;
import java.util.List;

@ModData(label = "Tracers", category = ModCategory.RENDER)
public class Tracers extends ToggleableModule {

    private boolean do3D = false;
    private float savedFOV;

    @Listener
    protected Link<EventRender2D> onRender2D = new Link<>(event -> {
        List<EntityPlayer> playerList = Wrapper.getLoadedPlayersNoNPCs();
        Collections.sort(playerList, PlayerUtils.getPlayerDistanceComparator());
        for (EntityPlayer listEntity : playerList) {
            GL11.glLineWidth(1.5F);
            if (listEntity != Wrapper.getPlayer()) {
                Vec3d pointer;
                if ((pointer = EntityPositionListener.getEntityLowerBounds().get(listEntity)) != null) {
                    Vec3d point = new Vec3d(pointer.xCoord / event.getScaledResolution().getScaleFactor() * 2, pointer.yCoord / event.getScaledResolution().getScaleFactor() * 2, pointer.zCoord);

                    double distance = Wrapper.getPlayer().getDistanceToEntity(listEntity);

                    if (point.zCoord > -1 && point.zCoord < 1) {

                        GL11.glPushMatrix();
                        float y = (float) ((point.yCoord / 2));
                        float x = (float) ((point.xCoord / 2));

                        GL11.glColor4f(1, 1, 1, 1);
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glEnable(GL11.GL_LINE_SMOOTH);
                        if (Direkt.getInstance().getFriendManager().isFriend(listEntity))
                            GL11.glColor4f(0.0F, 0.5F, 1.0F, 1.0F);
                        else if (distance < 15F) {
                            GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
                        } else if (distance < 20) {
                            GL11.glColor4f(1.0F, (float) ((distance - 15) / 5), 0.0F, 0.5F);
                        } else if (distance < 45F) {
                            GL11.glColor4f(1.0F, 1.0F, 0.0F, 0.5F);
                        } else if (distance < 50) {
                            GL11.glColor4f((float) (1.0F - (distance - 45) / 5), 1.0F, 0.0F, 0.5F);
                        } else if (distance >= 50F) {
                            GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.5F);
                        }
                        GL11.glBegin(GL11.GL_LINES);
                        GL11.glVertex2d(event.getScaledResolution().getScaledWidth() / 2, event.getScaledResolution().getScaledHeight() / 2);
                        GL11.glVertex2d(x, y);
                        GL11.glEnd();
                        GL11.glDisable(GL11.GL_LINE_SMOOTH);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glColor4f(1, 1, 1, 1);
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }, Link.VERY_LOW_PRIORITY);

    @Listener
    protected Link<EventRender3D> onRender3D = new Link<>(event -> {
//GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final boolean savedBobbing = Wrapper.getGameSettings().viewBobbing;
        final float savedTimeInPortal = Wrapper.getPlayer().timeInPortal;
        final float savedPrevTimeInPortal = Wrapper.getPlayer().prevTimeInPortal;
        Wrapper.getGameSettings().viewBobbing = false;
        Wrapper.getPlayer().timeInPortal = 0;
        Wrapper.getPlayer().prevTimeInPortal = 0;
        Wrapper.getMinecraft().entityRenderer.setupCameraTransform(event.getPartialTicks(), 0, 0);
        List<EntityPlayer> playerList = Wrapper.getLoadedPlayersNoNPCs();
        Collections.sort(playerList, PlayerUtils.getPlayerDistanceComparator());
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        for (EntityPlayer listEntity : playerList) {
            if (listEntity != Wrapper.getPlayer()) {
                Vec3d point;
                if ((point = EntityPositionListener.getEntityLowerBounds().get(listEntity)) != null) {
                    double distance = Wrapper.getPlayer().getDistanceToEntity(listEntity);

                    if (!(point.zCoord > -1 && point.zCoord < 1)) {

                        OGLRender.enableGL3D(1F);
                        GL11.glEnable(GL11.GL_LINE_SMOOTH);
                        final float posX = (float) this.interpolate(listEntity.posX, listEntity.lastTickPosX, event.getPartialTicks()) - (float) RenderManager.renderPosX();
                        final float posY = (float) this.interpolate(listEntity.posY, listEntity.lastTickPosY, event.getPartialTicks()) - (float) RenderManager.renderPosY();
                        final float posZ = (float) this.interpolate(listEntity.posZ, listEntity.lastTickPosZ, event.getPartialTicks()) - (float) RenderManager.renderPosZ();

                        if (Direkt.getInstance().getFriendManager().isFriend(listEntity))
                            GL11.glColor4f(0.0F, 0.5F, 1.0F, 1.0F);
                        else if (distance < 15F) {
                            GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
                        } else if (distance < 20) {
                            GL11.glColor4f(1.0F, (float) ((distance - 15) / 5), 0.0F, 0.5F);
                        } else if (distance < 45F) {
                            GL11.glColor4f(1.0F, 1.0F, 0.0F, 0.5F);
                        } else if (distance < 50) {
                            GL11.glColor4f((float) (1.0F - (distance - 45) / 5), 1.0F, 0.0F, 0.5F);
                        } else if (distance >= 50F) {
                            GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.5F);
                        }
                        GL11.glLineWidth(1.5f);
                        GL11.glBegin(GL11.GL_LINES);
                        GL11.glVertex3d(0, Wrapper.getPlayer().getEyeHeight(), 0);
                        GL11.glVertex3d(posX, posY, posZ);
                        GL11.glEnd();
                    }
                    GL11.glDisable(GL11.GL_LINE_SMOOTH);
                    OGLRender.disableGL3D();
                }
            }
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glPopMatrix();
        Wrapper.getGameSettings().viewBobbing = savedBobbing;
        Wrapper.getPlayer().timeInPortal = savedTimeInPortal;
        Wrapper.getPlayer().prevTimeInPortal = savedPrevTimeInPortal;
    }, Link.VERY_LOW_PRIORITY);

    @Override
    public void onEnable() {
        this.savedFOV = Wrapper.getGameSettings().fovSetting;
    }

    @Override
    public void onDisable() {
        Wrapper.getGameSettings().fovSetting = this.savedFOV;
    }

    private double interpolate(final double now, final double then, final double percent) {
        return then + (now - then) * percent;
    }

}

