// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import xyz.niggfaclient.utils.shader.BloomUtil;
import xyz.niggfaclient.utils.shader.GaussianBlur;
import xyz.niggfaclient.utils.shader.StencilUtil;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import xyz.niggfaclient.utils.other.MathUtils;
import net.minecraft.entity.player.EntityPlayer;
import xyz.niggfaclient.utils.render.RenderUtils;
import java.util.Calendar;
import xyz.niggfaclient.utils.other.ServerUtils;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.ScorePlayerTeam;
import xyz.niggfaclient.font.Fonts;
import com.google.common.collect.Lists;
import com.google.common.collect.Iterables;
import net.minecraft.scoreboard.Score;
import com.google.common.base.Predicate;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.events.impl.ScoreboardEvent;
import net.minecraft.client.gui.ScaledResolution;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.ShaderEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.utils.other.TimerUtil;
import net.minecraft.client.shader.Framebuffer;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Blur", description = "Blurs interfaces", cat = Category.RENDER)
public class Blur extends Module
{
    public static Property<Boolean> blur;
    public static DoubleProperty radius;
    public static Property<Boolean> shadow;
    private final DoubleProperty shadowRadius;
    private final DoubleProperty shadowOffset;
    public static Property<Boolean> showElements;
    public static Property<Boolean> scoreboard;
    public static Property<Boolean> targethud;
    public static Property<Boolean> sessionStats;
    public static Property<Boolean> hotbar;
    public static Property<Boolean> hud;
    private Framebuffer framebuffer;
    public TimerUtil timer;
    public float hotbarX;
    @EventLink
    private final Listener<ShaderEvent> shaderEventListener;
    
    public Blur() {
        this.shadowRadius = new DoubleProperty("Radius", 7.0, 1.0, 20.0, 1.0, Blur.shadow::getValue);
        this.shadowOffset = new DoubleProperty("Offset", 2.0, 1.0, 15.0, 1.0, Blur.shadow::getValue);
        this.framebuffer = new Framebuffer(1, 1, false);
        this.timer = new TimerUtil();
        final ScaledResolution sr;
        ScoreboardEvent scoreboardEvent;
        Scoreboard scoreboard;
        ScoreObjective scoreobjective;
        ScorePlayerTeam scoreplayerteam;
        int j1;
        ScoreObjective scoreobjective2;
        CustomMinecraft visuals;
        boolean scoreboardFont;
        Scoreboard scoreboard2;
        Collection collection;
        ArrayList arraylist;
        ArrayList arraylist2;
        int i;
        final Iterator<Object> iterator;
        Object score;
        ScorePlayerTeam scoreplayerteam2;
        String s;
        int j2;
        int k1;
        int b0;
        int l;
        int m;
        final Iterator<Object> iterator2;
        Object score2;
        ScorePlayerTeam scoreplayerteam3;
        String s2;
        int l2;
        int i2;
        String s3;
        int width;
        this.shaderEventListener = (e -> {
            sr = new ScaledResolution(this.mc);
            if (Blur.scoreboard.getValue()) {
                scoreboardEvent = new ScoreboardEvent();
                Client.getInstance().getEventBus().post(scoreboardEvent);
                scoreboard = this.mc.theWorld.getScoreboard();
                scoreobjective = null;
                scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());
                if (scoreplayerteam != null) {
                    j1 = scoreplayerteam.getChatFormat().getColorIndex();
                    if (j1 >= 0) {
                        scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + j1);
                    }
                }
                scoreobjective2 = ((scoreobjective != null) ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1));
                if (scoreobjective2 != null) {
                    visuals = ModuleManager.getModule(CustomMinecraft.class);
                    scoreboardFont = (visuals.isEnabled() && visuals.scoreboardFont.getValue());
                    scoreboard2 = scoreobjective2.getScoreboard();
                    collection = scoreboard2.getSortedScores(scoreobjective2);
                    arraylist = Lists.newArrayList((Iterable<?>)Iterables.filter((Iterable<? extends E>)collection, new Predicate() {
                        public boolean apply(final Score p_apply_1_) {
                            return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
                        }
                        
                        @Override
                        public boolean apply(final Object p_apply_1_) {
                            return this.apply((Score)p_apply_1_);
                        }
                    }));
                    if (arraylist.size() > 15) {
                        arraylist2 = Lists.newArrayList((Iterable<?>)Iterables.skip((Iterable<? extends E>)arraylist, collection.size() - 15));
                    }
                    else {
                        arraylist2 = arraylist;
                    }
                    i = (scoreboardFont ? Fonts.sf21.getStringWidth(scoreobjective2.getDisplayName()) : this.mc.fontRendererObj.getStringWidth(scoreobjective2.getDisplayName()));
                    arraylist2.iterator();
                    while (iterator.hasNext()) {
                        score = iterator.next();
                        scoreplayerteam2 = scoreboard2.getPlayersTeam(((Score)score).getPlayerName());
                        s = ScorePlayerTeam.formatPlayerName(scoreplayerteam2, ((Score)score).getPlayerName()) + ": " + EnumChatFormatting.RED + ((Score)score).getScorePoints();
                        i = Math.max(i, scoreboardFont ? Fonts.sf21.getStringWidth(s) : this.mc.fontRendererObj.getStringWidth(s));
                    }
                    j2 = arraylist2.size() * (scoreboardFont ? Fonts.sf21.getHeight() : this.mc.fontRendererObj.FONT_HEIGHT);
                    k1 = sr.getScaledHeight() / 2 + j2 / 3;
                    b0 = 3;
                    l = sr.getScaledWidth() - i - b0;
                    m = 0;
                    arraylist2.iterator();
                    while (iterator2.hasNext()) {
                        score2 = iterator2.next();
                        ++m;
                        scoreplayerteam3 = scoreboard2.getPlayersTeam(((Score)score2).getPlayerName());
                        s2 = ScorePlayerTeam.formatPlayerName(scoreplayerteam3, ((Score)score2).getPlayerName());
                        l2 = k1 - m * this.mc.fontRendererObj.FONT_HEIGHT;
                        i2 = sr.getScaledWidth() - b0 + 2;
                        Gui.drawRect(l - 2, l2, i2, l2 + this.mc.fontRendererObj.FONT_HEIGHT, Color.BLACK);
                        if (!ServerUtils.isOnHypixel() || !s2.startsWith("§7") || !s2.contains(String.valueOf(Calendar.getInstance().get(1)).substring(2)) || !s2.contains("§8")) {
                            if (scoreboardFont) {
                                if (visuals.isEnabled() && visuals.betterScoreboardText.getValue() && m == 1) {
                                    RenderUtils.drawWaveString("solaris.cc", (float)l, (float)l2, true);
                                }
                                else {
                                    Fonts.sf21.drawString(s2, (float)l, (float)l2, 553648127);
                                }
                            }
                            else if (visuals.isEnabled() && visuals.betterScoreboardText.getValue() && m == 1) {
                                RenderUtils.drawWaveString("solaris.cc", (float)l, (float)l2, false);
                            }
                            else {
                                this.mc.fontRendererObj.drawString(s2, (float)l, (float)l2, 553648127);
                            }
                        }
                        if (m == arraylist2.size()) {
                            s3 = scoreobjective2.getDisplayName();
                            Gui.drawRect(l - 2, l2 - this.mc.fontRendererObj.FONT_HEIGHT - 1, i2, l2 - 1, Color.BLACK);
                            Gui.drawRect(l - 2, l2 - 1, i2, l2, Color.BLACK);
                            if (scoreboardFont) {
                                Fonts.sf21.drawString(s3, l + i / 2.0f - Fonts.sf21.getStringWidth(s3) / 2.0f, (float)(l2 - Fonts.sf21.getHeight()), 553648127);
                            }
                            else {
                                this.mc.fontRendererObj.drawString(s3, l + i / 2.0f - this.mc.fontRendererObj.getStringWidth(s3) / 2.0f, (float)(l2 - this.mc.fontRendererObj.FONT_HEIGHT), 553648127);
                            }
                        }
                    }
                }
                scoreboardEvent.setPost();
                Client.getInstance().getEventBus().post(scoreboardEvent);
            }
            if (Blur.hotbar.getValue() && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
                width = sr.getScaledWidth() / 2;
                if (this.timer.hasElapsed(8L)) {
                    this.timer.reset();
                    this.hotbarX = MathUtils.lerp(this.hotbarX, (float)(width - 91 - 1 + this.mc.thePlayer.inventory.currentItem * 20), 0.25f);
                }
                Gui.drawRect(width - 91, sr.getScaledHeight() - 23, width - 91 + 182, sr.getScaledHeight(), new Color(0, 0, 0, 60));
                RenderUtils.drawRoundedRect(this.hotbarX, sr.getScaledHeight() - 22 - 1, 22.0, 24.0, 4.0, new Color(0, 0, 0, 130).getRGB());
            }
        });
    }
    
    public void blurScreen() {
        if (Blur.blur.getValue()) {
            StencilUtil.initStencilToWrite();
            Client.getInstance().getEventBus().post(new ShaderEvent());
            StencilUtil.readStencilBuffer(1);
            GaussianBlur.renderBlur(Blur.radius.getValue().floatValue());
            StencilUtil.uninitStencilBuffer();
        }
        if (Blur.shadow.getValue()) {
            (this.framebuffer = RenderUtils.createFrameBuffer(this.framebuffer)).framebufferClear();
            this.framebuffer.bindFramebuffer(true);
            Client.getInstance().getEventBus().post(new ShaderEvent());
            this.framebuffer.unbindFramebuffer();
            BloomUtil.renderBlur(this.framebuffer.framebufferTexture, this.shadowRadius.getValue().intValue(), this.shadowOffset.getValue().intValue());
        }
    }
    
    static {
        Blur.blur = new Property<Boolean>("Blur", false);
        Blur.radius = new DoubleProperty("Blur Radius", 10.0, 1.0, 50.0, 1.0, Blur.blur::getValue);
        Blur.shadow = new Property<Boolean>("Shadow", false);
        Blur.showElements = new Property<Boolean>("Show Elements..", false);
        Blur.scoreboard = new Property<Boolean>("Scoreboard", true, Blur.showElements::getValue);
        Blur.targethud = new Property<Boolean>("Target HUD", true, Blur.showElements::getValue);
        Blur.sessionStats = new Property<Boolean>("Session Stats", true, Blur.showElements::getValue);
        Blur.hotbar = new Property<Boolean>("Hotbar", true, Blur.showElements::getValue);
        Blur.hud = new Property<Boolean>("HUD", true, Blur.showElements::getValue);
    }
}
