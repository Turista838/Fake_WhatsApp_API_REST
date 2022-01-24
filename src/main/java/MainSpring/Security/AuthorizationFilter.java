package MainSpring.Security;

import MainSpring.Model.AuthenticatedUsers;
import MainSpring.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class AuthorizationFilter extends OncePerRequestFilter
{
    @Autowired
    private AuthenticatedUsers authenticatedUsers;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
    {
        String token = httpServletRequest.getHeader("Authorization");

        if(authenticatedUsers == null){
            ServletContext servletContext = httpServletRequest.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            authenticatedUsers = webApplicationContext.getBean(AuthenticatedUsers.class);
        }

        if (token != null && authenticatedUsers.hashMap.containsKey(token))
        {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));

            UsernamePasswordAuthenticationToken uPAT =
                    new UsernamePasswordAuthenticationToken(token, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(uPAT);
        }

        if(token != null && !authenticatedUsers.hashMap.containsKey(token)){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
