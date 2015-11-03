package mil.idf.af.ofek.crypto;

import static mil.idf.af.ofek.TestResources.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Random;

import mil.idf.af.ofek.crypto.DoubleEncryption;
import mil.idf.af.ofek.crypto.EncryptionAlgorithm;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

public class DoubleEncryptionTest extends EncryptionAlgorithmTest {
  private static int          KEY1 = 1;
  private static int          KEY2 = 2;
  private EncryptionAlgorithm mockAlgo;
  private DoubleEncryption    $;
  
  @Before
  public void setUp() throws Exception {
    mockAlgo = mock(EncryptionAlgorithm.class);
    $ = new DoubleEncryption(mockAlgo);
  }
  
  @Test
  public void doubleEncryptionIsInItselfAnEncryptionAlgorithm() {}
  
  @Test
  public void doubleEncryptionCallsArgumentAlgorithmTwice() {
    encryption($, KEY1);
    verify(mockAlgo, times(2)).encrypt(Matchers.anyString(), Matchers.anyInt());
  }
  
  @Test
  public void doubleDecryptionCallsArgumentAlgorithmTwice() {
    decryption($, KEY1);
    verify(mockAlgo, times(2)).decrypt(Matchers.anyString(), Matchers.anyInt());
  }
  
  @Test
  public void doubleEncryptionWithDifferentKeysUsesBothKeys() {
    $.encrypt(MSG, KEY1, KEY2);
    verify(mockAlgo, times(1)).encrypt(Matchers.anyString(), Matchers.eq(KEY1));
    verify(mockAlgo, times(1)).encrypt(Matchers.anyString(), Matchers.eq(KEY2));
  }
  
  @Test
  public void doubleDecryptionWithDifferentKeysUsesBothKeys() {
    $.decrypt(MSG, KEY1, KEY2);
    verify(mockAlgo, times(1)).decrypt(Matchers.anyString(), Matchers.eq(KEY1));
    verify(mockAlgo, times(1)).decrypt(Matchers.anyString(), Matchers.eq(KEY2));
  }
  
  @Test
  public void doubleEncryptionReversesInRightOrder() {
    // encryption with each key appends key
    when(mockAlgo.encrypt(MSG, KEY1)).thenReturn(MSG + "1");
    when(mockAlgo.encrypt(MSG + "1", KEY2)).thenReturn(MSG + "1" + "2");
    
    // decryption with each key removes it from the end
    when(mockAlgo.decrypt(MSG + "1", KEY1)).thenReturn(MSG);
    when(mockAlgo.decrypt(MSG + "1" + "2", KEY2)).thenReturn(MSG + "1");
    
    String cypher = $.encrypt(MSG, KEY1, KEY2);
    verify(mockAlgo).encrypt(MSG, KEY1);
    verify(mockAlgo).encrypt(MSG + "1", KEY2);
    String plainText = $.decrypt(cypher, KEY1, KEY2);
    verify(mockAlgo).decrypt(MSG + "1" + "2", KEY2);
    verify(mockAlgo).decrypt(MSG + "1", KEY1);
    
    // true only if keys were removed in correct order
    assertEquals(MSG, plainText);
  }
  
  @Test
  public void doubleEncryptionHasSameKeyStrengthAsInnerAlgorithm() {
    when(mockAlgo.getKeyStrength()).thenReturn(new Random().nextInt());
    assertEquals(mockAlgo.getKeyStrength(), $.getKeyStrength());
  }
  
}
