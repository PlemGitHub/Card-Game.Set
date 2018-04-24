package entities;

public class Deck {

	private int deckSize = 27;
	private Card[] deck = new Card[deckSize];	// 81 is the deck size. 3*3*3*3 combinations = 81.
	private int currentCard;
	
	public Deck() {
		generateNewDeck();
		currentCard = 0;
	}

	private void generateNewDeck() {
		for (int i = 0; i < deckSize; i++) {
			deck[i] = new Card();
			int currentPos = i;
			if (i > 0 && checkEqualCards(currentPos))
				i--;
		}
	}

	private boolean checkEqualCards(int currentPos) {
		for (int i = 0; i < currentPos; i++) {
			if (deck[currentPos].checkEqualCard(deck[i]))
				return true;
		}
		return false;
	}
	
	public Card drawNextCard(){
		if (currentCard < deckSize)
			return deck[currentCard];
		else
			return null;
	}
	
	public void increaseCurrentPos(){
		currentCard++;
	}
}
