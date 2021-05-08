#include <stdio.h>
#include <string.h>
#include <stdlib.h>
void megafedt (int yes);
void megalort (int no);
void megafedt (int yes){
int *yesp = &yes;
if(*yesp!=0) { 
printf("%s","jaja, det lort virker sku!"); 

}else { 
megalort(*yesp); 

}

}

void megalort (int no){
int *nop = &no;
if(*nop==0) { 
printf("%s","nej det er sku lort"); 

}else { 
megafedt(*nop); 

}

}

int main() { 
int a  = 67;
int*ap = &a;
int b  = 5;
int*bp = &b;
*bp = 3;
*ap = *bp;
if(*ap>*bp) { 
megafedt(*ap); 

}else { 
megalort(*bp); 

}

return 0;
}