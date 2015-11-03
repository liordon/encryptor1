package mil.idf.af.ofek.io;

import java.util.Observer;

import mil.idf.af.ofek.FileEncryptor;
import mil.idf.af.ofek.crypto.EncryptionAlgorithm;
import mil.idf.af.ofek.logs.EncryptionEvent;

public class FileEncryptorFactory {
  private final EncryptionAlgorithm algorithm;
  private final Observer            logger;
  
  public FileEncryptorFactory(EncryptionAlgorithm ea, Observer o) {
    algorithm = ea;
    logger = o;
  }
  
  public FileEncryptor build() {
    EncryptionEvent e = new EncryptionEvent();
    e.addObserver(logger);
    try {
      return new FileEncryptor(algorithm.clone(), e, new FileInteractor());
    } catch (CloneNotSupportedException e1) {
      e1.printStackTrace();
      return null;
    }
  }
}
