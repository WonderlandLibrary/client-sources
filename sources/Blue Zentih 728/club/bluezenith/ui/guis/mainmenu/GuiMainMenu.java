package club.bluezenith.ui.guis.mainmenu;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.data.preferences.Preferences;
import club.bluezenith.core.user.ClientUser;
import club.bluezenith.module.modules.render.ClickGUI;
import club.bluezenith.ui.GoodbyeScreen;
import club.bluezenith.ui.button.ScrollingSelector;
import club.bluezenith.ui.guis.GuiConfirmDialog;
import club.bluezenith.ui.guis.mainmenu.changelog.fetch.ChangelogProvider;
import club.bluezenith.ui.guis.mainmenu.changelog.fetch.ProdChangelogProvider;
import club.bluezenith.ui.guis.mainmenu.changelog.fetch.TestChangelogProvider;
import club.bluezenith.ui.guis.mainmenu.changelog.render.BasicChangelogRenderer;
import club.bluezenith.ui.guis.mainmenu.changelog.render.ChangelogRenderer;
import club.bluezenith.util.client.TriConsumer;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.gui.*;
import net.minecraft.util.MathHelper;
import viamcp.ViaMCP;
import viamcp.protocols.ProtocolCollection;

import java.awt.*;
import java.io.IOException;

import static club.bluezenith.BlueZenith.fancyName;
import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.ui.clickgui.ClickGui.i;
import static club.bluezenith.util.font.FontUtil.*;
import static club.bluezenith.util.render.ColorUtil.pulse;
import static club.bluezenith.util.render.RenderUtil.animate;
import static club.bluezenith.util.render.RenderUtil.blur;
import static java.util.Arrays.stream;
import static viamcp.protocols.ProtocolCollection.values;

public class GuiMainMenu extends GuiScreen {
                                                      //true when the mouse clicked in this screen, to prevent accidentally enabling dragging when switching from another screen
    private static boolean sentAlert, draggingSlider; //sentAlert is used for displaying the alert notification (if there is),
                                             //sentAlert used on first render, because there might be a huge delay between initGui and drawScreen calls, which may leave the notification unseen.
    private static final Color primary = new Color(250, 235, 42);//new Color(8, 66, 232); //Colors of the title (client name).
    private static final Color secondary = new Color(8, 41, 211);
    private ChangelogProvider changelogProvider; //todo server-side changelog support. must return a json array of json objects with text and type (ChangelogEntry.type), require api k
    private ChangelogRenderer changelogRenderer;
    private float titleX, titleY, titleX2, titleY2, backgroundAlpha, targetBackgroundAlpha; //positions for clickable title
    private int mousePrevX;

    private ScrollingSelector scrollingSelector;
    private String protocolVersion = "1.8.x";

    @Override
    public void initGui() {
        this.buttonList.clear();

        final FontRenderer titleFont = vkDemibold;

        final int clientNameWidth = titleFont.getStringWidth(fancyName); //width of the client name, used to center buttons and to adapt their width
        final int middleOffset = width / 2 - clientNameWidth/2; //middle of the screen offset by the string width/2, to place the buttons in the middle under the title
        final int buttonMargin = 24; //distance between buttons
        final int heightOffset = height/2 + titleFont.FONT_HEIGHT/2 + 45; // 45 is the bypass value I guess


        //add buttons with click listeners
        this.buttonList.add(new GuiButton(0, middleOffset, heightOffset - buttonMargin * 4, clientNameWidth, 20, "singleplayer")
                .onClick(() -> mc.displayGuiScreen(new GuiSelectWorld(this))));

        this.buttonList.add(new GuiButton(1, middleOffset, heightOffset - buttonMargin * 3, clientNameWidth, 20, "multiplayer")
                .onClick(() -> mc.displayGuiScreen(new GuiMultiplayer(this))));

        this.buttonList.add(new GuiButton(2, middleOffset, heightOffset - buttonMargin * 2, clientNameWidth, 20, "altmanager")
                .onClick(() -> mc.displayGuiScreen(getBlueZenith().getNewAltManagerGUI())));

        this.buttonList.add(scrollingSelector = new ScrollingSelector(3, middleOffset + 60, height / 2 + buttonMargin + 11,
                "",
                stream(values()).map(val -> ProtocolCollection.getProtocolById(val.getVersion().getVersion()).getName()).toArray(String[]::new))
                .setIndexConsumer((index) -> {
                    protocolVersion = scrollingSelector.getCurrentValue();
                    ViaMCP.getInstance().setVersion(values()[index].getVersion().getVersion());
                }).setValue(protocolVersion));

        this.buttonList.add(new GuiButton(4, middleOffset,  heightOffset, clientNameWidth, 20, "settings")
                .onClick(() -> mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings))));

        this.buttonList.add(new GuiButton(5, middleOffset, heightOffset + buttonMargin, clientNameWidth, 20, "shutdown")
                .onClick(() -> mc.displayGuiScreen(new GuiConfirmDialog(this, false, (shutdown) -> {
                    if(shutdown) mc.displayGuiScreen(new GoodbyeScreen());
                    else mc.displayGuiScreen(null);
                }).setTitle("Are you sure you want to quit?").setConfirmText("Yes").setDeclineText("No, take me back"))));

        this.buttonList.forEach(GuiButton::useMediumFont); //enable all buttons to use medium (VK Sans Medium) font

        if(changelogProvider == null) {
            changelogProvider = new TestChangelogProvider();//todo new ProdChangelogProvider();
            changelogProvider.fetch();
        }

        if(changelogRenderer == null)
            changelogRenderer = new BasicChangelogRenderer();
        changelogRenderer.setWidth(middleOffset - 5);
        changelogRenderer.setContents(changelogProvider.getChangelogEntries());
        changelogRenderer.setMaxHeight(height - height/3F); //take up 2/3 of the screen height
        changelogRenderer.setPosition(0, vkChangelog50.FONT_HEIGHT);
        draggingSlider = false;
        mousePrevX = 0; //reset dragging state
    }

    //the render function. placed in a lambda to reduce the drawScreen method size (things have to be drawn twice when blurring)
    final TriConsumer<Integer, Integer, Float> renderCallback = (mouseX, mouseY, partialTicks) -> {
        final TFontRenderer titleFont = vkDemibold; //the font used to draw the title.
        final String clientName = fancyName; //shortcut for the client name
        float titleWidth;
        final float centerPos = width / 2F - (titleWidth = titleFont.getStringWidthF(clientName)) / 2F; //X position for the title string that will center it.
        final int y = height / 2 - titleFont.FONT_HEIGHT - 50; //Y position for the title string. - 50 is just a "bypass value" that makes it look better I guess.

        final TFontRenderer greetingFont = vkMedium35; //the font used to draw the greeting (bottom right corner)

        final ClientUser userInfo = BlueZenith.getBlueZenith().getClientUser();
        final String greetingMessage = "welcome back, " + userInfo.getRank().getColorCode() + userInfo.getUsername();

        //use a gradient
        titleFont.setGradient(charIndex -> new Color(pulse(charIndex, primary.getRed(), primary.getGreen(), primary.getBlue(), secondary.getRed(), secondary.getGreen(), secondary.getBlue())))
                                                     //+ 0.01F is unnecessary but without it the title looks slightly broken, and the addition seems to fix it, so I'm keeping it.
                .drawString(clientName, titleX = centerPos + 0.01F, titleY = y, 0, true); //draw the title

        titleY2 = titleY + titleFont.FONT_HEIGHT; //set title positions
        titleX2 = titleX + titleWidth;

        greetingFont.drawString(greetingMessage,
                width - greetingFont.getStringWidth(greetingMessage) - 2, //draw it on the right side of the screen, slightly offset
                height - greetingFont.FONT_HEIGHT - 2, //draw it on the bottom, slightly offset too.
                -1);

        super.drawScreen(mouseX, mouseY, partialTicks); //draw buttons
    };

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground(); //draws either the shader background, or the gradient background, if shader usage is explicitly disabled by the user.

        backgroundAlpha = animate(targetBackgroundAlpha, backgroundAlpha, 0.05F); //smoothen the background alpha to make it look better when dragging, because mouseX is an int
        //ps: darkened background is drawn in the drawDefaultBackground method

        if(Preferences.useBlurInMenus) { //true by default
            renderCallback.accept(mouseX, mouseY, partialTicks);
            changelogRenderer.render(mouseX, mouseY, partialTicks, false);
            blur(0, 0, width, height);
        }
        changelogRenderer.render(mouseX, mouseY, partialTicks, true);
        renderCallback.accept(mouseX, mouseY, partialTicks); //draw the "main layer"
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        button.runClickCallback();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(mouseButton == 0)
            changelogRenderer.mouseClicked(mouseX, mouseY);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) { //basically the slider.
        if(!i(mouseX, mouseY, titleX, titleY, titleX2, titleY2) && !draggingSlider) return; //returns if ur not hovering over the client name (title) and havent locked on the slider.

        if(mousePrevX == 0 || mousePrevX == mouseX) {
            mousePrevX = mouseX; //dont do stuff if u just clicked/didnt move the mouse
            return;
        }

        if(!draggingSlider)
            draggingSlider = true; //started dragging

        targetBackgroundAlpha += (mouseX - mousePrevX); //increment/decrement next alpha based on where you drag
        targetBackgroundAlpha = MathHelper.clamp(targetBackgroundAlpha, 0, 150); //limit the alpha by 150 (looks real weird bc of blur on high alpha values)
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        mousePrevX = mouseX; //this was the previous location
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        draggingSlider = false; //not dragging the mouse anymore, prevent slider value from changing.
        mousePrevX = 0; //reset drag state to prevent value changing right away
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == getBlueZenith().getModuleManager().getModule(ClickGUI.class).keyBind)
            mc.displayGuiScreen(ClickGUI.oldDropdownUI);

        super.keyTyped(typedChar, keyCode);
    }

    public float getBackgroundAlpha() {
        return this.backgroundAlpha;
    }
}
