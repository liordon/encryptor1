package ofek.logs;

import static ofek.logs.EventType.*;

import java.util.Observable;

public class EncryptionEvent extends Observable{
  private EventType state = DECRYPTION_ENDED;
  private String sourceFile, outputFile, algorithm;
  private long lastStart;
  
  private void publisResults(long processTime) {
    EncryptionEventArgs eea = new EncryptionEventArgs(sourceFile,outputFile, algorithm, processTime);
    setChanged();
    notifyObservers(eea);
  }
  
  private void setFields(String src, String out, String algo) {
    sourceFile	= src;
    outputFile	= out;
    algorithm	= algo;
    lastStart = System.currentTimeMillis();
  }
  
  public void encryptionStarted(String src, String out, String algo){
    if (ENCRYPTION_ENDED != state && DECRYPTION_ENDED != state)
      throw new IllegalEncryptionEventException();
    state = ENCRYPTION_STARTED;
    setFields(src, out, algo);
  }
  public void encryptionEnded(){
    long processTime = System.currentTimeMillis()-lastStart;
    if (ENCRYPTION_STARTED != state)
      throw new IllegalEncryptionEventException();
    publisResults(processTime);
    state = ENCRYPTION_ENDED;
  }
  
  public void decryptionStarted(String src, String out, String algo){
    if (ENCRYPTION_ENDED != state && DECRYPTION_ENDED != state)
      throw new IllegalEncryptionEventException();
    state = DECRYPTION_STARTED;
    setFields(src, out, algo);
  }
  public void decryptionEnded(){
    long processTime = System.currentTimeMillis()-lastStart;
    if (DECRYPTION_STARTED != state)
      throw new IllegalEncryptionEventException();
    state = DECRYPTION_ENDED;
    publisResults(processTime);
  }
}
