package com.bigdata.textsummary;

import java.util.ArrayList;
import java.util.Collections;

public class Term {
	
	private double termFreq;	
	private double docFrequency;
	private ArrayList<DocListObj> documentList;
		
	
	public Term(int docFreq,ArrayList<DocListObj> postingList){
		this.setDocFrequency(docFreq);
		this.setDocumentList(postingList);
	}
	
	public void setTermFreq(double termFreq) {
		this.termFreq = termFreq;
	}
	
	public double getTermFreq() {
		return termFreq;
	}
	public void setDocFrequency(double docFrequency) {
		this.docFrequency = docFrequency;
	}
	
	public double getDocFrequency() {
		return docFrequency;
	}
	
	public void setDocumentList(ArrayList<DocListObj> documentList) {
		this.documentList = documentList;
	}
	
	public ArrayList<DocListObj> getDocumentList() {
		return documentList;
	}
	
	public void findTermAndUpdateCounts(DocListObj obj){
		boolean contains = false;
		for(DocListObj listItem : documentList){
			if(listItem.getDocId().equalsIgnoreCase(obj.getDocId())){
				contains = true;
				listItem.incrementTermFreq();
				break;
			}
		}
			
		if(!contains){
			documentList.add(obj);
			Collections.sort(documentList);
			this.docFrequency++;
		}
		this.termFreq++;
		
	}
	
	
}
