package wtf.shiyeno.modules.impl.combat;

import java.util.Iterator;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SkullItem;
import net.minecraft.potion.Effects;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.MultiBoxSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.world.InventoryUtil;
import wtf.shiyeno.util.world.WorldUtil.TotemUtil;

@FunctionAnnotation(
        name = "AutoTotem",
        type = Type.Combat
)
public class AutoTotemFunction extends Function {
    private final SliderSetting health = new SliderSetting("Здоровье", 3.5F, 1.0F, 20.0F, 0.05F);
    private final BooleanOption swapBack = new BooleanOption("Возвращать предмет", true);
    private final BooleanOption noBallSwitch = new BooleanOption("Не брать если шар в руке", false);
    private final MultiBoxSetting mode = new MultiBoxSetting("Срабатывать", new BooleanOption[]{new BooleanOption("Золотые сердца", true), new BooleanOption("Кристаллы", true), new BooleanOption("Обсидиан", false), new BooleanOption("Якорь", false), new BooleanOption("Падение", true), new BooleanOption("Кристалл в руке", true), new BooleanOption("Здоровье на элитре", true)});
    private final SliderSetting radiusExplosion = (new SliderSetting("Дистанция до кристала", 6.0F, 1.0F, 8.0F, 1.0F)).setVisible(() -> {
        return this.mode.get(1);
    });
    private final SliderSetting radiusObs = (new SliderSetting("Дистанция до обсидиана", 6.0F, 1.0F, 8.0F, 1.0F)).setVisible(() -> {
        return this.mode.get(2);
    });
    private final SliderSetting radiusAnch = (new SliderSetting("Дистанция до якоря", 6.0F, 1.0F, 8.0F, 1.0F)).setVisible(() -> {
        return this.mode.get(2);
    });
    private final SliderSetting HPElytra = (new SliderSetting("Брать по здоровью на элитре", 6.0F, 1.0F, 20.0F, 0.005F)).setVisible(() -> {
        return this.mode.get("Здоровье на элитре");
    });
    private final SliderSetting DistanceFall = (new SliderSetting("Дистанция падения", 20.0F, 3.0F, 50.0F, 0.005F)).setVisible(() -> {
        return this.mode.get("Падение");
    });
    int oldItem = -1;

    public AutoTotemFunction() {
        this.addSettings(new Setting[]{this.mode, this.health, this.swapBack, this.noBallSwitch, this.radiusExplosion, this.HPElytra, this.DistanceFall});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            this.handleEventUpdate((EventUpdate)event);
        }

    }

    private void handleEventUpdate(EventUpdate event) {
        int slot = InventoryUtil.getItemSlot(Items.TOTEM_OF_UNDYING);
        boolean handNotNull = !(mc.player.getHeldItemOffhand().getItem() instanceof AirItem);
        boolean totemInHand = mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING || mc.player.getHeldItemMainhand().getItem() == Items.TOTEM_OF_UNDYING;
        if (this.condition()) {
            if (slot >= 0 && !totemInHand) {
                mc.playerController.windowClick(0, slot, 40, ClickType.SWAP, mc.player);
                if (handNotNull && this.oldItem == -1) {
                    this.oldItem = slot;
                }
            }
        } else if (this.oldItem != -1 && this.swapBack.get()) {
            mc.playerController.windowClick(0, this.oldItem, 40, ClickType.SWAP, mc.player);
            this.oldItem = -1;
        }

    }

    private boolean condition() {
        float absorption = this.mode.get(0) && mc.player.isPotionActive(Effects.ABSORPTION) ? mc.player.getAbsorptionAmount() : 0.0F;
        if (mc.player.getHealth() + absorption <= this.health.getValue().floatValue()) {
            return true;
        } else {
            if (!this.isBall()) {
                if (this.checkCrystal()) {
                    return true;
                }

                if (this.checkObsidian()) {
                    return true;
                }

                if (this.checkAnchor()) {
                    return true;
                }

                if (this.checkPlayerItemCrystal()) {
                    return true;
                }
            }

            return this.checkHPElytra() ? true : this.checkFall();
        }
    }

    private boolean checkHPElytra() {
        if (!this.mode.get("Здоровье на элитре")) {
            return false;
        } else {
            return ((ItemStack)mc.player.inventory.armorInventory.get(2)).getItem() == Items.ELYTRA && mc.player.getHealth() <= this.HPElytra.getValue().floatValue();
        }
    }

    private boolean checkPlayerItemCrystal() {
        if (!this.mode.get("Кристалл в руке")) {
            return false;
        } else {
            Iterator var1 = mc.world.getPlayers().iterator();

            PlayerEntity entity;
            do {
                do {
                    do {
                        if (!var1.hasNext()) {
                            return false;
                        }

                        entity = (PlayerEntity)var1.next();
                    } while(mc.player == entity);
                } while(entity.getHeldItemOffhand().getItem() != Items.END_CRYSTAL && entity.getHeldItemMainhand().getItem() != Items.END_CRYSTAL);
            } while(!(mc.player.getDistance(entity) < 6.0F));

            return true;
        }
    }

    private boolean checkFall() {
        if (!this.mode.get(4)) {
            return false;
        } else {
            return mc.player.fallDistance > this.DistanceFall.getValue().floatValue();
        }
    }

    private boolean isBall() {
        if (this.mode.get(3) && mc.player.fallDistance > 5.0F) {
            return false;
        } else {
            return this.noBallSwitch.get() && mc.player.getHeldItemOffhand().getItem() instanceof SkullItem;
        }
    }

    private boolean checkObsidian() {
        if (!this.mode.get(2)) {
            return false;
        } else {
            return TotemUtil.getBlock(this.radiusObs.getValue().floatValue(), Blocks.OBSIDIAN) != null;
        }
    }

    private boolean checkAnchor() {
        if (!this.mode.get(3)) {
            return false;
        } else {
            return TotemUtil.getBlock(this.radiusAnch.getValue().floatValue(), Blocks.RESPAWN_ANCHOR) != null;
        }
    }

    private boolean checkCrystal() {
        if (!this.mode.get(1)) {
            return false;
        } else {
            Iterator var1 = mc.world.getAllEntities().iterator();

            Entity entity;
            do {
                do {
                    if (!var1.hasNext()) {
                        return false;
                    }

                    entity = (Entity)var1.next();
                    if (entity instanceof EnderCrystalEntity && mc.player.getDistance(entity) <= this.radiusExplosion.getValue().floatValue()) {
                        return true;
                    }
                } while(!(entity instanceof TNTEntity) && !(entity instanceof TNTMinecartEntity));
            } while(!(mc.player.getDistance(entity) <= this.radiusExplosion.getValue().floatValue()));

            return true;
        }
    }

    private void reset() {
        this.oldItem = -1;
    }

    protected void onEnable() {
        this.reset();
        super.onEnable();
    }

    protected void onDisable() {
        this.reset();
        super.onDisable();
    }
}