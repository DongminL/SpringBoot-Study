package com.example.firstproject.crawling;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

@Slf4j
public class Crawling {

    public static void main(String[] args) {
        String url = "https://finance.naver.com/world/";
        Connection conn = Jsoup.connect(url);
        Document document = null;

        try {
            document = conn.get();
            Elements index = document.select(".tb_status thead tr");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
