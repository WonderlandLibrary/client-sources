package net.minecraft.client.resources;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.resources.data.*;
import com.google.common.collect.*;
import java.io.*;
import com.google.gson.*;
import org.apache.commons.io.*;

public class SimpleResource implements IResource
{
    private JsonObject mcmetaJson;
    private final InputStream mcmetaInputStream;
    private final IMetadataSerializer srMetadataSerializer;
    private final ResourceLocation srResourceLocation;
    private final String resourcePackName;
    private final Map<String, IMetadataSection> mapMetadataSections;
    private final InputStream resourceInputStream;
    private boolean mcmetaJsonChecked;
    
    public SimpleResource(final String resourcePackName, final ResourceLocation srResourceLocation, final InputStream resourceInputStream, final InputStream mcmetaInputStream, final IMetadataSerializer srMetadataSerializer) {
        this.mapMetadataSections = (Map<String, IMetadataSection>)Maps.newHashMap();
        this.resourcePackName = resourcePackName;
        this.srResourceLocation = srResourceLocation;
        this.resourceInputStream = resourceInputStream;
        this.mcmetaInputStream = mcmetaInputStream;
        this.srMetadataSerializer = srMetadataSerializer;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getResourcePackName() {
        return this.resourcePackName;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof SimpleResource)) {
            return "".length() != 0;
        }
        final SimpleResource simpleResource = (SimpleResource)o;
        if (this.srResourceLocation != null) {
            if (!this.srResourceLocation.equals(simpleResource.srResourceLocation)) {
                return "".length() != 0;
            }
        }
        else if (simpleResource.srResourceLocation != null) {
            return "".length() != 0;
        }
        if (this.resourcePackName != null) {
            if (!this.resourcePackName.equals(simpleResource.resourcePackName)) {
                return "".length() != 0;
            }
        }
        else if (simpleResource.resourcePackName != null) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public boolean hasMetadata() {
        if (this.mcmetaInputStream != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public InputStream getInputStream() {
        return this.resourceInputStream;
    }
    
    @Override
    public <T extends IMetadataSection> T getMetadata(final String s) {
        if (!this.hasMetadata()) {
            return null;
        }
        if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
            this.mcmetaJsonChecked = (" ".length() != 0);
            Reader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
                this.mcmetaJson = new JsonParser().parse(reader).getAsJsonObject();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            finally {
                IOUtils.closeQuietly(reader);
            }
            IOUtils.closeQuietly(reader);
        }
        IMetadataSection metadataSection = this.mapMetadataSections.get(s);
        if (metadataSection == null) {
            metadataSection = this.srMetadataSerializer.parseMetadataSection(s, this.mcmetaJson);
        }
        return (T)metadataSection;
    }
    
    @Override
    public int hashCode() {
        int n;
        if (this.resourcePackName != null) {
            n = this.resourcePackName.hashCode();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = (0x99 ^ 0x86) * n;
        int n3;
        if (this.srResourceLocation != null) {
            n3 = this.srResourceLocation.hashCode();
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        return n2 + n3;
    }
    
    @Override
    public ResourceLocation getResourceLocation() {
        return this.srResourceLocation;
    }
}
