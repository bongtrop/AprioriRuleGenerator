import java.util.ArrayList;


public class Frequency {
	public int f;
	public ArrayList<Integer> item;
	
	public Frequency() {
		item = new ArrayList<Integer>();
		f = 0;
	}
	
	public String toString() {
		String result = "(";
		for (int i=0;i<item.size();i++) {
			result+=item.get(i);
			if (i!=item.size()-1) result+=", ";
		}
		result+=") ";

		result+=f;
		return result;
	}
}
