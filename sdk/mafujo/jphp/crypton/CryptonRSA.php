<?php

namespace mafujo\jphp\crypton;

class CryptonRSA
{

    function __construct(string $keyPublic = null, string $keyPrivate = null)
    {
        
    }
    public function getPublicKey() : string {}
    public function getPrivateKey() : string {}
у
    public function encryptFile(string $sourcePath, string $resultPath = null) : void {}
    public function decryptFile(string $sourcePath, string $resultPath = null) : void {}

    public function encryptString(string $string) : string { }
    public function decryptString(string $string) : string { }

    public function wrapAES(string $secretKey) { }
    public function unwrapAES(string $wrapedKey) { }


}
