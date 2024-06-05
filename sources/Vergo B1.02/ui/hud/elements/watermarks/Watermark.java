package xyz.vergoclient.ui.hud.elements.watermarks;

import net.minecraft.util.ResourceLocation;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventRenderGUI;
import xyz.vergoclient.modules.OnEventInterface;

public class Watermark implements OnEventInterface {

    private static final ResourceLocation VERGOSENSE_BACKGROUND_TEXTURE;

    static {
        VERGOSENSE_BACKGROUND_TEXTURE = new ResourceLocation("fuckafriendforfree.png");
    }

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventRenderGUI && e.isPre()) {
            EventRenderGUI event = (EventRenderGUI) e;
            drawWatermark(event);
        }
    }

    public static void drawWatermark(EventRenderGUI e) {
        if(Vergo.config.modHud.waterMark.is("Simple")) {
            simpleWatermark sw = new simpleWatermark();

            sw.onEvent(e);
        }

        if(Vergo.config.modHud.waterMark.is("vergosense")) {
            vergosenseWatermark vergosenseWatermark = new vergosenseWatermark();

            vergosenseWatermark.onEvent(e);
        }

        if(Vergo.config.modHud.waterMark.is("Text")) {
            textWatermark tw = new textWatermark();

            tw.onEvent(e);
        }

        if(Vergo.config.modHud.waterMark.is("Planet")) {
            planetWatermark pw = new planetWatermark();

            pw.onEvent(e);
        }
    }
}