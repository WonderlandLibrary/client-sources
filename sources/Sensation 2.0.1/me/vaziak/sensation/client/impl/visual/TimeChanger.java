package me.vaziak.sensation.client.impl.visual;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

/**
 * @author antja03
 */
public class TimeChanger extends Module {

    public DoubleProperty timeProperty = new DoubleProperty("Time", "The time that your game will be set to.", null,
            14000, 0, 16000, 100, null);

    private S03PacketTimeUpdate lastTimePacket;

    public TimeChanger() {
        super("Time Changer", Category.VISUAL);
        registerValue(timeProperty);
    }

    @Override
    public void onDisable() {
    }

}
