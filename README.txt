======================================================
1. Goal
======================================================
Spring Integration is an add-on for Vaadin that let you use Spring and its dependency injection mechanism in Vaadin painlessly.
======================================================
2. Use
======================================================
The following steps are necessary:

- use the SpringApplicationServlet as the Vaadin servlet
- configure the application bean's name in the Spring's context
- configure the Spring context startup listener
- configure the Spring context request listener
======================================================
3. Dependencies
======================================================
Spring Integration is of course dependent on Spring (web module) and Vaadin.
======================================================
4. Example
======================================================
A typical web.xml would look like this:

<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <servlet>
  	<servlet-name>Spring Integration</servlet-name>
  	<servlet-class>com.packtpub.vaadin.SpringApplicationServlet</servlet-class>
  	<init-param>
  		<param-name>applicationBeanName</param-name>
  		<param-value>app</param-value>
  	</init-param>
  </servlet>
  <servlet-mapping>
  	<servlet-name>Spring Integration</servlet-name>
  	<url-pattern>/*</url-pattern>
  </servlet-mapping>
  <listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <listener>
  	<listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
  </listener>
</web-app>