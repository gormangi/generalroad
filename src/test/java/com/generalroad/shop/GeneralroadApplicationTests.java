package com.generalroad.shop;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeneralroadApplicationTests {

    private static final String SECRET_KEY = "gormangi";

    @Test
    void string_encryption() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(SECRET_KEY);
        config.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        config.setIvGenerator(new RandomIvGenerator());
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        String originalString = "jdbc:log4jdbc:oracle:thin:@gormangi_high?TNS_ADMIN=./classes/Wallet_gormangi";

        // 암호화
        String encryptedString = encryptor.encrypt(originalString);
        System.out.println("Encrypted String ::: ENC(" + encryptedString + ")");

        // 복호화
        String decryptedString = encryptor.decrypt(encryptedString);
        System.out.println("Decrypted String ::: " + decryptedString);

        System.out.println("============================================================");

        String originalString2 = "GENERALROAD";

        // 암호화
        String encryptedString2 = encryptor.encrypt(originalString2);
        System.out.println("Encrypted String ::: ENC(" + encryptedString2 + ")");

        // 복호화
        String decryptedString2 = encryptor.decrypt(encryptedString2);
        System.out.println("Decrypted String ::: " + decryptedString2);

        System.out.println("============================================================");

        String originalString3 = "#Didtjdrb87#";

        // 암호화
        String encryptedString3 = encryptor.encrypt(originalString3);
        System.out.println("Encrypted String ::: ENC(" + encryptedString3 + ")");

        // 복호화
        String decryptedString3 = encryptor.decrypt(encryptedString3);
        System.out.println("Decrypted String ::: " + decryptedString3);

        System.out.println("============================================================");

        String originalString4 = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy";

        // 암호화
        String encryptedString4 = encryptor.encrypt(originalString4);
        System.out.println("Encrypted String ::: ENC(" + encryptedString4 + ")");

        // 복호화
        String decryptedString4 = encryptor.decrypt(encryptedString4);
        System.out.println("Decrypted String ::: " + decryptedString4);

    }

}
