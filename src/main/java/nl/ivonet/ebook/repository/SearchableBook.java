package nl.ivonet.ebook.repository;

import javaslang.Tuple2;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

class SearchableBook {

    private static final Pattern EPUB_MATCHER = Pattern.compile("\\/[^/]*\\/[^/]*\\/[^/]*\\/[^/]*\\.epub");
    private String path;
    private String title;
    private String description;
    private final List<String> authors;

    SearchableBook(Tuple2<File, Book> pair) {
        this.path = retrieveCorrectPath(pair._1.getAbsolutePath());
        this.title = pair._2.getTitle();
        this.authors = pair._2.getMetadata().getAuthors().stream().map(Author::toString).collect(toList());
        this.description = pair._2.getMetadata().getDescriptions().stream().findFirst().orElse("");
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    private String retrieveCorrectPath(String absolutePath) {
        Matcher matcher = EPUB_MATCHER.matcher(absolutePath);
        if (matcher.find()) {
            return matcher.group().replace("/", "+");
        }
        return "";
    }
}
