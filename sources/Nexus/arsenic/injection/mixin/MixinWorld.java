package arsenic.injection.mixin;

import arsenic.event.impl.EventPlayerJoinWorld;
import arsenic.main.Nexus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin(value = World.class, priority = 1111)
public class MixinWorld {


    /*
    @ModifyVariable(method = "spawnEntityInWorld", at = @At("STORE"), ordinal = 0)
    public EntityPlayer mixinSpawnEntityInWorld(EntityPlayer entityplayer) {
        Arsenic.getNexus().getEventManager().post(new EventPlayerJoinWorld(entityplayer, entityplayer.getEntityWorld()));
        return entityplayer;
    } */

    @Inject(method = "spawnEntityInWorld", at = @At("HEAD"))
    public void spawnEntityInWorld(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if(entityIn instanceof EntityPlayer)
            Nexus.getNexus().getEventManager().post(new EventPlayerJoinWorld((EntityPlayer) entityIn, entityIn.getEntityWorld()));
    }
}
