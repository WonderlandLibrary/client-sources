package net.minecraft.client.main.neptune.Mod.Collection.Combat;

import java.util.ArrayList;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Events.EventTick;
import net.minecraft.client.main.neptune.Events.EventUpdate;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Mod.NumValue;
import net.minecraft.client.main.neptune.Utils.CombatUtils;
import net.minecraft.client.main.neptune.Utils.EntityUtils;
import net.minecraft.client.main.neptune.Utils.RotationUtils;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;

public class Aboat extends Mod {

	private NumValue strength = new NumValue("Aim Speed", 1, 0, 10, ValueDisplay.DECIMAL);
    public static ArrayList<Entity> attackList;
    public static ArrayList<Entity> targets;
    public static int currentTarget;
    
    static {
        Aboat.attackList = new ArrayList<Entity>();
        Aboat.targets = new ArrayList<Entity>();
    }
    

	public Aboat() {
		super("Aim", Category.HACKS);
		this.setBind(Keyboard.KEY_X);
	}
	
	

	@Override
	public void onEnable() {
		Memeager.register(this);
	}

	@Override
	public void onDisable() {
		Memeager.unregister(this);
	}


	@Memetarget
	public void onTick(EventTick event) {
		this.setRenderName(String.format("%s", "SilentAim"));
	}
	
    private boolean isValidTarget(final Entity target) {
        return !target.isDead && target.isEntityAlive() && this.mc.thePlayer.getDistanceToEntity(target) <= 4.5 && Aboat.targets.contains(target) && target != this.mc.thePlayer && !Neptune.getWinter().friendUtils.isFriend(target.getName()) && !target.isInvisible();
    }

    @Memetarget(0)
    public void onEvent(final EventTick event) {
        for (final Object o : this.mc.theWorld.loadedEntityList) {
            final Entity e = (Entity)o;
            if (e instanceof EntityPlayer && !Aboat.targets.contains(e)) {
                Aboat.targets.add(e);
            }
        }
        for (final Object o : this.mc.theWorld.loadedEntityList) {
            final Entity e = (Entity)o;
            if (this.isValidTarget(e) && !Aboat.attackList.contains(e)) {
                Aboat.attackList.add(e);
            }
        }
        for (final Object o : Aboat.attackList) {
            final Entity e = (Entity)o;
            if (!this.isValidTarget(e)) {
                Aboat.attackList.remove(e);
            }
        }
        if (Aboat.currentTarget >= 1) {
            Aboat.currentTarget = 0;
        }
        if (!this.mc.thePlayer.isDead && (CombatUtils.isWithingFOV(Aboat.attackList.get(Aboat.currentTarget), 180.0f) || this.mc.objectMouseOver.entityHit != null) && this.mc.thePlayer.canEntityBeSeen(Aboat.attackList.get(Aboat.currentTarget))) {
        	if(mc.thePlayer.isEntityAlive() && this.mc.currentScreen == null && !(this.mc.currentScreen instanceof GuiChat)) {
	            final float yawNeeded = CombatUtils.faceTarget(Aboat.attackList.get(Aboat.currentTarget), (float)this.strength.getValue(), 0.0f, false)[0];
	            final float pitchNeeded = CombatUtils.faceTarget(Aboat.attackList.get(Aboat.currentTarget), 0.0f, 0.0f, false)[1];
	            this.mc.thePlayer.rotationYaw = yawNeeded;
	            this.mc.thePlayer.rotationPitch = pitchNeeded;
        	}
        }
    }

}
