package wtf.shiyeno.modules.impl.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import org.joml.Vector4i;
import org.joml.Vector4d;
import wtf.shiyeno.Initilization;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.MultiBoxSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.BetterText;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.UserProfile;
import wtf.shiyeno.util.animations.Animation;
import wtf.shiyeno.util.animations.Direction;
import wtf.shiyeno.util.animations.impl.DecelerateAnimation;
import wtf.shiyeno.util.animations.impl.EaseBackIn;
import wtf.shiyeno.util.drag.Dragging;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.font.styled.StyledFont;
import wtf.shiyeno.util.math.MathUtil;
import wtf.shiyeno.util.math.PlayerPositionTracker;
import wtf.shiyeno.util.misc.HudUtil;
import wtf.shiyeno.util.render.BloomHelper;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.util.render.animation.AnimationMath;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static wtf.shiyeno.ui.clickgui.Window.light;
import static wtf.shiyeno.util.render.ColorUtil.rgba;
import static wtf.shiyeno.util.render.RenderUtil.Render2D.drawCircle;
import static wtf.shiyeno.util.render.RenderUtil.Render2D.drawFace;

@FunctionAnnotation(name = "Interface", type = Type.Render)
public class HUD2 extends Function {

    public final MultiBoxSetting elements = new MultiBoxSetting("Элементы",
            new BooleanOption("Ватермарка", true),
            new BooleanOption("Активные функции", true),
            new BooleanOption("Координаты", true),
            new BooleanOption("Активный таргет", true),
            new BooleanOption("Активные стаффы", true),
            new BooleanOption("Активные бинды", true),
            new BooleanOption("Активные эффекты", true),
            new BooleanOption("Уведомления", true)
    );

    public final BooleanOption glowing = new BooleanOption("Свечение", true);
    public final BooleanOption shadow = new BooleanOption("Тень", true);
    public final BooleanOption targetFollow = new BooleanOption("Преследовать игрока", false);

    private final SliderSetting offset = new SliderSetting("Отступ", 5, 1, 10, 1);

    public HUD2() {
        addSettings(elements, glowing, shadow, offset, targetFollow);
    }

    final float round_degree = 3;
    final float drag_degree = round_degree + 1;

    final float cWidth = 0;

    final int t_color = Color.WHITE.getRGB();
    final int bg_color = ColorUtil.interpolateColor(light, ColorUtil.getColorStyle(0), 0.02f);
    final int sec_color = RenderUtil.reAlphaInt(ColorUtil.interpolateColor(light, ColorUtil.getColorStyle(0), 0.02f), 150);

    int[] colors = new int[360];

    final StyledFont medium = Fonts.msMedium[13];
    final StyledFont small = Fonts.msMedium[14];

    MainWindow window;

    List<Function> functions = new ArrayList<>();

    final Dragging keyBinds = Initilization.createDrag(this, "KeyBinds-new", 10, 100);
    final Dragging potions = Initilization.createDrag(this, "Potions-new", 10, 200);
    final Dragging staffList = Initilization.createDrag(this, "StaffList-new", 10, 300);

    final Dragging targetHUD = Initilization.createDrag(this, "TargetHUD-new", 10, 300);
    final Dragging timerHUD = Initilization.createDrag(this, "TimerHUD-new", 10, 400);

    public final boolean draggableViewEnabled(String name) {
        Map<String, Integer> map = new HashMap<>();
        map.put("TargetHUD-new", 3);
        map.put("StaffList-new", 4);
        map.put("KeyBinds-new", 5);
        map.put("Potions-new", 6);

        if (Objects.equals(name, "TimerHUD-new"))
            return true;
        else
            return elements.get(map.get(name));
    }

    Animation tHudAnimation = new DecelerateAnimation(300, 1, Direction.BACKWARDS);
    Animation coordsAnimation = new DecelerateAnimation(300, 1, Direction.BACKWARDS);

    PlayerEntity target = null;

    private final Pattern namePattern = Pattern.compile("^\\w{3,16}$");
    private final Pattern prefixMatches = Pattern.compile(".*(mod|der|adm|help|wne|мод|хелп|помо|адм|владе|отри|таф|taf|curat|курато|dev|раз|supp|сапп|yt|ютуб|mladmin).*");

    private final Map<ITextComponent, String> staffPlayers = new LinkedHashMap<>();

    private Vector4d targetVector;

    @Override
    public void onEvent(Event event) {
        if (mc.player == null || mc.world == null) return;

        if (event instanceof EventUpdate) {
            staffPlayers.clear();

            for (ScorePlayerTeam team : mc.world.getScoreboard().getTeams().stream().sorted(Comparator.comparing(Team::getName)).toList()) {
                String name = team.getMembershipCollection().toString();
                name = name.substring(1, name.length() - 1);
                if (namePattern.matcher(name).matches()) {
                    if (prefixMatches.matcher(team.getPrefix().getString().toLowerCase(Locale.ROOT)).matches() || Managment.STAFF_MANAGER.isStaff(name)) {
                        staffPlayers.put(team.getPrefix(), name);
                    }
                }
            }

            if (Managment.FUNCTION_MANAGER.getFunctions().isEmpty() || !functions.isEmpty()) return;
            updateFunctions();
        }

        if (mc.currentScreen instanceof ChatScreen) {
            coordsAnimation.setDirection(Direction.FORWARDS);
        } else {
            coordsAnimation.setDirection(Direction.BACKWARDS);
        }

        if (event instanceof EventRender e && e.isRender2D()) {
            for (int i = 0; i < colors.length; i++) {
                colors[i] = Managment.STYLE_MANAGER.getCurrentStyle().getColor(i);
            }

            window = e.scaledResolution;

            final boolean glowing = this.glowing.get();
            final boolean shadow = this.shadow.get();
            final int offset = this.offset.getValue().intValue();
            final MatrixStack matrixStack = e.matrixStack;

            if (elements.get(0)) renderWatermark(matrixStack, offset, glowing, shadow);
            if (elements.get(1)) renderFunctions(matrixStack, offset, glowing);
            if (elements.get(2)) renderCoordinates(matrixStack, offset, glowing);
            if (elements.get(3)) renderTarget(matrixStack, offset, glowing);
            if (elements.get(4)) renderStaffList(matrixStack, offset, glowing);
            if (elements.get(5)) renderKeyBinds(matrixStack, offset, glowing);
            if (elements.get(6)) renderPotions(matrixStack, offset, glowing);

            renderTimer(matrixStack, offset, glowing);
        } else if (event instanceof EventRender e && e.isRender3D()) {
            if (target != null)
                targetVector = PlayerPositionTracker.updatePlayerPositions(target, e.partialTicks);
        }
    }


    private String getUserName() {
        UserProfile userProfile = Managment.USER_PROFILE;
        if (userProfile == null)
            return "";
        return userProfile.getName();
    }

    BetterText betterText = new BetterText(List.of("", "shiyeno", getUserName()), 5000);

    private void renderWatermark(MatrixStack matrixStack, int offset, boolean glowing, boolean shadow) {
        var watermark = betterText.output;
        var dateString = new SimpleDateFormat("hh:mm a").format(new Date());
        var ping = HudUtil.calculatePing();

        var logoWidth = Fonts.logo[13].getWidth("A") + 4;
        var watermarkWidth = medium.getWidth(watermark) + 8;
        var dateWidth = medium.getWidth(dateString) + 8;
        var fpsWidth = medium.getWidth(Minecraft.debugFPS + " fps") + 8;
        var pingWidth = medium.getWidth(ping + "ms") + 8;

        var fpsIconWidth = +Fonts.icons3[13].getWidth("F");
        var pingIconWidth = +Fonts.icons3[13].getWidth("P");
        var dateIconWidth = +Fonts.icons3[13].getWidth("C");

        RenderUtil.Render2D.drawRoundedRect(offset, offset,
                logoWidth + watermarkWidth + fpsWidth + pingWidth + dateWidth + fpsIconWidth + pingIconWidth + dateIconWidth,
                14,
                round_degree,
                bg_color
        );

        Fonts.logo[13].drawString(matrixStack,
                ClientUtil.gradient("A", ColorUtil.getColorStyle(0), ColorUtil.getColorStyle(90)),
                offset + 4, offset + 6.75, t_color);

        Fonts.icons3[13].drawString(matrixStack,
                "F",
                offset + logoWidth + watermarkWidth, offset + 6.75, RenderUtil.reAlphaInt(t_color, 80));
        Fonts.icons3[13].drawString(matrixStack,
                "P",
                offset + logoWidth + watermarkWidth + fpsIconWidth + fpsWidth, offset + 6.75, RenderUtil.reAlphaInt(t_color, 80));
        Fonts.icons3[13].drawString(matrixStack,
                "C",
                offset + logoWidth + watermarkWidth + fpsIconWidth + fpsWidth + pingIconWidth + pingWidth, offset + 6.5, RenderUtil.reAlphaInt(t_color, 80));

        medium.drawString(matrixStack, ClientUtil.gradient(watermark, ColorUtil.getColorStyle(0), ColorUtil.getColorStyle(90)), offset + 4 + logoWidth, offset + 5.5, t_color);
        medium.drawString(matrixStack, Minecraft.debugFPS + " fps", offset + logoWidth + watermarkWidth + fpsIconWidth + 4, offset + 6, t_color);
        medium.drawString(matrixStack, ping + "ms", offset + watermarkWidth + logoWidth + fpsIconWidth + fpsWidth + 4 + pingIconWidth, offset + 6, t_color);
        medium.drawString(matrixStack, dateString, offset + watermarkWidth + logoWidth + fpsIconWidth + fpsWidth + +pingIconWidth + pingWidth + dateIconWidth + 4, offset + 6, t_color);
    }

    private void renderFunctions(MatrixStack matrixStack, int offset, boolean glowing) {
        float padding = 4;
        float heightPadding = 4f;

        float height = small.getFontHeight() + heightPadding  * 2;

        List<Function> fs = new ArrayList<>();

        for (Function f : functions) {
            f.animation = AnimationMath.fast(f.animation, f.state ? 1 : 0, 15);
            if (f.animation < 0.1) continue;
            fs.add(f);
        }

        List<Function> fs1 = new ArrayList<>();
        int in = 0;
        for (Function f : functions) {
            if (!f.state) continue;
            fs1.add(f);
        }

        for (Function f : fs1) {
            boolean isLast = in == fs1.size() - 1;

            f.degree = isLast ? round_degree : Math.min(small.getWidth(f.name) + 1 - small.getWidth(fs1.get(in + 1).name), round_degree);
            in++;
        }

        float index = 0;

        for (Function f : fs) {
            boolean isFirst = index == 0;
            boolean isLast = Math.round(index) == fs.size() - 1;

            float width = small.getWidth(f.name) + (padding * 2);

            float r_posX = window.scaledWidth() - offset - width;
            float r_posY = offset + (index * height);

            float degree = f.degree;

            Vector4f left_vec = new Vector4f(isFirst ? drag_degree : 0, degree, isFirst ? drag_degree : 0, isLast ? drag_degree : 0);

            float finalIndex = index;

            GlStateManager.pushMatrix();
            GlStateManager.translated(r_posX, r_posY, 0);
            GlStateManager.scaled(1, f.animation, 1);
            GlStateManager.translated(-(r_posX), -r_posY, 0);


            RenderUtil.Render2D.drawRoundedCorner(r_posX, r_posY, width + 2, height, left_vec,
                    ColorUtil.interpolateColor(light, getColor((int) (finalIndex * 10)), 0.02f));

            BloomHelper.registerRenderCall(() -> {
                small.drawStringWithShadow(matrixStack, f.name, r_posX + padding + 1, r_posY + padding + (height - heightPadding * 2) / 2 - 1.5,
                        RenderUtil.reAlphaInt(getColor((int) (finalIndex * 10)), 210)
                );
            });
            small.drawString(matrixStack, f.name, r_posX + padding + 1, r_posY + padding + (height - heightPadding * 2) / 2 - 1.5,
                    ColorUtil.interpolateColor(getColor((int) (finalIndex * 10)),
                            new Color(255, 255, 255).getRGB(), 0.5f));

            GlStateManager.popMatrix();

            index += f.animation;
            in++;
        }


    }

    float ptHeight = 0;
    float ptLineWidth = 0;

    float ptWidthAnimation = 0, ptHeightAnimation = 0;
    float ptLineWidthAnimation = 0;

    Map<String, Integer> effects = new HashMap<String, Integer>();

    private void renderPotions(MatrixStack matrixStack, int offset, boolean glowing) {
        float posX = potions.getX();
        float posY = potions.getY();

        RenderUtil.Render2D.drawRoundedCorner(posX, posY + 18 - drag_degree,
                ptWidthAnimation,
                ptHeightAnimation - 18 + drag_degree,
                new Vector4f(0, drag_degree, 0, drag_degree),
                sec_color);

        RenderUtil.Render2D.drawRoundedCorner(posX, posY,
                ptWidthAnimation,
                18,
                new Vector4f(drag_degree,
                        Math.abs((int) ((ptLineWidthAnimation / ptWidthAnimation) * drag_degree) - drag_degree),
                        drag_degree,
                        Math.abs((int) ((ptLineWidthAnimation / ptWidthAnimation) * drag_degree) - drag_degree)),
                bg_color);

        RenderUtil.Render2D.drawRect(
                posX + 8 + ptWidthAnimation / 2 - ptLineWidthAnimation / 2,
                posY + 17.75f,
                Math.max(0, ptLineWidthAnimation - 16),
                0.5f,
                RenderUtil.reAlphaInt(t_color, (int) ((ptLineWidthAnimation / ptWidthAnimation) * 80)));

        var titleWidth = small.getWidth("Potions");

        BloomHelper.registerRenderCall(() -> {
            small.drawStringWithShadow(matrixStack, "Potions", posX + (ptWidthAnimation - titleWidth) / 2, posY + 7.5,
                    RenderUtil.reAlphaInt(getColor(100), 210)
            );
        });
        small.drawString(matrixStack, "Potions", posX + (ptWidthAnimation - titleWidth) / 2, posY + 7.5,
                ColorUtil.interpolateColor(getColor(100),
                        new Color(255, 255, 255).getRGB(), 0.5f));

        BloomHelper.registerRenderCall(() -> {
            Fonts.icons3[13].drawStringWithShadow(matrixStack, "O", posX + 6, posY + 8,
                    RenderUtil.reAlphaInt(getColor(100), 210)
            );
        });
        Fonts.icons3[13].drawString(matrixStack, "O", posX + 6, posY + 8,
                ColorUtil.interpolateColor(getColor(100),
                        new Color(255, 255, 255).getRGB(), 0.5f));

        RenderUtil.SmartScissor.push();
        RenderUtil.SmartScissor.setFromComponentCoordinates(posX, posY, ptWidthAnimation, ptHeightAnimation);

        int index = 0;

        float ptWidth = 100;
        for (EffectInstance eff : mc.player.getActivePotionEffects().stream().sorted(Comparator.comparing(EffectInstance::getDuration)).toList()) {
            if (!effects.containsKey(eff.getEffectName())) {
                effects.put(eff.getEffectName(), eff.getDuration());
            }

            if (effects.get(eff.getEffectName()) < eff.getDuration()) {
                effects.put(eff.getEffectName(), eff.getDuration());
            }

            final String text = I18n.format(eff.getEffectName()) + " " + I18n.format("enchantment.level." + (eff.getAmplifier() + 1));
            final String duration = EffectUtils.getPotionDurationString(eff, 1);
            final float textWidth = medium.getWidth(text) + medium.getWidth(duration) + 4 + 8;

            float progress = (float) eff.getDuration() / effects.get(eff.getEffectName());
            float radius = MathHelper.clamp(progress * 360, 0, 360);

            ptWidth = Math.max(textWidth + 30, ptWidth);

            medium.drawString(matrixStack, text, posX + 4, posY + 26 + (index * 10), t_color);

            final int mainColor = rgba(30, 30, 30, 255);
            int circleColor = ColorUtil.interpolateColor(
                    new Color(255, 141, 141).getRGB(),
                    new Color(169, 255, 169).getRGB(),
                    progress
            );
            drawCircle(posX + ptWidthAnimation - 4 - 4, posY + 26 + (index * 10) + 1, 0, 360, 3, 2, false, mainColor);
            drawCircle(posX + ptWidthAnimation - 4 - 4, posY + 26 + (index * 10) + 1, 0, -radius, 3, 2, false, circleColor);

            medium.drawString(matrixStack, duration, posX + ptWidthAnimation - 16 - medium.getWidth(duration), posY + 26 + (index * 10), circleColor);

            index++;
        }

        RenderUtil.SmartScissor.unset();
        RenderUtil.SmartScissor.pop();

        Set<EffectInstance> effectSet = new HashSet<>(mc.player.getActivePotionEffects());
        effects.entrySet().removeIf(entry -> effectSet.stream().noneMatch(e -> e.getEffectName().equals(entry.getKey())));

        ptHeight = 18 + (index * 10) + (index > 0 ? 8 : 0);
        ptLineWidth = index > 0 ? ptWidth : 0;

        this.ptHeightAnimation = AnimationMath.fast(this.ptHeightAnimation, ptHeight, 10);
        this.ptWidthAnimation = AnimationMath.fast(this.ptWidthAnimation, ptWidth, 10);
        this.ptLineWidthAnimation = AnimationMath.fast(this.ptLineWidthAnimation, ptLineWidth, 10);

        potions.setWidth(ptWidth);
        potions.setHeight(ptHeight);
    }

    float kbHeight = 0;
    float kbLineWidth = 0;

    float kbWidthAnimation = 0, kbHeightAnimation = 0;
    float kbLineWidthAnimation = 0;

    private void renderKeyBinds(MatrixStack matrixStack, int offset, boolean glowing) {
        float posX = keyBinds.getX();
        float posY = keyBinds.getY();

        RenderUtil.Render2D.drawRoundedCorner(posX, posY + 18 - drag_degree,
                kbWidthAnimation,
                kbHeightAnimation - 18 + drag_degree,
                new Vector4f(0, drag_degree, 0, drag_degree),
                sec_color);

        RenderUtil.Render2D.drawRoundedCorner(posX, posY,
                kbWidthAnimation,
                18,
                new Vector4f(drag_degree,
                        Math.abs((int) ((kbLineWidthAnimation / kbWidthAnimation) * drag_degree) - drag_degree),
                        drag_degree,
                        Math.abs((int) ((kbLineWidthAnimation / kbWidthAnimation) * drag_degree) - drag_degree)),
                bg_color);

        RenderUtil.Render2D.drawRect(
                posX + 8 + kbWidthAnimation / 2 - kbLineWidthAnimation / 2,
                posY + 17.75f,
                Math.max(0, kbLineWidthAnimation - 16),
                0.5f,
                RenderUtil.reAlphaInt(t_color, (int) ((kbLineWidthAnimation / kbWidthAnimation) * 80)));

        var titleWidth = small.getWidth("Keybinds");

        BloomHelper.registerRenderCall(() -> {
            small.drawStringWithShadow(matrixStack, "Keybinds", posX + (kbWidthAnimation - titleWidth) / 2, posY + 7.5,
                    RenderUtil.reAlphaInt(getColor(100), 210)
            );
        });
        small.drawString(matrixStack, "Keybinds", posX + (kbWidthAnimation - titleWidth) / 2, posY + 7.5,
                ColorUtil.interpolateColor(getColor(100),
                        new Color(255, 255, 255).getRGB(), 0.5f));

        BloomHelper.registerRenderCall(() -> {
            Fonts.icons3[13].drawStringWithShadow(matrixStack, "L", posX + 6, posY + 8,
                    RenderUtil.reAlphaInt(getColor(100), 210)
            );
        });
        Fonts.icons3[13].drawString(matrixStack, "L", posX + 6, posY + 8,
                ColorUtil.interpolateColor(getColor(100),
                        new Color(255, 255, 255).getRGB(), 0.5f));

        RenderUtil.SmartScissor.push();
        RenderUtil.SmartScissor.setFromComponentCoordinates(posX, posY, kbWidthAnimation, kbHeightAnimation);
        int i = 0;

        float kbWidth = 100;
        for (Function f : Managment.FUNCTION_MANAGER.getFunctions()) {
            if (!f.state || f.bind == 0) continue;

            String text = ClientUtil.getKey(f.bind);

            String bindText = text.toUpperCase();
            float bindWidth = medium.getWidth(bindText);
            String name = f.name;
            if (name.length() > 13) {
                name = name.substring(0, 13) + "..";
            }

            var rowLength = medium.getWidth(bindText) + medium.getWidth(bindText) + 8 + 30;
            kbWidth = Math.max(rowLength, kbWidth);

            medium.drawString(matrixStack, name, posX + cWidth + 4, posY + 26 + (i * 10), t_color);
            medium.drawString(matrixStack, bindText, posX + cWidth + kbWidthAnimation - bindWidth - 4 - 2, posY + 26 + (i * 10), t_color);

            i++;
        }
        RenderUtil.SmartScissor.unset();
        RenderUtil.SmartScissor.pop();

        kbHeight = 18 + (i * 10) + (i > 0 ? 8 : 0);
        kbLineWidth = i > 0 ? kbWidth : 0;

        this.kbHeightAnimation = AnimationMath.fast(this.kbHeightAnimation, kbHeight, 10);
        this.kbWidthAnimation = AnimationMath.fast(this.kbWidthAnimation, kbWidth, 10);
        this.kbLineWidthAnimation = AnimationMath.fast(this.kbLineWidthAnimation, kbLineWidth, 10);

        keyBinds.setWidth(kbWidth);
        keyBinds.setHeight(kbHeight);
    }

    float slHeight = 0;
    float slLineWidth = 0;

    float slWidthAnimation = 0, slHeightAnimation = 0;
    float slLineWidthAnimation = 0;

    private void renderStaffList(MatrixStack matrixStack, int offset, boolean glowing) {
        float posX = staffList.getX();
        float posY = staffList.getY();

        RenderUtil.Render2D.drawRoundedCorner(posX, posY + 18 - drag_degree,
                slWidthAnimation,
                slHeightAnimation - 18 + drag_degree,
                new Vector4f(0, drag_degree, 0, drag_degree),
                sec_color);

        RenderUtil.Render2D.drawRoundedCorner(posX, posY,
                slWidthAnimation,
                18,
                new Vector4f(drag_degree,
                        Math.abs((int) ((slLineWidthAnimation / slWidthAnimation) * drag_degree) - drag_degree),
                        drag_degree,
                        Math.abs((int) ((slLineWidthAnimation / slWidthAnimation) * drag_degree) - drag_degree)),
                bg_color);

        RenderUtil.Render2D.drawRect(
                posX + 8 + slWidthAnimation / 2 - slLineWidthAnimation / 2,
                posY + 17.75f,
                Math.max(0, slLineWidthAnimation - 16),
                0.5f,
                RenderUtil.reAlphaInt(t_color, (int) ((slLineWidthAnimation / slWidthAnimation) * 80)));

        var titleWidth = small.getWidth("Staff");

        BloomHelper.registerRenderCall(() -> {
            small.drawStringWithShadow(matrixStack, "Staff", posX + (slWidthAnimation - titleWidth) / 2, posY + 7.5,
                    RenderUtil.reAlphaInt(getColor(100), 210)
            );
        });
        small.drawString(matrixStack, "Staff", posX + (slWidthAnimation - titleWidth) / 2, posY + 7.5,
                ColorUtil.interpolateColor(getColor(100),
                        new Color(255, 255, 255).getRGB(), 0.5f));

        BloomHelper.registerRenderCall(() -> {
            Fonts.icons3[13].drawStringWithShadow(matrixStack, "S", posX + 6, posY + 8.5,
                    RenderUtil.reAlphaInt(getColor(100), 210)
            );
        });
        Fonts.icons3[13].drawString(matrixStack, "S", posX + 6, posY + 8.5,
                ColorUtil.interpolateColor(getColor(100),
                        new Color(255, 255, 255).getRGB(), 0.5f));

        RenderUtil.SmartScissor.push();
        RenderUtil.SmartScissor.setFromComponentCoordinates(posX, posY, slWidthAnimation, slHeightAnimation);
        int i = 0;

        float slWidth = 100;
        for (Map.Entry<ITextComponent, String> entry : staffPlayers.entrySet()) {
            ITextComponent p = entry.getKey();
            String n = entry.getValue();

            slWidth = Math.max(small.getWidth(p.getString() + n + 30), slWidth);

            small.drawString(matrixStack, p, posX + 4, posY + 26 + (i * 10), t_color);
            small.drawString(matrixStack, n, posX + small.getWidth(p.getString()) + 4, posY + 26 + (i * 10), t_color);

            i++;
        }

        RenderUtil.SmartScissor.unset();
        RenderUtil.SmartScissor.pop();

        slHeight = 18 + (i * 10) + (i > 0 ? 8 : 0);
        slLineWidth = i > 0 ? slWidth : 0;

        this.slHeightAnimation = AnimationMath.fast(this.slHeightAnimation, slHeight, 10);
        this.slWidthAnimation = AnimationMath.fast(this.slWidthAnimation, slWidth, 10);
        this.slLineWidthAnimation = AnimationMath.fast(this.slLineWidthAnimation, slLineWidth, 10);

        staffList.setWidth(slWidth);
        staffList.setHeight(slHeight);
    }

    float health = 0;

    private double tHudAlpha = 0.0D;

    private void renderTarget(MatrixStack matrixStack, int offset, boolean glowing) {
        float posX = targetHUD.getX();
        float posY = targetHUD.getY();

        targetHUD.setWidth(100);
        targetHUD.setHeight(32);

        this.target = getTarget(this.target);
        this.tHudAlpha = tHudAnimation.getOutput();

        if (tHudAlpha == 0.0F) {
            target = null;
        }

        if (target == null) {
            return;
        }

        this.health = AnimationMath.fast(health, target.getHealth() / target.getMaxHealth(), 5);
        this.health = MathHelper.clamp(this.health, 0, 1);
        String healthValue = (int) MathUtil.round(this.health * 20 + target.getAbsorptionAmount(), 0.5f) + "";

        GlStateManager.pushMatrix();

        List<ItemStack> stacks = new ArrayList<>(Arrays.asList(target.getHeldItemMainhand(), target.getHeldItemOffhand()));
        target.getArmorInventoryList().forEach(stacks::add);
        stacks.removeIf(w -> w.getItem() instanceof AirItem);

        if (targetFollow.get() && targetVector != null) {
            if (coordsAnimation.getOutput() > 0) {
                posX = (window.getScaledWidth() - 100) / 2f;
                posY = (window.getScaledHeight() - 32 - (stacks.isEmpty() ? 0 : 10)) / 2f;
                targetHUD.setX(posX);
                targetHUD.setY(posY);

            } else {
                posX = (float)targetVector.x;
                posY = (float)targetVector.y + 60;
            }
        }

        if (!stacks.isEmpty()) {
            RenderUtil.Render2D.drawRoundedCorner(posX, posY + 32, 100, 10,
                    new Vector4f(0, drag_degree, 0, drag_degree),
                    RenderUtil.reAlphaInt(bg_color, (int) (150 * tHudAlpha)));

            targetHUD.setHeight(32 + 10);
        }

        RenderUtil.Render2D.drawRoundedCorner(posX, posY, 100, 32,
                new Vector4f(drag_degree, stacks.isEmpty() ? drag_degree : 0, drag_degree, stacks.isEmpty() ? drag_degree : 0),
                RenderUtil.reAlphaInt(bg_color, (int) (255 * tHudAlpha)));

        drawItemStack(posX + 2, posY + 32, 10, (float) tHudAlpha);

        drawFace(posX + 4, posY + 4, 8F, 8F, 8F, 8F, 24, 24, 64F, 64F, (float) tHudAlpha, (AbstractClientPlayerEntity) target);

        RenderUtil.SmartScissor.push();
        RenderUtil.SmartScissor.setFromComponentCoordinates(posX + 32, posY + 3, 100 - 36, 10);

        String targetName = this.target.getName().getString();

        medium.drawString(matrixStack, targetName, posX + 32, posY + 6,
                RenderUtil.reAlphaInt(t_color, (int) (255 * tHudAlpha)));

        RenderUtil.Render2D.drawTransparentGradient(posX + 100 - 30 - 1, posY + 3, 30, 10,
                bg_color,
                (float) tHudAlpha);

        RenderUtil.SmartScissor.unset();
        RenderUtil.SmartScissor.pop();

        var healthLength = medium.getWidth(healthValue + "HP");
        medium.drawString(matrixStack, healthValue + "HP", posX + 32 + 64 * health - healthLength * health, posY + 20,
                RenderUtil.reAlphaInt(t_color, (int) (255 * tHudAlpha)));

        RenderUtil.SmartScissor.push();
        RenderUtil.SmartScissor.setFromComponentCoordinates(posX + 32, posY + 38 - 8 - 4, (100 - 36) * health, 1f);

        RenderUtil.Render2D.drawGradientRound(posX + 32, posY + 38 - 8 - 4, 100 - 36, 0.5f, 0.25f,
                RenderUtil.reAlphaInt(getColor(100), (int) (255 * tHudAlpha)),
                RenderUtil.reAlphaInt(getColor(100), (int) (255 * tHudAlpha)),
                RenderUtil.reAlphaInt(getColor(0), (int) (255 * tHudAlpha)),
                RenderUtil.reAlphaInt(getColor(0), (int) (255 * tHudAlpha)));

        RenderUtil.SmartScissor.unset();
        RenderUtil.SmartScissor.pop();

        GlStateManager.popMatrix();
    }

    private void drawItemStack(float x, float y, float offset, float alphaMod) {
        List<ItemStack> stacks = new ArrayList<>(Arrays.asList(target.getHeldItemMainhand(), target.getHeldItemOffhand()));
        target.getArmorInventoryList().forEach(stacks::add);
        stacks.removeIf(w -> w.getItem() instanceof AirItem);
        Collections.reverse(stacks);
        final AtomicReference<Float> posX = new AtomicReference<>(x);

        for (var stack : stacks) {
            if (stack.isEmpty()) continue;

            GlStateManager.pushMatrix();
            AnimationMath.sizeAnimation(posX.get() + 4.5, y + 4.5, alphaMod);

            HudUtil.drawItemStack(stack, posX.getAndAccumulate(offset, Float::sum), y, true, true, 0.6f);

            GlStateManager.popMatrix();
        }
    }

    private float perc;

    private void renderTimer(MatrixStack matrixStack, int offset, boolean glowing) {
        float posX = timerHUD.getX();
        float posY = timerHUD.getY();

        float quotient = Managment.FUNCTION_MANAGER.timerFunction.maxViolation / Managment.FUNCTION_MANAGER.timerFunction.timerAmount.getValue().floatValue();
        float minimumValue = Math.min(Managment.FUNCTION_MANAGER.timerFunction.getViolation(), quotient);
        perc = AnimationMath.lerp(perc, ((quotient - minimumValue) / quotient), 10);

        String text = (int) (perc * 100) + "%";
        float width = 100;
        timerHUD.setWidth(width);
        timerHUD.setHeight(36);

        RenderUtil.Render2D.drawRoundedCorner(posX, posY + 18 - drag_degree,
                width,
                36 - 18 + drag_degree,
                new Vector4f(0, drag_degree, 0, drag_degree),
                sec_color);

        RenderUtil.Render2D.drawRoundedCorner(posX, posY,
                width,
                18,
                new Vector4f(drag_degree, 0, drag_degree, 0),
                bg_color);

        var titleWidth = small.getWidth("Timer");
        var textWidth = medium.getWidth(text);

        BloomHelper.registerRenderCall(() -> {
            small.drawStringWithShadow(matrixStack, "Timer", posX + (width - titleWidth) / 2, posY + 7.5,
                    RenderUtil.reAlphaInt(getColor(100), 210)
            );
        });
        small.drawString(matrixStack, "Timer", posX + (width - titleWidth) / 2, posY + 7.5,
                ColorUtil.interpolateColor(getColor(100),
                        new Color(255, 255, 255).getRGB(), 0.5f));

        BloomHelper.registerRenderCall(() -> {
            Fonts.icons3[13].drawStringWithShadow(matrixStack, "T", posX + 6, posY + 8,
                    RenderUtil.reAlphaInt(getColor(100), 210)
            );
        });
        Fonts.icons3[13].drawString(matrixStack, "T", posX + 6, posY + 8,
                ColorUtil.interpolateColor(getColor(100),
                        new Color(255, 255, 255).getRGB(), 0.5f));

        RenderUtil.Render2D.drawRect(
                posX + 8,
                posY + 17.75f,
                width - 16,
                0.5f,
                RenderUtil.reAlphaInt(t_color, 80));

        medium.drawString(matrixStack, text, posX + 4 + 92 * perc - textWidth * perc, posY + 24,
                t_color);

        RenderUtil.SmartScissor.push();
        RenderUtil.SmartScissor.setFromComponentCoordinates(posX + 4, posY + 30, (100 - 8) * perc, 1f);

        RenderUtil.Render2D.drawGradientRound(posX + 4, posY + 30, 100 - 8, 0.5f, 0.25f,
                getColor(100),
                getColor(100),
                getColor(0),
                getColor(0));

        RenderUtil.SmartScissor.unset();
        RenderUtil.SmartScissor.pop();
    }

    private void renderCoordinates(MatrixStack matrixStack, int offset, boolean glowing) {
        var coords = (int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ();
        var bps = String.format("%.2f", Math.hypot(mc.player.getPosX() - mc.player.prevPosX, mc.player.getPosZ() - mc.player.prevPosZ) * 20);

        var width = medium.getWidth(coords) +
                medium.getWidth(bps) +
                Fonts.icons3[13].getWidth("X") +
                Fonts.icons3[13].getWidth("E") +
                4 * 5;
        var height = window.getScaledHeight() - offset - 14 - (float) (coordsAnimation.getOutput() * 14);

        final AtomicReference<Float> posX = new AtomicReference<>(offset + 4f);

        RenderUtil.Render2D.drawRoundedRect(
                offset,
                height,
                width,
                14,
                drag_degree,
                bg_color);

        Fonts.icons3[13].drawString(matrixStack,
                "X",
                posX.getAndAccumulate(Fonts.icons3[13].getWidth("X") + 4, Float::sum),
                height + 6.25,
                RenderUtil.reAlphaInt(t_color, 80));

        medium.drawString(matrixStack,
                coords,
                posX.getAndAccumulate(medium.getWidth(coords) + 4, Float::sum),
                height + 6, t_color);

        Fonts.icons3[13].drawString(matrixStack,
                "E",
                posX.getAndAccumulate(Fonts.icons3[13].getWidth("E") + 4, Float::sum),
                height + 6.25,
                RenderUtil.reAlphaInt(t_color, 80));

        medium.drawString(matrixStack,
                bps,
                posX.getAndAccumulate(medium.getWidth(bps) + 4, Float::sum),
                height + 6, t_color);
    }

    private PlayerEntity getTarget(PlayerEntity nullTarget) {
        PlayerEntity target = nullTarget;

        if (Managment.FUNCTION_MANAGER.auraFunction.getTarget() instanceof PlayerEntity) {
            target = (PlayerEntity) Managment.FUNCTION_MANAGER.auraFunction.getTarget();
            tHudAnimation.setDirection(Direction.FORWARDS);
        } else if (mc.currentScreen instanceof ChatScreen) {
            target = mc.player;
            tHudAnimation.setDirection(Direction.FORWARDS);
        } else {
            tHudAnimation.setDirection(Direction.BACKWARDS);
        }

        return target;
    }

    private void updateFunctions() {
        for (Function function : Managment.FUNCTION_MANAGER.getFunctions()) {
            if (function.category == Type.Render) continue;

            functions.add(function);
        }

        functions.sort((f1, f2) -> Float.compare(small.getWidth(f2.name), small.getWidth(f1.name)));
    }

    private int getColor(int index) {
        return colors[index % colors.length];
    }
}