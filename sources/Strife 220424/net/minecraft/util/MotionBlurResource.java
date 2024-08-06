package net.minecraft.util;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;

public final class MotionBlurResource implements IResource {
    private static final String JSON = "{\n" +
            "  \"targets\": [\n" +
            "    \"swap\",\n" +
            "    \"previous\"\n" +
            "  ],\n" +
            "  \"passes\": [\n" +
            "    {\n" +
            "      \"name\": \"phosphor\",\n" +
            "      \"intarget\": \"minecraft:main\",\n" +
            "      \"outtarget\": \"swap\",\n" +
            "      \"auxtargets\": [\n" +
            "        {\n" +
            "          \"name\": \"PrevSampler\",\n" +
            "          \"id\": \"previous\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"uniforms\": [\n" +
            "        {\n" +
            "          \"name\": \"Phosphor\",\n" +
            "          \"values\": [\n" +
            "            %.2f,\n" +
            "            %.2f,\n" +
            "            %.2f\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"blit\",\n" +
            "      \"intarget\": \"swap\",\n" +
            "      \"outtarget\": \"previous\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"blit\",\n" +
            "      \"intarget\": \"swap\",\n" +
            "      \"outtarget\": \"minecraft:main\"\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";

    private final double strength;

    public MotionBlurResource(double strength) {
        this.strength = strength;
    }

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