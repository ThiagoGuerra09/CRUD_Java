package Tp1aed3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//classe clube de futebol que cria atributos e funções 


public class ClubeDeFutebol {
    protected int id;
    protected String nome;
    protected String cnpj;
    protected String cidade;
    protected int partidasJogadas;
    protected int pontos;

    public ClubeDeFutebol(){

    }

    public ClubeDeFutebol (int i, String n, String cn, String c){

        id = i;
        nome = n;
        cnpj = cn;
        cidade = c;
        partidasJogadas = 0;
        pontos = 0;
    }
    public String toString(){
        return "ID: " + id + 
        "\nNOME: " + nome +
        "\nCNPJ: " + cnpj +
        "\nCIDADE: " + cidade +
        "\nPartidas Jogadas: " + partidasJogadas +
        "\nPontos: " + pontos;
    }
    //metodo para passar os atributos do objeto para o array de bytes do main//
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();       //array de bytes//
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.cnpj);
        dos.writeUTF(this.cidade);
        dos.writeInt(this.partidasJogadas);
        dos.writeInt(this.pontos);

        return baos.toByteArray();
}   
    //metodo para passar o array de bytes para as variáveis de atributos do objeto//
    public void fromByteArray(byte[] b) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(b);        //array de bytes//
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.cnpj= dis.readUTF();
        this.cidade= dis.readUTF();
        this.partidasJogadas = dis.readInt();
        this.pontos = dis.readInt();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
    }

    public String getNome() {
        return null;
    }

    public char[] imprimeClube() {
        return null;
    }
    public void partidasepont(int pontos){
        partidasJogadas++;
        this.pontos = pontos;
    }
}
