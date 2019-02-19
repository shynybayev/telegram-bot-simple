import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Top {
    private Document document;

    public String[] getTopPoems(String period){
        if (period.equals("За месяц")){
            try{
                document = Jsoup.connect("https://www.surgebook.com/poems/popular?when=this_month&order=popular").get();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (period.equals("Сегодня")){
            try {
                document = Jsoup.connect("https://www.surgebook.com/poems/popular?when=today&order=popular").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (period.equals("За месяц")){
            try {
                document = Jsoup.connect("https://www.surgebook.com/poems/popular?when=this_month&order=popular").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (period.equals("За все время")){
            try {
                document = Jsoup.connect("https://www.surgebook.com/poems/popular?order=popular").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String[] info = new String[20];

        Elements name = document.getElementsByClass("poem_mv1v2_title");
        Elements author = document.getElementsByClass("poem_mv1v2_author");
        Elements poem = document.getElementsByClass("poem_mv1v2_text");
        Elements like = document.getElementsByClass("poem_mv1v2_status_list_item poem_mv1v2_status_list_item_like");
        Elements comment = document.getElementsByClass("poem_mv1v2_status_list_item poem_mv1v2_status_list_item_comment");
        Elements view = document.getElementsByClass("poem_mv1v2_status_list_item poem_mv1v2_status_list_item_view");

        for (int i = 0; i < name.size(); i++) {
            if (i < 20){
                info[i] = "\n\n\n" + name.get(i).text() + "\n"
                + "Автор: " + author.get(i).text() + "\n\n\n"
                + poem.get(i).text()
                + "Лайков: " + like.get(i).text()
                + "\nКомментариев: " + comment.get(i).text()
                + "\nПросмотров: " + view.get(i).text();
            }
        }

        return info;
    }

    public String[] getTopBooks(String period){

        if (period.equals("За месяц")){
            try{
                document = Jsoup.connect("https://www.surgebook.com/books/popular?when=this_month&order=popular").get();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (period.equals("Сегодня")){
            try {
                document = Jsoup.connect("https://www.surgebook.com/books/popular?when=today&order=popular").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (period.equals("За неделю")){
            try {
                document = Jsoup.connect("https://www.surgebook.com/books/popular?when=this_week&order=popular").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (period.equals("За все время")){
            try {
                document = Jsoup.connect("https://www.surgebook.com/books/popular?order=popular").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Elements nameBook = document.getElementsByClass("book_view_mv1v2_cover");

        ArrayList<String> books = new ArrayList<>();
        for (int i = 0; i < nameBook.size(); i++) {
            if (i < 10){
                books.add(nameBook.get(i).attr("href"));
            }
        }

        Set<String> hs = new HashSet<>();
        hs.addAll(books);
        books.clear();
        books.addAll(hs);

        String[] strBook = new String[books.size()];
        for (int i = 0; i < books.size(); i++) {
            strBook[i] = books.get(i);
        }

        return strBook;
    }
}
