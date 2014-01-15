package com.blackjack;

import com.blackjack.simulator.BlackjackAction;

public class SimulatorStatistics {

	public static long hitTime = 0;
	public static long doubleTime = 0;
	public static long standTime = 0;
	public static long totalTime = 0;
	public static long shuffleTime = 0;
	
	public static void addHit(long t){
		hitTime+=t;
	}
	
	public static void addStand(long t){
		standTime+=t;
	}
	
	public static void addDouble(long t){
		doubleTime+=t;
	}
	
	public static void addTotal(long t){
		totalTime+=t;
	}
	
	public static void addAction(BlackjackAction action, Long actionTime) {
		switch(action){
		case DOUBLE:
			addDouble(actionTime);
			break;
		case HIT:
			addHit(actionTime);
			break;
		case STAND:
			addStand(actionTime);
			break;
		case SURRENDER:
			break;
		default:
			break;
		
		}
	}
	
	public static void print(){
		System.out.println("Hit: " + hitTime + "ms, Stand: " + standTime + "ms, Double: " + doubleTime+"ms.");
		System.out.println("Shuffle time: " + shuffleTime + "ms.");
	}

}
