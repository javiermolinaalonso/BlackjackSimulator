package com.blackjack.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.blackjack.exceptions.IllegalSplitException;

public abstract class BJHand {

	public static final byte BUST = -1;
	public static final byte TWE_ONE = 1;
	public static final byte IN_GAME = 0;

	protected List<Byte> cards;
	
	public BJHand(){
		this.cards = new ArrayList<Byte>();
	}
	
	public BJHand(Byte card){
		this();
		cards.add(card);
	}
	
	public BJHand(PlayerHand playerHands) {
		this();
		cards.addAll(playerHands.getCards());
	}

	public abstract boolean isSplittable() throws IllegalSplitException;
	
	public void addCard(Byte card){
		//TODO Throw exceptions
		this.cards.add(card);
	}
	
	public Integer getHigherValue(){
		int max = 0;
		for(Integer value : getValue()){
			if(value <= DealerRules.MAX_VALUE)
				max = Math.max(max, value);
		}
		return max;
	}
	public List<Integer> getValue() throws NullPointerException {
		if (cards == null)
			throw new NullPointerException("Cards can't be null");
		
		List<Integer> possibleValues = new ArrayList<Integer>();
		possibleValues.add(0);
		
		for(Byte b : cards){
			Integer value = BlackJackMultiDeck.values.get(b).get(0);
			Integer otherValue = null;
			if(BlackJackMultiDeck.values.get(b).size() > 1){
				otherValue = BlackJackMultiDeck.values.get(b).get(1);
			}
			int size = possibleValues.size();
			for(int counter = 0; counter < size; counter++){
				if(otherValue != null)
					possibleValues.add(possibleValues.get(counter)+otherValue);
				possibleValues.set(counter, possibleValues.get(counter)+value);
			}
		}
		if(possibleValues.size() >= 2){
			List<Integer> values = new ArrayList<Integer>();
			Iterator<Integer> it = possibleValues.iterator();
			while(it.hasNext()){
				Integer value = it.next();
				if(value > DealerRules.MAX_VALUE && possibleValues.size() > 1)
					it.remove();
				else{
					if(values.contains(value))
						it.remove();
					else
						values.add(value);
				}
				
			}
		}
		return possibleValues;
	}
	
	public boolean isBlackJack(){
		return isTwentyOne() && cards.size() == 2;
	}
	
	public boolean isBust(){
		boolean exceeded = true;
		for(Integer i : getValue()){
			if(i <= DealerRules.MAX_VALUE){
				exceeded = false;
			}
		}
		return exceeded;
	}
	
	public boolean isTwentyOne(){
		for(Integer i : getValue()){
			if(DealerRules.MAX_VALUE.equals(i))
				return true;
		}
		return false;
	}
	
	public boolean isSoft(){
		return getValue().size() > 1;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(byte b : cards)
			sb.append(Deck.getValue(b)).append(" ");
		sb.append(getValue().toString());
		return sb.toString();
	}
	
	public List<Byte> getCards(){
		return cards;
	}
}
