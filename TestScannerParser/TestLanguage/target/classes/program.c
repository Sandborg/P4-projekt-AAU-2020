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
printf("%s",""+*ap); 

}else { 
printf("%s","Nederen"); 

}

return 0; 
}