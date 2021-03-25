all: 
	jflex Scanner.jflex
	cup grammar.cup
	javac -cp java-cup-11b-runtime.jar Scanner.java sym.java parser.java

parser:
	cup grammar.cup
	
scanner:
	jflex Scanner.jflex

compile:
	javac -cp java-cup-11b-runtime.jar Scanner.java sym.java parser.java
	
main:
	java -cp java-cup-11b-runtime.jar:. *.java Main

remove:
	rm -f *.java *.java~ *.class;
	$(info Files removed!) @true
	
help: 
	$(info ~scanner: Får jflex til at lave scanner/lexer.) @true
	$(info ~parser: Får cup til at lave en parser.) @true
	$(info ~compile: Kompiler Scanner.java, sym.java, parser.java og laver Main.class.) @true
	$(info ~main: Kører Main.) @true
	$(info ~remove: Fjerner alle .java og .class filer fra mappen.) @true 

