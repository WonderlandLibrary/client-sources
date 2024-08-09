package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import im.expensive.Expensive;
import im.expensive.command.staffs.StaffStorage;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventUpdate;
import im.expensive.functions.impl.combat.KillAura;
import im.expensive.functions.impl.combat.TestAura;
import im.expensive.ui.display.impl.StaffListRenderer;
import im.expensive.ui.styles.Style;
import im.expensive.utils.animations.Animation;
import im.expensive.utils.animations.Direction;
import im.expensive.utils.animations.impl.EaseBackIn;
import im.expensive.utils.font.styled.StyledFont;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.DisplayUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.ui.styles.StyleManager;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.drag.Dragging;
import im.expensive.utils.font.Fonts;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static im.expensive.utils.render.ColorUtils.IntColor.rgba;
import static net.minecraft.client.gui.AbstractGui.blit;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "HUD", type = Category.Render)
public class HUD extends Function {

    public ModeListSetting elements = new ModeListSetting("Элементы",
            new BooleanSetting("Логотип", true),
            new BooleanSetting("Список модулей", true),
            new BooleanSetting("Таргет худ", true),
            new BooleanSetting("Эффекты от зелей", true),
            new BooleanSetting("Информация о мире", true),
            new BooleanSetting("Броня", true),
            new BooleanSetting("Таблица модераторов", true));
    public ModeListSetting watermarkElement = new ModeListSetting("Элементы логотипа",
            new BooleanSetting("Название клиента", true),
            new BooleanSetting("Логин в чите", true),
            new BooleanSetting("Счетчик кадров", true),
            new BooleanSetting("Счетчик пинга", true)).setVisible(() -> elements.get(0).get());

    public ModeListSetting limitations = new ModeListSetting("Ограничения",
            new BooleanSetting("По рендеру", true),
            new BooleanSetting("По бинду", false)).setVisible(() -> elements.get(1).get());
    public SliderSetting fontSize = new SliderSetting("Font Size", 14, 12, 18, 1);

    public HUD() {
        addSettings(elements, watermarkElement, limitations, fontSize);
    }

    public static float offs = 0;
    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (!elements.get(6).get()) {
            return;
        }
        staffPlayers.clear();
        mc.world.getScoreboard().getTeams().stream().sorted(Comparator.comparing(Team::getName)).toList().forEach(team -> {
            String staffName = team.getMembershipCollection().toString().replaceAll("[\\[\\]]", "");
            boolean vanish = true;

            if (mc.getConnection() != null) {
                for (NetworkPlayerInfo info : mc.getConnection().getPlayerInfoMap()) {
                    if (info.getGameProfile().getName().equals(staffName)) {
                        vanish = false;
                    }
                }
            }

            if (namePattern.matcher(staffName).matches() && !staffName.equals(mc.player.getName().getString())) {
                if (!vanish) {
                    if (prefixMatches.matcher(team.getPrefix().getString().toLowerCase(Locale.ROOT)).matches() || StaffStorage.isStaff(staffName)) {
                        staffPlayers.add(new StaffListRenderer.StaffData(team.getPrefix(), staffName, StaffListRenderer.StaffData.Status.NONE));
                    }
                }
                if (vanish && !team.getPrefix().getString().isEmpty()) {
                    staffPlayers.add(new StaffListRenderer.StaffData(team.getPrefix(), staffName, StaffListRenderer.StaffData.Status.VANISHED));
                }
            }
        });

        this.staffPlayers = staffPlayers.stream()
                .sorted(Comparator.comparing(this::getPriority))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    @Subscribe
    public void onEvent(EventDisplay event) {
        handleRender(event);
    }

    /**
     * Îáðàáîò÷èê ñîáûòèÿ òèïà EventRender
     *
     * @param renderEvent Ñîáûòèå äëÿ ðåíäåðà
     */
    private void handleRender(EventDisplay renderEvent) {
        final MatrixStack stack = renderEvent.getMatrixStack();
        if (elements.get(0).get()) {
            onTitleRender(stack);
        }
        if (elements.get(1).get()) {
            onArrayListRender(stack);
        }
        if (elements.get(2).get()) {
            onRenderTargetHUD(stack);
        }
        if (elements.get(3).get()) {
            onPotionElementsRender(stack, renderEvent);
        }

        if (elements.get(4).get()) {
            onInformationRender(stack);
        }
        if (elements.get(5).get()) {
            onArmorRender();
        }
        if (elements.get(6).get()) {
            onStaffListRender(renderEvent);
        }
    }

    private Set<StaffListRenderer.StaffData> staffPlayers = new LinkedHashSet<>();
    private final Pattern namePattern = Pattern.compile("^\\w{3,16}$");
    private final Pattern prefixMatches = Pattern.compile(".*(mod|der|adm|help|wne|хелп|адм|поддержка|кура|own|taf|curat|dev|supp|yt|сотруд).*");
    float width;
    float height;
    public final Dragging dragging = Expensive.getInstance().createDrag(this, "stafflist", 190, 61);

    private void onStaffListRender(EventDisplay eventDisplay) {

        float posX = dragging.getX();
        float posY = dragging.getY();
        float padding = 5;
        float fontSize = 6.5f;
        MatrixStack ms = eventDisplay.getMatrixStack();
        ITextComponent name = GradientUtil.gradient("StaffList");


        drawStyledRect(posX, posY, width, height, 4);
        im.expensive.utils.render.font.Fonts.sfui.drawCenteredText(ms, name, posX + width / 2, posY + padding + 1f, fontSize);

        posY += fontSize + padding * 2;

        float maxWidth = im.expensive.utils.render.font.Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
        posY += 3.5f;
        for (StaffListRenderer.StaffData f : staffPlayers) {
            ITextComponent prefix = f.getPrefix();

            float prefixWidth = im.expensive.utils.render.font.Fonts.sfMedium.getWidth(prefix, fontSize);
            String staff = (prefix.getString().isEmpty() ? "" : " ") + f.getName();
            float nameWidth = im.expensive.utils.render.font.Fonts.sfMedium.getWidth(staff, fontSize);


            float localWidth = prefixWidth + nameWidth + im.expensive.utils.render.font.Fonts.sfMedium.getWidth(f.getStatus().string, fontSize) + padding * 3;

            im.expensive.utils.render.font.Fonts.sfMedium.drawText(ms, prefix, posX + padding, posY, fontSize, 255);
            im.expensive.utils.render.font.Fonts.sfMedium.drawText(ms, staff, posX + padding + prefixWidth, posY, -1, fontSize);
            im.expensive.utils.render.font.Fonts.sfMedium.drawText(ms, f.getStatus().string, posX + width - padding - im.expensive.utils.render.font.Fonts.sfMedium.getWidth(f.getStatus().string, fontSize), posY, f.getStatus().color, fontSize);

            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }

            posY += fontSize + padding;
            localHeight += fontSize + padding;
        }

        width = Math.max(maxWidth, 80);
        height = localHeight + 2.5f;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }

    private void onInformationRender(final MatrixStack stack) {
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        float y = mainWindow.scaledHeight() - Fonts.gilroyBold[15].getFontHeight();
        String pos = (int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ();

        Fonts.gilroyBold[15].drawString(stack, GradientUtil.gradient("Coords: "), 4, y, -1);
        Fonts.gilroyBold[15].drawString(stack, pos, 4 + Fonts.gilroyBold[15].getWidth("Coords: "), y, new Color(230, 230, 230).getRGB());
    }

    @AllArgsConstructor
    @Data
    public static class StaffData {
        ITextComponent prefix;
        String name;
        StaffListRenderer.StaffData.Status status;

        public enum Status {
            NONE("", -1),
            VANISHED("V", ColorUtils.rgb(254, 68, 68));
            public final String string;
            public final int color;

            Status(String string, int color) {
                this.string = string;
                this.color = color;
            }
        }

        @Override
        public String toString() {
            return prefix.getString();
        }
    }


    private int getPriority(StaffListRenderer.StaffData staffData) {
        return switch (staffData.toString()) {
            case "admin", "админ" -> 0;
            case "ml.admin" -> 1;
            case "gl.moder" -> 2;
            case "st.moder", "s.moder" -> 3;
            case "moder", "модератор", "куратор" -> 4;
            case "j.moder" -> 5;
            case "st.helper" -> 6;
            case "helper+" -> 7;
            case "helper" -> 8;
            case "yt+" -> 9;
            case "yt" -> 10;
            default -> 11;
        };
    }

    private void onArmorRender() {
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        ;
        int count = 0;
        for (int i = 0; i < mc.player.inventory.getSizeInventory(); i++) {
            ItemStack s = mc.player.inventory.getStackInSlot(i);
            if (s.getItem() == Items.TOTEM_OF_UNDYING) {
                count++;
            }
        }
        float xPos = mainWindow.scaledWidth() / 2f;
        float yPos = mainWindow.scaledHeight();

        boolean is = mc.player.inventory.mainInventory.stream().map(ItemStack::getItem).toList().contains(Items.TOTEM_OF_UNDYING);
        int off = is ? +5 : 0;
        for (ItemStack s : mc.player.inventory.armorInventory) {
            drawItemStack(s, xPos - off + 74, yPos - 56 + (mc.player.isCreative() ? 20 : 0), null, false);
            off += 15;
        }
        if (is)
            drawItemStack(new ItemStack(Items.TOTEM_OF_UNDYING), xPos - off + 73, yPos - 56 + (mc.player.isCreative() ? 20 : 0), String.valueOf(count), false);

    }

    public static void drawItemStack(ItemStack stack, float x, float y, boolean withoutOverlay, boolean scale, float scaleValue) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(x, y, 0);
        if (scale) GL11.glScaled(scaleValue, scaleValue, scaleValue);
        mc.getItemRenderer().renderItemAndEffectIntoGUI(stack, 0, 0);
        if (withoutOverlay) mc.getItemRenderer().renderItemOverlays(mc.fontRenderer, stack, 0, 0);
        RenderSystem.popMatrix();
    }

    public void drawItemStack(ItemStack stack,
                              double x,
                              double y,
                              String altText,
                              boolean withoutOverlay) {

        RenderSystem.translated(x, y, 0);
        mc.getItemRenderer().renderItemAndEffectIntoGUI(stack, 0, 0);
        if (!withoutOverlay)
            mc.getItemRenderer().renderItemOverlayIntoGUI(mc.fontRenderer, stack, 0, 0, altText);
        RenderSystem.translated(-x, -y, 0);
    }

    /**
     * Âûïîëíÿåò ðåíäåð ýôôåêòîâ íà ýêðàíå
     *
     * @param stack       Ìàòðèöà äëÿ ðåíäåðèíãà.
     * @param renderEvent Îáðàáîò÷èê òèïà EventRender
     */
    private void onPotionElementsRender(final MatrixStack stack, final EventDisplay renderEvent) {
        float off = Fonts.gilroyBold[15].getFontHeight();
        for (EffectInstance e : mc.player.getActivePotionEffects()) {
            MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
            ;
            String effectName = I18n.format(e.getEffectName());
            String level = effectName;
            String duration = EffectUtils.getPotionDurationString(e, 1);
            String effectString = level + " (" + duration + ")";

            float x = mainWindow.scaledWidth() - Fonts.gilroyBold[15].getWidth(effectString + " " + I18n.format("enchantment.level." + (e.getAmplifier() + 1))) - 2;
            float y = mainWindow.scaledHeight() - off;
            Fonts.gilroyBold[15].drawString(stack, GradientUtil.gradient(level), x, y, -1);
            Fonts.gilroyBold[15].drawString(stack, " " + I18n.format("enchantment.level." + (e.getAmplifier() + 1)) + " (" + duration + ")", x + Fonts.gilroyBold[15].getWidth(level), y, new Color(230, 230, 230).getRGB());

            PotionSpriteUploader potionspriteuploader = this.mc.getPotionSpriteUploader();
            TextureAtlasSprite textureatlassprite = potionspriteuploader.getSprite(e.getPotion());
            mc.getTextureManager().bindTexture(textureatlassprite.getAtlasTexture().getTextureLocation());
//            blit(stack, (int) x - 10, (int) y - 3, AbstractGui.blitOffset, 10, 10, textureatlassprite);
            off += Fonts.gilroyBold[15].getFontHeight();
        }
        offs = off;
    }

    /**
     * Âûïîëíÿåò ðåíäåð ëîãîòèïà ÷èòà
     *
     * @param stack Ìàòðèöà äëÿ ðåíäåðèíãà.
     */
    private void onTitleRender(final MatrixStack stack) {
        Expensive.UserData profile = Expensive.userData;

        StringBuilder titleText = new StringBuilder();
        int counter = 0; // Ïåðåìåííàÿ äëÿ ïîäñ÷åòà ýëåìåíòîâ

        if (watermarkElement.get(0).get()) {
            titleText.append("Fixed | Krakazybra");
            counter++;
        }
        if (watermarkElement.get(1).get()) {
            if (counter > 0) {
                titleText.append(TextFormatting.DARK_GRAY + " | " + TextFormatting.WHITE);
            }
            titleText.append("Src FIXED BY krakazybra");
            counter++;
        }
        if (watermarkElement.get(2).get()) {
            if (counter > 0) {
                titleText.append(TextFormatting.DARK_GRAY + " | " + TextFormatting.WHITE);
            }
            titleText.append(mc.debugFPS + "fps");
            counter++;
        }
        if (watermarkElement.get(3).get()) {
            if (counter > 0) {
                titleText.append(TextFormatting.DARK_GRAY + " | " + TextFormatting.WHITE);
            }
            titleText.append(calculatePing() + "ms");
        }


        // Êîîðäèíàòû âàòåðìêè
        final float x = 5, y = 9, titleWidth = Fonts.gilroyBold[14].getWidth(titleText.toString()) + 6, titleHeight = 12;

        DisplayUtils.drawRoundedRect(x, y, titleWidth, titleHeight, 1, rgba(16, 16, 20, 180));
        DisplayUtils.drawShadow(x, y, 1.5f, titleHeight, 5, getColor(0, 1), getColor(11, 1));

        DisplayUtils.drawRectVerticalW(x, y, 1.5f, titleHeight, getColor(0, 1), getColor(11, 1));

        Fonts.gilroyBold[14].drawString(stack, titleText.toString(), x + 4, y + Fonts.gilroyBold[14].getFontHeight() / 2f, -1);

    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {
        DisplayUtils.drawRoundedRect(x, y, width, height, 1, rgba(16, 16, 20, 180));
        DisplayUtils.drawShadow(x, y, 1.5f, height, 5, getColor(0, 1), getColor(11, 1));

        DisplayUtils.drawRectVerticalW(x, y, 1.5f, height, getColor(0, 1), getColor(11, 1));

    }

    java.util.List<Function> sortedFunctions = new ArrayList<>();
    final StopWatch delay = new StopWatch();

    /**
     * Âûïîëíÿåò ðåíäåð ñïèñêà èç ìîäóëåé
     *
     * @param stack Ìàòðèöà äëÿ ðåíäåðèíãà.
     */
    private void onArrayListRender(final MatrixStack stack) {
        float xArray = 5;
        float listY = 30;
        float height = 11;
        float offset = 0;

        final StyledFont font = Fonts.gilroyBold[fontSize.get().intValue()];

        if (delay.isReached(10000L)) {
            sortedFunctions = getSorted(font);
            delay.reset();
        }
        float gradientForce = 1;
        int yOffset = (fontSize.get().intValue() > 14 ? -14 + fontSize.get().intValue() - (fontSize.get().intValue() > 14 ? 1 : 0) : 0);

        for (Function f : sortedFunctions) {
            if (limitations.get(0).get() && f.getCategory() == Category.Render
                    || limitations.get(1).get() && f.getBind() == 0)
                continue;
            f.getAnimation().update();
            if (f.getAnimation().getValue() >= 0.01) {
                float width = font.getWidth(f.getName()) + 4;
                int color = getColor((int) ((offset + height * f.getAnimation().getValue()) * gradientForce), 1);
                int color2 = getColor((int) (offset * gradientForce), 1);


                float finalOffset = offset;
                MathUtil.scaleElements(xArray + width / 2F, listY + offset, 1F, (float) f.getAnimation().getValue(), 1, () -> {
                    DisplayUtils.drawRectW(xArray, listY + finalOffset, width, height, rgba(16, 16, 20, 180));
                    DisplayUtils.drawShadow(xArray, listY + finalOffset, 1.5f, height, 5, color, color2);
                    DisplayUtils.drawRectVerticalW(xArray, listY + finalOffset, 1.5f, height, color, color2);
                    font.drawString(stack, f.getName(), xArray + 3, listY + finalOffset + font.getFontHeight() / 2f - yOffset, color2);
                });

                offset += height * f.getAnimation().getValue();
            }

        }

    }

    public static java.util.List<Function> getSorted(StyledFont font) {
        java.util.List<Function> modules = Expensive.getInstance().getFunctionRegistry().getFunctions();
        modules.sort((o1, o2) -> {
            float width1 = font.getWidth(o1.getName()) + 4;
            float width2 = font.getWidth(o2.getName()) + 4;
            return Float.compare(width2, width1);
        });
        return modules;
    }

    float health = 0;
    public final Dragging targetHUD = Expensive.getInstance().createDrag(this, "targetDraggable", 240, 61);
    private final Animation targetHudAnimation = new EaseBackIn(200, 1, 1.5f);
    private PlayerEntity target = null;
    private double scale = 0.0D;

    private void onRenderTargetHUD(final MatrixStack stack) {
        this.target = getTarget(this.target);
        this.targetHudAnimation.setDuration(300);
        this.scale = targetHudAnimation.getOutput();

        if (scale == 0.0F) {
            target = null;
        }

        if (target == null) {
            return;
        }

        final String targetName = this.target.getName().getString();
        final float nameWidth = Fonts.gilroyBold[14].getWidth(targetName);
        final float xPosition = this.targetHUD.getX();
        final float yPosition = this.targetHUD.getY();
        final float maxWidth = Math.max(nameWidth + 50, 120);
        final float maxHeight = 30;

        float currentHealth = fix1000Health(target);
        float maxHealth = MathHelper.clamp(target.getMaxHealth(), 0, 20);

        this.health = MathUtil.fast(health, currentHealth / maxHealth, 5);
        this.health = MathHelper.clamp(this.health, 0, 1);

        GlStateManager.pushMatrix();
        sizeAnimation(xPosition + (maxWidth / 2), yPosition + (maxHeight / 2), scale);

        Vector4i colors = new Vector4i(
                getColor(0, 1),
                getColor(90, 1),
                getColor(180, 1),
                getColor(270, 1)
        );

        DisplayUtils.drawRoundedRect(xPosition, yPosition, maxWidth, maxHeight, 1, rgba(23, 23, 23, 255));
        DisplayUtils.drawShadow(xPosition, yPosition, 1.5f, maxHeight, 10, colors.x, colors.y);
        DisplayUtils.drawRectVerticalW(xPosition, yPosition, 1.5f, maxHeight, colors.x, colors.y);
        // Îòðèñîâêà ãîëîâû
        drawFace(xPosition + 6, yPosition + 4, 8F, 8F, 8F, 8F, 22, 22, 64F, 64F, (AbstractClientPlayerEntity) target);
        // Îòðèñîâêà êðóãîâîãî èíäèêàòîðà çäîðîâüÿ
        drawCircle(
                xPosition + maxWidth - 15,
                yPosition + maxHeight / 2,
                0,
                360,
                10,
                4,
                false,
                ColorUtils.rgba(26, 26, 26, 255)
        );

        drawCircle(
                xPosition + maxWidth - 15,
                yPosition + maxHeight / 2,
                0,
                this.health * 360 + 1,
                10,
                4,
                false
        );

        drawItemStack(xPosition + 32, yPosition + 22 - 5.5f, 10);


        Fonts.gilroyBold[18].drawString(stack, targetName.substring(0, Math.min(targetName.length(), 10)), xPosition + 32, yPosition + 6, -1);


        String healthValue = (int) MathUtil.round(this.health * 20, 0.5f) + "";
        Fonts.gilroyBold[16].drawCenteredString(stack, healthValue, xPosition + maxWidth - 15, yPosition + maxHeight / 2 - 2.2f, ColorUtils.rgba(255, 255, 255, 255));
        GlStateManager.popMatrix();

        this.targetHUD.setWidth(maxWidth);
        this.targetHUD.setHeight(maxHeight);
    }

    public void drawCircle(float x, float y, float start, float end, float radius, float width, boolean filled, int color) {

        float i;
        float endOffset;
        if (start > end) {
            endOffset = end;
            end = start;
            start = endOffset;
        }

        GlStateManager.enableBlend();
        GL11.glDisable(GL_TEXTURE_2D);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (i = end; i >= start; i--) {
            ColorUtils.setColor(color);
            float cos = (float) (MathHelper.cos((float) (i * Math.PI / 180)) * radius);
            float sin = (float) (MathHelper.sin((float) (i * Math.PI / 180)) * radius);
            GL11.glVertex2f(x + cos, y + sin);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        if (filled) {
            GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            for (i = end; i >= start; i--) {
                ColorUtils.setColor(color);
                float cos = (float) MathHelper.cos((float) (i * Math.PI / 180)) * radius;
                float sin = (float) MathHelper.sin((float) (i * Math.PI / 180)) * radius;
                GL11.glVertex2f(x + cos, y + sin);
            }
            GL11.glEnd();
        }

        GL11.glEnable(GL_TEXTURE_2D);
        GlStateManager.disableBlend();
    }

    public void drawCircle(float x, float y, float start, float end, float radius, float width, boolean filled) {

        float i;
        float endOffset;
        if (start > end) {
            endOffset = end;
            end = start;
            start = endOffset;
        }
        GlStateManager.enableBlend();
        RenderSystem.disableAlphaTest();
        GL11.glDisable(GL_TEXTURE_2D);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.shadeModel(7425);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(width);

        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (i = end; i >= start; i--) {
            ColorUtils.setColor(getColor((int) (i * 1), 1));
            float cos = (float) (MathHelper.cos((float) (i * Math.PI / 180)) * radius);
            float sin = (float) (MathHelper.sin((float) (i * Math.PI / 180)) * radius);
            GL11.glVertex2f(x + cos, y + sin);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        if (filled) {
            GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            for (i = end; i >= start; i--) {
                ColorUtils.setColor(getColor((int) (i * 1), 1));
                float cos = (float) MathHelper.cos((float) (i * Math.PI / 180)) * radius;
                float sin = (float) MathHelper.sin((float) (i * Math.PI / 180)) * radius;
                GL11.glVertex2f(x + cos, y + sin);
            }
            GL11.glEnd();
        }

        RenderSystem.enableAlphaTest();
        RenderSystem.shadeModel(7424);
        GL11.glEnable(GL_TEXTURE_2D);
        GlStateManager.disableBlend();
    }

    public static void drawFace(float d,
                                float y,
                                float u,
                                float v,
                                float uWidth,
                                float vHeight,
                                float width,
                                float height,
                                float tileWidth,
                                float tileHeight,
                                AbstractClientPlayerEntity target) {
        try {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            ResourceLocation skin = target.getLocationSkin();
            mc.getTextureManager().bindTexture(skin);
            float hurtPercent = getHurtPercent(target);
            GL11.glColor4f(1, 1 - hurtPercent, 1 - hurtPercent, 1);
            AbstractGui.drawScaledCustomSizeModalRect(d, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glPopMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float getRenderHurtTime(LivingEntity hurt) {
        return (float) hurt.hurtTime - (hurt.hurtTime != 0 ? mc.timer.renderPartialTicks : 0.0f);
    }

    public static float getHurtPercent(LivingEntity hurt) {
        return getRenderHurtTime(hurt) / (float) 10;
    }

    public static void sizeAnimation(double width, double height, double scale) {
        GlStateManager.translated(width, height, 0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0);
    }

    private void drawItemStack(float x, float y, float offset) {
        List<ItemStack> stackList = new ArrayList<>(Arrays.asList(target.getHeldItemMainhand(), target.getHeldItemOffhand()));
        stackList.addAll((Collection<? extends ItemStack>) target.getArmorInventoryList());

        final AtomicReference<Float> posX = new AtomicReference<>(x);

        stackList.stream()
                .filter(stack -> !stack.isEmpty())
                .forEach(stack -> drawItemStack(stack,
                        posX.getAndAccumulate(offset, Float::sum),
                        y,
                        true,
                        true, 0.6f));
    }


    private PlayerEntity getTarget(PlayerEntity nullTarget) {
        PlayerEntity target = nullTarget;

        KillAura killAura = Expensive.getInstance().getFunctionRegistry().getKillAura();

        if (killAura.getTarget() instanceof PlayerEntity) {
            target = (PlayerEntity) killAura.getTarget();
            targetHudAnimation.setDirection(Direction.FORWARDS);
        } else if (mc.currentScreen instanceof ChatScreen) {
            target = mc.player;
            targetHudAnimation.setDirection(Direction.FORWARDS);
        } else {
            targetHudAnimation.setDirection(Direction.BACKWARDS);
        }

        return target;
    }

    public static int getColor(int index) {
        return getColor(index, 16);
    }

    public static int getColor(int index, float multitude) {
        StyleManager styleManager = Expensive.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(),
                styleManager.getCurrentStyle().getSecondColor().getRGB(), (int) (index * multitude), Math.max(20 - 5, 1));
    }

    public static int getColor(int firstColor, int secondColor, int index, float multitude) {
        return ColorUtils.gradient(firstColor, secondColor, (int) (index * multitude), Math.max(20 - 5, 1));
    }

    private float fix1000Health(Entity entity) {
        Score score = mc.world.getScoreboard().getOrCreateScore(entity.getScoreboardName(),
                mc.world.getScoreboard().getObjectiveInDisplaySlot(2));

        LivingEntity living = (LivingEntity) entity;

        return userConnectedToFunTimeAndEntityIsPlayer(entity) ? score.getScorePoints() : MathHelper.clamp(living.getHealth(), 0, 20);
    }


    private boolean userConnectedToFunTimeAndEntityIsPlayer(Entity entity) {
        String header = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();
        return (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime")
                && (header.contains("анархия") || header.contains("гриферский")) && entity instanceof PlayerEntity);
    }

    public static int calculatePing() {
        return mc.player.connection.getPlayerInfo(mc.player.getUniqueID()) != null ?
                mc.player.connection.getPlayerInfo(mc.player.getUniqueID()).getResponseTime() : 0;
    }
}