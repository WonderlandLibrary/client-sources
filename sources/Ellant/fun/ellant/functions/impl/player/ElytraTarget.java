package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.Setting;
import fun.ellant.functions.settings.impl.SliderSetting;

@FunctionRegister(
        name = "ElytraTarget",
        type = Category.COMBAT, desc = "Гонится за игроком когда вы на элитрах"
)
public class ElytraTarget extends Function {
    SliderSetting distanceSetting = new SliderSetting("Дистанция", 5.0F, 1.0F, 35.0F, 0.5F);

    public ElytraTarget() {
        this.addSettings(new Setting[]{this.distanceSetting});
    }

    @Subscribe
    public void onEvent(EventUpdate event) {
        if (event instanceof EventUpdate) {
            LivingEntity auraTarget = Ellant.getInstance().getFunctionRegistry().getKillAura().getTarget();
            if (auraTarget != null && mc.player.getDistanceSq(auraTarget) <= (double)((Float)this.distanceSetting.get() * (Float)this.distanceSetting.get())) {
                ItemStack chestStack = mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                if (!chestStack.isEmpty() && chestStack.getItem() instanceof ElytraItem) {
                    float[] rotations = this.getRotations(auraTarget);
                    mc.player.rotationYaw = rotations[0];
                    mc.player.rotationPitch = rotations[1];
                }
            }
        }

    }

    public float[] getRotations(LivingEntity entity) {
        double x = entity.getPosX() - mc.player.getPosX();
        double y = entity.getPosY() - mc.player.getPosY() + 0.228D;
        double z = entity.getPosZ() - mc.player.getPosZ();
        double u = Math.sqrt(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 57.29577951308232D - 90.0D);
        float pitch = (float)(-Math.atan2(y, u) * 57.29577951308232D);
        return new float[]{yaw, pitch};
    }
}