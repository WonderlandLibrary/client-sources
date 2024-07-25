package club.bluezenith.ui;

import club.bluezenith.util.client.MillisTimer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.concurrent.Future;

import static club.bluezenith.BlueZenith.scheduledExecutorService;
import static club.bluezenith.util.font.FontUtil.inter35;
import static club.bluezenith.util.font.FontUtil.rubikMedium45;
import static club.bluezenith.util.math.MathUtil.getRandomInt;
import static club.bluezenith.util.render.RenderUtil.animate;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static net.minecraft.client.audio.PositionedSoundRecord.create;

public class GoodbyeScreen extends GuiScreen {
    private static final ResourceLocation
            earRapeSound = new ResourceLocation("hi"),
            windowsShutdownSound = new ResourceLocation("shutdown");

    private final long shutdownTimeMs;
    private float alpha;
    private final String quote;
    private final boolean doEarRape;
    private boolean soundPlayed;
    private final MillisTimer earRapeTimer;

    private final Future<?> closeFuture;

    public GoodbyeScreen() {
        doEarRape = getRandomInt(0, 100) <= 7;

        shutdownTimeMs = doEarRape ? 2800 : 1800;

        if(doEarRape) earRapeTimer = new MillisTimer();
        else earRapeTimer = null;

        quote = quotes[getRandomInt(0, quotes.length)];

        closeFuture = scheduledExecutorService.schedule(() -> mc.shutdown(), shutdownTimeMs + 150, MILLISECONDS);
        alpha = 0.05F;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        final ScaledResolution resolution = new ScaledResolution(mc);

        drawRect(0, 0, resolution.getScaledWidth(), resolution.getScaledHeight(), new Color(0, 0, 0, alpha).getRGB());

        final FontRenderer title = rubikMedium45,
                           description = inter35;

        final float halfWidth = (float) resolution.getScaledWidth_double() / 2F,
                halfHeight = (float) resolution.getScaledHeight_double() / 2F;

        final String titleString = doEarRape ? "You got lucky! This sound appears with a low chance of 7%." : "Shutting down..";

        title.drawString(titleString, halfWidth - title.getStringWidthF(titleString) / 2F, halfHeight - 20, -1, true);
        description.drawString(quote, halfWidth - description.getStringWidthF(quote) / 2F, halfHeight + 5, -1, true);

        final String tip = "Tip: You can click ESC to go back to the main menu.";
        description.drawString(
                tip,
                (float) (resolution.getScaledWidth_double() - description.getStringWidthF(tip) - 2),
                2,
                new Color(200, 200, 200, 100).getRGB());

        final float seconds = shutdownTimeMs / 1200F;
        final float framesPerSecond = 60;

        final float framesUntilShutdown = framesPerSecond * seconds;
        final float increment = 1F / framesUntilShutdown;

        alpha = animate(1F, alpha, increment);
    }

    @Override
    public void updateScreen() {
        if(doEarRape && earRapeTimer.hasTimeReached(500L)) {
            earRapeTimer.reset();
            mc.getSoundHandler().playSound(create(earRapeSound));
        } else if(!doEarRape && !soundPlayed) {
            mc.getSoundHandler().playSound(create(windowsShutdownSound));
            soundPlayed = true;
        }
    }

    @Override
    public void onGuiClosed() {
        closeFuture.cancel(false);
    }

    private static final String[] quotes = {
            "See you later.",
            "We laughed until we had to cry, we loved right down to our last goodbye, we were the best.",
            "A farewell is necessary before we can meet again, and meeting again, after moments or a lifetime, is certain for those who are friends.",
            "Great is the art of beginning, but greater is the art of ending.",
            "The two hardest things to say in life is hello for the first time and goodbye for the last.",
            "We started with a simple hello, but ended with a complicated goodbye.",
            "You have been my friend. That in itself is a tremendous thing.",
            "No distance of place or lapse of time can lessen the friendship of those who are thoroughly persuaded of each other’s worth.",
            "The pain of parting is nothing to the joy of meeting again.",
            "Good friends never say goodbye. They simply say ‘See you soon.’",
            "This is not a goodbye, my darling, this is a thank you."
    };
}
