package me.travis.wurstplus.module.modules.combat;

import java.util.HashSet;
import java.util.Set;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import me.travis.wurstplus.event.events.RenderEvent;
import me.travis.wurstplus.util.Wrapper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

@Module.Info(name = "Strength Detecter", category = Module.Category.MISC)
public class StrengthDetect extends Module {

    public Set<EntityPlayer> strengthedPlayers;
    public Set<EntityPlayer> renderPlayers;

    @Override
    protected void onEnable() {
        strengthedPlayers = new HashSet<EntityPlayer>();
        renderPlayers = new HashSet<EntityPlayer>();
    }

    public void onUpdate() {
        if (this.isDisabled() || mc.player == null) {
            return;
        }
        for (EntityPlayer ent : mc.world.playerEntities) {
            if (!EntityUtil.isLiving(ent) ||((EntityLivingBase)ent).getHealth() <= 0.0f) continue;
            if (ent.isPotionActive(MobEffects.STRENGTH) && !this.strengthedPlayers.contains(ent)) {
                Command.sendChatMessage("\u00A74[" + ent.getDisplayNameString() + "]\u00A7r is now strong");
                this.strengthedPlayers.add(ent);
            }
            if (this.strengthedPlayers.contains(ent) && !ent.isPotionActive(MobEffects.STRENGTH)) {
                Command.sendChatMessage("\u00A73[" + ent.getDisplayNameString() + "]\u00A7r is no longer strong");
                this.strengthedPlayers.remove(ent);
            }
            checkRender();
        }
    } 

    public void checkRender() {
        try {
            this.renderPlayers.clear();
            for (EntityPlayer ent : mc.world.playerEntities) {
                if (!EntityUtil.isLiving(ent) ||((EntityLivingBase)ent).getHealth() <= 0.0f) continue;
                this.renderPlayers.add(ent);
            }
            for (EntityPlayer ent : this.strengthedPlayers) {
                if (!this.renderPlayers.contains(ent)) {
                    Command.sendChatMessage("\u00A73[" + ent.getDisplayNameString() + "]\u00A7r is (probably) no longer strong");
                    this.strengthedPlayers.remove(ent);
                }
            }
        } catch (Exception e) {}   
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (Wrapper.getMinecraft().getRenderManager().options == null) return;
            boolean isThirdPersonFrontal = Wrapper.getMinecraft().getRenderManager().options.thirdPersonView == 2;
            float viewerYaw = Wrapper.getMinecraft().getRenderManager().playerViewY;

            for (EntityPlayer e : strengthedPlayers) {
                if (e.getName() == mc.player.getName()) {
                    return;
                }
                GlStateManager.pushMatrix();
                Vec3d pos = EntityUtil.getInterpolatedPos(e, event.getPartialTicks());
                GlStateManager.translate(pos.x-mc.getRenderManager().renderPosX, pos.y-mc.getRenderManager().renderPosY, pos.z-mc.getRenderManager().renderPosZ);
                GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1), 1.0F, 0.0F, 0.0F);
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);

                GlStateManager.disableDepth();

                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                            
                glColor3f(1,0.2f,0.2f);

                GlStateManager.disableTexture2D();
                glLineWidth(4f);
                glEnable( GL_LINE_SMOOTH );
                glBegin(GL_LINE_LOOP);
                {
                    glVertex2d(-e.width/2,0);
                    glVertex2d(-e.width/2,e.height);
                    glVertex2d(e.width/2,e.height);
                    glVertex2d(e.width/2,0);
                }
                glEnd();

                GlStateManager.popMatrix();
            }
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
            GlStateManager.disableDepth();
            GlStateManager.enableCull();
            GlStateManager.glLineWidth(1);
            glColor3f(1,1,1);
    }
}
