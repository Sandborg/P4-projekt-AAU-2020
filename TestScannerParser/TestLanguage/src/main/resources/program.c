#include <stdio.h>
#include <string.h>
#include <stdlib.h>
int main() { 
int a  = 3;
int*ap = &a;
int b  = 5;
int*bp = &b;
*bp = 3;
if(*ap==*bp) { 
*ap = 3;
char *lol = malloc(sizeof(char) * (1000)); 
strcpy(lol,"jeg er 3 år gammel"); 
char **lolp = &lol;
printf("%s",*lolp); 

}else { 
printf("%s","Nederen"); 

}

return 0; 
}