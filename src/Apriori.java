import java.util.ArrayList;


public class Apriori {
	private int[][] mat;
	private int row;
	private int col;
	private double min_support;
	private double min_confident;
	
	public Apriori(int tran, int item, double minsup, double mincon) {
		this.mat = new int[tran][item];
		for (int i=0;i<tran;i++) {
			for (int j=0;j<item;j++) {
				mat[i][j] = (int) Math.round(Math.random());
			}
		}
		row = tran;
		col = item;
		min_support = minsup;
		min_confident = mincon;
	}
	
	public Apriori(int[][] mat, int tran, int item, double minsup, double mincon) {
		this.mat = mat;
		row = tran;
		col = item;
		min_support = minsup;
		min_support = mincon;
	}
	
	private double findSupport(Rule rule) {
		int c = 0;
		for (int i=0;i<row;i++) {
			boolean is = true;
			for (int j=0;j<rule.itemAll.size();j++) {
				if (mat[i][rule.itemAll.get(j)]==0) {
					is = false;
					break;
				}
			}
			if (is) c++;
		}
		return (c/row)*100;
	}
	
	private double fineConfident(Rule rule) {
		int c_all = 0;
		int c_given = 0;
		for (int i=0;i<row;i++) {
			boolean is = true;
			for (int j=0;j<rule.itemAll.size();j++) {
				if (mat[i][rule.itemAll.get(j)]==0) {
					is = false;
					break;
				}
			}
			if (is) c_all++;
			
			is = true;
			for (int j=0;j<rule.itemGiven.size();j++) {
				if (mat[i][rule.itemGiven.get(j)]==0) {
					is = false;
					break;
				}
			}
			if (is) c_given++;
		}
		
		if (c_given==0) c_given=c_all;
		
		return (c_all/c_given)*100;
	}
	
	private ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set) {

		ArrayList<ArrayList<Integer>> subsetCollection = new ArrayList<ArrayList<Integer>>();

		if (set.size() == 0) {
			subsetCollection.add(new ArrayList<Integer>());
		} else {
			ArrayList<Integer> reducedSet = new ArrayList<Integer>();

			reducedSet.addAll(set);

			int first = reducedSet.remove(0);
			ArrayList<ArrayList<Integer>> subsets = getSubsets(reducedSet);
			subsetCollection.addAll(subsets);

			subsets = getSubsets(reducedSet);

			for (ArrayList<Integer> subset : subsets) {
				subset.add(0, first);
			}

			subsetCollection.addAll(subsets);
		}

		return subsetCollection;
	}
	
	
	
	public ArrayList<Rule> createRule(int len) {
		ArrayList<Rule> rules = new ArrayList<Rule>();
		
		
		for (int i=2;i<=len;i++) {
			
		}
		
		return rules;
	}
	
	
	
	
}
