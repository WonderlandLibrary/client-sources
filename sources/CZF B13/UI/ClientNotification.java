package gq.vapu.czfclient.UI;

import gq.vapu.czfclient.UI.Font.CFontRenderer;
import gq.vapu.czfclient.UI.Font.FontLoaders;
import gq.vapu.czfclient.Util.ClientUtil;
import gq.vapu.czfclient.Util.Render.Colors;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import gq.vapu.czfclient.Util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;


public class ClientNotification {
    Minecraft mc = Minecraft.getMinecraft();
    private final String message;
    private final TimeHelper timer;
    private double lastY;
    private double posY;
    private double width;
    private double height;
    private double animationX;
    private final int color;
    private int imageWidth;
    private final ResourceLocation image;
    private final long stayTime;

    public ClientNotification(final String message, final Type type) {
        this.message = message;
        (this.timer = new TimeHelper()).reset();
        final CFontRenderer font = FontLoaders.GoogleSans16;
        this.width = font.getStringWidth(message) + 20;
        this.height = 20.0;
        this.animationX = this.width;
        this.stayTime = 2000L;
        this.imageWidth = 16;
        this.posY = -1.0;
        this.image = new ResourceLocation("ClientRes/Notification/" + type.name() + ".png");
        this.color = Colors.DARKGREY.c;

//		this.color = new Color(255, 255, 255,220).getRGB();
    }

    public static int reAlpha(final int n, final float n2) {
        final Color color = new Color(n);
        return new Color(0.003921569f * color.getRed(), 0.003921569f * color.getGreen(), 0.003921569f * color.getBlue(), n2).getRGB();
    }

    public void draw(final double getY, final double lastY) {
        this.width = FontLoaders.GoogleSans16.getStringWidth(this.message) + 45;
        this.height = 22.0D;
        this.imageWidth = 11;
        this.lastY = lastY;
        this.animationX = RenderUtil.getAnimationState(this.animationX, this.isFinished() ? this.width : 0.0D, Math.max(this.isFinished() ? 200 : 30, Math.abs(this.animationX - (this.isFinished() ? this.width : 0.0D)) * 20.0D) * 0.3D);
        if (this.posY == -1.0D) {
            this.posY = getY;
        } else {
            this.posY = RenderUtil.getAnimationState(this.posY, getY, 200.0D);
        }

        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        int x1 = (int) ((double) res.getScaledWidth() - this.width + this.animationX);
        int x2 = (int) ((double) res.getScaledWidth() + this.animationX);
        int y1 = (int) this.posY - 22;
        int y2 = (int) ((double) y1 + this.height);
        RenderUtil.drawRect((float) x1, (float) y1, (float) x2, (float) y2, ClientUtil.reAlpha(this.color, 0.6F));
        //BlurUtil.blurAreaBoarder(x1, y1, x1, y1, 170);
        RenderUtil.drawImage(this.image, (int) ((double) x1 + (this.height - (double) this.imageWidth) / 2.0D) - 1, y1 + (int) ((this.height - (double) this.imageWidth) / 2.0D), this.imageWidth, this.imageWidth);
        ++y1;
        if (this.message.contains(" Enabled")) {
            FontLoaders.GoogleSans16.drawString(this.message.replace(" Enabled", ""), (float) (x1 + 19), (float) ((double) y1 + this.height / 4.0D), -1);
            FontLoaders.GoogleSans16.drawString(" Enabled", (float) (x1 + 20 + FontLoaders.GoogleSans16.getStringWidth(this.message.replace(" Enabled", ""))), (float) ((double) y1 + this.height / 4.0D), Colors.GREEN.c);
        } else if (this.message.contains(" Disabled")) {
            FontLoaders.GoogleSans16.drawString(this.message.replace(" Disabled", ""), (float) (x1 + 19), (float) ((double) y1 + this.height / 4.0D), -1);
            FontLoaders.GoogleSans16.drawString(" Disabled", (float) (x1 + 20 + FontLoaders.GoogleSans16.getStringWidth(this.message.replace(" Disabled", ""))), (float) ((double) y1 + this.height / 4.0D), Colors.RED.c);
        } else {
            float var10002 = (float) (x1 + 20);
            float var10003 = (float) ((double) y1 + this.height / 4.0D);
            FontLoaders.GoogleSans16.drawString(this.message, var10002, var10003, -1);
        }


//		this.lastY = lastY;
//		double Speed=Math.abs(this.animationX - (this.isFinished() ? this.width : 0.0)) * 20.0;
//		if(ClientUtil.notifications.size()>15) {
//			Speed=Math.abs(this.animationX - (this.isFinished() ? this.width : 0.0)) * 5.0*20;
//		}
//		this.animationX = RenderUtil.getAnimationState(this.animationX, this.isFinished() ? this.width : 0.0, Math.max(this.isFinished() ? 200 : 30,Speed));
//		if (this.posY == -1.0) {
//			this.posY = getY;
//		}
//		else {
//			this.posY = RenderUtil.getAnimationState(this.posY, getY, 300);
//		}
//		final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
//		final int x1 = (int)(res.getScaledWidth() - this.width + this.animationX - 15);
//		final int x2 = (int)(res.getScaledWidth() + this.animationX - 15);
//		final int y1 = (int)this.posY;
//		final int y2 = (int)(y1 + this.height);
//		Gui.drawRect(x1, y1, x2 + 20, y2, this.color);
//		 Gui.drawRect(x1, y2, x2 + 20, y2 + 0.5f, this.color);e
//		int rainbowTick = 0;
//		Color sense1 = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));
//		Gui.drawRect(x1, y2, x2 + 20, y2 + 0.5f, ClientUtil.reAlpha(1, 0.5f));
//		//Gui.drawRect(x1 + 20, y1 + 5, (int)(x1 + this.height) + 40, y2 + 5, sense1.getRGB());
//		Gui.drawRect(x1, y1, (int)(x1 + this.height) + 15, y2, ClientUtil.reAlpha(-1, 0.1f));
//		//Gui.drawRect(x1 + 5, y1 + 5, (int)(x1 + this.height) + 20, y2 + 5, sense1.getRGB());
//		//RenderUtil.drawImage(this.image, (int)(x1 + (this.height - this.imageWidth) / 1.3), y1 + (int)((this.height - this.imageWidth) / 2.0), this.imageWidth, this.imageWidth);
//		final CFontRenderer font =  FontLoaders.GoogleSans16;
//		font.drawString(this.message, (float)this.animationX+RenderUtil.width()-font.getStringWidth(message) - 5, (float)(y1 + this.height / 3.5)+ 3.0f, new Color(90, 90, 90).getRGB());
    }

    public boolean shouldDelete() {
        return this.isFinished() && this.animationX >= this.width;
    }

    private boolean isFinished() {
        return this.timer.isDelayComplete(this.stayTime) && this.posY == this.lastY;
    }

    public double getHeight() {
        return this.height;
    }

    public enum Type {
        SUCCESS("SUCCESS", 0),
        INFO("INFO", 1),
        WARNING("WARNING", 2),
        ERROR("ERROR", 3);

        Type(final String s, final int n) {
        }
    }
}

