package com.blackjack.simulator;

import java.util.HashMap;

import com.blackjack.StateSimulator;
import com.blackjack.entities.BlackJackMultiDeck;
import com.blackjack.entities.DealerHand;
import com.blackjack.entities.PlayerHand;

public class RunnableSimulator implements Runnable {

	private BlackJackMultiDeck bjmd;
	private PlayerHand playerHands; 
	private byte dealerCard;
	private BlackjackAction[] availableActions;
	private HashMap<BlackjackAction, StateSimulator> mapRatio;
	private Integer simulations;
	
	public RunnableSimulator(BlackJackMultiDeck bjmd, PlayerHand playerHands,
			byte dealerCard, BlackjackAction[] availableActions, Integer simulations) {
		super();
		this.bjmd = bjmd;
		this.playerHands = playerHands;
		this.dealerCard = dealerCard;
		this.availableActions = availableActions;
		this.simulations = simulations;
		mapRatio = new HashMap<BlackjackAction, StateSimulator>();
		for(BlackjackAction action : availableActions){
			mapRatio.put(action, new StateSimulator(action));
		}
	}


	public void run() {
		for(BlackjackAction action : availableActions){
			final BlackJackMultiDeck starterDeck = new BlackJackMultiDeck(bjmd);
			final PlayerHand starterHand = new PlayerHand(playerHands);
			DealerHand dealerHand = new DealerHand(dealerCard);
			dealerHand.addCard(starterDeck.pick());
			
			RunnableActionSimulator sim = new RunnableActionSimulator(starterDeck, starterHand, dealerHand, action, simulations);
			StateSimulator simResult;
			try {
				simResult = sim.call();
				mapRatio.get(action).addAll(simResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		
//		return mapRatio;
	}


	public HashMap<BlackjackAction, StateSimulator> getMapRatio() {
		return mapRatio;
	}

}
