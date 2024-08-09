package im.expensive.ui.autobuy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.Expensive;
import im.expensive.utils.animations.Direction;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.client.Vec2i;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

public class AutoBuyUI extends Screen implements IMinecraft {


    boolean typing;
    String text = "";

    public AutoBuyUI(ITextComponent titleIn) {
        super(titleIn);
    }

    float x;
    float y;
    boolean selecting;

    private SelectorUI selectorUI = new SelectorUI(null);

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        selectorUI = new SelectorUI(null);
        selecting = false;
        super.init(minecraft, width, height);
    }

    int sc;
    float animScroll, scrolled;
    int scroll;

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        Vec2i fix = ClientUtil.getMouse((int) mouseX, (int) mouseY);
        mouseX = fix.getX();
        mouseY = fix.getY();
        float width = 400;
        float height = 300;
        if (selecting) {
            if (MathUtil.isHovered((float) mouseX, (float) mouseY, x, y, width, height - 65)) {
                sc += (int) (delta * 15);
            }
        } else {
            if (MathUtil.isHovered((float) mouseX, (float) mouseY, x, y, width, height - 65)) {
                scrolled += delta * 15;
            }
        }

        selectorUI.scroll((float) delta, (float) mouseX, (float) mouseY);
        return super.mouseScrolled(mouseX, mouseY, delta);


    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        selectorUI.release(mouseX, mouseY);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public enum CatItems {
        FOOD, STONE, SWORD, BOOTS, ALL
    }

    public CatItems catItems;

    private Vec2i adjustMouseCoordinates(int mouseX, int mouseY) {
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();

        float adjustedMouseX = (mouseX - windowWidth / 2f) / scale + windowWidth / 2f;
        float adjustedMouseY = (mouseY - windowHeight / 2f) / scale + windowHeight / 2f;

        return new Vec2i((int) adjustedMouseX, (int) adjustedMouseY);
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        mc.gameRenderer.setupOverlayRendering(2);
        scroll = (int) MathUtil.fast(scroll, sc, 10);
        animScroll = MathUtil.fast(animScroll, scrolled, 10);
        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        x = windowWidth / 2 - 400 / 2;
        y = windowHeight / 2 - 300 / 2;

        float width = 400;
        float height = 300;
        selectorUI.render(matrixStack, mouseX, mouseY, partialTicks);

        DisplayUtils.drawShadow(x, y, width, height, 10,
                ColorUtils.rgba(17, 17, 17, 128));

        DisplayUtils.drawRoundedRect(x, y, width, height, new Vector4f(7, 7, 7, 7),
                ColorUtils.rgba(17, 17, 17, 255));


        DisplayUtils.drawRoundedRect(x + 10, y + height - 60, width - 20, 50, 4, ColorUtils.rgba(23, 23, 23, 255));
        Fonts.montserrat.drawCenteredText(matrixStack, selecting ? "Назад" : "Добавить", x + width / 2f, y + height - 30 - 10, MathUtil.isHovered(mouseX, mouseY, x + 10, y + height - 60, width - 20, 50) ? -1 : ColorUtils.rgba(255, 255, 255, 128), 10);
        if (selecting) {

            Scissor.push();
            Scissor.setFromComponentCoordinates(x, y, width, height - 65);
            int itemX = (int) (x + 3);
            int itemY = (int) (y + 5 + scroll);
            DisplayUtils.drawRoundedRect(x + 5, itemY, width - 10, 28, 3, ColorUtils.rgba(23, 23, 23, 255));
            DisplayUtils.drawRectW(x + width - 100, itemY, 0.5f, 28, ColorUtils.rgb(17, 17, 17));
            {
                int categoryX = (int) (x + width - 100) + 4;
                int categoryY = itemY + 5;
                GlStateManager.pushMatrix();
                GlStateManager.translatef(categoryX, categoryY, 0);
                GlStateManager.scaled(0.9, 0.9, 0.9);
                GlStateManager.translatef(-categoryX, -categoryY, 0);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.COOKED_BEEF.getDefaultInstance(), categoryX, categoryY);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.STONE.getDefaultInstance(), categoryX + 20, categoryY);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.DIAMOND_SWORD.getDefaultInstance(), categoryX + 40, categoryY);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.DIAMOND_BOOTS.getDefaultInstance(), categoryX + 60, categoryY);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.BARRIER.getDefaultInstance(), categoryX + 80, categoryY);

                GlStateManager.popMatrix();
            }
            String textToDraw = searchText;

            if (!searching && searchText.isEmpty()) {
                textToDraw = "Найти предмет....";
            }
            if (textToDraw.length() > 25) {
                textToDraw = textToDraw.substring(0, 25);
            }
            Fonts.montserrat.drawText(matrixStack, textToDraw + (searching ? System.currentTimeMillis() % 1000 > 500 ? "_" : "" : ""), x + 10, itemY + 28 / 2f - 7 / 2f, -1, 7);
            itemY += 30;

            for (Item item : Registry.ITEM) {
                if (item instanceof AirItem) continue;

                if (MathUtil.isHovered(itemX, itemY, x, y - 15, width, height - 65 + 15)) {

                    if (catItems == CatItems.FOOD) {
                        if (!item.isFood()) continue;
                    }
                    if (catItems == CatItems.STONE) {
                        if (!(item instanceof BlockItem)) continue;
                    }
                    if (catItems == CatItems.SWORD) {
                        if (!(item instanceof SwordItem)) continue;
                    }
                    if (catItems == CatItems.BOOTS) {
                        if (!(item instanceof ArmorItem)) continue;
                    }
                    if (!item.getName().getString().toLowerCase().contains(searchText.toLowerCase()))
                        continue;
                    mc.getItemRenderer().renderItemAndEffectIntoGUI(item.getDefaultInstance(), itemX, itemY);

                }
                itemX += 18;
                if (itemX >= x + width - 5) {
                    itemX = (int) x + 3;
                    itemY += 20;
                }

            }

            if (!searching)
                sc = Math.min(0, sc);
            else
                sc = 0;

        } else {
            float widthPanel = 120;
            float buyX = 0;
            float buyY = animScroll;
            float offset = 10;
            Scissor.push();
            Scissor.setFromComponentCoordinates(x, y, width, height - 60);
            for (AutoBuy buy : Expensive.getInstance().getAutoBuyHandler().items) {


                DisplayUtils.drawRoundedRect(x + 10 + buyX, y + 10 + buyY, widthPanel, 50, 3, ColorUtils.rgba(23, 23, 23, 255));
                DisplayUtils.drawRoundedRect(x + 15 + buyX, y + 15 + buyY, 20, 20, 3, ColorUtils.rgba(30, 30, 30, 255));

                Scissor.push();
                Scissor.setFromComponentCoordinates(x + 10 + buyX, y + 10 + buyY, widthPanel, 50);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(buy.getItem().getDefaultInstance(), (int) (x + 17 + buyX), (int) (y + 17 + buyY));
                String itemName = buy.getItem().getName().getString();
                Fonts.montserrat.drawText(matrixStack, buy.getItem().getName().getString(), (int) (x + 37 + buyX), y + 21 + buyY, -1, 7, 0.05f);
                if (Fonts.montserrat.getWidth(buy.getItem().getName().getString(), 7, 0.05f) + 37 >= widthPanel) {
                    DisplayUtils.drawRectVerticalW(x + buyX + widthPanel, y + 21f + buyY, 10, 7, ColorUtils.rgba(23, 23, 23, 255), ColorUtils.rgba(23, 23, 23, 0));
                }

                Fonts.montserrat.drawText(matrixStack, "Цена: ", (int) (x + 13 + buyX), y + 40 + buyY, ColorUtils.rgba(255, 255, 255, 128), 5, 0.05f);
                Fonts.montserrat.drawText(matrixStack, String.valueOf(buy.getPrice()), (int) (x + 13 + buyX) + Fonts.montserrat.getWidth("Цена: ", 5, 0.05f), y + 40 + buyY, ColorUtils.rgba(64, 255, 64, 128), 5, 0.05f);
                float off = 0;
                if (!buy.getEnchanments().isEmpty()) {
                    Fonts.montserrat.drawText(matrixStack, "Чары: ", (int) (x + 13 + buyX), y + 40 + buyY + 10, ColorUtils.rgba(255, 255, 255, 128), 5, 0.05f);
                    for (Enchantment enchantment : buy.getEnchanments().keySet()) {
                        int level = buy.getEnchanments().get(enchantment);

                        IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(enchantment.getName());

                        String enchText = iformattabletextcomponent.getString().substring(0, 2) + level;

                        Fonts.montserrat.drawText(matrixStack, enchText, (int) (x + 13 + buyX) + Fonts.montserrat.getWidth("Чары: ", 5, 0.05f) + off, y + 40 + buyY + 10, ColorUtils.rgba(255, 255, 255, 128), 5, 0.05f);
                        off += Fonts.montserrat.getWidth(enchText, 5, 0.05f) + 1;
                    }
                }
                if (buy.isItems() || buy.isDon()) {
                    Fonts.montserrat.drawText(matrixStack, "Только с предметами", (int) (x + 13 + buyX), y + 40 + buyY + 10, ColorUtils.rgba(255, 0, 0, 128), 5, 0.05f);
                } else if (buy.isFake()) {
                    Fonts.montserrat.drawText(matrixStack, "Проверяется на фейк предмет", (int) (x + 13 + buyX), y + 40 + buyY + 10, ColorUtils.rgba(255, 0, 0, 128), 5, 0.05f);

                }
                buyX += widthPanel + 10;

                if (x + buyX + 110 >= x + width - 10) {
                    buyX = 0;
                    buyY += 60;
                }

                Scissor.unset();
                Scissor.pop();
            }
            scrolled = MathHelper.clamp(scrolled, -buyY - 300, 0);
        }
        Scissor.unset();
        Scissor.pop();
        mc.gameRenderer.setupOverlayRendering();

    }

    float scale;

    private void updateScaleBasedOnScreenWidth() {
        final float MIN_SCALE = 0.5f;

        float totalPanelWidth = 400;
        float screenWidth = mc.getMainWindow().getScaledWidth();

        if (totalPanelWidth >= screenWidth) {
            scale = screenWidth / totalPanelWidth;
            scale = MathHelper.clamp(scale, MIN_SCALE, 1.0f);
        } else {
            scale = 1f;
        }
    }

    public String searchText = "";
    public boolean searching;

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        selectorUI.key(keyCode);
        if (searching && keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (!searchText.isEmpty()) searchText = searchText.substring(0, searchText.length() - 1);
        }
        if (searching && keyCode == GLFW.GLFW_KEY_ENTER) {
            searching = false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        selectorUI.charTyped(codePoint);
        if (searching)
            searchText += codePoint;
        return super.charTyped(codePoint, modifiers);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        Vec2i fix = ClientUtil.getMouse((int) mouseX, (int) mouseY);
        mouseX = fix.getX();
        mouseY = fix.getY();
        float width = 400;
        float height = 300;
        if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 10, y + height - 60, width - 20, 50)) {
            selecting = !selecting;
            if (!selecting) {
                selectorUI.selected = null;
                selectorUI.enchantmentUI.stack = null;
            }
        }


        if (selecting) {
            selectorUI.mouseClicked(mouseX, mouseY, button);
            int itemX = (int) (x + 3);
            int itemY = (int) (y + 5 + scroll);
            {
                int categoryX = (int) (x + width - 100) + 4;
                int categoryY = itemY + 5;
                if (MathUtil.isHovered((float) mouseX, (float) mouseY, categoryX + 1, categoryY + 1, 13, 13)) {
                    catItems = CatItems.FOOD;
                }
                if (MathUtil.isHovered((float) mouseX, (float) mouseY, categoryX + 1 + 20, categoryY + 1, 13, 13)) {
                    catItems = CatItems.STONE;
                }
                if (MathUtil.isHovered((float) mouseX, (float) mouseY, categoryX + 1 + 40 * 0.9f, categoryY + 1, 13, 13)) {
                    catItems = CatItems.SWORD;
                }
                if (MathUtil.isHovered((float) mouseX, (float) mouseY, categoryX + 1 + 60 * 0.9f, categoryY + 1, 13, 13)) {
                    catItems = CatItems.BOOTS;
                }
                if (MathUtil.isHovered((float) mouseX, (float) mouseY, categoryX + 1 + 80 * 0.9f, categoryY + 1, 13, 13)) {
                    catItems = CatItems.ALL;
                }
            }
            if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 5, itemY, width - 100, 28)) {
                searching = !searching;
            }
            itemY += 30;
            for (Item item : Registry.ITEM) {
                if (!item.getName().getString().toLowerCase().contains(searchText.toLowerCase()))
                    continue;
                if (item instanceof AirItem) continue;

                if (catItems == CatItems.FOOD) {
                    if (!item.isFood()) continue;
                }
                if (catItems == CatItems.STONE) {
                    if (!(item instanceof BlockItem)) continue;
                }
                if (catItems == CatItems.SWORD) {
                    if (!(item instanceof SwordItem)) continue;
                }
                if (catItems == CatItems.BOOTS) {
                    if (!(item instanceof ArmorItem)) continue;
                }
                if (MathUtil.isHovered((float) mouseX, (float) mouseY, itemX, itemY, 15, 15) && MathUtil.isHovered((float) mouseX, (float) mouseY, x, y, width, height - 65)) {
                    boolean ignore = false;
                    for (AutoBuy a : Expensive.getInstance().getAutoBuyHandler().items) {
                        if (a.getItem() == item) {
                            ignore = true;
                            break;
                        }
                    }
                    if (!ignore) {
                        searching = false;
                        selectorUI.openAnimation.setDirection(Direction.FORWARDS);
                        selectorUI.enchantmentUI.openAnimation.setDirection(Direction.FORWARDS);
                        selectorUI.instance = item.getDefaultInstance();
                        selectorUI.selected = item;
                        selectorUI.enchantmentUI.stack = selectorUI.instance;
                        selectorUI.enchantmentUI.enchantmentIntegerMap.clear();
                    }
                }
                itemX += 18;
                if (itemX >= x + width - 5) {
                    itemX = (int) x + 3;
                    itemY += 20;
                }
            }
        } else {
            float widthPanel = 120;
            float buyX = 0;
            float buyY = scrolled;

            float offset = 10;
            for (AutoBuy buy : Expensive.getInstance().getAutoBuyHandler().items) {
                if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 10 + buyX, y + 10 + buyY, widthPanel, 50) && button == 1) {
                    Expensive.getInstance().getAutoBuyHandler().items.remove(buy);
                    AutoBuyConfig.updateFile();
                }
                buyX += widthPanel + 10;

                if (x + buyX + 110 >= x + width - 10) {
                    buyX = 0;
                    buyY += 60;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
