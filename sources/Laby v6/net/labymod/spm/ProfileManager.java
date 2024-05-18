package net.labymod.spm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

public class ProfileManager
{
    public static File PROFILES_FOLDER = new File("spm");
    public static File OF_FOLDER = new File(PROFILES_FOLDER, "of");
    public static File CURRENT_OPTIONS = new File("options.txt");
    public static File CURRENT_OF_OPTIONS = new File("optionsof.txt");
    private static ProfileManager instance;
    private ArrayList<File> profiles = new ArrayList();

    public ArrayList<File> getProfiles()
    {
        return this.profiles;
    }

    public static ProfileManager getProfileManager()
    {
        return instance == null ? new ProfileManager() : instance;
    }

    public ProfileManager()
    {
        instance = this;
        this.loadProfiles();
    }

    public void loadProfiles()
    {
        this.profiles.clear();

        if (!PROFILES_FOLDER.exists())
        {
            PROFILES_FOLDER.mkdir();
            System.out.println("[SPM] Profiles folder created");
        }

        if (!OF_FOLDER.exists())
        {
            OF_FOLDER.mkdir();
        }

        File[] afile = PROFILES_FOLDER.listFiles();

        if (afile != null && afile.length != 0)
        {
            for (File file1 : afile)
            {
                if (file1 != null && file1.getName().endsWith(".txt") && file1.getName().length() >= 5)
                {
                    this.profiles.add(file1);
                }
            }
        }
        else
        {
            System.out.println("[SPM] Folder is empty");
        }
    }

    public boolean saveProfileAs(String name)
    {
        return this.saveProfileAs(new File(PROFILES_FOLDER, name + ".txt"));
    }

    public boolean saveProfileAs(File file)
    {
        if (!CURRENT_OPTIONS.exists())
        {
            return false;
        }
        else
        {
            try
            {
                if (file.exists())
                {
                    file.delete();
                }

                FileUtils.copyFile(CURRENT_OPTIONS, file);

                if (CURRENT_OF_OPTIONS.exists())
                {
                    File file1 = new File(OF_FOLDER, file.getName());

                    if (file1.exists())
                    {
                        file1.delete();
                    }

                    FileUtils.copyFile(CURRENT_OF_OPTIONS, file1);
                }

                if (!this.profiles.contains(file))
                {
                    this.profiles.add(file);
                }

                System.out.println("[SPM] Saved profile " + file.getName());
                return true;
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
                return false;
            }
        }
    }

    public boolean deleteProfile(File file)
    {
        if (!file.exists())
        {
            return false;
        }
        else if (file.delete())
        {
            File file1 = new File(OF_FOLDER, file.getName());

            if (file1.exists())
            {
                file1.delete();
            }

            this.profiles.remove(file);
            System.out.println("[SPM] Deleted profile " + file.getName());
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean loadProfile(File file)
    {
        if (!file.exists())
        {
            return false;
        }
        else
        {
            if (CURRENT_OPTIONS.delete())
            {
                try
                {
                    File file1 = new File(OF_FOLDER, file.getName());

                    if (file1.exists() && CURRENT_OF_OPTIONS.delete())
                    {
                        FileUtils.copyFile(file1, CURRENT_OF_OPTIONS);
                    }

                    FileUtils.copyFile(file, CURRENT_OPTIONS);
                    Minecraft.getMinecraft().gameSettings.loadOptions();
                    System.out.println("[SPM] Loaded profile " + file.getName());
                    return true;
                }
                catch (IOException ioexception)
                {
                    ioexception.printStackTrace();
                }
            }

            return false;
        }
    }
}
