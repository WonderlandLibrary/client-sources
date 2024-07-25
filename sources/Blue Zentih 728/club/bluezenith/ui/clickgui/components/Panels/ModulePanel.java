package club.bluezenith.ui.clickgui.components.Panels;

import club.bluezenith.BlueZenith;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.ClickGUI;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.*;
import club.bluezenith.ui.clickgui.components.Panel;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.MinecraftInstance;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import fr.lavache.anime.Easing;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static club.bluezenith.module.modules.render.ClickGUI.oldDropdownUI;
import static club.bluezenith.util.render.RenderUtil.*;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import static org.lwjgl.input.Keyboard.isKeyDown;
import static org.lwjgl.input.Mouse.isButtonDown;
import static org.lwjgl.opengl.GL11.glEnable;

public class ModulePanel extends club.bluezenith.ui.clickgui.components.Panel implements MouseClickedThing {
    public List<Module> modules = new ArrayList<>();
    private final MillisTimer timer = new MillisTimer();
    public final ModuleCategory category;
    private StringValue selectedTextField = null;
    private float textFieldCounter = 0;
    private boolean wasPressed = false;
    private Value<?> sliderVal = null;
    private Module lastMod = null;
    private final FontRenderer fArrow = FontUtil.ICON_fontArrow28;
    private final FontRenderer f2;
    private final FontRenderer bigCheckbox = FontUtil.ICON_testFont3;
    int index = 0;
    private List<Module> cachedModuleList = new ArrayList<>();
    
    List<Module> bindListeners = new ArrayList<>();
    public ModulePanel(float x, float y, ModuleCategory category){
        super(x, y, "Modules " + category.displayName);
        this.category = category;
        f = FontUtil.inter28;
        f2 = FontUtil.ICON_testFont2;
        mHeight = f.FONT_HEIGHT + 9;
        originalMheight = mHeight;
        amogus();
    }

    private void amogus() {
        if(this.category == ModuleCategory.RENDER) {
            final int index = this.modules.indexOf(BlueZenith.getBlueZenith().getModuleManager().getModule(HUD.class));
            if(index == -1) return;
            final Module hud = this.modules.get(index);
            final Module other = this.modules.get(0);
            this.modules.set(0, hud);
            this.modules.set(index, other);
            cachedModuleList = this.modules.stream().distinct().collect(Collectors.toList());
        }
    }
    public Panel calculateSize() {
        float pY = mHeight;
        width = f.getStringWidth(category.displayName) + 12;
        for (Module m : modules) {
            if(f.getStringWidth(m.getName()) + 12 > width){
                width = f.getStringWidth(m.getName()) + 12;
            }
            pY += mHeight;
        }
        width = Math.max(width, 120) - 10;
        height = pY;
        return this;
    }
    public List<Module> getModules(){
        return modules;
    }
    public void addModule(Module mod){
        this.modules.add(mod);
        this.cachedModuleList.add(mod);
        mod.clickGuiAnim.setReversed(true);
        amogus();
    }

    public void onSearch() {
        if(oldDropdownUI == null || oldDropdownUI.typedString == "") return;
        List<Module> list = new ArrayList<>();
        for (Module mod : cachedModuleList) {
            if (mod.getName().toLowerCase().startsWith(oldDropdownUI.typedString.toLowerCase())) {
                list.add(mod);
            }
        }
        this.modules = list;
    }

    public void onCancelSearch() {
        this.modules = cachedModuleList;
    }

    int rainbow = 0;
    public void drawPanel(int mouseX, int mouseY, float partialTicks, boolean handleClicks) {

        if(this.showContent)
        openAnimationProgress = animate(1F, openAnimationProgress, 0.15F);

        ScaledResolution sc = new ScaledResolution(mc);
        rainbow = 0;
        final double animStart = 0.9;
        final double animStatus = animStart + (click.scaleAnimation.getValue() * (1 - animStart));
        final float animStatusF = (float) animStatus;
        GlStateManager.translate(sc.getScaledWidth() / 2, sc.getScaledHeight() / 2, 0);
        GlStateManager.scale(animStatus, animStatus, 1);
        GlStateManager.translate(-sc.getScaledWidth() / 2, -sc.getScaledHeight() / 2, 0);
        click.scaleAnimation.setSpeed(0.5f);
        click.scaleAnimation.setEase(Easing.QUARTIC_OUT);
        click.scaleAnimation.update();
        final float xWidth = x + width;
        float w = width;
        float h = f.FONT_HEIGHT + 8;
        Color mainColor = click.primaryColor.get();

        Color backgroundColor = click.oldDropdownBackground;
        Color settingsColor = backgroundColor.brighter();

        float borderWidth = 1.5f;
        rect(x - borderWidth, y, xWidth + borderWidth, y + mHeight, new Color(21, 21, 21));
        f.drawString(category.displayName, x + 4, y + mHeight / 2f - f.FONT_HEIGHT / 2f, Color.WHITE.getRGB());
        float y1 = y + mHeight;
        index = 0;
        Module[] mods = modules.toArray(new Module[0]);
        for (Module m : mods) {
            if(!this.showContent)
                continue;

            if(m.getState())
            rainbow++;
            List<Value<?>> vl = m.getValues().stream().filter(Value::isVisible).collect(Collectors.toList());
            boolean hovering = i(mouseX, mouseY, x, y1, xWidth, y1 + mHeight);

            if(!click.toggleAnimation.is("None")) {
                if (m.getState()) {
                    m.toggleAlpha = RenderUtil.animate(255, m.toggleAlpha, .1f);
                    //  m.toggleAlpha = MathHelper.clamp(m.toggleAlpha, 20, 255);
                } else
                    m.toggleAlpha = RenderUtil.animate((click.toggleAnimation.is("Simple")) ? 0 : -1, m.toggleAlpha, .15f);
            } else {
                if (m.getState()) {
                    m.toggleAlpha = 255;
                    //  m.toggleAlpha = MathHelper.clamp(m.toggleAlpha, 20, 255);
                } else
                    m.toggleAlpha = 0;
            }


            if(hovering)
                m.hoverAlpha = MathHelper.clamp(m.hoverAlpha + 1.5f * delta, 0, 255);
            else m.hoverAlpha = MathHelper.clamp(m.hoverAlpha - 1.5f * delta, 0, 255);

            ifHoveringAndWasntPressed(m, vl, hovering);

            final Color darkerBG = backgroundColor.darker();
            rect(x, y1, xWidth, y1 + mHeight, backgroundColor.getRGB());
            GlStateManager.pushMatrix();
            double start = click.toggleAnimation.is("Scale") ? 1 : 0.1;
            if (m.toggleAlpha > 0 && !click.toggleAnimation.is("Simple") && !click.toggleAnimation.is("None")) {
                final double xScale = click.toggleAnimation.is("Scale") ? start + (1 - start) : 1;
                GlStateManager.translate((x + xWidth) / 2, (y1 + y1 + mHeight) / 2, 0);
                GlStateManager.scale((m.toggleAlpha / 255f) * xScale, (m.toggleAlpha / 255f) * start + (1 - start), 1);
                GlStateManager.translate(-((x + xWidth) / 2), -((y1 + y1 + mHeight) / 2), 0);
            }
            final Color colour = new Color(colour(rainbow++, click));
            rect(x, y1, xWidth, y1 + mHeight,
                    ( m.toggleAlpha > 0 ? new Color(colour.getRed()/255F, colour.getGreen()/255F, colour.getBlue()/255F,
                            click.toggleAnimation.is("Simple") ? m.toggleAlpha/255F : 1F)
                            : m.hoverAlpha > 0 ? new Color(darkerBG.getRed() / 255f, darkerBG.getGreen() / 255f, darkerBG.getBlue() / 255f, m.hoverAlpha / 255f)
                            : backgroundColor
                    ));
            GlStateManager.popMatrix();
            rect(x - borderWidth, y1, x, y1 + mHeight, backgroundColor.darker().getRGB());
            rect(xWidth, y1, xWidth + borderWidth, y1 + mHeight, backgroundColor.darker().getRGB());
            if(modules.indexOf(m) == modules.size() - 1 && !m.showSettings)
                rect(x - borderWidth, y1 + mHeight, xWidth + borderWidth, y1 + mHeight + borderWidth, backgroundColor.darker().getRGB());
            f.drawString(m.getName(), x + 5, y1 + (mHeight / 2f - f.FONT_HEIGHT / 2f), Color.white.getRGB(), true);
            drawBindString(y1, m);
            //else f.drawString(m.keyBind > 0 ? "[" + Keyboard.getKeyName(m.keyBind) + "...]" : "[...]", x + 5, y1 + (mHeight / 2f - f.FONT_HEIGHT / 2f), mainColor.getRGB());
            y1 += mHeight;
            if(canRender(m) && (timer.hasTimeReached(35) || !click.closePrevious.get())){
                float spacing = y1; // im sorry for too many variables :(
                float downOffset = 0; // lol
                if(m.arrayListHeight == 0) m.arrayListHeight = h * vl.size();
                m.clickGuiAnim.setMax(m.arrayListHeight).update();

                glEnable(GL11.GL_SCISSOR_TEST);
                final float cropProgress = 2 - animStatusF;
                crop((x - borderWidth)*(1 - cropProgress), (y1 - 1) + oldDropdownUI.scroll, (xWidth + borderWidth)*cropProgress, oldDropdownUI.scroll + (y1 + d(m.arrayListHeight, m.clickGuiAnim.getValue()) + 1 + borderWidth + borderWidth) * cropProgress);
                for (Value<?> v : vl) {
                    rect(x - borderWidth, spacing, xWidth, spacing + h + borderWidth, backgroundColor.darker().getRGB());
                    rect(xWidth, spacing, xWidth + borderWidth, spacing + h + borderWidth, backgroundColor.darker().getRGB());
                    float y2r = spacing + h;
                    float she_lied = h / 2f - f.FONT_HEIGHT / 2f;
                    if (v instanceof FontValue) {
                        drawFontValue(mouseX, mouseY, handleClicks, xWidth, h, settingsColor, spacing, (FontValue) v, she_lied);
                        spacing += h;
                        downOffset += h;
                    } else if (v instanceof ModeValue) {
                        drawModeValue(mouseX, mouseY, handleClicks, xWidth, h, settingsColor, spacing, (ModeValue) v, she_lied);
                        spacing += h;
                        downOffset += h;
                    } else if(v instanceof ExtendedModeValue) {
                        drawEModeValue(mouseX, mouseY, handleClicks, xWidth, h, settingsColor, spacing, (ExtendedModeValue) v, she_lied);
                        spacing += h;
                        downOffset += h;
                    } else if (v instanceof FloatValue) {
                        drawFloatValue(mouseX, mouseY, handleClicks, w, mainColor, spacing, v, y2r, she_lied);

                        spacing += h;
                        downOffset += h;
                    } else if (v instanceof IntegerValue) {
                        drawIntValue(mouseX, mouseY, handleClicks, w, mainColor, spacing, v, y2r, she_lied);

                        spacing += h;
                        downOffset += h;
                    } else if (v instanceof StringValue) {
                        drawStringValue(mouseX, mouseY, handleClicks, xWidth, h, settingsColor, spacing, (StringValue) v, she_lied);
                        spacing += h;
                        downOffset += h;
                    } else if (v instanceof BooleanValue) {
                        drawBoolVal(mouseX, mouseY, handleClicks, xWidth, h, mainColor, settingsColor, spacing, (BooleanValue) v, she_lied);
                        spacing += h;
                        downOffset += h;
                    } else if (v instanceof ActionValue) {
                        drawActionVal(mouseX, mouseY, handleClicks, xWidth, h, settingsColor, spacing, v, she_lied);
                        spacing += h;
                        downOffset += h;
                        //TODO: Fix this bullshit
                    } else if (v instanceof ListValue) {
                        ListValue lValue = (ListValue) v;
                        rect(x, spacing, xWidth, spacing + h, settingsColor);
                        rect(x + 5, spacing + 2, x + width - 5, spacing + h - (lValue.expanded ? 0 : 2), backgroundColor);
                        f.drawString(lValue.name, x + width / 2f - f.getStringWidth(lValue.name) / 2f, spacing + she_lied, Color.GRAY.getRGB(), true);
                        if (i(mouseX, mouseY, x, spacing, xWidth, spacing + h) && (isButtonDown(0)) && !wasPressed && handleClicks) {
                            lValue.expanded = !lValue.expanded;
                            toggleSound();
                        }
                        spacing += h;
                        downOffset += h;
                        int the = rainbow;
                        for (String i : lValue.getOptions()) {
                            if (!lValue.expanded) continue;
                            boolean free_download = lValue.getOptionState(i);
                            String z = free_download ? "F" : "D";
                            GlStateManager.disableBlend();
                            rect(x - borderWidth, spacing, x, spacing + h + 6, backgroundColor.darker().getRGB());
                            rect(xWidth, spacing, xWidth + borderWidth, spacing + h + 6, backgroundColor.darker().getRGB());
                            rect(x, spacing, xWidth, spacing + h + 6, settingsColor.getRGB());
                            rect(x + 5, spacing, x + width - 5, spacing + h, backgroundColor);
                            GlStateManager.enableBlend();
                            f2.drawString(z, x + 6 + 6, spacing + (h / 2f - f2.FONT_HEIGHT / 2f) + 1, colour(the++, click));
                            f.drawString(i, x + 10 + 5 + f2.getStringWidthF(z) + 2, spacing + she_lied + 0.5f, Color.WHITE.getRGB());
                            if (i(mouseX, mouseY, x, spacing, xWidth, spacing + h) && (isButtonDown(0)) && !wasPressed && handleClicks) {
                                toggleSound();
                                lValue.toggleOption(i);
                            }
                            spacing += h;
                            downOffset += h;
                        }
                        float ff = lValue.expanded ? 3 + borderWidth : 0;
                        spacing += ff;
                        downOffset += ff;
                    } else if (v instanceof ColorValue) {
                        final ColorValue value = (ColorValue) v;
                        rect(x, spacing, xWidth, spacing + h, settingsColor);
                        final float bounds = xWidth - x;

                        if (value.sliderState == 1) { //hue slider
                            drawHueSlider(mouseX, mouseY, handleClicks, xWidth, h, spacing, value, bounds);
                        } else if (value.sliderState == 2) { //saturation slider
                            drawSatSlider(mouseX, mouseY, handleClicks, xWidth, h, spacing, value, bounds);
                        } else if (value.sliderState == 3) {
                            drawHSBSlider(mouseX, mouseY, handleClicks, xWidth, h, spacing, value, bounds);
                        } else {
                            value.sliderState = 1;
                        }
                        f.drawString(value.name, x + width / 2 - f.getStringWidth(value.name) / 2f, spacing + she_lied, -1, true);
                        spacing += h;
                        downOffset += h;
                    }
                    if(m.getValues().indexOf(v) == m.getValues().size() - 1) {
                        rect(x, spacing, xWidth, spacing + borderWidth, backgroundColor.darker().getRGB());
                    }
                }
                m.arrayListHeight = downOffset;
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
                m.arrayListHeight = downOffset;
                y1 += d(m.arrayListHeight, m.clickGuiAnim.getValue());
            }
        }
        height = y1 - y;
        textFieldCounter = (textFieldCounter + delta * 0.003f) % 1;
        wasPressed = isButtonDown(0) || isButtonDown(1);
    }

    private void drawBindString(float y1, Module m) {
        if((isKeyDown(Keyboard.KEY_LSHIFT)) && m.keyBind > 0 && !bindListeners.contains(m)) {
            String bindString = "[" + Keyboard.getKeyName(m.keyBind) + "]";
            f.drawString(bindString, x + width - f.getStringWidthF(bindString) - 3, y1 + (mHeight / 2f - f.FONT_HEIGHT / 2f), Color.white.getRGB(), true);
        } else if(bindListeners.contains(m)) {
            String bindString = "[...]";
            f.drawString(bindString, x + width - f.getStringWidthF(bindString) - 3, y1 + (mHeight / 2f - f.FONT_HEIGHT / 2f), Color.white.getRGB(), true);
        } else fArrow.drawString(m.getValues().isEmpty() ? "" : (m.showSettings ? "v" : "^"), x + width - fArrow.getStringWidthF("v") - 3, y1 + (mHeight / 2f - f.FONT_HEIGHT / 2f) + 3, Color.white.getRGB(), true);
    }

    private void drawHSBSlider(int mouseX, int mouseY, boolean handleClicks, float xWidth, float h, float spacing, ColorValue value, float bounds) {
        for (float i = 0; i < bounds; i++) {
            final Color colour = Color.getHSBColor(value.h, value.s, i / bounds);
            //
            float[] hsb = new float[3];
            hsb = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), hsb);
            if (hsb[2] == value.b)
                value.xB = x + i;
            //
            rect(x + i, spacing, x + i + 1, spacing + h, colour);

            if (i(mouseX, mouseY, x - 1, spacing, xWidth, spacing + h) && handleClicks) {
                if (isButtonDown(0)) {
                    if (x + i == value.xB) {
                        float[] hsb1 = new float[3];
                        hsb1 = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), hsb1);
                        value.setBrightness(hsb1[2]);
                    }
                    value.xB = mouseX;
                    value.xB = MathHelper.clamp(value.xB, x + 0.5f, x + bounds - 0.5f);
                } else if (isButtonDown(1) && !wasPressed) {
                    wasPressed = true;
                    value.sliderState++;
                }
            }
            if(i == bounds - 1)
                rect(value.xB, spacing, value.xB + 0.5f, spacing + h, -1);
        }
    }

    private void drawSatSlider(int mouseX, int mouseY, boolean handleClicks, float xWidth, float h, float spacing, ColorValue value, float bounds) {
        for (float i = 0; i < bounds; i++) {
            final Color colour = Color.getHSBColor(value.h, i / bounds, value.b);
            //
            float[] hsb = new float[3];
            hsb = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), hsb);
            if (hsb[1] == value.s)
                value.xS = x + i;

            rect(x + i, spacing, x + i + 1, spacing + h, colour);

            if (i(mouseX, mouseY, x - 1, spacing, xWidth, spacing + h) && handleClicks) {
                if (isButtonDown(0)) {
                    if (x + i == value.xS) {
                        float[] hsb1 = new float[3];
                        hsb1 = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), hsb1);
                        value.setSaturation(hsb1[1]);
                    }
                    value.xS = mouseX;
                    value.xS = MathHelper.clamp(value.xS, x + 0.5f, x + bounds - 0.5f);
                } else if (isButtonDown(1) && !wasPressed) {
                    wasPressed = true;
                    value.sliderState++;
                }
            }
            if(i == bounds - 1)
                rect(value.xS, spacing, value.xS + 0.5f, spacing + h, -1);
        }
    }

    private void drawHueSlider(int mouseX, int mouseY, boolean handleClicks, float xWidth, float h, float spacing, ColorValue value, float bounds) {
        for (float i = 0; i < bounds; i++) {
            final Color colour = Color.getHSBColor(i / bounds, value.s, value.b);
            //
            float[] hsb = new float[3];
            hsb = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), hsb);
            if (hsb[0] == value.h)
                value.xH = x + i;
            //
            rect(x + i, spacing, x + i + 1, spacing + h, colour);

            if (i(mouseX, mouseY, x, spacing, xWidth, spacing + h) && handleClicks) {
                if (isButtonDown(0)) {
                    if (x + i == value.xH) {
                        float[] hsb1 = new float[3];
                        hsb1 = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), hsb1);
                        value.setHue(hsb1[0]);
                    }
                    value.xH = mouseX;
                    value.xH = MathHelper.clamp(value.xH, x, x + bounds - 0.5f);
                } else if (isButtonDown(1) && !wasPressed) {
                    wasPressed = true;
                    value.sliderState++;
                }
            }
            if(i == bounds - 1)
                rect(value.xH, spacing, value.xH + 0.5f, spacing + h, -1);
        }
    }

    private void drawActionVal(int mouseX, int mouseY, boolean handleClicks, float xWidth, float h, Color settingsColor, float spacing, Value<?> v, float she_lied) {
        boolean hovering = i(mouseX, mouseY, x, spacing, xWidth, spacing + h);
        rect(x, spacing, xWidth, spacing + h, settingsColor);
        f.drawString(v.name, x + width / 2 - f.getStringWidth(v.name) / 2f, spacing + she_lied, Color.white.getRGB(), true);
        if (hovering && (isButtonDown(0) || isButtonDown(1)) && !wasPressed && handleClicks) {
            v.next();
            MinecraftInstance.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        }
    }

    private void drawBoolVal(int mouseX, int mouseY, boolean handleClicks, float xWidth, float h, Color mainColor, Color settingsColor, float spacing, BooleanValue v, float she_lied) {
        BooleanValue bValue = v;
        rect(x, spacing, xWidth, spacing + h, settingsColor);
        if (bValue.get())
            bigCheckbox.drawString("F", x + width - 14, spacing + 4.5f, colour(rainbow, click));
        else bigCheckbox.drawString("D", x + width - 14, spacing + 4.5f, colour(rainbow, click));
        f.drawString(bValue.name, x + 5, spacing + she_lied, Color.white.getRGB(), true);
        if (i(mouseX, mouseY, x, spacing, xWidth, spacing + h) && (isButtonDown(0) || isButtonDown(1)) && !wasPressed && handleClicks) {
            bValue.next();
            MinecraftInstance.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        }
    }

    private void drawStringValue(int mouseX, int mouseY, boolean handleClicks, float xWidth, float h, Color settingsColor, float spacing, StringValue v, float she_lied) {
        StringValue val = v;
        String typingIndicator = (selectedTextField != null && selectedTextField == val && textFieldCounter > 0.5 ? "_" : "");
        rect(x, spacing, xWidth, spacing + h, settingsColor);
        if (val.get().isEmpty())
            f.drawString(val.name + "... " + typingIndicator, x + width / 2f - f.getStringWidthF(val.name + "... ") / 2f, spacing + she_lied, Color.GRAY.getRGB(), true);
        else if(val.get().length() < 25)
            f.drawString(val.get() + typingIndicator, x + width / 2f - f.getStringWidthF(val.get() + " ") / 2f, spacing + she_lied, Color.WHITE.getRGB(), true);
        else {
            final String toDraw = val.get().substring(val.get().length() - 24);
            f.drawString(toDraw + typingIndicator, x + width / 2f - f.getStringWidthF(toDraw + " ") / 2f, spacing + she_lied, Color.WHITE.getRGB(), true);

        }
        if (i(mouseX, mouseY, x, spacing, xWidth, spacing + h) && (isButtonDown(0)) && !wasPressed && handleClicks) {
            this.selectedTextField = val;
            toggleSound();
        } else if (this.selectedTextField != null && isButtonDown(0) && !i(mouseX, mouseY, x, y, xWidth, y + 10) && handleClicks && !wasPressed) {
            this.selectedTextField = null;
            wasPressed = true;
        }
    }

    private void drawIntValue(int mouseX, int mouseY, boolean handleClicks, float w, Color mainColor, float spacing, Value<?> v, float y2r, float she_lied) {
        float a;
        IntegerValue intValue = (IntegerValue) v;
        a = x + w * (Math.max(intValue.min, Math.min(intValue.get(), intValue.max)) - intValue.min) / (intValue.max - intValue.min);
        final float overallProgress = abs(a/intValue.animProgress);
        final boolean back = a < intValue.animProgress;
        intValue.animProgress = click.animateSliders.get() ? intValue.animateProgress(a, overallProgress/(back ? 5.5F : 9F)) : a;
        rect(x, spacing, x + w, y2r, new Color(63, 65, 68, mainColor.getAlpha()));
        rect(x, spacing, intValue.animProgress, y2r, colour(rainbow, click));
        f.drawString(intValue.name + ": " + intValue.get(), x + 4, spacing + she_lied, Color.white.getRGB(), true);

        if (isButtonDown(0) && handleClicks && ((i(mouseX, mouseY, x, spacing, x + w, y2r) && sliderVal == null) || sliderVal == v)) {
            sliderVal = v;
            double i = MathHelper.clamp(((double) mouseX - (double) x) / ((double) w - 3), 0, 1);

            BigDecimal bigDecimal = new BigDecimal(Double.toString((intValue.min + (intValue.max - intValue.min) * i)));
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            intValue.set((int) increment(bigDecimal.floatValue(), intValue.increment, 0));
            intValue.needAnimation = true;
        } else if (!isButtonDown(0) && sliderVal == v) {
            sliderVal = null;
        }
    }

    private void drawFloatValue(int mouseX, int mouseY, boolean handleClicks, float w, Color mainColor, float spacing, Value<?> v, float y2r, float she_lied) {
        FloatValue floatValue = (FloatValue) v;
        float a = x + w * (Math.max(floatValue.min, Math.min(floatValue.get(), floatValue.max)) - floatValue.min) / (floatValue.max - floatValue.min);
        //RenderUtil.rect(x, spacing, xWidth, spacing + h, settingsColor);
        rect(x, spacing, x + w, y2r, new Color(63, 65, 68, mainColor.getAlpha()));
        final float overallProgress = abs(a/floatValue.animProgress);
        final boolean back = a < floatValue.animProgress;
        floatValue.animProgress = click.animateSliders.get() ? floatValue.animateProgress(a, overallProgress/(back ? 5.5F : 9F))  : a;
        rect(x, spacing, floatValue.animProgress, y2r, colour(rainbow, click));
        f.drawString(floatValue.name + ": " + floatValue.get(), x + 4, spacing + she_lied, Color.white.getRGB(), true);

        if (isButtonDown(0) && handleClicks && ((i(mouseX, mouseY, x, spacing, x + w, y2r) && sliderVal == null) || sliderVal == v)) {
            sliderVal = v;
            double i = MathHelper.clamp(((double) mouseX - (double) x) / ((double) w - 3), 0, 1);

            BigDecimal bigDecimal = new BigDecimal(Double.toString((floatValue.min + (floatValue.max - floatValue.min) * i)));
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            floatValue.set(increment(bigDecimal.floatValue(), floatValue.increment, 2));
            floatValue.needAnimation = true;
        } else if (!isButtonDown(0) && sliderVal == v) {
            sliderVal = null;
        }
    }

    private void drawModeValue(int mouseX, int mouseY, boolean handleClicks, float xWidth, float h, Color settingsColor, float spacing, ModeValue v, float she_lied) {
        ModeValue modeValue = v;
        rect(x, spacing, xWidth, spacing + h, settingsColor);
        f.drawString(modeValue.name, x + 5, spacing + she_lied, Color.white.getRGB(), true);
        f.drawString(modeValue.get(), x + width - f.getStringWidth(modeValue.get()) - 5, spacing + she_lied, Color.GRAY.getRGB(), true);
        if (i(mouseX, mouseY, x, spacing, xWidth, spacing + h) && !wasPressed && handleClicks) {
            if (isButtonDown(0)) {
                modeValue.next();
                toggleSound();
            } else if (isButtonDown(1)) {
                modeValue.previous();
                toggleSound();
            }
        }
    }

    private void drawEModeValue(int mouseX, int mouseY, boolean handleClicks, float xWidth, float h, Color settingsColor, float spacing, ExtendedModeValue v, float she_lied) {
        rect(x, spacing, xWidth, spacing + h, settingsColor);
        f.drawString(v.name, x + 5, spacing + she_lied, Color.white.getRGB(), true);
        f.drawString(v.get().getKey(), x + width - f.getStringWidth(v.get().getKey()) - 5, spacing + she_lied, Color.GRAY.getRGB(), true);
        if (i(mouseX, mouseY, x, spacing, xWidth, spacing + h) && !wasPressed && handleClicks) {
            if (isButtonDown(0)) {
                v.next();
                toggleSound();
            } else if (isButtonDown(1)) {
                v.previous();
                toggleSound();
            }
        }
    }

    private void drawFontValue(int mouseX, int mouseY, boolean handleClicks, float xWidth, float h, Color settingsColor, float spacing, FontValue v, float she_lied) {
        FontValue fontValue = v;
        rect(x, spacing, xWidth, spacing + h, settingsColor);
        f.drawString(fontValue.name, x + 5, spacing + she_lied, Color.white.getRGB(), true);
        f.drawString(fontValue.get().getName(), x + width - f.getStringWidth(fontValue.get().getName()) - 5, spacing + she_lied, Color.GRAY.getRGB(), true);
        if (i(mouseX, mouseY, x, spacing, xWidth, spacing + h) && !wasPressed && handleClicks) {
            if (isButtonDown(0)) {
                fontValue.next();
                toggleSound();
            }
            if (isButtonDown(1)) {
                fontValue.previous();
                toggleSound();
            }
        }
    }

    private void ifHoveringAndWasntPressed(Module m, List<Value<?>> vl, boolean hovering) {
        if(hovering && !wasPressed) {
            if(isButtonDown(2)) {
                if(!bindListeners.contains(m)) {
                    bindListeners.add(m);
                    oldDropdownUI.listeningForKey = true;
                }
            }
            if(isButtonDown(0)) {
                m.toggle();
                toggleSound();
            }
            if(isButtonDown(1) && !vl.isEmpty() && !bindListeners.contains(m)){
                if(lastMod != null && lastMod != m && click.closePrevious.get()) {
                    lastMod.clickGuiAnim.setReversed(true);
                    lastMod.showSettings = false;
                }
                m.showSettings = !m.showSettings;
                m.clickGuiAnim.setReversed(!m.showSettings);
                toggleSound();
                wasPressed = true;
                lastMod = m;
            }
        }
    }

    private float d(float h, float value){
        return value;
    }
    private boolean canRender(Module m){
        return m.clickGuiAnim.isReversed() ? m.clickGuiAnim.getValue() > m.clickGuiAnim.getMin() : m.clickGuiAnim.getValue() <= m.clickGuiAnim.getMax();
    }

    public void keyTyped(char charTyped, int keyCode){
        if(!bindListeners.isEmpty() && !isButtonDown(2) && keyCode != 28 && keyCode != 1 && keyCode != 54 && keyCode != 42) {
            for (Module k : bindListeners) {
                k.setKeybind(keyCode);
            }
            oldDropdownUI.listeningForKey = false;
            bindListeners.clear();
        }
        Keyboard.enableRepeatEvents(true);
        if(selectedTextField == null || selectedTextField.get() == null){
            if (keyCode == 1 && selectedTextField == null) {
                MinecraftInstance.mc.displayGuiScreen(null);

                if (MinecraftInstance.mc.currentScreen == null) {
                    MinecraftInstance.mc.setIngameFocus();
                }
            }
            return;
        }
        String fieldText = selectedTextField.get();
        if(keyCode == 14) {
            selectedTextField.set(fieldText.substring(0, fieldText.length() > 0 ? fieldText.length() - 1 : 0));
        }else if(keyCode == 28 || keyCode == Keyboard.KEY_ESCAPE){
            selectedTextField = null;
        }else if(!Character.isISOControl(charTyped)){
            selectedTextField.set(fieldText + charTyped);
        }
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void onClick(int button) {
        if(!bindListeners.isEmpty())
            switch (button) {
                case 0:
                    bindListeners.clear();
                    oldDropdownUI.listeningForKey = false;
                    break;

                case 1:
                    for (Module mod : bindListeners) {
                        if (mod.keyBind > 0)
                            mod.setKeybind(0);
                    }
                    bindListeners.clear();
                    oldDropdownUI.listeningForKey = false;
                    break;
        }
    }

    float increment(float value, float increment, int decimals) {
        final double x = round(value / increment) * increment;
        return BigDecimal.valueOf(x).setScale(decimals, RoundingMode.HALF_DOWN).floatValue();
    }

    int colour(int index, ClickGUI ui) {
        switch (ui.colorMode.get()) {
            case "Custom":
                return ui.primaryColor.getRGB();
            case "Match HUD":
                if(ui.colorType.is("Static"))
                    return HUD.module.getColor(1);
                else return HUD.module.getColor(index);
            case "Rainbow":
                if(ui.colorType.is("Static"))
                    return ColorUtil.rainbow(1, 0.2F, ui.primaryColor.s, ui.primaryColor.b).getRGB();
                else return ColorUtil.rainbow(index, 0.2F, ui.primaryColor.s, ui.primaryColor.b).getRGB();

            case "Gradient":
                final Color color = ui.primaryColor.get();
                final int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
                final Color color2 = ui.secondaryColor.get();
                final int r2 = color2.getRed(), g2 = color2.getGreen(), b2 = color2.getBlue();
                if(ui.colorType.is("Static")) {
                    return ColorUtil.pulse(1, r, g, b, r2, g2, b2);
                }
                else return ColorUtil.pulse(index, r, g, b, r2, g2, b2);
        }
        return -1;
    }
}
