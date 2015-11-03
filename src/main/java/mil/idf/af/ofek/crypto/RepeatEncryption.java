package mil.idf.af.ofek.crypto;

public class RepeatEncryption implements EncryptionAlgorithm {
  private final EncryptionAlgorithm ea;
  private final Integer             numCrypt;
  
  public RepeatEncryption(EncryptionAlgorithm algo, int num) {
    ea = algo;
    numCrypt = num;
  }
  
  @Override
  public String encrypt(String msg, int key) {
    String midString = msg;
    for (int i = 0; i < numCrypt; ++i)
      midString = ea.encrypt(midString, key);
    
    return midString;
  }
  
  @Override
  public String decrypt(String cypher, int key) {
    String midString = cypher;
    for (int i = 0; i < numCrypt; ++i)
      midString = ea.decrypt(midString, key);
    
    return midString;
  }
  
  @Override
  public int getKeyStrength() {
    return ea.getKeyStrength();
  }
  
  @Override
  public String getName() {
    return numCrypt.toString() + " times " + ea.getName();
  }
  
  @Override
  public RepeatEncryption clone() throws CloneNotSupportedException {
    return (RepeatEncryption) super.clone();
  }
}
