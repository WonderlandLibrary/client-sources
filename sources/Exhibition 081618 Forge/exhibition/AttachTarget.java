package exhibition;

import java.lang.instrument.Instrumentation;

import javax.swing.JOptionPane;

import net.minecraft.launchwrapper.LaunchClassLoader;

public class AttachTarget {
	
	/**PRE START CODE OF MINECRAFT just instance a Squad.class pre start*/
	public static void agentmain(final String args, final Instrumentation instrumentation) {
		try {
			for (final Class<?> classes : instrumentation.getAllLoadedClasses()) {
				if (classes.getName().equals("net.minecraft.client.Minecraft")) {
					LaunchClassLoader classLoader = (LaunchClassLoader)classes.getClassLoader();
					classLoader.addURL(MCHook.class.getProtectionDomain().getCodeSource().getLocation());
					final Class<?> instance = classLoader.loadClass(MCHook.class.getName());
					instance.newInstance();
					return;
				}
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());  
		}
	}
}
