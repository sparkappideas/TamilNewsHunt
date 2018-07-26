package in.spark.idea.fullynews.FullyParser;

import java.util.Date;

/**
 * Created by Kanagasabapathi on 8/15/2017.
 */

public class Article {

    private String title;
    private String author;
    private String link;
    private Date pubDate;
    private String description;
    private String content;
    private String image;

    public String getTitle() {

        return title;

    }

    public String getAuthor() {

        return author;

    }

    public String getLink() {

        return link;

    }

    public Date getPubDate() {

        return pubDate;

    }

    public String getDescription() {

        return description;

    }

    public String getContent() {

        return content;

    }

    public String getImage() {

        return image;

    }

    public void setTitle(String title) {

        this.title = title;

    }

    public void setAuthor(String author) {

        this.author = author;

    }

    public void setLink(String link) {

        this.link = link;

    }

    public void setPubDate(Date pubDate) {

        this.pubDate = pubDate;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    public void setContent(String content) {

        this.content = content;

    }

    public void setImage(String image) {

        this.image = image;

    }

    @Override
    public String toString() {

        return "Article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", link='" + link + '\'' +
                ", pubDate=" + pubDate +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                '}';

    }
}
