#include <stdio.h>
#include <string.h>
#include <stdlib.h>
int pik (int a);
void print (char *s);
int pow (int base,int exp);
int pik (int a){
int *ap = &a;
char append[5];
int j  = 123123;
int*jp = &j;
printf("%d",*ap);
; 
return 4; 

}

void print (char *s){
char **sp = &s;

}

int pow (int base,int exp){
int *basep = &base;
int *expp = &exp;
char append[5];
int result  = 1;
int*resultp = &result;
int i  = 0;
int*ip = &i; 
for(*ip;*ip<*expp;*ip = *ip+1) { 
char append[5];
*resultp = *resultp**basep;

 }
return *resultp; 

}

int main() { 
char append[5];
int a  = 3;
int*ap = &a;
char *peter = malloc(sizeof(char) * (1000)); 
strcpy(peter, "" );
char **peterp = &peter;strcat(*peterp,"hej");
strcat(*peterp,"pik(2)");
strcat(*peterp,"XD");
;
printf("%s",*peterp);
; 
strcpy(*peterp, "");
strcat(*peterp,"hahaha");
sprintf(append ,"%d",pik(2));
strcat(*peterp,append);
strcat(*peterp,"wtf");
;
printf("%s",*peterp);
; 
strcpy(*peterp, "");
sprintf(append,"%d",*ap);
strcat(*peterp,append);
;

return 0;
}