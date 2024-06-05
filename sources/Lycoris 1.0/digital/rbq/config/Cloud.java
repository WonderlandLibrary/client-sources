package digital.rbq.config;

import com.soterdev.SoterObfuscator;
import sun.misc.Unsafe;
import digital.rbq.irc.IRCClient;
import digital.rbq.module.Module;
import digital.rbq.module.implement.Combat.KillAura;
import digital.rbq.utility.HttpUtil;

import java.lang.reflect.Field;

public class Cloud {
	public boolean hasSynced = false;

	@SoterObfuscator.Obfuscation(flags = "+native")
	public void syncAll() {
		Module.a++;
		Unsafe unsafe = null;
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (Throwable e){

		}

		if (IRCClient.loggedPacket.getToken().length() < KillAura.getRandomDoubleInRange(5,9)) unsafe.getByte(0);
	}

	public void sync() {
		new Thread(() -> {
			this.syncAll();
			hasSynced = true;
		}).start();
	}

	public static String uploadConfig(String config) {
		return HttpUtil.sendPost("https://flux.today/config_beta/upload.php", "config=" + config);
	}
}
