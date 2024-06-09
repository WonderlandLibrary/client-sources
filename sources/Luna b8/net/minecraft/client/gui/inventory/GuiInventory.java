package net.minecraft.client.gui.inventory;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.zCore.Render.Particles.ParticleGenerator;

import lunadevs.luna.Connector.ParticleSync.Particles.Particle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class GuiInventory extends InventoryEffectRenderer
{
    /** The old x position of the mouse pointer */
    private float oldMouseX;
 // TODO Particle
 	private ParticleGenerator particles;
 	private Random random = new Random();

    /** The old y position of the mouse pointer */
    private float oldMouseY;
    private static final String __OBFID = "CL_00000761";

    public GuiInventory(EntityPlayer p_i1094_1_)
    {
        super(p_i1094_1_.inventoryContainer);
        this.allowUserInput = true;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }

        this.func_175378_g();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();

        if (this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
        else
        {
            super.initGui();
        }
     // TODO Particle
     		this.particles = new ParticleGenerator(100, this.width, this.height);

     	}

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items). Args : mouseX, mouseY
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;
     // TODO Particle

		
     	/**	for (Particle p : particles.particles) {
     			for (Particle p2 : particles.particles) {
     				int xx = (int) (MathHelper.cos(0.1F * (p.x + p.k)) * 10.0F);
     				int xx2 = (int) (MathHelper.cos(0.1F * (p2.x + p2.k)) * 10.0F);

     				boolean mouseOver = (mouseX >= p.x + xx - 95) && (mouseY >= p.y - 90) && (mouseX <= p.x)
     						&& (mouseY <= p.y);

     				if (mouseOver) {
     					if (mouseY >= p.y - 80 && mouseX >= p2.x - 100 && mouseY >= p2.y && mouseY <= p2.y + 70
     							&& mouseX <= p2.x) {

     						int maxDistance = 100;

     						final int xDif = p.x - mouseX;
     						final int yDif = p.y - mouseY;
     						final int distance = (int) Math.sqrt(xDif * xDif + yDif + yDif);

     						final int xDif1 = p2.x - mouseX;
     						final int yDif1 = p2.y - mouseY;
     						final int distance2 = (int) Math.sqrt(xDif1 * xDif1 + yDif1 + yDif1);

     						if (distance < maxDistance && distance2 < maxDistance) {

     							GL11.glPushMatrix();
     							GL11.glEnable(GL11.GL_LINE_SMOOTH);
     							GL11.glDisable(GL11.GL_DEPTH_TEST);
     							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
     							GL11.glDisable(GL11.GL_TEXTURE_2D);
     							GL11.glDepthMask(false);
     							GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
     							GL11.glEnable(GL11.GL_BLEND);
     							GL11.glLineWidth(1.5F);
     							GL11.glBegin(GL11.GL_LINES);

     							GL11.glVertex2d(p.x + xx, p.y);
     							GL11.glVertex2d(p2.x + xx2, p2.y);
     							GL11.glEnd();
     							GL11.glPopMatrix();

     							if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
     								if (p2.x > mouseX) {
     									p2.y -= random.nextInt(5);
     								}
     								if (p2.y < mouseY) {
     									p2.x += random.nextInt(5);
     								}

     							}

     						}
     					}
     				}
     			}

     		}

     		this.particles.drawParticles();*/

    }

    /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(inventoryBackground);
        int var4 = this.guiLeft;
        int var5 = this.guiTop;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        drawEntityOnScreen(var4 + 51, var5 + 75, 30, (float)(var4 + 51) - this.oldMouseX, (float)(var5 + 75 - 50) - this.oldMouseY, this.mc.thePlayer);
    }

    /**
     * Draws the entity to the screen. Args: xPos, yPos, scale, mouseX, mouseY, entityLiving
     */
    public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)p_147046_0_, (float)p_147046_1_, 50.0F);
        GlStateManager.scale((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float var6 = p_147046_5_.renderYawOffset;
        float var7 = p_147046_5_.rotationYaw;
        float var8 = p_147046_5_.rotationPitch;
        float var9 = p_147046_5_.prevRotationYawHead;
        float var10 = p_147046_5_.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        p_147046_5_.renderYawOffset = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 20.0F;
        p_147046_5_.rotationYaw = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 40.0F;
        p_147046_5_.rotationPitch = -((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
        var11.func_178631_a(180.0F);
        var11.func_178633_a(false);
        var11.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        var11.func_178633_a(true);
        p_147046_5_.renderYawOffset = var6;
        p_147046_5_.rotationYaw = var7;
        p_147046_5_.rotationPitch = var8;
        p_147046_5_.prevRotationYawHead = var9;
        p_147046_5_.rotationYawHead = var10;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.func_179090_x();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
        }
    }
}