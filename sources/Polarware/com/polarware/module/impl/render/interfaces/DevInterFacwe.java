package com.polarware.module.impl.render.interfaces;

import com.polarware.Client;
import com.polarware.component.impl.render.ParticleComponent;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PostStrafeEvent;
import com.polarware.event.impl.other.KillEvent;
import com.polarware.event.impl.other.TickEvent;
import com.polarware.event.impl.render.Render2DEvent;
import com.polarware.module.impl.render.InterfaceModule;
import com.polarware.module.impl.render.interfaces.component.ModuleComponent;
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.font.Font;
import com.polarware.util.font.FontManager;
import com.polarware.util.math.MathUtil;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.render.ColorUtil;
import com.polarware.util.render.RenderUtil;
import com.polarware.util.render.particle.Particle;
import com.polarware.util.vector.Vector2d;
import com.polarware.util.vector.Vector2f;
import com.polarware.value.Mode;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.NumberValue;
import com.polarware.value.impl.SubMode;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import util.time.StopWatch;

import java.awt.*;

public class DevInterFacwe extends Mode<InterfaceModule> {

    private final Font productSansMedium36 = FontManager.getProductSansMedium(22);
    private final Font productSansRegular = FontManager.getProductSansMedium(20);
    private final Font minecraft = FontManager.getMinecraft();
    private static ResourceLocation CLOSE_RESOURCE = null;
    private static ResourceLocation CLOSE_RESOURCE2 = null;

    private Font arrayListFont = productSansRegular;
    private final StopWatch stopWatch = new StopWatch();
    private final StopWatch frames = new StopWatch();
    private int cum = 1;

    private final ModeValue colorMode = new ModeValue("ArrayList Color Mode", this) {{
        add(new SubMode("Static"));
        add(new SubMode("Fade"));
        add(new SubMode("Breathe"));
        add(new SubMode("Rainbow"));
        setDefault("Fade");
    }};

    private final ModeValue font = new ModeValue("ArrayList Font", this) {{
        add(new SubMode("Product Sans"));
        add(new SubMode("Minecraft"));
        setDefault("Product Sans");
    }};

    private final ModeValue shader = new ModeValue("Shader Effect", this) {{
        add(new SubMode("Glow"));
        add(new SubMode("Shadow"));
        add(new SubMode("None"));
        setDefault("Shadow");
    }};
    private final ModeValue hudmpd = new ModeValue("WaterMark Mode", this) {{
        add(new SubMode("Normal"));
        add(new SubMode("Exhibition 1"));
        add(new SubMode("Exhibition 2"));
        add(new SubMode("Virtue"));
        add(new SubMode("Polar"));
        add(new SubMode("Polar 2"));
        setDefault("Normal");
    }};
    private final NumberValue polarspeed = new NumberValue("Polar Speed", this, 100, 1, 300, 1);
    private final BooleanValue polar2shadow = new BooleanValue("Draw Polar 2 shadow(fps Killer 3000)", this, true);
    private final BooleanValue dropShadow = new BooleanValue("Drop Shadow", this, true);
    private final BooleanValue sidebar = new BooleanValue("Sidebar", this, true);
    private final BooleanValue particles = new BooleanValue("Particles on Kill", this, true);
    private final ModeValue background = new ModeValue("BackGround", this) {{
        add(new SubMode("Off"));
        add(new SubMode("Normal"));
        add(new SubMode("Blur"));
        setDefault("Normal");
    }};
    private String speed = "";

    private boolean glow, shadow;
    private boolean normalBackGround, blurBackGround;
    private String username, coordinates;
    private Color logoColor;

    public DevInterFacwe(String name, InterfaceModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {
        speed = MathUtil.round((MoveUtil.speed() * 20) * mc.timer.timerSpeed, 1) + "";
    };

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        if (mc == null || mc.gameSettings.showDebugInfo || mc.theWorld == null || mc.thePlayer == null) {
            return;
        }
        //1.5
        this.getParent().setModuleSpacing((this.arrayListFont == minecraft ? 1.5F : 0.0F) + this.arrayListFont.height());
        this.getParent().setWidthComparator(this.arrayListFont);
        this.getParent().setEdgeOffset(4);


        for (final ModuleComponent moduleComponent : this.getParent().getActiveModuleComponents()) {
            //moduleComponent.setAnimationTime(5);
            if (moduleComponent.animationTime == 0) {
                continue;
            }



            String name = (this.getParent().lowercase.getValue() ? moduleComponent.getTranslatedName().toLowerCase() : moduleComponent.getTranslatedName()).replace(getParent().getRemoveSpaces().getValue() ? " " : "", "");

            String tag = (this.getParent().lowercase.getValue() ? moduleComponent.getTag().toLowerCase() : moduleComponent.getTag())
                    .replace(getParent().getRemoveSpaces().getValue() ? " " : "", "");

            final double x = moduleComponent.getPosition().getX();
            final double y = moduleComponent.getPosition().getY();
            final Color finalColor = moduleComponent.getColor();

            final double widthOffset = arrayListFont == minecraft ? 3.5 : 2;

            if (this.normalBackGround || this.blurBackGround) {
                Runnable backgroundRunnable = () ->
                        RenderUtil.rectangle(x + 0.5 - widthOffset, y - 2.5,
                                (moduleComponent.nameWidth + moduleComponent.tagWidth) + 2 + widthOffset,
                                this.getParent().moduleSpacing, getTheme().getBackgroundShade());

                if (this.normalBackGround) {
                    NORMAL_RENDER_RUNNABLES.add(backgroundRunnable);
                }

                if (this.blurBackGround) {
                    NORMAL_BLUR_RUNNABLES.add(() ->
                            RenderUtil.rectangle(x + 0.5 - widthOffset, y - 2.5,
                                    (moduleComponent.nameWidth + moduleComponent.tagWidth) + 2 + widthOffset,
                                    this.getParent().moduleSpacing, Color.BLACK));
                }

                // Draw the glow/shadow around the module
                NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
                    if (glow || shadow) {
                        RenderUtil.rectangle(x + 0.5 - widthOffset, y - 2.5,
                                (moduleComponent.nameWidth + moduleComponent.tagWidth) + 2 + widthOffset,
                               this.getParent().moduleSpacing, glow ? ColorUtil.withAlpha(finalColor, 164) : getTheme().getDropShadow());
                    }
                });
            }

            final boolean hasTag = !moduleComponent.getTag().isEmpty() && this.getParent().suffix.getValue();

            Runnable textRunnable = () -> {
                if (dropShadow.getValue()) {
                    arrayListFont.drawStringWithShadow(name, x, y, finalColor.getRGB());

                    if (hasTag) {
                        arrayListFont.drawStringWithShadow(tag, x + moduleComponent.getNameWidth() + 3, y, 0xFFCCCCCC);
                    }
                } else {
                    arrayListFont.drawString(name, x, y, finalColor.getRGB());

                    if (hasTag) {
                        arrayListFont.drawString(tag, x + moduleComponent.getNameWidth() + 3, y, 0xFFCCCCCC);
                    }
                }
            };

            Runnable shadowRunnable = () -> {
                arrayListFont.drawString(name, x, y, Color.BLACK.getRGB());

                if (hasTag) {
                    arrayListFont.drawString(tag, x + moduleComponent.getNameWidth() + 3, y, Color.BLACK.getRGB());
                }
            };

            NORMAL_RENDER_RUNNABLES.add(textRunnable);

            if (glow) {
                NORMAL_POST_BLOOM_RUNNABLES.add(textRunnable);
            } else if (shadow) {
                NORMAL_POST_BLOOM_RUNNABLES.add(shadowRunnable);
            }
            if (this.sidebar.getValue()) {
                Runnable runnable = () -> RenderUtil.roundedRectangle(x + moduleComponent.getNameWidth() + moduleComponent.getTagWidth() + 2, y -2,
                        2, 14, 1, finalColor);

                runnable.run();
                NORMAL_POST_BLOOM_RUNNABLES.add(runnable);
            }
        }

        if (coordinates == null || username == null) return;

        NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
            if (!stopWatch.finished(2000)) {
                ParticleComponent.render();
            }
        });

        switch (hudmpd.getValue().getName()) {
            case "Exhibition 1":
                this.mc.fontRendererObj.drawStringWithShadow("P" + EnumChatFormatting.GRAY + "olarware " + "[" + EnumChatFormatting.WHITE + Minecraft.getDebugFPS() + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.GRAY + "[" + EnumChatFormatting.WHITE + speed + EnumChatFormatting.GRAY + "]", 6, 6, logoColor.getRGB());

                NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
                    this.mc.fontRendererObj.drawStringWithShadow("P" + EnumChatFormatting.GRAY + "olarware " + "[" + EnumChatFormatting.WHITE + Minecraft.getDebugFPS() + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.GRAY + "[" + EnumChatFormatting.WHITE + speed + EnumChatFormatting.GRAY + "]", 6, 6, logoColor.getRGB());
                });
                break;
            case "Exhibition 2":
                this.mc.fontRendererObj.drawStringWithShadow("P" + EnumChatFormatting.GRAY + "olarware V" + Client.VERSION, 6, 6, logoColor.getRGB());

                NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
                    this.mc.fontRendererObj.drawStringWithShadow("P" + EnumChatFormatting.GRAY + "olarware V" + Client.VERSION, 6, 6, logoColor.getRGB());
                });
                break;
            case "Virtue":
                this.mc.fontRendererObj.drawStringWithShadow("Virtue", 3, 3,Color.LIGHT_GRAY.getRGB());

                NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
                    this.mc.fontRendererObj.drawStringWithShadow("Virtue", 3, 3, Color.LIGHT_GRAY.getRGB());
                });
                break;
            case "Polar":
                if(frames.finished(polarspeed.getValue().longValue())) {
                    cum++;
                    if (cum >= 38) {
                        cum = 1;
                    }
                    CLOSE_RESOURCE = new ResourceLocation("rise/frames/dance" + cum + ".png");
                    //ChatUtil.display(cum);
                    frames.reset();
                } else {

                }
                RenderUtil.image(CLOSE_RESOURCE, 10,10, 60, 75, ColorUtil.withAlpha(Color.WHITE, 255));
                break;
            case "Polar 2":
                if(frames.finished(polarspeed.getValue().longValue())) {
                    cum++;
                    if (cum >= 38) {
                        cum = 1;
                    }
                    CLOSE_RESOURCE = new ResourceLocation("rise/dance2/frame_" + cum + "_delay-0.05s.png");
                    CLOSE_RESOURCE2 = new ResourceLocation("rise/dance3/frame_" + cum + "_delay-0.05s.png");
                   // ChatUtil.display(cum);
                    frames.reset();
                }
                double factor = this.getTheme().getBlendFactor(new Vector2d(0, 0));
                RenderUtil.image(CLOSE_RESOURCE, 10,10, 60, 75, ColorUtil.withAlpha( ColorUtil.mixColors(this.getTheme().getFirstColor(), this.getTheme().getSecondColor(), factor), 255));
                RenderUtil.image(CLOSE_RESOURCE2, 10,10, 60, 75, ColorUtil.withAlpha(Color.WHITE, 255));
                NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
                    if(polar2shadow.getValue()) {
                        if (frames.finished(polarspeed.getValue().longValue())) {
                            cum++;
                            if (cum >= 38) {
                                cum = 1;
                            }
                            CLOSE_RESOURCE = new ResourceLocation("rise/dance2/frame_" + cum + "_delay-0.05s.png");
                            CLOSE_RESOURCE2 = new ResourceLocation("rise/dance3/frame_" + cum + "_delay-0.05s.png");
                            // ChatUtil.display(cum);
                            frames.reset();
                        }
                        RenderUtil.image(CLOSE_RESOURCE, 10, 10, 60, 75, ColorUtil.withAlpha(ColorUtil.mixColors(this.getTheme().getFirstColor(), this.getTheme().getSecondColor(), factor), 255));
                        RenderUtil.image(CLOSE_RESOURCE2, 10, 10, 60, 75, ColorUtil.withAlpha(Color.WHITE, 255));
                    }
                });
                break;
            case "Normal":
                float charX = 6.0F;
                for (char i : (Client.NAME + " " + Client.VERSION).toCharArray()) {
                    String string = String.valueOf(i);

                    mc.fontRendererObj.drawStringWithShadow(
                            string,
                            charX, 5,
                            this.getTheme().getAccentColor(new Vector2d(charX * 32, 6)).getRGB()
                    );

                    float finalCharX = charX;
                    NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
                                this.mc.fontRendererObj.drawStringWithShadow(Client.NAME, 6, 6, this.getTheme().getAccentColor(new Vector2d(finalCharX * 32, 11.25F)).getRGB());
                            });
                    charX += this.mc.fontRendererObj.width(string) + 0.25F;
                }
            break;
        }

        if (mc.thePlayer.ticksExisted % 150 == 0) {
            stopWatch.reset();
        }

    };

    @EventLink()
    public final Listener<KillEvent> onKill = event -> {
        if (!stopWatch.finished(2000) && this.particles.getValue()) {
            for (int i = 0; i <= 10; i++) {
                ParticleComponent.add(new Particle(new Vector2f(0, 0),
                        new Vector2f((float) Math.random(), (float) Math.random())));
            }
        }

        stopWatch.reset();
    };

    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        threadPool.execute(() -> {
            glow = this.shader.getValue().getName().equals("Glow");
            shadow = this.shader.getValue().getName().equals("Shadow");
            arrayListFont = this.font.getValue().getName().equals("Minecraft") ? minecraft : productSansRegular;

            username = mc.getSession() == null || mc.getSession().getUsername() == null ? "null" : mc.getSession().getUsername();
            coordinates = (int) mc.thePlayer.posX + ", " + (int) mc.thePlayer.posY + ", " + (int) mc.thePlayer.posZ;

            logoColor = this.getTheme().getFirstColor();

            normalBackGround = background.getValue().getName().equals("Normal");
            blurBackGround = normalBackGround || background.getValue().getName().equals("Blur");

            // modules in the top right corner of the screen
            for (final ModuleComponent moduleComponent : this.getParent().getActiveModuleComponents()) {
                if (moduleComponent.animationTime == 0) {
                    continue;
                }
                final boolean hasTag = !moduleComponent.getTag().isEmpty() && this.getParent().suffix.getValue();

                String name = (this.getParent().lowercase.getValue() ? moduleComponent.getTranslatedName().toLowerCase() : moduleComponent.getTranslatedName()).replace(getParent().getRemoveSpaces().getValue() ? " " : "", "");

                String tag = (this.getParent().lowercase.getValue() ? moduleComponent.getTag().toLowerCase() : moduleComponent.getTag())
                        .replace(getParent().getRemoveSpaces().getValue() ? " " : "", "");

                Color color = this.getTheme().getFirstColor();
                switch (this.colorMode.getValue().getName()) {
                    case "Breathe": {
                        double factor = this.getTheme().getBlendFactor(new Vector2d(0, 0));
                        color = ColorUtil.mixColors(this.getTheme().getFirstColor(), this.getTheme().getSecondColor(), factor);
                        break;
                    }
                    case "Fade": {
                        color = this.getTheme().getAccentColor(new Vector2d(0, moduleComponent.getPosition().getY()));
                        break;
                    }
                    case "Rainbow": {
                        color = ColorUtil.rainbow((int)moduleComponent.getPosition().getY() * 20, 0.5f, 3.5);
                        break;
                    }
                }
                moduleComponent.setColor(color);
                moduleComponent.setNameWidth(arrayListFont.width(name));
                moduleComponent.setTagWidth(hasTag ? (arrayListFont.width(tag) + 4) : 0);
            }
        });
    };
}
