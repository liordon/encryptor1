package mil.idf.af.ofek.crypto;

import java.math.BigInteger;

public class ShiftMultiplyEncryption extends CharToCharEncryptor {
  
  private int fixKey(int key){
    int smallKey=key%NUM_CHARS;
    if(0 > smallKey){
      smallKey+=NUM_CHARS;
    }
    return smallKey;
  }
  
  @Override
  public String encrypt(String msg, int key) {
    return super.encrypt(msg, fixKey(key));
  }
  
  @Override
  public String decrypt(String cypher, int key) {
    int negKey = BigInteger.valueOf(fixKey(key))
	.modInverse(BigInteger.valueOf(NUM_CHARS)).intValue();
    return super.encrypt(cypher, negKey);
  }  

  @Override
  protected int encryptionMorph(int charVal, int key) {
    return (charVal*key) % NUM_CHARS;
  }

  @Override
  protected int decryptionMorph(int charVal, int negKey) {
    return (charVal*negKey) % NUM_CHARS;
  }
}
