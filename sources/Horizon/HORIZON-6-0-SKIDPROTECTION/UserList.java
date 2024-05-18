package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import java.io.BufferedWriter;
import java.util.Collection;
import java.io.Writer;
import org.apache.commons.io.IOUtils;
import com.google.common.io.Files;
import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.io.IOException;
import com.google.gson.GsonBuilder;
import com.google.common.collect.Maps;
import java.util.List;
import java.lang.reflect.Type;
import org.apache.logging.log4j.LogManager;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.io.File;
import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;

public class UserList
{
    protected static final Logger HorizonCode_Horizon_È;
    protected final Gson Â;
    private final File Ý;
    private final Map Ø­áŒŠá;
    private boolean Âµá€;
    private static final ParameterizedType Ó;
    private static final String à = "CL_00001876";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Ó = new ParameterizedType() {
            private static final String HorizonCode_Horizon_È = "CL_00001875";
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { UserListEntry.class };
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
    
    public UserList(final File saveFile) {
        this.Ø­áŒŠá = Maps.newHashMap();
        this.Âµá€ = true;
        this.Ý = saveFile;
        final GsonBuilder var2 = new GsonBuilder().setPrettyPrinting();
        var2.registerTypeHierarchyAdapter((Class)UserListEntry.class, (Object)new HorizonCode_Horizon_È(null));
        this.Â = var2.create();
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final boolean state) {
        this.Âµá€ = state;
    }
    
    public void HorizonCode_Horizon_È(final UserListEntry entry) {
        this.Ø­áŒŠá.put(this.Ý(entry.Ø­áŒŠá()), entry);
        try {
            this.Ø­áŒŠá();
        }
        catch (IOException var3) {
            UserList.HorizonCode_Horizon_È.warn("Could not save the list after adding a user.", (Throwable)var3);
        }
    }
    
    public UserListEntry HorizonCode_Horizon_È(final Object obj) {
        this.Âµá€();
        return this.Ø­áŒŠá.get(this.Ý(obj));
    }
    
    public void Â(final Object p_152684_1_) {
        this.Ø­áŒŠá.remove(this.Ý(p_152684_1_));
        try {
            this.Ø­áŒŠá();
        }
        catch (IOException var3) {
            UserList.HorizonCode_Horizon_È.warn("Could not save the list after removing a user.", (Throwable)var3);
        }
    }
    
    public String[] Â() {
        return (String[])this.Ø­áŒŠá.keySet().toArray(new String[this.Ø­áŒŠá.size()]);
    }
    
    protected String Ý(final Object obj) {
        return obj.toString();
    }
    
    protected boolean Ø­áŒŠá(final Object entry) {
        return this.Ø­áŒŠá.containsKey(this.Ý(entry));
    }
    
    private void Âµá€() {
        final ArrayList var1 = Lists.newArrayList();
        for (final UserListEntry var3 : this.Ø­áŒŠá.values()) {
            if (var3.Ý()) {
                var1.add(var3.Ø­áŒŠá());
            }
        }
        for (final Object var4 : var1) {
            this.Ø­áŒŠá.remove(var4);
        }
    }
    
    protected UserListEntry HorizonCode_Horizon_È(final JsonObject entryData) {
        return new UserListEntry(null, entryData);
    }
    
    protected Map Ý() {
        return this.Ø­áŒŠá;
    }
    
    public void Ø­áŒŠá() throws IOException {
        final Collection var1 = this.Ø­áŒŠá.values();
        final String var2 = this.Â.toJson((Object)var1);
        BufferedWriter var3 = null;
        try {
            var3 = Files.newWriter(this.Ý, Charsets.UTF_8);
            var3.write(var2);
        }
        finally {
            IOUtils.closeQuietly((Writer)var3);
        }
        IOUtils.closeQuietly((Writer)var3);
    }
    
    class HorizonCode_Horizon_È implements JsonDeserializer, JsonSerializer
    {
        private static final String Â = "CL_00001874";
        
        private HorizonCode_Horizon_È() {
        }
        
        public JsonElement HorizonCode_Horizon_È(final UserListEntry p_152751_1_, final Type p_152751_2_, final JsonSerializationContext p_152751_3_) {
            final JsonObject var4 = new JsonObject();
            p_152751_1_.HorizonCode_Horizon_È(var4);
            return (JsonElement)var4;
        }
        
        public UserListEntry HorizonCode_Horizon_È(final JsonElement p_152750_1_, final Type p_152750_2_, final JsonDeserializationContext p_152750_3_) {
            if (p_152750_1_.isJsonObject()) {
                final JsonObject var4 = p_152750_1_.getAsJsonObject();
                final UserListEntry var5 = UserList.this.HorizonCode_Horizon_È(var4);
                return var5;
            }
            return null;
        }
        
        public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            return this.HorizonCode_Horizon_È((UserListEntry)p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
        
        HorizonCode_Horizon_È(final UserList list, final Object p_i1141_2_) {
            this(list);
        }
    }
}
