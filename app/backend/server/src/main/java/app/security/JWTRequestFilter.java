package app.security;

import app.APIConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    APIConfig apiConfig;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {

        String servletPath = request.getServletPath();

        // if the path is unsecured, let the request pass through without check
        if (this.apiConfig.UNSECURED_PATHS.contains(servletPath)) {
            chain.doFilter(request, response);
            return;
        }

        if ("OPTIONS".equals(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        // get the encrypted token string from the authorization request header
        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // block the request if no token was found
        if (encryptedToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided. You need to logon first.");
            return;
        }

        // decode the encoded and signed token, after removing optional Bearer prefix
        JWToken jwToken = null;
        try {
            jwToken = JWToken.decode(encryptedToken.replace("Bearer ", ""),
                    this.apiConfig.getIssuer(), this.apiConfig.getPassphrase());
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage() + " You need to logon first.");
            return;
        }

        // obtain the source ip address of the request
        String sourceIpAddress = JWToken.getIpAddress(request);

        // block the request if the source ip cannot be identified
        if (sourceIpAddress == null
                || !sourceIpAddress.equals(jwToken.getIpAddress())
        ) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Cannot identify or validate your source IP-Address.");
            return;
        }

        // pass-on the token info as an attribute for the request
        request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);

        chain.doFilter(request, response);
    }

}