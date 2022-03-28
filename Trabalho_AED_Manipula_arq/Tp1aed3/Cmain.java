package Tp1aed3;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;


public class Cmain {
    private static void clearBuffer(Scanner scanner)        //procedimento para linpa o bufffer de memória//
    {
        if (scanner.hasNextLine()) 
        {
            scanner.nextLine();
        }
    }
    static Scanner sca = new Scanner(System.in);
    public static void main(String[] args) throws IOException{
        short opcoes;
        do{
            System.out.print("1->Criar Clube\n2->Ver os clubes criados\n3->Deletar\n4->Update\n5->Registrar Partida\n0->Sair\n");
            System.out.print("Escolha a opção que deseja: ");
            opcoes = sca.nextShort();
         
            // opções usando switch case para o usuario escolher oq fazer com base na escolha do numero.

            switch(opcoes){
                case 1:
                    System.out.println(Create_Time(opcoes));
                break;
                case 2:
                    Read_Time(opcoes);
                break;
                case 3:
                    boolean bol;
                    do{
                        System.out.print("Digite o id do clube que será deletado:");
                        int escolha = sca.nextInt();
                        bol = Times_Delete(escolha);
                        System.out.println((bol == true) ? "Clube deletado":"Clube não existe"); 
                    }while(bol == false);
                break;
                case 4:
                    System.out.print("Informe o id do time que você deseja atualizar:");
                    int timeupdate = sca.nextInt();
                    System.out.print("\nDigite o nome:");
                    String n = sca.next();
                    System.out.print("\nDigite o cnpj:");
                    String novocnpj = sca.next();
                    System.out.print("\nDigite a cidade:");
                    String novacidade = sca.next();

                    ClubeDeFutebol objeto = new ClubeDeFutebol(timeupdate, n, novocnpj, novacidade);

                    System.out.println(objeto);

                 
                    System.out.print("\n----Atualizado!----"+ "\n");
                    Times_Update(objeto);
                break;
                case 5:
                System.out.print("Digite o id do primeiro time:");
                int idtime1 = sca.nextInt();
                
                System.out.print("Digite quantos gols esse time fez?:");
                int golstime1 = sca.nextInt();

                ClubeDeFutebol time1 = read(idtime1);
                // System.out.println(time1);

                System.out.print("Digite o id do segundo time:");
                int idtimes2 = sca.nextInt();

                System.out.print("Digite quantos gols esse time fez?:");
                int golstime2 = sca.nextInt();
                
                ClubeDeFutebol time2 = read(idtimes2);
                // System.out.println(time2);
                
                // Condições de if para atualizar a quantidade de partidas e pontos dos times.
                if(golstime1 > golstime2){
                    time1.partidasepont(3);
                    time2.partidasepont(0);
                }
                else if(golstime2 > golstime1){
                    time1.partidasepont(0);
                    time2.partidasepont(3);
                }
                else{
                    time1.partidasepont(1);
                    time2.partidasepont(1);
                }

                Times_Update(time1);
                Times_Update(time2);

                System.out.println(time1);
                System.out.println(time2);

                break;
                default:
                System.out.println("----Escolha invalida----");
            }
        }while(opcoes!=0);
        sca.close();
    }


    //****************FIM DO MAIN, INICIO DO CRUD ****************/


    public static String Create_Time(int opcoes) {       //Cria o time no arquivo .db//

        ArrayList<ClubeDeFutebol> TimesWrite = new ArrayList<ClubeDeFutebol>();       //Lista para armazerar oque vai ser escrito no arquivo//
        int id = 1;         //id dos clubes//
        int op = 0;         //Variável auxiliar//

        id = Read_Time(opcoes);       //Resgata id para a criação do id seguinte//
        if(id==0){
            id=1;
        }
        else{
            id++;
        }        
        //Capitura informações do clube inserido pelo usuario//
        do{
            clearBuffer(sca);
            System.out.println("Digite o nome do time:");
            String nome = sca.nextLine();
            System.out.println("Digite o CNPJ do time:");
            String CNPJ = sca.nextLine();
            System.out.println("Digite a cidade do time:");
            String cidade = sca.nextLine();
            
            ClubeDeFutebol time = new ClubeDeFutebol(id, nome, CNPJ, cidade);
            TimesWrite.add(time);
            id++;

            System.out.println("1->Acrescentar outro time\n0->Sair");
            System.out.print("Escolha: ");
            op = sca.nextInt();
        }while(op==1);
        RandomAccessFile arq;
        byte[] b;       //Array do tipo byte declaração//
        id--;
        try{            //Tratamento de cod//
            arq = new RandomAccessFile("Tp1aed3/Arquivos/Arquivo.db", "rw");      //Criação/Abertura do arquivo .bd, para leitura e escrita//
            arq.seek(0);
            arq.writeInt(id);
            arq.seek(arq.length());     //Move o ponteiro do arquivo para a posição indicada//
                
                //For para escrever todos os times no arquivo enqunato nomedoarraylist.size//
                for(int repet = 0; repet < TimesWrite.size(); repet++){  
                    arq.writeByte(' ');   
                    b = TimesWrite.get(repet).toByteArray();        //Transforma e escreve os atributos do time.get(posição) na array do tipo byte b//
                    arq.writeInt(b.length);
                    arq.write(b);
                }
                arq.close();
        }catch(Exception e){            //Tratamento de cod//
            System.out.println("ERRO");
        }

        return "----Criado com sucesso----";
    }
    
    
    //função read

    public static int Read_Time(int opcoes) {     //Lê os times do arquivo .db//

        ArrayList<ClubeDeFutebol> TimesRead = new ArrayList<ClubeDeFutebol>();       //Lista para armazerar oque vai ser escrito no arquivo//

        RandomAccessFile arq;
        byte[] b;       //Array do tipo byte declaração//
        byte lapide;
        int tam;        //Variável auxiliar//
        try{
            arq = new RandomAccessFile("Tp1aed3/Arquivos/Arquivo.db", "rw");      //Criação/Abertura do arquivo .bd, para leitura e escrita//
            arq.seek(4);        //Move o ponteiro do arquivo para a posição indicada//

                //While para Ler todos os times no arquivo até que o ponteiro seja do mesmo tamanho do arquivo//
                while(arq.getFilePointer() < arq.length()){
                    ClubeDeFutebol timeR = new ClubeDeFutebol();      //Instancia objetos sem valor nos atributos//
                    lapide = arq.readByte();
                    tam = arq.readInt();        //Lê do arquivo o tamanho da array de bytes do objeto a se lido//
                    // System.out.println(tam);
                    b = new byte[tam];
                    arq.read(b);            //Lê o arquivo e passa tudo para a array b// 
                    if(lapide != '*') {
                        timeR.fromByteArray(b);     //Chama a função fromByteArray para armazenar no objeto os atributos adiquiridos pela array b//
                        TimesRead.add(timeR);
                    }
                }
                arq.close();
        }catch(Exception e){
            System.out.println("ERRO");
        }


        if(opcoes == 2)    
        {
            //Printa na tela os times com seus atributos//
            for(int repet = 0; repet < TimesRead.size(); repet++){
                System.out.println(TimesRead.get(repet));
            }
        }
        return TimesRead.size();
    }

   // função delete

    public static boolean Times_Delete(int id) throws IOException {

        RandomAccessFile arq = new RandomAccessFile("Tp1aed3/Arquivos/Arquivo.db", "rw");
        arq.seek(4);
        // pular cabecalho
        long pos;
        byte lapide;
        int tam;
        byte[] b;
        ClubeDeFutebol objeto;
        while (arq.getFilePointer() < arq.length()) {
            pos = arq.getFilePointer();
            lapide = arq.readByte();
            tam = arq.readInt();
            b = new byte[tam];
            arq.read(b);
            if (lapide != '*') {
                objeto = new ClubeDeFutebol();
                objeto.fromByteArray(b);
                if (objeto.getId() == id) {
                    arq.seek(pos);
                    arq.writeByte('*');
                    arq.close();
                    return true;
                }
            }
        }

        arq.close();
        return false;
    }

    // função update
 
    public static boolean Times_Update(ClubeDeFutebol novoObjeto) throws IOException {
        RandomAccessFile arq = new RandomAccessFile("Tp1aed3/Arquivos/Arquivo.db", "rw");
        arq.seek(4);
        long pos;
        byte lapide;
        byte[] b;
        byte[] novoB;
        int tam;
        ClubeDeFutebol objeto;
        while (arq.getFilePointer() < arq.length()) {
            pos = arq.getFilePointer();
            lapide = arq.readByte();
            tam = arq.readInt();
            b = new byte[tam];
            arq.read(b);
            if (lapide != '*') {
                objeto = new ClubeDeFutebol();
                objeto.fromByteArray(b);
                if (objeto.getId() == novoObjeto.getId()) {
                    novoB = novoObjeto.toByteArray();
                    if (novoB.length < tam) {
                        arq.seek(pos + 5);
                        arq.write(novoB);
                    } else {
                        arq.seek(pos);
                        arq.writeByte('*');
                        arq.seek(arq.length());
                        arq.writeByte(' ');
                        arq.writeInt(novoB.length);
                        arq.write(novoB);
                    }
                    arq.close();

                    return true;
                }
            }
        }
        arq.close();

        return false;
    }


            // função read 


    public static ClubeDeFutebol read(int id) throws IOException {
        RandomAccessFile arq = new RandomAccessFile("Tp1aed3/Arquivos/Arquivo.db", "rw");
        arq.seek(4);
        // pular cabecalho

        byte lapide;
        byte[] b;
        int tam;
        ClubeDeFutebol objeto;
        while (arq.getFilePointer() < arq.length()) {
            lapide = arq.readByte();
            tam = arq.readInt();
            b = new byte[tam];
            arq.read(b);
            if (lapide != '*') {
                objeto = new ClubeDeFutebol();
                objeto.fromByteArray(b);
                // System.out.println(objeto.getId());
                if (objeto.getId() == id) {
                    arq.close();
                    return objeto;
                    
                }
            }
        }
        arq.close();
        return null;
    }

}
