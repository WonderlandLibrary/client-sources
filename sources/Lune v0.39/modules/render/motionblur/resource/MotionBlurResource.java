package me.superskidder.lune.modules.render.motionblur.resource;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.render.MotionBlur;
import me.superskidder.lune.manager.ModuleManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Locale;

/**
 * @author: Fyu
 * @description:
 * @create: 2020/12/29-17:09
 */
public class MotionBlurResource implements IResource {
    private static final String JSON = "{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"phosphor\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"Phosphor\",\"values\":[%.2f, %.2f, %.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}";

    @Override
    public ResourceLocation getResourceLocation() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        Mod motionBlur = ModuleManager.getModByClass(MotionBlur.class);
        double amount = 0.7D + MotionBlur.amount.getValue() / 100.0D * 3.0D - 0.01D;
        if(!motionBlur.getState()){
            amount = 0.7D + 0 / 100.0D * 3.0D - 0.01D;
        }
        return IOUtils.toInputStream(String.format(Locale.ENGLISH, "{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"phosphor\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"Phosphor\",\"values\":[%.2f, %.2f, %.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}", amount, amount, amount));
    }

    @Override
    public boolean hasMetadata() {
        return false;
    }

    @Override
    public <T extends IMetadataSection> T getMetadata(String p_110526_1_) {
        return null;
    }

    @Override
    public String getResourcePackName() {
        return null;
    }
}
