package mil.idf.af.ofek.crypto;

public interface EncryptionAlgorithm extends Cloneable {
  
  public String encrypt(String msg, int key);
  
  public String decrypt(String cypher, int key);
  
  public int getKeyStrength();
  
  public String getName();
  
  public EncryptionAlgorithm clone() throws CloneNotSupportedException;
}
