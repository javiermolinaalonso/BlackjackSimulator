package com.blackjack.simulator;

public enum BlackjackAction {
	HIT,
	STAND,
//	SPLIT,
	DOUBLE,
	SURRENDER;
	
	public static BlackjackAction[] availableActionsNotFirstRound(){
		return new BlackjackAction[]{HIT, STAND};
	}
	
	public static BlackjackAction[] availableActionsFirstRound(){
		return new BlackjackAction[]{HIT, STAND, DOUBLE};
	}
		
	public boolean isEnd(){
		return this.equals(STAND) || this.equals(DOUBLE) || this.equals(SURRENDER);
	}
	
}
