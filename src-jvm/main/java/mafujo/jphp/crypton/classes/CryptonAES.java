package mafujo.jphp.crypton.classes;

import mafujo.jphp.crypton.CryptonExtension;
import php.runtime.annotation.Reflection;
import php.runtime.env.Environment;
import php.runtime.lang.BaseObject;
import php.runtime.reflection.ClassEntity;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Reflection.Namespace(CryptonExtension.NS)
@Reflection.Name("CryptonAES")
public class CryptonAES extends BaseObject
{
    public CryptonAES(Environment env) {
        super(env);
    }

    protected CryptonAES(ClassEntity entity) {
        super(entity);
    }

    public CryptonAES(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }



    private final int KEY_SIZE = 128;
    private final int DATA_LENGTH = 128;


    private SecretKey __keySecret;

    protected Cipher __encryptCipher;
    protected Cipher __decryptCipher;


    @Reflection.Signature
    public void __construct()
            throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(KEY_SIZE);
        this.__keySecret = keyGenerator.generateKey();

        this.__construct(this.getSecretKey());
    }

    @Reflection.Signature
    public void __construct(String secretKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        if(this.__keySecret == null)
        {
            this.__keySecret = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "AES");
        }

        this.__encryptCipher = Cipher.getInstance("AES/ECB/NoPadding");
        this.__encryptCipher.init(Cipher.ENCRYPT_MODE, this.__keySecret);

        this.__decryptCipher = Cipher.getInstance("AES/ECB/NoPadding");
        this.__decryptCipher.init(Cipher.DECRYPT_MODE, this.__keySecret);
    }

    @Reflection.Signature
    public String getSecretKey()
    {
        return Base64.getEncoder().encodeToString(this.__keySecret.getEncoded());
    }





    @Reflection.Signature
    public void encryptFile(String sourcePath, String resultPath)
            throws  IOException
    {
        File sourceFile = new File(sourcePath);
        File resultFile = new File(resultPath);

        InputStream inputStream = new FileInputStream(sourceFile);
        OutputStream outputStream = new FileOutputStream(resultFile);
        this.encryptStream(inputStream, outputStream);

        inputStream.close();
        outputStream.close();
    }

    @Reflection.Signature
    public void decryptFile(String sourcePath, String resultPath)
            throws IOException
    {
        File sourceFile = new File(sourcePath);
        File resultFile = new File(resultPath);

        InputStream inputStream = new FileInputStream(sourceFile);
        OutputStream outputStream = new FileOutputStream(resultFile);
        this.decryptStream(inputStream, outputStream);

        inputStream.close();
        outputStream.close();
    }

    protected void encryptStream(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, this.__encryptCipher);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0)
        {
            cipherOutputStream.write(buffer, 0, length);
        }
        cipherOutputStream.close();
    }

    protected void decryptStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, this.__decryptCipher);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = cipherInputStream.read(buffer)) > 0)
        {
            outputStream.write(buffer, 0, length);
        }
        cipherInputStream.close();
    }
}
