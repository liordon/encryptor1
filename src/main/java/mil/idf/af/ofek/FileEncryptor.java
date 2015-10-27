package mil.idf.af.ofek;

import java.io.IOException;
import java.util.Random;

import mil.idf.af.ofek.crypto.EncryptionAlgorithm;
import mil.idf.af.ofek.io.FileInteractor;

public class FileEncryptor {
  private static FileInteractor fi = new FileInteractor();
  private static EncryptionAlgorithm ea;
  
  public FileEncryptor(EncryptionAlgorithm algo) {
    ea = algo;
  }
  
  public void encryptFile(String srcFilePath, String keyPath, String cypherPath) throws IOException{
    Integer key = new Random().nextInt(999999)+1;
    String msg = fi.readData(srcFilePath);
    fi.writeData(ea.encrypt(msg, key), cypherPath);
    fi.writeData(key.toString(), keyPath);
  }
  
  public void decryptFile(String srcFilePath, String keyPath, String plainTextPath) throws NumberFormatException, IOException{
    int key = Integer.parseInt(fi.readData(keyPath));
    String msg = fi.readData(srcFilePath);
    fi.writeData(ea.decrypt(msg, key), plainTextPath);
  }
}
