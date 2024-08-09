/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.autobuy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.Ellant;
import fun.ellant.utils.animations.Direction;
import fun.ellant.utils.client.ClientUtil;
import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.client.Vec2i;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.AirItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AutoBuyUI
extends Screen
implements IMinecraft {
    boolean typing;
    String text = "";
    float x;
    float y;
    boolean selecting;
    private SelectorUI selectorUI = new SelectorUI(null);
    int sc;
    float animScroll;
    float scrolled;
    int scroll;
    public CatItems catItems;
    float scale;
    public String searchText = "";
    public boolean searching;

    final ResourceLocation run = new ResourceLocation("expensive/images/hud/run2.png");
    float iconSize = 10;
    public AutoBuyUI(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        this.selectorUI = new SelectorUI(null);
        this.selecting = false;
        super.init(minecraft, width, height);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        Vec2i fix = ClientUtil.getMouse((int)mouseX, (int)mouseY);
        mouseX = fix.getX();
        mouseY = fix.getY();
        float width = 400.0f;
        float height = 300.0f;
        if (this.selecting) {
            if (MathUtil.isHovered((float)mouseX, (float)mouseY, this.x, this.y, width, height - 65.0f)) {
                this.sc += (int)(delta * 15.0);
            }
        } else if (MathUtil.isHovered((float)mouseX, (float)mouseY, this.x, this.y, width, height - 65.0f)) {
            this.scrolled = (float)((double)this.scrolled + delta * 15.0);
        }
        this.selectorUI.scroll((float)delta, (float)mouseX, (float)mouseY);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.selectorUI.release(mouseX, mouseY);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private Vec2i adjustMouseCoordinates(int mouseX, int mouseY) {
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();
        float adjustedMouseX = ((float)mouseX - (float)windowWidth / 2.0f) / this.scale + (float)windowWidth / 2.0f;
        float adjustedMouseY = ((float)mouseY - (float)windowHeight / 2.0f) / this.scale + (float)windowHeight / 2.0f;
        return new Vec2i((int)adjustedMouseX, (int)adjustedMouseY);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        block14: {
            AutoBuyUI.mc.gameRenderer.setupOverlayRendering(2);
            this.scroll = (int)MathUtil.fast(this.scroll, this.sc, 10.0f);
            this.animScroll = MathUtil.fast(this.animScroll, this.scrolled, 10.0f);
            int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
            int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
            this.x = windowWidth / 2 - 200;
            this.y = windowHeight / 2 - 150;
            float width = 400.0f;
            float height = 300.0f;
            this.selectorUI.render(matrixStack, mouseX, mouseY, partialTicks);
            DisplayUtils.drawShadow(this.x, this.y, width, height, 10, ColorUtils.rgba(17, 17, 17, 128));
            DisplayUtils.drawRoundedRect(this.x, this.y, width, height, new Vector4f(7.0f, 7.0f, 7.0f, 7.0f), ColorUtils.rgba(17, 17, 17, 255));
            DisplayUtils.drawRoundedRect(this.x + 10.0f, this.y + height - 60.0f, width - 20.0f, 50.0f, 4.0f, ColorUtils.rgba(23, 23, 23, 255));
            DisplayUtils.drawImage(run, this.x + 2.5f, this.y + 6.5f, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
            Fonts.montserrat.drawCenteredText(matrixStack, this.selecting ? "\u041d\u0430\u0437\u0430\u0434" : "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c", this.x + width / 2.0f, this.y + height - 30.0f - 10.0f, MathUtil.isHovered(mouseX, mouseY, this.x + 10.0f, this.y + height - 60.0f, width - 20.0f, 50.0f) ? -1 : ColorUtils.rgba(255, 255, 255, 128), 10.0f);
            if (this.selecting) {
                Scissor.push();
                Scissor.setFromComponentCoordinates(this.x, this.y, width, height - 65.0f);
                int itemX = (int)(this.x + 3.0f);
                int itemY = (int)(this.y + 5.0f + (float)this.scroll);
                DisplayUtils.drawRoundedRect(this.x + 5.0f, (float)itemY, width - 10.0f, 28.0f, 3.0f, ColorUtils.rgba(23, 23, 23, 255));
                DisplayUtils.drawRectW(this.x + width - 100.0f, itemY, 0.5, 28.0, ColorUtils.rgb(17, 17, 17));
                int categoryX = (int)(this.x + width - 100.0f) + 4;
                int categoryY = itemY + 5;
                GlStateManager.pushMatrix();
                GlStateManager.translatef(categoryX, categoryY, 0.0f);
                GlStateManager.scaled(0.9, 0.9, 0.9);
                GlStateManager.translatef(-categoryX, -categoryY, 0.0f);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.COOKED_BEEF.getDefaultInstance(), categoryX, categoryY);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.STONE.getDefaultInstance(), categoryX + 20, categoryY);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.DIAMOND_SWORD.getDefaultInstance(), categoryX + 40, categoryY);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.DIAMOND_BOOTS.getDefaultInstance(), categoryX + 60, categoryY);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.BARRIER.getDefaultInstance(), categoryX + 80, categoryY);
                GlStateManager.popMatrix();
                String textToDraw = this.searchText;
                if (!this.searching && this.searchText.isEmpty()) {
                    textToDraw = "Найти предмет....";
                }
                if (textToDraw.length() > 25) {
                    textToDraw = textToDraw.substring(0, 25);
                }
                Fonts.montserrat.drawText(matrixStack, textToDraw + (this.searching ? (System.currentTimeMillis() % 1000L > 500L ? "_" : "") : ""), this.x + 10.0f, (float)itemY + 14.0f - 3.5f, -1, 7.0f);
                itemY += 30;
                Iterator var25 = Registry.ITEM.iterator();
                while (true) {
                    if (!var25.hasNext()) {
                        this.sc = !this.searching ? Math.min(0, this.sc) : 0;
                        break block14;
                    }
                    Item item = (Item)var25.next();
                    if (item instanceof AirItem) continue;
                    if (MathUtil.isHovered(itemX, itemY, this.x, this.y - 15.0f, width, height - 65.0f + 15.0f)) {
                        if (this.catItems == CatItems.FOOD && !item.isFood() || this.catItems == CatItems.STONE && !(item instanceof BlockItem) || this.catItems == CatItems.SWORD && !(item instanceof SwordItem) || this.catItems == CatItems.BOOTS && !(item instanceof ArmorItem) || !item.getName().getString().toLowerCase().contains(this.searchText.toLowerCase())) continue;
                        mc.getItemRenderer().renderItemAndEffectIntoGUI(item.getDefaultInstance(), itemX, itemY);
                    }
                    if (!((float)(itemX += 18) >= this.x + width - 5.0f)) continue;
                    itemX = (int)this.x + 3;
                    itemY += 20;
                }
            }
            float widthPanel = 120.0f;
            float buyX = 0.0f;
            float buyY = this.animScroll;
            float offset = 10.0f;
            Scissor.push();
            Scissor.setFromComponentCoordinates(this.x, this.y, width, height - 60.0f);
            for (AutoBuy buy : Ellant.getInstance().getAutoBuyHandler().items) {
                DisplayUtils.drawRoundedRect(this.x + 10.0f + buyX, this.y + 10.0f + buyY, widthPanel, 50.0f, 3.0f, ColorUtils.rgba(23, 23, 23, 255));
                DisplayUtils.drawRoundedRect(this.x + 15.0f + buyX, this.y + 15.0f + buyY, 20.0f, 20.0f, 3.0f, ColorUtils.rgba(30, 30, 30, 255));
                Scissor.push();
                Scissor.setFromComponentCoordinates(this.x + 10.0f + buyX, this.y + 10.0f + buyY, widthPanel, 50.0);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(buy.getItem().getDefaultInstance(), (int)(this.x + 17.0f + buyX), (int)(this.y + 17.0f + buyY));
                String itemName = buy.getItem().getName().getString();
                Fonts.montserrat.drawText(matrixStack, buy.getItem().getName().getString(), (int)(this.x + 37.0f + buyX), this.y + 21.0f + buyY, -1, 7.0f, 0.05f);
                if (Fonts.montserrat.getWidth(buy.getItem().getName().getString(), 7.0f, 0.05f) + 37.0f >= widthPanel) {
                    DisplayUtils.drawRectVerticalW(this.x + buyX + widthPanel, this.y + 21.0f + buyY, 10.0, 7.0, ColorUtils.rgba(23, 23, 23, 255), ColorUtils.rgba(23, 23, 23, 0));
                }
                Fonts.montserrat.drawText(matrixStack, "Цена предмета: ", (int)(this.x + 13.0f + buyX), this.y + 40.0f + buyY, ColorUtils.rgba(255, 255, 255, 128), 5.0f, 0.05f);
                Fonts.montserrat.drawText(matrixStack, String.valueOf(buy.getPrice()), (float)((int)(this.x + 13.0f + buyX)) + Fonts.montserrat.getWidth("Цена предмета: ", 5.0f, 0.05f), this.y + 40.0f + buyY, ColorUtils.rgba(64, 255, 64, 128), 5.0f, 0.05f);
                float off = 0.0f;
                if (!buy.getEnchanments().isEmpty()) {
                    Fonts.montserrat.drawText(matrixStack, "Зачарования: ", (int)(this.x + 13.0f + buyX), this.y + 40.0f + buyY + 10.0f, ColorUtils.rgba(255, 255, 255, 128), 5.0f, 0.05f);
                    for (Enchantment enchantment : buy.getEnchanments().keySet()) {
                        int level = buy.getEnchanments().get(enchantment);
                        TranslationTextComponent iformattabletextcomponent = new TranslationTextComponent(enchantment.getName());
                        String var10000 = iformattabletextcomponent.getString().substring(0, 2);
                        String enchText = var10000 + level;
                        Fonts.montserrat.drawText(matrixStack, enchText, (float)((int)(this.x + 13.0f + buyX)) + Fonts.montserrat.getWidth("Зачарования предмета: ", 5.0f, 0.05f) + off, this.y + 40.0f + buyY + 10.0f, ColorUtils.rgba(255, 255, 255, 128), 5.0f, 0.05f);
                        off += Fonts.montserrat.getWidth(enchText, 5.0f, 0.05f) + 1.0f;
                    }
                }
                if (!buy.isDon()) {
                    if (buy.isFake()) {
                        Fonts.montserrat.drawText(matrixStack, "Проверяется на фейк предмет", (int)(this.x + 13.0f + buyX), this.y + 40.0f + buyY + 10.0f, ColorUtils.rgba(255, 0, 0, 128), 5.0f, 0.05f);
                    }
                } else {
                    Fonts.montserrat.drawText(matrixStack, "Только с предметами", (int)(this.x + 13.0f + buyX), this.y + 40.0f + buyY + 10.0f, ColorUtils.rgba(255, 0, 0, 128), 5.0f, 0.05f);
                }
                if (this.x + (buyX += widthPanel + 10.0f) + 110.0f >= this.x + width - 10.0f) {
                    buyX = 0.0f;
                    buyY += 60.0f;
                }
                Scissor.unset();
                Scissor.pop();
            }
            this.scrolled = MathHelper.clamp(this.scrolled, -buyY - 300.0f, 0.0f);
        }
        Scissor.unset();
        Scissor.pop();
        AutoBuyUI.mc.gameRenderer.setupOverlayRendering();
    }

    private void updateScaleBasedOnScreenWidth() {
        float MIN_SCALE = 0.5f;
        float totalPanelWidth = 400.0f;
        float screenWidth = mc.getMainWindow().getScaledWidth();
        if (totalPanelWidth >= screenWidth) {
            this.scale = screenWidth / totalPanelWidth;
            this.scale = MathHelper.clamp(this.scale, 0.5f, 1.0f);
        } else {
            this.scale = 1.0f;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.selectorUI.key(keyCode);
        if (this.searching && keyCode == 259 && !this.searchText.isEmpty()) {
            this.searchText = this.searchText.substring(0, this.searchText.length() - 1);
        }
        if (this.searching && keyCode == 257) {
            this.searching = false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        this.selectorUI.charTyped(codePoint);
        if (this.searching) {
            this.searchText = this.searchText + codePoint;
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float width;
        float height;
        Vec2i fix = ClientUtil.getMouse((int)mouseX, (int)mouseY);
        if (MathUtil.isHovered((float)(mouseX = (double)fix.getX()), (float)(mouseY = (double)fix.getY()), this.x + 10.0f, this.y + (height = 300.0f) - 60.0f, (width = 400.0f) - 20.0f, 50.0f)) {
            boolean bl = this.selecting = !this.selecting;
            if (!this.selecting) {
                this.selectorUI.selected = null;
                this.selectorUI.enchantmentUI.stack = null;
            }
        }
        if (this.selecting) {
            this.selectorUI.mouseClicked(mouseX, mouseY, button);
            int itemX = (int)(this.x + 3.0f);
            int itemY = (int)(this.y + 5.0f + (float)this.scroll);
            int categoryX = (int)(this.x + width - 100.0f) + 4;
            int categoryY = itemY + 5;
            if (MathUtil.isHovered((float)mouseX, (float)mouseY, categoryX + 1, categoryY + 1, 13.0f, 13.0f)) {
                this.catItems = CatItems.FOOD;
            }
            if (MathUtil.isHovered((float)mouseX, (float)mouseY, categoryX + 1 + 20, categoryY + 1, 13.0f, 13.0f)) {
                this.catItems = CatItems.STONE;
            }
            if (MathUtil.isHovered((float)mouseX, (float)mouseY, (float)(categoryX + 1) + 36.0f, categoryY + 1, 13.0f, 13.0f)) {
                this.catItems = CatItems.SWORD;
            }
            if (MathUtil.isHovered((float)mouseX, (float)mouseY, (float)(categoryX + 1) + 54.0f, categoryY + 1, 13.0f, 13.0f)) {
                this.catItems = CatItems.BOOTS;
            }
            if (MathUtil.isHovered((float)mouseX, (float)mouseY, this.x + 5.0f, itemY, width - 100.0f, 28.0f)) {
                this.searching = !this.searching;
            }
            itemY += 30;
            Iterator var18 = Registry.ITEM.iterator();
            while (true) {
                if (!var18.hasNext()) {
                    return super.mouseClicked(mouseX, mouseY, button);
                }
                Item item = (Item)var18.next();
                if (!item.getName().getString().toLowerCase().contains(this.searchText.toLowerCase()) || item instanceof AirItem || this.catItems == CatItems.FOOD && !item.isFood() || this.catItems == CatItems.STONE && !(item instanceof BlockItem) || this.catItems == CatItems.SWORD && !(item instanceof SwordItem) || this.catItems == CatItems.BOOTS && !(item instanceof ArmorItem)) continue;
                if (MathUtil.isHovered((float)mouseX, (float)mouseY, itemX, itemY, 15.0f, 15.0f) && MathUtil.isHovered((float)mouseX, (float)mouseY, this.x, this.y, width, height - 65.0f)) {
                    boolean ignore = false;
                    for (AutoBuy a : Ellant.getInstance().getAutoBuyHandler().items) {
                        if (a.getItem() != item) continue;
                        ignore = true;
                        break;
                    }
                    if (!ignore) {
                        this.searching = false;
                        this.selectorUI.openAnimation.setDirection(Direction.FORWARDS);
                        this.selectorUI.enchantmentUI.openAnimation.setDirection(Direction.FORWARDS);
                        this.selectorUI.instance = item.getDefaultInstance();
                        this.selectorUI.selected = item;
                        this.selectorUI.enchantmentUI.stack = this.selectorUI.instance;
                        this.selectorUI.enchantmentUI.enchantmentIntegerMap.clear();
                    }
                }
                if (!((float)(itemX += 18) >= this.x + width - 5.0f)) continue;
                itemX = (int)this.x + 3;
                itemY += 20;
            }
        }
        float widthPanel = 120.0f;
        float buyX = 0.0f;
        float buyY = this.scrolled;
        float offset = 10.0f;
        for (AutoBuy buy : Ellant.getInstance().getAutoBuyHandler().items) {
            if (MathUtil.isHovered((float)mouseX, (float)mouseY, this.x + 10.0f + buyX, this.y + 10.0f + buyY, widthPanel, 50.0f) && button == 1) {
                Ellant.getInstance().getAutoBuyHandler().items.remove(buy);
                AutoBuyConfig.updateFile();
            }
            if (!(this.x + (buyX += widthPanel + 10.0f) + 110.0f >= this.x + width - 10.0f)) continue;
            buyX = 0.0f;
            buyY += 60.0f;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static enum CatItems {
        FOOD,
        STONE,
        SWORD,
        BOOTS,
        ALL;

    }
}

