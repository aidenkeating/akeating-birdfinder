package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnionFind {
	private int[] connected;
	private int[] sizes;
	
	public UnionFind(int n) {
		connected = new int[n];
		sizes = new int[n];
		
		for(int i = 0; i < n; i++) {
			connected[i] = i;
			sizes[i] = 1;
		}
	}
	
	public void join(int nodeOne, int nodeTwo) {
		int nodeOneRoot = root(nodeOne);
		int nodeTwoRoot = root(nodeTwo);
		
		if(nodeOneRoot == nodeTwoRoot) {
			return;
		}
		if(sizes[nodeOneRoot] > sizes[nodeTwoRoot]) {
			connected[nodeTwoRoot] = nodeOneRoot;
			sizes[nodeOneRoot] += sizes[nodeTwoRoot];
			sizes[nodeTwoRoot] = 1;
		} else {
			connected[nodeOneRoot] = nodeTwoRoot;
			sizes[nodeTwoRoot] += sizes[nodeOneRoot];
			sizes[nodeOneRoot] = 1;
		}
	}
	
	public int root(int node) {
		while(connected[node] != node) {
			node = connected[node];
		}
		return node;
	}
	
	public Set<Integer> getRoots(int noiseReduction) {
		Set<Integer> roots = new HashSet<>();
		for(int i = 0; i < sizes.length; i++) {
			if(sizes[i] > noiseReduction) {
				roots.add(i);
			}
		}
		return roots;
	}
	
	public List<Integer> getConnectedNodes(int node) {
		List<Integer> nodes = new ArrayList<>();
		for(int i = 0; i < connected.length; i++) {
			if(root(node) == root(i)) {
				nodes.add(i);
			}
		}
		return nodes;
	}

}
