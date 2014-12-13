package com.bigdata.textsummary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Tokenizer {
	
	Map<String,Integer> wordFrequencyMap;
	Set<String> tokenSet;
	ArrayList<String> stopWordsList;
	Map<String,Term> termStatsMap;
	int docSetSize;
	
	static String sgml_tag_regex = "[\\<.*?>]";
	static String token_space_regex = "\\s+";
	
	public Tokenizer(){
		wordFrequencyMap =  new HashMap<String,Integer>();
		stopWordsList =  readStopList();
		tokenSet = new HashSet<String>();
		termStatsMap = new HashMap<String,Term>();
		docSetSize = 0;
	}
		
	public void readTweets(String filePath){
		
			try {
				BufferedReader reader = new BufferedReader(new FileReader(filePath));
				String line = null;
				int i=0;
				while((line=reader.readLine())!=null){
					line = trimLine(line);
					String[] tokens = line.split(token_space_regex);
					for(String token:tokens){
						if(stopWordsList.contains(token)||isNumber(token)){
							continue;
						}
						tokenSet.add(token);
						if(!wordFrequencyMap.containsKey(token)){
							wordFrequencyMap.put(token, 1);						
						}else{
							wordFrequencyMap.put(token, wordFrequencyMap.get(token)+1);
						}
						if(!termStatsMap.containsKey(token)){
							DocListObj obj = new DocListObj(String.valueOf(i), 1);
							ArrayList<DocListObj> docList = new ArrayList<DocListObj>();
							docList.add(obj);
							termStatsMap.put(token, new Term(1,docList));						
						}else{
							termStatsMap.get(token).findTermAndUpdateCounts(new DocListObj(String.valueOf(i), 1));
						}						
					}
					i++;
					docSetSize++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			writeToFile(termStatsMap,"termStats.txt");
			writeToFile(wordFrequencyMap,"wordFreq.txt");
	}
	
	
	public Set<String> getTokenSet(){
		return tokenSet;
	}
	
	
	public Map<String,Integer> getTermFrequencyMap(){
		return wordFrequencyMap;				
	}
	
	
	public Map<String,Term> getTermStatsMap(){
		return termStatsMap;
	}
		
	public int getDocSetSize(){
		return docSetSize;
	}
		
	static ArrayList<String> readStopList(){
		ArrayList<String> stopWords =  new ArrayList<String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("stopwords"));
			while(scanner.hasNext()){
				stopWords.add(scanner.next());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		return stopWords;
	}
	
	static String trimLine(String line){
		line = line.toLowerCase();
		line = line.trim();
		line = line.replace(sgml_tag_regex,"");
		line = line.replace("'","");line = line.replace(",","");
		line = line.replace(".","");line = line.replace("  ","");
		line = line.replace("(","");line = line.replace(")","");					
		line =  line.replace("/","");line =  line.replace("+","");
		line = line.replace("-", "");line = line.replace("*","");
		line = line.replace("\"","");line = line.replace("!","");
		line = line.replace("?","");line = line.replace("&","");
		return line;
	}
	

	static void writeToFile(Map<?, ?> tokenMap,String fileName) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(fileName,"UTF-8");
			for (Map.Entry<?,?> token : tokenMap.entrySet()){
//				pw.write(token.getKey() + "  " +((BinaryTerm)token.getValue()).getDocumentFrequency()+ "   " + ((BinaryTerm)token.getValue()).getPostingListBinaryString() + "\n");
				pw.write(token.getKey()+" : "+(token.getValue())+"\n");
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			pw.close();
		}
	}
	
	static void writeSimpleMapToFile(Map<?, ?> tokenMap,String fileName){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(fileName,"UTF-8");
			for (Map.Entry<?,?> token : tokenMap.entrySet()){
//				Document doc =  (Document) token.getValue();
				pw.write(token.getKey()+" : "+token.getValue()+"\n");
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			pw.close();
		}
	}
	
	static boolean isNumber(String a){
		boolean isNumber = false;
		try{
			int num = Integer.valueOf(a);
			isNumber =  true;		
		}catch(Exception e){
			isNumber = false;
		}
		return isNumber;
	}
	
}
