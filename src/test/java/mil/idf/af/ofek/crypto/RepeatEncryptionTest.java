package mil.idf.af.ofek.crypto;

import static mil.idf.af.ofek.TestResources.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Random;

import mil.idf.af.ofek.crypto.EncryptionAlgorithm;
import mil.idf.af.ofek.crypto.RepeatEncryption;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

public class RepeatEncryptionTest {
  private static final int KEY = 3;
  private static final int NUM_CRYPT = 5;
  private EncryptionAlgorithm mea;
  private RepeatEncryption $;
  
  @Before
  public void setUp() throws Exception {
    mea = mock(EncryptionAlgorithm.class);
    $ = new RepeatEncryption(mea,NUM_CRYPT);
  }
  
  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public void RepeatEncryptionCanRepeatAnyNumberOfTimes() {
    int rnd = Math.abs(new Random().nextInt(NUM_CRYPT));
    $ = new RepeatEncryption(mea,rnd);
    $.encrypt(MSG, KEY);
    
    verify(mea, times(rnd)).encrypt(Matchers.anyString(), Matchers.eq(KEY));
  }
  
  @Test
  public void RepeatDecryptionCanRepeatAnyNumberOfTimes() {
    int rnd = Math.abs(new Random().nextInt(NUM_CRYPT));
    $ = new RepeatEncryption(mea,rnd);
    $.decrypt(MSG, KEY);
    
    verify(mea, times(rnd)).decrypt(Matchers.anyString(), Matchers.eq(KEY));
  }
  
  @Test
  public void repeatEncryptionHasSameKeyStrengthAsInnerAlgorithm(){
    when(mea.getKeyStrength()).thenReturn(new Random().nextInt());
    assertEquals(mea.getKeyStrength(),$.getKeyStrength());
  }
  
}
