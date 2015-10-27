package mil.idf.af.ofek.logs;

import static mil.idf.af.ofek.TestResources.*;
import static org.junit.Assert.*;
import static java.lang.Math.abs;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import mil.idf.af.ofek.logs.EncryptionEvent;
import mil.idf.af.ofek.logs.EncryptionEventArgs;
import mil.idf.af.ofek.logs.IllegalEncryptionEventException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EncryptionEventTest implements Observer{
  private EncryptionEvent $;
  private EncryptionEventArgs eea;
  private static final int TIME_DELTA = 2;
  
  @Override
  public void update(Observable o, Object arg) {
      eea = (EncryptionEventArgs) arg;
  }
  
  @Rule
  public ExpectedException illegalOrder = ExpectedException.none(); 
  
  @Before
  public void setup() {
    $ = new EncryptionEvent();
    $.addObserver(this);
  }
  
  @Test
  public void encryptionAndDecryptionEventsReturnsSpecifiedParameters() {
    $.encryptionStarted(PLAIN_FILE, ENCRYPTED_FILE, ALGORITHM);
    $.encryptionEnded();
    assertEquals(PLAIN_FILE, eea.getSourceFile());
    assertEquals(ENCRYPTED_FILE, eea.getOutputFile());
    assertEquals(ALGORITHM, eea.getAlgorithm());
    
    $.decryptionStarted(PLAIN_FILE, ENCRYPTED_FILE, ALGORITHM);
    $.decryptionEnded();
    assertEquals(PLAIN_FILE, eea.getSourceFile());
    assertEquals(ENCRYPTED_FILE, eea.getOutputFile());
    assertEquals(ALGORITHM, eea.getAlgorithm());
  }
  
  @Test
  public void encryptionEventsAreTimedCorrectly() throws InterruptedException {
    int time = new Random().nextInt(300);
    $.encryptionStarted(PLAIN_FILE, ENCRYPTED_FILE, ALGORITHM);
    Thread.sleep(time);
    $.encryptionEnded();
    
    assert( TIME_DELTA >= abs(eea.getTimeSpent()-time) );
  }
  
  @Test
  public void subsequentEncryptionEventsThrowException(){
    $.encryptionStarted(PLAIN_FILE, ENCRYPTED_FILE, ALGORITHM);
    illegalOrder.expect(IllegalEncryptionEventException.class);
    $.encryptionStarted(PLAIN_FILE, ENCRYPTED_FILE, ALGORITHM);
  }
  
  @Test
  public void endingEncryptionWithoutStartingItThrowsException(){
    illegalOrder.expect(IllegalEncryptionEventException.class);
    $.encryptionEnded();
  }
  
  @Test
  public void subsequentDecryptionEventsThrowException(){
    $.decryptionStarted(PLAIN_FILE, ENCRYPTED_FILE, ALGORITHM);
    illegalOrder.expect(IllegalEncryptionEventException.class);
    $.decryptionStarted(PLAIN_FILE, ENCRYPTED_FILE, ALGORITHM);
  }
  
  @Test
  public void endingDecryptionWithoutStartingItThrowsException(){
    illegalOrder.expect(IllegalEncryptionEventException.class);
    $.decryptionEnded();
  }
}
