package net.minecraft.client.resources;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.resources.data.*;

public interface IResource
{
    ResourceLocation func_177241_a();
    
    InputStream getInputStream();
    
    boolean hasMetadata();
    
    IMetadataSection getMetadata(final String p0);
    
    String func_177240_d();
}
