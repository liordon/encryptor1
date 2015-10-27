package mil.idf.af.ofek.io;

import static mil.idf.af.ofek.TestResources.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import mil.idf.af.ofek.FileEncryptor;
import mil.idf.af.ofek.crypto.EncryptionAlgorithm;
import mil.idf.af.ofek.crypto.ShiftUpEncryption;
import mil.idf.af.ofek.io.FileInteractor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileEncryptorTest {
  private int key;
  private static FileInteractor fi = new FileInteractor();
  private static EncryptionAlgorithm ea = new ShiftUpEncryption();
  private static FileEncryptor $ = new FileEncryptor(ea);
  
  @Before
  public void setUp() throws Exception {
    key = Math.abs(new Random().nextInt());
    verifyAlgorythm(MSG, key);
  }
  
  @After
  public void tearDown() throws Exception {
    Files.deleteIfExists(Paths.get(ENCRYPTED_FILE));
    Files.deleteIfExists(Paths.get(DECRYPTED_FILE));
    Files.deleteIfExists(Paths.get(KEY_FILE));
  }
  
  @Test
  public void encryptionAndDecryptionAreReversible() throws NumberFormatException, IOException {
    $.encryptFile(PLAIN_FILE, KEY_FILE, ENCRYPTED_FILE);
    $.decryptFile(ENCRYPTED_FILE, KEY_FILE, DECRYPTED_FILE);
    String msg = fi.readData(PLAIN_FILE);
    String res = fi.readData(DECRYPTED_FILE);
    assertEquals(msg, res);
  }
  
  @Test
  public void encryptionThroughFileEncryptorUsesSpecifiedAlgorithm() throws IOException{
    $.encryptFile(PLAIN_FILE, KEY_FILE, ENCRYPTED_FILE);
    String plainText = fi.readData(PLAIN_FILE);
    String cypherText = fi.readData(ENCRYPTED_FILE);
    int usedKey = Integer.parseInt(fi.readData(KEY_FILE));
    assertEquals(ea.encrypt(plainText, usedKey),cypherText);
  }

  private void verifyAlgorythm(String s, int eKey){
    if(! s.equals(ea.decrypt(ea.encrypt(s, eKey), eKey))){
      fail(" plaintext: "+s+
	  "\ncyphertext: "+ea.encrypt(s, eKey)+
	  "\ndecryption: "+ea.decrypt(ea.encrypt(s, eKey),eKey)+
	  "\nencryption is not reversible with key "+key+" . "+
	  "\nfix it before file interactor can be tested.");}
  }
}
