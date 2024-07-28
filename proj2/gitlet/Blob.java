package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.CWD;
import static gitlet.Repository.getObjectFilePath;
import static gitlet.Utils.*;

public class Blob implements Serializable {
    private String fileName;
    private byte[] content;
    private String id;
    private File blobFilePath;

    public Blob(File file) {
        fileName = file.getName();
        content = readContents(file);
        id = generateId();
        blobFilePath = getObjectFilePath(id);
    }

    public void save() {
        writeObject(blobFilePath, this);
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void writeBlobtoCWD() {
        File filePath = join(CWD, getFileName());
        writeContents(filePath, content);
    }

    private String generateId() {
        return sha1(fileName, content);
    }

    public static Blob readBlob(String blobId) {
        File blobFilePath = getObjectFilePath(blobId);
        return readObject(blobFilePath, Blob.class);
    }

}
