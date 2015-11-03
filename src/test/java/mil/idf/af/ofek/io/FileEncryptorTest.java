package mil.idf.af.ofek.io;

import static mil.idf.af.ofek.TestResources.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Random;

import mil.idf.af.ofek.FileEncryptor;
import mil.idf.af.ofek.crypto.EncryptionAlgorithm;
import mil.idf.af.ofek.crypto.ShiftUpEncryption;
import mil.idf.af.ofek.logs.EncryptionEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;

public class FileEncryptorTest {
  private Integer                   key;
  private final FileInteractor      interactor = mock(FileInteractor.class);
  private final EncryptionAlgorithm algorithm  = mock(ShiftUpEncryption.class); // mock(EncryptionAlgorithm.class);
  private final FileEncryptor       $          = new FileEncryptor(algorithm,
                                                   mock(EncryptionEvent.class),
                                                   interactor);
  
  @Before
  public void setUp() throws Exception {
    key = Math.abs(new Random().nextInt());
    when(interactor.readFromFile(KEY_FILE)).thenReturn(key.toString());
    when(interactor.readFromFile(PLAIN_FILE)).thenReturn(MSG);
    when(interactor.readFromFile(ENCRYPTED_FILE)).thenReturn(FAKE_CYPHER);
    when(algorithm.encrypt(MSG, key)).thenReturn(FAKE_CYPHER);
    when(algorithm.decrypt(FAKE_CYPHER, key)).thenReturn(MSG);
  }
  
  @Rule
  public ExpectedException illegalTarget = ExpectedException.none();
  
  @Test
  public void encryptionAndDecryptionUseArgumentAlgorithm() throws IOException {
    $.encryptFile(PLAIN_FILE, KEY_FILE, ENCRYPTED_FILE);
    verify(algorithm).encrypt(MSG, key);
    
    $.decryptFile(ENCRYPTED_FILE, KEY_FILE, DECRYPTED_FILE);
    verify(algorithm).decrypt(FAKE_CYPHER, key);
  }
  
  @Test
  public void encryptionAndDecryptionStoreResultsInSpecifiedFiles()
      throws NumberFormatException, IOException {
    $.encryptFile(PLAIN_FILE, KEY_FILE, ENCRYPTED_FILE);
    verify(interactor).writeToFile(FAKE_CYPHER, ENCRYPTED_FILE);
    
    $.decryptFile(ENCRYPTED_FILE, KEY_FILE, DECRYPTED_FILE);
    verify(interactor).writeToFile(MSG, DECRYPTED_FILE);
  }
  
  @Test
  public void multipleEncryptionsAreFunctional() throws IOException {
    $.encryptFiles(PLAIN_FILES, KEY_FILE, CRYPT_FILES);
    for (int i = 0; PLAIN_FILES.length > i; ++i) {
      verify(interactor).readFromFile(PLAIN_FILES[i]);
      verify(interactor).writeToFile(Matchers.anyString(),
          Matchers.matches(CRYPT_FILES[i]));
    }
  }
  
  @Test
  public void multipleDecryptionsAreFunctional() throws IOException {
    $.decryptFiles(CRYPT_FILES, KEY_FILE, DECRYPT_FILES);
    for (int i = 0; PLAIN_FILES.length > i; ++i) {
      verify(interactor).readFromFile(CRYPT_FILES[i]);
      verify(interactor).writeToFile(Matchers.anyString(),
          Matchers.matches(DECRYPT_FILES[i]));
    }
  }
}
