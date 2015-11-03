package mil.idf.af.ofek.crypto;

public class XorEncryption implements EncryptionAlgorithm {
  
  @Override
  public String encrypt(String msg, int key) {
    StringBuilder $ = new StringBuilder(msg.length());
    for (char c : msg.toCharArray()) {
      $.append((char) (c ^ key));
    }
    return $.toString();
  }
  
  @Override
  public String decrypt(String cypher, int key) {
    return encrypt(cypher, key);
  }
  
  @Override
  public int getKeyStrength() {
    return 3;
  }
  
  @Override
  public String getName() {
    return "Xor encryption";
  }
  
  @Override
  public XorEncryption clone() throws CloneNotSupportedException {
    return (XorEncryption) super.clone();
  }
}
