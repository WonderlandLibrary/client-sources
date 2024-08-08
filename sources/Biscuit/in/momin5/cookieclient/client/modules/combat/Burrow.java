package in.momin5.cookieclient.client.modules.combat;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.player.BlockUtils;
import in.momin5.cookieclient.api.util.utils.player.InventoryUtils;
import in.momin5.cookieclient.api.util.utils.player.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.lang.reflect.Field;

public class Burrow extends Module {

    SettingBoolean rotate = register( new SettingBoolean("Rotate", this,true));
    SettingBoolean instant = register( new SettingBoolean("Instant", this,true));
    SettingMode type = register( new SettingMode("Type",this, "Packet", "Packet", "Normal"));
    SettingMode block = register( new SettingMode("Block",this, "All", "All", "EChest", "Chest"));
    SettingNumber force = register(new SettingNumber("Force", this,1.5, -5.0, 10.0,0.5));
    SettingBoolean center = register( new SettingBoolean("Center", this,false));
    SettingBoolean bypass = register(new SettingBoolean("Bypass", this,false));


    public Burrow(){
        super("Burrow","hurr durr why you burr?", Category.COMBAT);
    }

    CrystalAura crystalAura = new CrystalAura();
    int swapBlock = -1;
    Vec3d centerBlock = Vec3d.ZERO;
    BlockPos oldPos;
    boolean flag;

    @Override
    public void onEnable() {
        if (nullCheck()) {
            this.disable();
            return;
        }
        flag = false;
        if (crystalAura.isEnabled()) {
            flag = true;
            crystalAura.disable();
        }

        mc.player.motionX = 0;
        mc.player.motionZ = 0;

        centerBlock = this.getCenter(mc.player.posX, mc.player.posY, mc.player.posZ);
        if (centerBlock != Vec3d.ZERO && center.isEnabled()) {
            double x_diff = Math.abs(centerBlock.x - mc.player.posX);
            double z_diff = Math.abs(centerBlock.z - mc.player.posZ);
            if (x_diff <= 0.1 && z_diff <= 0.1) {
                centerBlock = Vec3d.ZERO;
            } else {
                double motion_x = centerBlock.x - mc.player.posX;
                double motion_z = centerBlock.z - mc.player.posZ;
                mc.player.motionX = motion_x / 2;
                mc.player.motionZ = motion_z / 2;
            }
        }

        oldPos = PlayerUtil.getPlayerPos();
        switch (block.getMode()) {
            case "All":
                swapBlock = InventoryUtils.findBlockInHotbar(Blocks.OBSIDIAN);
                break;
            case "EChest":
                swapBlock = InventoryUtils.findBlockInHotbar(Blocks.ENDER_CHEST);
                break;
            case "Chest":
                swapBlock = InventoryUtils.findBlockInHotbar(Blocks.CHEST);
                break;
        }
        if (swapBlock == -1) {
            this.disable();
            return;
        }
        if (instant.isEnabled()) {
            this.setTimer(50f);
        }
        if (type.is("Normal")) {
            mc.player.jump();
        }
    }

    @Override
    public void onUpdate() {
        if (type.is("Normal")) {
            if (mc.player.posY > (oldPos.getY() + 1.04)) {
                int old = mc.player.inventory.currentItem;
                this.switchToSlot(swapBlock);
                BlockUtils.placeBlock(oldPos, EnumHand.MAIN_HAND, rotate.isEnabled(), true, false);
                this.switchToSlot(old);
                mc.player.motionY = force.value;
                this.disable();
            }
        } else {
            mc.player.connection.sendPacket(
                    new CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.41999998688698,
                            mc.player.posZ,
                            true
                    )
            );
            mc.player.connection.sendPacket(
                    new CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.7531999805211997,
                            mc.player.posZ,
                            true
                    )
            );
            mc.player.connection.sendPacket(
                    new CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.00133597911214,
                            mc.player.posZ,
                            true
                    )
            );
            mc.player.connection.sendPacket(
                    new CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.16610926093821,
                            mc.player.posZ,
                            true
                    )
            );
            int old = mc.player.inventory.currentItem;
            this.switchToSlot(swapBlock);
            BlockUtils.placeBlock(oldPos, EnumHand.MAIN_HAND, rotate.isEnabled(), true, false);
            this.switchToSlot(old);
            mc.player.connection.sendPacket(
                    new CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + force.getValue(),
                            mc.player.posZ,
                            false
                    )
            );
            if(bypass.isEnabled() && !mc.player.isSneaking()) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                mc.player.setSneaking(true);
                mc.playerController.updateController();
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                mc.player.setSneaking(false);
                mc.playerController.updateController();
            }


            this.disable();
        }
    }

    @Override
    public void onDisable(){
        if(instant.isEnabled() && !nullCheck()){
            this.setTimer(1f);
        }
        if (flag) {
            CrystalAura crystalAura = new CrystalAura();
            crystalAura.enable();
        }
    }

    private void switchToSlot(final int slot) {
        mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
        mc.player.inventory.currentItem = slot;
        mc.playerController.updateController();
    }

    private String tickLengt = isObfuscated() ? "field_194149_e" : "tickLength";
    private String time = isObfuscated() ? "field_71428_T" : "timer";

    public boolean isObfuscated() {
        try {
            return Minecraft.class.getDeclaredField("instance") == null;
        } catch (Exception var1) {
            return true;
        }
    }

    private void setTimer(float value) {
        try {
            Field timer = Minecraft.class.getDeclaredField(time);
            timer.setAccessible(true);
            Field tickLength = Timer.class.getDeclaredField(tickLengt);
            tickLength.setAccessible(true);
            tickLength.setFloat(timer.get(mc), 50.0F / value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Vec3d getCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D ;

        return new Vec3d(x, y, z);
    }


}
