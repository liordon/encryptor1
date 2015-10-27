package mil.idf.af.ofek.crypto;

public class DoubleEncryption implements EncryptionAlgorithm {
  private final EncryptionAlgorithm ea;
  
  public DoubleEncryption(EncryptionAlgorithm algo) {
    ea = algo;
  }
  
  /**
   * this function exists mostly for compatibility purposes. it performs the
   * second encryption with a key of twice the original value.
   */
  @Override
  public String encrypt(String msg, int key) {
    return ea.encrypt(ea.encrypt(msg, key), key + 1);
  }
  
  /**
   * this function exists mostly for compatibility purposes. it performs the
   * first decryption with a key of twice the original value.
   */
  @Override
  public String decrypt(String cypher, int key) {
    return ea.decrypt(ea.decrypt(cypher, key), key + 1);
  }
  
  public String encrypt(String msg, int key1, int key2) {
    return ea.encrypt(ea.encrypt(msg, key1), key2);
  }
  
  public String decrypt(String cypher, int key1, int key2) {
    return ea.decrypt(ea.decrypt(cypher, key2), key1);
  }
  
  @Override
  public int getKeyStrength() {
    return ea.getKeyStrength();
  }
  
  @Override
  public String getName() {
    return "double " + ea.getName();
  }
}
