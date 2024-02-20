package com.limanov.t1.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limanov.t1.annotation.Inject;
import com.limanov.t1.repository.ServletRepositoryImpl;
import com.limanov.t1.model.MessageDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Objects;

public class DispatcherServlet extends HttpServlet {
    private ServletRepositoryImpl servletRepository;
    private ObjectMapper mapper;

    @Inject
    public void setServletRepository(ServletRepositoryImpl servletRepository) {
        this.servletRepository = servletRepository;
    }

    @Inject
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        servletRepository.initServletRepository();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MessageDto responseObject = (MessageDto) getResponseObject(request, "GET", null);
        sendRequest(Objects.nonNull(responseObject), mapper.writeValueAsString(responseObject), response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)  throws IOException {
        MessageDto message = mapper.readValue(request.getReader(), MessageDto.class);
        Boolean responseObject = (Boolean) getResponseObject(request, "POST", message);
        boolean result = Objects.nonNull(responseObject) && responseObject == Boolean.TRUE;
        sendRequest(result, "Succeed", response);
    }

    private void sendRequest(boolean result, String status, HttpServletResponse response) throws IOException {
        String responseStr = result ? status : "FAILED";
        response = updateResponse(result, response);
        PrintWriter out = response.getWriter();
        out.println(responseStr);
    }

    private Object getResponseObject(HttpServletRequest request, String httpMethod, MessageDto message) {
        String path = request.getContextPath();
        SupportServlet controller = servletRepository.getServlet(path);
        Method method = servletRepository.getMethod(path).get(httpMethod);
        Object answer;
        try {
        answer = Objects.isNull(message) ? method.invoke(controller) : method.invoke(controller, message);

        } catch (Exception e) {
            answer = null;
        }
        return answer;
    }

    private HttpServletResponse updateResponse(boolean type, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(Boolean.TRUE.equals(type) ? 200 : 500);
        return response;
    }
}
