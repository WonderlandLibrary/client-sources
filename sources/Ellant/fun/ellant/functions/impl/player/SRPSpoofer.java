package fun.ellant.functions.impl.player;

import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import net.minecraft.network.play.client.CResourcePackStatusPacket;
import net.minecraftforge.eventbus.api.Event;

@FunctionRegister(name = "RPSpoofer", type = Category.PLAYER, desc = "Пон?")
public class SRPSpoofer extends Function {

    public void onEvent(Event event) {
        mc.player.connection.sendPacket(new CResourcePackStatusPacket(CResourcePackStatusPacket.Action.ACCEPTED));
        mc.player.connection.sendPacket(new CResourcePackStatusPacket(CResourcePackStatusPacket.Action.SUCCESSFULLY_LOADED));
    }
}