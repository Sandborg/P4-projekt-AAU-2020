#include <stdio.h>
#include <string.h>
#include <stdlib.h>
int lol (int a);
void print (char *s);
int pow (int base,int exp);
int lol (int a){
int *ap = &a;
char append[5];
return *ap+9; 

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
char *hahaha = malloc(sizeof(char) * (1000)); 
strcpy(hahaha, "" );
 = "lol"+"lol"+lol(4);char **hahahap = &hahaha;strcat(*hahahap,"lol");
strcat(*hahahap,"lol");
sprintf(append ,"%d",lol(4));
strcat(*hahahap,append);
;
printf("%s",*hahahap);
; 

return 0;
}