package fr.dog.module.impl.render;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.event.impl.render.Render3DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.util.player.ChatUtil;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DamageIndicator extends Module {
    public DamageIndicator() {
        super("DamageIndicator", ModuleCategory.RENDER);
    }


    public ArrayList<Indicator> indicators = new ArrayList<>();

    @SubscribeEvent
    private void onRender2D(Render3DEvent event) {
        for(Indicator indicator : indicators){
            indicator.render();
        }
    }
    @SubscribeEvent
    private void onUpdate(PlayerTickEvent event) {




        for(Entity entity : mc.theWorld.loadedEntityList){
            if(entity instanceof EntityLivingBase player){
                if(player.lastHealth > player.getHealth()){
                    indicators.add(new Indicator(new DecimalFormat("#.##").format(player.lastHealth - player.getHealth()), (float) player.posX, (float) (player.posY + 2), (float) player.posZ,new Animation(Easing.EASE_IN_OUT_QUAD, 500)));
                }
                player.lastHealth = player.getHealth();
            }

        }
        ArrayList<Indicator> toRem = new ArrayList<>();
        for(Indicator indicator : indicators){
            if(indicator.animation.isFinished()){
                toRem.add(indicator);
            }
        }
        for(Indicator indicator : toRem){
            indicators.remove(indicator);
        }
    }


    @AllArgsConstructor
    @Getter
    @Setter
    public class Indicator {


        private String text;
        private float x,y,z;


        public Animation animation;
        public void render(){
            animation.run(1);


            float x = (float) (this.x - RenderManager.viewerPosX);
            float y = (float) (this.y + (float) animation.getValue() - RenderManager.viewerPosY);
            float z = (float) (this.z - RenderManager.viewerPosZ);

            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x, (float) y, (float) z);
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(-0.022f, -0.022f, -0.022f);
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            mc.fontRendererObj.drawString(text, (float) (-mc.fontRendererObj.getStringWidth(text) / 2), -3.0f, -1, true);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();


        }
    }
}
