import java.util.*;

%%
%class nums
%standalone
%line
%column
Numbers = \d+\.?\d*  <-Dækker over floats. d = tal i regex. \d+ skal være der 1 eller flere gange. \.? Skal tænkes som et betinget udtryk, punktum er der enten 1 eller 0 gange. d* 0 eller flere gange.  

%{
    List<String> numbers = new ArrayList();
%}

%eof{
    System.out.print("Numbers: ");
    
    for(int i =0; i <numbers.size();i++){
    
    if(i==numbers.size()-1){
    	System.out.println(numbers.get(i));
    	}
    else{
    	System.out.println(numbers.get(i)+", ");
    	}
    }
%eof}

%%
{Numbers} {numbers.add(yytext());}
\n 	{}
.	{}
