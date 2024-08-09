package fun.ellant.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventPacket;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.SliderSetting;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraftforge.eventbus.api.Event;

@FunctionRegister(name="SuperBow", type = Category.COMBAT, desc = "Увеличивает силу стрелы")
public class SuperBow
        extends Function {
    private final SliderSetting power = new SliderSetting("Сила", 30, 1, 100, 1);

    public SuperBow() {
        addSettings(power);
    }

    @Subscribe
    public void onPacketSend(EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof CPlayerDiggingPacket && ((CPlayerDiggingPacket)eventPacket.getPacket()).getAction() == CPlayerDiggingPacket.Action.RELEASE_USE_ITEM && SuperBow.mc.player.getActiveItemStack().getItem() == Items.BOW) {
            SuperBow.mc.player.connection.sendPacket(new CEntityActionPacket(SuperBow.mc.player, CEntityActionPacket.Action.START_SPRINTING));
            int i = 0;
            while ((float)i < this.power.min) {
                this.spoof(SuperBow.mc.player.getPosX(), SuperBow.mc.player.getPosY() + 1.0E-10, SuperBow.mc.player.getPosZ(), false);
                this.spoof(SuperBow.mc.player.getPosX(), SuperBow.mc.player.getPosY() - 1.0E-10, SuperBow.mc.player.getPosZ(), true);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ++i;
            }
        }
    }


    private void spoof(double x, double y, double z, boolean ground) {
        SuperBow.mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(x, y, z, SuperBow.mc.player.rotationYaw, SuperBow.mc.player.rotationPitch, ground));
    }
}

