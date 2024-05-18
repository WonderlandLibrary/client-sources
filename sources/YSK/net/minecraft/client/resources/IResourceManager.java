package net.minecraft.client.resources;

import net.minecraft.util.*;
import java.util.*;
import java.io.*;

public interface IResourceManager
{
    Set<String> getResourceDomains();
    
    List<IResource> getAllResources(final ResourceLocation p0) throws IOException;
    
    IResource getResource(final ResourceLocation p0) throws IOException;
}
