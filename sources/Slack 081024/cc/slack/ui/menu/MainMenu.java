package cc.slack.ui.menu;

import cc.slack.start.Slack;
import cc.slack.ui.altmanager.gui.GuiAccountManager;
import cc.slack.utils.client.Login;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.other.FileUtil;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import okhttp3.*;

public class MainMenu extends GuiScreen {
    private List<Particle> particles = new ArrayList<>();
    private final int particlesDensity = 2500;

    private final ResourceLocation imageResource = new ResourceLocation("slack/menu/menulogo.png");

    String debugMessage = "";
    TimeUtil dmTimer = new TimeUtil();

    public static String discordId = "";
    public static String idid = "";

    private static boolean lgi = false;

    public static int animY;
    private TimeUtil animTimer = new TimeUtil();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("slack/menu/mainmenu.jpg"));
        drawModalRectWithCustomSizedTexture(0, 0,0,0, this.width, this.height, this.width, this.height);

        if (!Minecraft.renderChunksCache || !Minecraft.getMinecraft().pointedEffectRenderer) {
            RenderUtil.drawRoundedRect(width / 2 - 110, height / 2 - 115, width / 2 + 110 , this.height / 2 + 95, 15, new Color(20, 20, 20, 110).getRGB());
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.color(1, 1, 1, 1);

            Fonts.poppins18.drawString(decodes("U2xhY2sgQ2xpZW50"), width / 2 - 10, height / 2 - 90, new Color(255, 255, 255).getRGB());

            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            RenderUtil.drawImage(imageResource, width / 2 - 35, height / 2 - 100, 15, 27);
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();

            if (!dmTimer.hasReached(10000))
                Fonts.apple18.drawStringWithShadow(debugMessage, 5, 5, new Color(255, 50, 50).getRGB());

            super.drawScreen(mouseX, mouseY, partialTicks);

            if (lgi) {
                if (animTimer.hasReached(800)) {
                    Fonts.apple45.drawCenteredStringWithShadow(decodes("TG9nZ2luZyBpbi4uLg=="), width / 2f, height / 2f - 20, new Color(255, 255, 255).getRGB());
                    Gui.drawRect(0, 0, width, height, new Color (0, 0, 0, Math.min(100, (int) (animTimer.elapsed() / 3))).getRGB());
                    String hwid = FileUtil.fetchHwid();

                    if (hwid == "f") {
                        setMsg(decodes("Q291bGQgbm90IGdyYWIgSHdpZCB0byB2ZXJpZnkuIFBsZWFzZSBvcGVuIGEgdGlja2V0Lg=="));
                        return;
                    }

                    if (discordId.length() < 16 || discordId.length() > 20) {
                        setMsg(decodes("SW52YWxpZCBEaXNjb3JkIGlk"));
                        return;
                    }

                    OkHttpClient client = new OkHttpClient();
                    Request request = Login.sendReq(client, hwid, discordId);


                    // Execute the request
                    try {
                        Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {

                            String resp = response.body().string();

                            if (Login.isSuccess(discordId, resp, hwid)) {
                                idid = hwid;
                                setMsg(decodes("TG9naW4gU3VjY2Vzc2Z1bA=="));
                            } else {
                                setMsg(decodes("Q3JlZGVudGlhbHMgZGlkbid0IG1hdGNoLg=="));
                                lgi = false;
                                return;
                            }
                        } else {
                            setMsg(decodes("RmFpbGVkIHRvIGdldCByZXNwb25zZSBmcm9tIHNlcnZlci4="));
                            lgi = false;
                            return;
                        }
                    } catch (IOException e) {
                        setMsg(decodes("RmFpbGVkIHRvIGNvbnRhY3Qgc2VydmVyLg=="));
                        lgi = false;
                        return;
                    }

                    this.menuList.clear();
                    this.mc.pointedEffectRenderer = true;
                    Minecraft.renderChunksCache = true;

                    animTimer.reset();

                    addButtons();
                    lgi = false;
                } else {
                    Fonts.apple45.drawCenteredStringWithShadow(decodes("TG9nZ2luZyBpbi4uLg=="), width / 2f, height / 2f - 20, new Color(255, 255, 255).getRGB());
                    Gui.drawRect(0, 0, width, height, new Color (0, 0, 0, Math.min(100, (int) (animTimer.elapsed() / 5))).getRGB());
                }
            } else {
                animTimer.reset();
            }
            return;
        }

        if (!animTimer.hasReached(700)) {
            animY = (int) (Math.pow(1 - (animTimer.elapsed() / 700.0), 4) * this.height * 0.7);
        } else {
            animY = 0;
        }

        Fonts.poppins18.drawString(decodes("TWFkZSBieSBEZzYzNiwgVnByYWgsIGFuZCBvdGhlcnMgd2l0aCA8Mw=="),
                width - 7 - Fonts.poppins18.getStringWidth(decodes("TWFkZSBieSBEZzYzNiwgVnByYWgsIGFuZCBvdGhlcnMgd2l0aCA8Mw==")),
                height - 13, new Color(255, 255, 255, 150).getRGB());
        GlStateManager.color(1, 1, 1, 1);

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        RenderUtil.drawImage(imageResource, width / 2 - 23, height / 2 - 95 + animY, 46, 80);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();

        for (Particle particle : particles) {
            particle.update();
            particle.render(mc);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

    }



    @Override
    public void initGui() {

        int numberOfParticles = (this.width * this.height) / particlesDensity;
        particles.clear();
        for (int i = 0; i < numberOfParticles; i++) {
            particles.add(new Particle(this.width, this.height));
        }

        if (Minecraft.renderChunksCache) {
            addButtons();
        } else {

            this.menuList.add(new MainMenuButton(10, width/2 - 100, height / 2 - 48, 200, 19, decodes("RmV0Y2ggRGlzY29yZCBJRA==")));
            this.menuList.add(new MainMenuButton(8, width/2 - 100, height / 2, 200, 19, decodes("Q29weSBId2lk")));
            this.menuList.add(new MainMenuButton(951, width/2 - 100, height / 2 - 24, 200, 19, decodes("TG9nLUlu")));
            this.menuList.add(new MainMenuButton(12, width/2 - 100, height / 2 + 40, 200, 19, decodes("T3VyIFdlYnNpdGU=")));
            this.menuList.add(new MainMenuButton(13, width/2 - 100, height / 2 + 64, 200, 19, decodes("Sm9pbiBvdXIgRGlzY29yZCE=")));

        }

        super.initGui();
    }

    @Override
    protected void actionPerformedMenu(MainMenuButton buttonMenu) throws IOException {
        if (lgi) return;
        super.actionPerformedMenu(buttonMenu);

        switch (buttonMenu.id) {
            case 1:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;

            case 2:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;

            case 3:
                mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;

            case 4:
                mc.displayGuiScreen(new GuiAccountManager(this));
                break;

            case 6:
                mc.shutdown();
                break;

            case 7:
                mc.displayGuiScreen(new MenuInfo());
                break;

            case 8:
                if (FileUtil.fetchHwid() == "f") {
                    setMsg(decodes("RmFpbGVkIHRvIGZldGNoIGh3aWQ="));
                    return;
                }
                setMsg(decodes("Q29waWVkIHRvIGNsaXBib2FyZA=="));
                GuiScreen.setClipboardString(FileUtil.fetchHwid());
                break;

            case 10:
                discordId = GuiScreen.getClipboardString();
                setMsg(decodes("U2V0IGRpc2NvcmQgSWQgdG86IA==") + discordId);
                return;

            case 12:
                FileUtil.showURL(Slack.getInstance().Website);
                break;

            case 13:
                FileUtil.showURL(Slack.getInstance().DiscordServer);
                break;
        }

        if (buttonMenu.id == 951) {
            lgi = true;
            animTimer.reset();
        } else {
            lgi = false;
        }
    }


    private void setMsg(String m) {
        dmTimer.reset();
        debugMessage = m;
    }

    private String decodes(String encodedInput) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedInput);
        return new String(decodedBytes);
    }

    private void addButtons() {
        this.menuList.add(new MainMenuButton(1, width/2 - 120, height / 2 + 10, 240, 20, decodes("U2luZ2xlUGxheWVy")));
        this.menuList.add(new MainMenuButton(2, width/2 - 120, height / 2 + 35, 240, 20, decodes("TXVsdGlQbGF5ZXI=")));


        this.menuList.add(new MainMenuButton(3, width/2 - 120, height / 2 + 60, 117, 20, decodes("U2V0dGluZ3M=")));
        this.menuList.add(new MainMenuButton(4, width/2 + 3, height / 2 + 60, 117, 20, decodes("QWx0IE1hbmFnZXI=")));
        this.menuList.add(new MainMenuButton(6, 5, height - 25, 60, 20, decodes("U2h1dGRvd24="), new Color(255, 0, 0)));
        this.menuList.add(new MainMenuButton(13, 70, height - 25, 100, 20, decodes("Sm9pbiBPdXIgRGlzY29yZA=="), new Color(86, 105, 247)));
        this.menuList.add(new MainMenuButton(7, 175, height - 25, 20, 20, "i"));
    }

}
