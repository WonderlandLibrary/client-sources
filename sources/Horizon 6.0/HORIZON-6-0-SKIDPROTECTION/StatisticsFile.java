package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.HashMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.common.collect.Maps;
import com.google.gson.JsonParser;
import java.util.Map;
import java.util.HashSet;
import com.google.gson.JsonParseException;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import java.util.Set;
import java.io.File;
import org.apache.logging.log4j.Logger;

public class StatisticsFile extends StatFileWriter
{
    private static final Logger Â;
    private final MinecraftServer Ý;
    private final File Ø­áŒŠá;
    private final Set Âµá€;
    private int Ó;
    private boolean à;
    private static final String Ø = "CL_00001471";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public StatisticsFile(final MinecraftServer p_i45306_1_, final File p_i45306_2_) {
        this.Âµá€ = Sets.newHashSet();
        this.Ó = -300;
        this.à = false;
        this.Ý = p_i45306_1_;
        this.Ø­áŒŠá = p_i45306_2_;
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.Ø­áŒŠá.isFile()) {
            try {
                this.HorizonCode_Horizon_È.clear();
                this.HorizonCode_Horizon_È.putAll(this.HorizonCode_Horizon_È(FileUtils.readFileToString(this.Ø­áŒŠá)));
            }
            catch (IOException var2) {
                StatisticsFile.Â.error("Couldn't read statistics file " + this.Ø­áŒŠá, (Throwable)var2);
            }
            catch (JsonParseException var3) {
                StatisticsFile.Â.error("Couldn't parse statistics file " + this.Ø­áŒŠá, (Throwable)var3);
            }
        }
    }
    
    public void Â() {
        try {
            FileUtils.writeStringToFile(this.Ø­áŒŠá, HorizonCode_Horizon_È(this.HorizonCode_Horizon_È));
        }
        catch (IOException var2) {
            StatisticsFile.Â.error("Couldn't save stats", (Throwable)var2);
        }
    }
    
    @Override
    public void Â(final EntityPlayer p_150873_1_, final StatBase p_150873_2_, final int p_150873_3_) {
        final int var4 = p_150873_2_.Ø­áŒŠá() ? this.HorizonCode_Horizon_È(p_150873_2_) : 0;
        super.Â(p_150873_1_, p_150873_2_, p_150873_3_);
        this.Âµá€.add(p_150873_2_);
        if (p_150873_2_.Ø­áŒŠá() && var4 == 0 && p_150873_3_ > 0) {
            this.à = true;
            if (this.Ý.ˆÏ()) {
                this.Ý.Œ().HorizonCode_Horizon_È(new ChatComponentTranslation("chat.type.achievement", new Object[] { p_150873_1_.Ý(), p_150873_2_.áˆºÑ¢Õ() }));
            }
        }
        if (p_150873_2_.Ø­áŒŠá() && var4 > 0 && p_150873_3_ == 0) {
            this.à = true;
            if (this.Ý.ˆÏ()) {
                this.Ý.Œ().HorizonCode_Horizon_È(new ChatComponentTranslation("chat.type.achievement.taken", new Object[] { p_150873_1_.Ý(), p_150873_2_.áˆºÑ¢Õ() }));
            }
        }
    }
    
    public Set Ý() {
        final HashSet var1 = Sets.newHashSet((Iterable)this.Âµá€);
        this.Âµá€.clear();
        this.à = false;
        return var1;
    }
    
    public Map HorizonCode_Horizon_È(final String p_150881_1_) {
        final JsonElement var2 = new JsonParser().parse(p_150881_1_);
        if (!var2.isJsonObject()) {
            return Maps.newHashMap();
        }
        final JsonObject var3 = var2.getAsJsonObject();
        final HashMap var4 = Maps.newHashMap();
        for (final Map.Entry var6 : var3.entrySet()) {
            final StatBase var7 = StatList.HorizonCode_Horizon_È(var6.getKey());
            if (var7 != null) {
                final TupleIntJsonSerializable var8 = new TupleIntJsonSerializable();
                if (var6.getValue().isJsonPrimitive() && var6.getValue().getAsJsonPrimitive().isNumber()) {
                    var8.HorizonCode_Horizon_È(var6.getValue().getAsInt());
                }
                else if (var6.getValue().isJsonObject()) {
                    final JsonObject var9 = var6.getValue().getAsJsonObject();
                    if (var9.has("value") && var9.get("value").isJsonPrimitive() && var9.get("value").getAsJsonPrimitive().isNumber()) {
                        var8.HorizonCode_Horizon_È(var9.getAsJsonPrimitive("value").getAsInt());
                    }
                    if (var9.has("progress") && var7.á() != null) {
                        try {
                            final Constructor var10 = var7.á().getConstructor((Class[])new Class[0]);
                            final IJsonSerializable var11 = var10.newInstance(new Object[0]);
                            var11.HorizonCode_Horizon_È(var9.get("progress"));
                            var8.HorizonCode_Horizon_È(var11);
                        }
                        catch (Throwable var12) {
                            StatisticsFile.Â.warn("Invalid statistic progress in " + this.Ø­áŒŠá, var12);
                        }
                    }
                }
                var4.put(var7, var8);
            }
            else {
                StatisticsFile.Â.warn("Invalid statistic in " + this.Ø­áŒŠá + ": Don't know what " + var6.getKey() + " is");
            }
        }
        return var4;
    }
    
    public static String HorizonCode_Horizon_È(final Map p_150880_0_) {
        final JsonObject var1 = new JsonObject();
        for (final Map.Entry var3 : p_150880_0_.entrySet()) {
            if (var3.getValue().Â() != null) {
                final JsonObject var4 = new JsonObject();
                var4.addProperty("value", (Number)var3.getValue().HorizonCode_Horizon_È());
                try {
                    var4.add("progress", var3.getValue().Â().HorizonCode_Horizon_È());
                }
                catch (Throwable var5) {
                    StatisticsFile.Â.warn("Couldn't save statistic " + var3.getKey().Âµá€() + ": error serializing progress", var5);
                }
                var1.add(var3.getKey().à, (JsonElement)var4);
            }
            else {
                var1.addProperty(var3.getKey().à, (Number)var3.getValue().HorizonCode_Horizon_È());
            }
        }
        return var1.toString();
    }
    
    public void Ø­áŒŠá() {
        for (final StatBase var2 : this.HorizonCode_Horizon_È.keySet()) {
            this.Âµá€.add(var2);
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayerMP p_150876_1_) {
        final int var2 = this.Ý.Ï­Ï­Ï();
        final HashMap var3 = Maps.newHashMap();
        if (this.à || var2 - this.Ó > 300) {
            this.Ó = var2;
            for (final StatBase var5 : this.Ý()) {
                var3.put(var5, this.HorizonCode_Horizon_È(var5));
            }
        }
        p_150876_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S37PacketStatistics(var3));
    }
    
    public void Â(final EntityPlayerMP p_150884_1_) {
        final HashMap var2 = Maps.newHashMap();
        for (final Achievement var4 : AchievementList.Âµá€) {
            if (this.HorizonCode_Horizon_È(var4)) {
                var2.put(var4, this.HorizonCode_Horizon_È((StatBase)var4));
                this.Âµá€.remove(var4);
            }
        }
        p_150884_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S37PacketStatistics(var2));
    }
    
    public boolean Âµá€() {
        return this.à;
    }
}
