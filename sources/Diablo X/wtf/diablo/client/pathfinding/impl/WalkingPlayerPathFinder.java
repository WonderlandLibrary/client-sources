package wtf.diablo.client.pathfinding.impl;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import wtf.diablo.client.pathfinding.api.PathNode;

import java.util.*;

public final class WalkingPlayerPathFinder {

    private static final TreeSet<PathNode> QUEUE = new TreeSet<>(Comparator.comparing(PathNode::getFScore));

    // maps a node to the node that was comes before it in the path
    private static final HashMap<PathNode, PathNode> PATH = new HashMap<>();
    // maps the position hash of a node to the corresponding node at that position
    private static final HashMap<Integer, PathNode> NODES = new HashMap<>();

    private PathNode start, goal;
    private PathNode bestNode, lastNode;
    private int maxIterations;
    private final boolean legit;

    /**
     * Creates the path finder instance
     */
    public WalkingPlayerPathFinder(final int maxIterations, final boolean legit)
    {
        this.legit = legit;
        this.maxIterations = maxIterations;
    }

    /**
     *
     * @param start the starting position
     * @param goal the position to locate
     */
    public WalkingPlayerPathFinder setDestination(final BlockPos start, final BlockPos goal)
    {

        QUEUE.clear();
        PATH.clear();
        NODES.clear();

        this.start = new PathNode(start.getX(), start.getY(), start.getZ(), this.legit);
        this.goal = new PathNode(goal.getX(), goal.getY(), goal.getZ(), this.legit);

        this.start.setGScore(0);
        this.start.setHScore(this.heuristic(this.start));

        this.bestNode = this.start;

        QUEUE.add(this.start);

        return this;
    }

    private PathNode[] retracePath(PathNode current)
    {
        final HashSet<PathNode> containedNodes = new HashSet<>();
        final ArrayList<PathNode> path = new ArrayList<>();

        do {
            path.add(current);

            if (containedNodes.contains(current)) break;
            containedNodes.add(current);
        } while ((current = PATH.get(current)) != null);


        return path.toArray(new PathNode[0]);
    }

    public PathNode[] createPath(final WorldClient worldClient)
    {
        return this.createPath(maxIterations, worldClient);
    }

    /**
     * Executes the task with a maximum of the given amount of max iterations in the given world
     */
    public PathNode[] createPath(final int maxIterations, final WorldClient worldClient)
    {
        PathNode currentNode;
        int i;
        for (i = 0; i < maxIterations && (currentNode = QUEUE.pollFirst()) != null; i++) {
            if (currentNode.equals(this.goal) || (!this.goal.isAccessible(worldClient) && manhattenDistance(currentNode, this.goal) < 4))
                return retracePath(currentNode);

            for (int[] offset : PathNode.NEIGHBOR_OFFSETS) {
                final PathNode neighbor = getPathNodeAt(currentNode.getPosX() + offset[0], currentNode.getPosY() + offset[1], currentNode.getPosZ() + offset[2]);
                final float tentativeGScore = currentNode.getGScore() + offset[3];

                if (tentativeGScore < neighbor.getGScore() && neighbor.isAccessible(worldClient)) {
                    PATH.put(neighbor, currentNode);

                    neighbor.setGScore(tentativeGScore);
                    neighbor.setHScore(this.heuristic(neighbor));
                    if (neighbor.getHScore() < bestNode.getHScore()) bestNode = neighbor;

                    QUEUE.add(neighbor);
                }
            }
        }

        return this.retracePath(this.bestNode);
    }

    /**
     * Returns all previously explored PathNodes
     *
     * @return all explored PathNodes
     */
    public PathNode[] getExplored()
    {
        return NODES.values().toArray(new PathNode[0]);
    }

    /**
     * Returns the PathNode at a given position, or creates one and
     * puts it into the Node map, if it isn't already an element of it.
     *
     * @param x the x-coordinate of the path node
     * @param y the y-coordinate of the path node
     * @param z the z-coordinate of the path node
     * @return the PathNode in the node map if it exists,
     * otherwise a new PathNode that is then inserted into the node map
     */
    private PathNode getPathNodeAt(int x, int y, int z)
    {
        final int positionHash = PathNode.positionHash(x, y, z);

        PathNode pathNode;
        if ((pathNode = NODES.get(positionHash)) != null) return pathNode;

        pathNode = new PathNode(x, y, z, this.legit);
        NODES.put(positionHash, pathNode);
        return pathNode;
    }

    /**
     * The heuristic function is what specifies which nodes are to be prioritised
     */
    private float heuristic(final PathNode pathNode)
    {
        return 2.5F * manhattenDistance(pathNode, this.goal);
    }

    /**
     * Returns the manhatten distance (or L1 norm) between nodeA and nodeB
     */
    public static float manhattenDistance(final PathNode nodeA, final PathNode nodeB)
    {
        final int diffX = nodeA.getPosX() - nodeB.getPosX();
        final int diffY = nodeA.getPosY() - nodeB.getPosY();
        final int diffZ = nodeA.getPosZ() - nodeB.getPosZ();

        return Math.abs(diffX) + Math.abs(diffY) + Math.abs(diffZ);
    }
}