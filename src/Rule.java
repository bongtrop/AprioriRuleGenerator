import java.util.ArrayList;


public class Rule {
	public ArrayList<Integer> itemAll;
	public ArrayList<Integer> itemGiven;
	public double support;
	public double confident;
	
	public Rule(int max) {
		itemAll = new ArrayList<Integer>();
		itemGiven = new ArrayList<Integer>();
		support = 0;
		confident = 0;
	}
}
