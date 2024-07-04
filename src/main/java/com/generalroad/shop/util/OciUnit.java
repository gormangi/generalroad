package com.generalroad.shop.util;

import com.generalroad.shop.common.vo.FileVO;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.transfer.UploadConfiguration;
import com.oracle.bmc.objectstorage.transfer.UploadManager;
import com.oracle.bmc.objectstorage.transfer.UploadManager.UploadRequest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OciUnit {

    static final String UPLOAD_BASE_URL = "https://axnjchk5vwme.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/axnjchk5vwme/b/bucket-20240702-1751/o/";
    static final String BUKET_NAME = "bucket-20240702-1751";
    private static final Logger logger = Logger.getLogger(OciUnit.class.getName());

    public static Map<String, Object> createObject(FileVO fileVO) throws Exception {

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource configResource = resourceLoader.getResource("classpath:ocikey/config");
        Resource keyResource = resourceLoader.getResource("classpath:ocikey/oci_api.pem");

        // Create a temporary file for the key file
        Path tempKeyFile = Files.createTempFile("oci_api", ".pem");
        try (InputStream keyInputStream = keyResource.getInputStream()) {
            Files.copy(keyInputStream, tempKeyFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        // Read the config file and update the key_file path
        Path tempConfigFile = Files.createTempFile("config", ".tmp");
        try (InputStream configInputStream = configResource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(configInputStream));
             BufferedWriter writer = Files.newBufferedWriter(tempConfigFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("key_file=")) {
                    line = "key_file=" + tempKeyFile.toString();
                }
                writer.write(line);
                writer.newLine();
            }
        }

        ConfigFileReader.ConfigFile config = ConfigFileReader.parse(tempConfigFile.toString(), "DEFAULT");

        AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);
        ObjectStorage client = new ObjectStorageClient(provider);
        client.setRegion(Region.AP_CHUNCHEON_1);

        GetNamespaceResponse namespaceResponse = client.getNamespace(GetNamespaceRequest.builder().build());
        String namespaceName = namespaceResponse.getValue();

        UploadConfiguration uploadConfiguration =
                UploadConfiguration.builder()
                        .allowMultipartUploads(true)
                        .allowParallelUploads(true)
                        .build();

        UploadManager uploadManager = new UploadManager(client, uploadConfiguration);

        String fileBase64 = fileVO.getFileContent().split(",")[1];
        byte[] decodedFile = Base64.getDecoder().decode(fileBase64);

        String objectName = CreateUuid.createShortUuid() + "." + fileVO.getFileExtentionName();

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedFile)) {

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucketName(BUKET_NAME)
                    .namespaceName(namespaceName)
                    .objectName(objectName)
                    .contentType(fileVO.getFileType())
                    .build();

            UploadRequest uploadDetails = UploadRequest.builder(inputStream, decodedFile.length)
                    .allowOverwrite(true)
                    .build(request);

            uploadManager.upload(uploadDetails);
            client.close();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "File upload failed", e);
        }

        // Delete the temporary key file
        Files.deleteIfExists(tempKeyFile);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("uploadedFileName", objectName);
        resultMap.put("uploadBaseUrl", UPLOAD_BASE_URL);

        return resultMap;
    }

    public static void deleteObject(List<String> fileNames) throws Exception {

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource configResource = resourceLoader.getResource("classpath:ocikey/config");
        Resource keyResource = resourceLoader.getResource("classpath:ocikey/oci_api.pem");

        // Create a temporary file for the key file
        Path tempKeyFile = Files.createTempFile("oci_api", ".pem");
        try (InputStream keyInputStream = keyResource.getInputStream()) {
            Files.copy(keyInputStream, tempKeyFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        // Read the config file and update the key_file path
        Path tempConfigFile = Files.createTempFile("config", ".tmp");
        try (InputStream configInputStream = configResource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(configInputStream));
             BufferedWriter writer = Files.newBufferedWriter(tempConfigFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("key_file=")) {
                    line = "key_file=" + tempKeyFile.toString();
                }
                writer.write(line);
                writer.newLine();
            }
        }

        ConfigFileReader.ConfigFile config = ConfigFileReader.parse(tempConfigFile.toString(), "DEFAULT");

        AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);
        ObjectStorage client = new ObjectStorageClient(provider);
        client.setRegion(Region.AP_CHUNCHEON_1);

        GetNamespaceResponse namespaceResponse = client.getNamespace(GetNamespaceRequest.builder().build());
        String namespaceName = namespaceResponse.getValue();
        for (String fileName : fileNames) {
            DeleteObjectRequest request =
                    DeleteObjectRequest.builder()
                            .bucketName(BUKET_NAME)
                            .namespaceName(namespaceName)
                            .objectName(fileName)
                            .build();
            client.deleteObject(request);
        }
        client.close();

        // Delete the temporary key file
        Files.deleteIfExists(tempKeyFile);
    }

    public static byte[] readObject(String fileName) throws Exception {

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource configResource = resourceLoader.getResource("classpath:ocikey/config");
        Resource keyResource = resourceLoader.getResource("classpath:ocikey/oci_api.pem");

        // Create a temporary file for the key file
        Path tempKeyFile = Files.createTempFile("oci_api", ".pem");
        try (InputStream keyInputStream = keyResource.getInputStream()) {
            Files.copy(keyInputStream, tempKeyFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        // Read the config file and update the key_file path
        Path tempConfigFile = Files.createTempFile("config", ".tmp");
        try (InputStream configInputStream = configResource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(configInputStream));
             BufferedWriter writer = Files.newBufferedWriter(tempConfigFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("key_file=")) {
                    line = "key_file=" + tempKeyFile.toString();
                }
                writer.write(line);
                writer.newLine();
            }
        }

        ConfigFileReader.ConfigFile config = ConfigFileReader.parse(tempConfigFile.toString(), "DEFAULT");

        AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);
        ObjectStorage client = new ObjectStorageClient(provider);
        client.setRegion(Region.AP_CHUNCHEON_1);

        GetNamespaceResponse namespaceResponse = client.getNamespace(GetNamespaceRequest.builder().build());
        String namespaceName = namespaceResponse.getValue();

        GetObjectRequest request =
                GetObjectRequest.builder()
                        .namespaceName(namespaceName)
                        .bucketName(BUKET_NAME)
                        .objectName(fileName)
                        .build();

        GetObjectResponse response = client.getObject(request);
        InputStream ios = response.getInputStream();
        byte[] readFileByteArr = ios.readAllBytes();

        client.close();

        // Delete the temporary key file
        Files.deleteIfExists(tempKeyFile);

        return readFileByteArr;
    }
}
