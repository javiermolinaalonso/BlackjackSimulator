package com.blackjack.simulator;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.blackjack.StateSimulator;
import com.blackjack.entities.BlackJackMultiDeck;
import com.blackjack.entities.Deck;
import com.blackjack.entities.PlayerHand;
import com.blackjack.entities.SimulatorResults;

public class BasicSimulator {

	private static final Integer DECKS = 1;
	
	private static final Integer NTHREADS = 4;
	
	private BlackjackAction[] availableActions;
	
	protected HashMap<BlackjackAction, StateSimulator> mapRatio;
	private Integer simulations;
	
	private BasicSimulator(){
		mapRatio = new HashMap<BlackjackAction, StateSimulator>();
	}
	
	public BasicSimulator(BlackjackAction[] actions, Integer simulations){
		this();
		this.availableActions = actions;
		this.simulations = simulations;
		for(BlackjackAction action : actions){
			mapRatio.put(action, new StateSimulator(action));
		}
	}

	public SimulatorResults simulate(String playerHand, String dealerCard){
		BlackJackMultiDeck bjmd = new BlackJackMultiDeck(DECKS);
		
		PlayerHand hand = new PlayerHand();
		
		for(int i = 0; i < playerHand.length(); i+=2){
			hand.addCard(bjmd.pickCard(Deck.getValue(playerHand.substring(i,i+2))));
		}
		byte dc = bjmd.pickCard(Deck.getValue(dealerCard));
		
		
		return simulate(bjmd, hand, dc);
	}
	
	public SimulatorResults simulate(BlackJackMultiDeck bjmd, PlayerHand playerHands, byte dealerCard) {
		Integer simulationsRemaining = new Integer(simulations);
		
		BlockingQueue<Runnable> blockQueue = new LinkedBlockingQueue<Runnable>(simulations);
		BlackjackSimulatorExecutor executor = new BlackjackSimulatorExecutor(NTHREADS, NTHREADS*2, 1l, TimeUnit.HOURS, blockQueue, availableActions);
		
		while(simulationsRemaining > 0){
			RunnableSimulator sim = new RunnableSimulator(bjmd, playerHands, dealerCard, availableActions, simulations);
			executor.execute(sim);
			simulationsRemaining--;
		}
		
		long waitTime = Math.max(1l, Double.valueOf(Math.log(simulations)).longValue());
		while(!simulations.equals(Integer.valueOf((int) executor.getCompletedTaskCount()))){
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return new SimulatorResults(playerHands.toString(), Deck.getValue(dealerCard), executor.getMapRatio());
	}
	

	public HashMap<BlackjackAction, StateSimulator> getMapRatio() {
		return mapRatio;
	}
	
}
