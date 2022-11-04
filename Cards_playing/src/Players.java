import java.util.ArrayList;
import java.util.Random;

public class Players {
    // the list of cards about each color
    private ArrayList<Cards> card_play;

    // the cards that are exist in hand of each player
    private ArrayList<String[]> person;

    /**
     * constructor for adding colors to the card_play
     * @param red_card
     * @param blue_card
     * @param green_card
     * @param black_card
     */
    public Players(Red red_card , Blue blue_card , Green green_card , Black black_card){
        card_play = new ArrayList<>();
            card_play.add(red_card);
            card_play.add(blue_card);
            card_play.add(green_card);
            card_play.add(black_card);
        person = new ArrayList<>();
    }

    /**
     * @return card_play
     */
    public ArrayList<Cards> getCard_play() {
        return card_play;
    }

    public ArrayList<String[]> getPerson() {
        return person;
    }

    /**
     * giving 7 cards to the player randomly
     * adding to the person field
     * removing from Cards class
     */
    public void add_first() {
       // ArrayList<Cards> card = getCard_play();
        Random generato = new Random();
        int i = 0;
        while (i < 7) {
            Cards color = card_play.get(generato.nextInt(4));
            int num_random = generato.nextInt(13);
            String name_card = color.getCards().get(num_random);
            String[] str = new String[]{name_card,color.toString()};
            if (person.contains(str) || !color.getCards().containsValue(name_card)) {
                //    System.out.println("no" + " " + i);
                continue;
            }
            //System.out.println("ok");
            person.add(new String[]{name_card, color.toString()});
            color.getCards().remove(num_random);
            i++;
        }
    }

    public void print(){
            for(int i = 0 ; i < person.size() ; i++) {
                System.out.println(person.get(i)[0]+ " " +person.get(i)[1]);
            }
    }

    public void print_other(){
        for(int j = 0 ; j < card_play.size() ; j++) {
            for (int i = 0; i < 13 ; i++) {
                System.out.println(card_play.get(j).getCards().get(i));
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
