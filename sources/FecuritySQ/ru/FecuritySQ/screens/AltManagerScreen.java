package ru.FecuritySQ.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.INestedGuiEventHandler;
import net.minecraft.client.gui.screen.AbstractCommandBlockScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.shader.GaussianBlur;
import ru.FecuritySQ.shader.ShaderUtil;
import ru.FecuritySQ.shader.StencilUtil;
import ru.FecuritySQ.utils.MathUtil;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class AltManagerScreen extends Screen implements INestedGuiEventHandler {

    public static final RenderSkyboxCube PANORAMA_RESOURCES = new RenderSkyboxCube(new ResourceLocation("textures/gui/title/background/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY_TEXTURES = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    private final RenderSkybox panorama = new RenderSkybox(PANORAMA_RESOURCES);

    private final Screen lastScreen;

    private long firstRenderTime;

    public static CopyOnWriteArrayList<Alt> alts = new CopyOnWriteArrayList<>();

    boolean initbuttons = false;

    public AltManagerScreen(Screen parentScreen) {
        super(new StringTextComponent(""));
        this.lastScreen = parentScreen;
    }

    float scroll = 0;

    int windowWidth = 350;
    int windowHeight = 225;

    int windowX, windowY;
    public ShaderUtil shaderUtil = new ShaderUtil("background");


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if(buttons.isEmpty()) initbuttons = false;

        if(!initbuttons){
            this.addButton(new AltButton(10, 10, 150, 15, new TranslationTextComponent("Добавить"), (p_lambda$init$1_1_) ->
            {
                alts.add(new Alt());
            }));
            this.addButton(new AltButton(10, 10, 150, 15, new TranslationTextComponent("Очистить все"), (p_lambda$init$1_1_) ->
            {
                alts.clear();
            }));
            initbuttons = true;
        }
        if (this.firstRenderTime == 0L) {
            this.firstRenderTime = Util.milliTime();
        }
        Minecraft mc = Minecraft.getInstance();
        float f = (float) (Util.milliTime() - this.firstRenderTime) / 1000.0F;
        GlStateManager.disableDepthTest();
        this.panorama.render(partialTicks, MathHelper.clamp(f, 0.0F, 1.0F));
        this.minecraft.getTextureManager().bindTexture(PANORAMA_OVERLAY_TEXTURES);
        shaderUtil.init();
        shaderUtil.setUniformf("resolution", minecraft.getMainWindow().getScaledWidth(), minecraft.getMainWindow().getScaledHeight());
        shaderUtil.setUniformf("time", (System.currentTimeMillis() - MainMenuScreen.initTime) / 1000f);
        shaderUtil.drawQuads();
        shaderUtil.unload();
        GaussianBlur.renderBlur(20);
        int x = minecraft.getMainWindow().getScaledWidth();
        int y = minecraft.getMainWindow().getScaledHeight();



        RenderUtil.drawRound(0,0, x, y, 0, new Color(0,0,0, 150).getRGB());


         windowX = (x / 2) - (windowWidth / 2);
         windowY = (y / 2) - (windowHeight / 2);

        //RenderUtil.drawRound((x / 2) - (windowWidth / 2), (y / 2) - (windowHeight / 2) + 1, windowWidth, windowHeight, 6, new Color(0, 102, 255).getRGB());
        //RenderUtil.drawRound((x / 2) - (windowWidth / 2), (y / 2) - (windowHeight / 2), windowWidth, windowHeight, 6, new Color(15, 15, 15).getRGB());


        StencilUtil.initStencilToWrite();
        RenderUtil.drawRound(windowX, windowY, windowWidth, windowHeight -10, 6, new Color(0, 102, 255).getRGB());
        StencilUtil.readStencilBuffer(0);
        RenderUtil.drawRound(windowX, windowY, windowWidth, windowHeight + 1, 6, new Color(0, 102, 255).getRGB());
        StencilUtil.uninitStencilBuffer();
        RenderUtil.drawRound(windowX, windowY, windowWidth, windowHeight, 6, new Color(15, 15, 15).getRGB());

        Fonts.mntsb16.drawCenteredString(matrixStack, "Менеджер аккаунтов", x / 2 + 16,  (y / 2) - (windowHeight / 2) + 10, -1);

        RenderUtil.drawImage(matrixStack, new ResourceLocation("FecuritySQ/icons/close.png"), windowX + windowWidth - 20, windowY + 3, 16, 16);
        RenderUtil.drawImage(matrixStack, new ResourceLocation("FecuritySQ/icons/user.png"), windowX + windowWidth / 2 - 44, windowY + 3, 16, 16);

        int offset = 0;
        for(Widget button : buttons){
            button.x = windowX + 25 + offset;
            button.y = windowY + windowHeight - button.getHeightRealms() - 8;
            offset+= button.getWidth() + 6;
        }


        int maxScroll = 34 * alts.size();

        if(alts.size() > 5){
            maxScroll = 34 * alts.size() - 150;
        }

        if(scroll <= -maxScroll){
            scroll = -maxScroll;
        }

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.scissorRect(windowX, windowY + 27, (windowWidth * 2),  windowHeight * 1.5f);

        int altOffset = 0;
        for(Alt alt : alts){
            alt.x = windowX + (windowWidth / 2) - alt.width / 2;
            alt.y = windowY + + scroll + 30 + altOffset;
            altOffset+= alt.height + 4;
            alt.draw(mouseX, mouseY);
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == GLFW.GLFW_KEY_ESCAPE){
            Minecraft.getInstance().displayGuiScreen(new MainMenuScreen(true));
        }
        for(Alt alt : alts) {
            alt.keyPressed(keyCode, scanCode, modifiers);
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(button == 0){
            if(MathUtil.collided((float) (windowX + windowWidth - 20), (float) (windowY + 3), 16F, 16F, (int) mouseX, (int) mouseY)){
               Minecraft.getInstance().displayGuiScreen(new MainMenuScreen(true));
            }
        }
        for(Alt alt : alts){
            boolean check = alt.getY() > this.buttons.get(0).y - 20;
            if(!check){
                alt.mouseClicked((int) mouseX, (int) mouseY, button);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        for(Alt alt : alts){
            alt.charTyped(codePoint, modifiers);
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {

        if(scroll >= 0){
            scroll = 0;
        }

        if(alts.size() > 5) {
            if (delta > 0) {
                this.scroll += 12f;
            } else if (delta < 0) {
                this.scroll -= 12f;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }
}
