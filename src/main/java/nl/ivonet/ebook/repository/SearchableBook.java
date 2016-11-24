package nl.ivonet.ebook.repository;

import javaslang.Tuple2;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

class SearchableBook {

    private static final Pattern EPUB_MATCHER = Pattern.compile("\\/[^/]*\\/[^/]*\\/[^/]*\\/[^/]*\\.epub");
    private static final Base64.Encoder BASE_64_ENCODER = Base64.getEncoder();
    private String path;
    private String title;
    private String description;
    private List<String> authors;
    private String image;

    SearchableBook(Tuple2<File, Book> pair) {
        this.path = retrieveCorrectPath(pair._1.getAbsolutePath());
        this.title = pair._2.getTitle();
        this.authors = pair._2.getMetadata().getAuthors().stream().map(Author::toString).collect(toList());
        this.description = pair._2.getMetadata().getDescriptions().stream().findFirst().orElse("");
        this.image = encodeImage(pair._2.getCoverImage());
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

    public String getImage() {
        return image;
    }

    private String encodeImage(Resource image) {
        try {
            return "data:image/jpg;base64," + BASE_64_ENCODER.encodeToString(image.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String retrieveCorrectPath(String absolutePath) {
        Matcher matcher = EPUB_MATCHER.matcher(absolutePath);
        return matcher.find() ? matcher.group().replace("/", "+") : "";
    }
}
