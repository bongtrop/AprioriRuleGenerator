import java.util.ArrayList;


public class Rule {
	public ArrayList<Integer> itemAll;
	public ArrayList<Integer> itemGiven;
	public double support;
	public double confident;
	
	public Rule() {
		itemAll = new ArrayList<Integer>();
		itemGiven = new ArrayList<Integer>();
		support = 0;
		confident = 0;
	}
	
	public String toString() {
		boolean start = true;
		String result = "(";
		for (int i=0;i<itemGiven.size();i++) {
			result+=itemGiven.get(i);
			if (i!=itemGiven.size()-1) result+=", ";
		}
		result+=" => ";
		
		for (int i=0;i<itemAll.size();i++) {
			if (!itemGiven.contains(itemAll.get(i))) {
				if (!start) result+=", ";
				result+=itemAll.get(i);
				start = false;
			}
		}
		result+=") "+support+" "+confident;
		return result;
	}
}
