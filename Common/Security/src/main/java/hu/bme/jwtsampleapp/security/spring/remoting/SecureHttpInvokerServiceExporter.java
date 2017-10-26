package hu.bme.jwtsampleapp.security.spring.remoting;

import hu.bme.jwtsampleapp.security.spring.UserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Extends the default HttpServiceExporter with the inclusion of the JWT token based UserAuthentication in the requests.
 */
@Slf4j
public class SecureHttpInvokerServiceExporter extends HttpInvokerServiceExporter {

    public static final String AUTHENTICATION_KEY = "SECURE_REMOTING_AUTH_KEY";

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            RemoteInvocation invocation = readRemoteInvocation(request);

            try {
                // Check if we ahve an authentication object in the request
                Object rawAuthentication = invocation.getAttribute(AUTHENTICATION_KEY);
                if (rawAuthentication == null) {
                    log.debug("No authentication object found in remote invocation.");
                } else {

                    if(rawAuthentication instanceof AnonymousAuthenticationToken) {
                        AnonymousAuthenticationToken anonymousAuthenticationToken = (AnonymousAuthenticationToken) rawAuthentication;
                        SecurityContextHolder.getContext().setAuthentication(anonymousAuthenticationToken);
                    } else if (rawAuthentication instanceof UserAuthentication) {
                        UserAuthentication authentication = (UserAuthentication) rawAuthentication;
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        log.warn("Unknown Authentication type {} !", rawAuthentication.getClass().getCanonicalName());
                    }
                }

                // Call the actual method
                RemoteInvocationResult result = invokeAndCreateResult(invocation, getProxy());
                writeRemoteInvocationResult(request, response, result);
            } catch (ClassCastException e) {
                log.warn("Bad authentication found in remote invocation!");
                throw new IllegalArgumentException(e);
            } finally {
                SecurityContextHolder.getContext().setAuthentication(null);
            }

        } catch (ClassNotFoundException ex) {
            throw new NestedServletException("Class not found during deserialization", ex);
        }
    }
}
