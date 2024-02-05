package com.limanov.t1.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SupportServlet extends HttpServlet {
    static List<String> helpList = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/plain;charset=UTF-8");
        if (helpList.isEmpty()) {
            helpList.add("У тебя все получится!");
        }
        sendResp(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        String newMessage = req.getParameter("message");
        if (newMessage != null && !newMessage.isEmpty()) {
            helpList.add(newMessage);
        }
        sendResp(resp);
    }

    private void sendResp(HttpServletResponse resp) throws IOException {
        String toSupport = getRandomPhrase();
        PrintWriter writer = resp.getWriter();
        writer.println(String.format("<html>" +
                "<body>" +
                "<h2>%s!<h2>" +
                "</body>" +
                "</html>", toSupport));
    }

    private String getRandomPhrase() {
        int min = 0;
        int max = helpList.size() - 1;
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        return helpList.get(randomNumber);
    }

    public List<String> getHelpList() {
        return helpList;
    }
}
