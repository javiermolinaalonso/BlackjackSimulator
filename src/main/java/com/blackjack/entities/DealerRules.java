package com.blackjack.entities;


public class DealerRules {
	public static final Integer MAX_VALUE = 21;
	public static final Integer MAX_PLAYERS = 6;
	public static final Integer MIN_DEALER_VALUE = 17;
	
	public static Integer N_DECKS = 6;
	public static Boolean SPLIT_ALLOWED = true;
	public static Boolean MULTIPLE_SPLIT_ALLOWED = false;
	public static Boolean SECURE_ALLOWED = false;
	public static Boolean SURRENDER_ALLOWED = false;
	public static Boolean DOUBLE_ALLOWED = true;
	public static Boolean IMMEDIATE_BLACKJACK_SHOWN = false;
	public static Integer NUM_DECKS_TO_SHUFFLE = 2;
	public static Float 	BLACKJACK_REWARD_RELATION = 1.5f;
	public static Float 	MIN_BET = 2f;
	public static Float 	MAX_BET = 300f;
	
	public Integer getMaxPlayers() {
		return MAX_PLAYERS;
	}
	
}
