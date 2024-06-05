package net.minecraft.client.resources;

import net.minecraft.util.*;
import java.io.*;
import java.util.*;

public interface IResourceManager
{
    Set getResourceDomains();
    
    IResource getResource(final ResourceLocation p0) throws IOException;
    
    List getAllResources(final ResourceLocation p0) throws IOException;
}
