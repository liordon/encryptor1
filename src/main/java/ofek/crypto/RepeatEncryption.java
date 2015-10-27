package ofek.crypto;


public class RepeatEncryption implements EncryptionAlgorithm {
  private EncryptionAlgorithm ea;
  private int numCrypt;

  public RepeatEncryption(EncryptionAlgorithm algo, int num) {
    ea = algo;
    numCrypt = num;
  }

  @Override
  public String encrypt(String msg, int key) {
    String midString = msg;
    for (int i=0 ; i < numCrypt ; ++i)
      midString = ea.encrypt(midString, key);
    
    return midString;
  }

  @Override
  public String decrypt(String cypher, int key) {
    String midString = cypher;
    for (int i=0 ; i < numCrypt ; ++i)
      midString = ea.decrypt(midString, key);
    
    return midString;
  }

  @Override
  public int getKeyStrength() {
    return ea.getKeyStrength();
  }
  
  
}
