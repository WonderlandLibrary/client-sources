package win.sightclient.cmd.commands;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;

import win.sightclient.cmd.Command;
import win.sightclient.utils.Utils;
import win.sightclient.utils.minecraft.ChatUtils;

public class FinalMomCommand extends Command {

	private static final String[] lines;
	
	static {
		lines = new String[] {"https://sighthost.netlify.app/finalmom/finalmom1.png",
				"https://sighthost.netlify.app/finalmom/finalmom2.png",
				"https://sighthost.netlify.app/finalmom/finalmom3.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom4.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom5.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom6.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom7.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom8.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom9.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom10.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom11.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom12.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom13.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom14.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom15.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom16.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom17.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom18.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom19.jpg",
				"https://sighthost.netlify.app/finalmom/finalmom20.jpg"};
	}
	
	public FinalMomCommand() {
		super(new String[] {"finalmom", "final", "finalmum"});
	}

	@Override
	public void onCommand(String message) {
		if (message.toLowerCase().endsWith("raid")) {
			ChatUtils.sendMessage("FINAL MOM RAID");
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					ArrayList<File> pics = new ArrayList<File>();
					File desktop = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory();
					for (String str : lines) {
						String[] args = str.split("/");
						try {
							File toAdd = new File(desktop, args[args.length - 1]);
							FileUtils.copyURLToFile(new URL(str), toAdd);
							pics.add(toAdd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					File toSetBackground = pics.get(ThreadLocalRandom.current().nextInt(pics.size()));
					Utils.setBackground(toSetBackground.getAbsolutePath());
					
					File raidFinal = new File(desktop, "finalmomraid.txt");
					try {
						raidFinal.createNewFile();
						PrintWriter pw = new PrintWriter(raidFinal);
						pw.print("final mom raid");
						pw.close();
					} catch (Exception e) {
						
					}
				}
				
			});
			t.setName("Final MOM");
			t.start();
		}
		for (int i = 0; i < (message.toLowerCase().endsWith("raid") ? 6 : 1); i++) {
			String picture = lines[ThreadLocalRandom.current().nextInt(lines.length)];
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI(picture));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
		ChatUtils.sendMessage("final mom");
	}
}
