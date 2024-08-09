package mathax.client.systems.modules.combat;


import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Jebus.MathUtil;
import mathax.client.utils.entity.EntityUtils;
import mathax.client.utils.player.Rot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;

public class SyracuseAimAssist extends Module {

    public SyracuseAimAssist() {
        super(Categories.Combat, Items.BOW, "SyracuseAimAssist", "Syracuse Aim Assist");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();


    private final Setting<Double> distance = sgGeneral.add(new DoubleSetting.Builder()
        .name("distance")
        .description("The distance to aim")
        .defaultValue(6)
        .min(3)
        .sliderMax(10)
        .build()
    );

    private final Setting<Double> smoothness = sgGeneral.add(new DoubleSetting.Builder()
        .name("smoothness")
        .description("The smoothness of the aim")
        .defaultValue(6)
        .min(0)
        .sliderMax(10)
        .build()
    );

    private final Setting<Boolean> seeonly = sgGeneral.add(new BoolSetting.Builder()
        .name("seeonly")
        .description("Only aim at entities you can see")
        .defaultValue(true)
        .build()
    );

    private Setting<Boolean> verticle = sgGeneral.add(new BoolSetting.Builder()
        .name("verticle")
        .description("Aim at verticle entities")
        .defaultValue(true)
        .build()
    );

    private Setting<Boolean> horizontal = sgGeneral.add(new BoolSetting.Builder()
        .name("horizontal")
        .description("Aim at horizontal entities")
        .defaultValue(false)
        .build()
    );


    public static boolean isOverEntity() {
        if (mc.crosshairTarget == null) return false;
        HitResult hitResult = mc.crosshairTarget;
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            return true;
        } else {
            return false;
        }
    }

    public boolean isHoldingFirework() {
        PlayerInventory inventory = mc.player.getInventory();
        ItemStack heldItem = inventory.getMainHandStack();

        return heldItem.getItem() instanceof FireworkRocketItem;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (isHoldingFirework()) return;
        if (isOverEntity()) return;
        if (mc.currentScreen != null) return;

        PlayerEntity targetPlayer = EntityUtils.findClosest(PlayerEntity.class, distance.get().floatValue());

        if (targetPlayer == null || (seeonly.get() && !mc.player.canSee(targetPlayer))) {
            return;
        }

        Rot targetRot = MathUtil.getDir(mc.player, targetPlayer.getPos());

        float yawDist = MathHelper.subtractAngles((float) targetRot.yaw(), mc.player.getYaw());
        float pitchDist = MathHelper.subtractAngles((float) targetRot.pitch(), mc.player.getPitch());

        float yaw;
        float pitch;

        float stren = smoothness.get().floatValue() / 10;

        yaw = mc.player.getYaw();
        if (Math.abs(yawDist) > stren) {
            yaw = mc.player.getYaw();
            if (yawDist < 0) {
                yaw += stren;
            } else if (yawDist > 0) {
                yaw -= stren;
            }
        } else {
            // aw = (float) targetRot.yaw();
        }

        pitch = mc.player.getPitch();
        if (Math.abs(pitchDist) > stren) {
            pitch = mc.player.getPitch();
            if (pitchDist < 0) {
                pitch += stren;
            } else if (pitchDist > 0) {
                pitch -= stren;
            }
        } else {
            // pitch = (float) targetRot.pitch();
        }

        float stren2 = smoothness.get().floatValue() / 50;
        yaw = MathHelper.lerpAngleDegrees(stren2, mc.player.getYaw(), (float) targetRot.yaw());
        pitch = MathHelper.lerpAngleDegrees(stren2, mc.player.getPitch(), (float) targetRot.pitch());
        if (verticle.get()) mc.player.setYaw(yaw);
        if (horizontal.get()) mc.player.setPitch(pitch);
    }
}
