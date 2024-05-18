package host.kix.uzi.module.modules.combat;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.Stopwatch;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

import java.util.Random;

/**
 * Created by myche on 4/17/2017.
 */
public class Triggerbot extends Module {

    private Random random = new Random();
    private Stopwatch stopwatch = new Stopwatch();

    public Triggerbot() {
        super("Triggerbot", 0, Category.COMBAT);
    }

    @SubscribeEvent
    public void update(UpdateEvent e){
        MovingObjectPosition objectMouseOver = mc.objectMouseOver;
        if (objectMouseOver == null)
            return;
        Entity entity = objectMouseOver.entityHit;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase living = (EntityLivingBase) entity;
            if (stopwatch.hasCompleted(getDelay())) {
                if (!isValidEntity(living))
                    return;
                mc.thePlayer.swingItem();
                mc.playerController.attackEntity(Minecraft.getMinecraft().thePlayer, living);
                stopwatch.reset();
            }
        }
    }

    private boolean isValidEntity(EntityLivingBase entity) {
        if (entity == null)
            return false;
        if (entity == mc.thePlayer)
            return false;
        if (!entity.isEntityAlive())
            return false;
        if (mc.thePlayer.getDistanceToEntity(entity) > 4)
            return false;
        if (entity.isInvisibleToPlayer(mc.thePlayer))
            return false;
        if (entity.getTeam() != null && entity.getTeam().isSameTeam(mc.thePlayer.getTeam()))
            return false;
        if (entity instanceof EntityPlayer) {
            return !Uzi.getInstance().getFriendManager().get(entity.getName()).isPresent();
        } else if (entity instanceof IAnimals && !(entity instanceof IMob)) {
            return false;
        } else if (entity instanceof IMob) {
            return false;
        }
        return false;
    }


    public Long getDelay() {
        return 125L - random.nextInt(250);
    }


}
