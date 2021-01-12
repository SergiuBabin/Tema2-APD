# Tema2-APD
## Traffic simulator
### ALGORITMI PARALELI SI DISTRIBUITI

Tema a fost realizata de Babin Sergiu 334CC timp de 2 zile.

Detalii Implementare:

#### ================== Cerinta 1 ====================
Aceasta problema am rezolvata cu un sleep() pentru fiecare masina 
cu timpul ei de asteptare la semafor.

#### ================== Cerinta 2 ====================
La aceasta problema am folosit un semafor() si un sleep(), 
unde semafor-ul dirija astfel incat maxim n masini sa fie 
in intersectie la acelasi moment de timp si sleep-ul oprea 
masinile un anumit timp in intersectie.

#### ================== Cerinta 3 ====================
Aici am format un vector de semafoare care fiecare permitea
doar undei masini sa se afle in intersecti la un moment de timp.

#### ================== Cerinta 4 ====================
Aceasta problema am rezolvato folosind semafor si bariera.
Bariera pentru a respecta conditiile ca toate masinile sa ajunga (reached) 
la sensul giratoriu inainte sa se treaca mai departe si Toate masinile (de pe 
toate sensurile) trebuie sa paraseasca sensul giratoriul inainte ca o 
runda noua de masini sa porneasca.
Semaforul pentru a dirija numarul maxim de masini in sensul giratoriu.

#### ================== Cerinta 5 ====================
Aici am format un vector de semafoare care fiecare permitea
maxim n masini sa se afle in intersecti la un moment de timp.
