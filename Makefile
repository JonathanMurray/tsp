MAIN_FILE = tsp.Main
ARGS = 
PACKAGE = */

JFLAGS = -g
JC = javac
RUN = java #-Xmx1024m


.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

default: clean compile run

clean:
	$(RM) $(PACKAGE)*.class

compile:
	javac $(PACKAGE)*.java

run:
	$(RUN) $(MAIN_FILE) $(ARGS)
