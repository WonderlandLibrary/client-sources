package zamorozka.modules.VISUALLY;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.util.math.Vec3d;
import zamorozka.event.EventTarget;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.EntityUtils;
import zamorozka.ui.Render3DEvent;
import zamorozka.ui.RenderUtils;
import zamorozka.ui.RenderingTools;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.WorldUtils;
import zamorozka.ui.Wrapper;

public class Tracers extends Module {
	
	public Tracers() {
		super("Tracers", Keyboard.KEY_NONE, Category.VISUALLY);
	}
	
	@EventTarget
	public void onRender(RenderEvent3D event) {
        for (final Entity entity : Tracers.mc.world.loadedEntityList) {
            if (entity != null && entity != mc.player && isValid(entity)) {
               RenderingTools.drawTracer(entity, indexer.getFriends().isFriend(entity.getName()) ? new Color(0, 255, 0, 150) : new Color(255, 255, 255, 150));
            }
        }
	}
	
    private boolean isValid(Entity entity) {
        if (entity == mc.player)
            return false;
        if (entity.isDead)
            return false;
        if ((entity instanceof EntityItem))
            return false;
        if ((mc.gameSettings.thirdPersonView == 2))
        	return false;
        if ((entity instanceof net.minecraft.entity.passive.EntityAnimal))
            return false;
        if ((entity instanceof EntityPlayer))
            return true;
        if((entity.isInvisible()))
        	return false;
        if ((entity instanceof EntityArmorStand))
            return false;
        if ((entity instanceof IAnimals))
            return false;
        if ((entity instanceof EntityItemFrame))
            return false;
        if ((entity instanceof EntityArrow || entity instanceof EntitySpectralArrow))
            return false;
        if ((entity instanceof EntityMinecart))
            return false;
        if ((entity instanceof EntityBoat))
            return false;
        if ((entity instanceof EntityDragonFireball))
            return false;
        if ((entity instanceof EntityXPOrb))
            return false;
        if ((entity instanceof EntityMinecartChest))
            return false;
        if ((entity instanceof EntityTNTPrimed))
            return false;
        if ((entity instanceof EntityMinecartTNT))
            return false;
        if ((entity instanceof EntityVillager))
            return false;
        if ((entity instanceof EntityExpBottle))
            return false;
        if ((entity instanceof EntityLightningBolt))
            return false;
        if ((entity instanceof EntityPotion))
            return false;
        if ((entity instanceof Entity))
            return false;
        if (((entity instanceof net.minecraft.entity.monster.EntityMob || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.boss.EntityDragon || entity instanceof net.minecraft.entity.monster.EntityGolem)))
        	return false;
        return entity != mc.player;
    }
	
}