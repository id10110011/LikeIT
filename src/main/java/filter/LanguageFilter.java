package filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LanguageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String language = getLocale(req);
        req.setAttribute("lang", language);
        chain.doFilter(request, response);
    }

    private String getLocale(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        String language = (String)httpSession.getAttribute("lang");
        if (language == null) {
            language = "en";
            httpSession.setAttribute("lang", "en");
        }
        return language;
    }
}
