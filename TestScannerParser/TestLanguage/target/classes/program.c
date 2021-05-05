#include <stdio.h>
#include <string.h>
int lol (char *haha);
int lol (char *haha){
char **hahap = &haha;
char *goddag = "hsdf";char **goddagp = &goddag;
*goddagp = "basd";

}

int main() { 
char *hej = "3.3";char **hejp = &hej;
char *med = "lol";char **medp = &med;
lol(*hejp); 

return 0; 
}