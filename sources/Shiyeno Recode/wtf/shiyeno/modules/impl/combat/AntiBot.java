package wtf.shiyeno.modules.impl.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "AntiBot",
        type = Type.Combat
)
public class AntiBot extends Function {
    private final ModeSetting mode = new ModeSetting("Выбор режима", "Старый Matrix", new String[]{"Старый Matrix", "Новый Matrix"});
    private final TimerUtil timerUtil = new TimerUtil();
    public static List<Entity> isBot = new ArrayList();

    public AntiBot() {
        this.addSettings(new Setting[]{this.mode});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (this.mode.is("Новый Matrix")) {
                this.newMatrix();
            }

            if (this.mode.is("Старый Matrix")) {
                this.oldMatrix();
            }
        }
    }

    public void oldMatrix() {
        Iterator var1 = mc.world.getPlayers().iterator();

        while(var1.hasNext()) {
            PlayerEntity entity = (PlayerEntity)var1.next();
            if (!entity.getUniqueID().equals(PlayerEntity.getOfflineUUID(entity.getName().getString())) && !isBot.contains(entity)) {
                isBot.add(entity);
            }
        }
    }

    public void newMatrix() {
        Iterator var1 = mc.world.getPlayers().iterator();

        while(var1.hasNext()) {
            PlayerEntity entity = (PlayerEntity)var1.next();
            if (mc.player != entity && ((ItemStack)entity.inventory.armorInventory.get(0)).getItem() != Items.AIR && ((ItemStack)entity.inventory.armorInventory.get(1)).getItem() != Items.AIR && ((ItemStack)entity.inventory.armorInventory.get(2)).getItem() != Items.AIR && ((ItemStack)entity.inventory.armorInventory.get(3)).getItem() != Items.AIR && ((ItemStack)entity.inventory.armorInventory.get(0)).isEnchantable() && ((ItemStack)entity.inventory.armorInventory.get(1)).isEnchantable() && ((ItemStack)entity.inventory.armorInventory.get(2)).isEnchantable() && ((ItemStack)entity.inventory.armorInventory.get(3)).isEnchantable() && entity.getHeldItemOffhand().getItem() == Items.AIR && (((ItemStack)entity.inventory.armorInventory.get(0)).getItem() == Items.LEATHER_BOOTS || ((ItemStack)entity.inventory.armorInventory.get(1)).getItem() == Items.LEATHER_LEGGINGS || ((ItemStack)entity.inventory.armorInventory.get(2)).getItem() == Items.LEATHER_CHESTPLATE || ((ItemStack)entity.inventory.armorInventory.get(3)).getItem() == Items.LEATHER_HELMET || ((ItemStack)entity.inventory.armorInventory.get(0)).getItem() == Items.IRON_BOOTS || ((ItemStack)entity.inventory.armorInventory.get(1)).getItem() == Items.IRON_LEGGINGS || ((ItemStack)entity.inventory.armorInventory.get(2)).getItem() == Items.IRON_CHESTPLATE || ((ItemStack)entity.inventory.armorInventory.get(3)).getItem() == Items.IRON_HELMET) && entity.getHeldItemMainhand().getItem() != Items.AIR && !((ItemStack)entity.inventory.armorInventory.get(0)).isDamaged() && !((ItemStack)entity.inventory.armorInventory.get(1)).isDamaged() && !((ItemStack)entity.inventory.armorInventory.get(2)).isDamaged() && !((ItemStack)entity.inventory.armorInventory.get(3)).isDamaged() && entity.getFoodStats().getFoodLevel() == 20) {
                if (!isBot.contains(entity)) {
                    isBot.add(entity);
                }

                return;
            }

            if (isBot.contains(entity)) {
                isBot.remove(entity);
            }
        }

    }

    public static boolean checkBot(LivingEntity entity) {
        return entity instanceof PlayerEntity ? isBot.contains(entity) : false;
    }

    public void onDisable() {
        super.onDisable();
        isBot.clear();
    }
}