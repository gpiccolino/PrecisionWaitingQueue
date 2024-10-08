package com.precisionpos.tv.waitingqueue.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class EncryptString {
	private static EncryptString singleton;
	
	private Cipher ecipher;
    private Cipher dcipher;
     
    // 8-byte Salt
    byte[] salt = {
        (byte)0x39, (byte)0x41, (byte)0xD1, (byte)0xF5,
        (byte)0x9C, (byte)0x3A, (byte)0xDC, (byte)0x5F
    };
    
    // Iteration count
    private int iterationCount = 15;

    /**
     * Factory method to get the instance
     * @return
     */
    public static EncryptString getInstance() {
    	if(singleton == null) {
    		singleton = new EncryptString("%>dMp_4e_e*");
    	}
    	return singleton;
    }
    /**
     * Constructor with pass phrase
     * @param passPhrase
     */
    private EncryptString(String passPhrase) {
        try {
            // Create the key
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance(
                "PBEWithMD5AndDES").generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            // Create the ciphers
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (java.security.InvalidAlgorithmParameterException e) {
        } catch (java.security.spec.InvalidKeySpecException e) {
        } catch (javax.crypto.NoSuchPaddingException e) {
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (java.security.InvalidKeyException e) {
        }
    }
    
    public String encrypt(String str) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string            
            return Base64.encodeToString(enc, Base64.URL_SAFE);
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }    
    
    public String decrypt(String str) {
        try {
            // Decode base64 to get bytes
            byte[] dec = Base64.decode(str.getBytes(), Base64.URL_SAFE);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
        	e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
        	e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        } 
        return null;
    }


}