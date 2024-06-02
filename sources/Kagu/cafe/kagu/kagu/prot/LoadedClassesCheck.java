/**
 * 
 */
package cafe.kagu.kagu.prot;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import cafe.kagu.kagu.Kagu;

/**
 * @author DistastefulBannock
 *
 */
public class LoadedClassesCheck {

	/**
	 * Called at cheat start
	 */
	public static void start() {
		try {
			Thread.sleep(new Random().nextInt(501) + 150);
		} catch (Exception e) {

		}
		new Thread(() -> {
			String[] allowedPackages = new String[] { "i.love.sucking.hard.cocks"/* This is the package that remapped classes will be placed into */,
					"org.eclipse", // Comment this out before release, if you don't then people will be able to load the eclipse debugger agents
					"cafe.kagu.kagu", "net.minecraft", "assets.minecraft", "assets.kagusounds", "optifine", "shadersmod.client", "shadersmod.common",
					"joptsimple", "org.apache.logging", "javax", "com.sun", "com.google", "org.lwjgl", "org.apache.commons", "org.json",
					"org.apache.http", "org.yaml", "com.sparkjava", "spark", "org.slf4j", "net.java.dev.jna", "com.mojang.authlib",
					"io.netty", "com.mojang.util", "oshi", "tv.twitch", "paulscode", "com.ibm.icu", "org.eclipse.jetty", "com.fasterxml",
					"com.jcraft", "com.mojang.patchy", "sun.security", "cafe.kagu.keyauth", "okhttp3", "kotlin", "okio", "com.intellij.rt.execution",
					"com.intellij.rt.debugger"};
			while (true) try {
				Thread.sleep(ThreadLocalRandom.current().nextLong(1001) + 1500);
				ClassLoader myCL = Thread.currentThread().getContextClassLoader();
				while (myCL != null) {
					i:
					for (Iterator<?> iter = list(myCL); iter.hasNext();) {
						String next = iter.next() + "";
						try {
							String packageName = next.split(" ")[1];
//							for (String s : packageName.split("\\."))
//								System.err.println(s);
							packageName = packageName.substring(0, packageName.length() - packageName.split("\\.")[packageName.split("\\.").length - 1].length() - 1);
							for (String allowedPackage : allowedPackages) {
								if (packageName.startsWith(allowedPackage))
									continue i;
							}
//							System.err.println(packageName);
							Kagu.getKeyAuth().log("Failed loaded class check, the class \"" + next + "\" was loaded but the class isn't on the whitelist", msg -> {}, msg -> {});
							Kagu.getKeyAuth().ban(msg -> {}, msg -> {});
							Runtime.getRuntime().halt(Note.LOADED_CLASSES_INVALID_PACKAGE_CHECK);
							while (true);
						} catch (Exception e) {
							
						}
					}
					myCL = myCL.getParent();
				}
			} catch (Exception e) {
				
			}
		}).start();
	}
	
	/**
	 * Skidded from stackoverflow
	 * @param CL
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static Iterator<?> list(ClassLoader CL)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> CL_class = CL.getClass();
		while (CL_class != java.lang.ClassLoader.class) {
			CL_class = CL_class.getSuperclass();
		}
		java.lang.reflect.Field ClassLoader_classes_field = CL_class.getDeclaredField("classes");
		ClassLoader_classes_field.setAccessible(true);
		Vector<?> classes = (Vector<?>) ClassLoader_classes_field.get(CL);
		return classes.iterator();
	}

}
