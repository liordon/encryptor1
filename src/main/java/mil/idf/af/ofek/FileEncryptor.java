package mil.idf.af.ofek;

import java.io.FileNotFoundException;
import java.io.IOException;

import mil.idf.af.ofek.crypto.EncryptionAlgorithm;
import mil.idf.af.ofek.io.FileInteractor;
import mil.idf.af.ofek.logs.EncryptionEvent;

public class FileEncryptor {
  private final FileInteractor      fileInteractor;
  private final EncryptionAlgorithm encryptor;
  private final EncryptionEvent     event;
  
  public FileEncryptor(EncryptionAlgorithm algo, EncryptionEvent e,
      FileInteractor f) {
    encryptor = algo;
    event = e;
    fileInteractor = f;
  }
  
  public void storeKey(Integer key, String keyPath)
      throws FileNotFoundException {
    fileInteractor.writeToFile(key.toString(), keyPath);
  }
  
  private int loadKey(String keyPath) throws IOException {
    return Integer.parseInt(fileInteractor.readFromFile(keyPath));
  }
  
  private void writeEncryption(String srcFilePath, String cypherPath, int key)
      throws IOException {
    String msg = fileInteractor.readFromFile(srcFilePath);
    event.encryptionStarted(srcFilePath, cypherPath, encryptor.toString());
    fileInteractor.writeToFile(encryptor.encrypt(msg, key), cypherPath);
    event.encryptionEnded();
  }
  
  private void writeDecryption(String srcFilePath, String plainTextPath, int key)
      throws IOException, FileNotFoundException {
    String msg = fileInteractor.readFromFile(srcFilePath);
    event.decryptionStarted(srcFilePath, plainTextPath, encryptor.toString());
    fileInteractor.writeToFile(encryptor.decrypt(msg, key), plainTextPath);
    event.decryptionEnded();
  }
  
  public void encryptFiles(String[] srcFiles, String keyPath,
      String[] cypherFiles) throws IOException {
    int key = loadKey(keyPath);
    
    for (int i = 0; i < srcFiles.length; ++i)
      writeEncryption(srcFiles[i], cypherFiles[i], key);
  }
  
  public void decryptFiles(String[] srcFiles, String keyPath,
      String[] plainFiles) throws IOException {
    int key = loadKey(keyPath);
    
    for (int i = 0; i < srcFiles.length; ++i)
      writeDecryption(srcFiles[i], plainFiles[i], key);
  }
  
  private String[] wrap(String s) {
    String[] $ = { s };
    return $;
  }
  
  public void encryptFile(String srcFilePath, String keyPath, String cypherPath)
      throws IOException {
    encryptFiles(wrap(srcFilePath), keyPath, wrap(cypherPath));
  }
  
  public void decryptFile(String srcFilePath, String keyPath,
      String plainTextPath) throws IOException {
    decryptFiles(wrap(srcFilePath), keyPath, wrap(plainTextPath));
  }
}
