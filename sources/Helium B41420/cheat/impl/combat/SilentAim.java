package rip.helium.cheat.impl.combat;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.cheat.FriendManager;
import rip.helium.cheat.impl.combat.aura.Aura;
import rip.helium.event.minecraft.MouseClickEvent;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.event.minecraft.RunTickEvent;
import rip.helium.utils.EntityUtils;
import rip.helium.utils.RotationUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SilentAim extends Cheat {

    private EntityLivingBase targetEntity;

    public SilentAim() {
        super("pSilent", "epic aimbot", CheatCategory.COMBAT);

    }

    private EntityLivingBase bowtarget;
    private float velocity;

    @Collect
    public void onUpdate(final MouseClickEvent event) {
        if (event.getMouseButton() == 0) {
            targetEntity = getEntity();
            if (targetEntity != null) { //npe makes it commit sudoku
                attackEntity(targetEntity);
            }
        }
    }

    public boolean canAttackEntity(EntityLivingBase entity) {
        return entity != null && entity.isEntityAlive() && mc.thePlayer.isEntityAlive() && mc.thePlayer.canEntityBeSeen(entity) && mc.thePlayer.getDistanceToEntity(entity) <= 4.0 && entity != mc.thePlayer && entity instanceof EntityLivingBase;
    }
    
    public final void attackEntity(EntityLivingBase entity) {
        mc.thePlayer.swingItem();
        mc.playerController.attackEntity(mc.thePlayer, entity);
    }
    

    private EntityLivingBase getEntity() {
        for (final Object o : mc.theWorld.loadedEntityList) {
            if (!(o instanceof EntityLivingBase)) continue;
            final EntityLivingBase entity = (EntityLivingBase) o;
            if (entity == mc.thePlayer) continue;
            if (!mc.thePlayer.canEntityBeSeen(entity)) continue;
            if (canAttackEntity(entity)) {
                return entity;
            }
        }
        return null;
    }
}
