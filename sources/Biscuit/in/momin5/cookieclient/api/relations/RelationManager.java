package in.momin5.cookieclient.api.relations;

import in.momin5.cookieclient.api.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RelationManager extends Util {
	private static final List<Relation> relations = new ArrayList<>();

	public RelationManager() {
		relations.add(new Relation(mc.session.getUsername(), mc.session.getPlayerID(), RelationType.FRIEND));
	}

	public static void addRelation(Relation relation) {
		relations.add(relation);
	}
	public static Relation addFriend(String name, UUID uuid) {
		Relation relation = new Relation(name, uuid, RelationType.FRIEND);
		relations.add(relation);
		return relation;
	}
	public static Relation addEnemy(String name, UUID uuid) {
		Relation relation = new Relation(name, uuid, RelationType.ENEMY);
		relations.add(relation);
		return relation;
	}

	public static boolean isFriend(UUID uuid) {
		for (Relation relation : relations) {
			if (relation.getType() == RelationType.FRIEND && relation.getUuid().equals(uuid))
				return true;
		}
		return false;
	}
	public static boolean isEnemy(UUID uuid) {
		for (Relation relation : relations) {
			if (relation.getType() == RelationType.ENEMY && relation.getUuid().equals(uuid))
				return true;
		}
		return false;
	}

	public static Relation getRelation(UUID uuid) {
		for (Relation relation : relations) {
			if (relation.getType() == RelationType.FRIEND && relation.getUuid().equals(uuid))
				return relation;
		}
		return null;
	}
}
