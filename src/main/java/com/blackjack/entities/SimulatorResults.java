package com.blackjack.entities;

import java.util.HashMap;

import com.blackjack.StateSimulator;
import com.blackjack.simulator.BlackjackAction;

public class SimulatorResults {

	private String playerHand;
	private String dealerCard;
	private HashMap<BlackjackAction, StateSimulator> mapRatio;
	
	public SimulatorResults(String playerHand, String dealerCard,
			HashMap<BlackjackAction, StateSimulator> mapRatio) {
		super();
		this.playerHand = playerHand;
		this.dealerCard = dealerCard;
		this.mapRatio = mapRatio;
	}
	public String getPlayerHand() {
		return playerHand;
	}
	public String getDealerCard() {
		return dealerCard;
	}
	public HashMap<BlackjackAction, StateSimulator> getMapRatio() {
		return mapRatio;
	}
	
	public BlackjackAction getBestAction() {
		Double best = Double.NEGATIVE_INFINITY;
		BlackjackAction bestAction = null;
		for(BlackjackAction action : mapRatio.keySet()){
			if(mapRatio.get(action).getMoneyRatio() > best){
				bestAction = action;
				best = mapRatio.get(action).getMoneyRatio();
			}
		}
		return bestAction;
	}
	public Double getBestRatio() {
		return mapRatio.get(getBestAction()).getMoneyRatio();
	}
}
