import java.util.ArrayList;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Apriori apriori = new Apriori([TRANSACTION NUMBER], [MAXIMUM BUY], [ITEM NUMBER], [MINIMUM SUPPORT], [MINIMUM CONFIDENT]);
		Apriori apriori = new Apriori(100, 20, 50,25.0,25.0);
		ArrayList<Rule> rules = apriori.getAllStrongRules(4);
		for (int i=0;i<rules.size();i++) {
			System.out.println(rules.get(i));
		}
	}

}
