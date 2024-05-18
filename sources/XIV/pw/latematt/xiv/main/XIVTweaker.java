package pw.latematt.xiv.main;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthew
 */
public class XIVTweaker implements ITweaker {
    private List<String> arguments = new ArrayList<>();

    public void acceptOptions(List<String> arguments, File gameDir, File assetsDir, String profile) {
        this.arguments.addAll(arguments);
        this.arguments.add("--gameDir");
        this.arguments.add(gameDir.getAbsolutePath());

        this.arguments.add("--version");
        this.arguments.add(profile);
    }

    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {

    }

    public String getLaunchTarget() {
        return "pw.latematt.xiv.main.XIVMain";
    }

    public String[] getLaunchArguments() {
        return arguments.toArray(new String[arguments.size()]);
    }
}
