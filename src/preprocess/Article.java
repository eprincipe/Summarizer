package preprocess;
import java.util.ArrayList;

public class Article {

	private ArrayList<Sentence> sentences;
	private ArrayList<Sentence> summary;
	
	public Article(String sum) {
		summary = new ArrayList<Sentence> ();
		sentences = new ArrayList<Sentence> ();
		ArrayList<String> s = splitSentences(sum);
		for(String str: s)
			summary.add(new Sentence(str));
	}
	
	private ArrayList<String> splitSentences(String line) {
		String[] s = line.split("[\\.\\?\\!]");
		ArrayList<String> ret = new ArrayList<String>();
		String part = s[0];
		int i = 1;
		while(i < s.length) {
			if(part.length() > 0) {
				if(Character.isDigit(part.charAt(part.length()-1)) &&
						Character.isDigit(s[i].charAt(0))) {
					part = part + "." + s[i];
				} else {
					ret.add(part);
					part = s[i];
				}
			} else 
				part = s[i];
			i++;
		}
		ret.add(part);
		return ret;
	}
	
	public void appendToArticle(String line) {
		ArrayList<String> s = splitSentences(line);
		for(String str: s)
			if(!str.equalsIgnoreCase(""))
				sentences.add(new Sentence(str));
	}
	
	private int getIndex(Sentence sum) {
		for(int i = 0; i < sentences.size(); i++)
			if(sentences.get(i).equals(sum))
				return i;
		return -1;
	}
	
	public String toString() {
		String buffer = "";
		int i = -1;
		int error = 0;
		for(Sentence s: summary) {
			i = getIndex(s);
			if(i != -1)
				buffer += i + " ";
			else 
				error++;
		}
		
		buffer = "$ARTICLE " + (summary.size()-error) + " " + sentences.size() + "\n" + buffer + "\n";
		for(Sentence s: sentences)
			buffer += s.toString() + "\n";
		
		return buffer;
	}
}
