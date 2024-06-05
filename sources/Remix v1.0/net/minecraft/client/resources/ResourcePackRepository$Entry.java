package net.minecraft.client.resources;

import net.minecraft.client.resources.data.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import org.apache.commons.io.*;
import net.minecraft.util.*;

public class Entry
{
    private final File resourcePackFile;
    private IResourcePack reResourcePack;
    private PackMetadataSection rePackMetadataSection;
    private BufferedImage texturePackIcon;
    private ResourceLocation locationTexturePackIcon;
    
    private Entry(final File p_i1295_2_) {
        this.resourcePackFile = p_i1295_2_;
    }
    
    Entry(final ResourcePackRepository this$0, final File p_i1296_2_, final Object p_i1296_3_) {
        this(this$0, p_i1296_2_);
    }
    
    public void updateResourcePack() throws IOException {
        this.reResourcePack = (this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile));
        this.rePackMetadataSection = (PackMetadataSection)this.reResourcePack.getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack");
        try {
            this.texturePackIcon = this.reResourcePack.getPackImage();
        }
        catch (IOException ex) {}
        if (this.texturePackIcon == null) {
            this.texturePackIcon = ResourcePackRepository.this.rprDefaultResourcePack.getPackImage();
        }
        this.closeResourcePack();
    }
    
    public void bindTexturePackIcon(final TextureManager p_110518_1_) {
        if (this.locationTexturePackIcon == null) {
            this.locationTexturePackIcon = p_110518_1_.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
        }
        p_110518_1_.bindTexture(this.locationTexturePackIcon);
    }
    
    public void closeResourcePack() {
        if (this.reResourcePack instanceof Closeable) {
            IOUtils.closeQuietly((Closeable)this.reResourcePack);
        }
    }
    
    public IResourcePack getResourcePack() {
        return this.reResourcePack;
    }
    
    public String getResourcePackName() {
        return this.reResourcePack.getPackName();
    }
    
    public String getTexturePackDescription() {
        return (this.rePackMetadataSection == null) ? (EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)") : this.rePackMetadataSection.func_152805_a().getFormattedText();
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return this == p_equals_1_ || (p_equals_1_ instanceof Entry && this.toString().equals(p_equals_1_.toString()));
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("%s:%s:%d", this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", this.resourcePackFile.lastModified());
    }
}
