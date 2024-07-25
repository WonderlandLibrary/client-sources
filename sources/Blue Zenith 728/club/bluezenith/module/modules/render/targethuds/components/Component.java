package club.bluezenith.module.modules.render.targethuds.components;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.targethuds.components.provider.ColorProvider;
import club.bluezenith.module.modules.render.targethuds.components.provider.ProgressProvider;
import club.bluezenith.module.modules.render.targethuds.components.provider.WidthProvider;
import net.minecraft.entity.player.EntityPlayer;

public abstract class Component {

    protected boolean resetWidth, resetHeight;
    protected float width = -1F, height = -1F;
    protected float posX, posY;
    public EntityPlayer target;
    public EntityPlayer prevTarget;
    protected ColorProvider colorProvider;
    protected WidthProvider widthProvider;
    protected ProgressProvider progressProvider;

    public abstract void render(Render2DEvent event, TargetHUD targetHUD);

    public Component target(EntityPlayer target) {
        if(this.target != target) {
            prevTarget = this.target;
            this.target = target;
        }
        return this;
    }

    public Component posX(float posX) {
        this.posX = posX;
        return this;
    }

    public Component posY(float posY) {
        this.posY = posY;
        return this;
    }

    public Component width(float width) {
        if(this.width == -1F || resetWidth)
        this.width = width;
        return this;
    }

    public Component height(float height) {
        if(this.height == -1F || resetHeight)
        this.height = height;
        return this;
    }

    public Component resetWidth() {
        this.resetWidth = true;
        return this;
    }

    public Component resetHeight() {
        this.resetHeight = true;
        return this;
    }

    public Component widthProvider(WidthProvider widthProvider) {
        this.widthProvider = widthProvider;
        return this;
    }

    public Component colorProvider(ColorProvider colorProvider) {
        this.colorProvider = colorProvider;
        return this;
    }

    public Component progress(ProgressProvider progressProvider) {
        this.progressProvider = progressProvider;
        return this;
    }

}
