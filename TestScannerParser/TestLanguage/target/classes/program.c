#include <stdio.h>
#include <string.h>
#include <stdlib.h>
int print (char *s);
int pow (int base,int exp);
int print (char *s){
char **sp = &s;
char append[2000];
char lastValue[2000];
return 0; 

}

int pow (int base,int exp){
int *basep = &base;
int *expp = &exp;
char append[2000];
char lastValue[2000];
int result  = 1;
int*resultp = &result;
int i  = 0;
int*ip = &i; 
for(*ip;*ip<*expp;*ip = *ip+1) { 
char append[2000];
char lastValue[2000];
*resultp = *resultp**basep;

 }
return *resultp; 

}

int main() { 
char append[2000];
char lastValue[2000];
int a  = 8;
int*ap = &a;
*ap = 3+3*7+2/2;
char *p = malloc(sizeof(char) * (2000)); 
strcpy(p, "" );
char **pp = &p;sprintf(append,"%d",*ap);
strcat(*pp,append);
strcat(*pp,"hej");
;
printf("%s",*pp);
; 

return 0;
}