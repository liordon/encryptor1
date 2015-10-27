package mil.idf.af.ofek.logs;

import java.util.Observable;
import java.util.Observer;

public class EncryptionLogger implements Observer {
  
  @Override
  public void update(Observable o, Object arg) {
    EncryptionEventArgs eea = (EncryptionEventArgs) arg;
    System.out.println("the encryption of file " + eea.getSourceFile()
        + "with the algorithm " + eea.getAlgorithm() + " took "
        + eea.getTimeSpent() + "miliseconds. the encrypted file is "
        + eea.getOutputFile());
  }
  
}
