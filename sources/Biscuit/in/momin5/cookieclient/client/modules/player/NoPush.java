package in.momin5.cookieclient.client.modules.player;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.BlockPushEvent;
import in.momin5.cookieclient.api.event.events.WaterPushEvent;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.common.MinecraftForge;

public class NoPush extends Module {
    public NoPush(){
        super("NoPush", Category.PLAYER);
    }


    @Override
    public void onEnable(){
        MinecraftForge.EVENT_BUS.register(this);
        CookieClient.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    public Listener<BlockPushEvent> onBurrowPush = new Listener<>(event -> {
        event.cancel();
    });

    @EventHandler
    private final Listener<WaterPushEvent> whenTheImposterIsSus = new Listener<>(event -> {
        event.cancel();
    });
}

