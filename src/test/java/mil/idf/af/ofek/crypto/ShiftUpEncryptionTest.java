package mil.idf.af.ofek.crypto;

import static mil.idf.af.ofek.TestResources.*;
import static org.junit.Assert.*;

import java.util.Random;

import mil.idf.af.ofek.crypto.ShiftUpEncryption;

import org.junit.Test;

public class ShiftUpEncryptionTest extends EncryptionAlgorithmTest {
  private static final int KEY = 3;
  private static final int NEG_KEY = -3;
  private static final int LARGE_KEY = 61420;
  private static ShiftUpEncryption $ = new ShiftUpEncryption();
  

  
  @Test
  public void zeroEncryptionIsMeaningless(){
    assertEquals(MSG,encryption($,0));
  }
  
  @Test
  public void encryptedMsgIsDifferentThanOriginal() {
    assertNotEquals(MSG,encryption($,KEY));
  }
  
  @Test
  public void sameKeyDecryptionReversesEncryption() {
    assertEquals(MSG,decryption($,KEY));
  }
  
  @Test
  public void bigKeyEncryptionIsStillReversible(){
    assertEquals(MSG, decryption($,LARGE_KEY));
  }
  
  @Test
  public void negativeKeyEncryptionIsValid(){
    assertEquals(MSG, decryption($,NEG_KEY));
  }
  
  @Test
  public void unmatchingKeyDoesNotDecrypt(){
    int key2 = Math.abs(new Random().nextInt())+1;
    if ( key2 != KEY )
      assertNotEquals(MSG, $.decrypt(encryption($, KEY),  key2));
  }
  
  @Test
  public void bug1_aDisappearsForKey29(){
    int key = 29;
    String newMsg = $.encrypt(MSG, key);
    assertEquals(MSG.length(), newMsg.length());
    assertEquals(MSG, $.decrypt(newMsg, key));
  }
}
