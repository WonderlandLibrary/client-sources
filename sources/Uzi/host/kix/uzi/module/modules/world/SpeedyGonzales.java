package host.kix.uzi.module.modules.world;

import com.darkmagician6.eventapi.SubscribeEvent;

import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.events.BlockDamageEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import com.darkmagician6.eventapi.SubscribeEvent;

/**
 * Created by myche on 3/6/2017.
 */
public class SpeedyGonzales extends Module{

    public SpeedyGonzales() {
        super("SpeedyGonzales", 0, Category.WORLD);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.isEnabled()) {
            if (event.type == EventType.PRE) {
                Minecraft.getMinecraft().thePlayer
                        .addPotionEffect(new PotionEffect(Potion.digSpeed.id, Integer.MAX_VALUE, 0));
                Minecraft.getMinecraft().playerController.blockHitDelay = 0;
            }
        }
    }

    @SubscribeEvent
    public void onBlockDamage(BlockDamageEvent event) {
        if (this.isEnabled()) {
            Minecraft.getMinecraft().thePlayer.swingItem();
            final PlayerControllerMP playerController = Minecraft.getMinecraft().playerController;
            playerController.curBlockDamageMP += getBlock(event.getBlockPos().getX(), event.getBlockPos().getY(),
                    event.getBlockPos().getZ()).getPlayerRelativeBlockHardness(Minecraft.getMinecraft().thePlayer,
                    Minecraft.getMinecraft().theWorld, event.getBlockPos())
                    * 0.186F;
        }
    }

    public Block getBlock(double posX, double posY, double posZ) {
        posX = MathHelper.floor_double(posX);
        posY = MathHelper.floor_double(posY);
        posZ = MathHelper.floor_double(posZ);
        return this.mc.theWorld.getChunkFromBlockCoords(new BlockPos(posX, posY, posZ))
                .getBlock(new BlockPos(posX, posY, posZ));
    }

    @Override
    public void onDisable() {
        mc.thePlayer.removePotionEffect(Potion.digSpeed.id);
    }

}
