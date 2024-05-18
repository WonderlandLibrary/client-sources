package wwe;

import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import com.mojang.authlib.*;
import wwe.utils.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public class OCue extends EntityOtherPlayerMP
{
    public OCue(final WorldClient worldClient, final GameProfile gameProfile) {
        super((World)Wrapper.mc.world, Wrapper.mc.player.getGameProfile());
    }
    
    public void rrFs(final EntityPlayer entityPlayer, final boolean b) {
        if (b) {
            this.inventory.copyInventory(entityPlayer.inventory);
            this.setHealth(entityPlayer.getHealth());
            this.foodStats = entityPlayer.getFoodStats();
            this.experienceLevel = entityPlayer.experienceLevel;
            this.experienceTotal = entityPlayer.experienceTotal;
            this.experience = entityPlayer.experience;
            this.setScore(entityPlayer.getScore());
            this.teleportDirection = entityPlayer.getTeleportDirection();
            if (OCue.\u2002\u2007\u2008) {
                throw null;
            }
        }
        else if (this.world.getGameRules().getBoolean("keepInventory") || entityPlayer.isSpectator()) {
            this.inventory.copyInventory(entityPlayer.inventory);
            this.experienceLevel = entityPlayer.experienceLevel;
            this.experienceTotal = entityPlayer.experienceTotal;
            this.experience = entityPlayer.experience;
            this.setScore(entityPlayer.getScore());
        }
        this.xpSeed = entityPlayer.getXPSeed();
        this.getDataManager().set(OCue.PLAYER_MODEL_FLAG, (Object)entityPlayer.getDataManager().get(OCue.PLAYER_MODEL_FLAG));
    }
}
