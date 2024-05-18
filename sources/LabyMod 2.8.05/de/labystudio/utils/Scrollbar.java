package de.labystudio.utils;

import de.labystudio.labymod.LabyMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.input.Mouse;

public class Scrollbar
{
    private int listSize;
    private int entryHeight;
    private int scrollY;
    public double barLength;
    private double backLength;
    private int posTop;
    private int posBottom;
    private int left;
    public int top;
    private int right;
    private int speed = 1;
    int clickY;
    boolean hold;

    public int getScrollY()
    {
        return this.scrollY;
    }

    public void reset()
    {
        this.scrollY = 0;
    }

    public void setScrollY(int scrollY)
    {
        this.scrollY = scrollY;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public int getSpeed()
    {
        return this.speed;
    }

    public void init()
    {
        this.setDefaultPosition();
    }

    public void setEntryHeight(int entryHeight)
    {
        this.entryHeight = entryHeight;
    }

    public Scrollbar(int entryHeight)
    {
        this.entryHeight = entryHeight;
        this.setDefaultPosition();
    }

    public void update(int listSize)
    {
        this.listSize = listSize + 1;
    }

    public void setPosition(int left, int top, int right, int bottom)
    {
        this.left = left;
        this.posTop = top;
        this.right = right;
        this.posBottom = bottom;
        this.calc();
    }

    public void calc()
    {
        double d0 = (double)this.entryHeight;
        double d1 = (double)(this.listSize * this.entryHeight);
        double d2 = (double)(this.posBottom - this.posTop);

        if (d2 < d1)
        {
            double d3 = d2 / d1;
            double d4 = d3 * d2;
            double d5 = (double)this.scrollY / d3 * d3 * d3;
            this.top = (int)(-d5) + this.posTop;
            this.barLength = d4;
            this.backLength = d2;
        }
    }

    public void setDefaultPosition()
    {
        this.setPosition(LabyMod.getInstance().draw.getWidth() / 2 + 150, 40, LabyMod.getInstance().draw.getWidth() / 2 + 156, LabyMod.getInstance().draw.getHeight() - 40);
    }

    public boolean isHidden()
    {
        return this.listSize == 0 ? true : this.posBottom - this.posTop >= this.listSize * this.entryHeight;
    }

    public void draw()
    {
        if (!this.isHidden())
        {
            this.calc();
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos((double)this.left, (double)this.posBottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            worldrenderer.pos((double)this.right, (double)this.posBottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            worldrenderer.pos((double)this.right, (double)this.posTop, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            worldrenderer.pos((double)this.left, (double)this.posTop, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos((double)this.left, (double)this.top + this.barLength, 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
            worldrenderer.pos((double)this.right, (double)this.top + this.barLength, 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
            worldrenderer.pos((double)this.right, (double)this.top, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
            worldrenderer.pos((double)this.left, (double)this.top, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos((double)this.left, (double)this.top + this.barLength - 1.0D, 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
            worldrenderer.pos((double)(this.right - 1), (double)this.top + this.barLength - 1.0D, 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
            worldrenderer.pos((double)(this.right - 1), (double)this.top, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
            worldrenderer.pos((double)this.left, (double)this.top, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }

    public void mouseAction(int x, int y, boolean drag)
    {
        this.calc();
        boolean flag = false;

        if (!drag)
        {
            this.hold = false;

            if (x <= this.right && x >= this.left && y >= this.top && (double)y <= (double)this.top + this.barLength)
            {
                this.hold = true;
            }
            else
            {
                if (x > this.right || x < this.left || y < this.posTop || y > this.posBottom)
                {
                    return;
                }

                drag = true;
                flag = true;
            }
        }
        else if (!this.hold)
        {
            return;
        }

        int i = this.scrollY;
        double d0 = this.backLength / (double)(this.listSize * this.entryHeight);
        int j = (int)((double)(-y) / d0);

        if (drag)
        {
            this.scrollY = j - this.clickY;
        }
        else
        {
            this.clickY = j - this.scrollY;
        }

        if (this.listSize * this.entryHeight + this.scrollY < this.posBottom - this.posTop && !flag)
        {
            this.scrollY = i;
        }

        if (this.scrollY > 0 && !flag)
        {
            this.scrollY = i;
        }

        if (flag)
        {
            if (this.listSize * this.entryHeight + this.scrollY < this.posBottom - this.posTop)
            {
                this.scrollY += this.posBottom - this.posTop - (this.listSize * this.entryHeight + this.scrollY);
            }

            if (this.scrollY > 0)
            {
                this.scrollY = 0;
            }
        }
    }

    public void mouseInput()
    {
        int i = Mouse.getEventDWheel();

        if (i > 0)
        {
            if (this.scrollY < 0)
            {
                this.scrollY += this.speed;
            }
        }
        else if (i < 0 && this.listSize * this.entryHeight + this.scrollY > this.posBottom - this.posTop)
        {
            this.scrollY -= this.speed;
        }

        if (this.listSize * this.entryHeight + this.scrollY < this.posBottom - this.posTop)
        {
            this.scrollY += this.posBottom - this.posTop - (this.listSize * this.entryHeight + this.scrollY);
        }

        if (this.scrollY > 0)
        {
            this.scrollY = 0;
        }
    }
}
