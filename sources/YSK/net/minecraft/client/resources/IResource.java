package net.minecraft.client.resources;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.resources.data.*;

public interface IResource
{
    String getResourcePackName();
    
    ResourceLocation getResourceLocation();
    
    InputStream getInputStream();
    
     <T extends IMetadataSection> T getMetadata(final String p0);
    
    boolean hasMetadata();
}
