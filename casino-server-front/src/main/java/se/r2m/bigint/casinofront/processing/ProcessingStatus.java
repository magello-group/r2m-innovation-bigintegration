package se.r2m.bigint.casinofront.processing;

public class ProcessingStatus {

    private final String status;

    private final int processingTimeMs;

    public ProcessingStatus() {
      status = "UNKNOWN";
      processingTimeMs = -1;
    }

    public ProcessingStatus(String status, int processingTimeMs) {
      this.status = status;
      this.processingTimeMs = processingTimeMs;
    }

    public String getStatus() {
      return status;
    }

    public int getProcessingTimeMs() {
      return processingTimeMs;
    }
  }