package us.dev.direkt.module.internal.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL45;

import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.render.EventRender3D;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.util.render.Box;
import us.dev.direkt.util.render.OGLRender;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@ModData(label = "Projectories", category = ModCategory.RENDER)
public class Projectories extends ToggleableModule {
    private static final Map<Class<? extends Entity>, Double> gravity;
    private static final Map<Class<? extends Entity>, Integer> colours;
    
    @Listener
    protected Link<EventRender3D> onRender3D = new Link<>(event -> {
        Wrapper.getWorld().getLoadedEntityList().stream()
                .filter(this::shouldRender)
                .forEach(this::renderLine);
    });

    private void renderLine(Entity entity) {
        double posX = entity.posX;
        double posY = entity.posY;
        double posZ = entity.posZ;
        double motionX = entity.motionX;
        double motionY = entity.motionY;
        double motionZ = entity.motionZ;

        final double gravity = Projectories.gravity.get(entity.getClass());
        final double resistance = entity.isInWater() ? 0.8 : 0.99;
        final double size = 0.5;

        int colour = Projectories.colours.get(entity.getClass());

        //Wrapper.addChatMessage(Wrapper.getBlock(Wrapper.getWorld().rayTraceBlocks(new Vec3d(posX, posY, posZ), new Vec3d(posX + motionX * resistance, posY + motionY * resistance - gravity, posZ + motionZ * resistance)).getBlockPos()));

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        if (entity instanceof EntityPotion) {
            final int color = PotionUtils.getPotionColorFromEffectList(PotionUtils.getPotionFromItem(((EntityPotion) entity).getPotion()).getEffects());
            OGLRender.glColor(0x55FFFFFF & (new Color(color).darker().getRGB()));
        } else if (entity instanceof EntityArrow) {
            final int color = 0;
            OGLRender.glColor(new Color(colour).darker().getRGB());
        } else
            OGLRender.glColor(new Color(colour).getRGB());

        GL11.glTranslated(-(Wrapper.getPlayer().posX), -(Wrapper.getPlayer().posY), -(Wrapper.getPlayer().posZ));
        GL11.glBegin(GL11.GL_LINE_STRIP);
        while (true) {
            GL11.glVertex3d(posX, posY, posZ);
            boolean collidedWithGround = Wrapper.getWorld().rayTraceBlocks(new Vec3d(posX, posY, posZ), new Vec3d(posX + motionX * resistance, posY + motionY * resistance - gravity, posZ + motionZ * resistance)) != null;
            boolean[] lambdaArrayHackery = new boolean[1];
            Wrapper.getWorld().getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size)).stream().filter(obj -> obj instanceof EntityLiving).forEach(obj -> lambdaArrayHackery[0] = true);
            boolean collidedWithEntity = lambdaArrayHackery[0];

            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            motionX *= resistance;
            motionY = motionY * resistance - gravity;
            motionZ *= resistance;

            boolean collided = collidedWithGround || collidedWithEntity || entity.onGround;

            if (entity instanceof EntityArrow) {
                NBTTagCompound compound = new NBTTagCompound();
                entity.writeToNBTAtomically(compound);
                if (compound.getBoolean("inGround")) {
                    collided = true;
                }
            }
            if (posY < 0 || collided) {
                if (entity.posY != entity.lastTickPosY)
                    GL11.glVertex3d(posX, MathHelper.floor_double(posY) + 1, posZ);
                GL11.glEnd();
                GL11.glTranslated(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ);

                GL11.glDisable(GL11.GL_LINE_SMOOTH);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glPopMatrix();

                if (entity.posY != entity.lastTickPosY) {
                    GL11.glPushMatrix();

                    if (entity instanceof EntityPotion) {
                        final int color = PotionUtils.getPotionColorFromEffectList(PotionUtils.getPotionFromItem(((EntityPotion) entity).getPotion()).getEffects());
                        OGLRender.glColor(0x55FFFFFF & (new Color(color).darker().getRGB()));
                    } else if (entity instanceof EntityArrow) {
                        final int color = 0;
                        OGLRender.glColor(0x55FFFFFF & (new Color(colour).darker().getRGB()));
                    } else
                        OGLRender.glColor(0x55FFFFFF & (new Color(colour).getRGB()));

                    OGLRender.enableGL3D(1.5f);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glTranslated(MathHelper.floor_double(posX) + 0.5 - RenderManager.renderPosX(), MathHelper.floor_double(posY) - RenderManager.renderPosY(), MathHelper.floor_double(posZ) + 0.5 - RenderManager.renderPosZ());
                    OGLRender.drawBox(new Box(-0.5, 0, -0.5, 0.5, 1.0, 0.5));
                    OGLRender.drawOutlinedBox(new Box(-0.5, 0, -0.5, 0.5, 1, 0.5));
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glDisable(GL11.GL_BLEND);
                    OGLRender.disableGL3D();
                    GL11.glPopMatrix();
                }

                return;
            }
        }
    }

    private boolean shouldRender(Entity entity) {
        return (entity instanceof EntityArrow || entity instanceof EntityEgg || entity instanceof EntityEnderPearl || entity instanceof EntitySnowball || entity instanceof EntityExpBottle || entity instanceof EntityPotion || entity instanceof EntityItem);
    }

    private static void load(Class<? extends Entity> entityClass, double gravity, int colour) {
        Projectories.gravity.put(entityClass, gravity);
        colours.put(entityClass, colour);
    }

    static {
        gravity = new HashMap<>();
        colours = new HashMap<>();

        load(EntityArrow.class, 0.05F, 0xFF0000);
        load(EntitySpectralArrow.class, 0.05F, 0xFF0000);
        load(EntityTippedArrow.class, 0.05F, 0xFF0000);

        load(EntityEgg.class, 0.03F, 0x55D7C583);
        load(EntityEnderPearl.class, 0.03F, 0x287260);
        load(EntitySnowball.class, 0.03F, 0xFFFFFF);

        load(EntityExpBottle.class, 0.07F, 0x00FF60);
        load(EntityPotion.class, 0.05F, 0);
        load(EntityItem.class, 0.04F, 0x303030);
    }
}
