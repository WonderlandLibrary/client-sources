package optfine;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class CloudRenderer
{
    float partialTicks;
    private double cloudPlayerX;
    private boolean renderFancy;
    private double cloudPlayerY;
    private Minecraft mc;
    private int cloudTickCounterUpdate;
    int cloudTickCounter;
    private double cloudPlayerZ;
    private int glListClouds;
    private boolean updated;
    
    public CloudRenderer(final Minecraft mc) {
        this.updated = ("".length() != 0);
        this.renderFancy = ("".length() != 0);
        this.glListClouds = -" ".length();
        this.cloudTickCounterUpdate = "".length();
        this.cloudPlayerX = 0.0;
        this.cloudPlayerY = 0.0;
        this.cloudPlayerZ = 0.0;
        this.mc = mc;
        this.glListClouds = GLAllocation.generateDisplayLists(" ".length());
    }
    
    public void startUpdateGlList() {
        GL11.glNewList(this.glListClouds, 1502 + 531 + 1584 + 1247);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean shouldUpdateGlList() {
        if (!this.updated) {
            return " ".length() != 0;
        }
        if (this.cloudTickCounter >= this.cloudTickCounterUpdate + (0x11 ^ 0x5)) {
            return " ".length() != 0;
        }
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        int n;
        if (this.cloudPlayerY + renderViewEntity.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f) {
            n = " ".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        int n3;
        if (renderViewEntity.prevPosY + renderViewEntity.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f) {
            n3 = " ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        return (n3 ^ n2) != 0x0;
    }
    
    public void renderGlList() {
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        final double n = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * this.partialTicks;
        final double n2 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * this.partialTicks;
        final double n3 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * this.partialTicks;
        final float n4 = (float)(n - this.cloudPlayerX + (this.cloudTickCounter - this.cloudTickCounterUpdate + this.partialTicks) * 0.03);
        final float n5 = (float)(n2 - this.cloudPlayerY);
        final float n6 = (float)(n3 - this.cloudPlayerZ);
        GlStateManager.pushMatrix();
        if (this.renderFancy) {
            GlStateManager.translate(-n4 / 12.0f, -n5, -n6 / 12.0f);
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            GlStateManager.translate(-n4, -n5, -n6);
        }
        GlStateManager.callList(this.glListClouds);
        GlStateManager.popMatrix();
        GlStateManager.resetColor();
    }
    
    public void prepareToRender(final boolean renderFancy, final int cloudTickCounter, final float partialTicks) {
        if (this.renderFancy != renderFancy) {
            this.updated = ("".length() != 0);
        }
        this.renderFancy = renderFancy;
        this.cloudTickCounter = cloudTickCounter;
        this.partialTicks = partialTicks;
    }
    
    public void reset() {
        this.updated = ("".length() != 0);
    }
    
    public void endUpdateGlList() {
        GL11.glEndList();
        this.cloudTickCounterUpdate = this.cloudTickCounter;
        this.cloudPlayerX = this.mc.getRenderViewEntity().prevPosX;
        this.cloudPlayerY = this.mc.getRenderViewEntity().prevPosY;
        this.cloudPlayerZ = this.mc.getRenderViewEntity().prevPosZ;
        this.updated = (" ".length() != 0);
        GlStateManager.resetColor();
    }
}
