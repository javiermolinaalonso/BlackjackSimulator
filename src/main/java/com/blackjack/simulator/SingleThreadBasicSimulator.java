package com.blackjack.simulator;

import java.util.HashMap;

import com.blackjack.StateSimulator;
import com.blackjack.entities.BlackJackMultiDeck;
import com.blackjack.entities.DealerHand;
import com.blackjack.entities.Deck;
import com.blackjack.entities.PlayerHand;
import com.blackjack.entities.SimulatorResults;
import com.blackjack.evaluator.HandComparator;

public class SingleThreadBasicSimulator {

	private static final Integer DECKS = 6;
	
	private static final Double DEFAULT_REW	  = 1d;
	private static final Double BLACKJACK_REW = 1.5d;
	private static final Double SURRENDER_REW = 0.5d;
	
	private final HandComparator comparator = new HandComparator();
	
	private HashMap<BlackjackAction, StateSimulator> mapRatio;
	private Integer simulations;
	
	private SingleThreadBasicSimulator(){
		mapRatio = new HashMap<BlackjackAction, StateSimulator>();
	}
	
	public SingleThreadBasicSimulator(BlackjackAction[] actions, Integer simulations){
		this();
		for(BlackjackAction action : actions){
			mapRatio.put(action, new StateSimulator(action));
		}
		this.simulations = simulations;
	}
	
	public SimulatorResults simulate(String playerHand, String dealerCard){
		return simulate(playerHand, dealerCard, null);
	}
	
	public SimulatorResults simulate(String playerHand, String dealerCard, String burnedCards){
		return simulate(playerHand, dealerCard, burnedCards, DECKS);
	}
	
	public SimulatorResults simulate(String playerHand, String dealerCard, String burnedCards, Integer burnAmount){
		BlackJackMultiDeck bjmd = new BlackJackMultiDeck(DECKS);
		
		if(burnedCards != null){
			bjmd.burn(burnedCards, burnAmount);
		}
		PlayerHand hand = new PlayerHand();
		for(int i = 0; i < playerHand.length(); i+=2){
			hand.addCard(bjmd.pickCard(Deck.getValue(playerHand.substring(i,i+2))));
		}
		byte dc = bjmd.pickCard(Deck.getValue(dealerCard));
		
		return simulate(bjmd, hand, dc);
	}
	
	public SimulatorResults simulate(BlackJackMultiDeck bjmd, PlayerHand playerHands, byte dealerCard) {
		Integer simulationsRemaining = new Integer(simulations);
		
		while(simulationsRemaining > 0){
			for(BlackjackAction action : mapRatio.keySet()){
				final BlackJackMultiDeck starterDeck = new BlackJackMultiDeck(bjmd);
				final PlayerHand starterHand = new PlayerHand(playerHands);
				DealerHand dealerHand = new DealerHand(dealerCard);
				dealerHand.addCard(starterDeck.pick());
				simulate(starterDeck, starterHand, dealerHand, action);
			}
			simulationsRemaining--;
		}
		return new SimulatorResults(playerHands.toString(), Deck.getValue(dealerCard), mapRatio);
	}
	
	private void simulate(BlackJackMultiDeck bjmd, PlayerHand playerHands, DealerHand dealerHand, BlackjackAction action) {
		Double amntWin = DEFAULT_REW; //By default the amount is 1, but double is 2 and BJ is 1,5
//		System.out.println("Start [" + action + "]: " + playerHands.toString() + " vs " + dealerHand.getShownCardReadable());
		switch(action){
		case DOUBLE:
			amntWin = 2 * DEFAULT_REW;
			playerHands.addCard(bjmd.pick());
			break;
		case HIT:
			playerHands.addCard(bjmd.pick());
			break;
		case STAND:
			break;
		case SURRENDER:
			mapRatio.get(action).addDVictory(SURRENDER_REW);
			return;
		}
		
		simulate(bjmd, playerHands, dealerHand, action, amntWin);
//		System.out.println("End:   " + playerHands.toString() + " vs " + dealerHand.toString());
//		System.out.println();
	}
	
	private void simulate(BlackJackMultiDeck bjmd, PlayerHand playerHand, DealerHand dealerHand, BlackjackAction action, Double amntWin) {
		if(action.isEnd() || playerHand.isBust() || playerHand.isTwentyOne()){
			//Dealer completes it's hand
			while(!dealerHand.isFinished()){
				dealerHand.addCard(bjmd.pick());
			}
			
			calculateResults(playerHand, dealerHand, amntWin, action);
		}else{
			//Note: This is not correct yet!!! 
			SingleThreadBasicSimulator sm = new SingleThreadBasicSimulator(BlackjackAction.availableActionsNotFirstRound(), 1);
			SimulatorResults r = sm.simulate(bjmd, playerHand, dealerHand.getShownCard());
//			System.out.println("With hand after hit: " + playerHand.toString() +" the best action is: " + sm.getBestAction() + " with a chance of " + sm.mapRatio.get(sm.getBestAction()).getPVictoryRatio());
			StateSimulator state = sm.mapRatio.get(r.getBestAction());
			mapRatio.get(action).addDraw(state.getDrawRatio());
			mapRatio.get(action).addDVictory(state.getDVictoryRatio());
			mapRatio.get(action).addPVictory(state.getPVictoryRatio());
		}
	}
	
	private void calculateResults(PlayerHand playerHand, DealerHand dealerHand, Double amount, BlackjackAction action) {
		int result = comparator.compare(playerHand, dealerHand);
		if(result == HandComparator.PLAYER_LOOSE){
			mapRatio.get(action).addDVictory(amount);
		}else if(result == HandComparator.PLAYER_DRAW){
			mapRatio.get(action).addDraw(1d);
		}else{
			if(playerHand.isBlackJack()){
				mapRatio.get(action).addPVictory(BLACKJACK_REW*amount);
			}else{
				mapRatio.get(action).addPVictory(amount);
			}
		}
	}

}
