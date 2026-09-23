package practice.irctc.IRCTC.Config.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\"  : \"Unauthorized\" ,"
                + " \"message\"  :\" " + exception.getMessage() + " \" } ");
    }

}
