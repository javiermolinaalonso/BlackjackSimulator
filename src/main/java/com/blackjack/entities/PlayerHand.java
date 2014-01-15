package com.blackjack.entities;

import java.util.List;

import com.blackjack.exceptions.IllegalSplitException;

public class PlayerHand extends BJHand {

	private boolean splitted = false;
	private Integer finalValue;
	
	public PlayerHand(){
		super();
	}
	public PlayerHand(Byte card){
		super(card);
	}
	
	public PlayerHand(Byte card, Byte card2){
		super(card);
		this.cards.add(card2);
	}
	
	public PlayerHand(PlayerHand playerHands) {
		super(playerHands);
	}

	public void stand(){
		List<Integer> values = getValue();
		if(values.size() == 1)
			finalValue = values.get(0);
		else{
			finalValue = 0;
			for(Integer value : values){
				finalValue = Math.max(value, finalValue);
			}
		}
	}
	
	public PlayerHand split() throws IllegalStateException, NullPointerException, IllegalSplitException {
		if(cards == null)
			throw new NullPointerException("Cards can't be null");
		if(!isSplittable())
			throw new IllegalStateException("This hand isn't splittable");
		Byte removedCard = this.cards.remove(1);
		PlayerHand addHand = new PlayerHand(removedCard);
		this.splitted = true;
		return addHand;
	}
	
	/**
	 * Returns true if the rules allows split
	 * @return
	 */
	public boolean isSplittable() throws IllegalSplitException {
		if(!DealerRules.SPLIT_ALLOWED)
			return false;
		
		if(cards.size() != 2)
			return false;
		
		if(splitted && !DealerRules.MULTIPLE_SPLIT_ALLOWED)
			throw new IllegalSplitException("Multiple split is not allowed");
		
		return BlackJackMultiDeck.values.get(cards.get(0)).equals(BlackJackMultiDeck.values.get(cards.get(1)));
	}
}
