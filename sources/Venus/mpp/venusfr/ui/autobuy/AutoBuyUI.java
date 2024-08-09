/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.autobuy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mpp.venusfr.ui.autobuy.AutoBuy;
import mpp.venusfr.ui.autobuy.AutoBuyConfig;
import mpp.venusfr.ui.autobuy.SelectorUI;
import mpp.venusfr.utils.animations.Direction;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.client.Vec2i;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.AirItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
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

    public AutoBuyUI(ITextComponent iTextComponent) {
        super(iTextComponent);
    }

    @Override
    public void init(Minecraft minecraft, int n, int n2) {
        this.selectorUI = new SelectorUI(null);
        this.selecting = false;
        super.init(minecraft, n, n2);
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        Vec2i vec2i = ClientUtil.getMouse((int)d, (int)d2);
        d = vec2i.getX();
        d2 = vec2i.getY();
        float f = 400.0f;
        float f2 = 300.0f;
        if (this.selecting) {
            if (MathUtil.isHovered((float)d, (float)d2, this.x, this.y, f, f2 - 65.0f)) {
                this.sc += (int)(d3 * 15.0);
            }
        } else if (MathUtil.isHovered((float)d, (float)d2, this.x, this.y, f, f2 - 65.0f)) {
            this.scrolled = (float)((double)this.scrolled + d3 * 15.0);
        }
        this.selectorUI.scroll((float)d3, (float)d, (float)d2);
        return super.mouseScrolled(d, d2, d3);
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        this.selectorUI.release(d, d2);
        return super.mouseReleased(d, d2, n);
    }

    private Vec2i adjustMouseCoordinates(int n, int n2) {
        int n3 = mc.getMainWindow().getScaledWidth();
        int n4 = mc.getMainWindow().getScaledHeight();
        float f = ((float)n - (float)n3 / 2.0f) / this.scale + (float)n3 / 2.0f;
        float f2 = ((float)n2 - (float)n4 / 2.0f) / this.scale + (float)n4 / 2.0f;
        return new Vec2i((int)f, (int)f2);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        AutoBuyUI.mc.gameRenderer.setupOverlayRendering(2);
        this.scroll = (int)MathUtil.fast(this.scroll, this.sc, 10.0f);
        this.animScroll = MathUtil.fast(this.animScroll, this.scrolled, 10.0f);
        int n3 = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int n4 = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        this.x = n3 / 2 - 200;
        this.y = n4 / 2 - 150;
        float f2 = 400.0f;
        float f3 = 300.0f;
        this.selectorUI.render(matrixStack, n, n2, f);
        DisplayUtils.drawShadow(this.x, this.y, f2, f3, 10, ColorUtils.rgba(17, 17, 17, 128));
        DisplayUtils.drawRoundedRect(this.x, this.y, f2, f3, new Vector4f(7.0f, 7.0f, 7.0f, 7.0f), ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(this.x + 10.0f, this.y + f3 - 60.0f, f2 - 20.0f, 50.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        Fonts.montserrat.drawCenteredText(matrixStack, this.selecting ? "\u041d\u0430\u0437\u0430\u0434" : "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c", this.x + f2 / 2.0f, this.y + f3 - 30.0f - 10.0f, MathUtil.isHovered(n, n2, this.x + 10.0f, this.y + f3 - 60.0f, f2 - 20.0f, 50.0f) ? -1 : ColorUtils.rgba(255, 255, 255, 128), 10.0f);
        if (this.selecting) {
            Scissor.push();
            Scissor.setFromComponentCoordinates(this.x, this.y, f2, f3 - 65.0f);
            int n5 = (int)(this.x + 3.0f);
            int n6 = (int)(this.y + 5.0f + (float)this.scroll);
            DisplayUtils.drawRoundedRect(this.x + 5.0f, (float)n6, f2 - 10.0f, 28.0f, 3.0f, ColorUtils.rgba(21, 24, 40, 165));
            DisplayUtils.drawRectW(this.x + f2 - 100.0f, n6, 0.5, 28.0, ColorUtils.rgb(17, 17, 17));
            int n7 = (int)(this.x + f2 - 100.0f) + 4;
            int n8 = n6 + 5;
            GlStateManager.pushMatrix();
            GlStateManager.translatef(n7, n8, 0.0f);
            GlStateManager.scaled(0.9, 0.9, 0.9);
            GlStateManager.translatef(-n7, -n8, 0.0f);
            mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.COOKED_BEEF.getDefaultInstance(), n7, n8);
            mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.STONE.getDefaultInstance(), n7 + 20, n8);
            mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.DIAMOND_SWORD.getDefaultInstance(), n7 + 40, n8);
            mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.DIAMOND_BOOTS.getDefaultInstance(), n7 + 60, n8);
            mc.getItemRenderer().renderItemAndEffectIntoGUI(Items.BARRIER.getDefaultInstance(), n7 + 80, n8);
            GlStateManager.popMatrix();
            String string = this.searchText;
            if (!this.searching && this.searchText.isEmpty()) {
                string = "\u041d\u0430\u0439\u0442\u0438 \u043f\u0440\u0435\u0434\u043c\u0435\u0442....";
            }
            if (string.length() > 25) {
                string = string.substring(0, 25);
            }
            Fonts.montserrat.drawText(matrixStack, string + (this.searching ? (System.currentTimeMillis() % 1000L > 500L ? "_" : "") : ""), this.x + 10.0f, (float)n6 + 14.0f - 3.5f, -1, 7.0f);
            n6 += 30;
            for (Item item : Registry.ITEM) {
                if (item instanceof AirItem) continue;
                if (MathUtil.isHovered(n5, n6, this.x, this.y - 15.0f, f2, f3 - 65.0f + 15.0f)) {
                    if (this.catItems == CatItems.FOOD && !item.isFood() || this.catItems == CatItems.STONE && !(item instanceof BlockItem) || this.catItems == CatItems.SWORD && !(item instanceof SwordItem) || this.catItems == CatItems.BOOTS && !(item instanceof ArmorItem) || !item.getName().getString().toLowerCase().contains(this.searchText.toLowerCase())) continue;
                    mc.getItemRenderer().renderItemAndEffectIntoGUI(item.getDefaultInstance(), n5, n6);
                }
                if (!((float)(n5 += 18) >= this.x + f2 - 5.0f)) continue;
                n5 = (int)this.x + 3;
                n6 += 20;
            }
            this.sc = !this.searching ? Math.min(0, this.sc) : 0;
        } else {
            float f4 = 120.0f;
            float f5 = 0.0f;
            float f6 = this.animScroll;
            float f7 = 10.0f;
            Scissor.push();
            Scissor.setFromComponentCoordinates(this.x, this.y, f2, f3 - 60.0f);
            for (AutoBuy autoBuy : venusfr.getInstance().getAutoBuyHandler().items) {
                DisplayUtils.drawRoundedRect(this.x + 10.0f + f5, this.y + 10.0f + f6, f4, 50.0f, 3.0f, ColorUtils.rgba(21, 24, 40, 165));
                DisplayUtils.drawRoundedRect(this.x + 15.0f + f5, this.y + 15.0f + f6, 20.0f, 20.0f, 3.0f, ColorUtils.rgba(21, 24, 40, 165));
                Scissor.push();
                Scissor.setFromComponentCoordinates(this.x + 10.0f + f5, this.y + 10.0f + f6, f4, 50.0);
                mc.getItemRenderer().renderItemAndEffectIntoGUI(autoBuy.getItem().getDefaultInstance(), (int)(this.x + 17.0f + f5), (int)(this.y + 17.0f + f6));
                String string = autoBuy.getItem().getName().getString();
                Fonts.montserrat.drawText(matrixStack, autoBuy.getItem().getName().getString(), (int)(this.x + 37.0f + f5), this.y + 21.0f + f6, -1, 7.0f, 0.05f);
                if (Fonts.montserrat.getWidth(autoBuy.getItem().getName().getString(), 7.0f, 0.05f) + 37.0f >= f4) {
                    DisplayUtils.drawRectVerticalW(this.x + f5 + f4, this.y + 21.0f + f6, 10.0, 7.0, ColorUtils.rgba(21, 24, 40, 165), ColorUtils.rgba(23, 23, 23, 0));
                }
                Fonts.montserrat.drawText(matrixStack, "\u0426\u0435\u043d\u0430: ", (int)(this.x + 13.0f + f5), this.y + 40.0f + f6, ColorUtils.rgba(255, 255, 255, 128), 5.0f, 0.05f);
                Fonts.montserrat.drawText(matrixStack, String.valueOf(autoBuy.getPrice()), (float)((int)(this.x + 13.0f + f5)) + Fonts.montserrat.getWidth("\u0426\u0435\u043d\u0430: ", 5.0f, 0.05f), this.y + 40.0f + f6, ColorUtils.rgba(64, 255, 64, 128), 5.0f, 0.05f);
                float f8 = 0.0f;
                if (!autoBuy.getEnchanments().isEmpty()) {
                    Fonts.montserrat.drawText(matrixStack, "\u0427\u0430\u0440\u044b: ", (int)(this.x + 13.0f + f5), this.y + 40.0f + f6 + 10.0f, ColorUtils.rgba(255, 255, 255, 128), 5.0f, 0.05f);
                    for (Enchantment enchantment : autoBuy.getEnchanments().keySet()) {
                        int n9 = autoBuy.getEnchanments().get(enchantment);
                        TranslationTextComponent translationTextComponent = new TranslationTextComponent(enchantment.getName());
                        String string2 = translationTextComponent.getString().substring(0, 2) + n9;
                        Fonts.montserrat.drawText(matrixStack, string2, (float)((int)(this.x + 13.0f + f5)) + Fonts.montserrat.getWidth("\u0427\u0430\u0440\u044b: ", 5.0f, 0.05f) + f8, this.y + 40.0f + f6 + 10.0f, ColorUtils.rgba(255, 255, 255, 128), 5.0f, 0.05f);
                        f8 += Fonts.montserrat.getWidth(string2, 5.0f, 0.05f) + 1.0f;
                    }
                }
                if (autoBuy.isItems() || autoBuy.isDon()) {
                    Fonts.montserrat.drawText(matrixStack, "\u0422\u043e\u043b\u044c\u043a\u043e \u0441 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u0430\u043c\u0438", (int)(this.x + 13.0f + f5), this.y + 40.0f + f6 + 10.0f, ColorUtils.rgba(255, 0, 0, 128), 5.0f, 0.05f);
                } else if (autoBuy.isFake()) {
                    Fonts.montserrat.drawText(matrixStack, "\u041f\u0440\u043e\u0432\u0435\u0440\u044f\u0435\u0442\u0441\u044f \u043d\u0430 \u0444\u0435\u0439\u043a \u043f\u0440\u0435\u0434\u043c\u0435\u0442", (int)(this.x + 13.0f + f5), this.y + 40.0f + f6 + 10.0f, ColorUtils.rgba(255, 0, 0, 128), 5.0f, 0.05f);
                }
                f5 += f4 + 10.0f;
                if (this.x + f5 + 110.0f >= this.x + f2 - 10.0f) {
                    f5 = 0.0f;
                    f6 += 60.0f;
                }
                Scissor.unset();
                Scissor.pop();
            }
            this.scrolled = MathHelper.clamp(this.scrolled, -f6 - 300.0f, 0.0f);
        }
        Scissor.unset();
        Scissor.pop();
        AutoBuyUI.mc.gameRenderer.setupOverlayRendering();
    }

    private void updateScaleBasedOnScreenWidth() {
        float f = 0.5f;
        float f2 = 400.0f;
        float f3 = mc.getMainWindow().getScaledWidth();
        if (f2 >= f3) {
            this.scale = f3 / f2;
            this.scale = MathHelper.clamp(this.scale, 0.5f, 1.0f);
        } else {
            this.scale = 1.0f;
        }
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        this.selectorUI.key(n);
        if (this.searching && n == 259 && !this.searchText.isEmpty()) {
            this.searchText = this.searchText.substring(0, this.searchText.length() - 1);
        }
        if (this.searching && n == 257) {
            this.searching = false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public boolean charTyped(char c, int n) {
        this.selectorUI.charTyped(c);
        if (this.searching) {
            this.searchText = this.searchText + c;
        }
        return super.charTyped(c, n);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        float f;
        float f2;
        Vec2i vec2i = ClientUtil.getMouse((int)d, (int)d2);
        if (MathUtil.isHovered((float)(d = (double)vec2i.getX()), (float)(d2 = (double)vec2i.getY()), this.x + 10.0f, this.y + (f2 = 300.0f) - 60.0f, (f = 400.0f) - 20.0f, 50.0f)) {
            boolean bl = this.selecting = !this.selecting;
            if (!this.selecting) {
                this.selectorUI.selected = null;
                this.selectorUI.enchantmentUI.stack = null;
            }
        }
        if (this.selecting) {
            this.selectorUI.mouseClicked(d, d2, n);
            int n2 = (int)(this.x + 3.0f);
            int n3 = (int)(this.y + 5.0f + (float)this.scroll);
            int n4 = (int)(this.x + f - 100.0f) + 4;
            int n5 = n3 + 5;
            if (MathUtil.isHovered((float)d, (float)d2, n4 + 1, n5 + 1, 13.0f, 13.0f)) {
                this.catItems = CatItems.FOOD;
            }
            if (MathUtil.isHovered((float)d, (float)d2, n4 + 1 + 20, n5 + 1, 13.0f, 13.0f)) {
                this.catItems = CatItems.STONE;
            }
            if (MathUtil.isHovered((float)d, (float)d2, (float)(n4 + 1) + 36.0f, n5 + 1, 13.0f, 13.0f)) {
                this.catItems = CatItems.SWORD;
            }
            if (MathUtil.isHovered((float)d, (float)d2, (float)(n4 + 1) + 54.0f, n5 + 1, 13.0f, 13.0f)) {
                this.catItems = CatItems.BOOTS;
            }
            if (MathUtil.isHovered((float)d, (float)d2, (float)(n4 + 1) + 72.0f, n5 + 1, 13.0f, 13.0f)) {
                this.catItems = CatItems.ALL;
            }
            if (MathUtil.isHovered((float)d, (float)d2, this.x + 5.0f, n3, f - 100.0f, 28.0f)) {
                this.searching = !this.searching;
            }
            n3 += 30;
            for (Item item : Registry.ITEM) {
                if (!item.getName().getString().toLowerCase().contains(this.searchText.toLowerCase()) || item instanceof AirItem || this.catItems == CatItems.FOOD && !item.isFood() || this.catItems == CatItems.STONE && !(item instanceof BlockItem) || this.catItems == CatItems.SWORD && !(item instanceof SwordItem) || this.catItems == CatItems.BOOTS && !(item instanceof ArmorItem)) continue;
                if (MathUtil.isHovered((float)d, (float)d2, n2, n3, 15.0f, 15.0f) && MathUtil.isHovered((float)d, (float)d2, this.x, this.y, f, f2 - 65.0f)) {
                    boolean bl = false;
                    for (AutoBuy autoBuy : venusfr.getInstance().getAutoBuyHandler().items) {
                        if (autoBuy.getItem() != item) continue;
                        bl = true;
                        break;
                    }
                    if (!bl) {
                        this.searching = false;
                        this.selectorUI.openAnimation.setDirection(Direction.FORWARDS);
                        this.selectorUI.enchantmentUI.openAnimation.setDirection(Direction.FORWARDS);
                        this.selectorUI.instance = item.getDefaultInstance();
                        this.selectorUI.selected = item;
                        this.selectorUI.enchantmentUI.stack = this.selectorUI.instance;
                        this.selectorUI.enchantmentUI.enchantmentIntegerMap.clear();
                    }
                }
                if (!((float)(n2 += 18) >= this.x + f - 5.0f)) continue;
                n2 = (int)this.x + 3;
                n3 += 20;
            }
        } else {
            float f3 = 120.0f;
            float f4 = 0.0f;
            float f5 = this.scrolled;
            float f6 = 10.0f;
            for (AutoBuy autoBuy : venusfr.getInstance().getAutoBuyHandler().items) {
                if (MathUtil.isHovered((float)d, (float)d2, this.x + 10.0f + f4, this.y + 10.0f + f5, f3, 50.0f) && n == 1) {
                    venusfr.getInstance().getAutoBuyHandler().items.remove(autoBuy);
                    AutoBuyConfig.updateFile();
                }
                if (!(this.x + (f4 += f3 + 10.0f) + 110.0f >= this.x + f - 10.0f)) continue;
                f4 = 0.0f;
                f5 += 60.0f;
            }
        }
        return super.mouseClicked(d, d2, n);
    }

    public static enum CatItems {
        FOOD,
        STONE,
        SWORD,
        BOOTS,
        ALL;

    }
}

