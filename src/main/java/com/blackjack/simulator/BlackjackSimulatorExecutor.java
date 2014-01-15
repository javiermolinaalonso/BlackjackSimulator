package com.blackjack.simulator;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.blackjack.StateSimulator;

public class BlackjackSimulatorExecutor extends ThreadPoolExecutor {

	protected HashMap<BlackjackAction, StateSimulator> mapRatio;
	
	public BlackjackSimulatorExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, BlackjackAction[] actions) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		mapRatio = new HashMap<BlackjackAction, StateSimulator>();
		for(BlackjackAction action : actions){
			mapRatio.put(action, new StateSimulator(action));
		}
	}

	@Override
	public void afterExecute(Runnable r, Throwable t){
		RunnableSimulator sim = (RunnableSimulator) r;
		for(BlackjackAction nSimAction : sim.getMapRatio().keySet()){
			mapRatio.get(nSimAction).addAll(sim.getMapRatio().get(nSimAction));
		}
	}

	public HashMap<BlackjackAction, StateSimulator> getMapRatio() {
		return mapRatio;
	}
}
