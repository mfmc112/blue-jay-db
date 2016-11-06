package com.bluejay.core.storage.node;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PrefixNode implements Serializable {

	private static final long serialVersionUID = 8132935311422861818L;

	char c;
	Set<Long> matchingIds;
	Map<Character, PrefixNode> children;
	
	public PrefixNode(char c) {
		this.c = c;
		this.matchingIds = null;
		children = null;
	}
	
	public void insert(String s, Long id){
		insert(s, 0, id);
	}
	
	public void insert(String s, int pos, Long id){
		if (s == null || s.length() <= 0 || pos >= s.length()) return;
		
		// Create Map to the exact size of the String to avoid resizing
		if (children == null) children = new HashMap<Character, PrefixNode>(s.length());
		
		char c = s.charAt(pos);
		PrefixNode node = children.get(c);
		if (node == null){ // does not exist
			node = new PrefixNode(c);
			children.put(c, node);
		}
		
		if (pos == s.length()-1){
			if (node.matchingIds == null) node.matchingIds = new HashSet<Long>();
			node.matchingIds.add(id);
			return;
		}else{
			node.insert(s, ++pos, id);
		}
	}
	
	public Set<Long> search(String s) {
		return search(s, 0);
	}
	
	private Set<Long> search(String s, int pos) {
		if (s == null || s.length() <= 0 || pos > s.length() ) return null;
		if (children == null) return null;
	
		char c = s.charAt(pos);
		PrefixNode node = children.get(c);
		if (node == null) return null;
		if (pos == s.length()-1) return node.matchingIds;

		return node.search(s, ++pos);
	}

	public Set<Long> searchStartWith(String s) {
		return searchStartWith(s, 0);
	}

	
	public Set<Long> searchStartWith(String s, int pos) {
		if (s == null || s.length() <= 0) return null;
		
		if (pos <= s.length()-1){
			char c = s.charAt(pos);
			if (children == null) return null; // did not find it
			
			PrefixNode node = children.get(c);
			if (node == null) return null; // did not find it
			return node.searchStartWith(s, ++pos);
		}else if (pos > s.length()-1){ // Found a match
			// return all the children ids
			if (children != null) {
				Set<Long> ids = new HashSet<Long>();
				for (PrefixNode node : children.values()) {
					if (node.children != null){
						Set<Long> foundIds = node.searchStartWith(s, pos);
						if (foundIds!= null) ids.addAll(foundIds);
					}else{
						return node.matchingIds;		
					}
				}
				return ids;
			}else{
				return matchingIds;
			}
		}
		return null;
	}
	
}
