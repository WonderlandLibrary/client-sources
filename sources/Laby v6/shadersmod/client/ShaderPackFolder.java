package shadersmod.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ShaderPackFolder implements IShaderPack
{
    protected File packFile;

    public ShaderPackFolder(String name, File file)
    {
        this.packFile = file;
    }

    public void close()
    {
    }

    public InputStream getResourceAsStream(String resName)
    {
        try
        {
            File file1 = new File(this.packFile, resName.substring(1));

            if (file1 != null)
            {
                return new BufferedInputStream(new FileInputStream(file1));
            }
        }
        catch (Exception var3)
        {
            ;
        }

        return null;
    }

    public String getName()
    {
        return this.packFile.getName();
    }
}
