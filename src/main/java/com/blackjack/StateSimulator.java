package com.blackjack;

import java.text.NumberFormat;

import com.blackjack.simulator.BlackjackAction;

public class StateSimulator {

	private BlackjackAction action;
	private Double pVictories;
	private Double dVictories;
	private Double draws;
	
	
	public StateSimulator(){
		super();
		this.pVictories = 0d;
		this.dVictories = 0d;
		this.draws = 0d;
	}
	
	public StateSimulator(BlackjackAction action) {
		this();
		this.action = action;
	}
	public Double getpVictories() {
		return pVictories;
	}
	public void setpVictories(Double pVictories) {
		this.pVictories = pVictories;
	}
	public Double getdVictories() {
		return dVictories;
	}
	public void setdVictories(Double dVictories) {
		this.dVictories = dVictories;
	}
	public Double getDraws() {
		return draws;
	}
	public void setDraws(Double draws) {
		this.draws = draws;
	}
	public BlackjackAction getAction() {
		return action;
	}
	
	@Override
	public String toString(){
		NumberFormat percFormat = NumberFormat.getPercentInstance();
		NumberFormat numbFormat = NumberFormat.getNumberInstance();
		StringBuffer sb = new StringBuffer();
		sb.append("Action: " + action.toString() + "\n");
		sb.append("Player victories: " + numbFormat.format(pVictories) + "(" + percFormat.format(pVictories / (pVictories + dVictories + draws)) + ")\n");
		sb.append("Dealer victories: " + numbFormat.format(dVictories) + "(" + percFormat.format(dVictories / (pVictories + dVictories + draws)) + ")\n");
		sb.append("Draws: " + numbFormat.format(draws) + "(" + percFormat.format(draws / (pVictories + dVictories)) + ")\n");
		sb.append("Money ratio: " + numbFormat.format(getMoneyRatio())+"\n");
		return sb.toString();
	}

	public void addDVictory(Double amount) {
		this.dVictories+=amount;
	}

	public void addDraw(Double amount) {
		this.draws+=amount;
	}

	public void addPVictory(Double amount) {
		pVictories+=amount;
	}

	public double getMoneyRatio(){
		return pVictories - dVictories;
	}
	
	public double getPVictoryRatio(){
		return pVictories / (pVictories + dVictories + draws);
	}
	
	public double getDVictoryRatio(){
		return dVictories / (pVictories + dVictories + draws);
	}
	
	public double getDrawRatio(){
		return draws / (pVictories + dVictories + draws);
	}

	public void addAll(StateSimulator stateSimulator) {
		this.draws += stateSimulator.draws;
		this.dVictories += stateSimulator.dVictories;
		this.pVictories += stateSimulator.pVictories;
	}
}
