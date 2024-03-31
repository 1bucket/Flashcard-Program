default: run

run: Driver.class Manage.class
	java Driver

clean:
	rm *.class

Driver.class: Driver.java
	javac Driver.java

Manage.class: Manage.java
	javac Manage.java