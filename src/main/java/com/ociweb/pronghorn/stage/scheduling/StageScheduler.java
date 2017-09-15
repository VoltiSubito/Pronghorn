package com.ociweb.pronghorn.stage.scheduling;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.pipe.ThreadBasedCallerLookup;

public abstract class StageScheduler {

	static final Logger log = LoggerFactory.getLogger(StageScheduler.class);
	protected GraphManager graphManager;
	
	private ThreadLocal<Integer> callerId = new ThreadLocal<Integer>();
	
	public StageScheduler(GraphManager graphManager) {
		GraphManager.disableMutation(graphManager);
		this.graphManager = graphManager;		
		assert(initThreadChecking(graphManager));
	}

	private boolean initThreadChecking(final GraphManager graphManager) {
		Pipe.setThreadCallerLookup(new ThreadBasedCallerLookup(){

			@Override
			public int getCallerId() {
				Integer id = callerId.get();
				return null==id ? -1 : id.intValue();
			}

			@Override
			public int getProducerId(int pipeId) {				
				return GraphManager.getRingProducerId(graphManager, pipeId);
			}

			@Override
			public int getConsumerId(int pipeId) {
				return GraphManager.getRingConsumerId(graphManager, pipeId);
			}});
		
		return true;
	}

	protected void setCallerId(Integer caller) {
		callerId.set(caller);
	}
	
	protected void clearCallerId() {
		callerId.set(null);
	}
	
	protected boolean validShutdownState() {
		return GraphManager.validShutdown(graphManager);	
	}

	public abstract void startup();
	public abstract void shutdown();
	public abstract boolean awaitTermination(long timeout, TimeUnit unit);
	public abstract void awaitTermination(long timeout, TimeUnit unit, Runnable clean, Runnable dirty);
	public abstract boolean TerminateNow();

	

	private static int idealThreadCount() {
		return Runtime.getRuntime().availableProcessors()*4;
	}
	
	public static StageScheduler defaultScheduler(GraphManager gm) {
		
		final boolean threadLimitHard = true;//must make this a hard limit or we can saturate the system easily.
		final int scale = 2;
		
		int threadLimit = idealThreadCount();
		assert(threadLimit>0);
		final int countStages = GraphManager.countStages(gm);
		if (threadLimit<=0 && countStages > scale*threadLimit) {
			//do not allow the ThreadPerStageScheduler to be used, we must group
			threadLimit = idealThreadCount()*scale;//this must be large so give them a few more
		}
		
		return (threadLimit>=countStages) ?
				                           new ThreadPerStageScheduler(gm): 
 			                               new FixedThreadsScheduler(gm, threadLimit, threadLimitHard);
	}
	
	
	
}
