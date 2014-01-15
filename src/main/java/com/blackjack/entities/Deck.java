package com.blackjack.entities;


public class Deck {
	private static byte[] deck;
	public static final char[] RANKS = new char[]{'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};
	public static final char[] COLORS = new char[]{'h', 's', 'c', 'd'};

	public Deck() {
		deck = new byte[52];
		for (int i=0; i < 52; i++) {
			deck[i] = (byte) i;
		}
	}
	
	public static byte getValue(String s){
		for (byte i=0; i < 52; i++) {
			if(s.equals(Deck.getValue(i))){
				return i;
			}
		}
		throw new IllegalArgumentException("The card " + s +" is not correct");
	}
	
	public static String getValue(byte b){
//		A = 13;
		StringBuilder sb = new StringBuilder();
		sb.append(RANKS[deck[b%13]]).append(COLORS[deck[b] % 4]);
		return sb.toString();
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		String delim = "";
		for (int i=0; i < 52; i++) {
			sb.append(delim).append(RANKS[deck[i] >> 2]).append(COLORS[deck[i] & 3]);
			delim = " ";
		}
		return sb.append("]").toString();
	}
	
	public byte[] getDeck() {
		return deck;
	}
}
