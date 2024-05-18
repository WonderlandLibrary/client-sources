package me.valk;

import java.util.ArrayList;
import java.util.List;

import me.valk.module.Module;
import me.valk.utils.chat.ChatColor;
import me.valk.utils.value.Value;

public class Client {

	private List<Value> vals = new ArrayList<Value>();

	private String name;
	private int build;
	private String[] authors;
	private ClientData data;

	public Client(String name, int build, String[] authors, ClientData data) {
		this.name = name;
		this.build = build;
		this.authors = authors;
		this.data = data;
	}


	public void addValue(Value value){
		this.vals.add(value);
	}

	public List<Value> getVals() {
		return vals;
	}


	public void setVals(List<Value> vals) {
		this.vals = vals;
	}

	public String getName() {
		return name;
	}

	public int getBuild() {
		return build;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void start(){

	}

	public ClientData getData() {
		return data;
	}

	public void setData(ClientData data) {
		this.data = data;
	}

	public static class ClientData {

		private ChatColor displayColor;

		public ClientData(ChatColor displayColor){
			this.displayColor = displayColor;
		}

		public ChatColor getDisplayColor(){
			return this.displayColor;
		}

	}

}
