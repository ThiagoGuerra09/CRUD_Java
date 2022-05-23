import java.util.*;

public class MainClube {


  // Fazer Partida

  public static boolean realizarPartida(Scanner entrada) {

  // para saber se a partida foi relizada com sucesso, retorna true e false.
  

    time time1 = new time();
    time time2 = new time();
    arqcrud arq = new arqcrud();
    Boolean status = false;
    boolean status2 = false;
    entrada = new Scanner(System.in);
    System.out.print("Escreva o nome do primeiro clube: ");
    String timeUm = entrada.nextLine();
    System.out.println();
    System.out.print("Escreva o nome do segundo clube: ");
    String timeDois = entrada.nextLine();
    System.out.println();
    boolean retorno = true;

    long retornoTimeUm = arq.procurarClube(timeUm, time1);

    if (retornoTimeUm >= 0) {
      long retornoTimeDois = arq.procurarClube(timeDois, time2);

      System.out.println(time1.toString());
      System.out.println(time2.toString());

      String confirmartimes;
      System.out.println("Esses são os times que deseja realizar uma partida? Responda com sim ou nao");
      confirmartimes = entrada.nextLine();

      if (confirmartimes.toLowerCase().equals("sim")) {

        if (retornoTimeDois >= 0) {

          short placarTimeUm = 0;
          short placarTimeDois = 0;
          byte pontos = 0;

          System.out.println("Digite o placar do jogo !");
          System.out.print("Gols Time " + timeUm + ": ");
          placarTimeUm = entrada.nextShort();
          System.out.println();
          System.out.print("Gols Time " + timeDois + ": ");
          placarTimeDois = entrada.nextShort();

          if (placarTimeUm > placarTimeDois) {

            pontos = 3;
            status = arq.arquivoUpdate(timeUm, entrada, "Parcial", pontos, time1);
            pontos = 0;
            status2 = arq.arquivoUpdate(timeDois, entrada, "Parcial", pontos, time2);

          } else {
            pontos = 3;
            if (placarTimeUm < placarTimeDois) {
              pontos = 3;
              status = arq.arquivoUpdate(timeDois, entrada, "Parcial", pontos, time2);
              pontos = 0;
              status2 = arq.arquivoUpdate(timeUm, entrada, "Parcial", pontos, time1);
            } else {
              pontos = 1;
              if (placarTimeUm == placarTimeDois) {
                status = arq.arquivoUpdate(timeUm, entrada, "Parcial", pontos, time1);
                status2 = arq.arquivoUpdate(timeDois, entrada, "Parcial", pontos, time2);
              }
            }

          }
        } else {
          System.out.println("Time 2 não encontrado - Partida CANCELADA");

          retorno = false;
        }
      } else {
        System.out.println("Partida CANCELADA");
        retorno = false;
      }

    } else {
      System.out.println("Time 1 não encontrado - Partida CANCELADA");

      retorno = false;
    }

    if (status == true && status2 == true) {
      System.out.println("Partida Concluida!");

      System.out.println("------------------X------------------");
      System.out.println(time1.toString());
      System.out.println("------------------X------------------");
      System.out.println(time2.toString());
      System.out.println("------------------X------------------");

    } else {
      System.out.println("Partida não concluida");
      retorno = false;
    }

    return retorno;
  }


  // eNumero recebe como parametro duas Strings, a primeira é a que vai
  // ser analiada, e a segunda é para testar a string 



  public static boolean eNumero(String str, String tipo) {

    boolean idOrnot = str.matches("-?\\d+");
    boolean verifica = false;
    if (idOrnot == true) {

      if (tipo.equals("Byte") == true) {

        int Inteiro = Integer.parseInt(str);

        if (Inteiro <= 127) {
          verifica = true;
        }

      }

    }

    return verifica;
  }




  public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in);
    arqcrud arqcru = new arqcrud();
    time futebas = new time();

    arqcru.salvarPrecisaOrdernar(2);
    String menuStr = "";
    byte menu = 0;
    byte zero = 0;
    int menuconvert = 0;
    boolean contador = false;
    boolean eNumero = false;

    while (contador == false) {
      System.out
          .println(
              "0 - Encerrar Programa \n1 - Create Clube \n2 - Realizar uma partida\n3 - Read do arquivo\n4 - Update de um Registro\n5 - Delete de um Registro\n6 - Limpar todos os Arquivos");

      menuStr = entrada.nextLine();
      eNumero = eNumero(menuStr, "Byte");

      if (eNumero == true) {
        menuconvert = Integer.parseInt(menuStr);
        menu = (byte) menuconvert;

        switch (menu) {
          case 0:
            contador = true;
            arqcru.salvarPrecisaOrdernar(1);
            System.out.println("Encerrando o programa...");
            break;
          case 1:
            arqcru.criarClube(entrada);
            break;
          case 2:
            // System.out.println("CASE 2 - Fazer Partida");
            realizarPartida(entrada);
            break;
          case 3:

            System.out.println("Digite o ID, Nome do Clube ou Cidade a ser procurado no arquivo");
            String entradaPesquisadeClube = entrada.nextLine();
            futebas.printarClubesExistentes(arqcru.procurarClube(entradaPesquisadeClube, futebas));
            break;
          case 4:
            System.out.println("Digite o ID ou Nome do Clube na qual será atualizado os dados !");

            String entradaUpg = entrada.nextLine();
            arqcru.arquivoUpdate(entradaUpg, entrada, "Completo", zero, null);

            break;
          case 5:

            System.out.println("Digite o ID para ser deletado");
            String idDelete = entrada.nextLine();
            arqcru.arquivoDelete(idDelete, entrada, futebas);
            break;

          case 6:
            arqcru.deletaTudo(1, 1, 1, 1, 1, 1, 1);// Apagar todo o arquivo
            arqcru.setPrecisarOrdenar(true);
            arqcru.salvarPrecisaOrdernar(1);
            break;

          default:
            System.out.println("Digito Não Valido... Inserir novamente o digito");

        }
        entrada = new Scanner(System.in);// limpar o Buffer 
      } else {
        System.out.println("Digito Não Valido... Inserir novamente o digito");

      }

    }

    entrada.close();

  }
}
