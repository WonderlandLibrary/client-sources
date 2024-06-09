/**
 * 
 */
package cafe.kagu.kagu.prot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.RandomUtils;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.utils.OSUtil;

/**
 * @author DistastefulBannock
 *
 */
public class BasicProcessLookupCheck {
	
	private static long timeout = Long.MAX_VALUE;
	
	/**
	 * Called at cheat start
	 */
	public static void start() {
		if (OSUtil.isUnknown())
			System.exit(Note.OS_TYPE_UNKNOWN);
		final long maxTimeout = 3000;
		final String[] procNames = new String[] {"BurpSuite", "ProcessHacker", "Wireshark", "Cheat Engine", "ReClass"};
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
				    String line;
				    Process p = Runtime.getRuntime().exec(OSUtil.isWindows() ? (System.getenv("windir") +"\\system32\\"+"tasklist.exe") : "ps -e");
				    BufferedReader input =
				            new BufferedReader(new InputStreamReader(p.getInputStream()));
				    while ((line = input.readLine()) != null) {
				    	for (String name : procNames) {
				    		if (line.toLowerCase().contains(name.toLowerCase())) {
				    			Kagu.getKeyAuth().log("Basic process check flagged for \"" + name + "\"", msg -> {}, msg -> {});
				    			Runtime.getRuntime().exit(Note.BASIC_PROCESS_CHECK_FLAGGED);
				    			while (true);
				    		}
				    	}
				    }
				    input.close();
				    timeout = System.currentTimeMillis();
				} catch (Exception err) {
					if (System.currentTimeMillis() - timeout > maxTimeout) {
						Kagu.getKeyAuth().log("Basic process check timed out", msg -> {}, msg -> {});
						Runtime.getRuntime().exit(Note.BASIC_PROCESS_CHECK_TIMEOUT);
						while (true);
					}
				    err.printStackTrace();
				}
			}
		}, RandomUtils.nextLong(0, 500), RandomUtils.nextLong(500, 1500));
	}
	
}
