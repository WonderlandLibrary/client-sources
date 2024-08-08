package in.momin5.cookieclient.client.modules.player;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.misc.Timer;
import in.momin5.cookieclient.api.util.utils.player.MessageUtil;
import in.momin5.cookieclient.api.util.utils.rotation.RotationUtils;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;

public class Quiver extends Module {

    private Timer releaseTimer = new Timer();
    SettingNumber releaseDelay = register(new SettingNumber("Release Delay",this,200,0,2000,1));
    //SettingBoolean onlyStrength = register(new SettingBoolean("OnlyGoodArrows",this,true));

    public Quiver() {
        super("Quiver", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        if(nullCheck())
            return;
        releaseTimer.reset();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        releaseTimer.reset();
    }

    @Override
    public void onUpdate(){
        if(mc.player.getHeldItemMainhand().getItem() == Items.BOW && mc.gameSettings.keyBindUseItem.isKeyDown()) {
            if (!releaseTimer.hasPassed((int) releaseDelay.getValue()))
                return;

            RotationUtils.faceVectorPacketInstant(new Vec3d(mc.player.posX,mc.player.posY + 2,mc.player.posZ),false);
            releaseTimer.reset();
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.player.resetActiveHand();

        }
    }

    private int findStrengthArrows() {
        for(int i = 0;i < 36;i++) {
            ItemStack item = mc.player.inventoryContainer.getInventory().get(i);
            PotionType potion = PotionUtils.getPotionFromItem(item);
            if(potion == PotionTypes.STRENGTH || potion == PotionTypes.STRONG_STRENGTH || potion == PotionTypes.LONG_STRENGTH) {
                return i;
            }
        }
        return -1;
    }

    private int findSwiftnessArrows() {
        for(int i = 0;i < 36;i++) {
            ItemStack item = mc.player.inventoryContainer.getInventory().get(i);
            PotionType potion = PotionUtils.getPotionFromItem(item);
            if(potion == PotionTypes.SWIFTNESS || potion == PotionTypes.STRONG_SWIFTNESS || potion == PotionTypes.LONG_SWIFTNESS) {
                return i;
            }
        }
        return -1;
    }
}
