package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class ChunkProviderSettings
{
    public final float HorizonCode_Horizon_È;
    public final float Â;
    public final float Ý;
    public final float Ø­áŒŠá;
    public final float Âµá€;
    public final float Ó;
    public final float à;
    public final float Ø;
    public final float áŒŠÆ;
    public final float áˆºÑ¢Õ;
    public final float ÂµÈ;
    public final float á;
    public final float ˆÏ­;
    public final float £á;
    public final float Å;
    public final float £à;
    public final int µà;
    public final boolean ˆà;
    public final boolean ¥Æ;
    public final int Ø­à;
    public final boolean µÕ;
    public final boolean Æ;
    public final boolean Šáƒ;
    public final boolean Ï­Ðƒà;
    public final boolean áŒŠà;
    public final boolean ŠÄ;
    public final boolean Ñ¢á;
    public final int ŒÏ;
    public final boolean Çªà¢;
    public final int Ê;
    public final boolean ÇŽÉ;
    public final int ˆá;
    public final int ÇŽÕ;
    public final int É;
    public final int áƒ;
    public final int á€;
    public final int Õ;
    public final int à¢;
    public final int ŠÂµà;
    public final int ¥à;
    public final int Âµà;
    public final int Ç;
    public final int È;
    public final int áŠ;
    public final int ˆáŠ;
    public final int áŒŠ;
    public final int £ÂµÄ;
    public final int Ø­Âµ;
    public final int Ä;
    public final int Ñ¢Â;
    public final int Ï­à;
    public final int áˆºáˆºÈ;
    public final int ÇŽá€;
    public final int Ï;
    public final int Ô;
    public final int ÇªÓ;
    public final int áˆºÏ;
    public final int ˆáƒ;
    public final int Œ;
    public final int £Ï;
    public final int Ø­á;
    public final int ˆÉ;
    public final int Ï­Ï­Ï;
    public final int £Â;
    public final int £Ó;
    public final int ˆÐƒØ­à;
    public final int £Õ;
    public final int Ï­Ô;
    public final int Œà;
    public final int Ðƒá;
    public final int ˆÏ;
    public final int áˆºÇŽØ;
    public final int ÇªÂµÕ;
    public final int áŒŠÏ;
    public final int áŒŠáŠ;
    public final int ˆÓ;
    public final int ¥Ä;
    public final int ÇªÔ;
    private static final String Û = "CL_00002006";
    
    private ChunkProviderSettings(final HorizonCode_Horizon_È p_i45639_1_) {
        this.HorizonCode_Horizon_È = p_i45639_1_.Â;
        this.Â = p_i45639_1_.Ý;
        this.Ý = p_i45639_1_.Ø­áŒŠá;
        this.Ø­áŒŠá = p_i45639_1_.Âµá€;
        this.Âµá€ = p_i45639_1_.Ó;
        this.Ó = p_i45639_1_.à;
        this.à = p_i45639_1_.Ø;
        this.Ø = p_i45639_1_.áŒŠÆ;
        this.áŒŠÆ = p_i45639_1_.áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = p_i45639_1_.ÂµÈ;
        this.ÂµÈ = p_i45639_1_.á;
        this.á = p_i45639_1_.ˆÏ­;
        this.ˆÏ­ = p_i45639_1_.£á;
        this.£á = p_i45639_1_.Å;
        this.Å = p_i45639_1_.£à;
        this.£à = p_i45639_1_.µà;
        this.µà = p_i45639_1_.ˆà;
        this.ˆà = p_i45639_1_.¥Æ;
        this.¥Æ = p_i45639_1_.Ø­à;
        this.Ø­à = p_i45639_1_.µÕ;
        this.µÕ = p_i45639_1_.Æ;
        this.Æ = p_i45639_1_.Šáƒ;
        this.Šáƒ = p_i45639_1_.Ï­Ðƒà;
        this.Ï­Ðƒà = p_i45639_1_.áŒŠà;
        this.áŒŠà = p_i45639_1_.ŠÄ;
        this.ŠÄ = p_i45639_1_.Ñ¢á;
        this.Ñ¢á = p_i45639_1_.ŒÏ;
        this.ŒÏ = p_i45639_1_.Çªà¢;
        this.Çªà¢ = p_i45639_1_.Ê;
        this.Ê = p_i45639_1_.ÇŽÉ;
        this.ÇŽÉ = p_i45639_1_.ˆá;
        this.ˆá = p_i45639_1_.ÇŽÕ;
        this.ÇŽÕ = p_i45639_1_.É;
        this.É = p_i45639_1_.áƒ;
        this.áƒ = p_i45639_1_.á€;
        this.á€ = p_i45639_1_.Õ;
        this.Õ = p_i45639_1_.à¢;
        this.à¢ = p_i45639_1_.ŠÂµà;
        this.ŠÂµà = p_i45639_1_.¥à;
        this.¥à = p_i45639_1_.Âµà;
        this.Âµà = p_i45639_1_.Ç;
        this.Ç = p_i45639_1_.È;
        this.È = p_i45639_1_.áŠ;
        this.áŠ = p_i45639_1_.ˆáŠ;
        this.ˆáŠ = p_i45639_1_.áŒŠ;
        this.áŒŠ = p_i45639_1_.£ÂµÄ;
        this.£ÂµÄ = p_i45639_1_.Ø­Âµ;
        this.Ø­Âµ = p_i45639_1_.Ä;
        this.Ä = p_i45639_1_.Ñ¢Â;
        this.Ñ¢Â = p_i45639_1_.Ï­à;
        this.Ï­à = p_i45639_1_.áˆºáˆºÈ;
        this.áˆºáˆºÈ = p_i45639_1_.ÇŽá€;
        this.ÇŽá€ = p_i45639_1_.Ï;
        this.Ï = p_i45639_1_.Ô;
        this.Ô = p_i45639_1_.ÇªÓ;
        this.ÇªÓ = p_i45639_1_.áˆºÏ;
        this.áˆºÏ = p_i45639_1_.ˆáƒ;
        this.ˆáƒ = p_i45639_1_.Œ;
        this.Œ = p_i45639_1_.£Ï;
        this.£Ï = p_i45639_1_.Ø­á;
        this.Ø­á = p_i45639_1_.ˆÉ;
        this.ˆÉ = p_i45639_1_.Ï­Ï­Ï;
        this.Ï­Ï­Ï = p_i45639_1_.£Â;
        this.£Â = p_i45639_1_.£Ó;
        this.£Ó = p_i45639_1_.ˆÐƒØ­à;
        this.ˆÐƒØ­à = p_i45639_1_.£Õ;
        this.£Õ = p_i45639_1_.Ï­Ô;
        this.Ï­Ô = p_i45639_1_.Œà;
        this.Œà = p_i45639_1_.Ðƒá;
        this.Ðƒá = p_i45639_1_.ˆÏ;
        this.ˆÏ = p_i45639_1_.áˆºÇŽØ;
        this.áˆºÇŽØ = p_i45639_1_.ÇªÂµÕ;
        this.ÇªÂµÕ = p_i45639_1_.áŒŠÏ;
        this.áŒŠÏ = p_i45639_1_.áŒŠáŠ;
        this.áŒŠáŠ = p_i45639_1_.ˆÓ;
        this.ˆÓ = p_i45639_1_.¥Ä;
        this.¥Ä = p_i45639_1_.ÇªÔ;
        this.ÇªÔ = p_i45639_1_.Û;
    }
    
    ChunkProviderSettings(final HorizonCode_Horizon_È p_i45640_1_, final Object p_i45640_2_) {
        this(p_i45640_1_);
    }
    
    public static class HorizonCode_Horizon_È
    {
        static final Gson HorizonCode_Horizon_È;
        public float Â;
        public float Ý;
        public float Ø­áŒŠá;
        public float Âµá€;
        public float Ó;
        public float à;
        public float Ø;
        public float áŒŠÆ;
        public float áˆºÑ¢Õ;
        public float ÂµÈ;
        public float á;
        public float ˆÏ­;
        public float £á;
        public float Å;
        public float £à;
        public float µà;
        public int ˆà;
        public boolean ¥Æ;
        public boolean Ø­à;
        public int µÕ;
        public boolean Æ;
        public boolean Šáƒ;
        public boolean Ï­Ðƒà;
        public boolean áŒŠà;
        public boolean ŠÄ;
        public boolean Ñ¢á;
        public boolean ŒÏ;
        public int Çªà¢;
        public boolean Ê;
        public int ÇŽÉ;
        public boolean ˆá;
        public int ÇŽÕ;
        public int É;
        public int áƒ;
        public int á€;
        public int Õ;
        public int à¢;
        public int ŠÂµà;
        public int ¥à;
        public int Âµà;
        public int Ç;
        public int È;
        public int áŠ;
        public int ˆáŠ;
        public int áŒŠ;
        public int £ÂµÄ;
        public int Ø­Âµ;
        public int Ä;
        public int Ñ¢Â;
        public int Ï­à;
        public int áˆºáˆºÈ;
        public int ÇŽá€;
        public int Ï;
        public int Ô;
        public int ÇªÓ;
        public int áˆºÏ;
        public int ˆáƒ;
        public int Œ;
        public int £Ï;
        public int Ø­á;
        public int ˆÉ;
        public int Ï­Ï­Ï;
        public int £Â;
        public int £Ó;
        public int ˆÐƒØ­à;
        public int £Õ;
        public int Ï­Ô;
        public int Œà;
        public int Ðƒá;
        public int ˆÏ;
        public int áˆºÇŽØ;
        public int ÇªÂµÕ;
        public int áŒŠÏ;
        public int áŒŠáŠ;
        public int ˆÓ;
        public int ¥Ä;
        public int ÇªÔ;
        public int Û;
        private static final String ŠÓ = "CL_00002004";
        
        static {
            HorizonCode_Horizon_È = new GsonBuilder().registerTypeAdapter((Type)HorizonCode_Horizon_È.class, (Object)new Â()).create();
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_177865_0_) {
            if (p_177865_0_.length() == 0) {
                return new HorizonCode_Horizon_È();
            }
            try {
                return (HorizonCode_Horizon_È)ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È.fromJson(p_177865_0_, (Class)HorizonCode_Horizon_È.class);
            }
            catch (Exception var2) {
                return new HorizonCode_Horizon_È();
            }
        }
        
        @Override
        public String toString() {
            return ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È.toJson((Object)this);
        }
        
        public HorizonCode_Horizon_È() {
            this.Â = 684.412f;
            this.Ý = 684.412f;
            this.Ø­áŒŠá = 512.0f;
            this.Âµá€ = 512.0f;
            this.Ó = 200.0f;
            this.à = 200.0f;
            this.Ø = 0.5f;
            this.áŒŠÆ = 80.0f;
            this.áˆºÑ¢Õ = 160.0f;
            this.ÂµÈ = 80.0f;
            this.á = 8.5f;
            this.ˆÏ­ = 12.0f;
            this.£á = 1.0f;
            this.Å = 0.0f;
            this.£à = 1.0f;
            this.µà = 0.0f;
            this.ˆà = 63;
            this.¥Æ = true;
            this.Ø­à = true;
            this.µÕ = 8;
            this.Æ = true;
            this.Šáƒ = true;
            this.Ï­Ðƒà = true;
            this.áŒŠà = true;
            this.ŠÄ = true;
            this.Ñ¢á = true;
            this.ŒÏ = true;
            this.Çªà¢ = 4;
            this.Ê = true;
            this.ÇŽÉ = 80;
            this.ˆá = false;
            this.ÇŽÕ = -1;
            this.É = 4;
            this.áƒ = 4;
            this.á€ = 33;
            this.Õ = 10;
            this.à¢ = 0;
            this.ŠÂµà = 256;
            this.¥à = 33;
            this.Âµà = 8;
            this.Ç = 0;
            this.È = 256;
            this.áŠ = 33;
            this.ˆáŠ = 10;
            this.áŒŠ = 0;
            this.£ÂµÄ = 80;
            this.Ø­Âµ = 33;
            this.Ä = 10;
            this.Ñ¢Â = 0;
            this.Ï­à = 80;
            this.áˆºáˆºÈ = 33;
            this.ÇŽá€ = 10;
            this.Ï = 0;
            this.Ô = 80;
            this.ÇªÓ = 17;
            this.áˆºÏ = 20;
            this.ˆáƒ = 0;
            this.Œ = 128;
            this.£Ï = 9;
            this.Ø­á = 20;
            this.ˆÉ = 0;
            this.Ï­Ï­Ï = 64;
            this.£Â = 9;
            this.£Ó = 2;
            this.ˆÐƒØ­à = 0;
            this.£Õ = 32;
            this.Ï­Ô = 8;
            this.Œà = 8;
            this.Ðƒá = 0;
            this.ˆÏ = 16;
            this.áˆºÇŽØ = 8;
            this.ÇªÂµÕ = 1;
            this.áŒŠÏ = 0;
            this.áŒŠáŠ = 16;
            this.ˆÓ = 7;
            this.¥Ä = 1;
            this.ÇªÔ = 16;
            this.Û = 16;
            this.HorizonCode_Horizon_È();
        }
        
        public void HorizonCode_Horizon_È() {
            this.Â = 684.412f;
            this.Ý = 684.412f;
            this.Ø­áŒŠá = 512.0f;
            this.Âµá€ = 512.0f;
            this.Ó = 200.0f;
            this.à = 200.0f;
            this.Ø = 0.5f;
            this.áŒŠÆ = 80.0f;
            this.áˆºÑ¢Õ = 160.0f;
            this.ÂµÈ = 80.0f;
            this.á = 8.5f;
            this.ˆÏ­ = 12.0f;
            this.£á = 1.0f;
            this.Å = 0.0f;
            this.£à = 1.0f;
            this.µà = 0.0f;
            this.ˆà = 63;
            this.¥Æ = true;
            this.Ø­à = true;
            this.µÕ = 8;
            this.Æ = true;
            this.Šáƒ = true;
            this.Ï­Ðƒà = true;
            this.áŒŠà = true;
            this.ŠÄ = true;
            this.Ñ¢á = true;
            this.ŒÏ = true;
            this.Çªà¢ = 4;
            this.Ê = true;
            this.ÇŽÉ = 80;
            this.ˆá = false;
            this.ÇŽÕ = -1;
            this.É = 4;
            this.áƒ = 4;
            this.á€ = 33;
            this.Õ = 10;
            this.à¢ = 0;
            this.ŠÂµà = 256;
            this.¥à = 33;
            this.Âµà = 8;
            this.Ç = 0;
            this.È = 256;
            this.áŠ = 33;
            this.ˆáŠ = 10;
            this.áŒŠ = 0;
            this.£ÂµÄ = 80;
            this.Ø­Âµ = 33;
            this.Ä = 10;
            this.Ñ¢Â = 0;
            this.Ï­à = 80;
            this.áˆºáˆºÈ = 33;
            this.ÇŽá€ = 10;
            this.Ï = 0;
            this.Ô = 80;
            this.ÇªÓ = 17;
            this.áˆºÏ = 20;
            this.ˆáƒ = 0;
            this.Œ = 128;
            this.£Ï = 9;
            this.Ø­á = 20;
            this.ˆÉ = 0;
            this.Ï­Ï­Ï = 64;
            this.£Â = 9;
            this.£Ó = 2;
            this.ˆÐƒØ­à = 0;
            this.£Õ = 32;
            this.Ï­Ô = 8;
            this.Œà = 8;
            this.Ðƒá = 0;
            this.ˆÏ = 16;
            this.áˆºÇŽØ = 8;
            this.ÇªÂµÕ = 1;
            this.áŒŠÏ = 0;
            this.áŒŠáŠ = 16;
            this.ˆÓ = 7;
            this.¥Ä = 1;
            this.ÇªÔ = 16;
            this.Û = 16;
        }
        
        @Override
        public boolean equals(final Object p_equals_1_) {
            if (this == p_equals_1_) {
                return true;
            }
            if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
                final HorizonCode_Horizon_È var2 = (HorizonCode_Horizon_È)p_equals_1_;
                return this.ÇŽá€ == var2.ÇŽá€ && this.Ô == var2.Ô && this.Ï == var2.Ï && this.áˆºáˆºÈ == var2.áˆºáˆºÈ && Float.compare(var2.á, this.á) == 0 && Float.compare(var2.Å, this.Å) == 0 && Float.compare(var2.£á, this.£á) == 0 && Float.compare(var2.µà, this.µà) == 0 && Float.compare(var2.£à, this.£à) == 0 && this.É == var2.É && this.áˆºÏ == var2.áˆºÏ && this.Œ == var2.Œ && this.ˆáƒ == var2.ˆáƒ && this.ÇªÓ == var2.ÇªÓ && Float.compare(var2.Â, this.Â) == 0 && Float.compare(var2.Ø, this.Ø) == 0 && Float.compare(var2.Ó, this.Ó) == 0 && Float.compare(var2.à, this.à) == 0 && this.ÇªÂµÕ == var2.ÇªÂµÕ && this.áŒŠáŠ == var2.áŒŠáŠ && this.áŒŠÏ == var2.áŒŠÏ && this.áˆºÇŽØ == var2.áˆºÇŽØ && this.Ä == var2.Ä && this.Ï­à == var2.Ï­à && this.Ñ¢Â == var2.Ñ¢Â && this.Ø­Âµ == var2.Ø­Âµ && this.Õ == var2.Õ && this.ŠÂµà == var2.ŠÂµà && this.à¢ == var2.à¢ && this.á€ == var2.á€ && this.µÕ == var2.µÕ && this.ÇŽÕ == var2.ÇŽÕ && this.£Ó == var2.£Ó && this.£Õ == var2.£Õ && this.ˆÐƒØ­à == var2.ˆÐƒØ­à && this.£Â == var2.£Â && this.ˆáŠ == var2.ˆáŠ && this.£ÂµÄ == var2.£ÂµÄ && this.áŒŠ == var2.áŒŠ && this.áŠ == var2.áŠ && this.Âµà == var2.Âµà && this.È == var2.È && this.Ç == var2.Ç && this.¥à == var2.¥à && Float.compare(var2.Ý, this.Ý) == 0 && this.Ø­á == var2.Ø­á && this.Ï­Ï­Ï == var2.Ï­Ï­Ï && this.ˆÉ == var2.ˆÉ && this.£Ï == var2.£Ï && this.ÇªÔ == var2.ÇªÔ && this.¥Ä == var2.¥Ä && this.ˆÓ == var2.ˆÓ && this.Û == var2.Û && this.ÇŽÉ == var2.ÇŽÉ && Float.compare(var2.Âµá€, this.Âµá€) == 0 && Float.compare(var2.áŒŠÆ, this.áŒŠÆ) == 0 && Float.compare(var2.áˆºÑ¢Õ, this.áˆºÑ¢Õ) == 0 && Float.compare(var2.ÂµÈ, this.ÂµÈ) == 0 && this.Œà == var2.Œà && this.ˆÏ == var2.ˆÏ && this.Ðƒá == var2.Ðƒá && this.Ï­Ô == var2.Ï­Ô && this.áƒ == var2.áƒ && this.ˆà == var2.ˆà && Float.compare(var2.ˆÏ­, this.ˆÏ­) == 0 && Float.compare(var2.Ø­áŒŠá, this.Ø­áŒŠá) == 0 && this.¥Æ == var2.¥Æ && this.Ø­à == var2.Ø­à && this.Ê == var2.Ê && this.ˆá == var2.ˆá && this.Ï­Ðƒà == var2.Ï­Ðƒà && this.Ñ¢á == var2.Ñ¢á && this.Æ == var2.Æ && this.áŒŠà == var2.áŒŠà && this.ŠÄ == var2.ŠÄ && this.Šáƒ == var2.Šáƒ && this.ŒÏ == var2.ŒÏ && this.Çªà¢ == var2.Çªà¢;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            int var1 = (this.Â != 0.0f) ? Float.floatToIntBits(this.Â) : 0;
            var1 = 31 * var1 + ((this.Ý != 0.0f) ? Float.floatToIntBits(this.Ý) : 0);
            var1 = 31 * var1 + ((this.Ø­áŒŠá != 0.0f) ? Float.floatToIntBits(this.Ø­áŒŠá) : 0);
            var1 = 31 * var1 + ((this.Âµá€ != 0.0f) ? Float.floatToIntBits(this.Âµá€) : 0);
            var1 = 31 * var1 + ((this.Ó != 0.0f) ? Float.floatToIntBits(this.Ó) : 0);
            var1 = 31 * var1 + ((this.à != 0.0f) ? Float.floatToIntBits(this.à) : 0);
            var1 = 31 * var1 + ((this.Ø != 0.0f) ? Float.floatToIntBits(this.Ø) : 0);
            var1 = 31 * var1 + ((this.áŒŠÆ != 0.0f) ? Float.floatToIntBits(this.áŒŠÆ) : 0);
            var1 = 31 * var1 + ((this.áˆºÑ¢Õ != 0.0f) ? Float.floatToIntBits(this.áˆºÑ¢Õ) : 0);
            var1 = 31 * var1 + ((this.ÂµÈ != 0.0f) ? Float.floatToIntBits(this.ÂµÈ) : 0);
            var1 = 31 * var1 + ((this.á != 0.0f) ? Float.floatToIntBits(this.á) : 0);
            var1 = 31 * var1 + ((this.ˆÏ­ != 0.0f) ? Float.floatToIntBits(this.ˆÏ­) : 0);
            var1 = 31 * var1 + ((this.£á != 0.0f) ? Float.floatToIntBits(this.£á) : 0);
            var1 = 31 * var1 + ((this.Å != 0.0f) ? Float.floatToIntBits(this.Å) : 0);
            var1 = 31 * var1 + ((this.£à != 0.0f) ? Float.floatToIntBits(this.£à) : 0);
            var1 = 31 * var1 + ((this.µà != 0.0f) ? Float.floatToIntBits(this.µà) : 0);
            var1 = 31 * var1 + this.ˆà;
            var1 = 31 * var1 + (this.¥Æ ? 1 : 0);
            var1 = 31 * var1 + (this.Ø­à ? 1 : 0);
            var1 = 31 * var1 + this.µÕ;
            var1 = 31 * var1 + (this.Æ ? 1 : 0);
            var1 = 31 * var1 + (this.Šáƒ ? 1 : 0);
            var1 = 31 * var1 + (this.Ï­Ðƒà ? 1 : 0);
            var1 = 31 * var1 + (this.áŒŠà ? 1 : 0);
            var1 = 31 * var1 + (this.ŠÄ ? 1 : 0);
            var1 = 31 * var1 + (this.Ñ¢á ? 1 : 0);
            var1 = 31 * var1 + (this.ŒÏ ? 1 : 0);
            var1 = 31 * var1 + this.Çªà¢;
            var1 = 31 * var1 + (this.Ê ? 1 : 0);
            var1 = 31 * var1 + this.ÇŽÉ;
            var1 = 31 * var1 + (this.ˆá ? 1 : 0);
            var1 = 31 * var1 + this.ÇŽÕ;
            var1 = 31 * var1 + this.É;
            var1 = 31 * var1 + this.áƒ;
            var1 = 31 * var1 + this.á€;
            var1 = 31 * var1 + this.Õ;
            var1 = 31 * var1 + this.à¢;
            var1 = 31 * var1 + this.ŠÂµà;
            var1 = 31 * var1 + this.¥à;
            var1 = 31 * var1 + this.Âµà;
            var1 = 31 * var1 + this.Ç;
            var1 = 31 * var1 + this.È;
            var1 = 31 * var1 + this.áŠ;
            var1 = 31 * var1 + this.ˆáŠ;
            var1 = 31 * var1 + this.áŒŠ;
            var1 = 31 * var1 + this.£ÂµÄ;
            var1 = 31 * var1 + this.Ø­Âµ;
            var1 = 31 * var1 + this.Ä;
            var1 = 31 * var1 + this.Ñ¢Â;
            var1 = 31 * var1 + this.Ï­à;
            var1 = 31 * var1 + this.áˆºáˆºÈ;
            var1 = 31 * var1 + this.ÇŽá€;
            var1 = 31 * var1 + this.Ï;
            var1 = 31 * var1 + this.Ô;
            var1 = 31 * var1 + this.ÇªÓ;
            var1 = 31 * var1 + this.áˆºÏ;
            var1 = 31 * var1 + this.ˆáƒ;
            var1 = 31 * var1 + this.Œ;
            var1 = 31 * var1 + this.£Ï;
            var1 = 31 * var1 + this.Ø­á;
            var1 = 31 * var1 + this.ˆÉ;
            var1 = 31 * var1 + this.Ï­Ï­Ï;
            var1 = 31 * var1 + this.£Â;
            var1 = 31 * var1 + this.£Ó;
            var1 = 31 * var1 + this.ˆÐƒØ­à;
            var1 = 31 * var1 + this.£Õ;
            var1 = 31 * var1 + this.Ï­Ô;
            var1 = 31 * var1 + this.Œà;
            var1 = 31 * var1 + this.Ðƒá;
            var1 = 31 * var1 + this.ˆÏ;
            var1 = 31 * var1 + this.áˆºÇŽØ;
            var1 = 31 * var1 + this.ÇªÂµÕ;
            var1 = 31 * var1 + this.áŒŠÏ;
            var1 = 31 * var1 + this.áŒŠáŠ;
            var1 = 31 * var1 + this.ˆÓ;
            var1 = 31 * var1 + this.¥Ä;
            var1 = 31 * var1 + this.ÇªÔ;
            var1 = 31 * var1 + this.Û;
            return var1;
        }
        
        public ChunkProviderSettings Â() {
            return new ChunkProviderSettings(this, null);
        }
    }
    
    public static class Â implements JsonDeserializer, JsonSerializer
    {
        private static final String HorizonCode_Horizon_È = "CL_00002003";
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È(final JsonElement p_177861_1_, final Type p_177861_2_, final JsonDeserializationContext p_177861_3_) {
            final JsonObject var4 = p_177861_1_.getAsJsonObject();
            final HorizonCode_Horizon_È var5 = new HorizonCode_Horizon_È();
            try {
                var5.Â = JsonUtils.HorizonCode_Horizon_È(var4, "coordinateScale", var5.Â);
                var5.Ý = JsonUtils.HorizonCode_Horizon_È(var4, "heightScale", var5.Ý);
                var5.Âµá€ = JsonUtils.HorizonCode_Horizon_È(var4, "lowerLimitScale", var5.Âµá€);
                var5.Ø­áŒŠá = JsonUtils.HorizonCode_Horizon_È(var4, "upperLimitScale", var5.Ø­áŒŠá);
                var5.Ó = JsonUtils.HorizonCode_Horizon_È(var4, "depthNoiseScaleX", var5.Ó);
                var5.à = JsonUtils.HorizonCode_Horizon_È(var4, "depthNoiseScaleZ", var5.à);
                var5.Ø = JsonUtils.HorizonCode_Horizon_È(var4, "depthNoiseScaleExponent", var5.Ø);
                var5.áŒŠÆ = JsonUtils.HorizonCode_Horizon_È(var4, "mainNoiseScaleX", var5.áŒŠÆ);
                var5.áˆºÑ¢Õ = JsonUtils.HorizonCode_Horizon_È(var4, "mainNoiseScaleY", var5.áˆºÑ¢Õ);
                var5.ÂµÈ = JsonUtils.HorizonCode_Horizon_È(var4, "mainNoiseScaleZ", var5.ÂµÈ);
                var5.á = JsonUtils.HorizonCode_Horizon_È(var4, "baseSize", var5.á);
                var5.ˆÏ­ = JsonUtils.HorizonCode_Horizon_È(var4, "stretchY", var5.ˆÏ­);
                var5.£á = JsonUtils.HorizonCode_Horizon_È(var4, "biomeDepthWeight", var5.£á);
                var5.Å = JsonUtils.HorizonCode_Horizon_È(var4, "biomeDepthOffset", var5.Å);
                var5.£à = JsonUtils.HorizonCode_Horizon_È(var4, "biomeScaleWeight", var5.£à);
                var5.µà = JsonUtils.HorizonCode_Horizon_È(var4, "biomeScaleOffset", var5.µà);
                var5.ˆà = JsonUtils.HorizonCode_Horizon_È(var4, "seaLevel", var5.ˆà);
                var5.¥Æ = JsonUtils.HorizonCode_Horizon_È(var4, "useCaves", var5.¥Æ);
                var5.Ø­à = JsonUtils.HorizonCode_Horizon_È(var4, "useDungeons", var5.Ø­à);
                var5.µÕ = JsonUtils.HorizonCode_Horizon_È(var4, "dungeonChance", var5.µÕ);
                var5.Æ = JsonUtils.HorizonCode_Horizon_È(var4, "useStrongholds", var5.Æ);
                var5.Šáƒ = JsonUtils.HorizonCode_Horizon_È(var4, "useVillages", var5.Šáƒ);
                var5.Ï­Ðƒà = JsonUtils.HorizonCode_Horizon_È(var4, "useMineShafts", var5.Ï­Ðƒà);
                var5.áŒŠà = JsonUtils.HorizonCode_Horizon_È(var4, "useTemples", var5.áŒŠà);
                var5.ŠÄ = JsonUtils.HorizonCode_Horizon_È(var4, "useMonuments", var5.ŠÄ);
                var5.Ñ¢á = JsonUtils.HorizonCode_Horizon_È(var4, "useRavines", var5.Ñ¢á);
                var5.ŒÏ = JsonUtils.HorizonCode_Horizon_È(var4, "useWaterLakes", var5.ŒÏ);
                var5.Çªà¢ = JsonUtils.HorizonCode_Horizon_È(var4, "waterLakeChance", var5.Çªà¢);
                var5.Ê = JsonUtils.HorizonCode_Horizon_È(var4, "useLavaLakes", var5.Ê);
                var5.ÇŽÉ = JsonUtils.HorizonCode_Horizon_È(var4, "lavaLakeChance", var5.ÇŽÉ);
                var5.ˆá = JsonUtils.HorizonCode_Horizon_È(var4, "useLavaOceans", var5.ˆá);
                var5.ÇŽÕ = JsonUtils.HorizonCode_Horizon_È(var4, "fixedBiome", var5.ÇŽÕ);
                if (var5.ÇŽÕ < 38 && var5.ÇŽÕ >= -1) {
                    if (var5.ÇŽÕ >= BiomeGenBase.Ï­Ðƒà.ÇªÔ) {
                        final HorizonCode_Horizon_È horizonCode_Horizon_È = var5;
                        horizonCode_Horizon_È.ÇŽÕ += 2;
                    }
                }
                else {
                    var5.ÇŽÕ = -1;
                }
                var5.É = JsonUtils.HorizonCode_Horizon_È(var4, "biomeSize", var5.É);
                var5.áƒ = JsonUtils.HorizonCode_Horizon_È(var4, "riverSize", var5.áƒ);
                var5.á€ = JsonUtils.HorizonCode_Horizon_È(var4, "dirtSize", var5.á€);
                var5.Õ = JsonUtils.HorizonCode_Horizon_È(var4, "dirtCount", var5.Õ);
                var5.à¢ = JsonUtils.HorizonCode_Horizon_È(var4, "dirtMinHeight", var5.à¢);
                var5.ŠÂµà = JsonUtils.HorizonCode_Horizon_È(var4, "dirtMaxHeight", var5.ŠÂµà);
                var5.¥à = JsonUtils.HorizonCode_Horizon_È(var4, "gravelSize", var5.¥à);
                var5.Âµà = JsonUtils.HorizonCode_Horizon_È(var4, "gravelCount", var5.Âµà);
                var5.Ç = JsonUtils.HorizonCode_Horizon_È(var4, "gravelMinHeight", var5.Ç);
                var5.È = JsonUtils.HorizonCode_Horizon_È(var4, "gravelMaxHeight", var5.È);
                var5.áŠ = JsonUtils.HorizonCode_Horizon_È(var4, "graniteSize", var5.áŠ);
                var5.ˆáŠ = JsonUtils.HorizonCode_Horizon_È(var4, "graniteCount", var5.ˆáŠ);
                var5.áŒŠ = JsonUtils.HorizonCode_Horizon_È(var4, "graniteMinHeight", var5.áŒŠ);
                var5.£ÂµÄ = JsonUtils.HorizonCode_Horizon_È(var4, "graniteMaxHeight", var5.£ÂµÄ);
                var5.Ø­Âµ = JsonUtils.HorizonCode_Horizon_È(var4, "dioriteSize", var5.Ø­Âµ);
                var5.Ä = JsonUtils.HorizonCode_Horizon_È(var4, "dioriteCount", var5.Ä);
                var5.Ñ¢Â = JsonUtils.HorizonCode_Horizon_È(var4, "dioriteMinHeight", var5.Ñ¢Â);
                var5.Ï­à = JsonUtils.HorizonCode_Horizon_È(var4, "dioriteMaxHeight", var5.Ï­à);
                var5.áˆºáˆºÈ = JsonUtils.HorizonCode_Horizon_È(var4, "andesiteSize", var5.áˆºáˆºÈ);
                var5.ÇŽá€ = JsonUtils.HorizonCode_Horizon_È(var4, "andesiteCount", var5.ÇŽá€);
                var5.Ï = JsonUtils.HorizonCode_Horizon_È(var4, "andesiteMinHeight", var5.Ï);
                var5.Ô = JsonUtils.HorizonCode_Horizon_È(var4, "andesiteMaxHeight", var5.Ô);
                var5.ÇªÓ = JsonUtils.HorizonCode_Horizon_È(var4, "coalSize", var5.ÇªÓ);
                var5.áˆºÏ = JsonUtils.HorizonCode_Horizon_È(var4, "coalCount", var5.áˆºÏ);
                var5.ˆáƒ = JsonUtils.HorizonCode_Horizon_È(var4, "coalMinHeight", var5.ˆáƒ);
                var5.Œ = JsonUtils.HorizonCode_Horizon_È(var4, "coalMaxHeight", var5.Œ);
                var5.£Ï = JsonUtils.HorizonCode_Horizon_È(var4, "ironSize", var5.£Ï);
                var5.Ø­á = JsonUtils.HorizonCode_Horizon_È(var4, "ironCount", var5.Ø­á);
                var5.ˆÉ = JsonUtils.HorizonCode_Horizon_È(var4, "ironMinHeight", var5.ˆÉ);
                var5.Ï­Ï­Ï = JsonUtils.HorizonCode_Horizon_È(var4, "ironMaxHeight", var5.Ï­Ï­Ï);
                var5.£Â = JsonUtils.HorizonCode_Horizon_È(var4, "goldSize", var5.£Â);
                var5.£Ó = JsonUtils.HorizonCode_Horizon_È(var4, "goldCount", var5.£Ó);
                var5.ˆÐƒØ­à = JsonUtils.HorizonCode_Horizon_È(var4, "goldMinHeight", var5.ˆÐƒØ­à);
                var5.£Õ = JsonUtils.HorizonCode_Horizon_È(var4, "goldMaxHeight", var5.£Õ);
                var5.Ï­Ô = JsonUtils.HorizonCode_Horizon_È(var4, "redstoneSize", var5.Ï­Ô);
                var5.Œà = JsonUtils.HorizonCode_Horizon_È(var4, "redstoneCount", var5.Œà);
                var5.Ðƒá = JsonUtils.HorizonCode_Horizon_È(var4, "redstoneMinHeight", var5.Ðƒá);
                var5.ˆÏ = JsonUtils.HorizonCode_Horizon_È(var4, "redstoneMaxHeight", var5.ˆÏ);
                var5.áˆºÇŽØ = JsonUtils.HorizonCode_Horizon_È(var4, "diamondSize", var5.áˆºÇŽØ);
                var5.ÇªÂµÕ = JsonUtils.HorizonCode_Horizon_È(var4, "diamondCount", var5.ÇªÂµÕ);
                var5.áŒŠÏ = JsonUtils.HorizonCode_Horizon_È(var4, "diamondMinHeight", var5.áŒŠÏ);
                var5.áŒŠáŠ = JsonUtils.HorizonCode_Horizon_È(var4, "diamondMaxHeight", var5.áŒŠáŠ);
                var5.ˆÓ = JsonUtils.HorizonCode_Horizon_È(var4, "lapisSize", var5.ˆÓ);
                var5.¥Ä = JsonUtils.HorizonCode_Horizon_È(var4, "lapisCount", var5.¥Ä);
                var5.ÇªÔ = JsonUtils.HorizonCode_Horizon_È(var4, "lapisCenterHeight", var5.ÇªÔ);
                var5.Û = JsonUtils.HorizonCode_Horizon_È(var4, "lapisSpread", var5.Û);
            }
            catch (Exception ex) {}
            return var5;
        }
        
        public JsonElement HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_177862_1_, final Type p_177862_2_, final JsonSerializationContext p_177862_3_) {
            final JsonObject var4 = new JsonObject();
            var4.addProperty("coordinateScale", (Number)p_177862_1_.Â);
            var4.addProperty("heightScale", (Number)p_177862_1_.Ý);
            var4.addProperty("lowerLimitScale", (Number)p_177862_1_.Âµá€);
            var4.addProperty("upperLimitScale", (Number)p_177862_1_.Ø­áŒŠá);
            var4.addProperty("depthNoiseScaleX", (Number)p_177862_1_.Ó);
            var4.addProperty("depthNoiseScaleZ", (Number)p_177862_1_.à);
            var4.addProperty("depthNoiseScaleExponent", (Number)p_177862_1_.Ø);
            var4.addProperty("mainNoiseScaleX", (Number)p_177862_1_.áŒŠÆ);
            var4.addProperty("mainNoiseScaleY", (Number)p_177862_1_.áˆºÑ¢Õ);
            var4.addProperty("mainNoiseScaleZ", (Number)p_177862_1_.ÂµÈ);
            var4.addProperty("baseSize", (Number)p_177862_1_.á);
            var4.addProperty("stretchY", (Number)p_177862_1_.ˆÏ­);
            var4.addProperty("biomeDepthWeight", (Number)p_177862_1_.£á);
            var4.addProperty("biomeDepthOffset", (Number)p_177862_1_.Å);
            var4.addProperty("biomeScaleWeight", (Number)p_177862_1_.£à);
            var4.addProperty("biomeScaleOffset", (Number)p_177862_1_.µà);
            var4.addProperty("seaLevel", (Number)p_177862_1_.ˆà);
            var4.addProperty("useCaves", p_177862_1_.¥Æ);
            var4.addProperty("useDungeons", p_177862_1_.Ø­à);
            var4.addProperty("dungeonChance", (Number)p_177862_1_.µÕ);
            var4.addProperty("useStrongholds", p_177862_1_.Æ);
            var4.addProperty("useVillages", p_177862_1_.Šáƒ);
            var4.addProperty("useMineShafts", p_177862_1_.Ï­Ðƒà);
            var4.addProperty("useTemples", p_177862_1_.áŒŠà);
            var4.addProperty("useMonuments", p_177862_1_.ŠÄ);
            var4.addProperty("useRavines", p_177862_1_.Ñ¢á);
            var4.addProperty("useWaterLakes", p_177862_1_.ŒÏ);
            var4.addProperty("waterLakeChance", (Number)p_177862_1_.Çªà¢);
            var4.addProperty("useLavaLakes", p_177862_1_.Ê);
            var4.addProperty("lavaLakeChance", (Number)p_177862_1_.ÇŽÉ);
            var4.addProperty("useLavaOceans", p_177862_1_.ˆá);
            var4.addProperty("fixedBiome", (Number)p_177862_1_.ÇŽÕ);
            var4.addProperty("biomeSize", (Number)p_177862_1_.É);
            var4.addProperty("riverSize", (Number)p_177862_1_.áƒ);
            var4.addProperty("dirtSize", (Number)p_177862_1_.á€);
            var4.addProperty("dirtCount", (Number)p_177862_1_.Õ);
            var4.addProperty("dirtMinHeight", (Number)p_177862_1_.à¢);
            var4.addProperty("dirtMaxHeight", (Number)p_177862_1_.ŠÂµà);
            var4.addProperty("gravelSize", (Number)p_177862_1_.¥à);
            var4.addProperty("gravelCount", (Number)p_177862_1_.Âµà);
            var4.addProperty("gravelMinHeight", (Number)p_177862_1_.Ç);
            var4.addProperty("gravelMaxHeight", (Number)p_177862_1_.È);
            var4.addProperty("graniteSize", (Number)p_177862_1_.áŠ);
            var4.addProperty("graniteCount", (Number)p_177862_1_.ˆáŠ);
            var4.addProperty("graniteMinHeight", (Number)p_177862_1_.áŒŠ);
            var4.addProperty("graniteMaxHeight", (Number)p_177862_1_.£ÂµÄ);
            var4.addProperty("dioriteSize", (Number)p_177862_1_.Ø­Âµ);
            var4.addProperty("dioriteCount", (Number)p_177862_1_.Ä);
            var4.addProperty("dioriteMinHeight", (Number)p_177862_1_.Ñ¢Â);
            var4.addProperty("dioriteMaxHeight", (Number)p_177862_1_.Ï­à);
            var4.addProperty("andesiteSize", (Number)p_177862_1_.áˆºáˆºÈ);
            var4.addProperty("andesiteCount", (Number)p_177862_1_.ÇŽá€);
            var4.addProperty("andesiteMinHeight", (Number)p_177862_1_.Ï);
            var4.addProperty("andesiteMaxHeight", (Number)p_177862_1_.Ô);
            var4.addProperty("coalSize", (Number)p_177862_1_.ÇªÓ);
            var4.addProperty("coalCount", (Number)p_177862_1_.áˆºÏ);
            var4.addProperty("coalMinHeight", (Number)p_177862_1_.ˆáƒ);
            var4.addProperty("coalMaxHeight", (Number)p_177862_1_.Œ);
            var4.addProperty("ironSize", (Number)p_177862_1_.£Ï);
            var4.addProperty("ironCount", (Number)p_177862_1_.Ø­á);
            var4.addProperty("ironMinHeight", (Number)p_177862_1_.ˆÉ);
            var4.addProperty("ironMaxHeight", (Number)p_177862_1_.Ï­Ï­Ï);
            var4.addProperty("goldSize", (Number)p_177862_1_.£Â);
            var4.addProperty("goldCount", (Number)p_177862_1_.£Ó);
            var4.addProperty("goldMinHeight", (Number)p_177862_1_.ˆÐƒØ­à);
            var4.addProperty("goldMaxHeight", (Number)p_177862_1_.£Õ);
            var4.addProperty("redstoneSize", (Number)p_177862_1_.Ï­Ô);
            var4.addProperty("redstoneCount", (Number)p_177862_1_.Œà);
            var4.addProperty("redstoneMinHeight", (Number)p_177862_1_.Ðƒá);
            var4.addProperty("redstoneMaxHeight", (Number)p_177862_1_.ˆÏ);
            var4.addProperty("diamondSize", (Number)p_177862_1_.áˆºÇŽØ);
            var4.addProperty("diamondCount", (Number)p_177862_1_.ÇªÂµÕ);
            var4.addProperty("diamondMinHeight", (Number)p_177862_1_.áŒŠÏ);
            var4.addProperty("diamondMaxHeight", (Number)p_177862_1_.áŒŠáŠ);
            var4.addProperty("lapisSize", (Number)p_177862_1_.ˆÓ);
            var4.addProperty("lapisCount", (Number)p_177862_1_.¥Ä);
            var4.addProperty("lapisCenterHeight", (Number)p_177862_1_.ÇªÔ);
            var4.addProperty("lapisSpread", (Number)p_177862_1_.Û);
            return (JsonElement)var4;
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
        
        public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            return this.HorizonCode_Horizon_È((HorizonCode_Horizon_È)p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }
    }
}
