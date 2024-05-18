package us.dev.direkt.module.internal.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import org.lwjgl.opengl.GL11;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.render.EventRender3D;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.util.render.Box;
import us.dev.direkt.util.render.OGLRender;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.List;

@ModData(label = "Trajectories", category = ModCategory.RENDER)
public class Trajectories extends ToggleableModule {

    @Listener
    protected Link<EventRender3D> onRender3D = new Link<>(event -> {
    	
    	
    	boolean render = false;
        boolean collidedWithEntity = false;
        Entity entity = null;
    	
        ItemArrow itemarrow = (ItemArrow)Items.ARROW;
        EntityArrow entityarrow = itemarrow.createArrow(Wrapper.getWorld(), new ItemStack(itemarrow), Wrapper.getPlayer());

        boolean isHoldingThrowable = false;
        EnumHand hand = null;
        
        if(isHoldingThrowable(EnumHand.MAIN_HAND)){
        	hand = EnumHand.MAIN_HAND;
        	isHoldingThrowable = true;
        }
        else if(isHoldingThrowable(EnumHand.OFF_HAND)){
        	hand = EnumHand.OFF_HAND;
        	isHoldingThrowable = true;
        }
        
        if (isHoldingThrowable) {
            if (Wrapper.getPlayer().getHeldItem(hand).getItem() instanceof ItemBow) {
                if (ItemBow.getArrowVelocity(Wrapper.getPlayer().getItemInUseMaxCount()) > 0)
                	entityarrow = this.setHeadingFromThrower(entityarrow, Wrapper.getPlayer(), Wrapper.getPlayer().rotationPitch, Wrapper.getPlayer().rotationYaw, 0.0F, ItemBow.getArrowVelocity(Wrapper.getPlayer().getItemInUseMaxCount()) * 3.0F, 0);
                else
                    entityarrow = this.setHeadingFromThrower(entityarrow, Wrapper.getPlayer(), Wrapper.getPlayer().rotationPitch, Wrapper.getPlayer().rotationYaw, 0.0F, ItemBow.getArrowVelocity(20) * 3.0F, 0);
                
            }
            else if (Wrapper.getPlayer().getHeldItem(hand).getItem() instanceof ItemEnderPearl || Wrapper.getPlayer().getHeldItem(hand).getItem() instanceof ItemSnowball || Wrapper.getPlayer().getHeldItem(hand).getItem() instanceof ItemEgg)
            	entityarrow = this.setHeadingFromThrower(entityarrow, Wrapper.getPlayer(), Wrapper.getPlayer().rotationPitch, Wrapper.getPlayer().rotationYaw, 0, 1.875F, 0);
            	
            else if (Wrapper.getPlayer().getHeldItem(hand).getItem() instanceof ItemSplashPotion || Wrapper.getPlayer().getHeldItem(hand).getItem() instanceof ItemLingeringPotion)
                entityarrow = this.setHeadingFromThrower(entityarrow, Wrapper.getPlayer(), Wrapper.getPlayer().rotationPitch, Wrapper.getPlayer().rotationYaw, -20.0F, 0.5F, 0);
            
            else if (Wrapper.getPlayer().getHeldItem(hand).getItem() instanceof ItemExpBottle)
                entityarrow = this.setHeadingFromThrower(entityarrow, Wrapper.getPlayer(), Wrapper.getPlayer().rotationPitch, Wrapper.getPlayer().rotationYaw, -20.0F, 0.6F, 0);
        	render = true;
        }
        
    	if(render){
			for(int i = 0; i < 200; i++){
				double X = entityarrow.posX;
				double Y = entityarrow.posY;
				double Z = entityarrow.posZ;
				
				double mX = entityarrow.motionX;
				double mY = entityarrow.motionY;
				double mZ = entityarrow.motionZ;
				entityarrow.onUpdate();
				final Entity ent = this.findEntityOnPath(new Vec3d(X, Y, Z), new Vec3d(X + mX, Y + mY, Z + mZ), mX, mY, mZ, 0.5F, X, Y, Z);
	            if (ent != null && ent instanceof EntityLivingBase) {
	            	entity = ent;
	                collidedWithEntity = true;
	                break;
	            }
			}
    	
            GL11.glPushMatrix();
            GL11.glColor4d(0, 0.5, 0, 0.5F);
            OGLRender.enableGL3D(1.5f);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            if (collidedWithEntity)
                this.drawBoxESP(entity);
            else {
	            GL11.glTranslated(MathHelper.floor_double(entityarrow.posX) + 0.5 - RenderManager.renderPosX(), MathHelper.floor_double(entityarrow.posY) - RenderManager.renderPosY(), MathHelper.floor_double(entityarrow.posZ) + 0.5 - RenderManager.renderPosZ());
	            OGLRender.drawBox(new Box(-0.5, 0, -0.5, 0.5, 1.0, 0.5));
	            OGLRender.drawOutlinedBox(new Box(-0.5, 0, -0.5, 0.5, 1, 0.5));
            }
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            OGLRender.disableGL3D();
            GL11.glPopMatrix();
    	}
    });

    private void drawBoxESP(Entity e) {
        GL11.glPushMatrix();
        OGLRender.enableGL3D(1.5f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        final double[] pos = OGLRender.interpolate(e);
        final double x = pos[0];
        final double y = pos[1];
        final double z = pos[2];

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.4f);
        OGLRender.drawBox(new Box(e.getEntityBoundingBox().minX - e.posX, e.getEntityBoundingBox().minY - e.posY, e.getEntityBoundingBox().minZ - e.posZ, e.getEntityBoundingBox().maxX - e.posX, e.getEntityBoundingBox().maxY - e.posY, e.getEntityBoundingBox().maxZ - e.posZ));
        GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        OGLRender.drawOutlinedBox(new Box(e.getEntityBoundingBox().minX - e.posX, e.getEntityBoundingBox().minY - e.posY, e.getEntityBoundingBox().minZ - e.posZ, e.getEntityBoundingBox().maxX - e.posX, e.getEntityBoundingBox().maxY - e.posY, e.getEntityBoundingBox().maxZ - e.posZ));
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        OGLRender.disableGL3D();
        GL11.glPopMatrix();
    }

    private Entity findEntityOnPath(Vec3d start, Vec3d end, double motionX, double motionY, double motionZ, double size, double posX, double posY, double posZ) {
        Entity entity = null;
        List<Entity> list = Wrapper.getWorld().getEntitiesInAABBexcluding(null, new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size).addCoord(motionX, motionY, motionZ).expandXyz(1.0D), null);
        double d0 = 0.0D;

        for (Entity entity1 : list) {
            if (entity1 != Wrapper.getPlayer()) {
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.30000001192092896D);
                RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);

                if (raytraceresult != null) {
                    double d1 = start.squareDistanceTo(raytraceresult.hitVec);

                    if (d1 < d0 || d0 == 0.0D) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        return entity;
    }

    private EntityArrow setHeadingFromThrower(EntityArrow projectile, Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
        float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
        float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        projectile.setThrowableHeading((double) f, (double) f1, (double) f2, velocity, inaccuracy);
        return projectile;
    }
    
    private static boolean isHoldingThrowable(EnumHand e) {
        ItemStack is = e.equals(EnumHand.MAIN_HAND) ? Wrapper.getPlayer().getHeldItemMainhand() : Wrapper.getPlayer().getHeldItemOffhand();
        return (is != null) && ((is.getItem() instanceof ItemBow) || (is.getItem() instanceof ItemEnderPearl) || (is.getItem() instanceof ItemSnowball) || (is.getItem() instanceof ItemEgg) || (is.getItem() instanceof ItemSplashPotion) || (is.getItem() instanceof ItemLingeringPotion) || (is.getItem() instanceof ItemExpBottle));
    }
    
}
