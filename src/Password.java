import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Password {

	
	//Generamos el hash de la contraseņa
	public static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
		//numero de iteraciones de la funcion
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    //Generamos una salt random
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
    
    
    
    
    //Validamos la contraseņa
    public static boolean validatePassword(String originalPassword, String usuario) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
         try {
        	boolean esta = false;
        	String storedPassword = "";
        	FileReader f = new FileReader(new File(".//src//file//data.txt"));
        	BufferedReader fileInput=new BufferedReader(f);
			while(fileInput.ready() && !esta) {
				String readLine=fileInput.readLine();
				if(readLine.split(",")[0].equals(usuario)) {
					storedPassword = readLine.split(",")[1];
					esta = true;
				}
			}
			fileInput.close();
			f.close();
			if(!esta) {
				return false;
			}
			if(storedPassword.equals(" ")) {
				return true;
			}
            String[] parts = storedPassword.split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            //Generamos el hash de la contraseņa dada con el salt y numero de interacciones de la original ya guardada en el archivo
            PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] testHash = skf.generateSecret(spec).getEncoded();
                       
            int diff = hash.length ^ testHash.length;
            for(int i = 0; i < hash.length && i < testHash.length; i++) {
                   diff |= hash[i] ^ testHash[i];
            }

            return diff == 0;

         } catch (NumberFormatException | IOException e) {
            System.out.println("NumberFormatException validando contraseņa");
            return false;
         }
    }

    private static byte[] fromHex(String hex) {

        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++) {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }
}
