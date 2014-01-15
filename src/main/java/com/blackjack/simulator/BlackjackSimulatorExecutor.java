package com.blackjack.simulator;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.blackjack.StateSimulator;

public class BlackjackSimulatorExecutor extends ThreadPoolExecutor {

	protected ConcurrentHashMap<BlackjackAction, StateSimulator> mapRatio;
	private Object sync = new Object();
	
	public BlackjackSimulatorExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, BlackjackAction[] actions) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new RejectedHandler());
		mapRatio = new ConcurrentHashMap<BlackjackAction, StateSimulator>();
		for(BlackjackAction action : actions){
			mapRatio.put(action, new StateSimulator(action));
		}
	}

	@Override
	public void afterExecute(Runnable r, Throwable t){
		RunnableSimulator sim = (RunnableSimulator) r;
		synchronized (sync) {
			for(BlackjackAction nSimAction : sim.getMapRatio().keySet()){
				mapRatio.get(nSimAction).addAll(sim.getMapRatio().get(nSimAction));
			}
		}
	}

	public Map<BlackjackAction, StateSimulator> getMapRatio() {
		return mapRatio;
	}
	
}
