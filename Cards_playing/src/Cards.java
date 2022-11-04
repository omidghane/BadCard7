import java.util.ArrayList;
import java.util.HashMap;

public class Cards {
    // cards that are contained in each color class
    protected HashMap<Integer,String> cards;

  //  private ArrayList<HashMap<String, Integer>> type_cards;

    // key observe the value of cards related to order
    private int[] key = {0,1,2,3,4,5,6,7,8,9,10,11,12};

    // the names that each cards may have
    private String[] value = {"2","3","4","5","6","7","8","9","10","A","B","C","D"};

    /**
     * constructor for getting the identity to the cards
     */
    public Cards() {
        cards = new HashMap<>();
        for(int i = 0 ; i < key.length ; i++) {
            cards.put(key[i] , value[i]);
        }
    }

    /**
     * @return cards
     */
    public HashMap<Integer, String> getCards() {
        return cards;
    }

    /**
     * @return String descibes class
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
