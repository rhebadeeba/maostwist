import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    
    Mao newGame = new Mao();

    System.out.println("\nThe game has begun \n");

    System.out.println("Dealing cards... \n");
    newGame.shuffleDeck();
    newGame.deal();
    newGame.dealComputer();
    newGame.newMiddleCard();

    boolean firstTurn = true;
    
    while (firstTurn || (!newGame.playerWon() && !newGame.computerWon())){
      if (newGame.skipPlayer){
        System.out.println("\n The player's turn was skipped. \n");
      } else {
        newGame.playerTurn();
      }
      if(newGame.cardValue(newGame.playedCard) != 8) {
        newGame.skipPlayer = false;
      }

      if(newGame.skipComputer){
        System.out.println("\n The computer's turn was skipped. \n");
      } else newGame.computerTurn();
      if(newGame.cardValue(newGame.computerPlayed) != 8) {
        newGame.skipComputer = false;
      }

      if (firstTurn) firstTurn = false;

      if (newGame.cards.size() == 0){
        newGame.cards = newGame.middleCards;
        newGame.shuffleDeck();
      }
    }
    if (newGame.playerWon()){
      System.out.println("\n Congrats, you won! ");
    } else if (newGame.computerWon()) {
      System.out.println("\n You lost, the computer won :(");
    }
    
  }
}