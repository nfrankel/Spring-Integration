/*
 * Copyright 2011 Nicolas Frankel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package ch.frankel.vaadin.spring;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

/**
 * Vaadin application servlet that knows how to connect to a Spring's
 * application context and to retrieve the application bean from it.
 * 
 * <p>
 * The servlet has to be configured in the web.xml, as well as the application
 * bean name (defaults to "application"):
 * 
 * <pre>
 * &lt;servlet&gt;
 *   &lt;servlet-name&gt;Spring Integration&lt;/servlet-name&gt;
 *   &lt;servlet-class&gt;com.packtpub.vaadin.SpringApplicationServlet&lt;/servlet-class&gt;
 *   &lt;init-param&gt;
 *     &lt;param-name&gt;applicationBeanName&lt;/param-name&gt;
 *     &lt;param-value&gt;app&lt;/param-value&gt;
 *   &lt;/init-param&gt;
 * &lt;/servlet&gt;
 * 
 * @author Nicolas Fränkel
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SpringApplicationServlet extends AbstractApplicationServlet {

    /** Default application bean name in Spring application context. */
    private static final String DEFAULT_APP_BEAN_NAME = "application";

    /** Application bean name in Spring application context. */
    private String name;

    /**
     * Get and stores in the servlet the application bean's name in the Spring's
     * context. It's expected to be configured as a the servlet
     * &lt;code&gt;init-param&lt;/code&gt; named applicationBeanName. If no
     * param is found, the default is "application".
     * 
     * @see AbstractApplicationServlet#init(ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {

	super.init(config);

	String name = config.getInitParameter("applicationBeanName");

	this.name = name == null ? DEFAULT_APP_BEAN_NAME : name;
    }

    /**
     * Get the application bean in Spring's context.
     * 
     * @see AbstractApplicationServlet#getNewApplication(HttpServletRequest)
     */
    @Override
    protected Application getNewApplication(HttpServletRequest request) throws ServletException {

	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

	if (wac == null) {

	    throw new ServletException("Cannot get an handle on Spring's context. Is Spring running?"
		    + "Check there's an org.springframework.web.context.ContextLoaderListener configured.");
	}

	Object bean = wac.getBean(name);

	if (!(bean instanceof Application)) {

	    throw new ServletException("Bean " + name + " is not of expected class Application");
	}

	return (Application) bean;
    }

    /**
     * Get the application class from the bean configured in Spring's context.
     * 
     * @see AbstractApplicationServlet#getApplicationClass()
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {

	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

	if (wac == null) {

	    throw new ClassNotFoundException("Cannot get an handle on Spring's context. Is Spring running? "
		    + "Check there's an org.springframework.web.context.ContextLoaderListener configured.");
	}

	Object bean = wac.getBean(name);

	if (bean == null) {

	    throw new ClassNotFoundException("No application bean found under name " + name);
	}

	return (Class) bean.getClass();
    }
}
