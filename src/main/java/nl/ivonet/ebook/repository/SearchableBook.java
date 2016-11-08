package nl.ivonet.ebook.repository;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchableBook {

    private static final Pattern EPUB_MATCHER = Pattern.compile("\\/[^/]*\\/[^/]*\\/[^/]*\\/[^/]*\\.epub");
    private String title;
    private String path;

    SearchableBook(File file) {
        this.path = retrieveCorrectPath(file.getAbsolutePath());
        this.title = file.getName();
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    private String retrieveCorrectPath(String absolutePath) {
        Matcher matcher = EPUB_MATCHER.matcher(absolutePath);
        if (matcher.find()) {
            return matcher.group().replace("/", "+");
        }
        return "";
    }
}
