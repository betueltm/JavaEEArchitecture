package betueltm.javatests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Example {
	public static void main(String[] args) {
		String[] anagrams = {"eat","tea","tan","ate","nat","bat"};
		List<List<String>> groupedAnagrams = groupAnagrams(anagrams);
		
		System.out.println(groupedAnagrams);
	}
	
	public static List<List<String>> groupAnagrams(String[] anagrams) {
		Stack<String> anagramsStack = new Stack<String>();
		Collections.addAll(anagramsStack, anagrams);
		
		Map<String,List<String>> anagramGroupMap = new HashMap<String, List<String>>();
		
		while(!anagramsStack.isEmpty()) {
			String anagram = anagramsStack.pop();
			boolean existis = false;
			for (String key : anagramGroupMap.keySet()) {
				if(isAnagram(anagram, key)) {
					List<String> anagramGroup = anagramGroupMap.get(key);
					anagramGroup.add(anagram);
					existis = true;
					break;
				}
			}
			
			if(!existis) {
				List<String> newAnagramGroup = new ArrayList<String>();
				newAnagramGroup.add(anagram);
				anagramGroupMap.put(anagram, newAnagramGroup);
			}
		}
		
		return anagramGroupMap.entrySet().stream().map(list -> list.getValue()).collect(Collectors.toList());
	}
	
	private static boolean isAnagram(String a, String b) {
		if(a.length() != b.length()) return false;
		
		char[] charArray = a.toCharArray();
		char[] charArray2 = b.toCharArray();
		
		Arrays.sort(charArray);
		Arrays.sort(charArray2);
		
		return String.valueOf(charArray).equals(String.valueOf(charArray2));
	}
	
//	Given an array of strings strs, group the anagrams together. You can return the answer in any order. 
//			An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, 
	
	//typically using all the original letters exactly once. (Example, Melon and Lemon)
//
//
//			Example 1: Input: strs = ["eat","tea","tan","ate","nat","bat"]
//
//
//			Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
//
//
//			public List<List<String>> groupAnagrams(String[] strs) {
//
//			}
}
