package net.minecraft.util;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;

public class MotionBlurResource implements IResource {

    private final double strength;

    public MotionBlurResource(double strength) {
        this.strength = strength;
    }

    private static final String JSON = "{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"phosphor\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"Phosphor\",\"values\":[%.2f, %.2f, %.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}";

    public ResourceLocation getResourceLocation() {
        return null;
    }

    public InputStream getInputStream() {
        double amount = 0.7D + strength / 100.0D * 3.0D - 0.01D;
        return IOUtils.toInputStream(String.format(Locale.ENGLISH, JSON, amount, amount, amount), Charset.defaultCharset());
    }

    public boolean hasMetadata() {
        return false;
    }

    public <T extends IMetadataSection> T getMetadata(String p_110526_1_) {
        return null;
    }

    public String getResourcePackName() {
        return null;
    }
}