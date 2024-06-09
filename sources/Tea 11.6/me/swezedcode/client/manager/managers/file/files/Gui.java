package me.swezedcode.client.manager.managers.file.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.cgui.component.Frames;
import me.swezedcode.client.gui.clickGui.ClickGui;
import me.swezedcode.client.gui.clickGui.Frame;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.manager.managers.file.FileCustom;
import net.minecraft.client.Minecraft;


public class Gui extends FileCustom {
	
	private static String fileName = "gui.txt";
	
	public Gui() {
		super("Gui");
	}

	public static void loadSettings() {
		Manager.getManager().getFileManager();
		if (!new File(FileManager.getDirectory(), fileName).exists()) {
			FileManager.createFile(fileName);
			return;
		}
		if (FileManager.readFile(fileName).isEmpty()) {
			return;
		}
		Scanner inFile;
		try {
			inFile = new Scanner(new BufferedReader(new FileReader(Minecraft.getMinecraft().mcDataDir + "\\" + Tea.getInstance().getName() + "\\" + "gui" + ".txt")));
			while (inFile.hasNextLine()) {
				String f = inFile.nextLine();
				String[] parts = f.split(":");
				String title = parts[0];
				int x = Integer.parseInt(parts[1]);
				int y = Integer.parseInt(parts[2]);
				boolean open = Boolean.valueOf(parts[3]);
				for (Frame frame : ClickGui.Frames) {
					if (frame.title.equals(title)) {
						frame.setX(x);
						frame.setY(y);
						frame.setExpanded(open);
					}
				}
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void saveSettings() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(
					new File(Minecraft.getMinecraft().mcDataDir + "\\" + Tea.getInstance().getName() + "\\" + "gui" + ".txt")));
			for (Frame panel : ClickGui.Frames) {
				printWriter.println(String.valueOf(panel.getTitle()) + ":" + panel.getX() + ":" + panel.getY() + ":"
						+ panel.isExpanded());
			}
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
