package ofek.crypto;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class EncryptionAlgorithmComparatorTest {
  private EncryptionAlgorithmComparator $ = new EncryptionAlgorithmComparator();
  EncryptionAlgorithm mea1 = mock(EncryptionAlgorithm.class);
  EncryptionAlgorithm mea2 = mock(EncryptionAlgorithm.class);
  int str = new Random().nextInt(200);

  @Before
  public void setup(){
    when(mea1.getKeyStrength()).thenReturn(str);
    when(mea2.getKeyStrength()).thenReturn(str);
  }
  
  @Test
  public void anyEncryptionAlgorithmShouldEqualItself() {
    assertEquals(0, $.compare(mea1, mea1));
  }
  
  @Test
  public void differentButSameStrengthAlgorithmsShouldEqualEachOther(){
    assertEquals(0, $.compare(mea1, mea2));
  }
  
  @Test
  public void ifFirstAlgorithmIsStrongerResultIsPositive(){
    when(mea2.getKeyStrength()).thenReturn(str+1);
    assert(0 < $.compare(mea2, mea1));
  }
  
  @Test
  public void ifFirstAlgorithmIsWeakerResultIsNegative(){
    when(mea2.getKeyStrength()).thenReturn(str-1);
    assert(0 > $.compare(mea2, mea1));
  }
}
