package mafujo.jphp.crypton.classes;

import mafujo.jphp.crypton.CryptonExtension;
import php.runtime.annotation.Reflection;
import php.runtime.env.Environment;
import php.runtime.lang.BaseObject;
import php.runtime.reflection.ClassEntity;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
@Reflection.Name("CryptonRSA2")

public class CryptonRSA2 extends CryptonAES
{
    public CryptonRSA2(Environment env) {
        super(env);
    }

    protected CryptonRSA2(ClassEntity entity) {
        super(entity);
    }

    public CryptonRSA2(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    private KeyFactory __keyFactory;
    private KeyPair __keyPair;
    private PublicKey __keyPublic;
    private PrivateKey __keyPrivate;




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
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        this.__keyFactory = KeyFactory.getInstance("RSA");

        if(this.__keyPublic == null)
        {
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            this.__keyPublic = this.__keyFactory.generatePublic(publicKeySpec);
        }

        this.__encryptCipher = Cipher.getInstance("RSA");
        this.__encryptCipher.init(Cipher.ENCRYPT_MODE, this.__keyPublic);

    }
    @Reflection.Signature
    public void __construct(String publicKey, String privateKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException
    {
        this.__construct(publicKey);

        if(this.__keyPrivate == null)
        {
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            this.__keyPrivate = this.__keyFactory.generatePrivate(privateKeySpec);
        }


        this.__decryptCipher = Cipher.getInstance("RSA");
        this.__decryptCipher.init(Cipher.DECRYPT_MODE, this.__keyPrivate);
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

}
