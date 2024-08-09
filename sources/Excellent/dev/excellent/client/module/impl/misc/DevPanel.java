package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.api.interfaces.shader.IShader;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.AntiBot;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.DragValue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.TextFormatting;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2d;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@ModuleInfo(name = "Dev Panel", description = "", category = Category.MISC)
public class DevPanel extends Module implements IShader, IRenderAccess {
    private final BooleanValue transaction = new BooleanValue("Transactions", this, true);
    private final BooleanValue keepAlive = new BooleanValue("Keep Alive", this, true);
    private final BooleanValue teleport = new BooleanValue("Teleport", this, true);
    private final BooleanValue velocity = new BooleanValue("Velocity", this, true);
    private final BooleanValue abilities = new BooleanValue("Abilities", this, true);
    private final BooleanValue devPanel = new BooleanValue("Dev Panel", this, false);
    private final BooleanValue eventCalls = new BooleanValue("Event Calls", this, false);

    private final DragValue position = new DragValue("Position", this, new Vector2d(200, 200), true);

    private final DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yy");

    public static HashMap<String, Integer> calls = new HashMap<>();
    private long threadLag;
    private boolean measuring;

    public final Listener<PacketEvent> onPacket = event -> {

        final IPacket<?> packet = event.getPacket();
        if (event.isReceive()) {
            if (transaction.getValue() && packet instanceof SConfirmTransactionPacket wrapper) {

                ChatUtil.addText(TextFormatting.RED + " Transaction " + TextFormatting.RESET + " (ActionNumber: %s)   (WindowID: %s)", wrapper.getActionNumber(), wrapper.getWindowId());
            } else if (keepAlive.getValue() && packet instanceof SKeepAlivePacket wrapper) {
                ChatUtil.addText(TextFormatting.GREEN + " Keep Alive " + TextFormatting.RESET + " (ID: %s)", wrapper.getId());
            } else if (teleport.getValue() && packet instanceof SPlayerPositionLookPacket wrapper) {
                ChatUtil.addText(TextFormatting.BLUE + " Server Teleport " + TextFormatting.RESET + " (Position: %s)",
                        Mathf.round(wrapper.getX(), 3) + " " +
                                Mathf.round(wrapper.getY(), 3) + " " +
                                Mathf.round(wrapper.getZ(), 3));
            } else if (velocity.getValue() && packet instanceof SEntityVelocityPacket wrapper) {

                if (wrapper.getEntityID() == mc.player.getEntityId()) {
                    ChatUtil.addText(TextFormatting.LIGHT_PURPLE + " Velocity " + TextFormatting.RESET + " (DeltaX: %s) (DeltaY: %s)  (DeltaZ: %s) ",
                            wrapper.motionX / 8000D, wrapper.motionY / 8000D, wrapper.motionZ / 8000D);
                }
            } else if (velocity.getValue() && packet instanceof SExplosionPacket) {
                ChatUtil.addText(TextFormatting.LIGHT_PURPLE + " Explosion (Velocity) ");
            } else if (abilities.getValue() && packet instanceof SPlayerAbilitiesPacket) {
                ChatUtil.addText(TextFormatting.YELLOW + " Abilities");
            }
        }
    };

    public final Listener<Render2DEvent> onRender2D = event -> {

        if (devPanel.getValue()) {
            Font font = Fonts.INTER_BOLD.get(18);
            Font small = Fonts.INTER_MEDIUM.get(14);

            String title = excellent.getInfo().getName() + " " + excellent.getInfo().getVersion() + " Dev Panel " + date.format(LocalDateTime.now());

            float padding = 5;
            float margin = font.getHeight();
            float smargin = small.getHeight();
            position.size.set(padding + font.getWidth(title) + padding, padding
                    + margin + (smargin / 2F)
                    + margin
                    + margin
                    + margin
                    + margin
                    + margin
                    + smargin
                    + smargin
                    + smargin
                    + padding
            );

            MatrixStack matrix = event.getMatrix();

            int first = ColorUtil.getColor(7, 200, 249);
            int second = ColorUtil.getColor(13, 65, 225);

            float round = 4F;

            int backgroundColor = ColorUtil.getColor(13, 65, 225);
            int textColor = ColorUtil.getColor(255, 255, 255);

            BLUR_SHADER.addTask2D(() -> onShader(matrix, round, backgroundColor));
            BLOOM_SHADER.addTask2D(() -> onShader(matrix, round, backgroundColor));


            onShader(matrix, round, ColorUtil.getColor(13, 65, 225, 64));

            float posY = (float) (position.position.y + padding);
            float posX = (float) (position.position.x + padding);

            font.drawGradient(matrix, title, posX, posY, first, second);

            posY += margin + (smargin / 2F);
            font.drawGradient(matrix, "Framerate", posX, posY, first, second);
            posY += margin;
            small.draw(matrix, "[" + Minecraft.getDebugFPS() + " / " + mc.getMainWindow().getLimitFramerate() + "]", posX, posY, textColor);
            posY += margin;
            font.drawGradient(matrix, "Debugger", posX, posY, first, second);
            posY += margin;
            small.draw(matrix, "Thread Lag: " + threadLag, posX, posY, textColor);
            posY += margin;
            small.draw(matrix, "Bot count: " + AntiBot.bots.size(), posX, posY, textColor);
            posY += smargin;
            font.drawGradient(matrix, "Performance", posX, posY, first, second);
            posY += margin;
            small.draw(matrix, "Render 2D: " + render2dProfiler.getLastTime() + " ns", posX, posY, textColor);
            posY += smargin;
            small.draw(matrix, "Drag: " + dragProfiler.getLastTime() + " ns", posX, posY, textColor);
        }
    };

    private void onShader(MatrixStack matrix, float round, int backgroundColor) {
        RectUtil.drawRoundedRectShadowed(matrix, (float) position.position.x, (float) position.position.y, (float) position.position.x + (float) position.size.x, (float) position.position.y + (float) position.size.y, round, 1F, backgroundColor, backgroundColor, backgroundColor, backgroundColor, false, false, true, true);
    }

    public final Listener<MotionEvent> onMotion = event -> {
        if (measuring) return;

        long systemTime = System.currentTimeMillis();
        measuring = true;
        boolean run = mc.player.ticksExisted % 100 == 0 && eventCalls.getValue();
        THREAD_POOL.execute(() -> {
            threadLag = System.currentTimeMillis() - systemTime;
            measuring = false;
            if (run) {
                ChatUtil.addText("Displaying Calls: ");
                for (String name : calls.keySet()) {
                    ChatUtil.addText(name + ": " + calls.get(name));
                }
                calls.clear();
            }
        });
    };
}
