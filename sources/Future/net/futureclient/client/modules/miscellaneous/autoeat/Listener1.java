package net.futureclient.client.modules.miscellaneous.autoeat;

import net.futureclient.client.events.Event;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.futureclient.client.IE;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemAppleGold;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.miscellaneous.AutoEat;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final AutoEat k;
    
    public Listener1(final AutoEat k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (AutoEat.getMinecraft14().player.getFoodStats().getFoodLevel() <= this.k.hunger.B().intValue() || (this.k.health.B().floatValue() < 20.0f && AutoEat.getMinecraft().player.getHealth() <= this.k.health.B().floatValue())) {
            int i = 0;
            int currentItem = 0;
            while (i < 9) {
                final Item item = AutoEat.getMinecraft7().player.inventory.getStackInSlot(currentItem).getItem();
                Listener1 listener1 = null;
                Label_0170: {
                    if (this.k.health.B().floatValue() < 20.0f) {
                        if (item instanceof ItemAppleGold) {
                            listener1 = this;
                            break Label_0170;
                        }
                    }
                    else if (item instanceof ItemFood && (!(item instanceof ItemFishFood) || !ItemFishFood.FishType.byItemStack(AutoEat.getMinecraft17().player.inventory.getStackInSlot(currentItem)).equals((Object)ItemFishFood.FishType.PUFFERFISH))) {
                        listener1 = this;
                        break Label_0170;
                    }
                    i = ++currentItem;
                    continue;
                }
                AutoEat.e(listener1.k, true);
                if (AutoEat.getMinecraft18().gameSettings.keyBindUseItem.isKeyDown() && AutoEat.M(this.k).e(15000L)) {
                    AutoEat.M(this.k, true);
                    KeyBinding.setKeyBindState(AutoEat.getMinecraft19().gameSettings.keyBindUseItem.getKeyCode(), false);
                    ((IMinecraft)AutoEat.getMinecraft9()).clickMouse(IE.RD.D);
                    AutoEat.M(this.k, false);
                    AutoEat.M(this.k).e();
                }
                if (AutoEat.M(this.k) || !AutoEat.M(this.k).e(500L)) {
                    break;
                }
                AutoEat.getMinecraft4().player.inventory.currentItem = currentItem;
                if (AutoEat.M(this.k).e(1500L)) {
                    if (AutoEat.getMinecraft15().currentScreen != null) {
                        AutoEat.getMinecraft13().playerController.processRightClick((EntityPlayer)AutoEat.getMinecraft3().player, (World)AutoEat.getMinecraft6().world, EnumHand.MAIN_HAND);
                    }
                    KeyBinding.setKeyBindState(AutoEat.getMinecraft1().gameSettings.keyBindUseItem.getKeyCode(), true);
                    return;
                }
                break;
            }
        }
        else if (AutoEat.getMinecraft20().player.getFoodStats().getFoodLevel() >= this.k.hunger.B().intValue() || (this.k.health.B().floatValue() < 20.0f && AutoEat.getMinecraft11().player.getHealth() >= this.k.health.B().floatValue())) {
            if (AutoEat.M(this.k) && AutoEat.M(this.k).e(20L)) {
                KeyBinding.setKeyBindState(AutoEat.getMinecraft10().gameSettings.keyBindUseItem.getKeyCode(), false);
                ((IMinecraft)AutoEat.getMinecraft2()).clickMouse(IE.RD.D);
                AutoEat.getMinecraft16().player.inventory.currentItem = AutoEat.M(this.k);
                AutoEat.M(this.k, false);
                AutoEat.e(this.k, false);
                return;
            }
            AutoEat.M(this.k, AutoEat.getMinecraft8().player.inventory.currentItem);
            AutoEat.M(this.k).e();
            AutoEat.e(this.k, false);
        }
        else {
            AutoEat.M(this.k, AutoEat.getMinecraft5().player.inventory.currentItem);
            AutoEat.M(this.k).e();
            AutoEat.e(this.k, false);
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
