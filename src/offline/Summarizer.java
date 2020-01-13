package offline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Summarizer {
	
	private Report[] article;
	private ArrayList<ArrayList<Integer>> results;
	private double[] score;

	public Summarizer() {
		BufferedReader r = null;
		try {
			r = new BufferedReader(new FileReader("bibliography.txt"));
		} catch (IOException e) {
			System.err.println("Bibliography file not found.");
			System.exit(-1);
		}
		
		try {
			int total = Integer.parseInt(r.readLine());
			
			article = new Report[total];
			String[] buffer;
			int sent;
			String sum;
			String[] text;
			
			for(int i = 0; i < total; i++) {
				buffer = r.readLine().split(" ");
				sent = Integer.parseInt(buffer[2]);
				
				sum = r.readLine();
				text = new String[sent];
				for(int j = 0; j < sent; j++)
					text[j] = r.readLine(); 
				
				article[i] = new Report(sum, text);
				r.readLine();
			}
		} catch (NumberFormatException | IOException e) {
			System.err.println("Error while reading bibliography file.");
			System.exit(-1);
		}
		
		try {
			r.close();
		} catch (IOException e) {
			System.err.println("Error while closing bibliography file.");
			System.exit(-1);
		}
	}
	
	public void summarize(boolean kest) {
		results = new ArrayList<ArrayList<Integer>>(article.length);
		
		for(int i = 0; i < article.length; i++)
			results.add(KMeans.cluster(article[i], kest));
	}
	
	public void printScore(boolean tofile, boolean printsum)
			throws IOException{
		score = new double[results.size()];
		double total = 0; 
		for(int i = 0; i < results.size(); i++) {
			score[i] = Scorer.f1score(article[i].getSummary(), results.get(i));
			total += score[i];
		}
		total = total / score.length;
		
		BufferedWriter w;
		if(tofile)
			w = new BufferedWriter(new FileWriter("scores.txt")); 
		else
			w = new BufferedWriter(new OutputStreamWriter(System.out));
		
		w.write(total + "");
		w.newLine();
		
		for(int i = 0; i < article.length; i++) {
			w.write(score[i] + "");
			w.newLine();
			if(printsum) {
				w.write("Label:\n");
				for(int l: article[i].getSummary()) {
					w.write(article[i].getTextLine(l));
					w.newLine();
				}
				w.write("Prediction:\n");
				for(Integer p: results.get(i)) {
					w.write(article[i].getTextLine(p));
					w.newLine();
				}
			}
			if(printsum)
				w.newLine();
		}		
		
		w.close();
	}
	
	public static void main(String[] args) {
		boolean kest = false, tofile = false,
				printsum = false;
		for(int i =0; i < args.length; i++) {
			switch(args[i]) {
			case "-k":
				kest = true;
				break;
			case "-f":
				tofile = true;
				break;
			case "-s":
				printsum = true;
				break;
			default:
				break;
			}
			
		}
		
		Summarizer s = new Summarizer();
		s.summarize(kest);
		try {
			s.printScore(tofile, printsum);
		} catch (IOException e) {
			System.err.println("Could not write on the output file.");
			System.exit(-1);
		}
	}
}
