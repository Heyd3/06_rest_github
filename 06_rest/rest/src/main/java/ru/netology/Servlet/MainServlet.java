package ru.netology.Servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private final static String API_POSTS_PATH = "/api/posts";
    private final static String API_POSTS_DIGIT_PATH = "/api/posts/\\d+";
    private final static String SLASH = "/";
    private PostController controller;

    @Override
    public void init() {
        final var applicationContext = new AnnotationConfigApplicationContext("ru.netology");
        controller = applicationContext.getBean(PostController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.equals(API_POSTS_PATH)) {
            controller.all(resp);
            return;
        }
        if (path.matches(API_POSTS_DIGIT_PATH)) {
            // easy way
            final var id = Long.parseLong(path.substring(path.lastIndexOf(SLASH)));
            controller.getById(id, resp);
            return;
        }
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.equals(API_POSTS_PATH)) {
            controller.save(req.getReader(), resp);
            return;

        }
        super.doPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.matches(API_POSTS_DIGIT_PATH)) {
            // easy way
            final var id = Long.parseLong(path.substring(path.lastIndexOf(SLASH)));
            controller.removeById(id, resp);
            return;
        }
        super.doDelete(req, resp);
    }
}
