package alphentus.mod.mods.combat;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 08.08.2020.
 */
public class AntiBots extends Mod {

    public Setting mineplex = new Setting("Mineplex", true, this);
    public Setting attackDead = new Setting("Attack Dead", true, this);
    public Setting timolia = new Setting("Timolia", false, this);
    public ArrayList<Entity> timoliaProofed = new ArrayList<Entity>();

    public AntiBots() {
        super("AntiBots", Keyboard.KEY_NONE, true, ModCategory.COMBAT);

        Init.getInstance().settingManager.addSetting(mineplex);
        Init.getInstance().settingManager.addSetting(attackDead);
        Init.getInstance().settingManager.addSetting(timolia);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState())
            return;
        if(!timolia.isState())
            return;
        KillAura killAura = Init.getInstance().modManager.getModuleByClass(KillAura.class);
        if (event.getType() == Type.TICKUPDATE) {
            for (Entity e : mc.theWorld.loadedEntityList) {
                EntityLivingBase livingBase = (EntityLivingBase) e;
                //if (!timoliaProofed.contains(e)) {
                //if (Init.getInstance().modManager.getModuleByClass(KillAura.class).isInTabList(e) && !e.isInvisible() && e != mc.thePlayer) {

/*                        if (!Init.getInstance().modManager.getModuleByClass(KillAura.class).mineplexProof.contains(e))
                        systemMessage("Health: " + livingBase.getHealth());*/

                if (killAura.getState() && !killAura.mineplexProof.contains(e)) {
                    systemMessage("----------START STATS----------");
                    systemMessage("Name: " + e.getName());
                    systemMessage("Ping: " + mc.getNetHandler().getPlayerInfo(e.getUniqueID()).getResponseTime());
                    systemMessage("Health: " + livingBase.getHealth());
                    systemMessage("Sprinting: " + e.isSprinting());
                    systemMessage("Sneaking: " + e.isSneaking());
                    systemMessage("HurtTime: " + e.hurtResistantTime);
                    systemMessage("Is Ground: " + e.onGround);
                    systemMessage("Is Air: " + e.isAirBorne);
                    systemMessage("Alive: " + e.isEntityAlive());
                    systemMessage("Dead: " + e.isDead);
                    systemMessage("PosX: " + (e.posX - e.prevPosX));
                    systemMessage("PosY: " + (e.posY - e.prevPosY));
                    systemMessage("PosZ: " + (e.posZ - e.prevPosZ));
                    systemMessage("Existing Ticks: " + e.ticksExisted);
                    systemMessage("NoClip: " + e.noClip);
                    systemMessage("EntityID: " + e.getEntityId());
                    systemMessage("UniqueID: " + e.getUniqueID());
                    systemMessage("StepHeight: " + e.stepHeight);
                    systemMessage("----------END STATS----------");

                    if (checkTicks(e, 50) && checkMovement(e) && checkName(e) && checkSprinting(e) && checkGround(e) && checkPing(e) && getItemStack(e) != null) {
                        timoliaProofed.add(e);
                    }
                    //}
                    //}
                }
            }
        }
    }

    public boolean checkTicks(Entity e, int ticks) {
        return e.ticksExisted > ticks;
    }

    public boolean checkMovement(Entity e) {
        double xDif = e.posX - e.prevPosX;
        double zDif = e.posZ - e.prevPosZ;
        return ((xDif > 0 && xDif < 0.3) || (xDif < 0 && xDif > -0.3)) && ((zDif > 0 && zDif < 0.3) || (zDif < 0 && zDif > -0.3));
    }

    public boolean checkName(Entity e) {
        if (e.getName().contains("-"))
            return false;
        if (e.getName().contains("/"))
            return false;
        if (e.getName().contains("|"))
            return false;
        if (e.getName().contains("<"))
            return false;
        if (e.getName().contains(">"))
            return false;
        if (e.getName().contains("§"))
            return false;
        return true;
    }

    public boolean checkSprinting(Entity e) {
        return e.isSprinting() && !e.isSneaking();
    }

    public boolean checkGround(Entity e) {
        return e.onGround && !e.isAirBorne;
    }

    public boolean checkPing(Entity e) {
        int ping = mc.getNetHandler().getPlayerInfo(e.getUniqueID()).getResponseTime();
        return ping >= 0 && ping <= 300;
    }

    public ItemStack getItemStack(Entity e) {
        return ((EntityLivingBase) e).getHeldItem();
    }

    @Override
    public void onEnable() {
        timoliaProofed.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        timoliaProofed.clear();
        super.onDisable();
    }
}