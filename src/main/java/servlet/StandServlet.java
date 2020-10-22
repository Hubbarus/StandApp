//package servlet;
//
//import dto.ItemDTO;
////import service.Consumer;
//
//import javax.inject.Inject;
//import javax.jms.ObjectMessage;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet(urlPatterns = { "/" })
//public class StandServlet extends HttpServlet {
//
////    @Inject private Consumer consumer;
//
//    private String url = "/WEB-INF/views/main.xhtml";
//
//    @Override
//    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////        List<ItemDTO> receive = consumer.receive();
////        resp.getWriter().write(String.valueOf(receive));
//        resp.sendRedirect(url);
////        req.getRequestDispatcher(url).forward(req, resp);
//    }
//}
