#include <stdio.h>
#include <string.h>
#include <stdlib.h>
char * testfunc (float kurt,char *awesome);
int print (char *s);
int pow (int base,int exp);
char * testfunc (float kurt,char *awesome){
float *kurtp = &kurt;
char **awesomep = &awesome;
char append[2000];
char lastValue[2000];
strcpy(lastValue,*awesomep);strcpy(*awesomep, "");
strcat(*awesomep,lastValue);
sprintf(append,"%f",*kurtp);
strcat(*awesomep,append);
;
char *peter = malloc(sizeof(char) * (2000)); 
strcpy(peter, "" );
char **peterp = &peter;
strcpy(lastValue,*peterp);strcpy(*peterp, "");
strcat(*peterp,lastValue);
sprintf(append,"%f",*kurtp);
strcat(*peterp,append);
;
printf("%s",*peterp);
; 
return *awesomep; 

}

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
int a  = 5;
int*ap = &a;
int b;
int*bp = &b;
*bp = *ap;
int c;
int*cp = &c;
if(*ap==*bp) { 
char append[2000];
char lastValue[2000];
*cp = 2;

}
char *fdgisgfj = malloc(sizeof(char) * (2000)); 
strcpy(fdgisgfj, "" );
char **fdgisgfjp = &fdgisgfj;strcat(*fdgisgfjp,"awesome");
;
printf("%d",pow(14,8));
printf("%s","hej hej");
; 
printf("%s","
");
printf("%s",testfunc(10.2,"awesome"));
; 

return 0;
}