package com.blackjack;

import com.blackjack.entities.SimulatorResults;
import com.blackjack.simulator.BasicSimulator;
import com.blackjack.simulator.BlackjackAction;
import com.blackjack.simulator.SingleThreadBasicSimulator;


public class Simulator {
	
	private static Integer SIMULATIONS = 1000;
	private static final String PLAYER_HAND = "9d9c";
	private static final String DEALER_HAND = "7s";
	public static void main(String[] args) throws Exception {
		Long timeSingle = System.currentTimeMillis();
//		doSingleThread();
		timeSingle = System.currentTimeMillis() - timeSingle;
		
		
		Long timeMultithread = System.currentTimeMillis();
		doMultiThread();
		timeMultithread = System.currentTimeMillis() - timeMultithread;
		
		System.out.println("Time Single Thread: " + timeSingle);
		System.out.println("Time Multithreading: " + timeMultithread);
		
		SimulatorStatistics.print();
		System.exit(0);
	}
	
	private static void doSingleThread(){
		SingleThreadBasicSimulator sm = new SingleThreadBasicSimulator(BlackjackAction.availableActionsFirstRound(), SIMULATIONS);
		SimulatorResults results = sm.simulate(PLAYER_HAND, DEALER_HAND);
		print(results);
	}
	
	private static void doMultiThread(){
		BasicSimulator sm = new BasicSimulator(BlackjackAction.availableActionsFirstRound(), SIMULATIONS);
		SimulatorResults results = sm.simulate(PLAYER_HAND, DEALER_HAND);
		print(results);
	}
	
	private static void print(SimulatorResults results){
		System.out.println("The best action for " + results.getPlayerHand() +" against " + 
				results.getDealerCard() + " is " + results.getBestAction().toString() + " with ratio " + results.getBestRatio());
		
		for(BlackjackAction action : results.getMapRatio().keySet()){
			System.out.println(results.getMapRatio().get(action).toString());
		}
	}
}
