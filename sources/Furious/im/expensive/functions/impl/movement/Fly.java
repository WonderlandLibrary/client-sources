package im.expensive.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventPacket;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.player.MoveUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name = "Fly", type = Category.Movement)
public class Fly extends Function {

    private final ModeSetting mode = new ModeSetting("Мод", "Vanilla", "Vanilla", "Matrix Jump", "Matrix Glide",
            "GrimAC");
    private final SliderSetting horizontal = new SliderSetting("По горизонтали", 0.5f, 0f, 5f, 0.1f);
    private final SliderSetting vertical = new SliderSetting("По вертикали", 0.5f, 0f, 5f, 0.1f);

    public Fly() {
        addSettings(mode, horizontal, vertical);
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
                for (Entity en : mc.world.getAllEntities()) {
                    if (en instanceof BoatEntity) {
                        if (mc.player.getDistance(en) <= 2) {
                            MoveUtils.setMotion(1.2f);
                            mc.player.motion.y = 1;
                            break;
                        }
                    }
                }
            }

            case 4 -> { // GrimAC Elytra
                if (mc.player.ticksExisted % 2 != 0) return;

                int slot = -1;

                for (ItemStack stack : mc.player.inventory.mainInventory) {
                    if (stack.getItem() instanceof ElytraItem) {
                        slot = mc.player.inventory.mainInventory.indexOf(stack);
                    }
                }

                mc.player.abilities.isFlying = false;

                if (slot == -1) return;

                int chestSlot = 6;

                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, chestSlot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);

                mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                mc.player.abilities.isFlying = true;

                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, chestSlot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            }
        }
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
