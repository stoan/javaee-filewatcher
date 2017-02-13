# javaee-filewatcher

Java 7 introduced a directory monitoring API, [Watchservice](https://docs.oracle.com/javase/tutorial/essential/io/notification.html)  which is designed to be more efficient than polling. 

There are plenty of tutorials on how to use this API in a Java SE environment but I could barely find tutorials based on a Java EE environment.

This is basically a "template" project to use as reference when you use the Watchservice in a Java EE(transactional) environment, this is meant to help avoid the gotchas I came across.
