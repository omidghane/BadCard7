import java.util.Scanner;

public class Main {
    public static void main( String[] args){
        Scanner input = new Scanner(System.in);

        Green green = new Green();
        Blue blue = new Blue();
        Red red = new Red();
        Black black = new Black();

        Players omid = new Me(red, blue, green, black);
        Players bot_1 = null;
        Players bot_2 = null;
        Players bot_3 = null;
        Players bot_4 = null;
        Game_playing game = null;
        System.out.println("enter the number of players (3 to 5)");
        int index_number = input.nextInt();
        System.out.println();
        System.out.println("enter the index of player which start playing");
        int index_start = input.nextInt();
           if(index_number == 3){
               bot_1 = new Bot(red,blue,green,black);
               bot_2 = new Bot(red,blue,green,black);
               omid.add_first();
               bot_1.add_first();
               bot_2.add_first();
               game = new Game_playing(index_start,red,blue,green,black , omid,bot_1,bot_2);
           }
           else if(index_number == 4){
               bot_3 = new Bot(red,blue,green,black);
               bot_1 = new Bot(red,blue,green,black);
               bot_2 = new Bot(red,blue,green,black);
               omid.add_first();
               bot_1.add_first();
               bot_2.add_first();
               bot_3.add_first();
               game = new Game_playing(index_start,red,blue,green,black , omid,bot_1,bot_2,bot_3);
            }
           else if(index_number == 5){
               bot_4 = new Bot(red,blue,green,black);
               bot_3 = new Bot(red,blue,green,black);
               bot_1 = new Bot(red,blue,green,black);
               bot_2 = new Bot(red,blue,green,black);
               omid.add_first();
               bot_1.add_first();
               bot_2.add_first();
               bot_3.add_first();
               bot_4.add_first();
               game = new Game_playing(index_start,red,blue,green,black , omid,bot_1,bot_2,bot_3,bot_4);
           }
       // assert game != null;
        game.assign_frontcard();
          // int turn = game.getTurn();
        System.out.println("turn : " + (game.getTurn()+1));
           game.print_frontcard();
           game.printMyCards();
       //    int help = game.getHelp();
           while(true){
               if(game.getTurn() == 0){
                   System.out.println("1- play a card  2- getting a card");
                   int index = input.nextInt();
                   if(index == 1) {
                       if(game.enable_play()) {
                           while(true) {
                               if (game.play_Me(input.nextInt()) != -1){
                                   break;
                               }
                           }
                       }else{
                           System.out.println("your are not able to play, you must get a card");
                           game.getPlayers().get(0).getPerson().add(game.get_card_storage());

                           System.out.println();
                           System.out.println("turn : " + (game.getTurn()+1));
                           game.print_frontcard();
                           game.printMyCards();

                           System.out.println("if want to play insert 1 unless insert 0");
                           if(input.nextInt() == 1){
                               if(game.enable_play()) {
                                   while (true) {

                                       if (game.play_Me(input.nextInt()) != -1) {
                                           break;
                                       }
                                   }
                               }else {
                                   System.out.println("you are not yet able to play");
                                   game.next_turn();
                               }
                           }
                           else {
                               game.next_turn();
                           }
                       }
                   }else if(index == 2){
                       game.getPlayers().get(0).getPerson().add(game.get_card_storage());

                       System.out.println();
                       System.out.println("turn : " + (game.getTurn()+1));
                       game.print_frontcard();
                       game.printMyCards();

                       System.out.println("if want to play insert 1 unless insert 0");
                       if(input.nextInt() == 1){
                           if(game.enable_play()) {
                               while (true) {
                                   if (game.play_Me(input.nextInt()) != -1) {
                                       break;
                                   }
                               }
                           }
                           else {
                               System.out.println("you are not yet able to play");
                               game.next_turn();
                           }
                       }
                       else {
                           game.next_turn();
                       }
                   }
                   System.out.println();
                   System.out.println("turn : " + (game.getTurn()+1));
                   game.print_frontcard();
                   game.printMyCards();
               }
               else{
                   System.out.println("if continue insert 0");
                   int a = input.nextInt();
                   if(game.enable_play()){
                       game.play_bot();
                   }
                   else {
                       game.getPlayers().get(game.getTurn()).getPerson().add(game.get_card_storage());
                       if(game.enable_play()){
                           game.play_bot();
                       }
                       else {
                           game.next_turn();
                       }
                   }
                   System.out.println();
                   System.out.println("turn : " + (game.getTurn()+1));
                   game.print_frontcard();
                   game.printMyCards();
               }
             //  int who = 0;
               for(int i = 0 ; i < game.getPlayers().size() ; i++){
                   if(game.getPlayers().get(i).getPerson().isEmpty()) {
                       if(index_number == 3) {
                           System.out.println("omid : " + game.score(0));
                           System.out.println("bot_1 : " + game.score(1));
                           System.out.println("bot_2 : " + game.score(2));
                       }
                       else if(index_number == 4){
                           System.out.println("omid : " + game.score(0));
                           System.out.println("bot_1 : " + game.score(1));
                           System.out.println("bot_2 : " + game.score(2));
                           System.out.println("bot_3 : " + game.score(3));
                       }
                       else if(index_number == 5){
                           System.out.println("omid : " + game.score(0));
                           System.out.println("bot_1 : " + game.score(1));
                           System.out.println("bot_2 : " + game.score(2));
                           System.out.println("bot_3 : " + game.score(3));
                           System.out.println("bot_4 : " + game.score(4));
                       }
                   }
                   }

              for(int i = 0 ; i < game.getPlayers().size() ; i++){
                   if(game.getPlayers().get(i).getPerson().isEmpty()){
                       System.out.print("winner : ");
                       if(i == 0){
                           System.out.println("omid");
                           return;
                       }
                       else if(i == 1){
                           System.out.println("bot_1");
                           return;
                       }
                       else if(i == 2){
                           System.out.println("bot_2");
                           return;
                       }
                       else if(i == 3){
                           System.out.println("bot_3");
                           return;
                       }
                       else if(i == 4){
                           System.out.println("bot_4");
                           return;
                       }
                   }
               }
           }



    }
}
