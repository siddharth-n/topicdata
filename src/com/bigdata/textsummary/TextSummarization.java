package com.bigdata.textsummary;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

public class TextSummarization {
	static ArrayList<String> stopWordsList ;
	static Set<String> vocabulary;
	static Set<String> tokenSet;
	static Map<String,Term> termStatsMap;
	static Map<String,Integer> termFrequencyMap;
	static int docSetSize = 0;
	static Map<String,Double> tfIdfMap;
	
	static String filePath = "tweets.txt"; 
	
	public static void main(String args[]){
		vocabulary =  new HashSet<String>();
		Tokenizer tokenizer =  new Tokenizer();
		tokenizer.readTweets(filePath);
		tokenSet =  tokenizer.getTokenSet();
		termStatsMap =  tokenizer.getTermStatsMap();
		termFrequencyMap =  tokenizer.getTermFrequencyMap();
		docSetSize =  tokenizer.getDocSetSize();
		tfIdfMap = new HashMap<String,Double>();
		computeTfIdfWeights();
		showTopTen(tfIdfMap);
	}
	
		
	static void computeTfIdfWeights(){
		for(String token:tokenSet){
			int tf = termFrequencyMap.get(token);
//			System.out.println(token+" : "+tf);
			double df = termStatsMap.get(token).getDocFrequency();
			double Idf = Math.log(docSetSize/df)/Math.log(2);
			double tf_idf =  tf*Idf;
//			System.out.println(token+" : "+tf_idf);
			tfIdfMap.put(token,tf_idf);
		}
	}
		
	
	private static void showTopTen(Map<String, Double> w_table) {
		TreeSet<Entry<String, Double>> sortedSet = new TreeSet<Entry<String, Double>>(new ValueComparator());
		sortedSet.addAll(w_table.entrySet());
		Iterator<Entry<String, Double>> iterator = sortedSet.iterator();
		for(int i = 0 ; i < 10 && iterator.hasNext(); i++){
			Entry<String, Double> entry = iterator.next();
//			System.out.println((i+1) + " : " + entry.getValue() + " : " /*+ documentInfo.docId*/ + " : " + document.getDoc_name() + " : " + document.getDoc_title());
			System.out.println(entry.getKey()+" : "+entry.getValue());
		}			
	}
	
	static  class ValueComparator implements Comparator<Entry<String, Double>> {
		@Override
		public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
			if(o1.getValue() < o2.getValue()){
				return 1;
			}	
		   return -1;
		}
	}
	
	
}
