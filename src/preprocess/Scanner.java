package preprocess;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scanner {
	
	private ArrayList<Article> articles;
	
	public Scanner(String artPath, String sumPath) {
		List<File> art = ListFiles(artPath);
		List<File> sum = ListFiles(sumPath);
		
		articles = new ArrayList<Article>(art.size());
		
		for(int i = 0; i < art.size() && i < sum.size(); i++)
			if(art.get(i).getName().equalsIgnoreCase(sum.get(i).getName())) {
				articles.add(loadArticle(art.get(i), sum.get(i)));
			}
	}
	
	private List<File> ListFiles (String path) {
		List<File> files = null;
		try (Stream<Path> paths = Files.walk(Paths.get(path))) {
			files = paths.filter(Files::isRegularFile)
				.map(Path::toFile)
				.collect(Collectors.toList());
			paths.close();
			
		} catch (IOException e) {
			System.err.println("Invalid path error.");
			System.exit(-1);
		}
		return files;
	}
	
	private Article loadArticle(File art, File sum) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(art));
			BufferedReader s = new BufferedReader(new FileReader(sum));
			
			r.readLine();
			String buffer;
			Article news = new Article(s.readLine());
			s.close();
			
			while((buffer = r.readLine()) != null)
				if(!buffer.equalsIgnoreCase(""))
					news.appendToArticle(buffer);
			
			r.close();
			return news;
		} catch (IOException e) {
			System.err.println("Error while reading the files.");
			return null;
		}
	}
	
	public void print() {
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("bibliography.txt"));
			w.write("" + articles.size());
			w.newLine();
			
			for(Article a: articles) {
				w.write(a.toString());
				w.newLine();
			}
			w.close();
		} catch (IOException e) {
			System.err.println("Could not print articles.");
		}
		
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(args[0], args[1]);
		s.print();
	}
}
