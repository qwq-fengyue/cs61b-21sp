package gitlet;

import java.io.File;

import static gitlet.Repository.HEAD;
import static gitlet.Repository.BRANCH_HEADS_DIR;
import static gitlet.Utils.*;

public class Branch {
    public static void setHEAD(String branchName) {
        writeContents(HEAD, branchName);
    }

    public static void setBranchHead(String branchName, String commitId) {
        File branchHeadPath = join(BRANCH_HEADS_DIR, branchName);
        writeContents(branchHeadPath, commitId);
    }

    public static String getCurBranchName() {
        return readContentsAsString(HEAD);
    }

    // return head commitId
    public static String getBranchHead(String branchName) {
        File curBranchHeadPath = join(BRANCH_HEADS_DIR, branchName);
        return readContentsAsString(curBranchHeadPath);
    }
}
