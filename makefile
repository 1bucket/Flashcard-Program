default: run

run: Driver.class
	java Driver

clean:
	rm *.class

Driver.class: Driver.java
	javac Driver.java