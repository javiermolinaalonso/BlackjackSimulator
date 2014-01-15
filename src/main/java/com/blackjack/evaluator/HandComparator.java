package com.blackjack.evaluator;

import java.util.Comparator;

import com.blackjack.entities.BJHand;
import com.blackjack.entities.DealerHand;
import com.blackjack.entities.PlayerHand;

public class HandComparator implements Comparator<BJHand>{

	public static final int PLAYER_WIN = 1;
	public static final int PLAYER_LOOSE = 2;
	public static final int PLAYER_DRAW = 3;
	
	public int doCompare(PlayerHand playerHand, DealerHand dealerHand) {
		if(playerHand.isBust())
			return PLAYER_LOOSE;
		if(playerHand.isBlackJack() && !dealerHand.isBlackJack())
			return PLAYER_WIN;
		int pValue = playerHand.getHigherValue();
		int dValue = dealerHand.getHigherValue(); 
		if(pValue > dValue)
			return PLAYER_WIN;
		else if(pValue < dValue)
			return PLAYER_LOOSE;
		else
			return PLAYER_DRAW;
	}

	public int compare(BJHand o1, BJHand o2) throws ClassCastException {
		return doCompare((PlayerHand) o1, (DealerHand)o2);
	}

	
}
