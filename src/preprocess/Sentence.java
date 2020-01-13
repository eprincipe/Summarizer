package preprocess;
import java.util.ArrayList;

public class Sentence {
	
	private ArrayList<String> words;

	public Sentence(String sentence) {
		words = new ArrayList<String>();
		String[] split = sentence.split("[^a-zA-Z'-]");
		for(int i = 0; i < split.length; i++)
			if(split[i].length() > 1)
				words.add(split[i]);
	}

	@Override
	public String toString() {
		String str = "";
		for(String s: words)
			str += s + " ";
		return str;
	}
	
	public boolean equals(Sentence other) {
		if(this.words.size() != other.words.size())
			return false;
		for(int i = 0; i < words.size(); i++)
			if(!this.words.get(i).equalsIgnoreCase(other.words.get(i)))
				return false;
		return true;
	}
}
