package mil.idf.af.ofek.io;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static mil.idf.af.ofek.TestResources.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observer;

import mil.idf.af.ofek.crypto.EncryptionAlgorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

public class FileEncryptorFactoryTest {
  private final EncryptionAlgorithm  algo     = mock(EncryptionAlgorithm.class);
  private final Observer             observer = mock(Observer.class);
  private final FileEncryptorFactory $        = new FileEncryptorFactory(algo,
                                                  observer);
  
  @Before
  public void setUp() throws Exception {}
  
  @After
  public void tearDown() throws Exception {
    Files.deleteIfExists(Paths.get(ENCRYPTED_FILE));
  }
  
  @Test
  public void canReturnFileEncryptor() {
    assert (null != $.build());
  }
  
  @Test
  public void returnedEncryptorDoesNotUseSameAlgorithmObject()
      throws CloneNotSupportedException {
    EncryptionAlgorithm newmock = mock(EncryptionAlgorithm.class);
    when(newmock.encrypt(MSG, 42)).thenReturn(MSG);
    when(algo.clone()).thenReturn(newmock);
    try {
      $.build().encryptFile(PLAIN_FILE + ".txt", PREEXISTING_KEY,
          PLAIN_FILE + ".txt");
    } catch (IOException e) {
      e.printStackTrace();
      fail("file Interactor Failed The Test");
    }
    verify(algo).clone();
    verify(algo, never()).encrypt(Matchers.anyString(), Matchers.anyInt());
    verify(newmock).encrypt(Matchers.anyString(), Matchers.anyInt());
  }
}
