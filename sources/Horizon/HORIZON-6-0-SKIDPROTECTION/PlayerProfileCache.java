package HORIZON-6-0-SKIDPROTECTION;

import java.text.ParseException;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import com.google.common.collect.Iterators;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.IOException;
import java.util.Iterator;
import java.io.BufferedReader;
import org.apache.commons.io.IOUtils;
import java.io.FileNotFoundException;
import java.io.Reader;
import com.google.common.io.Files;
import com.google.common.base.Charsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import com.mojang.authlib.Agent;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.GameProfile;
import com.google.gson.GsonBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.io.File;
import com.google.gson.Gson;
import java.util.LinkedList;
import java.util.Map;
import java.text.SimpleDateFormat;

public class PlayerProfileCache
{
    public static final SimpleDateFormat HorizonCode_Horizon_È;
    private final Map Ý;
    private final Map Ø­áŒŠá;
    private final LinkedList Âµá€;
    private final MinecraftServer Ó;
    protected final Gson Â;
    private final File à;
    private static final ParameterizedType Ø;
    private static final String áŒŠÆ = "CL_00001888";
    
    static {
        HorizonCode_Horizon_È = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        Ø = new ParameterizedType() {
            private static final String HorizonCode_Horizon_È = "CL_00001886";
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { HorizonCode_Horizon_È.class };
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
    
    public PlayerProfileCache(final MinecraftServer p_i1171_1_, final File p_i1171_2_) {
        this.Ý = Maps.newHashMap();
        this.Ø­áŒŠá = Maps.newHashMap();
        this.Âµá€ = Lists.newLinkedList();
        this.Ó = p_i1171_1_;
        this.à = p_i1171_2_;
        final GsonBuilder var3 = new GsonBuilder();
        var3.registerTypeHierarchyAdapter((Class)HorizonCode_Horizon_È.class, (Object)new Â(null));
        this.Â = var3.create();
        this.Â();
    }
    
    private static GameProfile HorizonCode_Horizon_È(final MinecraftServer p_152650_0_, final String p_152650_1_) {
        final GameProfile[] var2 = { null };
        final ProfileLookupCallback var3 = (ProfileLookupCallback)new ProfileLookupCallback() {
            private static final String HorizonCode_Horizon_È = "CL_00001887";
            
            public void onProfileLookupSucceeded(final GameProfile p_onProfileLookupSucceeded_1_) {
                var2[0] = p_onProfileLookupSucceeded_1_;
            }
            
            public void onProfileLookupFailed(final GameProfile p_onProfileLookupFailed_1_, final Exception p_onProfileLookupFailed_2_) {
                var2[0] = null;
            }
        };
        p_152650_0_.ÇªÂµÕ().findProfilesByNames(new String[] { p_152650_1_ }, Agent.MINECRAFT, var3);
        if (!p_152650_0_.Ñ¢Â() && var2[0] == null) {
            final UUID var4 = EntityPlayer.HorizonCode_Horizon_È(new GameProfile((UUID)null, p_152650_1_));
            final GameProfile var5 = new GameProfile(var4, p_152650_1_);
            var3.onProfileLookupSucceeded(var5);
        }
        return var2[0];
    }
    
    public void HorizonCode_Horizon_È(final GameProfile p_152649_1_) {
        this.HorizonCode_Horizon_È(p_152649_1_, null);
    }
    
    private void HorizonCode_Horizon_È(final GameProfile p_152651_1_, Date p_152651_2_) {
        final UUID var3 = p_152651_1_.getId();
        if (p_152651_2_ == null) {
            final Calendar var4 = Calendar.getInstance();
            var4.setTime(new Date());
            var4.add(2, 1);
            p_152651_2_ = var4.getTime();
        }
        final String var5 = p_152651_1_.getName().toLowerCase(Locale.ROOT);
        final HorizonCode_Horizon_È var6 = new HorizonCode_Horizon_È(p_152651_1_, p_152651_2_, null);
        if (this.Ø­áŒŠá.containsKey(var3)) {
            final HorizonCode_Horizon_È var7 = this.Ø­áŒŠá.get(var3);
            this.Ý.remove(var7.HorizonCode_Horizon_È().getName().toLowerCase(Locale.ROOT));
            this.Ý.put(p_152651_1_.getName().toLowerCase(Locale.ROOT), var6);
            this.Âµá€.remove(p_152651_1_);
        }
        else {
            this.Ø­áŒŠá.put(var3, var6);
            this.Ý.put(var5, var6);
        }
        this.Âµá€.addFirst(p_152651_1_);
    }
    
    public GameProfile HorizonCode_Horizon_È(final String p_152655_1_) {
        final String var2 = p_152655_1_.toLowerCase(Locale.ROOT);
        HorizonCode_Horizon_È var3 = this.Ý.get(var2);
        if (var3 != null && new Date().getTime() >= var3.Ý.getTime()) {
            this.Ø­áŒŠá.remove(var3.HorizonCode_Horizon_È().getId());
            this.Ý.remove(var3.HorizonCode_Horizon_È().getName().toLowerCase(Locale.ROOT));
            this.Âµá€.remove(var3.HorizonCode_Horizon_È());
            var3 = null;
        }
        if (var3 != null) {
            final GameProfile var4 = var3.HorizonCode_Horizon_È();
            this.Âµá€.remove(var4);
            this.Âµá€.addFirst(var4);
        }
        else {
            final GameProfile var4 = HorizonCode_Horizon_È(this.Ó, var2);
            if (var4 != null) {
                this.HorizonCode_Horizon_È(var4);
                var3 = this.Ý.get(var2);
            }
        }
        this.Ý();
        return (var3 == null) ? null : var3.HorizonCode_Horizon_È();
    }
    
    public String[] HorizonCode_Horizon_È() {
        final ArrayList var1 = Lists.newArrayList((Iterable)this.Ý.keySet());
        return var1.toArray(new String[var1.size()]);
    }
    
    public GameProfile HorizonCode_Horizon_È(final UUID p_152652_1_) {
        final HorizonCode_Horizon_È var2 = this.Ø­áŒŠá.get(p_152652_1_);
        return (var2 == null) ? null : var2.HorizonCode_Horizon_È();
    }
    
    private HorizonCode_Horizon_È Â(final UUID p_152653_1_) {
        final HorizonCode_Horizon_È var2 = this.Ø­áŒŠá.get(p_152653_1_);
        if (var2 != null) {
            final GameProfile var3 = var2.HorizonCode_Horizon_È();
            this.Âµá€.remove(var3);
            this.Âµá€.addFirst(var3);
        }
        return var2;
    }
    
    public void Â() {
        List var1 = null;
        BufferedReader var2 = null;
        Label_0055: {
            try {
                var2 = Files.newReader(this.à, Charsets.UTF_8);
                var1 = (List)this.Â.fromJson((Reader)var2, (Type)PlayerProfileCache.Ø);
                break Label_0055;
            }
            catch (FileNotFoundException ex) {}
            finally {
                IOUtils.closeQuietly((Reader)var2);
            }
            return;
        }
        if (var1 != null) {
            this.Ý.clear();
            this.Ø­áŒŠá.clear();
            this.Âµá€.clear();
            var1 = Lists.reverse(var1);
            for (final HorizonCode_Horizon_È var4 : var1) {
                if (var4 != null) {
                    this.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È(), var4.Â());
                }
            }
        }
    }
    
    public void Ý() {
        final String var1 = this.Â.toJson((Object)this.HorizonCode_Horizon_È(1000));
        BufferedWriter var2 = null;
        try {
            var2 = Files.newWriter(this.à, Charsets.UTF_8);
            var2.write(var1);
        }
        catch (FileNotFoundException var3) {}
        catch (IOException ex) {}
        finally {
            IOUtils.closeQuietly((Writer)var2);
        }
    }
    
    private List HorizonCode_Horizon_È(final int p_152656_1_) {
        final ArrayList var2 = Lists.newArrayList();
        final ArrayList var3 = Lists.newArrayList(Iterators.limit((Iterator)this.Âµá€.iterator(), p_152656_1_));
        for (final GameProfile var5 : var3) {
            final HorizonCode_Horizon_È var6 = this.Â(var5.getId());
            if (var6 != null) {
                var2.add(var6);
            }
        }
        return var2;
    }
    
    class HorizonCode_Horizon_È
    {
        private final GameProfile Â;
        private final Date Ý;
        private static final String Ø­áŒŠá = "CL_00001885";
        
        private HorizonCode_Horizon_È(final GameProfile p_i46333_2_, final Date p_i46333_3_) {
            this.Â = p_i46333_2_;
            this.Ý = p_i46333_3_;
        }
        
        public GameProfile HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        public Date Â() {
            return this.Ý;
        }
        
        HorizonCode_Horizon_È(final PlayerProfileCache playerProfileCache, final GameProfile p_i1166_2_, final Date p_i1166_3_, final Object p_i1166_4_) {
            this(playerProfileCache, p_i1166_2_, p_i1166_3_);
        }
    }
    
    class Â implements JsonDeserializer, JsonSerializer
    {
        private static final String Â = "CL_00001884";
        
        private Â() {
        }
        
        public JsonElement HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_152676_1_, final Type p_152676_2_, final JsonSerializationContext p_152676_3_) {
            final JsonObject var4 = new JsonObject();
            var4.addProperty("name", p_152676_1_.HorizonCode_Horizon_È().getName());
            final UUID var5 = p_152676_1_.HorizonCode_Horizon_È().getId();
            var4.addProperty("uuid", (var5 == null) ? "" : var5.toString());
            var4.addProperty("expiresOn", PlayerProfileCache.HorizonCode_Horizon_È.format(p_152676_1_.Â()));
            return (JsonElement)var4;
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È(final JsonElement p_152675_1_, final Type p_152675_2_, final JsonDeserializationContext p_152675_3_) {
            if (!p_152675_1_.isJsonObject()) {
                return null;
            }
            final JsonObject var4 = p_152675_1_.getAsJsonObject();
            final JsonElement var5 = var4.get("name");
            final JsonElement var6 = var4.get("uuid");
            final JsonElement var7 = var4.get("expiresOn");
            if (var5 == null || var6 == null) {
                return null;
            }
            final String var8 = var6.getAsString();
            final String var9 = var5.getAsString();
            Date var10 = null;
            if (var7 != null) {
                try {
                    var10 = PlayerProfileCache.HorizonCode_Horizon_È.parse(var7.getAsString());
                }
                catch (ParseException var13) {
                    var10 = null;
                }
            }
            if (var9 != null && var8 != null) {
                UUID var11;
                try {
                    var11 = UUID.fromString(var8);
                }
                catch (Throwable var14) {
                    return null;
                }
                final PlayerProfileCache horizonCode_Horizon_È = PlayerProfileCache.this;
                horizonCode_Horizon_È.getClass();
                final HorizonCode_Horizon_È var12 = horizonCode_Horizon_È.new HorizonCode_Horizon_È(new GameProfile(var11, var9), var10, null);
                return var12;
            }
            return null;
        }
        
        public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            return this.HorizonCode_Horizon_È((HorizonCode_Horizon_È)p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
        
        Â(final PlayerProfileCache playerProfileCache, final Object p_i46332_2_) {
            this(playerProfileCache);
        }
    }
}
