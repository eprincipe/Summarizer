package offline;


public class Report {

	private int[] summary;
	private Line[] text;
	private double[][] simMatrix;

	public Report(String sum, String[] tex) {
		String[] s = sum.split(" ");
		summary = new int[s.length];
		for(int i = 0; i < s.length; i++)
			summary[i] = Integer.parseInt(s[i]);
		
		this.text = new Line[tex.length];
		
		for(int i = 0; i < tex.length; i++)
			this.text[i] = new Line(tex[i].split(" "));
		
		simMatrix = new double[tex.length][tex.length];
		for(int i = 0; i < simMatrix.length; i++)
			for(int j = 0; j < simMatrix[i].length; j++)
				if(j < i)
					simMatrix[i][j] = simMatrix[j][i];
				else
					simMatrix[i][j] = text[i].sim(text[j]);	
	}
	
	public Line[] getText() {
		return text;
	}
	
	public double getSim(int i, int j) {
		return simMatrix[i][j];
	}
	
	public int sumSize() {
		return summary.length;
	}
	
	public int[] getSummary() {
		return summary;
	}
	
	public String getTextLine(int i) {
		return text[i].toString();
	}
}