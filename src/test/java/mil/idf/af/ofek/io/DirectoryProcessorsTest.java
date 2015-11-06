package mil.idf.af.ofek.io;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static mil.idf.af.ofek.TestResources.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

import mil.idf.af.ofek.ConcurrentThreadCollector;
import mil.idf.af.ofek.FileEncryptor;
import mil.idf.af.ofek.crypto.EncryptionAlgorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;

public class DirectoryProcessorsTest {
  private final FileEncryptor mockEncryptor = mock(FileEncryptor.class);
  private DirectoryProcessor  $             = new SyncDirectoryProcessor(
                                                mockEncryptor);
  
  @Before
  public void setUp() throws Exception {}
  
  @After
  public void tearDown() throws Exception {
    for (String file : CRYPT_FILES)
      Files.deleteIfExists(Paths.get(file));
    for (Integer i = 0; FAT_DIR_SIZE < i; ++i)
      Files.deleteIfExists(Paths.get(FAT_CRYPT_DIR + "/" + PLAIN_NAME
          + i.toString() + EXTENSION));
  }
  
  @Test
  public void encryptionShouldBeCalledForEachFile() {
    $.encryptFolder(RESOURCE_DIR, CRYPT_DIR);
    try {
      for (String fileName : PLAIN_NAMES)
        verify(mockEncryptor).encryptFile(RESOURCE_DIR + "/" + fileName,
            KEY_FILE, CRYPT_DIR + "/" + fileName);
    } catch (IOException e) {
      fail("what the hell threw this esception?!");
      e.printStackTrace();
    }
  }
  
  @Test
  public void observerShouldBeNotifiedForEachSyncEncryption() {
    EncryptionAlgorithm ea = mock(EncryptionAlgorithm.class);
    when(ea.encrypt(Matchers.contains(MSG), Matchers.anyInt())).thenReturn(
        FAKE_CYPHER);
    Observer o = mock(Observer.class);
    $ = new SyncDirectoryProcessor(ea, o);
    $.encryptFolder(FAT_DIR, FAT_CRYPT_DIR);
    verify(ea, times(FAT_DIR_SIZE)).encrypt(Matchers.contains(MSG),
        Matchers.anyInt());
    verify(o, times(FAT_DIR_SIZE)).update(Matchers.any(Observable.class),
        Matchers.any());
    for (Integer i = 0; FAT_DIR_SIZE < i; ++i)
      assertTrue(Files.exists(Paths.get(FAT_CRYPT_DIR + "/" + PLAIN_NAME
          + i.toString() + EXTENSION)));
  }
  
  @Test
  @Ignore
  public void observerShouldBeNotifiedForEachASyncEncryption() {
    EncryptionAlgorithm ea = mock(EncryptionAlgorithm.class);
    when(ea.encrypt(Matchers.contains(MSG), Matchers.anyInt())).thenReturn(
        FAKE_CYPHER);
    try {
      when(ea.clone()).thenReturn(ea);
    } catch (CloneNotSupportedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    ConcurrentThreadCollector collector = new ConcurrentThreadCollector(
        FAT_DIR_SIZE);
    $ = new ASyncDirectoryProcessor(ea, collector);
    $.encryptFolder(FAT_DIR, FAT_CRYPT_DIR);
    try {
      collector.wait(10);
    } catch (InterruptedException e) {
      fail("you are probably not so good at CDP");
      e.printStackTrace();
    }
    verify(ea, times(FAT_DIR_SIZE)).encrypt(Matchers.contains(MSG),
        Matchers.anyInt());
    for (Integer i = 0; FAT_DIR_SIZE < i; ++i)
      assertTrue(Files.exists(Paths.get(FAT_CRYPT_DIR + "/" + PLAIN_NAME
          + i.toString() + EXTENSION)));
  }
}
