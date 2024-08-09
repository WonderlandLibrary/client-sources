package wtf.shiyeno.modules.impl.util;

import net.minecraft.item.Items;
import net.minecraft.network.play.client.CPlayerPacket;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.game.EventKey;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.impl.combat.AuraFunction;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BindSetting;
import wtf.shiyeno.util.world.InventoryUtil;

@FunctionAnnotation(
        name = "ClickPearl",
        type = Type.Util
)
public class ClickPearl extends Function {
    private BindSetting pearl = new BindSetting("Кнопка перла", 0);
    boolean test = false;

    public ClickPearl() {
        this.addSettings(new Setting[]{this.pearl});
    }

    public void onEvent(Event event) {
        if (event instanceof EventKey e) {
            if (e.key == this.pearl.getKey()) {
                InventoryUtil.inventorySwapClick(Items.ENDER_PEARL, true);
            }
        }
    }

    public void rotation() {
        AuraFunction var10000 = Managment.FUNCTION_MANAGER.auraFunction;
        if (AuraFunction.target != null) {
            mc.player.connection.sendPacket(new CPlayerPacket.RotationPacket(mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround()));
        }
    }
}