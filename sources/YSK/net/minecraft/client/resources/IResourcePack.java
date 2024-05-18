package net.minecraft.client.resources;

import net.minecraft.client.resources.data.*;
import net.minecraft.util.*;
import java.util.*;
import java.awt.image.*;
import java.io.*;

public interface IResourcePack
{
     <T extends IMetadataSection> T getPackMetadata(final IMetadataSerializer p0, final String p1) throws IOException;
    
    boolean resourceExists(final ResourceLocation p0);
    
    Set<String> getResourceDomains();
    
    String getPackName();
    
    BufferedImage getPackImage() throws IOException;
    
    InputStream getInputStream(final ResourceLocation p0) throws IOException;
}
