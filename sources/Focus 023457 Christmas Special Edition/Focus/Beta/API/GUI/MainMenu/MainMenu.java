package Focus.Beta.API.GUI.MainMenu;

import Focus.Beta.API.GUI.alt.GuiAltLogin;
import Focus.Beta.Client;
import Focus.Beta.IMPL.Shader.Shader;
import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.UTILS.render.RenderUtil;
import Focus.Beta.UTILS.render.RenderUtil2;
import Focus.Beta.UTILS.world.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import viamcp.gui.GuiProtocolSelector;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class MainMenu extends GuiScreen {
    public float lastPercent;
    public float percent;
    public float percent2;
    public float lastPercent2;
    public float upx;
    public float outro;
    public float lastOutro;
    boolean cangobackUP;
    Timer timer = new Timer();
    private float hHeight;
    private float hWidth;
    public Shader shader;
    public long initTime = System.currentTimeMillis();
    private float animatedMouseX;
    private float animatedMouseY;

    public MainMenu(){
        try{
            this.shader = new Shader("/Focus/Beta/IMPL/Shader/Menu.fsh");
        } catch (IOException e) {
            throw new IllegalStateException("Falied to load shader", e);
        }
    }

    public float smoothTrans(double current, double last){
        return (float) (current + (last - current) / (Minecraft.debugFPS / 10));
    }
    @Override
    public void initGui() {
        initTime = System.currentTimeMillis();
        percent = 1.33f;
        lastPercent = 1f;
        this.hHeight = 540.0F;
        this.hWidth = 960.0F;
        percent2 = 1.33f;
        lastPercent2 = 1f;
        outro = 1;
        lastOutro = 1;
        super.initGui();
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);

        this.mc.getTextureManager().bindTexture(new ResourceLocation("focus/JP.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(-21 + (animatedMouseX / 90), ((animatedMouseY * -1  / 90)), 0, 0, width + 20, height + 20, width + 21, height + 20);
        float outro = smoothTrans(this.outro, lastOutro);
        if (mc.currentScreen == null) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
            GlStateManager.scale(outro, outro, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
        }

        if(timer.hasElapsed(1, true)){
            if(upx < 40 && !cangobackUP)
                upx += 0.6f;
            else
                cangobackUP = true;

            if(cangobackUP == true){
                if(upx > 40)
                    upx -= 0.6f;
                else if(upx == 0)
                    cangobackUP = false;
            }
        }
        percent = smoothTrans(this.percent, lastPercent);
        percent2 = smoothTrans(this.percent2, lastPercent2);



        if (percent > 0.98) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
            GlStateManager.scale(percent, percent, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
        } else {
            if (percent2 <= 1) {
                GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
                GlStateManager.scale(percent2, percent2, 0);
                GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
            }
        }

        //FontLoaders.arial20.drawString("Discord", 16, 9, -1);
        RenderUtil2.drawRoundedRect( sr.getScaledWidth() - 625, 179, sr.getScaledWidth() - 480, sr.getScaledHeight() - 327 + 85, 0, new Color(0,0,0, 100).getRGB());
        RenderUtil2.drawRoundedRect( sr.getScaledWidth() - 625, 179, sr.getScaledWidth() - 480, sr.getScaledHeight() - 327, 0, new Color(0,0,0, 130).getRGB());
        FontLoaders.arial16.drawString(Client.instance.name, 338, 181.5f, -1);

        RenderUtil2.drawRoundedRect( 489, 179, sr.getScaledWidth() - 340, sr.getScaledHeight() - 327 + 58, 0, new Color(0,0,0, 100).getRGB());
        RenderUtil2.drawRoundedRect( 489, 179, sr.getScaledWidth() - 340, sr.getScaledHeight() - 327, 0, new Color(0,0,0, 130).getRGB());
        FontLoaders.arial16.drawString("Minecraft", 492, 181.5f, -1);

        /// RenderUtil2.drawRoundedRect( 489, 251, sr.getScaledWidth() - 340, sr.getScaledHeight() - 255, 0, new Color(0,0,0, 100).getRGB());
        // RenderUtil2.drawRoundedRect( 489, 251, sr.getScaledWidth() - 340, sr.getScaledHeight() - 255 + 32, 0, new Color(0,0,0, 130).getRGB());
        // FontLoaders.arial16.drawString("ViaVersion", 492, 181.5f + 72, -1);

//        RenderUtil2.drawRoundedRect( 489 + 3, 262, sr.getScaledWidth() - 340 - 3, sr.getScaledHeight() - 255 + 27, 0, new Color(0,0,0, 100).getRGB());
        //      FontLoaders.arial22.drawString("Change Version", 489 + 28, 255 + 10, -1);
        //OptionsButton
        RenderUtil2.drawRoundedRect( 489 + 3, 191, sr.getScaledWidth() - 340 - 4, sr.getScaledHeight() - 327 + 27, 0, new Color(0,0,0, 130).getRGB());
        FontLoaders.arial22.drawString("Options", 489 + 45, 194, -1);
        FontLoaders.arial16.drawString("Allows you to change options", 489 + 12, 191 + 14, -1);

        //ExitButton
        RenderUtil2.drawRoundedRect( 489 + 3, 217, sr.getScaledWidth() - 340 - 4, sr.getScaledHeight() - 327 + 54, 0, new Color(0,0,0, 130).getRGB());
        FontLoaders.arial22.drawString("Exit", 489 + 55, 220 , -1);
        FontLoaders.arial16.drawString("Close Minecraft", 489 + 35, 191 + 41, -1);

        //SinglePlayerButton
        RenderUtil2.drawRoundedRect( sr.getScaledWidth() - 619 - 3, 191, sr.getScaledWidth() - 480 - 4, sr.getScaledHeight() - 327 + 27, 0, new Color(0,0,0, 130).getRGB());
        FontLoaders.arial22.drawString("SinglePlayer", 335 + 39, 194, -1);
        FontLoaders.arial16.drawString("Allows you to play on offline worlds", 335 + 7, 191 + 14, -1);

        //MultiPlayerButton
        RenderUtil2.drawRoundedRect( sr.getScaledWidth() - 619 - 3, 217, sr.getScaledWidth() - 480 - 4, sr.getScaledHeight() - 327 + 54, 0, new Color(0,0,0, 130).getRGB());
        FontLoaders.arial22.drawString("MultiPlayer", 335 + 41, 220 , -1);
        FontLoaders.arial16.drawString("Allows you to play on online servers", 335 + 7, 191 + 41, -1);

        //AltManagerButton
        RenderUtil2.drawRoundedRect( sr.getScaledWidth() - 619 - 3, 217 + 27, sr.getScaledWidth() - 480 - 4, sr.getScaledHeight() - 327 + 80, 0, new Color(0,0,0, 130).getRGB());
        FontLoaders.arial22.drawString("AltManager", 335 + 41, 220 + 26, -1);
        FontLoaders.arial16.drawString("Allows you to change your account", 335 + 7, 191 + 41 + 26, -1);


        animatedMouseX += ((mouseX-animatedMouseX) / 1.8) + 0.1;
        animatedMouseY += ((mouseY-animatedMouseY) / 1.8) + 0.1;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(mouseX > 338 && mouseX < 471 && mouseY > 191 && mouseY < 215 && mouseButton == 0){
            System.out.println("Single");
            mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if(mouseX > 338 && mouseX < 471 && mouseY > 218 && mouseY < 239 && mouseButton == 0){
            System.out.println("Multi");
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if(mouseX > 338 && mouseX < 471 && mouseY > 245 && mouseY < 266 && mouseButton == 0){
            System.out.println("Alt");
            mc.displayGuiScreen(new GuiAltLogin(this));
        }
        if(mouseX > 492 && mouseX < 614&& mouseY > 191 && mouseY < 214 && mouseButton == 0){
            System.out.println("Options");
            this.mc.displayGuiScreen(new GuiProtocolSelector(this));
        }
        if(mouseX > 492 && mouseX < 614 && mouseY > 219 && mouseY < 240 && mouseButton == 0){
            System.out.println("Exit");
            mc.shutdown();
        }
        if(mouseButton == 0){
            System.out.println(mouseX +" " + mouseY);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
