package com.blackjack.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.blackjack.SimulatorStatistics;

public class BlackJackMultiDeck {

	private LinkedList<Byte> availableCards;
	private LinkedList<Byte> usedCards;
	private int currentCount = 0;
	private int decks;
	
	public static HashMap<Byte, List<Integer>> values;
	
	public BlackJackMultiDeck(int decks){
		this.decks = decks;
		availableCards = new LinkedList<Byte>();
		usedCards = new LinkedList<Byte>();
		for(int i = 0;i<decks;i++){
			Deck d = new Deck();
			for (int j = 0; j < d.getDeck().length; j++)
				availableCards.add(d.getDeck()[j]);
		}
		shuffleAll();
		values = new HashMap<Byte, List<Integer>>();
		for(byte i = 0; i<52; i++){
			List<Integer> value = new ArrayList<Integer>();
			if(i%13 == 0){
				value.add(1);
				value.add(11);
			}else if(i%13 > 8){
				value.add(10);
			}else{
				value.add(i%13 + 1);
			}
			values.put(i, value);
		}
		
	}

	@SuppressWarnings("unchecked")
	public BlackJackMultiDeck(BlackJackMultiDeck bjmd) {
		this.decks = bjmd.decks;
		this.usedCards = (LinkedList<Byte>) bjmd.usedCards.clone();
		this.availableCards = (LinkedList<Byte>) bjmd.availableCards.clone();
		shuffle();
	}

	public static List<Integer> getValue(List<Byte> cards){
		List<Integer> possibleValues = new ArrayList<Integer>();
		possibleValues.add(0);
		
		for(Byte b : cards){
			for(Integer value : values.get(b)){
				for(int i = 0; i < possibleValues.size(); i++){
					possibleValues.set(i, possibleValues.get(i)+value);
				}
				if(values.get(b).size() > possibleValues.size())
					possibleValues.add(value);
			}
		}
		return possibleValues;
	}
	
	public byte pick(){
		byte card = availableCards.poll();
		usedCards.push(card);
		currentCount++;
		return card;
	}
	
	public byte pickCard(byte cardValue){
		int i = 0;
		while(i < availableCards.size()){
			if(availableCards.get(i).compareTo(cardValue) == 0){
				byte card = availableCards.get(i);
				availableCards.remove(i);
				usedCards.push(card);
				currentCount++;
				return card;
			}
			i++;
		}
		throw new IllegalArgumentException("The card is not in the deck");
	}
	
	public void shuffleAll(){
		availableCards.addAll(usedCards);
		usedCards.clear();
		shuffle();
	}
	
	/*
	 * Shuffle the available cards
	 */
	public void shuffle() {
		Long t = System.currentTimeMillis();
//		Random rgen = new Random();
//		int max = availableCards.size()-1;
//		Long seed = Math.abs(Long.valueOf(Math.abs(System.nanoTime()) % 203894));
//		for(int i = 0; i < 10000; i++){
//			int origin = rgen.nextInt(seed.intValue()+1) % max;
//			int end = rgen.nextInt(seed.intValue()+1) % max;
//			byte card = availableCards.remove(origin);
//			byte card2 = availableCards.remove(end);
//			availableCards.add(end, card);
//			availableCards.add(origin, card2);
//		}
		Collections.shuffle(availableCards);
		t = System.currentTimeMillis() - t;
		SimulatorStatistics.shuffleTime+=t;
	}
	
	/**
	 * Burn the first "amount" cards
	 * @param amount
	 */
	public void burn(Integer amount){
		try{
			for(int i = 0; i < amount; i++){
				pick();
			}
		}catch(Exception e){
			System.out.println("Invalid amount");
		}
	}
	
	public void burn(String cards, Integer amount){
		try{
			for(int i = 0; i < cards.length(); i+=2){
				for(int j = 0; j < amount; j++){
					pickCard(Deck.getValue(cards.substring(i,i+2)));
				}
			}
		}catch(Exception e){
			System.out.println("Invalid amount");
		}
	}
	
	public void print(){
		for(int i = 0; i < availableCards.size(); i++)
			System.out.print(Deck.getValue(availableCards.get(i))+", ");
		System.out.println();
	}
	
	public int getRemainingCards(){
		return availableCards.size();
	}
	
	public int getUsedCards(){
		return currentCount;
	}
	
}
