import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Author {
    private Document document;          //парсинг страницы пользователя
    private Document booksDoc;          //парсинг книг пользователя
    private String nameAuthor = "";     //имя автора
    private int valuesLikesBooks;       //Количество лайков, просмотров, комментариев взяхтых из всех книг пользователя
    private int valuesViewsBooks;
    private int valuesCommentsBooks;

    public Author(String name){
        this.nameAuthor = name;
        connect();
    }

    private void connect() {
        try {
            document = Jsoup.connect("https://www.surgebook.com/" + nameAuthor).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Методы которые принимают имя, аватарки и статус пользователя
     */
    public String getName(){
        Elements namePerson = document.getElementsByClass("author-name bold");
        return namePerson.text();
    }

    public String getBio(){
        Elements bioPerson = document.getElementsByClass("author-bio");
        return bioPerson.text();
    }

    public String getImg(){
        Elements elements = document.getElementsByClass("user-avatar");
        String url = elements.attr("style");
        url = url.replace("background-image: url('","");
        url = url.replace("');", "");
        return url;
    }

    public String getInfoPerson(){
        String info = "";

        info += "Имя " + getName() + "\n";
        info += "Статус " + getBio() + "\n";

        Elements names = document.getElementsByClass("info-status-name");
        Elements values = document.getElementsByClass("info-status-num");

        for (int i = 0; i < names.size(); i++)
            info += names.get(i).text() + ": " + values.get(i).text() + "\n";


        info += getBooks();
        return info;
    }

    public String getBooks(){
        try {
            booksDoc = Jsoup.connect("https://surgebook.com/" + nameAuthor + "/books/all").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String text = "\nСписок книг\n";
        ArrayList<String> textUrlBooks = new ArrayList<>();

        Elements books = booksDoc.getElementsByClass("book_view_mv1v2_title");
        Elements booksUrl = booksDoc.getElementsByClass("book_view_mv1v2_cover");

        for (int i = 0; i < books.size(); i++) {
            text += books.get(i).text() + "\n";
            textUrlBooks.add(booksUrl.get(i).attr("href"));
        }

        getStatistics(textUrlBooks);

        text += "\n\nКоличество лайков на книгах: " + valuesLikesBooks + "\n";
        text += "Количество просмотров на книгах: " + valuesViewsBooks + "\n";
        text += "Количество комментариев на книгах: " + valuesCommentsBooks + "\n";

        return text;
    }

    private String getStatistics(ArrayList textUrlBooks) {
        for (int i = 0; i < textUrlBooks.size(); i++) { //подключаемся к каждой книге
            try {
                booksDoc = Jsoup.connect(textUrlBooks.get(i).toString()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements elements = booksDoc.getElementsByClass("font-size-14 color-white ml-5");
            /**
             * Все лайки, комменты и просмотры находятся в одном классе, парсим его
             */
            valuesLikesBooks += Integer.valueOf(elements.get(0).text());
            valuesCommentsBooks += Integer.valueOf(elements.get(1).text());
            valuesViewsBooks += Integer.valueOf(elements.get(2).text());
        }
        return "";
    }

}

