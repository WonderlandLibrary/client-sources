package me.finz0.osiris.gui.editor.component;

import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.OsirisEvent;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.util.TimerUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;

import java.text.DecimalFormat;

/**
 * Author Seth
 * 8/31/2019 @ 3:07 AM.
 */
public final class PacketTimeComponent extends DraggableHudComponent {

    private TimerUtil timer = new TimerUtil();

    public PacketTimeComponent() {
        super("PacketTime");
        AuroraMod.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    public Listener<PacketEvent.Receive> listener = new Listener<>(event -> {
        if(event.getEra() == OsirisEvent.Era.PRE) {
            if(event.getPacket() != null) {
                this.timer.reset();
            }
        }
    });

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        final float seconds = ((System.currentTimeMillis() - this.timer.getTime()) / 1000.0f) % 60.0f;
        final String delay = "PACKET: " + (seconds >= 3.0f ? "\2474" : "\247f") + new DecimalFormat("#.#").format(seconds);

        this.setW(Minecraft.getMinecraft().fontRenderer.getStringWidth(delay));
        this.setH(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT);

        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(delay, this.getX(), this.getY(), -1);
    }

}
