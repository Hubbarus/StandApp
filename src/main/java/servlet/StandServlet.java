package servlet;

import dto.ItemDTO;
import service.Consumer;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = { "/" })
public class StandServlet extends HttpServlet {

    @Inject private Consumer consumer;

    private String url = "/WEB-INF/views/main.xhtml";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        consumer.start();
        resp.setContentType("text/html");
        resp.setIntHeader("Refresh", 3);
        PrintWriter writer = resp.getWriter();

        writer.write(String.valueOf(consumer.getItems()));
    }
}
