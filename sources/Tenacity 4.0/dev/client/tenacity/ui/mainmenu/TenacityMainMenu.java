package dev.client.tenacity.ui.mainmenu;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.misc.NetworkingUtils;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.GradientUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.client.tenacity.utils.render.RoundedUtil;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.animations.impl.EaseBackIn;
import dev.utils.animations.impl.EaseInOutQuad;
import dev.utils.animations.impl.ElasticAnimation;
import dev.utils.font.FontUtil;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TenacityMainMenu extends GuiScreen {

    private Animation openAnimation;
    private Animation hoverBarAnimation;
    private Animation openSideAnim;
    private Animation fadeInAnim;

    private final List<MenuButton> buttons = new ArrayList() {{
        add(new MenuButton("Tenacity/MainMenu/button1.png", "Singleplayer"));
        add(new MenuButton("Tenacity/MainMenu/button2.png", "Multiplayer"));
        add(new MenuButton("Tenacity/MainMenu/button3.png", "Alt Manager"));
    }};

    private final List<OptionButton> sidebarButtons = new ArrayList() {{
        add(new OptionButton(FontUtil.XMARK, "Exit"));
        add(new OptionButton(FontUtil.SETTINGS, "Settings"));
        add(new OptionButton("Discord"));
        add(new OptionButton(FontUtil.SCRIPT, "Scripting"));
    }};

    private boolean clickedSidebar;

    @Override
    public void initGui() {
        mc.gameSettings.ofFastRender = false;
        clickedSidebar = false;
        mc.gameSettings.guiScale = 2;
        openAnimation = new ElasticAnimation(750, 1, 3.8f, 1.75f, false);
        hoverBarAnimation = new ElasticAnimation(700, 1, 3.8f, 2f, false);
        openSideAnim = new ElasticAnimation(700, 1, 3.8f, 2f, false);
        fadeInAnim = new DecelerateAnimation(550, 1);
        buttons.forEach(MenuButton::initGui);
        sidebarButtons.forEach(OptionButton::initGui);
    }

    private final float barsWidth = 131 / 2f;
    private final float barsHeight = 119 / 2f;
    private final float sideBarWidth = 216;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        Color gradientColor1 = ColorUtil.interpolateColorsBackAndForth(15, 1, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), false);
        Color gradientColor2 = ColorUtil.interpolateColorsBackAndForth(15, 1, Tenacity.INSTANCE.getAlternateClientColor(), Tenacity.INSTANCE.getClientColor(), false);
        // Gui.drawGradientRectSideways2(0, 0, this.width, this.height, gradientColor1.getRGB(), gradientColor2.getRGB());
        GradientUtil.drawGradient(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), 1, gradientColor1, gradientColor1, gradientColor2, gradientColor2);

        width = sr.getScaledWidth();
        height = sr.getScaledHeight();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderUtil.setAlphaLimit(0);
        mc.getTextureManager().bindTexture(new ResourceLocation("Tenacity/logoShadow.png"));
        float textureWH = 310;
        float textureY = (float) (height / 2f - (textureWH / 4f + (70 * openAnimation.getOutput())));
        drawModalRectWithCustomSizedTexture(width / 2f - textureWH / 4f, textureY, 0, 0, textureWH / 2, textureWH / 2,
                textureWH / 2, textureWH / 2);


        float separation = 0;
        for (MenuButton button : buttons) {
            button.buttonWH = 179 / 2f;
            button.x = width / 2f - button.buttonWH / 2f - 90.5f + separation;
            button.y = textureY + 180;

            button.clickAction = () -> {
                switch (button.text) {
                    case "Singleplayer":
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    case "Multiplayer":
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    case "Alt Manager":
                        mc.displayGuiScreen(Tenacity.INSTANCE.getAltManager());
                        break;
                }
            };

            button.drawScreen(mouseX, mouseY);
            separation += 90.5f;
        }


        boolean hoveringBars = HoveringUtil.isHovering(11, 18, barsWidth - 33, barsHeight - 33, mouseX, mouseY);
        hoverBarAnimation.setDirection(hoveringBars && !clickedSidebar ? Direction.FORWARDS : Direction.BACKWARDS);
        mc.getTextureManager().bindTexture(new ResourceLocation("Tenacity/MainMenu/triplebars.png"));
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, (-5 + 62 * hoverBarAnimation.getOutput()), 0);
        GlStateManager.rotate((float) (-90 * hoverBarAnimation.getOutput()), 0, 0, 1);
        GlStateManager.translate(0, -(-5 + (62 * hoverBarAnimation.getOutput())), 0);
        drawModalRectWithCustomSizedTexture(-5, (float) (((float) (-5 + (62 * hoverBarAnimation.getOutput())) - 45) + (45 * openAnimation.getOutput())), 0, 0, barsWidth, barsHeight, barsWidth, barsHeight);
        GlStateManager.popMatrix();


        openSideAnim.setDirection(clickedSidebar ? Direction.FORWARDS : Direction.BACKWARDS);
        fadeInAnim.setDirection(openSideAnim.getDirection());

        if (clickedSidebar || !openSideAnim.isDone()) {
            Gui.drawRect2(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), ColorUtil.applyOpacity(Color.BLACK, (float) Math.min(.4 * openSideAnim.getOutput(), .4)).getRGB());
        }
        GlStateManager.enableBlend();

        float sidebarWidth = (float) ((float) (15 * hoverBarAnimation.getOutput()) + (sideBarWidth * openSideAnim.getOutput()));
        int interpolateSideRect = ColorUtil.interpolateColorsBackAndForth(15, 1,
                new Color(192, 147, 203), new Color(98, 180, 206), false).getRGB();
        Gui.drawRect2(0, 0, sidebarWidth, sr.getScaledHeight(), interpolateSideRect);

        if (clickedSidebar || !openSideAnim.isDone()) {
            int seperation = 0;
            for (OptionButton sideButton : sidebarButtons) {
                sideButton.iconAdjustY = 3;
                switch (sideButton.name) {
                    case "Exit":
                        sideButton.color = Color.RED;
                        sideButton.clickAction = () -> mc.shutdown();
                        break;
                    case "Settings":
                        sideButton.color = new Color(80, 194, 255);
                        sideButton.clickAction = () -> mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                    case "Discord":
                        sideButton.color = new Color(88, 101, 242);
                        sideButton.iconAdjustY = 4;
                        sideButton.clickAction = () -> NetworkingUtils.openLink("https://tenacity.dev/discord");
                        break;
                    case "Scripting":
                        sideButton.color = new Color(255, 80, 126);
                        sideButton.clickAction = () -> NetworkingUtils.openLink("https://scripting.tenacity.dev");
                        break;
                }
                sideButton.x = (float) (-200 + (200 * openSideAnim.getOutput()));
                sideButton.y = 50 + seperation;
                sideButton.height = 50;
                sideButton.width = 170;
                sideButton.drawScreen(mouseX, mouseY);
                seperation += 50 + 25;
            }
        }


    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        buttons.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton));
        ScaledResolution sr = new ScaledResolution(mc);

        //If clicking anywhere else on the screen besides the sidebar, close the sidebar
        if (!HoveringUtil.isHovering(0, 0, 216, sr.getScaledHeight(), mouseX, mouseY) && mouseButton == 0 && clickedSidebar) {
            clickedSidebar = false;
        }
        //If hovering sidebar and clicking then open the sidebar
        boolean hoveringBars = HoveringUtil.isHovering(11, 18, barsWidth - 33, barsHeight - 33, mouseX, mouseY);
        if (hoveringBars && mouseButton == 0) {
            clickedSidebar = true;
        }

        if(clickedSidebar && mouseButton == 0){
            sidebarButtons.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton));
        }


    }
}
