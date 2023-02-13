<?php

use mafujo\jphp\crypton\CryptonRSA;
use mafujo\jphp\crypton\CryptonAES;

use php\io\IOException;
use php\lang\Thread;


$cry = new CryptonRSA;
//var_dump($cry->getPrivateKey());
//var_dump($cry->getPublicKey());


//$privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDBU9tkKGewap/PDhWIOxPl1ahs0blVUi0ARVbQY10C2xNWJaTwyzZsQtp7+/L2AerzpYbnk7KaZQzKh0noZpKj3s/A1tuBDy67MAa3pVThfgZykAFl57swJZZcjRdyBGmblTkxWpwak9DJCIa8/PA/bir89q82UWy1+BUWaC6Jy+PGzvvbG86yCFlXhilp6c4viZpPnpzOIKHDR3zGWdPPm0LYXo/x/0DgL0B+NsFoGoV8Z/CRVMRo+J0eTN0R3uYsspQ2ZHEgaswKcLtpnwA0lP+398TzJh58q5fY2qwrP4WXDbzwIhmMrw3TuTEiGxxOdcTqHVWkyQ46cuomUDNzAgMBAAECggEASh6CUGYFSue2GUYHD1HHESjXB9FoQsPcqOvsmrxdnoirYy3PjNn8JYmEBmDUB2k40Oy3SfWgVf1D6CnyNf2NXvI/qGk3gsV1XyLMkH/8iO8uPaHkR1OOuqjOevFE+dujOZj+cmOQ0ce2Fj0Em1RGL6tnI9DX3/VbvnA8dXi/zugHtERw7hFl24NAItTnBLee3mU7WNe9Z2Yg8Yt8lItNCZgwgLdZdaC2/2bgAy4vKzntmkY+EusYYtWOY/8VCyyTahvLdnDW8EBcbEti30Of18CstoOh/vtksbt9IpscQoOddO36igbjWAVEKB+9pehlMmAqre/wqWlZ81YeznWgIQKBgQDrOcHSLJYg8supgMVH5IS8mnaX0eQArYToIFyZ1C3XuyRqeV5PhIP1WGNf3Wde5+NIY3/COol/o1ZjOTDOeoUVxJhqz5tH3HdXzmgKV5Kkk2aGjTO94BkhU3QhHZqIOGmP4/8Mci1fZthPnFYQi/1FUQOTUYeN/XYWZUxZXVZpywKBgQDSZtJ2yMPKGlphUwYlGJgJWYfgTBlh1jH2pjwRgf75ujiXcJaNhoDPmDOQKFueJrUfBLG6tNf9vMpgmJPtEWT0WGEEmQNDYMWJgHFGxqB4H5jWi0oa5TAKiZQ7mHbIlKH/eZlh+m0knCSp/OfJK95vTW72n4HN1aQ9QEX8ZERH+QKBgEOGD/KCZBJPQFGHKpqpqcTuXk7UrKvZI8byy0JJW2OLUDfUYVNYSf0bA2PYjbz7ug3kqh8aaLAgNkNDfpQ3LY8DIXG/6OZnDkdBdDJL00e1HIBRdmHyO6SKbVK4Sc0CzKLGo0dyWz4AquWnjjkvxmOojusyQ0RqVwS0zYb1uUGjAoGBAMBvpM1Z4KEnq0RasEMblGrwxTLjx09Rnk8OSeBlenSj2wC/zCNTQtscRQTztKAYnQ3quDehEfAUTTE+PAXXI/PMJagT3wUwLFwP6wslvj1AAQUxTqz8tAzfOoGrd/EK6IQGjsArUMnsOihzlPfNNNuuAfB1gT5myZnwAFLQzhbZAoGANkammBGExxuCvTgBqP1tJFCrCOYcy0hpM8c4iGi9wiRWQkGhFnB3hyJTthlXH9/9Sc1R6tLZiggtaN4Uu0qa6956NT85rkwwqk9Mf1WpRgh/tY2poOdGDWTUPvFukH7BottV6DpvP4UT86kiws27JJ2FcULNOt28tj66te51DKg=";
//$publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwVPbZChnsGqfzw4ViDsT5dWobNG5VVItAEVW0GNdAtsTViWk8Ms2bELae/vy9gHq86WG55OymmUMyodJ6GaSo97PwNbbgQ8uuzAGt6VU4X4GcpABZee7MCWWXI0XcgRpm5U5MVqcGpPQyQiGvPzwP24q/PavNlFstfgVFmguicvjxs772xvOsghZV4YpaenOL4maT56cziChw0d8xlnTz5tC2F6P8f9A4C9AfjbBaBqFfGfwkVTEaPidHkzdEd7mLLKUNmRxIGrMCnC7aZ8ANJT/t/fE8yYefKuX2NqsKz+Flw288CIZjK8N07kxIhscTnXE6h1VpMkOOnLqJlAzcwIDAQAB";

//$cry = new CryptonRSA($publicKey);

//$cry = new CryptonRSA($publicKey, $privateKey);

$aes = new CryptonAES("M3ONhPv4nRdR3fl9xiCJ1g==");
var_dump($aes->getSecretKey());



try
{
    $aes->encryptFile("./sw11.mkv", "./sw11.enc.mkv");
    $aes->decryptFile("./sw11.enc.mkv", "./sw11.dec.mkv");

    //$aes->encryptFile("./lol.txt", "./lol.enc.txt");
    //$aes->decryptFile("./lol.enc.txt", "./lol.dec.txt");

    $encodedString = $cry->wrapAES($aes->getSecretKey());
    var_dump($encodedString);
    var_dump($cry->unwrapAES($encodedString));

}
catch(IOException $ex)
{
    var_dump($ex->getMessage());
}

