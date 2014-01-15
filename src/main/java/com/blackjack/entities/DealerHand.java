package com.blackjack.entities;



public class DealerHand extends BJHand {

	public byte shownCard;
	
	public DealerHand(Byte card) {
		super(card);
		shownCard = card;
	}

	public boolean isFinished(){
		for(Integer value : getValue()){
			if(value >= DealerRules.MIN_DEALER_VALUE && value <= DealerRules.MAX_VALUE)
				return true;
		}
		return isBust();
	}

	public byte getShownCard(){
		return shownCard;
	}
	
	public boolean isSecurizable(){
		if(!DealerRules.SECURE_ALLOWED)
			return false;
		return false;
	}
	
	public String getShownCardReadable(){
		return Deck.getValue(shownCard);
	}

	@Override
	public boolean isSplittable() {
		return false;
	}
}
