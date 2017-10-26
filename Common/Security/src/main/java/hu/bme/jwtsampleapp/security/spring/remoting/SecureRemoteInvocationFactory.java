package hu.bme.jwtsampleapp.security.spring.remoting;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecureRemoteInvocationFactory implements RemoteInvocationFactory {

    @Override
    public RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
        RemoteInvocation remoteInvocation = new RemoteInvocation(methodInvocation);
        remoteInvocation.addAttribute(SecureHttpInvokerServiceExporter.AUTHENTICATION_KEY, SecurityContextHolder.getContext().getAuthentication());
        return remoteInvocation;
    }

}
