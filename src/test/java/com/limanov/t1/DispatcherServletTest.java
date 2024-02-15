package com.limanov.t1;

import com.limanov.t1.servlet.DispatcherServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DispatcherServletTest {

    @Test
    public void get_is_correct() throws IOException, ServletException {
        ApplicationContext context = new ApplicationContext();
        DispatcherServlet servlet = (DispatcherServlet) context.getBeanForClass(HttpServlet.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        PrintWriter writer = mock(PrintWriter.class);
        servlet.init();
        when(request.getContextPath()).thenReturn("/v1/support/");
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);
        verify(writer).println(anyString());
        verify(writer, times(1)).println(anyString());

    }
}
