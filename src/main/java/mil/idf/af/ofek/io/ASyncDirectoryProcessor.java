package mil.idf.af.ofek.io;

import java.io.IOException;
import java.util.Observer;

import mil.idf.af.ofek.FileEncryptor;
import mil.idf.af.ofek.crypto.EncryptionAlgorithm;

public class ASyncDirectoryProcessor implements DirectoryProcessor {
  
  private final FileEncryptorFactory encryptorFactory;
  
  public ASyncDirectoryProcessor(EncryptionAlgorithm ea, Observer o) {
    encryptorFactory = new FileEncryptorFactory(ea, o);
  }
  
  @Override
  public void encryptFolder(String srcDir, String targetDir) {
    final String keyPath = targetDir + "/key.txt";
    final String[] filesToEncrypt = new DirectoryInteractor()
        .getTextFilesFromFolder(srcDir);
    
    for (String file : filesToEncrypt) {
      final String plainText = srcDir + "/" + file;
      final String cypherText = targetDir + "/" + file;
      final FileEncryptor encryptor = encryptorFactory.build();
      new Runnable() {
        
        @Override
        public void run() {
          try {
            encryptor.encryptFile(plainText, keyPath, cypherText);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }.run();
    }
    
  }
  
  @Override
  public void decryptFolder(String srcDir, String targetDir) {
    // TODO Auto-generated method stub
    
  }
  
}
