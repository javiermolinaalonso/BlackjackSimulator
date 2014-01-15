package com.blackjack;

import java.text.NumberFormat;

import com.blackjack.entities.SimulatorResults;
import com.blackjack.simulator.BasicSimulator;
import com.blackjack.simulator.BlackjackAction;
import com.blackjack.simulator.SingleThreadBasicSimulator;


public class Simulator {
	
	private static Integer SIMULATIONS = 10000;
	private static final String PLAYER_HAND = "9d9c";
	private static final String DEALER_HAND = "7s";
	private static final String BURNED_CARDS = "2s2d2h2c3s3d3h3c4s4d4h4c5s5d5h5c6s6d6h6c7d7h7c8s8d8h8c9s9hAsAdAhAc";
	
	public static void main(String[] args) throws Exception {
		Long timeSingle = System.currentTimeMillis();
//		doSingleThread();
		timeSingle = System.currentTimeMillis() - timeSingle;
//		System.out.println("Time Single Thread: " + timeSingle);
		
		
		Long timeMultithread = System.currentTimeMillis();
		doMultiThread();
		timeMultithread = System.currentTimeMillis() - timeMultithread;
		
//		SimulatorStatistics.print();
		System.exit(0);
	}
	
	private static void doSingleThread(){
		SingleThreadBasicSimulator sm = new SingleThreadBasicSimulator(BlackjackAction.availableActionsFirstRound(), SIMULATIONS);
		SimulatorResults results = sm.simulate(PLAYER_HAND, DEALER_HAND, BURNED_CARDS);
		print(results);
	}
	
	private static void doMultiThread(){
		BasicSimulator sm = new BasicSimulator(BlackjackAction.availableActionsFirstRound(), SIMULATIONS);
		SimulatorResults results = sm.simulate(PLAYER_HAND, DEALER_HAND, BURNED_CARDS);
		print(results);
	}
	
	private static void print(SimulatorResults results){
		NumberFormat percFormat = NumberFormat.getPercentInstance();
		percFormat.setMaximumFractionDigits(3);
		percFormat.setMinimumFractionDigits(3);
		
		System.out.println("The best action for " + results.getPlayerHand() +" against " + 
				results.getDealerCard() + " is " + results.getBestAction().toString() + " with ratio " + percFormat.format(results.getBestRatio() / SIMULATIONS));
		
		for(BlackjackAction action : results.getMapRatio().keySet()){
			System.out.println(results.getMapRatio().get(action).toString());
		}
	}
}
