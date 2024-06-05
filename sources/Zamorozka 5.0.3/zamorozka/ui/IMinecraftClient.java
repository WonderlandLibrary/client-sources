package zamorozka.ui;

import java.net.Proxy;

import net.minecraft.util.Session;

public interface IMinecraftClient {
    public void leftClick();

    public void rightClick();

    public void setItemUseCooldown(int cooldown);

    public Proxy getProxy();

    public void setSession(Session session);

    public int getFps();
}