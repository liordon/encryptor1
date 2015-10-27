package mil.idf.af.ofek.crypto;

import static mil.idf.af.ofek.TestResources.*;
import static org.junit.Assert.*;
import mil.idf.af.ofek.crypto.XorEncryption;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class XorEncryptionTest extends EncryptionAlgorithmTest{
  private static final int KEY = 3;
  private XorEncryption $ = new XorEncryption();
  
  @Before
  public void setUp() throws Exception {
  }
  
  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public void xorEncryptionIsSameLengthAsOriginalMsg() {
    assertEquals(MSG.length(), $.encrypt(MSG, KEY).length());
  }
  
  @Test
  public void xorEncryptionIsReversible() {
    assertEquals(MSG, decryption($, KEY));
  }
  
  @Test
  public void negativeKeyIsAcceptable() {
    assertEquals(MSG, decryption($, -KEY));
  }
}
