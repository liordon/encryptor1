package ofek.crypto;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*; 
import static ofek.TestResources.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

public class DoubleEncryptionTest extends EncryptionAlgorithmTest{
  private static int KEY1 = 1;
  private static int KEY2 = 2;
  private EncryptionAlgorithm mea;
  private DoubleEncryption $;
  
  @Before
  public void setUp() throws Exception {
    mea = mock(EncryptionAlgorithm.class);
    $ = new DoubleEncryption(mea);
  }
  
  @Test
  public void doubleEncryptionIsInItselfAnEncryptionAlgorithm() {
    @SuppressWarnings("unused")
    EncryptionAlgorithm ea = $;
  }
  
  @Test
  public void doubleEncryptionCallsArgumentAlgorithmTwice(){
    encryption($, KEY1);
    verify(mea, times(2)).encrypt(Matchers.anyString(), Matchers.anyInt());
  }
  
  @Test
  public void doubleDecryptionCallsArgumentAlgorithmTwice(){
    decryption($, KEY1);
    verify(mea, times(2)).decrypt(Matchers.anyString(), Matchers.anyInt());
  }
  
  @Test
  public void doubleEncryptionWithDifferentKeysUsesBothKeys(){
    $.encrypt(MSG, KEY1, KEY2);
    verify(mea, times(1)).encrypt(Matchers.anyString(), Matchers.eq(KEY1));
    verify(mea, times(1)).encrypt(Matchers.anyString(), Matchers.eq(KEY2));
  }
  
  @Test
  public void doubleDecryptionWithDifferentKeysUsesBothKeys(){
    $.decrypt(MSG, KEY1, KEY2);
    verify(mea, times(1)).decrypt(Matchers.anyString(), Matchers.eq(KEY1));
    verify(mea, times(1)).decrypt(Matchers.anyString(), Matchers.eq(KEY2));
  }
  
  @Test
  public void doubleEncryptionReversesInRightOrder(){
    
    //encryption with each key appends key
    when(mea.encrypt(MSG,KEY1)).thenReturn(MSG+"1");
    when(mea.encrypt(MSG+"2", KEY1)).thenReturn(MSG+"2"+"1");
    when(mea.encrypt(MSG, KEY2)).thenReturn(MSG+"2");
    when(mea.encrypt(MSG+"1", KEY2)).thenReturn(MSG+"1"+"2");
    
    //decryption with each key removes it from the end
    when(mea.decrypt(MSG+"1",KEY1)).thenReturn(MSG);
    when(mea.decrypt(MSG+"2"+"1", KEY1)).thenReturn(MSG+"2");
    when(mea.decrypt(MSG+"2", KEY2)).thenReturn(MSG);
    when(mea.decrypt(MSG+"1"+"2", KEY2)).thenReturn(MSG+"1");
    
    String cypher = $.encrypt(MSG, KEY1, KEY2);
    String plainText = $.decrypt(cypher, KEY1, KEY2);
    
    //true only if keys were removed in correct order
    assertEquals(MSG, plainText);
  }
  
  @Test
  public void doubleEncryptionHasSameKeyStrengthAsInnerAlgorithm(){
    when(mea.getKeyStrength()).thenReturn(new Random().nextInt());
    assertEquals(mea.getKeyStrength(),$.getKeyStrength());
  }
  
}
