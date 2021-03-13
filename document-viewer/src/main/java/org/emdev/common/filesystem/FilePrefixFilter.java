package org.emdev.common.filesystem;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FilePrefixFilter implements FileFilter, FilenameFilter {

    private final Set<String> prefixes;

// --Commented out by Inspection START (3/10/21 5:13 PM):
//    public FilePrefixFilter(final Set<String> prefixes) {
//        this.prefixes = prefixes;
//    }
// --Commented out by Inspection STOP (3/10/21 5:13 PM)

    public FilePrefixFilter(final String... prefixes) {
        this.prefixes = new HashSet<>(Arrays.asList(prefixes));
    }

    @Override
    public final boolean accept(final File file) {
        return acceptImpl(file.getName().toLowerCase());
    }

    @Override
    public boolean accept(final File dir, final String name) {
        return acceptImpl(name.toLowerCase());
    }

// --Commented out by Inspection START (3/10/21 4:49 PM):
//    public boolean accept(final String name) {
//        if (LengthUtils.isEmpty(name)) {
//            return false;
//        }
//        if (!new File(name).exists()) {
//            return false;
//        }
//        return acceptImpl(name.toLowerCase());
//    }
// --Commented out by Inspection STOP (3/10/21 4:49 PM)

    protected boolean acceptImpl(final String name) {
        boolean res = false;
        for (final String prefix : prefixes) {
            res |= acceptImpl(prefix, name);
        }
        return res;
    }

    protected boolean acceptImpl(final String prefix, final String name) {
        return name != null && name.startsWith(prefix);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FilePrefixFilter) {
            final FilePrefixFilter that = (FilePrefixFilter) obj;
            return this.prefixes.equals(that.prefixes);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.prefixes.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + prefixes;
    }

}
