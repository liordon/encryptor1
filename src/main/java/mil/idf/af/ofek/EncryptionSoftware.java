package mil.idf.af.ofek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import mil.idf.af.ofek.crypto.DoubleEncryption;
import mil.idf.af.ofek.crypto.EncryptionAlgorithm;
import mil.idf.af.ofek.crypto.ShiftUpEncryption;
import mil.idf.af.ofek.io.DirectoryInteractor;
import mil.idf.af.ofek.io.DirectoryProcessor;
import mil.idf.af.ofek.io.FileInteractor;
import mil.idf.af.ofek.io.SyncDirectoryProcessor;
import mil.idf.af.ofek.logs.EncryptionEvent;
import mil.idf.af.ofek.logs.EncryptionLogger;

public class EncryptionSoftware {
  private static EncryptionAlgorithm algorithm      = new DoubleEncryption(
                                                        new ShiftUpEncryption());
  private static EncryptionEvent     event          = new EncryptionEvent();
  private static FileInteractor      fileInteractor = new FileInteractor();
  private static FileEncryptor       encryptor      = new FileEncryptor(
                                                        algorithm, event,
                                                        fileInteractor);
  private static BufferedReader      userInput      = new BufferedReader(
                                                        new InputStreamReader(
                                                            System.in));
  
  private static String readFromUser(String request) {
    System.out.println(request);
    String $ = null;
    try {
      $ = userInput.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return $;
  }
  
  public static void main(String[] args) {
    event.addObserver(new EncryptionLogger());
    Integer choice = 1;
    while (choice != 0) {
      System.out.println("what would you like to do?\n"
          + "1 encrypt a plaintext file\n" + "2 decrypt an encrypted file\n"
          + "3 encrypt an entire folder\n" + "0 quit");
      try {
        choice = Integer.parseInt(userInput.readLine());
      } catch (IOException e) {
        System.out.println("not an integer input");
        e.printStackTrace();
      }
      System.out.println();
      switch (choice) {
        case (1):
          singleEncryptionProcess();
          break;
        case (2):
          singleDecryptionProcess();
          break;
        case (3):
          folderEncryptionProcess();
          break;
        default:
          System.out.println(choice.toString() + "is not a legal option");
        case (0):
          ;
      }
    }
    
  }
  
  private static void singleEncryptionProcess() {
    int key = new Random().nextInt(999) + 1;
    String path = readFromUser("please specify the path for the plaintext file");
    FilePathParser fpp = new FilePathParser(path);
    
    try {
      encryptor.storeKey(key, fpp.getKeyName());
      encryptor.encryptFile(path, fpp.getKeyName(), fpp.getEncryptedName());
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    System.out.println("encryption completed succeddfully!");
  }
  
  private static void singleDecryptionProcess() {
    String keyPath = readFromUser("please specify the path for the key file");
    String cypherPath = readFromUser("please specify the path "
        + "for the encrypted file");
    FilePathParser fpp = new FilePathParser(cypherPath);
    
    try {
      encryptor.decryptFile(cypherPath, keyPath, fpp.getDecryptedName());
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    System.out.println("decryption completed succeddfully!");
  }
  
  private static void folderEncryptionProcess() {
    int key = new Random().nextInt(999) + 1;
    String path = readFromUser("please specify the path for the plaintext folder");
    FilePathParser fpp = new FilePathParser(path);
    new DirectoryInteractor().makeDirInPath(fpp.getEncryptedFolderPath());
    
    try {
      encryptor.storeKey(key, fpp.getFolderKeyName());
      DirectoryProcessor dirProcessor = new SyncDirectoryProcessor(encryptor);
      dirProcessor.encryptFolder(path, key);
      
      // String[] filesToEncrypt = fe.getDirContent(path);
      // filesToEncrypt = keepOnlyTxt(path, filesToEncrypt);
      // String[] encryptionTargetFiles = encryptedFilesNames(
      // fpp.getEncryptedFolderPath(), filesToEncrypt);
      
      // fe.encryptFiles(filesToEncrypt, fpp.getFolderKeyName(),
      // encryptionTargetFiles);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    System.out.println("encryption completed succeddfully!");
  }
  
  private static String[] encryptedFilesNames(String encryptionFolderPath,
      String[] filesToEncrypt) {
    
    ArrayList<String> $ = new ArrayList<String>();
    for (String f : filesToEncrypt) {
      FilePathParser fpp = new FilePathParser(f);
      $.add(encryptionFolderPath + "/" + fpp.getFileName() + fpp.getExtention());
    }
    
    return $.toArray(new String[$.size()]);
  }
  
  private static String[] keepOnlyTxt(String originalFolderPath,
      String[] filesToEncrypt) {
    ArrayList<String> $ = new ArrayList<String>();
    for (String f : filesToEncrypt) {
      FilePathParser fpp = new FilePathParser(f);
      if (fpp.getExtention().equals(".txt"))
        $.add(originalFolderPath + "/" + f);
    }
    return $.toArray(new String[$.size()]);
  }
  
}
