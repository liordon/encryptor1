package mil.idf.af.ofek.crypto;

public abstract class CharToCharEncryptor implements EncryptionAlgorithm {
  protected static final int FIRST_CHAR = 29;
  protected static final int NUM_CHARS  = 97; // for multuply encryption this
                                              // must be prime.
                                              
  @Override
  public String encrypt(String msg, int key) {
    StringBuilder $ = new StringBuilder(msg.length());
    for (char c : msg.toCharArray()) {
      int enc = c - FIRST_CHAR;
      if (0 <= enc && NUM_CHARS > enc)
        enc = encryptionMorph((c - FIRST_CHAR), key);
      $.append((char) (enc + FIRST_CHAR));
    }
    return $.toString();
  }
  
  @Override
  public String decrypt(String cypher, int key) {
    StringBuilder $ = new StringBuilder(cypher.length());
    for (char c : cypher.toCharArray()) {
      int dec = c - FIRST_CHAR;
      if (0 <= dec && NUM_CHARS > dec)
        dec = decryptionMorph((c - FIRST_CHAR), key);
      $.append((char) (dec + FIRST_CHAR));
    }
    return $.toString();
  }
  
  /**
   * this function encrypts a single character from a plaintext. the input and
   * output should be in modulu 94.
   * 
   * @param charVal
   *          - an int signifying the place of a character in the ascii table. 0
   *          stands for [space] while 94 stands for ~
   * @param key
   *          - the encryption key
   * @return - an int signifying the place of the chypertext character in the
   *         ascii table.
   */
  protected abstract int encryptionMorph(int charVal, int key);
  
  /**
   * does the reverse operation to encryptionMorph.
   * 
   * @param charVal
   *          - an int signifying the place of a character in the ascii table. 0
   *          stands for [space] while 94 stands for ~
   * @param key
   *          - the decryption key
   * @return - an int signifying the place of the plaintext character in the
   *         ascii table.
   */
  protected abstract int decryptionMorph(int charVal, int key);
  
  @Override
  public int getKeyStrength() {
    return 2;
  }
  
  @Override
  public EncryptionAlgorithm clone() throws CloneNotSupportedException {
    return (EncryptionAlgorithm) super.clone();
  }
}
