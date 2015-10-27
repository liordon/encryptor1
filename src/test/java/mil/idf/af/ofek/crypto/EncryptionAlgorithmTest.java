package mil.idf.af.ofek.crypto;

import static mil.idf.af.ofek.TestResources.MSG;
import mil.idf.af.ofek.crypto.EncryptionAlgorithm;

public class EncryptionAlgorithmTest {

  protected String encryption(EncryptionAlgorithm underInspection, int key){
    return underInspection.encrypt(MSG, key);
  }
  
  protected String decryption(EncryptionAlgorithm underInspection, int key){
    return underInspection.decrypt(encryption(underInspection, key), key);
  } 
}
