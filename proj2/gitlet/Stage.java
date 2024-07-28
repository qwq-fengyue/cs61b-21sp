package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import static gitlet.Repository.INDEX;
import static gitlet.Utils.readObject;
import static gitlet.Utils.writeObject;
import static java.lang.System.exit;

public class Stage implements Serializable {
    // map fileName to blob id
    private final HashMap<String, String> added;
    private final HashSet<String> removed;

    public Stage() {
        added = new HashMap<>();
        removed = new HashSet<>();
    }

    public void add(File file) {
        String fileName = file.getName();
        if (isRemoved(fileName)) {
            removed.remove(fileName);
        }

        Blob blob = new Blob(file);
        if (added.containsKey(fileName) && added.get(fileName).equals(blob.getId())) {
            exit(0);
        }
        blob.save();
        added.put(fileName, blob.getId());
    }

    public void save() {
        writeObject(INDEX, this);
    }

    public boolean isEmpty() {
        return added.isEmpty() && removed.isEmpty();
    }

    public boolean isRemoved(String fileName) {
        return removed.contains(fileName);
    }

    public HashSet<String> getRemoved() {
        return removed;
    }

    public HashMap<String, String> getAdded() {
        return added;
    }

    public void clear() {
        added.clear();
        removed.clear();
    }

    public static Stage readStage() {
         return readObject(INDEX, Stage.class);
    }

}
