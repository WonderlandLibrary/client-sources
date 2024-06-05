package de.tired.api.guis.altmanager;

import de.tired.api.extension.Extension;
import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import de.tired.api.guis.accountlogin.AccountLoginThread;
import de.tired.api.performanceMode.PerformanceGui;
import de.tired.api.util.render.Scissoring;
import de.tired.api.util.font.CustomFont;
import de.tired.api.util.shader.renderapi.AnimationUtil;


import de.tired.interfaces.FHook;
import de.tired.interfaces.IHook;
import de.tired.shaderloader.ShaderManager;
import de.tired.shaderloader.ShaderRenderer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.api.TheAlteningException;
import com.thealtening.api.retriever.AsynchronousDataRetriever;
import com.thealtening.api.retriever.BasicDataRetriever;
import com.thealtening.auth.TheAlteningAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import de.tired.shaderloader.list.BackGroundShader;
import de.tired.tired.Tired;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.net.Proxy;

public class AltManager extends GuiScreen implements IHook {

    private static final double widthRect = 140;
    private int scrollAmount = 4;
    private GuiButton loginAlt, removeAlt, renameAlt, addAlt;
    public float animationY;
    public Account account;

    private boolean clicked = false;

    public AccountLoginThread loginThread;

    private int registeredClicks = 0;

    public boolean isOver = false;

    public AltManager() {
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1337187:
                if (account == null) {
                    loginThread = new AccountLoginThread("", "");
                    loginThread.status = "No alt selected";
                    return;
                }
                final String user = account.getUserName();
                final String password = account.getPassword();
                for (Account alt : AltManagerRegistry.getRegistry()) {
                    final BasicDataRetriever basicDataRetriever = new BasicDataRetriever(alt.getKey());
                    final TheAlteningAuthentication theAlteningAuthentication = TheAlteningAuthentication.theAltening();
                    basicDataRetriever.updateKey(alt.getKey());
                    if (!alt.isTheAltening()) {
                        theAlteningAuthentication.updateService(AlteningServiceType.MOJANG);
                        loginThread = new AccountLoginThread(user, password);
                        loginThread.start();
                    } else {

                        try {
                            theAlteningAuthentication.updateService(AlteningServiceType.THEALTENING);
                            final AsynchronousDataRetriever asynchronousDataRetriever = basicDataRetriever.toAsync();
                            final com.thealtening.api.response.Account account = asynchronousDataRetriever.getAccount();
                            final YggdrasilUserAuthentication service = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);

                            service.setUsername(account.getToken());
                            service.setPassword("nullamongus");
                        } catch (TheAlteningException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case 1337:
                MC.displayGuiScreen(new AddAltGui());
                break;
            case 123:
                clicked = true;
                break;
            case 12:
                MC.displayGuiScreen(new TheAlteningLoginFM());
        }
        super.actionPerformed(button);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int yAddon = 50;
        for (Account alt : AltManagerRegistry.getRegistry()) {
            account = alt;
            isOver = isOver((int) (width / 2f - widthRect + 10), -5 + yAddon + scrollAmount, 200, 35, mouseX, mouseY);
            if (isOver && mouseButton == 0) {
                registeredClicks += 1;

                if (registeredClicks == 1) {
                    if (clicked) {
                        for (Account alt2 : AltManagerRegistry.getRegistry()) {
                            AltManagerRegistry.getRegistry().remove(alt2);
                        }
                        clicked = false;
                    }
                }

                if (registeredClicks == 2) {
                    final String user = alt.getUserName();
                    final String password = alt.getPassword();
                    if (!alt.isTheAltening()) {
                        loginThread = new AccountLoginThread(user, password);
                        loginThread.start();
                    }

                    registeredClicks = 0;
                }

            }
            yAddon += 50;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        if (PerformanceGui.usingType != null)
            switch (PerformanceGui.usingType) {
                case NORMAL_PERFORMANCE:

                    ShaderManager.shaderBy(BackGroundShader.class).doRender();
                    GlStateManager.popMatrix();
                    ShaderRenderer.stopBlur();
                    ShaderRenderer.startBlur();
                    Gui.drawRect(width / 2f - widthRect, 35, width / 2f + widthRect, height / 2f + 200, Integer.MIN_VALUE);
                    ShaderRenderer.stopBlur();
                    break;
                case HIGH_PERFORMANCE:
                    this.drawDefaultBackground();
                    Gui.drawRect(width / 2f - widthRect, 35, width / 2f + widthRect, height / 2f + 200, Integer.MIN_VALUE);
            }

        FHook.fontRenderer.drawStringWithShadow2("AltList", calculateMiddle("AltList", FHook.fontRenderer, 0, width), 15, -1);

        FHook.fontRenderer.drawStringWithShadow(this.loginThread == null ? "Waiting.." : this.loginThread.getStatus(), 3, 3, -1);


        int yAddon = 50;
        for (Account alt : AltManagerRegistry.getRegistry()) {
            this.animationY = (float) AnimationUtil.getAnimationState((double) this.animationY, scrollAmount, Math.max(0.6D, Math.abs((double) this.animationY - animationY)) * 282);
            GlStateManager.pushMatrix();
            int wheel = Mouse.getDWheel();

            if (wheel < 0) {
                if (yAddon + 300 > -7 + yAddon) scrollAmount -= 16;
            } else if (wheel > 0) {
                scrollAmount += 34;
                if (scrollAmount > 0)
                    scrollAmount = 0;
            }
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            Scissoring.SCISSORING.scissorOtherWay(width / 2f - widthRect + 10, 37, 322, 1222.0);
            Scissoring.SCISSORING.scissorOtherWay(width / 2f - widthRect + 10, 40, 322, 382);
            isOver = isOver((int) (width / 2f - widthRect + 10), (int) (-5 + yAddon + animationY), 200, 35, mouseX, mouseY);

            RenderProcessor.drawRoundedRectangle(width / 2f - widthRect + 10, 35 + yAddon + animationY, width / 2f + widthRect - 10, -7 + yAddon + animationY, 0, Integer.MIN_VALUE);

            GlStateManager.resetColor();
            GlStateManager.disableBlend();
            if (isOver) {
                Gui.drawRect(width / 2f - widthRect + 10, 35 + yAddon + animationY, width / 2f + widthRect - 10, -7 + yAddon + animationY, Integer.MIN_VALUE);
            }

            FHook.fontRenderer.drawStringWithShadow(alt.isTheAltening() ? "TheAlteing" : alt.getUserName(), width / 2f - widthRect + 60, 2f + yAddon + animationY, -1);

            StringBuilder s;

            String flag = alt.isTheAltening() ? "Alt" : alt.getPassword();
            s = new StringBuilder();

            for (int flag1 = 0; flag1 < flag.length(); ++flag1) {
                s.append("*");
            }

            FHook.fontRenderer.drawStringWithShadow(s.toString(), width / 2f - widthRect + 60, 17f + yAddon + animationY, -1);

            if (alt.isTheAltening()) {
                FHook.fontRenderer.drawStringWithShadow("[TheAltening]", width / 2f - widthRect + 90, 17f + yAddon + animationY, -1);
            }

            GlStateManager.resetColor();
            GlStateManager.disableBlend();
            if (!alt.isTheAltening()) {
                alt.loadHead();
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawPlayerHeadRes(alt.getHead(), (int) width / 2 - (int) widthRect + 20, (int) ((int) (-2 + yAddon) + animationY), 30, 30);
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GlStateManager.popMatrix();
            yAddon += 50;
        }

        GlStateManager.resetColor();
        GlStateManager.disableBlend();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean isOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    @Override
    public void initGui() {
        Tired.INSTANCE.discordRPC.setStage(new String[]{"In AltManager", "lmfao"});
        buttonList.add(loginAlt = new GuiButton(1337187, (int) (width / 2f - widthRect), (int) (height / 2f + 180), 120, 20, "Login"));
        buttonList.add(addAlt = new GuiButton(1337, (int) (width / 2f - widthRect + 120), (int) (height / 2f + 180), 50, 20, "AddAlt"));
        buttonList.add(new GuiButton(123, (int) (width / 2f - widthRect + 170), (int) (height / 2f + 180), 110, 20, "RemoveAlt"));
        super.initGui();
    }

    public int calculateMiddle(String text, CustomFont fontRenderer, int x, int widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }
}
