package us.dev.direkt.gui.color;

import us.dev.api.timing.Timer;
import us.dev.direkt.Direkt;
import us.dev.direkt.event.internal.events.game.EventRunTick;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.awt.*;
import java.util.function.Supplier;

/**
 * @author Foundry
 */
public enum ClientColors {
    FADING_GREEN(() ->  Color.HSBtoRGB(0.283f, getFadingHue(), 0.82f)),
    NARROW_FADING_GREEN(() -> Color.HSBtoRGB(0.283f, getNarrowFadingHue(), 0.82f));

    private static float FADING_HUE = 1F, NARROW_FADING_HUE = 1F;
    private final Supplier<Integer> colorSupplier;

    ClientColors(Supplier<Integer> colorSupplier) {
        this.colorSupplier = colorSupplier;
    }

    public int getColor() {
        return colorSupplier.get();
    }

    public static float getFadingHue() {
        return FADING_HUE;
    }

    public static float getNarrowFadingHue() {
        return NARROW_FADING_HUE;
    }

    static {
        Direkt.getInstance().getEventManager().register(new Object() {
            private Timer hueTimer = new Timer(), narrowHueTimer = new Timer();
            private boolean hueDarkening, narrowHueDarkening;

            @Listener
            protected Link<EventRunTick> onRunTick = new Link<>(event -> {
                if (narrowHueTimer.hasReach(140)) {
                    if (!narrowHueDarkening) {
                        if (NARROW_FADING_HUE > 0.8) {
                            NARROW_FADING_HUE -= 0.02;
                        } else {
                            narrowHueDarkening = true;
                        }
                    } else {
                        if (NARROW_FADING_HUE < 1) {
                            NARROW_FADING_HUE += 0.02;
                        } else {
                            narrowHueDarkening = false;
                        }
                    }
                    narrowHueTimer.reset();
                }
                if (hueTimer.hasReach(70)) {
                    if (!hueDarkening) {
                        if (FADING_HUE > 0.6) {
                            FADING_HUE -= 0.05;
                        } else {
                            hueDarkening = true;
                        }
                    } else {
                        if (FADING_HUE < 1) {
                            FADING_HUE += 0.05;
                        } else {
                            hueDarkening = false;
                        }
                    }
                    hueTimer.reset();
                }
            });
        });
    }
}
