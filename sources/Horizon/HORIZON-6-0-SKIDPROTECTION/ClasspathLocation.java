package HORIZON-6-0-SKIDPROTECTION;

import java.io.InputStream;
import java.net.URL;

public class ClasspathLocation implements ResourceLocation
{
    @Override
    public URL HorizonCode_Horizon_È(final String ref) {
        final String cpRef = ref.replace('\\', '/');
        return ResourceLoader.class.getClassLoader().getResource(cpRef);
    }
    
    @Override
    public InputStream Â(final String ref) {
        final String cpRef = ref.replace('\\', '/');
        return ResourceLoader.class.getClassLoader().getResourceAsStream(cpRef);
    }
}
