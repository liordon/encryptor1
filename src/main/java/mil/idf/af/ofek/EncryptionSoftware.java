package mil.idf.af.ofek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mil.idf.af.ofek.crypto.DoubleEncryption;
import mil.idf.af.ofek.crypto.EncryptionAlgorithm;
import mil.idf.af.ofek.crypto.ShiftUpEncryption;

public class EncryptionSoftware {
  private static EncryptionAlgorithm ea = new DoubleEncryption(
                                            new ShiftUpEncryption());
  private static FileEncryptor       fe = new FileEncryptor(ea);
  private static BufferedReader      in = new BufferedReader(
                                            new InputStreamReader(System.in));
  
  private static void intErrMsg() {
    System.out.println("your input was not an int, please try again");
  }
  
  private static void illegalOptionErrMsg(String choice) {
    System.out.println(choice + "is an illegal option! you criminal!");
  }
  
  private static void fileReadingErrMsg() {
    System.out.println("sorry, this file could not be read.");
  }
  
  public static void main(String[] args) {
    Integer choice = 1;
    while (choice != 0) {
      System.out.println("what would you like to do?\n"
          + "1 encrypt a plaintext file\n" + "2 decrypt an encrypted file\n"
          + "3 encrypt an entire folder\n" + "4 decrypt an encrypted folder\n"
          + "0 quit");
      try {
        choice = Integer.parseInt(in.readLine());
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NumberFormatException e) {
        intErrMsg();
      }
      System.out.println();
      switch (choice) {
        case (1):
          encryptionProcess();
          break;
        case (2):
          decryptionProcess();
          break;
        default:
          illegalOptionErrMsg(choice.toString());
        case (0):
          ;
      }
    }
    
  }
  
  private static void encryptionProcess() {
    System.out.println("please specify the path for the plaintext file");
    
    String path = "";
    try {
      path = (in.readLine());
    } catch (IOException e) {
      System.out.println("something went wrong with your input.");
      e.printStackTrace();
      return;
    }
    
    FilePathParser fpp = new FilePathParser(path);
    
    try {
      fe.encryptFile(path, fpp.getKeyName(), fpp.getEncryptedName());
    } catch (IOException e) {
      fileReadingErrMsg();
      e.printStackTrace();
      return;
    }
    
    System.out.println("encryption completed succeddfully!");
  }
  
  private static void decryptionProcess() {
    String cypherPath;
    String keyPath;
    try {
      System.out.println("please specify the path for the encrypted file");
      cypherPath = in.readLine();
      
      System.out.println("please specify the path for the key file");
      keyPath = in.readLine();
      
    } catch (IOException e) {
      System.out.println("something went wrong with your file paths.");
      e.printStackTrace();
      return;
    }
    FilePathParser fpp = new FilePathParser(cypherPath);
    
    try {
      fe.decryptFile(cypherPath, keyPath, fpp.getDecryptedName());
    } catch (IOException e) {
      fileReadingErrMsg();
      e.printStackTrace();
      return;
    }
    System.out.println("decryption completed succeddfully!");
  }
  
  // private static void folderEncryption(){
  //
  // }
}
