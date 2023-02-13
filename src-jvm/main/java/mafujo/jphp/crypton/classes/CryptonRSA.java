package mafujo.jphp.crypton.classes;

import mafujo.jphp.crypton.CryptonExtension;
import php.runtime.annotation.Reflection;
import php.runtime.env.Environment;
import php.runtime.lang.BaseObject;
import php.runtime.reflection.ClassEntity;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Reflection.Namespace(CryptonExtension.NS)
@Reflection.Name("CryptonRSA")
public class CryptonRSA extends BaseObject
{
    public CryptonRSA(Environment env) {
        super(env);
    }

    protected CryptonRSA(ClassEntity entity) {
        super(entity);
    }

    public CryptonRSA(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    private KeyFactory __keyFactory;
    private KeyPair __keyPair;
    private PublicKey __keyPublic;
    private PrivateKey __keyPrivate;


    private Cipher __encryptCipher;
    private Cipher __decryptCipher;

    @Reflection.Signature
    public void __construct()
            throws NoSuchAlgorithmException
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        this.__keyPair = keyPairGenerator.generateKeyPair();

        this.__keyPublic = this.__keyPair.getPublic();
        this.__keyPrivate = this.__keyPair.getPrivate();

        try
        {
            this.__construct(this.getPublicKey(), this.getPrivateKey());
        }
        catch (NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException e)
        {
            throw new RuntimeException(e);
        }

    }

    @Reflection.Signature
    public void __construct(String publicKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException
    {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        if(this.__keyPublic == null)
        {
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            this.__keyPublic = keyFactory.generatePublic(publicKeySpec);
        }

        this.__encryptCipher = Cipher.getInstance("RSA");
        this.__encryptCipher.init(Cipher.WRAP_MODE, this.__keyPublic);

    }
    @Reflection.Signature
    public void __construct(String publicKey, String privateKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException
    {
        this.__construct(publicKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        if(this.__keyPrivate == null)
        {
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            //EncodedKeySpec privateKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            this.__keyPrivate = keyFactory.generatePrivate(privateKeySpec);
        }

        
        this.__decryptCipher = Cipher.getInstance("RSA");
        this.__decryptCipher.init(Cipher.UNWRAP_MODE, this.__keyPrivate);
    }



    @Reflection.Signature
    public String getPublicKey()
    {
        return Base64.getEncoder().encodeToString(this.__keyPair.getPublic().getEncoded());
    }
    @Reflection.Signature
    public  String getPrivateKey()
    {
        return Base64.getEncoder().encodeToString(this.__keyPair.getPrivate().getEncoded());
    }
    @Reflection.Signature
    public void encryptFile(String sourcePath)
    {
        this.encryptFile(sourcePath, sourcePath);
    }
    @Reflection.Signature
    public void encryptFile(String sourcePath, String resultPath)
    {
        try
        {
            this.fwrite(resultPath, this.__encryptCipher.doFinal(Files.readAllBytes(Paths.get(sourcePath))));
        }
        catch (IllegalBlockSizeException | BadPaddingException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Reflection.Signature
    public void decryptFile(String sourcePath)
    {
        this.decryptFile(sourcePath, sourcePath);
    }
    @Reflection.Signature
    public void decryptFile(String sourcePath, String resultPath)
    {
        try
        {
            this.fwrite(resultPath, this.__decryptCipher.doFinal(Files.readAllBytes(Paths.get(sourcePath))));
        }
        catch (IllegalBlockSizeException | BadPaddingException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void fwrite(String filePath, byte[] bytes)
    {
        try (FileOutputStream stream = new FileOutputStream(new File(filePath)))
        {
            stream.write(bytes);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Reflection.Signature
    public String encryptString(String string) throws IllegalBlockSizeException, BadPaddingException
    {
        return Base64.getEncoder().encodeToString(this.__encryptCipher.doFinal(string.getBytes(StandardCharsets.UTF_8)));
    }

    @Reflection.Signature
    public String decryptString(String string) throws IllegalBlockSizeException, BadPaddingException
    {
        return new String(this.__encryptCipher.doFinal(Base64.getDecoder().decode(string)), StandardCharsets.UTF_8);
    }


    @Reflection.Signature
    public String wrapAES(String secretKey) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException
    {
        return Base64.getEncoder().encodeToString(this.__encryptCipher.wrap(new SecretKeySpec(Base64.getDecoder().decode(secretKey), "AES")));
    }

    @Reflection.Signature
    public String unwrapAES(String wrapedKey) throws  NoSuchAlgorithmException, InvalidKeyException
    {
        return Base64.getEncoder().encodeToString(this.__decryptCipher.unwrap(Base64.getDecoder().decode(wrapedKey), "AES", Cipher.SECRET_KEY).getEncoded());
    }




}
