package com.blackjack.simulator;

import java.util.concurrent.Callable;

import com.blackjack.StateSimulator;
import com.blackjack.entities.BlackJackMultiDeck;
import com.blackjack.entities.DealerHand;
import com.blackjack.entities.PlayerHand;
import com.blackjack.entities.SimulatorResults;
import com.blackjack.evaluator.HandComparator;

public class RunnableActionSimulator implements Callable<StateSimulator> {

	private static final Double DEFAULT_REW	  = 1d;
	private static final Double BLACKJACK_REW = 1.5d;
	private static final Double SURRENDER_REW = 0.5d;
	
	private BlackJackMultiDeck bjmd;
	private PlayerHand playerHands; 
	private DealerHand dealerHand;
	private BlackjackAction action;
	private Integer simulations;
	
	public RunnableActionSimulator(BlackJackMultiDeck bjmd, PlayerHand playerHands,
			DealerHand dealerHand, BlackjackAction action, Integer simulations) {
		super();
		this.bjmd = bjmd;
		this.playerHands = playerHands;
		this.dealerHand = dealerHand;
		this.action = action;
		this.simulations = simulations;
	}

	public StateSimulator call() throws Exception {
		
		StateSimulator sm = new StateSimulator(action);
		
		Double amntWin = DEFAULT_REW; //By default the amount is 1, but double is 2 and BJ is 1,5
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
			sm.addDVictory(SURRENDER_REW);
			return sm;
		}
		
		simulate(bjmd, playerHands, dealerHand, action, amntWin, sm);
		
		return sm;
	}
	
	private void simulate(BlackJackMultiDeck bjmd, PlayerHand playerHand, DealerHand dealerHand, BlackjackAction action, Double amntWin, StateSimulator ss) {
		if(action.isEnd() || playerHand.isBust() || playerHand.isTwentyOne()){
			//Dealer completes it's hand
			while(!dealerHand.isFinished()){
				dealerHand.addCard(bjmd.pick());
			}
			
			calculateResults(playerHand, dealerHand, amntWin, ss);
		}else{
			BasicSimulator simulator = new BasicSimulator(BlackjackAction.availableActionsNotFirstRound(), 5);
			SimulatorResults r = simulator.simulate(bjmd, playerHand, dealerHand.getShownCard());
			StateSimulator state = r.getMapRatio().get(r.getBestAction());
			ss.addDraw(state.getDrawRatio());
			ss.addDVictory(state.getDVictoryRatio());
			ss.addPVictory(state.getPVictoryRatio());
		}
	}
	
	private void calculateResults(PlayerHand playerHand, DealerHand dealerHand, Double amount, StateSimulator ss) {
		int result = new HandComparator().compare(playerHand, dealerHand);
		if(result == HandComparator.PLAYER_LOOSE){
			ss.addDVictory(amount);
		}else if(result == HandComparator.PLAYER_DRAW){
			ss.addDraw(1d);
		}else{
			if(playerHand.isBlackJack()){
				ss.addPVictory(BLACKJACK_REW*amount);
			}else{
				ss.addPVictory(amount);
			}
		}
	}

}
