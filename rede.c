#include <stdio.h>


void init(int *escolha, int *boolCarregouLista) {
	*escolha = -1;
	*boolCarregouLista = 0;
}

int menu(int bCarregouLista) {
	int escolha;

	printf("1 - votar em alguém\n");	
	printf("2 - votar em branco\n");	
	printf("3 - votar nulo\n");
	printf("999 - carrega a lista de candidatos do servidor\n");
	printf("888 - finalizar as votações da urna e enviar ao servidor\n");


	// verifica se a escolha esta no menu, se nao estiver espera digitar uma escolha valida do menu
	while(1) {
		scanf("%d", &escolha);
		if(bCarregouLista) {
			if(escolha != 1 && escolha != 2 && escolha != 3 && escolha != 999 && escolha != 888) {
				printf("Valor digitado nao pertence a nenhuma opcao do menu, tente novamente!\n");
			} else {
				break;
			}
		} else {
			if(escolha != 999) {
				printf("Tente carregar a lista dos candidados do servidor antes de mais nada\n");
			} else {
				break;
			}
		}
	}

	return escolha;
}

// funcao que vai comunicar com o servidor e retorna falso ou vdd
// nao sei se vai precisar ou se a carrega fara tudo sozinha
int comunica_servidor() {

	return 0;
}


// funcao que vai carregar a lista do servidor e retorna falso ou vdd
int carrega_lista_servidor() {

	return 0;
}

int main() {
	int esc;
	int bCarregouLista;		// bool carregou lista
	
	// inicializa as variaveis do cliente
	init(&esc, &bCarregouLista);

	//executa menu do cliente	
	esc = menu(bCarregouLista);
	if(esc == 999) {
		carrega_lista_servidor();
	} else if(esc == 1) {
		
	} else if(esc == 2) {
		
	} else if(esc == 3) {
		
	} else {		// esc==888, já que só pega valor valido do menu
		// sair
		return 0;
	}

	if(esc == -1) {
		printf("Erro, valor de inicialização, erro na funcao menu()\n");
	}

	return 0;
}