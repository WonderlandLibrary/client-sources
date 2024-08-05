package fr.dog.module.impl.render;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.PacketReceiveEvent;
import fr.dog.event.impl.render.Render2DEvent;
import fr.dog.event.impl.render.RenderBlur;
import fr.dog.event.impl.render.RenderGlow;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ModeProperty;
import fr.dog.util.math.TimeUtil;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.font.TTFFontRenderer;
import fr.dog.util.render.opengl.StencilUtil;
import lombok.AllArgsConstructor;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class SessionStats extends Module {
    private final TimeUtil stopwatch = new TimeUtil();
    private final TimeUtil killCountdown = new TimeUtil();
    private final ModeProperty mode = ModeProperty.newInstance("Session Stats", new String[]{"Tenacity", "Moon"}, "Tenacity");
    private final BooleanProperty stats = BooleanProperty.newInstance(  "Session Stats", true, () -> mode.is("Tenacity"));
    private final BooleanProperty blur = BooleanProperty.newInstance("Blur", true, () -> mode.is("Moon"));
    private final BooleanProperty glow = BooleanProperty.newInstance("Glow", true);
    private final String[] killString = {"was &+'s final", "by &+"};
    private final Session session = new Session(0, 0,0);
    private String time = "0 seconds";
    private long seconds;

    public SessionStats() {
        super("SessionStats", ModuleCategory.RENDER);
        this.setDraggable(true);
        this.registerProperties(mode, glow, blur, stats);
        this.setX(5);
        this.setY(27);
    }

    @SubscribeEvent
    public void onRender2D(Render2DEvent e) {
        if (stopwatch.finished(1000)) {
            long elapsed = System.currentTimeMillis() - this.session.startTime;

            seconds = (elapsed / 1000);
            long minutes = (elapsed / (1000 * 60)) % 60;
            long hours = (elapsed / (1000 * 60 * 60)) % 24;

            time = String.format("%02d:%02d:%02d", hours, minutes, seconds % 60);


            stopwatch.reset();
        }

        String[] toDisplay = new String[] {
                String.format("K/D: %s", (float) Math.round(((float) this.session.kills / (this.session.death + 1))*10)/10),
                String.format("Wins: %s", this.session.wins)
        };

        switch (mode.getValue()){
            case "Tenacity" -> {
                TTFFontRenderer osb12 = Fonts.getOpenSansBold(12);
                TTFFontRenderer osr17 = Fonts.getOpenSansRegular(17);
                TTFFontRenderer osb17 = Fonts.getOpenSansBold(17);

                float maxWidth = osb17.getWidth("Stats :") + 65;

                for (String text : toDisplay) {
                    float width = osr17.getWidth(text) + 65;
                    if(width > maxWidth)
                        maxWidth = width;
                }

                float maxHeight = 30;
                for (String ignored : toDisplay) {
                    float height = osr17.getHeight("I");
                    maxHeight += height;
                }

                setWidth(stats.getValue() ? maxWidth : 50);
                setHeight(maxHeight);

                RenderUtil.drawRoundedRect(getX(), getY(), getWidth(), getHeight(), 10,
                        Dog.getInstance().getThemeManager().getCurrentTheme().color1, Dog.getInstance().getThemeManager().getCurrentTheme().color2,Dog.getInstance().getThemeManager().getCurrentTheme().color1, Dog.getInstance().getThemeManager().getCurrentTheme().color2);

                RenderUtil.drawCircleLoading(getX()-3,getY()-4,getHeight() + 8, 1, new Color(0,0,0,50));
                RenderUtil.drawCircleLoading(getX()-3,getY()-4,getHeight() + 8, (seconds % 3600) / 3600f, new Color(-1));
                osb12.drawCenteredString(time, getX() + 25, getY() + 21, Color.WHITE.getRGB());

                if(!stats.getValue())
                    return;

                osb17.drawString("Stats : ", getX() + 55, getY() + 6, Color.WHITE.getRGB());
                RenderUtil.drawLine(getX() + 55, getY() + 16, osb17.getWidth("Stats :"), 0, 2, Color.WHITE);


                float stringY = getY() + 20;
                for (String string : toDisplay) {
                    osr17.drawString(string, getX() + 55, stringY, Color.WHITE.getRGB());
                    stringY += osr17.getHeight("I") + 2;
                }
            }
            case "Moon" -> {
                this.setWidth(112.5f);
                this.setHeight(82.5f);

                TTFFontRenderer moonTitle = Fonts.getMoon(23);
                TTFFontRenderer moonSubtitle = Fonts.getMoon(20);

                moonTitle.drawString("Session Information", getX() + 27.5f, getY()+11, Color.WHITE.getRGB());
                RenderUtil.drawRoundedRect(getX()+7.5f,getY()+7.5f,15,15, 5, new Color(0,0,0,50));

                RenderUtil.drawRoundedRect(getX()+7.5f,getY() + 25,15,15, 5, new Color(0,0,0,50));
                inGameImages.get("sessionTime").drawImg(getX()+10,getY() + 27.5f,10,10);
                moonSubtitle.drawString(time, getX() + 27.5f, getY() + 29.5f, Color.WHITE.getRGB());

                RenderUtil.drawRoundedRect(getX()+7.5f,getY() + 42.5f,15,15, 5, new Color(0,0,0,50));
                inGameImages.get("sessionKD").drawImg(getX()+10,getY() + 45,10,10);
                moonSubtitle.drawString(""+(float) Math.round(((float) this.session.kills / (this.session.death + 1))*10)/10, getX() + 27.5f, getY() + 47, Color.WHITE.getRGB());

                RenderUtil.drawRoundedRect(getX()+7.5f,getY() + 60,15,15, 5, new Color(0,0,0,50));
                inGameImages.get("sessionWin").drawImg(getX()+10,getY() + 62.5f,10,10);
                moonSubtitle.drawString("" + this.session.wins, getX() + 27.5f, getY()+64.5f, Color.WHITE.getRGB());

                if(mc.thePlayer.isServerWorld()) {
                    StencilUtil.renderStencil(() -> RenderUtil.drawRoundedRectGl(getX()+7.5f,getY()+7.5f,15,15, 5, new Color(0,0,0,50)),
                            () -> RenderUtil.drawImage(new ResourceLocation("servers/" + mc.getCurrentServerData().serverIP + "/icon"), getX() + 7.5f, getY() + 7.5f, 15, 15));
                }

            }
        }
    }

    @SubscribeEvent
    private void onGlow(RenderGlow e) {
        if(!glow.getValue())
            return;

        switch (mode.getValue()) {
            case "Tenacity" -> {
                //
            }
            case "Moon" -> RenderUtil.drawRoundedRect(getX(),getY(),getWidth(),getHeight(),10, new Color(0,0,0));
        }

    }

    @SubscribeEvent
    private void onBlur(RenderBlur e){
        if(!blur.getValue())
            return;

        switch (mode.getValue()) {
            case "Tenacity" -> {
                //
            }
            case "Moon" -> RenderUtil.drawRoundedRectGl(getX(),getY(),getWidth(),getHeight(),10, new Color(-1));
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketReceiveEvent e) {
        if (e.getPacket() instanceof S45PacketTitle s45) {
            if (s45.getMessage() == null)
                return;

            if (s45.getMessage().getUnformattedText().contains("VICTORY!")) {
                ++this.session.wins;
            }

            if (s45.getMessage().getUnformattedText().contains("DIED") && killCountdown.finished(10000)) {
                ++this.session.death;
                killCountdown.reset();
            }
        }

        if(e.getPacket() instanceof S02PacketChat s02){
            final List<String> modifiedKillStrings = new ArrayList<>();

            for(String element : this.killString){
                modifiedKillStrings.add(element.replace("&+", mc.thePlayer.getName()));
            }

            if(s02.getChatComponent().getUnformattedText().contains("Bed"))
                return;

            if (modifiedKillStrings.stream().anyMatch(s -> s02.getChatComponent().getUnformattedText().contains(s))) {
                ++this.session.kills;
            }
        }
    }

    @AllArgsConstructor
    private static class Session {
        int kills, death, wins;
        final long startTime = System.currentTimeMillis();
    }
}
