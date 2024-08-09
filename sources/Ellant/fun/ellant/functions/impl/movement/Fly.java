package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventPacket;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.player.MoveUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name = "Fly", type = Category.MOVEMENT, desc = "Позволяет вам летать")
public class Fly extends Function {

    private final ModeSetting mode = new ModeSetting("Мод", "Vanilla", "Vanilla", "Matrix Jump", "Matrix Glide",
            "GrimAC", "Storm Elytra", "Storm Glitch");
    private final SliderSetting horizontal = new SliderSetting("По горизонтали", 0.5f, 0f, 5f, 0.1f).setVisible(() -> !mode.is("Storm Elytra"));
    private final SliderSetting vertical = new SliderSetting("По вертикали", 0.5f, 0f, 5f, 0.1f).setVisible(() -> !mode.is("Storm Elytra"));
    private final SliderSetting verticalstorm = new SliderSetting("Подбрасывание", 0.3f, 0.1f, 1f, 0.01f).setVisible(() -> mode.is("Storm Elytra"));
    public Fly() {
        addSettings(mode, horizontal, vertical, verticalstorm);
    }

    public Entity vehicle;

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.player == null || mc.world == null)
            return;

        switch (mode.getIndex()) {
            case 0 -> {
                updatePlayerMotion();
            }

            case 1 -> { // Matrix Jump
                if (mc.player.isOnGround())
                    mc.player.jump();
                else {
                    MoveUtils.setMotion(Math.min(horizontal.get(), 1.97f));
                    mc.player.motion.y = vertical.get();
                }
            }

            case 2 -> { // Matrix Glide
                mc.player.motion = Vector3d.ZERO;
                MoveUtils.setMotion(horizontal.get());
                mc.player.setMotion(mc.player.getMotion().x, -0.003, mc.player.getMotion().z);
            }

            case 3 -> { // GrimAC
                for (Entity entity : mc.world.getAllEntities()) {
                    if (entity instanceof BoatEntity && mc.player.getDistance(entity) <= 2) {
                        MoveUtils.setMotion(1.2f);
                        mc.player.motion.y = 1;
                        break;
                    }
                }
            }

            case 4 -> { // Storm Elytra
                if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == Items.ELYTRA) {
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                    mc.player.setMotion(mc.player.getMotion().getX(), verticalstorm.get(), mc.player.getMotion().getZ());
                    mc.playerController.processRightClick(mc.player, mc.world, Hand.MAIN_HAND);
                } else {
                    int elytraSlot = findInventoryElytra();
                    if (elytraSlot != -1) {
                        mc.playerController.windowClick(0, elytraSlot, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
                    }
                }
                break;
            }
            case 5 -> { // Storm Glitch
                double posX;
                double posZ;
                MoveUtils.setMotion(0.0F);
                mc.player.setVelocity(0, 0, 0);
                if (mc.player.ticksExisted % 10 == 1 && mc.player.hurtTime == 0) {
                    mc.player.setOnGround(true);
                    posX = -Math.sin(Math.toRadians(mc.player.rotationYaw)) * 0.37D;
                    posZ = Math.cos(Math.toRadians(mc.player.rotationYaw)) * 0.37D;
                    mc.getConnection().sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX() + (!mc.gameSettings.keyBindJump.pressed ? posX : 0), mc.player.getPosY() + randomize(0.15f, 0.19f), mc.player.getPosZ() + (!mc.gameSettings.keyBindJump.pressed ? posZ : 0), true));
                    mc.getConnection().sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX() + (!mc.gameSettings.keyBindJump.pressed ? posX : 0), mc.player.getPosY() + (double) 80, mc.player.getPosZ() + (!mc.gameSettings.keyBindJump.pressed ? posZ : 0), true));
                }
            }
        }
    }
    public static float randomize(float min, float max) {
        return (float) (min + (max - min) * Math.random());
    }
    public int findInventoryElytra() {
        for (int i = 9; i < 36; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == Items.ELYTRA) {
                return i;
            }
        }
        return -1;
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (mc.player == null || mc.world == null)
            return;

        switch (mode.getIndex()) {
            case 1 -> { // Matrix Jump
                if (e.getPacket() instanceof SPlayerPositionLookPacket p) {
                    if (mc.player == null)
                        toggle();
                    mc.player.setPosition(p.getX(), p.getY(), p.getZ());
                    mc.player.connection.sendPacket(new CConfirmTeleportPacket(p.getTeleportId()));
                    e.cancel();
                    toggle();
                }
            }
            case 3 -> { // GrimAC
                if (e.getPacket() instanceof SPlayerPositionLookPacket p) {

                    toggle();
                }
            }
        }
    }

    private void updatePlayerMotion() {
        double motionX = mc.player.getMotion().x;
        double motionY = getMotionY();
        double motionZ = mc.player.getMotion().z;

        MoveUtils.setMotion(horizontal.get());
        mc.player.motion.y = motionY;
    }

    private double getMotionY() {
        return mc.gameSettings.keyBindSneak.pressed ? -vertical.get()
                : mc.gameSettings.keyBindJump.pressed ? vertical.get() : 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.player.abilities.isFlying = false;
    }
}
