package com.kilo.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.kilo.ui.inter.slotlist.part.HackFriend;

public class ChatSpamManager {

	private static List<String> list = new CopyOnWriteArrayList<String>();
	
	public static List<String> getList() {
		return list;
	}
	
	public static void addMessage(String f) {
		list.add(f);
	}
	
	public static void addMessage(int index, String w) {
		list.add(index, w);
	}
	
	public static void removeMessage(String w) {
		list.remove(w);
	}
	
	public static void removeMessage(int index) {
		list.remove(list.get(index));
	}
	
	public static String getMessage(int index) {
		if (list.size() == 0) {
			return null;
		}
		return list.get(index);
	}
	
	public static int getIndex(String w) {
		return list.indexOf(w);
	}
	
	public static int getSize() {
		return getList().size();
	}
}
