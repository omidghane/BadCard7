import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game_playing {
    // players that play
    private ArrayList<Players> players;

    // turn of players
    private int turn;

    // helping thing about changing color when playing card_B
    private int help;

    // the information of frontcard
    private String[] front_card;

    // the cards that are storaged
    private ArrayList<Cards> storage_cards;

    // determining the direction of play
    private String direction;

    /**
     * constructor for determining players and cards
     * @param red_card
     * @param blue_card
     * @param green_card
     * @param black_card
     * @param players
     */
    public Game_playing(int turn ,Red red_card , Blue blue_card , Green green_card , Black black_card ,Players ...players){
        this.players = new ArrayList<>();
        for(Players player : players) {
            this.players.add(player);
        }
        storage_cards = new ArrayList<>();
        storage_cards.add(red_card);
        storage_cards.add(blue_card);
        storage_cards.add(green_card);
        storage_cards.add(black_card);

        front_card = new String[2];
        direction = "clockwise";
        this.turn = turn-1;
        help = -1;
    }

    /**
     * @return help
     */
    public int getHelp() {
        return help;
    }

    /**
     * @return players
     */
    public ArrayList<Players> getPlayers() {
        return players;
    }

    /**
     * @return turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * @param limit
     * @return a random number
     */
    public int random_num(int limit){
        Random generator = new Random();
        return generator.nextInt(limit);
    }

    /**
     * @return a random cards information
     * removing card from storage
     */
    public String[] get_card_storage(){
        if(!storage_cards.isEmpty()) {
            while (true) {
                int rand_color = random_num(4);
                int rand_card = random_num(13);
                Cards color = storage_cards.get(rand_color);
                String name_card = color.cards.get(rand_card);
                if (color.getCards().containsValue(name_card)) {
                    String[] get_card = new String[]{name_card,color.toString()};
                    color.getCards().remove(rand_card);
                    return get_card;
                }
            }
        }
        return null;
    }


    /**
     * assign the frontcard
     */
    public void assign_frontcard(){
        while (true) {
            String[] card = get_card_storage();
            String name_card = card[0];
            if (name_card.equals("2") || name_card.equals("7") || name_card.equals("8") || name_card.equals("10") || name_card.equals("A") || name_card.equals("B")) {
                continue;
            }
            front_card = card;
            return;
        }
    }

    /**
     * change the turn to the next player
     * @return index player
     */
    public void next_turn(){
        int index;
        if(direction.equals("clockwise")) {
            if(turn + 1 > players.size()-1){
                turn = 0;
            }
            else {
               // System.out.println("ok");
                turn++;
            }
        }
        else if(direction.equals("anticlockwise")){
            if(turn - 1 < 0){
                turn = players.size()-1;
            }
            else {
                turn--;
            }
        }
    }

    /**
     * change the turn to the previous player
     * @return index player
     */
    public void perv_turn(){
        int index;
        if(direction.equals("clockwise")) {
            if(turn - 1 < 0){
                turn = players.size()-1;
            }
            else {
                turn--;
            }
        }
        else if(direction.equals("anticlockwise")){
            if(turn + 1 > players.size()-1){
                turn = 0;
            }
            else {
                // System.out.println("ok");
                turn++;
            }
        }
    }

    /**
     * check if the player is able to play or must pick a card from storage cards
     * @return true,false
     */
    public boolean enable_play(){

        if(help == -1) {
            for (String[] card : players.get(turn).getPerson()) {
                if (card[0].equals(front_card[0]) || card[1].equals(front_card[1])) {
                    return true;
                }
            }
        }
        else if(help != -1){
            for (String[] card : players.get(turn).getPerson()) {
                if (card[0].equals(front_card[0]) || card[1].equals(search_color())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * before this we should check that i can play card or no!!
     * choosing one card to play
     * if card is not suitable according to the frontcard must choose another
     * check doing cards
     * and go to the next player
     * @param index
     */
    public int play_Me(int index){
        if(index > players.get(0).getPerson().size() || index < 0){
            System.out.println("choose another card");
            return -1;
        }
            String[] card = players.get(0).getPerson().get(index - 1);
            if(help == -1) {
                if (card[0].equals(front_card[0]) || card[1].equals(front_card[1])) {

                    storage_cards.get(index_color(front_card[1])).getCards().put(index_name(front_card[0]), front_card[0]);
                    front_card = card;
                    players.get(0).getPerson().remove(index - 1);
                    doing_cards(card);
                    next_turn();
                    return 0;
                } else {
                    System.out.println("choose another card");
                    return -1;
                }
            }
            else if(help != -1){
                if (card[0].equals(front_card[0]) || card[1].equals(search_color())) {
                    if(card[0].equals(front_card[0])){
                        help = -1;
                    }

                    storage_cards.get(index_color(front_card[1])).getCards().put(index_name(front_card[0]), front_card[0]);
                    front_card = card;
                    players.get(0).getPerson().remove(index - 1);
                    doing_cards(card);
                    next_turn();
                //    help = -1;
                    return 0;
                } else {
                    System.out.println("choose another card");
                    return -1;
                }
            }
            return -1;
    }

    /**
     * before this we should check that it can play card or no!!
     * playing bot
     * @param
     */
    public void play_bot(){
        if(help == -1) {
            while (true) {
                Players player = players.get(turn);
                int rand_index = random_num(player.getPerson().size());
                String[] card = player.getPerson().get(rand_index);
                if (card[0].equals(front_card[0]) || card[1].equals(front_card[1])) {

                    storage_cards.get(index_color(front_card[1])).getCards().put(index_name(front_card[0]), front_card[0]);
                    front_card = card;
                    player.getPerson().remove(rand_index);
                    doing_cards(card);
                    next_turn();
                  //  help = -1;
                    return;
                }
            }
        }else if(help != -1){
            while (true) {
                Players player = players.get(turn);
                int rand_index = random_num(player.getPerson().size());
                String[] card = player.getPerson().get(rand_index);
                if (card[0].equals(front_card[0]) || card[1].equals(search_color())) {
                    if(card[0].equals(front_card[0])){
                        help = -1;
                    }

                    storage_cards.get(index_color(front_card[1])).getCards().put(index_name(front_card[0]), front_card[0]);
                    front_card = card;
                    player.getPerson().remove(rand_index);
                    doing_cards(card);
                    next_turn();
                    return;
                }
            }
        }
    }

    /**
     * determine which index is related to the color of card
     * @param card
     * @return index of color
     */
    public int index_color(String card){
        if(card.equals("red")){
            return 0;
        }
        else if(card.equals("blue")){
            return 1;
        }
        else if(card.equals("green")){
            return 2;
        }
        else if(card.equals("black")){
            return 3;
        }
        return -2;
    }


    /**
     * determine which index is related to the name of card
     * @param card
     * @return
     */
    public int index_name(String card){
        if(card.equals("2")){
            return 0;
        }
        else if(card.equals("3")){
            return 1;
        }
        else if(card.equals("4")){
            return 2;
        }
        else if(card.equals("5")){
            return 3;
        }
        else if(card.equals("6")){
            return 4;
        }
        else if(card.equals("7")){
            return 5;
        }
        else if(card.equals("8")){
            return 6;
        }
        else if(card.equals("9")){
            return 7;
        }
        else if(card.equals("10")){
            return 8;
        }
        else if(card.equals("A")){
            return 9;
        }
        else if(card.equals("B")){
            return 10;
        }
        else if(card.equals("C")){
            return 11;
        }
        else if(card.equals("D")){
            return 12;
        }
        return -2;
    }

    /**
     * method for printing my cards
     */
    public void printMyCards(){
        ArrayList<String[]> myCardsArrayList = players.get(0).getPerson();
        String cardView = "";
        int end_card = myCardsArrayList.size()-1;
        for (int i = 0; i < myCardsArrayList.size(); i++) {
            cardView +=  color_print("┍━━━",i);
        }
        cardView += color_print("━━┑",end_card);
        cardView += "\n";
        for (int i = 0; i < myCardsArrayList.size(); i++) {
            String str = myCardsArrayList.get(i)[0];
            cardView += color_print("│ ",i) + str + (str.length() == 2 ? "" : " ");
        }
        cardView += color_print("  │",end_card);
        cardView += "\n";
        for (int i = 0; i < myCardsArrayList.size(); i++) {
            cardView +=  color_print("│   ",i);

        }
        cardView += color_print("  │",end_card);
        cardView += "\n";
        for (int i = 0; i < myCardsArrayList.size(); i++) {
            cardView += color_print("│   ",i);

        }
        cardView += color_print("  │",end_card);
        cardView += "\n";
        for (int i = 0; i < myCardsArrayList.size(); i++) {
            cardView += color_print("┕━━━",i);

        }
        cardView += color_print("━━┙",end_card);
        cardView += "\n";
        for (int i = 0; i < myCardsArrayList.size(); i++) {
            cardView += (i + 1) + "   ";

        }
        System.out.println(cardView);
    }

    /**
     * method for printing frontcard
     */
    public void print_frontcard(){
        System.out.println( "direction:" +direction + (direction.length() == 13 ? "" : "") + color_print(" ┍━━━━━┑   ",-1));
        System.out.println( "player1:"+ players.get(0).getPerson().size()+ (players.get(0).getPerson().size() > 9 ? "" : " " ) +(direction.length() == 13 ? "    " : "") + color_print("          │  ",-1) + front_card[0] + (front_card[0].length() == 2 ? "" : " ") +color_print(" │  ",-1));
        System.out.println( "player2:"+ players.get(1).getPerson().size()+ (players.get(1).getPerson().size() > 9 ? "" : " " ) +(direction.length() == 13 ? "    " : "") + color_print("          │     │ ",-1));
        System.out.println( "player3:"+ players.get(2).getPerson().size()+ (players.get(2).getPerson().size() > 9 ? "" : " " ) +(direction.length() == 13 ? "    " : "") + color_print("          ┕━━━━━┙",-1));
        if(players.size() == 4) {
            System.out.println("player4:"+ players.get(3).getPerson().size());
        }
        else if(players.size() == 5) {
            System.out.println("player4:"+ players.get(3).getPerson().size());
            System.out.println("player5:"+ players.get(4).getPerson().size());
        }
        else{
            System.out.println();
        }
    }

    /**
     * changing the color of desired text
     * @param name
     * @param index
     * @return colored text
     */
    public String color_print(String name,int index){
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";

        String color;
        if(index != -1) {
             color = players.get(0).getPerson().get(index)[1];
        }
        else{
            color = front_card[1];
        }

        if(color.equals("red")) {
            return ANSI_RED + name + ANSI_RESET;
        }
        else if(color.equals("blue")){
            return ANSI_BLUE + name + ANSI_RESET;
        }
        else if(color.equals("green")){
            return ANSI_GREEN + name + ANSI_RESET;
        }
        else if(color.equals("black")){
            return ANSI_BLACK + name + ANSI_RESET;
        }
        return null;
    }


    /**
     * command for card 10
     * change the direction of playing turn
     */
    public void card_10(){
        if(direction.equals("clockwise")){
            direction = "anticlockwise";
        }
        else if(direction.equals("anticlockwise")){
            direction = "clockwise";
        }
    }

    /**
     * command for card 2
     * giving one of the players card to another player
     */
    public void card_2(){
        Scanner input = new Scanner(System.in);
        if(turn == 0) {
            while(true) {
                System.out.println("enter a player index you want to give card");
                int index = input.nextInt();
                if (index - 1 == 0) {
                    System.out.println("choose someone unless yourself");
                    continue;
                }
                Players me = players.get(0);
                int rand = random_num(me.getPerson().size());
                players.get(index-1).getPerson().add(me.getPerson().get(rand));
                me.getPerson().remove(rand);
                break;
            }
        }
        else{
            while (true){
                int index = random_num(players.size());
                if(index == turn){
                    continue;
                }
                Players bot = players.get(turn);
                int rand = random_num(bot.getPerson().size());
                players.get(index).getPerson().add(bot.getPerson().get(rand));
                bot.getPerson().remove(rand);
                break;
            }
        }
    }

    /**
     * cousing punishment for the next player
     * if next player again does card 7 couse more punishment for the next player
     */
    public void card_7(){
        Scanner input = new Scanner(System.in);
        int num_card = 0;
        //front_card = in
        while (true){
            int help = 0;
            if(front_card[1].equals("black")){
                num_card += 4;
            }
            else {
                num_card += 2;
            }
            next_turn();

            for(String[] card_next : players.get(turn).getPerson()){
                if(card_next[0].equals("7")){
                    System.out.println("next player has card7, continue 0");
                    int con = input.nextInt();

                 //   card = card_next;
                    front_card = card_next;
                    players.get(turn).getPerson().remove(card_next);
                    help = 1;
                    break;
                }
            }
            if (help == 1){
                continue;
            }

            for(int i = 0 ; i < num_card ; i++) {
                players.get(turn).getPerson().add(get_card_storage());
            }
            break;
        }
    }

    /**
     * command for card 8
     * player must play again
     * unless must get another card from storages card and if possible play
     */
    public void card_8(){
        Scanner input = new Scanner(System.in);
        if(enable_play()) {
            System.out.println("turn : " + (getTurn()+1));
            print_frontcard();
            printMyCards();
            System.out.println("continue 0");
            int a = input.nextInt();

            if (turn == 0) {
                play_Me(input.nextInt());
                perv_turn();
            } else {
                play_bot();
                perv_turn();
            }
        }
        else {
            players.get(turn).getPerson().add(get_card_storage());
            if(enable_play()) {
                System.out.println("turn : " + (getTurn()+1));
                print_frontcard();
                printMyCards();
                System.out.println("continue 0");
                int a = input.nextInt();

                if (turn == 0) {
                    play_Me(input.nextInt());
                    perv_turn();
                } else {
                    play_bot();
                    perv_turn();
                }
            }
        }
    }

    /**
     * ignoring the next player
     */
    public void card_A(){
        next_turn();
    }

    /**
     * determining the next card color
     */
    public void card_B(){
        int color;
        String str;
        Scanner input = new Scanner(System.in);
        System.out.println("red : 1 , blue : 2 , green : 3 , black : 4");
        while (true) {
            if (turn == 0) {
                color = input.nextInt() - 1;
            } else {
                color = random_num(4);
            }

            if (color == 0) {
                str = "red";
            } else if (color == 1) {
                str = "blue";
            } else if (color == 2) {
                str = "green";
            } else if (color == 3) {
                str = "black";
            } else {
                System.out.println("enter again");
                continue;
            }
            break;
        }
        System.out.println(str);
        help = color;
        }

        public String search_color(){
        if(help == 0){
            return "red";
        }
        else if(help == 1){
            return "blue";
        }
        else if(help == 2){
            return "green";
        }
        else if(help == 3){
            return "black";
        }
        return null;
        }

    /**
     * check if it is contains the doing cards
     * @param card
     */
    public void doing_cards(String[] card){
        Scanner input = new Scanner(System.in);
        if(card[0].equals("2")){
            card_2();
        }
        else if(card[0].equals("7")){
            System.out.println();
            next_turn();
            System.out.println("turn : " + (getTurn()+1));
            perv_turn();
            print_frontcard();
            printMyCards();
            card_7();
        }
        else if(card[0].equals("8")){
            card_8();
        }
        else if(card[0].equals("10")){
            card_10();
        }
        else if(card[0].equals("A")){
            card_A();
        }
        else if(card[0].equals("B")){
            card_B();
        }
    }

    /**
     * @param index
     * @return calculated total score of each player
     */
    public int score(int index){
        int score = 0;
        ArrayList<String[]> cards = players.get(index).getPerson();
               for(String[] card : cards){
                   if(card[0].equals("3")){
                       score += 3;
                   }
                   else if(card[0].equals("4")){
                       score += 4;
                   }
                   else if(card[0].equals("5")){
                       score += 5;
                   }
                   else if(card[0].equals("9")){
                       score += 9;
                   }
                   else if(card[0].equals("C")){
                       score += 12;
                   }
                   else if(card[0].equals("D")){
                       score += 13;
                   }
                   else if(card[0].equals("2")){
                       score += 2;
                   }
                   else if(card[0].equals("7") && card[1].equals("black")){
                       score += 15;
                   }
                   else if(card[0].equals("7")){
                       score += 10;
                   }
                   else if(card[0].equals("8")){
                       score += 8;
                   }
                   else if(card[0].equals("10")){
                       score += 10;
                   }
                   else if(card[0].equals("A")){
                       score += 11;
                   }
                   else if(card[0].equals("B")){
                       score += 12;
                   }
               }
               return score;
    }

}
