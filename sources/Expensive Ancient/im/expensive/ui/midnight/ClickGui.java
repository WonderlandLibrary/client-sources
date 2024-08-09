package im.expensive.ui.midnight;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.ui.midnight.component.impl.*;
import im.expensive.ui.midnight.component.impl.Component;
import im.expensive.ui.styles.Style;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.client.Vec2i;
import im.expensive.utils.font.Fonts;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.Stencil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static im.expensive.ui.midnight.component.impl.ModuleComponent.binding;
import static im.expensive.utils.client.IMinecraft.mc;

public class ClickGui extends Screen {
    public ClickGui() {
        super(new StringTextComponent("GUI"));
        for (Function function : Expensive.getInstance().getFunctionRegistry().getFunctions()) {
            objects.add(new ModuleComponent(function));
        }
        for (Style style : Expensive.getInstance().getStyleManager().getStyleList()) {
            this.theme.add(new ThemeComponent(style));
        }
        cfg.clear();
        for (String config : Expensive.getInstance().getConfigStorage().getConfigsByName()) {
            cfg.add(new ConfigComponent(Expensive.getInstance().getConfigStorage().findConfig(config)));
        }
    }

    double xPanel, yPanel;
    Category current = Category.Combat;

    float animation;

    public ArrayList<ModuleComponent> objects = new ArrayList<>();

    private CopyOnWriteArrayList<ConfigComponent> config = new CopyOnWriteArrayList<>();

    public List<ThemeComponent> theme = new ArrayList<>();

    public float scroll = 0;
    public float animateScroll = 0;

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        scroll += delta * 15;
        ColorComponent.opened = null;
        ThemeComponent.selected = null;
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public void closeScreen() {
        if (typing || !searchText.isEmpty()) {
            typing = false;
            searchText = "";
        }
        if (configTyping || !configName.isEmpty()) {
            configTyping = false;
            configName = "";
        }
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    boolean searchOpened;
    float seacrh;

    private String searchText = "";
    public static boolean typing;

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        float scale = 2f;
        float width = 900 / scale;
        float height = 650 / scale;
        float leftPanel = 200 / scale;
        float x = MathUtil.calculateXPosition(mc.getMainWindow().scaledWidth() / 2f, width);
        float y = MathUtil.calculateXPosition(mc.getMainWindow().scaledHeight() / 2f, height);
        xPanel = x;
        yPanel = y;
        animation = MathUtil.lerp(animation, 0, 5);

        Vec2i fixed = ClientUtil.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();

        int finalMouseX = mouseX;
        int finalMouseY = mouseY;

        mc.gameRenderer.setupOverlayRendering(2);
        renderBackground(matrixStack, x, y, width, height, leftPanel, finalMouseX, finalMouseY);
        renderCategories(matrixStack, x, y, width, height, leftPanel, finalMouseX, finalMouseY);
        renderComponents(matrixStack, x, y, width, height, leftPanel, finalMouseX, finalMouseY);
        renderColorPicker(matrixStack, x, y, width, height, leftPanel, finalMouseX, finalMouseY);
        renderSearchBar(matrixStack, x, y, width, height, leftPanel, finalMouseX, finalMouseY);
        mc.gameRenderer.setupOverlayRendering();


    }

    void renderColorPicker(MatrixStack matrixStack, float x, float y, float width, float height, float leftPanel, int mouseX, int mouseY) {
        if (ColorComponent.opened != null) {
            ColorComponent.opened.draw(matrixStack, mouseX, mouseY);
        }
        if (ThemeComponent.selected != null) {
            ThemeComponent.selected.draw(matrixStack, mouseX, mouseY);
        }
    }

    void renderBackground(MatrixStack matrixStack, float x, float y, float width, float height, float leftPanel, int mouseX, int mouseY) {
        DisplayUtils.drawShadow(x, y, width, height, 20, new Color(22, 24, 28).getRGB());
        DisplayUtils.drawRoundedRect(x, y, width, height, new Vector4f(4, 4, 4, 4), new Color(22, 24, 28).getRGB());
        DisplayUtils.drawRoundedRect(x, y, width, 64 / 2f, new Vector4f(6, 0, 6, 0), new Color(17, 18, 21).getRGB());
        DisplayUtils.drawRectHorizontalW(x + leftPanel, y, 3, 64 / 2f, new Color(12, 13, 15, 150).getRGB(), new Color(12, 13, 15, 0).getRGB());
        DisplayUtils.drawRoundedRect(x, y, leftPanel, height, new Vector4f(6, 6, 0, 0), new Color(17, 18, 21).getRGB());
        Fonts.woveline[19].drawCenteredString(matrixStack, "Fixed SRC", x + leftPanel / 2f, y + 14, -1);
    }

    void renderCategories(MatrixStack matrixStack, float x, float y, float width, float height, float leftPanel, int mouseX, int mouseY) {
        float heightCategory = 38 / 2f;
        for (Category t : Category.values()) {

            if (t == current)
                DisplayUtils.drawRoundedRect(x + (leftPanel / 2f) * animation, y + 32.5f + t.ordinal() * heightCategory, leftPanel * (1 - animation), heightCategory, new Vector4f(20 * animation, 20 * animation, 20 * animation, 20 * animation), new Color(32, 36, 42).getRGB());

            boolean hovered = MathUtil.isInRegion(mouseX, mouseY, x, y + 32.5f + t.ordinal() * heightCategory, leftPanel, heightCategory);
            DisplayUtils.drawRoundedRect(x + (leftPanel / 2f) * animation, y + 32.5f + t.ordinal() * heightCategory, leftPanel * (1 - animation), heightCategory, new Vector4f(20 * animation, 20 * animation, 20 * animation, 20 * animation), new Color(32, 36, 42, (int) (100 * (t.anim / 5f))).getRGB());

            t.anim = MathUtil.lerp(t.anim, (hovered ? 5 : 0), 10);
            DisplayUtils.drawImage(new ResourceLocation("expensive/images/ui/" + t.name().toLowerCase() + ".png"), (float) (x + 10 + t.anim), y + 38 + t.ordinal() * heightCategory, 8, 8, t == current ? new Color(74, 166, 218).getRGB() : new Color(163, 176, 188).getRGB());
            Fonts.gilroy[14].drawString(matrixStack, t.name(), x + 20 + t.anim, y + 41 + t.ordinal() * heightCategory, t == current ? new Color(74, 166, 218).getRGB() : new Color(163, 176, 188).getRGB());
        }
        DisplayUtils.drawRectHorizontalW(x + leftPanel, y + 64 / 2f, 5, height - 64 / 2f, new Color(12, 13, 15, 50).getRGB(), new Color(12, 13, 15, 0).getRGB());
    }

    void renderComponents(MatrixStack matrixStack, float x, float y, float width, float height, float leftPanel, int mouseX, int mouseY) {
        Scissor.push();
        Scissor.setFromComponentCoordinates(x, y + 64 / 2f, width, height - 64 / 2f);
        drawComponents(matrixStack, mouseX, mouseY);
        Scissor.unset();
        Scissor.pop();
        DisplayUtils.drawRoundedRect(x + leftPanel, y + 64 / 2f, width - leftPanel, height - 64 / 2f, new Vector4f(0, 0, 6, 6), new Color(22, 24, 28, ((int) (255 * animation))).getRGB());
    }

    void renderSearchBar(MatrixStack matrixStack, float x, float y, float width, float height, float leftPanel, int mouseX, int mouseY) {
        seacrh = MathUtil.lerp(seacrh, searchOpened ? 1 : 0, 15);
        if (seacrh >= 0.01) {
            DisplayUtils.drawShadow(x + leftPanel + 6 + ((width - leftPanel - 12 - (64 / 2f) / 2f) / 2f) * (1 - seacrh), y + 7, (width - leftPanel - 28 - (64 / 2f) / 2f) * (seacrh), 64 / 2f - 14, 12, new Color(17, 18, 21, (int) (seacrh * 255f)).darker().getRGB());
            DisplayUtils.drawRoundedRect(x + leftPanel + 6 + ((width - leftPanel - 12 - (64 / 2f) / 2f) / 2f) * ((1 - seacrh)), y + 7, (width - leftPanel - 28 - (64 / 2f) / 2f) * (seacrh), 64 / 2f - 14, 4, new Color(17, 18, 21, (int) (seacrh * 255f)).brighter().getRGB());
            matrixStack.push();
            matrixStack.translate(x + leftPanel + 6 + ((width - leftPanel - 28 - (64 / 2f) / 2f) / 2f) * (1 - seacrh), y + 14, 0);
            matrixStack.scale(seacrh, seacrh, 1);
            matrixStack.translate(-(x + leftPanel + 6 + ((width - leftPanel - 28 - (64 / 2f) / 2f) / 2f) * (1 - seacrh)), -(y + 14), 0);
            float xOffset = 0;
            float fontTextWidth = Fonts.gilroy[16].getWidth(searchText);

            if (fontTextWidth > (width - leftPanel - 32 - (64 / 2f) / 2f) * (seacrh)) {
                // Вычисляем xOffset
                xOffset = fontTextWidth - ((width - leftPanel - 32 - (64 / 2f) / 2f) * (seacrh));
            }
            Stencil.initStencilToWrite();
            DisplayUtils.drawRectW(x + leftPanel + 6 + ((width - leftPanel - 12 - (64 / 2f) / 2f) / 2f) * ((1 - seacrh)), y + 7, (width - leftPanel - 32 - (64 / 2f) / 2f) * (seacrh), 64 / 2f - 14, -1);
            Stencil.readStencilBuffer(1);
            Fonts.gilroy[16].drawString(matrixStack, searchText + (typing ? System.currentTimeMillis() % 1000 > 500 ? "_" : "" : ""), x + leftPanel + 10 + ((width - leftPanel - 12 - (64 / 2f) / 2f) / 2f) * (1 - seacrh) - xOffset, y + 14, -1);
            Stencil.uninitStencilBuffer();
            matrixStack.pop();
        }
        Fonts.icons[16].drawString(matrixStack, "B", x + width - (64 / 2f) / 2f, y + (64 / 2f) / 2f - 1, -1);

        Fonts.icons[16].drawString(matrixStack, "C", x + width - ((64 / 2f) / 2f) * 2F, y + (64 / 2f) / 2f - 1, -1);
    }

    public CopyOnWriteArrayList<ConfigComponent> cfg = new CopyOnWriteArrayList<>();

    private String configName = "";
    private boolean configTyping;
    public static String confign;

    void drawComponents(MatrixStack stack, int mouseX, int mouseY) {

        List<ModuleComponent> moduleComponentList = objects.stream()
                .filter(moduleObject -> {
                    if (!searchText.isEmpty()) {
                        return true;
                    } else {
                        return moduleObject.function.getCategory() == current;
                    }
                }).toList();

        List<ModuleComponent> first = moduleComponentList
                .stream()
                .filter(moduleObject -> objects.indexOf(moduleObject) % 2 == 0)
                .toList();

        List<ModuleComponent> second = moduleComponentList
                .stream()
                .filter(moduleObject -> objects.indexOf(moduleObject) % 2 != 0)
                .toList();

        for (ConfigComponent c : config) {
            if (c.config.getFile().getName().equalsIgnoreCase(confign)) {
                selectedCfg = c;
            }
        }

        float scale = 2f;
        animateScroll = MathUtil.lerp(animateScroll, scroll, 10);
        float width = 900 / scale;
        float height = 650 / scale;
        float leftPanel = 200 / scale;
        float x = MathUtil.calculateXPosition(mc.getMainWindow().scaledWidth() / 2f, width);
        float y = MathUtil.calculateXPosition(mc.getMainWindow().scaledHeight() / 2f, height);
        if (current == Category.Configurations || current == Category.Theme) {

            DisplayUtils.drawRoundedRect(x + leftPanel + 10, y + 64 / 2F + 10, width - leftPanel - 20, height - 64 / 2F - 20, 5, new Color(17, 18, 21).getRGB());
            if (current == Category.Configurations) {
                DisplayUtils.drawRoundedRect(x + leftPanel + 15, y + 64 / 2F + 15, width - leftPanel - 35 - 35 * 2 + 3, 32 / 2f, 4, new Color(22, 24, 28).getRGB());

                DisplayUtils.drawRoundedRect(x + width - 45 - 2, y + 64 / 2F + 15, 35 - 2, 32 / 2f, 4, new Color(22, 24, 28).getRGB());
                Fonts.gilroy[14].drawCenteredString(stack, "Create", x + width - 45 - 2 + (35 - 2) / 2f, y + 64 / 2F + 21.5F, -1);
                DisplayUtils.drawRoundedRect(x + width - 45 - 35 - 2, y + 64 / 2F + 15, 35 - 2, 32 / 2f, 4, new Color(22, 24, 28).getRGB());
                Fonts.gilroy[14].drawCenteredString(stack, "Reload", x + width - 45 - 35 - 2 + (35 - 2) / 2f, y + 64 / 2F + 21.5F, -1);
                float fontTextWidth = Fonts.gilroy[16].getWidth(configName);

                float xOffset = 0;

                if (fontTextWidth > width - leftPanel - 35 - 35 * 2 ) {
                    // Вычисляем xOffset
                    xOffset = fontTextWidth - (width - leftPanel - 35 - 35 * 2 -8);
                }

                Stencil.initStencilToWrite();
                DisplayUtils.drawRectW(x + leftPanel + 17, y + 64 / 2F + 15, width - leftPanel - 35 - 35 * 2, 32 / 2f,-1);
                Stencil.readStencilBuffer(1);
                Fonts.gilroy[16].drawString(stack, configName + (configTyping ? System.currentTimeMillis() % 1000 > 500 ? "_" : "" : ""), x + leftPanel + 18 - xOffset, y + 64 / 2F + 20, -1);
                Stencil.uninitStencilBuffer();
                config = cfg;
            }
            Scissor.push();
            Scissor.setFromComponentCoordinates(x + leftPanel + 10, (float) (yPanel + (64 / 2f) + 35), width - leftPanel - 20, height - 64 / 2F - 45);
            float offset = (float) (yPanel + (64 / 2f) + 8) + animateScroll;
            for (ConfigComponent component : config) {
                if (current != Category.Configurations) continue;
                component.parent = this;
                component.selected = component == selectedCfg;
                component.setPosition((float) (xPanel + (100f + 12)), offset + 29, 314 + 12, 20);
                component.drawComponent(stack, mouseX, mouseY);
                offset += component.height + 2;
            }
            Scissor.unset();
            Scissor.pop();

            Scissor.push();
            Scissor.setFromComponentCoordinates(x + leftPanel + 10, (float) (yPanel + (64 / 2f)) + 10, width - leftPanel - 20, height - 64 / 2F - 20);
            float offset2 = (float) (yPanel + (64 / 2f) - 12) + animateScroll;
            for (ThemeComponent component : theme) {
                if (current != Category.Theme) continue;
                component.parent = this;
                component.setPosition((float) (xPanel + (100f + 12)), offset2 + 29, 314 + 12, 20);
                component.drawComponent(stack, mouseX, mouseY);
                offset2 += component.height + 2;
            }
            Scissor.unset();
            Scissor.pop();


            scroll = Math.min(scroll, 0);


        }


        float offset = (float) (yPanel + (64 / 2f) + 12) + animateScroll;
        float size1 = 0;
        for (ModuleComponent component : first) {
            if (searchText.isEmpty()) {
                if (component.function.getCategory() != current) continue;
            } else {
                if (!component.function.getName().toLowerCase().contains(searchText.toLowerCase())) continue;
            }
            component.parent = this;
            component.setPosition((float) (xPanel + (100f + 12)), offset, 314 / 2f, 37);
            component.drawComponent(stack, mouseX, mouseY);
            if (!component.components.isEmpty()) {
                for (Component settingComp : component.components) {
                    if (settingComp.setting != null && settingComp.setting.visible.get()) {
                        offset += settingComp.height;
                        size1 += settingComp.height;
                    }
                }
            }
            offset += component.height + 8;
            size1 += component.height + 8;
        }

        float offset2 = (float) (yPanel + (64 / 2f) + 12) + animateScroll;
        float size2 = 0;
        for (ModuleComponent component : second) {
            if (searchText.isEmpty()) {
                if (component.function.getCategory() != current) continue;
            } else {
                if (!component.function.getName().toLowerCase().contains(searchText.toLowerCase())) continue;
            }
            component.parent = this;
            component.setPosition((float) (xPanel + (100f + 12) + 314 / 2f + 10), offset2, 314 / 2f, 37);
            component.drawComponent(stack, mouseX, mouseY);
            if (!component.components.isEmpty()) {
                for (Component settingComp : component.components) {
                    if (settingComp.setting != null && settingComp.setting.visible.get()) {
                        offset2 += settingComp.height;
                        size2 += settingComp.height;
                    }
                }
            }
            offset2 += component.height + 8;
            size2 += component.height + 8;
        }


        float max = Math.max(size1, size2);
        if (max < height) {
            scroll = 0;
        } else {
            scroll = MathHelper.clamp(scroll, -(max - height + 50), 0);
        }
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        ColorComponent.opened = null;
        ThemeComponent.selected = null;
        typing = false;
        configTyping = false;
        configOpened = false;
        configName = "";
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        Vec2i fixed = ClientUtil.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();

        for (ModuleComponent m : objects) {
            if (searchText.isEmpty()) {
                if (m.function.getCategory() != current) continue;
            } else {
                if (!m.function.getName().toLowerCase().contains(searchText.toLowerCase())) continue;
            }
            m.mouseReleased((int) mouseX, (int) mouseY, button);
        }
        for (ThemeComponent component : theme) {
            if (current != Category.Theme) continue;
            component.parent = this;
            component.mouseReleased((int) mouseX, (int) mouseY, button);
        }
        if (ColorComponent.opened != null) {
            ColorComponent.opened.unclick((int) mouseX, (int) mouseY);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            this.minecraft.keyboardListener.enableRepeatEvents(false);
        }
        boolean ctrlDown = Screen.hasControlDown();
        if (typing) {
            if (!(current == Category.Configurations || current == Category.Theme)) {
                if (ctrlDown && keyCode == GLFW.GLFW_KEY_V) {
                    String pasteText = GLFW.glfwGetClipboardString(Minecraft.getInstance().getMainWindow().getHandle());
                    searchText += pasteText;
                }
                if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                    if (!searchText.isEmpty()) {
                        searchText = searchText.substring(0, searchText.length() - 1);
                    }
                }
                if (keyCode == GLFW.GLFW_KEY_DELETE) {
                    searchText = "";
                }
                if (keyCode == GLFW.GLFW_KEY_ENTER) {
                    typing = false;
                }
            }
        }

        for (ModuleComponent m : objects) {
            if (searchText.isEmpty()) {
                if (m.function.getCategory() != current) continue;
            } else {
                if (!m.function.getName().toLowerCase().contains(searchText.toLowerCase())) continue;
            }
            m.keyTyped(keyCode, scanCode, modifiers);
        }

        if (binding != null) {
            if (keyCode == GLFW.GLFW_KEY_DELETE) {
                binding.function.setBind(0);
            } else {
                binding.function.setBind(keyCode);
            }
            binding = null;
        }

        if (configTyping) {
            if (ctrlDown && keyCode == GLFW.GLFW_KEY_V) {
                String pasteText = GLFW.glfwGetClipboardString(Minecraft.getInstance().getMainWindow().getHandle());
                configName += pasteText;
            }
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                if (!configName.isEmpty()) {
                    configName = configName.substring(0, configName.length() - 1);
                }
            }
            if (keyCode == GLFW.GLFW_KEY_DELETE) {
                configName = "";
            }
            if (keyCode == GLFW.GLFW_KEY_ENTER) {
                configTyping = false;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (typing)
            searchText += codePoint;
        if (configTyping)
            configName += codePoint;

        for (ModuleComponent m : objects) {
            if (searchText.isEmpty()) {
                if (m.function.getCategory() != current) continue;
            } else {
                if (!m.function.getName().toLowerCase().contains(searchText.toLowerCase())) continue;
            }
            m.charTyped(codePoint, modifiers);
        }
        return super.charTyped(codePoint, modifiers);
    }

    private boolean configOpened;

    private ConfigComponent selectedCfg;

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        Vec2i fixed = ClientUtil.getMouse((int) mouseX, (int) mouseY);

        mouseX = fixed.getX();
        mouseY = fixed.getY();

        float scale = 2f;
        float width = 900 / scale;
        float height = 650 / scale;
        float leftPanel = 199 / scale;
        float x = MathUtil.calculateXPosition(mc.getMainWindow().scaledWidth() / 2f, width);
        float y = MathUtil.calculateXPosition(mc.getMainWindow().scaledHeight() / 2f, height);
        float heightCategory = 38 / 2f;


        if (ColorComponent.opened != null) {
            if (!ColorComponent.opened.click((int) mouseX, (int) mouseY))
                return super.mouseClicked(mouseX, mouseY, button);
        }

        for (Category t : Category.values()) {
            if (MathUtil.isInRegion((float) mouseX, (float) mouseY, x, y + 32.5f + t.ordinal() * heightCategory, leftPanel, heightCategory)) {
                if (current == t) continue;
                current = t;
                animation = 1;
                scroll = 0;
                searchText = "";
                ColorComponent.opened = null;
                ThemeComponent.selected = null;
                typing = false;
            }
        }

        for (ConfigComponent component : config) {
            if (current != Category.Configurations) continue;
            component.parent = this;
            if (MathUtil.isInRegion((float) mouseX, (float) mouseY, component.x + component.width - 35 - 2, component.y + 2, 35 - 2, 32 / 2f))
                selectedCfg = component;
            component.mouseClicked((int) mouseX, (int) mouseY, button);
        }

        for (ThemeComponent component : theme) {
            if (current != Category.Theme) continue;
            component.parent = this;
            component.mouseClicked((int) mouseX, (int) mouseY, button);
        }

        if (MathUtil.isInRegion((float) mouseX, (float) mouseY, x + width - 45 - 2, y + 64 / 2F + 15, 35 - 2, 32 / 2f)) {
            Expensive.getInstance().getConfigStorage().saveConfiguration(configName);
            configName = "";
            configTyping = false;
            cfg.clear();
            for (String config : Expensive.getInstance().getConfigStorage().getConfigsByName()) {
                cfg.add(new ConfigComponent(Expensive.getInstance().getConfigStorage().findConfig(config)));
            }
        }
        if (MathUtil.isInRegion((float) mouseX, (float) mouseY, x + width - 45 - 35 - 2, y + 64 / 2F + 15, 35 - 2, 32 / 2f)) {
            cfg.clear();
            for (String config : Expensive.getInstance().getConfigStorage().getConfigsByName()) {
                cfg.add(new ConfigComponent(Expensive.getInstance().getConfigStorage().findConfig(config)));
            }
        }

        if (MathUtil.isInRegion((float) mouseX, (float) mouseY, x, y + 64 / 2f, width, height - 64 / 2f)) {
            for (ModuleComponent m : objects) {
                if (searchText.isEmpty()) {
                    if (m.function.getCategory() != current) continue;
                } else {
                    if (!m.function.getName().toLowerCase().contains(searchText.toLowerCase())) continue;
                }
                m.mouseClicked((int) mouseX, (int) mouseY, button);
            }
        }


        if (MathUtil.isInRegion((float) mouseX, (float) mouseY, x + width - (64 / 2f) / 2f - 1, y + (64 / 2f) / 2f - 5, 10, 10)) {
            if (!(current == Category.Configurations || current == Category.Theme)) {
                typing = false;
                searchText = "";
                searchOpened = !searchOpened;
            }
        }

        if (MathUtil.isInRegion((float) mouseX, (float) mouseY, x + width - ((64 / 2f) * 2) / 2f - 1, y + (64 / 2f) / 2f - 5, 10, 10)) {
            try {
                Runtime.getRuntime().exec("explorer " + Expensive.getInstance().getConfigStorage().CONFIG_DIR);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (MathUtil.isInRegion((float) mouseX, (float) mouseY, x + leftPanel + 15, y + 64 / 2F + 15, width - leftPanel - 35 * 2 + 3, 32 / 2f)) {
            configTyping = !configTyping;
        }


        if (MathUtil.isInRegion((float) mouseX, (float) mouseY, x + leftPanel + 6 + ((width - leftPanel - 12 - (64 / 2f) / 2f) / 2f) * (1 - seacrh), y + 7, (width - leftPanel - 12 - (64 / 2f) / 2f) * (seacrh), 64 / 2f - 14)) {
            typing = !typing;
        } else {
            typing = false;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
