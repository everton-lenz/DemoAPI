package br.com.sol7.orcamento.util;

import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by User on 29/02/2016.
 */
public class ApplicationResourceServlet extends HttpServlet {

    private ApplicationResources applicationResources;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (applicationResources == null) {
            ServletContext servletContext = getServletContext();
            applicationResources = WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(ApplicationResources.class);
        }
        String filename = URLDecoder.decode(req.getPathInfo().substring(1), "UTF-8");
        Path file = Paths.get(applicationResources.getPath(filename));
        resp.setHeader("Content-Type", Files.probeContentType(file));
        resp.setHeader("Content-Length", String.valueOf(Files.size(file)));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getFileName().toString() + "\"");
        Files.copy(file, resp.getOutputStream());
    }
}
