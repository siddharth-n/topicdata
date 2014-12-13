package com.bigdata.textsummary;

public class DocListObj implements Comparable<DocListObj>{

	private String docId;
	private int termFreq;

	public DocListObj(String docId, int termFreq) {
		this.docId = docId;
		this.termFreq = termFreq;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocId() {
		return docId;
	}

	public void setTermFreq(int termFreq) {
		this.termFreq = termFreq;
	}

	public int getTermFreq() {
		return termFreq;
	}

	public void incrementTermFreq() {
		this.termFreq++;
	}

	@Override
	public int compareTo(DocListObj obj) {
		if(Integer.valueOf(this.docId)<Integer.valueOf(obj.getDocId())){
			return -1;
		}else{
			return 1;
		}
	}
	
	public String toString() {
		return "[docID = " + this.docId + ", termFreq = " + this.termFreq + "]";
	}

}
