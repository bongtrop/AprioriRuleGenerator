import java.util.ArrayList;
import java.util.HashSet;


public class Apriori {
	private int[][] mat;
	private int row;
	private int col;
	private double min_support;
	private double min_confident;
	
	public Apriori(int tran, int max_buy, int item, double minsup, double mincon) {
		this.mat = new int[tran][item];
		for (int i=0;i<tran;i++) {
			int buy = 0;
			for (int j=0;j<item;j++) {
				mat[i][j] = (int) Math.round(Math.random());
				buy += mat[i][j];
				if (buy==max_buy) break;
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
	
	public int findFrequency(Frequency f) {
		int c = 0;
		for (int i=0;i<row;i++) {
			boolean is = true;
			for (int j=0;j<f.item.size();j++) {
				if (mat[i][f.item.get(j)]==0) {
					is = false;
					break;
				}
			}
			if (is) c++;
		}
		return c;
	}
	
	private double findSupport(Rule rule) {
		double c = 0;
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
	
	private double findConfident(Rule rule) {
		double c_all = 0;
		double c_given = 0;
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
		if (c_given==0) c_given=1;
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
	
	private ArrayList<Frequency> initFrequency() {
		ArrayList<Frequency> fs = new ArrayList<Frequency>();
		for (int i=0;i<col;i++) {
			Frequency f = new Frequency();
			f.item.add(i);
			f.f = findFrequency(f);
			fs.add(f);
		}
		return fs;
	}
	
	private ArrayList<Rule> initRule() {
		ArrayList<Rule> rules = new ArrayList<Rule>();
		for (int i=0;i<col;i++) {
			for (int j=i+1;j<col;j++) {
				ArrayList<Integer> itemAll = new ArrayList<Integer>();
				itemAll.add(i);
				itemAll.add(j);
				ArrayList<ArrayList<Integer>> subset = getSubsets(itemAll);
				for (int k=1;k<subset.size()-1;k++) {
					Rule rule = new Rule();
					rule.itemAll.addAll(itemAll);
					rule.itemGiven.addAll(subset.get(k));
					rule.support = findSupport(rule);
					rule.confident = findConfident(rule);
					rules.add(rule);
				}
			}
		}
		return rules;
	}
	
	private ArrayList<Rule> findStrongRule(ArrayList<Rule> rules) {
		ArrayList<Rule> nrules = new ArrayList<Rule>();
		for (int i=0;i<rules.size();i++) {
			if (rules.get(i).confident>=this.min_confident && rules.get(i).support>=this.min_support) {
				nrules.add(rules.get(i));
			}
		}
		
		return nrules;
	}
	
	private void removeDuplicate(ArrayList<Integer> items) {
		HashSet<Integer> hs = new HashSet<Integer>();
		hs.addAll(items);
		items.clear();
		items.addAll(hs);
	}
	
	private boolean forsome(ArrayList<Integer> a, ArrayList<Integer> b) {
		for (int i=0;i<b.size();i++) {
			if (a.contains(b.get(i))) return true;
		}
		return false;
	}
	
	private ArrayList<Frequency> joinFrequency(ArrayList<Frequency> fs, int len) {
		ArrayList<Frequency> result = new ArrayList<Frequency>();
		for (int i=0;i<fs.size();i++) {
			for (int j=i+1;j<fs.size();j++) {
				if (forsome(fs.get(i).item,fs.get(j).item) || len==2) {
					ArrayList<Integer> items = new ArrayList<Integer>();
					items.addAll(fs.get(i).item);
					items.addAll(fs.get(j).item);
					removeDuplicate(items);
					if (items.size()==len) {
						Frequency f = new Frequency();
						f.item.addAll(items);
						f.f = findFrequency(f);
						result.add(f);
					}
				}
			}
		}
		return result;
	}
	
	private ArrayList<Rule> joinRules(ArrayList<Rule> rules, int len) {
		ArrayList<Rule> nrules = new ArrayList<Rule>();
		for (int i=0;i<rules.size();i++) {
			for (int j=i+1;j<rules.size();j++) {
				if (forsome(rules.get(i).itemAll,rules.get(j).itemAll)) {
					ArrayList<Integer> itemAll = new ArrayList<Integer>();
					itemAll.addAll(rules.get(i).itemAll);
					itemAll.addAll(rules.get(j).itemAll);
					removeDuplicate(itemAll);
					if (itemAll.size()==len) {
						ArrayList<ArrayList<Integer>> subset = getSubsets(itemAll);
						for (int k=1;k<subset.size()-1;k++) {
							Rule rule = new Rule();
							rule.itemAll.addAll(itemAll);
							rule.itemGiven.addAll(subset.get(k));
							rule.support = findSupport(rule);
							rule.confident = findConfident(rule);
							nrules.add(rule);
						}
					}
				}
			}
		}
		return nrules;
	}
	
	public ArrayList<Rule> getAllStrongRules(int len) {
		ArrayList<Rule> rules = initRule();
		ArrayList<Rule> strong_rules = new ArrayList<Rule>();
		rules = findStrongRule(rules);
		strong_rules.addAll(rules);
		for (int i=3;i<=len;i++) {
			rules = joinRules(rules,i);
			rules = findStrongRule(rules);
			strong_rules.addAll(rules);
		}
		
		return strong_rules;
	}
	
	public ArrayList<Rule> getAllRules(int len) {
		ArrayList<Rule> rules = initRule();
		ArrayList<Rule> result = new ArrayList<Rule>();
		result.addAll(rules);
		for (int i=3;i<=len;i++) {
			rules = joinRules(rules, i);
			result.addAll(rules);
		}
		return rules;
	}
	
	public ArrayList<Rule> getStrongRulesAtLength(int len) {
		ArrayList<Rule> rules = initRule();
		rules = findStrongRule(rules);
		for (int i=3;i<=len;i++) {
			rules = joinRules(rules,i);
			rules = findStrongRule(rules);
		}
		
		return rules;
	}
	
	public ArrayList<Rule> getRulesAtLength(int len) {
		ArrayList<Rule> rules = initRule();
		for (int i=3;i<=len;i++) {
			rules = joinRules(rules, i);
		}
		return rules;
	}
	
	public ArrayList<Frequency> getAllFrequency(int len) {
		ArrayList<Frequency> fs = initFrequency();
		ArrayList<Frequency> result = new ArrayList<Frequency>();
		result.addAll(fs);
		for (int i=2;i<=len;i++) {
			fs = joinFrequency(fs,i);
			result.addAll(fs);
		}
		return result;
	}
	
	public ArrayList<Frequency> getFrequencyAtLength(int len) {
		ArrayList<Frequency> fs = initFrequency();
		for (int i=2;i<=len;i++) {
			fs = joinFrequency(fs,i);
		}
		return fs;
	}
}
