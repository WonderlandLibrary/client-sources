package club.bluezenith.module.modules.fun;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.NoObf;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.render.RenderUtil;
import club.bluezenith.events.Listener;
import fr.lavache.anime.Animate;
import fr.lavache.anime.Easing;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@SuppressWarnings("unused")
public class DeathScreen extends Module {
    public DeathScreen() {
        super("DeathScreen", ModuleCategory.FUN);
        this.displayName = "Death Screen";
    }
    Animate ImageAnim = new Animate().setEase(Easing.BOUNCE_OUT).setSpeed(300);
    Animate arrowAnim = new Animate().setEase(Easing.QUAD_IN_OUT).setSpeed(200).setMax(80).setMin(0);
    PositionedSoundRecord re = PositionedSoundRecord.create(new ResourceLocation("LMAOYOURDEADXDD"));
    PositionedSoundRecord er = PositionedSoundRecord.create(new ResourceLocation("epic fail"));
    boolean sexy = false;
    int alpha = 255;
    @NoObf
    @Listener
    public void render2D(Render2DEvent e){
        int w = 200;
        int h = 100;
        if(mc.thePlayer.getHealth() <= 0){
            updateAndSetLimits(e, h);
            float pX = e.resolution.getScaledWidth() / 2f - 101 - 30;
            float pY = e.resolution.getScaledHeight() - 49;
            RenderUtil.drawImage(new ResourceLocation("club/bluezenith/fun/jaja un meme.jpg"), e.resolution.getScaledWidth() / 2f - (w / 2f), ImageAnim.getValue(), w,h, 1);
            RenderUtil.drawScaledFont(FontUtil.comicSans42, "epic fail XDDDDD", e.resolution.getScaledWidth() / 2f - (mc.fontRendererObj.getStringWidthF("epic fail XDDDDD") * 4 / 2), ImageAnim.getValue() - (h / 4f) - (mc.fontRendererObj.FONT_HEIGHT * 4 / 2f) - 2, Color.WHITE.getRGB(),true, 4);
            RenderUtil.drawImage(new ResourceLocation("club/bluezenith/fun/red.png"), e.resolution.getScaledWidth() / 2f - 101, e.resolution.getScaledHeight() - 59, 100, 50, alpha / 255f);
            drawArrows(pX, pY);

            if(!sexy){
                mc.getSoundHandler().playSound(re);
                mc.getSoundHandler().playSound(er);
                sexy = true;
            }
        }else{
            if(sexy){
                mc.getSoundHandler().stopSound(re);
                mc.getSoundHandler().stopSound(er);
                sexy = false;
            }
            ImageAnim.reset();
            alpha = 255;
        }
    }
    @Override
    public void onEnable(){
        ImageAnim.reset();
    }
    public void drawArrows(float x, float y){
        RenderUtil.drawImage(new ResourceLocation("club/bluezenith/fun/arrow.jpg"), x - 15 - arrowAnim.getValue(), y, 60, 30, 1);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 30 - arrowAnim.getValue(), y - 15 - arrowAnim.getValue(), 0);
        GL11.glRotatef(45, 0, 0, 0.1f);
        RenderUtil.drawImage(new ResourceLocation("club/bluezenith/fun/arrow.jpg"), -30, -15, 60, 30, 1);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 75, y - 30 - arrowAnim.getValue(), 0);
        GL11.glRotatef(90, 0, 0, 0.1f);
        RenderUtil.drawImage(new ResourceLocation("club/bluezenith/fun/arrow.jpg"), -30, -15, 60, 30, 1);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 124 + arrowAnim.getValue(), y - 15 - arrowAnim.getValue(), 0);
        GL11.glRotatef(135, 0, 0, 0.1f);
        RenderUtil.drawImage(new ResourceLocation("club/bluezenith/fun/arrow.jpg"), -30, -15, 60, 30, 1);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 139 + arrowAnim.getValue(), y + 15, 0);
        GL11.glRotatef(180, 0, 0, 0.1f);
        RenderUtil.drawImage(new ResourceLocation("club/bluezenith/fun/arrow.jpg"), -30, -15, 60, 30, 1);
        GlStateManager.popMatrix();
    }
    @NoObf
    public void updateAndSetLimits(Render2DEvent e, float h){
        ImageAnim.setMax(e.resolution.getScaledHeight() / 2f - (h / 2f)).update();
        alpha = MathHelper.clamp((int) (alpha - 0.2 * RenderUtil.delta), 0, 255);
        if(alpha < 1)
            alpha = 255;

        if(arrowAnim.getValue() >= arrowAnim.getMax() && !arrowAnim.isReversed()){
            arrowAnim.setReversed(true);
        }else if(arrowAnim.getValue() <= arrowAnim.getMin() && arrowAnim.isReversed()){
            arrowAnim.setReversed(false);
        }
        arrowAnim.update();
    }
    /*
    float lol = 50;
    Animate[] anims = new Animate[]{
        new Animate().setEase(Easing.QUAD_IN).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.QUAD_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.QUAD_IN_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.CUBIC_IN).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.CUBIC_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.CUBIC_IN_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.QUARTIC_IN).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.QUARTIC_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.QUARTIC_IN_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.QUINTIC_IN).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.QUINTIC_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.QUINTIC_IN_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.SINE_IN).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.SINE_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.SINE_IN_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.EXPO_IN).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.EXPO_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.EXPO_IN_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.CIRC_IN).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.CIRC_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.CIRC_IN_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.ELASTIC_IN).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.ELASTIC_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.ELASTIC_IN_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.BACK_IN).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.BACK_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.BACK_IN_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.BOUNCE_OUT).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.BOUNCE_IN).setMax(200).setSpeed(lol),
        new Animate().setEase(Easing.BOUNCE_IN_OUT).setMax(200).setSpeed(lol),
    };

    @Listenerr
    public void render2D(Render2DEvent e){
        float y = 0;
        int sex = 0;
        for (Animate a : anims) {
            a.update();
            RenderUtil.rect(a.getValue(), y, a.getValue() + 10, y + 10, Color.WHITE);
            mc.fontRendererObj.drawString(sex+"",a.getValue(), y, Color.BLACK.getRGB());
            y += 10;
            sex++;
        }
    }
    @Listenerr
    public void onPacket(PacketEvent e){
        if(e.packet instanceof C01PacketChatMessage){
            C01PacketChatMessage p = (C01PacketChatMessage) e.packet;
            if(p.getMessage().equals("back")){
                for (Animate a : anims) {
                    a.setReversed(!a.isReversed());
                }
                e.cancel();
            }
            if(p.getMessage().equals("reset")){
                for (Animate a : anims) {
                    a.reset();
                }
                e.cancel();
            }
        }
    }*/
}
