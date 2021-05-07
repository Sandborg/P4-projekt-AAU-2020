#include <stdio.h>
#include <string.h>
#include <stdlib.h>
int main() { 
int h;
int*hp = &h;
*hp = 8+3*2;
int k;
int*kp = &k;
*kp = 3+2+1;
char *lol = malloc(sizeof(char) * (1000)); 
strcpy(lol,"jeg er 321 år gammel321"); 
char **lolp = &lol;

return 0; 
}