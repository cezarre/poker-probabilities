package poker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Poker {

	//Generowanie losowe rozdań w pokera i empiryczne wyznaczenie prawdopodobieństwa wszystkich konfiguracji
	
	static int highCard_count = 0;
	static int onePair_count = 0;
	static int twoPairs_count = 0;
	static int threeOfKind_count = 0;
	static int straight_count = 0;
	static int flush_count = 0;
	static int fullHouse_count = 0;
	static int fourOfKind_count = 0;
	static int straightFlush_count = 0;
	static int royalFlush_count = 0;
	
	public static class Card{
		int suit;
		int rank;
		
		public Card(int suit, int rank) {
			this.suit = suit;
			this.rank = rank;
		}

		public Card() {
		}
	}
	static int nrOfHands = 2000000;
	static Card[] cards = new Card[5];
	static ArrayList<Card[]> hands = new ArrayList<Card[]>();
	
	
	static boolean onePair(Card[] h) {
		
		for (int i = 0; i < h.length; i++) { 
			for (int j = i + 1 ; j < h.length; j++) { 
				if (h[i].rank == h[j].rank) {
					return true;
				}
			}
		}			
		
		return false;
	}
	
	static boolean twoPairs(Card[] h) {
		Card[] temp_cards= new Card[3];
		
		for (int i = 0; i < h.length; i++) { 
			for (int j = i + 1 ; j < h.length; j++) { 
				if (h[i].rank == h[j].rank) {
					//create new array without first pair
					int index = 0;
					for(int c = 0 ; c < 5 ; c++) {
						
						if (c != i && c != j) {
							temp_cards[index] = h[c];
							index++;
						}
					}
					//check another pair
					if (onePair(temp_cards)) {
						return true;
					}
					
				}
			}
		}			
		return false;
	}
	
	static boolean threeOfKind(Card[] h) {
		for (int i = 0; i < h.length; i++) { 
			for (int j = i + 1 ; j < h.length; j++) { 
				if (h[i].rank == h[j].rank) {
					for (int k = j + 1; k < h.length; k++) {
						if (h[j].rank == h[k].rank) {
							return true;
						}
					}
				}
			}
		}		
		return false;
	}
	
	static boolean straight_check(int[] ranks) {
		for (int i = 1 ; i < ranks.length ; i++) {
			if (ranks[i] != ranks[i-1] + 1) {
				return false;
			}
		}
		
		return true;
	}
	
	static boolean straight(Card[] h) {

		//create array of ranks
		int[] ranks = new int[h.length];		
		for (int i = 0 ; i < h.length ; i++) {
			ranks[i] = h[i].rank;			
		}
		
		Arrays.sort(ranks);
		
		if (straight_check(ranks)) {
			return true;
		}
		
		if(ranks[ranks.length - 1] == 12) {
			ranks[ranks.length - 1] = -1;
		}
		
		if (straight_check(ranks)) {
			return true;
		}
		
		return false;
	}
	
	static boolean flush(Card[] h) {
		for (int i = 1 ; i < h.length ; i++) {
			if (h[i].suit != h[0].suit) {
				return false;
			}
		}

		return true;
	}
	
	static boolean fullHouse(Card[] h) {
		Card[] temp_cards= new Card[3];
		
		for (int i = 0; i < h.length; i++) { 
			for (int j = i + 1 ; j < h.length; j++) { 
				if (h[i].rank == h[j].rank) {
					//create new array without first pair
					int index = 0;
					for(int c = 0 ; c < 5 ; c++) {
						
						if (c != i && c != j) {
							temp_cards[index] = h[c];
							index++;
						}
					}
					//check another pair
					if (threeOfKind(temp_cards)) {
						return true;
					}
					
				}
			}
		}			
		return false;
	}
	
	static boolean fourOfKind(Card[] h) {
		for (int i = 0; i < h.length; i++) { 
			for (int j = i + 1 ; j < h.length; j++) { 
				if (h[i].rank == h[j].rank) {
					for (int k = j + 1; k < h.length; k++) {
						if (h[j].rank == h[k].rank) {
							for (int m = k + 1; m < h.length; m++) {
								if (h[k].rank == h[m].rank) {
									return true;
								}
							}
						}
					}
				}
			}
		}		
		return false;
	}
	
	static boolean straightFlush(Card[] h) {
		if(straight(h) && flush(h)) {
			return true;
		}
		return false;
	}
	
	static boolean royalFlush(Card[] h) {
		
		int[] ranks = new int[h.length];		
		for (int i = 0 ; i < h.length ; i++) {
			ranks[i] = h[i].rank;			
		}
		
		Arrays.sort(ranks);
		
		
		if(straightFlush(h) && ranks[ranks.length -1] == 12) {
			return true;
		}
		return false;
	}
	
	static void checkHand(Card[] h) {
		if(royalFlush(h)) {
			royalFlush_count++;
			//System.out.println("royalFlush");
			return;
		}
		else if(straightFlush(h)) {
			straightFlush_count++;
			//System.out.println("straightFlush");
			return;
		}
		else if(fourOfKind(h)) {
			fourOfKind_count++;
			//System.out.println("four of kind");
			return;
		}
		else if(fullHouse(h)) {
			fullHouse_count++;
			//System.out.println("full house");
			return;
		}
		else if(flush(h)) {
			flush_count++;
			//System.out.println("flush");
			return;
		}
		else if(straight(h)) {
			straight_count++;
			//System.out.println("straight");
			return;
		}
		else if(threeOfKind(h)) {
			threeOfKind_count++;
			//System.out.println("3 of kind");
			return;
		}
		else if(twoPairs(h)) {
			twoPairs_count++;
			//System.out.println("2 pairs");
			return;
		}
		else if(onePair(h)) {
			onePair_count++;
			//System.out.println("pair");
		}
		else {
			highCard_count++;
			//System.out.println("high card");
		}
		
	}
	
	public static void main(String[] args) {

		// c-clubs, d-diamonds, h-hearts, s-spades, J-jack, Q-queen, K-king, A-ace
		String[] suit = {"c", "d", "h", "s"};
		String[] rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		Random rand = new Random();
		
		
		
		ArrayList<Integer> hand = new ArrayList<Integer>();
        for (int i=0; i<52; i++) {
            hand.add(new Integer(i));
        }
        
        for (int j=0 ; j<nrOfHands ; j++) {
        	cards = new Card[5];
        	Collections.shuffle(hand);
        	
            
            
            for (int i=0 ; i<5 ; i++) {
            	cards[i] = new Card();
    			cards[i].suit = hand.get(i) / 13;
    			cards[i].rank = hand.get(i) % 13;
    			
    		}
            hands.add(cards);
        	
        }
        
        
        for (Card[] h : hands) {       	      	
           	checkHand(h);       	
        }
        
		
        DecimalFormat formatter = new DecimalFormat("#0.00000");
        
        double d = ((double)royalFlush_count/(double)nrOfHands) * 100.0;        
        System.out.println("Royal flush frequency: " + royalFlush_count + " and probability: " + formatter.format(d) + "%");
        
        d = ((double)straightFlush_count/(double)nrOfHands) * 100.0;        
        System.out.println("Straight flush frequency: " + straightFlush_count + " and probability: " +  formatter.format(d) + "%");
        
        d = ((double)fourOfKind_count/(double)nrOfHands) * 100.0;        
        System.out.println("Four of kind frequency: " + fourOfKind_count + " and probability: " +  formatter.format(d) + "%");
        
        d = ((double)fullHouse_count/(double)nrOfHands) * 100.0;        
        System.out.println("Full house frequency: " + fullHouse_count + " and probability: " +  formatter.format(d) + "%");
        
        
        d = ((double)flush_count/(double)nrOfHands) * 100.0;        
        System.out.println("Flush frequency: " + flush_count + " and probability: " +  formatter.format(d) + "%");
        
        d = ((double)straight_count/(double)nrOfHands) * 100.0;        
        System.out.println("Straight frequency: " + straight_count + " and probability: " +  formatter.format(d) + "%");
        
        d = ((double)threeOfKind_count/(double)nrOfHands) * 100.0;        
        System.out.println("Three pairs frequency: " + threeOfKind_count + " and probability: " +  formatter.format(d) + "%");
        
        d = ((double)twoPairs_count/(double)nrOfHands) * 100.0;        
        System.out.println("Two pairs frequency: " + twoPairs_count + " and probability: " +  formatter.format(d) + "%");
		
        d = ((double)onePair_count/(double)nrOfHands) * 100.0;        
        System.out.println("One pair frequency: " + onePair_count + " and probability: " +  formatter.format(d) + "%");
        
        d = ((double)highCard_count/(double)nrOfHands) * 100.0;        
        System.out.println("High card frequency: " + highCard_count + " and probability: " +  formatter.format(d) + "%");
		
		
		

	}

}
