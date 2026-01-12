package com.engine.simulation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simulation of the High-Throughput Asynchronous Reporting Engine.
 * Demonstrates the use of Thread Pools to prevent system exhaustion.
 */
public class ReportProcessor {

    // A Fixed Thread Pool ensures we only process 5 reports at a time
    private final ExecutorService reportExecutor = Executors.newFixedThreadPool(5);

    public void processReportRequest(String reportId) {
        System.out.println("Pushing Report " + reportId + " to ActiveMQ Queue...");
        
        // Simulating the Consumer picking up the message from the Queue
        reportExecutor.submit(() -> {
            try {
                System.out.println("Thread [" + Thread.currentThread().getName() + "] started processing Report: " + reportId);
                
                // Simulating heavy DB work (Oracle PL/SQL execution)
                Thread.sleep(2000); 
                
                System.out.println("Successfully generated Report: " + reportId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public static void main(String[] args) {
        ReportProcessor engine = new ReportProcessor();
        
        // Simulating a burst of 10 users requesting reports
        for (int i = 1; i <= 10; i++) {
            engine.processReportRequest("REQ-00" + i);
        }
        
        engine.shutdown();
    }

    public void shutdown() {
        reportExecutor.shutdown();
    }
}
