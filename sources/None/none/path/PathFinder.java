package none.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import none.event.events.EventChat;
import none.module.modules.combat.InfiniteAura;

public class PathFinder {

	protected Minecraft mc = Minecraft.getMinecraft();
	public ArrayList<Vec3> path = new ArrayList<>();
	public ArrayList<Node> nodetowork = new ArrayList<>();
	public ArrayList<Node> nodes = new ArrayList<>();
	public ArrayList<Vec3> ways;
	public Vec3 startpos = null;
	public Vec3 endpos = null;
	public boolean cantfind = false;
	public PathFinder() {
		ways = new ArrayList<>();
//		ways.add(new Vec3(1,0,0));
//		ways.add(new Vec3(-1,0,0));
//		ways.add(new Vec3(0,1,0));
//		ways.add(new Vec3(0,-1,0));
//		ways.add(new Vec3(0,0,1));
//		ways.add(new Vec3(0,0,-1));
		ways.add(new Vec3(1,0,0));
		ways.add(new Vec3(1,1,0));
		ways.add(new Vec3(1,-1,0));
		ways.add(new Vec3(1,0,1));
		ways.add(new Vec3(1,0,-1));
		
		ways.add(new Vec3(-1,0,0));
		ways.add(new Vec3(-1,1,0));
		ways.add(new Vec3(-1,-1,0));
		ways.add(new Vec3(-1,0,1));
		ways.add(new Vec3(-1,0,-1));
		
		ways.add(new Vec3(0,1,0));
		ways.add(new Vec3(1,1,0));
		ways.add(new Vec3(-1,1,0));
		ways.add(new Vec3(0,1,1));
		ways.add(new Vec3(0,1,-1));
		
		ways.add(new Vec3(0,-1,0));
		ways.add(new Vec3(1,-1,0));
		ways.add(new Vec3(-1,-1,0));
		ways.add(new Vec3(0,-1,1));
		ways.add(new Vec3(0,-1,-1));
		
		ways.add(new Vec3(0,0,1));
		ways.add(new Vec3(1,0,1));
		ways.add(new Vec3(-1,0,1));
		ways.add(new Vec3(0,1,1));
		ways.add(new Vec3(0,-1,1));
		
		ways.add(new Vec3(0,0,-1));
		ways.add(new Vec3(1,0,-1));
		ways.add(new Vec3(-1,0,-1));
		ways.add(new Vec3(0,1,-1));
		ways.add(new Vec3(0,-1,-1));
		
		ways.add(new Vec3(1,1,1));
		ways.add(new Vec3(1,1,-1));
		ways.add(new Vec3(-1,1,1));
		ways.add(new Vec3(-1,1,-1));
		
		ways.add(new Vec3(1,-1, 1));
		ways.add(new Vec3(1,-1, -1));
		ways.add(new Vec3(-1,-1, 1));
		ways.add(new Vec3(-1,-1, -1));
	}
	
	public ArrayList<Vec3> doPath2(int loop, Vec3 start, Vec3 end) {
		ArrayList<Vec3> path = new ArrayList<>();
		// run in new thread
		nodes.clear();
		nodetowork.clear();
		for (int i = 0; i < loop; i++) {
			path = doPath(start, end);
			if (path != null && !path.isEmpty()) {
				break;
			}
		}
		return path;
	}
	
	public ArrayList<Vec3> doPath(Vec3 start, Vec3 end) {
		startpos = start.floor();
		endpos = end.floor();
		ArrayList<Vec3> initpath = new ArrayList<>();
		initpath.add(start);
		if (nodetowork.size() == 0) {
			nodetowork.add(new Node(null, initpath, start, 0, getDistance(start, end)));
		}
		if (!cantfind) {
//		find :
//			for(int i = 0; i < loop; i++) {
				updateAllHcost(nodetowork);
				sortNode(nodetowork);
				Node node = nodetowork.get(0);
				nodetowork.remove(node);
//				for (Node node : new ArrayList<Node>(nodetowork)) {
//					nodetowork.remove(node);
					nodes.add(node);
					if (node.getLoc().xCoord == end.xCoord && node.getLoc().yCoord == end.yCoord && node.getLoc().zCoord == end.zCoord) {
						path = node.getPath();
						cantfind = true;
//						break;
					}
					for (Vec3 vec : ways) {
						if (vec.yCoord != 0 && InfiniteAura.noy.getObject()) {
							
						}else {
							Vec3 initvec = node.getLoc().add(vec).floor();
							if (canPass(initvec)) {
								addUpdate(node, initvec, 0);
							}
						}
					}
//				}
					
//				if (i == loop && path.isEmpty()) {
//					EventChat.addchatmessage(">.<");
//					cantfind = true;
//				}
//			}
		}
		if (cantfind && !path.isEmpty()) {
			return path;
		}
		return null;
	}
	
	public void addUpdate(Node parent, Vec3 loc, double cost) {
		Node node = isHubExisting(loc);
		double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getFcost();
        }
        if (parent.getNode() == null) {
        	ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
            path.add(loc);
        	nodetowork.add(new Node(parent, path, loc, 1, getDistance(loc, endpos)));
        }else if (node == null) {
        	ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
            path.add(loc);
        	nodetowork.add(new Node(parent, path, loc, parent.getGcost() + 1, getDistance(loc, endpos)));
        }else if (node.getFcost() > parent.getFcost()) {
        	ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
            path.add(loc);
            node.gcost = parent.getGcost();
            node.hcost = getDistance(parent.getLoc(), endpos);
            node.loc = loc;
            node.path = path;
            node.node = parent;
        }
	}
	
	public void sortNode(ArrayList<Node> list) {
		//Sort Fcost
		list.sort(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if (o1.getFcost() + o1.hcost < o2.getFcost() + o2.hcost) {
					return -1;
				}else if (o1.getFcost() + o1.hcost > o2.getFcost() + o2.hcost) {
					return 1;
				}
				return 0;
			}
		});
	}
	
	public void updateAllHcost(ArrayList<Node> list) {
		for (Node node : list) {
			node.hcost = getDistance(node.getLoc(), endpos);
		}
	}
	
	public Node isHubExisting(Vec3 loc) {
        for (Node hub : nodes) {
            if (hub.getLoc().xCoord == loc.xCoord && hub.getLoc().yCoord == loc.yCoord && hub.getLoc().zCoord == loc.zCoord) {
                return hub;
            }
        }
        
        for (Node hub : nodetowork) {
            if (hub.getLoc().xCoord == loc.xCoord && hub.getLoc().yCoord == loc.yCoord && hub.getLoc().zCoord == loc.zCoord) {
                return hub;
            }
        }
        return null;
    }
	
	public boolean canPass(Vec3 vec) {
		BlockPos pos = new BlockPos(vec);
		if (mc.theWorld.getBlock(pos) instanceof BlockAir && mc.theWorld.getBlock(pos.add(0,1,0)) instanceof BlockAir) {
			return true;
		}
		
		if (mc.theWorld.getBlock(pos) instanceof BlockGlass && mc.theWorld.getBlock(pos.add(0,1,0)) instanceof BlockGlass) {
			return true;
		}
		
		if (mc.theWorld.getBlock(pos) instanceof BlockGlass) {
			return true;
		}
		
		if (mc.theWorld.getBlock(pos) instanceof BlockFlower) {
			return true;
		}
		
		if (mc.theWorld.getBlock(pos) instanceof BlockCarpet) {
			return true;
		}
		
		return false;
	}
	
	public double getDistance(Vec3 start, Vec3 end) {
		final double xDiff = start.xCoord - end.xCoord;
        final double yDiff = start.yCoord - end.yCoord;
        final double zDiff = start.zCoord - end.zCoord;
        return MathHelper.sqrt_double(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
	}
	
	public class Node {
		
		public double gcost;// How far to start
		public double hcost;// How far to end
		public double fcost;
		public Vec3 loc;
		public ArrayList<Vec3> path;
		public Node node;
		public Node(Node node,ArrayList<Vec3> path, Vec3 loc, double gcost, double hcost) {
			this.loc = loc;
			this.gcost = gcost;
			this.hcost = hcost;
			this.path = path;
			this.node = node;
			this.fcost = gcost + hcost;
		}
		
		public double getFcost() {
			return gcost + hcost;
		}
		
		public double getGcost() {
			return gcost;
		}
		
		public double getHcost() {
			return hcost;
		}
		
		public Vec3 getLoc() {
			return loc;
		}
		
		public ArrayList<Vec3> getPath() {
			return path;
		}
		
		public Node getNode() {
			return node;
		}
	}
}
