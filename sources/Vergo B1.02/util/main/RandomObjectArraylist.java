package xyz.vergoclient.util.main;
import java.util.ArrayList;
import java.util.Random;

public class RandomObjectArraylist<E> extends ArrayList<E> {
	
	public RandomObjectArraylist(E... things) {
		
		for (E thing: things) {
			this.add(thing);
		}
		
	}
	
	public E getRandomObject() {
		
		if (this.size() == 0) {
			return null;
		}else {
			try {
				return this.get(new Random().nextInt(this.size() - 1));
			} catch (Exception e) {
				return this.get(0);
			}
		}
		
	}
	
}