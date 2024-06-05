package me.mees.remix.pathfinder;

import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.block.material.*;

public class NodeProcessor
{
    public ArrayList<Node> path;
    public ArrayList<Node> triedPaths;
    
    public NodeProcessor() {
        this.path = new ArrayList<Node>();
        this.triedPaths = new ArrayList<Node>();
    }
    
    public static Block getBlock(final BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }
    
    private ArrayList<Node> getNeighbors(final Node node) {
        final ArrayList<Node> neighbors = new ArrayList<Node>();
        BlockPos b1 = node.getBlockpos();
        BlockPos b2 = node.getBlockpos();
        b1 = b1.add(1, 1, 1);
        b2 = b2.add(-1, -1, -1);
        neighbors.add(this.createNode(b1.offsetUp()));
        neighbors.add(this.createNode(b1.offsetDown()));
        neighbors.add(this.createNode(b1.offsetEast()));
        neighbors.add(this.createNode(b1.offsetWest()));
        neighbors.add(this.createNode(b2.offsetUp()));
        neighbors.add(this.createNode(b2.offsetDown()));
        neighbors.add(this.createNode(b2.offsetEast()));
        neighbors.add(this.createNode(b2.offsetWest()));
        neighbors.add(this.createNode(b1.offsetNorth()));
        neighbors.add(this.createNode(b1.offsetSouth()));
        for (final BlockPos pos : BlockPos.getAllInBox(b1, b2)) {
            if (pos.equals(node.getBlockpos())) {
                continue;
            }
            if (pos.getX() > node.getBlockpos().getX() && pos.getZ() > node.getBlockpos().getZ()) {
                continue;
            }
            if (pos.getX() < node.getBlockpos().getX() && pos.getZ() < node.getBlockpos().getZ()) {
                continue;
            }
            if (pos.getX() > node.getBlockpos().getX() && pos.getZ() > node.getBlockpos().getZ()) {
                continue;
            }
            if (pos.getX() > node.getBlockpos().getX() && pos.getZ() < node.getBlockpos().getZ()) {
                continue;
            }
            neighbors.add(this.createNode(pos));
        }
        return neighbors;
    }
    
    public void getPath(final BlockPos start, final BlockPos finish) {
        final Node startNode = this.createNode(start);
        final Node endNode = this.createNode(finish);
        final ArrayList<Node> openNodes = new ArrayList<Node>();
        final ArrayList<Node> closedNodes = new ArrayList<Node>();
        openNodes.clear();
        openNodes.add(startNode);
        final int count = 0;
        while (openNodes.size() > 0) {
            Node currentNode = openNodes.get(0);
            double minFCost = 100000.0;
            for (int i = 1; i < openNodes.size(); ++i) {
                final double FCost = openNodes.get(i).getF_Cost(currentNode, endNode);
                if (FCost < minFCost) {
                    minFCost = FCost;
                    currentNode = openNodes.get(i);
                }
            }
            openNodes.clear();
            openNodes.remove(currentNode);
            closedNodes.add(currentNode);
            this.triedPaths.add(currentNode);
            if (currentNode.getBlockpos().equals(endNode.getBlockpos())) {
                endNode.parent = currentNode;
                this.retracePath(startNode, endNode);
                return;
            }
            for (final Node neighbor : this.getNeighbors(currentNode)) {
                if (this.isNodeClosed(neighbor, closedNodes)) {
                    continue;
                }
                final double hCost = currentNode.getH_Cost(endNode);
                if (hCost < neighbor.getH_Cost(endNode) && this.isNodeClosed(neighbor, openNodes)) {
                    continue;
                }
                neighbor.parent = currentNode;
                if (this.isNodeClosed(neighbor, openNodes)) {
                    continue;
                }
                openNodes.add(neighbor);
            }
        }
    }
    
    private boolean isNodeClosed(final Node node, final ArrayList<Node> nodes) {
        for (final Node n : nodes) {
            if (n.getBlockpos().equals(node.getBlockpos())) {
                return true;
            }
        }
        return false;
    }
    
    private void retracePath(final Node startNode, final Node endNode) {
        final ArrayList<Node> path = new ArrayList<Node>();
        for (Node currentNode = endNode; !currentNode.equals(startNode); currentNode = currentNode.parent) {
            path.add(currentNode);
        }
        Collections.reverse(path);
        this.path = path;
    }
    
    public Node createNode(final BlockPos pos) {
        return new Node(getBlock(pos).getMaterial() == Material.air && getBlock(pos.offsetUp()).getMaterial() == Material.air && getBlock(pos.offsetUp()).getMaterial() == Material.air, pos);
    }
}
