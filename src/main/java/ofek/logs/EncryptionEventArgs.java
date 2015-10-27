package ofek.logs;

public class EncryptionEventArgs {
  private final String sourceFile, outputFile, algorithm;

  private final long timeSpent;

  public EncryptionEventArgs(String src, String out,
      String algo, long time) {
    sourceFile	= src;
    outputFile	= out;
    algorithm	= algo;
    timeSpent	= time;
  }
  
  public String getSourceFile() {
    return sourceFile;
  }
  
  public String getOutputFile() {
    return outputFile;
  }
  
  public String getAlgorithm() {
    return algorithm;
  }
  
  public long getTimeSpent() {
    return timeSpent;
  }
  
}
