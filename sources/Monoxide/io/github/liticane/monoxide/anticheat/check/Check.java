package io.github.liticane.monoxide.anticheat.check;

import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.math.time.TimeHelper;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import io.github.liticane.monoxide.anticheat.data.PlayerData;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Check implements Methods {

    private final String name;
    protected final PlayerData data;
    private final TimeHelper delay;

    private double buffer;

    public Check(PlayerData data) {
        this.name = getClass().getAnnotation(Info.class).name();
        this.data = data;
        this.delay = new TimeHelper();
    }

    public abstract void handle(Packet<? extends INetHandler> event);

    public double increaseBuffer() {
        buffer++;
        return buffer;
    }

    public double increaseBufferBy(double increase) {
        buffer += increase;
        return buffer;
    }

    public double reduceBuffer(double amount) {
        if(buffer > 0) {
            buffer -= amount;
        }
        return buffer;
    }

    public double resetBuffer() {
        buffer = 0;
        return buffer;
    }

    protected void flag() {
        if (delay.hasReached(100)) {
            sendMessage("§c" + data.getPlayer().getCommandSenderName() + " §7failed §c" + name + "§7!");
            delay.reset();
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String name();
    }

    public String getName() {
        return name;
    }

    public PlayerData getData() {
        return data;
    }

    public TimeHelper getDelay() {
        return delay;
    }

    public double getBuffer() {
        return buffer;
    }
}
