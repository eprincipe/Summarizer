package offline;

import java.util.Map.Entry;

public class Line {

	private Dictionary dic;
	private String[] words;
	
	public Line(String[] split) {
		this.words = split;
		
		//fill the dictionary
		this.dic = new Dictionary();
		for(int i = 0; i < words.length; i++)
			this.dic.put(words[i]);
	}

	public double sim(Line other) {
		double sim = 0;
		
		for(Entry<String, Integer> w1: this.dic.entrySet())
			for(Entry<String, Integer> w2: other.dic.entrySet())
				if(w1.getKey().equalsIgnoreCase(w2.getKey()))
					sim++;
		
		sim = 2*sim/(this.dic.size() + other.dic.size());
		
		return sim;
	}
	
	public int getDicSize() {
		return dic.size();
	}
	
	public Dictionary getDic() {
		return dic;
	}
	
	public String toString() {
		String buffer = "";
		for(String str: words)
			buffer += str + " ";
		return buffer;
	}
}
