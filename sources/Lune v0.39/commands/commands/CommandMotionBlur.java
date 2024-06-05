package me.superskidder.lune.commands.commands;

import me.superskidder.lune.modules.render.MotionBlur;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.utils.player.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.reflect.Method;

/**
 * @author: Fyu
 * @description:
 * @create: 2020/12/29-17:11
 */
public class CommandMotionBlur extends Command {
    private Minecraft mc = Minecraft.getMinecraft();

    public CommandMotionBlur(){
        super("MotionBlur");
    }

    @Override
    public void run(String[] args) {
        if (args.length == 0) {
            PlayerUtil.sendMessage("Usage: .motionblur <0 - 10>.");;
        } else {
            int amount = NumberUtils.toInt(args[0], -1);
            if (amount >= 0 && amount <= 10) {
                if (MotionBlur.isFastRenderEnabled()) {
                    PlayerUtil.sendMessage("Motion blur does not work if Fast Render is enabled, please disable it in Options > Video Settings > Performance.");
                } else {
                    if (this.mc.entityRenderer.getShaderGroup() != null) {
                        this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                    }

                    if (amount != 0) {
                        MotionBlur.amount.setValue((double) amount);

                        try {
                            Method method = EntityRenderer.class.getDeclaredMethod("loadShader", ResourceLocation.class);
                            method.setAccessible(true);
                            method.invoke(this.mc.entityRenderer, new ResourceLocation("motionblur", "motionblur"));
                            this.mc.entityRenderer.getShaderGroup().createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
                            PlayerUtil.sendMessage("Motion blur enabled.");
                        } catch (Throwable var5) {
                            PlayerUtil.sendMessage("Failed to enable Motion blur.");
                            var5.printStackTrace();
                        }
                    } else {
                        ModuleManager.getModByClass(MotionBlur.class).setStage(false);
                        PlayerUtil.sendMessage("Motion blur disabled.");
                    }
                }
            } else {
                PlayerUtil.sendMessage("Invalid amount.");
            }
        }

    }
}
