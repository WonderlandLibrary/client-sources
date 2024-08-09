package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.renderer.GL;
import xyz.mathax.mathaxclient.renderer.Renderer2D;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.HudElement;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.render.RenderUtils;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class InventoryViewerHudElement extends HudElement {
    private static final Identifier TEXTURE = new MatHaxIdentifier("textures/container.png");
    private static final Identifier TEXTURE_TRANSPARENT = new MatHaxIdentifier("textures/container-transparent.png");

    private final ItemStack[] editorInventory;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup backgroundSettings = settings.createGroup("Background");

    private final Setting<Double> scaleSetting = generalSettings.add(new DoubleSetting.Builder()
        .name("Scale")
        .description("The scale.")
        .defaultValue(2)
        .min(1)
        .sliderRange(1, 5)
        .build()
    );

    // Background

    private final Setting<Background> backgroundSetting = backgroundSettings.add(new EnumSetting.Builder<Background>()
        .name("Background")
        .description("Background of inventory viewer.")
        .defaultValue(Background.Texture)
        .build()
    );

    private final Setting<SettingColor> backgroundColorSetting = backgroundSettings.add(new ColorSetting.Builder()
        .name("Background Color")
        .description("Color of the background.")
        .defaultValue(new SettingColor())
        .visible(() -> backgroundSetting.get() != Background.None)
        .build()
    );

    public InventoryViewerHudElement(Hud hud) {
        super(hud, "Inventory Viewer", "Displays your inventory.");

        editorInventory = new ItemStack[9 * 3];
        editorInventory[0] = Items.TOTEM_OF_UNDYING.getDefaultStack();
        editorInventory[5] = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 6);
        editorInventory[19] = new ItemStack(Items.OBSIDIAN, 64);
        editorInventory[editorInventory.length - 1] = Items.NETHERITE_AXE.getDefaultStack();
    }

    @Override
    public void update(OverlayRenderer renderer) {
        box.setSize(backgroundSetting.get().width * scaleSetting.get(), backgroundSetting.get().height * scaleSetting.get());
    }

    @Override
    public void render(OverlayRenderer renderer) {
        double x = box.getX();
        double y = box.getY();

        if (backgroundSetting.get() != Background.None) {
            drawBackground((int) x, (int) y);
        }

        for (int row = 0; row < 3; row++) {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = getStack(9 + row * 9 + i);
                if (stack == null) {
                    continue;
                }

                int itemX = backgroundSetting.get() == Background.Texture ? (int) (x + (8 + i * 18) * scaleSetting.get()) : (int) (x + (1 + i * 18) * scaleSetting.get());
                int itemY = backgroundSetting.get() == Background.Texture ? (int) (y + (7 + row * 18) * scaleSetting.get()) : (int) (y + (1 + row * 18) * scaleSetting.get());
                RenderUtils.drawItem(stack, itemX, itemY, scaleSetting.get(), true);
            }
        }
    }

    private ItemStack getStack(int i) {
        if (isInEditor()) {
            return editorInventory[i - 9];
        }

        return mc.player.getInventory().getStack(i);
    }

    private void drawBackground(int x, int y) {
        int w = (int) box.width;
        int h = (int) box.height;

        switch (backgroundSetting.get()) {
            case Texture, Outline -> {
                GL.bindTexture(backgroundSetting.get() == Background.Texture ? TEXTURE : TEXTURE_TRANSPARENT);

                Renderer2D.TEXTURE.begin();
                Renderer2D.TEXTURE.texturedQuad(x, y, w, h, backgroundColorSetting.get());
                Renderer2D.TEXTURE.render(null);
            }
            case Flat -> {
                Renderer2D.COLOR.begin();
                Renderer2D.COLOR.quad(x, y, w, h, backgroundColorSetting.get());
                Renderer2D.COLOR.render(null);
            }
        }
    }

    public enum Background {
        None("None", 162, 54),
        Texture("Texture", 176, 67),
        Outline("Outline", 162, 54),
        Flat("Flat", 162, 54);

        private final String name;

        private final int width, height;

        Background(String name, int width, int height) {
            this.name = name;
            this.width = width;
            this.height = height;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
