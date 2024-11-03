package net.silentclient.client.utils.resource;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;

import org.apache.commons.io.IOUtils;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.mods.render.ColorSaturationMod;

public class SaturationResource implements IResource {
	private static final String JSON =
	        "{\r\n"
	        + "    \"targets\": [\r\n"
	        + "        \"swap\"\r\n"
	        + "    ],\r\n"
	        + "    \"passes\": [\r\n"
	        + "        {\r\n"
	        + "            \"name\": \"color_convolve\",\r\n"
	        + "            \"intarget\": \"minecraft:main\",\r\n"
	        + "            \"outtarget\": \"swap\",\r\n"
	        + "            \"uniforms\": [\r\n"
	        + "                { \"name\": \"Saturation\", \"values\": [ %.2f ] }\r\n"
	        + "            ]\r\n"
	        + "        },\r\n"
	        + "        {\r\n"
	        + "            \"name\": \"blit\",\r\n"
	        + "            \"intarget\": \"swap\",\r\n"
	        + "            \"outtarget\": \"minecraft:main\"\r\n"
	        + "        }\r\n"
	        + "    ]\r\n"
	        + "}\r\n"
	        + "";

	    @Override
	    public ResourceLocation getResourceLocation() {
	        return null;
	    }

	    @Override
	    public InputStream getInputStream() {
	        double amount = Client.getInstance().getSettingsManager().getSettingByClass(ColorSaturationMod.class, "Amount").getValDouble();
	        return IOUtils.toInputStream(
	            String.format(Locale.ENGLISH, JSON, amount, amount, amount), Charset.defaultCharset());
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
