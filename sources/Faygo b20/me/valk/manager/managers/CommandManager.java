package me.valk.manager.managers;

import java.util.ArrayList;
import java.util.Set;

import com.google.common.reflect.ClassPath;

import me.valk.Vital;
import me.valk.command.Command;
import me.valk.command.commands.BindCommand;
import me.valk.command.commands.FriendCommand;
import me.valk.command.commands.SpamCommand;
import me.valk.command.commands.ThemeCommand;
import me.valk.command.commands.ToggleCommand;
import me.valk.command.commands.VClipCommand;
import me.valk.command.commands.WayPointCommand;
import me.valk.command.commands.hClipCommand;
import me.valk.manager.Manager;
import me.valk.module.annotations.DevOnly;

public class CommandManager extends Manager<Command> {

	public CommandManager(){
		addContent(new BindCommand());
		addContent(new FriendCommand());
		addContent(new ToggleCommand());
		addContent(new VClipCommand());
		addContent(new ThemeCommand());
		addContent(new hClipCommand());
		addContent(new WayPointCommand());
		addContent(new SpamCommand());

	}

	public Command getCommandFromName(String name){
		for(Command m : getContents()){
			if(m.getName().equalsIgnoreCase(name))
				return m;
		}

		return null;
	}

	public Command getCommandFromClass(Class clas){
		for(Command m : getContents()){
			if(m.getClass() == clas){
				return m;
			}
		}
		return null;
	}

	private ArrayList<Class<?>> getClasses(final String packageName){
		final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		try{
			final ClassLoader loader = Thread.currentThread()
					.getContextClassLoader();
			for(final ClassPath.ClassInfo info : ClassPath.from(loader)
					.getAllClasses()){
				if(info.getName().startsWith(packageName)){
					final Class<?> clazz = info.load();
					classes.add(clazz);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return classes;
	}

	public boolean hasCommand(Command m){

		for(Command command : getContents()){
			if(command == m)
				return true;
		}

		return false;
	}
	
}
