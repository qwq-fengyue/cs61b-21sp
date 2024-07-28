package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Repository.getObjectFilePath;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    private Date date;

    private String timeStamp;

    /** The message of this Commit. */
    private String message;

    private List<String> parents;

    private Map<String, String> fileName2blobId;

    // The SHA1 id.
    private String id;

    private File commitFilePath;

    /* TODO: fill in the rest of this class. */

    public Commit() {
        this(new Date(0), "initial commit", new ArrayList<>(), new HashMap<>());
    }

    public Commit(String message, List<String> parents, Map<String, String> fileName2blobId) {
        this(new Date(), message, parents, fileName2blobId);
    }

    private Commit(Date date, String message, List<String> parents, Map<String, String> fileName2blobId) {
        this.date = date;
        timeStamp = date2timeStamp(date);
        this.message = message;
        this.parents = parents;
        this.fileName2blobId = fileName2blobId;
        id = generateId();
        commitFilePath = getObjectFilePath(id);
    }

    private static String date2timeStamp(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        return dateFormat.format(date);
    }

    private String generateId() {
        return sha1(timeStamp, message, parents.toString(), fileName2blobId.toString());
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getFileName2blobId() {
        return fileName2blobId;
    }

    public String getBlob(String fileName) {
        return fileName2blobId.get(fileName);
    }

    public boolean hasParent() {
        return parents.size() != 0;
    }
    public String getFirstParentId() {
        if (!hasParent()) {
            return "";
        }
        return parents.get(0);
    }

    public void log() {
        System.out.println("===");
        System.out.println("commit " + id);
        if (parents.size() == 2) {
            String parentId = parents.get(0);
            String secondParentId = parents.get(1);
            System.out.println("Merge: " + parentId.substring(0, 7) + " " + secondParentId.substring(0, 7));
        }
        System.out.println("Date: " + timeStamp);
        System.out.println(message + "\n");
    }
    public void save() {
        writeObject(commitFilePath, this);
    }

    public static Commit readCommit(String commitId) {
        File commitFilePath = getObjectFilePath(commitId);
        return readCommit(commitFilePath);
    }

    public static Commit readCommit(File commitFilePath) {
        return readObject(commitFilePath, Commit.class);
    }

}
