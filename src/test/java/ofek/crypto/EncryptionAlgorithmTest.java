package ofek.crypto;

import static ofek.TestResources.MSG;

public class EncryptionAlgorithmTest {

  protected String encryption(EncryptionAlgorithm underInspection, int key){
    return underInspection.encrypt(MSG, key);
  }
  
  protected String decryption(EncryptionAlgorithm underInspection, int key){
    return underInspection.decrypt(encryption(underInspection, key), key);
  } 
}
