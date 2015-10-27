package ofek.crypto;

import static org.junit.Assert.*;
import static ofek.TestResources.*;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ShiftMultiplyEncryptionTest extends EncryptionAlgorithmTest{
  private static final ShiftMultiplyEncryption $ = new ShiftMultiplyEncryption();
  private int key;
  
  @Before
  public void setUp(){
    key = Math.abs(new Random().nextInt())+1;
  }
  
  @Test
  public void decryptionReversesEncryption() {
    assertEquals(MSG, decryption($,key));
  }
  
  @Test
  public void negativeKeyIsValid() {
    assertEquals(MSG, decryption($,-key));
  }
  
  @Test
  public void unitEncryptionIsMeaningless() {
    assertEquals(MSG, encryption($,1));
  }
  
  @Test
  public void nonUnitEncryptionIsDifferentFromOrigin() {
    assertNotEquals(MSG, encryption($, key+1));
  }
  
  @Test
  public void encryptionIsPossibleForLargeKey() {
    key+=3000;
    assertEquals(MSG, decryption($, key));
  }
  
  @Test
  public void unmatchingKeyDoesNotDecrypt(){
    int key2 = Math.abs(new Random().nextInt())+1;
    if ( key2 != key )
      assertNotEquals(MSG, $.decrypt(encryption($, key),  key2));
  }
}
