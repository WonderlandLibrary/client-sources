package me.vaziak.sensation.client.api.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import me.vaziak.sensation.client.api.event.annotations.Collect;

/**
 * @author Spec
 * 
 * @since 10/23/2019
 *
 * Fuck them naming conventions - I was hype listening to THAT SUPERMAN THAT OOOOOOOO
 * 
 * 
 * I m d own a quatr bag
 * */

public class EventSystem {
	 private static List<Class> SUPERMANTHATOOOOOOOH = new ArrayList<>();

	 public static void call(Event NOWWATCHMEROLE) {
		 if (SUPERMANTHATOOOOOOOH.size() >= 0) {	
			 SUPERMANTHATOOOOOOOH.forEach(souljaBoi -> {
				 Method[] offinthiOOh = souljaBoi.getMethods();
				 
				 Stream.of(offinthiOOh).parallel().filter(WATCHMECRANK -> WATCHMECRANK.isAnnotationPresent(Collect.class)
						 && WATCHMECRANK.getParameterTypes().length == 1
						 && WATCHMECRANK.getParameterTypes()[0].isInstance(NOWWATCHMEROLE)).forEach(method -> {
							 try {
								 method.invoke(souljaBoi.newInstance(), NOWWATCHMEROLE);
							 } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
								 System.out.println("YOUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
								 e.printStackTrace();
							 }
						 });
			 });
		 }
	 }

	 public static void hook(Object object) {
		 if (object == null) {

			 System.out.println("Wtf dd i jst do?");
		 }
		 if (SUPERMANTHATOOOOOOOH.contains(object.getClass())) {
			 throw new RuntimeException(object.getClass() + " is already registered you dumb shit");
		 } else {
			 SUPERMANTHATOOOOOOOH.add(object.getClass());
		 }
	 }

	 public static void unhook(Object object) {
		 if (!SUPERMANTHATOOOOOOOH.contains(object.getClass())) {
			 throw new RuntimeException("Could not unregister " + object.getClass() + " because its not registed you dumb fuck");
		 } else {
			 SUPERMANTHATOOOOOOOH.remove(object.getClass());
		 }
	 }
}
