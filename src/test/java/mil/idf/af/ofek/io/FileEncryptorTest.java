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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileEncryptorTest {
  private int                        key;
  private static FileInteractor      fi = new FileInteractor();
  private static EncryptionAlgorithm ea = new ShiftUpEncryption();
  private static FileEncryptor       $  = new FileEncryptor(ea);
  
  private void verifyAlgorithm(String s, int eKey) {
    if (!s.equals(ea.decrypt(ea.encrypt(s, eKey), eKey))) {
      fail(" plaintext: " + s + "\ncyphertext: " + ea.encrypt(s, eKey)
          + "\ndecryption: " + ea.decrypt(ea.encrypt(s, eKey), eKey)
          + "\nencryption is not reversible with key " + key + " . "
          + "\nfix it before file interactor can be tested.");
    }
  }
  
  @Before
  public void setUp() throws Exception {
    key = Math.abs(new Random().nextInt());
    verifyAlgorithm(MSG, key);
  }
  
  @After
  public void tearDown() throws Exception {
    Files.deleteIfExists(Paths.get(ENCRYPTED_FILE));
    Files.deleteIfExists(Paths.get(DECRYPTED_FILE));
    Files.deleteIfExists(Paths.get(KEY_FILE));
    Files.deleteIfExists(Paths.get(CRYPT_DIR));
  }
  
  @Rule
  public ExpectedException illegalTarget = ExpectedException.none();
  
  @Test
  public void encryptionAndDecryptionAreReversible()
      throws NumberFormatException, IOException {
    $.storeKey(key, KEY_FILE);
    $.encryptFile(PLAIN_FILE, KEY_FILE, ENCRYPTED_FILE);
    $.decryptFile(ENCRYPTED_FILE, KEY_FILE, DECRYPTED_FILE);
    String msg = fi.readData(PLAIN_FILE);
    String res = fi.readData(DECRYPTED_FILE);
    assertEquals(msg, res);
  }
  
  @Test
  public void encryptionThroughFileEncryptorUsesSpecifiedAlgorithm()
      throws IOException {
    $.storeKey(key, KEY_FILE);
    $.encryptFile(PLAIN_FILE, KEY_FILE, ENCRYPTED_FILE);
    String plainText = fi.readData(PLAIN_FILE);
    String cypherText = fi.readData(ENCRYPTED_FILE);
    int usedKey = Integer.parseInt(fi.readData(KEY_FILE));
    assertEquals(ea.encrypt(plainText, usedKey), cypherText);
  }
  
  @Test
  public void noneExistingFolderInputThrowsException() throws IOException {
    illegalTarget.expect(IllegalEncryptionTargetException.class);
    $.getDirContent("here");
  }
  
  @Test
  public void folderCreationIsFunctional() throws IOException {
    $.createDirectory(CRYPT_DIR);
    assertTrue(fi.isFolder(CRYPT_DIR));
  }
  
  @Test
  public void encryptedFolderContainsEncryptedVersionsOfTxtFiles()
      throws IOException {
    $.storeKey(key, KEY_FILE);
    $.encryptFiles(PLAIN_FILES, KEY_FILE, CRYPT_FILES);
    assertTrue("file#1 should have an encrypted version",
        fi.isFile(CRYPT_FILES[0]));
    assertTrue("file#2 should have an encrypted version",
        fi.isFile(CRYPT_FILES[1]));
  }
  
  @Test
  public void multipleEncryptionsAndDecryptionsAreReversible()
      throws NumberFormatException, IOException {
    $.storeKey(key, KEY_FILE);
    $.encryptFiles(PLAIN_FILES, KEY_FILE, CRYPT_FILES);
    $.decryptFiles(CRYPT_FILES, KEY_FILE, DECRYPT_FILES);
    for (int i = 0; PLAIN_FILES.length > i; ++i) {
      String msg = fi.readData(PLAIN_FILES[i]);
      String res = fi.readData(DECRYPT_FILES[i]);
      assertEquals(msg, res);
    }
  }
}
