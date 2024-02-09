package com.limanov.t1.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SupportServletTest extends Mockito {

    private SupportServlet supportServlet;
    private HttpServletResponse resp;
    private HttpServletRequest req;

    @BeforeEach
    public void init() throws IOException {
        this.supportServlet = new SupportServlet();
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        StringWriter respStringWriter = new StringWriter();
        PrintWriter respWriter = new PrintWriter(respStringWriter);
        when(resp.getWriter()).thenReturn(respWriter);
    }

    @Test
    void doGet() throws ServletException, IOException {
        supportServlet.getHelpList().clear();
        supportServlet.doGet(req, resp);

        assertTrue(supportServlet.getHelpList().contains("У тебя все получится!"));
        Mockito.verify(resp).setContentType(Mockito.eq("text/plain;charset=UTF-8"));
        assertEquals(1, supportServlet.getHelpList().size());
    }

    @Test
    void doPost() throws ServletException, IOException {
        Mockito.when(req.getParameter("message")).thenReturn("TestMessage");

        supportServlet.doPost(req, resp);

        assertTrue(supportServlet.helpList.contains(req.getParameter("message")));
    }
}