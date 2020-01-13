package offline;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class KMeans {

	public static ArrayList<Integer> cluster(Report a, boolean kest) {
		int k = kest? k(a): a.sumSize();
		Line[] t = a.getText();
		ArrayList<Integer> centroid = new ArrayList<Integer>(k);
		int[] clind = new int[t.length];
		
		//randomly assigning 
		ThreadLocalRandom.current().ints(0, t.length).distinct().limit(k).forEach(centroid::add);
		
		boolean stop;
		double min, dist;
		double[] cd = new double[t.length];
		do {
			//reassign sentences to clusters
			stop = true;
			for(int i = 0; i < clind.length; i++) {
				min = -Double.MAX_VALUE;
				for(Integer c: centroid) {
					dist = a.getSim(c, i);
					if(min < dist) {
						min = dist;
						clind[i] = c;
					}
				}
			}
			//reassign clusters to the one with least
			//cumulative similarity until no central move
			for(int i = 0; i < clind.length; i++)
				cd[i] = 0;
			for(int i = 0; i < clind.length; i++)
				for(int j = 0; j < clind.length; j++)
					cd[i] += clind[i] == clind[j]? a.getSim(i, j): 0; 
			for(int i = 0; i < centroid.size(); i++)
				for(int j = 0; j < clind.length; j++)
					if(clind[centroid.get(i)] == clind[j]
							&& cd[centroid.get(i)] < cd[j]) {
						centroid.set(i, j);
						stop = false;
					}
			
		} while(!stop);
		
		return centroid;
	}
	
	//k topics estimation proposed by Ramirez and Aliguliyev in 2008
	public static int k(Report a) {
		Dictionary d = new Dictionary();
		Line[] t = a.getText();
		double k = 0;
		for(int i = 0; i < t.length; i++) {
			d.putAll(t[i].getDic());
			k += t[i].getDicSize();
		}
		
		k = d.size()*t.length / k;
		return (int)(k + 0.5);
	}
}
