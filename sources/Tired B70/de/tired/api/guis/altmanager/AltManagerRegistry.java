package de.tired.api.guis.altmanager;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class AltManagerRegistry {

	@Getter
	@Setter
	public static Account lastAlt;
	@Getter
	@Setter
	public static ArrayList<Account> registry = new ArrayList<>();


}
