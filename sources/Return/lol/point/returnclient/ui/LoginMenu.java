package lol.point.returnclient.ui;

import lol.point.Return;
import lol.point.returnclient.module.impl.client.Theme;
import lol.point.returnclient.ui.components.UIButton2;
import lol.point.returnclient.ui.components.UITextInput;
import lol.point.returnclient.ui.utils.security.LoginUtil;
import lol.point.returnclient.util.render.FastFontRenderer;
import lol.point.returnclient.util.render.shaders.ShaderUtil;
import lol.point.returnclient.util.system.OSUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class LoginMenu extends GuiScreen {

    public static String status = null;
    private final String cause = null;
    private final ExecutorService executor = null;
    private final CompletableFuture<Void> task = null;

    private final List<UIButton2> buttons = new ArrayList<>();
    private final List<UITextInput> inputs = new ArrayList<>();

    private final FastFontRenderer big = Return.INSTANCE.fontManager.getFont("Nano-Regular 200");
    private final FastFontRenderer small = Return.INSTANCE.fontManager.getFont("Nano-Regular 20");
    private final FastFontRenderer text = Return.INSTANCE.fontManager.getFont("ProductSans-Regular 18");
    private final FastFontRenderer copyRight = Return.INSTANCE.fontManager.getFont("ProductSans-Bold 18");
    private final FastFontRenderer icons = Return.INSTANCE.fontManager.getFont("ReturnNew-Icons 18");

    public void initGui() {
        buttons.clear();
        buttons.add(new UIButton2("Login", "m", 200, 20));
        buttons.add(new UIButton2("Quit", "P", 200, 20));
        inputs.clear();
        inputs.add(new UITextInput("Username", "b", 200, 20));
    }

    public void onGuiClosed() {
        if (task != null && !task.isDone()) {
            task.cancel(true);
            executor.shutdownNow();
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        Color gradient1, gradient2;
        gradient1 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient1;
        gradient2 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient2;

        RenderUtil.rectangle(0, 0, width, height, new Color(22, 22, 22));
        ShaderUtil.renderGlow(() -> {
            big.drawStringWithShadow("R", (sr.getScaledWidth() / 2f) - (59f / 2f), (sr.getScaledHeight() / 2f) - 105, gradient1);
            small.drawStringWithShadow("eturn", (sr.getScaledWidth() / 2f) - (59f / 2f) + 14, (sr.getScaledHeight() / 2f) - 105 + 67, -1);
        }, 2, 2, 0.86f);

        big.drawStringWithShadow("R", (sr.getScaledWidth() / 2f) - (59f / 2f), (sr.getScaledHeight() / 2f) - 105, gradient1);
        small.drawStringWithShadow("eturn", (sr.getScaledWidth() / 2f) - (59f / 2f) + 14, (sr.getScaledHeight() / 2f) - 105 + 67, -1);
        copyRight.drawString("  2024-2025 Return Client. All Rights Reserved", ((float) sr.getScaledWidth() / 2f) - (copyRight.getWidth("  2024-2025 Return Client. All Rights Reserved") / 2), sr.getScaledHeight() - 12, new Color(60, 60, 60));
        icons.drawString("o", ((float) sr.getScaledWidth() / 2f) - (copyRight.getWidth("@@ 2024-2025 Return Client. All Rights Reserved") / 2) + 2, sr.getScaledHeight() - 11, new Color(60, 60, 60));

        float yOffset1 = 0;
        for (UITextInput input : inputs) {
            float iconX = ((float) sr.getScaledWidth() / 2f) - (200 / 2f), iconY = ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset1;
            if (input.placeHolderText.equals("Username")) {
                iconX += 5;
                iconY += 7;
            }
            input.drawButton(mouseX, mouseY, ((float) sr.getScaledWidth() / 2f) - (200 / 2f), ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset1, iconX, iconY);
            yOffset1 += 21;
        }

        float yOffset = cause != null ? yOffset1 + 10 : yOffset1 + 0;
        for (UIButton2 button : buttons) {
            float iconX = ((float) sr.getScaledWidth() / 2f) - (200 / 2f), iconY = ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset;
            switch (button.buttonText) {
                case "Login" -> {
                    iconX += 4;
                    iconY += 6.5f;
                }
                case "Quit" -> {
                    iconX += 5;
                    iconY += 6.5f;
                }
            }
            button.drawButton(mouseX, mouseY, ((float) sr.getScaledWidth() / 2f) - (200 / 2f), ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset, iconX, iconY);
            yOffset += 21;
        }

        if (status != null) {
            text.drawCenteredString(status, (float) width / 2, (float) height / 2 - text.getHeight() / 2 - text.getHeight() * 2, -1);
        }

        if (cause != null) {
            Gui.drawRect(0, (int) (height - 2 - text.getHeight() - 2), (int) (2 + text.getWidth(cause) + 2), height, 0x64000000);
            Gui.drawRect(0, height - 1, (int) (2 + text.getWidth(cause) + 2), height, 0xFF000000);
            text.drawCenteredString(cause, (float) width / 2, (float) height / 2 - text.getHeight() / 2 - text.getHeight() * 2 + 10, -1);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);

        float yOffset1 = 0;
        for (UITextInput input : inputs) {
            input.mouseClicked(mouseButton, mouseX, mouseY, ((float) sr.getScaledWidth() / 2f) - (200 / 2f), ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset1);
            yOffset1 += 21;
        }

        float yOffset = cause != null ? yOffset1 + 10 : yOffset1 + 0;
        for (UIButton2 button : buttons) {
            if (button.mouseClicked(mouseButton, mouseX, mouseY, ((float) sr.getScaledWidth() / 2f) - (200 / 2f), ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset)) {
                switch (button.buttonText) {
                    case "Login" -> {
                        String inputText = inputs.getFirst().inputText;
                        boolean isValid = LoginUtil.authentication(inputText);
                        if (isValid) {
                            mc.displayGuiScreen(new MainMenu());
                        } else {
                            System.out.println(OSUtil.getHWID());
                            status = "§cInvalid credentials or hardware id. Please contact administrators";
                        }
                    }

                    case "Quit" -> mc.shutdown();
                }
            }
            yOffset += 21;
        }
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            return;
        }

        for (UITextInput input : inputs) {
            input.keyTyped(keyCode, typedChar);
        }
    }
}

