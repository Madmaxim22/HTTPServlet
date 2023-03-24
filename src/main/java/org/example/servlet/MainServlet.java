package org.example.servlet;

import org.example.controller.PostController;
import org.example.service.PostService;
import org.example.repository.PostRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;
  private static final String GET = "GET";
  private static final String POST = "POST";
  private static final String DELETE = "DELETE";
  private static final String PATH = "/api/posts";
  private static final String PATH_POST_NUMBER = "/api/posts/\\d+";


  @Override
  public void init() {
    final var repository = new PostRepository();
    final var service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals(GET) && path.equals(PATH)) {
        controller.all(resp);
        return;
      }
      if (method.equals(POST) && path.equals(PATH)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals(GET) && path.matches(PATH_POST_NUMBER)) {
        // easy way
        controller.getById(parseLong(path), resp);
        return;
      }
      if (method.equals(DELETE) && path.matches(PATH_POST_NUMBER)) {
        // easy way
        controller.removeById(parseLong(path), resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  private Long parseLong(String path) {
    return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
  }
}

