package mil.idf.af.ofek;

import java.io.FileNotFoundException;
import java.io.IOException;

import mil.idf.af.ofek.crypto.EncryptionAlgorithm;
import mil.idf.af.ofek.io.FileInteractor;
import mil.idf.af.ofek.logs.EncryptionEvent;

public class FileEncryptor {
  private static FileInteractor     fi = new FileInteractor();
  private final EncryptionAlgorithm ea;
  private final EncryptionEvent     ee;
  
  public FileEncryptor(EncryptionAlgorithm algo, EncryptionEvent event) {
    ea = algo;
    ee = event;
  }
  
  public void storeKey(Integer key, String keyPath)
      throws FileNotFoundException {
    fi.writeData(key.toString(), keyPath);
  }
  
  private int loadKey(String keyPath) throws IOException {
    return Integer.parseInt(fi.readData(keyPath));
  }
  
  private void writeEncryption(String srcFilePath, String cypherPath, int key)
      throws IOException {
    String msg = fi.readData(srcFilePath);
    ee.encryptionStarted(srcFilePath, cypherPath, ea.toString());
    fi.writeData(ea.encrypt(msg, key), cypherPath);
    ee.encryptionEnded();
  }
  
  private void writeDecryption(String srcFilePath, String plainTextPath, int key)
      throws IOException, FileNotFoundException {
    String msg = fi.readData(srcFilePath);
    ee.decryptionStarted(srcFilePath, plainTextPath, ea.toString());
    fi.writeData(ea.decrypt(msg, key), plainTextPath);
    ee.decryptionEnded();
  }
  
  public String[] getDirContent(String dirPath) {
    return fi.contentOf(dirPath);
  }
  
  public void createDirectory(String dirPath) {
    fi.makeDir(dirPath);
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
