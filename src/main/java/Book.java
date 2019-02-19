import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Book {
    private Document document; //документ в котором будет хранится book

    public Book(String href){
        connect(href);
    }

    private void connect(String href){
        try {
            document = Jsoup.connect(href).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle(){ //Получаем название обложки
        return document.title();
    }

    public String getLikes(){ //смотрим сколько лайков
        Element element = document.getElementById("likes");
        return element.text();
    }

    public String getDescription(){ //описание книги
        Element element = document.getElementById("description");
        return element.text();
    }

    public String getGenres(){ //вытаскиваем жанры
        Elements elements = document.getElementsByClass("genres d-block");
        return elements.text();
    }

    public String getCommentList(){ //вытаскиваем комментарии
        Elements elements = document.getElementsByClass("comment_mv1_item");
        String comment = elements.text();
        comment.replaceAll("Ответить", "\n\n"); //чистим от нравится, ответить, дат, времени
        comment.replaceAll("Нравится", "");
        comment.replaceAll("\\d{4}-\\d{2}-\\d{2}", "");
        comment.replaceAll("\\d{2}-\\d{2}-\\d{2}", "");
        return comment;
    }

    public String getImg(){ //крадем обложку книги
        Elements elements = document.getElementsByClass("cover-book");
        String url = elements.attr("style");
        url = url.replace("background-image: url('", "");
        url = url.replace("');", ""); //чистим Url от лишнего
        return url;
    }

    public String getAuthorName(){ //Имя автора
        Elements elements = document.getElementsByClass("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis");
        return elements.text();
    }

}
