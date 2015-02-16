import java.util.ArrayList;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Apriori apriori = new Apriori(50,20,5.0,5.0);
		ArrayList<Rule> rules = apriori.getStrongRulesAtLength(4);
		for (int i=0;i<rules.size();i++) {
			System.out.println(rules.get(i));
		}
	}

}
