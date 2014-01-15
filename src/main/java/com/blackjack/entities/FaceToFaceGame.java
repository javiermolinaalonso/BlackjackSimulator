package com.blackjack.entities;

public class FaceToFaceGame {

	private BlackJackMultiDeck bjmd;
	private PlayerHand playerHands;
	private byte dealerCard;
	
	
	public FaceToFaceGame(BlackJackMultiDeck bjmd, PlayerHand playerHands, byte dealerCard) {
		super();
		this.bjmd = bjmd;
		this.playerHands = playerHands;
		this.dealerCard = dealerCard;
	}
	public BlackJackMultiDeck getBjmd() {
		return bjmd;
	}
	public PlayerHand getPlayerHands() {
		return playerHands;
	}
	public byte getDealerCard() {
		return dealerCard;
	}
	
	
}
