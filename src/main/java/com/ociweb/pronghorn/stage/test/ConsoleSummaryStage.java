package com.ociweb.pronghorn.stage.test;

import java.io.IOException;

import com.ociweb.pronghorn.pipe.FieldReferenceOffsetManager;
import com.ociweb.pronghorn.pipe.MessageSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.pipe.PipeReader;
import com.ociweb.pronghorn.pipe.util.Appendables;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class ConsoleSummaryStage<T extends MessageSchema> extends PronghornStage {

	private final Pipe<T> inputRing;
	private final StringBuilder console = new StringBuilder();
	
	private final long[] totalCounts;
	private final long[] counts;
	
	private long stepTime = 2000;//2 sec
	private long nextOutTime = System.currentTimeMillis()+stepTime;
		
	//TODO: AA, need validation stage to confirm values are in range and text is not too long

	public ConsoleSummaryStage(GraphManager gm, Pipe<T> inputRing) {
		super(gm, inputRing, NONE);
		this.inputRing = inputRing;

		FieldReferenceOffsetManager from = Pipe.from(inputRing);		
		totalCounts = new long[from.tokensLen];
		counts = new long[from.tokensLen];
	}

	@Override
	public void shutdown() {
		try {
            processCounts("Final:",counts,totalCounts);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
		processTotal("Totals:",totalCounts, Pipe.from(inputRing));
	}

	@Override
	public void run() {
	    try {
		boolean foundData = dataToRead(counts);
		long now = System.currentTimeMillis();
		if (foundData || now>nextOutTime) {
			nextOutTime = now+stepTime;			
                if (!processCounts("Running:",counts,totalCounts)) {
                	return;
                }
		}

	    } catch (IOException e) {
	        throw new RuntimeException(e);            
	   }

	}
	
	public long totalMessages() {
		long sum = 0;
		int i = totalCounts.length;
		while (--i>=0) {
			sum += totalCounts[i];
		}
		return sum;
	}

	private boolean processCounts(String label, long[] counts,	long[] totalCounts) throws IOException {
		
		console.setLength(0);
		return processCountsLoop(label, counts, totalCounts, 0, 0, 0, counts.length);
	}

    private boolean processCountsLoop(String label, long[] counts, long[] totalCounts, int i, long newMessages, long totalMessages, int limit) throws IOException {
        while (i<limit) {
			newMessages += counts[i];
			writeToConsole(counts, totalCounts, i);
			totalMessages += totalCounts[i];
			i++;
		}
		return cleanupReport(label, newMessages, totalMessages);
    }

    private boolean cleanupReport(String label, long newMessages, long totalMessages) throws IOException {
        if (newMessages>0) {
			Appendables.appendValue(console.append(" total:"), totalMessages);
			System.out.println(label+console);
		}
		return newMessages>0;
    }

    private void writeToConsole(long[] counts, long[] totalCounts, int i) throws IOException {
        if (counts[i]>0) {
        	totalCounts[i] += counts[i];
        	Appendables.appendValue(Appendables.appendValue(console.append('['), i).append(']'), counts[i]).append(' ');
        	counts[i]=0;
        }
    }

	private boolean processTotal(String label, long[] totalCounts, FieldReferenceOffsetManager from) {
		
		console.setLength(0);
		int i = 0;
		long totalMsg = 0;
		while (i<totalCounts.length) {
			totalMsg += totalCounts[i];
			if (totalCounts[i]>0) {
				console.append('[').append(i).append(']').append(totalCounts[i]);
				if (null!=from.fieldNameScript) {
					if (null!=from.fieldNameScript[i]) {
						console.append(" Name:").append(from.fieldNameScript[i]);
					}
					console.append(" Id:").append(from.fieldIdScript[i]);					
				}
				console.append("\n ");
			}
			i++;
		}
		System.out.println(label);
		System.out.println(console);
		System.out.println("Total:"+totalMsg);
		return totalMsg>0;
	}
	
	private boolean dataToRead(long[] counts) {
		
		int msgIdx = 0;
		boolean data = false;
		
		while (PipeReader.tryReadFragment(inputRing)) {
			if (PipeReader.isNewMessage(inputRing)) {
				msgIdx = PipeReader.getMsgIdx(inputRing);
				if (msgIdx<0) {
				    PipeReader.releaseReadLock(inputRing);
				    requestShutdown();
					break;
				} else {
					counts[msgIdx]++;
					data = true;
				}
			}
			PipeReader.releaseReadLock(inputRing);
		}		
		return data;
	}
}
