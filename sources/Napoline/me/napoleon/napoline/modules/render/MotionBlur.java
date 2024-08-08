package me.napoleon.napoline.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import me.napoleon.napoline.events.EventChangeValue;
import me.napoleon.napoline.events.EventTick;
import me.napoleon.napoline.junk.values.Value;
import me.napoleon.napoline.junk.values.type.Num;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.modules.render.motionblur.resource.MotionBlurResourceManager;
import me.napoleon.napoline.utils.player.PlayerUtil;

/**
 * @author: Fyu
 * @description: 动态模糊
 * @create: 2020/12/29-17:04
 */
public class MotionBlur extends Mod {
    private Minecraft mc = Minecraft.getMinecraft();
    private Map domainResourceManagers;
    public static Num<Double> amount = new Num<>("Amount", 0.75, 0.0, 10.0);

    public MotionBlur(){
        super("MotionBlur", ModCategory.Render,"Just like name");
        this.addValues(MotionBlur.amount);
    }

    @Override
    public void onEnabled() {

        this.reloadShader();
    }

    @Override
    public void onDisable() {
        this.reloadShader();
    }

    @EventTarget
    public void onTick(EventTick event) {
        if(mc.theWorld == null)
            return;
        if (this.domainResourceManagers == null) {
            try {
                Field[] var2 = SimpleReloadableResourceManager.class.getDeclaredFields();
                int var3 = var2.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    Field field = var2[var4];
                    if (field.getType() == Map.class) {
                        field.setAccessible(true);
                        this.domainResourceManagers = (Map)field.get(Minecraft.getMinecraft().getResourceManager());
                        break;
                    }
                }
            } catch (Exception var6) {
                throw new RuntimeException(var6);
            }
        }

        if (!this.domainResourceManagers.containsKey("motionblur")) {
            this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
        }
    }

    @EventTarget
    public void onValueChanged(EventChangeValue event){
        if(mc.theWorld == null)
            return;
        switch (event.getMode()) {
            case BEFORE:
                break;
            case AFTER:
                Value<?> value = event.getValue();
                if (value == MotionBlur.amount) {
                    this.reloadShader();
                }
                break;
        }
    }

    public static boolean isFastRenderEnabled() {
        try {
            Field fastRender = GameSettings.class.getDeclaredField("ofFastRender");
            return fastRender.getBoolean(Minecraft.getMinecraft().gameSettings);
        } catch (Exception var1) {
            return false;
        }
    }

    public void reloadShader(){
        if(mc.theWorld == null)
            return;
        try {
            Method method = EntityRenderer.class.getDeclaredMethod("loadShader", ResourceLocation.class);
            method.setAccessible(true);
            method.invoke(this.mc.entityRenderer, new ResourceLocation("motionblur", "motionblur"));
            this.mc.entityRenderer.getShaderGroup().createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
        } catch (Throwable e) {
            PlayerUtil.sendMessage("Failed to load Motion blur.");
            e.printStackTrace();
        }
    }
}
