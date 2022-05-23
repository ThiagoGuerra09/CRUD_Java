import java.io.*;

public class Clubesindice {
  private Short idIndice;
  private long posiIndice;
  private String lapide;

  public Clubesindice() {
    lapide = " ";
    idIndice = -1;
    posiIndice = -1;

  }

  public Clubesindice(Short id, long posicao, String lapide) {

    this.lapide = lapide;
    idIndice = id;
    posiIndice = posicao;

  }

  public Short getIdIndice() {
    return idIndice;
  }

  public String getLapide() {
    return lapide;
  }

  public long getPosiIndice() {
    return posiIndice;
  }

  public void setIdIndice(Short idIndice) {
    this.idIndice = idIndice;
  }

  public void setLapide(String lapide) {
    this.lapide = lapide;
  }

  public void setPosiIndice(long posiIndice) {
    this.posiIndice = posiIndice;
  }

  // faz a escrita do indice no arquivo 
  public void writeIndicetoArq() {


    // escreverIndice faz a lista de indices e sua respectiva posicao no
    // arquivo que é um número long
  

    try {
      RandomAccessFile arq = new RandomAccessFile("C:/Users/thiag/Downloads/AEDS3/CRUD/TP02/Arquivos/indice.db", "rw");
      long tamdoArq = arq.length();
      arqcrud arqcru = new arqcrud();

      if (tamdoArq == 0) {

        tamdoArq = 13;
        arq.seek(tamdoArq);
        arq.writeShort(idIndice);
        arq.writeLong(posiIndice);
        arq.writeUTF(lapide);

      } else {
        boolean ePar = true;

        if (arqcru.temMargemZero() == true) {

          arq.seek(tamdoArq - 13);
          arq.writeShort(idIndice);
          arq.writeLong(posiIndice);
          arq.writeUTF(lapide);

        } else {

          if ((idIndice) % 2 == 1) {
            ePar = false;
          }

          if (!ePar) {
            arq.seek(tamdoArq + 13);
            arq.writeShort(idIndice);
            arq.writeLong(posiIndice);
            arq.writeUTF(lapide);
          } else {
            arq.seek(tamdoArq - 26);
            // System.out.println(arq.getFilePointer());
            arq.writeShort(idIndice);
            arq.writeLong(posiIndice);
            arq.writeUTF(lapide);
          }

        }
      }

      arq.close();
    } catch (Exception e) {

      String error = e.getMessage();
      System.out.println(error);
      return;

    }

  }

  // swap para ordenar 
  public static void swapIndice(Clubesindice[] a, int posi1, int posi2) {
    Clubesindice vTemp[] = new Clubesindice[1];

    vTemp[0] = a[posi1];
    a[posi1] = a[posi2];
    a[posi2] = vTemp[0];

  }



  // quicksort da distribuicao
  public void quicksortIndice(Clubesindice[] array, int esq, int dir) {

    int i = esq, j = dir;
    int pivo = array[(dir + esq) / 2].idIndice;
    while (i <= j) {
      while (array[i].idIndice < pivo)
        i++;
      while (array[j].idIndice > pivo)
        j--;
      if (i <= j) {
        swapIndice(array, i, j);
        i++;
        j--;
      }
    }
    if (esq < j)
      quicksortIndice(array, esq, j);
    if (i < dir)
      quicksortIndice(array, i, dir);

  }

  // toByteArray para salvar o array 
  public byte[] toByteArray(Clubesindice[] ic, int qtdX) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    try {

      int contador = 0;

      while (contador < qtdX) {
        dos.writeShort(ic[contador].idIndice);
        dos.writeLong(ic[contador].posiIndice);
        dos.writeUTF(ic[contador].lapide);
        contador++;
      }

    } catch (Exception e) {
      String error = e.getMessage();
      System.out.println("Mensagem de error: " + error);

    }
    return baos.toByteArray();
  }

}