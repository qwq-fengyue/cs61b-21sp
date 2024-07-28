package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gitlet.Blob.readBlob;
import static gitlet.Branch.*;
import static gitlet.Commit.readCommit;
import static gitlet.Stage.readStage;
import static gitlet.Utils.*;
import static java.lang.System.exit;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The objects directory where all Git objects (blobs, commits, etc.) are stored. */
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    /** The refs directory where references (heads) are stored.*/
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    /** The heads directory where branch heads (pointers to the latest commit on each branch) are stored. */
    public static final File BRANCH_HEADS_DIR = join(REFS_DIR, "heads");
    /** The HEAD file that save the current branch's name. */
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    /** The index file that maps hash codes to objects. (Stage area) */
    public static final File INDEX = join(GITLET_DIR, "index");

    public static final String DEFAULT_BRANCH_NAME = "master";

    /* TODO: fill in the rest of this class. */


    public static void init() {
        checkRepoInit();

        // Create directories
        GITLET_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        REFS_DIR.mkdir();
        BRANCH_HEADS_DIR.mkdir();


        Commit initCommit = new Commit();
        initCommit.save();
        setHEAD(DEFAULT_BRANCH_NAME);
        setBranchHead(DEFAULT_BRANCH_NAME, initCommit.getId());

        Stage stage = new Stage();
        stage.save();
    }

    public static void add(String fileName) {
        checkRepoNotInit();
        File file = join(CWD, fileName);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            exit(0);
        }

        Stage stage = readStage();
        stage.add(file);
        stage.save();
    }

    public static void commit(String message) {
        checkRepoNotInit();
        if (message.isEmpty()) {
            System.out.println("Please enter a commit message.");
            exit(0);
        }

        Stage stage = readStage();
        if (stage.isEmpty()) {
            System.out.println("No changes added to the commit.");
            exit(0);
        }

        String branchName = getCurBranchName();
        String curCommitId = getBranchHead(branchName);
        Commit curCommit = readCommit(curCommitId);

        Map<String, String> fileName2blobId = curCommit.getFileName2blobId();
        for (String removeBlobName : stage.getRemoved()) {
            fileName2blobId.remove(removeBlobName);
        }
        fileName2blobId.putAll(stage.getAdded());

        stage.clear();
        stage.save();

        List<String> parents = new ArrayList<>();
        parents.add(curCommitId);

        Commit newCommit = new Commit(message, parents, fileName2blobId);
        newCommit.save();

        setBranchHead(branchName, newCommit.getId());
    }

    public static void checkoutBranch(String branchName) {

    }

    public static void checkoutFile(String fileName) {
        String curCommitId = readCurCommitId();
        checkoutFile(curCommitId, fileName);
    }

    public static void checkoutFile(String commitId, String fileName) {
        File commitFilePath = getObjectFilePath(commitId);
        if (!commitFilePath.exists()) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }

        Commit commit = readCommit(commitFilePath);
        String blobId = commit.getBlob(fileName);
        if (blobId == null) {
            System.out.println("File does not exist in that commit.");
            exit(0);
        }

        Blob blob = readBlob(blobId);
        blob.writeBlobtoCWD();
    }

    public static void log() {
        Commit curCommit = readCurCommit();
        curCommit.log();
        while (curCommit.hasParent()) {
            String parentCommitId = curCommit.getFirstParentId();
            curCommit = readCommit(parentCommitId);
            curCommit.log();
        }
    }

    private static void checkRepoInit() {
        if(GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            exit(0);
        }
    }

    private static void checkRepoNotInit() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }

    private static String readCurCommitId() {
        String branchName = getCurBranchName();
        return getBranchHead(branchName);
    }

    private static Commit readCurCommit() {
        String commitId = readCurCommitId();
        return readCommit(commitId);
    }
    
    public static File getObjectFilePath(String id) {
        return join(OBJECTS_DIR, id);
    }
}
