#include <stdio.h>
#include <string.h>
#include <stdlib.h>
void print (char *s);
int pow (int a);
void print (char *s){
char **sp = &s;

}

int pow (int a){
int *ap = &a;
return *ap**ap; 

}

int main() { 
int i  = 0;
int*ip = &i;
for(*ip;*ip<5;*ip = *ip+1) { 
char *lol = malloc(sizeof(char) * (1000)); 
strcpy(lol,"Jubiii 1"); 
char **lolp = &lol;
if(*ip==3) { 
printf("%s","haha"); 

}
printf("%s",*lolp); 

 }

return 0;
}