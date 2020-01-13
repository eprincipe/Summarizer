package offline;

import java.util.ArrayList;

public class Scorer {
	
	public static double f1score(int[] label, ArrayList<Integer> pred) {
		double tp = 0, fp, fn;
		
		for(Integer l: label)
			for(Integer p: pred)
				if(p == l)
					tp++;
		
		fp = pred.size()-tp;
		fn = label.length-tp;
		
		double precision = tp / (tp+fp);
		double recall = tp / (tp +fn);
		
		if(precision+recall == 0)
			return 0;
		return 2*precision*recall/(precision+recall);
	}

}
