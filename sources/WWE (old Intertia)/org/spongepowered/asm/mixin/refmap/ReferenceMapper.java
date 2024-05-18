package org.spongepowered.asm.mixin.refmap;

import com.google.common.collect.*;
import java.util.*;
import org.spongepowered.asm.service.*;
import org.apache.commons.io.*;
import org.apache.logging.log4j.*;
import java.io.*;
import com.google.gson.*;

public final class ReferenceMapper implements IReferenceMapper, Serializable
{
    private static final long serialVersionUID = 2L;
    public static final String DEFAULT_RESOURCE = "mixin.refmap.json";
    public static final ReferenceMapper DEFAULT_MAPPER;
    private final Map<String, Map<String, String>> mappings;
    private final Map<String, Map<String, Map<String, String>>> data;
    private final transient boolean readOnly;
    private transient String context;
    private transient String resource;
    
    public ReferenceMapper() {
        this(false, "mixin.refmap.json");
    }
    
    private ReferenceMapper(final boolean readOnly, final String resource) {
        super();
        this.mappings = (Map<String, Map<String, String>>)Maps.newHashMap();
        this.data = (Map<String, Map<String, Map<String, String>>>)Maps.newHashMap();
        this.context = null;
        this.readOnly = readOnly;
        this.resource = resource;
    }
    
    @Override
    public boolean isDefault() {
        return this.readOnly;
    }
    
    private void setResourceName(final String s) {
        if (!this.readOnly) {
            this.resource = ((s != null) ? s : "<unknown resource>");
        }
    }
    
    @Override
    public String getResourceName() {
        return this.resource;
    }
    
    @Override
    public String getStatus() {
        return this.isDefault() ? "No refMap loaded." : ("Using refmap " + this.getResourceName());
    }
    
    @Override
    public String getContext() {
        return this.context;
    }
    
    @Override
    public void setContext(final String context) {
        this.context = context;
    }
    
    @Override
    public String remap(final String s, final String s2) {
        return this.remapWithContext(this.context, s, s2);
    }
    
    @Override
    public String remapWithContext(final String s, final String s2, final String s3) {
        Map<String, Map<String, String>> map = this.mappings;
        if (s != null) {
            map = this.data.get(s);
            if (map == null) {
                map = this.mappings;
            }
        }
        return this.remap(map, s2, s3);
    }
    
    private String remap(final Map<String, Map<String, String>> map, final String s, final String s2) {
        if (s == null) {
            for (final Map<String, String> map2 : map.values()) {
                if (map2.containsKey(s2)) {
                    return map2.get(s2);
                }
            }
        }
        final Map<String, String> map3 = map.get(s);
        if (map3 == null) {
            return s2;
        }
        final String s3 = map3.get(s2);
        return (s3 != null) ? s3 : s2;
    }
    
    public String addMapping(final String s, final String s2, final String s3, final String s4) {
        if (this.readOnly || s3 == null || s4 == null || s3.equals(s4)) {
            return null;
        }
        Object o = this.mappings;
        if (s != null) {
            o = this.data.get(s);
            if (o == null) {
                o = Maps.newHashMap();
                this.data.put(s, (Map<String, Map<String, String>>)o);
            }
        }
        Map<String, String> map = ((Map<String, Map<String, String>>)o).get(s2);
        if (map == null) {
            map = new HashMap<String, String>();
            ((Map<String, Map<String, String>>)o).put(s2, map);
        }
        return map.put(s3, s4);
    }
    
    public void write(final Appendable writer) {
        new GsonBuilder().setPrettyPrinting().create().toJson(this, writer);
    }
    
    public static ReferenceMapper read(final String resourceName) {
        final Logger logger = LogManager.getLogger("mixin");
        Reader input = null;
        try {
            final InputStream resourceAsStream = MixinService.getService().getResourceAsStream(resourceName);
            if (resourceAsStream != null) {
                input = new InputStreamReader(resourceAsStream);
                final ReferenceMapper json = readJson(input);
                json.setResourceName(resourceName);
                return json;
            }
            return ReferenceMapper.DEFAULT_MAPPER;
        }
        catch (JsonParseException ex) {
            logger.error("Invalid REFMAP JSON in " + resourceName + ": " + ex.getClass().getName() + " " + ex.getMessage());
        }
        catch (Exception ex2) {
            logger.error("Failed reading REFMAP JSON from " + resourceName + ": " + ex2.getClass().getName() + " " + ex2.getMessage());
        }
        finally {
            IOUtils.closeQuietly(input);
        }
        return ReferenceMapper.DEFAULT_MAPPER;
    }
    
    public static ReferenceMapper read(final Reader reader, final String resourceName) {
        try {
            final ReferenceMapper json = readJson(reader);
            json.setResourceName(resourceName);
            return json;
        }
        catch (Exception ex) {
            return ReferenceMapper.DEFAULT_MAPPER;
        }
    }
    
    private static ReferenceMapper readJson(final Reader json) {
        return new Gson().fromJson(json, ReferenceMapper.class);
    }
    
    static {
        DEFAULT_MAPPER = new ReferenceMapper(true, "invalid");
    }
}
