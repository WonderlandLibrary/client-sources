package wtf.resolute.moduled.impl.movement;

import com.google.common.eventbus.Subscribe;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.network.IPacket;
import wtf.resolute.evented.EventPacket;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import wtf.resolute.moduled.settings.impl.SliderSetting;
import wtf.resolute.utiled.player.InventoryUtil;
import wtf.resolute.utiled.player.MoveUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.vector.Vector3d;

import java.util.concurrent.CopyOnWriteArrayList;

@ModuleAnontion(name = "Fly", type = Categories.Movement,server = "")
public class Fly extends Module {

    private final ModeSetting flMode = new ModeSetting("Flight Mode",
            "Motion",
            "Motion", "Glide", "Трезубец");

    private final SliderSetting motion
            = new SliderSetting("Speed XZ",
            1F,
            0F,
            8F,
            0.1F).setVisible(() -> !flMode.is("Трезубец"));

    private final SliderSetting motionY
            = new SliderSetting("Speed Y",
            1F,
            0F,
            8F,
            0.1F).setVisible(() -> !flMode.is("Трезубец"));

    private final BooleanSetting setPitch = new BooleanSetting("Поворачивать голову", false).setVisible(() -> flMode.is("Трезубец"));
    private int originalSlot = -1;
    public long lastUseTridantTime = 0;

    public CopyOnWriteArrayList<IPacket<?>> packets = new CopyOnWriteArrayList<>();

    public Fly() {
        addSettings(flMode, motion, motionY, setPitch);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {

        if (event instanceof EventUpdate) {

            handleFlyMode();
        }
    }

    /**
     * Обрабатывает выбранный режим полета.
     */
    private void handleFlyMode() {
        switch (flMode.get()) {
            case "Motion" -> handleMotionFly();
            case "Glide" -> handleGlideFly();
            case "Трезубец" -> handleTridentFly();
        }
    }


    private void handleTridentFly() {
        final int slot = InventoryUtil.getTrident();
        if ((mc.player.isInWater() || mc.world.getRainStrength(1) == 1)
                && EnchantmentHelper.getRiptideModifier(mc.player.getHeldItemMainhand()) > 0) {
            if (slot != -1) {
                originalSlot = mc.player.inventory.currentItem;
                if (mc.gameSettings.keyBindUseItem.pressed && setPitch.get()) {
                    mc.player.rotationPitch = -90;
                }
                mc.gameSettings.keyBindUseItem.setPressed(mc.player.ticksExisted % 20 < 15);
            }
        }
    }

    /**
     * Обрабатывает режим полета "Motion".
     */
    private void handleMotionFly() {
        final float motionY = this.motionY.get().floatValue();
        final float speed = this.motion.get().floatValue();

        mc.player.motion.y = 0;

        if (mc.gameSettings.keyBindJump.pressed) {
            mc.player.motion.y = motionY;
        } else if (mc.player.isSneaking()) {
            mc.player.motion.y = -motionY;
        }

        MoveUtils.setMotion(speed);
    }

    /**
     * Обрабатывает режим полета "Glide".
     */
    private void handleGlideFly() {
        if (mc.player.isOnGround()) {
            mc.player.motion.y = 0.42;  // Устанавливаем вертикальную скорость при нахождении на земле
        } else {
            mc.player.setVelocity(0, -0.003, 0);  // Устанавливаем вертикальную скорость при полете
            MoveUtils.setMotion(motion.get().floatValue());  // Устанавливаем горизонтальную скорость движения
        }
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;

        if (flMode.is("Трезубец")) {
            if (originalSlot != -1) {
                mc.player.inventory.currentItem = originalSlot;
                originalSlot = -1;
            }
            if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                mc.gameSettings.keyBindUseItem.setPressed(false);
            }
        }
        super.onDisable();
    }
}