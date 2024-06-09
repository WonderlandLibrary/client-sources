package HORIZON-6-0-SKIDPROTECTION;

import java.io.File;
import java.io.FileNotFoundException;

public class ResourcePackFileNotFoundException extends FileNotFoundException
{
    private static final String HorizonCode_Horizon_È = "CL_00001086";
    
    public ResourcePackFileNotFoundException(final File p_i1294_1_, final String p_i1294_2_) {
        super(String.format("'%s' in ResourcePack '%s'", p_i1294_2_, p_i1294_1_));
    }
}
