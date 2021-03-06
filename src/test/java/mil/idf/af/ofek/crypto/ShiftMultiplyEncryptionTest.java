package mil.idf.af.ofek.crypto;

import static mil.idf.af.ofek.TestResources.*;
import static org.junit.Assert.*;

import java.util.Random;

import mil.idf.af.ofek.crypto.ShiftMultiplyEncryption;

import org.junit.Before;
import org.junit.Test;

public class ShiftMultiplyEncryptionTest extends EncryptionAlgorithmTest {
  private static final ShiftMultiplyEncryption $ = new ShiftMultiplyEncryption();
  private int                                  key;
  
  @Before
  public void setUp() {
    key = Math.abs(new Random().nextInt(94)) + 2;
  }
  
  @Test
  public void decryptionReversesEncryption() {
    assertEquals(MSG, decryption($, key));
  }
  
  @Test
  public void negativeKeyIsValid() {
    assertEquals(MSG, decryption($, -key));
  }
  
  @Test
  public void unitEncryptionIsMeaningless() {
    assertEquals(MSG, encryption($, 1));
  }
  
  @Test
  public void nonUnitEncryptionIsDifferentFromOrigin() {
    assertNotEquals(MSG, encryption($, key));
  }
  
  @Test
  public void encryptionIsPossibleForLargeKey() {
    key += 3000;
    assertEquals(MSG, decryption($, key));
  }
  
  @Test
  public void unmatchingKeyDoesNotDecrypt() {
    int key2 = Math.abs(new Random().nextInt(95)) + 1 + key;
    assertNotEquals(key, key2);
    assertNotEquals(MSG, $.decrypt($.encrypt(MSG, key), key2));
  }
}
