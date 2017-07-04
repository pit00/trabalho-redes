// CLIENTE EM C PARA O TRABALHO DAS URNAS DE REDES
// GRUPO 10
// Lucas Vieira Costa Nicolau 8517101
// Danilo de Moraes Costa 8921972
// Andréia de Barros Carpi 9292816

#include <stdio.h> 
#include <stdlib.h> 
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>

#define TAMANHO_MSG 50
#define PORTA_DEFAULT 40010   // porta dada para nosso grupo

int main(int argc , char *argv[]) {
    int socket_cliente;
    struct sockaddr_in servidor;
    char mensagem[TAMANHO_MSG];
    char resposta_server[TAMANHO_MSG];
    char resposta_server2[TAMANHO_MSG];
    char resposta_server3[TAMANHO_MSG];
    char resposta_server4[TAMANHO_MSG];
    int voto;
    int i;
    int porta;
    char *endereco;     

    int votacao_iniciada = 0;
    int candidatos_carregados = 0;

    // Avisa se vai usar a porta default ou a passada para o projeto
    if (argc > 2) {
        fprintf(stderr, "Usando porta (%d) para o projeto\n", atoi(argv[2]));
    } else if(argc == 2) {
        fprintf(stderr, "Usando porta(%d) default para o projeto\n", PORTA_DEFAULT);
    } else {
        fprintf(stderr, "./cliente <endereco> <porta>, sendo <porta> opcional\n");
        return 1;
    }

    // Escolhe se abre a porta pelo numero passado ou pela default
    if(argc > 2) {
        endereco = argv[1];
        porta = atoi(argv[2]);
    } else if(argc == 2) {
        endereco = argv[1];
        porta = PORTA_DEFAULT;
    }

    // Cria o socket
    socket_cliente = socket(AF_INET , SOCK_STREAM , 0);
    if(socket_cliente == -1) {
        printf("Não foi possivel criar o socket");
    }

    // Avisa que criou o socket
    puts("Socket created");
    servidor.sin_addr.s_addr = inet_addr(endereco); 
    servidor.sin_family = AF_INET;
    servidor.sin_port = htons(porta);
 
    // Conecta no server remoto
    if (connect(socket_cliente, (struct sockaddr*)&servidor, sizeof(servidor)) < 0) {
        perror("Falha de conexão!\nErro");      // se der esse erro verifique se o servidor está rodando
        return 1;
    }
     
    printf("Conectado !\n");
    

    printf("-------------------------------------------\n"); 
    printf("         MENU INCIAL DE VOTAÇÃO\n");
    printf("-------------------------------------------\n");

    
    // Mantem comunicação com o server
    for(i=0; ;i++) {
        if(votacao_iniciada && candidatos_carregados) {
    
            printf("-------------------------------------------\n");
            printf("Bem vindo eleitor número %d\n", i);
            printf("-------------------------------------------\n");
            // parte que envia para quem quer votar
            printf("Entre com o voto : ");
        } else if(!votacao_iniciada) {
            printf(" (0) Inicia votação\n"); 
            printf(" (888) Encerrar votação\n");
            printf("Escolha : ");
        } else {
            printf(" (999) Carrega lista de candidatos\n");
            printf(" (888) Encerrar votação\n"); 
            printf("Escolha : ");
        }

        scanf("%d" , &voto);
        // converte o voto para passar para o servidor, string mais fácil de manejar
        sprintf(mensagem, "%d", voto);  
        
        // adiciona essa espaço, porque estava dando conflito qndo enviava uma msg menor q a anterior
        // (se enviasse 12 e depois 2, a terceira ia ser 22)
        // adicionei 9 pq o maior intero(2147483647) tem 10 casas decimais e eu sempre mandarei no minimo 1 casa decimal no voto       
        strcat(mensagem, "         ");      
        //printf("teste mensagem = %s\n ", mensagem);
        
        // Envia os dados
        if(send(socket_cliente , mensagem , strlen(mensagem) , 0) < 0){
            puts("Falha no envio");
            return 1;   // encerra programa
        }
        // se pediu pra encerrar
        if(voto == 888) {
            printf("A votação foi encerrada!\n");

            // recebe mensagem de codigo
            if(recv(socket_cliente, resposta_server, TAMANHO_MSG, 0) < 0) {
                puts("recv failed");
                break;
            }

            //Recebe numero de candidato
            if(recv(socket_cliente, resposta_server, TAMANHO_MSG, 0) < 0) {
                puts("recv failed");
                break;
            }
            // recebeu o numero de candidatos
            int candidatos = atoi(resposta_server);
            
            printf("cand : %d\n", candidatos);
            printf("-------------------------------------------\n"); 
            printf("   Eleiçao para melhor docente de 2017\n");
            printf("-------------------------------------------\n");
            // o primeiro dado enviado pelo servidor sera o numero de candidatos
            for(i=0; i<7; i++) {       // sinal que acabou de receber os carregados

                //Recebe numero de candidato
                if(recv(socket_cliente, resposta_server, TAMANHO_MSG, 0) < 0) {
                    puts("recv failed");
                    break;
                }
                //Recebe o nome do candidato
                if(recv(socket_cliente, resposta_server2, TAMANHO_MSG, 0) < 0) {
                    puts("recv failed");
                    break;
                }

                 //Recebe o nome do partido
                if(recv(socket_cliente, resposta_server3, TAMANHO_MSG, 0) < 0) {
                    puts("recv failed");
                    break;
                }

                 //Recebe o primeiro numero de votos
                if(recv(socket_cliente, resposta_server4, TAMANHO_MSG, 0) < 0) {
                    puts("recv failed");
                    break;
                }

                printf(" no %d | cand.: %s | partido: %s | votos: %d\n", atoi(resposta_server), resposta_server2, resposta_server3, atoi(resposta_server4));
            }
            break;
        }
        
        if(votacao_iniciada && candidatos_carregados) {
            printf("-------------------------------------------\n");
            printf("Parabéns, voto realizado com sucesso!\nPróximo eleitor, por favor...\n");
            printf("-------------------------------------------\n");
        }   

        

        if(!votacao_iniciada) {
            //Recebe resposta do servidor
            if(recv(socket_cliente, resposta_server, TAMANHO_MSG, 0) < 0) {
                puts("recv failed");
                break;
            }

            if(atoi(resposta_server) == 123) {
                votacao_iniciada = 1;
                i=0;
            } else {
                printf("Não foi possivel iniciar a votação no servidor\n");
            }
        } else if(!candidatos_carregados) {
            //Recebe resposta do servidor
            if(recv(socket_cliente, resposta_server, TAMANHO_MSG, 0) < 0) {
                puts("recv failed");
                break;
            }
            

            if(atoi(resposta_server) != 783) {

                int candidatos = atoi(resposta_server);
                
                printf("-------------------------------------------\n"); 
                printf("Eleiçao para diretor da USP Sao Carlos 2017\n");
                printf("-------------------------------------------\n");
                // o primeiro dado enviado pelo servidor sera o numero de candidatos
                for(i=0; i<7; i++) {        // sinal que acabou de receber os carregados
                    
                    if(recv(socket_cliente, resposta_server, TAMANHO_MSG, 0) < 0) {
                        puts("recv failed");
                        break;
                    }
                    //Recebe resposta do servidor
                    if(recv(socket_cliente, resposta_server2, TAMANHO_MSG, 0) < 0) {
                        puts("recv failed");
                        break;
                    }
                    printf(" (%d) %s\n", atoi(resposta_server), resposta_server2);
                    
                }

                printf(" (888) Encerrar votação\n");


                printf("-------------------------------------------\n"); 
                printf(" (!)ATENÇÃO: Caso o número não esteja entre\n os anteriores, será considerado um voto em\n BRANCO(!)\n");
                printf("-------------------------------------------\n"); 
                candidatos_carregados = 1;
                i = 0;
            } else {
                printf("Não foi possivel carregar os candidatos do servidor\n");
            }

        }
   }
    
    // encerra o socket(refêrencia para o código para fechar: http://www.gnu.org/software/libc/manual/html_node/Closing-a-Socket.html)
    shutdown(socket_cliente, 0);        
    return 0;
}
