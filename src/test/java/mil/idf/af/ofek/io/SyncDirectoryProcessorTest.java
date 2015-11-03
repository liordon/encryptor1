package mil.idf.af.ofek.io;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static mil.idf.af.ofek.TestResources.*;

import java.io.IOException;

import mil.idf.af.ofek.FileEncryptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SyncDirectoryProcessorTest {
  private final FileEncryptor          mockEncryptor = mock(FileEncryptor.class);
  private final SyncDirectoryProcessor $             = new SyncDirectoryProcessor(
                                                         mockEncryptor);
  
  @Before
  public void setUp() throws Exception {}
  
  @After
  public void tearDown() throws Exception {}
  
  @Test
  public void encryptionShouldBeCalledForEachFile() {
    $.encryptFolder(RESOURCE_DIR, CRYPT_DIR);
    try {
      // verify(mockEncryptor).encryptFiles(PLAIN_FILES, KEY_FILE, CRYPT_FILES);
      for (String fileName : PLAIN_NAMES)
        verify(mockEncryptor).encryptFile(RESOURCE_DIR + fileName, KEY_FILE,
            CRYPT_DIR + fileName);
    } catch (IOException e) {
      fail("what the hell threw this esception?!");
      e.printStackTrace();
    }
  }
}
