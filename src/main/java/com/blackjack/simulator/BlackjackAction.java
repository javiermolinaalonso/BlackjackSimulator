package com.blackjack.simulator;

public enum BlackjackAction {
	HIT,
	STAND,
//	SPLIT,
	DOUBLE,
	SURRENDER;
	
	public static BlackjackAction[] availableActionsNotFirstRound(){
		return new BlackjackAction[]{STAND};
	}
	
	public static BlackjackAction[] availableActionsFirstRound(){
		return new BlackjackAction[]{STAND, DOUBLE};
	}
		
	public boolean isEnd(){
		return this.equals(STAND) || this.equals(DOUBLE) || this.equals(SURRENDER);
	}
	
}
