package com.blackjack.simulator;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedHandler implements RejectedExecutionHandler{

	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("REJECTED");
	}
	
}
