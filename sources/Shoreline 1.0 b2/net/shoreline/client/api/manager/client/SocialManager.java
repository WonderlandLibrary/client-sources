package net.shoreline.client.api.manager.client;

import net.shoreline.client.api.social.SocialRelation;
import net.shoreline.client.util.Globals;
import net.minecraft.entity.player.PlayerEntity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Manages client social relationships by storing the associated player
 * {@link UUID}'s with a {@link SocialRelation} value to the user. Backed by a
 * {@link ConcurrentMap} so getting/setting runs in O(1).
 *
 * @author linus
 * @since 1.0
 *
 * @see UUID
 * @see SocialRelation
 */
public class SocialManager implements Globals
{
    //
    private final ConcurrentMap<UUID, SocialRelation> relationships =
            new ConcurrentHashMap<>();

    /**
     *
     *
     * @param uuid
     * @param relation
     * @return
     */
    public boolean isRelation(UUID uuid, SocialRelation relation)
    {
        return relationships.get(uuid) == relation;
    }

    /**
     *
     *
     * @param uuid
     * @return
     *
     * @see #isRelation(UUID, SocialRelation)
     */
    public boolean isFriend(UUID uuid)
    {
        return isRelation(uuid, SocialRelation.FRIEND);
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean isFriend(String name)
    {
        return getFriendEntities().stream().anyMatch(e -> e.getEntityName().equals(name));
    }

    /**
     *
     *
     * @param uuid
     * @param relation
     */
    public void addRelation(UUID uuid, SocialRelation relation)
    {
        if (mc.player != null && uuid.equals(mc.player.getUuid()))
        {
            return;
        }
        final SocialRelation relationship = relationships.get(uuid);
        if (relationship != null)
        {
            relationships.replace(uuid, relation);
            return;
        }
        relationships.put(uuid, relation);
    }

    /**
     *
     *
     * @param uuid
     *
     * @see #addRelation(UUID, SocialRelation)
     */
    public void addFriend(UUID uuid)
    {
        addRelation(uuid, SocialRelation.FRIEND);
    }

    /**
     *
     * @param uuid
     * @return
     */
    public SocialRelation remove(UUID uuid)
    {
        return relationships.remove(uuid);
    }

    /**
     *
     *
     * @param relation
     * @return
     */
    public Collection<UUID> getRelations(SocialRelation relation)
    {
        final List<UUID> friends = new ArrayList<>();
        for (Map.Entry<UUID, SocialRelation> relationship :
                relationships.entrySet())
        {
            if (relationship.getValue() == relation)
            {
                friends.add(relationship.getKey());
            }
        }
        return friends;
    }

    /**
     *
     *
     * @return
     *
     * @see #getRelations(SocialRelation)
     */
    public Collection<UUID> getFriends()
    {
        return getRelations(SocialRelation.FRIEND);
    }

    /**
     *
     * @param relation
     * @return
     */
    public Collection<PlayerEntity> getEntities(SocialRelation relation)
    {
        if (mc.world == null)
        {
            return null;
        }
        Collection<PlayerEntity> entities = new ArrayList<>();
        for (UUID uuid : getRelations(relation))
        {
            entities.add(mc.world.getPlayerByUuid(uuid));
        }
        return entities;
    }

    /**
     *
     * @return
     */
    public Collection<PlayerEntity> getFriendEntities()
    {
        return getEntities(SocialRelation.FRIEND);
    }
}
