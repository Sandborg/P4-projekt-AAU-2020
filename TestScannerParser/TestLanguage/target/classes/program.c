#include <stdio.h>
#include <string.h>
#include <stdlib.h>
int func (int **a);
int func (int **a){
int b  = 2;
int*bp = &b;
**a =3;
if(*bp==2&&**a==3) { 
printf("%s","awesome"); 

}

}

int main() { 
int x  = 3;
int*xp = &x;
if(*xp==3) { 
printf("%s","awesome"); 

}

return 0; 
}