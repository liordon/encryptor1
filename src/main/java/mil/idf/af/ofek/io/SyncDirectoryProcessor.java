package mil.idf.af.ofek.io;

import java.io.IOException;
import java.util.Observer;

import mil.idf.af.ofek.FileEncryptor;
import mil.idf.af.ofek.crypto.EncryptionAlgorithm;
import mil.idf.af.ofek.logs.EncryptionEvent;

public class SyncDirectoryProcessor implements DirectoryProcessor {
  private final FileEncryptor encryptor;
  
  public SyncDirectoryProcessor(FileEncryptor file) {
    encryptor = file;
  }
  
  public SyncDirectoryProcessor(EncryptionAlgorithm ea, Observer o) {
    EncryptionEvent ee = new EncryptionEvent();
    ee.addObserver(o);
    encryptor = new FileEncryptor(ea, ee, new FileInteractor());
  }
  
  @Override
  public void encryptFolder(String srcDir, String targetDir) {
    String[] filesToEncrypt = new DirectoryInteractor()
        .getTextFilesFromFolder(srcDir);
    
    try {
      for (String file : filesToEncrypt)
        encryptor.encryptFile(srcDir + "/" + file, targetDir + "/key.txt",
            targetDir + "/" + file);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    
  }
  
  @Override
  public void decryptFolder(String srcDir, String targetDir) {
    // TODO Auto-generated method stub
    
  }
  
}
