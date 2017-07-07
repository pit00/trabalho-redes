// SERVER EM C PARA O TRABALHO DAS URNAS DE REDES
// GRUPO 10
// Lucas Vieira Costa Nicolau 8517101
// Danilo de Moraes Costa 8921972
// Andréia de Barros Carpi 9292816

#include <stdio.h>
#include <stdlib.h> 
#include <string.h> 
#include <sys/socket.h>
#include <arpa/inet.h> 
#include <unistd.h>

#define TAMANHO_MSG 50       // tamanhos das mensagens que serao passadas pelo socket
#define PORTA_DEFAULT 40010   // porta dada para nosso grupo

int total_votos;

// estrutura urna
struct urna {
    int cod_votacao;
    char nome_candidato[TAMANHO_MSG];
    char partido[TAMANHO_MSG];			// de onde é o professor, ICMC, EESC
    int num_votos;
};

void imprime_ficha(struct urna ficha) {
	printf("---------------------------------------\n");
			    	
	printf("código: %d\n", ficha.cod_votacao);
	printf("candidato: %s", ficha.nome_candidato);
	printf("partido: %s", ficha.partido);
	printf("votos: %d\n", ficha.num_votos);
	printf("---------------------------------------\n");
}

// inicializa a estrutura urna com valores para a votacao
struct urna *criar_urna(struct urna *ficha){
	ficha[0].cod_votacao = 0;
	strcpy(ficha[0].nome_candidato, "Branco\n");
	strcpy(ficha[0].partido, "Nenhum\n");
	ficha[0].num_votos = 0;

	ficha[1].cod_votacao = 1;
	strcpy(ficha[1].nome_candidato, "Nulo\n");
	strcpy(ficha[1].partido, "Nenhum\n");
	ficha[1].num_votos = 0;

	int i;

	for(i=2; i<5; i++) {	
		ficha[i].cod_votacao = i;
		strcpy(ficha[i].partido, "ICMC\n");
		ficha[i].num_votos = 0;
	}


	for(i=5; i<8; i++) {	
		ficha[i].cod_votacao = i;
		strcpy(ficha[i].partido, "EESC\n");
		ficha[i].num_votos = 0;
	}


	// Candidatos do ICMC
	strcpy(ficha[2].nome_candidato, "Julio Estrella\n");
    strcpy(ficha[3].nome_candidato, "Elisa\n");
    strcpy(ficha[4].nome_candidato, "Simões\n");
    
    // Candidatos da EESC
    strcpy(ficha[5].nome_candidato, "Jonas de Carvalho\n");
    strcpy(ficha[6].nome_candidato, "Hélio Navarro\n");

	return ficha;
}

int main(int argc , char *argv[]) {
    int socket_servidor, socket_cliente, c, read_size;
    struct sockaddr_in server , cliente;
    char msg_cliente[TAMANHO_MSG];
    char msg_cliente2[10];
    int voto;
    int porta;
    int i;

    // usado para converter cod do candidato/num votos para string e enviar pelo socket
    char str[TAMANHO_MSG];	
	char str2[TAMANHO_MSG];
		 				

    int votacao_iniciada = 0;		// boolean que representa se a votação foi iniciada
    int candidatos_carregados = 0;		// boolean que representa se a votação foi iniciada

    // cria socket
    socket_servidor = socket(AF_INET , SOCK_STREAM , 0);
    if (socket_servidor == -1) {
        printf("Não foi possivel criar o socket\n");
    }
    printf("Socket foi criado!\n");
     


     // Avisa se vai usar a porta default ou a passada para o projeto
	if (argc != 2) {
		fprintf(stderr, "Usando porta(%d) default para o projeto\n", PORTA_DEFAULT);
	} else {
		fprintf(stderr, "Usando porta (%d) para o projeto\n", atoi(argv[1]));
	}

	// Escolhe se abre a porta pelo numero passado ou pela default
	if(argc == 2) {
		porta = atoi(argv[1]);
	} else {
		porta = PORTA_DEFAULT;
	}

    //Prepara a estrutura sockaddr_in
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(porta);
     
    //Bind
    if(bind(socket_servidor,(struct sockaddr *)&server , sizeof(server)) < 0) {
        //print the error message
        perror("bind failed. Error!");
        return 1;
    }
    printf("bind feita!\n");
     
    //Listen
    listen(socket_servidor , 3);
    

    //aloca estrutura
    struct urna *ficha = (struct urna *) malloc(7 * sizeof(struct urna));

    // inicializa urna
    ficha = criar_urna(ficha);

    total_votos = 0;

    printf("Urna zerada\n");

    //Espera conecao do cliente
    printf("Esperando por conexões do cliente...\n");

    c = sizeof(struct sockaddr_in);
	  
    while(1) {     
	    //aceita conexao do cliente
	    socket_cliente = accept(socket_servidor, (struct sockaddr *)&cliente, (socklen_t*)&c);
	    if (socket_cliente < 0) {
	        perror("Nao foi possivel aceitar o cliente");
	        free(ficha);
	        return 1;
	    }

	    printf("Cliente conectado\n");
	     	
		    //Receive a message from client
		    while((read_size = recv(socket_cliente , msg_cliente, TAMANHO_MSG, 0)) > 0 ) {
		    	voto = atoi(msg_cliente);		// converte voto devolta para string
		    	printf("voto: %d\n", voto);

		    	if(voto == 1) {
		    		printf("votacao iniciada\n");
		    	}

		    	if(voto == 888) {		// encerra o servidor
	    		  	if(recv(socket_cliente , msg_cliente, TAMANHO_MSG, 0) == 0) {		//  cliente desconectado
				    	break;
				    }
			    	total_votos += atoi(msg_cliente);

			    	printf("---------------------------------------\n");
			    	printf("Total de votos : %d\n", total_votos);
			    	printf("---------------------------------------\n");
			    	
		 			for(i=0; i<7; i++) {
					    if(recv(socket_cliente , msg_cliente, TAMANHO_MSG, 0) == 0) {		// cliente desconectado
					    	break;
					    }
				    	ficha[i].num_votos = atoi(msg_cliente);
				    	imprime_ficha(ficha[i]);
			    	}


		    	}

		    	if(voto == 999) {		// carrega candidatos
			 			printf("Candidatos carregado!\n");
			 			candidatos_carregados = 1;
			 			if(send(socket_cliente, "7\n", strlen("7\n"), 0) < 0) return 1;

			 			printf("Enviando candidatos\n");	
			 			for(i=0; i<7; i++) {
			 				sprintf(str, "%d\n", ficha[i].cod_votacao);
			 				sprintf(str2, "%d\n", ficha[i].num_votos);
			 				
			 				if(send(socket_cliente, str, strlen(str), 0) < 0) return 1;
			 				if(send(socket_cliente, ficha[i].nome_candidato, strlen(ficha[i].nome_candidato), 0) < 0) return 1;
			 				if(send(socket_cliente, ficha[i].partido, strlen(ficha[i].partido), 0) < 0) return 1;
			 				if(send(socket_cliente, str2, strlen(str2), 0) < 0) return 1;	
	        			}
			 	}
				  
	 	} 

    	if(read_size == 0) {
	        printf("Cliente desconectado!\n");
	        candidatos_carregados = 0;
	        votacao_iniciada = 0;
	    } else if(read_size == -1) {
	        perror("recv falhou!");
	    }

	}

    // limpa ficha q foi alocada dinamicamente
    free(ficha);	

    //fecha os sockets
    shutdown(socket_cliente, 2);
    shutdown(socket_servidor, 2);
    // retorna erro, pois n era pra ter chego aqui, encerrava no opcode 888
    return 0;
}
