package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.config.*;
import javax.swing.*;
import java.awt.event.*;

public class AutoGLHFModule extends Module
{
    public static AutoGLHFModule INSTANCE;
    private long lastTrigger;
    private boolean shouldSend;
    
    public AutoGLHFModule() {
        super("AutoGLHF");
        this.lastTrigger = 0L;
        this.shouldSend = true;
        AutoGLHFModule.INSTANCE = this;
    }
    
    public void onChat(final eu message) {
        if (ModuleConfig.INSTANCE.isEnabled(this) && ave.A().D() != null && ave.A().D().b != null && System.currentTimeMillis() > this.lastTrigger + 1000L && message.c().contains("The game starts in ") && !message.c().contains(":") && this.shouldSend) {
            final int timeLeft = Integer.parseInt(message.c().replaceAll("\\D+", ""));
            if (timeLeft <= 10) {
                this.shouldSend = false;
                final Timer t = new Timer((timeLeft + 1) * 1000, event -> {
                    ave.A().h.e("/achat GL HF!");
                    ave.A().q.d().a("/achat GL HF!");
                    this.lastTrigger = System.currentTimeMillis();
                    this.shouldSend = true;
                    return;
                });
                t.setRepeats(false);
                t.start();
            }
        }
    }
}
