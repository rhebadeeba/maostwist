import java.util.ArrayList;
import java.util.Random;
import java.util.*;
import java.util.Scanner;

public class Mao {
  Random rand = new Random();
  public ArrayList<String> cards;
  public ArrayList<String> playerCards;
  public ArrayList<String> computerCards;
  public String middleCard;
  public String playedCard;
  public boolean skipPlayer;
  public boolean skipComputer;
  public boolean turnOver = true;
  public boolean ohNoSeven = false;
  int sevenCount = 0;
  Scanner scanner = new Scanner(System.in);
  public boolean firstTurn;
  public String computerPlayed;
  public String[] suits = { "Hearts", "Diamonds", "Spades", "Clubs" };
  public String[] values = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King" };
  public ArrayList<String> middleCards;
  
  public Mao() {
    cards = new ArrayList<String>();
    playerCards = new ArrayList<String>();
    computerCards = new ArrayList<String>();
    middleCard = "";
    playedCard = "";
    skipPlayer = false;
    skipComputer = false;
    firstTurn = true;
    computerPlayed = "";
    middleCards = new ArrayList<String>();

    for (String s : suits) {
      for (String v : values) {
        cards.add(v + " of " + s);
      }
    }
  }

  public void deal() {
    for (int i = 0; i < 7; i++) {
      playerCards.add(cards.remove(i));
    }
  }

  public void dealComputer() {
    for (int i = 0; i < 7; i++) {
      computerCards.add(cards.remove(i));
    }
  }

  public void shuffleDeck() {
    Collections.shuffle(cards);
  }

  public int cardValue(String card) {
    if ((card.contains("Ace"))) {
      return 1;
    } else if ((card.contains("Two"))) {
      return 2;
    } else if ((card.contains("Three"))) {
      return 3;
    } else if ((card.contains("Four"))) {
      return 4;
    } else if ((card.contains("Five"))) {
      return 5;
    } else if ((card.contains("Six"))) {
      return 6;
    } else if ((card.contains("Seven"))) {
      return 7;
    } else if ((card.contains("Eight"))) {
      return 8;
    } else if ((card.contains("Nine"))) {
      return 9;
    } else if ((card.contains("Ten"))) {
      return 10;
    } else if ((card.contains("Jack"))) {
      return 11;
    } else if ((card.contains("Queen"))) {
      return 12;
    } else if ((card.contains("King"))) {
      return 13;
    } else
      return -1;
  }

  public String playerString() {
    String printPlayerCards = "";
    for (String s : playerCards) {
      printPlayerCards += (s + "  ");
    }
    return printPlayerCards;
  }

  public void playerTurn()
  {
    System.out.println("\n Your cards are: " + playerString());
    System.out.println("\n The middle card is: " + middleCard);

    if (canPlay(playerCards)){
      System.out.println("What card would you like to play? (0 - " + (playerCards.size()-1) + ")");
      Scanner scanner = new Scanner(System.in);
      int index = scanner.nextInt();
      if(!canPlayCard(playerCards.get(index))){
        System.out.println("This card cannot be played. Please choose another card.");
        index = scanner.nextInt();
      }
      playedCard = playerCards.remove(index);
      
      System.out.println("You played: " + playedCard);
      checkCard();
      middleCard = playedCard;
      middleCards.add(middleCard);
    }
    else{
      System.out.println("\n You must draw a card. ");
      String newCard = cards.remove(0);
      playerCards.add(newCard);
      System.out.println("\n You drew a " + newCard);
    }
  }

  public void computerTurn(){
    System.out.println("\n The computer is taking its turn... ");

    if (!canPlay(computerCards)){
      System.out.println("\n The computer must draw a card.");
      computerCards.add(cards.remove(0));
    }
    if(canPlay(computerCards)){
      if(ohNoSeven)
      {
        ohNoSeven = false;
        for(int i = 0; i < computerCards.size();i++)
          {
            if(computerCards.get(i).contains("Seven"))
            {
              computerPlayed = computerCards.remove(i);
              break;
            }
          }
        for(int i = 0; i < sevenCount;i++)
          {
            computerCards.add(cards.remove(i));
          }
        turnOver = false; 
        middleCard = computerPlayed;
        middleCards.add(middleCard);
      }
      if((turnOver) && (canPlay(computerCards)))
      {
        for (int i = 0; i < computerCards.size(); i++){
          if(canPlayCard(computerCards.get(i))){
            computerPlayed = computerCards.remove(i);
            break;
        }
    }
        
      System.out.println("\n The computer played: " + computerPlayed);
  
      if(cardValue(computerPlayed) == 8){
        skipComputer = true;
      }
      if (cardValue(computerPlayed) == 1){
        skipPlayer = true;
      }
      if (cardValue(computerPlayed) == 11) {
        String randomSuit = suits[rand.nextInt(3)];
        System.out.println("\n The computer changed the suit to " + randomSuit);
        computerPlayed = "Jack of " + randomSuit;
      }
      middleCard = computerPlayed;
      middleCards.add(middleCard);
    } 
  }
  }
  
  public void checkCard()
  {
    if(cardValue(playedCard) == 8)
    {
      skipPlayer = true;
    }
    if(cardValue(playedCard) == 1)
    {
      skipComputer = true;
    }
    if(cardValue(playedCard) == 11)
    {
      changeSuit();
    }
    Scanner scanner = new Scanner(System.in);
    if(playedCard.contains("Spade"))
    {
      System.out.println("What card did you just play? ");
      String guess = scanner.nextLine();
      guess.toUpperCase();
      if(guess.equals(playedCard))
      {
        System.out.println("\n You got it right!");
      }
      else
      {
        System.out.println("\n That is not the card you played :(");
        playerCards.add(cards.get(0));
        cards.remove(0);
        
      }
    }
    
    if(cardValue(playedCard) == 7)
    {
      sevenCount++;
      System.out.println("You Played a 7! ");
      String response = scanner.nextLine();
      response.toUpperCase();
      String exp = "Have a";
      for(int i = 1; i < sevenCount;i++)
          {
            exp += " very";
          }
      exp += " nice day";
      if(!response.equals(exp)){
        System.out.println("Failure to include the expected amount of \"very\"'s in the phrase \"Have a nice day\"");
        playerCards.add(cards.get(0));
        cards.remove(0);
        }
      }
    }

  public void newMiddleCard(){
    middleCard = cards.remove(0);
    middleCards.add(middleCard);
  }

  public boolean playerWon(){
    return(playerCards.size() == 0);
  }

  public boolean computerWon(){
    return(computerCards.size() == 0);
  }

  public void changeSuit(){
    System.out.println("You chose a Jack. What suit would you like to change your card to? ");
    Scanner scanner = new Scanner(System.in);
    String newSuit = scanner.nextLine();
    playedCard = "Jack of " + newSuit;
    middleCard = playedCard;
  }

  public boolean canPlay(ArrayList<String> hand){
    for (String s: hand){
      String middleSuit = middleCard.substring(middleCard.indexOf(" ") + 1);
      if (s.contains(middleSuit) || cardValue(middleCard) == cardValue(s)) {
        return true;
      }
    }
    return false;
  }

  public boolean canPlayCard(String card){
     String middleSuit = middleCard.substring(middleCard.indexOf(" ") + 1);
      if (card.contains(middleSuit) || cardValue(middleCard) == cardValue(card)) {
        return true;
      } else return false;
  }
  
}